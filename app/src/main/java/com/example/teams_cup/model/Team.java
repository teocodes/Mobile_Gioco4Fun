package com.example.teams_cup.model;

/**
 * Created by teams_cup on 07/03/2016.
 */
public class Team {
    private String name;
    private int points;

    public Team(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return name + " ---> " + points;
    }
}
