package com.hatfat.swccg.json.parse.data

import java.io.Serializable
import kotlin.text.contains
import kotlin.text.toInt

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

    fun getSetInt() : Int {
        var set = 0
        set = if (this.set?.contains("d") == true) {
            // Virtual Defensive Shields
            200
        } else {
            this.set?.toInt() ?: 0
        }

        return set
    }

    fun toSimpleCard(): SWCCGSimpleCard {
        return SWCCGSimpleCard(
            this.abbr,
            this.front.title,
            this.counterpart,
        )
    }
}
