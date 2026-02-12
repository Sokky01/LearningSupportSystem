package logic;

import dao.GradeUpdateDAO;

public class GradeUpdateLogic {

    /**
     * ミッションポイント累計に基づいてグレードを更新する
     * MaxGrade（達成済み最高グレード）とSettingGradeId（表示用グレード）の両方を更新
     * 
     * @param studentNo 学籍番号
     * @return 更新が行われた場合true、更新不要の場合false
     */
    public boolean updateGradeByPoints(int studentNo) {
        GradeUpdateDAO dao = new GradeUpdateDAO();
        
        // 1. 現在のポイント累計を取得
        int pointTotal = dao.getPointTotal(studentNo);
        
        // 2. 現在のSettingGradeIdを取得
        int currentSettingGradeId = dao.getCurrentSettingGradeId(studentNo);
        
        // 3. ポイント累計から新しいグレードIDを判定
        int newGradeId = determineGradeId(pointTotal);
        
        // 4. 現在の表示グレードより上位の場合のみ更新
        if (newGradeId > currentSettingGradeId) {
            int updatedRows = dao.updateGrade(studentNo, newGradeId);
            return updatedRows > 0;
        }
        
        return false; // 更新不要
    }

    /**
     * ポイント累計からグレードIDを判定する
     * @param pointTotal ポイント累計
     * @return グレードID (1=ブロンズ, 2=シルバー, 3=ゴールド, 4=プラチナ, 5=ダイヤモンド)
     */
    private int determineGradeId(int pointTotal) {
        // ★ポイント基準値はここで調整してください
        if (pointTotal >= 2000) {
            return 5; // ダイヤモンド
        } else if (pointTotal >= 1500) {
            return 4; // プラチナ
        } else if (pointTotal >= 20) {
            return 3; // ゴールド
        } else if (pointTotal >= 10) {
            return 2; // シルバー
        } else {
            return 1; // ブロンズ
        }
    }
}