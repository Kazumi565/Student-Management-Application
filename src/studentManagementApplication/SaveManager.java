package studentManagementApplication;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class SaveManager {
    private static final String SAVE_FILE_PATH = "student_management_system_save.txt";

    public void saveState(List<Faculty> faculties) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            outputStream.writeObject(faculties);
            System.out.println("State saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Faculty> loadState() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            Object savedObject = inputStream.readObject();
            if (savedObject instanceof List) {
                System.out.println("State loaded successfully.");
                return (List<Faculty>) savedObject;
            }
        } catch (FileNotFoundException e) {
            // Handle file not found, create the file if needed
            System.out.println("Save file not found. Creating a new one.");
            saveState(new ArrayList<>()); // Save an empty state to create the file
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
