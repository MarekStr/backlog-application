package pl.marek.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marek.backend.PriorityOfTask;
import pl.marek.backend.UserStories;
import pl.marek.backend.UserStoriesService;
import pl.marek.ui.MainView;


@Route(value = "UserStoriesGrid", layout = MainView.class)
@PageTitle("User Stories")
public class UserStoriesGrid extends VerticalLayout {

    @Autowired
    private UserStoriesService userStoriesService;

    private Grid<UserStories> gridWithUserStories = new Grid(UserStories.class);
    private TextArea story = new TextArea("Enter User Story");
    private TextArea storyCopyTextArea = new TextArea("Update Story");
    private ComboBox priorityTaskCombobox = new ComboBox("Set priority level for this story");
    private Button addToToDoGridButton = new Button("Add Task to ToDo");
    private Dialog userStoriesDialog = new Dialog();
    private Button addNewUsersStories = new Button("Add new User Story");
    private Button saveUserStory = new Button("Save");
    private Button cancelUserStory = new Button("Cancel");
    private Button deleteUserStoryButton = new Button("Delete User Story");
    private Button updateUserStoryButton= new Button("Update User Story");
    private Button moveStoryToDoneButton =new Button("Move this story to Done");
    private TextField priorityTask = new TextField();
    private VerticalLayout mainContent = new VerticalLayout(gridWithUserStories);
    private Binder<UserStories> binder = new Binder<>(UserStories.class);
    private UserStories userStories;

    public UserStoriesGrid(UserStoriesService userStoriesService) {
        this.userStoriesService = userStoriesService;

        // tworzenie grida
        gridWithUserStories.setColumns("story", "priorityTask");
        gridWithUserStories.getColumnByKey("priorityTask").setFlexGrow(0).setWidth("200px");  // ustawianie szerokosci kolumn w gridzie


        mainContent.setSizeFull();
        addNewUsersStories.addClickListener(event -> newStory());
        addNewUsersStories.addThemeVariants(ButtonVariant.LUMO_LARGE);


        // tworzenie okna dialog i komponentow na nim
        HorizontalLayout buttonOnDialog = new HorizontalLayout(saveUserStory, cancelUserStory);
        buttonOnDialog.setPadding(true);
        story.setPlaceholder("Write here ...");
        HorizontalLayout storyAndComboBox = new HorizontalLayout(story, priorityTaskCombobox);
        storyAndComboBox.setPadding(true);
        storyAndComboBox.setAlignItems(Alignment.END);
        saveUserStory.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveUserStory.addClickListener(e -> saveButton());
        cancelUserStory.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelUserStory.addClickListener(e -> cancelButton());
        userStoriesDialog.setCloseOnEsc(false);
        userStoriesDialog.setCloseOnOutsideClick(false);
        priorityTaskCombobox.setItems(PriorityOfTask.values());
        userStoriesDialog.add(storyAndComboBox, buttonOnDialog);
        priorityTaskCombobox.addValueChangeListener(event ->
                priorityTask.setValue(String.valueOf(priorityTaskCombobox.getValue())));




        // obsluga pojawiajacego sie dolnego panelu i wszystkich jego opcji
        gridWithUserStories.addSelectionListener(e -> openEditingWindow());
        HorizontalLayout bottomField = new HorizontalLayout(addToToDoGridButton,updateUserStoryButton, moveStoryToDoneButton, deleteUserStoryButton, storyCopyTextArea);
        deleteUserStoryButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        addToToDoGridButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        storyCopyTextArea.getStyle().set("minWidth", "500px");
        bottomField.setPadding(true);
        bottomField.setAlignItems(Alignment.START);
        add(addNewUsersStories, mainContent, bottomField);

        updateUserStoryButton.addClickListener(e -> updateUserStoryInGrid());
        deleteUserStoryButton.addClickListener(e -> deleteUserStory());
        addToToDoGridButton.addClickListener(e -> moveUserStoryToDoGrid());
        moveStoryToDoneButton.addClickListener(e->moveStoryToDone());


        // obsluga bindera

        binder.bindInstanceFields(this);
        binder.setBean(new UserStories());

        updateUserStoriesGrid();
    }

    private void moveStoryToDone() {
        userStoriesService.addUserStoryToDoneGrid(userStories);
        userStoriesService.removeUserStory(userStories);
        updateUserStoriesGrid();
    }

    private void moveUserStoryToDoGrid() {
        userStoriesService.addUserStoryToToDoGrid(userStories);
        updateUserStoriesGrid();
        Notification.show("move to 2 grid");
    }

    private void updateUserStoryInGrid() {
        story.setValue(storyCopyTextArea.getValue());
        userStoriesService.updateUserStoryInDataBase(userStories);
        Notification.show("User story updated");
        updateUserStoriesGrid();
    }

    private void deleteUserStory() {
        userStoriesService.removeUserStory(userStories);
        updateUserStoriesGrid();
    }

    private void openEditingWindow() {  // otwieranie okna na dole ekranu po zaznaczeniu wiersza
        addNewUsersStories.setEnabled(false);
        if (gridWithUserStories.asSingleSelect().isEmpty()) {
            setFormVisible(false);
            addNewUsersStories.setEnabled(true);
        } else {
            userStories = gridWithUserStories.asSingleSelect().getValue();
            binder.setBean(userStories);
            storyCopyTextArea.setValue(story.getValue());
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
        addToToDoGridButton.setVisible(visible);
        deleteUserStoryButton.setVisible(visible);
        storyCopyTextArea.setVisible(visible);
        updateUserStoryButton.setVisible(visible);
        moveStoryToDoneButton.setVisible(visible);
    }

    private void updateUserStoriesGrid() {
        gridWithUserStories.setItems(userStoriesService.findAll());
        setFormVisible(false);
    }

    private void saveButton() {  // obsługa przycisku save na oknie dialog
        UserStories userStories = binder.getBean();
        userStoriesService.addUserStory(userStories);
        updateUserStoriesGrid();
        userStoriesDialog.close();
    }

    private void cancelButton() { //obsługa przycisku cancel na oknie dialog
        userStoriesDialog.close();
    }

    private void newStory() {
        story.clear();
        userStoriesDialog.open();
    }
}