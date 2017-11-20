package me.plynk.yellowphrases.features.characters

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.di.ConfigPersistent
import me.plynk.yellowphrases.features.base.BasePresenterImpl
import me.plynk.yellowphrases.model.Character
import javax.inject.Inject

@ConfigPersistent
class CharactersPresenter @Inject
constructor(private val remoteRepository: RemoteRepository) : BasePresenterImpl<CharactersContract.View>(), CharactersContract.Presenter {

    private var characters: List<Character>? = null

    override fun loadCharacters() {
        if (characters == null){
            loadCharactersFromNetwork()
        }else{
            view?.showCharacters(characters!!)
        }
    }

    private fun loadCharactersFromNetwork() {

        remoteRepository.getCharacters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    view?.showLoading()
                }.doAfterTerminate {
                    view?.dismissLoading()
                }.subscribe({ result ->
                    characters = result
                    view?.showCharacters(result)
                }, { error ->
                    view?.showTryAgainError(error.message, View.OnClickListener{loadCharactersFromNetwork()})
                    error.printStackTrace()
                })
    }

}