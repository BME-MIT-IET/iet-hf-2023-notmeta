package game;

import assets.Backpack;
import assets.field.Field;
import assets.virologist.Virologist;
import assets.virologist.VirologistBackpack;
import collectables.agent.Agent;
import collectables.equipment.Equipment;
import collectables.genome.Genome;

import java.io.*;

public class ProtoUI {

    public static final String VIROLOGIST = "virologist";
    public static final String FIELD = "field";
    public static final String AMINO_ACID = "aminoacid";
    public static final String NUCLEOTIDE = "nucleotide";
    public static final String EQUIPMENT = "equipment";

    private static final String COMMAND_NOT_ALLOWED = "command not allowed\n";
    static boolean godmode = false;    //after the game starts no more system commands are allowed
    public static void main(String[] args){
        Controller controller = new Controller();
        final BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in)); //BufferedReader for the console
        final PrintWriter cpw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true); //BufferedWriter for the console

        run(controller, cpw, cbr);
    }

    private static void run(Controller ct, PrintWriter pw, BufferedReader br) {
        boolean running = true;
        while(running){
            try {
                String input = br.readLine();
                String[] command = input.split("\\s+");
                switch (command[0]){
                    case FIELD:
                        if(godmode && (command.length == 3 || (command.length == 4 && command[1].equals("laboratory") || command[1].equals("bearlaboratory")))){
                            if (command[1].equals("laboratory") || command[1].equals("bearlaboratory")){
                                ct.createLaboratory(command[1], command[2], command[3]);
                                break;
                            }
                            ct.createField(command[1], command[2]);
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "neighbor":
                        if(godmode && command.length == 3){
                            ct.neighborFields(command[1], command[2]);
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case VIROLOGIST:
                        if(godmode && command.length == 3){
                            ct.createVirologist(command[1], command[2]);
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "put":
                        if(godmode && command.length == 4){
                            switch (command[1]) {
                                case AMINO_ACID:
                                    ct.putAminoacidOnField(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case NUCLEOTIDE:
                                    ct.putNucleotideOnField(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case EQUIPMENT:
                                    ct.putEquipmentOnField(command[2], command[3]);
                                    break;
                            }
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "give":
                        if(godmode && command.length == 4) {
                            switch (command[1]) {
                                case AMINO_ACID:
                                    ct.giveAminoacidToVirologist(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case NUCLEOTIDE:
                                    ct.giveNucleotideToVirologist(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case EQUIPMENT:
                                    ct.giveEquipmentToVirologist(command[2], command[3]);
                                    break;
                                case "agent":
                                    ct.giveAgentToVirologist(command[2], command[3]);
                                    break;
                            }
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "show":
                        if(command.length == 2){
                            switch (command[1]) {
                                case VIROLOGIST:
                                    Virologist virologist = ct.getCurrentVirologist();
                                    showVirologist(virologist, pw);
                                    break;
                                case FIELD:
                                    Field field = ct.getCurrentField();
                                    showField(field, pw);
                                    break;
                            }
                            break;
                        }
                        if(godmode && command.length == 3){
                            switch (command[1]) {
                                case VIROLOGIST:
                                    Virologist virologist = ct.getVirologist(command[2]);
                                    showVirologist(virologist, pw);
                                    break;
                                case FIELD:
                                    assets.field.Field field = ct.getField(command[2]);
                                    showField(field, pw);
                                    break;
                            }
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "effect":
                        if(godmode && command.length == 3){
                            ct.effectVirologist(command[1], command[2]);
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "teach":
                        if(godmode && command.length == 3) {
                            ct.teachGenome(command[1], command[2]);
                            break;
                        }
                        pw.printf(COMMAND_NOT_ALLOWED);
                        break;
                    case "runTest":
                        if (command.length == 2) {
                            String userDir = "user.dir";
                            godmode = true;
                            int lenght=System.getProperty(userDir).length();
                            String inputFileName, runOutputFileName, expectedOutputFileName;
                            if(System.getProperty(userDir).startsWith("src", lenght-3)){
                                 inputFileName =System.getProperty(userDir).substring(0,System.getProperty(userDir).length()-4)+ "/TestInputs/" + command[1] + "_Input.txt";
                                 runOutputFileName = System.getProperty(userDir).substring(0,System.getProperty(userDir).length()-4)+ "/RunOutputs/" + command[1] + "_RunOutput.txt";
                            }
                           else {
                                 inputFileName =System.getProperty(userDir)+ "/TestInputs/" + command[1] + "_Input.txt";
                                 runOutputFileName = System.getProperty(userDir)+ "/RunOutputs/" + command[1] + "_RunOutput.txt";
                            }
                            Controller testCt = new Controller();
                            PrintWriter testPw = new PrintWriter(new BufferedWriter(new FileWriter(runOutputFileName)));
                            BufferedReader testBr = new BufferedReader(new FileReader(inputFileName));
                            run(testCt, testPw, testBr);
                            testPw.close();
                            testBr.close();
                            BufferedReader actual = new BufferedReader(new FileReader(runOutputFileName));
                            if(System.getProperty(userDir).startsWith("src", lenght-3)) {
                                expectedOutputFileName = System.getProperty(userDir).substring(0, lenght - 4) + "/TestOutputs/" + command[1] + "_Output.txt";
                            }
                            else {
                                expectedOutputFileName = System.getProperty(userDir) + "/TestOutputs/" + command[1] + "_Output.txt";
                            }
                            BufferedReader expected = new BufferedReader(new FileReader(expectedOutputFileName));
                            int firstDifferentLine = compareFiles(expected, actual);
                            expected.close();
                            actual.close();
                            if (firstDifferentLine == -1) {
                                pw.printf("test succeeded\n");

                            } else {
                                pw.printf("test failed at line %d\n", firstDifferentLine);
                            }
                            godmode = false;
                            break;
                        }
                        pw.printf("wrong command. use: runTest [name of the test]\n");
                        break;
                    case "new":
                        if(command.length == 1){
                            ct = new Controller();
                            ct.importMap("map.txt");
                            pw.printf("new game created\n");
                            break;
                        }
                        pw.printf("wrong command. use: new\n");
                        break;
                    case "load":
                        if(command.length == 2){
                            Controller loadedGame;
                            try {
                                String filename = "Saves/" + command[1] + ".ser";
                                ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
                                loadedGame = (Controller) in.readObject();
                                ct = loadedGame;
                                pw.printf("game loaded\n");
                                in.close();
                            } catch (IOException i) {
                                i.printStackTrace();
                                break;
                            } catch (ClassNotFoundException c) {
                                pw.printf("Controller class not found\n");
                                c.printStackTrace();
                                break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: load [name of the game]\n");
                        break;
                    case "save":
                        if(command.length == 2){
                            try{
                                String filename = "Saves/" + command[1] + ".ser";
                                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
                                out.writeObject(ct);
                                pw.printf("game saved\n");
                                out.close();
                            }
                            catch (IOException i){
                                i.printStackTrace();
                            }
                            break;
                        }
                        pw.printf("wrong command. use: save [name of the game]\n");
                        break;
                    case "start":
                        if(command.length == 1){
                            ct.start();
                            pw.printf("the game has started\n");
                            break;
                        }
                        pw.printf("wrong command. use: start\n");
                        break;
                    case "exit":
                        if(command.length == 1){
                            br.close();
                            pw.close();
                            running = false;
                            break;
                        }
                        pw.printf("wrong command. use: exit\n");
                        break;
                    case "move":
                        if(command.length == 1 || command.length == 2|| (command.length == 3 && command[1].equals("randomoff") && godmode )){
                            if(command.length == 1){
                                Field field = ct.getCurrentField();
                                showDirections(field, pw);
                                break;
                            }
                            if(command[1].equals("randomoff") && godmode){
                                ct.moveVirologistRandomOff(Integer.parseInt(command[2]));
                                break;
                            }
                            ct.moveVirologist(Integer.parseInt(command[1]));
                            break;
                        }
                        pw.printf("wrong command. use: move | move [fieldID]\n");
                        break;
                    case "drop":
                        if(command.length == 1 || command.length == 3){
                            if(command.length == 1){
                                Virologist virologist = ct.getCurrentVirologist();
                                showVirologistBackpack(virologist, pw);
                                break;
                            }
                            switch (command[1]){
                                case AMINO_ACID:
                                    ct.dropAminoacid(Integer.parseInt(command[2]));
                                    break;
                                case NUCLEOTIDE:
                                    ct.dropNucleotide(Integer.parseInt(command[2]));
                                    break;
                                case EQUIPMENT:
                                    ct.dropEquipment(Integer.parseInt(command[2]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: drop [type] [quantity/index]\n");
                        break;
                    case "take":
                        if(command.length == 1 || command.length == 3){
                            if(command.length == 1){
                                Field field = ct.getCurrentField();
                                showFieldBackpack(field, pw);
                                break;
                            }
                            switch (command[1]){
                                case AMINO_ACID:
                                    ct.takeAminoacid(Integer.parseInt(command[2]));
                                    break;
                                case NUCLEOTIDE:
                                    ct.takeNucleotide(Integer.parseInt(command[2]));
                                    break;
                                case EQUIPMENT:
                                    ct.takeEquipment(Integer.parseInt(command[2]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: drop [type] [quantity/index]\n");
                        break;
                    case "steal":
                        if(command.length == 1 || command.length == 2 || command.length == 4){
                            if(command.length == 1){
                                Field field = ct.getCurrentField();
                                showStealableVirologists(field, pw);
                                break;
                            }
                            if(command.length == 2){
                                Virologist virologist = ct.getVirologist(command[1]);
                                showVirologistBackpack(virologist, pw);
                                break;
                            }
                            switch (command[2]){
                                case AMINO_ACID:
                                    ct.stealAminoacid(command[1], Integer.parseInt(command[3]));
                                    break;
                                case NUCLEOTIDE:
                                    ct.stealNucleotide(command[1], Integer.parseInt(command[3]));
                                    break;
                                case EQUIPMENT:
                                    ct.stealEquipment(command[1], Integer.parseInt(command[3]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: steal | steal [virologist] | steal [virologist] [type] [quantity/index]\n");
                        break;
                    case "learn":
                        if(command.length == 1){
                            ct.learnGenome();
                            break;
                        }
                        pw.printf("wrong command. use: learn\n");
                        break;
                    case "create":
                        if(command.length == 1 || command.length == 2){
                            if(command.length == 1){
                                Virologist virologist = ct.getCurrentVirologist();
                                showCreatable(virologist, pw);
                                break;
                            }
                            ct.createAgent(command[1]);
                            break;
                        }
                        pw.printf("wrong command. use: create | create [agent]\n");
                        break;
                    case "infect":
                        if(command.length == 1 || command.length == 2 || command.length == 3 || (command.length == 4 && command[1].equals("randomoff") && godmode)){
                            if(command.length == 1){
                                Field field = ct.getCurrentField();
                                showVirologists(field, pw);
                                break;
                            }
                            if(command.length == 2){
                                Virologist virologist = ct.getCurrentVirologist();
                                showAgents(virologist, pw);
                                break;
                            }
                            if(command[1].equals("randomoff") && godmode){
                                ct.infectVirologistRandomOff(command[2], Integer.parseInt(command[3]));
                                break;
                            }
                            ct.infectVirologist(command[1], Integer.parseInt(command[2]));
                            break;
                        }
                        pw.printf("wrong command. use: infect | infect [virologist] | infect [virologist] [agent]\n");
                        break;
                    case "kill":
                        if(command.length == 1 || command.length == 2){
                            if(command.length == 1){
                                Field field = ct.getCurrentField();
                                showVirologists(field, pw);
                                break;
                            }
                            ct.killVirologist(command[1]);
                            break;
                        }
                        pw.printf("wrong command. use: kill | kill [virologist]\n");
                        break;
                    case "endTurn":
                        if(command.length == 1){
                            ct.endTurn();
                            pw.printf("turn ended\n%s's turn\n", ct.getCurrentVirologist().GetName());
                            break;
                        }
                        pw.printf("wrong command. use: endTurn\n");
                        break;
                    case "godmode":
                        if(command.length == 2){
                            if(command[1].equals("on")){
                                godmode = true;
                                pw.printf("godmode activated\n");
                                break;
                            }
                            if(command[1].equals("off")){
                                godmode = false;
                                pw.printf("godmode deactivated\n");
                                break;
                            }
                        }
                        pw.printf("wrong command. use: godmode [on/off]\n");
                        break;
                    default:
                        pw.printf("unknown command\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            br.close();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void showBackpack(Backpack backpack, PrintWriter pw) {
        pw.printf("aminoacid: %d\nnucleotide: %d\nequipments:\n", backpack.getAminos().size(), backpack.getNucleotide().size());
        int index = 1;
        for(Equipment e : backpack.getEquipments()){
            if(e.GetDurability() < 0)
                pw.printf("%d. %s\n", index, e.getName());
            else
                pw.printf("%d. %s %d\n", index, e.getName(), e.GetDurability());
            index++;
        }
    }
    /**
     * Show the Virologists the current Virologist can interact with.
     * @param field the field the Virologist is on.
     * @param pw the output will be written there
     */
    private static void showVirologists(Field field, PrintWriter pw) {
        for(Virologist v : field.GetVirologists()){
            pw.printf("%s\n", v.GetName());
        }
    }

    /**
     * Show the Virologists the current Virologist can steal from.
     * @param field the field the Virologist is on.
     * @param pw the output will be written there
     */
    private static void showStealableVirologists(Field field, PrintWriter pw) {
        for(Virologist v : field.GetVirologists()){
            if(v.GetGetStolenBehavior().CanStealForm())
                pw.printf("%s\n", v.GetName());
        }
    }

    /**
     * Shows the creatable Agents, their cost and the Materials the Virologist has.
     * @param virologist the current Virologist
     * @param pw the output will be written there
     */
    private static void showCreatable(Virologist virologist, PrintWriter pw) {
        int aminoCount = virologist.getBackpack().getAminos().size();
        int nucleoCount = virologist.getBackpack().getNucleotide().size();
        pw.printf("you have: \t%d aminoacid \t%d nucleotide\n", aminoCount, nucleoCount);
        for(Genome g : virologist.GetLearnedGenomes()){
            pw.printf("%s \t%d aminoacid \t%d nucleotide\n",g.GetName() ,g.getAminoCost(), g.getNucleoCost());
        }
    }

    /**
     * Shows the agents that a Virologist has.
     * @param virologist the current virologist
     * @param pw the output will be written there
     */
    private static void showAgents(Virologist virologist, PrintWriter pw) {
        int index = 1;
        for(Agent a : virologist.getBackpack().GetAgents()){
            pw.printf("%d. %s %d\n", index, a.getName(), a.getWarranty());
            index++;
        }
    }

    /**
     * Shows the items that are on the Field
     * @param field the Field the current Virologist is on
     * @param pw the output will be written there
     */
    private static void showFieldBackpack(Field field, PrintWriter pw) {
        Backpack backpack = field.getBackpack();
        pw.printf("materials:\n");
        showBackpack(backpack, pw);
    }

    /**
     * Show the Materials and Equipments that are in a Virologist's backpack
     * @param virologist the Virologist who owns the Backpack
     * @param pw the output will be written there
     */
    private static void showVirologistBackpack(Virologist virologist, PrintWriter pw) {
        VirologistBackpack backpack = virologist.getBackpack();
        showBackpack(backpack, pw);
    }

    /**
     * Shows the neighbors of a Field
     * @param field The Field the Current Virologist is on
     * @param pw the output will be written there
     */
    private static void showDirections(Field field, PrintWriter pw) {
        for(int d: field.GetDirections()){
            if(d == 0)
                pw.printf("0 - this\n");
            else
                pw.printf("%d - %s\n", d, field.GetNeighbour(d).GetType());
        }
    }

    /**
     * Shows every important information about the Field
     * @param field the Field
     * @param pw the output will be written there
     */
    private static void showField(Field field, PrintWriter pw) {
        pw.printf("fieldID: %s\ntype: %s\n", field.getFieldID(),field.GetType());
        if(field.GetGenome() != null){
            pw.printf("genome: %s\n", field.GetGenome().GetName());
        }
        pw.printf("virologists:\n");
        for(Virologist v : field.GetVirologists()){
            pw.printf("-%s\n", v.GetName());
        }
        showFieldBackpack(field, pw);
    }

    /**
     * Shows every important information about the Virologist
     * @param virologist the Virologist
     * @param pw the output will be written there
     */
    private static void showVirologist(Virologist virologist, PrintWriter pw) {
        pw.printf("name: %s\nfield: %s\n", virologist.GetName(), virologist.GetRoute().GetLocation().getFieldID());
        String state = "";
        switch (virologist.getState()){
            case KILLED:
                state = "killed";
                break;
            case BEFORE_MOVE:
                state = "before_move";
                break;
            case NOT_IN_TURN:
                state = "not_in_turn";
                break;
            case AFTER_ACTION:
                state = "after_action";
                break;
            case BEFORE_ACTION:
                state = "before_action";
        }
        pw.printf("state: %s\nlearned genomes:\n", state);
        for(Genome g :virologist.GetLearnedGenomes()){
            pw.printf("-%s\n", g.GetName());
        }
        VirologistBackpack backpack = virologist.getBackpack();
        pw.printf("backpack:\ncapacity: %d\n", backpack.GetCapacity());
        showBackpack(backpack, pw);

        pw.printf("agents:\n");
        int aIndex = 1;
        for(Agent a : backpack.GetAgents()){
            pw.printf("%d. %s %d\n", aIndex, a.getName(), a.getWarranty());
            aIndex++;
        }
        pw.printf("applied agents:\n");
        for(Agent a : backpack.GetAppliedAgents()){
            if(a.getDuration() != -1)
                pw.printf("-%s %d\n", a.getName(), a.getDuration());
            else
                pw.printf("-%s\n", a.getName());
        }
    }

    /**
     * Compares the content of two files.
     * @param expectedBuffer reader for the expected output
     * @param actualBuffer reader for the actual output
     * @return the first line of mismatch if the files are different. else -1
     * @throws IOException reading failed
     */
    private static int compareFiles(BufferedReader expectedBuffer, BufferedReader actualBuffer) throws IOException {
        int lineNumber = 1;
        String expected;
        String actual;
        while ((expected = expectedBuffer.readLine()) != null) {
            actual = actualBuffer.readLine();
            if (!expected.equals(actual)) {
                return lineNumber;
            }
            lineNumber++;
        }
        String lastLineRead = actualBuffer.readLine();
        return lastLineRead == null ? -1 : lineNumber;
    }
}
