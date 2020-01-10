import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapleCalculator {

    private DecimalFormat df;
    private Font elementFont;
    private Dimension screenSize;
    private JFrame mainFrame;
    private boolean addedWindowListen;
    private boolean saved;
    private int previousClickItem;

    private MapleCharacter loadedChar;
    private int mapleVersion;

    public MapleCalculator() {
        df = new DecimalFormat("0.00");
        elementFont = new Font("Serif", Font.BOLD, 16);
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize((int)screenSize.getWidth() * 3 / 4,(int)screenSize.getHeight() * 3 / 4);
        mainFrame = new JFrame("MapleCalculator");
        mainFrame.setSize(screenSize);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setVisible(true);
        addedWindowListen = false;
        saved = true;
        previousClickItem = -1;

        mapleVersion = 201;
    }

    public static void main(String[] args) {
        MapleCalculator main = new MapleCalculator();
        main.loadEmptyFolders();
        main.startScreen();
    }

    public void loadEmptyFolders() {
        File characters = new File("./Characters");
        if(!characters.exists()) {
            characters.mkdir();
        }
        File icons = new File("./Icons");
        if(!icons.exists()) {
            icons.mkdir();
        }
    }

    public void startScreen() {
        mainFrame.getContentPane().removeAll();
        JList characters = new JList(listOfCharacters());
        JScrollPane scroll = new JScrollPane(characters);
        JPanel options = new JPanel(new GridLayout(1, 2));
        JButton newCharacter = new JButton("Create New Character");
        JButton deleteCharacter = new JButton("Delete Selected Character");

        newCharacter.addActionListener(e -> {
            beforeLoadingCharacter();
            saved = false;
            loadedChar = new MapleCharacter();
            statsScreen();
        });
        deleteCharacter.addActionListener((ActionEvent e) -> {
            if(characters.getSelectedValue() != null) {
                File character = new File("./Characters/" + characters.getSelectedValue() + ".txt");
                int response = JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure you want to delete " + characters.getSelectedValue() + "?", null,
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    character.delete();
                    startScreen();
                }
            }
        });
        characters.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && characters.getSelectedValue() != null) {
                    saved = true;
                    loadedChar = new MapleCharacter((String)characters.getSelectedValue());
                    beforeLoadingCharacter();
                    statsScreen();
                }
            }
        });

        Font font = new Font("Helvetica", Font.BOLD, 24);
        characters.setFont(font);
        newCharacter.setFont(font);
        deleteCharacter.setFont(font);

        options.add(newCharacter);
        options.add(deleteCharacter);
        mainFrame.add(scroll, BorderLayout.CENTER);
        mainFrame.add(options, BorderLayout.SOUTH);
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private String[] listOfCharacters() {
        ArrayList<String> list = new ArrayList<>();
        File characters = new File("./Characters");
        for(File file : characters.listFiles()) {
            if(file.getName().lastIndexOf(".") == -1) {
                list.add(file.getName());
            } else {
                list.add(file.getName().substring(0, file.getName().lastIndexOf(".")));
            }
        }
        return list.toArray(new String[0]);
    }

    private void beforeLoadingCharacter() {
        if(!addedWindowListen) {
            addedWindowListen = true;
            mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            mainFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent we) {
                    if (saved) {
                        mainFrame.dispose();
                    } else {
                        int response = JOptionPane.showConfirmDialog(mainFrame,
                                "Changes have not been saved. Quit anyway?", null,
                                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            mainFrame.dispose();
                        }
                    }
                }
            });
        }
    }

    private void loadNavigation() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.CENTER, (int)screenSize.getWidth() / 8,
                (int)screenSize.getHeight() / 64));
        JButton stats = new JButton("Stats");
        JButton comparisons = new JButton("Comparisons");
        JButton items = new JButton("Items");
        JButton options = new JButton("Options");

        stats.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            statsScreen();
        });
        items.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            itemsScreen();
        });
        comparisons.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            comparisonsScreen();
        });
        options.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            optionsScreen();
        });

        bar.setBorder(new MatteBorder(2, 0, 0, 0, Color.GRAY));

        bar.add(stats);
        bar.add(comparisons);
        bar.add(items);
        bar.add(options);
        mainFrame.add(bar, BorderLayout.SOUTH);
    }

    private void statsScreen() {
        mainFrame.getContentPane().removeAll();
        SpringLayout layout = new SpringLayout();
        JPanel mainPanel = new JPanel(layout);
        JPanel topPanel = new JPanel(new FlowLayout());
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        JPanel charPanel = new JPanel(new GridLayout(2, 2));
        JPanel basicStats = new JPanel(new GridLayout(4, 5));
        JPanel specStats = new JPanel(new GridLayout(7, 1));
        JPanel ignoreAndFinal = new JPanel(new GridLayout(2, 3));
        JTextField name = new JTextField(loadedChar.getName(), 15);
        JTextField level = new JTextField(String.valueOf(loadedChar.getLevel()), 4);
        JComboBox job = new JComboBox(MapleInfo.allJobs());
        JComboBox weapon = new JComboBox(MapleInfo.allWeapons());

        JTextField minRange = new JTextField(String.valueOf(loadedChar.getMinRange()));
        JTextField maxRange = new JTextField(String.valueOf(loadedChar.getMaxRange()));
        JTextField apPercent = new JTextField(String.valueOf(loadedChar.getApPercent()));
        JTextField atkPercent = new JTextField(String.valueOf(loadedChar.getAtkPercent()));
        JTextField damgPercent = new JTextField(String.valueOf(loadedChar.getDamgPercent()));
        JTextField critDPercent = new JTextField(String.valueOf(loadedChar.getCritDPercent()));
        JTextField bossPercent = new JTextField(String.valueOf(loadedChar.getBossPercent()));

        JLabel totalStat = new JLabel("<html>Total<br>Stat</html>");
        JLabel finalStat = new JLabel("<html>Final<br>Stat</html>");
        JLabel statPercent = new JLabel("Stat %");
        JLabel ap = new JLabel("AP");
        JLabel stat1 = new JLabel("");
        JLabel stat2 = new JLabel("");
        JLabel stat3 = new JLabel("");
        JTextField totalStat1 = new JTextField(String.valueOf(loadedChar.getTotalStat1()));
        JTextField finalStat1 = new JTextField(String.valueOf(loadedChar.getFinalStat1()));
        JTextField statPercent1 = new JTextField(String.valueOf(loadedChar.getStatPercent1()));
        JTextField ap1 = new JTextField(String.valueOf(loadedChar.getAp1()));
        JTextField totalStat2 = new JTextField(String.valueOf(loadedChar.getTotalStat2()));
        JTextField finalStat2 = new JTextField(String.valueOf(loadedChar.getFinalStat2()));
        JTextField statPercent2 = new JTextField(String.valueOf(loadedChar.getStatPercent2()));
        JTextField ap2 = new JTextField(String.valueOf(loadedChar.getAp2()));
        JTextField totalStat3 = new JTextField(String.valueOf(loadedChar.getTotalStat3()));
        JTextField finalStat3 = new JTextField(String.valueOf(loadedChar.getFinalStat3()));
        JTextField statPercent3 = new JTextField(String.valueOf(loadedChar.getStatPercent3()));
        JTextField ap3 = new JTextField(String.valueOf(loadedChar.getAp3()));

        JLabel ignoreDef = new JLabel("<html>Ignore Def<br>" +
                df.format(MapleInfo.calculateMultiplicative(loadedChar.getIgnoreDef())) + "%</html>");
        JTextField addIgnoreDef = new JTextField("-");
        DefaultListModel ignoreDefText = new DefaultListModel();
        for(Integer source : loadedChar.getIgnoreDef().toArray(new Integer[0])){
            ignoreDefText.addElement(String.valueOf(source));
        }
        JList ignoreDefList = new JList(ignoreDefText);
        JScrollPane ignoreDefPane = new JScrollPane(ignoreDefList);

        JLabel finalAtk = new JLabel("<html>Final Atk<br>" +
                df.format(MapleInfo.calculateMultiplicative(loadedChar.getFinalAtk())) + "%</hmtl>");
        JTextField addFinalAtk = new JTextField("-");
        DefaultListModel finalAtkText = new DefaultListModel();
        for(Integer source : loadedChar.getFinalAtk().toArray(new Integer[0])){
            finalAtkText.addElement(String.valueOf(source));
        }
        JList finalAtkList = new JList(finalAtkText);
        JScrollPane finalAtkPane = new JScrollPane(finalAtkList);

        job.setSelectedItem(loadedChar.getJob());
        changeStatLabels(stat1, stat2, stat3);
        weapon.setSelectedItem(loadedChar.getWeapon());

        name.addActionListener((ActionEvent e) -> {
            if(name.getText().length() == 0) {
                name.setText(loadedChar.getName());
                return;
            }
            String [] invalidChars = {"/", "\\", ":", "*", "\"", "<", ">", "|", "?"};
            for(String currentChar : invalidChars) {
                if(name.getText().contains(currentChar)) {
                    JOptionPane.showMessageDialog(mainFrame, "Invalid Characters\n/ \\ : * \" < > | ?",
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    name.setText(loadedChar.getName());
                    return;
                }
            }
            saved = false;
            loadedChar.setName(name.getText());
        });
        level.addActionListener((ActionEvent e) -> {
            if(!level.getText().matches("[0-9]+") || Integer.parseInt(level.getText()) < 1 ||
                    Integer.parseInt(level.getText()) > 275) {
                level.setText(String.valueOf(loadedChar.getLevel()));
            } else {
                saved = false;
                loadedChar.setLevel(Integer.parseInt(level.getText()));
            }
        });
        job.addActionListener((ActionEvent e) -> {
            saved = false;
            loadedChar.setJob(String.valueOf(job.getSelectedItem()));
            changeStatLabels(stat1, stat2, stat3);
        });
        weapon.addActionListener((ActionEvent e) -> {
            saved = false;
            loadedChar.setWeapon(String.valueOf(weapon.getSelectedItem()));
        });
        minRange.addActionListener((ActionEvent e) -> {
            if(!minRange.getText().matches("[0-9]+"))  {
                minRange.setText(String.valueOf(loadedChar.getMinRange()));
            } else {
                saved = false;
                loadedChar.setMinRange(Integer.parseInt(minRange.getText()));
            }
        });
        maxRange.addActionListener((ActionEvent e) -> {
            if(!maxRange.getText().matches("[0-9]+"))  {
                maxRange.setText(String.valueOf(loadedChar.getMaxRange()));
            } else {
                saved = false;
                loadedChar.setMaxRange(Integer.parseInt(maxRange.getText()));
            }
        });
        apPercent.addActionListener((ActionEvent e) -> {
            if(!apPercent.getText().matches("[0-9]+"))  {
                apPercent.setText(String.valueOf(loadedChar.getApPercent()));
            } else {
                saved = false;
                loadedChar.setApPercent(Integer.parseInt(apPercent.getText()));
            }
        });
        atkPercent.addActionListener((ActionEvent e) -> {
            if(!atkPercent.getText().matches("[0-9]+"))  {
                atkPercent.setText(String.valueOf(loadedChar.getAtkPercent()));
            } else {
                saved = false;
                loadedChar.setAtkPercent(Integer.parseInt(atkPercent.getText()));
            }
        });
        damgPercent.addActionListener((ActionEvent e) -> {
            if(!damgPercent.getText().matches("[0-9]+"))  {
                damgPercent.setText(String.valueOf(loadedChar.getDamgPercent()));
            } else {
                saved = false;
                loadedChar.setDamgPercent(Integer.parseInt(damgPercent.getText()));
            }
        });
        critDPercent.addActionListener((ActionEvent e) -> {
            try {
                double percent = Double.parseDouble(critDPercent.getText());
                saved = false;
                loadedChar.setCritDPercent(percent);
            } catch(NumberFormatException nfe) {
                critDPercent.setText(String.valueOf(loadedChar.getCritDPercent()));
            }
        });
        bossPercent.addActionListener((ActionEvent e) -> {
            if(!bossPercent.getText().matches("[0-9]+"))  {
                bossPercent.setText(String.valueOf(loadedChar.getBossPercent()));
            } else {
                saved = false;
                loadedChar.setBossPercent(Integer.parseInt(bossPercent.getText()));
            }
        });
        totalStat1.addActionListener((ActionEvent e) -> {
            if(!totalStat1.getText().matches("[0-9]+"))  {
                totalStat1.setText(String.valueOf(loadedChar.getTotalStat1()));
            } else {
                saved = false;
                loadedChar.setTotalStat1(Integer.parseInt(totalStat1.getText()));
            }
        });
        totalStat2.addActionListener((ActionEvent e) -> {
            if(!totalStat2.getText().matches("[0-9]+"))  {
                totalStat2.setText(String.valueOf(loadedChar.getTotalStat2()));
            } else {
                saved = false;
                loadedChar.setTotalStat2(Integer.parseInt(totalStat2.getText()));
            }
        });
        totalStat3.addActionListener((ActionEvent e) -> {
            if(!totalStat3.getText().matches("[0-9]+"))  {
                totalStat3.setText(String.valueOf(loadedChar.getTotalStat3()));
            } else {
                saved = false;
                loadedChar.setTotalStat3(Integer.parseInt(totalStat3.getText()));
            }
        });
        finalStat1.addActionListener((ActionEvent e) -> {
            if(!finalStat1.getText().matches("[0-9]+"))  {
                finalStat1.setText(String.valueOf(loadedChar.getFinalStat1()));
            } else {
                saved = false;
                loadedChar.setFinalStat1(Integer.parseInt(finalStat1.getText()));
            }
        });
        finalStat2.addActionListener((ActionEvent e) -> {
            if(!finalStat2.getText().matches("[0-9]+"))  {
                finalStat2.setText(String.valueOf(loadedChar.getFinalStat2()));
            } else {
                saved = false;
                loadedChar.setFinalStat2(Integer.parseInt(finalStat2.getText()));
            }
        });
        finalStat3.addActionListener((ActionEvent e) -> {
            if(!finalStat3.getText().matches("[0-9]+"))  {
                finalStat3.setText(String.valueOf(loadedChar.getFinalStat3()));
            } else {
                saved = false;
                loadedChar.setFinalStat3(Integer.parseInt(finalStat3.getText()));
            }
        });
        statPercent1.addActionListener((ActionEvent e) -> {
            if(!statPercent1.getText().matches("[0-9]+"))  {
                statPercent1.setText(String.valueOf(loadedChar.getStatPercent1()));
            } else {
                saved = false;
                loadedChar.setStatPercent1(Integer.parseInt(statPercent1.getText()));
            }
        });
        statPercent2.addActionListener((ActionEvent e) -> {
            if(!statPercent2.getText().matches("[0-9]+"))  {
                statPercent2.setText(String.valueOf(loadedChar.getStatPercent2()));
            } else {
                saved = false;
                loadedChar.setStatPercent2(Integer.parseInt(statPercent2.getText()));
            }
        });
        statPercent3.addActionListener((ActionEvent e) -> {
            if(!statPercent3.getText().matches("[0-9]+"))  {
                statPercent3.setText(String.valueOf(loadedChar.getStatPercent3()));
            } else {
                saved = false;
                loadedChar.setStatPercent3(Integer.parseInt(statPercent3.getText()));
            }
        });
        ap1.addActionListener((ActionEvent e) -> {
            if(!ap1.getText().matches("[0-9]+"))  {
                ap1.setText(String.valueOf(loadedChar.getAp1()));
            } else {
                saved = false;
                loadedChar.setAp1(Integer.parseInt(ap1.getText()));
            }
        });
        ap2.addActionListener((ActionEvent e) -> {
            if(!ap2.getText().matches("[0-9]+"))  {
                ap2.setText(String.valueOf(loadedChar.getAp2()));
            } else {
                saved = false;
                loadedChar.setAp2(Integer.parseInt(ap2.getText()));
            }
        });
        ap3.addActionListener((ActionEvent e) -> {
            if(!ap3.getText().matches("[0-9]+"))  {
                ap3.setText(String.valueOf(loadedChar.getAp3()));
            } else {
                saved = false;
                loadedChar.setAp3(Integer.parseInt(ap3.getText()));
            }
        });
        addIgnoreDef.addActionListener((ActionEvent e) -> {
            if(!addIgnoreDef.getText().matches("[0-9]+") || Integer.parseInt(addIgnoreDef.getText()) < 1) {
                addIgnoreDef.setText("-");
            } else {
                saved = false;
                loadedChar.getIgnoreDef().add(Integer.parseInt(addIgnoreDef.getText()));
                ignoreDefText.addElement(addIgnoreDef.getText());
                ignoreDef.setText("<html>Ignore Def<br>" +
                        df.format(MapleInfo.calculateMultiplicative(loadedChar.getIgnoreDef())) + "%</html>");
                addIgnoreDef.setText("-");
            }
        });
        ignoreDefList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && ignoreDefList.getSelectedValue() != null) {
                    saved = false;
                    loadedChar.getIgnoreDef().remove(ignoreDefList.getSelectedIndex());
                    ignoreDefText.remove(ignoreDefList.getSelectedIndex());
                    ignoreDef.setText("<html>Ignore Def<br>" +
                            df.format(MapleInfo.calculateMultiplicative(loadedChar.getIgnoreDef())) + "%</html>");
                }
            }
        });
        addFinalAtk.addActionListener((ActionEvent e) -> {
            if(!addFinalAtk.getText().matches("[0-9]+") || Integer.parseInt(addFinalAtk.getText()) < 1) {
                addFinalAtk.setText("-");
            } else {
                saved = false;
                loadedChar.getFinalAtk().add(Integer.parseInt(addFinalAtk.getText()));
                finalAtkText.addElement(addFinalAtk.getText());
                finalAtk.setText("<html>Final Atk<br>" +
                        df.format(MapleInfo.calculateMultiplicative(loadedChar.getFinalAtk())) + "%</hmtl>");
                addFinalAtk.setText("-");
            }
        });
        finalAtkList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && finalAtkList.getSelectedValue() != null) {
                    saved = false;
                    loadedChar.getFinalAtk().remove(finalAtkList.getSelectedIndex());
                    finalAtkText.remove(finalAtkList.getSelectedIndex());
                    finalAtk.setText("<html>Final Atk<br>" +
                            df.format(MapleInfo.calculateMultiplicative(loadedChar.getFinalAtk())) + "%</hmtl>");
                }
            }
        });

        charPanel.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Character", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        name.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Name", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        level.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Level", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        job.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Job", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        weapon.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Weapon", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(
                new MatteBorder(1, 1, 1, 1, Color.GRAY), "Stats",
                TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        minRange.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Min Range", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        maxRange.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Max Range", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        apPercent.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "AP %", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        atkPercent.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Atk %", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        damgPercent.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Damage %", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        critDPercent.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Critical Damage %", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        bossPercent.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Boss Damage %", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        addIgnoreDef.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Add Source", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        ignoreDefPane.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Sources", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        addFinalAtk.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Add Source", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        finalAtkPane.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1,
                Color.GRAY), "Sources", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));

        stat1.setHorizontalAlignment(JLabel.CENTER);
        stat2.setHorizontalAlignment(JLabel.CENTER);
        stat3.setHorizontalAlignment(JLabel.CENTER);
        totalStat.setHorizontalAlignment(JLabel.CENTER);
        finalStat.setHorizontalAlignment(JLabel.CENTER);
        statPercent.setHorizontalAlignment(JLabel.CENTER);
        ap.setHorizontalAlignment(JLabel.CENTER);
        ignoreDef.setHorizontalAlignment(JLabel.CENTER);
        finalAtk.setHorizontalAlignment(JLabel.CENTER);

        name.setFont(elementFont);
        level.setFont(elementFont);
        job.setFont(elementFont);
        weapon.setFont(elementFont);
        stat1.setFont(elementFont);
        stat2.setFont(elementFont);
        stat3.setFont(elementFont);
        totalStat.setFont(elementFont);
        finalStat.setFont(elementFont);
        statPercent.setFont(elementFont);
        ap.setFont(elementFont);
        ignoreDef.setFont(elementFont);
        finalAtk.setFont(elementFont);

        bottomPanel.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 16,
                (int)screenSize.getHeight() / 2));
        basicStats.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 48,
                (int)screenSize.getHeight() / 2));
        specStats.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 48,
                (int)screenSize.getHeight() / 2));
        ignoreAndFinal.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 48,
                (int)screenSize.getHeight() / 2));

        charPanel.add(name);
        charPanel.add(level);
        charPanel.add(job);
        charPanel.add(weapon);
        topPanel.add(charPanel);
        basicStats.add(new JPanel());
        basicStats.add(totalStat);
        basicStats.add(finalStat);
        basicStats.add(statPercent);
        basicStats.add(ap);
        basicStats.add(stat1);
        basicStats.add(totalStat1);
        basicStats.add(finalStat1);
        basicStats.add(statPercent1);
        basicStats.add(ap1);
        basicStats.add(stat2);
        basicStats.add(totalStat2);
        basicStats.add(finalStat2);
        basicStats.add(statPercent2);
        basicStats.add(ap2);
        basicStats.add(stat3);
        basicStats.add(totalStat3);
        basicStats.add(finalStat3);
        basicStats.add(statPercent3);
        basicStats.add(ap3);
        specStats.add(minRange);
        specStats.add(maxRange);
        specStats.add(apPercent);
        specStats.add(atkPercent);
        specStats.add(damgPercent);
        specStats.add(critDPercent);
        specStats.add(bossPercent);
        ignoreAndFinal.add(ignoreDef);
        ignoreAndFinal.add(addIgnoreDef);
        ignoreAndFinal.add(ignoreDefPane);
        ignoreAndFinal.add(finalAtk);
        ignoreAndFinal.add(addFinalAtk);
        ignoreAndFinal.add(finalAtkPane);
        bottomPanel.add(basicStats);
        bottomPanel.add(specStats);
        bottomPanel.add(ignoreAndFinal);
        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
        mainFrame.add(mainPanel, BorderLayout.CENTER);

        layout.putConstraint(SpringLayout.NORTH, topPanel, 5, SpringLayout.NORTH, mainPanel);
        layout.putConstraint(SpringLayout.WEST, topPanel, (int)screenSize.getWidth() / 3,
                SpringLayout.WEST, mainPanel);
        layout.putConstraint(SpringLayout.NORTH, bottomPanel, 5, SpringLayout.SOUTH, topPanel);
        layout.putConstraint(SpringLayout.WEST, bottomPanel, (int)screenSize.getWidth() / 32,
                SpringLayout.WEST, mainPanel);

        loadNavigation();
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void changeStatLabels(JLabel stat1, JLabel stat2, JLabel stat3) {
        switch (MapleInfo.statsBasedOnJob(loadedChar.getJob()).length) {
            case 0:
                stat1.setText("");
                stat2.setText("");
                stat3.setText("");
                break;
            case 2:
                stat1.setText(MapleInfo.statsBasedOnJob(loadedChar.getJob())[0]);
                stat2.setText(MapleInfo.statsBasedOnJob(loadedChar.getJob())[1]);
                stat3.setText("");
                break;
            case 3:
                stat1.setText(MapleInfo.statsBasedOnJob(loadedChar.getJob())[0]);
                stat2.setText(MapleInfo.statsBasedOnJob(loadedChar.getJob())[1]);
                stat3.setText(MapleInfo.statsBasedOnJob(loadedChar.getJob())[2]);
        }
    }

    private void comparisonsScreen() {
        mainFrame.getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1));
        JPanel equiv = new JPanel(new GridLayout(1 + 3 * MapleInfo.statsBasedOnJob(loadedChar.getJob()).length,
                4));
        JLabel equivLabel = new JLabel("Predefined Equivalences");
        JLabel statType = new JLabel("Stat Type");
        JLabel statWindow = new JLabel("Stat Window");
        JLabel xStat = new JLabel("x Stat");
        JLabel xAttack = new JLabel("x Attack");
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 1));
        JPanel comparison = new JPanel(new GridLayout(6, 5));
        JLabel comparisonLabel = new JLabel("Stat Comparisons");

        JLabel statAmountC = new JLabel("Stat Amount");
        JLabel statAmountC2 = new JLabel("Stat Amount");
        JLabel statTypeC = new JLabel("Stat Type");
        JLabel statTypeC2 = new JLabel("Stat Type");
        JTextField[] enterAmount = new JTextField[5];
        JComboBox[] leftSelect = new JComboBox[5];
        JLabel[] compareResult = new JLabel[5];
        JComboBox[] rightSelect = new JComboBox[5];

        for(int i = 0; i < 5; i++) {
            enterAmount[i] = new JTextField(loadedChar.getComparisons()[i][0]);
            leftSelect[i] = new JComboBox(MapleInfo.statTypes());
            rightSelect[i] = new JComboBox(MapleInfo.statTypes());
            compareResult[i] = new JLabel(df.format(MapleInfo.calculateStatComparison(loadedChar, i)));

            leftSelect[i].setSelectedItem(loadedChar.getComparisons()[i][1]);
            rightSelect[i].setSelectedItem(loadedChar.getComparisons()[i][2]);
            compareResult[i].setHorizontalAlignment(JLabel.CENTER);

            enterAmount[i].setFont(elementFont);
            leftSelect[i].setFont(elementFont);
            compareResult[i].setFont(elementFont);
            rightSelect[i].setFont(elementFont);

            int row = i;
            enterAmount[i].addActionListener((ActionEvent e) -> {
                if((!enterAmount[row].getText().matches("[0-9]+") && !enterAmount[row].getText().equals("-")) ||
                        enterAmount[row].getText().matches("[0-9]+") &&
                                Integer.parseInt(enterAmount[row].getText()) < 1) {
                    enterAmount[row].setText(loadedChar.getComparisons()[row][0]);
                } else {
                    saved = false;
                    loadedChar.getComparisons()[row][0] = enterAmount[row].getText();
                    compareResult[row].setText(df.format(MapleInfo.calculateStatComparison(loadedChar, row)));
                }
            });
            leftSelect[i].addActionListener((ActionEvent e) -> {
                saved = false;
                loadedChar.getComparisons()[row][1] = String.valueOf(leftSelect[row].getSelectedItem());
                compareResult[row].setText(df.format(MapleInfo.calculateStatComparison(loadedChar, row)));
            });
            rightSelect[i].addActionListener((ActionEvent e) -> {
                saved = false;
                loadedChar.getComparisons()[row][2] = String.valueOf(rightSelect[row].getSelectedItem());
                compareResult[row].setText(df.format(MapleInfo.calculateStatComparison(loadedChar, row)));
            });
        }

        equiv.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 16,
                (int)screenSize.getHeight() * 6 / 16));
        comparison.setPreferredSize(new Dimension((int)screenSize.getWidth() * 15 / 16,
                (int)screenSize.getHeight() * 6 / 16));
        topPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.GRAY));
        equiv.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
        comparison.setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));

        equivLabel.setFont(elementFont);
        statType.setFont(elementFont);
        statWindow.setFont(elementFont);
        xStat.setFont(elementFont);
        xAttack.setFont(elementFont);
        comparisonLabel.setFont(elementFont);
        statAmountC.setFont(elementFont);
        statAmountC2.setFont(elementFont);
        statTypeC.setFont(elementFont);
        statTypeC2.setFont(elementFont);

        statType.setHorizontalAlignment(JLabel.CENTER);
        statWindow.setHorizontalAlignment(JLabel.CENTER);
        xStat.setHorizontalAlignment(JLabel.CENTER);
        xAttack.setHorizontalAlignment(JLabel.CENTER);
        statAmountC.setHorizontalAlignment(JLabel.CENTER);
        statAmountC2.setHorizontalAlignment(JLabel.CENTER);
        statTypeC.setHorizontalAlignment(JLabel.CENTER);
        statTypeC2.setHorizontalAlignment(JLabel.CENTER);

        equiv.add(statType);
        equiv.add(statWindow);
        equiv.add(xStat);
        equiv.add(xAttack);

        for(int i = 0; i < MapleInfo.statsBasedOnJob(loadedChar.getJob()).length; i++) {
            String stats = MapleInfo.statsBasedOnJob(loadedChar.getJob())[i];
            JLabel oneStat = new JLabel("1 " + stats);
            JLabel onePercentStat = new JLabel("1% " + stats);
            JLabel oneAttack = new JLabel("1 Attack");

            double stat = MapleInfo.calculateOneStatOrPercent(loadedChar, i, "Stat");
            double statPercent = MapleInfo.calculateOneStatOrPercent(loadedChar, i, "Percent");
            double attack = MapleInfo.calculateAttackStat(loadedChar, i);

            JLabel calcOneStat = new JLabel(df.format(stat));
            JLabel topEq = new JLabel(df.format(stat / attack));
            JLabel calcPercentStat = new JLabel(df.format(statPercent));
            JLabel middleLeftEq = new JLabel(df.format(statPercent / stat));
            JLabel middleRightEq = new JLabel(df.format(statPercent / attack));
            JLabel calcOneAtk = new JLabel(df.format(attack));
            JLabel botEq = new JLabel(df.format(attack / stat));

            oneStat.setFont(elementFont);
            onePercentStat.setFont(elementFont);
            oneAttack.setFont(elementFont);
            calcOneStat.setFont(elementFont);
            topEq.setFont(elementFont);
            calcPercentStat.setFont(elementFont);
            middleLeftEq.setFont(elementFont);
            middleRightEq.setFont(elementFont);
            calcOneAtk.setFont(elementFont);
            botEq.setFont(elementFont);

            oneStat.setHorizontalAlignment(JLabel.CENTER);
            onePercentStat.setHorizontalAlignment(JLabel.CENTER);
            oneAttack.setHorizontalAlignment(JLabel.CENTER);
            calcOneStat.setHorizontalAlignment(JLabel.CENTER);
            topEq.setHorizontalAlignment(JLabel.CENTER);
            calcPercentStat.setHorizontalAlignment(JLabel.CENTER);
            middleLeftEq.setHorizontalAlignment(JLabel.CENTER);
            middleRightEq.setHorizontalAlignment(JLabel.CENTER);
            calcOneAtk.setHorizontalAlignment(JLabel.CENTER);
            botEq.setHorizontalAlignment(JLabel.CENTER);

            equiv.add(oneStat);
            equiv.add(calcOneStat);
            equiv.add(new JLabel());
            equiv.add(topEq);
            equiv.add(onePercentStat);
            equiv.add(calcPercentStat);
            equiv.add(middleLeftEq);
            equiv.add(middleRightEq);
            equiv.add(oneAttack);
            equiv.add(calcOneAtk);
            equiv.add(botEq);
            equiv.add(new JLabel());
        }

        topPanel.add(equivLabel);
        topPanel.add(equiv);
        mainPanel.add(topPanel);

        comparison.add(statAmountC);
        comparison.add(statTypeC);
        comparison.add(new JLabel());
        comparison.add(statAmountC2);
        comparison.add(statTypeC2);

        for(int i = 0; i < 5; i++) {
            comparison.add(enterAmount[i]);
            comparison.add(leftSelect[i]);
            JLabel equal = new JLabel("=");
            equal.setFont(elementFont);
            equal.setHorizontalAlignment(JLabel.CENTER);
            comparison.add(equal);
            comparison.add(compareResult[i]);
            comparison.add(rightSelect[i]);
        }

        botPanel.add(comparisonLabel);
        botPanel.add(comparison);
        mainPanel.add(botPanel);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        loadNavigation();
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void itemsScreen() {
        mainFrame.getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new BorderLayout(0, 0));
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JPanel inventoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JScrollPane inventoryScroll = new JScrollPane(inventoryPanel);
        JButton equip = new JButton("EQUIP");
        JButton use = new JButton("USE");
        JButton etc = new JButton("ETC");
        JButton setUp = new JButton("SET-UP");
        JButton cash = new JButton("CASH");
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        JPanel searchPanel = new JPanel(new GridBagLayout());
        JTextField search = new JTextField(20);
        JPanel resultsPanel = new JPanel(new BorderLayout());
        DefaultListModel<MapleItem> searchResults = new DefaultListModel();
        MapleItemList searchList = new MapleItemList(searchResults);
        JScrollPane resultsPane = new JScrollPane(searchList);

        reloadInventory(inventoryPanel, 0);

        inventoryPanel.setPreferredSize(new Dimension((int)(screenSize.getWidth() / 2 * 17 / 18),
                (int)Math.ceil((double)loadedChar.getInventory().get(0).size() /
                        (int)(screenSize.getWidth() / 2 / 69)) * 69));
        inventoryPanel.setBackground(Color.WHITE);

        leftPanel.setBorder(new MatteBorder(0, 0, 0, 2, Color.GRAY));
        search.setBorder(BorderFactory.createTitledBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY),
                "Search", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));
        resultsPanel.setBorder(BorderFactory.createTitledBorder(new MatteBorder(0, 0, 0, 0,
                        Color.GRAY), "Search Results", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, elementFont));

        search.setFont(new Font(Font.SERIF, Font.BOLD, 24));
        searchList.setFont(elementFont);

        search.addActionListener((ActionEvent e) -> {
            if(search.getText().length() > 0) {
                searchResults.removeAllElements();
                for(MapleItem item : MapleRequest.itemRequest(search.getText(), "GMS",
                        String.valueOf(mapleVersion))) {
                    searchResults.addElement(item);
                }
            }
        });
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                previousClickItem = -1;
            }
        });
        searchList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                previousClickItem = -1;
                if(e.getClickCount() == 2 && searchList.getSelectedValue() != null) {
                    MapleItem item = (MapleItem)searchList.getSelectedValue();
                    File savedIcon = new File("./Icons/" + item.getId() + ".png");
                    if(!savedIcon.exists()) {
                        BufferedImage newIcon = MapleRequest.iconRequest(String.valueOf(item.getId()),
                                "GMS", String.valueOf(mapleVersion));
                        try {
                            if(newIcon != null) {
                                ImageIO.write(newIcon, "png", savedIcon);
                                item.setIcon(newIcon);
                            }
                        } catch(IOException ioe) {
                        }
                    }
                    saved = false;
                    if(item.getOverallCategory().equals("Equip")) {
                        loadedChar.getInventory().get(0).add(item);
                        reloadInventory(inventoryPanel, 0);
                    } else if(item.getOverallCategory().equals("Use")) {
                        loadedChar.getInventory().get(1).add(item);
                        reloadInventory(inventoryPanel, 1);
                    } else if(item.getOverallCategory().equals("Setup")) {
                        loadedChar.getInventory().get(3).add(item);
                        reloadInventory(inventoryPanel, 3);
                    } else if(item.getOverallCategory().equals("Etc")) {
                        loadedChar.getInventory().get(2).add(item);
                        reloadInventory(inventoryPanel, 2);
                    } else if(item.isCash() || item.getOverallCategory().equals("Cash")) {
                        loadedChar.getInventory().get(4).add(item);
                        reloadInventory(inventoryPanel, 4);
                    }
                }
            }
        });
        equip.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            reloadInventory(inventoryPanel, 0);
        });
        use.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            reloadInventory(inventoryPanel, 1);
        });
        etc.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            reloadInventory(inventoryPanel, 2);
        });
        setUp.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            reloadInventory(inventoryPanel, 3);
        });
        cash.addActionListener((ActionEvent e) -> {
            previousClickItem = -1;
            reloadInventory(inventoryPanel, 4);
        });

        buttonPanel.add(equip);
        buttonPanel.add(use);
        buttonPanel.add(etc);
        buttonPanel.add(setUp);
        buttonPanel.add(cash);
        searchPanel.add(search);
        resultsPanel.add(resultsPane, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.NORTH);
        leftPanel.add(inventoryScroll, BorderLayout.CENTER);
        rightPanel.add(searchPanel);
        rightPanel.add(resultsPanel);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        loadNavigation();
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    private void reloadInventory(JPanel inventoryPanel, int location) {
        inventoryPanel.removeAll();
        for(int i = 0; i < loadedChar.getInventory().get(location).size(); i++) {
            MapleItem item = loadedChar.getInventory().get(location).get(i);
            ImageIcon itemIcon = new ImageIcon(item.getIcon());
            Image scaledImage = itemIcon.getImage();
            scaledImage = scaledImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            JLabel icon = new JLabel(new ImageIcon(scaledImage));
            String htmlFormat = "<html>Name: " + item.getName() + "<br>Description: " + item.getDesc() +
                    "<br>Cash: " + item.isCash() + "<br>Overall Category: " + item.getOverallCategory() +
                    "<br>Category: " + item.getCatetory() + "<br>Subcategory: " + item.getSubCategory() + "</html>";
            icon.setToolTipText(htmlFormat);

            int itemIndex = i;
            icon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 1) {
                        if(previousClickItem == -1) {
                            previousClickItem = itemIndex;
                        } else if(previousClickItem == itemIndex) {
                            previousClickItem = -1;
                        } else {
                            saved = false;
                            MapleItem item1 = loadedChar.getInventory().get(location).get(previousClickItem);
                            MapleItem item2 = loadedChar.getInventory().get(location).get(itemIndex);
                            loadedChar.getInventory().get(location).set(previousClickItem, item2);
                            loadedChar.getInventory().get(location).set(itemIndex, item1);
                            reloadInventory(inventoryPanel, location);
                            previousClickItem = -1;
                        }
                    } else if(e.getClickCount() == 2) {
                        int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to " +
                                        "remove " + item.getName() + " from your inventory?", null,
                                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                        if(response == JOptionPane.YES_OPTION) {
                            saved = false;
                            loadedChar.getInventory().get(location).remove(itemIndex);
                            reloadInventory(inventoryPanel, location);
                        }
                    }
                }
            });
            inventoryPanel.add(icon);
        }
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    private void optionsScreen() {
        mainFrame.getContentPane().removeAll();
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, (int)screenSize.getWidth() * 2 / 3,
                (int)screenSize.getHeight() / 6));
        JPanel subPanel = new JPanel(new GridLayout(2, 1));
        SpringLayout layout = new SpringLayout();
        JPanel topPanel = new JPanel(layout);
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel versionLabel = new JLabel("Maple Version");
        JTextField version = new JTextField(String.valueOf(mapleVersion), 3);
        JButton deleteIcons = new JButton("Delete All Saved Icons");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JLabel credit = new JLabel("Credit to DelusionDash ");

        File [] icons = new File("./Icons").listFiles();
        double size = 0;
        for(File icon : icons) {
            size += icon.length();
        }
        JLabel folderSize = new JLabel("Folder Size: " + df.format(size / Math.pow(1024, 2)) + " MB");

        version.addActionListener((ActionEvent e) -> {
            if(!version.getText().matches("[0-9]+") || Integer.parseInt(version.getText()) < 1) {
                version.setText(String.valueOf(mapleVersion));
            } else {
                mapleVersion = Integer.parseInt(version.getText());
                saveButton.setEnabled(true);
                saved = false;
            }
        });
        deleteIcons.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to delete all icons?",
                    null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(response == JOptionPane.YES_OPTION) {
                for(File icon : icons){
                    icon.delete();
                }
                folderSize.setText("Folder Size: 0.00 MB");
            }
        });
        loadButton.addActionListener((ActionEvent e) -> {
            if(!saved) {
                int response = JOptionPane.showConfirmDialog(mainFrame, "Go to home screen without saving?",
                        null, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(response == JOptionPane.YES_OPTION) {
                    saved = true;
                    startScreen();
                }
            } else {
                startScreen();
            }
        });
        if(saved) {
            saveButton.setEnabled(false);
        }
        saveButton.addActionListener((ActionEvent e) -> {
            saveButton.setEnabled(false);
            saved = true;
            loadedChar.save();
        });

        subPanel.setPreferredSize(new Dimension((int)screenSize.getWidth() / 3,
                (int)screenSize.getHeight() * 2 / 3));
        subPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));

        versionLabel.setFont(elementFont);
        version.setFont(elementFont);
        folderSize.setFont(elementFont);
        deleteIcons.setFont(elementFont);
        saveButton.setFont(elementFont);
        loadButton.setFont(elementFont);

        credit.setVerticalAlignment(JLabel.BOTTOM);

        subPanel.add(topPanel);
        subPanel.add(bottomPanel);
        topPanel.add(versionLabel);
        topPanel.add(version);
        topPanel.add(folderSize);

        layout.putConstraint(SpringLayout.NORTH, versionLabel, (int)subPanel.getPreferredSize().getHeight() / 3,
                SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.WEST, versionLabel, (int)subPanel.getPreferredSize().getWidth() / 3,
                SpringLayout.WEST, subPanel);
        layout.putConstraint(SpringLayout.NORTH, version, (int)subPanel.getPreferredSize().getHeight() / 3,
                SpringLayout.NORTH, subPanel);
        layout.putConstraint(SpringLayout.WEST, version, 10, SpringLayout.EAST, versionLabel);
        layout.putConstraint(SpringLayout.NORTH, folderSize, 25, SpringLayout.NORTH, versionLabel);
        layout.putConstraint(SpringLayout.WEST, folderSize, 0, SpringLayout.WEST, versionLabel);

        bottomPanel.add(deleteIcons);
        bottomPanel.add(loadButton);
        bottomPanel.add(saveButton);

        mainPanel.add(subPanel);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.add(credit, BorderLayout.EAST);
        loadNavigation();
        mainFrame.repaint();
        mainFrame.revalidate();
    }

}
