package team5427.lib.filters.soft.lowPassFilters;
import java.util.Arrays;

//gaussian blur is used to reduce image noise by blurring pixels with their surrounding neighbors
public class gaussianFilter {
    //2D array implementation, may be buggy
    private double[][] kernel2D;
    private double[] kernel1D;
    private double[][] image;
    private int kernelSize;
    private double standardDeviation;
    private double[][] gaussianWeightedImage;;

    public gaussianFilter(double[][] image, int kernelSize, double standardDeviation){
        this.image=image;
        //note, kernelSize must be odd
        this.kernelSize=kernelSize;
        this.standardDeviation=standardDeviation;
        apply1DKernel(kernelSize,standardDeviation);
        /*
        apply2DKernel(kernelSize,standardDeviation);
         */
    }

    /*************2D ARRAYS***************/
    public double[][] generate2DKernel(int kernelSize, double standardDeviation){
        double[][] kernelArr = new double[kernelSize][kernelSize];
        double total=0;

        for(int i=0;i<kernelSize;i++){
            for(int j=0;j<kernelSize;j++){
                //horizontal distance from center
                int x = i-kernelSize/2;
                //vertical distance from center
                int y = j-kernelSize/2;

                //gaussian formula
                double exponent = -(x*x+y*y)/(2*Math.pow(standardDeviation,2));
                kernelArr[i][j]=Math.exp(exponent);
                total+=kernelArr[i][j];
            }
        }

        //the total sum of all the weights should be 1
        for(int i=0;i<kernelSize;i++){
            for(int j=0;j<kernelSize;j++){
                kernelArr[i][j]/=total;
            }
        }

        return kernelArr;
    }
    public void apply2DKernel(int kernelSize, double standardDeviation){
        this.kernel2D=generate2DKernel(kernelSize,standardDeviation);
        this.gaussianWeightedImage = new double[kernel2D.length][kernel2D[0].length];

        for(int i=0;i<image.length;i++){
            for(int j=0;j<image[0].length;j++){
                double totalProduct=0;

                //applies the kernel weights and the neighbors
                for(int k=0;k<kernelSize;k++){
                    for(int l=0;l<kernelSize;l++){
                        //got some help from claude here
                        //the code here basically locates the "neighbors" around the current index
                        int indexI = i+k-kernelSize/2;
                        int indexJ = j+l-kernelSize/2;

                        //if the "neighbor" is out of bounds, it finds the closest index instead
                        indexI=Math.max(0,Math.min(image.length-1,indexI));
                        indexJ=Math.max(0,Math.min(image[0].length-1,indexJ));

                        totalProduct+=image[i][j]*kernel2D[k][l];
                    }
                }

                gaussianWeightedImage[i][j]=totalProduct;
            }
        }
    }
    //returns the final gaussian blurred image
    public double[][] get2DGaussianWeightedImage(){
        return get2DGaussianWeightedImage();
    }

    /*************1D ARRAYS***************/
    //Separated 1D arrays, which should be more optimized than calculating 2D arrays
    //not sure if this code is correct, i got help from online resources and claude
    public double[] generate1DKernel(int kernelSize,double standardDeviation){
        double [] kernelArr = new double[kernelSize];
        double total=0;

        for(int i=0;i<kernelSize;i++){
            int x = i-kernelSize/2;
            //gaussian formula
            kernelArr[i]=Math.exp(-(x*x)/(2*Math.pow(standardDeviation,2)));
            total+=kernelArr[i];
        }

        //makes sure sum is equal to 1
        for(int i=0;i<kernelSize;i++){
            kernelArr[i]/=total;
        }

        return kernelArr;
    }
    public void apply1DKernel(int kernelSize, double standardDeviation){
        this.kernel1D=generate1DKernel(kernelSize,standardDeviation);
        double[][] temp = new double[image.length][image[0].length];

        //horizontal
        for(int i=0;i< image.length;i++){
            for(int j=0;j<image[0].length;j++){
                double totalProduct=0;

                for(int k=0;k<kernelSize;k++){
                    //index for neighbors
                    int indexJ = j+k-kernelSize/2;

                    //solves any issues with neighbors being out of bounds
                    indexJ = Math.max(0,Math.min(image[0].length-1,indexJ));

                    totalProduct+=image[i][indexJ]*kernel1D[k];
                }
                temp[i][j]=totalProduct;
            }
        }

        //vertical
        for(int i=0;i< image.length;i++){
            for(int j=0;j<image[0].length;j++){
                double totalProduct=0;

                for(int k=0;k<kernelSize;k++){
                    //index for neighbors
                    int indexI = i+k-kernelSize/2;

                    //solves any issues with neighbors being out of bounds
                    indexI = Math.max(0,Math.min(image.length-1,indexI));

                    totalProduct+=image[indexI][j]*kernel1D[k];
                }
                temp[i][j]=totalProduct;
            }
        }
        this.gaussianWeightedImage=temp;
    }
    public double[][] get1DGaussianWeightedImage(){
        return gaussianWeightedImage;
    }
}
