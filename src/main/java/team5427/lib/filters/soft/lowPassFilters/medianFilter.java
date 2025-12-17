package team5427.lib.filters.soft.lowPassFilters;

import java.util.Arrays;

public class medianFilter {
    private static final int windowSize = 10;
    private static double[] window;
    private double currentMedian;

    public medianFilter(double value){
        window = new double[]{value};
        this.currentMedian=calculateMedian(window);
    }

    public void addValue(double value){
        if(window.length+1<=10){
            //array length will be still be less than n, so no deletion is necessary
            double[] tempArr = new double[window.length+1];
            for(int i = 0; i< window.length; i++){
                tempArr[i]= window[i];
            }
            tempArr[window.length]=value;
            window =tempArr;
        }else{
            //slides all inputs 1 index to the left
            for(int i=0;i<windowSize-1;i++){
                window[i]= window[i+1];
            }
            window[windowSize-1]=value;
        }
        this.currentMedian=calculateMedian(window);
    }

    private double calculateMedian(double[] array){
        double[] sortedArr = array;
        Arrays.sort(sortedArr);
        if(array.length%2==0){
            return (sortedArr[array.length/2-1]+sortedArr[array.length/2])/2;
        }else{
            return sortedArr[(int) (array.length/2)];
        }
    }

    public double getCurrentMedian(){
        return currentMedian;
    }
}
