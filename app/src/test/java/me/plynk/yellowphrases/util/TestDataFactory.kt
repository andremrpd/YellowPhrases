package me.plynk.yellowphrases.util

import me.plynk.yellowphrases.model.Character
import me.plynk.yellowphrases.model.Phrase
import me.plynk.yellowphrases.model.SaveRequest
import me.plynk.yellowphrases.model.Saved
import java.util.*

object TestDataFactory {

    fun randomUuid(): String = UUID.randomUUID().toString()

    fun makeCharacter(id: String): Character = Character(randomUuid(), randomUuid(), id, randomUuid())

    fun makeCharacersList(size: Int): List<Character> = (0 until size).mapTo(ArrayList()) { makeCharacter(it.toString()) }

    fun makePhrase(id: String): Phrase = Phrase(randomUuid(), randomUuid(), id)

    fun makePhraseList(size: Int): List<Phrase> = (0 until size).mapTo(ArrayList()){ makePhrase(it.toString()) }

    fun makeSaveRequest(success: Boolean): SaveRequest = SaveRequest(success, randomUuid())

    fun makeSaved(phraseId: String): Saved = Saved(phraseId)

}