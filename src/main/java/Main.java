import botavius.exception.BotaviusException;
import botavius.parser.Parser;
import botavius.storage.Storage;
import botavius.tasklist.TaskList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
        HBox imperialFrame = new HBox(0, createRomanColumn(), root, createRomanColumn());
        imperialFrame.setPadding(Insets.EMPTY);
        imperialFrame.setStyle("-fx-background-color: #0f0c12;");
        HBox.setHgrow(root, Priority.ALWAYS);
        stage.setScene(new Scene(imperialFrame, 860, 560));
        stage.show();
    }

    /** Creates a decorative Roman column that visually frames the dashboard. */
    private VBox createRomanColumn() {
        Region capitalTop = columnBlock(68, 12);
        Region capitalMiddle = columnBlock(58, 10);
        StackPane shaft = createFlutedShaft();
        Region baseTop = columnBlock(34, 10);
        Region baseBottom = columnBlock(68, 14);
        VBox column = new VBox(4, capitalTop, capitalMiddle, shaft, baseTop, baseBottom);
        column.setAlignment(Pos.CENTER);
        column.setFillWidth(false);
        VBox.setVgrow(shaft, Priority.ALWAYS);
        column.setMinWidth(82);
        column.setPrefWidth(82);
        column.setMaxWidth(82);
        return column;
    }

    /** Creates a tall shaft with raised vertical strips that suggest Roman fluting. */
    private StackPane createFlutedShaft() {
        Region shaftBase = columnBlock(28, 0);
        HBox flutes = new HBox(3);
        flutes.setAlignment(Pos.CENTER);
        for (int index = 0; index < 5; index++) {
            Region flute = new Region();
            flute.setMinSize(3, 0);
            flute.setPrefSize(3, 0);
            flute.setMaxSize(3, Double.MAX_VALUE);
            flute.setStyle("-fx-background-color: linear-gradient(to right, #4b3324, #dfbd82, #5d402a);"
                    + "-fx-background-radius: 2px;");
            flutes.getChildren().add(flute);
        }
        StackPane shaft = new StackPane(shaftBase, flutes);
        shaft.setMinWidth(32);
        shaft.setPrefWidth(32);
        shaft.setMaxWidth(32);
        StackPane.setAlignment(flutes, Pos.CENTER);
        return shaft;
    }

    /** Creates one stone-colored section of a decorative column. */
    private Region columnBlock(double width, double height) {
        Region block = new Region();
        block.setMinSize(width, height);
        block.setPrefSize(width, height);
        block.setMaxSize(width, height);
        block.setStyle("-fx-background-color: linear-gradient(to right, #6b4a31, #c39a62 45%, #795538);"
                + "-fx-border-color: #d7b477 #49301f #49301f #d7b477; -fx-border-width: 1px;");
        return block;
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
