package asstest7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CourseRegistrationSystem {
    private Scanner scanner;
    private Student student;

    public CourseRegistrationSystem(Student student) {
        this.student = student;
        scanner = new Scanner(System.in);
    }

    // Display the main menu options.
    public void displayMenu() {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Welcome to Online Course Registration System");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("1. View course list");
        System.out.println("2. Manage course timetable");
        System.out.println("3. Check probation status");
        System.out.println("4. Check prerequisite");
        System.out.println("Any other number that is not included in these selections will be considered as invalid input");
        System.out.println("-------------------------------------------------------------------------");
    }

    // Get the user's choice from the menu.
    private String getUserChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextLine();
        
    }
    
    private static boolean containsAlphabet(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }
    
    // Main method to run the menu and perform actions based on user input.
    public void run(List<Session> availableCourses, List<Student> allStudents) {
    	String choice;
    	int number = 0;
    	do {
	        displayMenu();
	        choice = getUserChoice();
	        try {
	            // Attempt to parse the input as an integer
	            number = Integer.parseInt(choice);
	            
	        } catch (NumberFormatException e) {
	            // If parsing fails, it's not a valid number

	            // Check if the input contains alphabetic characters
	            if (containsAlphabet(choice)) {
	                System.out.println("You entered alphabetic characters.");
	            } 
	            else 
	            {
	                System.out.println("Invalid input. Please enter a valid integer. ");
	            }
	        }

	        
	    
	        switch (number) {
	            case 1:
	                Course course = new Course();
	                course.execute();
	                break;
	                
	            case 2:
	                // Sub-menu for managing the course timetable.
	                System.out.println("\nMenu");
	                System.out.println("1. Register a course");
	                System.out.println("2. Withdraw a course");
	                System.out.println("3. View my registered courses");
	                System.out.println("4. View registered course timetable");
	                System.out.println("5. Save timetable & Exit");
	                
	                int subchoice;
		   		       while (true) {
		   		           try {
		   		                 System.out.print("\nEnter your choice:");
		   		                 subchoice = scanner.nextInt();
		   		                 scanner.nextLine(); // Consume the newline character.
		   		                 // Exit the loop if a valid integer choice is entered.
		   		                 break;
		   		             } catch (InputMismatchException e) {
		   		                 // Handle input that is not an integer.
		   		                 System.out.println("Invalid choice. Please enter a valid integer for your choice.");
		   		                 scanner.nextLine();
		   		             }
	   		         }
	   		
	   		         switch (subchoice) {
	   		         	//add course
	   		             case 1:
	   		                 System.out.print("Enter course code to add: ");
	   		                 String courseCodeToAdd = scanner.nextLine();
	   		                 Session selectedCourse = null;
	   		                 //Search for the selected course based on the course code.
	   		                 for (Session session1 : availableCourses) {
	   		                     if (session1.getCode().equals(courseCodeToAdd)) {
	   		                         selectedCourse = session1;
	   		                         break;
	   		                     }
	   		                 }
	   		                 if (selectedCourse != null) {
	   		                     System.out.println("Choose one of the lecture session: ");
	   		                     selectedCourse.displayTheoryOptions();
	   		                     int lectureChoice = scanner.nextInt();
	   		                     scanner.nextLine();
	   		                     selectedCourse.selectLecture(lectureChoice);
	   		
	   		                     System.out.println("Choose one of the practical session: ");
	   		                     selectedCourse.displayPracticalOptions();
	   		                     int practicalChoice = scanner.nextInt();
	   		                     scanner.nextLine();
	   		                     selectedCourse.selectPractical(practicalChoice);
	   		
	   		                     student.addCourse(selectedCourse);
	   		                 } else {
	   		                     //No matching course code
	   		                     System.out.println("Unable to find this course, please make sure you"
	   		                     		+ " inputted the correct course code.");
	   		                 }
	   		                 break;
		   		         //Delete selected course
	   		             case 2:
	   		                 System.out.print("Enter course code to delete: ");
	   		                 String courseCodeToRemove = scanner.nextLine();
	   		                 student.removeCourse(courseCodeToRemove);
	   		                 break;
		   		         //Display student's timetable
	   		             case 3:
	   		                 student.showTimetable();
	   		                 break;
	   		                 
	   		             //display specific registered course timetable
	   		             case 4:
	   		            	student.showcoursetimetable();
	   		            	break;
	   		            	
	   		             //Save timetable to "student_timtable.txt" file and exit	   
	   		             case 5:
	   		                 Runtime.getRuntime().addShutdownHook(new Thread(() -> {
	   		                     try {
	   		                         FileWriter writer = new FileWriter("student_timtable.txt", true);
	   		                         for (Student student1 : allStudents) {
	   		                             writer.write(student1.getTimetableAsString());
	   		                             writer.write("\n");
	   		                         }
	   		                         writer.close();
	   		                     } catch (IOException e) {
	   		                         System.out.println("Failed to save your timetable to file.");
	   		                     }
	   		                 }));
	   		                 //Exit and update timetable
	   		                 
	   		                 System.out.println("Your timetable is updated. "
	   		                 		+ "Thank you for using the Course Enrollment System!");
	   		                 System.exit(0);
	   		             default:
	   		                 System.out.println("Invalid input. Please try again!");
	   		         }
	                
	                break;
	                
	            case 3:
	                // Check probation status for the student.
	                System.out.println(student.checkProbation());
	                break;
	            case 4:
	            	// Check prerequisite for the student.
	            	student.PrerequisiteChecking();
	            	break;
	            	
	                
	            default:
	                // Invalid choice message.
	                System.out.println("Invalid choice. Please try again.");
	                break;
	        }
    	}while(number <= 4 && number > 0);
    }
}

