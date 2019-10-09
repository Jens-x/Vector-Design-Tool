import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Gui extends JPanel implements ActionListener, Runnable, MouseListener, MouseMotionListener
{
    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int x1Tem;
    private int y1Tem;
    private int x2Tem;
    private int y2Tem;
    private int x3;
    private int y3;
    private int x3tem;
    private int y3tem;
    private int x4;
    private int y4;
    private boolean fill = false;
    private boolean polyStarted = false;
    private int selection = -1;
    private int selectionTem = -1;
    private int exportSize = 0;
    private static WindowManager windowManager = new WindowManager();

    private Color penColour;
    private Color fillColour;

    private Graphics2D graphic;
    private BufferedImage canvas;
    private JDialog dBox;

    Tracker tracker;
    Tracker temTracker;
    Poly polygon;

    private boolean openNew = false;

    /**
     * GUI Constructor to initialise local variables
     */
    public Gui(){
        tracker = new Tracker();
        temTracker = new Tracker();
        x1 = 0;
        y1 = 0;
        x2 = 0;
        y2 = 0;
        penColour = Color.BLACK;
        fillColour = Color.WHITE;
    }

    /**
     * For creating the canvas to be displayed in the GUI
     */
    public void paintComponent(Graphics e)
    {
        Graphics2D graphic2 = (Graphics2D) e;
        super.paintComponent(e);

        if (canvas == null)
        {
            int w = 800;
            int h = 800;
            canvas = (BufferedImage) (this.createImage(w, h));
            graphic = canvas.createGraphics();
            graphic.setColor(Color.BLACK);
        }

        graphic2.drawImage(canvas, null, 0, 0);
    }

    /**
     * The method for creating the GUI to be displayed.
     * Adding appropriate menu items and icons
     * Setting action listeners and action commands
     * Adding the toolbar and its commands
     */
    public void createGUI() {

        // Container for all content setup
        JFrame frame = new JFrame("VEC Paint");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/iconVEC.png")));
        frame.setSize(WIDTH, HEIGHT);
        frame.setBackground(Color.WHITE);
        frame.getContentPane().add(this);

        // Make mouse moving & dragging working
        addMouseMotionListener(this);

        // Components setup
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        JMenu drawMenu = new JMenu("Draw");
        JMenu fillMenu = new JMenu("Fill");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem fileNew = new JMenuItem("New", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/new.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem fileOpen = new JMenuItem("Open", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/open.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem fileSave = new JMenuItem("Save", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/save.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem fileExport = new JMenuItem("Export", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/export.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem fileExit = new JMenuItem("Exit", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/exit.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem helpAbout = new JMenuItem("About", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/about.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem helpGuide = new JMenuItem("Guide", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/help.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem drawLine = new JMenuItem("Line");
        JMenuItem drawPlot = new JMenuItem("Plot");
        JMenuItem drawRect = new JMenuItem("Rectangle");
        JMenuItem drawEllipse = new JMenuItem("Ellipse");
        JMenuItem drawPoly = new JMenuItem("Poly");
        JMenuItem undoCommand = new JMenuItem("Undo", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/undo.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem redoCommand = new JMenuItem("Redo", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/redo.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));
        JMenuItem undoHistory = new JMenuItem("Undo History", new ImageIcon(new ImageIcon(getClass().getResource(
                "/resources/undohistory.png")).getImage().getScaledInstance(
                20, 20, Image.SCALE_DEFAULT)));

        JMenuItem fillSolid = new JMenuItem("Opaque");
        JMenuItem fillClear = new JMenuItem("Transparent");

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(drawMenu);
        menuBar.add(fillMenu);
        menuBar.add(helpMenu);
        fileMenu.add(fileNew);
        fileMenu.add(fileOpen);
        fileMenu.add(fileSave);
        fileMenu.add(fileExport);
        fileMenu.addSeparator();
        fileMenu.add(fileExit);
        helpMenu.add(helpAbout);
        helpMenu.add(helpGuide);
        drawMenu.add(drawLine);
        drawMenu.add(drawPlot);
        drawMenu.add(drawRect);
        drawMenu.add(drawEllipse);
        drawMenu.add(drawPoly);
        fillMenu.add(fillSolid);
        fillMenu.add(fillClear);
        editMenu.add(undoCommand);
        editMenu.add(redoCommand);
        editMenu.add(undoHistory);

        // Action listeners for components
        fileNew.addActionListener(this);
        fileOpen.addActionListener(this);
        fileSave.addActionListener(this);
        fileExport.addActionListener(this);
        fileExit.addActionListener(this);
        helpAbout.addActionListener(this);
        helpGuide.addActionListener(this);
        drawLine.addActionListener(this);
        drawPlot.addActionListener(this);
        drawRect.addActionListener(this);
        drawEllipse.addActionListener(this);
        drawPoly.addActionListener(this);
        fillSolid.addActionListener(this);
        fillClear.addActionListener(this);
        fillSolid.setActionCommand("Fill On");
        fillClear.setActionCommand("Fill Off");
        undoCommand.addActionListener(this);
        redoCommand.addActionListener(this);
        undoHistory.addActionListener(this);

        frame.setJMenuBar(menuBar);

        // Toolbar initialisation
        setPreferredSize(new Dimension(450,130));
        JToolBar toolBar = new JToolBar("Tools");

        JToggleButton btnLine = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconLine.png")));
        JToggleButton btnPlot = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconPlot.png")));
        JToggleButton btnRect = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconRect.png")));
        JToggleButton btnEllipse = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconEllipse.png")));
        JToggleButton btnPoly = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconPoly.png")));
        JToggleButton btnFill = new JToggleButton(new ImageIcon(getClass().getResource(
                "/resources/iconFill.png")));
        JButton btnFillColour = new JButton(new ImageIcon(getClass().getResource(
                "/resources/colourwheel.png")));
        JButton btnPenColour = new JButton(new ImageIcon(getClass().getResource(
                "/resources/pen.png")));

        btnLine.setToolTipText("Draw Line");
        btnPlot.setToolTipText("Draw Plot");
        btnRect.setToolTipText("Draw Rectangle");
        btnEllipse.setToolTipText("Draw Ellipse");
        btnPoly.setToolTipText("Draw Polygon");
        btnFill.setToolTipText("Toggle Fill");
        btnFillColour.setToolTipText("Fill Colour");
        btnPenColour.setToolTipText("Pen Colour");

        btnLine.addActionListener(this);
        btnPlot.addActionListener(this);
        btnRect.addActionListener(this);
        btnEllipse.addActionListener(this);
        btnPoly.addActionListener(this);
        btnFill.addActionListener(this);
        btnFillColour.addActionListener(e ->
        {
            Color tempColour = JColorChooser.showDialog(frame,"Choose Fill Colour",
                    Color.WHITE);
            if(tempColour != null)
            {
                fillColour = tempColour;
                if (fill){
                    String command = "FILL " + "#" + Integer.toHexString(fillColour.getRGB()).substring(2).toUpperCase();
                    tracker.addCommand(command);
                }
            }
        });
        btnPenColour.addActionListener(e ->
        {
            Color tempColour = JColorChooser.showDialog(frame,"Choose Pen Colour",
                    Color.BLACK);
            if(tempColour != null)
            {
                penColour = tempColour;
                String command = "PEN " + "#"+Integer.toHexString(penColour.getRGB()).substring(2).toUpperCase();
                tracker.addCommand(command);
            }
        });

        // Action commands for method draw()
        btnLine.setActionCommand("Line");
        btnPlot.setActionCommand("Plot");
        btnRect.setActionCommand("Rectangle");
        btnEllipse.setActionCommand("Ellipse");
        btnPoly.setActionCommand("Poly");
        btnFill.setActionCommand("Fill Toggle");

        // Button group for toolbar buttons
        ButtonGroup drawBtnGroup = new ButtonGroup();
        drawBtnGroup.add(btnLine);
        drawBtnGroup.add(btnPlot);
        drawBtnGroup.add(btnRect);
        drawBtnGroup.add(btnEllipse);
        drawBtnGroup.add(btnPoly);

        toolBar.add(btnLine);
        toolBar.add(btnPlot);
        toolBar.add(btnRect);
        toolBar.add(btnEllipse);
        toolBar.add(btnPoly);
        toolBar.addSeparator(new Dimension(20, 40));
        toolBar.add(btnFill);
        toolBar.add(btnFillColour);
        toolBar.add(btnPenColour);

        // Place toolbar on screen
        add(toolBar, BorderLayout.PAGE_START);
        addMouseListener(this);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(windowManager);
        frame.setVisible(true);
    }

    /**
     * The method for creating text area with specified font, size and bold or not
     * @param font The name of the font to be used
     * @param size The size of text
     * @param bold If the text is bold or not
     * @return JTextArea object
     */
    private JTextArea createTextArea(String font, int size, Boolean bold){
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        if(bold) {
            textArea.setFont(new Font(font, Font.BOLD, size));
        }
        else{
            textArea.setFont(new Font(font, Font.PLAIN, size));
        }
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEtchedBorder());

        return textArea;
    }

    /**
     * Checks if the x and y coordinates to make draw from right to left, down to up working
     */
    public void check()
    {
        if (x1 > x2)
        {
            int z = 0;
            z = x1;
            x1 = x2;
            x2 = z;
        }
        if (y1 > y2)
        {
            int z = 0;
            z = y1;
            y1 = y2;
            y2 = z;
        }
    }

    /**
     * Same as check() method for temporary coordinates for intermediate shapes during dragging
     */
    public void checkTem()
    {
        x3tem = x3;
        y3tem = y3;
        if (x3 > x4)
        {
            int z = 0;
            z = x3;
            x3 = x4;
            x4 = z;
        }
        if (y3 > y4)
        {
            int z = 0;
            z = y3;
            y3 = y4;
            y4 = z;
        }
    }

    /**
     * Set the local variable selection to the command that is being executed
     * @param selection selection in integer form
     */
    public void setSelection(int selection){
        this.selection = selection;
    }

    /**
     * Set the coordinate of drawing for command reading
     * @param x1 Value to be assigned to global variable x1
     * @param y1 Value to be assigned to global variable y1
     * @param x2 Value to be assigned to global variable x2
     * @param y2 Value to be assigned to global variable y2
     */
    public void setCoordinate(double x1, double y1, double x2, double y2){

        this.x1 = (int) Math.round(x1 * canvas.getWidth());
        this.x2 = (int) Math.round(x2 * canvas.getWidth());
        this.y1 = (int) Math.round(y1 * canvas.getWidth());
        this.y2 = (int) Math.round(y2 * canvas.getWidth());

    }

    /**
     * Set the pen colour for use in saving the file
     * @param penColour The pen colour to be set
     */
    public void setPenColour(Color penColour){

        this.penColour = penColour;
        String command = "PEN " + "#"+Integer.toHexString(penColour.getRGB()).substring(2).toUpperCase();
        tracker.addCommand(command);
    }

    /**
     * Set the fill colour for use in saving the file
     * @param fillColour The fill colour to be set
     */
    public void setFillColour(Color fillColour){
        this.fillColour = fillColour;
        String command = "FILL " + "#"+Integer.toHexString(fillColour.getRGB()).substring(2).toUpperCase();
        tracker.addCommand(command);
    }

    /**
     * Toggle fill on or off
     * @param fill depending on value of true or false
     */
    public void setFill(Boolean fill){
        this.fill = fill;
        if(!fill){
            tracker.addCommand("FILL OFF");
        }
    }

    /**
     * Clears commands executed when undo is selected
     */
    public void clearScreen(){
        repaint();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, getWidth(), getHeight());
        fill = false;
        repaint();
    }

    /**
     * Reset parameters like pen colour, fill colour and tracker
     */
    public void resetPara(){
        tracker.clearCommands();
        penColour = Color.BLACK;
        fillColour = Color.WHITE;
    }

    /**
     * Create a Polygon object if one hasn't been created else add co-ordinates
     */
    public void drawPolygon(){
        // If the drawing hasn't started, init the polygon
        if (!polyStarted){
            polygon = new Poly(this);
            polygon.addCoordinate(x1, y1);
            polyStarted = true;
        }
        else{
            // Else, simply add the coordinate of the next one
            polygon.addCoordinate(x1, y1);
        }
    }

    /**
     * Finish drawing the polygon.
     */
    public void finishPolygon(){
        ArrayList<String> coordinates = polygon.getCoordinates();
        if (!fill)
        {
            graphic.setColor(penColour);
            graphic.drawPolygon(polygon.getPoly());
            String command = "POLYGON";
            for (int i = 0; i < coordinates.size(); i++){
                command += " " + coordinates.get(i);
            }
            tracker.addCommand(command);
        }
        else {
            graphic.setColor(fillColour);
            graphic.fillPolygon(polygon.getPoly());
            graphic.setColor(penColour);
            graphic.drawPolygon(polygon.getPoly());
            String command = "POLYGON";
            for (int i = 0; i < coordinates.size(); i++){
                command += " " + coordinates.get(i);
            }
            tracker.addCommand(command);
        }
        polyStarted = false;
        polygon = null;
        temTracker.clearCommands();
    }

    /**
     * Get the width of current canvas
     * @return The width of current canvas in integer
     */
    public int guiGetWidth(){
        return canvas.getWidth();
    }

    /**
     * Get the height of current canvas
     * @return The height of current canvas in integer
     */
    public int guiGetHeight(){
        return canvas.getHeight();
    }

    /**
     * Store the current parameters in buffer
     */
    public void storePara(){
        selectionTem = selection;
        x1Tem = x1;
        x2Tem = x2;
        y1Tem = y1;
        y2Tem = y2;
    }

    /**
     * Restore the current parameters from buffer
     */
    public void restorePara(){

        selection = selectionTem;
        x1 = x1Tem;
        x2 = x2Tem;
        y1 = y1Tem;
        y2 = y2Tem;
    }

    /**
     *  Clear the screen and redraw all shapes stored in command list
     */
    public void refreshScreen(){
        clearScreen();

        ArrayList<String> commands = tracker.getCommand();
        tracker.clearCommands();

        storePara();

        FileOperation fileOP = new FileOperation(this);
        for (int i = 0; i < commands.size(); i++) {
            fileOP.readCommand(commands.get(i));
        }

        restorePara();
    }

    /**
     * Draw temporary shapes while mouse dragging or moving
     */
    public void drawTemp(){
        int w = x3 - x4;
        if (w < 0)
            w = w * (-1);

        int h = y4 - y3;
        if (h < 0)
            h = h * (-1);

        // Draw line to screen and add dimensions to command list
        if (selection == 1) {
            graphic.setColor(penColour);
            graphic.drawLine(x3, y3, x4, y4);
        }

        // This line must be put after draw line and polygon
        checkTem();

        // Draw rectangle to screen and add dimensions to command list
        if (selection == 2)
        {
            if (!fill)
            {
                graphic.setColor(penColour);
                graphic.drawRect(x3, y3, w, h);
            } else {
                graphic.setColor(fillColour);
                graphic.fillRect(x3, y3, w, h);
                graphic.setColor(penColour);
                graphic.drawRect(x3, y3, w, h);
            }
        }

        // Draw ellipse to screen and add dimensions to command list
        else if (selection == 3)
        {
            if (!fill)
            {
                graphic.setColor(penColour);
                graphic.drawOval(x3, y3, w, h);
            } else
            {
                graphic.setColor(fillColour);
                graphic.fillOval(x3, y3, w, h);
                graphic.setColor(penColour);
                graphic.drawOval(x3, y3, w, h);
            }
        }

        repaint();
    }

    /**
     * Used to draw the respective shape that has been selected
     * Gets co-ordinates, converts to decimal rounded 2 places
     * and populates the appropriate shape in the GUI and adds
     * the text command to the VEC file to be saved.
     */
    public void draw() {
        int w = x1 - x2;
        if (w < 0)
            w = w * (-1);

        int h = y2 - y1;
        if (h < 0)
            h = h * (-1);

        // Get height and width of canvas to calculate percentage of each item drawn (between 0.0 and 1.0)
        int canvasHeight = canvas.getHeight();
        int canvasWidth = canvas.getWidth();

        // Calculate percentages of x1, y1, x2, y2, w and h relative to the canvas dimensions
        double x1Percent = (double)x1 / (double)canvasWidth;
        double y1Percent = (double)y1 / (double)canvasHeight;
        double x2Percent = (double)x2 / (double)canvasWidth;
        double y2Percent = (double)y2 / (double)canvasHeight;
        double wPercent = (double)w / (double)canvasWidth;
        double hPercent = (double)h / (double)canvasHeight;

        // Round the coordinate values to 2 decimal places (one decimal place will show 0.1 not 0.10)
        x1Percent = Math.round(x1Percent * 100) / 100.0;
        y1Percent = Math.round(y1Percent * 100) / 100.0;
        x2Percent = Math.round(x2Percent * 100) / 100.0;
        y2Percent = Math.round(y2Percent * 100) / 100.0;
        wPercent = Math.round(wPercent * 100) / 100.0;
        hPercent = Math.round(hPercent * 100) / 100.0;

        // Plot to screen and add dimensions to command list
        if (selection == 0){
            graphic.setColor(penColour);
            graphic.fillOval(x1,y1,5,5);
            Plot plot1 = new Plot(x1, y1, w, h, this);
            String command = "PLOT " + x1Percent + " " + y1Percent;
            tracker.addCommand(command);
        }

        // Draw line to screen and add dimensions to command list
        else if (selection == 1) {
            graphic.setColor(penColour);
            graphic.drawLine(x1, y1, x2, y2);
            Line line1 = new Line(x1, y1, x2, y2, this);
            String command = "LINE " + x1Percent + " " + y1Percent + " " + x2Percent + " " + y2Percent;
            tracker.addCommand(command);
        }

        // This line must be put after draw line and polygon
        check();

        // Draw rectangle to screen and add dimensions to command list
        if (selection == 2)
        {
            if (!fill)
            {
                graphic.setColor(penColour);
                graphic.drawRect(x1, y1, w, h);
                Rect rect1 = new Rect(x1, y1, w, h, this);
                String command = "RECTANGLE " + x1Percent + " " + y1Percent + " " + x2Percent + " " + y2Percent;
                tracker.addCommand(command);
            } else {
                graphic.setColor(fillColour);
                graphic.fillRect(x1,y1,w,h);
                graphic.setColor(penColour);
                graphic.drawRect(x1, y1, w, h);
                Rect rect1 = new Rect(x1, y1, w, h, this);
                String command = "RECTANGLE " + x1Percent + " " + y1Percent + " " + x2Percent + " " + y2Percent;
                tracker.addCommand(command);
            }
        }

        // Draw ellipse to screen and add dimensions to command list
        else if (selection == 3)
        {
            if (!fill)
            {
                graphic.setColor(penColour);
                graphic.drawOval(x1, y1, w, h);
                Ellipse ellipse1 = new Ellipse(x1, y1, w, h, this);
                String command = "ELLIPSE " + x1Percent + " " + y1Percent + " " + x2Percent + " " + y2Percent;
                tracker.addCommand(command);
            } else
            {
                graphic.setColor(fillColour);
                graphic.fillOval(x1, y1, w, h);
                graphic.setColor(penColour);
                graphic.drawOval(x1, y1, w, h);
                Ellipse ellipse1 = new Ellipse(x1, y1, w, h, this);
                String command = "ELLIPSE " + x1Percent + " " + y1Percent + " " + x2Percent + " " + y2Percent;
                tracker.addCommand(command);
            }
        }

        repaint();
    }



    @Override
    public void run()
    {
        SwingUtilities.invokeLater(new Gui());
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Open")){
            JFileChooser filechoose = new JFileChooser();
            filechoose.setFileFilter(new FileNameExtensionFilter("VEC file","vec"));
            int value = filechoose.showOpenDialog(this);
            if(value==JFileChooser.APPROVE_OPTION) {
                clearScreen();
                tracker.clearCommands();
                File file = filechoose.getSelectedFile();
                String filename = file.getAbsolutePath();

                FileOperation fileOP = new FileOperation(this);
                fileOP.readFile(filename);
            } else if(value==JFileChooser.CANCEL_OPTION) {
            }
        }

        if(e.getActionCommand().equals("Save")){
            JFileChooser filechoose = new JFileChooser();
            filechoose.setSelectedFile(new File("new.vec"));
            filechoose.setFileFilter(new FileNameExtensionFilter("VEC file","vec"));
            int value = filechoose.showSaveDialog(this);
            if(value==JFileChooser.APPROVE_OPTION) {
                File file = filechoose.getSelectedFile();
                String filename = file.getAbsolutePath();

                FileOperation fileOP = new FileOperation(this);
                try {
                    fileOP.writeFile(filename, tracker);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else if(value==JFileChooser.CANCEL_OPTION) {
            }
        }

        /**
         * Display a dialog box asking the user to input the dimensions they wish the picture to be
         * Opens save dialog to browse to specific location
         * Resizes the canvas to suit dimensions given
         * Saves resized image as Bitmap format
         */
        if(e.getActionCommand().equals("Export")){
            exportDialogBox();

            if (exportSize != 0){
                JFileChooser filechoose = new JFileChooser();
                filechoose.setSelectedFile(new File("new.bmp"));
                filechoose.setFileFilter(new FileNameExtensionFilter("Bitmap File","bmp"));
                int value = filechoose.showSaveDialog(this);
                if(value==JFileChooser.APPROVE_OPTION) {
                    File file = filechoose.getSelectedFile();
                    String filename = file.getAbsolutePath();

                    try {
                        BufferedImage resized = new BufferedImage(exportSize,exportSize,
                                canvas.getType());
                        Graphics2D temp = resized.createGraphics();
                        temp.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        temp.drawImage(canvas,0,0,exportSize,exportSize,0,0,canvas.getWidth(),
                                canvas.getHeight(),null);
                        ImageIO.write(resized,"bmp",file);
                        temp.dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else if(value==JFileChooser.CANCEL_OPTION) {
                }
            }
        }

        // Create new Tracker object and call attach the appropriate methods to the undo and redo action listeners
        switch (e.getActionCommand())
        {
            case "Undo":
                // If there are commands to undo
                if (tracker.getCommand().size() != 0)
                {
                    // Declare an array list of string to store undo commands
                    ArrayList<String> undoCommands = tracker.undo();
                    // Clear the canvas
                    clearScreen();
                    resetPara();
                    // Redraw all commands on the screen
                    FileOperation fileOP = new FileOperation(this);
                    for (int i = 0; i < undoCommands.size(); i++)
                    {
                        fileOP.readCommand(undoCommands.get(i));
                    }
                }

                break;
            case "Redo":
                // If the there are commands to redo
                if (tracker.getUndo().size() != 0)
                {
                    // Retrieve the latest command from undo list
                    String redoCommand = tracker.redo();
                    // Draw the command on the screen
                    FileOperation fileOP = new FileOperation(this);
                    fileOP.readCommand(redoCommand);
                }
                break;
            case "Undo History":
                undoHistoryDialog();
                break;
        }


        switch (e.getActionCommand())
        {
            case "Plot":
                selection = 0;
                break;
            case "Line":
                selection = 1;
                break;
            case "Rectangle":
                selection = 2;
                break;
            case "Ellipse":
                selection = 3;
                break;
            case "Poly":
                selection = 4;
                break;
        }

        if(e.getActionCommand().startsWith("Fill"))
        {
            if (e.getActionCommand().equals("Fill Off"))
            {
                fill = false;
                tracker.addCommand("FILL OFF");
            }

            if (e.getActionCommand().equals("Fill On"))
            {
                fill = true;
                String command = "FILL " + "#" + Integer.toHexString(fillColour.getRGB()).substring(2).toUpperCase();
                tracker.addCommand(command);
            }


            if(e.getActionCommand().equals("Fill Toggle"))
            {
                if (!fill) {
                    fill = true;
                    String command = "FILL " + "#" + Integer.toHexString(fillColour.getRGB()).substring(2).toUpperCase();
                    tracker.addCommand(command);
                } else {
                    fill = false;
                    tracker.addCommand("FILL OFF");
                }
            }
        }


        if (e.getActionCommand().equals("Exit"))
        {
            //Thread.currentThread().interrupt();
            System.exit(0);
        }

        if (e.getActionCommand().equals("About"))
        {
            JFrame about = new JFrame("About");
            JTextArea aboutTxt = createTextArea("Arial", 16, false);
            aboutTxt.append("Created by,\n Tolga Pasin\n Jens Xue\n " +
                    "Nicholas Beaumont\n Alex Dooley");
            about.add(aboutTxt);
            about.setSize(300, 300);
            about.setVisible(true);
        }

        // On click on "New" button.
        if (e.getActionCommand().equals("New")) {
            // Create new GUI object and thread for it.
            // Opens new GUI in another window.
            Gui test2 = new Gui();
            Thread thread2 = new Thread(test2);
            thread2.start();
            test2.createGUI();
        }
    }

    public void exportDialogBox()
    {
        JFrame box = new JFrame();
        box.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources" +
                "/export.png")));
        dBox = new JDialog(box, "Export as BMP", true);
        GridBagConstraints gbc = new GridBagConstraints();
        dBox.setLayout(new GridBagLayout());

        JButton affirm = new JButton("OK");
        JSpinner exportSizeSpinner = new JSpinner();
        exportSizeSpinner.setPreferredSize(new Dimension(50,25));

        affirm.addActionListener(e ->
        {
            if (Integer.parseInt(exportSizeSpinner.getValue().toString()) != 0)
            {
                dBox.setVisible(false);
                exportSize = Integer.parseInt(exportSizeSpinner.getValue().toString());
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30,30,0,0);
        dBox.add(new JLabel("Enter BMP Dimensions (pixels): "), gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(30,0,0,20);
        dBox.add(exportSizeSpinner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(20,60,20,0);
        dBox.add(affirm, gbc);

        dBox.pack();
        dBox.setVisible(true);
    }

    public void undoHistoryDialog()
    {
        JFrame box = new JFrame();
        box.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources" +
                "/undohistory.png")));
        dBox = new JDialog(box, "Undo History", true);
        GridBagConstraints gbc = new GridBagConstraints();
        dBox.setLayout(new GridBagLayout());

        if(!tracker.getUndo().isEmpty()){
            for (int i = 0; i < tracker.getUndo().size(); i++){
                gbc.gridx = 0;
                gbc.gridy = i;
                gbc.insets = new Insets(10,10,10,10);
                gbc.anchor = GridBagConstraints.EAST;
                dBox.add(new JLabel(i+1 + ". " + tracker.getUndo().get(i)),gbc);
            }
            gbc.gridx = 0;
            gbc.gridy = tracker.getUndo().size() + 1;
            dBox.add(new JLabel(""),gbc);
        } else {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(30,30,30,40);
            dBox.add(new JLabel("No history to display."),gbc);
        }

        JButton close = new JButton("Close");

        close.addActionListener(e -> {
            dBox.setVisible(false);
        });

        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        gbc.insets = new Insets(0,0,5,5);
        dBox.add(close,gbc);

        dBox.pack();
        dBox.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        // If Polygon option is selected, get the coordinate
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (selection == 4) {
                x1 = e.getX();
                y1 = e.getY();
                drawPolygon();
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3 && polyStarted){
            finishPolygon();
        }

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        x1 = e.getX();
        y1 = e.getY();
        x3 = x1;
        y3 = y1;
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        x2 = e.getX();
        y2 = e.getY();

        draw();
        tracker.clearUndo();
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        x4 = e.getX();
        y4 = e.getY();

        refreshScreen();

        drawTemp();
        x3 = x3tem;
        y3 = y3tem;
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }


}

