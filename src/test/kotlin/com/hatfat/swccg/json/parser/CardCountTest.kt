package com.hatfat.swccg.json.parser

import com.hatfat.swccg.json.parse.count.CardCount
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class CardCountTest {

    val cardCount = CardCount()

    @Test
    fun case01() {
        testInput("3 X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case02() {
        testInput("X-Wing Laser Cannon 3", null)
    }

    @Test
    fun case03() {
        testInput("x3 X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case04() {
        testInput("x 3 X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case05() {
        testInput("3x X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case06() {
        testInput("  3   x X-Wing Laser Cannon   ", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case07() {
        testInput("3 x X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case08() {
        testInput("X-Wing Laser Cannon x3", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case09() {
        testInput("X-Wing Laser Cannon X3  ", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case10() {
        testInput("X-Wing Laser Cannon x 3", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case11() {
        testInput("X-Wing Laser Cannon 3x", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case12() {
        testInput("X-Wing Laser Cannon 3 x", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case13() {
        testInput("X-Wing Laser Cannon (3)", Pair("X-Wing Laser Cannon", 3), true)
    }

    @Test
    fun case14() {
        testInput("r2-x2", null)
    }

    @Test
    fun case15() {
        testInput("r2-x2 x2", Pair("r2-x2", 2))
    }

    @Test
    fun case16() {
        testInput("3X X-Wing Laser Cannon", Pair("X-Wing Laser Cannon", 3))
    }

    @Test
    fun case17() {
        testInput("3X 3720 to 1", Pair("3720 to 1", 3))
    }

    @Test
    fun case18() {
        testInput("3Xhansolo", null)
    }

    @Test
    fun case19() {
        testInput("3Xhansolo", Pair("hansolo", 3), true)
    }

    @Test
    fun case20() {
        testInput("4-love of money with Concussion Gun x2", Pair("4-love of money with Concussion Gun", 2))
    }

    @Test
    fun case21() {
        testInput("vader (x3)", Pair("vader", 3))
    }

    @Test
    fun case22() {
        testInput("vader (3x)", Pair("vader", 3))
    }

    @Test
    fun case23() {
        testInput("vader (3)", Pair("vader", 3), true)
    }

    @Test
    fun case24() {
        testInput("(3) vader", Pair("vader", 3))
    }

    @Test
    fun case25() {
        testInput("(3x) vader", Pair("vader", 3))
    }

    @Test
    fun case26() {
        testInput("(x3) vader", Pair("vader", 3))
    }

    @Test
    fun case27() {
        testInput("1holotheatre", null)
    }

    @Test
    fun case28() {
        testInput("1holotheatre", Pair("holotheatre", 1), true)
    }

    @Test
    fun case29() {
        testInput("holotheatre3", null)
    }

    @Test
    fun case30() {
        testInput("holotheatre3", Pair("holotheatre", 3), true)
    }

    @Test
    fun case31() {
        testInput("X-Wing Laser Cannon 3", Pair("X-Wing Laser Cannon", 3), true)
    }

    @Test
    fun case32() {
        testInput("2xHan with Gun", null)
    }

    @Test
    fun case33() {
        testInput("2xHan with Gun", Pair("Han with Gun", 2), true)
    }

    @Test
    fun case34() {
        testInput("Imperial Barrier (x2)", Pair("Imperial Barrier", 2), true)
    }

    @Test
    fun case35() {
        testInput("(x3) Imperial Barrier", Pair("Imperial Barrier", 3), true)
    }

    @Test
    fun case36() {
        testInput("Docking Bay 94 1", Pair("Docking Bay 94", 1), true)
    }

    @Test
    fun case37() {
        testInput("EPP Han x2", Pair("EPP Han", 2))
    }

    @Test
    fun case38() {
        testInput("3720 to 1 x3", Pair("3720 to 1", 3))
    }

//    testInput("Myo x3 (Rep)", Pair("Myo", 3))

    fun testInput(input: String, expected: Pair<String, Int>?, extraSearches: Boolean = false) {
        val result = cardCount.getCardCount(input, extraSearches)

        if (expected != null) {
            assertEquals(expected.first, result?.first?.trim())
            assertEquals(expected.second, result?.second)
        } else {
            assertNull(result)
        }
    }
}
