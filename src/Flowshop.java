import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Flowshop {
	  public static void main (String[] args) throws IOException
	  {
		  int fileNum = 120;
		  System.out.println("************************ Start of program ************************\n\n");
		  System.out.println("Running algorithm on jobs read from " + fileNum/3 + " files\n\n");
		  long totalStart = System.currentTimeMillis();
		  BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"));

		  for (int i = 0; i <= fileNum; i=i+3)
		  {
			  String path = "input/2/Ta"+ String.format("%03d", i) +"_2.txt";
			  ArrayList<Job> jobs = readFile(path);	  
			
			  if (jobs.size() != 0)
			  {
				  long s = System.currentTimeMillis();

				  NEH neh = new NEH(jobs);
			      neh.getInitialSolution();

			      // Perform iterated local search
			      IteratedLocalSearch iteratedLocalSearch = new IteratedLocalSearch(neh.getSchedule(), neh.getMakeSpan());
			      List<Job> solution = iteratedLocalSearch.performILS();
				  long duration = System.currentTimeMillis()-s;

				  String out = "\n\nExecuting test " + String.format("%03d", i) + ":\n"+
						  "   Flow-shop Schedule: ";
//				  System.out.println("Executing test " + String.format("%03d", i) + " of 120:");
//				  System.out.print("   Flow-shop Schedule: ");
				  for (int j = 0; j < solution.size(); j++) {
					  out = out + solution.get(j).getJobID() + " ";
//					  System.out.print(solution.get(j).getJobID() + " ");
					  }
				  s = System.currentTimeMillis();
				  out = out + "\n   Flow-shop Makespan: " + neh.calculateMakespan(solution);
				  duration = duration+  (System.currentTimeMillis()-s);
//				  out = out + "\n   Time Duration: "+ (int)(duration/1000);
				  out = out + "\n   Time Duration: "+ duration;
				  bw.write(out);
				  bw.flush();
				  
//				  System.out.println();
//				  System.out.println("   Flow-shop Makespan: " + neh.calculateMakespan(solution));
//				  System.out.println();
			  }  
		  }
		  long totalDuration =(System.currentTimeMillis()-totalStart);
//		  long totalDuration =(int) (System.currentTimeMillis()-totalStart)/1000;
		  System.out.println("Time of running algorithm:"+totalDuration+"\n\n");
		  bw.write("\n\nTime of running algorithm:"+totalDuration);

		  bw.close();
		  System.out.println("************************ End of program ************************");
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
