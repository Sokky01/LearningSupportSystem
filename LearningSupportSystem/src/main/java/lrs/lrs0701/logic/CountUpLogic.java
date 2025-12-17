package lrs.lrs0701.logic;

import lrs.lrs0701.dao.CountUpDAO;

public class CountUpLogic {
	public boolean execute(int learningTime,int userId,int subjectId) {
		CountUpDAO CUD = new CountUpDAO();
		return CUD.learningRecord(learningTime,userId,subjectId);
	}
}
