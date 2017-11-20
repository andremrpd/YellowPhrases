package me.plynk.yellowphrases.data.remote

import io.reactivex.Observable
import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import me.plynk.yellowphrases.model.SaveRequest
import me.plynk.yellowphrases.model.Saved
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlynkAPI{

    @GET("simpsons")
    fun getCharacters(): Observable<List<Character>>

    @GET("simpsons/{characterId}/phrases")
    fun getPhrases(@Path("characterId") charaterId: String): Observable<List<Phrase>>

    @POST("user/phrase")
    fun savePhrase(@Body saved: Saved): Observable<SaveRequest>

}