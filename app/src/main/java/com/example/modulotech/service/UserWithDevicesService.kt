package com.example.modulotech.service

import com.example.modulotech.BuildConfig
import com.example.modulotech.service.model.DeviceRemote
import com.example.modulotech.service.model.HeaterRemote
import com.example.modulotech.service.model.LightRemote
import com.example.modulotech.service.model.RollerShutterRemote
import com.example.modulotech.service.model.UserWithDevicesRemote
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import java.lang.reflect.Type
import java.util.Date
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UserWithDevicesService {

    companion object {
        private const val BASE_URL = "http://storage42.com/"

        fun create(): UserWithDevicesService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)

                // Use custom okHttpClient for cache setup to avoid duplicate http request for the same endpoint
                // As I choose to split the request according to REST model
                .client(HttpClient.okHttpClient) // provide standard http client
                .addConverterFactory(GsonConverterFactory.create(GsonParser.gson))
                .build()
                .create(UserWithDevicesService::class.java)
        }
    }

    @GET("modulotest/data.json")
    suspend fun getData(): UserWithDevicesRemote
}

object GsonParser {

    private val mDeviceFactory = RuntimeTypeAdapterFactory
        .of(DeviceRemote::class.java, "productType")
        .registerSubtype(LightRemote::class.java, LightRemote.KEY_PARSER)
        .registerSubtype(HeaterRemote::class.java, HeaterRemote.KEY_PARSER)
        .registerSubtype(RollerShutterRemote::class.java, RollerShutterRemote.KEY_PARSER)

    val gson: Gson = GsonBuilder()
        .registerTypeAdapterFactory(mDeviceFactory)
        .registerTypeAdapter(Date::class.java, object : JsonDeserializer<Date> {
            @Throws(JsonParseException::class)
            override fun deserialize(
                json: JsonElement,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): Date {
                return Date(json.asJsonPrimitive.asLong)
            }
        }).create()
}

object HttpClient {

    private val mLogger = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
        else HttpLoggingInterceptor.Level.NONE
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(mLogger)
        .build()
}
