package banquemisr.challenge05.di

import banquemisr.challenge05.BuildConfig
import banquemisr.challenge05.data.remote.ApiInterface
import banquemisr.challenge05.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Request interceptors to add headers and optionally monitor requests.
     */
    @Provides
    @Singleton
     fun provideInterceptors(): Array<Interceptor>
    {
         val token = Constants.token
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
    @Provides
    @Singleton
     fun provideClient(interceptors : Array<Interceptor>): OkHttpClient
    {
        val builder = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
//            .cache(Cache(Application.appCacheDir, cacheSize.toLong()))

        for (interceptor in interceptors)
        {
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
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit
    {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder().client(client).addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.API_BASE).build()
    }

    /**
     * Retrofit service created from our API interface.
     */
    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface
    {
        return retrofit.create(ApiInterface::class.java)
    }

}