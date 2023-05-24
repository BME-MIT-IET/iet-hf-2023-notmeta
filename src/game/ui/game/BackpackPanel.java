package game.ui.game;

import assets.virologist.VirologistBackpack;
import game.Controller;
import game.ui.SceneLauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 *Panel which prints out the content of the virologist's backpack and the effects whitch affects him
 */
public class BackpackPanel extends JPanel {
    private ArrayList<String> players;
    private SceneLauncher sceneLauncher;
    private GameScene gameScene;
    private final int backpackWidth = 200;
    private final int backpackHeight = 400;

    private Controller controller;


    /**
     *constructor of the BackpackPanel
      */
    public BackpackPanel(GameScene gameScene, SceneLauncher sl, ArrayList<String> players, Controller controller){
        this.gameScene = gameScene;
        this.sceneLauncher = sl;
        this.players = players;
        this.controller =controller;
        setPreferredSize(new Dimension(backpackWidth, backpackHeight));
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.green);
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }

    @Override
    public void paint(Graphics g) {
        drawPlayerBackpack(g);
    }

    /**
    * prints out the content of the backpack and the active effects
     */
    private void drawPlayerBackpack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        Font nicknameFont = new Font("Consolas", Font.PLAIN, 15);
        g2d.setFont(nicknameFont);
        FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());
        g2d.setStroke(new BasicStroke(5));
        g2d.draw(new Line2D.Float(0,0,800,0));
        g2d.drawString(controller.getVirologists().get(controller.getIndex()).GetName()+"'s backpack", 25, 20);

        VirologistBackpack backpack = controller.getVirologists().get(controller.getIndex()).GetBackpack();
        int linegap = 0;
        for (int i = 0; i < backpack.GetEquipments().size(); i++) {
            g2d.drawString(backpack.GetEquipments().get(i).GetName() + " " + backpack.GetEquipments().get(i).GetDurability(),
                    30,
                    40 + linegap*15);
            linegap++;
        }
        g2d.drawString("Aminoacids: " + backpack.GetAminos().size(), 30, 40 + linegap*15);
        linegap++;

        g2d.drawString("Nucleotide: " + backpack.GetNucleotide().size(), 30, 40 + linegap*15);
        linegap++;

        for (int i = 0; i < backpack.GetAgents().size(); i++) {
            g2d.drawString(backpack.GetAgents().get(i).GetName() + " " +backpack.GetAgents().get(i).getWarranty(),
                    30,
                    40 + linegap*15);
            linegap++;
        }


        g2d.drawString("Active effects", 25, 250);

        if(!(backpack.GetAppliedAgents().isEmpty())){
            for (int i=0; i<backpack.GetAppliedAgents().size(); i++){
                g2d.drawString(backpack.GetAppliedAgents().get(i).GetName()+": "
                        +backpack.GetAppliedAgents().get(i).getDuration()
                        +" turns left",
                        30, 265+i*15);
            }
        }

    }
}
