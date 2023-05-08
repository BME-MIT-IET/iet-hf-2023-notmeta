package game.ui.game.map;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for Warehouses
 */
public class DWareHouse extends DField{
    public DWareHouse(String s) {
        super();
        drawableID = s;
    }

    @Override
    public JButton Draw() { //has a different look if its selected
        Icon icon;
        if (activeStatus){
            icon = new ImageIcon("Images/warehouseActive.png");
        } else {
            icon = new ImageIcon("Images/warehouse.png");
        }
        JButton button = new JButton(icon);
        button.addActionListener(this);
        return button;
    }
}
