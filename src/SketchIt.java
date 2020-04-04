import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class SketchIt extends Application {



    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SketchIT.fxml"));
        Parent root = fxmlLoader.load();
        MainUI UIcontrol = fxmlLoader.getController();

        Model model = new Model();

        SketchCanvas sketchCanvas = new SketchCanvas(model, UIcontrol);
        sketchCanvas.initialize();
        UIcontrol.sketchCanvas = sketchCanvas;
        UIcontrol.model = model;


        stage.setMinHeight(200);
        stage.setMinWidth(300);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if(!Double.isNaN(oldVal.doubleValue())) {
                sketchCanvas.resizeCanvasWidth(oldVal.doubleValue(), newVal.doubleValue());
            }
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if(!Double.isNaN(oldVal.doubleValue())) {
                sketchCanvas.resizeCanvasHeight(oldVal.doubleValue(), newVal.doubleValue());
            }
        });


        Scene scene = new Scene(root);
        scene.setOnKeyPressed(sketchCanvas::handleKeyEvent);

        stage.setScene(scene);
        stage.show();
    }

}
