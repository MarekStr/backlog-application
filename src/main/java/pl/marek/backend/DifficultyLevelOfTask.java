package pl.marek.backend;

public enum DifficultyLevelOfTask {

// ograniczenie dla dlugosci 20 znakow z bazy danych

    very_hard("Very Hard"),
    hard("Hard"),
    normal("Normal"),
    easy("Easy"),
    very_easy("Very easy"),
    for_kids("Easy For Kids");

    private final String fullName;

    DifficultyLevelOfTask(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }
}
