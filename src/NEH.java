//
//
//import java.util.*;
//
//public class NEH {
//    /**
//     * Performs NEH.
//     * NOTE: NOT WORKING YET. PUTTING IT UP FOR EVERYONE's REFERENCE.
//     *
//     */
//    private List<Data> processingTimes;
//    private List<Data> schedule;
//
//    NEH(List<Data> processingTimes){
//        this.processingTimes = processingTimes;
//        this.schedule = new ArrayList<>();
//    }
//
//    void getFeasibleSolution(){
//        Collections.sort(this.processingTimes, new DataComparator());
//        Data p1 = this.processingTimes.get(0);
//        Data p2 = this.processingTimes.get(1);
//        assignRelativeOrder(p1, p2);
//
//        // go through each data point and add to schedule
//        for(int i=2; i<this.processingTimes.size(); i++){
//            assignPosition(this.processingTimes.get(i));
//        }
//
//        System.out.println("Schedule:");
//        for(Data d: this.schedule){
//            System.out.println(d.getJobID());
//        }
//
//    }
//
//    void assignRelativeOrder(Data p1, Data p2){
//        /**
//         * Find correct position relative to Data point 1 and 2 to minimize makespan.
//         */
//        // if sequence p1-p2
//        int p1Sum = 0;
//        int p2Sum = 0;
//        for(int i=0; i<p1.getProcessingTimes().size(); i++){
//            p1Sum += p1.getProcessingTimes().get(i);
//            p2Sum = Math.max(p1Sum, p2Sum) + p2.getProcessingTimes().get(i);
//            System.out.println("p1sum: " + p1Sum + " | p2sum: " + p2Sum);
//        }
//        int makeSpan1 = p2Sum;
//        System.out.println("Makespan for sequence p1-p2 is: " + p2Sum);
//
//        // if sequence p2-p1
//        p1Sum = 0;
//        p2Sum = 0;
//        for(int i=0; i<p1.getProcessingTimes().size(); i++){
//            p1Sum += p2.getProcessingTimes().get(i);
//            p2Sum = Math.max(p1Sum, p2Sum) + p1.getProcessingTimes().get(i);
//            System.out.println("p1sum: " + p1Sum + " | p2sum: " + p2Sum);
//        }
//        System.out.println("Makespan for sequence p1-p2 is: " + p2Sum);
//
//        if(makeSpan1<=p2Sum){
//            this.schedule.add(p1);
//            this.schedule.add(p2);
//        } else {
//            this.schedule.add(p2);
//            this.schedule.add(p1);
//        }
//    }
//
//    void assignPosition(Data p){
//        /**
//         * Assign the data point at right point in the existing schedule without
//         * changing the order of exisiting and already allocated jobs.
//         */
//        int positionIndex = 0;
//        int correctIndex = 0;
//        int minMakeSpan = Integer.MAX_VALUE;
//        int currentMakeSpan = 0;
//
//        while(positionIndex<this.schedule.size()+1){
//            int[] makeSpans = new int[this.schedule.size()];
//            for(int i=0; i<p.getProcessingTimes().size();i++){
//                boolean checkForPI = true;
//                for(int j=0; j<this.schedule.size(); j++){
//                    //get processing time
//                    int processingTime;
//                    if(j==positionIndex && checkForPI){
//                        processingTime = p.getProcessingTimes().get(i);
//                    } else {
//                        processingTime = this.schedule.get(j).getProcessingTimes().get(i);
//                    }
//
//                    //add for first element
//                    if(j==0 && (j != positionIndex || checkForPI)){
//                        makeSpans[j] += processingTime;
//                    } else {
//                        int max = 0;
//                        for(int k=0; k<=j;k++){
//                            if(makeSpans[k]>max)
//                                max = makeSpans[k];
//                        }
//                        if(j+1>=makeSpans.length && (j != positionIndex || checkForPI)){
//                            currentMakeSpan = max + processingTime;
//                        } else{
//                            if(!(j != positionIndex || checkForPI))
//                                makeSpans[j+1] = max + processingTime;
//                            else
//                                makeSpans[j] = max + processingTime;
//                        }
//                    }
//
//                    if(j==positionIndex && checkForPI){
//                        j--;
//                        checkForPI = false;
//                    }
//                }
//                System.out.println("Result");
//                for(int f=0;f<makeSpans.length;f++){
//                    System.out.println(makeSpans[f]);
//                }
//                System.out.println(currentMakeSpan + "\n");
//            }
//            if(currentMakeSpan<minMakeSpan){
//                minMakeSpan = currentMakeSpan;
//                correctIndex = positionIndex;
//            }
//            positionIndex++;
//        }
//
//        this.schedule.add(correctIndex, p);
//    }
//}

import java.util.*;
/**
 * NEH Class performs NEH heuristic
 * @author somayeghahari
 *
 */
public class NEH {

    /**
     * list of jobs
     */
    private List<Job> jobs;
    /**
     * list of scheduled jobs
     */
    private List<Job> schedule;
    /**
     * makespan of the scheduled jobs
     */
    private int makeSpan;

    /**
     * class constructor
     * @param jobs
     */
    NEH(List<Job> jobs){
        this.jobs = new ArrayList<>(jobs);
        this.schedule = new ArrayList<>();
        this.makeSpan = 0;
    }

    /**
     * function gets initial solution
     * 	it sorts the jobs in descending order
     * 	it sequences all the jobs according to NEH heuristic
     */
    public void getInitialSolution(){
        Collections.sort(jobs, new JobComparator());

        // step 2 of NEH heuristic
        Job p1 = jobs.get(0);
        Job p2 = jobs.get(1);
        assignTwoJobsOrder(p1, p2);

        // step3 of NEH heuristic
        for (int j = 2; j < jobs.size(); j++) {
            int temp = Integer.MAX_VALUE;
            List<Job> minMakespanSchedule =new ArrayList<Job>(); ;


            // each new job iterates through all positions
            for (int i = 0; i <= schedule.size(); i++) {
                List<Job> tempSchedule = new ArrayList<Job>();

                for (int k = 0; k < schedule.size(); k++) {
                    if(k!=i) {
                        tempSchedule.add(schedule.get(k));
                    }
                    else {
                        tempSchedule.add(jobs.get(j));
                        tempSchedule.add(schedule.get(k));
                    }
                }
                if(tempSchedule.size()==schedule.size())
                    tempSchedule.add(jobs.get(j));

//            	System.out.println("TTTTTTTTemp Schedule:");
//                for(Job d: tempSchedule){
//                    System.out.print(d.getJobID()+",");
//                }
//                System.out.println();
                // the makespan of sequence is calculated
                if(calculateMakespan(tempSchedule) < temp ) {
                    temp = calculateMakespan(tempSchedule);
                    minMakespanSchedule = tempSchedule;
                }
            }
            // the sequence with lowest makespan is set as scheduled
            if(!minMakespanSchedule.isEmpty()) {
                this.schedule = minMakespanSchedule;
                this.makeSpan = temp;
//            	System.out.println("flow-shop Schedule:");
//                for(Job d: this.schedule){
//                    System.out.print(d.getJobID()+",");
//                }
//                System.out.println();
//            	System.out.println("Flow-shop Makespan: " + makespan);
            }
        }
        System.out.print("   Flow-shop Schedule: ");
        for(Job d: this.schedule){
            System.out.print(d.getJobID()+", ");
        }
        System.out.println();
        System.out.println("   Flow-shop Makespan: " + this.makeSpan);

    }

    /**
     * function to assign two jobs to schedule
     * @param p1 first job
     * @param p2 second job
     */
    private void assignTwoJobsOrder(Job p1, Job p2){
        /**
         * Find correct position relative to job 1 and 2 to minimize makespan.
         */
        // if sequence p1-p2
        int p1Sum = 0;
        int p2Sum = 0;
        for(int i=0; i<p1.getProcessingTimes().size(); i++){
            p1Sum += p1.getProcessingTimes().get(i);
            p2Sum = Math.max(p1Sum, p2Sum) + p2.getProcessingTimes().get(i);
        }
        int makeSpan1 = p2Sum;

        // if sequence p2-p1
        p1Sum = 0;
        p2Sum = 0;
        for(int i=0; i<p1.getProcessingTimes().size(); i++){
            p1Sum += p2.getProcessingTimes().get(i);
            p2Sum = Math.max(p1Sum, p2Sum) + p1.getProcessingTimes().get(i);
        }

        if(makeSpan1<=p2Sum){
            this.schedule.add(p1);
            this.schedule.add(p2);
            this.makeSpan = makeSpan1;

        } else {
            this.schedule.add(p2);
            this.schedule.add(p1);
            this.makeSpan = p2Sum;
        }
    }

    /**
     * function calculates makespan of list of jobs in sequence
     * @param jobs list of jobs
     * @return makespan of list of jobs
     */
    public int calculateMakespan(List<Job> jobs) {
        int totalMakespan = 0;

        ArrayList<int[]> sumLst = new ArrayList<int[]>();

        sumLst.add(new int[jobs.get(0).getSizePT()]);
        sumLst.get(0)[0] = jobs.get(0).getProcessingTimes().get(0);
        for (int i = 1; i < jobs.get(0).getSizePT(); i++) {
            sumLst.get(0)[i] = sumLst.get(0)[i - 1] + jobs.get(0).getProcessingTimes().get(i);
        }


        for (int i = 1; i < jobs.size(); i++) {
            sumLst.add(new int[jobs.get(i).getSizePT()]);

        }
        for (int i = 1; i < sumLst.size(); i++) {
            sumLst.get(i)[0] = sumLst.get(i - 1)[0] + jobs.get(i).getProcessingTimes().get(0);
        }

        // go through all jobs
        for (int i = 1; i < jobs.size(); i++) {
            for (int j = 1; j < sumLst.get(i).length; j++) {
                // find start point of next job
                if (sumLst.get(i - 1)[j] > sumLst.get(i)[j - 1]) {
                    sumLst.get(i)[j] = sumLst.get(i - 1)[j] + jobs.get(i).getProcessingTimes().get(j);

                }
                else {
                    sumLst.get(i)[j] = sumLst.get(i)[j - 1] + jobs.get(i).getProcessingTimes().get(j);

                }
            }
        }

        totalMakespan = sumLst.get(sumLst.size() - 1)[sumLst.get(sumLst.size() - 1).length - 1];

        return totalMakespan;
    }

    public List<Job> getSchedule() {
        return schedule;
    }

    public int getMakeSpan() {
        return makeSpan;
    }
}