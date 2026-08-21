public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getTaskString() {
        String return_string = "";
        if (isDone) {
            return_string = "[X] ";
        } else {
            return_string = "[ ] ";
        }
        return return_string + this.description;
    }
}
