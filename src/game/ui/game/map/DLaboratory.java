package game.ui.game.map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the Laboratories
 */
public class DLaboratory extends DField{
    public DLaboratory(String s) {
        super();
        drawableID = s;
    }

    @Override
    public JButton Draw() { //has a different look if its selected
        Icon icon;
        if (activeStatus){
            icon = new ImageIcon("Images/laboratoryActive.png");
        } else {
            icon = new ImageIcon("Images/laboratory.png");
        }
        JButton button = new JButton(icon);
        button.addActionListener((ActionListener) this);
        return button;
    }
}
