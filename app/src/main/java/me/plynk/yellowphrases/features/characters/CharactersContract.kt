package me.plynk.yellowphrases.features.characters

import me.plynk.yellowphrases.delegate.LoadingDelegate
import me.plynk.yellowphrases.features.base.BasePresenter
import me.plynk.yellowphrases.features.base.BaseView
import me.plynk.yellowphrases.model.Character

object CharactersContract {

    interface View : BaseView, LoadingDelegate{
        fun showCharacters(characters: List<Character>)
    }

    interface Presenter : BasePresenter<View>{
        fun loadCharacters()
    }
}