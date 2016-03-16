package com.example.matteo.prova_2;

import com.example.matteo.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 07/03/2016.
 */
public class FakeTeam {

    List<Team> fakeList = null;

    public FakeTeam() {

        Team team1 = new Team("milan",87 );
        Team team2 = new Team("inter", 75);
        Team team3 = new Team("juventus", 40);

        fakeList = new ArrayList<Team>();
        fakeList.add(team1);
        fakeList.add(team2);
        fakeList.add(team3);
    }

    public List<Team> getFakeList() {
        return fakeList;
    }

    public void setFakeList(List<Team> fakeList) {
        this.fakeList = fakeList;
    }
}
