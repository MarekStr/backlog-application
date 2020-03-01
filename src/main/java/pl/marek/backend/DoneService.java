package pl.marek.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoneService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // wyszukiwanie w tabeli Done
    public List<ToDoTask> findAllDone() {
        try {
            return jdbcTemplate.query("SELECT id, user_story, task_details, finish_date,difficulty FROM done",
                    (rs, rowNum) -> new ToDoTask(rs.getLong("id"), rs.getString("user_story")
                            ,rs.getString("task_details"), rs.getDate("finish_date")
                            ,rs.getString("difficulty")));
        } catch (Exception e) {
            return new ArrayList<ToDoTask>();
        }
    }
}


//    CREATE TABLE done (
//        id INT UNSIGNED NOT NULL AUTO_INCREMENT,
//        user_story TEXT,
//        task_details TEXT,
//        finish_date DATE,
//        difficulty VARCHAR(20),
//    PRIMARY KEY  (id)
//	);