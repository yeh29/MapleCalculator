import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.util.HashMap;

public class MapleCalculator {
    
    private final JFrame MAIN_FRAME;
    private DecimalFormat df;
    private MapleCharacter loadedChar;
    private boolean compareMode;
    private boolean saved;
    private int mapleVersion;
    
    public MapleCalculator(){
        MAIN_FRAME = new JFrame("Maplestory Calculator");
        df = new DecimalFormat("0.00");
        MAIN_FRAME.setSize(500, 500);
        MAIN_FRAME.setLayout(new FlowLayout());
        MAIN_FRAME.setResizable(false);
        MAIN_FRAME.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //MAIN_FRAME.getContentPane().setBackground(Color.WHITE);
        loadedChar = null;
        compareMode = false;
        saved = false;
        mapleVersion = 201;
    }
    
    public static void main(String [] args){
        MapleCalculator main = new MapleCalculator();
        main.loadEmptyFolders();
        main.startScreen();
    }
    
    private void loadEmptyFolders(){
        File characters = new File("./Characters");
        if(!characters.exists())
            characters.mkdir();
        File icons = new File("./Icons");
        if(!icons.exists())
            icons.mkdir();
    }
    
    public void startScreen(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 70 , 70));
        JPanel newCharPanel = new JPanel (new GridLayout(1,1));
        JPanel existingCharPanel = new JPanel(new GridLayout(1,1));
        panel.setPreferredSize(new Dimension(400, 400));
        existingCharPanel.setPreferredSize(new Dimension(300, 100));
        newCharPanel.setPreferredSize(new Dimension(300, 100));  
        //panel.setBackground(Color.WHITE);
        
        JButton existingChar = new JButton("Existing Character");
        JButton newChar = new JButton("New Character");
        Font text = new Font("Serif", Font.BOLD, 20);
        existingChar.setFont(text);
        newChar.setFont(text);
        existingChar.addActionListener((ActionEvent e) -> {
            loadListOfChar();
        });
        newChar.addActionListener((ActionEvent e) -> {
            createNewChar();
        });
        
        //existingChar.setBackground(new Color(193, 22, 12));
        //existingChar.setForeground(Color.BLACK);
        
        MAIN_FRAME.add(panel);
        panel.add(existingCharPanel);
        panel.add(newCharPanel);
        existingCharPanel.add(existingChar);
        newCharPanel.add(newChar);
        
        MAIN_FRAME.setVisible(true);
    }
    
    private void createNewChar(){
        JFrame infoFrame = new JFrame("New Character");
        infoFrame.setSize(350 , 300);
        infoFrame.setLayout(new FlowLayout());
        infoFrame.setLocationRelativeTo(null);
        infoFrame.setResizable(false);
        infoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        infoFrame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                MAIN_FRAME.setEnabled(true);
                infoFrame.dispose();
            }
        });
        MAIN_FRAME.setEnabled(false);
        
        JPanel panel = new JPanel(new GridLayout(8,1));
        JLabel nameLabel = new JLabel("Name");
        JTextField name = new JTextField();
        JLabel levelLabel = new JLabel("Level");
        JTextField level = new JTextField();
        JLabel jobLabel = new JLabel("Job");
        String [] possibleJobs = MapleInfo.allJobs();
        JComboBox job = new JComboBox(possibleJobs);
        JButton createChar = new JButton("Create Character");

        infoFrame.add(panel);
        panel.add(nameLabel);
        panel.add(name);
        panel.add(levelLabel);
        panel.add(level);
        panel.add(jobLabel);
        panel.add(job);
        panel.add(Box.createRigidArea(new Dimension(0, 0)));
        panel.add(createChar);
        
        Font text = new Font("Serif", Font.BOLD, 22);
        nameLabel.setFont(text);
        levelLabel.setFont(text);
        jobLabel.setFont(text);
        
        createChar.addActionListener((ActionEvent e) -> {
            if(name.getText().length() == 0){
                JOptionPane.showMessageDialog(infoFrame, "Empty Name", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String [] invalidChars = {"/", "\\", ":", "*", "\"", "<", ">", "|", "?"};
            for(String currentChar : invalidChars){
                if(name.getText().contains(currentChar)){
                    JOptionPane.showMessageDialog(infoFrame, "Invalid Name", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            for (File names : new File("./Characters").listFiles()) {
                if (names.getName().equals(name.getText() + ".txt")) {
                    JOptionPane.showMessageDialog(infoFrame, "Name Taken", "Error", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            if(!level.getText().matches("[0-9]+")){
                JOptionPane.showMessageDialog(infoFrame, "Level Can Only Be 1 - 250", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(Integer.parseInt(level.getText()) < 1 || Integer.parseInt(level.getText()) > 250){
                JOptionPane.showMessageDialog(infoFrame, "Level Can Only Be 1 - 250", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            loadedChar = new MapleCharacter(name.getText(), Integer.parseInt(level.getText()), (String)job.getSelectedItem());
            MAIN_FRAME.setEnabled(true);
            infoFrame.dispose();
            loadedCharacterScreen();
        });
        
        infoFrame.setVisible(true);
    }
    
    private void loadListOfChar(){
        loadedChar = new MapleCharacter("Test", 220, "Mercedes");  
        //Load list and select one character
        //loadedChar = new MapleCalculator(); //Add name parameter
        //CHANGE: actually load list from .txt, make screen 
        loadedCharacterScreen();
    }
    
    private void loadedCharacterScreen(){
        MAIN_FRAME.getContentPane().removeAll();
        MAIN_FRAME.setSize(1100, 750);
        MAIN_FRAME.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        MAIN_FRAME.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                if(saved)
                    MAIN_FRAME.dispose();
                else{
                    int response = JOptionPane.showConfirmDialog(MAIN_FRAME, "Changes have not been saved. Quit Anyway?",
                            null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                    if(response == JOptionPane.YES_OPTION)
                        MAIN_FRAME.dispose();
                }
            }
        });
        MAIN_FRAME.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        MAIN_FRAME.add(Box.createRigidArea(new Dimension(1100, 670)));
        loadNavigation();
        MAIN_FRAME.repaint();
        MAIN_FRAME.revalidate();
    }
    
    private void loadNavigation(){
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 8));
        bar.setBorder(new MatteBorder(2, 0, 0, 0, Color.GRAY));
        bar.setPreferredSize(new Dimension (1100, 80));
        JButton home = new JButton("Home");
        JButton stats = new JButton("Stats");
        JButton equips = new JButton("Equips");
        JButton etcEquips = new JButton("Etc Equips");
        JButton linkSkills = new JButton("Link Skills");
        JButton skills = new JButton("Skills");
        JButton etcSkills = new JButton("Etc Skills");
        JButton legion = new JButton("Legion");
        JButton options = new JButton("Options");
        bar.add(home);
        bar.add(stats);
        bar.add(equips);
        bar.add(etcEquips);
        bar.add(linkSkills);
        bar.add(skills);
        bar.add(etcSkills);
        bar.add(legion);
        bar.add(options);
        MAIN_FRAME.add(bar);

        home.addActionListener((ActionEvent e) -> homeScreen());
        stats.addActionListener((ActionEvent e) -> statsScreen());
        equips.addActionListener((ActionEvent e) -> equipsScreen());
        etcEquips.addActionListener((ActionEvent e) -> etcEquipsScreen());
        linkSkills.addActionListener((ActionEvent e) -> linkSkillsScreen());
        skills.addActionListener((ActionEvent e) -> skillsScreen());
        etcSkills.addActionListener((ActionEvent e) -> etcSkillsScreen());
        legion.addActionListener((ActionEvent e) -> legionScreen());
        options.addActionListener((ActionEvent e) -> optionsScreen());
    }
    
    private void homeScreen(){
        
        MAIN_FRAME.getContentPane().removeAll();
        
        JPanel mainPanel = new JPanel(new GridLayout(1, 3));
        mainPanel.setPreferredSize(new Dimension(1100, 670));
        JPanel sub1 = new JPanel(new GridLayout(20, 1));
        JPanel sub2 = new JPanel(new GridLayout(20, 1));
        JPanel sub3 = new JPanel(new GridLayout(20, 1));
        sub1.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
        sub2.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
        JLabel str = new JLabel("  STR: " + loadedChar.getTotalStr() + " (" + loadedChar.getBaseStr() + "+" +
                (loadedChar.getTotalStr() - loadedChar.getBaseStr()) + ")");
        JLabel dex = new JLabel("  DEX: " + loadedChar.getTotalDex() + " (" + loadedChar.getBaseDex() + "+" +
                (loadedChar.getTotalDex() - loadedChar.getBaseDex()) + ")");
        JLabel intell = new JLabel("  INT " + loadedChar.getTotalInt() + " (" + loadedChar.getBaseInt() + "+" +
                (loadedChar.getTotalInt() - loadedChar.getBaseInt()) + ")");
        JLabel luk = new JLabel("  LUK: " + loadedChar.getTotalLuk() + " (" + loadedChar.getBaseLuk() + "+" +
                (loadedChar.getTotalLuk() - loadedChar.getBaseLuk()) + ")");
        JLabel hp = new JLabel("  HP: " + loadedChar.getHp());
        JLabel mp = new JLabel("  MP: " + loadedChar.getMp());
        JLabel damageRange = new JLabel("  Damage Range: " + loadedChar.getDamageRangeMin() + "-" + loadedChar.getDamageRangeMax());
        JLabel bonusDamage = new JLabel("  Damage: " + loadedChar.getBonusDamage() + "%");
        JLabel bossDamage = new JLabel("  Boss Damage: " + loadedChar.getBossDamage() + "%");
        JLabel finalDamage = new JLabel("  Final Damage: " + loadedChar.getFinalDamage() + "%");
        JLabel ignoreDef = new JLabel("  Ignore Def: " + df.format(loadedChar.getIgnoreDef())  + "%");
        JLabel critRate = new JLabel("  Critical Rate: " + loadedChar.getCritRate() + "%");
        JLabel critDamg = new JLabel("  Critical Damage: " + df.format(loadedChar.getCritDamage()) + "%");
        JLabel statusResist = new JLabel("  Status Resist: " + loadedChar.getStatusResist());
        JLabel knockback = new JLabel("  Knockback Resist: " + loadedChar.getKnockbackResist() + "%");
        JLabel defense = new JLabel("  Defense: " + loadedChar.getDefense());
        JLabel speed = new JLabel("  Speed: " + loadedChar.getSpeed());
        JLabel jump = new JLabel("  Jump: " + loadedChar.getJump());
        JLabel starForce = new JLabel("  Star Force: " + loadedChar.getStarForce());
        JLabel arcanePower = new JLabel("  Arcane Power: " + loadedChar.getArcanePower());
        JLabel ignoreEleDef = new JLabel(" Ignore Elemental Defense: " + loadedChar.getIgnoreEleDef() + "%");
        JLabel eleResist = new JLabel(" Elemental Resist: " + loadedChar.getEleResist() + "%");
        JLabel mesoRate = new JLabel(" Increase Meso Rate: " + loadedChar.getMesoRate() + "%");
        JLabel dropRate = new JLabel(" Increase Drop Rate: " + loadedChar.getDropRate() + "%");
        JLabel attackSpeed = new JLabel(" Attack Speed: " + loadedChar.getAttackSpeed());
        JLabel incomingDamg = new JLabel(" Block Incoming Damage: " + loadedChar.getIgnoreDamage() + "%");
        JLabel buffDuration = new JLabel(" Buff Duration: " + loadedChar.getBuffDurr() + "%");
        JLabel hpPercent= new JLabel(" HP %: " + loadedChar.getHpPercent() + "%");
        JLabel mpPercent = new JLabel(" MP %: " + loadedChar.getMpPercent() + "%");
        JLabel defPercent = new JLabel(" DEF %: " + loadedChar.getDefensePercent() + "%");
        JLabel strPercent = new JLabel(" STR %: " + loadedChar.getStrPercent() + "%");
        JLabel dexPercent = new JLabel(" DEX %: " + loadedChar.getDexPercent() + "%");
        JLabel intPercent = new JLabel(" INT %: " + loadedChar.getIntPercent() + "%");
        JLabel lukPercent = new JLabel(" LUK %: " + loadedChar.getLukPercent() + "%");
        JLabel att = new JLabel(" Total Att: " + (int)(loadedChar.getTotalAtt()));
        JLabel magicAtt = new JLabel(" Total Magic Att: " + (int)(loadedChar.getTotalMagicAtt()));
        JLabel attPercent = new JLabel (" Att %: " + loadedChar.getAttPercent() + "%");
        JLabel magicAttPercent = new JLabel(" Magic Att %: " + loadedChar.getMagicAttPercent() + "%");
        JLabel [] comparisons = new JLabel[loadedChar.statComparisons(df).length];
        for(int i = 0; i < comparisons.length; i++){
            comparisons[i] = new JLabel(loadedChar.statComparisons(df)[i]);
        }
        
        MAIN_FRAME.add(mainPanel);
        loadNavigation();
        mainPanel.add(sub1);
        mainPanel.add(sub2);
        mainPanel.add(sub3);
        sub1.add(str);
        sub1.add(dex);
        sub1.add(intell);
        sub1.add(luk);
        sub1.add(hp);
        sub1.add(mp);
        sub1.add(damageRange);
        sub1.add(bonusDamage);
        sub1.add(bossDamage);
        sub1.add(finalDamage);
        sub1.add(ignoreDef);
        sub1.add(critRate);
        sub1.add(critDamg);
        sub1.add(statusResist);
        sub1.add(knockback);
        sub1.add(defense);
        sub1.add(speed);
        sub1.add(jump);
        sub1.add(starForce);
        sub1.add(arcanePower);
        sub2.add(ignoreEleDef);
        sub2.add(eleResist);
        sub2.add(mesoRate);
        sub2.add(dropRate);
        sub2.add(attackSpeed);
        sub2.add(incomingDamg);
        sub2.add(buffDuration);
        sub2.add(hpPercent);
        sub2.add(mpPercent);
        sub2.add(defPercent);
        sub2.add(strPercent);
        sub2.add(dexPercent);
        sub2.add(intPercent);
        sub2.add(lukPercent);
        sub2.add(att);
        sub2.add(attPercent);
        sub2.add(magicAtt);
        sub2.add(magicAttPercent);
        for (JLabel comparison : comparisons) {
            sub3.add(comparison);
        }
        
        MAIN_FRAME.repaint();
        MAIN_FRAME.revalidate();
    }
    
    private void statsScreen(){
        MAIN_FRAME.getContentPane().removeAll();
        
        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 35));
        JPanel subPanel = new JPanel(layout);
        mainPanel.setPreferredSize(new Dimension(1100, 670));
        subPanel.setPreferredSize(new Dimension(950, 600));
        subPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));
                
        JLabel strLabel = new JLabel("Base STR");
        JLabel dexLabel = new JLabel("Base DEX");
        JLabel intLabel = new JLabel("Base INT");
        JLabel lukLabel = new JLabel("Base LUK");
        JLabel hpLabel = new JLabel("HP");
        JLabel mpLabel = new JLabel("MP");
        JLabel apLabel = new JLabel("Arcane Power");
        JTextField str = new JTextField(String.valueOf(loadedChar.getBaseStr()));
        JTextField dex = new JTextField(String.valueOf(loadedChar.getBaseDex()));
        JTextField intell = new JTextField(String.valueOf(loadedChar.getBaseInt()));
        JTextField luk = new JTextField(String.valueOf(loadedChar.getBaseLuk()));
        JTextField hp = new JTextField(String.valueOf(loadedChar.getHp()));
        JTextField mp = new JTextField(String.valueOf(loadedChar.getMp()));
        JTextField ap = new JTextField(String.valueOf(loadedChar.getArcanePower()));
        str.setPreferredSize((new Dimension(55, 30)));
        dex.setPreferredSize((new Dimension(55, 30)));
        intell.setPreferredSize((new Dimension(55, 30)));
        luk.setPreferredSize((new Dimension(55, 30)));
        ap.setPreferredSize((new Dimension(55, 30)));
        hp.setPreferredSize((new Dimension(55, 30)));
        mp.setPreferredSize((new Dimension(55, 30)));
        ap.setPreferredSize(new Dimension(55, 30));
                
        JLabel willpowerLabel = new JLabel("Willpower Lvl");
        JLabel empathyLabel = new JLabel("Empathy Lvl");
        JLabel insightLabel = new JLabel("Insight Lvl");
        JLabel ambitionLabel = new JLabel("Amibition Lvl");
        JTextField willpower = new JTextField(String.valueOf(loadedChar.getStats("willpower")));
        JTextField empathy = new JTextField(String.valueOf(loadedChar.getStats("empathy")));
        JTextField insight = new JTextField(String.valueOf(loadedChar.getStats("insight")));
        JTextField ambition = new JTextField(String.valueOf(loadedChar.getStats("ambition")));
        willpower.setPreferredSize(new Dimension(55,30));
        empathy.setPreferredSize(new Dimension(55,30));
        insight.setPreferredSize(new Dimension(55,30));
        ambition.setPreferredSize(new Dimension(55,30));
        
        JLabel IALabel = new JLabel("Inner Ability");
        JComboBox availAbility = new JComboBox(MapleInfo.innerAbility());
        JTextField IAAmount = new JTextField("0");
        JButton removeIA = new JButton("Remove");
        DefaultListModel IAText = new DefaultListModel();
        for(String ability : loadedChar.getInnerAbility().toArray(new String[0])){
            IAText.addElement(ability);
        }
        JList currentIA = new JList(IAText);
        JScrollPane IAScroll = new JScrollPane(currentIA);
        IAAmount.setPreferredSize(new Dimension(55,30));
        currentIA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        currentIA.setLayoutOrientation(JList.VERTICAL);
        IAScroll.setPreferredSize(new Dimension(200,150));
        removeIA.setPreferredSize(new Dimension(100,30));
        
        JLabel hyperStatLabel = new JLabel("Hyper Stats");
        JLabel [] statsLabel = new JLabel[MapleInfo.hyperStats().length];
        JTextField [] hyperStats = new JTextField[MapleInfo.hyperStats().length];
        for(int i = 0; i < MapleInfo.hyperStats().length; i++){
            statsLabel[i] = new JLabel(MapleInfo.hyperStats()[i]);
            hyperStats[i] = new JTextField(String.valueOf(loadedChar.getStats(MapleInfo.hyperStatToMap(MapleInfo.hyperStats()[i]))));
            hyperStats[i].setPreferredSize(new Dimension(55, 30));
        }
        
        JTextFieldListener(str, "baseStr", 4, Integer.MAX_VALUE);
        JTextFieldListener(dex, "baseDex", 4, Integer.MAX_VALUE);
        JTextFieldListener(intell, "baseInt", 4, Integer.MAX_VALUE);
        JTextFieldListener(luk, "baseLuk", 4, Integer.MAX_VALUE);
        hp.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(hp.getText());
                if(tempStat <= 0 || tempStat > Integer.MAX_VALUE)
                    throw new NumberFormatException();
                int baseHP;
                if(loadedChar.getJob().equals("Kanna")){
                    baseHP = (int)((tempStat - loadedChar.getStats("finalHp")) / (1 + loadedChar.getHpPercent() / 100.0)) - 
                            loadedChar.getStats("bonusHp") - loadedChar.getStats("equipMp"); 
                } else if(loadedChar.getJob().equals("Demon Avenger")) {
                    baseHP = (int)((int)((tempStat - loadedChar.getStats("finalHp")) / (1 + loadedChar.getHpPercent() / 100.0)) - 
                            loadedChar.getStats("bonusHp") - loadedChar.getStats("equipHp") / 2.0); 
                } else {
                    baseHP = (int)((tempStat - loadedChar.getStats("finalHp")) / (1 + loadedChar.getHpPercent() / 100.0)) - loadedChar.getStats("bonusHp"); 
                }
                loadedChar.insertIntoStatsMap("hp", tempStat);
                loadedChar.insertIntoStatsMap("baseHp", baseHP);
                saved = false;
            } catch (NumberFormatException nfe){
                hp.setText(String.valueOf(loadedChar.getStats("hp")));
            }
        });
        mp.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(mp.getText());
                if(tempStat <= 0 || tempStat > Integer.MAX_VALUE)
                    throw new NumberFormatException();
                int baseMP = (int)((tempStat - loadedChar.getStats("finalMp")) / (1 + loadedChar.getMpPercent() / 100.0)) - loadedChar.getStats("bonusMp");
                loadedChar.insertIntoStatsMap("mp", tempStat);
                loadedChar.insertIntoStatsMap("baseMp", baseMP);
                saved = false;
            } catch (NumberFormatException nfe){
                mp.setText(String.valueOf(loadedChar.getStats("mp")));
            }
        });
        ap.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(ap.getText());
                if(tempStat < 0 || tempStat > 1320 || tempStat % 10 != 0)
                    throw new NumberFormatException();
                loadedChar.mapDiffUpdate(MapleInfo.arcanePowerCalc(loadedChar.getArcanePower(), loadedChar.getJob()), MapleInfo.arcanePowerCalc(tempStat, loadedChar.getJob()));
                loadedChar.insertIntoStatsMap("arcanePower", tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                ap.setText(String.valueOf(loadedChar.getStats("arcanePower")));
            }
        });
        willpower.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(willpower.getText());
                if(tempStat < 0 || tempStat > 100)
                    throw new NumberFormatException();
                loadedChar.mapDiffUpdate(MapleInfo.willpowerLvToStat(loadedChar.getStats("willpower")), MapleInfo.willpowerLvToStat(tempStat));
                loadedChar.insertIntoStatsMap("willpower", tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                willpower.setText(String.valueOf(loadedChar.getStats("willpower")));
            }
        });
        empathy.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(empathy.getText());
                if(tempStat < 0 || tempStat > 100)
                    throw new NumberFormatException();
                loadedChar.mapDiffUpdate(MapleInfo.empathyLvToStat(loadedChar.getStats("empathy")), MapleInfo.empathyLvToStat(tempStat));
                loadedChar.insertIntoStatsMap("empathy", tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                empathy.setText(String.valueOf(loadedChar.getStats("empathy")));
            }
        });
        insight.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(insight.getText()); 
                if(tempStat < 0 || tempStat > 100)
                    throw new NumberFormatException();
                loadedChar.mapDiffUpdate(MapleInfo.insightLvToStat(loadedChar.getStats("insight")), MapleInfo.insightLvToStat(tempStat));
                loadedChar.insertIntoStatsMap("insight", tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                insight.setText(String.valueOf(loadedChar.getStats("insight")));
            }
        });
        ambition.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(ambition.getText()); 
                if(tempStat < 0 || tempStat > 100)
                    throw new NumberFormatException();
                loadedChar.setIgnoreDef(loadedChar.ignoreDefChange(loadedChar.getIgnoreDef(), MapleInfo.ambitionLvToStat(loadedChar.getStats("ambition")), 0));
                loadedChar.setIgnoreDef(loadedChar.ignoreDefChange(loadedChar.getIgnoreDef(), MapleInfo.ambitionLvToStat(tempStat), 1));
                loadedChar.insertIntoStatsMap("ambition", tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                ambition.setText(String.valueOf(loadedChar.getStats("ambition")));
            }
        });
        IAAmount.addActionListener((ActionEvent e) -> {
            try{
                int amount = Integer.parseInt(IAAmount.getText()); 
                if(amount < 0 || amount > Integer.MAX_VALUE)
                    throw new NumberFormatException();
                String line = String.valueOf(availAbility.getSelectedItem()) + ": " + amount;
                loadedChar.getInnerAbility().add(line);
                IAText.addElement(line);
                if(MapleInfo.resolveIAChoice(String.valueOf(availAbility.getSelectedItem())) == null){
                    loadedChar.resolveAIOther(String.valueOf(availAbility.getSelectedItem()), amount, 1);
                } else {
                    loadedChar.additiveStatsMap(MapleInfo.resolveIAChoice(String.valueOf(availAbility.getSelectedItem())), amount);
                }
                IAAmount.setText("0");
                saved = false;
            } catch (NumberFormatException nfe){
                IAAmount.setText("0");
            }
        });
        removeIA.addActionListener((ActionEvent e) -> {
            String removing = IAText.getElementAt(currentIA.getSelectedIndex()).toString();
            IAText.remove(currentIA.getSelectedIndex());
            for(int i = 0; i < loadedChar.getInnerAbility().size(); i++){
                if(loadedChar.getInnerAbility().get(i).equals(removing)){
                    loadedChar.getInnerAbility().remove(i);
                    break;
                }
            }
            String IAStat = removing.substring(0, removing.indexOf(":"));
            int amount = Integer.parseInt(removing.substring(removing.indexOf(":") + 2));
            if(MapleInfo.resolveIAChoice(IAStat) == null){
                    loadedChar.resolveAIOther(IAStat, amount, -1);
            } else {
                    loadedChar.additiveStatsMap(MapleInfo.resolveIAChoice(IAStat), -amount);
            }
            saved = false;
        });
        for(int i = 0; i < hyperStats.length; i++){
            JTextField curr = hyperStats[i];
            String stat = MapleInfo.hyperStats()[i];
            curr.addActionListener((ActionEvent e) -> {
                try{
                    int tempStat = Integer.parseInt(curr.getText()); 
                    if(tempStat < 0 || tempStat > 10)
                        throw new NumberFormatException();
                    if(stat.equals("Ignore DEF")){
                        HashMap<String, Integer> oldVal = MapleInfo.HyperLvToStat(stat, loadedChar.getStats(MapleInfo.hyperStatToMap(stat)));
                        HashMap<String, Integer> newVal = MapleInfo.HyperLvToStat(stat, tempStat);
                        loadedChar.setIgnoreDef(loadedChar.ignoreDefChange(loadedChar.getIgnoreDef(), oldVal.get("ignoreDefense"), 0));
                        loadedChar.setIgnoreDef(loadedChar.ignoreDefChange(loadedChar.getIgnoreDef(), newVal.get("ignoreDefense"), 1));
                    } else if(stat.equals("Critical Damage")){
                        HashMap<String, Integer> oldVal = MapleInfo.HyperLvToStat(stat, loadedChar.getStats(MapleInfo.hyperStatToMap(stat)));
                        HashMap<String, Integer> newVal = MapleInfo.HyperLvToStat(stat, tempStat);
                        loadedChar.setAddCritDamage(-oldVal.get("critDamg"));
                        loadedChar.setAddCritDamage(newVal.get("critDamg"));
                    } else {
                        loadedChar.mapDiffUpdate(MapleInfo.HyperLvToStat(stat, loadedChar.getStats(MapleInfo.hyperStatToMap(stat))), MapleInfo.HyperLvToStat(stat, tempStat));
                    }
                    loadedChar.insertIntoStatsMap(MapleInfo.hyperStatToMap(stat), tempStat);
                    saved = false;
                } catch (NumberFormatException nfe){
                    curr.setText(String.valueOf(loadedChar.getStats(MapleInfo.hyperStatToMap(stat))));
                }
            });
        }
        
        layout.putConstraint(SpringLayout.NORTH, strLabel, 50, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.WEST, strLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, str, 90, SpringLayout.WEST, strLabel);
        layout.putConstraint(SpringLayout.NORTH, str, 45, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.NORTH, dexLabel, 45, SpringLayout.NORTH, strLabel);
        layout.putConstraint(SpringLayout.WEST, dexLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, dex, 90, SpringLayout.WEST, dexLabel);
        layout.putConstraint(SpringLayout.NORTH, dex, 45, SpringLayout.NORTH, str);
        layout.putConstraint(SpringLayout.NORTH, intLabel, 45, SpringLayout.NORTH, dexLabel);
        layout.putConstraint(SpringLayout.WEST, intLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, intell, 90, SpringLayout.WEST, intLabel);
        layout.putConstraint(SpringLayout.NORTH, intell, 45, SpringLayout.NORTH, dex);
        layout.putConstraint(SpringLayout.NORTH, lukLabel, 45, SpringLayout.NORTH, intLabel);
        layout.putConstraint(SpringLayout.WEST, lukLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, luk, 90, SpringLayout.WEST, lukLabel);
        layout.putConstraint(SpringLayout.NORTH, luk, 45, SpringLayout.NORTH, intell);
        layout.putConstraint(SpringLayout.NORTH, hpLabel, 45, SpringLayout.NORTH, lukLabel);
        layout.putConstraint(SpringLayout.WEST, hpLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, hp, 90, SpringLayout.WEST, hpLabel);
        layout.putConstraint(SpringLayout.NORTH, hp, 45, SpringLayout.NORTH, luk);
        layout.putConstraint(SpringLayout.NORTH, mpLabel, 45, SpringLayout.NORTH, hpLabel);
        layout.putConstraint(SpringLayout.WEST, mpLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, mp, 90, SpringLayout.WEST, mpLabel);
        layout.putConstraint(SpringLayout.NORTH, mp, 45, SpringLayout.NORTH, hp);
        layout.putConstraint(SpringLayout.NORTH, apLabel, 45, SpringLayout.NORTH, mpLabel);
        layout.putConstraint(SpringLayout.WEST, apLabel, 50, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, ap, 90, SpringLayout.WEST, apLabel);
        layout.putConstraint(SpringLayout.NORTH, ap, 45, SpringLayout.NORTH, mp);
        layout.putConstraint(SpringLayout.NORTH, willpowerLabel, 50, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.WEST, willpowerLabel, 250, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, willpower, 90, SpringLayout.WEST, willpowerLabel);
        layout.putConstraint(SpringLayout.NORTH, willpower, 45, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.NORTH, empathyLabel, 45, SpringLayout.NORTH, willpowerLabel);
        layout.putConstraint(SpringLayout.WEST, empathyLabel, 250, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, empathy, 90, SpringLayout.WEST, empathyLabel);
        layout.putConstraint(SpringLayout.NORTH, empathy, 45, SpringLayout.NORTH, willpower);
        layout.putConstraint(SpringLayout.NORTH, insightLabel, 45, SpringLayout.NORTH, empathyLabel);
        layout.putConstraint(SpringLayout.WEST, insightLabel, 250, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, insight, 90, SpringLayout.WEST, insightLabel);
        layout.putConstraint(SpringLayout.NORTH, insight, 45, SpringLayout.NORTH, empathy);
        layout.putConstraint(SpringLayout.NORTH, ambitionLabel, 45, SpringLayout.NORTH, insightLabel);
        layout.putConstraint(SpringLayout.WEST, ambitionLabel, 250, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.WEST, ambition, 90, SpringLayout.WEST, ambitionLabel);
        layout.putConstraint(SpringLayout.NORTH, ambition, 45, SpringLayout.NORTH, insight);
        layout.putConstraint(SpringLayout.NORTH, IALabel, 90, SpringLayout.NORTH, apLabel);
        layout.putConstraint(SpringLayout.WEST, IALabel, 100, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.NORTH, availAbility, 25, SpringLayout.NORTH, IALabel);
        layout.putConstraint(SpringLayout.WEST, availAbility, 45, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, IAAmount, 30, SpringLayout.NORTH, availAbility);
        layout.putConstraint(SpringLayout.WEST, IAAmount, 110, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, IAScroll, 90, SpringLayout.NORTH, apLabel);
        layout.putConstraint(SpringLayout.WEST, IAScroll, 250, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, removeIA, 60, SpringLayout.NORTH, IAAmount);
        layout.putConstraint(SpringLayout.WEST, removeIA, 85, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, hyperStatLabel, 40, SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.WEST, hyperStatLabel, 750, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, statsLabel[0], 30, SpringLayout.NORTH, hyperStatLabel);
        layout.putConstraint(SpringLayout.WEST, statsLabel[0], 650, SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, hyperStats[0], 30, SpringLayout.NORTH, hyperStatLabel);
        layout.putConstraint(SpringLayout.WEST, hyperStats[0], 175, SpringLayout.WEST, statsLabel[0]);
        for(int i = 1; i < hyperStats.length; i++){
            layout.putConstraint(SpringLayout.NORTH, statsLabel[i], 30, SpringLayout.NORTH, statsLabel[i-1]);
            layout.putConstraint(SpringLayout.WEST, statsLabel[i], 650, SpringLayout.WEST, subPanel);
            layout.putConstraint(SpringLayout.NORTH, hyperStats[i], 30, SpringLayout.NORTH, statsLabel[i-1]);
            layout.putConstraint(SpringLayout.WEST, hyperStats[i], 175, SpringLayout.WEST, statsLabel[i]);
        }
        
        MAIN_FRAME.add(mainPanel);
        mainPanel.add(subPanel);
        subPanel.add(strLabel);
        subPanel.add(str);
        subPanel.add(dexLabel);
        subPanel.add(dex);
        subPanel.add(intLabel);
        subPanel.add(intell);
        subPanel.add(lukLabel);
        subPanel.add(luk);
        subPanel.add(hpLabel);
        subPanel.add(hp);
        subPanel.add(mpLabel);
        subPanel.add(mp);
        subPanel.add(apLabel);
        subPanel.add(ap);
        subPanel.add(willpowerLabel);
        subPanel.add(empathyLabel);
        subPanel.add(insightLabel);
        subPanel.add(ambitionLabel);
        subPanel.add(willpower);
        subPanel.add(empathy);
        subPanel.add(insight);
        subPanel.add(ambition);
        subPanel.add(IALabel);
        subPanel.add(availAbility);
        subPanel.add(IAAmount);
        subPanel.add(IAScroll);
        subPanel.add(removeIA);
        subPanel.add(hyperStatLabel);
        for(int i = 0; i < hyperStats.length; i++){
            subPanel.add(statsLabel[i]);
            subPanel.add(hyperStats[i]);
        }
        
        loadNavigation();
        
        MAIN_FRAME.repaint();
        MAIN_FRAME.revalidate();
    }
    
    private void equipsScreen(){
        
    }
    
    private void etcEquipsScreen(){
        //Title, CS Equips, Totems
    }
    
    private void linkSkillsScreen(){
        
    }
    
    private void skillsScreen(){
        
    }
    
    private void etcSkillsScreen(){
        //5th Job Nodes, Hyper Skills
    }
    
    private void legionScreen(){
        
    }
    
    private void optionsScreen(){
        MAIN_FRAME.getContentPane().removeAll();
        
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        mainPanel.setPreferredSize(new Dimension(1100, 670));
        SpringLayout layout = new SpringLayout();
        JPanel middle = new JPanel(layout);
        middle.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));
        middle.setPreferredSize(new Dimension(550, 550)); 
        
        JLabel versionLabel = new JLabel("Maple Version");
        JTextField version = new JTextField(String.valueOf(mapleVersion));
        JLabel compareLabel = new JLabel("Compare Mode");
        JCheckBox compare = new JCheckBox();
        JButton saveButton = new JButton("Save");
        JButton deleteIcons = new JButton("Delete All Saved Icons");
        
        File [] icons = new File("./Icons").listFiles();
        double size = 0;
        for(File icon : icons){
            size += icon.length();
        }
        JLabel folderSize = new JLabel("Folder Size: " + df.format(size / Math.pow(1024 , 2)) + " MB");
        
        MAIN_FRAME.add(mainPanel);
        loadNavigation();
        mainPanel.add(middle);
        middle.add(versionLabel);
        middle.add(version);
        middle.add(compareLabel);
        middle.add(compare);
        middle.add(saveButton);
        middle.add(deleteIcons);
        middle.add(folderSize);
        
        layout.putConstraint(SpringLayout.NORTH, versionLabel, 140, SpringLayout.NORTH, middle);
        layout.putConstraint(SpringLayout.WEST, versionLabel, 180, SpringLayout.WEST, middle);
        layout.putConstraint(SpringLayout.WEST, version, 20, SpringLayout.EAST, versionLabel);
        layout.putConstraint(SpringLayout.NORTH, version, 0, SpringLayout.NORTH, versionLabel);
        layout.putConstraint(SpringLayout.NORTH, compareLabel, 10, SpringLayout.SOUTH, versionLabel);
        layout.putConstraint(SpringLayout.WEST, compareLabel, 182, SpringLayout.WEST, middle);
        layout.putConstraint(SpringLayout.WEST, compare, 20, SpringLayout.EAST, compareLabel);
        layout.putConstraint(SpringLayout.NORTH, compare, 5, SpringLayout.NORTH, compareLabel);
        layout.putConstraint(SpringLayout.WEST, saveButton, 240, SpringLayout.WEST, middle);
        layout.putConstraint(SpringLayout.NORTH, saveButton, 20, SpringLayout.SOUTH, compareLabel);
        layout.putConstraint(SpringLayout.WEST, deleteIcons, 190, SpringLayout.WEST, middle);
        layout.putConstraint(SpringLayout.NORTH, deleteIcons, 100, SpringLayout.SOUTH, saveButton);
        layout.putConstraint(SpringLayout.NORTH, folderSize, 10, SpringLayout.SOUTH, deleteIcons);
        layout.putConstraint(SpringLayout.WEST, folderSize, 210, SpringLayout.WEST, middle);
        Font labels = new Font("Serif", Font.BOLD, 20);
        Font text = new Font("Serif", Font.PLAIN, 16);
        Font buttonLabel = new Font("Serif", Font.BOLD, 16);
        versionLabel.setFont(labels);
        compareLabel.setFont(labels);
        version.setPreferredSize(new Dimension(40, 35));
        version.setFont(text);
        saveButton.setPreferredSize(new Dimension(75, 35));
        saveButton.setFont(buttonLabel);
        
        saveButton.addActionListener((ActionEvent e) -> {
            saveButton.setEnabled(false);
            saved = true;
            loadedChar.save();
        });
        deleteIcons.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(MAIN_FRAME, "Are you sure you want to delete all icons?",
                            null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(response == JOptionPane.YES_OPTION){
                for(File icon : icons){
                    icon.delete();
                }
                folderSize.setText("Folder Size: 0.00 MB");
            }
        });
        compare.addActionListener((ActionEvent e) -> {
            compareMode = compare.isSelected();
        });
        compare.setEnabled(false); //Future Feature
        version.addActionListener((ActionEvent e) -> {
            try{
                mapleVersion = Integer.parseInt(version.getText());
                if(mapleVersion < 1)
                    throw new NumberFormatException();
                saveButton.setEnabled(true);
                saved = false;
            } catch (NumberFormatException nfe){
                version.setText(String.valueOf(mapleVersion));
            }
        });
        
        MAIN_FRAME.repaint();
        MAIN_FRAME.revalidate();
    }
    
    private void JTextFieldListener(JTextField field, String stat, int min, int max){
        field.addActionListener((ActionEvent e) -> {
            try{
                int tempStat = Integer.parseInt(field.getText());
                if(tempStat < min || tempStat > max)
                    throw new NumberFormatException();
                loadedChar.insertIntoStatsMap(stat, tempStat);
                saved = false;
            } catch (NumberFormatException nfe){
                field.setText(String.valueOf(loadedChar.getStats(stat)));
            }
        });
    }
    
}
