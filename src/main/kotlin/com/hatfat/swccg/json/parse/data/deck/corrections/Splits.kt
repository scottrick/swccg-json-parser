package com.hatfat.swccg.json.parse.data.deck.corrections

/*
    Corrections that split one line into multiple lines.
 */
fun createLightSplitCorrections(): Map<String, List<String>> {
    val splits = mutableMapOf<String, List<String>>()

    splits["red squadron 1 4 7"] = listOf("redsquadron1", "redsquadron4", "redsquadron7")

    return splits
}

fun createDarkSplitCorrections(): Map<String, List<String>> {
    val splits = mutableMapOf<String, List<String>>()

    splits["saber 1 2 3 4"] = listOf("saber1", "saber2", "saber3", "saber4")
    splits["tempest scout 1 2 5 6"] = listOf("tempestscout1", "tempestscout2", "tempestscout5", "tempestscout6")
    splits["tempest scout 3456"] = listOf("tempestscout3", "tempestscout4", "tempestscout5", "tempestscout6")

    return splits
}
