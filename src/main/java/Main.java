import botavius.exception.BotaviusException;
import botavius.parser.Parser;
import botavius.storage.Storage;
import botavius.tasklist.TaskList;
import botavius.tasklist.Task;
import botavius.tasklist.Deadline;
import botavius.tasklist.Event;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** A Roman-tech themed graphical interface for Botavius. */
public class Main extends Application {
    /** Persists tasks between application sessions. */
    private Storage storage;
    /** Holds the current session's tasks. */
    private TaskList tasks;
    /** Displays task results. */
    private VBox taskView;
    /** Displays command feedback. */
    private Label status;
    /** Start of the currently displayed task range, or {@code null} on the calendar screen. */
    private LocalDate activeFromDate;
    /** End of the currently displayed task range, or {@code null} on the calendar screen. */
    private LocalDate activeToDate;

    /** Builds and displays the Botavius dashboard. */
    @Override
    public void start(Stage stage) {
        storage = new Storage("save.txt");
        try {
            tasks = new TaskList(storage.load());
        } catch (BotaviusException exception) {
            tasks = new TaskList("");
        }
        taskView = new VBox(6);
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
        Button newTask = new Button("NEW TASK");
        newTask.setOnAction(event -> showNewTaskDialog());
        HBox actions = new HBox(10, newTask, refresh, status);
        VBox header = new VBox(5, title, subtitle, commandBar, actions);
        header.setPadding(new Insets(24));
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(taskView);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #17131c;");
        taskView.setStyle("-fx-background-color: #211b27; -fx-padding: 12px;");
        execute.setStyle(buttonStyle());
        refresh.setStyle(buttonStyle());
        newTask.setStyle(buttonStyle());
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
        taskView.getChildren().setAll(createCalendar());
    }

    /** Creates date fields and a button for filtering scheduled tasks. */
    private VBox createCalendar() {
        DatePicker fromDate = new DatePicker(LocalDate.now());
        DatePicker toDate = new DatePicker(LocalDate.now());
        Button showTasks = new Button("SHOW TASKS");
        showTasks.setStyle(buttonStyle());
        showTasks.setOnAction(event -> showTasksInRange(fromDate.getValue(), toDate.getValue()));
        HBox dateFields = new HBox(8, new Label("FROM:"), fromDate, new Label("TO:"), toDate, showTasks);
        VBox view = new VBox(8, new Label("Select an inclusive date range"), dateFields);
        return view;
    }

    /** Displays scheduled tasks whose dates fall within an inclusive date range. */
    private void showTasksInRange(LocalDate fromDate, LocalDate toDate) {
        taskView.getChildren().clear();
        if (fromDate == null || toDate == null || fromDate.isAfter(toDate)) {
            taskView.getChildren().add(new Label("Choose a valid FROM and TO date."));
            return;
        }
        activeFromDate = fromDate;
        activeToDate = toDate;
        Button back = new Button("BACK TO DATE RANGE");
        back.setStyle(buttonStyle());
        back.setOnAction(event -> {
            activeFromDate = null;
            activeToDate = null;
            refreshTasks();
        });
        taskView.getChildren().add(back);
        taskView.getChildren().add(new Label("TASKS FROM " + fromDate + " TO " + toDate));
        boolean foundTask = false;
        for (int index = 0; index < TaskList.getTasks().size(); index++) {
            Task task = TaskList.getTasks().get(index);
            LocalDate taskDate = getTaskDate(task);
            if (taskDate != null && !taskDate.isBefore(fromDate) && !taskDate.isAfter(toDate)) {
                taskView.getChildren().add(createTaskControl(task, index + 1));
                foundTask = true;
            }
        }
        if (!foundTask) {
            taskView.getChildren().add(new Label("No scheduled tasks found in this range."));
        }
    }

    /** Returns the date used to place a scheduled task in the range result. */
    private LocalDate getTaskDate(Task task) {
        if (task instanceof Deadline deadline) {
            return deadline.getBy().toLocalDate();
        }
        if (task instanceof Event event) {
            return event.getFrom().toLocalDate();
        }
        return null;
    }

    /** Creates a checkbox that sends the matching mark or unmark command. */
    private Node createTaskControl(Task task, int taskNumber) {
        CheckBox taskBox = new CheckBox(taskNumber + ". " + task.toString());
        taskBox.setSelected(task.isDone());
        taskBox.setOnAction(event -> executeParserCommand((taskBox.isSelected() ? "mark " : "unmark ")
                + taskNumber));
        return taskBox;
    }

    /** Sends a GUI-generated command through the existing parser. */
    private void executeParserCommand(String command) {
        try {
            status.setText(Parser.process(command, tasks).replace("\n", "  "));
            if (activeFromDate != null && activeToDate != null) {
                showTasksInRange(activeFromDate, activeToDate);
            } else {
                refreshTasks();
            }
        } catch (BotaviusException | NumberFormatException exception) {
            status.setText("ERROR // " + exception.getMessage());
        }
    }

    /** Opens the task-type menu and creates the selected task through the parser. */
    private void showNewTaskDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Botavius Task");
        ComboBox<String> type = new ComboBox<>();
        type.getItems().addAll("todo", "deadline", "event");
        type.setValue("todo");
        TextField description = new TextField();
        description.setPromptText("Description");
        DatePicker date = new DatePicker(LocalDate.now());
        Spinner<Integer> fromHour = new Spinner<>(new IntegerSpinnerValueFactory(0, 23, 9));
        Spinner<Integer> fromMinute = new Spinner<>(new IntegerSpinnerValueFactory(0, 59, 0));
        Spinner<Integer> toHour = new Spinner<>(new IntegerSpinnerValueFactory(0, 23, 10));
        Spinner<Integer> toMinute = new Spinner<>(new IntegerSpinnerValueFactory(0, 59, 0));
        fromHour.setEditable(true);
        fromMinute.setEditable(true);
        toHour.setEditable(true);
        toMinute.setEditable(true);
        VBox fields = new VBox(8, type, description, date,
                new HBox(6, new Label("From (hour/min):"), fromHour, fromMinute),
                new HBox(6, new Label("To (hour/min):"), toHour, toMinute));
        type.setOnAction(event -> fields.getChildren().get(4).setVisible("event".equals(type.getValue())));
        fields.getChildren().get(4).setVisible(false);
        dialog.getDialogPane().setContent(fields);
        dialog.getDialogPane().getButtonTypes().addAll(new ButtonType("ADD TASK", ButtonData.OK_DONE),
                new ButtonType("BACK", ButtonData.CANCEL_CLOSE));
        dialog.getDialogPane().setStyle("-fx-background-color: #211b27; -fx-text-fill: #e8ded2;");
        dialog.setOnShown(event -> dialog.getDialogPane().lookupAll(".button")
                .forEach(node -> ((Button) node).setStyle(buttonStyle())));
        dialog.setResultConverter(button -> button.getButtonData() == ButtonData.OK_DONE ? button : null);
        dialog.showAndWait().ifPresent(button -> addTask(type.getValue(), description.getText(), date.getValue(),
                fromHour.getValue(), fromMinute.getValue(), toHour.getValue(), toMinute.getValue()));
    }

    /** Formats and submits a newly entered task to the parser. */
    private void addTask(String type, String description, LocalDate date, int fromHour, int fromMinute,
                         int toHour, int toMinute) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String start = date.atTime(fromHour, fromMinute).format(format);
        String end = date.atTime(toHour, toMinute).format(format);
        String command = type + " " + description;
        if ("deadline".equals(type)) {
            command += " /by " + start;
        } else if ("event".equals(type)) {
            command += " /from " + start + " /to " + end;
        }
        executeParserCommand(command);
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
