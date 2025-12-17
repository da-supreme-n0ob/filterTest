package team5427.lib.filters.soft.lowPassFilters;

import java.util.Arrays;

public class linearFilter {
    //moving-average filter
    private static final int windowSize = 10;
    private static double[] window;
    private double currentAverage;

    public linearFilter(double value){
        window= new double[]{value};
        this.currentAverage=calculateAverage(window);
    }

    public void addValue(double value){
        if(window.length+1<=10){
            //array length will be still be less than n, so no deletion is necessary
            double[] tempArr = new double[window.length+1];
            for(int i=0;i< window.length;i++){
                tempArr[i]= window[i];
            }
            tempArr[window.length]=value;
            window=tempArr;
        }else{
            //slides all inputs 1 index to the left
            for(int i=0;i<windowSize-1;i++){
                window[i]=window[i+1];
            }
            window[windowSize-1]=value;
        }
        this.currentAverage=calculateAverage(window);
    }

    private double calculateAverage(double[] array){
        double total=0;
        for(double x:array){
            total+=x;
        }
        return total/array.length;
    }

    public double getCurrentAverage(){
        return currentAverage;
    }
}
