package com.hatfat.swccg.json.parse.serialize

import com.google.gson.*
import com.hatfat.swccg.json.parse.data.SWCCGCardFace
import java.lang.reflect.Type

/*
    Handles deserializing the "hack" elements that could  be Strings or Numbers...
 */
class SWCCGCardFaceSerializer : JsonSerializer<SWCCGCardFace> {
    private val gson = Gson()

    override fun serialize(src: SWCCGCardFace?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        if (src == null) {
            return JsonNull.INSTANCE
        }

        val jsonObject = JsonObject()

        jsonObject.addProperty("title", src.title)
        jsonObject.addProperty("imageUrl", src.imageUrl)
        jsonObject.addProperty("type", src.type)
        jsonObject.addProperty("subType", src.subType)
        jsonObject.addProperty("uniqueness", src.uniqueness)
        addHackElement(jsonObject, "destiny", src.destiny)
        addHackElement(jsonObject, "power", src.power)
        addHackElement(jsonObject, "ability", src.ability)
        addHackElement(jsonObject, "maneuver", src.maneuver)
        addHackElement(jsonObject, "armor", src.armor)
        addHackElement(jsonObject, "hyperspeed", src.hyperspeed)
        addHackElement(jsonObject, "landspeed", src.landspeed)
        addHackElement(jsonObject, "politics", src.politics)
        addHackElement(jsonObject, "deploy", src.deploy)
        addHackElement(jsonObject, "forfeit", src.forfeit)
        src.icons?.let {
            val iconsArray = JsonArray()
            it.forEach { iconsArray.add(it) }
            jsonObject.add("icons", iconsArray)
        }
        jsonObject.addProperty("lightSideIcons", src.lightSideIcons)
        jsonObject.addProperty("darkSideIcons", src.darkSideIcons)
        src.characteristics?.let {
            val characteristicsArray = JsonArray()
            it.forEach { characteristicsArray.add(it) }
            jsonObject.add("characteristics", characteristicsArray)
        }
        jsonObject.addProperty("gametext", src.gametext)
        addHackElement(jsonObject, "parsec", src.parsec)
        jsonObject.addProperty("lore", src.lore)
        src.extraText?.let {
            val extraTextArray = JsonArray()
            it.forEach { extraTextArray.add(it) }
            jsonObject.add("extraText", extraTextArray)
        }

        return jsonObject
    }

    private fun addHackElement(jsonObject: JsonObject, name: String, value: String?) {
        if (value == null) {
            return
        }

        value.toIntOrNull().let {
            if (it != null) {
                jsonObject.addProperty(name, it)
            } else {
                jsonObject.addProperty(name, value)
            }
        }
    }
}