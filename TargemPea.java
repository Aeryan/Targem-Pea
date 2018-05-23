import javafx.application.Application;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class TargemPea extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        primaryStage.setTitle("Targem Pea");
        primaryStage.setMinWidth(640);
        primaryStage.setMinHeight(320);
        primaryStage.setWidth(640);
        primaryStage.setHeight(320);

        Group avajuur = new Group();


        // Kirjeldan avaStseeni, mis küsib kasutajalt küsimuse.
        final VBox avakas = new VBox();
        avakas.setFillWidth(true);
        avakas.setAlignment(Pos.CENTER);

        Label avang = new Label("Tere! Käesolev programm aitab leida vastuseid kõige piinavametele küsimustele, " +
                "mida kaastudengid on kohanud! Sisesta küsimuse osa:");
        final TextField avasisend = new TextField();
        Button edasi = new Button("Otsi");
        Button välju = new Button("Välju");

        avakas.setPrefWidth(primaryStage.getWidth());
        avakas.setLayoutY(60);
        avang.setTextAlignment(TextAlignment.CENTER);
        avang.setWrapText(true);

        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                avakas.setPrefWidth((Double) t1);
            }
        });

        avakas.getChildren().addAll(avang, avasisend, edasi, välju);
        avajuur.getChildren().add(avakas);

        final Scene avaStseen = new Scene(avajuur);
        primaryStage.setScene(avaStseen);

        edasi.addEventHandler(MouseEvent.MOUSE_PRESSED, new Pakkuja(primaryStage, avaStseen, avasisend));

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    primaryStage.close();
                }
            }
        });

        välju.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        primaryStage.show();

    }
}