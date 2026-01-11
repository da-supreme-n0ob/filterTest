package team5427.lib.filters.soft.lowPassFilters;

import java.util.Arrays;

public class linearFilter {
    //moving-average filter
    private static final int windowSize = 10;
    private double[][] inputImage;
    private double[][] finalImage;

    public linearFilter(double[][] input){
        inputImage = input;
        finalImage = calculate(inputImage);
    }

    public double[][] calculate(double[][] input){
        double[][] output = input;

        for(int i=1;i<output.length-1;i++){
            for(int j=1;j<output[0].length-1;j++){
                double neighbourSum = 0;

                for(int y=-1;y<=1;y++){
                    for(int x=-1;x<=1;x++){
                        neighbourSum += input[i+y][j+x];
                    }
                }
                neighbourSum/=9;

                if(neighbourSum<0){
                    neighbourSum = 0;
                }else if(neighbourSum>255){
                    neighbourSum = 255;
                }

                output[i][j] = neighbourSum;
            }
        }

        return output;
    }

    public double[][] getResult(){
        return finalImage;
    }
}
