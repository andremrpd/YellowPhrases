package me.plynk.yellowphrases.di.components

import dagger.Subcomponent
import me.plynk.yellowphrases.di.PerFragment
import me.plynk.yellowphrases.di.modules.FragmentModule
import me.plynk.yellowphrases.features.characters.CharactersFragment
import me.plynk.yellowphrases.features.phrases.PhrasesFragment

@PerFragment
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent{

    fun inject(fragment: PhrasesFragment)
    fun inject(fragment: CharactersFragment)
}