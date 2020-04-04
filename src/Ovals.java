import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.StrokeLineCap;

public class Ovals extends Shapes{


    Ovals(double xStart, double yStart, double xEnd, double yEnd, Color fillColor, int thick, Color lineColour, double dashes){
        this.startX = xStart;
        this.startY = yStart;
        this.endX = xEnd;
        this.endY =yEnd;
        this.fillColor = fillColor;
        this.lineThickness = thick;
        this.lineColor = lineColour;
        this.lineDashes = dashes;

        this.name ="Oval";
    }

    @Override
    public Shapes fork(){
        return new Ovals(this.startX,this.startY,this.endX,this.endY,
                this.fillColor,this.lineThickness,this.lineColor,this.lineDashes);
    }


    @Override
    public void draw(GraphicsContext gc){
        double x,y;
        double w = endX - startX;
        double h = endY - startY;
        x = startX;
        y = startY;
        if(w < 0){ x = endX; w = - w;}
        if(h < 0){ y = endY; h = - h;}

        gc.setFill(this.fillColor);
        gc.setStroke(this.lineColor);
        gc.fillOval(x,y,w,h);

        gc.setLineWidth(this.lineThickness);
        gc.setLineCap(StrokeLineCap.BUTT);

        gc.setLineDashes(10d,this.lineDashes);

        if(this.selected){
            gc.setLineWidth(this.lineThickness + 3);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineDashes(0d,this.lineDashes + 8d);
        }

        gc.strokeOval(x,y,w,h);
    }

    @Override
    public boolean within(double xP, double yP){


        double w = endX - startX;
        double h = endY - startY;

        if(w < 0){ w = - w;}
        if(h < 0){ h = - h;}

        return new Ellipse((this.startX+this.endX)/2,(this.startY+this.endY)/2,(w + lineThickness)/2 ,(h + lineThickness)/2 ).contains(xP, yP);
    }

}
