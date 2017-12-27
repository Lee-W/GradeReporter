public class GPAFourPointThreeScaleCalculator extends GPACalculator {
    public double translateGradeToGPA(double grade) {
        if (grade >= 90) {
            return 4.3;
        } else if (grade >= 85) {
            return 4.0;
        } else if (grade >= 80) {
            return 3.7;
        } else if (grade >= 77) {
            return 3.3;
        } else if (grade >= 73) {
            return 3.0;
        } else if (grade >= 70) {
            return 2.7;
        } else if (grade >= 67) {
            return 2.3;
        } else if (grade >= 63) {
            return 2.0;
        } else if (grade >= 60) {
            return 1.7;
        } else {
            return 0;
        }
    }
}
