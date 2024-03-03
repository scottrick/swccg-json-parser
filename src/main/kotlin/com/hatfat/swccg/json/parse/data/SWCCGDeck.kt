package com.hatfat.swccg.json.parse.data

data class SWCCGDeck(
    var author: String?,
    var title: String?,
    var side: String?,
    var date: String?,
    var description: String?,
    var id: Int?,
) {
    companion object {
        fun create(): SWCCGDeck {
            return SWCCGDeck(null, null, null, null, null, null)
        }
    }
}