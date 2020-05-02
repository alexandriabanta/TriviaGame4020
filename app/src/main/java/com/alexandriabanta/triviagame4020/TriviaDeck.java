package com.alexandriabanta.triviagame4020;

public class TriviaDeck {

    public int numOfQuestions = 10;
    public String category;

    public Question questionsDeck[];

    public TriviaDeck() {
        numOfQuestions = 0;
    }

    public TriviaDeck(int arraySize) {
        numOfQuestions = arraySize;
        questionsDeck = new Question[numOfQuestions];

        //go through and initialize each question in array
        for (int i = 0; i < numOfQuestions; i++) {
            questionsDeck[i] = new Question();
        }
    }

    //each question has 4 answers
    public class Question {

        public String questionTitle = "";
        public String correctAnswer = "";
        public String incorrectAnswer1 = "", incorrectAnswer2 = "", incorrectAnswer3 = "";

        public void setQuestion(String questionTitle, String ca, String a1, String a2, String a3) {
            this.questionTitle = questionTitle;
            this.correctAnswer = ca;
            this.incorrectAnswer1 = a1;
            this.incorrectAnswer2 = a2;
            this.incorrectAnswer3 = a3;
        }
    }
}
