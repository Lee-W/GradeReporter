public class GPAFourPointZeroScaleCalculator extends GPACalculator {
    public double translateGradeToGPA(double grade) {
        if (grade >= 80) {
            return 4;
        } else if (grade >= 70) {
            return 3;
        } else if (grade >= 60) {
            return 2;
        } else if (grade >= 50) {
           return 1;
        } else {
            return 0;
        }
    }
}
