package pl.marek.ui.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marek.backend.DifficultyLevelOfTask;
import pl.marek.backend.PriorityOfTask;
import pl.marek.backend.ToDoTask;
import pl.marek.backend.ToDoTaskService;
import pl.marek.ui.MainView;

import java.time.LocalDate;

@Route(value = "ToDoGrid", layout = MainView.class)
@PageTitle("To Do")
public class ToDoGrid extends VerticalLayout {

    @Autowired
    private ToDoTaskService toDoTaskService;

    private Grid<ToDoTask> gridWithToDo = new Grid(ToDoTask.class);
    private Button updateTaskButton = new Button("Update Task");
    private Button addToInProgressButton = new Button("Move Task to Sprint");
    private Button closeWindowButton = new Button("Close");
    private TextArea story = new TextArea("User story");
    private TextArea taskDetails = new TextArea("Detailed description of the task");
    private DatePicker finishDate = new DatePicker("Set finish date");
    private ComboBox difficultyLevelComboBox = new ComboBox("Difficulty Level");
    private ComboBox priorityTaskComboBox = new ComboBox("Change task priority");
    private TextField difficultyLevel = new TextField();
    private TextField priorityTask = new TextField();
    private HorizontalLayout allButtons = new HorizontalLayout(updateTaskButton, addToInProgressButton, closeWindowButton);
    private HorizontalLayout chooseWindow = new HorizontalLayout(finishDate, difficultyLevelComboBox, priorityTaskComboBox);
    private Binder<ToDoTask> binderToDo = new Binder<>(ToDoTask.class);
    private ToDoTask toDoTask;

    public ToDoGrid(ToDoTaskService toDoTaskService) {
        this.toDoTaskService = toDoTaskService;

        // tworzenie grida
        gridWithToDo.setColumns("finishDate", "story", "taskDetails", "difficultyLevel", "priorityTask");
        gridWithToDo.setSortableColumns("finishDate");
        gridWithToDo.getColumnByKey("finishDate").setFlexGrow(0).setWidth("150px");
        gridWithToDo.getColumnByKey("difficultyLevel").setFlexGrow(0).setWidth("150px");
        gridWithToDo.getColumnByKey("priorityTask").setFlexGrow(0).setWidth("200px");



        //gridWithToDo.getColumnByKey("finishDate").setResizable(true);    // ustawianie kolumn w gridzie
       // gridWithToDo.getColumns()
         //       .forEach(personColumn -> personColumn.setAutoWidth(true));


        // obsluga pojawiajacego sie dolnego panelu i wszystkich jego opcji
        difficultyLevelComboBox.setItems(DifficultyLevelOfTask.values());
        difficultyLevelComboBox.addValueChangeListener(event ->
                difficultyLevel.setValue(String.valueOf(difficultyLevelComboBox.getValue())));
        gridWithToDo.addSelectionListener(e -> openBottomWindow());
        VerticalLayout buttonsAndBoxes = new VerticalLayout(chooseWindow, allButtons);
        HorizontalLayout bottomWindowToDo = new HorizontalLayout(taskDetails, buttonsAndBoxes, story);
        priorityTaskComboBox.setItems(PriorityOfTask.values());
        priorityTaskComboBox.addValueChangeListener(event ->
                priorityTask.setValue(String.valueOf(priorityTaskComboBox.getValue())));
        finishDate.setMin(LocalDate.now());
        story.setReadOnly(true);
        allButtons.setPadding(true);
        addToInProgressButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        closeWindowButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        add(gridWithToDo, bottomWindowToDo);

        // obsluga Button-ow
        updateTaskButton.addClickListener(e -> updateTaskDetails());
        closeWindowButton.addClickListener(e -> setFormVisible(false));
        addToInProgressButton.addClickListener(e-> moveFromTaskToInProgress());


        // obsluga bindera
        binderToDo.forField(finishDate)
                .withConverter(new LocalDateToDateConverter())
                .bind(ToDoTask::getFinishDate, ToDoTask::setFinishDate);
        binderToDo.bindInstanceFields(this);
        binderToDo.setBean(new ToDoTask());


        updateToDoGrid();
    }

    private void moveFromTaskToInProgress() {
        toDoTaskService.addTaskToInProgressGrid(toDoTask);
        toDoTaskService.removeToDoTask(toDoTask);
        updateToDoGrid();
    }

    private void updateTaskDetails() {
        toDoTaskService.updateToDoInDataBase(toDoTask);
        updateToDoGrid();
    }

    private void openBottomWindow() { // otwieranie okna na dole ekranu po zaznaczeniu wiersza
        if (gridWithToDo.asSingleSelect().isEmpty()) {
            setFormVisible(false);
        } else {
            toDoTask = gridWithToDo.asSingleSelect().getValue();
            binderToDo.setBean(toDoTask);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        taskDetails.setVisible(visible);
        chooseWindow.setVisible(visible);
        allButtons.setVisible(visible);
        story.setVisible(visible);
    }

    private void updateToDoGrid() {
        gridWithToDo.setItems(toDoTaskService.findAllToDo());
        setFormVisible(false);
    }
}
