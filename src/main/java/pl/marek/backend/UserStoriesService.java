package pl.marek.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStoriesService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   // wyszukiwanie w tabeli User stories : tasks
    public List<UserStories> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, user_story, priority FROM tasks",
                    (rs, rowNum) -> new UserStories(rs.getLong("id"), rs.getString("user_story")
                            ,rs.getString("priority")   ));
        } catch (Exception e) {
            return new ArrayList<UserStories>();
        }

    }
    // dodawanie nowego zadania
    public int addUserStory(UserStories userStories) {
        try {
            return jdbcTemplate.update("INSERT INTO tasks (`user_story`, `priority`) VALUES (?,?) ",
                    userStories.getStory(), userStories.getPriorityTask());
        } catch (Exception e) {
            return 0;
        }
    }
    // update w pierwszej tabeli
  public int updateUserStoryInDataBase(UserStories userStories) {
        try {
            return jdbcTemplate.update("UPDATE tasks SET user_story = ?, priority = ? WHERE id = ?",
                    userStories.getStory(), userStories.getPriorityTask(), userStories.getId());
        } catch (Exception e) {
            return 0;
        }
    }
    // kasowanie zadania z pierwszej tabeli
    public int removeUserStory(UserStories userStories){
        try {
            return jdbcTemplate.update("DELETE FROM tasks WHERE id=?",
                    userStories.getId());
                    }catch (Exception e){
            return 0;
        }
    }
    // dodawanie zadania z pierwszej tabeli do drugiej tabeli
    public int addUserStoryToToDoGrid(UserStories userStories) {
        try {
            return jdbcTemplate.update("INSERT INTO to_do (`user_story`, `priority`) VALUES (?,?) ",
                    userStories.getStory(), userStories.getPriorityTask());
        } catch (Exception e) {
            return 0;
        }
    }

    public int addUserStoryToDoneGrid(UserStories userStories) {
        try {
            return jdbcTemplate.update("INSERT INTO done(`user_story`) VALUES (?)",
                    userStories.getStory());
        }catch (Exception e){
            return 0;
        }
    }
}