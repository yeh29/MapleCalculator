import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MapleCharacter {

    private String name;
    private int level;
    private String job;
    private String weapon;
    private int minRange;
    private int maxRange;
    private int apPercent;
    private int atkPercent;
    private int damgPercent;
    private double critDPercent;
    private int bossPercent;
    private int totalStat1;
    private int finalStat1;
    private int statPercent1;
    private int ap1;
    private int totalStat2;
    private int finalStat2;
    private int statPercent2;
    private int ap2;
    private int totalStat3;
    private int finalStat3;
    private int statPercent3;
    private int ap3;
    private ArrayList<Integer> ignoreDef;
    private ArrayList<Integer> finalAtk;

    private String[][] comparisons;
    private ArrayList<ArrayList<MapleItem>> inventory;

    public MapleCharacter() {
        name = "Default";
        level = 1;
        job = "-";
        weapon = "-";
        minRange = 0;
        maxRange = 0;
        apPercent = 0;
        atkPercent = 0;
        damgPercent = 0;
        critDPercent = 0;
        bossPercent = 0;
        totalStat1 = 4;
        finalStat1 = 0;
        statPercent1 = 0;
        ap1 = 4;
        totalStat2 = 4;
        finalStat2 = 0;
        statPercent2 = 0;
        ap2 = 4;
        totalStat3 = 4;
        finalStat3 = 0;
        statPercent3 = 0;
        ap3 = 4;
        ignoreDef = new ArrayList<>();
        finalAtk = new ArrayList<>();
        comparisons = new String[5][3];
        for(String[] row : comparisons) {
            Arrays.fill(row, "-");
        }
        inventory = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            inventory.add(new ArrayList<>());
        }
    }

    public MapleCharacter(String file) {
        load(file);
    }

    private void load(String file) {
        ignoreDef = new ArrayList<>();
        finalAtk = new ArrayList<>();
        comparisons = new String[5][3];
        inventory = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            inventory.add(new ArrayList<>());
        }
        File load = new File("./Characters/" + file + ".txt");
        try (BufferedReader br = new BufferedReader(new FileReader(load))) {
            name = br.readLine();
            level = Integer.parseInt(br.readLine());
            job = br.readLine();
            weapon = br.readLine();
            minRange = Integer.parseInt(br.readLine());
            maxRange = Integer.parseInt(br.readLine());
            apPercent = Integer.parseInt(br.readLine());
            atkPercent = Integer.parseInt(br.readLine());
            damgPercent = Integer.parseInt(br.readLine());
            critDPercent = Double.parseDouble(br.readLine());
            bossPercent = Integer.parseInt(br.readLine());
            totalStat1 = Integer.parseInt(br.readLine());
            finalStat1 = Integer.parseInt(br.readLine());
            statPercent1 = Integer.parseInt(br.readLine());
            ap1 = Integer.parseInt(br.readLine());
            totalStat2 = Integer.parseInt(br.readLine());
            finalStat2 = Integer.parseInt(br.readLine());
            statPercent2 = Integer.parseInt(br.readLine());
            ap2 = Integer.parseInt(br.readLine());
            totalStat3 = Integer.parseInt(br.readLine());
            finalStat3 = Integer.parseInt(br.readLine());
            statPercent3 = Integer.parseInt(br.readLine());
            ap3 = Integer.parseInt(br.readLine());
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Ignore Def>")) {
                    break;
                } else {
                    ignoreDef.add(Integer.parseInt(line));
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Final Atk>")) {
                    break;
                } else {
                    finalAtk.add(Integer.parseInt(line));
                }
            }
            for(int i = 0; i < comparisons.length; i++) {
                for(int j = 0; j < comparisons[i].length; j++) {
                    comparisons[i][j] = br.readLine();
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Equip>")) {
                    break;
                } else {
                    String[] item = new String[7];
                    item[0] = line;
                    for(int i = 1; i < 7; i++) {
                        item[i] = br.readLine();
                    }
                    inventory.get(0).add(new MapleItem(item[0], item[1], Integer.parseInt(item[2]),
                            Boolean.parseBoolean(item[3]), item[4], item[5], item[6]));
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Use>")) {
                    break;
                } else {
                    String[] item = new String[7];
                    item[0] = line;
                    for(int i = 1; i < 7; i++) {
                        item[i] = br.readLine();
                    }
                    inventory.get(1).add(new MapleItem(item[0], item[1], Integer.parseInt(item[2]),
                            Boolean.parseBoolean(item[3]), item[4], item[5], item[6]));
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Etc>")) {
                    break;
                } else {
                    String[] item = new String[7];
                    item[0] = line;
                    for(int i = 1; i < 7; i++) {
                        item[i] = br.readLine();
                    }
                    inventory.get(2).add(new MapleItem(item[0], item[1], Integer.parseInt(item[2]),
                            Boolean.parseBoolean(item[3]), item[4], item[5], item[6]));
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Setup>")) {
                    break;
                } else {
                    String[] item = new String[7];
                    item[0] = line;
                    for(int i = 1; i < 7; i++) {
                        item[i] = br.readLine();
                    }
                    inventory.get(3).add(new MapleItem(item[0], item[1], Integer.parseInt(item[2]),
                            Boolean.parseBoolean(item[3]), item[4], item[5], item[6]));
                }
            }
            while (true) {
                String line = br.readLine();
                if(line.equals("<End Cash>")) {
                    break;
                } else {
                    String[] item = new String[7];
                    item[0] = line;
                    for(int i = 1; i < 7; i++) {
                        item[i] = br.readLine();
                    }
                    inventory.get(4).add(new MapleItem(item[0], item[1], Integer.parseInt(item[2]),
                            Boolean.parseBoolean(item[3]), item[4], item[5], item[6]));
                }
            }
        } catch (IOException ex){
        }
    }

    public void save() {
        File save = new File("./Characters/" + name + ".txt");
        try (PrintWriter pw = new PrintWriter(save)) {
            pw.println(name);
            pw.println(level);
            pw.println(job);
            pw.println(weapon);
            pw.println(minRange);
            pw.println(maxRange);
            pw.println(apPercent);
            pw.println(atkPercent);
            pw.println(damgPercent);
            pw.println(critDPercent);
            pw.println(bossPercent);
            pw.println(totalStat1);
            pw.println(finalStat1);
            pw.println(statPercent1);
            pw.println(ap1);
            pw.println(totalStat2);
            pw.println(finalStat2);
            pw.println(statPercent2);
            pw.println(ap2);
            pw.println(totalStat3);
            pw.println(finalStat3);
            pw.println(statPercent3);
            pw.println(ap3);
            for(int stat : ignoreDef) {
                pw.println(stat);
            }
            pw.println("<End Ignore Def>");
            for(int stat : finalAtk) {
                pw.println(stat);
            }
            pw.println("<End Final Atk>");
            for(String[] subStat : comparisons) {
                for(String stat : subStat) {
                    pw.println(stat);
                }
            }
            for(MapleItem equip : inventory.get(0)) {
                pw.println(equip.getName());
                pw.println(equip.getDesc());
                pw.println(equip.getId());
                pw.println(equip.isCash());
                pw.println(equip.getOverallCategory());
                pw.println(equip.getCatetory());
                pw.println(equip.getSubCategory());
            }
            pw.println("<End Equip>");
            for(MapleItem use : inventory.get(1)) {
                pw.println(use.getName());
                pw.println(use.getDesc());
                pw.println(use.getId());
                pw.println(use.isCash());
                pw.println(use.getOverallCategory());
                pw.println(use.getCatetory());
                pw.println(use.getSubCategory());
            }
            pw.println("<End Use>");
            for(MapleItem etc : inventory.get(2)) {
                pw.println(etc.getName());
                pw.println(etc.getDesc());
                pw.println(etc.getId());
                pw.println(etc.isCash());
                pw.println(etc.getOverallCategory());
                pw.println(etc.getCatetory());
                pw.println(etc.getSubCategory());
            }
            pw.println("<End Etc>");
            for(MapleItem setup : inventory.get(3)) {
                pw.println(setup.getName());
                pw.println(setup.getDesc());
                pw.println(setup.getId());
                pw.println(setup.isCash());
                pw.println(setup.getOverallCategory());
                pw.println(setup.getCatetory());
                pw.println(setup.getSubCategory());
            }
            pw.println("<End Setup>");
            for(MapleItem cash : inventory.get(4)) {
                pw.println(cash.getName());
                pw.println(cash.getDesc());
                pw.println(cash.getId());
                pw.println(cash.isCash());
                pw.println(cash.getOverallCategory());
                pw.println(cash.getCatetory());
                pw.println(cash.getSubCategory());
            }
            pw.println("<End Cash>");
            pw.flush();
        } catch (FileNotFoundException ex){
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getJob() {
        return job;
    }

    public String getWeapon() {
        return weapon;
    }

    public long getMinRange() {
        return minRange;
    }

    public long getMaxRange() {
        return maxRange;
    }

    public int getApPercent() {
        return apPercent;
    }

    public int getAtkPercent() {
        return atkPercent;
    }

    public int getDamgPercent() {
        return damgPercent;
    }

    public double getCritDPercent() {
        return critDPercent;
    }

    public int getBossPercent() {
        return bossPercent;
    }

    public int getTotalStat1() {
        return totalStat1;
    }

    public int getFinalStat1() {
        return finalStat1;
    }

    public int getStatPercent1() {
        return statPercent1;
    }

    public int getAp1() {
        return ap1;
    }

    public int getTotalStat2() {
        return totalStat2;
    }

    public int getFinalStat2() {
        return finalStat2;
    }

    public int getStatPercent2() {
        return statPercent2;
    }

    public int getAp2() {
        return ap2;
    }

    public int getTotalStat3() {
        return totalStat3;
    }

    public int getFinalStat3() {
        return finalStat3;
    }

    public int getStatPercent3() {
        return statPercent3;
    }

    public int getAp3() {
        return ap3;
    }

    public ArrayList<Integer> getIgnoreDef() {
        return ignoreDef;
    }

    public ArrayList<Integer> getFinalAtk() {
        return finalAtk;
    }

    public String[][] getComparisons() {
        return comparisons;
    }

    public ArrayList<ArrayList<MapleItem>> getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setMinRange(int minRange) {
        this.minRange = minRange;
    }

    public void setMaxRange(int maxRange) {
        this.maxRange = maxRange;
    }

    public void setApPercent(int apPercent) {
        this.apPercent = apPercent;
    }

    public void setAtkPercent(int atkPercent) {
        this.atkPercent = atkPercent;
    }

    public void setDamgPercent(int damgPercent) {
        this.damgPercent = damgPercent;
    }

    public void setCritDPercent(double critDPercent) {
        this.critDPercent = critDPercent;
    }

    public void setBossPercent(int bossPercent) {
        this.bossPercent = bossPercent;
    }

    public void setTotalStat1(int totalStat1) {
        this.totalStat1 = totalStat1;
    }

    public void setFinalStat1(int finalStat1) {
        this.finalStat1 = finalStat1;
    }

    public void setStatPercent1(int statPercent1) {
        this.statPercent1 = statPercent1;
    }

    public void setAp1(int ap1) {
        this.ap1 = ap1;
    }

    public void setTotalStat2(int totalStat2) {
        this.totalStat2 = totalStat2;
    }

    public void setFinalStat2(int finalStat2) {
        this.finalStat2 = finalStat2;
    }

    public void setStatPercent2(int statPercent2) {
        this.statPercent2 = statPercent2;
    }

    public void setAp2(int ap2) {
        this.ap2 = ap2;
    }

    public void setTotalStat3(int totalStat3) {
        this.totalStat3 = totalStat3;
    }

    public void setFinalStat3(int finalStat3) {
        this.finalStat3 = finalStat3;
    }

    public void setStatPercent3(int statPercent3) {
        this.statPercent3 = statPercent3;
    }

    public void setAp3(int ap3) {
        this.ap3 = ap3;
    }

}
