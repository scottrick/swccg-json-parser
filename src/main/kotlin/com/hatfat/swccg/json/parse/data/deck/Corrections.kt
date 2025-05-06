package com.hatfat.swccg.json.parse.data.deck

fun createRedFlagList(): List<String> {
    val redFlags = mutableListOf<String>()

    redFlags.add("boob")

    return redFlags
}

fun insertSharedGeneralCorrections(corrections: MutableMap<String, String>) {
    corrections["planed"] = "planned"
    corrections["insurection"] = "insurrection"
    corrections["millenium"] = "millennium"
    corrections["lieutentant"] = "lieutenant"
    corrections["stumach"] = "stomach"
    corrections["vengence"] = "vengeance"
    corrections["villany"] = "villainy"
    corrections["ornamnet"] = "ornament"
    corrections["execcutor"] = "executor"
    corrections["lifesaver"] = "lightsaber"
    corrections["prpare"] = "prepare"
    corrections["moonevers"] = "maneuvers"
    corrections["berrier"] = "barrier"
    corrections["frist"] = "first"
    corrections["preparded"] = "prepared"
    corrections["whatareyoutryingtopushonus"] = "whatreyoutryintopushonus"
    corrections["sullest"] = "sullust"
    corrections["dsiidockingbay"] = "deathstariidockingbay"
    corrections["dsdb"] = "deathstardockingbay"
    corrections["spaceportdb"] = "spaceportdockingbay"
    corrections["attck"] = "attack"
    corrections["desroy"] = "destroy"
    corrections["contol"] = "control"
    corrections["couse"] = "course"
    corrections["lighsaber"] = "lightsaber"
    corrections["lightsaver"] = "lightsaber"
    corrections["lightsaver"] = "lightsaber"
    corrections["flashlight"] = "lightsaber"
    corrections["withstick"] = "withlightsaber"
    corrections["maneuvres"] = "maneuvers"
    corrections[" saber"] = " lightsaber"
    corrections["strenght"] = "strength"
    corrections["forjthe"] = "forthe"
    corrections["jp"] = "jabbaspalace"
    corrections["defence"] = "defense"
    corrections["wgun"] = "withgun"
    corrections["vibroaxe"] = "vibroax"
    corrections["enforcment"] = "enforcement"
    corrections["tatjabba"] = "tatooinejabba"
    corrections["tatooinedb"] = "tatooinedockingbay94"
    corrections["cmdr"] = "commander"
    corrections["intrudermissle"] = "intrudermissile"
    corrections["kashyyk"] = "kashyyyk"
    corrections["tatdb"] = "tatooinedockingbay94"
    corrections["tatcantina"] = "tatooinecantina"
    corrections["raltir"] = "ralltiir"
    corrections["ralltir"] = "ralltiir"
    corrections["raltiir"] = "ralltiir"
    corrections["rallitir"] = "ralltiir"
    corrections["corelia"] = "corellia"
    corrections["correlia"] = "corellia"
    corrections["correllia"] = "corellia"
    corrections["karde"] = "karrde"
    corrections["nottry"] = "notry"
}

fun insertSharedExactCorrections(corrections: MutableMap<String, String>) {
    corrections["dockingbay"] = "spaceportdockingbay"
    corrections["tatooinedockingbay"] = "tatooinedockingbay94"
}

fun createLightGeneralCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()
    insertSharedGeneralCorrections(corrections)

    corrections["wiseadvise"] = "wiseadvice"
    corrections["saveyouitwill"] = "saveyouitcan"
    corrections["rendevous"] = "rendezvous"
    corrections["thelocalgym"] = "trainingarea"
    corrections["yoda800yearoldmentor"] = "yoda"
    corrections["yodathemuppet"] = "yoda"
    corrections["exclude"] = "vibro"
    corrections["yman"] = "yoda"
    corrections["jeditest1"] = ""
    corrections["jeditest2"] = ""
    corrections["jeditest3"] = ""
    corrections["youtryingto"] = "youtryinto"
    corrections["insightsserve"] = "insightserve"
    corrections["insightserveyou"] = "insightservesyou"
    corrections["transterm"] = "transmissionterminated"
    corrections["engineeringcorp"] = "engineeringcorporation"
    corrections["bargainintable"] = "bargainingtable"
    corrections["hftmf"] = "headingforthemedicalfrigate"
    corrections["home1"] = "homeone"
    corrections["ooctrans"] = "outofcommissiontrans"
    corrections["commision"] = "commission"
    corrections["antillies"] = "antilles"

    return corrections
}

fun createLightExactCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()
    insertSharedExactCorrections(corrections)

    corrections["mfalcon"] = "millenniumfalcon"
    corrections["wiseadvicedoordonot"] = "doordonotwiseadvice"
    corrections["hiddenbase"] = "hiddenbasesystemswillslipthroughyourfingers"
    corrections["hiddenbaseswstyf"] = "hiddenbasesystemswillslipthroughyourfingers"
    corrections["hanwithgun"] = "hanwithheavyblasterpistol"
    corrections["hanwithblaster"] = "hanwithheavyblasterpistol"
    corrections["eppluke"] = "lukewithlightsaber"
    corrections["lukewithsaber"] = "lukewithlightsaber"
    corrections["wedgerogueleader"] = "wedgeantillesredsquadronleader"
    corrections["lowerpassages"] = "jabbaspalaceantechamber"
    corrections["barrier"] = "rebel barrier"
    corrections["headingforthemedfrigate"] = "headingforthemedicalfrigate"
    corrections["lukeskywalkerjk"] = "lukeskywalkerjediknight"
    corrections["lukejedi"] = "lukeskywalkerjediknight"
    corrections["lsjk"] = "lukeskywalkerjediknight"
    corrections["lukeskywalkerrs"] = "lukeskywalkerrebelscout"
    corrections["lukescout"] = "lukeskywalkerrebelscout"
    corrections["lsrs"] = "lukeskywalkerrebelscout"
    corrections["uncleandauntlars"] = "owenlarsberulars"
    corrections["asp"] = "asp707"
    corrections["yavin4warroom"] = "yavin4massassiwarroom"
    corrections["endorchirpashut"] = "endorchiefchirpashut"
    corrections["chirpashut"] = "endorchiefchirpashut"
    corrections["yavin4throneroom"] = "yavin4massassithroneroom"
    corrections["hothwarroom"] = "hothechocommandcenter"
    corrections["mindwhatyouhavelearned"] = "mindwhatyouhavelearnedsaveyouitcan"
    corrections["hothdb"] = "hothechodockingbay"
    corrections["homeonedb"] = "homeonedockingbay"
    corrections["clashoflightsabers"] = "clashofsabers"
    corrections["artooandthreepio"] = "artoothreepio"
    corrections["signal"] = "thesignal"
    corrections["wedge"] = "wedgeantilles"
    corrections["chewieprotector"] = "chewbaccaprotector"
    corrections["landowpistol"] = "landowithblasterpistol"
    corrections["falcon"] = "millenniumfalcon"
    corrections["jabbaspalace"] = "tatooinejabbaspalace"
    corrections["projection"] = "projectionofaskywalker"
    corrections["afa"] = "angerfearaggression"
    corrections["grabber"] = "whatreyoutryingtopushonus"
    corrections["honor"] = "honorofthejedi"
    corrections["cantina"] = "tatooinecantina"
    corrections["squadassign"] = "squadronassignments"
    corrections["noblesac"] = "noblesacrifice"
    corrections["dashrender"] = "dashrendar"
    corrections["wedgersl"] = "wedgeantillesredsquadronleader"
    corrections["elyhkrue"] = "elyhekrue"
    corrections["boussh"] = "boushh"
    corrections["xwinglasercannons"] = "xwinglasercannon"
    corrections["naytaan"] = "lieutenantnaytaan"
    corrections["brenquersy"] = "brenquersey"
    corrections["holokland"] = "holokand"

    return corrections
}

fun createLightWildcardCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()

    corrections["madine"] = "generalcrixmadine"
    corrections["khaa"] = "tawsskhaa"
    corrections["agentsin"] = "agentsinthecourtnolovefortheempire"
    corrections["watchyourstep"] = "watchyourstepthisplacecanbealittlerough"
    corrections["wys"] = "watchyourstepthisplacecanbealittlerough"
    corrections["94"] = "tatooinedockingbay94"
    corrections["capthan"] = "captainhansolo"
    corrections["capnhan"] = "captainhansolo"
    corrections["chewiewith"] = "chewiewithblasterrifle"
    corrections["landowithgun"] = "landowithblasterpistol"
    corrections["lukew"] = "lukewithlightsaber"
    corrections["leebo"] = "lebo2d9"
    corrections["artooihavea"] = "artooihaveabadfeelingaboutthis"
    corrections["whatreyou"] = "whatreyoutryintopushonus"
    corrections["transmissionterminatedooc"] = "outofcommissiontransmissionterminated"
    corrections["ooctt"] = "outofcommissiontransmissionterminated"
    corrections["skate"] = "pulsarskate"
    corrections["mirax"] = "miraxterrik"
    corrections["hanw"] = "hanwithheavyblasterpistol"
    corrections["ralltiircaptain"] = "ralltiirfreightercaptain"
    corrections["rfc"] = "ralltiirfreightercaptain"
    corrections["ecclando"] = "landowithblasterpistol"
    corrections["jediluke"] = "lukeskywalkerjediknight"
    corrections["yisyw"] = "yourinsightservesyouwell"
    corrections["dodnwa"] = "doordonotwiseadvice"
    corrections["deathstardockingbay"] = "deathstardockingbay327"
    corrections["westgallery"] = "cloudcitywestgallery"
    corrections["leiaw"] = "leiawithblasterrifle"
    corrections["omdh"] = "ourmostdesperatehour"
    corrections["landowv"] = "landowithvibroax"
    corrections["d2inr"] = "artoodetooinred5"
    corrections["ackbar"] = "admiralackbar"
    corrections["hobbie"] = "derekhobbieklivian"
    corrections["landoin"] = "landoinmillenniumfalcon"
    corrections["kalfal"] = "kalfalnlcndros"
    corrections["biggs"] = "biggsdarklighter"
    corrections["lukerebelscout"] = "lukeskywalkerrebelscout"
    corrections["ralfreighter"] = "ralltiirfreightercaptain"
    corrections["hasshn"] = "majorhaashn"

    return corrections
}

fun createLightSplitCorrections(): Map<String, List<String>> {
    val splits = mutableMapOf<String, List<String>>()

    splits["red squadron 1 4 7"] = listOf("redsquadron1", "redsquadron4", "redsquadron7")

    return splits
}

fun createDarkGeneralCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()
    insertSharedGeneralCorrections(corrections)

    corrections["wrarped"] = "wrapped"
    corrections["abysin"] = "abyssin"
    corrections["emporer"] = "emperor"
    corrections["empoper"] = "emperor"
    corrections["interperors"] = "interceptor"
    corrections["therewillbehell"] = "therellbehell"
    corrections["wakeemui"] = "wakeelmui"
    corrections["zuckass"] = "zuckuss"
    corrections["ghhk"] = "ghhhk"
    corrections["monok"] = "monnok"
    corrections["chimara"] = "chimaera"
    corrections["chimera"] = "chimaera"
    corrections["advenger"] = "avenger"
    corrections["rendilli"] = "rendili"
    corrections["twileck"] = "twilek"
    corrections["vaderlightsaber"] = "vaderslightsaber"
    corrections["tatdesert"] = "tatooinedesert"
    corrections["dsdocking"] = "deathstardocking"
    corrections["omnibox"] = "ommnibox"
    corrections["sarlaccpit"] = "greatpitofcarkoon"
    corrections["jadeteh"] = "jadetheemperorshand"
    corrections["loveofmoney"] = "lom"
    corrections["concussiongun"] = "concussionrifle"
    corrections["villiany"] = "villainy"
    corrections["darthvadersstrike"] = "darkstrike"
    corrections["jabbassmooch"] = "huttsmooch"
    corrections["evastan"] = "evazan"
    corrections["twilel"] = "twilek"
    corrections["trweu"] = "thoserebelswontescapeus"
    corrections["godhert"] = "godherdt"
    corrections["preparedefense"] = "prepareddefense"
    corrections["ardon"] = "ardan"
    corrections["doordonot"] = "thereisnotry"
    corrections["palatine"] = "palpatine"

    return corrections
}

fun createDarkExactCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()
    insertSharedExactCorrections(corrections)

    corrections["shockinginformation"] = "shockingrevelation"
    corrections["interceptor"] = "tieinterceptor"
    corrections["whatreyoutryintopushonus"] = "therellbehelltopay"
    corrections["rebelswontescape"] = "thoserebelswontescape"
    corrections["ghhhkrebelswontescapeus"] = "ghhhkthoserebelswontescapeus"
    corrections["lowerpassages"] = "jabbaspalacelowerpassages"
    corrections["jabbaspalacelowerpassage"] = "jabbaspalacelowerpassages"
    corrections["s+d"] = "searchanddestroy"
    corrections["searchdestroy"] = "searchanddestroy"
    corrections["barrier"] = "imperial barrier"
    corrections["impbarrier"] = "imperialbarrier"
    corrections["ddb"] = "deathstardockingbay327"
    corrections["dsbookingarea"] = "deathstardockingbay327"
    corrections["dssolitary"] = "deathstardetentionblockcorridor"
    corrections["ccdb"] = "cloudcityeastplatform"
    corrections["executordb"] = "executordockingbay"
    corrections["hdadtj"] = "huntdownanddestroythejeditheirfirehasgoneoutoftheuniverse"
    corrections["prepdef"] = "prepareddefenses"
    corrections["marajade"] = "marajadetheemperorshand"
    corrections["mara"] = "marajadetheemperorshand"
    corrections["mobpoints"] = "mobilizationpoints"
    corrections["twilek"] = "twilek advisor"
    corrections["visages"] = "visage of the emperor"
    corrections["chokevader"] = "darthvaderdarklordofthesith"
    corrections["eppvader"] = "darthvaderwithlightsaber"
    corrections["maralightsaber"] = "marajadeslightsaber"
    corrections["emperorpalpy"] = "emperorpalpatine"
    corrections["imparrestorder"] = "imperialarrestorder"
    corrections["iao"] = "imperialarrestorder"
    corrections["medchamber"] = "executormeditationchamber"
    corrections["meditationchamber"] = "executormeditationchamber"
    corrections["janus"] = "janusgreejatus"
    corrections["1holotheatre"] = "executorholotheatre"
    corrections["holotheatre"] = "executorholotheatre"
    corrections["1impdecree"] = "imperialdecree"
    corrections["impdecree"] = "imperialdecree"
    corrections["dre"] = "drevazan"
    corrections["fett"] = "bobafettwithblasterrifle"
    corrections["eppbobafett"] = "bobafettwithblasterrifle"
    corrections["eppfett"] = "bobafettwithblasterrifle"
    corrections["ychf"] = "youcannothideforever"
    corrections["ig88withgun"] = "ig88withriotgun"
    corrections["ig88inship"] = "ig88inig2000"
    corrections["marasstick"] = "marajadeslightsaber"
    corrections["maraslightsaber"] = "marajadeslightsaber"
    corrections["mkosfai"] = "mykindofscumfearlessandinventive"
    corrections["dreadnought"] = "dreadnaughtclassheavycruiser"
    corrections["oppressiveenforcementthereisnotry"] = "thereisnotryoppressiveenforcement"
    corrections["bibfortunaadvior"] = "twilekadvisor"
    corrections["monoc"] = "myo" // 2001-01-15-12932
    corrections["dremrwalrus"] = "drevazanpondababa"
    corrections["jabbasprize"] = "jabbasprizejabbasprize"
    corrections["jabbaspalace"] = "tatooinejabbaspalace"
    corrections["scum"] = "scumandvillainy"
    corrections["mercpilot"] = "mercenarypilot"
    corrections["scumvillainy"] = "scumandvillainy"
    corrections["grabber"] = "therellbehelltopay"
    corrections["gmtarkin"] = "grandmofftarkin"
    corrections["granddaddythrawn"] = "grandadmiralthrawn"
    corrections["cantina"] = "tatooinecantina"
    corrections["gallid"] = "gailid"
    corrections["snoovaspaddle"] = "vibroax"

    return corrections
}

fun createDarkWildcardCorrections(): Map<String, String> {
    val corrections = mutableMapOf<String, String>()

    corrections["sycfa"] = "setyourcourseforalderaantheultimatepowerintheuniverse"
    corrections["setyourcourse"] = "setyourcourseforalderaantheultimatepowerintheuniverse"
    corrections["chybc"] = "comehereyoubigcoward"
    corrections["courtofthe"] = "courtofthevilegangsterishallenjoywatchingyoudie"
    corrections["fettw"] = "bobafettwithblasterrifle"
    corrections["fettin"] = "bobafettinslavei"
    corrections["dengarw"] = "dengarwithblastercarbine"
    corrections["iggywith"] = "ig88withriotgun"
    corrections["ig88w"] = "ig88withriotgun"
    corrections["iggyin"] = "ig88inig2000"
    corrections["vaderw"] = "darthvaderwithlightsaber"
    corrections["dengarwith"] = "dengarwithblastercarbine"
    corrections["dengarin"] = "dengarinpunishingone"
    corrections["boskin"] = "bosskinhoundstooth"
    corrections["bosskin"] = "bosskinhoundstooth"
    corrections["bosskw"] = "bosskwithmortargun"
    corrections["zuckster"] = "zuckussinmisthunter"
    corrections["zuckusin"] = "zuckussinmisthunter"
    corrections["zuckussin"] = "zuckussinmisthunter"
    corrections["4lomw"] = "4lomwithconcussionrifle"
    corrections["elephant"] = "ephantmon"
    corrections["r3p"] = "u3po"
    corrections["desertheart"] = "tatooinedesertheart"
    corrections["passenger"] = "jabbassailbargepassengerdeck"
    corrections["dlots"] = "darthvaderdarklordofthesith"
    corrections["ccengin"] = "cloudcityengineer"
    corrections["deathstardockingbay"] = "deathstardockingbay327"
    corrections["tinto"] = "thereisnotryoppressiveenforcement"
    corrections["mkos"] = "mykindofscumfearlessandinventive"
    corrections["djas"] = "djaspuhr"
    corrections["westgallery"] = "cloudcitywestgallery"
    corrections["yalnal"] = "nevaryalnal"
    corrections["tdigwatt"] = "thisdealisgettingworseallthetimeprayidontalteritanyfurther"
    corrections["obsidian2"] = "os722inobsidian2"
    corrections["endoroperation"] = "endoroperationsimperialoutpost"
    corrections["pellaeon"] = "captaingiladpellaeon"
    corrections["dreadnaught"] = "dreadnaughtclassheavycruiser"
    corrections["scyfa"] = "setyourcourseforalderaantheultimatepowerintheuniverse"
    corrections["wblastercarbine"] = "dengarwithblastercarbine"
    corrections["controlandset"] = "controlsetforstun"

    return corrections
}

fun createDarkSplitCorrections(): Map<String, List<String>> {
    val splits = mutableMapOf<String, List<String>>()

    return splits
}
