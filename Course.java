package asstest7;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Course{
	// This method reads and prints the contents of a file named "CourseList.txt".
	public void execute() {
		try {
	        File file = new File("CourseList.txt");
	        FileReader fileReader = new FileReader(file);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);

	        String line;
	        
	        System.out.println();
	     // Print each line of the course listings).
	        while ((line = bufferedReader.readLine()) != null) 
	        {
	        	
	        	System.out.println(line);
	        }
	        bufferedReader.close();
	        }catch (IOException e) 
	        {
	        	// Handle and print any IOException that might occur during file reading.
	        	e.printStackTrace();
	        }
		System.out.println("Attention: ");
		System.out.println("The course list will be updated each semester");
		System.out.println("Registration of course are based on first come first served");
		System.out.println("Course that are not available, please wait for next semester");
		System.out.println();
		
	} 
}
