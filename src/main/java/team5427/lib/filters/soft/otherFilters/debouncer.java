package team5427.lib.filters.soft.otherFilters;

//filters noise out of sensory inputs
//a change will only be recognized when it has been stable for some time
public class debouncer {
    private double requiredTime;//amount of time needed for the input before changing
    private boolean prevValue;
    private double stableTime;
    private boolean currentState;//final state after filtering

    public debouncer(double requiredTime){
        this.requiredTime=requiredTime;
        this.prevValue=false;
        this.stableTime=0;
        this.currentState=false;
    }

    public void calculate(boolean input, double currentTime){
        //resets timer since input changed
        if(input!=prevValue){
            stableTime=currentTime;
            prevValue=input;
        }
        //checks if input has remained stable for a while
        if(currentTime-stableTime>=requiredTime){
            currentState=input;
        }
    }

    public boolean getCurrentState(){
        return currentState;
    }
}
