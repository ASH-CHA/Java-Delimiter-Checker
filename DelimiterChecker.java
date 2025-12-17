/*
 * Ashton Chavez
 * CMSC-315 Programming Project 1
 * May 28, 2024
 * 
 * The DelimiterChecker class contains the main method and manages the overall process 
 * of checking delimiter matching in the Java source file. It prompts the user for the file name, 
 * uses the FileAnalyzer to read the file, and uses a stack to track and verify matching delimiters 
 * (parentheses, braces, and square brackets). It reports any mismatches or unmatched delimiters 
 * found during the process.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class DelimiterChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = null;
        FileAnalyzer fileAnalyzer = null;

        // Prompt for a valid file name
        while (fileAnalyzer == null) {
            try {
                System.out.print("Enter the file name (including extension, e.g., TestFile.java): ");
                fileName = scanner.nextLine();
                fileAnalyzer = new FileAnalyzer(fileName);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
        scanner.close();

        Stack<Character> stack = new Stack<>();
        try {
            Character c;
            // Process the file character by character
            while ((c = fileAnalyzer.nextChar()) != null) {
                // If it's a left delimiter, push onto the stack
                if (c == '{' || c == '(' || c == '[') {
                    stack.push(c);
                } 
                // If it's a right delimiter, pop from the stack and check for a match
                else if (c == '}' || c == ')' || c == ']') {
                    if (stack.isEmpty()) {
                        System.out.println("Mismatched " + c + " at " + fileAnalyzer.getCurrentPosition());
                        return;
                    }
                    char open = stack.pop();
                    if (!matches(open, c)) {
                        System.out.println("Mismatched " + open + " and " + c + " at " + fileAnalyzer.getCurrentPosition());
                        return;
                    }
                }
            }
            // Check for any unmatched left delimiters
            if (!stack.isEmpty()) {
                System.out.println("Unmatched " + stack.pop() + " at the end of the file.");
            } else {
                System.out.println("All delimiters match correctly.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to check if delimiters match
    private static boolean matches(char open, char close) {
        return (open == '{' && close == '}') || (open == '(' && close == ')') || (open == '[' && close == ']');
    }
}