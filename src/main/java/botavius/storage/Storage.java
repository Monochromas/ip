package botavius.storage;

import java.io.*;

/** Reads and writes the task data file used by Botavius. */
public class Storage {
    /** Path of the file used for persistence. */
    private String filePath;
    /** Creates storage backed by the specified file.
     *
     * @param filePath path of the persistence file
     */
    public Storage(String filePath) {
            this.filePath = filePath;
    }
    /**
     * Writes task data to the configured file.
     *
     * @param saveData serialized task data to write
     * @return {@code saved!} after the write attempt
     */
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
     * Reads all text from the configured file.
     *
     * @return file contents, or an empty string when reading fails
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
