package banquemisr.challenge05.data.remote

import banquemisr.challenge05.Application
import banquemisr.challenge05.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Helper class to communicate with the dKilo API.
 *
 * It uses Retrofit and Picasso.
 *
 * @see ApiInterface
 */
class ApiClient {
    private lateinit var mRetrofit: Retrofit
    private lateinit var mService: ApiInterface

    private val cacheSize = 10 * 1024 * 1024 // 10 MB

    var token: String? = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiNWRmZGE2MzA0ZmQzYWE1NWM1MDQ3YTY0NzhmZGUzZCIsInN1YiI6IjY1ZTM0Y2VkZmUwNzdhMDE4NTBlZmZmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.sWFklZiKwB01bORAc4HJ92sip_FKp3t11GSBR2POuPI"

    companion object {
        /**
         * Create an authenticated instance of the client.
         */
        fun authenticated(token: String?): ApiClient
        {
            val client = ApiClient()
            client.token = token

            return client
        }
    }

    /**
     * Request interceptors to add headers and optionally monitor requests.
     */
    private fun interceptors(): Array<Interceptor> {


        return arrayOf(
            Interceptor { chain ->
                chain.run {
                    proceed(request().newBuilder().apply {

                        addHeader("Accept", "application/json")
//                        addHeader("User-Agent", "Android/${BuildConfig.VERSION_CODE}")
                        token?.let {
                            addHeader("Authorization", "Bearer $token")
                        }

                    }.build())
                }
            },
        )
    }

    /**
     * OkHttp client to be used in Retrofit and Picasso.
     *
     * This is where we setup caching and add interceptors.
     */
    private fun client(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
//            .cache(Cache(Application.appCacheDir, cacheSize.toLong()))
        val interceptors = interceptors()

        for (interceptor in interceptors) {
            builder.addInterceptor(interceptor)
        }
        /** logging interceptor to log network requests to logCat   */
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    /**
     * Retrofit instance creator method.
     *
     * This is where we setup JSON decoding and the API's base URL.
     */
    fun retrofit(): Retrofit {
        if (!::mRetrofit.isInitialized) {
            val client = client()
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            mRetrofit = Retrofit.Builder()
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))

                .baseUrl(BuildConfig.API_BASE).build()
        }

        return mRetrofit
    }

    /**
     * Retrofit service created from our API interface.
     */
    fun service(): ApiInterface
    {
        if (!::mService.isInitialized) {
            mService = retrofit().create(ApiInterface::class.java)
        }

        return mService
    }



}