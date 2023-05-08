package game.ui.game.map;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the Shelters
 */
public class DShelter extends DField{
    public DShelter(String s) {
        super();
        drawableID = s;
    }

    @Override
    public JButton Draw() { //has a different look if its selected
        Icon icon;
        if (activeStatus){
            icon = new ImageIcon("Images/shelterActive.png");
        } else {
            icon = new ImageIcon("Images/shelter.png");
        }
        JButton button = new JButton(icon);
        button.addActionListener(this);
        return button;
    }
}
