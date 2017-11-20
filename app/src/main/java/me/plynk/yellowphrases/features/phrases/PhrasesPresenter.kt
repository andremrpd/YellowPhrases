package me.plynk.yellowphrases.features.phrases

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.di.ConfigPersistent
import me.plynk.yellowphrases.features.base.BasePresenterImpl
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import javax.inject.Inject

@ConfigPersistent
class PhrasesPresenter @Inject
constructor(private val remoteRepository: RemoteRepository) : BasePresenterImpl<PhrasesContract.View>(), PhrasesContract.Presenter {

    private var phrases: List<Phrase>? = null

    override fun loadPhrases(character: Character) {

        if (phrases == null){
            loadPhrasesFromNetwork(character)
        }else{
            view?.showPhrases(phrases!!)
        }
    }

    private fun loadPhrasesFromNetwork(character: Character) {
        remoteRepository.getPhrases(character._id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    view?.showLoading()
                }.doAfterTerminate {
                    view?.dismissLoading()
                }.subscribe({ result ->
                    phrases = result
                    view?.showPhrases(result)
                }, { error ->
                    view?.showTryAgainError(error.message, View.OnClickListener{loadPhrases(character)})
                    error.printStackTrace()
                })
    }

    override fun savePhrase(phraseItem: PhrasesAdapter.PhraseItem) {

        remoteRepository.savePhrase(phraseItem.phrase!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe{
                    phraseItem.showLoading()
                }.doAfterTerminate {
                    phraseItem.dismissLoading()
                }.subscribe({ result ->
                    phraseItem.setSaved(result.status)
                    if (!result.status) view?.showErrorMsg("Error saving phrase: " +  result.description)
                }, { error ->
                    phraseItem.setSaved(false)
                    view?.showErrorMsg("Error saving phrase: " +  error.message)
                    error.printStackTrace()
                })
    }


}