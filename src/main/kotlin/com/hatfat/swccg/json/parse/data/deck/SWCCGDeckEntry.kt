package com.hatfat.swccg.json.parse.data.deck

data class SWCCGDeckEntry(
    val cardName: String,
    val count: Int,
    val isStarting: Boolean,
    val cardId: Int,
)