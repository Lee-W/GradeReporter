import java.util.ArrayList;

public abstract class GPACalculator {
    public double calculateGPA(ArrayList<Double> grades, ArrayList<Double> credits) {
        if (grades.size() != credits.size()) {
            return 0;
        }

        double gpa = 0;
        double creditSum = 0;

        double grade, credit;

        for (int i=0; i<grades.size(); i++) {
            grade = grades.get(i);
            credit = credits.get(i);

            gpa += translateGradeToGPA(grade)*credit;
            creditSum += credit;
        }
        if (creditSum != 0) {
            return gpa/creditSum;
        } else {
            return 0;
        }
    }

    protected abstract double translateGradeToGPA(double grade);
}