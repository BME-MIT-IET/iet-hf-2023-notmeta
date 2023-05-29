package game.ui.game;

import assets.virologist.State;
import collectables.agent.Agent;
import collectables.equipment.Equipment;
import collectables.genome.Genome;
import collectables.material.Aminoacid;
import collectables.material.Nucleotide;
import game.Controller;
import game.ui.SceneLauncher;
import game.ui.StyledMenuButtonUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Panel which contains the buttons of actions.
 */
public class ActionPanel extends JPanel implements ActionListener {
    private ArrayList<String> players;
    private Controller controller;
    private SceneLauncher sceneLauncher;
    private GameScene gameScene;
    private BackpackPanel backpackPanel;
    private MapPanel mapPanel;
    private final int actionWidth = 600;
    private final int actionHeight = 400;
    private JLabel label;
    private JButton move;
    private JButton learn;
    private JButton pickUp;
    private JButton drop;
    private JButton create;
    private JButton infect;
    private JButton steal;
    private JButton kill;
    private JButton endTurn;

    /**
     *constructor of the ActionPanel
     */
    public ActionPanel(GameScene gameScene, SceneLauncher sl, ArrayList<String> players, Controller controller, BackpackPanel backpackPanel, MapPanel mapPanel) {
        this.gameScene = gameScene;
        this.sceneLauncher = sl;
        this.players = players;
        this.controller = controller;
        this.backpackPanel = backpackPanel;
        this.mapPanel = mapPanel;
        setPreferredSize(new Dimension(actionWidth, actionHeight));
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.cyan);
        this.setLayout(null);
        Font actionFont = new Font("Calibri", Font.PLAIN, 15);
        Color actionColor = new Color(0x2dce98);
        int width = 80;
        int xGap = 16;
        int height = 40;
        int yGap = 8;

        label = new JLabel("Actions");
        label.setFont(actionFont);
        label.setBounds(190, yGap, width, height);
        this.add(label);

        move = new JButton("Move");
        move.setFont(actionFont);
        move.addActionListener(this);
        move.setUI(new StyledMenuButtonUI());
        move.setBackground(actionColor);
        move.setBounds(xGap, 2 * yGap + height, width, height);
        move.setName("moveActionBtn");
        this.add(move);

        learn = new JButton("Learn");
        learn.setFont(actionFont);
        learn.addActionListener(this);
        learn.setUI(new StyledMenuButtonUI());
        learn.setBackground(actionColor);
        learn.setBounds(2 * xGap + width, 2 * yGap + height, width, height);
        this.add(learn);

        pickUp = new JButton("Pick up");
        pickUp.setFont(actionFont);
        pickUp.addActionListener(this);
        pickUp.setUI(new StyledMenuButtonUI());
        pickUp.setBackground(actionColor);
        pickUp.setBounds(3 * xGap + 2 * width, 2 * yGap + height, width, height);
        this.add(pickUp);

        drop = new JButton("Drop");
        drop.setFont(actionFont);
        drop.addActionListener(this);
        drop.setUI(new StyledMenuButtonUI());
        drop.setBackground(actionColor);
        drop.setBounds(4 * xGap + 3 * width, 2 * yGap + height, width, height);
        this.add(drop);

        create = new JButton("Create");
        create.setFont(actionFont);
        create.addActionListener(this);
        create.setUI(new StyledMenuButtonUI());
        create.setBackground(actionColor);
        create.setBounds(xGap, 3 * yGap + 2 * height, width, height);
        this.add(create);

        infect = new JButton("Infect");
        infect.setFont(actionFont);
        infect.addActionListener(this);
        infect.setUI(new StyledMenuButtonUI());
        infect.setBackground(actionColor);
        infect.setBounds(2 * xGap + width, 3 * yGap + 2 * height, width, height);
        this.add(infect);

        steal = new JButton("Steal");
        steal.setFont(actionFont);
        steal.addActionListener(this);
        steal.setUI(new StyledMenuButtonUI());
        steal.setBackground(actionColor);
        steal.setBounds(3 * xGap + 2 * width, 3 * yGap + 2 * height, width, height);
        this.add(steal);

        kill = new JButton("Kill");
        kill.setFont(actionFont);
        kill.addActionListener(this);
        kill.setUI(new StyledMenuButtonUI());
        kill.setBackground(actionColor);
        kill.setBounds(4 * xGap + 3 * width, 3 * yGap + 2 * height, width, height);
        this.add(kill);

        endTurn = new JButton("End turn");
        endTurn.setFont(actionFont);
        endTurn.addActionListener(this);
        endTurn.setUI(new StyledMenuButtonUI());
        endTurn.setBackground(actionColor);
        endTurn.setBounds(200 - width, 4 * yGap + 3 * height, 2 * width, height);
        this.add(endTurn);


    }

    /**
     * It overrides the paint method
     * @param g the graphics class
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Update();
    }

    /**
     * This method updates the states of all button.
     */
    public void Update() {
        State state = controller.getCurrentVirologist().getState();
        switch (state) {
            case BEFORE_MOVE:
                move.setEnabled(true);
                learn.setEnabled(false);
                pickUp.setEnabled(false);
                drop.setEnabled(false);
                create.setEnabled(false);
                infect.setEnabled(false);
                steal.setEnabled(false);
                kill.setEnabled(false);
                endTurn.setEnabled(true);
                break;
            case BEFORE_ACTION:
                move.setEnabled(false);
                learn.setEnabled(true);
                pickUp.setEnabled(true);
                drop.setEnabled(true);
                create.setEnabled(true);
                infect.setEnabled(true);
                steal.setEnabled(true);
                kill.setEnabled(true);
                endTurn.setEnabled(true);
                break;
            case AFTER_ACTION:
                move.setEnabled(false);
                learn.setEnabled(false);
                pickUp.setEnabled(false);
                drop.setEnabled(false);
                create.setEnabled(false);
                infect.setEnabled(false);
                steal.setEnabled(false);
                kill.setEnabled(false);
                endTurn.setEnabled(true);
                break;
            case NOT_IN_TURN:
                move.setEnabled(false);
                learn.setEnabled(false);
                pickUp.setEnabled(false);
                drop.setEnabled(false);
                create.setEnabled(false);
                infect.setEnabled(false);
                steal.setEnabled(false);
                kill.setEnabled(false);
                endTurn.setEnabled(false);
                break;
            default:
                endTurn.setEnabled(true);
        }
    }

    /**
     * This handles all the actions, get inputs from the player and call to the controllers methods.
     * @param e the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endTurn) {
            controller.endTurn();
            JOptionPane.showMessageDialog(null, "Start " + controller.getVirologists().get(controller.getIndex()).GetName() + "'s turn");
            gameScene.repaint();
        } else if (e.getSource() == move) {
            int dir = 0;
            if (mapPanel.getActiveField() == null)
                JOptionPane.showMessageDialog(null, "Select a field to move!");
            else {
                String id = mapPanel.getActiveField().getId();
                ArrayList<Integer> directions = controller.getCurrentField().GetDirections();
                for (Integer d : directions) {
                    if (controller.getCurrentField().GetNeighbour(d).getFieldID().equals(id)) {
                        dir = d;
                        break;
                    }
                }
                controller.moveVirologist(dir);
                mapPanel.setActiveFiled(null);
                gameScene.repaint();
            }
        } else if (e.getSource() == learn) {
            controller.learnGenome();
            gameScene.repaint();
        } else if (e.getSource() == pickUp) {
            ArrayList<Aminoacid> aminoList = controller.getCurrentField().getBackpack().getAminos();
            ArrayList<Nucleotide> nucleoList = controller.getCurrentField().getBackpack().getNucleotide();
            ArrayList<Equipment> equipmentList = controller.getCurrentField().getBackpack().getEquipments();
            String[] names = {"Aminoacid", "Nucleotide", "Equipment"};
            int x = JOptionPane.showOptionDialog(null, "Choose what you want to pick up!",
                    "Choose",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, names, names[0]);

            switch (x) {
                case 0:
                    JOptionPane optionPane = new JOptionPane();
                    if (aminoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There's no aminoacid on the field");
                        break;
                    } else {
                        JSlider slider = getSlider(optionPane, aminoList.size());
                        optionPane.setMessage(new Object[]{"Select a value of aminoacid's to pick up: ", slider});
                        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                        JDialog dialog = optionPane.createDialog(null, "My Slider");
                        dialog.setVisible(true);
                        int value = slider.getValue();
                        controller.takeAminoacid(value);
                        break;
                    }

                case 1:
                    JOptionPane optionPane2 = new JOptionPane();
                    if (nucleoList.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There's no nucleotid on the field");
                        break;
                    } else {
                        JSlider slider2 = getSlider(optionPane2, nucleoList.size());
                        optionPane2.setMessage(new Object[]{"Select a value of nucleotide's to pick up: ", slider2});
                        optionPane2.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        optionPane2.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                        JDialog dialog2 = optionPane2.createDialog(null, "Capacity");
                        dialog2.setVisible(true);
                        int value2 = slider2.getValue();
                        controller.takeNucleotide(value2);
                        break;
                    }
                case 2:
                    ArrayList<String> list = new ArrayList<>();
                    for (Equipment eq : equipmentList) {
                        list.add(eq.getName());
                    }
                    Object[] equipments = list.toArray();
                    if (equipments.length != 0) {
                        int index = JOptionPane.showOptionDialog(null, "Choose what you want to pick up!",
                                "Choose",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, equipments, equipments[0]);
                        controller.takeEquipment(index + 1);
                    } else {
                        JOptionPane.showMessageDialog(null, "There's no Equipment on the field");
                    }
                    break;
            }
            gameScene.repaint();
        } else if (e.getSource() == drop) {
            ArrayList<Aminoacid> aminoList = controller.getCurrentVirologist().getBackpack().getAminos();
            ArrayList<Nucleotide> nucleoList = controller.getCurrentVirologist().getBackpack().getNucleotide();
            ArrayList<Equipment> equipmentList = controller.getCurrentVirologist().getBackpack().getEquipments();
            String[] names = {"Aminoacid", "Nucleotide", "Equipment"};
            int x = JOptionPane.showOptionDialog(null, "Choose what you want to drop!",
                    "Choose",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, names, names[0]);

            switch (x) {
                case 0:
                    JOptionPane optionPane = new JOptionPane();
                    JSlider slider = getSlider(optionPane, aminoList.size());
                    optionPane.setMessage(new Object[]{"Select a value of aminoacid's to drop: ", slider});
                    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog = optionPane.createDialog(null, "My Slider");
                    dialog.setVisible(true);
                    int value = slider.getValue();
                    controller.dropAminoacid(value);
                    break;
                case 1:
                    JOptionPane optionPane2 = new JOptionPane();
                    JSlider slider2 = getSlider(optionPane2, nucleoList.size());
                    optionPane2.setMessage(new Object[]{"Select a value of nucleotide's to drop: ", slider2});
                    optionPane2.setMessageType(JOptionPane.QUESTION_MESSAGE);
                    optionPane2.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                    JDialog dialog2 = optionPane2.createDialog(null, "Capacity");
                    dialog2.setVisible(true);
                    int value2 = slider2.getValue();
                    controller.dropNucleotide(value2);
                    break;
                case 2:
                    ArrayList<String> list = new ArrayList<>();
                    for (Equipment eq : equipmentList) {
                        list.add(eq.getName());
                    }
                    Object[] equipments = list.toArray();
                    int index = JOptionPane.showOptionDialog(null, "Choose what you want to drop!",
                            "Choose",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, equipments, equipments[0]);

                    controller.dropEquipment(index + 1);
                    break;
            }
            gameScene.repaint();
        } else if (e.getSource() == create) {
            if (controller.getCurrentVirologist().GetLearnedGenomes().isEmpty())
                JOptionPane.showMessageDialog(null, "You can not create any agents yet");
            else {
                ArrayList<String> list = new ArrayList<>();
                for (Genome g : controller.getCurrentVirologist().GetLearnedGenomes()) {
                    String create = g.GetName() + " a:" + g.getAminoCost() + " n:" + g.getNucleoCost();
                    list.add(create);
                }
                Object[] options = list.toArray();
                int index = JOptionPane.showOptionDialog(null, "Choose what you want to create!",
                        "Choose",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                controller.createAgent(controller.getCurrentVirologist().GetLearnedGenomes().get(index).GetName());
                gameScene.repaint();
            }
        } else if (e.getSource() == infect) {
            if (mapPanel.getActiveVirologist() == null)
                JOptionPane.showMessageDialog(null, "Select a virologist to infect!");
            else if (controller.getCurrentVirologist().getBackpack().GetAgents().isEmpty())
                JOptionPane.showMessageDialog(null, "You have no agents.");
            else {
                ArrayList<String> list = new ArrayList<>();
                for (Agent a : controller.getCurrentVirologist().getBackpack().GetAgents()) {
                    String create = a.getName() + " warranty:" + a.getWarranty();
                    list.add(create);
                }
                Object[] options = list.toArray();
                int index = JOptionPane.showOptionDialog(null, "Choose the agent to infect with!",
                        "Choose",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                controller.infectVirologist(mapPanel.getActiveVirologist().GetVirologistName(), index + 1);
                gameScene.repaint();
            }
        } else if (e.getSource() == steal) {
            if (mapPanel.getActiveVirologist() == null)
                JOptionPane.showMessageDialog(null, "Select a virologist to steal from!");
            else {
                ArrayList<Aminoacid> aminoList = controller.getVirologist(mapPanel.getActiveVirologist().GetVirologistName()).getBackpack().getAminos();
                ArrayList<Nucleotide> nucleoList = controller.getVirologist(mapPanel.getActiveVirologist().GetVirologistName()).getBackpack().getNucleotide();
                ArrayList<Equipment> equipmentList = controller.getVirologist(mapPanel.getActiveVirologist().GetVirologistName()).getBackpack().getEquipments();
                String[] names = {"Aminoacid", "Nucleotide", "Equipment"};
                int x = JOptionPane.showOptionDialog(null, "Choose what you want to steal!",
                        "Choose",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, names, names[0]);

                switch (x) {
                    case 0:
                        JOptionPane optionPane = new JOptionPane();
                        JSlider slider = getSlider(optionPane, aminoList.size());
                        optionPane.setMessage(new Object[]{"Select a value of aminoacid's to steal: ", slider});
                        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                        JDialog dialog = optionPane.createDialog(null, "My Slider");
                        dialog.setVisible(true);
                        int value = slider.getValue();
                        controller.stealAminoacid(mapPanel.getActiveVirologist().GetVirologistName(), value);
                        break;
                    case 1:
                        JOptionPane optionPane2 = new JOptionPane();
                        JSlider slider2 = getSlider(optionPane2, nucleoList.size());
                        optionPane2.setMessage(new Object[]{"Select a value of nucleotide's to steal: ", slider2});
                        optionPane2.setMessageType(JOptionPane.QUESTION_MESSAGE);
                        optionPane2.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                        JDialog dialog2 = optionPane2.createDialog(null, "Capacity");
                        dialog2.setVisible(true);
                        int value2 = slider2.getValue();
                        controller.stealNucleotide(mapPanel.getActiveVirologist().GetVirologistName(), value2);
                        break;
                    case 2:
                        ArrayList<String> list = new ArrayList<>();
                        for (Equipment eq : equipmentList) {
                            list.add(eq.getName());
                        }
                        Object[] equipments = list.toArray();
                        int index = JOptionPane.showOptionDialog(null, "Choose what you want to steal!",
                                "Choose",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, equipments, equipments[0]);

                        controller.stealEquipment(mapPanel.getActiveVirologist().GetVirologistName(), index + 1);
                        break;
                }
                gameScene.repaint();
            }

        } else if (e.getSource() == kill) {
            if (mapPanel.getActiveVirologist() == null)
                JOptionPane.showMessageDialog(null, "Select a virologist to kill!");
            else {
                controller.killVirologist(mapPanel.getActiveVirologist().GetVirologistName());
                gameScene.repaint();
            }
        }
    }

    /**
     * This method create a new slider between the value of 0 and max.
     * @param optionPane the optinoPane
     * @param max the maximum value on the slider
     * @return the new slider
     */
    static JSlider getSlider(final JOptionPane optionPane, int max) {
        JSlider slider = new JSlider(0, max);
        slider.setMajorTickSpacing(2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider theSlider = (JSlider) changeEvent.getSource();
                if (!theSlider.getValueIsAdjusting()) {
                    optionPane.setInputValue(theSlider.getValue());
                }
            }
        };
        slider.addChangeListener(changeListener);
        return slider;
    }
}
