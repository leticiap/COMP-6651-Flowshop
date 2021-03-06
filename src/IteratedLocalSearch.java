import java.util.ArrayList;
import java.util.List;


/**
 * Class for the Iterated Local Search
 * 
 * Contains all necessary methods to execute ILS given
 * a specific schedule of jobs
 * 
 * @see Job
 */
public class IteratedLocalSearch {
    private List<Job> initialSchedule;
    private int initialMakeSpan;

    IteratedLocalSearch(List<Job> initialSchedule, int initialMakeSpan){
        this.initialSchedule = new ArrayList<>(initialSchedule);
        this.initialMakeSpan = initialMakeSpan;
    }

    /**
     * Executes ILS and Perturbation on the given schedule
     * @return the final version of the schedule organized by the ILS heuristic
     */
    public List<Job> performILS(){
        List<Job> pie = new ArrayList<>(this.initialSchedule);
        List<Job> pieBest = new ArrayList<>(pie);

        // determine stopping criteria. Currently, it's counter to 100.
        int i = 1;
        while(i<=100){
            List<Job> pie1 = new ArrayList<>(perturbation(pie));
            List<Job> pie2 = new ArrayList<>(localSearch(pie1));
            int makeSpanPie2 = calculateMakespan(pie2);
            int makeSpanPie = calculateMakespan(pie);
            if(makeSpanPie2<makeSpanPie){
                pie = new ArrayList<>(pie2);
                if(makeSpanPie<calculateMakespan(pieBest)){
                    pieBest = new ArrayList<>(pie);
                }
            }
            i++;
        }
        return pieBest;
    }

    /**
     * Executes perturbation on a given list of jobs
     * @param schedule - list of jobs
     * @return a new reorganized list of the same jobs
     */
    public List<Job> perturbation(List<Job> schedule){
        int pt1 = (int)(Math.random()*(schedule.size()));
        int pt2 = (int)(Math.random()*(schedule.size()));
        while(pt1==pt2){
            int ward = getRandomBinary();
            if(ward==1){
                Job temp = schedule.get(pt2);
                do{
                    if(pt2-1>=0){
                        schedule.set(pt2, schedule.get(pt2-1));
                        pt2 -= 1;
                    }
                }while(pt2>pt1);
                schedule.set(pt2, temp);
            } else {
                Job temp = schedule.get(pt1);
                do{
                    if(pt1+1<schedule.size()){
                        schedule.set(pt1, schedule.get(pt1+1));
                        pt1 += 1;
                    }
                }while(pt1<pt2);
                schedule.set(pt1, temp);
            }
        }
        return schedule;
    }
    
    /**
     * Executes the iterated local search on a given list of jobs
     * @param pie1 - list of jobs
     * @return a new reorganized list of the same jobs
     */
    public List<Job> localSearch(List<Job> pie1){
        List<Job> initialPermutation = new ArrayList<>(pie1);
        List<Job> startPermutation;
        do{
            startPermutation = new ArrayList<>(initialPermutation);
            List<Job> permutation = new ArrayList<>(initialPermutation);
            for(int i=0;i<permutation.size();i++){
                for(int j=i+1; j<permutation.size(); j++){
                    swap(i,j, permutation);
                    if(calculateMakespan(permutation)<calculateMakespan(initialPermutation)){
                        initialPermutation = new ArrayList<>(permutation);
                    }
                }
            }
        } while(calculateMakespan(initialPermutation)<calculateMakespan(startPermutation));
        return startPermutation;
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

    /**
     * Swaps jobs in two positions i and j inside the permutation list
     * @param i - first index
     * @param j - second index
     * @param permutation - list of jobs
     */
    private void swap(int i, int j, List<Job> permutation){
        Job temp = permutation.get(i);
        permutation.set(i, permutation.get(j));
        permutation.set(j, temp);
    }

    /**
     * Return a random binary 0 or 1
     * @return random integer
     */
    private int getRandomBinary(){
        if(Math.random()<=0.5)
            return 0;
        else
            return 1;
    }
}
