package model;

import java.util.ArrayList;
import java.util.List;

// DisplayMissionsBeansのリストをもつラッパークラス（リストとして扱うために使用）
public class DisplayMissionsBeansList {
	private List<DisplayMissionsBeans> missions = new ArrayList<>();
	
	// getter/setter
    public List<DisplayMissionsBeans> getMissions() { return missions; }
    public void addMission(DisplayMissionsBeans mission) { this.missions.add(mission); }
}
