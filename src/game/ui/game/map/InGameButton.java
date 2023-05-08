package game.ui.game.map;

import game.ui.game.MapPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class for the Buttons on the map
 */
public abstract class InGameButton extends JButton {
    protected MapPanel mapPanel;
    protected boolean activeStatus = false;
    protected String drawableID;
    protected int x,y; //coordinates
    public abstract JButton Draw();
    public void SetCoords(Point p){
        x = p.x;
        y = p.y;
    }
    public Point GetCoords(){
        return new Point(x,y);
    }
    public String GetID(){
        return drawableID;
    }
    public void setActivateStatus(boolean s) { activeStatus = s; }
    public void setMapPanel(MapPanel mapPanel) { this.mapPanel = mapPanel; }
}
