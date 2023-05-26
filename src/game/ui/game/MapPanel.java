package game.ui.game;

import game.ui.game.map.DField;
import game.ui.game.map.DVirologist;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * JPanel that represents the map. It contains buttons as Fields and Virologists
 */
public class MapPanel extends JLayeredPane {
    private final GameScene gameScene;
    private DField activeField = null;
    private DVirologist activeVirologist = null;

    public MapPanel(GameScene gameScene){
        this.gameScene = gameScene;
        int mapWidth = 600;
        int mapHeight = 400;
        this.setPreferredSize(new Dimension(mapWidth, mapHeight));
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.white);
        this.setLayout(null);

    }
    @Override
    public void paint(Graphics g){
        //remove the remaining buttons
        for(Component component : this.getComponents()){
            this.remove(component);
        }
        //for each DField that is to be displayed create a button
        for(DField df: gameScene.getVisibleFields()){
            df.setMapPanel(this);
            JButton fieldButton = df.Draw();

            fieldButton.setName(df.getId());

            fieldButton.setBounds(df.GetCoords().x - 25, df.GetCoords().y -25, 50,50);
            this.add(fieldButton, 0);
        }
        //for each DVirologist that is to be displayed create a button
        for(DVirologist dv: gameScene.getVisibleVirologists()){
            dv.setMapPanel(this);
            JButton virologistButton = dv.Draw();
            virologistButton.setName(dv.getId());
            virologistButton.setBounds(gameScene.getCurrentField().GetCoords().x + dv.GetCoords().x-10,
                    gameScene.getCurrentField().GetCoords().y + dv.GetCoords().y-10, 20,20);
            virologistButton.setBorder(new EmptyBorder(new Insets(0,0,0,0)));
            this.add(virologistButton, 1);
        }
        super.paint(g);
        double r = 35;
        //create lines between neighboring DFields
        for(DField df: gameScene.getVisibleFields()){
            df.setMapPanel(this);
            for(DField neighbor: df.GetNeighbors()){
                if(gameScene.getVisibleFields().contains(neighbor)){
                    int dx = neighbor.GetCoords().x - df.GetCoords().x;
                    int dy = neighbor.GetCoords().y - df.GetCoords().y;
                    int dl = (int)Math.sqrt((double)(dx * dx) + dy * dy);
                    double rx = dx * r / dl;
                    double ry = dy * r / dl;
                    int x1 = df.GetCoords().x + (int)rx;
                    int y1 = df.GetCoords().y + (int)ry;
                    int x2 = neighbor.GetCoords().x - (int)rx;
                    int y2 = neighbor.GetCoords().y - (int)ry;
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }

    /**
     * Sets the given DField as activeField
     * @param activeField the DField
     */
    public void setActiveFiled(DField activeField){
        if (this.activeField != null){
            this.activeField.setActivateStatus(false);
        }
        this.activeField = activeField;
    }

    /**
     * Sets the given Virologist as activeVirologist
     * @param activeVirologist the Virologist
     */
    public void setActiveVirologists(DVirologist activeVirologist){
        if (this.activeVirologist != null){
            this.activeVirologist.setActivateStatus(false);
        }
        this.activeVirologist = activeVirologist;
    }

    public DField getActiveField(){
        return this.activeField;
    }

    public DVirologist getActiveVirologist() {
        return this.activeVirologist;
    }
}
