package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.hatfat.swccg.json.parse.data.SWCCGSet
import java.io.*
import java.util.regex.Pattern

fun parseSets(gson: Gson) {
    /* https://github.com/PlayersCommittee/gemp-swccg-public/blob/master/gemp-swccg-async/src/main/web/js/gemp-016/cardFilter.js */
    /* parse sets list from above link, and output nicely formatted sets.json file */
    val outputFilename = "output/sets.json"
    val setList: MutableList<SWCCGSet> = mutableListOf()

    val hackMap = HashMap<String, String>()
    hackMap["Virtual Premium Set"] = "Demo Deck"
    hackMap["3rd Anthology"] = "Third Anthology"
    hackMap["Enhanced Premiere Pack"] = "Enhanced Premiere"
    hackMap["2nd Anthology"] = "Second Anthology"
    hackMap["Sealed Deck"] = "Official Tournament Sealed Deck"
    hackMap["1st Anthology"] = "First Anthology"
    hackMap["ESB 2-Player"] = "Empire Strikes Back Introductory Two Player Game"
    hackMap["Rebel Leader"] = "Rebel Leader Pack"
    hackMap["Premiere 2-Player"] = "Premiere Introductory Two Player Game"

    val setsInputStream = FileInputStream(File("input/sets_source.txt"))
    val setsReader = BufferedReader(InputStreamReader(setsInputStream))

    val idPattern = Pattern.compile("'(.*?)'")
    val namePattern = Pattern.compile(">(.*?)<")

    setsReader.forEachLine {
        val idMatcher = idPattern.matcher(it)
        val nameMatcher = namePattern.matcher(it)
        if (idMatcher.find() && nameMatcher.find()) {
            val setId = idMatcher.group(1).toInt()
            val gempName = nameMatcher.group(1)
            var setName = gempName

            if (gempName.startsWith("Set ") || gempName.startsWith("Block ")) {
                /* is a virtual set/block, so append Virtual */
                setName = "Virtual $gempName"
            }

            if (hackMap.containsKey(gempName)) {
                setName = hackMap[gempName]
            }

            setList.add(SWCCGSet(setId, setName, gempName))
        }
    }

    val outputFile = File(outputFilename)
    outputFile.parentFile.mkdirs()
    outputFile.createNewFile()
    val outputFileStream = FileOutputStream(outputFile)
    val outputWriter = BufferedWriter(OutputStreamWriter(outputFileStream))
    gson.toJson(setList, outputWriter)
    outputWriter.close()
    outputFileStream.close()

    println("Wrote ${setList.size} sets to ${outputFilename}.")
}