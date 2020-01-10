import java.util.ArrayList;
import java.util.Arrays;

public class MapleInfo {

    public static String [] allJobs() {
        return new String[] {"-", "Angelic Buster", "Aran", "Arch Mage (Fire, Poison)", "Arch Mage (Ice, Lightning)",
                "Ark", "Battle Mage", "Beast Tamer", "Bishop", "Blaster", "Blaze Wizard", "Bowmaster", "Buccaneer",
                "Cadena", "Cannoneer", "Corsair", "Dark Knight", "Dawn Warrior", "Demon Slayer", "Dual Blade",
                "Evan", "Hayato", "Hero", "Illium", "Jett", "Kanna", "Kaiser", "Kinesis", "Luminous", "Marksman",
                "Mechanic", "Mercedes", "Mihile", "Night Lord", "Night Walker", "Paladin", "Phantom", "Shade",
                "Shadower", "Thunder Breaker", "Wild Hunter", "Wind Archer", "Xenon", "Zero"};
    }

    public static String [] allWeapons() {
        return new String[] {"-", "Arm Cannon", "Bow", "Cane", "Cannon", "Chain", "Claw", "Crossbow",
                "Dagger", "Dual Bowguns", "Fan", "Great Sword (Lapis)", "Gun", "Katana", "Katara", "Knuckle",
                "Long Sword (Lazuli)", "Lucent Gauntlet", "One-handed Axe", "One-handed Blunt Weapon",
                "One-handed Sword", "Pole Arm", "Psy-limiter", "Scepter", "Shining Rod", "Soul Shooter", "Spear",
                "Staff", "Two-handed Axe", "Two-handed Blunt Weapon", "Two-handed Sword", "Wand", "Whip Blade"};
    }

    public static double weaponToMastery(String weapon, String job) {
        switch(weapon) {
            case "Claw":
                return 1.75;
            case "Knuckle":
            case "Soul Shooter":
            case "Arm Cannon":
                return 1.7;
            case "Gun":
            case "Cannon":
                return 1.5;
            case "Spear":
            case "Pole Arm":
            case "Great Sword (Lapis)":
                return 1.49;
            case "Crossbow":
            case "Fan":
                return 1.35;
            case "Two-handed Axe":
            case "Two-handed Sword":
                if(job.equals("Hero")) {
                    return 1.44;
                }
                return 1.34;
            case "Two-handed Blunt Weapon":
            case "Long Sword (Lazuli)":
            case "Scepter":
                return 1.34;
            case "Whip Blade":
                return 1.3125;
            case "Bow":
            case "Dagger":
            case "Katara":
            case "Dual Bowguns":
            case "Cane":
            case "Chain":
                return 1.3;
            case "Katana":
                return 1.25;
            case "One-handed Axe":
            case "One-handed Sword":
                if(job.equals("Hero")) {
                    return 1.3;
                }
                return 1.2;
            case "One-handed Blunt Weapon":
            case "Shining Rod":
            case "Psy-limiter":
            case "Lucent Gauntlet":
                return 1.2;
            case "Wand":
            case "Staff":
                if(job.equals("Blaze Wizard") || job.equals("Arch Mage (Fire, Poison)") ||
                        job.equals("Arch Mage (Ice, Lightning)") || job.equals("Bishop")) {
                    return 1.2;
                }
                return 1;
            default:
                return 0;
        }
    }

    public static String [] statsBasedOnJob(String job) {
        switch (job) {
            case "Battle Mage":
            case "Beast Tamer":
            case "Blaze Wizard":
            case "Evan":
            case "Kanna":
            case "Luminous":
            case "Bishop":
            case "Arch Mage (Fire, Poison)":
            case "Arch Mage (Ice, Lightning)":
            case "Kinesis":
            case "Illium":
                return new String[] {"INT", "LUK"};
            case "Bowmaster":
            case "Marksman":
            case "Wild Hunter":
            case "Wind Archer":
            case "Mercedes":
            case "Angelic Buster":
            case "Jett":
            case "Corsair":
            case "Mechanic":
                return new String[] {"DEX", "STR"};
            case "Dual Blade":
            case "Shadower":
            case "Cadena":
                return new String[] {"LUK", "STR", "DEX"};
            case "Night Walker":
            case "Phantom":
            case "Night Lord":
                return new String[] {"LUK", "DEX"};
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
                return new String[] {"STR", "DEX"};
            case "Xenon":
                return new String[] {"STR", "DEX", "LUK"};
            default:
                return new String[] {};
        }
    }

    public static boolean isPrimaryStat(String job, String stat) {
        if(stat.equals("STR")) {
            switch(job) {
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
                case "Xenon":
                    return true;
                default:
                    return false;
            }
        } else if(stat.equals("DEX")) {
            switch (job) {
                case "Bowmaster":
                case "Marksman":
                case "Wild Hunter":
                case "Wind Archer":
                case "Mercedes":
                case "Angelic Buster":
                case "Jett":
                case "Corsair":
                case "Mechanic":
                case "Xenon":
                    return true;
                default:
                    return false;
            }
        } else if(stat.equals("INT")) {
            switch(job) {
                case "Battle Mage":
                case "Beast Tamer":
                case "Blaze Wizard":
                case "Evan":
                case "Kanna":
                case "Luminous":
                case "Bishop":
                case "Arch Mage (Fire, Poison)":
                case "Arch Mage (Ice, Lightning)":
                case "Kinesis":
                case "Illium":
                    return true;
                default:
                    return false;
            }
        } else {
            switch (job) {
                case "Dual Blade":
                case "Shadower":
                case "Cadena":
                case "Night Walker":
                case "Phantom":
                case "Night Lord":
                case "Xenon":
                    return true;
                default:
                    return false;
            }
        }
    }

    public static String[] statTypes() {
        return new String[] {"-", "STR", "STR %", "DEX", "DEX %", "INT", "INT %", "LUK", "LUK %", "All Stat",
                "All Stat %", "Attack", "Attack %", "Damage %", "Boss Damage %", "Critical Damage %"};
    }

    public static double calculateMultiplicative(ArrayList<Integer> sources) {
        if(sources.size() == 0) {
            return 0;
        }
        double total = 0;
        for(Integer source : sources) {
            if(total == 0) {
                total = source;
            } else {
                total = 100 * (1 - (1 - total / 100.0) * (1 - source / 100.0));
            }
        }
        return total;
    }

    public static double calculateOneStatOrPercent(MapleCharacter character, int num, String option) {
        int ap;
        int apPercent = character.getApPercent();
        int statPercent;
        int finalStat;
        int totalStat;
        if (num == 0) {
            ap = character.getAp1();
            statPercent = character.getStatPercent1();
            finalStat = character.getFinalStat1();
            totalStat = character.getTotalStat1();
        } else if (num == 1) {
            ap = character.getAp2();
            statPercent = character.getStatPercent2();
            finalStat = character.getFinalStat2();
            totalStat = character.getTotalStat2();
        } else {
            ap = character.getAp3();
            statPercent = character.getStatPercent3();
            finalStat = character.getFinalStat3();
            totalStat = character.getTotalStat3();
        }
        double baseStat = (totalStat - finalStat) / (1 + statPercent / 100.0) - (ap * (1 + apPercent / 100.0));

        if (option.equals("Stat")) {
            return (1 + ap * (1 + apPercent / 100.0) + baseStat) * ((1 + statPercent / 100.0)) + finalStat - totalStat;
        } else {
            return ((ap * (1 + apPercent / 100.0) + baseStat) * (1 + (1 + statPercent / 100.0)) + finalStat -
                    totalStat) / 100;
        }
    }

    public static double calculateAttackStat(MapleCharacter character, int num) {
        double baseAttack = calculateBaseAttack(character);
        if(character.getJob().equals("Xenon")) {
            return (character.getTotalStat1() + character.getTotalStat2() + character.getTotalStat3()) / baseAttack;
        }

        double attack;
        if(statsBasedOnJob(character.getJob()).length == 3) {
            attack = (character.getTotalStat1() + character.getTotalStat2() / 4 + character.getTotalStat3() / 4) /
                    baseAttack;
        } else {
            attack = (character.getTotalStat1() + character.getTotalStat2() / 4) / baseAttack;
        }
        if(num == 0) {
            return attack;
        } else {
            return attack * 4;
        }
    }

    public static double calculateBaseAttack(MapleCharacter character) {
        int statVal;
        if(character.getJob().equals("Xenon")) {
            statVal = character.getTotalStat1() * 4 + character.getTotalStat2() * 4 + character.getTotalStat3() * 4;
        } else if(statsBasedOnJob(character.getJob()).length == 3) {
            statVal = character.getTotalStat1() * 4 + character.getTotalStat2() + character.getTotalStat3();
        } else {
            statVal = character.getTotalStat1() * 4 + character.getTotalStat2();
        }
        return (character.getMaxRange() / (0.01 * weaponToMastery(character.getWeapon(), character.getJob()) * statVal *
                (1 + character.getDamgPercent() / 100.0) *
                (1 + calculateMultiplicative(character.getFinalAtk()) / 100.0))) /
                (1 + character.getAtkPercent() / 100.0);
    }

    public static double calculateStatComparison(MapleCharacter character, int row) {
        String amount = character.getComparisons()[row][0];
        String from = character.getComparisons()[row][1];
        String to = character.getComparisons()[row][2];

        if(amount.equals("-") || from.equals("-") || to.equals("-")) {
            return 0;
        }

        ArrayList<String> stats = new ArrayList<>(Arrays.asList(statsBasedOnJob(character.getJob())));
        ArrayList<String> negativeStats = new ArrayList<>(Arrays.asList("STR", "DEX", "INT", "LUK"));
        negativeStats.removeAll(stats);
        for(String stat : negativeStats) {
            if(from.contains(stat) || to.contains(stat)) {
                return 0;
            }
        }
        if(from.equals(to)) {
            return Double.parseDouble(amount);
        }

        double comparedNew = comparisonAmountAndOption(character, stats, Integer.parseInt(amount), from);
        double comparedCurrent = comparisonAmountAndOption(character, stats, 0, from);
        double equiCurrent = comparisonAmountAndOption(character, stats, 0, to);
        double equi = comparedNew / comparedCurrent * equiCurrent - equiCurrent;

        if(to.equals("Attack %") || to.equals("Damage %") || to.equals("Boss Damage %") ||
                to.equals("Critical Damage %")) {
            equi = equi * 100;
        }

        double divide = 1;
        for(int i = 0; i < stats.size(); i++) {
            if(to.equals(stats.get(i))) {
                if(character.getJob().equals("Xenon") || i == 0) {
                    divide = calculateOneStatOrPercent(character, i, "Stat");
                } else {
                    if(stats.size() == 2) {
                        divide = calculateOneStatOrPercent(character, i, "Stat") /
                                calculateOneStatOrPercent(character, 0, "Stat");
                    } else {
                        divide = (calculateOneStatOrPercent(character, 1, "Stat") +
                                calculateOneStatOrPercent(character, 2, "Stat")) /
                                calculateOneStatOrPercent(character, 0, "Stat");
                    }
                }
            } else if(to.equals(stats.get(i) + " %")) {
                if(character.getJob().equals("Xenon") || i == 0) {
                    divide = calculateOneStatOrPercent(character, i, "Percent");
                } else {
                    divide = calculateOneStatOrPercent(character, i, "Percent") / 4.0;
                }
            }
        }
        if(to.equals("All Stat") || to.equals("All Stat %")) {
            String statOrPercent;
            if(to.equals("All Stat")) {
                statOrPercent = "Stat";
            } else {
                statOrPercent = "Percent";
            }
            if(character.getJob().equals("Xenon")) {
                divide = calculateOneStatOrPercent(character, 0, statOrPercent) +
                        calculateOneStatOrPercent(character, 1, statOrPercent) +
                        calculateOneStatOrPercent(character, 2, statOrPercent);
            } else {
                if(stats.size() == 2) {
                    divide = calculateOneStatOrPercent(character, 0, statOrPercent) +
                            calculateOneStatOrPercent(character, 1, statOrPercent) / 4.0;
                } else {
                    divide = calculateOneStatOrPercent(character, 0, statOrPercent) +
                            calculateOneStatOrPercent(character, 1, statOrPercent) / 4.0 +
                            calculateOneStatOrPercent(character, 2, statOrPercent) / 4.0;
                }
            }
        }

        return equi / divide;
    }

    private static double comparisonAmountAndOption(MapleCharacter character, ArrayList<String> stats,
                                                    int amount, String fromOrTo) {
        for(int i = 0; i < stats.size(); i++) {
            if(fromOrTo.equals(stats.get(i)) || fromOrTo.equals(stats.get(i) + " %")) {
                String statOrPercent;
                if(fromOrTo.equals(stats.get(i))) {
                    statOrPercent = "Stat";
                } else {
                    statOrPercent = "Percent";
                }
                if(character.getJob().equals("Xenon")) {
                    return character.getTotalStat1() + character.getTotalStat2() + character.getTotalStat3() +
                            amount * calculateOneStatOrPercent(character, i, statOrPercent);
                } else {
                    if(isPrimaryStat(character.getJob(), stats.get(i))) {
                        if(stats.size() == 2) {
                            return character.getTotalStat1() + character.getTotalStat2() / 4.0 + amount *
                                    calculateOneStatOrPercent(character, i, statOrPercent);
                        } else {
                            return character.getTotalStat1() +
                                    (character.getTotalStat2() + character.getTotalStat3()) / 4.0 + amount *
                                    calculateOneStatOrPercent(character, i, statOrPercent);
                        }
                    } else {
                        if(stats.size() == 2) {
                            return character.getTotalStat1() + character.getTotalStat2() / 4.0 + amount *
                                    (calculateOneStatOrPercent(character, i, statOrPercent) / 4.0);
                        } else {
                            return character.getTotalStat1() +
                                    (character.getTotalStat2() + character.getTotalStat3()) / 4.0 + amount *
                                    calculateOneStatOrPercent(character, i, statOrPercent) / 4.0;
                        }
                    }
                }
            }
        }
        if(fromOrTo.equals("All Stat") || fromOrTo.equals("All Stat %")) {
            String statOrPercent;
            if(fromOrTo.equals("All Stat")) {
                statOrPercent = "Stat";
            } else {
                statOrPercent = "Percent";
            }
            if(character.getJob().equals("Xenon")) {
                return character.getTotalStat1() + character.getTotalStat2() + character.getTotalStat3() + amount *
                        calculateOneStatOrPercent(character, 0, statOrPercent) + amount *
                        calculateOneStatOrPercent(character, 1, statOrPercent) + amount *
                        calculateOneStatOrPercent(character, 2, statOrPercent);
            } else {
                if(stats.size() == 2) {
                    return character.getTotalStat1() + character.getTotalStat2() / 4.0 + amount *
                            calculateOneStatOrPercent(character, 0, statOrPercent) + amount *
                            calculateOneStatOrPercent(character, 1, statOrPercent) / 4.0;
                } else {
                    return character.getTotalStat1() + (character.getTotalStat2() + character.getTotalStat3()) / 4.0 +
                            amount * calculateOneStatOrPercent(character, 0, statOrPercent) + amount *
                            calculateOneStatOrPercent(character, 1, statOrPercent) / 4.0 + amount *
                            calculateOneStatOrPercent(character, 2, statOrPercent) / 4.0;
                }

            }
        } else if(fromOrTo.equals("Attack")) {
            return amount + calculateBaseAttack(character);
        } else if(fromOrTo.equals("Attack %")) {
            return (amount + character.getAtkPercent() + 100) / 100.0;
        } else if(fromOrTo.equals("Damage %") || fromOrTo.equals("Boss Damage %")) {
            return (amount + character.getDamgPercent() + character.getBossPercent() + 100) / 100.0;
        } else {
            return (amount + character.getCritDPercent() + 135) / 100.0;
        }
    }

}
