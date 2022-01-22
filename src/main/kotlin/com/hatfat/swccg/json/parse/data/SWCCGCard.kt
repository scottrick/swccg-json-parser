package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGCard(
    var abbr: List<String>?,
    var back: SWCCGCardFace?,
    val canceledBy: List<String>?,
    val cancels: List<String>?,
    var combo: List<String>?,
    val conceptBy: String?,
    val counterpart: String?,
    var front: SWCCGCardFace,
    var gempId: String?,
    var id: Int?,
    var legacy: Boolean?,
    val matching: List<String>?,
    val matchingWeapon: List<String>?,
    var printings: Set<SWCCGPrinting>?,
    val pulledBy: List<String>?,
    val pulls: List<String>?,
    val rarity: String?,
    val rulings: List<String>?,
    var set: String?,
    val side: String?,
    val sourceType: String?,
) : Serializable, Comparable<SWCCGCard> {

    val isFlippable: Boolean
        get() = back != null

    override fun compareTo(other: SWCCGCard): Int {
        return front.compareTo(other.front)
    }
}
