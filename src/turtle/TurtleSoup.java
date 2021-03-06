package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(final Turtle turtle, int sideLength) {
        final int SIDES_OF_SQUARE = 4;
        for (int i = 0; i < SIDES_OF_SQUARE; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, must be >2
     * @return angle, in degrees between 0 and 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return  (sides - 2.0) * 180.0 / sides;
    }

    /**
     * Determine number of sides given the interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle interior angles, in degrees
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        // The sum of exterior angles should be 360 degrees
        // Divide 360 by the exterior angle to find the number of sides
        return Math.round(360 / (180 - (float)angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double rotation = 180 - calculateRegularPolygonAngle(sides); // Turtle will turn by the external angle
        for(int i = 0; i < sides; i++){
            turtle.forward(sideLength);
            turtle.turn(rotation);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing the angle currentHeading. The return angle must be expressed in
     * degrees, and 0 <= angle < 360.
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX currentY current location
     * @param targetX targetY target point
     * @return adjustment to heading (right turn amount) to get to target point.
     *         Must be positive, and less than 360.
     */
    public static double calculateHeadingToPoint(double currentHeading, double currentX, double currentY,
            double targetX, double targetY) {
        double sideX = targetX - currentX;
        double sideY = targetY - currentY;
        // atan2 gives me angle at 0 in east, to change to 0 north (positive clockwise)
        // atan2 returns radians and (positive anticlockwise)
        double angleFromEast = Math.toDegrees(Math.atan2(sideY,sideX));
        double angleFromNorth = 0;


        if(-180 <= angleFromEast && angleFromEast <= 90){
            angleFromNorth = 90  - angleFromEast;
        }
        else if(angleFromEast > 90){
            angleFromNorth = 360 - (angleFromEast - 90);
        }

        // Blows my mind XoX
        // double angleFromNorth = Math.toDegrees(Math.atan2(sideX,sideY));
        if (angleFromNorth - currentHeading >= 0){
            return angleFromNorth - currentHeading;
        }
        else{
            return 360 + (angleFromNorth - currentHeading);
        }
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point to the next.
     * 
     * You should use your calculateHeadingToPoint() function in this one to simplify things. Assume
     * that the turtle is at the first point, facing up (i.e. 0 degrees), and for further points,
     * assume that the turtle is facing the direction last specified.  
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size #points-1.
     */
    public static List<Double> calculateHeadings(List<Double> xCoords, List<Double> yCoords) {
        List<Double> headingAdjustments = new ArrayList<>();
        double currentHeading = 0;
        for(int i = 0; i < xCoords.size()-1 ; i++){
            currentHeading = calculateHeadingToPoint(currentHeading, xCoords.get(i), yCoords.get(i), xCoords.get(i+1), yCoords.get(i+1));
            headingAdjustments.add(currentHeading);
        }
        return headingAdjustments;
    }

    /**
     * Gives the midPoint
     * @param currentX currentY current location
     * @param targetX targetY target point
     * @return midPoint
     */
    private static double[] midPoint(double currentX, double currentY, double targetX, double targetY){
        double[] midPoint = new double[2];
        midPoint[0] = (currentX + targetX) / 2;
        midPoint[1] = (currentY + targetY) / 2;
        return midPoint;
    }

    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * We'll be peer-voting on the different images, and the highest-rated one will win a prize:
     * free dinner with select staff members.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        List<Double> xCoords = new ArrayList<>();
        xCoords.add(0.);
        xCoords.add(50.);
        xCoords.add(100.);
        xCoords.add(0.);
        List<Double> yCoords = new ArrayList<>();
        yCoords.add(0.);
        yCoords.add(86.6);
        yCoords.add(0.);
        yCoords.add(0.);
        
        List<Double> triangle1Headings = new ArrayList<>();
        triangle1Headings = calculateHeadings(xCoords,yCoords);
        
        for(int i = 0; i <triangle1Headings.size(); i++){
            turtle.turn(triangle1Headings.get(i));
            turtle.forward(100);
        }
    }

    /**
     * Main method
     * 
     * This is the method that runs when you run "java TurtleSoup".  Right now, it
     * will run your drawSquare() method.
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        // draw the window
        turtle.draw();

        //        drawSquare(turtle,60);
        //        drawRegularPolygon(turtle, 6, 60);
        drawPersonalArt(turtle);

    }

}
