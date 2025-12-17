package team5427.lib.filters.soft.highPassFilters;
//edge detection
public class soberFilter {
    
    //standard sobel kernels
    /*
    * while i was researching there was a lot more
    * different types of kernels used, but this was
    * the most common
    */
    //kernel for vertical edges
    private final int[][] sobelX={
            {-1,0,1},
            {-2,0,2},
            {-1,0,1},
    };
    //kernel for horizontal edges
    private final int[][] sobelY={
            {-1,-2,-1},
            {0,0,0},
            {1,2,1},
    };

    private int rows;
    private int columns;
    private int[][] image;
    private int[][] output;

    public soberFilter(int[][] input, int height,int width){
        this.image=new int[height][width];
        this.output=calculate(height,width);
    }
    public int[][] calculate(int height,int width){
        for(int i=0;i<height-1;i++){
            for(int j=0;j<width-1;j++){
                int x=applyKernel(image,i,j,sobelX);
                int y=applyKernel(image,i,j,sobelX);

                //gradient magnitude formula
                int magnitude = (int) Math.sqrt(x*x+y*y);
                //keeps magnitude in bound
                if(magnitude<0){
                    magnitude=0;
                }else if(magnitude>255){
                    magnitude=255;
                }
                output[i][j]=magnitude;
            }
        }
        return output;
    }
    public int applyKernel(int[][] input, int x, int y, int[][] kernel){
        int total=0;

        //loops through a 3x3 "neighborhood"
        for(int i=-1;i<=1;i++){
            for(int j=-1;j<=1;j++){
                int pixel = image[y+i][x+j];
                int kernelValue = kernel[i+1][j+1];
                total+=pixel*kernelValue;
            }
        }

        return total;
    }
    public int[][] getOutput(){
        return output;
    }
}
