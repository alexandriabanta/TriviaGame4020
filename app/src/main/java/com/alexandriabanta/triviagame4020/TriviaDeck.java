package com.alexandriabanta.triviagame4020;

public class TriviaDeck {

    private int numOfQuestions;
    private String category;

    private Questions questionsDeck[] = new Questions[numOfQuestions];

    //each question has 4 answers
    public class Questions {

        private String questionTitle;
        private String correctAnswer;
        private String incorrectAnswer1, incorrectAnswer2, incorrectAnswer3;

        public void setQuestion(String questionTitle, String ca, String a1, String a2, String a3) {
            this.questionTitle = questionTitle;
            correctAnswer = ca;
            incorrectAnswer1 = a1;
            incorrectAnswer2 = a2;
            incorrectAnswer3 = a3;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }
    }
}
