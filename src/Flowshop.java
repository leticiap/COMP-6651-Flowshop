import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Flowshop {
	  public static void main (String[] args)
	  {
		  String path = "input/2/Ta001_2.txt";
		  ArrayList<Integer> jobs = readFile(path);	  
		
		  if (jobs.size() != 0)
		  {
			  // Call the methods to process the jobs
			  
			  // this is a test to see if it is working, we can erase that
			  for (int i = 0; i < jobs.size(); i++)
				  System.out.println(jobs.get(i));
		  }
	  }
	  
	  /**
	   * Method to parse the input file given by the path parameter
	   * @param path - the path of the file to be opened
	   * @return the list with the cost for each job j
	   */
	  public static ArrayList<Integer> readFile(String path)
	  {
		  ArrayList<Integer> jobsDuration = new ArrayList<Integer>();
		  try {
				BufferedReader reader = new BufferedReader(new FileReader(path));
				String line;
				String[] jobParameters;
				while ((line = reader.readLine()) != null)
				{
					jobParameters = line.split("	");
					int duration = 0;
					if (jobParameters.length > 2)
					{
						for (int i = 2; i < jobParameters.length; i+=2)
							duration += Integer.parseInt(jobParameters[i]);
						jobsDuration.add(duration);
					}					
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		  return jobsDuration;
	  }
}
