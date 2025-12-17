package lrs.lrs0701.logic;

import java.util.List;

import lrs.lrs0701.dao.LearningstarttransitionDAO;
import lrs.lrs0701.model.LearningstarttransitionBeans;

public class LearningstarttransitionLogic {
	public List<LearningstarttransitionBeans> execute() {
		LearningstarttransitionDAO LSD = new LearningstarttransitionDAO();
		return LSD.subjectsNameAcquisition();
	}
}
