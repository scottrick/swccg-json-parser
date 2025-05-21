package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGSimpleCard (
    val abbr: List<String>?,
    val title: String?,
    val counterpart: String?,
) : Serializable
