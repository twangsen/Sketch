## Assignment 3
```
Wangsen Tian
20654708 w6tian 
openjdk 11.0.5 2019-10-15
macOS 10.15.2 (MacBook Pro Early 2015)
```
##

- MVC: The Model class is the model part of MVC,it will notify Canvas(View1) about shapes information and notify mainUI(View2)
to update line thickness palette, line style palette, line color and fill color according the selected Shapes;

- Implemented full Undo-Redo for canvas changes, click Edit-Undo or press "u" to undo, click  Edit-Redo or press "r" to redo changes;

- Used Images from https://icons8.com/icons

- The tool user currently using will be highlighted with color,
when start the app, all tools will be highlighted until user select one.

- Widgets are disabled when it is invalid to use them. <br />
For example, fill-color-picker are disable when use Line tool <br />
line thickness palette and line style palette are disabled when use fillTool, ect.<br />

- If a shape is selected by selection tool, click other tool (RectangleTool, LineTool, Fill tool..) will unselect the selected shape.

- Files are stored as .txt file

- The default color for line color and fill color are black and transparent.
Click the reset icon besides the colorPicker will reset the color to default