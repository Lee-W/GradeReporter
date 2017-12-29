import org.apache.commons.math3.exception.ZeroException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Iterator;

public class NCKUStat implements GradeStat {
    private GPACalculator gpaCalculator;

    public NCKUStat(String semester) {
        if (!semester.equals("before104")) {
            gpaCalculator = new GPAFourPointZeroScaleCalculator();
        } else {
            gpaCalculator = new GPAFourPointThreeScaleCalculator();
        }

    }

    @Override
    public JSONObject report(JSONObject rawData) {
        JSONObject summary = (JSONObject) rawData.get("summary");
        JSONObject semesterGrades = (JSONObject) rawData.get("courses");

        ArrayList<Double> grades = new ArrayList<Double>();
        ArrayList<Double> credits = new ArrayList<Double>();

        Iterator<String> keys = semesterGrades.keys();
        Double grade, credit;
        while(keys.hasNext()) {
            String key = keys.next();
            JSONArray gradeArray = (JSONArray) semesterGrades.get(key);
            for (Object jsonObject: gradeArray) {
                try {
                    grade = Double.parseDouble((String)((JSONObject) (jsonObject)).get("分數"));
                    credit = Double.parseDouble((String)((JSONObject) (jsonObject)).get("學分"));

                    grades.add(grade);
                    credits.add(credit);
                } catch (Exception e) {

                }
            }
        }
        double gpa = gpaCalculator.calculateGPA(grades, credits);
        summary.put("GPA", gpa);
        return summary;
    }
}
