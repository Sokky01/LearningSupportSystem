package logic;

import java.util.List;

import dao.TargetSettingDao;
import model.targetSettingBeans;

public class SetGoalLogic {

    /**
     * 目標時間更新
     */
    public void setGoal(int studentNo, int subjectId, int subjectGoal) {
        TargetSettingDao dao = new TargetSettingDao();
        dao.updateGoal(studentNo, subjectId, subjectGoal);
    }

    /**
     * 科目一覧取得（目標設定画面用）
     */
    public List<targetSettingBeans> getTargetSettingList(int studentNo) {
        TargetSettingDao dao = new TargetSettingDao();
        return dao.getTargetSettingList(studentNo);
    }
}
