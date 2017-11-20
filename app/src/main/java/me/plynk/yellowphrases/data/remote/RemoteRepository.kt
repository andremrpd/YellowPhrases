package me.plynk.yellowphrases.data.remote

import io.reactivex.Observable
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import me.plynk.yellowphrases.model.SaveRequest

interface RemoteRepository {

    fun getCharacters(): Observable<List<Character>>
    fun getPhrases(charaterId: String): Observable<List<Phrase>>
    fun savePhrase(phrase: Phrase): Observable<SaveRequest>

}