package com.hatfat.swccg.json.parse

import com.google.gson.GsonBuilder

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
//    val gson = GsonBuilder()
//        .setPrettyPrinting()
//        .disableHtmlEscaping()
//        .create()
//
//    val cardParse = CardParse(gson)
//    cardParse.lightDarkJsonWork()

//    parseSets(gson)

    val deckParse = DeckParse("../decktech.net/_decks")
    deckParse.parse()
}

