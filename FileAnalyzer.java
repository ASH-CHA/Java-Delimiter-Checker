/*
 * Ashton Chavez
 * CMSC-315 Programming Project 1
 * May 28, 2024
 * 
 * The FileAnalyzer class is responsible for reading a Java source file and 
 * providing characters one by one, excluding those inside comments (both single-line and multi-line), 
 * string literals, and character literals. It includes methods to get the next relevant character 
 * and to return the current position in the file (line number and character number).
 */

 import java.io.*;

 public class FileAnalyzer {
     // Reader to allow pushback of characters
     private PushbackReader reader;
     // Track the current line number in the file
     private int lineNumber;
     // Track the current character number in the line
     private int charNumber;
     // Flags to indicate whether we are inside various types of comments or literals
     private boolean inSingleLineComment;
     private boolean inMultiLineComment;
     private boolean inStringLiteral;
     private boolean inCharLiteral;
 
     // Constructor that initializes the reader and sets up initial state
     public FileAnalyzer(String fileName) throws FileNotFoundException {
         reader = new PushbackReader(new FileReader(fileName));
         lineNumber = 1;
         charNumber = 0;
         inSingleLineComment = false;
         inMultiLineComment = false;
         inStringLiteral = false;
         inCharLiteral = false;
     }
 
     // Method to get the next character from the file, skipping over comments and literals
     public Character nextChar() throws IOException {
         int next;
         while ((next = reader.read()) != -1) {
             char c = (char) next;
             charNumber++;
             
             // Handle end of single-line comments
             if (inSingleLineComment) {
                 if (c == '\n') {
                     inSingleLineComment = false;
                     lineNumber++;
                     charNumber = 0;
                 }
                 continue;
             }
 
             // Handle end of multi-line comments
             if (inMultiLineComment) {
                 if (c == '*' && reader.read() == '/') {
                     inMultiLineComment = false;
                 }
                 continue;
             }
 
             // Handle end of string literals
             if (inStringLiteral) {
                 if (c == '\"') {
                     inStringLiteral = false;
                 }
                 continue;
             }
 
             // Handle end of character literals
             if (inCharLiteral) {
                 if (c == '\'') {
                     inCharLiteral = false;
                 }
                 continue;
             }
 
             // Handle the start of comments and push back non-comment slashes
             if (c == '/') {
                 int nextChar = reader.read();
                 if (nextChar == '/') {
                     inSingleLineComment = true;
                     continue;
                 } else if (nextChar == '*') {
                     inMultiLineComment = true;
                     continue;
                 } else {
                     reader.unread(nextChar);
                 }
             }
 
             // Handle the start of string literals
             if (c == '\"') {
                 inStringLiteral = true;
                 continue;
             }
 
             // Handle the start of character literals
             if (c == '\'') {
                 inCharLiteral = true;
                 continue;
             }
 
             // Return the next non-whitespace character
             if (!Character.isWhitespace(c)) {
                 return c;
             }
 
             // Track new lines to update line and character numbers
             if (c == '\n') {
                 lineNumber++;
                 charNumber = 0;
             }
         }
         return null;  // Return null if end of file is reached
     }
 
     // Method to get the current position in the file as a string
     public String getCurrentPosition() {
         return "line " + lineNumber + ", character " + charNumber;
     }
 }