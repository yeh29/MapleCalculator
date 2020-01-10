import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapleCharacter {
    
    private String name;
    private String job;
    private int level;
    private double mastery;
    private double weaponMult;      //Calculate when equip weapon + affected by Job 
    private double critDamage;
    private double ignoreDef;
    private ArrayList<String> innerAbility;
    private final HashMap<String, Integer> STATS = new HashMap<>();
    private final HashMap<String, MapleEquip> EQUIPS = new HashMap<>();
    
    public MapleCharacter(){
        prepareMaps();
        //load(); Use Instead 
    }
    
    public MapleCharacter(String name, int level, String job){
        this.name = name;
        this.level = level;
        this.job = job;
        mastery = MapleInfo.initialMastery(job);
        weaponMult = 0;
        critDamage = 0;
        ignoreDef = 0;
        innerAbility = new ArrayList<>();
        prepareMaps();
    }
    
    public int getTotalStr(){
        int floor1 = (int)(STATS.get("baseStr") * (1 + STATS.get("ap%")/ 100.0) + STATS.get("bonusStr"));
        return (int)(floor1 * (1 + STATS.get("str%") / 100.0) + STATS.get("finalStr"));
    }
    
    public int getTotalDex(){
        int floor1 = (int)(STATS.get("baseDex") * (1 + STATS.get("ap%")/ 100.0) + STATS.get("bonusDex"));
        return (int)(floor1 * (1 + STATS.get("dex%") / 100.0) + STATS.get("finalDex"));
    }
    
    public int getTotalInt(){
        int floor1 = (int)(STATS.get("baseInt") * (1 + STATS.get("ap%")/ 100.0) + STATS.get("bonusInt"));
        return (int)(floor1 * (1 + STATS.get("int%") / 100.0) + STATS.get("finalInt")); 
    }
    
    public int getTotalLuk(){
        int floor1 = (int)(STATS.get("baseLuk") * (1 + STATS.get("ap%")/ 100.0) + STATS.get("bonusLuk"));
        return (int)(floor1 * (1 + STATS.get("luk%") / 100.0) + STATS.get("finalLuk"));
    }
    
    public int getBaseStr(){
        return STATS.get("baseStr");
    }
    
    public int getBaseDex(){
        return STATS.get("baseDex");
    }
    
    public int getBaseInt(){
        return STATS.get("baseInt");
    }
    
    public int getBaseLuk(){
        return STATS.get("baseLuk");
    }
    
    public int getHp(){
        return STATS.get("hp");
    }
    
    public int getMp(){
        return STATS.get("mp");
    }
    
    public int getBonusDamage(){
        return STATS.get("bonusDamage");
    }
    
    public int getBossDamage(){
        return STATS.get("bossDamage");
    }
    
    public int getFinalDamage(){
        return STATS.get("finalDamage");
    }
    
    public double getIgnoreDef(){
        return ignoreDef;
    }
    
    public int getCritRate(){
        return STATS.get("critRate");
    }
    
    public double getCritDamage(){
        return critDamage;
    }
    
    public int getStatusResist(){
        return STATS.get("statusResist");
    }
    
    public int getKnockbackResist(){
        return STATS.get("knockbackResist");
    }
    
    public int getDefense(){
        double fromStats = 1.5 * getTotalStr() + 0.4 * getTotalDex() + 0.4 * getTotalLuk() + STATS.get("addedDefense");
        return (int)(fromStats * (1 + STATS.get("def%") / 100.0));
    }
    
    public int getSpeed(){
        if(STATS.get("speed") > STATS.get("speedCap"))
            return STATS.get("speedCap");
        else
            return STATS.get("speed");
    }
    
    public int getJump(){
        if(STATS.get("jump") > STATS.get("jumpCap"))
            return STATS.get("jumpCap");
        else
            return STATS.get("jump");
    }
    
    public int getStarForce(){
        return STATS.get("starForce");
    }
    
    public int getArcanePower(){
        return STATS.get("arcanePower");
    }
    
    public int getAttackSpeed(){
        if(STATS.get("attackSpeed") < STATS.get("attackSpeedCap"))
            return STATS.get("attackSpeedCap");
        else
            return STATS.get("attackSpeed");
    }
    
    public int getIgnoreEleDef(){
        return STATS.get("ignoreElementalDef");
    }
    
    public int getEleResist(){
        return STATS.get("elementalResist");
    }
    
    public int getMesoRate(){
        return STATS.get("mesoRate");
    }
    
    public int getDropRate(){
        return STATS.get("dropRate");
    }
    
    public int getIgnoreDamage(){
        return STATS.get("ignoreDamage");
    }
    
    public int getHpPercent(){
        return STATS.get("hp%");
    }
    
    public int getMpPercent(){
        return STATS.get("mp%");
    }
    
    public int getDefensePercent(){
        return STATS.get("def%");
    }
    
    public int getStrPercent(){
        return STATS.get("str%");
    }
    
    public int getDexPercent(){
        return STATS.get("dex%");
    }
    
    public int getIntPercent(){
        return STATS.get("int%");
    }
    
    public int getLukPercent(){
        return STATS.get("luk%");
    }
    
    public double getTotalMagicAtt(){
        return STATS.get("magicAtt") * (1 + STATS.get("magicAtt%") / 100.0) + STATS.get("finalMagicAtt");
    }
    
    public double getTotalAtt(){
        return STATS.get("att") * (1 + STATS.get("att%") / 100.0) + STATS.get("finalAtt");
    }
    
    public int getMagicAttPercent(){
        return STATS.get("magicAtt%");
    }
    
    public int getAttPercent(){
        return STATS.get("att%");
    }
    
    public int getBuffDurr(){
        return STATS.get("buffDuration");
    }
    
    public int getDamageRangeMin(){
        double att;
        double statValue;
        if(MapleInfo.isMagicAtt(job))
           att = getTotalMagicAtt();
        else
            att = getTotalAtt();
        if(job.equals("Demon Avenger")){
            int baseHP = (int)(STATS.get("baseHp") / 3.5);
            int maxHP = (int)(getHp() - STATS.get("baseHp") / 3.5);
            statValue = baseHP + maxHP + getTotalStr();
        }else{
            statValue = statValue();
        } 
        int round = (int)(Math.round(weaponMult * statValue * att / 100.0));
        int up = (int)(Math.ceil(round * (1 + STATS.get("bonusDamage") / 100.0)));
        int down = (int)(up * mastery);
        return (int)(Math.round(down * (1 + STATS.get("finalDamage") / 100.0)));
    }
    
    public int getDamageRangeMax(){
        double att;
        double statValue;
        if(MapleInfo.isMagicAtt(job))
           att = getTotalMagicAtt();
        else
            att = getTotalAtt();
        if(job.equals("Demon Avenger")){
            int baseHP = (int)(STATS.get("baseHp") / 3.5);
            int maxHP = (int)(getHp() - STATS.get("baseHp") / 3.5);
            statValue = baseHP + maxHP + getTotalStr();
        }else{
           statValue = statValue();
        } 
        int round = (int)(Math.round(weaponMult * statValue * att / 100.0));
        int down = (int)(round * (1 + STATS.get("bonusDamage") / 100.0));
        return (int)(Math.round(down * (1 + STATS.get("finalDamage") / 100.0)));
    }
    
    private int statValue(){
        int statValue = 0;
        String [] primaryStat = MapleInfo.primaryStat(job);
        for(String s : primaryStat){
            statValue = statValue + statToValue(s) * 4;
        }
        String [] secondaryStat = MapleInfo.secondaryStat(job);
        if(secondaryStat != null){
            for(String s : secondaryStat){
                statValue = statValue + statToValue(s);
            }
        }
        return statValue;
    }
    
    private int statToValue(String stat){
        switch (stat) {
            case "str":
                return getTotalStr();
            case "dex":
                return getTotalDex();
            case "int":
                return getTotalInt();
            case "luk":
                return getTotalLuk();
            default:
                return getHp();
        }
    }
    
    public String [] statComparisons(DecimalFormat df){
        ArrayList<String> comparisons = new ArrayList<>();
        /*
            Currently 1 Primary Stat = x Primary Stat             (Can see from formula)
            Currently 1 Attack = x Primary Stat                   (Can see from damage formula)
            Currently 1% Primary Stat = x Primary Stat            (Can see from formula)
            Currently 8% Crit Damg = % primary stat               
            Currently 12% wep/magic att = % boss
        */ 
        if(!job.equals("Demon Avenger")){
            for(int i = 0; i < MapleInfo.primaryStat(job).length; i++){
                String primaryStat = MapleInfo.primaryStat(job)[i];
                String statPercent = primaryStat + "%";
                String finalStat = "final" + primaryStat.substring(0, 1).toUpperCase() + primaryStat.substring(1);
                String bonusStat = "bonus" + primaryStat.substring(0, 1).toUpperCase() + primaryStat.substring(1);
                String baseStat = "base" + primaryStat.substring(0, 1).toUpperCase() + primaryStat.substring(1);
                double totalStat = (STATS.get(baseStat) * (1 + STATS.get("ap%")/ 100.0) + STATS.get(bonusStat)) * (1 + STATS.get(statPercent) / 100.0) + STATS.get(finalStat);
                double statToStat = ((STATS.get(baseStat) * (1 + STATS.get("ap%")/ 100.0) + STATS.get(bonusStat) + 1) * (1 + STATS.get(statPercent) / 100.0) + 
                        STATS.get(finalStat)) - totalStat;
                double percentStatToStat = ((STATS.get(baseStat) * (1 + STATS.get("ap%")/ 100.0) + STATS.get(bonusStat)) * (1 + (STATS.get(statPercent) + 1) / 100.0) + 
                        STATS.get(finalStat)) - totalStat;
                double totalStatsAttack = 0;
                String [] primaryStats = MapleInfo.primaryStat(job);
                for(String s : primaryStats){
                    totalStatsAttack = totalStatsAttack + statToValue(s);
                }
                String [] secondaryStat = MapleInfo.secondaryStat(job);
                if(secondaryStat != null){
                    for(String s : secondaryStat){
                        totalStatsAttack = totalStatsAttack + statToValue(s) / 4.0;
                    }
                }
                double statsToAtt1;
                double statsToAtt2;
                double statsToAtt;
                if(weaponMult != 0 && getDamageRangeMax() != 0){
                    statsToAtt1 = getDamageRangeMax() / (0.01 * weaponMult * statValue() * (1 + STATS.get("bonusDamage") / 100.0) * (1 + STATS.get("finalDamage") / 100.0));
                    if(MapleInfo.isMagicAtt(job)){
                        statsToAtt2 = statsToAtt1 / (1 + STATS.get("magicAtt%") / 100.0);
                    } else {
                        statsToAtt2 = statsToAtt1 / (1 + STATS.get("att%") / 100.0);
                    }
                    statsToAtt = totalStatsAttack / statsToAtt2;
                } else {
                    statsToAtt = 0;
                }
                comparisons.add("1 " + primaryStat.toUpperCase() + " = " + df.format(statToStat) + " " + primaryStat.toUpperCase());
                comparisons.add("1% " + primaryStat.toUpperCase() + " = " + df.format(percentStatToStat) + " " + primaryStat.toUpperCase());
                if(MapleInfo.isMagicAtt(job)){
                    comparisons.add("1 Magic Att = " + df.format(statsToAtt) + " " + primaryStat.toUpperCase());
                } else {
                    comparisons.add("1 Att = " + df.format(statsToAtt) + " " + primaryStat.toUpperCase());
                }
            }
        }
        
        return comparisons.toArray(new String[0]);
    } 
    
    public int getStats(String stat){
        return STATS.get(stat);
    }
    
    public String getJob(){
        return job;
    }
    
    public ArrayList<String> getInnerAbility(){
        return innerAbility;
    }
    
    public void setIgnoreDef(double amt){
        ignoreDef = amt;
    }
    
    public void setAddCritDamage(double amt){
        critDamage = critDamage + amt;
    }
    
    public void resolveAIOther(String AI, int amount, int addOrRm){
        if(AI.equals("DEX to STR %")){
            additiveStatsMap("finalStr", (int)(STATS.get("baseDex") * (amount / 100.0) * addOrRm));
        } else if (AI.equals("INT to LUK %")){
            additiveStatsMap("finalLuk", (int)(STATS.get("baseInt") * (amount / 100.0) * addOrRm));
        } else if(AI.equals("LUK to DEX %")){
            additiveStatsMap("finalDex", (int)(STATS.get("baseLuk") * (amount / 100.0) * addOrRm));
        } else if(AI.equals("STR to DEX %")){
            additiveStatsMap("finalDex", (int)(STATS.get("baseStr") * (amount / 100.0) * addOrRm));
        } else if (AI.equals("Weapon Att By Level")){
            additiveStatsMap("finalAtt", (level / amount) * addOrRm);
        } else if (AI.equals("Magic Att By Level")){
            additiveStatsMap("finalMagicAtt", (level / amount) * addOrRm);
        } else if (AI.equals("Attack Speed")){
            additiveStatsMap("attackSpeed", amount * (-addOrRm));
        }
    }
    
    public double ignoreDefChange(double stat, double amount, int incOrDec){
        double change = stat;
        if(incOrDec == 0){
            change = -((-(stat / 100.0 - 1) / (1 - amount / 100.0)) - 1) * 100;
        } else if(incOrDec == 1){
            change = 100 * (1 - (1 - stat / 100.0) * (1 - amount / 100.0)); 
        }
        return change;
    }
    
    public void insertIntoStatsMap(String key, int value){
        STATS.put(key, value);
    }
    
    public void additiveStatsMap(String key, int amount){
        STATS.put(key, STATS.get(key) + amount);
    }
    
    public void mapDiffUpdate(HashMap<String, Integer> old, HashMap<String, Integer> update){
        for(Map.Entry<String, Integer> entry : update.entrySet()){
            String key = entry.getKey();
            if(old.containsKey(key)){
                additiveStatsMap(key, update.get(key) - old.get(key));
            } else {
                additiveStatsMap(key, update.get(key));
            }
        }
        for(Map.Entry<String, Integer> entry : old.entrySet()){
            String key = entry.getKey();
            if(!update.containsKey(key)){
                additiveStatsMap(key, -old.get(key));
            }
        }
    }
    
    private void prepareMaps(){
        STATS.put("att", 0);
        STATS.put("att%", 0);
        STATS.put("finalAtt", 0);
        STATS.put("magicAtt", 0);
        STATS.put("magicAtt%", 0);
        STATS.put("finalMagicAtt", 0);
        STATS.put("baseStr", 4);
        STATS.put("baseDex", 4);
        STATS.put("baseInt", 4);
        STATS.put("baseLuk", 4);
        STATS.put("bonusStr", 0);
        STATS.put("bonusDex", 0);
        STATS.put("bonusInt", 0);
        STATS.put("bonusLuk", 0);
        STATS.put("str%", 0);
        STATS.put("dex%", 0);
        STATS.put("int%", 0);
        STATS.put("luk%", 0);
        STATS.put("finalStr", 0);
        STATS.put("finalDex", 0);
        STATS.put("finalInt", 0);
        STATS.put("finalLuk", 0);
        STATS.put("ap%", 0);
        STATS.put("baseHp", 0);
        STATS.put("bonusHp", 0);
        STATS.put("hp%", 0);
        STATS.put("finalHp", 0);
        STATS.put("baseMp", 0);
        STATS.put("bonusMp", 0);
        STATS.put("mp%", 0);
        STATS.put("finalMp", 0);
        STATS.put("finalDamage", 0);
        STATS.put("bonusDamage", 0);
        STATS.put("bossDamage", 0);
        STATS.put("critRate", 5);
        STATS.put("statusResist", 0);
        STATS.put("knockbackResist", 0);
        STATS.put("addedDefense", 0);
        STATS.put("def%", 0);
        STATS.put("speed", 100);
        STATS.put("jump", 100);
        STATS.put("speedCap", 140);
        STATS.put("jumpCap", 120);
        STATS.put("arcanePower", 0);
        STATS.put("dropRate", 0);
        STATS.put("mesoRate", 0);
        STATS.put("elementalResist", 0);
        STATS.put("ignoreElementalDef", 0);
        STATS.put("ignoreDamage", 0);
        STATS.put("hp", 0);
        STATS.put("mp", 0);
        STATS.put("attackSpeed", 0);
        STATS.put("attackSpeedCap", 2);
        STATS.put("starForce", 0);
        STATS.put("buffDuration", 0);
        STATS.put("equipMp", 0);
        STATS.put("equipHp", 0);
        STATS.put("statsAp", 0);
        STATS.put("willpower", 0);
        STATS.put("empathy", 0);
        STATS.put("ambition", 0);
        STATS.put("insight", 0);
        STATS.put("hyperStr", 0);
        STATS.put("hyperDex", 0);
        STATS.put("hyperInt", 0);
        STATS.put("hyperLuk", 0);
        STATS.put("hyperHp", 0);
        STATS.put("hyperMp", 0);
        STATS.put("hyperSpeed", 0);
        STATS.put("hyperJump", 0);
        STATS.put("hyperCritRate", 0);
        STATS.put("hyperCritDamg", 0);
        STATS.put("hyperDamg", 0);
        STATS.put("hyperBossDamg", 0);
        STATS.put("hyperIgnoreDef", 0);
        STATS.put("hyperKnockback", 0);
        STATS.put("hyperEleResist", 0);
        STATS.put("hyperStatusResist", 0);
        EQUIPS.put("ring1", null);
        EQUIPS.put("ring2", null);
        EQUIPS.put("ring3", null);
        EQUIPS.put("ring4", null);
        EQUIPS.put("pocket", null);
        EQUIPS.put("codex", null);
        EQUIPS.put("pendant1", null);
        EQUIPS.put("pendant2", null);
        EQUIPS.put("weapon", null);
        EQUIPS.put("belt", null);
        EQUIPS.put("hat", null);
        EQUIPS.put("faceAccessory", null);
        EQUIPS.put("eyeAccessory", null);
        EQUIPS.put("top", null);
        EQUIPS.put("bottom", null);
        EQUIPS.put("shoes", null);
        EQUIPS.put("emblem", null);
        EQUIPS.put("badge", null);
        EQUIPS.put("medal", null);
        EQUIPS.put("subWeapon", null);
        EQUIPS.put("cape", null);
        EQUIPS.put("heart", null);
        EQUIPS.put("android", null);
        EQUIPS.put("gloves", null);
        EQUIPS.put("shoulder", null);
        EQUIPS.put("earrings", null);
        EQUIPS.put("title", null);
        EQUIPS.put("totem1", null);
        EQUIPS.put("totem2", null);
        EQUIPS.put("totem3", null);
    }
    
    public void save(){
        //Save When Done
        /*  File newCharFile = new File("./Characters/" + name.getText() + ".txt");
            try (PrintWriter pw = new PrintWriter(newCharFile)) {
                pw.println(name.getText());
                pw.flush();
                pw.println(level.getText());
                pw.flush();
                pw.println((String)job.getSelectedItem());
                pw.flush();
            } catch (FileNotFoundException ex){
                JOptionPane.showMessageDialog(infoFrame, "An Error Occurred", "Error", JOptionPane.ERROR_MESSAGE, null);
                return;
            }*/
    }
    
    private static void load(){
        //Remove static when done
    }
    
}
