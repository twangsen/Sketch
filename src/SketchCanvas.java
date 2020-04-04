import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class SketchCanvas {
    enum toolMode{CircleTool, RectangleTool, EraseTool, SelectTool, LineTool, FillTool, Empty}

    Canvas canvas;
    Model model;
    toolMode currentTool = toolMode.Empty;

    Shapes tempShape;
    MainUI mainUI;
    boolean dragged = false;

    int lineThickness = 1;
    double lineDashes = 0;
    Color fillColour = Color.TRANSPARENT;
    Color lineColour = Color.BLACK;

    double oldX, oldY;
    ArrayList<Model> history;
    int timelineIndex;


    SketchCanvas(Model model,MainUI mainUI){
        this.model = model;
        this.mainUI = mainUI;
        this.canvas = mainUI.getCanvas();
    }

    public void initialize() {

        this.canvas.setHeight(550);
        this.canvas.setWidth(730);
        this.model.canvasHeight = canvas.getHeight();
        this.model.canvasWidth = canvas.getWidth();
        this.canvas.getGraphicsContext2D().setFill(Color.WHITE);
        this.canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        this.model.gc = this.canvas.getGraphicsContext2D();

        this.canvas.setOnMousePressed(this::handlePressed);

        this.canvas.setOnMouseReleased(this::handleReleased);
        this.canvas.setOnMouseDragged(this::handleDragged);
        this.canvas.setOnMouseClicked(this::handleClicked);
        //this.canvas.setOnKeyPressed(this::handleKeyEvent);
        this.initialHistory();

    }

    private void handlePressed(MouseEvent mouseEvent){
        model.setUnchanged();
        oldX = mouseEvent.getX();
        oldY = mouseEvent.getY();
        //System.out.println("pressed");
        switch (this.currentTool){
            case CircleTool:
                this.tempShape = new Ovals(mouseEvent.getX(), mouseEvent.getY(),0,0,
                        this.fillColour,this.lineThickness,this.lineColour,this.lineDashes);
                break;
            case RectangleTool:
                this.tempShape = new Rectangles(mouseEvent.getX(), mouseEvent.getY(),0,0,
                        this.fillColour,this.lineThickness,this.lineColour,this.lineDashes);
                break;
            case LineTool:
                this.tempShape = new Lines(mouseEvent.getX(), mouseEvent.getY(),0,0,
                        this.lineThickness,this.lineColour,this.lineDashes);
                break;
            case SelectTool:
                this.model.setSelect(mouseEvent.getX(), mouseEvent.getY());
                clearCanvas();
                mainUI.setToSelected();
                this.model.draw();
        }
    }

    private void handleDragged(MouseEvent mouseEvent){
        this.dragged = true;
        if(currentTool == toolMode.CircleTool|| currentTool == toolMode.RectangleTool || currentTool == toolMode.LineTool) {
            this.tempShape.setEnd(mouseEvent.getX(), mouseEvent.getY());
            clearAndDraw();
            //System.out.println("current" + this.currentTool.toString());
            this.tempShape.draw(canvas.getGraphicsContext2D());
        } else if(currentTool == toolMode.SelectTool){
            this.model.moveSelected(mouseEvent.getX() - oldX,mouseEvent.getY() - oldY);
            oldX = mouseEvent.getX();
            oldY = mouseEvent.getY();
            clearAndDraw();
        }
    }

    public void handleReleased(MouseEvent mouseEvent){
        if(currentTool == toolMode.CircleTool|| currentTool == toolMode.RectangleTool || currentTool == toolMode.LineTool) {
            if (this.dragged) {
                this.model.add(tempShape);
                clearAndDraw();
                }
        }
        this.dragged = false;
        //System.out.println("released");
    }


    public void handleClicked(MouseEvent mouseEvent){

        if(currentTool == toolMode.EraseTool){
            model.erase(mouseEvent.getX(),mouseEvent.getY());
            clearAndDraw();
        } else if(currentTool == toolMode.FillTool){
            model.fill(mouseEvent.getX(),mouseEvent.getY(),fillColour);
            clearAndDraw();
        }

        if(model.actionChanged()){
            addNewModel();
        }

    }

    public void handleKeyEvent(KeyEvent e){
        //System.out.println("entered");
        if(currentTool == toolMode.SelectTool) {
            if (e.getCode().equals(KeyCode.ESCAPE)) {
                model.unselectAll();
                clearAndDraw();
            } else if (e.getCode().equals(KeyCode.DELETE) || e.getCode().equals(KeyCode.BACK_SPACE)) {
                model.deleteSelected();
                model.unselectAll();
                clearAndDraw();
                //System.out.println(model.actionChanged());
                if(model.actionChanged()){
                    addNewModel();
                }
            }
        }
        if(e.getCode().equals(KeyCode.R)){
            redo();
        } else if(e.getCode().equals(KeyCode.U)){
            undo();
        }
    }



    private void clearCanvas(){
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public void resizeCanvasHeight(Double oldVal, Double newVal){
        double oldCan = canvas.getHeight();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.setHeight(canvas.getHeight() + (newVal - oldVal));
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //System.out.println("Width haha: " + oldVal + " " + newVal + "Canvas:" + canvas.getHeight() +" "+ canvas.getWidth());
        double newCan = canvas.getHeight();

        model.canvasHeight = canvas.getHeight();
        model.resizeShapesHeight(oldCan,newCan);

        for(Model node: this.history){
            resizeSelf(node);
        }
    }

    public void resizeCanvasWidth(Double oldVal, Double newVal){
        double oldCan = canvas.getWidth();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.setWidth(canvas.getWidth() + (newVal - oldVal));
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //System.out.println("Height: " + oldVal + " " + newVal + "Canvas:" + canvas.getHeight() +" "+ canvas.getWidth());
        double newCan = canvas.getWidth();

        model.canvasWidth = canvas.getWidth();
        model.resizeShapesWidth(oldCan,newCan);

        for(Model node: this.history){
            resizeSelf(node);
        }
    }

    public void setThickness(int x){
        this.lineThickness= x;
        if(currentTool == toolMode.SelectTool){
            model.changeSelectedThickness(x);
            clearAndDraw();
            if(model.actionChanged()){
                addNewModel();
            }
        }
    }

    public void setDashes(double d){
        this.lineDashes = d;
        if(currentTool == toolMode.SelectTool){
            model.changeSelectedDashes(d);
            clearAndDraw();
            if(model.actionChanged()){
                addNewModel();
            }
        }
    }

    public void setLineColour(Color color){
        this.lineColour = color;
        if(currentTool == toolMode.SelectTool){
            model.changeSelectedLineColor(color);
            clearAndDraw();
            if(model.actionChanged()){
                addNewModel();
            }
        }
    }

    public void setFillColour(Color color){
        this.fillColour = color;
        if(currentTool == toolMode.SelectTool){
            model.changeSelectedFillColor(color);
            clearAndDraw();
            if(model.actionChanged()){
                addNewModel();
            }
        }

    }

    public void clearAndDraw(){
        clearCanvas();
        model.draw();
    }


    public void initialHistory(){
        this.history = new ArrayList<>();
        this.history.add(this.model.fork());
        timelineIndex = 0;
    }

    public void addNewModel(){


        if (history.size() > timelineIndex + 1) {
            history.subList(timelineIndex + 1, history.size()).clear();
        }
        this.history.add(this.model.fork());
        timelineIndex += 1;

    }

    public void undo(){
        if(timelineIndex >=  1){
            this.timelineIndex -= 1;

            this.model = this.history.get(timelineIndex).fork();
            //resizeSelf();

            mainUI.model = this.model;
            clearAndDraw();
        }
    }

    public void redo(){
        if(this.timelineIndex < this.history.size() -1){
            timelineIndex += 1;
            this.model = this.history.get(timelineIndex).fork();
            //resizeSelf();
            mainUI.model = this.model;
            clearAndDraw();
        }
    }

    public void resizeSelf(Model model){
        model.resizeHistoryWidth(model.canvasWidth,this.canvas.getWidth());
        model.canvasWidth = this.canvas.getWidth();
        model.resizeHistoryHeight(model.canvasHeight,this.canvas.getHeight());
        model.canvasHeight = this.canvas.getHeight();
    }
}
