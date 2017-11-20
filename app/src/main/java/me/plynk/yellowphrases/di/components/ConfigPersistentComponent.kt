package me.plynk.yellowphrases.di.components

import dagger.Component
import me.plynk.yellowphrases.di.ConfigPersistent
import me.plynk.yellowphrases.di.modules.ActivityModule
import me.plynk.yellowphrases.di.modules.FragmentModule

@ConfigPersistent
@Component(dependencies = arrayOf(AppComponent::class))
interface ConfigPersistentComponent {

    fun plusActivityComponent(activityModule: ActivityModule): ActivityComponent

    fun plusFragmentComponent(fragmentModule: FragmentModule): FragmentComponent

}
