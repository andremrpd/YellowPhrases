package me.plynk.yellowphrases.di.components

import android.app.Application
import android.content.Context
import dagger.Component
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.di.ApplicationContext
import me.plynk.yellowphrases.di.modules.AppModule
import me.plynk.yellowphrases.di.modules.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun remoteRepository(): RemoteRepository

}