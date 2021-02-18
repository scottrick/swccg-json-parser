package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.hatfat.swccg.json.parse.data.SWCCGCardList
import java.io.*

fun lightDarkJsonWork(gson: Gson) {
    println("Parsing Dark/Light json.")
    val lightInputStream = FileInputStream(File("input/Light.json"))
    val lightReader = BufferedReader(InputStreamReader(lightInputStream))
    val lightCardList = gson.fromJson(lightReader, SWCCGCardList::class.java)

    val darkInputStream = FileInputStream(File("input/Dark.json"))
    val darkReader = BufferedReader(InputStreamReader(darkInputStream))
    val darkCardList = gson.fromJson(darkReader, SWCCGCardList::class.java)

    println("lightCardList ${lightCardList.cards.size}")
    println("darkCardList  ${darkCardList.cards.size}")

    /* write out updated card lists */
    val lightOutputFile = File("output/Light.json")
    lightOutputFile.parentFile.mkdirs()
    lightOutputFile.createNewFile()
    val lightOutputStream = FileOutputStream(lightOutputFile)
    val lightWriter = BufferedWriter(OutputStreamWriter(lightOutputStream))
    gson.toJson(lightCardList, lightWriter)
    lightWriter.close()
    lightOutputStream.close()

    val darkOutputFile = File("output/Dark.json")
    darkOutputFile.parentFile.mkdirs()
    darkOutputFile.createNewFile()
    val darkOutputStream = FileOutputStream(darkOutputFile)
    val darkWriter = BufferedWriter(OutputStreamWriter(darkOutputStream))
    gson.toJson(darkCardList, darkWriter)
    darkWriter.close()
    darkOutputStream.close()
}
