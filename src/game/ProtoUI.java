package game;

import assets.Backpack;
import assets.field.Field;
import assets.virologist.Virologist;
import assets.virologist.VirologistBackpack;
import collectables.agent.Agent;
import collectables.equipment.Equipment;
import collectables.genome.Genome;
import game.Controller;

import java.io.*;

public class ProtoUI {
    static boolean godmode = false;    //after the game starts no more system commands are allowed
    public static void main(String[] args){
        Controller controller = new Controller(); //base controller
        // GrapUI ui= new
        final BufferedReader cbr = new BufferedReader(new InputStreamReader(System.in)); //BufferedReader for the console
        final PrintWriter cpw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true); //BufferedWriter for the console

        Run(controller, cpw, cbr);
    }

    private static void Run(Controller ct, PrintWriter pw, BufferedReader br) {
        boolean running = true;
        while(running){
            try {
                String input = br.readLine();
                String[] command = input.split("\\s+");
                switch (command[0]){
                    case "field":
                        if(godmode && (command.length == 3 || (command.length == 4 && command[1].equals("laboratory") || command[1].equals("bearlaboratory")))){
                            if (command[1].equals("laboratory") || command[1].equals("bearlaboratory")){
                                ct.CreateLaboratory(command[1], command[2], command[3]);
                                break;
                            }
                            ct.CreateField(command[1], command[2]);
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "neighbor":
                        if(godmode && command.length == 3){
                            ct.NeighborFields(command[1], command[2]);
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "virologist":
                        if(godmode && command.length == 3){
                            ct.CreateVirologist(command[1], command[2]);
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "put":
                        if(godmode && command.length == 4){
                            switch (command[1]) {
                                case "aminoacid":
                                    ct.PutAminoacidOnField(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case "nucleotide":
                                    ct.PutNucleotideOnField(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case "equipment":
                                    ct.PutEquipmentOnField(command[2], command[3]);
                                    break;
                            }
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "give":
                        if(godmode && command.length == 4) {
                            switch (command[1]) {
                                case "aminoacid":
                                    ct.GiveAminoacidToVirologist(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case "nucleotide":
                                    ct.GiveNucleotideToVirologist(Integer.parseInt(command[2]), command[3]);
                                    break;
                                case "equipment":
                                    ct.GiveEquipmentToVirologist(command[2], command[3]);
                                    break;
                                case "agent":
                                    ct.GiveAgentToVirologist(command[2], command[3]);
                                    break;
                            }
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "show":
                        if(command.length == 2){
                            switch (command[1]) {
                                case "virologist":
                                    Virologist virologist = ct.GetCurrentVirologist();
                                    ShowVirologist(virologist, pw);
                                    break;
                                case "field":
                                    Field field = ct.GetCurrentField();
                                    ShowField(field, pw);
                                    break;
                            }
                            break;
                        }
                        if(godmode && command.length == 3){
                            switch (command[1]) {
                                case "virologist":
                                    Virologist virologist = ct.GetVirologist(command[2]);
                                    ShowVirologist(virologist, pw);
                                    break;
                                case "field":
                                    assets.field.Field field = ct.GetField(command[2]);
                                    ShowField(field, pw);
                                    break;
                            }
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "effect":
                        if(godmode && command.length == 3){
                            ct.EffectVirologist(command[1], command[2]);
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "teach":
                        if(godmode && command.length == 3){
                            ct.TeachGenome(command[1], command[2]);
                            break;
                        }
                        pw.printf("command not allowed\n");
                        break;
                    case "runTest":
                        if(command.length == 2){
                            godmode = true;
                            int lenght=System.getProperty("user.dir").length();
                            String inputFileName,runOutputFileName,expectedOutputFileName;
                            if(System.getProperty("user.dir").startsWith("src", lenght-3)){
                                 inputFileName =System.getProperty("user.dir").substring(0,System.getProperty("user.dir").length()-4)+ "/TestInputs/" + command[1] + "_Input.txt";
                                 runOutputFileName = System.getProperty("user.dir").substring(0,System.getProperty("user.dir").length()-4)+ "/RunOutputs/" + command[1] + "_RunOutput.txt";
                            }
                           else {
                                 inputFileName =System.getProperty("user.dir")+ "/TestInputs/" + command[1] + "_Input.txt";
                                 runOutputFileName = System.getProperty("user.dir")+ "/RunOutputs/" + command[1] + "_RunOutput.txt";
                            }
                            Controller testCt = new Controller();
                            PrintWriter testPw = new PrintWriter(new BufferedWriter(new FileWriter(runOutputFileName)));
                            BufferedReader testBr = new BufferedReader(new FileReader(inputFileName));
                            Run(testCt, testPw, testBr);
                            testPw.close();
                            testBr.close();
                            BufferedReader actual = new BufferedReader(new FileReader(runOutputFileName));
                            if(System.getProperty("user.dir").startsWith("src", lenght-3)) {
                                expectedOutputFileName = System.getProperty("user.dir").substring(0, lenght - 4) + "/TestOutputs/" + command[1] + "_Output.txt";
                            }
                            else {
                                expectedOutputFileName = System.getProperty("user.dir") + "/TestOutputs/" + command[1] + "_Output.txt";
                            }
                            BufferedReader expected = new BufferedReader(new FileReader(expectedOutputFileName));
                            int FDL = CompareFiles(expected, actual); //First Different Line
                            expected.close();
                            actual.close();
                            if (FDL == -1) {
                                pw.printf("test succeeded\n");

                            } else {
                                pw.printf("test failed at line %d\n", FDL);
                            }
                            godmode = false;
                            break;
                        }
                        pw.printf("wrong command. use: runTest [name of the test]\n");
                        break;
                    case "new":
                        if(command.length == 1){
                            ct = new Controller();
                            ct.ImportMap("map.txt");
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
                            ct.Start();
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
                                Field field = ct.GetCurrentField();
                                ShowDirections(field, pw);
                                break;
                            }
                            if(command[1].equals("randomoff") && godmode){
                                ct.MoveVirologistRandomOff(Integer.parseInt(command[2]));
                                break;
                            }
                            ct.MoveVirologist(Integer.parseInt(command[1]));
                            break;
                        }
                        pw.printf("wrong command. use: move | move [fieldID]\n");
                        break;
                    case "drop":
                        if(command.length == 1 || command.length == 3){
                            if(command.length == 1){
                                Virologist virologist = ct.GetCurrentVirologist();
                                ShowVirologistBackpack(virologist, pw);
                                break;
                            }
                            switch (command[1]){
                                case "aminoacid":
                                    ct.DropAminoacid(Integer.parseInt(command[2]));
                                    break;
                                case "nucleotide":
                                    ct.DropNucleotide(Integer.parseInt(command[2]));
                                    break;
                                case "equipment":
                                    ct.DropEquipment(Integer.parseInt(command[2]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: drop [type] [quantity/index]\n");
                        break;
                    case "take":
                        if(command.length == 1 || command.length == 3){
                            if(command.length == 1){
                                Field field = ct.GetCurrentField();
                                ShowFieldBackpack(field, pw);
                                break;
                            }
                            switch (command[1]){
                                case "aminoacid":
                                    ct.TakeAminoacid(Integer.parseInt(command[2]));
                                    break;
                                case "nucleotide":
                                    ct.TakeNucleotide(Integer.parseInt(command[2]));
                                    break;
                                case "equipment":
                                    ct.TakeEquipment(Integer.parseInt(command[2]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: drop [type] [quantity/index]\n");
                        break;
                    case "steal":
                        if(command.length == 1 || command.length == 2 || command.length == 4){
                            if(command.length == 1){
                                Field field = ct.GetCurrentField();
                                ShowStealableVirologists(field, pw);
                                break;
                            }
                            if(command.length == 2){
                                Virologist virologist = ct.GetVirologist(command[1]);
                                ShowVirologistBackpack(virologist, pw);
                                break;
                            }
                            switch (command[2]){
                                case "aminoacid":
                                    ct.StealAminoacid(command[1], Integer.parseInt(command[3]));
                                    break;
                                case "nucleotide":
                                    ct.StealNucleotide(command[1], Integer.parseInt(command[3]));
                                    break;
                                case "equipment":
                                    ct.StealEquipment(command[1], Integer.parseInt(command[3]));
                                    break;
                            }
                            break;
                        }
                        pw.printf("wrong command. use: steal | steal [virologist] | steal [virologist] [type] [quantity/index]\n");
                        break;
                    case "learn":
                        if(command.length == 1){
                            ct.LearnGenome();
                            break;
                        }
                        pw.printf("wrong command. use: learn\n");
                        break;
                    case "create":
                        if(command.length == 1 || command.length == 2){
                            if(command.length == 1){
                                Virologist virologist = ct.GetCurrentVirologist();
                                ShowCreatable(virologist, pw);
                                break;
                            }
                            ct.CreateAgent(command[1]);
                            break;
                        }
                        pw.printf("wrong command. use: create | create [agent]\n");
                        break;
                    case "infect":
                        if(command.length == 1 || command.length == 2 || command.length == 3 || (command.length == 4 && command[1].equals("randomoff") && godmode)){
                            if(command.length == 1){
                                Field field = ct.GetCurrentField();
                                ShowVirologists(field, pw);
                                break;
                            }
                            if(command.length == 2){
                                Virologist virologist = ct.GetCurrentVirologist();
                                ShowAgents(virologist, pw);
                                break;
                            }
                            if(command[1].equals("randomoff") && godmode){
                                ct.InfectVirologistRandomOff(command[2], Integer.parseInt(command[3]));
                                break;
                            }
                            ct.InfectVirologist(command[1], Integer.parseInt(command[2]));
                            break;
                        }
                        pw.printf("wrong command. use: infect | infect [virologist] | infect [virologist] [agent]\n");
                        break;
                    case "kill":
                        if(command.length == 1 || command.length == 2){
                            if(command.length == 1){
                                Field field = ct.GetCurrentField();
                                ShowVirologists(field, pw);
                                break;
                            }
                            ct.KillVirologist(command[1]);
                            break;
                        }
                        pw.printf("wrong command. use: kill | kill [virologist]\n");
                        break;
                    case "endTurn":
                        if(command.length == 1){
                            ct.EndTurn();
                            pw.printf("turn ended\n%s's turn\n", ct.GetCurrentVirologist().GetName());
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



    private static void ShowBackpack(Backpack backpack, PrintWriter pw) {
        pw.printf("aminoacid: %d\nnucleotide: %d\nequipments:\n", backpack.GetAminos().size(), backpack.GetNucleotide().size());
        int index = 1;
        for(Equipment e : backpack.GetEquipments()){
            if(e.GetDurability() < 0)
                pw.printf("%d. %s\n", index, e.GetName());
            else
                pw.printf("%d. %s %d\n", index, e.GetName(), e.GetDurability());
            index++;
        }
    }
    /**
     * Show the Virologists the current Virologist can interact with.
     * @param field the field the Virologist is on.
     * @param pw the output will be written there
     */
    private static void ShowVirologists(Field field, PrintWriter pw) {
        for(Virologist v : field.GetVirologists()){
            pw.printf("%s\n", v.GetName());
        }
    }

    /**
     * Show the Virologists the current Virologist can steal from.
     * @param field the field the Virologist is on.
     * @param pw the output will be written there
     */
    private static void ShowStealableVirologists(Field field, PrintWriter pw) {
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
    private static void ShowCreatable(Virologist virologist, PrintWriter pw) {
        int aminoCount = virologist.GetBackpack().GetAminos().size();
        int nucleoCount = virologist.GetBackpack().GetNucleotide().size();
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
    private static void ShowAgents(Virologist virologist, PrintWriter pw) {
        int index = 1;
        for(Agent a : virologist.GetBackpack().GetAgents()){
            pw.printf("%d. %s %d\n", index, a.GetName(), a.getWarranty());
            index++;
        }
    }

    /**
     * Shows the items that are on the Field
     * @param field the Field the current Virologist is on
     * @param pw the output will be written there
     */
    private static void ShowFieldBackpack(Field field, PrintWriter pw) {
        Backpack backpack = field.GetBackpack();
        pw.printf("materials:\n");
        ShowBackpack(backpack, pw);
    }

    /**
     * Show the Materials and Equipments that are in a Virologist's backpack
     * @param virologist the Virologist who owns the Backpack
     * @param pw the output will be written there
     */
    private static void ShowVirologistBackpack(Virologist virologist, PrintWriter pw) {
        VirologistBackpack backpack = virologist.GetBackpack();
        ShowBackpack(backpack, pw);
    }

    /**
     * Shows the neighbors of a Field
     * @param field The Field the Current Virologist is on
     * @param pw the output will be written there
     */
    private static void ShowDirections(Field field, PrintWriter pw) {
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
    private static void ShowField(Field field, PrintWriter pw) {
        pw.printf("fieldID: %s\ntype: %s\n", field.GetFieldID(),field.GetType());
        if(field.GetGenome() != null){
            pw.printf("genome: %s\n", field.GetGenome().GetName());
        }
        pw.printf("virologists:\n");
        for(Virologist v : field.GetVirologists()){
            pw.printf("-%s\n", v.GetName());
        }
        ShowFieldBackpack(field, pw);
    }

    /**
     * Shows every important information about the Virologist
     * @param virologist the Virologist
     * @param pw the output will be written there
     */
    private static void ShowVirologist(Virologist virologist, PrintWriter pw) {
        pw.printf("name: %s\nfield: %s\n", virologist.GetName(), virologist.GetRoute().GetLocation().GetFieldID());
        String state = "";
        switch (virologist.GetState()){
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
        VirologistBackpack backpack = virologist.GetBackpack();
        pw.printf("backpack:\ncapacity: %d\n", backpack.GetCapacity());
        ShowBackpack(backpack, pw);

        pw.printf("agents:\n");
        int aIndex = 1;
        for(Agent a : backpack.GetAgents()){
            pw.printf("%d. %s %d\n", aIndex, a.GetName(), a.getWarranty());
            aIndex++;
        }
        pw.printf("applied agents:\n");
        for(Agent a : backpack.GetAppliedAgents()){
            if(a.getDuration() != -1)
                pw.printf("-%s %d\n", a.GetName(), a.getDuration());
            else
                pw.printf("-%s\n", a.GetName());
        }
    }

    /**
     * Compares the content of two files.
     * @param expected reader for the expected output
     * @param actual reader for the actual output
     * @return the first line of mismatch if the files are different. else -1
     * @throws IOException reading failed
     */
    private static int CompareFiles(BufferedReader expected, BufferedReader actual) throws IOException {
        int lineNumber = 1;
        String line1, line2;
        while ((line1 = expected.readLine()) != null) {
            line2 = actual.readLine();
            if (!line1.equals(line2)) {
                return lineNumber;
            }
            lineNumber++;
        }
        if (actual.readLine() == null) {
            return -1;
        }
        else {
            return lineNumber;
        }
    }
}
