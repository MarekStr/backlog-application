package pl.marek.ui.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.marek.backend.DoneService;
import pl.marek.backend.ToDoTask;
import pl.marek.ui.MainView;

@Route(value = "DoneGrid", layout = MainView.class)
@PageTitle("Done")
public class DoneGrid extends VerticalLayout {

    @Autowired
    private DoneService doneService;

    private Grid<ToDoTask> gridWithDoneTasks= new Grid<>(ToDoTask.class);

    public DoneGrid(DoneService doneService){
        this.doneService= doneService;

        gridWithDoneTasks.setColumns("finishDate", "taskDetails","story","difficultyLevel");
        gridWithDoneTasks.getColumnByKey("finishDate").setFlexGrow(0).setWidth("150px");
        gridWithDoneTasks.getColumnByKey("difficultyLevel").setFlexGrow(0).setWidth("150px");
        setSizeFull();
        add(gridWithDoneTasks);
        updateDoneGrid();
    }

    private void updateDoneGrid() {
        gridWithDoneTasks.setItems(doneService.findAllDone());
    }
}
