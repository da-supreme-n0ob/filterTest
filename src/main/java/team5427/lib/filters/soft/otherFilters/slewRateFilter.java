package team5427.lib.filters.soft.otherFilters;

//provides a cap to a changing value
//can be used to prevent jerky movement
public class slewRateFilter {
    private double maxRateOfChange;
    private double prevValue;
    private long prevTime; //measured in nanoseconds since precise time is important

    public slewRateFilter(double maxROC){
        this.maxRateOfChange=Math.abs(maxROC);//must be positive
        this.prevValue=0;
        this.prevTime=System.nanoTime();
    }

    public slewRateFilter(double maxROC, double startValue){
        this.maxRateOfChange=maxROC;
        this.prevValue=startValue;
        //measures elapsed time
        this.prevTime=System.nanoTime();
    }

    public double calculate(double value){
        long currentTime = System.nanoTime();
        double timeElapsed = (currentTime-prevTime)/1000./1000./1000.;//converted to seconds

        double maxChange = maxRateOfChange*timeElapsed; //maximum amount of change allowed within the given time frame
        double wantedChange = value-prevValue;  //change in value
        double finalValue;

        //sets the limit on how much the value can change
        if(wantedChange<-maxChange){
            finalValue =prevValue-maxChange;
        }else if(wantedChange>maxChange){
            finalValue =prevValue+maxChange;
        }else{
            finalValue =prevValue+wantedChange;
        }

        this.prevValue=finalValue;
        this.prevTime=currentTime;

        return finalValue;
    }
}
