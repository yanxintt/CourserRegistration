package asstest7;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String code;
    private String name;
    private int creditHours;
    private List<String> lectureSessions;
    private List<String> practicalSessions;
    private int selectedLecture;
    private int selectedPractical;

    public Session(String code, String name, int creditHours) {
        this.code = code;
        this.name = name;
        this.creditHours = creditHours;
        this.lectureSessions = new ArrayList<>();
        this.practicalSessions = new ArrayList<>();
        this.selectedLecture = -1;
        this.selectedPractical = -1;
    }

    public String getCode() {
        return code;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void addLecture(String... sessions) {//Arraylist<String> sessions
        for (String session : sessions) {
        	// Add each provided lecture session to the list.
        	lectureSessions.add(session);
        }
    }

    public void addPractical(String... sessions) {
        for (String session : sessions) {
        	// Add each provided practical session to the list.
            practicalSessions.add(session);
        }
    }

    public void displayTheoryOptions() {
        for (int i = 0; i < lectureSessions.size(); i++) {
        	//display each lecture session
            System.out.println((i + 1) + ". " + lectureSessions.get(i));
        }
    }

    public void displayPracticalOptions() {
        for (int i = 0; i < practicalSessions.size(); i++) {
        	//display each practical session
            System.out.println((i + 1) + ". " + practicalSessions.get(i));
        }
    }
    
    //Method to select session
    public void selectLecture(int choice) {
        if (choice >= 1 && choice <= lectureSessions.size()) {
        	selectedLecture = choice - 1;
        } else {
            System.out.println("The choice is out of range.");
        }
    }

    public void selectPractical(int choice) {
        if (choice >= 1 && choice <= practicalSessions.size()) {
            selectedPractical = choice - 1;
        } else {
            System.out.println("The choice is out of range.");
        }
    }

    //getter
    public int getSelectedLecture() {
        return selectedLecture;
    }

    public int getSelectedPractical() {
        return selectedPractical;
    }

    //retrieve lecture session based on the provided index
    public String getCourseLectureSession(int index) {
        if (index >= 0 && index < lectureSessions.size()) {
            return lectureSessions.get(index);
        } else {
            return "Invalid lecture session index.";
        }
    }
    
    //retrieve practical session based on the provided index
    public String getCoursePracticalSession(int index) {
        if (index >= 0 && index < practicalSessions.size()) {
            return practicalSessions.get(index);
        } else {
            return "Invalid practical session index";
        }
    }

    @Override
    public String toString() {
        return name + " (" + creditHours + " credit hours.)";
    }
}
