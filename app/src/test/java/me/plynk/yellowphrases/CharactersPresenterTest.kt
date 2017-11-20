package me.plynk.yellowphrases

import io.reactivex.Observable
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.features.characters.CharactersContract
import me.plynk.yellowphrases.features.characters.CharactersPresenter
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.util.RxSchedulersOverrideRule
import me.plynk.yellowphrases.util.TestDataFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersPresenterTest {

    @Mock lateinit var mockCharactersViews: CharactersContract.View
    @Mock lateinit var mockRemoteRepository: RemoteRepository

    private var charactersPresenter: CharactersContract.Presenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        charactersPresenter = CharactersPresenter(mockRemoteRepository)
        charactersPresenter?.attachView(mockCharactersViews)
    }

    @After
    fun tearDown() {
        charactersPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getCharactersSuccess() {
        val charactersList = TestDataFactory.makeCharacersList(10)
        `when`(mockRemoteRepository.getCharacters())
                .thenReturn(Observable.just(charactersList))

        charactersPresenter?.loadCharacters()

        verify<CharactersContract.View>(mockCharactersViews).showLoading()
        verify<CharactersContract.View>(mockCharactersViews).dismissLoading()
        verify<CharactersContract.View>(mockCharactersViews).showCharacters(charactersList)
        verify<CharactersContract.View>(mockCharactersViews, never()).showErrorMsg(ArgumentMatchers.anyString())
        verify<CharactersContract.View>(mockCharactersViews, never()).showTryAgainError(ArgumentMatchers.anyString(), any())
    }

    @Test
    @Throws(Exception::class)
    fun getCharactersError() {
        `when`(mockRemoteRepository.getCharacters())
                .thenReturn(Observable.error<List<Character>>(RuntimeException("Service Error")))

        charactersPresenter?.loadCharacters()

        verify<CharactersContract.View>(mockCharactersViews).showLoading()
        verify<CharactersContract.View>(mockCharactersViews).dismissLoading()
        verify<CharactersContract.View>(mockCharactersViews).showTryAgainError(ArgumentMatchers.anyString(), any())
        verify<CharactersContract.View>(mockCharactersViews, never()).showCharacters(ArgumentMatchers.anyList<Character>())
    }

}