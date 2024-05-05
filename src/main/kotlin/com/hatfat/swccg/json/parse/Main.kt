package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hatfat.swccg.json.parse.data.SWCCGCard
import com.hatfat.swccg.json.parse.data.SWCCGCardList
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .create()

//    val cardParse = CardParse(gson)
//    cardParse.lightDarkJsonWork()

//    parseSets(gson)

    val cards = getAllCards(gson, true)

    val deckParse = DeckParse("../decktech.net/_decks", cards)
    deckParse.parse()
}

fun getAllCards(gson: Gson, onlyDecipher: Boolean): List<SWCCGCard> {
    val lightCurrentInputStream = FileInputStream(File("output/Light.json"))
    val lightCurrentReader = BufferedReader(InputStreamReader(lightCurrentInputStream))
    val light = gson.fromJson(lightCurrentReader, SWCCGCardList::class.java)

    val darkCurrentInputStream = FileInputStream(File("output/Dark.json"))
    val darkCurrentReader = BufferedReader(InputStreamReader(darkCurrentInputStream))
    val dark = gson.fromJson(darkCurrentReader, SWCCGCardList::class.java)

    val cards = mutableListOf<SWCCGCard>()
    cards.addAll(light.cards)
    cards.addAll(dark.cards)

    if (!onlyDecipher) {
        val lightLegacyInputStream = FileInputStream(File("output/LightLegacy.json"))
        val lightLegacyReader = BufferedReader(InputStreamReader(lightLegacyInputStream))
        val lightLegacy = gson.fromJson(lightLegacyReader, SWCCGCardList::class.java)

        val darkLegacyInputStream = FileInputStream(File("output/DarkLegacy.json"))
        val darkLegacyReader = BufferedReader(InputStreamReader(darkLegacyInputStream))
        val darkLegacy = gson.fromJson(darkLegacyReader, SWCCGCardList::class.java)

        cards.addAll(lightLegacy.cards)
        cards.addAll(darkLegacy.cards)
    }

    return cards
}

