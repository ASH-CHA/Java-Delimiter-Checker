# Java Delimiter Checker

## Description

The **Java Delimiter Checker** is a console-based Java program that verifies whether delimiters in a Java source file are properly matched. It checks **parentheses `()`**, **braces `{}`**, and **square brackets `[]`**, ensuring they are correctly opened and closed in the proper order.

To avoid false errors, the program intelligently **ignores delimiters inside comments, string literals, and character literals**. If a mismatch or an unmatched delimiter is found, the program reports the exact **line and character position** where the error occurs.

---

## How It Works

1. The program prompts the user to enter the name of a Java source file.
2. The `FileAnalyzer` class reads the file **character by character**.
3. While reading, it:

   * Skips single-line comments (`//`)
   * Skips multi-line comments (`/* ... */`)
   * Skips string literals (`"..."`)
   * Skips character literals (`'...'`)
4. When a delimiter is encountered:

   * Opening delimiters (`{`, `(`, `[`) are pushed onto a stack.
   * Closing delimiters (`}`, `)`, `]`) pop from the stack and are checked for a match.
5. The program reports:

   * A mismatched delimiter and its location
   * An unmatched opening delimiter at end of file
   * Or a success message if all delimiters match correctly

---

## Program Structure

### `DelimiterChecker.java`

* Contains the `main` method
* Handles user input and file validation
* Uses a stack to track delimiters
* Reports errors or success messages

### `FileAnalyzer.java`

* Reads the source file using a `PushbackReader`
* Filters out comments and literals
* Tracks line and character position
* Provides the next relevant character for delimiter checking

---

## How to Run

1. Place both `.java` files in the same directory:

   * `DelimiterChecker.java`
   * `FileAnalyzer.java`
2. Compile the program:

   ```bash
   javac DelimiterChecker.java FileAnalyzer.java
   ```
3. Run the program:

   ```bash
   java DelimiterChecker
   ```
4. Enter the name of a Java source file when prompted.

---

## Example Output

```
Enter the file name (including extension, e.g., TestFile.java):
TestFile.java
Mismatched { and ] at line 14, character 22
```

OR

```
All delimiters match correctly.
```
