package com.hatfat.swccg.json.parse.data

data class SWCCGSet(
    val id: String,
    var name: String,
    var gempName: String?,
    var abbr: String?,
    var legacy: Boolean = false
)