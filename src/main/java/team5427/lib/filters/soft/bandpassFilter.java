package team5427.lib.filters.soft;
//lets through frequencies in a specified range
public class bandpassFilter {
    private double lowFreqCap;
    private double highFreqCap;
    private double sampleTime;//time between samples

    private double lastInput;
    private double lastHighOutput;
    private double lastLowOutput;

    private double highWeight;//weights on old and new inputs
    private double lowWeight;

    public bandpassFilter(double lowFreqCap, double highFreqCap, double sampleTime){
        this.lowFreqCap=lowFreqCap;
        this.highFreqCap=highFreqCap;
        this.sampleTime=sampleTime;

        //RC formula
        double highRC = 1./(2.*Math.PI*lowFreqCap);
        this.highWeight =highRC/(highRC+sampleTime);
        double lowRC = 1./(2.*Math.PI*highFreqCap);
        this.lowWeight =sampleTime/(lowRC+sampleTime);

        this.lastInput=0;
        this.lastHighOutput=0;
        this.lastLowOutput=0;
    }
    public double calculate(double input){
        //formula stuff
        double highOutput= highWeight *(lastHighOutput+input-lastInput);
        double lowOutput= lowWeight *highOutput+lastLowOutput*(1.- lowWeight);

        lastInput=input;
        lastHighOutput=highOutput;
        lastLowOutput=lowOutput;
        return  lowOutput;
    }
    public double getOutput(){
        return lastLowOutput;
    }

}
