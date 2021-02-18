package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hatfat.swccg.json.parse.data.SWCCGCardFace
import com.hatfat.swccg.json.parse.data.SWCCGCardList
import com.hatfat.swccg.json.parse.serialize.SWCCGCardFaceSerializer
import java.io.*

class Main {
}

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .registerTypeAdapter(SWCCGCardFace::class.java, SWCCGCardFaceSerializer())
        .create()

    parseSets(gson)
}

