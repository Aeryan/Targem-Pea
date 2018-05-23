import java.io.FileNotFoundException;
import java.util.ArrayList;

// Klass tekstifaili sisselugemiseks
public class Lugeja {
    public String failinimi;

    // Kui isendiloome käigus antakse parameetriga failinimi, loeb lugeja näitefaili asemel soovitavat faili
    public Lugeja() {
        this.failinimi = "Küsimused.txt";
    }

    public Lugeja(String failinimi) {
        this.failinimi = failinimi;
    }


    public ArrayList<Küsimus> loe() throws FileNotFoundException {

        java.io.File fail = new java.io.File(this.failinimi);
        java.util.Scanner sc = new java.util.Scanner(fail, "UTF-8");
        // Küsimuste list, mis hiljem tagastatakse
        ArrayList<Küsimus> küsimustik = new ArrayList<Küsimus>();

        // Kui failis on lugemata ridu, võtab Lugeja isend järgmise rea
        // Välimine "while"-kordus on tarvilik loodavate muutujate algväärtustamiseks
        while (sc.hasNextLine()) {

            ArrayList<ArrayList<String>> sõnestik = new ArrayList<ArrayList<String>>();
            ArrayList<String> variant = new ArrayList<String>();
            StringBuilder küss = new StringBuilder();
            String seisund = "küsimus";

            while (true) {
                String rida = sc.nextLine();
                /* Kui lugeja kohtab seisundivahetust märkivaid elemente, teeb ta vastava seisundivahetuse.
                Vastasel korral salvestatakse loetav rida vastavalt käesolevale seisundile. */
                if (rida.equals("@")) {
                    seisund = "parameetrid";
                }
                else if (rida.equals("@@")){
                    sõnestik.add(variant);
                    variant = new ArrayList<String>();
                    seisund = "parameetrid";
                }
                else if (rida.equals("@@@")){
                    sõnestik.add(variant);
                    Küsimus a = new Küsimus(küss.toString(), sõnestik);
                    küsimustik.add(a);
                    break;
                }
                else {
                    if (seisund.equals("küsimus")) {
                        küss.append(rida);
                        küss.append("\n");
                    }
                    else if (seisund.equals("parameetrid") && küss.toString().contains("&n&")) {
                        for (String i : rida.replace("\n", "").split("&n&")) {
                            variant.add(i);
                            seisund = "vastus";
                                                    }
                    }
                    else {
                        variant.add(rida);
                    }
                }
            }
        }
        return küsimustik;
    }
}