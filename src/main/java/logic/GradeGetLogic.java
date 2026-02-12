package logic;

import dao.GradeGetDAO;

public class GradeGetLogic {
	/**
	 * ユーザーのSettingGradeIdを取得する
	 * @param userId ユーザーID
	 * @return SettingGradeId (1=ブロンズ, 2=シルバー, 3=ゴールド, 4=プラチナ, 5=ダイヤモンド)
	 */
	public int execute(int userId) {
		GradeGetDAO GGD = new GradeGetDAO();
		return GGD.gradeGet(userId);
	}

}