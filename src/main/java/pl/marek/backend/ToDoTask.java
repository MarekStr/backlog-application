package pl.marek.backend;

import java.io.Serializable;
import java.util.Date;

public class ToDoTask implements Serializable, Cloneable {

    Long id;
    String story;
    String taskDetails;
     Date finishDate;
     String difficultyLevel;
     String priorityTask;

    public ToDoTask() {
    }

    public ToDoTask(Long id, String taskDetails, Date finishDate, String difficultyLevel, String priorityTask) {
        this.id = id;
        this.taskDetails = taskDetails;
        this.finishDate = finishDate;
        this.difficultyLevel = difficultyLevel;
        this.priorityTask = priorityTask;
    }

    public ToDoTask(Long id, String story, String taskDetails, Date finishDate, String difficultyLevel) {
        this.id = id;
        this.story = story;
        this.taskDetails = taskDetails;
        this.finishDate = finishDate;
        this.difficultyLevel = difficultyLevel;
    }

    public ToDoTask(Long id, String story, String taskDetails, Date finishDate, String difficultyLevel, String priorityTask) {
        this.id = id;
        this.story = story;
        this.taskDetails = taskDetails;
        this.finishDate = finishDate;
        this.difficultyLevel = difficultyLevel;
        this.priorityTask = priorityTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getPriorityTask() {
        return priorityTask;
    }

    public void setPriorityTask(String priorityTask) {
        this.priorityTask = priorityTask;
    }
}
