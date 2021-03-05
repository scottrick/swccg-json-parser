package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.hatfat.swccg.json.parse.data.SWCCGCardList
import java.io.*

class LegacySplit(
    private val gson: Gson
) {
    fun doSplitWork() {
        val lightInputStream = FileInputStream(File("input/Light.json"))
        val lightReader = BufferedReader(InputStreamReader(lightInputStream))
        val lightCardList = gson.fromJson(lightReader, SWCCGCardList::class.java)

        val darkInputStream = FileInputStream(File("input/Dark.json"))
        val darkReader = BufferedReader(InputStreamReader(darkInputStream))
        val darkCardList = gson.fromJson(darkReader, SWCCGCardList::class.java)

        val lightLegacy = SWCCGCardList(mutableListOf())
        val lightCurrent = SWCCGCardList(mutableListOf())
        val darkLegacy = SWCCGCardList(mutableListOf())
        val darkCurrent = SWCCGCardList(mutableListOf())

        lightCardList.cards.forEach { card ->
            card.legacy.let {
                if (it == null) {
                    println("legacy is NULL? ${card.front.title}")
                    return@forEach
                }

                if (it) {
                    lightLegacy.cards.add(card)
                } else {
                    lightCurrent.cards.add(card)
                }
            }
        }

        darkCardList.cards.forEach { card ->
            card.legacy.let {
                if (it == null) {
                    println("legacy is NULL? ${card.front.title}")
                    return@forEach
                }

                if (it) {
                    darkLegacy.cards.add(card)
                } else {
                    darkCurrent.cards.add(card)
                }
            }
        }

        println("Total Dark Side cards: ${darkCardList.cards.size}, Legacy: ${darkLegacy.cards.size}, Current: ${darkCurrent.cards.size}")
        if (darkCardList.cards.size == (darkLegacy.cards.size + darkCurrent.cards.size)) {
            println(" --> Dark card counts align.")
        } else {
            println(" --> ERROR: Dark card count does not align.")
        }

        println("Total Light Side cards: ${lightCardList.cards.size}, Legacy: ${lightLegacy.cards.size}, Current: ${lightCurrent.cards.size}")
        if (lightCardList.cards.size == (lightLegacy.cards.size + lightCurrent.cards.size)) {
            println(" --> Light Card counts align.")
        } else {
            println(" --> ERROR: Light card count does not align.")
        }

        writeCardList(lightLegacy, "output/Light-legacy.json")
        writeCardList(lightCurrent, "output/Light.json")
        writeCardList(darkLegacy, "output/Dark-legacy.json")
        writeCardList(darkCurrent, "output/Dark.json")
    }

    private fun writeCardList(cardList: SWCCGCardList, filename: String) {
        val outputFile = File(filename)
        outputFile.parentFile.mkdirs()
        outputFile.createNewFile()
        val outputStream = FileOutputStream(outputFile)
        val writer = BufferedWriter(OutputStreamWriter(outputStream))
        gson.toJson(cardList, writer)
        writer.close()
        outputStream.close()
    }
}