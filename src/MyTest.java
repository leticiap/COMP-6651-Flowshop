

import java.util.*;

public class MyTest {
    public static void main(String[] args) {

        // Create list of Data points
        List<Job> schedulingData = new ArrayList<>();

        // example 1
//        schedulingData.add(new Data(1, new ArrayList<>(Arrays.asList(15, 20))));
//        schedulingData.add(new Data(2, new ArrayList<>(Arrays.asList(5, 25))));
//        schedulingData.add(new Data(3, new ArrayList<>(Arrays.asList(2, 3))));

        // example 2
        schedulingData.add(new Job(1, new ArrayList<>(Arrays.asList(5, 9, 8, 10, 1))));
        schedulingData.add(new Job(2, new ArrayList<>(Arrays.asList(9, 3, 10, 1, 8))));
        schedulingData.add(new Job(3, new ArrayList<>(Arrays.asList(9, 4, 5, 8, 6))));
        schedulingData.add(new Job(4, new ArrayList<>(Arrays.asList(4, 8, 8, 7, 2))));

        for(Job d: schedulingData){
            System.out.println("Job id: " + d.getJobID());
            System.out.println("Processing times: " + d.getProcessingTimes());
            System.out.println("Total time: " + d.getTotalTime());
        }

        // Get initial results
        System.out.println("\nInitial result by NEH:");
        NEH neh = new NEH(schedulingData);
        neh.getInitialSolution();

        // Perform iterated local search
        IteratedLocalSearch iteratedLocalSearch = new IteratedLocalSearch(neh.getSchedule(), neh.getMakeSpan());
        List<Job> finalSchedule = iteratedLocalSearch.performILS();

        // After ILS schedule
        System.out.println("\nResult after ILS: ");
        for(Job j: finalSchedule){
            System.out.print(j.getJobID() + " ");
        }
        int makeSpanPostILS = neh.calculateMakespan(finalSchedule);
        System.out.println("Makespan: " + makeSpanPostILS);
    }
}