/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package brownianislands;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Dasty
 */
public class BrownianBridge 
{
    // Implementation a Brownian Bridge.
    // by David Blankley
    // based mostly on the algorithm proposed in Glasserman.

    // Goal of the algorithm:
    // Generate a WN process that follows a random path from start value to
    // end value with (2^m) steps.
    // NOTE: this code does not yet address variance!!

    //Inputs:
    // startValue
    // Initial value of the process.
    // endValue
    // End value of the process
    // timeStep
    // The magnitude of the timestep between start and end
    // numSplits
    // Easier to state it this way for now.
    
    Random r;
    double startValue = 0;
    double endValue = 0;
    double timeStep = 1;
    int numSplits = 20;
    int numPoints = (int) (Math.pow(2, 20) +1);
    double[] pathRes = new double[numPoints];
    
    
    double lowerIndex = 1;
    double upperIndex = numPoints;
    

    public BrownianBridge()
    {
        r = new Random();
        pathRes[1] = startValue;
        pathRes[numPoints-1] = endValue;
        buildOnePath(startValue,endValue,numSplits,lowerIndex,upperIndex,1);

        
    }
    
    // calculate a random posn for the point midway between start and end.
    // the points expected value is the linear interpolation between the two points
    private double calcPoint(double startValue,double endValue,double timeStep) 
    {
        double halfTS = timeStep/2;
        // unscaledVol <- ((halfTS-0)*(timeStep-halfTS))/timeStep
        // more simply:
        double unscaledVol = Math.sqrt( ((halfTS)*(halfTS))/timeStep );
        return ( ((startValue+endValue)/2) + (unscaledVol)*10*r.nextGaussian());
    }

    private void buildOnePath(double startValue,double endValue,double numSplits,double lowerI,double upperI,double timeval) 
    {
        // will always be an int.
        int midIndex = (int) ((lowerI+upperI)/2);
        double midPath = calcPoint(startValue,endValue,timeval);
        if (numSplits > 1 && midIndex != numPoints-1) {
            //left side
            pathRes[midIndex] = midPath;
            buildOnePath(startValue,pathRes[midIndex],numSplits-1,lowerI,midIndex,timeval/1);
            //right side
            buildOnePath(pathRes[midIndex], endValue,numSplits-1,midIndex,upperI,timeval/1);
        }
        // have a feeling this is hugely inefficient use of resources
    }

    
}
