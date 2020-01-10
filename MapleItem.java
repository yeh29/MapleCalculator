import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MapleItem {

    private String name;
    private String desc;
    private int id;
    private boolean isCash;
    private String overallCategory;
    private String catetory;
    private String subCategory;
    private BufferedImage icon;

    public MapleItem(String name, String desc, int id, boolean isCash, String overallCategory, String category,
                     String subCategory) {
        this.name = name;
        this.desc = desc;
        this.id = id;
        this.isCash = isCash;
        this.overallCategory = overallCategory;
        this.catetory = category;
        this.subCategory = subCategory;
        icon = loadFromFile();
    }

    private BufferedImage loadFromFile() {
        File savedIcon = new File("./Icons/" + getId() + ".png");
        if(savedIcon.exists()) {
            try {
                return ImageIO.read(savedIcon);
            } catch(IOException ioe) {
                return loadDefault();
            }
        } else {
            return loadDefault();
        }
    }

    private BufferedImage loadDefault() {
        BufferedImage image = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Helvetica", Font.BOLD, 24));
        g2d.drawString("?", 32, 32);
        return image;
    }

    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public boolean isCash() {
        return isCash;
    }

    public String getOverallCategory() {
        return overallCategory;
    }

    public String getCatetory() {
        return catetory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }
}
