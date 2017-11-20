package me.plynk.yellowphrases

import io.reactivex.Observable
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.features.phrases.PhrasesAdapter.PhraseItem
import me.plynk.yellowphrases.features.phrases.PhrasesContract
import me.plynk.yellowphrases.features.phrases.PhrasesPresenter
import me.plynk.yellowphrases.model.Phrase
import me.plynk.yellowphrases.model.SaveRequest
import me.plynk.yellowphrases.util.RxSchedulersOverrideRule
import me.plynk.yellowphrases.util.TestDataFactory
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhrasesPresenterTest {

    @Mock lateinit var mockPhrasesView: PhrasesContract.View
    @Mock lateinit var mockRemoteRepository: RemoteRepository

    private var phrasesPresenter: PhrasesContract.Presenter? = null

    @JvmField
    @Rule
    val mOverrideSchedulersRule = RxSchedulersOverrideRule()

    @Before
    fun setUp() {
        phrasesPresenter = PhrasesPresenter(mockRemoteRepository)
        phrasesPresenter?.attachView(mockPhrasesView)
    }

    @After
    fun tearDown() {
        phrasesPresenter?.detachView()
    }

    @Test
    @Throws(Exception::class)
    fun getCharactersSuccess() {

        val phrasesList = TestDataFactory.makePhraseList(10)
        val character = TestDataFactory.makeCharacter("characterId")
        Mockito.`when`(mockRemoteRepository.getPhrases("characterId"))
                .thenReturn(Observable.just(phrasesList))

        phrasesPresenter?.loadPhrases(character)

        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showLoading()
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).dismissLoading()
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showPhrases(phrasesList)
        Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showErrorMsg(ArgumentMatchers.anyString())
        Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showTryAgainError(ArgumentMatchers.anyString(), ArgumentMatchers.any())
    }

    @Test
    @Throws(Exception::class)
    fun getCharactersError() {

        val character = TestDataFactory.makeCharacter("characterId")
        Mockito.`when`(mockRemoteRepository.getPhrases("characterId"))
                .thenReturn(Observable.error<List<Phrase>>(RuntimeException("Service Error")))

        phrasesPresenter?.loadPhrases(character)

        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showLoading()
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).dismissLoading()
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showTryAgainError(ArgumentMatchers.anyString(), ArgumentMatchers.any())
        Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showPhrases(ArgumentMatchers.anyList<Phrase>())
    }


    @Test
    @Throws(Exception::class)
    fun savePhraseSuccess() {

            val phrase = TestDataFactory.makePhrase("phraseId")
            val saveRequest = TestDataFactory.makeSaveRequest(true)
            val phraseItem = mock(PhraseItem::class.java)
            Mockito.`when`(phraseItem.phrase).thenReturn( phrase)

            Mockito.`when`(mockRemoteRepository.savePhrase(phrase))
                    .thenReturn(Observable.just(saveRequest))

            phrasesPresenter?.savePhrase(phraseItem)

            Mockito.verify<PhraseItem>(phraseItem).showLoading()
            Mockito.verify<PhraseItem>(phraseItem).dismissLoading()
            Mockito.verify<PhraseItem>(phraseItem).setSaved(true)
            Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showErrorMsg(ArgumentMatchers.anyString())
            Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showTryAgainError(ArgumentMatchers.anyString(), ArgumentMatchers.any())
    }

    @Test
    @Throws(Exception::class)
    fun savePhraseErrorReturn() {
        val phrase = TestDataFactory.makePhrase("phraseId")
        val saveRequest = TestDataFactory.makeSaveRequest(false)
        val phraseItem = mock(PhraseItem::class.java)
        Mockito.`when`(phraseItem.phrase).thenReturn( phrase)

        Mockito.`when`(mockRemoteRepository.savePhrase(phrase))
                .thenReturn(Observable.just(saveRequest))

        phrasesPresenter?.savePhrase(phraseItem)

        Mockito.verify<PhraseItem>(phraseItem).showLoading()
        Mockito.verify<PhraseItem>(phraseItem).dismissLoading()
        Mockito.verify<PhraseItem>(phraseItem).setSaved(false)
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showErrorMsg(ArgumentMatchers.anyString())
        Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showTryAgainError(ArgumentMatchers.anyString(), ArgumentMatchers.any())
    }

    @Test
    @Throws(Exception::class)
    fun savePhraseErrorNetwork() {
        val phrase = TestDataFactory.makePhrase("phraseId")
        val phraseItem = mock(PhraseItem::class.java)
        Mockito.`when`(phraseItem.phrase).thenReturn( phrase)

        Mockito.`when`(mockRemoteRepository.savePhrase(phrase))
                .thenReturn(Observable.error<SaveRequest>(RuntimeException("Service Error")))

        phrasesPresenter?.savePhrase(phraseItem)

        Mockito.verify<PhraseItem>(phraseItem).showLoading()
        Mockito.verify<PhraseItem>(phraseItem).dismissLoading()
        Mockito.verify<PhraseItem>(phraseItem).setSaved(false)
        Mockito.verify<PhrasesContract.View>(mockPhrasesView).showErrorMsg(ArgumentMatchers.anyString())
        Mockito.verify<PhrasesContract.View>(mockPhrasesView, Mockito.never()).showTryAgainError(ArgumentMatchers.anyString(), ArgumentMatchers.any())
    }
}