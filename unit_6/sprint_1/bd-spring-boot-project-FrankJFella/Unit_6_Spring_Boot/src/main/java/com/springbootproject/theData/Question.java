package com.springbootproject.theData;

// This class will be used to send data to a server

import java.util.Objects;

public class Question {
    String theQuestion;  // if used with a Server app, the JSON attribute must match the name

    public Question() {}

    public Question(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return Objects.equals(getTheQuestion(), question.getTheQuestion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTheQuestion());
    }

    @Override
    public String toString() {
        return "Question{" +
                "theQuestion='" + theQuestion + '\'' +
                '}';
    }
}
