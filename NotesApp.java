import java.io.*;
import java.util.*;

public class NotesApp {

    private static final String NOTES_DIR = "notes";

    public static void main(String[] args) {
        File dir = new File(NOTES_DIR);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- NOTES APP ---");
            System.out.println("1. List notes");
            System.out.println("2. Create note");
            System.out.println("3. View note");
            System.out.println("4. Delete note");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int opt = sc.nextInt();
            sc.nextLine(); // consume leftover newline

            switch (opt) {
                case 1:
                    listNotes();
                    break;
                case 2:
                    createNote(sc);
                    break;
                case 3:
                    viewNote(sc);
                    break;
                case 4:
                    deleteNote(sc);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void listNotes() {
        File dir = new File(NOTES_DIR);
        String[] files = dir.list();

        System.out.println("\n--- Your Notes ---");
        if (files == null || files.length == 0) {
            System.out.println("No notes yet.");
            return;
        }

        for (String f : files) {
            System.out.println("- " + f.replace(".txt", ""));
        }
    }

    private static void createNote(Scanner sc) {
        System.out.print("Enter note title: ");
        String title = sc.nextLine();

        System.out.println("Enter note text (end with an empty line):");

        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            sb.append(line).append("\n");
        }

        try (FileWriter fw = new FileWriter(NOTES_DIR + "/" + title + ".txt")) {
            fw.write(sb.toString());
            System.out.println("Note saved.");
        } catch (IOException e) {
            System.out.println("Error saving note.");
        }
    }

    private static void viewNote(Scanner sc) {
        System.out.print("Enter note title to view: ");
        String title = sc.nextLine();

        File file = new File(NOTES_DIR + "/" + title + ".txt");
        if (!file.exists()) {
            System.out.println("Note not found.");
            return;
        }

        System.out.println("\n--- " + title + " ---");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading note.");
        }
    }

    private static void deleteNote(Scanner sc) {
        System.out.print("Enter note title to delete: ");
        String title = sc.nextLine();

        File file = new File(NOTES_DIR + "/" + title + ".txt");
        if (file.delete()) {
            System.out.println("Deleted.");
        } else {
            System.out.println("Note not found.");
        }
    }
}
