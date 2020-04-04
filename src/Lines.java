import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class Lines extends Shapes{


    Lines(double xStart, double yStart, double xEnd, double yEnd, int thick, Color lineColour, double dashes){
        this.startX = xStart;
        this.startY = yStart;
        this.endX = xEnd;
        this.endY =yEnd;
        this.lineThickness = thick;
        this.lineColor = lineColour;
        this.lineDashes = dashes;
        this.name ="Line";
    }

    @Override
    public Shapes fork(){
        return new Lines(this.startX,this.startY,this.endX,this.endY,
                this.lineThickness,this.lineColor,this.lineDashes);
    }


    @Override
    public boolean within(double xP, double yP){
        Line line = new Line(startX,startY,endX,endY);
        line.setStrokeWidth(this.lineThickness + 2);//larger hit box
        return line.contains(xP,yP);
    }

    @Override
    public void draw(GraphicsContext gc){

        gc.setStroke(this.lineColor);
        gc.setLineWidth(this.lineThickness);
        gc.setLineCap(StrokeLineCap.BUTT);

        gc.setLineDashes(10d,this.lineDashes);

        if(this.selected){
            gc.setLineWidth(this.lineThickness + 3);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineDashes(0d,this.lineDashes + 8d);
        }

        gc.strokeLine(this.startX,this.startY,this.endX,this.endY);
    }
}
