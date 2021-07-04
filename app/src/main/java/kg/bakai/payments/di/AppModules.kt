package kg.bakai.payments.di

import android.preference.PreferenceManager
import kg.bakai.payments.util.SessionManager
import okhttp3.OkHttpClient
import com.google.gson.GsonBuilder
import kg.bakai.payments.data.network.ApiService
import kg.bakai.payments.data.repository.MainRepository
import kg.bakai.payments.ui.HomeViewModel
import kg.bakai.payments.ui.LoginViewModel
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    single { SessionManager(get()) }
}

val repositoryModule = module {
    single { MainRepository(get(), get()) }
}

val viewModelModule = module {
    single { HomeViewModel(get()) }
    single { LoginViewModel(get()) }
}

val networkModule = module {
    single {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        clientBuilder
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("app-key", "12345")
                builder.header("v", "1")
                chain.proceed(builder.build())
            }
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.build()
    }

    single {
        val gson = GsonBuilder()
            .create()

        Retrofit.Builder()
            .baseUrl("http://82.202.204.94/api-test/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)

    }
}