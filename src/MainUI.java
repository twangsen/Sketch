import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.File;

public class MainUI {
    @FXML ToggleButton thickness1,thickness2,thickness3,thickness4,dash1,dash2,dash3,dash4;
    @FXML
    ImageView selectTool, eraserTool, lineTool, circleTool, recTool, fillTool,resetLine,resetFill;

    @FXML
    Canvas canvas;
    @FXML
    Line dashline1,dashline2,dashline3,dashline4,thick1,thick2,thick3,thick4;

    @FXML
    ColorPicker lineColourPicker,fillColourPicker;

    @FXML
    VBox vBox;
    private Image selectImage, selectImage_b,eraserImage,eraserImage_b, lineImage,lineImage_b,circleImage, circleImage_b,
    recImage, recImage_b,fillImage,fillImage_b;

    SketchCanvas sketchCanvas;
    Model model;

    FileChooser fileChooser = new FileChooser();

    public void initialize() {
        setImage();
        canvas.setHeight(550);
        canvas.setWidth(730);
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        dashline1.getStrokeDashArray().addAll(10d,0d);
        dashline2.getStrokeDashArray().addAll(10d,1d);
        dashline3.getStrokeDashArray().addAll(10d,4d);
        dashline4.getStrokeDashArray().addAll(10d,7d);
        resetColor();

        resetLine.setImage( new Image(getClass().getClassLoader().getResource("images/reset.png").toString()));
        resetFill.setImage( new Image(getClass().getClassLoader().getResource("images/reset.png").toString()));

        thickness1.setSelected(true);
        dash1.setSelected(true);

        fileChooser.setInitialDirectory(new File("./"));

    }


    public void setImage() {
        String a = getClass().getClassLoader().getResource("images/select.png").toString();
        String b = getClass().getClassLoader().getResource("images/eraser.png").toString();
        String c = getClass().getClassLoader().getResource("images/line.png").toString();
        String d = getClass().getClassLoader().getResource("images/circle.png").toString();
        String e = getClass().getClassLoader().getResource("images/rec.png").toString();
        String f = getClass().getClassLoader().getResource("images/fill.png").toString();
        selectImage = new Image(a);
        eraserImage = new Image(b);
        lineImage = new Image(c);
        circleImage = new Image(d);
        recImage = new Image(e);
        fillImage = new Image(f);
        a = getClass().getClassLoader().getResource("images/select_b.png").toString();
        b = getClass().getClassLoader().getResource("images/eraser_b.png").toString();
        c = getClass().getClassLoader().getResource("images/line_b.png").toString();
        d = getClass().getClassLoader().getResource("images/circle_b.png").toString();
        e = getClass().getClassLoader().getResource("images/rec_b.png").toString();
        f = getClass().getClassLoader().getResource("images/fill_b.png").toString();
        selectImage_b = new Image(a);
        eraserImage_b = new Image(b);
        lineImage_b = new Image(c);
        circleImage_b = new Image(d);
        recImage_b = new Image(e);
        fillImage_b = new Image(f);
        startImage();
    }

    private void startImage(){
        selectTool.setImage(selectImage);
        eraserTool.setImage(eraserImage);
        lineTool.setImage(lineImage);
        circleTool.setImage(circleImage);
        recTool.setImage(recImage);
        fillTool.setImage(fillImage);
    }

    private void greyImageAll(){
        selectTool.setImage(selectImage_b);
        eraserTool.setImage(eraserImage_b);
        lineTool.setImage(lineImage_b);
        circleTool.setImage(circleImage_b);
        recTool.setImage(recImage_b);
        fillTool.setImage(fillImage_b);
    }



    public Canvas getCanvas(){
        return canvas;
    }

    public void clickSelect(){
        enableAllToggle();
        enableLine();
        enableFill();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.SelectTool;
        greyImageAll();
        selectTool.setImage(selectImage);
    }

    public void clickEraser(){
        unselect();
        disableAllToggle();
        disableFill();
        disableLine();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.EraseTool;
        greyImageAll();
        eraserTool.setImage(eraserImage);
    }

    public void clickLine(){
        unselect();
        enableAllToggle();
        disableFill();
        enableLine();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.LineTool;
        greyImageAll();
        lineTool.setImage(lineImage);
    }

    public void clickCircle(){
        unselect();
        enableAllToggle();
        enableFill();
        enableLine();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.CircleTool;
        greyImageAll();
        circleTool.setImage(circleImage);
    }

    public void clickRect(){
        unselect();
        enableAllToggle();
        enableFill();
        enableLine();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.RectangleTool;
        greyImageAll();
        recTool.setImage(recImage);
    }

    public void clickFill(){
        unselect();
        disableAllToggle();
        disableLine();
        enableFill();
        this.sketchCanvas.currentTool = SketchCanvas.toolMode.FillTool;
        greyImageAll();
        fillTool.setImage(fillImage);
    }

    public void lineThick1(){
        this.sketchCanvas.setThickness(1);
    }
    public void lineThick2(){
        this.sketchCanvas.setThickness(3);
    }
    public void lineThick3(){
        this.sketchCanvas.setThickness(5);
    }
    public void lineThick4(){
        this.sketchCanvas.setThickness(7);
    }
    public void lineDash1(){
        this.sketchCanvas.setDashes(0d);
    }
    public void lineDash2(){
        this.sketchCanvas.setDashes(1d);
    }
    public void lineDash3(){
        this.sketchCanvas.setDashes(4d);
    }
    public void lineDash4(){
        this.sketchCanvas.setDashes(7d);
    }


    public void setLineColour(){
        this.sketchCanvas.setLineColour(lineColourPicker.getValue());
    }

    public void setFillColour(){
        this.sketchCanvas.setFillColour(fillColourPicker.getValue());
    }

    public void resetColor(){
        lineColourPicker.setValue(Color.BLACK);
        fillColourPicker.setValue(Color.TRANSPARENT);

    }

    public void resetLineClick(){
        lineColourPicker.setValue(Color.BLACK);
        setLineColour();
    }

    public void resetFillClick(){
        fillColourPicker.setValue(Color.TRANSPARENT);
        setFillColour();
    }

    public void setToSelected(){
        Shapes shapes = model.getSelectedShape();
        if (shapes == null){
            return;
        }
        lineColourPicker.setValue(shapes.lineColor);
        if(shapes.lineDashes == 0d){
            dash1.setSelected(true);
        } else if(shapes.lineDashes == 1d){
            dash2.setSelected(true);
        } else if(shapes.lineDashes == 4d){
            dash3.setSelected(true);
        } else if(shapes.lineDashes == 7d){
            dash4.setSelected(true);
        }

        if(shapes.lineThickness == 1){
            thickness1.setSelected(true);
        } else if(shapes.lineThickness == 3){
            thickness2.setSelected(true);
        } else if(shapes.lineThickness == 5){
            thickness3.setSelected(true);
        } else if(shapes.lineThickness == 7){
            thickness4.setSelected(true);
        }
        fillColourPicker.setDisable(false);

        if(shapes instanceof Lines){
            //resetFillClick();
            fillColourPicker.setDisable(true);
        } else {
            fillColourPicker.setValue(shapes.fillColor);
        }

    }

    public void disableAllToggle(){
        thickness1.setDisable(true);
        thickness2.setDisable(true);
        thickness3.setDisable(true);
        thickness4.setDisable(true);
        dash1.setDisable(true);
        dash2.setDisable(true);
        dash3.setDisable(true);
        dash4.setDisable(true);
    }

    public void enableAllToggle(){
        thickness1.setDisable(false);
        thickness2.setDisable(false);
        thickness3.setDisable(false);
        thickness4.setDisable(false);
        dash1.setDisable(false);
        dash2.setDisable(false);
        dash3.setDisable(false);
        dash4.setDisable(false);
    }

    public void disableFill(){
        fillColourPicker.setDisable(true);
        resetFill.setDisable(true);
    }
    public void disableLine(){
        lineColourPicker.setDisable(true);
        resetLine.setDisable(true);
    }

    public void enableLine(){
        lineColourPicker.setDisable(false);
        resetLine.setDisable(false);
    }

    public void enableFill(){
        fillColourPicker.setDisable(false);
        resetFill.setDisable(false);
    }

    private void unselect(){
        model.unselectAll();
        sketchCanvas.clearAndDraw();
    }

    public void newFile(){
        notSavedDialog();

        model.resetNew();
        sketchCanvas.clearAndDraw();
        sketchCanvas.initialHistory();
    }
    public void loadFile(){
        notSavedDialog();
        Window window = vBox.getScene().getWindow();
        fileChooser.setTitle("Load File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","*.txt"));
        File file = fileChooser.showOpenDialog(window);
        if (file == null) {
            return;
        } else {
            model.load(file, canvas.getWidth(),canvas.getHeight());
            sketchCanvas.clearAndDraw();
        }
        sketchCanvas.initialHistory();


    }
    public void saveFile(){
        Window window = vBox.getScene().getWindow();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("sketch");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file","*.txt"));

        File file = fileChooser.showSaveDialog(window);
        if (file == null) {
           return;
        } else {
            model.save(file);
        }


    }
    public void quit(){
        notSavedDialog();
        System.exit(0);
    }

    public void undo(){
        sketchCanvas.undo();
    }

    public void redo(){
        sketchCanvas.redo();
    }

    public void notSavedDialog(){
        if(model.needSave()) {
            Window stage = vBox.getScene().getWindow();
            Alert dlg = new Alert(Alert.AlertType.CONFIRMATION, "");
            dlg.initModality(Modality.APPLICATION_MODAL);
            dlg.initOwner(stage);
            dlg.setTitle("Sketch not Saved");
            String optionalMasthead = "Your sketch is not saved.";
            dlg.getDialogPane().setContentText("Do you want to save them before continue?");
            dlg.getDialogPane().setHeaderText(optionalMasthead);
            ((Button) dlg.getDialogPane().lookupButton(ButtonType.OK)).setText("Save");
            ((Button) dlg.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
            dlg.showAndWait().ifPresent(result -> {
                //System.out.println("Result is " + result);
                if(result.getText().equals("OK")){
                    saveFile();
                }
            });
        }
    }




}
