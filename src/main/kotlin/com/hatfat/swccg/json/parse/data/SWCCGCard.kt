package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGCard(
    val id: Int?,
    val side: String?,
    val rarity: String?,
    val set: String?,
    val front: SWCCGCardFace,
    val back: SWCCGCardFace?,
    val conceptBy: String?,
    val pulls: List<String>?,
    val pulledBy: List<String>?,
    val counterpart: String?,
    val combo: List<String>?,
    val matching: List<String>?,
    val matchingWeapon: List<String>?,
    val canceledBy: List<String>?,
    val cancels: List<String>?,
    val legacy: Boolean?
) : Serializable, Comparable<SWCCGCard> {

    val isFlippable: Boolean
        get() = back != null

    override fun compareTo(other: SWCCGCard): Int {
        return front.compareTo(other.front)
    }
}