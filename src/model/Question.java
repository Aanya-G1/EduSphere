package model;

public class Question {
    private int question_id;
    private int quiz_id;
    private String question;
    private String option1, option2, option3, option4;
    private int correct_answer;

    public Question() {}

    public int getQuestionId() { return question_id; }
    public void setQuestionId(int id) { this.question_id = id; }

    public int getQuizId() { return quiz_id; }
    public void setQuizId(int id) { this.quiz_id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String q) { this.question = q; }

    public String getOption1() { return option1; }
    public void setOption1(String o) { this.option1 = o; }

    public String getOption2() { return option2; }
    public void setOption2(String o) { this.option2 = o; }

    public String getOption3() { return option3; }
    public void setOption3(String o) { this.option3 = o; }

    public String getOption4() { return option4; }
    public void setOption4(String o) { this.option4 = o; }

    public int getCorrectAnswer() { return correct_answer; }
    public void setCorrectAnswer(int a) { this.correct_answer = a; }
}
