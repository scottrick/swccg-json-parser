package com.hatfat.swccg.json.parse.count

class CardCount {

    val debug = false
    val numRegex = Regex("\\d+")

    interface CountCheck {
        fun checkCount(cardLine: String): Pair<String, Int>?
    }

    val checkStartNum = object : CountCheck {
        val regex = Regex("^ *\\(?[X.x]? *\\d+ *[X.x]?\\)? +")

        override fun checkCount(cardLine: String): Pair<String, Int>? {
            regex.find(cardLine)?.let { matchResult ->
                val matchString = cardLine.substring(matchResult.range)

                val count = numRegex.find(matchString)?.let { countResult ->
                    matchString.substring(countResult.range)
                }!!.toInt()

                val cardLine = cardLine.removeRange(matchResult.range).trim()

                return Pair(cardLine, count)
            }

            return null
        }
    }

    val checkStartNumNoSpace = object : CountCheck {
        val regex = Regex("^ *\\(?[X.x]? *\\d+ *[X.x]?\\)? *")

        override fun checkCount(cardLine: String): Pair<String, Int>? {
            regex.find(cardLine)?.let { matchResult ->
                val matchString = cardLine.substring(matchResult.range)

                val count = numRegex.find(matchString)?.let { countResult ->
                    matchString.substring(countResult.range)
                }!!.toInt()

                val cardLine = cardLine.removeRange(matchResult.range).trim()

                return Pair(cardLine, count)
            }

            return null
        }
    }

    val checkEndNum = object : CountCheck {
        val regex = Regex(" +\\(?([X.x] *\\d+|\\d+ *[X.x])\\)? *$")

        override fun checkCount(cardLine: String): Pair<String, Int>? {
            regex.find(cardLine)?.let { matchResult ->
                val matchString = cardLine.substring(matchResult.range)

                val count = numRegex.find(matchString)?.let { countResult ->
                    matchString.substring(countResult.range)
                }!!.toInt()

                val cardLine = cardLine.substring(0, matchResult.range.start).trim()

                return Pair(cardLine, count)
            }

            return null
        }
    }

    val checkEndNumNoSpace = object : CountCheck {
        val regex = Regex(" *\\(?[X.x]? *\\d+ *[X.x]?\\)? *$")

        override fun checkCount(cardLine: String): Pair<String, Int>? {
            regex.find(cardLine)?.let { matchResult ->
                val matchString = cardLine.substring(matchResult.range)

                val count = numRegex.find(matchString)?.let { countResult ->
                    matchString.substring(countResult.range)
                }!!.toInt()

                val cardLine = cardLine.substring(0, matchResult.range.start).trim()

                return Pair(cardLine, count)
            }

            return null
        }
    }

    // Gets card count, and removes card count from string.
    fun getCardCount(startingCardLine: String, extraSearches: Boolean): Pair<String, Int>? {
        val cardLine = startingCardLine.trim()

        if (debug) {
            println("cardline: $cardLine")
        }

        checkStartNum.checkCount(cardLine)?.let { return it }
        checkEndNum.checkCount(cardLine)?.let { return it }

        if (extraSearches) {
            checkStartNumNoSpace.checkCount(cardLine)?.let { return it }
            checkEndNumNoSpace.checkCount(cardLine)?.let { return it }
        }

        return null
    }
}