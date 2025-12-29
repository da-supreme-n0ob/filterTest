package team5427.lib.filters.soft.otherFilters;

import java.util.Arrays;

import edu.wpi.first.wpilibj.Timer;
import team5427.lib.filters.photonVisionFilter;

public class standardDeviation{
    private final double baseStandardDeviation=0.35;
    private double newStandardDeviation;
    private double ambiguity;
    private double area;
    private double distance;
    private double timeElapsed;

    //balancing based on research, can alter if necessary
    private final double ambiguityWeight=3;
    private final double areaWeight= 0.4;
    private final double nominalArea = 0.02;
    private final double epsilon = 1e-6;//helps prevent division by zero when area is too small
    private final double distanceWeight = 0.15;
    private final double timeElapsedWeight = 2;

    public standardDeviation(String cameraName){
        photonVisionFilter camera = new photonVisionFilter(cameraName);
        this.ambiguity = camera.getPoseAmbiguity();
        this.area = camera.getTargetArea();
        this.distance = getDistance(camera);
        this.timeElapsed = Timer.getFPGATimestamp()-camera.getResultTimestamp();
        calculate();
    }
    
    private double getDistance(photonVisionFilter camera){
        if(camera.hasTarget()){
            double x = camera.getCameraToTargetX();
            double y = camera.getCameraToTargetY();
            double z = camera.getCameraToTargetZ();

            double distance = Math.sqrt(x*x+y*y+z*z);
            return distance;
        }else{
            return -1;
        }
    }

    private void calculate(){
        //broken up parts of the formula
        double distancePart = distanceWeight*distance*distance;
        double ambiguityPart = ambiguityWeight*ambiguity;
        double areaPart = areaWeight*(nominalArea/(area+epsilon));
        double timeElapsedPart = timeElapsedWeight*timeElapsed;

        //formula
        double factor = 1+distancePart+ambiguityPart+areaPart+timeElapsedPart;
        newStandardDeviation=baseStandardDeviation*factor;

        //rejects if certain things are too extreme
        if(distance>7||ambiguity>0.35||area<0.003||newStandardDeviation>3){
            newStandardDeviation=100;
        }
    }
    public double getStandardDeviation(){
        return newStandardDeviation;
    }
}
