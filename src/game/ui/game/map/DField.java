package game.ui.game.map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class for the Field buttons
 */
public abstract class DField extends InGameButton implements ActionListener {
    private ArrayList<DField> neighbors;
    public DField(){
        neighbors = new ArrayList<>();
    }
    public void AddNeighbor(DField neighbor){
        neighbors.add(neighbor);
    }
    public ArrayList<DField> GetNeighbors(){
        return neighbors;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (activeStatus){
            this.mapPanel.setActiveFiled(null);
            activeStatus = false;
        }else{
            this.mapPanel.setActiveFiled(this);
            activeStatus = true;
        }
        this.mapPanel.repaint();
    }
}
