import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {


    ArrayList<Shapes> shapes = new ArrayList<>();
    GraphicsContext gc;
    double canvasHeight, canvasWidth;
    Shapes selectedShape = null;
    int selectedIndex;
    boolean saved = true;
    boolean changed = false;

    public void draw() {
        for (Shapes node : shapes) {
            node.draw(gc);
        }
    }

    public void add(Shapes s) {
        this.shapes.add(s);
        this.saved = false;
        this.changed = true;
    }

    public void resizeShapesHeight(double oldVal, double newVal) {
        for (Shapes node : shapes) {
            node.startY = node.startY * (newVal / oldVal);
            node.endY = node.endY * (newVal / oldVal);
            node.draw(gc);
        }

    }

    public void resizeHistoryHeight(double oldVal, double newVal) {
        for (Shapes node : shapes) {
            node.startY = node.startY * (newVal / oldVal);
            node.endY = node.endY * (newVal / oldVal);
        }
    }


    public void resizeShapesWidth(double oldVal, double newVal) {
        for (Shapes node : shapes) {
            node.startX = node.startX * (newVal / oldVal);
            node.endX = node.endX * (newVal / oldVal);
            node.draw(gc);
        }
    }
    public void resizeHistoryWidth(double oldVal, double newVal) {
        for (Shapes node : shapes) {
            node.startX = node.startX * (newVal / oldVal);
            node.endX = node.endX * (newVal / oldVal);
        }
    }


    public void erase(double x, double y) {
        this.changed = false;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).within(x, y)) {
                shapes.remove(i);
                this.saved = false;
                this.changed = true;
                break;
            }
        }

    }

    public void fill(double x, double y, Color color) {
        this.changed = false;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).within(x, y)) {
                shapes.get(i).fillColor = color;
                this.saved = false;
                this.changed = true;
                break;
            }
        }
    }

    public void setSelect(double x, double y) {
        unselectAll();
        for (int i = shapes.size() - 1; i >= 0; i--) {
            if (shapes.get(i).within(x, y)) {
                shapes.get(i).selected = true;
                selectedShape = shapes.get(i);
                selectedIndex = i;
                break;
            }
        }
    }

    public void moveSelected(double x, double y) {
        if (selectedShape == null) {
            return;
        }
        shapes.get(selectedIndex).startX += x;
        shapes.get(selectedIndex).endX += x;
        shapes.get(selectedIndex).startY += y;
        shapes.get(selectedIndex).endY += y;
        this.saved = false;
        this.changed = true;
    }

    public void deleteSelected() {
        this.changed = false;
        if (selectedShape == null) {
            return;
        }
        shapes.remove(selectedIndex);
        this.changed = true;
        this.saved = false;
    }

    public void changeSelectedThickness(int i) {
        this.changed = false;
        if (selectedShape == null) {
            return;
        }
        if (shapes.get(selectedIndex).lineThickness != i) {
            shapes.get(selectedIndex).lineThickness = i;
            this.saved = false;
            this.changed = true;
        }
    }

    public void changeSelectedDashes(double d) {
        this.changed = false;
        if (selectedShape == null) {
            return;
        }
        if (shapes.get(selectedIndex).lineDashes != d) {
            shapes.get(selectedIndex).lineDashes = d;
            this.saved = false;
            this.changed = true;
        }
    }

    public void changeSelectedLineColor(Color c) {
        this.changed = false;
        if (selectedShape == null) {
            return;
        }
        if (shapes.get(selectedIndex).lineColor != c) {
            shapes.get(selectedIndex).lineColor = c;
            this.saved = false;
            this.changed = true;
        }
    }

    public void changeSelectedFillColor(Color c) {
        this.changed = false;
        if (selectedShape == null) {
            return;
        }
        if (shapes.get(selectedIndex).fillColor != c) {
            shapes.get(selectedIndex).fillColor = c;
            this.saved = false;
            this.changed = true;
        }
    }


    public void unselectAll() {
        for (Shapes node : shapes) {
            node.selected = false;
        }
        this.selectedShape = null;
    }


    public Shapes getSelectedShape() {
        return selectedShape;
    }

    public void save(File file) {
        try {
            FileWriter writer = new FileWriter(file.getPath());
            writer.write(canvasWidth + "\n"
                    + canvasHeight + "\n"
            );

            for (Shapes node : shapes) {
                writer.write(node.name + ","
                        + node.startX + ","
                        + node.startY + ","
                        + node.endX + ","
                        + node.endY + ","
                        + node.fillColor + ","
                        + node.lineThickness + ","
                        + node.lineColor + ","
                        + node.lineDashes + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.saved = true;
    }

    public void load(File file, double w, double h) {
        String s;
        double canvasOldX, canvasOldY;
        this.shapes = new ArrayList<>();
        Shapes tempShape = new Shapes();
        try {
            Scanner scanner = new Scanner(file);
            canvasOldX = Double.parseDouble(scanner.next());
            canvasOldY = Double.parseDouble(scanner.next());
            while (scanner.hasNext()) {
                s = scanner.next();
                String[] tokens = s.split(",");
                if (tokens[0].equals("Rec")) {
                    tempShape = new Rectangles(Double.parseDouble(tokens[1]),
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Color.web(tokens[5]),
                            Integer.parseInt(tokens[6]),
                            Color.web(tokens[7]),
                            Double.parseDouble(tokens[8])
                    );
                } else if (tokens[0].equals("Oval")) {
                    tempShape = new Ovals(Double.parseDouble(tokens[1]),
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Color.web(tokens[5]),
                            Integer.parseInt(tokens[6]),
                            Color.web(tokens[7]),
                            Double.parseDouble(tokens[8])
                    );
                } else if (tokens[0].equals("Line")) {
                    tempShape = new Lines(Double.parseDouble(tokens[1]),
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Integer.parseInt(tokens[6]),
                            Color.web(tokens[7]),
                            Double.parseDouble(tokens[8]));
                }
                shapes.add(tempShape);
            }
            resizeShapesHeight(canvasOldY, h);
            resizeShapesWidth(canvasOldX, w);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.saved = true;
    }

    public void resetNew() {
        shapes = new ArrayList<>();
        selectedShape = null;
        saved = true;
    }

    public boolean needSave() {
        //return (!this.saved && this.shapes.size() > 0);
        return !this.saved;
    }

    public boolean actionChanged() {
        return this.changed;
    }

    public void setUnchanged() {
        this.changed = false;
    }


    public Model fork() {
        Model newM = new Model();
        newM.shapes = new ArrayList<>();
        for (Shapes node : shapes) {
            newM.add(node.fork());
        }
        newM.gc = this.gc;
        newM.canvasHeight = this.canvasHeight;
        newM.canvasWidth = this.canvasWidth;
        newM.selectedShape = this.selectedShape;
        newM.selectedIndex = this.selectedIndex;
        newM.saved = this.saved;
        newM.changed = this.changed;

        return newM;
    }
}
