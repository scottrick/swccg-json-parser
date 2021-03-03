package com.hatfat.swccg.json.parse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hatfat.swccg.json.parse.data.SWCCGCardFace
import com.hatfat.swccg.json.parse.data.SWCCGCardList
import com.hatfat.swccg.json.parse.data.SWCCGSet
import java.io.*
import java.lang.reflect.Type
import java.util.*


class CardParse(
    private val gson: Gson
) {
    private val setNameMap = mutableMapOf<String, String>()
    private val invSetNameMap = mutableMapOf<String, String>()

    private var currentMaxId = 0;

    init {
        /* gemp set to json set name map */
        setNameMap["Premiere"] = "Premiere"
        setNameMap["ANewHope"] = "A New Hope"
        setNameMap["Hoth"] = "Hoth"
        setNameMap["Dagobah"] = "Dagobah"
        setNameMap["CloudCity"] = "Cloud City"
        setNameMap["JabbasPalace"] = "Jabba's Palace"
        setNameMap["SpecialEdition"] = "Special Edition"
        setNameMap["Endor"] = "Endor"
        setNameMap["DeathStarII"] = "Death Star II"
        setNameMap["ReflectionsII"] = "Reflections II"
        setNameMap["Tatooine"] = "Tatooine"
        setNameMap["Coruscant"] = "Coruscant"
        setNameMap["ReflectionsIII"] = "Reflections III"
        setNameMap["TheedPalace"] = "Theed Palace"
        setNameMap["PremiereIntroductoryTwoPlayerGame"] = "Premiere Introductory Two Player Game"
        setNameMap["JediPack"] = "Jedi Pack"
        setNameMap["RebelLeader"] = "Rebel Leader Pack"
        setNameMap["EmpireStrikesBackIntroductoryTwoPlayerGame"] = "Empire Strikes Back Introductory Two Player Game"
        setNameMap["FirstAnthology"] = "First Anthology"
        setNameMap["OfficialTournamentSealedDeck"] = "Official Tournament Sealed Deck"
        setNameMap["SecondAnthology"] = "Second Anthology"
        setNameMap["EnhancedPremiere"] = "Enhanced Premiere"
        setNameMap["EnhancedCloudCity"] = "Enhanced Cloud City"
        setNameMap["EnhancedJabbasPalace"] = "Enhanced Jabba's Palace"
        setNameMap["ThirdAnthology"] = "Third Anthology"
        setNameMap["JabbasPalaceSealedDeck"] = "Jabba's Palace Sealed Deck"
        setNameMap["Virtual0"] = "Virtual Set 0"
        setNameMap["Virtual1"] = "Virtual Set 1"
        setNameMap["Virtual2"] = "Virtual Set 2"
        setNameMap["Virtual3"] = "Virtual Set 3"
        setNameMap["Virtual4"] = "Virtual Set 4"
        setNameMap["Virtual5"] = "Virtual Set 5"
        setNameMap["Virtual6"] = "Virtual Set 6"
        setNameMap["Virtual7"] = "Virtual Set 7"
        setNameMap["Virtual8"] = "Virtual Set 8"
        setNameMap["Virtual9"] = "Virtual Set 9"
        setNameMap["Virtual10"] = "Virtual Set 10"
        setNameMap["Virtual11"] = "Virtual Set 11"
        setNameMap["Virtual12"] = "Virtual Set 12"
        setNameMap["Virtual13"] = "Virtual Set 13"
        setNameMap["VirtualPremium"] = "Demo Deck"

        setNameMap.forEach {
            invSetNameMap[it.value] = it.key
        }

        invSetNameMap["Virtual Premium Set"] = "VirtualPremium"
    }

    private val cardIdMapToGempId = mutableMapOf<Int, String>()

    init {
        /* Cards that aren't automatically mapping from their Gemp card data to the Scomp Link data */

        /*
            These four cards don't exist in gemp, but they are indeed real cards.
            They have duplicate cards in virtual set 0, just map them to there.
        */
        cardIdMapToGempId[5492] = "200_26" //•Don't Do That Again (Tatooine) (V)
        cardIdMapToGempId[6048] = "200_32" //•Your Insight Serves You Well (Death Star II) (V)
        cardIdMapToGempId[6293] = "200_95" //•Fanfare (Tatooine) (V)
        cardIdMapToGempId[6771] = "200_100" //•You Cannot Hide Forever (Death Star II) (V)

        /* Missing AI cards in GEMP */
        cardIdMapToGempId[5312] = "200_1" //•Aayla Secura (AI)
        cardIdMapToGempId[5340] = "200_2" //•Anakin Skywalker, Padawan Learner (AI)
        cardIdMapToGempId[5366] = "202_7" //•Azure Angel (AI)
        cardIdMapToGempId[5870] = "204_9" //•Rey (AI)
        cardIdMapToGempId[6078] = "203_22" //•Agent Kallus (AI)
        cardIdMapToGempId[6321] = "203_27" //•General Grievous (AI)
        cardIdMapToGempId[6322] = "203_27" //•General Grievous (OAI)
        cardIdMapToGempId[6649] = "201_40" //•Slave I, Symbol Of Fear (AI)

        /* Gemp cards with typos in their image urls */
        cardIdMapToGempId[5113] = "205_12" //•Emperor Palpatine, Foreseer
        cardIdMapToGempId[2278] = "5_177" //•Slave I
        cardIdMapToGempId[5144] = "206_12" //•Xizor's Bounty
    }

    private val gempCardIdsToIgnore = mutableSetOf<String>()

    init {
        /* Cards that show up in the gemp Id list but we can ignore. */

        /* legacy cards in the gemp jCard.js */
        gempCardIdsToIgnore.add("200_30") //gemp-swccg/images/cards/Virtual0-Light/weaponsdisplay.gif
        gempCardIdsToIgnore.add("200_37") //gemp-swccg/images/cards/Virtual0-Light/civildisorder.gif
        gempCardIdsToIgnore.add("200_96") //gemp-swccg/images/cards/Virtual0-Dark/firepower.gif
        gempCardIdsToIgnore.add("200_102") //gemp-swccg/images/cards/Virtual0-Dark/abilityabilityability.gif

        /* Cards where gemp has an incorrect image url */
        gempCardIdsToIgnore.add("5_177") //Slave I
        gempCardIdsToIgnore.add("205_12") //Emperor Palpatine, Foreseer
        gempCardIdsToIgnore.add("206_12") //Xizor's Bounty
    }

    fun lightDarkJsonWork() {
        println("Parsing Dark/Light json...")
        val lightInputStream = FileInputStream(File("input/Light.json"))
        val lightReader = BufferedReader(InputStreamReader(lightInputStream))
        val lightCardList = gson.fromJson(lightReader, SWCCGCardList::class.java)

        val darkInputStream = FileInputStream(File("input/Dark.json"))
        val darkReader = BufferedReader(InputStreamReader(darkInputStream))
        val darkCardList = gson.fromJson(darkReader, SWCCGCardList::class.java)

        println("Found ${lightCardList.cards.size} light side cards.")
        println("Found ${darkCardList.cards.size} dark side cards.")

        println("Parsing sets.json...")
        val setsInputStream = FileInputStream(File("input/sets.json"))
        val setsReader = BufferedReader(InputStreamReader(setsInputStream))
        val setsListType: Type = object : TypeToken<List<SWCCGSet>>() {}.type
        val setsList = gson.fromJson<List<SWCCGSet>>(setsReader, setsListType)

        println("Found ${setsList.size} sets.")

        println("Reading jCards.js...")
        val gempInputStream = FileInputStream(File("input/jCards.js"))
        val gempReader = BufferedReader(InputStreamReader(gempInputStream))
        val gempRegex = Regex("\":\"/gemp-swccg/images/cards/")
        val gempSetMap = mutableMapOf<String, MutableMap<String, String>>()

        /* parse all the gempIds and image urls and put into a map */
        gempReader.forEachLine {
            if (it.contains(gempRegex)) {
                val split = it.split(gempRegex)
                if (split.size == 2) {
                    /* got a gemp card */
                    val gempId = split[0].substring(1)
                    val image = split[1].substringBefore("\",")
                    val imageSplit = image.split("/")

                    if (imageSplit.size == 2) {
                        val gempSet = imageSplit[0]
                        val gempImageUrl = imageSplit[1]

                        if (gempId.startsWith("501_")) {
                            /* filter out play test cards */
                            return@forEachLine
                        }

                        val setMap: MutableMap<String, String> = gempSetMap[gempSet] ?: run {
                            val newMap = mutableMapOf<String, String>()
                            gempSetMap[gempSet] = newMap
                            newMap
                        }

                        if (setMap.containsKey(gempImageUrl)) {
                            println("DuplicateGempKeyFound --> $gempImageUrl")
                        } else {
                            setMap[gempImageUrl] = gempId
                        }
                    }
                }
            }
        }

        updateGempId(lightCardList, gempSetMap)
        updateGempId(darkCardList, gempSetMap)
        updateIcons(lightCardList)
        updateIcons(darkCardList)

        currentMaxId = listOf(lightCardList, darkCardList).map {
            it.cards.map { card -> card.id ?: 0 }.maxOrNull() ?: 0
        }.maxOrNull() ?: 0

        lightCardList.cards.forEach {
            if (it.gempId == null && it.legacy == false) {
                println("MissingGempId --> ${it.front.title}")
            }
        }
        darkCardList.cards.forEach {
            if (it.gempId == null && it.legacy == false) {
                println("MissingGempId --> ${it.front.title}")
            }
        }

        /*
        gempSetMap.forEach { pair ->
            pair.value.forEach inside@{
                /* Check EACH card we didn't successfully map from GEMP to our json data */
                if (it.value.endsWith("_BACK")) {
                    /* backside of an objective */
                    return@inside
                }

                if (it.value.startsWith("105_")) {
                    /* first anthology */
                    if (handleDuplicateCardSet(
                            listOf(lightCardList, darkCardList),
                            it.key,
                            listOf("Special Edition"),
                            "105"
                        )
                    ) {
                        return@inside
                    }
                }

                if (it.value.startsWith("107_")) {
                    /* second anthology */
                    if (handleDuplicateCardSet(
                            listOf(lightCardList, darkCardList),
                            it.key,
                            listOf("Special Edition", "Endor", "Death Star II"),
                            "107"
                        )
                    ) {
                        return@inside
                    }
                }

                if (gempCardIdsToIgnore.contains(it.value)) {
                    /* card we can ignore */
                    return@inside
                }

                println("UnhandledGempCard --> $it")
            }
        }
         */

        updateCardUrls(lightCardList)
        updateCardUrls(darkCardList)

//        updateCardSet(lightCardList, setsList)
//        updateCardSet(darkCardList, setsList)

        moveVirtualShieldsToNewSet(lightCardList)
        moveVirtualShieldsToNewSet(darkCardList)

        validateCardSets(lightCardList)
        validateCardSets(darkCardList)

        /* write out updated card lists */
        val lightOutputFile = File("output/Light.json")
        lightOutputFile.parentFile.mkdirs()
        lightOutputFile.createNewFile()
        val lightOutputStream = FileOutputStream(lightOutputFile)
        val lightWriter = BufferedWriter(OutputStreamWriter(lightOutputStream))
        gson.toJson(lightCardList, lightWriter)
        lightWriter.close()
        lightOutputStream.close()

        val darkOutputFile = File("output/Dark.json")
        darkOutputFile.parentFile.mkdirs()
        darkOutputFile.createNewFile()
        val darkOutputStream = FileOutputStream(darkOutputFile)
        val darkWriter = BufferedWriter(OutputStreamWriter(darkOutputStream))
        gson.toJson(darkCardList, darkWriter)
        darkWriter.close()
        darkOutputStream.close()
    }

    private fun moveVirtualShieldsToNewSet(cardList: SWCCGCardList) {
        cardList.cards.forEach {

            if (it.front.type == "Defensive Shield") {
                if (it.sets?.contains("13") == true) {
                    //ref3
                } else if (it.sets?.contains("1000d") == true) {
                    //virtual block shields
                } else if (it.sets?.contains("1008") == true) {
                    //virtual block 8
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("1008")
                    newSets.add("1000d")
                    it.sets = newSets
                } else if (it.sets?.contains("301") == true) {
                    //virtual premium set
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("301")
                    newSets.add("200d")
                    it.sets = newSets
                } else if (it.sets?.contains("203") == true) {
                    //virtual set 3
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("203")
                    newSets.add("200d")
                    it.sets = newSets
                } else if (it.sets?.contains("209") == true) {
                    //virtual premium set
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("209")
                    newSets.add("200d")
                    it.sets = newSets
                } else if (it.sets?.contains("213") == true) {
                    //virtual premium set
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("213")
                    newSets.add("200d")
                    it.sets = newSets
                } else if (it.sets?.contains("200") == true) {
                    //virtual set 0
                    val newSets = it.sets?.toMutableSet() ?: mutableSetOf<String>()
                    newSets.remove("200")
                    newSets.add("200d")
                    it.sets = newSets
                } else {
                    println("Didn't handle shield from sets ${it.sets}.")
                }
            }
        }
    }

    private fun validateCardSets(cardList: SWCCGCardList) {
        cardList.cards.forEach {
            it.sets.let { sets ->
                if (sets == null || sets.isEmpty()) {
                    println(" --> NoSetsForCard ${it.front.title}")
                    return@forEach
                }

                if (it.front.type == "Defensive Shield") {
                    if (sets.contains("200d")) {
                        //virtual defensive shield set
                    } else if (sets.contains("1000d")) {
                        //virtual block shield set
                    } else if (sets.contains("13")) {
                        //ref3
                    } else {
                        println(" --> Defensive Shield NOT in a Defensive Shield Set or ref3. $sets")
                    }
                }

                if (sets.contains("200d") && (it.front.type != "Defensive Shield")) {
                    println(" --> Non Defensive Shield in Defensive Shield Set.")
                }

//                if (sets.size != 1) {
//                    println(" --> Card ${it.front.title} is in more than one set! [${sets.size}]")
//                }
            }
        }
    }

    private fun updateCardSet(cardList: SWCCGCardList, sets: List<SWCCGSet>) {
        cardList.cards.forEach { card ->
            /* for now just map "Demo Deck" to "Virtual Premium Set" */
            if (card.set == "Demo Deck") {
                card.set = "Virtual Premium Set"
            }

            /* for now just map "Virtual Defensive Shield" to "Virtual Block Shields" */
            if (card.set == "Virtual Defensive Shield") {
                card.set = "Virtual Block Shields"
            }

            val set = sets.find { set ->
                set.name == card.set || set.gempName == card.set
            }

            if (set == null) {
                println("Failed to find set for ${card.front.title} : ${card.set} : ${card.legacy}")
                return@forEach
            }

            val setsList = card.sets?.toMutableSet() ?: mutableSetOf()
            setsList.add(set.id)

            card.sets = setsList
        }
    }

    private fun updateCardUrls(cardList: SWCCGCardList) {
        cardList.cards.forEach { card ->
            updateCardUrlForFace(card.front)
            card.back?.let {
                updateCardUrlForFace(it)
            }
        }
    }

    private fun updateCardUrlForFace(face: SWCCGCardFace) {
        face.imageUrl = face.imageUrl?.replace("Images-HT/starwars/", "")
        face.imageUrl = face.imageUrl?.replace("?raw=true", "")
    }

    private fun handleDuplicateCardSet(
        cardListList: List<SWCCGCardList>,
        gempCardUrl: String,
        originalSet: List<String>,
        newSetId: String
    ): Boolean {
        cardListList.forEach { cardList ->
            val originalCard = cardList.cards.filter { card ->
                originalSet.contains(card.set)
            }.find { card ->
                card.front.imageUrl?.contains(gempCardUrl) == true
            }

            if (originalCard != null) {
                /* found original, lets add the new set! */
                val setsList = originalCard.sets?.toMutableSet() ?: mutableSetOf()
                setsList.add(newSetId)

                originalCard.sets = setsList

                /* found the original version of the card, lets copy and create a new one */
                /*
                val cardCopy = originalCard.copy()
                cardCopy.front = cardCopy.front.copy()
                cardCopy.back = cardCopy.back?.copy()

                cardCopy.id = currentMaxId + 1
                cardCopy.set = newSet
                currentMaxId++

                val originalNoSpaces = originalCard.set?.replace(" ", "") ?: ""
                val newNoSpaces = newSet.replace(" ", "")

                cardCopy.front.let { front ->
                    front.imageUrl = front.imageUrl?.replace(originalNoSpaces, newNoSpaces)
                }
                cardCopy.back?.let { back ->
                    back.imageUrl = back.imageUrl?.replace(originalNoSpaces, newNoSpaces)
                }

                println("CreatedNewAnthologyVersion --> ${cardCopy.front.title}")
                cardList.cards.add(cardCopy)
                 */

                return true
            }
        }

        return false
    }

    private fun updateGempId(cardList: SWCCGCardList, map: Map<String, MutableMap<String, String>>) {
        cardList.cards.forEach { card ->
            if (card.legacy == true) {
                return@forEach
            }

            val url = card.front.imageUrl ?: return@forEach
            val side = card.side ?: return@forEach
            val set = card.set ?: return@forEach
            val gempSetKey = "${invSetNameMap[set]}-${side}"
            val setMap = map[gempSetKey] ?: return@forEach
            val imageFilename = url.substring(url.lastIndexOf('/') + 1, url.length).substringBefore("?raw=true")

            card.gempId = setMap.remove(imageFilename)

            if (card.gempId == null) {
                /* didn't find a match, check our manually created id map */
                card.gempId = cardIdMapToGempId[card.id]
            }

            if (card.gempId == null) {
                println("UnableToFindGempIdForCard --> [$imageFilename] ${card.id} in $setMap")
            }
        }
    }

    private fun updateIcons(cardList: SWCCGCardList) {
        val allIcons = mutableSetOf<String>()
        val allCharacteristics = mutableSetOf<String>()

        cardList.cards.forEach {
            fixIconsForFace(it.front)
            it.back?.let { back ->
                fixIconsForFace(back)
            }

            // TEST STUFF BELOW
            it.front.icons?.let { icons ->
                allIcons.addAll(icons)
            }

            it.back?.icons?.let { icons ->
                allIcons.addAll(icons)
            }

            it.front.characteristics?.let { characteristics ->
                allCharacteristics.addAll(characteristics)
            }

            it.back?.characteristics?.let { characteristics ->
                allCharacteristics.addAll(characteristics)
            }
        }
    }

    private val iconTypoFixMap = mutableMapOf<String, String>()

    init {
        iconTypoFixMap["Resistace"] = "Resistance"
    }

    private fun fixIconsForFace(face: SWCCGCardFace) {
        face.icons?.let { icons ->
            iconTypoFixMap.keys.forEach { key ->
                if (icons.contains(key)) {
                    icons.remove(key)
                    iconTypoFixMap[key]?.let { fixedIcon ->
                        icons.add(fixedIcon)
                    }
                }
            }
        }
    }
}