package game.ui.game;

import assets.field.Field;
import assets.virologist.Virologist;
import game.Controller;
import game.ui.SceneLauncher;
import game.ui.game.map.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *  JFrame to contain the GamePanel
 */
public class GameScene extends JFrame {
    private final Controller controller;
    private final ArrayList<DField> dmap;
    private final ArrayList<DVirologist> dVirologists;

    /**
     *constructor of the GameScene
     */
    public GameScene(SceneLauncher sl, ArrayList<String> players){
        this.setTitle("Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SceneLauncher.GAME_WIDTH, SceneLauncher.GAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.dmap = new ArrayList<>();
        this.dVirologists = new ArrayList<>();
        controller = new Controller(); //make a new controller for the game
        controller.setSceneLauncher(sl);
        controller.importMap("map.txt");

        //if less than 4 player is playing deletes the unnecessary virologists
        while(players.size() < controller.getVirologists().size()){
            controller.getVirologists().get(controller.getVirologists().size()-1).GetRoute().GetLocation().GetVirologists().remove(controller.getVirologists().get(controller.getVirologists().size()-1));
            controller.getVirologists().remove(controller.getVirologists().size()-1);
        }
        //renames the virologists
        for (int i=0; i<players.size();i++){
            controller.getVirologists().get(i).SetName(players.get(i));
        }
        //creates DVirologists from the Virologists with the same ID
        for (int i=0; i<players.size();i++){
            Virologist v = controller.getVirologists().get(i);
            dVirologists.add(new DVirologist(v.GetName(), i+1));
        }
        //creates DFields from the Fields with the same ID
        for(Field f :controller.getMap()){
            String type = f.GetType();
            switch (type){
                case "normal":
                    dmap.add(new DNormal(f.getFieldID()));
                    break;
                case "laboratory":
                    dmap.add(new DLaboratory(f.getFieldID()));
                    break;
                case "bearlaboratory":
                    dmap.add(new DLaboratory(f.getFieldID())); //has the same graphics to fool players
                    break;
                case "warehouse":
                    dmap.add(new DWareHouse(f.getFieldID()));
                    break;
                case "shelter":
                    dmap.add(new DShelter(f.getFieldID()));
                    break;
            }
        }
        //reads the coords of the DFields
        for(int i = 0; i < dmap.size(); i++){
            dmap.get(i).SetCoords(controller.getFieldCoords().get(i));
        }
        //sets the neighbors of the DFields
        for(Field f :controller.getMap()){
            DField df1 = findDFieldByID(f.getFieldID());
            for(int d :f.GetDirections()){
                String id = f.GetNeighbour(d).getFieldID();
                DField df2 = findDFieldByID(id);
                if(df1 != null && df2 != null) {
                    df1.AddNeighbor(df2);
                }
            }
        }
        //starts the game and adds the GamePanel
        controller.start();
        this.add(new GamePanel(this, sl, players, controller));
        this.pack();
        this.setVisible(true);
    }

    /**
     * Finds the DField by ID
     * @param id the ID
     * @return the DField
     */
    private DField findDFieldByID(String id){
        for(DField d : dmap)
            if(d.getId().equals(id))
                return d;
        return null;
    }

    /**
     * Finds DVirologist by name
     * @param id the name of the Virologist
     * @return the Virologist
     */
    private DVirologist findDVirologistByName(String id){
        for(DVirologist d : dVirologists)
            if(d.getId().equals(id))
                return d;
        return null;
    }

    /**
     * Finds the visible Virologists by looking at the current Field
     * @return the visible Virologists
     */
    public List<DVirologist> getVisibleVirologists(){
        ArrayList<DVirologist> visible = new ArrayList<>();
        Field f = controller.getCurrentVirologist().GetRoute().GetLocation();
        for(Virologist v: f.GetVirologists()){
            visible.add(findDVirologistByName(v.GetName()));
        }
        return visible;
    }

    /**
     * Finds the visible Fields by looking at the Rout of the current Virologist
     * @return the visible Field
     */
    public List<DField> getVisibleFields(){
        Virologist v = controller.getCurrentVirologist();
        ArrayList<DField> visible = new ArrayList<>();
        for(Field f : v.GetRoute().fields){
            visible.add(findDFieldByID(f.getFieldID()));
        }
        DField actual = findDFieldByID(v.GetRoute().GetLocation().getFieldID());
        if(actual == null) {
            return List.of();
        }
        for(DField df : actual.GetNeighbors()){
            if(!visible.contains(df))
                visible.add(df);
        }
        return visible;
    }


    public DField getCurrentField(){
        return findDFieldByID(controller.getCurrentField().getFieldID());
    }

}
