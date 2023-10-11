package uz.jasurbekruzimov.randomapp;
public class Game {
    private boolean isYes; // O'yin natijasi (true - Ha, false - Yo'q)

    public Game(boolean isYes) {
        this.isYes = isYes;
    }

    public boolean isYes() {
        return isYes;
    }

    public void setYes(boolean isYes) {
        this.isYes = isYes;
    }

    private boolean[] combination;

    public Game(boolean[] combination) {
        this.combination = combination;
    }

    public boolean[] getCombination() {
        return combination;
    }
}
