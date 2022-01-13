package com.example.schedioapp.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.lang.Exception
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.http.*
import java.io.IOException
import timber.log.Timber

private const val BASE_URL = "https://localhost:44342/api/"

// https://square.github.io/okhttp/3.x/logging-interceptor/okhttp3/logging/HttpLoggingInterceptor.Level.html
private val logger = HttpLoggingInterceptor()
    .apply { level = HttpLoggingInterceptor.Level.BODY }

val client = OkHttpClient.Builder()
    .addInterceptor(httpInterceptor())
    .build()

val contentType = "application/json".toMediaType()
val converterFactory = Json.asConverterFactory(contentType)

private val retrofit = Retrofit.Builder()
    .addConverterFactory(converterFactory)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ProjectApiService {
    // https://auth0.com/docs/security/tokens/access-tokens
    // @Headers("Authorization: Bearer ")
    @GET("projects")
    @Throws(Exception::class)
    fun getAllProjectsAsync(): Deferred<ApiProjectContainer>

    @POST("projects")
    fun putProject(@Body project: ApiProject): Deferred<ApiProject>
    // https://www.youtube.com/watch?v=m3yj7JaTTPI
    // https://material.io/components/radio-buttons/android#using-date-pickers
    // https://www.youtube.com/watch?v=qcDlcITNqnE
    // https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a

    @DELETE("projects/{id}")
    fun deleteProject(@Path("id") id: Int): Deferred<ApiProject>

}

object ProjectApi {
    val retrofitService: ProjectApiService by lazy {
        retrofit.create(ProjectApiService::class.java)
    }

    fun ProjectApiService.mockPutProject(project: ApiProject): ApiProject {
        return project
    }

    fun ProjectApiService.mockDeleteProject(project: ApiProject): ApiProject {
        return project
    }
}

class ProjectSerializer: KSerializer<ApiProject> {
    override fun deserialize(decoder: Decoder): ApiProject = decoder.decodeStructure(descriptor) {
        var id = 0
        var naam = ""
        var startDatum = ""
        var eindDatum = ""
        var budget = 0.0
        var status = ""
        var type = ""

        while(true) {
            when(val index = decodeElementIndex(descriptor)) {
                0 -> id = decodeIntElement(descriptor,0)
                1 -> naam = decodeStringElement(descriptor,1)
                2 -> startDatum = decodeStringElement(descriptor,2)
                3 -> eindDatum = decodeStringElement(descriptor,3)
                4 -> budget = decodeDoubleElement(descriptor,4)
                5 -> status = decodeStringElement(descriptor,5)
                6 -> type = decodeStringElement(descriptor,6)
                CompositeDecoder.DECODE_DONE -> break
                else -> error("Unexpected index: $index")
            }
        }
        ApiProject(id, naam, startDatum, eindDatum, budget, status, type)
    }

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ApiProject") {
        element<Int>("id")
        element<String>("naam")
        element<String>("startDatum")
        element<String>("eindDatum")
        element<Double>("budget")
        element<String>("status")
        element<String>("type")
    }

    override fun serialize(encoder: Encoder, value: ApiProject) {
        encoder.encodeStructure(descriptor) {
            encodeIntElement(descriptor,0,value.id)
            encodeStringElement(descriptor, 1, value.naam)
            encodeStringElement(descriptor, 2, value.startDatum)
            encodeStringElement(descriptor, 3, value.eindDatum)
            encodeDoubleElement(descriptor, 4, value.budget)
            encodeStringElement(descriptor, 5, value.status)
            encodeStringElement(descriptor, 6, value.type)
        }
    }
}

object  ProjectsSerializer: JsonTransformingSerializer<List<ApiProject>>(ListSerializer(ProjectSerializer()))

class httpInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val originalResponse: Response = chain.proceed(originalRequest)
        var responseString: String = originalResponse.body!!.string()
        val responseBody: ResponseBody
        val alteredResponseBody: ResponseBody
        val convertJsonString: String
        val contentType = "application/json".toMediaType()

        // Case 2: JsonArray is passed by means of API
        if(originalRequest.isHttps && originalResponse.isSuccessful && responseString.startsWith("[")) {
            Timber.i("Test interceptor response string: $responseString")
            convertJsonString = """{
                "apiProjects": $responseString
            }""".trimIndent()
            alteredResponseBody = convertJsonString.toResponseBody(contentType)
            return originalResponse.newBuilder().body(alteredResponseBody).build()
        }

        // Case 1: JsonObject is passed by means of API
        responseBody = responseString.toResponseBody(contentType)
        return originalResponse.newBuilder().body(responseBody).build()
    }

}