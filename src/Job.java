
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that defines a job
 * Stores three items as follows:
 *  1) Job id
 *  2) List of processing times on each machine
 *  3) Total time taken by the job on all machines
 */
public class Job {
    private int jobID;
    private List<Integer> processingTimes;
    private int totalTime;
    private int sizePT;

    Job(int jobID, ArrayList<Integer> processingTimes){
        this.jobID = jobID;
        this.processingTimes = processingTimes;
        this.totalTime = 0;
        this.sizePT = 0;
        for(Integer i: processingTimes){
            this.totalTime += i;
            this.sizePT++;
        }
    }

    public int getJobID() {
        return jobID;
    }

    public List<Integer> getProcessingTimes() {
        return processingTimes;
    }

    public int getSizePT() {
        return sizePT;
    }

    public int getTotalTime() {
        return totalTime;
    }
}

class JobComparator implements Comparator<Job>{
    /**
     * Comparator to compare total time taken on all machines.
     * Essential for first step of NEH.
     * @param o1 Job object 1
     * @param o2 Job object 2
     * @return Number to result in descending order of the two objects
     */

    @Override
    public int compare(Job o1, Job o2) {
        if(o1.getTotalTime()==o2.getTotalTime()){
          return 0;
        } else if(o1.getTotalTime()<o2.getTotalTime()){
            return 1;
        } else{
            return -1;
        }
    }
}
