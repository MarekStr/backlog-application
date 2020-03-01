package pl.marek.backend;

import java.io.Serializable;
import java.util.Date;

public class InProgress extends ToDoTask  implements Serializable, Cloneable  {

    public InProgress(){}


    public InProgress(Long id, String taskDetails, Date finishDate, String difficultyLevel, String priorityTask) {
        this.id = id;
        this.taskDetails = taskDetails;
        this.finishDate = finishDate;
        this.difficultyLevel = difficultyLevel;
        this.priorityTask = priorityTask;
    }
}
