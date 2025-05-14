package com.hatfat.swccg.json.parse.data.deck

fun createIgnoreFilters(): List<String> {
    val filters = mutableListOf<String>()

    filters.add(".deck -")

    return filters
}
