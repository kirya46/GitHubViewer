package com.common.githubviewer.di

import android.app.Application
import android.util.JsonToken
import com.common.githubviewer.api.GitHubApi
import com.common.githubviewer.util.AppPrefs
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketException
import javax.inject.Singleton


/**
 * Created by Kirill Stoianov on 09/07/18.
 */
@Module
class ApiModule {

    companion object {
        private val TAG = ApiModule::class.java.simpleName
    }

    /* PROVIDE APIs */
    @Provides
    @Singleton
    fun provideGitHubApiApi(builder: Retrofit.Builder): GitHubApi {
        return builder.baseUrl(GitHubApi.BASE_URL).build().create(GitHubApi::class.java)
    }
    /* PROVIDE APIs */


    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(appPrefs: AppPrefs): Interceptor {




        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                return@setErrorHandler
            }
            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                /*Thread.currentThread().uncaughtExceptionHandler.handleException(Thread.currentThread(), e)*/
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                /*Thread.currentThread().uncaughtExceptionHandler .handleException(Thread.currentThread(), e)*/
                return@setErrorHandler
            }
        }

        return Interceptor { chain ->


            val ongoing = chain.request().newBuilder()


            //put auth string
            appPrefs.getUserAuthString()?.let {
                ongoing.addHeader("Authorization",it)
            }

            //build request
            val request = ongoing.build()


            //execute request
            val response = chain.proceed(request)

            //throw error for non-success responses
            if (response.code()!= HttpURLConnection.HTTP_OK){
                throw RuntimeException("Request error: ${response.message()} status ${response.code()}.")
            }

            return@Interceptor response
        }
    }

    @Provides
    fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {

        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory<String>())
                .create()
    }

    class NullStringToEmptyAdapterFactory<T> : TypeAdapterFactory {

        override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

            val rawType: Class<T> = type.rawType as Class<T>

            return if (rawType == Double::class.javaObjectType) {
                DoubleAdapter() as TypeAdapter<T>
            } else {
                null
            }
        }
    }

    class DoubleAdapter : TypeAdapter<Number>() {

        override fun write(out: JsonWriter, value: Number) {
            out.value(value)
        }


        override fun read(`in`: JsonReader): Number? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            try {
                val result = `in`.nextString()
                return if ("" == result) {
                    null
                } else java.lang.Double.parseDouble(result)
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }

        }
    }
}