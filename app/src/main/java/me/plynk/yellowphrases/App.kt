package me.plynk.yellowphrases

import android.app.Application
import me.plynk.yellowphrases.di.components.AppComponent
import me.plynk.yellowphrases.di.components.DaggerAppComponent
import me.plynk.yellowphrases.di.modules.AppModule

class App: Application() {

    companion object {
        lateinit var appplicationComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appplicationComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}