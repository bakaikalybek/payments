package kg.bakai.payments

import android.app.Application
import kg.bakai.payments.di.appModule
import kg.bakai.payments.di.networkModule
import kg.bakai.payments.di.repositoryModule
import kg.bakai.payments.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, networkModule, viewModelModule, repositoryModule))
        }
    }
}