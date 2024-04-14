package com.hatfat.swccg.json.parse.data.deck

fun createCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()

    corrections["saveyouitwill"] = "saveyouitcan"
    corrections["lightsaver"] = "lightsaber"
    corrections["milleniumfalcon"] = "millenniumfalcon"
    corrections["yoda800yearoldmentor"] = "yoda"
    corrections["withstick"] = "withlightsaber"
    corrections["exclude"] = "vibro"
    corrections["maneuvres"] = "maneuvers"
    corrections[" saber"] = " lightsaber"
    corrections["thelocalgym"] = "trainingarea"
    corrections["yman"] = "yoda"
    corrections["lowerpassages"] = "jabbaspalacelowerpassages"
    corrections["hanwithgun"] = "hanwithheavyblasterpistol"
    corrections["wedgerogueleader"] = "wedgeantillesredsquadronleader "
    corrections["strenght"] = "strength"
    corrections["jeditest1"] = ""
    corrections["jeditest2"] = ""
    corrections["jeditest3"] = ""
    corrections["s+d"] = "searchanddestroy"

    return corrections
}