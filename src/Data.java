
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Data {
    /**
     * Class to hold job details.
     * Stores three items as follows:
     *  1) Job id
     *  2) List of processing times on each machine
     *  3) Total time taken by the job on all machines
     */
    private int jobID;
    private List<Integer> processingTimes;
    private int totalTime;

    Data(int jobID, ArrayList<Integer> processingTimes){
        this.jobID = jobID;
        this.processingTimes = processingTimes;
        this.totalTime = 0;
        for(Integer i: processingTimes){
            totalTime += i;
        }
    }

    public int getJobID() {
        return jobID;
    }

    public List<Integer> getProcessingTimes() {
        return processingTimes;
    }

    public int getTotalTime() {
        return totalTime;
    }
}

class DataComparator implements Comparator<Data>{
    /**
     * Comparator to compare total time taken on all machines.
     * Essential for first step of NEH.
     * @param o1 Data object 1
     * @param o2 Data object 2
     * @return Number to result in descending order of the two objects
     */

    @Override
    public int compare(Data o1, Data o2) {
        if(o1.getTotalTime()==o2.getTotalTime()){
          return 0;
        } else if(o1.getTotalTime()<o2.getTotalTime()){
            return 1;
        } else{
            return -1;
        }
    }
}
