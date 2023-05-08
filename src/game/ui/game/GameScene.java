package game.ui.game;

import assets.field.Field;
import assets.virologist.Virologist;
import game.Controller;
import game.ui.SceneLauncher;
import game.ui.game.map.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 *  JFrame to contain the GamePanel
 */
public class GameScene extends JFrame{
    private Controller controller;
    private ArrayList<DField> dmap;
    private ArrayList<DVirologist> dVirologists;

    /**
     *constructor of the GameScene
     */
    public GameScene(SceneLauncher sl, ArrayList<String> players){
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SceneLauncher.Gamewidth, SceneLauncher.Gameheight);
        this.setLocationRelativeTo(null);
        this.dmap = new ArrayList<>();
        this.dVirologists = new ArrayList<>();
        controller = new Controller(); //make a new controller for the game
        controller.SetSceneLauncher(sl);
        controller.ImportMap("map.txt");

        //if less than 4 player is playing deletes the unnecessary virologists
        while(players.size() < controller.GetVirologists().size()){
            controller.GetVirologists().get(controller.GetVirologists().size()-1).GetRoute().GetLocation().GetVirologists().remove(controller.GetVirologists().get(controller.GetVirologists().size()-1));
            controller.GetVirologists().remove(controller.GetVirologists().size()-1);
        }
        //renames the virologists
        for (int i=0; i<players.size();i++){
            controller.GetVirologists().get(i).SetName(players.get(i));
        }
        //creates DVirologists from the Virologists with the same ID
        for (int i=0; i<players.size();i++){
            Virologist v = controller.GetVirologists().get(i);
            dVirologists.add(new DVirologist(v.GetName(), i+1));
        }
        //creates DFields from the Fields with the same ID
        for(Field f :controller.GetMap()){
            String type = f.GetType();
            switch (type){
                case "normal":
                    dmap.add(new DNormal(f.GetFieldID()));
                    break;
                case "laboratory":
                    dmap.add(new DLaboratory(f.GetFieldID()));
                    break;
                case "bearlaboratory":
                    dmap.add(new DLaboratory(f.GetFieldID())); //has the same graphics to fool players
                    break;
                case "warehouse":
                    dmap.add(new DWareHouse(f.GetFieldID()));
                    break;
                case "shelter":
                    dmap.add(new DShelter(f.GetFieldID()));
                    break;
            }
        }
        //reads the coords of the DFields
        for(int i = 0; i < dmap.size(); i++){
            dmap.get(i).SetCoords(controller.GetFieldCoords().get(i));
        }
        //sets the neighbors of the DFields
        for(Field f :controller.GetMap()){
            DField df1 = FindDFieldByID(f.GetFieldID());
            for(int d :f.GetDirections()){
                String ID = f.GetNeighbour(d).GetFieldID();
                DField df2 = FindDFieldByID(ID);
                df1.AddNeighbor(df2);
            }
        }
        //starts the game and adds the GamePanel
        controller.Start();
        this.add(new GamePanel(this, sl, players, controller));
        this.pack();
        this.setVisible(true);
    }

    /**
     * Finds the DField by ID
     * @param ID the ID
     * @return the DField
     */
    private DField FindDFieldByID(String ID){
        for(DField d : dmap)
            if(d.GetID().equals(ID))
                return d;
        return null;
    }

    /**
     * Finds DVirologist by name
     * @param ID the name of the Virologist
     * @return the Virologist
     */
    private DVirologist FindDVirologistByName(String ID){
        for(DVirologist d : dVirologists)
            if(d.GetID().equals(ID))
                return d;
        return null;
    }

    /**
     * Finds the visible Virologists by looking at the current Field
     * @return the visible Virologists
     */
    public ArrayList<DVirologist> GetVisibleVirologists(){
        ArrayList<DVirologist> visible = new ArrayList<>();
        Field f = controller.GetCurrentVirologist().GetRoute().GetLocation();
        for(Virologist v: f.GetVirologists()){
            visible.add(FindDVirologistByName(v.GetName()));
        }
        return visible;
    }

    /**
     * Finds the visible Fields by looking at the Rout of the current Virologist
     * @return the visible Field
     */
    public ArrayList<DField> GetVisibleFields(){
        Virologist v = controller.GetCurrentVirologist();
        ArrayList<DField> visible = new ArrayList<>();
        for(Field f : v.GetRoute().fields){
            visible.add(FindDFieldByID(f.GetFieldID()));
        }
        DField actual = FindDFieldByID(v.GetRoute().GetLocation().GetFieldID());
        for(DField df : actual.GetNeighbors()){
            if(!visible.contains(df))
                visible.add(df);
        }
        return visible;
    }


    public DField GetCurrentField(){
        return FindDFieldByID(controller.GetCurrentField().GetFieldID());
    }

}
