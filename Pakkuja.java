import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Pakkuja implements EventHandler<MouseEvent>{

    private int counter;
    private static ArrayList<Küsimus> eksam;
    private static ArrayList<Küsimus> vasted = new ArrayList<Küsimus>();
    private Stage primaryStage;
    private TextField avasisend;
    private Scene avaStseen;

    public Pakkuja(Stage primaryStage, Scene avaStseen, TextField avasisend) {
        this.primaryStage = primaryStage;
        this.avasisend = avasisend;
        this.avaStseen = avaStseen;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {

        Lugeja logistik = new Lugeja();
        try {
            eksam = logistik.loe();
        }
        catch (FileNotFoundException e) {
            System.out.println("Keegi on tekstifaili ära kaotanud!");
            System.exit(0);
        }

        vasted = new ArrayList<Küsimus>();
        for (Küsimus i: eksam) {
            if (i.getKüsimus().toLowerCase().contains(avasisend.getText().toLowerCase())) {
                vasted.add(i);
            }
        }

        try {
            counter = 0;

            Button eelmine = new Button("Eelmine küsimus");
            Button tagasi = new Button("Tagasi");
            Button järgmine = new Button("Järgmine küsimus");

            final HBox nupud = new HBox(eelmine, tagasi, järgmine);
            nupud.setAlignment(Pos.CENTER);

            final Label küsimus = new Label(vasted.get(counter).getKüsimus().replace("&n&",
                    "___"));
            küsimus.setTextAlignment(TextAlignment.CENTER);
            küsimus.setPrefWidth(primaryStage.getWidth());
            küsimus.setWrapText(true);

            final Label pakkumisJuhend = new Label("Küsimuses on lüngad. Kirjuta vastuse saamiseks " +
                    "või uue vastuse lisamiseks lünkade sisu semikoolonitega eraldatult siia:");
            pakkumisJuhend.setTextAlignment(TextAlignment.CENTER);
            pakkumisJuhend.setPrefWidth(primaryStage.getWidth());
            pakkumisJuhend.setWrapText(true);

            final Label vastus = new Label("");
            final TextField parameetrid = new TextField();
            final Button paku = new Button("Paku");
            final Button lisa = new Button("Lisa vastus");

            final VBox pakkumised = new VBox(nupud, küsimus, vastus, lisa);

            if (vasted.get(counter).getVäärtused().size() > 1) {
                pakkumised.getChildren().removeAll(vastus, lisa);
                pakkumised.getChildren().addAll(pakkumisJuhend, parameetrid, paku, vastus, lisa);
            }

            pakkumised.setAlignment(Pos.CENTER);

            tagasi.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    primaryStage.setScene(avaStseen);
                }
            });

            eelmine.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    vastus.setText("");
                    parameetrid.setText("");
                    counter -= 1;
                    if (counter<0) {
                        counter = vasted.size()-1;
                    }
                    küsimus.setText(vasted.get(counter).getKüsimus().replace("&n&",
                            "___"));
                    if (vasted.get(counter).getVäärtused().size() > 1 &&
                            !pakkumised.getChildren().contains(pakkumisJuhend)) {
                        pakkumised.getChildren().removeAll(vastus, lisa);
                        pakkumised.getChildren().addAll(pakkumisJuhend, parameetrid, paku, vastus, lisa);
                    }
                    else if (vasted.get(counter).getVäärtused().size() == 1) {
                        vastus.setText(vasted.get(counter).getVäärtused().get(0).get(0));
                        if (pakkumised.getChildren().contains(pakkumisJuhend)) {
                            pakkumised.getChildren().removeAll(pakkumisJuhend, parameetrid, paku);
                        }
                    }
                }
            });
            järgmine.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    vastus.setText("");
                    parameetrid.setText("");
                    counter += 1;
                    if (counter == vasted.size()) {
                        counter = 0;
                    }
                    küsimus.setText(vasted.get(counter).getKüsimus().replace("&n&",
                            "___"));
                    if (vasted.get(counter).getVäärtused().size() > 1 &&
                            !pakkumised.getChildren().contains(pakkumisJuhend)) {
                        pakkumised.getChildren().removeAll(vastus, lisa);
                        pakkumised.getChildren().addAll(pakkumisJuhend, parameetrid, paku, vastus, lisa);
                    }
                    else if (vasted.get(counter).getVäärtused().size() == 1) {
                        vastus.setText(vasted.get(counter).getVäärtused().get(0).get(0));
                        if (pakkumised.getChildren().contains(pakkumisJuhend)) {
                            pakkumised.getChildren().removeAll(pakkumisJuhend, parameetrid, paku);
                        }
                    }
                }
            });

            paku.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    vastus.setText(vasted.get(counter).Kontrolli(parameetrid.getText().split(";")));
                }
            });

            parameetrid.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCharacter().equals("ENTER")) {
                        System.out.println("jee");
                    }
                }
            });

            lisa.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Label lisamisjuhend = new Label("Kirjuta siia pakutav vastus: ");
                    TextField lisamisvastus = new TextField();
                    Button sidemängija = new Button("Lisa vastus");
                    VBox lisamiskast = new VBox(lisamisjuhend, lisamisvastus, sidemängija);
                    lisamiskast.setAlignment(Pos.CENTER);
                    Scene puudumisStseen = new Scene(lisamiskast);
                    final Stage lisamine = new Stage();
                    lisamine.initModality(Modality.APPLICATION_MODAL);
                    lisamine.setResizable(false);
                    lisamine.setScene(puudumisStseen);
                    lisamine.show();

                    lisamine.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent keyEvent) {
                            if (keyEvent.getCode() == KeyCode.ESCAPE) {
                                lisamine.close();
                            }
                        }
                    });

                    sidemängija.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            while (true) {
                                if (Sidestaja.sidesta(eksam, vasted.get(counter).getKüsimus(),
                                        parameetrid, lisamisvastus)) {
                                    break;
                                }
                            }
                            lisamine.close();
                        }
                    });
                }
            });

            Group pakkumisjuur = new Group(pakkumised);
            Scene pakkumisStseen = new Scene(pakkumisjuur);

            primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    nupud.setPrefWidth((Double) t1);
                }
            });

            primaryStage.setScene(pakkumisStseen);
        }
        catch (IndexOutOfBoundsException e) {
            if(vasted.size() == 0) {
                // Kirjeldan akna, mis tekib tundmatu küsimusosa sisestamisel.
                final Stage puudub = new Stage();
                puudub.setResizable(false);
                puudub.initOwner(primaryStage);
                puudub.setTitle(":(");
                puudub.initModality(Modality.APPLICATION_MODAL);
                Button tagasi = new Button("):");
                tagasi.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        puudub.close();
                    }
                });
                VBox puudumiskast = new VBox(new Label("  Sellise sisuga küsimust ei leidunud.  "), tagasi);
                puudumiskast.setAlignment(Pos.CENTER);
                Scene puudumisStseen = new Scene(puudumiskast);
                puudub.setScene(puudumisStseen);
                puudub.show();

                puudub.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            puudub.close();
                        }
                    }
                });
            }
        }
    }
}
