package team5427.lib.filters.soft.otherFilters;

import java.util.Arrays;

public class standardDeviation{
    private final double baseStandardDeviation=0.1;
    private double newStandardDeviation;
    private double ambiguity;
    private double area;
    private double distance;
    private final double ambiguityWeight=4; //not sure how to balance those constants
    private final double areaWeight= 2;
    private final double distanceWeight = 0.1;
    private static double[] pictureStandardDeviation=null;

    public standardDeviation(double ambiguity, double area, double distance){
        this.ambiguity=ambiguity;
        this.area=area;
        this.distance=distance;
        calculate();
    }

    private void calculate(){
        //if the ambiguity is too high, the picture won't be processed
        if(ambiguity<0.2){
            //formula
            newStandardDeviation = baseStandardDeviation+ambiguityWeight*ambiguity+distanceWeight*Math.pow(distance,2)+1/Math.min((areaWeight*area),1);
            //minimum on area part makes sure a very small area won't inflate the stddev too much
        }

        //updates the array
        if(pictureStandardDeviation==null){
            double[] tempArr = {newStandardDeviation};
            pictureStandardDeviation = tempArr;
        }else{
            double[] tempArr = new double[pictureStandardDeviation.length+1];
            for(int i=0;i<tempArr.length-1;i++){
                tempArr[i]=pictureStandardDeviation[i];
            }
            tempArr[tempArr.length-1]=newStandardDeviation;
            pictureStandardDeviation = tempArr;
        }
        Arrays.sort(pictureStandardDeviation);
    }
    public double getStandardDeviation(){
        return newStandardDeviation;
    }
    public static double[] getPictureStandardDeviationArray(){
        return pictureStandardDeviation;
    }
}
