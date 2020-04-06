package com.example.avalon.service;

import com.example.avalon.domain.Mission;

public class MissionHandler {

  public static int[][] missionMatrix = {{2,3,2,3,3}, {2,3,4,3,4}, {2,3,3,4,4}, {3,4,4,5,5}, {3,4,4,5,5}, {3,4,4,5,5}};

  public static Mission setNumberOfVotes(Mission mission, int numberOfPlayers, int missionID) {
      int row = numberOfPlayers-5;
      int column = missionID-1;
      mission.setTotalNumberOfVotes(missionMatrix[row][column]);
      if (column == 3 && row>1)
          mission.setMinNegativeVotes(2);
      return mission;
  }
}
