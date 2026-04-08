package model;

import java.time.LocalDateTime;
import java.util.List;

public class Quiz {
    private int quiz_id;
    private String title;
    private int created_by;
    private LocalDateTime created_at;
    private List<Question> questions;

    public Quiz() {}

    public int getQuizId() { return quiz_id; }
    public void setQuizId(int quiz_id) { this.quiz_id = quiz_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCreatedBy() { return created_by; }
    public void setCreatedBy(int created_by) { this.created_by = created_by; }

    public LocalDateTime getCreatedAt() { return created_at; }
    public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }

    public List<Question> getQuestions() { return questions; }
    public void setQuestions(List<Question> questions) { this.questions = questions; }
}
