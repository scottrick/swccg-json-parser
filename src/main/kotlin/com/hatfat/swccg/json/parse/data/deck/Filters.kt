package com.hatfat.swccg.json.parse.data.deck

fun createIgnoreWildcardFilters(): List<String> {
    val filters = mutableListOf<String>()

    filters.add(".deck -")

    return filters
}

fun createIgnoreStartsWithFilters(): List<String> {
    val filters = mutableListOf<String>()

    filters.add("effects")
    filters.add("interrupt")
    filters.add("interupt")
    filters.add("locations")
    filters.add("starships")
    filters.add("starting")
    filters.add("character")
    filters.add("objective")
    filters.add("admiralso")
    filters.add("weapons(")
    filters.add("weapons(")
    filters.add("weapon(")
    filters.add("device")
    filters.add("creature")
    filters.add("vehicles")
    filters.add("ships")
    filters.add("unknown")
    filters.add("epicevent")
    filters.add("site")
    filters.add("podracer(")
    filters.add("defensives")
    filters.add("start(")
    filters.add("starting")
    filters.add("weaponsdevices")
    filters.add("jeditest")

    return filters
}

fun createIgnoreExactFilters(): List<String> {
    val filters = mutableListOf<String>()

    filters.add("weapons")
    filters.add("weapons 1")
    filters.add("weapons1")
    filters.add("weapons 2")
    filters.add("weapons2")
    filters.add("weapons 3")
    filters.add("weapons3")
    filters.add("weapons 4")
    filters.add("weapons4")
    filters.add("start")

    return filters
}
