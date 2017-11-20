package me.plynk.yellowphrases.data.remote

import io.reactivex.Observable
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import me.plynk.yellowphrases.model.SaveRequest
import me.plynk.yellowphrases.model.Saved

class RemoteRepositoryImpl(private val api: PlynkAPI): RemoteRepository {

    override fun getCharacters(): Observable<List<Character>> = api.getCharacters()

    override fun getPhrases(charaterId: String): Observable<List<Phrase>> = api.getPhrases(charaterId)

    override fun savePhrase(phrase: Phrase): Observable<SaveRequest> = api.savePhrase(Saved(phrase._id))


}