package asstest7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistrationTest {
	
	//list to store all student objects.
	 private static List<Student> allStudents = new ArrayList<>();
	 public static void main(String[] args) {

	        Scanner scanner = new Scanner(System.in);
	        
	        String username ="a";
	        int maxCreditHours = 0;
	        String status = null;
	        
            try {
            	// Open a file reader to read the "students.txt" file
                BufferedReader reader = new BufferedReader(new FileReader("students.txt"));
                String line;
                
               // Read each line in the file.
                while ((line = reader.readLine()) != null) {
                	//Split into parts using a (,) as the delimiter.
                    String[] parts = line.split(",");
                  //Check if the line contains at least 2 parts
					if (parts.length >= 2) {
						username = parts[0];
                        maxCreditHours = Integer.parseInt(parts[1]);
                        status = parts[3];
                        break;
                    }
                }
                
                // Close the file reader.
                reader.close();
            } catch (IOException e) {
            	// Handle any potential IOException
                System.out.println("Failed to read students.txt.");
                return;  // Exit the program if have issue reading the file.
            }
            if (maxCreditHours == 0) {
                System.out.println("No student information was found matching your name. "
                		+ "Please enter correct name.");
                return;
            }
	        Student student = new Student(username, maxCreditHours,status);
	        //add extracted information to the list of all students.
			allStudents.add(student);
			
			//Create a list to store available course objects.
   		    List<Session> availableCourses = new ArrayList<>();
   		    
   		    try (BufferedReader reader1 = new BufferedReader(new FileReader("courses.txt"))) {
   		         String line;
   		         while ((line = reader1.readLine()) != null) {
   		             String[] parts = line.split(",");
   		             if (parts.length >= 3) {
   		                 //Extract course info from the parts.
   		                 String courseCode = parts[0];
   		                 String courseName = parts[1];
   		                 int creditHours = Integer.parseInt(parts[2]);
   		
  		                 //Create a new Course object with the extracted information.
   		                 Session session1 = new Session(courseCode, courseName, creditHours);
   		
   		                 //Read the next line and check if it starts with "Lecture"
   		                 line = reader1.readLine();
   		                 if (line != null && line.startsWith("Lecture")) {
   		                     String[] lectureSessions = line.split(" ", 2)[1].split(", ");
   		                     for (String session : lectureSessions) {
   		                         //Extract and add lecture sessions to the course object.
   		                         session1.addLecture(session);
   		                     }
   		                 }
   		
   		                 //Read the next line and check if it starts with "Practical"
   		                 line = reader1.readLine();
   		                 if (line != null && line.startsWith("Practical")) {
   		                     String[] practicalSessions = line.split(" ", 2)[1].split(", ");
   		                     for (String session : practicalSessions) {
   		                         session1.addPractical(session);
   		                     }
   		                 }
   		
   		                 //Add the created course object to the list of available courses
   		                 availableCourses.add(session1);
   		             }
   		         }
   		     } catch (IOException e) {
   		         System.out.println("Failed to read courses.txt");
   		     }

	        UserAuthentification userAuthentication = new UserAuthentification("students.txt");

	        boolean loginSuccessful = false;

	        while (!loginSuccessful) {
	        	loginSuccessful = userAuthentication.login();
	        	
	        	if (!loginSuccessful) {
	        		System.out.println("");
	        	}

	        }

		   	while (true) {
		   		CourseRegistrationSystem courseRegistrationSystem = new CourseRegistrationSystem(student);
		        courseRegistrationSystem.run(availableCourses, allStudents);
		   		  
		        scanner.close();	   
	   		     }
		   	
	        }
	 
	 		
}
