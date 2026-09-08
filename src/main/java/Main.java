import botavius.exception.BotaviusException;
import botavius.parser.Parser;
import botavius.storage.Storage;
import botavius.tasklist.TaskList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** A Roman-tech themed graphical interface for Botavius. */
public class Main extends Application {
    /** Persists tasks between application sessions. */
    private Storage storage;
    /** Holds the current session's tasks. */
    private TaskList tasks;
    /** Displays task results. */
    private ListView<String> taskView;
    /** Displays command feedback. */
    private Label status;

    /** Builds and displays the Botavius dashboard. */
    @Override
    public void start(Stage stage) {
        storage = new Storage("save.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (BotaviusException exception) {
            tasks = new TaskList("");
        }
        taskView = new ListView<>();
        status = new Label("Awaiting your command, Praetor.");
        Label title = new Label("BOTAVIUS");
        title.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #e7bd63;");
        Label subtitle = new Label("IMPERIAL TASK TERMINAL  //  SPQR-25");
        subtitle.setStyle("-fx-text-fill: #9a8d83; -fx-font-family: monospace;");
        TextField command = new TextField();
        command.setPromptText("Enter a command: todo, deadline, event, list, find...");
        Button execute = new Button("EXECUTE");
        execute.setOnAction(event -> runCommand(command));
        command.setOnAction(event -> runCommand(command));
        HBox commandBar = new HBox(10, command, execute);
        HBox.setHgrow(command, Priority.ALWAYS);
        Button refresh = new Button("REFRESH TABLET");
        refresh.setOnAction(event -> refreshTasks());
        HBox actions = new HBox(10, refresh, status);
        VBox header = new VBox(5, title, subtitle, commandBar, actions);
        header.setPadding(new Insets(24));
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(taskView);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #17131c;");
        taskView.setStyle("-fx-control-inner-background: #211b27; -fx-control-inner-background-alt: #2a2231;"
                + "-fx-font-family: monospace; -fx-font-size: 15px; -fx-text-fill: #e8ded2;");
        execute.setStyle(buttonStyle());
        refresh.setStyle(buttonStyle());
        status.setStyle("-fx-text-fill: #c7b8a8; -fx-padding: 8px;");
        refreshTasks();
        stage.setTitle("Botavius // Imperial Task Terminal");
        stage.setScene(new Scene(root, 760, 560));
        stage.show();
    }

    /** Executes the command field and updates the task tablet. */
    private void runCommand(TextField command) {
        String input = command.getText().strip();
        if (input.isEmpty()) {
            status.setText("Enter a command before executing.");
            return;
        }
        try {
            if (!input.contains(" ") && !input.contains("/")) {
                status.setText("Use a command such as: todo buy milk");
            }
            if (!input.matches("(?i)(todo|deadline|event|list|find|mark|unmark|delete|bye)(\\s|$).*")) {
                input = "todo " + input;
            }
            String result = Parser.process(input, tasks);
            status.setText(result.replace("\n", "  "));
            refreshTasks();
            command.clear();
        } catch (BotaviusException | NumberFormatException exception) {
            status.setText("ERROR // " + exception.getMessage());
        } catch (RuntimeException exception) {
            status.setText("ERROR // " + exception.getMessage());
        }
    }

    /** Rebuilds the visible tablet from the current task list. */
    private void refreshTasks() {
        taskView.getItems().setAll(TaskList.listTasks().split("\n"));
    }

    /** Saves the session's task data when the window closes. */
    @Override
    public void stop() {
        if (storage != null && tasks != null) {
            storage.save(TaskList.getTaskStrings());
        }
    }

    /** @return the shared bronze button style */
    private String buttonStyle() {
        return "-fx-background-color: #9d6b35; -fx-text-fill: #fff3d4;"
                + "-fx-font-weight: bold; -fx-padding: 10px 16px;";
    }
}
