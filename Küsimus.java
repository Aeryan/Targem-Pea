import java.util.ArrayList;

// Klass, mille objektideks on küsimused koos parameetrite ja vastustega
public class Küsimus {
    private String küsimus;
    // List, mis hoiab üht komplekti parameetritest ja viimasel kohal neile vastavat vastust
    private ArrayList<ArrayList<String>> väärtused;

    public ArrayList<ArrayList<String>> getVäärtused() {
        return väärtused;
    }

    public String getKüsimus() {
        return küsimus;
    }

    public Küsimus(String küsimus, ArrayList<ArrayList<String>> väärtused) {
        this.küsimus = küsimus;
        this.väärtused = väärtused;
    }

    // Meetod, mis kontrollib, kas antud küsimusel leidub pakutav parameetrite komplekt
    // Tagastab vastuse, kui parameetrid sobivad, ja tühja sõne, kui antud parameetrite komplekti ei leidu
    String Kontrolli(String[] väärtustus) {
        for (ArrayList<String> j:väärtused) {
            for (int i = 0; i < väärtustus.length; i++) {
                if (!j.get(i).equals(väärtustus[i])) {
                    break;
                }
                if (i == väärtustus.length - 1) {
                    return j.get(j.size()-1);
                }
            }
        }
        return "";
    }
}