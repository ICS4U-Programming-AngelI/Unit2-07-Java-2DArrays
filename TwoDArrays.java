import java.io.File; // For file handling
import java.io.FileNotFoundException; // For missing files
import java.io.PrintWriter; // For writing CSV
import java.io.IOException; // For writing errors
import java.util.ArrayList; // To store students/assignments
import java.util.List; // List interface
import java.util.Random; // For generating marks
import java.util.Scanner; // For reading files

/**
 * This program reads student and assignment names from files,
 * generates random marks for each assignment using Gaussian distribution,
 * and writes the marks to a CSV file.
 *
 * Author: Angel
 * Version: 1.0
 * Since: 2025-11-06
 */
public final class TwoDArrays { // Main program class

    // Private constructor to prevent object creation
    private TwoDArrays() {
        throw new IllegalStateException("Utility class"); // Prevent instantiation
    }

    /**
     * Generates random marks for students and assignments.
     * @param studentArr array of student names
     * @param assignmentArr array of assignment names
     * @return 2D array containing the header row and marks
     */
    public static String[][] generateMarks(final String[] studentArr, final String[] assignmentArr) {
        Random random = new Random(); // Random number generator
        String[][] table = new String[studentArr.length + 1][assignmentArr.length + 1]; // Create 2D table

        table[0][0] = "Students"; // Header
        for (int j = 0; j < assignmentArr.length; j++) {
            table[0][j + 1] = assignmentArr[j]; // Header row assignments
        }

        for (int rowNum = 1; rowNum <= studentArr.length; rowNum++) { // Loop students
            String[] row = new String[assignmentArr.length + 1]; // Row array
            row[0] = studentArr[rowNum - 1]; // Student name

            for (int colNum = 1; colNum <= assignmentArr.length; colNum++) { // Loop assignments
                int randNum = (int) Math.round(random.nextGaussian() * 10 + 75); // Gaussian random mark
                if (randNum < 0) randNum = 0; // Min 0
                else if (randNum > 100) randNum = 100; // Max 100
                row[colNum] = String.valueOf(randNum); // Store as string
            }

            table[rowNum] = row; // Add row to table
        }

        return table; // Return table
    }

    /**
     * Main method: program entry point.
     * Reads students.txt and assignments.txt, generates marks, writes CSV.
     */
    public static void main(final String[] args) {
        try {
            // Read students
            List<String> studentsList = new ArrayList<>();
            try (Scanner studentScanner = new Scanner(new File("students.txt"))) {
                while (studentScanner.hasNextLine()) {
                    String line = studentScanner.nextLine().trim();
                    if (!line.isEmpty()) studentsList.add(line);
                }
            }
            String[] studentArray = studentsList.toArray(new String[0]);

            // Read assignments
            List<String> assignmentsList = new ArrayList<>();
            try (Scanner assignmentScanner = new Scanner(new File("assignments.txt"))) {
                while (assignmentScanner.hasNextLine()) {
                    String line = assignmentScanner.nextLine().trim();
                    if (!line.isEmpty()) assignmentsList.add(line);
                }
            }
            String[] assignmentsArray = assignmentsList.toArray(new String[0]);

            // Generate marks
            String[][] marksTable = generateMarks(studentArray, assignmentsArray);

            // Write to CSV
            try (PrintWriter writer = new PrintWriter(new File("marks.csv"))) {
                for (String[] row : marksTable) {
                    writer.println(String.join(",", row));
                }
            }

            System.out.println("marks.csv created successfully!");

        } catch (FileNotFoundException e) {
            System.out.println("One of the input files (students.txt or assignments.txt) is missing.");
        }
    }
}
