import java.util.ArrayList;
import java.util.List;

public class IteratedLocalSearch {
    private List<Job> initialSchedule;
    private int initialMakeSpan;

    IteratedLocalSearch(List<Job> initialSchedule, int initialMakeSpan){
        this.initialSchedule = initialSchedule;
        this.initialMakeSpan = initialMakeSpan;
    }

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

    public List<Job> perturbation(List<Job> schedule){
        return schedule;
    }

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

    private void swap(int i, int j, List<Job> permutation){
        Job temp = permutation.get(i);
        permutation.set(i, permutation.get(j));
        permutation.set(j, temp);

    }
}
