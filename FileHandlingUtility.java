import java.io.*;
import java.util.Scanner;

public class FileHandlingUtility {

    // ✅ Create a new file (empty or with content)
    public static void createFile(String filename, Scanner scanner) {
        File file = new File(filename);
        if (file.exists()) {
            System.out.println("⚠️ File already exists.");
            return;
        }

        try {
            if (file.createNewFile()) {
                System.out.println("✅ File '" + filename + "' created successfully.");

                System.out.print("📝 Do you want to write content now? (yes/no): ");
                String response = scanner.nextLine().trim().toLowerCase();

                if (response.equals("yes")) {
                    System.out.print("✍️ Enter content to write: ");
                    String content = scanner.nextLine();

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(content);
                        System.out.println("✅ Content written to the file.");
                    }
                } else {
                    System.out.println("📁 File left empty.");
                }

            } else {
                System.out.println("❌ File could not be created.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error creating file: " + e.getMessage());
        }
    }

    // ✅ Read content from a file
    public static void readFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("⚠️ File does not exist.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("\n📄 Contents of '" + filename + "':");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // ✅ Append content to an existing file
    public static void appendToFile(String filename, String content) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("⚠️ File does not exist.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.newLine();
            writer.write(content);
            System.out.println("✅ Content appended to '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("❌ Error appending to file: " + e.getMessage());
        }
    }

    // ✅ Modify file content with options
    public static void modifyFile(String filename, Scanner scanner) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("⚠️ File does not exist.");
            return;
        }

        // Display existing content
        System.out.println("\n📄 Current contents of '" + filename + "':");
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            return;
        }

        // Modification options
        System.out.println("\n🔧 Modification Options:");
        System.out.println("1. Replace entire content");
        System.out.println("2. Find and replace specific content");
        System.out.print("👉 Choose modify option: ");
        int modChoice = scanner.nextInt();
        scanner.nextLine(); // clear newline

        String updatedContent = "";

        try {
            if (modChoice == 1) {
                System.out.print("✏️ Enter new content to replace everything: ");
                updatedContent = scanner.nextLine();
            } else if (modChoice == 2) {
                System.out.print("🔍 Enter the text to find: ");
                String toFind = scanner.nextLine();
                System.out.print("✏️ Enter the text to replace it with: ");
                String toReplace = scanner.nextLine();
                updatedContent = contentBuilder.toString().replace(toFind, toReplace);
            } else {
                System.out.println("❗ Invalid choice.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(updatedContent.trim());
                System.out.println("✅ File modified successfully.");
            }

        } catch (IOException e) {
            System.out.println("❌ Error modifying file: " + e.getMessage());
        }
    }

    // ✅ Delete a file
    public static void deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("🗑️ File '" + filename + "' deleted successfully.");
            } else {
                System.out.println("❌ Failed to delete the file.");
            }
        } else {
            System.out.println("⚠️ File does not exist.");
        }
    }

    // ✅ Main method with menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        String filename;
        String content;

        do {
            System.out.println("\n📂 FILE HANDLING UTILITY MENU");
            System.out.println("1. Create a new file (empty or write)");
            System.out.println("2. Read a file");
            System.out.println("3. Append to a file");
            System.out.println("4. Modify a file");
            System.out.println("5. Delete a file");
            System.out.println("6. Exit");
            System.out.print("👉 Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // clear newline

            switch (choice) {
                case 1:
                    System.out.print("📄 Enter new file name (with .txt): ");
                    filename = scanner.nextLine();
                    createFile(filename, scanner);
                    break;

                case 2:
                    System.out.print("📖 Enter file name to read: ");
                    filename = scanner.nextLine();
                    readFile(filename);
                    break;

                case 3:
                    System.out.print("➕ Enter file name to append to: ");
                    filename = scanner.nextLine();
                    System.out.print("✍️ Enter content to append: ");
                    content = scanner.nextLine();
                    appendToFile(filename, content);
                    break;

                case 4:
                    System.out.print("🛠️ Enter file name to modify: ");
                    filename = scanner.nextLine();
                    modifyFile(filename, scanner);
                    break;

                case 5:
                    System.out.print("🗑️ Enter file name to delete: ");
                    filename = scanner.nextLine();
                    deleteFile(filename);
                    break;

                case 6:
                    System.out.println("👋 Exiting program. Goodbye!");
                    break;

                default:
                    System.out.println("❗ Invalid choice. Please try again.");
            }

        } while (choice != 6);

        scanner.close();
    }
}
