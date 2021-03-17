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

    val abbrMap = HashMap<String, String>()
    abbrMap["1"] = "P"
    abbrMap["2"] = "ANH"
    abbrMap["3"] = "H"
    abbrMap["4"] = "DAG"
    abbrMap["5"] = "CC"
    abbrMap["6"] = "JP"
    abbrMap["7"] = "SE"
    abbrMap["8"] = "EDR"
    abbrMap["9"] = "DS2"
    abbrMap["10"] = "Ref2"
    abbrMap["11"] = "TAT"
    abbrMap["12"] = "COR"
    abbrMap["13"] = "Ref3"
    abbrMap["14"] = "TP"

    abbrMap["101"] = "P2P"
    abbrMap["102"] = "JEDI"
    abbrMap["103"] = "RLP"
    abbrMap["104"] = "ESB2P"
    abbrMap["105"] = "1A"
    abbrMap["106"] = "OTSD"
    abbrMap["107"] = "2A"
    abbrMap["108"] = "EPP"
    abbrMap["109"] = "ECC"
    abbrMap["110"] = "EJP"
    abbrMap["111"] = "3A"
    abbrMap["112"] = "JPSD"

    abbrMap["200d"] = "VDS"
    abbrMap["200"] = "V0"
    abbrMap["201"] = "V1"
    abbrMap["202"] = "V2"
    abbrMap["203"] = "V3"
    abbrMap["204"] = "V4"
    abbrMap["205"] = "V5"
    abbrMap["206"] = "V6"
    abbrMap["207"] = "V7"
    abbrMap["208"] = "V8"
    abbrMap["209"] = "V9"
    abbrMap["210"] = "V10"
    abbrMap["211"] = "V11"
    abbrMap["212"] = "V12"
    abbrMap["213"] = "V13"
    abbrMap["214"] = "V14"

    abbrMap["301"] = "VP"
    abbrMap["401"] = "DC"
    abbrMap["501"] = "PT"

    abbrMap["1001"] = "VB1"
    abbrMap["1002"] = "VB2"
    abbrMap["1003"] = "VB3"
    abbrMap["1004"] = "VB4"
    abbrMap["1005"] = "VB5"
    abbrMap["1006"] = "VB6"
    abbrMap["1007"] = "VB7"
    abbrMap["1008"] = "VB8"
    abbrMap["1009"] = "VB9"
    abbrMap["1000d"] = "VBDS"

    val setsInputStream = FileInputStream(File("input/sets_source.txt"))
    val setsReader = BufferedReader(InputStreamReader(setsInputStream))

    val idPattern = Pattern.compile("'(.*?)'")
    val namePattern = Pattern.compile(">(.*?)<")

    var setAbbr: String = "NOT FOUND"

    setsReader.forEachLine {
        val idMatcher = idPattern.matcher(it)
        val nameMatcher = namePattern.matcher(it)
        if (idMatcher.find() && nameMatcher.find()) {
            val setId = idMatcher.group(1)
            val gempName = nameMatcher.group(1)
            var setName = gempName
            setAbbr = abbrMap[setId] ?: "NOT FOUND"

            if (gempName.startsWith("Set ") || gempName.startsWith("Block ")) {
                /* is a virtual set/block, so append Virtual */
                setName = "Virtual $gempName"
            }

            if (hackMap.containsKey(gempName)) {
                setName = hackMap[gempName]
            }

            setList.add(SWCCGSet(setId, setName, gempName, setAbbr))
        }
    }

    /* add virtual defensive shields set */
    setAbbr = abbrMap["200d"] ?: "NOT FOUND"
    setList.add(SWCCGSet("200d", "Virtual Defensive Shields", "Set D", setAbbr))

    /* add virtual block sets */
    for (i in 1..9) {
        val setId = (1000 + i).toString()
        val gempName = "Block $i"
        val name = "Virtual Block $i"
        setAbbr = abbrMap[setId] ?: "NOT FOUND"

        setList.add(SWCCGSet(setId, name, gempName, setAbbr, true))
    }

    /* add virtual shields set LEGACY */
    setAbbr = abbrMap["1000d"] ?: "NOT FOUND"
    setList.add(SWCCGSet("1000d", "Virtual Block Shields", "Block D", setAbbr, true))

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