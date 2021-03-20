package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGCard(
    var id: Int?,
    var gempId: String?,
    val side: String?,
    val rarity: String?,
    var set: String?,
    var printings: Set<SWCCGPrinting>?,
    var front: SWCCGCardFace,
    var back: SWCCGCardFace?,
    val conceptBy: String?,
    val pulls: List<String>?,
    val pulledBy: List<String>?,
    val counterpart: String?,
    var combo: List<String>?,
    val matching: List<String>?,
    val matchingWeapon: List<String>?,
    val canceledBy: List<String>?,
    val cancels: List<String>?,
    var legacy: Boolean?
) : Serializable, Comparable<SWCCGCard> {

    val isFlippable: Boolean
        get() = back != null

    override fun compareTo(other: SWCCGCard): Int {
        return front.compareTo(other.front)
    }
}
