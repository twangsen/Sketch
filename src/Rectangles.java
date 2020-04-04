import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class Rectangles extends Shapes{


    Rectangles(double xStart, double yStart, double xEnd, double yEnd, Color fillColor, int thick, Color lineColour, double dashes){
        this.startX = xStart;
        this.startY = yStart;
        this.endX = xEnd;
        this.endY =yEnd;
        this.fillColor = fillColor;
        this.lineThickness = thick;
        this.lineColor = lineColour;
        this.lineDashes = dashes;
        this.name ="Rec";
    }

    @Override
    public Shapes fork(){
        return new Rectangles(this.startX,this.startY,this.endX,this.endY,
                this.fillColor,this.lineThickness,this.lineColor,this.lineDashes);
    }

   @Override
   public boolean within(double x, double y){

        return ((x >= startX && x <= endX) || (x <= startX && x >= endX)) && ((y >= startY && y <= endY) || (y <= startY && y >= endY));
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
        gc.fillRect(x,y,w,h);

        gc.setLineWidth(this.lineThickness);
        gc.setLineCap(StrokeLineCap.BUTT);
        gc.setLineDashes(10d,this.lineDashes);
        if(lineDashes == 0d){
            gc.setLineDashes(0d);
        }

       if(this.selected){
           gc.setLineWidth(this.lineThickness + 3);
           gc.setLineCap(StrokeLineCap.ROUND);
           gc.setLineDashes(0d,this.lineDashes + 8d);
       }

        gc.strokeRect(x,y,w,h);
    }

}
