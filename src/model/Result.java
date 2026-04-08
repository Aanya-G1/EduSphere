package model;

public class Result {
    private int result_id;
    private int user_id;
    private int quiz_id;
    private int score;

    public Result() {}

    public int getResultId() { return result_id; }
    public void setResultId(int id) { this.result_id = id; }

    public int getUserId() { return user_id; }
    public void setUserId(int id) { this.user_id = id; }

    public int getQuizId() { return quiz_id; }
    public void setQuizId(int id) { this.quiz_id = id; }

    public int getScore() { return score; }
    public void setScore(int s) { this.score = s; }
}
