package logic;

import java.util.List;

import dao.LearningstarttransitionDAO;
import model.LearningstarttransitionBeans;

public class LearningstarttransitionLogic {
	public List<LearningstarttransitionBeans> execute() {
		LearningstarttransitionDAO LSD = new LearningstarttransitionDAO();
		return LSD.subjectsNameAcquisition();
	}
}
