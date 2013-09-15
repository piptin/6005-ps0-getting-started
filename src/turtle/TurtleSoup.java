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
        drawRegularPolygon(turtle, SIDES_OF_SQUARE, sideLength);
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
        return ((double) ((sides - 2) * 180)) / sides;
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
        // The sum of exerior angles should be 360 deg
        // Divide 360 by the exterior angle to find the number of sides
        return Math.round(360 / (180 - (float) angle));
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
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
        int diffX = targetX - currentX;
        int diffY = targetY - currentY;
        double angleFromNorth = Math.toDegrees(Math.atan2(diffX, diffY));
        double angle = angleFromNorth - currentHeading;
        // normalize angle to be positive 
        if(angle < 0)
            angle += 360;
        return angle;
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
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> headingChanges = new ArrayList<Double>();
        int numberOfCoordinates = xCoords.size();
        double currentHeading = 0;
        for(int i = 1; i < numberOfCoordinates; i++){
            double adjustment = calculateHeadingToPoint(currentHeading, xCoords.get(i-1), yCoords.get(i-1), xCoords.get(i), xCoords.get(i));
            currentHeading += adjustment;
            headingChanges.add(adjustment);
        }
        return headingChanges;
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
        // onion slice
        for(int i = 0; i < 15; i++){ // i < 15 to limit size of shapes
            drawRegularPolygon(turtle, i, i*10);
            drawRegularPolygon(turtle, i*10, i);
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
        
        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }

}
