import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MapleItemList extends JList {

    public MapleItemList(ListModel<?> dataModel) {
        super(dataModel);
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MapleItemList list = (MapleItemList)e.getSource();
                ListModel model = list.getModel();
                int index = list.locationToIndex(e.getPoint());
                if(index > -1) {
                    MapleItem item = (MapleItem)model.getElementAt(index);
                    String htmlFormat = "<html>Name: " + item.getName() + "<br>Description: " + item.getDesc() +
                            "<br>Cash: " + item.isCash() + "<br>Overall Category: " + item.getOverallCategory() +
                            "<br>Category: " + item.getCatetory() + "<br>Subcategory: " +
                            item.getSubCategory() + "</html>";
                    setToolTipText(htmlFormat);
                }
            }
        });
    }

}
