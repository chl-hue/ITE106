import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of students: ");
        int numStudents = input.nextInt();

        for (int i = 1; i <= numStudents; i++) {
            input.nextLine(); 
            System.out.print("Enter the name of student " + i + ": ");
            String studentName = input.nextLine();

            System.out.print("Enter the number of assignments: ");
            int numAssignments = input.nextInt();
            double totalScore = 0;

            for (int j = 1; j <= numAssignments; j++) {
                System.out.print("Enter score for assignment " + j + ": ");
                totalScore += input.nextDouble();
            }

            double averageScore = totalScore / numAssignments;
            char grade = (averageScore >= 90) ? 'A' : (averageScore >= 80) ? 'B' :
                         (averageScore >= 70) ? 'C' : (averageScore >= 60) ? 'D' : 'F';

            System.out.println("\nName: " + studentName);
            System.out.println("Average Score: " + averageScore);
            System.out.println("Letter Grade: " + grade);
        }

        input.close();
    }
}
