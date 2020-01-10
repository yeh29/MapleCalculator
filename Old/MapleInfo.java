import java.util.HashMap;

public class MapleInfo {

    public static String [] allJobs(){
        return new String [] {"Battle Mage" , "Beast Tamer" , "Blaze Wizard" , 
            "Evan" , "Kanna", "Luminous", "Bishop" , "Arch Mage (Fire, Poison)", 
            "Arch Mage (Ice, Lightning)" , "Kinesis", "Illium", "Dual Blade", 
            "Night Walker", "Phantom", "Night Lord" , "Shadower" ,"Xenon", "Cadena", 
            "Aran", "Dawn Warrior", "Demon Avenger", "Demon Slayer", "Hayato", "Kaiser", 
            "Mihile", "Hero", "Paladin", "Dark Knight","Zero", "Blaster", "Bowmaster",
            "Marksman", "Wild Hunter", "Wind Archer", "Mercedes", "Angelic Buster" ,
            "Cannoneer", "Jett", "Mechanic", "Buccaneer", "Corsair", "Shade", "Thunder Breaker", "Ark"};
    }
    
    public static double initialMastery(String job){
        switch (job) {
            case "Battle Mage":
            case "Beast Tamer":
            case "Blaze Wizard":
            case "Evan":
            case "Kanna":
            case "Luminous":
            case "Bishop":
            case "Arch Mage (Fire, Poison":
            case "Arch Mage (Ice, Lightning)":
            case "Kinesis":
            case "Illium":
                return 0.25;
            case "Night Walker":
            case "Night Lord":
            case "Bowmaster":
            case "Marksman":
            case "Wild Hunter":
            case "Wind Archer":
            case "Mercedes":
            case "Angelic Buster":
            case "Cannoneer":
            case "Jett":
            case "Mechanic":
            case "Corsair":
                return 0.2;
            default:
                return 0.15;
        }
    }
    public static boolean isMagicAtt(String job){
        switch (job) {
            case "Battle Mage":
            case "Beast Tamer":
            case "Blaze Wizard":
            case "Evan":
            case "Kanna":
            case "Luminous":
            case "Bishop":
            case "Arch Mage (Fire, Poison":
            case "Arch Mage (Ice, Lightning)":
            case "Kinesis":
            case "Illium":
                return true;
            default:
                return false;
        }
    }
    
    public static String [] primaryStat(String job){
        switch (job) {
            case "Battle Mage":
            case "Beast Tamer":
            case "Blaze Wizard":
            case "Evan":
            case "Kanna":
            case "Luminous":
            case "Bishop":
            case "Arch Mage (Fire, Poison":
            case "Arch Mage (Ice, Lightning)":
            case "Kinesis":
            case "Illium":
                return new String [] {"int"};
            case "Bowmaster":
            case "Marksman":
            case "Wild Hunter":
            case "Wind Archer":
            case "Mercedes":
            case "Angelic Buster":
            case "Jett":
            case "Corsair":
            case "Mechanic":
                return new String [] {"dex"};
            case "Dual Blade":
            case "Night Walker": 
            case "Phantom": 
            case "Night Lord":
            case "Shadower":
            case "Cadena":
                return new String [] {"luk"};
            case "Aran":
            case "Dawn Warrior":
            case "Demon Slayer":
            case "Hayato":
            case "Kaiser":
            case "Mihile":
            case "Hero":
            case "Paladin":
            case "Dark Knight":
            case "Zero":
            case "Blaster":
            case "Cannoneer":
            case "Buccaneer":
            case "Shade":
            case "Thunder Breaker":
            case "Ark":
                return new String [] {"str"};
            case "Xenon":
                return new String[] {"str", "dex", "luk"};
            default:
                return new String [] {"hp"};
        }
    }
    public static String [] secondaryStat(String job){
        switch (job) {
            case "Battle Mage":
            case "Beast Tamer":
            case "Blaze Wizard":
            case "Evan":
            case "Kanna":
            case "Luminous":
            case "Bishop":
            case "Arch Mage (Fire, Poison":
            case "Arch Mage (Ice, Lightning)":
            case "Kinesis":
            case "Illium":
                return new String [] {"luk"};
            case "Bowmaster":
            case "Marksman":
            case "Wild Hunter":
            case "Wind Archer":
            case "Mercedes":
            case "Angelic Buster":
            case "Jett":
            case "Corsair":
            case "Mechanic":
            case "Demon Avenger":
                return new String [] {"str"};
            case "Aran":
            case "Dawn Warrior":
            case "Demon Slayer":
            case "Hayato":
            case "Kaiser":
            case "Mihile":
            case "Hero":
            case "Paladin":
            case "Dark Knight":
            case "Zero":
            case "Blaster":
            case "Cannoneer":
            case "Buccaneer":
            case "Shade":
            case "Thunder Breaker":
            case "Ark":
            case "Night Walker": 
            case "Phantom": 
            case "Night Lord":
                return new String [] {"dex"};
            case "Dual Blade":
            case "Shadower":
            case "Cadena":
                return new String [] {"str", "dex"};
            default:
                return null;
        }
    }
    
    public static String [] hyperStats(){
        return new String[] {"STR", "DEX", "INT", "LUK", "HP", "MP", "Speed", "Jump",
            "Critical Rate", "Critical Damage", "Damage", "Boss Damage", "Ignore DEF",
            "Knockback Resistance", "Elemental Resistance", "Abnormal Status Resistance"};
    }
    
    public static String hyperStatToMap(String stat){
        switch(stat){
            case "STR":
                return "hyperStr";
            case "DEX":
                return "hyperDex";
            case "INT":
                return "hyperInt";
            case "LUK":
                return "hyperLuk";
            case "HP":
                return "hyperHp";
            case "MP":
                return "hyperMp";
            case "Speed":
                return "hyperSpeed";
            case "Jump":
                return "hyperJump";
            case "Critical Rate":
                return "hyperCritRate";
            case "Critical Damage":
                return "hyperCritDamg";
            case "Damage":
                return "hyperDamg";
            case "Boss Damage":
                return "hyperBossDamg";
            case "Ignore DEF":
                return "hyperIgnoreDef";
            case "Knockback Resistance":
                return "hyperKnockback";
            case "Elemental Resistance":
                return "hyperEleResist";
            case "Abnormal Status Resistance":
                return "hyperStatusResist";
            default:
                return "";
        }
    }
    
    public static HashMap<String, Integer> HyperLvToStat(String stat, int level){
        int amount = 0;
        if(stat.equals("STR") || stat.equals("DEX") || stat.equals("INT") || stat.equals("LUK")){
            amount = 10 * level;
        } else if(stat.equals("HP") || stat.equals("MP") || stat.equals("Jump") || stat.equals("Knockback Resistance")){
            amount = 2* level;
        } else if(stat.equals("Speed") || stat.equals("Elemental Resistance")){
            amount = 4 * level;
        } else if(stat.equals("Ignore DEF") || stat.equals("Damage")){
            amount = 3 * level;
        } else if (stat.equals("Critical Damage")){
            amount = level;
        } else if(stat.equals("Critical Rate") || stat.equals("Abnormal Status Resistance")){
            if(level >= 1 && level <= 5){
                amount = level;
            } else {
                amount = 2 * level - 5;
            }
        } else if(stat.equals("Boss Damage")){
            if(level >= 1 && level <= 5){
                amount = 3 * level;
            } else {
                amount = 4 * level - 5;
            }
        }
        if(level == 0){
            amount = 0;
        }
        HashMap <String, Integer> val = new HashMap<>();
        val.put(resolveHSChoice(stat), amount);
        return val;
    }
    
    private static String resolveHSChoice(String stat){
        switch(stat){
            case "STR":
                return "finalStr";
            case "DEX":
                return "finalDex";
            case "INT":
                return "finalInt";
            case "LUK":
                return "finalLuk";
            case "HP":
                return "hp%";
            case "MP":
                return "mp%";
            case "Speed":
                return "speed";
            case "Jump":
                return "jump";
            case "Critical Rate":
                return "critRate";
            case "Critical Damage":
                return "critDamg";
            case "Damage":
                return "bonusDamage";
            case "Boss Damage":
                return "bossDamage";
            case "Ignore DEF":
                return "ignoreDefense";
            case "Knockback Resistance":
                return "knockbackResist";
            case "Elemental Resistance":
                return "elementalResist";
            case "Abnormal Status Resistance":
                return "statusResist";
            default:
                return "";
        }
    }
    
    public static String [] innerAbility(){
        return new String [] {"STR", "DEX", "INT", "LUK", "Buff Duration", "DEX to STR %", "INT to LUK %",
            "Drop Rate", "Damage To Normal Monsters", "LUK to DEX %", "HP", "MP", "Meso Rate",
            "DEF", "STR to DEX %", "Weapon Att", "Critical Rate", "Jump", "Magic Att", "Speed",
            "DEF %", "Boss Damage", "HP%", "MP%", "Weapon Att By Level", "Attack Speed", "Magic Att By Level"};
    }
    
    
    public static HashMap<String, Integer> arcanePowerCalc(int arcaneForce, String job){
        HashMap<String, Integer> stat = new HashMap<>();
        if(job.equals("Xenon")){
            stat.put("finalStr", (int)(arcaneForce * 3.9));
            stat.put("finalDex", (int)(arcaneForce * 3.9));
            stat.put("finalLuk", (int)(arcaneForce * 3.9));
        } else if (job.equals("Demon Avenger")){
            stat.put("finalHp", arcaneForce * 140);
            stat.put("equipHp", arcaneForce * 140);
        } else {
            String [] mainStat = MapleInfo.primaryStat(job);
            String theStat = "final" + mainStat[0].substring(0, 1).toUpperCase() + mainStat[0].substring(1);
            stat.put(theStat, arcaneForce * 10);
        }
        return stat;
    }
 
    public static HashMap<String, Integer> willpowerLvToStat(int level){
        HashMap<String, Integer> stat = new HashMap<>();
        int base = level / 5;
        int bonusHP = 100 * base;
        int addedDef = base * 5;
        stat.put("bonusHp", bonusHP);
        stat.put("addedDefense", addedDef);
        stat.put("statusResist", base);
        return stat;
    }
    
    public static HashMap<String, Integer> empathyLvToStat(int level){
        HashMap<String, Integer> stat = new HashMap<>();
        int base = level / 5;
        int bonusMP = 100 * base;
        int buffDuration = base / 2;
        stat.put("bonusMp", bonusMP);
        stat.put("buffDuration", buffDuration);
        return stat;
    }
    
    public static HashMap<String, Integer> insightLvToStat(int level){
        HashMap<String, Integer> stat = new HashMap<>();
        stat.put("ignoreElementalDef", level / 20);
        return stat;
    }
    
    public static double ambitionLvToStat(int level){
        double base = level / 10.0;
        return (Math.floor(base * 2) / 2.0);
    }
    
    public static String resolveIAChoice(String fromList){
        switch(fromList){
            case "STR":
                return "finalStr";
            case "DEX":
                return "finalDex";
            case "INT":
                return "finalInt";
            case "LUK":
                return "finalLuk";
            case "Buff Duration":
                return "buffDuration";
            case "Drop Rate":
                return "dropRate";
            case "Damage To Normal Monsters":
                return "bonusDamage";
            case "HP":
                return "bonusHp";
            case "MP":
                return "bonusMp";
            case "Meso Rate":
                return "mesoRate";
            case "DEF":
                return "addedDefense";
            case "Weapon Att":
                return "att";
            case "Critical Rate":
                return "critRate";
            case "Jump":
                return "jump";
            case "Magic Att":
                return "magicAtt";
            case "Speed":
                return "speed";
            case "DEF %":
                return "def%";
            case "Boss Damage":
                return "bossDamage";
            case "HP%":
                return "hp%";
            case "MP%":
                return "mp%";
            default:
                return null;
        }
    }
    
}