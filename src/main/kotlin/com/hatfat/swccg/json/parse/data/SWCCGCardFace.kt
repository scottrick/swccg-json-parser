package com.hatfat.swccg.json.parse.data

import java.io.Serializable

data class SWCCGCardFace(
    val title: String?,
    val imageUrl: String?,
    val type: String?,
    val subType: String?,
    val uniqueness: String?,
    val destiny: String?,
    val power: String?,
    val maneuver: String?,
    val landspeed: String?,
    val armor: String?,
    val hyperspeed: String?,
    val ability: String?,
    val politics: String?,
    val deploy: String?,
    val forfeit: String?,
    val icons: List<String>?,
    val lightSideIcons: Int?,
    val darkSideIcons: Int?,
    val extraText: List<String>?,
    val characteristics: List<String>?,
    val gametext: String?,
    val parsec: String?,
    val lore: String?
) : Serializable, Comparable<SWCCGCardFace> {

    /* default constructor that gson will call.  otherwise the lazy property will not work */
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    @delegate:Transient
    val sortableTitle: String by lazy {
        var result = title ?: ""

        while (result.startsWith("<>")) {
            result = result.removePrefix("<>")
        }

        while (result.startsWith("•")) {
            result = result.removePrefix("•")
        }

        result
    }

    override fun compareTo(other: SWCCGCardFace): Int {
        if (sortableTitle.isBlank() && other.sortableTitle.isBlank()) {
            return 0;
        }

        if (sortableTitle.isBlank()) {
            return -1
        }

        if (other.sortableTitle.isBlank()) {
            return 1
        }

        return sortableTitle.compareTo(other.sortableTitle)
    }
}
