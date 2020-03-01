package pl.marek.ui.views;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marek.backend.InProgress;
import pl.marek.backend.InProgressService;
import pl.marek.ui.MainView;

@Route(value = "InProgressGrid", layout = MainView.class)
@PageTitle("In progess")
public class InProgressGrid extends VerticalLayout {

    @Autowired
    private InProgressService inProgressService;

    private Grid<InProgress> gridWithInProgress = new Grid(InProgress.class);
    private Button moveToDoneGridButton = new Button("Task Finished");

    private InProgress inProgress;

    public InProgressGrid(InProgressService inProgressService) {
        this.inProgressService = inProgressService;

        gridWithInProgress.setColumns("finishDate", "taskDetails", "difficultyLevel", "priorityTask");
        gridWithInProgress.setSortableColumns("finishDate");
        gridWithInProgress.getColumnByKey("finishDate").setFlexGrow(0).setWidth("150px");
        gridWithInProgress.getColumnByKey("difficultyLevel").setFlexGrow(0).setWidth("150px");
        gridWithInProgress.getColumnByKey("priorityTask").setFlexGrow(0).setWidth("200px");

        gridWithInProgress.addSelectionListener(e ->
                inProgress = gridWithInProgress.asSingleSelect().getValue());  // zaznaczanie lini z pobraniem wartosci

        moveToDoneGridButton.addClickListener(e -> moveToDoneGrid());

        add(gridWithInProgress, moveToDoneGridButton);

        updateInProgressGrid();
    }

    private void moveToDoneGrid() {
        inProgressService.addTaskToDoneGrid(inProgress);
        inProgressService.removeFromInProgress(inProgress);
        updateInProgressGrid();
        Notification.show("Task move finished task");
    }

    private void updateInProgressGrid() {
        gridWithInProgress.setItems(inProgressService.findAllInProgress());
    }
}