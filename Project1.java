//بسم الله الرحمن الرحيم
//Abdulaziz Ali Mijalli -- 446800377
import java.util.Arrays;
import java.util.Scanner;

public class Project1 {
    public static void main(String[] args) {
        String studentName = null;
        String kkuId = null;
        int numCourses = 0;
        int[] marks = null;
        boolean detailsEntered = false;
        boolean marksEntered = false;
        int choice;

        try (Scanner scanner = new Scanner(System.in)) {
            
            do {
                System.out.println("\n=========================================");
                System.out.println("Menu:");
                System.out.println("1. Enter Student Details");
                System.out.println("2. Enter Marks");
                System.out.println("3. Display Marks Info.");
                System.out.println("4. Display Student Details");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.out.println("\n[!] Invalid input. Please enter a number (1-5).");
                    scanner.nextLine();
                    choice = 0;
                    continue; //restart
                }

                switch (choice) {
                    case 1:
                        System.out.println("\n--- 1. Enter Student Details ---");
                        
                        System.out.print("Enter Student Name: ");
                        String rawName = scanner.nextLine();
                        
                        String trimmedName = rawName.trim();
                        String[] words = trimmedName.split("\\s+");
                        StringBuilder titleCaseName = new StringBuilder();

                        for (String word : words) {
                            if (word.length() > 0) {
                                // Cap and append lower
                                String firstLetter = word.substring(0, 1).toUpperCase();
                                String restOfWord = word.substring(1).toLowerCase();
                                titleCaseName.append(firstLetter).append(restOfWord).append(" ");
                            }
                        }
                        studentName = titleCaseName.toString().trim();


                        // ID
                        String tempId;
                        boolean isIdValid;
                        do {
                            System.out.print("Enter ID Number (must be 9 digits, start with 4): ");
                            tempId = scanner.nextLine().trim();
                            isIdValid = true;

                            if (tempId.length() != 9) {
                                isIdValid = false;
                            }
                            if (isIdValid && !tempId.startsWith("4")) {
                                isIdValid = false;
                            }
                            if (isIdValid) {
                                try {
                                    Long.parseLong(tempId);
                                } catch (NumberFormatException e) {
                                    isIdValid = false;
                                }
                            }
                            
                            if (!isIdValid) {
                                System.out.println("[!] ID validation failed. Please try again.");
                            }
                        } while (!isIdValid);
                        kkuId = tempId;

                        // Courses
                        int tempNumCourses = 0;
                        do {
                            System.out.print("How many courses last semester (Max 5)? ");
                            if (scanner.hasNextInt()) {
                                tempNumCourses = scanner.nextInt();
                                scanner.nextLine();
                                
                                if (tempNumCourses < 1 || tempNumCourses > 5) {
                                    System.out.println("[!] Course count must be between 1 and 5.");
                                    tempNumCourses = 0; 
                                }
                            } else {
                                System.out.println("[!] Invalid input. Please enter an integer.");
                                scanner.nextLine();
                            }
                        } while (tempNumCourses == 0);
                        numCourses = tempNumCourses;
                        //Marks
                        marks = new int[numCourses];
                        detailsEntered = true;
                        marksEntered = false; 
                        System.out.println("\n[i] Student details successfully recorded.");
                        break;

                    case 2:
                        if (!detailsEntered) {
                            System.out.println("\n[!] Error: Please enter Student Details (Option 1) first.");
                            break;
                        }
                        
                        System.out.println("\n--- 2. Enter Marks ---");
                        System.out.println("Enter " + numCourses + " marks separated by spaces (0-100):");
                        System.out.print("Marks: ");
                        
                        String marksLine = scanner.nextLine().trim();
                        String[] markTokens = marksLine.split("\\s+");

                        if (markTokens.length != numCourses) {
                            System.out.println("[!] Error: Expected " + numCourses + " marks but received " + markTokens.length + ".");
                            break;
                        }

                        boolean validMarks = true;
                        int[] tempMarks = new int[numCourses];
                        
                        for (int i = 0; i < numCourses; i++) {
                            try {
                                int mark = Integer.parseInt(markTokens[i]);
                                // Betw'een 0 - 100
                                if (mark < 0 || mark > 100) {
                                    System.out.println("[!] Error: Mark " + mark + " (for subject " + (i + 1) + ") is outside the valid range (0-100).");
                                    validMarks = false;
                                    break;
                                }
                                tempMarks[i] = mark;
                            } catch (NumberFormatException e) {
                                System.out.println("[!] Error: Invalid number format for mark " + (i + 1) + ".");
                                validMarks = false;
                                break;
                            }
                        }

                        if (validMarks) {
                            marks = tempMarks;
                            marksEntered = true;
                            System.out.println("\n[i] Marks successfully recorded.");
                            System.out.println("\n--- Displaying Marks Info (Auto-run from Option 2) ---");
                            
                            int autoSum = 0;
                            int autoMaxMark = 0; 
                            int autoMinMark = 100;
                            for (int mark : marks) {
                                autoSum += mark;
                                if (mark > autoMaxMark) {
                                    autoMaxMark = mark;
                                }
                                if (mark < autoMinMark) {
                                    autoMinMark = mark;
                                }
                            }
                            double autoAverage = (double) autoSum / numCourses;
                            
                            System.out.printf("Average Mark: %.2f\n", autoAverage);
                            System.out.println("Maximum Mark: " + autoMaxMark);
                            System.out.println("Minimum Mark: " + autoMinMark);
                            
                            System.out.println("\nMark breakdown (Pass >= 60):");
                            for (int mark : marks) {
                                String status = (mark >= 60) ? "Pass" : "Fail";
                                System.out.println("Mark: " + mark + " -> " + status);
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n--- 3. Display Marks Info. ---");
                        if (!detailsEntered || !marksEntered) {
                            System.out.println("[!] Error: Please ensure Student Details (Option 1) and Marks (Option 2) have been entered.");
                            break;
                        }
                        
                        int totalSum = 0;
                        int maxMark = 0; 
                        int minMark = 100;
                        for (int mark : marks) {
                            totalSum += mark;
                            if (mark > maxMark) {
                                maxMark = mark;
                            }
                            if (mark < minMark) {
                                minMark = mark;
                            }
                        }
                        double average = (double) totalSum / numCourses;
                        
                        System.out.println("All Marks: " + Arrays.toString(marks));
                        System.out.printf("Average Mark: %.2f\n", average);
                        System.out.println("Maximum Mark: " + maxMark);
                        System.out.println("Minimum Mark: " + minMark);
                        
                        System.out.println("\nMark breakdown (Pass >= 60):");
                        for (int mark : marks) {
                            String status = (mark >= 60) ? "Pass" : "Fail";
                            System.out.println("Mark: " + mark + " -> " + status);
                        }
                        break;

                    case 4:
                        System.out.println("\n--- 4. Display Student Details ---");
                        if (!detailsEntered || !marksEntered) {
                            System.out.println("[!] Error: Please ensure Student Details (Option 1) and Marks (Option 2) have been entered.");
                            break;
                        }
                        int displaySum = 0;
                        int displayMaxMark = 0; 
                        int displayMinMark = 100;
                        for (int mark : marks) {
                            displaySum += mark;
                            if (mark > displayMaxMark) {
                                displayMaxMark = mark;
                            }
                            if (mark < displayMinMark) {
                                displayMinMark = mark;
                            }
                        }
                        double displayAverage = (double) displaySum / numCourses;

                        // Min-Max
                        int maxIndex = -1;
                        int minIndex = -1;
                        for(int i = 0; i < marks.length; i++) {
                            if (marks[i] == displayMaxMark && maxIndex == -1) {
                                maxIndex = i;
                            }
   
                            if (marks[i] == displayMinMark && minIndex == -1) {
                                minIndex = i;
                            }
                        }

                        System.out.println("Name: " + studentName);
                        System.out.println("KKU ID: " + kkuId);
                        
                        // Table
                        System.out.println("-----------------------------------------");
                        System.out.printf("%1$-5s %2$-5s %3$-5s %4$-1s %5$-5s\n", 
                                "Crs", "Mark", "P/F", "Avg:", String.format("%.1f", displayAverage));
                        System.out.println("=========================================");
                        
                        for (int i = 0; i < numCourses; i++) {
                            int mark = marks[i];
                            String status = (mark >= 60) ? "Pass" : "Fail";
                            String indicator = "";
                            
                            if (i == maxIndex) {
                                indicator = "Max";
                            } else if (i == minIndex) {
                                indicator = "Min";
                            }

                            // Print said row
                            System.out.printf("%1$-5s %2$-5s %3$-5s %4$-1s %5$-5s\n", 
                                (i + 1), mark, status, 
                                (i == 0 ? "--" : "--"), // THIS IS FOR SPACING
                                indicator
                            );
                        }
                        System.out.println("-----------------------------------------");
                        break;

                    case 5:
                        System.out.println("\nExiting Student Mark Application. Goodbye!");
                        break;

                    default:
                        System.out.println("\n[!] Invalid choice. Please enter a number between 1 and 5.");
                }
            } while (choice != 5);
            
        } catch (Exception e) {
            System.err.println("\nAn unexpected application error occurred: " + e.getMessage());
        }
    }
}



