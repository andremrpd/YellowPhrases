package me.plynk.yellowphrases.features.phrases

import me.plynk.yellowphrases.delegate.LoadingDelegate
import me.plynk.yellowphrases.features.base.BasePresenter
import me.plynk.yellowphrases.features.base.BaseView
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase

object PhrasesContract {
    interface View : BaseView, LoadingDelegate {
        fun showPhrases(phrases: List<Phrase>)
    }

    interface Presenter : BasePresenter<View>{
        fun loadPhrases(character: Character)
        fun savePhrase(phraseItem: PhrasesAdapter.PhraseItem)

    }
}