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

class LoginFragment: Fragment() {

    private lateinit var account : Auth0
    private lateinit var loggedInText: TextView
    private lateinit var welcomeText: TextView
    private var loggedIn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        setHasOptionsMenu(true)

        //OAUTH
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun checkIfToken(){
        val token = CredentialsManager.getAccessToken(requireContext())
        if(token != null){
            //checking if the token works...
            showUserProfile(token)
        }
        else {
            Toast.makeText(context, "Token doesn't exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLoggedInText() {
        if(loggedIn) {
            welcomeText.text = getText(R.string.welcome_text)
            loggedInText.text = getText(R.string.login_text)
        }
        else {
            loggedInText.text = getText(R.string.logout_text)
        }
    }

    private fun loginWithBrowser() {
        // Setup the WebAuthProvider, using the custom scheme and scope.

        WebAuthProvider.login(account)
            .withScheme("demo")
            .withScope("openid profile email")
            // Launch the authentication passing the callback where the results will be received
            .start(requireContext(), object : Callback<Credentials, AuthenticationException> {
                // Called when there is an authentication failure
                override fun onFailure(error: AuthenticationException) {
                    loggedIn = false
                }

                // Called when authentication completed successfully
                override fun onSuccess(result: Credentials) {
                    // Get the access token from the credentials object.
                    // This can be used to call APIs
                    val accessToken = result.accessToken
                    Toast.makeText(context, accessToken, Toast.LENGTH_SHORT).show()

                    CredentialsManager.saveCredentials(requireContext(), result)
                    checkIfToken()
                    setLoggedInText()
                }
            })
    }

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

    private fun showUserProfile(accessToken: String){
        val client = AuthenticationAPIClient(account)

        // With the access token, call `userInfo` and get the profile from Auth0.
        client.userInfo(accessToken)
            .start(object : Callback<UserProfile, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Timber.i(error.stackTraceToString())
                    loggedIn = false
                    setLoggedInText()
                }

                override fun onSuccess(result: UserProfile) {
                    // We have the user's profile!
                    Timber.i("SUCCESS! got the user profile")
                    // val email = result.email.toString()
                    // val name = result.name.toString()
                    loggedIn = true
                    setLoggedInText()
                }
            })

    }

}