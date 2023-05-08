package game.ui.game.map;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class for the Virologists
 */
public class DVirologist extends InGameButton implements ActionListener {
    private int index;
    public DVirologist(String s, int index) {
        super();
        drawableID = s;
        this.index = index;
        int d = 20;
        switch (index){ //Each DVirologist gets a corner on a DField, so they are visible even if multiple virologists are on the same field
            case 1:
                x = d;
                y = d;
                break;
            case 2:
                x = d;
                y = -d;
                break;
            case 3:
                x = -d;
                y = d;
                break;
            case 4:
                x = -d;
                y = -d;
                break;
        }
    }

    @Override
    public JButton Draw() {
        Icon icon;
        JButton button;
        switch (index){ //each virologist has its own color: player1: red, player2: blue, player3: green and player4: yellow
            case 1:
                if (activeStatus){
                    icon = new ImageIcon("Images/redActive.png");
                } else {
                    icon = new ImageIcon("Images/red.png");
                }
                button = new JButton(icon);
                button.addActionListener(this);
                return button;
            case 2:
                if (activeStatus){
                    icon = new ImageIcon("Images/blueActive.png");
                } else {
                    icon = new ImageIcon("Images/blue.png");
                }
                button = new JButton(icon);
                button.addActionListener(this);
                return button;
            case 3:
                if (activeStatus){
                    icon = new ImageIcon("Images/greenActive.png");
                } else {
                    icon = new ImageIcon("Images/green.png");
                }
                button = new JButton(icon);
                button.addActionListener(this);
                return button;
            case 4:
                if (activeStatus){
                    icon = new ImageIcon("Images/yellowActive.png");
                } else {
                    icon = new ImageIcon("Images/yellow.png");
                }
                button = new JButton(icon);
                button.addActionListener(this);
                return button;
            default:
                return null;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "Virologist: name: " + drawableID);
        if (activeStatus){
            this.mapPanel.setActiveVirologists(null);
            activeStatus = false;
        }else{
            this.mapPanel.setActiveVirologists(this);
            activeStatus = true;
        }
        this.mapPanel.repaint();
    }

    public String GetVirologistName(){
        return drawableID;
    }

}
