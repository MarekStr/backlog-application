package pl.marek.backend;

public enum PriorityOfTask {

    // ograniczenie dla dlugosci 20 znakow z bazy danych

    very_most_important("Very most important"),
    most_important("Most important"),
    important("Important"),
    less_important("Less important"),
    not_important("Not important"),
    dont_do_it("Don't do it");

    private final String fullName;

    PriorityOfTask(String fullName) {
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
