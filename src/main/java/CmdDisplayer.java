import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Console;
import java.util.Iterator;
import java.util.Scanner;

public class CmdDisplayer {
    public static void displaySemesterData(JSONObject semesterData) {
        Iterator<String> semesters = semesterData.keys();
        while(semesters.hasNext()) {
            String semester = semesters.next();
            JSONArray courseGrades = (JSONArray) semesterData.get(semester);

            System.out.println(semester);

            // Print Title
            for (Object obj: ((JSONObject)courseGrades.get(0)).keySet()) {
                System.out.print(obj+"\t\t");
            }
            System.out.println();

            for (Object obj: courseGrades) {
                Iterator<String> keys = ((JSONObject) obj).keys();

                while(keys.hasNext()) {
                    String key = keys.next();
                    System.out.print(((JSONObject) obj).get(key)+"\t\t");
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    public static void displaySummaryData(JSONObject summaryData) {
        for (Object obj: summaryData.keySet()) {
            System.out.print(obj+"\t\t");
        }
        System.out.println();

        Iterator<String> summaryTitles = summaryData.keys();
        while (summaryTitles.hasNext()) {
            String key = summaryTitles.next();
            System.out.print(summaryData.get(key)+"\t\t");
        }

    }

    public static void main(String[] args) {
        String studentID, password;

        Console console = System.console();
        if (console != null) {
            studentID = console.readLine("Enter your student ID: ");
            password = new String(console.readPassword("Enter your password: "));
        } else {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your student ID: ");
            studentID = scanner.nextLine();

            System.out.print("Enter your password (Note that it will not be concealed): ");
            password = scanner.nextLine();
        }



        GradeReporter nckuGradeReporter = new GradeReporter(
                "ncku", studentID, password
        );
        JSONObject data = nckuGradeReporter.parseGradeData();
        JSONObject semesterData = (JSONObject) data.get("courses");
        JSONObject summary = (JSONObject) data.get("summary");

        displaySemesterData(semesterData);
        displaySummaryData(summary);


    }
}
