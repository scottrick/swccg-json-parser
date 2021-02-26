package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGCardList(
    val cards: MutableList<SWCCGCard>
) : Serializable
