import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Flowshop {
	  public static void main (String[] args)
	  {
		  for (int i = 0; i <= 120; i++)
		  {
			  String path = "input/2/Ta"+ String.format("%03d", i) +"_2.txt";
			  ArrayList<Job> jobs = readFile(path);	  
			
			  if (jobs.size() != 0)
			  {
				  NEH neh = new NEH(jobs);
			      neh.getInitialSolution();

			      // Perform iterated local search
			      IteratedLocalSearch iteratedLocalSearch = new IteratedLocalSearch(neh.getSchedule(), neh.getMakeSpan());
			      List<Job> solution = iteratedLocalSearch.performILS();
				  System.out.println("Executing test " + String.format("%03d", i) + " of 120:");
				  System.out.print("   Flow-shop Schedule: ");
				  for (int j = 0; j < solution.size(); j++)
					  System.out.print(solution.get(j).getJobID() + " ");
				  System.out.println();
				  System.out.println("   Flow-shop Makespan: " + neh.calculateMakespan(solution));
				  System.out.println();
			  }  
		  }
	  }
	  
	  /**
	   * Method to parse the input file given by the path parameter
	   * @param path - the path of the file to be opened
	   * @return the list of jobs, each job has a list of processing time
	   */
	  public static ArrayList<Job> readFile(String path)
	  {
		  ArrayList<Job> jobs = new ArrayList<Job>();
		  try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String line;
				String[] jobParameters;
				Integer id = 0;
				while ((line = reader.readLine()) != null)
				{
					jobParameters = line.split("	");
					ArrayList<Integer> processingTimes = new ArrayList<Integer>() ;
					if (jobParameters.length > 2)
					{
						for (int i = 2; i < jobParameters.length; i+=2)
							processingTimes.add( Integer.parseInt(jobParameters[i]));
						Job j = new Job(id , processingTimes);
						id ++;
						jobs.add(j);
					}					
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  return jobs;
	  }
}
