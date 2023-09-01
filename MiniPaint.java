// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP102/112 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP-102-112 - 2022T1, Assignment 8
 * Name: Emmanuel De Vera
 * Username:  Deveremma
 * ID:  300602434
 */

import ecs100.*;
import java.awt.Color;
import javax.swing.JColorChooser;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JButton;

/**
 * A simple drawing program.
 * The user can select from a variety of tools and options using the buttons and
 *   elements down the left side, and can use the mouse to add elements to the drawing
 *   according to the current tool and options
 * Note, most of the "action" in the program happens in response to mouse events;
 *   the buttons, textFields, and sliders mostly record information that is used
 *   later by the mouse responding.
 */


public class MiniPaint{

    // fields to remember:
    //  - the "tool" - what will be drawn when the mouse is next released.
    //                 may be a shape, or an image, or a caption,
    //    [Completion] or eraser, or flower
    //  - whether the shape should be filled or not
    //  - the position the mouse was pressed,
    //  - the string for the text caption
    //  - the width of the lines and the font size of the text captions.
    //  - [Completion] the name of the image file
    //  - [Completion] the colors for the border and fill for shapes and captions
    
    
    //just gonna put it out there that the latest comp assignment looks kinda big so I'll be busy with that for a couple days

    private String tool = "Line";   // the current tool, governing what the mouse will do.
                                    // Initial value is "Line";  changed by the buttons.
                                
    // More fields
    /*# YOUR CODE HERE */
    
    public static double startx;
    public static double starty;
    
    public static double endx;
    public static double endy;
    
    public static String caption;
    
    public static double thicc;
    public static double weight;
    
    public static String filler;
    
    public static Color colorline;
    public static Color colorfill;
    
    public static String file;
    public static double petal;
    
    public static int count;
    public static ArrayList<Double> data = new ArrayList<Double>();
    
    // These are all public so that they can all be accessed easily by the methods below. 

    /**This function is an improvement on UI.clearGraphics() where it can keep the current MiniPaint Settings
     */
    public void clear(){
        UI.clearGraphics();
        UI.setColor(colorline);
        UI.fillRect(10, 10, 35, 10);
        UI.setColor(colorfill);
        UI.fillRect(10, 30, 35, 10);
        UI.setColor(java.awt.Color.black);
        UI.setFontSize(12);
        UI.drawString(filler, 10, 60);
        UI.drawString(tool, 10, 80);
    }
    
    /**
     * Set up the interface: buttons, textfields, sliders,
     * listening to the mouse
    */
    public void setupGUI(){
        /*# YOUR CODE HERE */
        
        // Below I am setting up the inital settings of my Mini Paint. 
        
        filler = "No-Fill";
        petal = 6;
        thicc = 2;
        weight = 12;
        colorline = (java.awt.Color.black);
        colorfill = (java.awt.Color.black);
        
        UI.setLineWidth(thicc);
        UI.setFontSize(weight);
        
        UI.setColor(java.awt.Color.black);
        
        UI.fillRect(10, 10, 35, 10);
        UI.fillRect(10, 30, 35, 10);
        
        UI.setFontSize(12);
        UI.drawString(filler, 10, 60);
        UI.drawString(tool, 10, 80);
        
        UI.setMouseListener(this::doMouse); 
        
        UI.setMouseMotionListener(this::doMouse); 
        
        //Below I am setting up the buttons for the Minipaint
        
        // Since they all have actions that have only one line, I have decided to do Lambda function for most of them
        
        // This clears up a lot more space in the code overall
        
        UI.addButton("Clear", () -> {this.clear();});
        
        UI.addButton("Line", () -> {this.setTool("Line");} ); 
        
        UI.addButton("Rectangle", () -> {this.setTool("Rectangle");} );
        
        UI.addButton("Oval", () -> {this.setTool("Oval");} ); 
        
        UI.addTextField("Caption", this::doCaption);
        
        UI.addButton("Image", this::doImage); 
        
        UI.addButton("Eraser", () -> {this.setTool("Eraser");} ); 
        
        UI.addButton("Flower", () -> {this.setTool("Flower");} ); 
        
        UI.addButton("Triangle", () -> {this.setTool("Triangle");} ); 
        
        UI.addButton("Sketch", () -> {this.setTool("Sketch");} ); 
        
        JButton fillbutton = UI.addButton("No-Fill / Fill", () -> {
        if (filler == "No-Fill"){
            filler = "Fill";
            UI.setColor(java.awt.Color.black);
            UI.setFontSize(12);
            UI.eraseString("No-Fill", 10, 60);
            UI.drawString(filler, 10, 60);
        }
        else if (filler == "Fill"){
            filler = "No-Fill";
            UI.setColor(java.awt.Color.black);
            UI.setFontSize(12);
            UI.eraseString("Fill", 10, 60);
            UI.drawString(filler, 10, 60);
        }
        //tester.setText("Yopo!");
        
        // I tried so hard to get a changing button name from fill to no-fill and back and forth. 
        
        }   );
        
        fillbutton.setBackground(java.awt.Color.lightGray);
        
        
        JButton linecolor = UI.addButton("Line Color", this::setLineColor);
        linecolor.setBackground(java.awt.Color.black);
        linecolor.setForeground(java.awt.Color.white);
        
        JButton fillcolor = UI.addButton("Fill Color", this::setFillColor);
        fillcolor.setBackground(java.awt.Color.white);
        fillcolor.setForeground(java.awt.Color.black);
        fillcolor.setVisible(true);
        
        // Below are the sliders for line thickness, font size and petals
        
        UI.addSlider("Line Thickness",1,20,2, (value) -> {thicc = value; UI.setLineWidth(thicc);} );
        
        UI.addSlider("Font Size", 10, 51, 12, (value) -> {weight = value; UI.setFontSize(weight);} );
        
        UI.addSlider("Petals", 3, 20, 7, (value) -> {petal = value;} );
        
        UI.addButton("Quit", UI::quit);
        
        UI.setDivider(0.0);  // Hide the text area.
    
    }
    
    /*
    Color blue = (java.awt.Color.blue);
        Color red = (java.awt.Color.red);
        filler = "fill";
        
        mp.setupGUI(blue, red, filler);
    */
    /**
     * This function saves up a lot of space and essentially just sets the tool by the parameter that we passed to it. e.g "Rectangle"
     */
   public void setTool(String str){
       
       // Everytime the setTool function is called, it updates the top left to show the current tool
       
       UI.setFontSize(12);
       UI.eraseString(tool, 10, 80);
       tool = str;
       UI.setColor(java.awt.Color.black);
       UI.drawString(tool, 10, 80);
       
   }
     
   
    // Methods to respond to the buttons, textfield, and sliders
    // Mostly, These methods just save information to the fields.
    
    /** Responds whenever it notices a change in the Caption Field (either length or when enter key is pressed) */
    public void doCaption(String text){
        /*# YOUR CODE HERE */
        
        caption = text;
        if (caption.length() >= 1){ 
            this.setTool("Text");
        }
        else{
            this.setTool("Line");
        }
        
    }
    
    /** Respond to the Image Button - opens the file chooser */
    public void doImage(){
        
        this.setTool("Image");
        
        String open = UIFileChooser.open("File to read");
        file = open;
        
    }
    
    /** Respond to the Line Color button, opens the color chooser*/
    public void setLineColor(){
        colorline = JColorChooser.showDialog(null, "First Stripe", Color.white);
        
        UI.setColor(colorline);
        
        UI.fillRect(10, 10, 35, 10);
    }
    
    /** Respond to the Fill Color button, opens the color chooser */
    public void setFillColor(){
        colorfill = JColorChooser.showDialog(null, "First Stripe", Color.white);
        
        UI.setColor(colorfill);
        
        UI.fillRect(10, 30, 35, 10);
    }
    

    // The method header for doSetLine was given as an example; you will need other methods
    // for each button/textfield/slider
    // See hints in the Assignment description.


    
    /**
     * Respond to mouse events
     * When pressed, remember the position.
     * When released, draw what is specified by current tool
     * Uses the value stored in the field to determine which kind of tool to draw.
     *  It should call the drawALine, drawARectangle, drawAnOval, etc, methods
     *  passing the pressed and released positions
     * [Completion] should respond to "dragged" events also to do erasing
     */
    public void doMouse(String action, double x, double y) {
        /*# YOUR CODE HERE */
        
        
        /** Responds when the tool is Triangle, I've decided to just do the entire Triangle Function in here*/
        if (tool == "Triangle"){
            
            UI.setColor(colorline);
            

            if (action.equals("released")){
                UI.fillOval(x - (thicc / 2), y - (thicc / 2), thicc, thicc);
                
                /** Adds the x and y values always to an ArrayList*/
                
                data.add(x);
                data.add(y);
                count += 1;
                
                /** Counts how many points are currently present out of three*/
                
                if (count == 1){
                    ;
                    /** Do nothing because a point has already been made*/
                }
                else if (count == 2){
                    /** Draw a line between the first x and y and the second x and y*/
                    
                    double centerx = (data.get(data.size()-4));
                    double centery = (data.get(data.size()-3));
                    
                    UI.drawLine(centerx, centery, x, y);
                }
                else if (count == 3){
                    
                    
                    if (filler == "No-Fill"){
                        //[y3, x3, y2, x2, y1, x1]
                        
                        /** Above is a sort of visual on what the Array List looks like when count is 3*/
                    
                        double firstx = data.get(data.size()-6);
                        double firsty = data.get(data.size()-5);
                        
                        double secondx = data.get(data.size()-4);
                        double secondy = data.get(data.size()-3);
                        
                        /** Therfore we can simply get those values from the Array List and draw two lines accordingly*/
                        
                        /** In conclusion, counters and ArrayLIsts (anything that can store and retrieve multiple objects) is very powerful thing in coding. */
                        
                        
                        UI.drawLine(firstx, firsty, x, y);
                        UI.drawLine(secondx, secondy, x, y);
                        
                        count = 0;
                        data.clear();
                        
                        /** Lastly, we repeat the cycle again, set the count back to 0 and clear the ArrayList */
                    }
                    else if (filler == "Fill"){
                        //[y3, x3, y2, x2, y1, x1]
                    
                        
                        /** This is a repeat of above but now adds a fill polygon to fill the triangle - still we are using ArrayList for x and y values*/
                        
                        double firstx = data.get(data.size()-6);
                        double firsty = data.get(data.size()-5);
                        
                        double secondx = data.get(data.size()-4);
                        double secondy = data.get(data.size()-3);
                        
                        
                        UI.setColor(colorfill);
                        UI.fillPolygon(new double[]{firstx, secondx,x}, new double[]{firsty, secondy,y}, 3);
                
                        
                        UI.setColor(colorline);
                        UI.drawLine(firstx, firsty, x, y);
                        UI.drawLine(secondx, secondy, x, y);
                        
                        count = 0;
                        data.clear();
                    }
                                        
                }
                
            }
        
        }
        
        if (tool == "Line"){
            
            /** This is what the code does when the tool is Line*/
            
            if (action.equals("pressed")){
                
                startx = x;
                starty = y;

            }
            else if (action.equals("released")){
                
                endx = x;
                endy = y;
                
                /** Remembers startx, starty, endx, endy and passes it to DrawALine Function*/
                
                this.drawALine(startx, starty, endx, endy);

            }
            //else if (action.equals("dragged")){
                //UI.drawLine(startx, starty, x, y);
            //}
        
        }
        
        /** I understand that there is a lot of repetition in these next few lines but I haven't figured out how to more simply pass the require parameters in these scanarios*/
        
        if (tool == "Rectangle"){
            
            if (action.equals("pressed")){
                
                startx = x;
                starty = y;

            }
            else if (action.equals("released")){
                
                endx = x;
                endy = y;
                
                this.drawARectangle(startx, starty, endx, endy);

            }
        
        }
        
        if (tool == "Oval"){
            
            if (action.equals("pressed")){
                
                startx = x;
                starty = y;

            }
            else if (action.equals("released")){
                
                endx = x;
                endy = y;
                
                this.drawAnOval(startx, starty, endx, endy);

            }
        
        }
        
        if (tool == "Flower"){
            
            if (action.equals("pressed")){
                
                startx = x;
                starty = y;

            }
            else if (action.equals("released")){
                
                endx = x;
                endy = y;
                
                // Up right   x2 > x1 &&  y1 > y2
                
                if (endx > startx && starty > endy) {
                    double radius = Math.hypot(endx - startx, starty - endy);
                    this.drawAFlower(startx, starty, radius);
                }
                
                // Down right  x2 > x1  && y2 > y1
                
                if (endx > startx && endy > starty) {
                    double radius = Math.hypot(endx - startx, endy - starty);
                    this.drawAFlower(startx, starty, radius);
                }
                
                // Up Left   x1 > x2 && y1 > y2
                
                if (startx > endx && starty > endy) {
                    double radius = Math.hypot(startx - endx, starty - endy);
                    this.drawAFlower(startx, starty, radius);
                }
                
                // Down Left  x1 > x2 && y2 > y1
                
                if (startx > endx && endy > starty) {
                    double radius = Math.hypot(startx - endx, endy - starty);
                    this.drawAFlower(startx, starty, radius);
                }
                
            }
        
        }
        
        if (tool == "Text"){
            
            UI.setColor(java.awt.Color.black);
            UI.setFontSize(12);
            
            /** I have made it so that the actions is only when released - I could set it up as clicked, but any movement between pressed and released does not count as clicked*/
            
            if (action.equals("released")){
                
                startx = x;
                starty = y;
                
                this.drawACaption(startx, starty);

            }
        
        }
        
        if (tool == "Image"){
            
            if (action.equals("pressed")){
                
                startx = x;
                starty = y;

            }
            else if (action.equals("released")){
                
                endx = x;
                endy = y;
                
                this.drawAnImage(startx, starty, endx, endy);

            }
        }
        
        /** Below are the only two situations when the action is dragged, there is also "Rubber-Band" but that is for future improvements */
        
        if (tool == "Eraser"){
            
            if (action.equals("dragged")){
                
                UI.eraseOval(x - ((thicc + 20) / 2), y - (thicc / 2), thicc + 20, thicc + 20);

            }
        }
        
        /** Sketch was just a quick one I made for fun because I liked how the eraser worked and I wanted to make a sketch version for any colour */
        
        if (tool == "Sketch"){
            
            UI.setColor(colorline);
            
            if (action.equals("dragged")){
                
                UI.fillOval(x - ((thicc + 20) / 2), y - (thicc / 2), thicc + 20, thicc + 20);

            }
        }

    }

    /**
     * Draw a line between the two positions (x1, y1) and (x2, y2)
     */
    public void drawALine(double x1, double y1, double x2, double y2){
        /*# YOUR CODE HERE */
        
        UI.setColor(colorline);
        UI.drawLine(x1, y1, x2, y2);
        
    }
    
    /** [Completion]
     * Draws a simple flower with 6 petals, centered at (x,y) with the given radius
     */
    public void drawAFlower(double x, double y, double radius){
        /*# YOUR CODE HERE */
        
        //UI.println(radius / 3);
        //UI.println(x - (radius * (1/6)));
        //UI.println(y - (radius * (1/6)));
        
        /** A lot of Geometry and visualising and testing went into deriving what I have below*/
        
        /** To see a bit of a preview on my testing see the comments I've left at the very bottom of this code*/
        
        /** Circ_radius0 and circ_diameter0 are for the inner circle */
        
        double circ_radius0 = radius / (1.0 + (2.0 * (Math.cos(Math.toRadians(((petal - 2.0) / (petal * 2.0)) * 180.0)) / (1 - Math.cos(Math.toRadians((petal - 2.0) / (petal * 2.0)) * 180.0)))));
        double circ_diameter0 = circ_radius0 * 2;
        
        /** Circ_radius and circ_diameter are for the outer circles*/
        
        double circ_radius = circ_radius0 * (Math.cos(Math.toRadians(((petal - 2.0) / (petal * 2.0)) * 180.0)) / (1 - Math.cos(Math.toRadians((petal - 2.0) / (petal * 2.0)) * 180.0)));
        double circ_diameter = circ_radius * 2;
        
        /** Instructions want the inner circle to be yellow*/
        
        UI.setColor(java.awt.Color.yellow);
        
        /** Below is the basic code to draw the inner circle*/
        
        UI.fillOval(x - circ_radius0, y - circ_radius0, circ_diameter0, circ_diameter0); // center
        
        UI.setColor(java.awt.Color.black);
        
        UI.setColor(colorfill);
        
        /** I've very proud of this for loop which makes circles based on the number of petals*/
        
        for (int i = 0; i < petal; i++){
            
            /** It converts its working angle based on i * (360 / petal) example with 6 petals   [0, 60, 120, 180, 240, 300] example with 8 petals   [0, 45, 90, 135, 180, 225, 270, 315] */
            
            UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(i * (360.0 / petal)))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(i * (360.0 / petal)))) - circ_radius, circ_diameter, circ_diameter);
            
        }

    }
    
    /** This is the function that draws the rectangle based on startx, endx, starty, endy positions*/
    public void drawRect(double endx, double startx, double endy, double starty){
        if (endx > startx && endy > starty){
            
            UI.drawRect(startx, starty, endx-startx, endy-starty);
        }
    }
    
    /** This is the function that draws the Oval based on startx, endx, starty, endy positions*/
    public void drawOval(double endx, double startx, double endy, double starty){
        if (endx > startx && endy > starty){
            
            UI.drawOval(startx, starty, endx-startx, endy-starty);
        }
    }
    
    /** This is the function that fills the rectangle and draws the outline based on startx, endx, starty, endy positions*/
    public void fillRect(double endx, double startx, double endy, double starty){
        if (endx > startx && endy > starty){
                
            UI.setColor(colorfill);
            UI.fillRect(startx, starty, endx-startx, endy-starty);
            
            UI.setColor(colorline);
            UI.drawRect(startx, starty, endx-startx, endy-starty);
        }
    }
    
    /** This is the function that fills the oval and draws the outline based on startx, endx, starty, endy positions*/
    public void fillOval(double endx, double startx, double endy, double starty){
        if (endx > startx && endy > starty){
                
            UI.setColor(colorfill);
            UI.fillOval(startx, starty, endx-startx, endy-starty);
            
            UI.setColor(colorline);
            UI.drawOval(startx, starty, endx-startx, endy-starty);
        }
    }
    
    /** This is the function that draws the Image based on startx, endx, starty, endy positions*/
    public void drawImage(double endx, double startx, double endy, double starty, double min_width, double min_height){
        if (endx > startx && endy > starty){
            
            if (endx-startx <= min_width && endy-starty <= min_height){
                UI.drawImage(file, startx, starty);
            }
            else{  
                UI.drawImage(file, startx, starty, endx-startx, endy-starty);   
            }
            
        }
    }
    
    /**
     * Draw a rectangle between the two diagonal corners
     * [Completion] Works out the left, top, width, and height 
     * Then draws the rectangle, based on the options
     */
    public void drawARectangle(double x1, double y1, double x2, double y2){
        /*# YOUR CODE HERE */
        
        if (filler == "No-Fill"){
            
            UI.setColor(colorline);
            
            /** There are eight possible situations, fill, nofill, and the directions that the user decided to draw*/
            
            this.drawRect(x2, x1, y2, y1);  // Down Right (x2 > x1  ,  y2 > y1)
            this.drawRect(x1, x2, y2, y1);  // Down Left (x1 > x2  ,  y2 > y1)
            this.drawRect(x2, x1, y1, y2);  // Up Right (x2 > x1  ,  y1 > y2)
            this.drawRect(x1, x2, y1, y2);  // Up Left (x1 > x2  ,  y1 > y2)
            
        }
        else if (filler == "Fill"){
            
            this.fillRect(x2, x1, y2, y1);  // Down Right (x2 > x1  ,  y2 > y1)
            this.fillRect(x1, x2, y2, y1);  // Down Left (x1 > x2  ,  y2 > y1)
            this.fillRect(x2, x1, y1, y2);  // Up Right (x2 > x1  ,  y1 > y2)
            this.fillRect(x1, x2, y1, y2);  // Up Left (x1 > x2  ,  y1 > y2)
            
        }
        
    }

    /**
     * Draw an oval to fit the rectangle between the the two diagonal corners
     * [Completion] Works out the left, top, width, and height 
     * Then draws the oval, based on the options
     */
    public void drawAnOval(double x1, double y1, double x2, double y2){
        /*# YOUR CODE HERE */
        
        if (filler == "No-Fill"){
            
            UI.setColor(colorline);
            
            /** There are eight possible situations, fill, nofill, and the directions that the user decided to draw*/
            
            this.drawOval(x2, x1, y2, y1);  // Down Right (x2 > x1  ,  y2 > y1)
            this.drawOval(x1, x2, y2, y1);  // Down Left (x1 > x2  ,  y2 > y1)
            this.drawOval(x2, x1, y1, y2);  // Up Right (x2 > x1  ,  y1 > y2)
            this.drawOval(x1, x2, y1, y2);  // Up Left (x1 > x2  ,  y1 > y2)
            
        }
        else if (filler == "Fill"){
            
            
            this.fillOval(x2, x1, y2, y1);  // Down Right (x2 > x1  ,  y2 > y1)
            this.fillOval(x1, x2, y2, y1);  // Down Left (x1 > x2  ,  y2 > y1)
            this.fillOval(x2, x1, y1, y2);  // Up Right (x2 > x1  ,  y1 > y2)
            this.fillOval(x1, x2, y1, y2);  // Up Left (x1 > x2  ,  y1 > y2)
            
        }

    }

    /** 
     * Draws the current caption at the mouse released point.
     */
    public void drawACaption(double x, double y){
        /*# YOUR CODE HERE */
        
        UI.setFontSize(weight);
        UI.setColor(colorline);
        UI.drawString(caption, x - (caption.length() * weight * 0.4 * 0.5), y);
        
        /** The x position is slightly modified to try to make the caption be drawn at the center of the cursor position*/

    }

    /** [Completion]
     * Draws the current image between the two diagonal corners, unless
     *  they are very close, and then just draws the image at its natural size
     *  Works out the left, top, width, and height 
     * Then draws the image, if there is one.
     */
    public void drawAnImage(double x1, double y1, double x2, double y2){
        /*# YOUR CODE HERE */
                
        //UI.drawImage(file, x1, y1, x2 - x1, y2 - y1);
        
        // I UNDERSTAND THAT THE INSTRUCTIONS SAY POSITIONS WITH 5 PIXELS OF EACHOTHER BUT I THINK THAT IS A BIT TOO SMALL
        
        // THEREFORE BELOW I HAVE INCREASED THE SIZE FROM 5 PIXELS, AT ANYTIME I CAN SET THIS TO 5 PIXELS DEMONSTRATING I KNOW HOW TO DO IT
        
        double min_width = 64;  //double min_width = 5;
        double min_height = 36;  //double min_height = 5;
        
        /** There are four possible situations, only the directions that the user decided to draw*/
        
        this.drawImage(x2, x1, y2, y1, min_width, min_height);  // Down Right (x2 > x1  ,  y2 > y1)
        this.drawImage(x1, x2, y2, y1, min_width, min_height);  // Down Left (x1 > x2  ,  y2 > y1)
        this.drawImage(x2, x1, y1, y2, min_width, min_height);  // Up Right (x2 > x1  ,  y1 > y2)
        this.drawImage(x1, x2, y1, y2, min_width, min_height);  // Up Left (x1 > x2  ,  y1 > y2)

    }


    // Main:  constructs a new MiniPaint object and set up GUI
    public static void main(String[] arguments){
        MiniPaint mp = new MiniPaint();
        
        mp.setupGUI();
    }


}

/*
 * 
 * THIS IS A LOT OF GEOMETRIC TESTING THAT WENT INTO MAKING THE VARIABLE PETAL FLOWER DRAWER
         * 
         * double radians = Math.toRadians(60);
        double right = Math.cos(radians);
        double up = Math.sin(radians);
        
        double radians2 = Math.toRadians(120);
        double right2 = Math.cos(radians2);
        double up2 = Math.sin(radians2);
        
        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(45))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(45))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(90))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(90))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(135))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(135))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(180))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(180))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(225))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(225))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(270))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(270))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(315))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(315))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(360))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(360))) - circ_radius, circ_diameter, circ_diameter);
        
    
        
        
        UI.fillOval(x - circ_radius0, y - circ_radius0, circ_diameter0, circ_diameter0); // center
        
        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(0))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(0))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(72))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(72))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(144))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(144))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(216))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(216))) - circ_radius, circ_diameter, circ_diameter);

        UI.fillOval(x + ((circ_radius0 + circ_radius) * Math.cos(Math.toRadians(288))) - circ_radius, y + ((circ_radius0 + circ_radius) * Math.sin(Math.toRadians(288))) - circ_radius, circ_diameter, circ_diameter);

        
        
        
        UI.fillOval(x - circ_radius, y - circ_radius, circ_diameter, circ_diameter); // center
        
        UI.fillOval(x + (circ_diameter * right) - circ_radius, y + (circ_diameter * up) - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x + (circ_diameter * right) - circ_radius, y - (circ_diameter * up) - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x - (circ_diameter * right) - circ_radius, y + (circ_diameter * up) - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x - (circ_diameter * right) - circ_radius, y - (circ_diameter * up) - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x - (circ_diameter * right) - circ_radius, y - (circ_diameter * up) - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x + (circ_diameter) - circ_radius, y - circ_radius, circ_diameter, circ_diameter);
        
        UI.fillOval(x - (circ_diameter) - circ_radius, y - circ_radius, circ_diameter, circ_diameter);
        
        */
