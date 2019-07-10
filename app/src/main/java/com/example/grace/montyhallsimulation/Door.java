package com.example.grace.montyhallsimulation;

public class Door {
    char doorLetter;
    boolean isPrizeDoor = false;
    private boolean isSelected = false;

    public Door(char doorLetter) {
        this.doorLetter = doorLetter;
    }

    public void makePrizeDoor() {
        isPrizeDoor = true;
    }

    public void selectDoor() {
        isSelected = true;
    }

    public boolean isUserSelected() {
        return isSelected;
    }
}
