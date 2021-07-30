package com.dormakaba.ekit.core.productconfig.container.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MontyHallSimulation {

    public static void main(String[] args) {
        long iterations = 10000000;

        List<Boolean> resultsWhenSwitching = new ArrayList<>();
        for(int i = 0; i < iterations; i++) {
            resultsWhenSwitching.add(runIteration(true));
        }
        List<Boolean> resultsWhenNotSwitching = new ArrayList<>();
        for(int i = 0; i < iterations; i++) {
            resultsWhenNotSwitching.add(runIteration(false));
        }
        long chanceToWinSwitching = resultsWhenSwitching.stream().filter(result -> result).count() / iterations;
        long chanceToWinNotSwitching = resultsWhenNotSwitching.stream().filter(result -> result).count() / iterations;
        System.out.printf("Chance to win when not switching doors %d%n", chanceToWinNotSwitching);
        System.out.printf("Chance to win when switching doors %d%n", chanceToWinSwitching);
    }

    private static boolean runIteration(boolean switchDoor) {
        boolean[] doors = new boolean[] {false, false, false};
        doors[new Random().nextInt(3)] = true;

        int chosenDoor = new Random().nextInt(2);
        List<Integer> possibleDoorsToOpen = new ArrayList<>();
        for (int i = 0; i < doors.length; i++) {
            if(!doors[i] && i != chosenDoor) {
                possibleDoorsToOpen.add(i);
            }
        }
        int doorOpened = possibleDoorsToOpen.get(new Random().nextInt(possibleDoorsToOpen.size()));

        List<Integer> remainingDoors = Stream.of(0, 1, 2).filter(door -> doorOpened != door).collect(Collectors.toList());

        if (switchDoor) {
            int finalChosenDoor = chosenDoor;
            chosenDoor = remainingDoors.stream().filter(door -> door != finalChosenDoor).findFirst().get();
        }
        return doors[chosenDoor];
    }
}
