package logic;

import dao.CountUpDAO;
import model.DisplayMissionsBeans;
import model.DisplayMissionsBeansList;

public class CountUpLogic {
	public boolean execute(int learningTime,int userId,int subjectId) {
		CountUpDAO CUD = new CountUpDAO();
		boolean result = CUD.learningRecord(learningTime,userId,subjectId);
		
		if(result) {
			checkMission(userId, subjectId);
		}
		
		return result;
	}
	
	public void checkMission(int studentNo, int SubjectId) {
	    
		// DAOで時間を取得
	    CountUpDAO dao = new CountUpDAO();
	    int totalSubjectTime = dao.getTodayTotalTimeBySubject(studentNo, SubjectId);

	    // ミッションリストを取得
	    DisplayMissionsBeansList missionList = DisplayMissionsLogic.mission2(studentNo);

	    for (DisplayMissionsBeans m : missionList.getMissions()) {
	        if (m.isAchieved()) continue; // 達成済みはスルー

	        // 科目が一致しないミッションは無視する
	        if (m.getSubjectId() != SubjectId) continue;

	            // 分を秒に換算して比較
	            if (totalSubjectTime >= m.getRequiredValue() * 60) {
	                // 達成処理
	                DisplayMissionsLogic.mission4(studentNo, m.getMissionId(), true, m.getMissionPoint());
	        }
	    }
	}
	
}
