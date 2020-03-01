package pl.marek.backend;

import java.io.Serializable;

public class UserStories implements Serializable, Cloneable {

    private Long id;
    private String story;
    private String priorityTask;


    public UserStories() {
    }

    public UserStories(Long id, String story, String priorityTask) {
        this.id = id;
        this.story = story;
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

    public String getPriorityTask() {
        return priorityTask;
    }

    public void setPriorityTask(String priorityTask) {
        this.priorityTask = priorityTask;
    }
}
