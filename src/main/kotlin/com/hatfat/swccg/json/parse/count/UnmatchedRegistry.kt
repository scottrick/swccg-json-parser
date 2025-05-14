package com.hatfat.swccg.json.parse.count

import com.hatfat.swccg.json.parse.getProcessedCardName
import java.text.DecimalFormat

class UnmatchedRegistry {
    val registry = HashMap<String, UnmatchedLine>()

    fun registerUnmatchedLine(line: String, deck: String?) {
        registry.getOrPut(line.lowercase()) {
            UnmatchedLine(line.lowercase(), HashSet())
        }.decks.add(deck ?: "[Unknown]")
    }

    fun printSummary(verbose: Boolean = false) {
        println("---------------------------------------")
        println("UnmatchedRegistry Summary")
        println("Total entries: ${registry.keys.size}")
        println("---------------------------------------")

        if (verbose) {
            val countFormatter = DecimalFormat("000")
            val printDecks = false

            val numToPrint = 15
            var currentNum = 0
            val list = registry.values.sortedByDescending { it.decks.size }

            for (line in list) {
                val count = line.decks.size
                val countString = countFormatter.format(count)

                // corrections["bosskinbus"] = "bosskinhoundstooth"
                println("corrections[\"${getProcessedCardName(line.line, true)}\"] = \"TODO$countString\"")

                if (printDecks) {
                    line.decks.take(2).forEach {
                        println("  $it")
                    }
                }

                currentNum++

                if (currentNum > numToPrint) {
                    break
                }
            }
        }
    }
}