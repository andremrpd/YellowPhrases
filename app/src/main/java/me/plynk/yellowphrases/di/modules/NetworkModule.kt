package me.plynk.yellowphrases.di.modules

import dagger.Module
import dagger.Provides
import me.plynk.yellowphrases.BuildConfig
import me.plynk.yellowphrases.data.remote.PlynkAPI
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.data.remote.RemoteRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRemoteRepository(plynkAPI: PlynkAPI): RemoteRepository = RemoteRepositoryImpl(plynkAPI)

    @Provides
    @Singleton
    internal fun providePlynkAPI(retrofit: Retrofit): PlynkAPI = retrofit.create(PlynkAPI::class.java)

    @Provides
    @Singleton
    internal fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://j4g2c85pmf.execute-api.us-east-1.amazonaws.com/Prod/")
                .build()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }
}