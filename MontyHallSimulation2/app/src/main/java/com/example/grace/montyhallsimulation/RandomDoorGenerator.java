package com.example.grace.montyhallsimulation;

import java.util.ArrayList;
import java.util.Random;

public class RandomDoorGenerator {
    ArrayList<Door> doors;
    private Door prizeDoor;

    public RandomDoorGenerator() {
        Random rand = new Random();

        //generate random value between 0 and 1.
        double randomPrizeDoor = rand.nextDouble();

        doors = new ArrayList<>();
        doors.add(new Door('A'));
        doors.add(new Door('B'));
        doors.add(new Door('C'));

        if (randomPrizeDoor <= .33) { // a will be the prize door
            doors.get(0).makePrizeDoor();
            prizeDoor = doors.get(0);
        } else if (randomPrizeDoor <= .66) { // b will be the prize door
            doors.get(1).makePrizeDoor();
            prizeDoor = doors.get(1);
        } else { // c will be the prize door
            doors.get(2).makePrizeDoor();
            prizeDoor = doors.get(2);
        }

        //set the prize door to be last in the array list.
        doors.remove(prizeDoor);
        doors.add(prizeDoor);
    }

    public Door getPrizeDoor() {
        return prizeDoor;
    }

    public Door get(char letter) {
        int i;
        for (i = 0; i < 2; i++) {
            if (doors.get(i).doorLetter == letter) {
                break;
            }
        }
        return doors.get(i);
    }

}
