package pl.marek.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToDoTaskService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // wyszukiwanie w tabeli to_do
    public List<ToDoTask> findAllToDo() {
        try {
            return jdbcTemplate.query("SELECT id, user_story, task_details, finish_date,difficulty,priority FROM to_do",
                    (rs, rowNum) -> new ToDoTask(rs.getLong("id"), rs.getString("user_story")
                            ,rs.getString("task_details"),rs.getDate("finish_date")
                            ,rs.getString("difficulty"),rs.getString("priority")   ));
        } catch (Exception e) {
            return new ArrayList<ToDoTask>();
        }
    }
    // update w drugiej tabeli
    public int updateToDoInDataBase(ToDoTask toDoTask) {
        try {
            return jdbcTemplate.update("UPDATE to_do SET user_story = ?, task_details = ?, finish_date = ? , difficulty = ?, priority = ? WHERE id = ?",
                    toDoTask.getStory(), toDoTask.getTaskDetails(), toDoTask.getFinishDate(),toDoTask.getDifficultyLevel(), toDoTask.getPriorityTask(), toDoTask.getId());
        } catch (Exception e) {
            return 0;
        }
    }
    // kasowanie zadania z drugiej tabeli
    public int removeToDoTask(ToDoTask toDoTask){
        try {
            return jdbcTemplate.update("DELETE FROM to_do WHERE id=?",
                    toDoTask.getId());
        }catch (Exception e){
            return 0;
        }
    }
    // dodawanie zadania z tabeli to_do do tabeli in progress
    public int addTaskToInProgressGrid(ToDoTask toDoTask) {
        try {
            return jdbcTemplate.update("INSERT INTO in_progress (`task_details`, `finish_date`, `difficulty`, `priority`) VALUES (?,?,?,?) ",
                    toDoTask.getTaskDetails(), toDoTask.getFinishDate(),toDoTask.getDifficultyLevel(), toDoTask.getPriorityTask());
        } catch (Exception e) {
            return 0;
        }
    }

//    baza danych
//    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
//    user_story TEXT,
//    task_details TEXT,
//    finish_date DATE,
//    difficulty VARCHAR(15),
//    priority
}
