package team5427.lib.filters;

import java.util.List;

import org.checkerframework.checker.units.qual.A;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.PnpResult;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Transform3d;

//gets the data from a filtered image
public class photonVisionFilter{
    private PhotonCamera camera; //the camera
    private PhotonPipelineResult result; //the filtered image
    private PhotonTrackedTarget bestTarget; // the best target
    private Transform3d bestCameraToTarget; //3d pose

    private double yaw; //horizontal angle of target
    private double pitch; //vertical angle of target
    private double area; //area percentage of target
    private double skew; //target rotation

    private double poseAmbiguity; //reliability of detections

    //measured in meters
    private double targetX; //forward distance
    private double targetY; //horizontal distance
    private double targetZ; //vertical distance

    private int fiducialID; //ID of the april tag

    private double latencyInMilliseconds; //processing time
    private double timeStampSeconds; //time when image was taken

    //other information
    private List<PhotonTrackedTarget> allTargets;
    private List<TargetCorner> corners;

    public photonVisionFilter(String cameraName){
        camera=new PhotonCamera(cameraName);
        getResults();
    }
    private void getResults(){
        //gets the final filtered image from the camera
        //all the filters are applied by photonvision automatically
        result = camera.getLatestResult();

        //if statement checks for null pointer exceptions
        if(result.hasTargets()){
            //gets the best target
            bestTarget = result.getBestTarget();

            //gets target information
            yaw = bestTarget.getYaw();
            pitch = bestTarget.getPitch();
            area = bestTarget.getArea();
            skew = bestTarget.getSkew();

            //reliability of the information
            poseAmbiguity = bestTarget.getPoseAmbiguity();

            //april tag id
            fiducialID = bestTarget.getFiducialId();

            //stores the corners of the target
            corners = bestTarget.getDetectedCorners();

            //gets 3d pose info
            bestCameraToTarget = bestTarget.getBestCameraToTarget();

            targetX=bestCameraToTarget.getX();
            targetY=bestCameraToTarget.getY();
            targetZ=bestCameraToTarget.getZ();

            //stores multiple targets, if needed
            allTargets=result.getTargets();
        }else{
            bestTarget=null;
        }
        //not sure why latency command isn't working despite checking imports and documentations
        latencyInMilliseconds = result.getLatencyMillis();
        timeStampSeconds = result.getTimestampSeconds();
    }

    //target info getters
    public double getTargetYaw(){
        return yaw;
    }
    public double getTargetPitch(){
        return pitch;
    }
    public double getTargetArea(){
        return area;
    }
    public double getTargetSkew(){
        return skew;
    }
    public double getPoseAmbiguity(){
        return poseAmbiguity;
    }
    public int getFiducialId(){
        return fiducialID;
    }

    //3d pose getters
    public double getCameraToTargetX(){
        return targetX;
    }
    public double getCameraToTargetY(){
        return targetY;
    }
    public double getCameraToTargetZ(){
        return targetZ;
    }

    //other info
    public double getResultLatency(){
        return latencyInMilliseconds;
    }
    public double getResultTimestamp(){
        return timeStampSeconds;
    }
    public List<TargetCorner> getTargetCorners(){
        return corners;
    }
    public List<PhotonTrackedTarget> getAllTargets(){
        return allTargets;
    }
    public boolean hasTarget(){
        if(bestTarget!=null){
            return true;
        }
        return false;
    }
}