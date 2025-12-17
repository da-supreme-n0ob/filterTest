package team5427.lib.filters.soft.highPassFilters;

//filter for sharpening edges in a grayscale image
public class sharpeningFilter {
    //there are a few "intensities" of kernels
    private final int[][] kernelNormal = {
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    };
    private final int[][] kernelStrong = {
            {-1,-1,-1},
            {-1,9,-1},
            {-1,-1,-1}
    };
    private final int[][] kernelWeak = {
            {0,0,0},
            {0,2,0},
            {0,0,0}
    };
    private int[][] originalImage;
    private int[][] sharpenedImage;

    public sharpeningFilter(int[][] input, int width, int height,int intensity){
        //intensity is a integer from 0-2 and determines kernel used
        this.originalImage=input;
        switch(intensity){
            case 0:
                calculate(width,height,kernelNormal);
                break;
            case 1:
                calculate(width,height,kernelStrong);
                break;
            case 2:
                calculate(width,height,kernelWeak);
                break;
            default:
                calculate(width,height,kernelNormal);
                break;
        }

    }
    public void calculate(int width,int height, int[][] kernel){
        sharpenedImage=new int[height][width];
        //applies kernel
        for(int i=1;1<height-1;i++){//skips the borders
            for(int j=1;j<width-1;j++){
                int sum=0;
                //applies kernels
                for(int y=-1;y<=1;y++){
                    for(int x=-1;x<=1;x++){
                        sum+=originalImage[i+y][j+x]*kernel[y+1][x+1];
                    }
                }
                //makes sure sum is in bounds
                if(sum<0){
                    sum=0;
                }else if(sum>255){
                    sum=255;
                }
                sharpenedImage[i][j]=sum;
            }
        }
        //copies the remaining borders onto the output
        //horizontal borders
        for(int i=0;i<width;i++){
            sharpenedImage[0][i]=originalImage[0][i];
            sharpenedImage[height-1][i]=originalImage[height-1][i];
        }
        //vertical borders
        for(int j=0;j<width;j++){
            sharpenedImage[j][0]=originalImage[j][0];
            sharpenedImage[j][width-1]=originalImage[j][width-1];
        }
    }
    public int[][] getFilteredImage(){
        return sharpenedImage;
    }
}
