import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Shapes {
    String name;
    double startX, startY;
    double endX, endY;
    int layer;
    boolean selected = false;

    Color lineColor;
    Color fillColor;
    int lineThickness;
    Double lineDashes;

    public void draw(GraphicsContext gc){}

    public boolean within(double x, double y){return false;}

    public Shapes fork(){
        return null;
    }

    public void setEnd(double x, double y){
        this.endX = x;
        this.endY = y;
    }

}


