package pl.marek.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InProgressService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // wyszukiwanie w tabeli in progress
    public List<InProgress> findAllInProgress() {
        try {
            return jdbcTemplate.query("SELECT id, task_details, finish_date,difficulty,priority FROM in_progress",
                    (rs, rowNum) -> new InProgress(rs.getLong("id"), rs.getString("task_details")
                            ,rs.getDate("finish_date"), rs.getString("difficulty"),
                            rs.getString("priority")   ));
        } catch (Exception e) {
            return new ArrayList<InProgress>();
        }
    }
    // dodawanie zadania z tabeli in progress do tabeli done

    public int addTaskToDoneGrid(InProgress inProgress) {
        try {
            return jdbcTemplate.update("INSERT INTO done (`task_details`, `finish_date`, `difficulty`) VALUES (?,?,?) ",
                    inProgress.getTaskDetails(), inProgress.getFinishDate(),inProgress.getDifficultyLevel());
        } catch (Exception e) {
            return 0;
        }
    }

    // kasowanie zadania z tabeli in progerss
    public int removeFromInProgress(InProgress inProgress){
        try {
            return jdbcTemplate.update("DELETE FROM in_progress WHERE id=?",
                    inProgress.getId());
        }catch (Exception e){
            return 0;
        }
    }

//    baza danych
//    in_progress (
//            id INT UNSIGNED NOT NULL AUTO_INCREMENT,
//            task_details TEXT,
//            finish_date DATE,
//            difficulty VARCHAR(15),
//    priority
    // id 	user_story 	task_details 	finish_date 	difficulty
}
