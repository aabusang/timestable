import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, 1080, 800);

        primaryStage.setTitle("Project 1: Modulo Times Table");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
