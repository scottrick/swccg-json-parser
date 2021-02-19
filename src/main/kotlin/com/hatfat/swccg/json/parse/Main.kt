package com.hatfat.swccg.json.parse

import com.google.gson.GsonBuilder

class Main {
}

@Suppress("UNUSED_PARAMETER")
fun main(args: Array<String>) {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        //.registerTypeAdapter(SWCCGCardFace::class.java, SWCCGCardFaceSerializer())
        .create()

    lightDarkJsonWork(gson)
    //parseSets(gson)
}

