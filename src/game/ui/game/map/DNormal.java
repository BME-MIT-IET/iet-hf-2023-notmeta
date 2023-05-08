package game.ui.game.map;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the normal Fields
 */
public class DNormal extends DField{
    public DNormal(String s) {
        super();
        drawableID = s;
    }

    @Override
    public JButton Draw() { //has a different look if its selected
        this.setVisible(true);
        Icon icon;
        if (activeStatus){
            icon = new ImageIcon("Images/normalActive.png");
        } else {
            icon = new ImageIcon("Images/normal.png");
        }
        JButton button = new JButton(icon);
        button.addActionListener(this);
        return button;
    }
}
