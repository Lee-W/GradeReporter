import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Scanner;

public class CmdDisplayer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String studentID, password;

        System.out.print("Enter you student ID: ");
        studentID = scanner.nextLine();

        System.out.print("Enter your secret password: ");
        password = scanner.nextLine();

        GradeReporter nckuGradeReporter = new GradeReporter(
                "ncku", studentID, password
        );
        JSONObject data = nckuGradeReporter.parseGradeData();
        JSONObject semesterData = (JSONObject) data.get("courses");
        JSONObject summary = (JSONObject) data.get("summary");

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

        for (Object obj: summary.keySet()) {
            System.out.print(obj+"\t\t");
        }
        System.out.println();

        Iterator<String> summaryTitles = summary.keys();
        while (summaryTitles.hasNext()) {
            String key = summaryTitles.next();
            System.out.print(summary.get(key)+"\t\t");
        }
    }
}
