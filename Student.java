package asstest7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;



public class Student {
    private String name;
    private int maxCreditHours;
    private List<Session> sessions;
    private String status;
    Set<String> addedCourseCodes = new HashSet<>(); 

    public Student(String name, int maxCreditHours, String status) {
        this.name = name;
        this.maxCreditHours = maxCreditHours;
        this.sessions = new ArrayList<>();
        this.status = status;
    }
    public Student(String name) {
        this.name = name;
        this.maxCreditHours = 0;
    }

    public String getName() {
        return name;
    }

    public int getMaxCreditHours() {
        return maxCreditHours;
    }

    public List<Session> getCourses() {
        return sessions;
    }

    public String getStatus() { // getStatus method
        return status;
    }

    public boolean canAddCourse(Session session) {
        int currentCreditHours = calculateCurrentCreditHours();
        return (currentCreditHours + session.getCreditHours()) <= maxCreditHours;
    }

    public int calculateCurrentCreditHours() {
        int currentCreditHours = 0;
        for (Session session : sessions) {
            currentCreditHours += session.getCreditHours();
        }
        return currentCreditHours;
    }
    
    // Display the student's timetable.
    public void showTimetable() {
        if (sessions.isEmpty()) {
            System.out.println("Your timetable is empty.");
        } else {
            System.out.println("\nYour timetable： ");
            for (Session session : sessions) {
                System.out.println(session.toString());
                int selectedLectureIndex = session.getSelectedLecture();
                int selectedPracticalIndex = session.getSelectedPractical();
                if (selectedLectureIndex >= 0 && selectedPracticalIndex >= 0) {
                    System.out.println("  Lecture     ： " + session.getCourseLectureSession(selectedLectureIndex));
                    System.out.println("  Practical / Tutorial: " + session.getCoursePracticalSession(selectedPracticalIndex));
                } else {
                    System.out.println("  You haven't registered any lecture or practical yet.");
                }
            }
         	if (isUnderProbation()) {
         		maxCreditHours = 12;
            } else {
            	maxCreditHours =  maxCreditHours;
            }

            System.out.println("\nCurrent credit hours: " 
                + calculateCurrentCreditHours() + "/" + maxCreditHours);
        }
    }
    	
		private boolean isUnderProbation() {
		String probationStatus = checkProbation(); 
		return probationStatus != null && probationStatus.trim().equals("Under Probation");
		}
    	
    public void showcoursetimetable() {
        // Request input for the course code
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the course code: ");
        String courseCode = scanner.nextLine();

        boolean found = false;

        if (sessions.isEmpty()) {
            System.out.println("Your timetable is empty.");
        } else {
            System.out.println("\nYour timetable： ");
            for (Session session : sessions) {
                if (session.getCode().equals(courseCode)) {
                    System.out.println(session.toString());
                    int selectedLectureIndex = session.getSelectedLecture();
                    int selectedPracticalIndex = session.getSelectedPractical();
                    if (selectedLectureIndex >= 0 && selectedPracticalIndex >= 0) {
                        System.out.println("  Lecture 		  ： " + session.getCourseLectureSession(selectedLectureIndex));
                        System.out.println("  Practical / Tutorial : " + session.getCoursePracticalSession(selectedPracticalIndex));
                    } else {
                        System.out.println("  You haven't registered any lecture or practical yet.");
                    }
                    found = true;
                    break; // Exit the loop once the course is found
                }
            }
            
            if (!found) {
                System.out.println("You haven't registered any lecture or practical of this course yet.");
            }
        }
    }

    
    // Save the student's timetable to a txt file.
    public void saveTimetableToFile() {
        try {
            FileWriter writer = new FileWriter("students.txt", true); 
            writer.write(name + " " + maxCreditHours + "\n"); 
            for (Session session : sessions) {
                writer.write(name + " " + session.getCode() + " " + session.getSelectedLecture() + " " + session.getSelectedPractical() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed to save into students.txt.");
        }
    }
    
    // Add a course to the student's timetable
    public void addCourse(Session session) {
        if (canAddCourse(session)) {
        	if (!addedCourseCodes.contains(session.getCode())) {
            sessions.add(session);
        	addedCourseCodes.add(session.getCode());
            System.out.println("Successfully added this course： " + session.toString());}
            else {
            	System.out.println("You have already added course " + session.getCode() + " to your timetable.");
            }
        } else {
            System.out.println("Failed to add: " + session.toString() + ". Exceeds maximum credit hours.");
        }
    }
    
    // Remove a course from the student's timetable.
    public void removeCourse(String courseCode) {
        for (Session session : sessions) {
            if (session.getCode().equals(courseCode)) {
            	sessions.remove(session);
                System.out.println("Successfully dropped this course: " + session.toString());
                return;
            }
        }
        System.out.println("Failed to found： " + courseCode);
    }

    //i think this part got some problem
    // Get the student's timetable
    public String getTimetableAsString() {
    	UserAuthentification userAuthentification = new UserAuthentification(getName());
        StringBuilder timetableStr = new StringBuilder();
        timetableStr.append("Name: ").append(UserAuthentification.getUsername()).append("\n");
        for (Session session : sessions) {
            timetableStr.append("Course code: ").append(session.getCode()).append("\n");
            timetableStr.append("Lecture   		 : ").append(session.getCourseLectureSession(session.getSelectedLecture())).append("\n");
            timetableStr.append("Practical / Tutorial : ").append(session.getCoursePracticalSession(session.getSelectedPractical())).append("\n");
            timetableStr.append("--------------\n");
        }
        return timetableStr.toString();
    }

    // Check the student's probation status by reading from a file and return it.
    public String checkProbation() {
    	String filePath = "students.txt"; 
        

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into different values based on a delimiter (e.g., comma)
                String[] parts = line.split(",");

                // the format is string
                if (parts[0].equals(UserAuthentification.getUsername())) 
                	 status = parts[3];
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		
       return status;
        
        
    }
    
   public void PrerequisiteChecking()
   {
	   String fileName = "passed subjects.txt";
	   try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
           String line;
           while ((line = br.readLine()) != null) {
               // Split each line into name and subjects
               String[] parts = line.split(":");
               if (parts[0].equals(UserAuthentification.getUsername())) {
                   String passedSubjects=parts[1];
                   String PassSub1= "Programming And Programming Solving";
                   String PassSub2= "Object-Oriented Application Programming";
                   if (passedSubjects.contains(PassSub1)) 
                   {
                	    System.out.println("You can register Object-Oriented Application Programming");
                	    
                	    if (passedSubjects.contains(PassSub2))
                	    	System.out.println("You can register Software Design and Software Testing");
                	    else
                	    	System.out.println("You cannot register Software Design and Software Testing");
                	    	
                   } 
                   
                   else 
                   {
                	    System.out.println("You cannot register Object-Oriented Application Programming, Software Design and Software Testing");
                   }
                   
               }
           }
       } catch (IOException e) {
           e.printStackTrace();
       }

       return;
   }
}
