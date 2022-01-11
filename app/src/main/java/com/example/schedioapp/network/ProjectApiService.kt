package com.example.schedioapp.network

import android.util.Log
import com.example.schedioapp.domain.Project
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import java.lang.Exception
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ArraySerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import org.hamcrest.StringDescription
import org.json.JSONObject
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import timber.log.Timber
import java.io.IOException
import java.util.logging.Level.INFO
import java.util.logging.Logger
import kotlin.reflect.KClass


private const val BASE_URL = "https://localhost:44342/api/"

/*private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()*/

// https://square.github.io/okhttp/3.x/logging-interceptor/okhttp3/logging/HttpLoggingInterceptor.Level.html
private val logger = HttpLoggingInterceptor()
    .apply { level = HttpLoggingInterceptor.Level.BODY }

val client = OkHttpClient.Builder()
    .addInterceptor(httpInterceptor()) // TODO
    .build()

val contentType = "application/json".toMediaType()
val converterFactory = Json.asConverterFactory(contentType)

private val retrofit = Retrofit.Builder()
    //.addConverterFactory(MoshiConverterFactory.create(moshi))
    .addConverterFactory(converterFactory)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

/*fun getAllProjs(): ApiProjectContainer {
    return Json.decodeFromString<ApiProjectContainer>(wrappedStringJson)
}*/

interface ProjectApiService {
    // https://auth0.com/docs/security/tokens/access-tokens
    // @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJwcm9qZWN0bWFzdGVyQHNrZWRpby5iZSIsInVuaXF1ZV9uYW1lIjoicHJvamVjdG1hc3RlckBza2VkaW8uYmUiLCJodHRwOi8vc2NoZW1hcy5taWNyb3NvZnQuY29tL3dzLzIwMDgvMDYvaWRlbnRpdHkvY2xhaW1zL3JvbGUiOiJhZG1pbiIsImV4cCI6MTY0MDYzNjUxN30.bjF8Mt4vZ6wz6x1M4sXO8DgvHbEeu8x50oS1Zh23c_U")
    @GET("projects")
    @Throws(Exception::class)
    fun getAllProjectsAsync(): Deferred<ApiProjectContainer>

    /* @GET("projects/9")
    @Throws(Exception::class)
    fun getProjectAsync(): Deferred<ApiProject>*/

    @POST("projects")
    fun putProject(@Body project: ApiProject): Deferred<ApiProject>
    // https://www.youtube.com/watch?v=m3yj7JaTTPI
    // https://material.io/components/radio-buttons/android#using-date-pickers
    // https://www.youtube.com/watch?v=qcDlcITNqnE
    // https://medium.com/swlh/simplest-post-request-on-android-kotlin-using-retrofit-e0a9db81f11a
}

/*val wrappedStringJson = """{
  "apiProjects": [
    {
      "id": 9,
      "naam": "Campagne dashboard",
      "startDatum": "2021-02-01T00:00:00",
      "eindDatum": "2021-03-15T00:00:00",
      "budget": 5000,
      "status": "Voltooid",
      "type": "Marketing, Technology"
    },
    {
      "id": 8,
      "naam": "Event tracking website",
      "startDatum": "2021-04-10T00:00:00",
      "eindDatum": "2021-04-21T00:00:00",
      "budget": 8500,
      "status": "Voltooid",
      "type": "Marketing, Technology"
    },
    {
      "id": 7,
      "naam": "Facebook pagina opzetten",
      "startDatum": "2021-02-07T00:00:00",
      "eindDatum": "2021-06-10T00:00:00",
      "budget": 75000,
      "status": "Hangende",
      "type": "General management, Marketing, Technology"
    },
    {
      "id": 3,
      "naam": "GDPR audit",
      "startDatum": "2021-10-10T00:00:00",
      "eindDatum": "2021-12-15T00:00:00",
      "budget": 20000,
      "status": "Open",
      "type": "Finance, General management, R&D"
    },
    {
      "id": 6,
      "naam": "Lunch teambuilding",
      "startDatum": "2021-08-01T00:00:00",
      "eindDatum": "2021-08-15T00:00:00",
      "budget": 17999,
      "status": "Open",
      "type": "General management"
    },
    {
      "id": 2,
      "naam": "Magazine-app",
      "startDatum": "2021-03-05T00:00:00",
      "eindDatum": "2021-07-20T00:00:00",
      "budget": 250000,
      "status": "Bezig",
      "type": "Marketing, Product management, Technology"
    },
    {
      "id": 1,
      "naam": "Post app tracking",
      "startDatum": "2021-01-15T00:00:00",
      "eindDatum": "2021-04-15T00:00:00",
      "budget": 100000,
      "status": "Voltooid",
      "type": "Marketing, Product management"
    },
    {
      "id": 4,
      "naam": "Product management app",
      "startDatum": "2021-05-01T00:00:00",
      "eindDatum": "2021-09-30T00:00:00",
      "budget": 1500000,
      "status": "Bezig",
      "type": "Product management, R&D, Technology"
    },
    {
      "id": 5,
      "naam": "Tevredenheid oogproduct",
      "startDatum": "2021-07-17T00:00:00",
      "eindDatum": "2021-10-31T00:00:00",
      "budget": 80000,
      "status": "Open",
      "type": "R&D, Sales"
    },
    {
      "id": 10,
      "naam": "Werving nieuwe collega",
      "startDatum": "2021-05-01T00:00:00",
      "eindDatum": "2021-06-15T00:00:00",
      "budget": 5900,
      "status": "Hangende",
      "type": "General management, HR"
    }
  ]
}""".trimIndent()
*/
/*
val jsonString = """[
    {
      "id": 9,
      "naam": "Campagne dashboard",
      "startDatum": "2021-02-01T00:00:00",
      "eindDatum": "2021-03-15T00:00:00",
      "budget": 5000,
      "status": "Voltooid",
      "type": "Marketing, Technology"
    },
    {
      "id": 8,
      "naam": "Event tracking website",
      "startDatum": "2021-04-10T00:00:00",
      "eindDatum": "2021-04-21T00:00:00",
      "budget": 8500,
      "status": "Voltooid",
      "type": "Marketing, Technology"
    },
    {
      "id": 7,
      "naam": "Facebook pagina opzetten",
      "startDatum": "2021-02-07T00:00:00",
      "eindDatum": "2021-06-10T00:00:00",
      "budget": 75000,
      "status": "Hangende",
      "type": "General management, Marketing, Technology"
    },
    {
      "id": 3,
      "naam": "GDPR audit",
      "startDatum": "2021-10-10T00:00:00",
      "eindDatum": "2021-12-15T00:00:00",
      "budget": 20000,
      "status": "Open",
      "type": "Finance, General management, R&D"
    },
    {
      "id": 6,
      "naam": "Lunch teambuilding",
      "startDatum": "2021-08-01T00:00:00",
      "eindDatum": "2021-08-15T00:00:00",
      "budget": 17999,
      "status": "Open",
      "type": "General management"
    },
    {
      "id": 2,
      "naam": "Magazine-app",
      "startDatum": "2021-03-05T00:00:00",
      "eindDatum": "2021-07-20T00:00:00",
      "budget": 250000,
      "status": "Bezig",
      "type": "Marketing, Product management, Technology"
    },
    {
      "id": 1,
      "naam": "Post app tracking",
      "startDatum": "2021-01-15T00:00:00",
      "eindDatum": "2021-04-15T00:00:00",
      "budget": 100000,
      "status": "Voltooid",
      "type": "Marketing, Product management"
    },
    {
      "id": 4,
      "naam": "Product management app",
      "startDatum": "2021-05-01T00:00:00",
      "eindDatum": "2021-09-30T00:00:00",
      "budget": 1500000,
      "status": "Bezig",
      "type": "Product management, R&D, Technology"
    },
    {
      "id": 5,
      "naam": "Tevredenheid oogproduct",
      "startDatum": "2021-07-17T00:00:00",
      "eindDatum": "2021-10-31T00:00:00",
      "budget": 80000,
      "status": "Open",
      "type": "R&D, Sales"
    },
    {
      "id": 10,
      "naam": "Werving nieuwe collega",
      "startDatum": "2021-05-01T00:00:00",
      "eindDatum": "2021-06-15T00:00:00",
      "budget": 5900,
      "status": "Hangende",
      "type": "General management, HR"
    }
  ]"""
*/
/*val wrappedStringJson = """{
  "apiProjects": $jsonString
}""".trimIndent()*/

object ProjectApi {
    val retrofitService: ProjectApiService by lazy {
        retrofit.create(ProjectApiService::class.java)
    }

    fun ProjectApiService.mockPutJoke(project: ApiProject): ApiProject {
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

/*object ProjectListSerializer : JsonTransformingSerializer<List<ApiProject>>(ListSerializer(ApiProject.serializer())) {
    // If response is not an array, then it is a single object that should be wrapped into the array
    override fun transformDeserialize(element: JsonElement): JsonElement =
        if (element !is JsonArray) JsonArray(listOf(element)) else element
}*/

/*object ProjectsSerializer: KSerializer<ArrayList<ApiProject>> {
    override val descriptor = ListSerializer(ApiProject.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: ArrayList<ApiProject>) {

        encoder.encodeSerializableValue(ListSerializer(ProjectSerializer()), value)
    }

    override fun deserialize(decoder: Decoder): ArrayList<ApiProject> {
        val projects = decoder.decodeSerializableValue(ListSerializer(ProjectSerializer()))

        return projects.toList() as ArrayList<ApiProject>
    }

}*/

object  ProjectsSerializer: JsonTransformingSerializer<List<ApiProject>>(ListSerializer(ProjectSerializer()))

/*object ApiProjectContainerSerializer: KSerializer<ApiProjectContainer> {
    override val descriptor = buildClassSerialDescriptor("ApiProjectContainer") {
        element<Array<ApiProject>>("apiProjects")
    }

    override fun serialize(encoder: Encoder, value: ApiProjectContainer) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor,0,ListSerializer(ProjectSerializer()), value)
        }
    }

}*/

/* Example API response
[
  {
    "id": 9,
    "naam": "Campagne dashboard",
    "startDatum": "2021-02-01T00:00:00",
    "eindDatum": "2021-03-15T00:00:00",
    "budget": 5000,
    "status": "Voltooid",
    "type": "Marketing, Technology"
  },...
]
 */

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
            // Timber.i("Test interceptor response string: $responseString")

            Log.i("Test interceptor response string: ", responseString)
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