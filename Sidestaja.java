import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Sidestaja {

    public static boolean sidesta(ArrayList<Küsimus> eksam, String küsimus, TextField pakkumine, TextField vastus) {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("Küsimused.txt"), "UTF-8"))){
            StringBuilder sisu = new StringBuilder();
            for (Küsimus i: eksam) {
                if (i.getKüsimus().equals(küsimus)) {
                    ArrayList<String> lisand = new ArrayList<>(Arrays.asList(pakkumine.getText().split(";")));
                    lisand.add("Õige vastus on: " + vastus.getText());
                    i.getVäärtused().add(lisand);

                }
                sisu.append(i.getKüsimus() + "@\n");
                for (int j = 0; j < i.getVäärtused().size(); j ++) {
                    for (int k = 0; k < i.getVäärtused().get(j).size(); k++) {
                        sisu.append(i.getVäärtused().get(j).get(k));
                        if (k < i.getVäärtused().get(j).size()-2) {
                            sisu.append("&n&");
                        }
                        else if (k < i.getVäärtused().get(j).size() -1) {
                            sisu.append("\n");
                        }
                    }
                    if (j < i.getVäärtused().size()-1) {
                        sisu.append("\n@@\n");
                    }
                }
                sisu.append("\n@@@");
                if (eksam.indexOf(i) < eksam.size()-1) {
                    sisu.append("\n");
                }
            }

            bw.write(sisu.toString());
        }
        catch (IOException e) {
            System.out.println("Tekkis tõrge.");
        }
        return true;
    }
}
