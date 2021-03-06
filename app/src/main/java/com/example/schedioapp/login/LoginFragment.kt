package com.example.schedioapp.login

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.example.schedioapp.R
import timber.log.Timber

/**
 * A simple [Fragment] subclass
 * Displays the login functionality
 * @property account The Auth0 account information
 * @property loggedInText The text mentioning that you are logged in
 * @property welcomeText The text set to welcome a user
 * @property loggedIn The text stating whether you are logged in or not
 */
class LoginFragment: Fragment() {

    private lateinit var account : Auth0
    private lateinit var loggedInText: TextView
    private lateinit var welcomeText: TextView
    private var loggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        setHasOptionsMenu(true)

        account = Auth0(
            "P19jcnynaTin7PEGUimvTOHloP2bUGeM",
            "dev-l0pdju9l.eu.auth0.com"
        )

        val button = view.findViewById<Button>(R.id.login_button)
        button?.setOnClickListener {
            loginWithBrowser()
        }

        val logoutbutton = view.findViewById<Button>(R.id.logout_button)
        logoutbutton?.setOnClickListener {
            logout()
        }
        welcomeText = view.findViewById(R.id.welcome_text)
        loggedInText = view.findViewById(R.id.login_text)

        checkIfToken()
        setLoggedInText()

        return view
    }

    /**
     * Creates the options menu (overflow menu)
     * @property menu The menu element
     * @property inflater The class used to render the menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    /**
     * Checks whether an item was selected
     *
     * @property item The selected menu item
     * @return States whether an item was selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    /**
     * Checks if an access token is present
     */
    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            showUserProfile(token)
        }
        else {
            Toast.makeText(context, "Token doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets the text when you are logged in
     */
    private fun setLoggedInText() {
        if(loggedIn) {
            welcomeText.text = getText(R.string.welcome_text)
            loggedInText.text = getText(R.string.login_text)
        }
        else {
            loggedInText.text = getText(R.string.logout_text)
        }
    }

    /**
     * Method used for login, stating success or failure
     */
    private fun loginWithBrowser() {

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    loggedIn = false
                }
                override fun onSuccess(result: Credentials) {
                    val accessToken = result.accessToken
                    Toast.makeText(context, accessToken, Toast.LENGTH_SHORT).show()

                    CredentialsManager.saveCredentials(requireContext(), result)
                    checkIfToken()
                    setLoggedInText()
                }
            })
    }

    /**
     * Method used to logout of the app
     */
    private fun logout() {
        WebAuthProvider.logout(account)
            .withScheme("demo")
            .start(requireContext(), object: Callback<Void?, AuthenticationException> {
                override fun onSuccess(result: Void?) {
                    Toast.makeText(context, "logout OK", Toast.LENGTH_SHORT).show()
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(context, "logout NOK", Toast.LENGTH_SHORT).show()
                }
            })
    }

    /**
     * Method used to display the user profile
     *
     * @property accessToken The access token of the user
     */
    private fun showUserProfile(accessToken: String){
        val client = AuthenticationAPIClient(account)

        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Timber.i(error.stackTraceToString())
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onSuccess(result: UserProfile) {
                    Timber.i("SUCCESS! got the user profile")
                    loggedIn = true
                    setLoggedInText()
                }
            })

    }

}