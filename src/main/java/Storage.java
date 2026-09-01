import java.io.*;

public class Storage {
    /**
     * Saves all currently stored tasks to {@code save.txt}.
     *
     * <p>Each task is written on its own line using the task's string
     * representation. If the file cannot be written, the exception is
     * printed and the application continues running.</p>
     */
    private String filePath;

    public Storage(String filePath) {
            this.filePath = filePath;
    }
    public String save(String saveData) {
        //i didnt want to do an exception check but i am forced to.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            writer.write(saveData.toString());
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            //System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        } finally {
            ;
        }
        return "saved!";
    }

    /**
     * Loads previously saved tasks from {@code save.txt} into the task list.
     *
     * <p>The task type and completion status are reconstructed from each
     * saved line. If the file does not exist or cannot be read, loading is
     * skipped and the application continues with an empty task list.</p>
     */
    public String load() {
        System.out.println("fn: "+this.filePath); //debug
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            return br.readAllAsString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
