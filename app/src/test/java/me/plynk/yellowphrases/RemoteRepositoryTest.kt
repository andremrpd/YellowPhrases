package me.plynk.yellowphrases

import io.reactivex.Observable
import me.plynk.yellowphrases.data.remote.PlynkAPI
import me.plynk.yellowphrases.data.remote.RemoteRepository
import me.plynk.yellowphrases.data.remote.RemoteRepositoryImpl
import me.plynk.yellowphrases.util.RxSchedulersOverrideRule
import me.plynk.yellowphrases.util.TestDataFactory
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteRepositoryTest {

    @Rule
    @JvmField val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock lateinit var plynkApi: PlynkAPI

    private var repository: RemoteRepository? = null

    @Before
    fun setUp() {
        repository = RemoteRepositoryImpl(plynkApi)
    }

    @Test
    fun getCharactersComplete() {
        val charactersList = TestDataFactory.makeCharacersList(5)

        Mockito.`when`(plynkApi.getCharacters())
                .thenReturn(Observable.just(charactersList))

        repository?.getCharacters()
                ?.test()
                ?.assertComplete()
                ?.assertValue(charactersList)
    }

    @Test
    fun getPhrasesComplete() {
        val charaterId = "characterId"
        val phrasesList = TestDataFactory.makePhraseList(5)
        Mockito.`when`(plynkApi.getPhrases(ArgumentMatchers.anyString()))
                .thenReturn(Observable.just(phrasesList))

        repository?.getPhrases(charaterId)
                ?.test()
                ?.assertComplete()
                ?.assertValue(phrasesList)
    }

}