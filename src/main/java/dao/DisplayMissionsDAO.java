package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.DisplayMissionsBeans;
import model.DisplayMissionsBeansList;

public class DisplayMissionsDAO extends DbConnect {
	
	// 最終ログインを取得するメソッド
	public Date lastLogin(int studentNo) {

		// 利用者マスタテーブルから最終ログイン日時を取得する
		String sql = "SELECT lastLogin FROM userMaster WHERE studentNo = ?";
		Date lastLogin = null;

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// ?にstudentNoをセットする
			pStmt.setInt(1, studentNo);
			
			// SQL実行
			try (ResultSet rs = pStmt.executeQuery()) {
				if (rs.next()) {
					// lastLogin（最終ログイン日時）を取得する
					lastLogin = rs.getDate("lastLogin");
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}

		// lastLoginを返す
		return lastLogin;

	}

	// デイリーミッションをランダムに生成するメソッド
	public DisplayMissionsBeansList mission1(int studentNo) {

		// デイリーミッションをランダムに3件(LIMIT 3)取得し、恒常ミッションを全て連結する
		// ?が２つあり、どちらも同一のstudentNoを設定する
		String sql = "(SELECT mm.MissionId, mm.MissionName, mm.MissionType, mm.RequiredValue, mm.SubjectId, mm.MissionPoint, CASE WHEN ma.MissionId IS NOT NULL THEN TRUE ELSE FALSE END AS Achieved, ma.AchievementDate AS AchievementDate FROM missionMaster mm LEFT JOIN missionAchievement ma ON mm.MissionId = ma.MissionId AND ma.StudentNo = ? WHERE mm.MissionType IN (0, 1) ORDER BY RAND() LIMIT 3) UNION ALL (SELECT mm.MissionId, mm.MissionName, mm.MissionType, mm.RequiredValue, mm.SubjectId, mm.MissionPoint, CASE WHEN ma.MissionId IS NOT NULL THEN TRUE ELSE FALSE END AS Achieved, ma.AchievementDate AS AchievementDate FROM missionMaster mm LEFT JOIN missionAchievement ma ON mm.MissionId = ma.MissionId AND ma.StudentNo = ? WHERE mm.MissionType = 2) ORDER BY MissionType, MissionId;";
		DisplayMissionsBeansList dmbl = new DisplayMissionsBeansList();

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// ?にstudentNoをセットする
			pStmt.setInt(1, studentNo);
			pStmt.setInt(2, studentNo);

			try (ResultSet rs = pStmt.executeQuery()) {

				// whileでミッションを全件取得する
				while (rs.next()) {

					// Beansのインスタンスを生成し、各情報をセットする
					DisplayMissionsBeans m = new DisplayMissionsBeans();
					m.setMissionId(rs.getInt("MissionId"));
					m.setMissionName(rs.getString("MissionName"));
					m.setMissionType(rs.getInt("MissionType"));
					m.setRequiredValue(rs.getInt("RequiredValue"));
					m.setSubjectId(rs.getInt("SubjectId"));
					m.setMissionPoint(rs.getInt("MissionPoint"));
					m.setAchieved(rs.getBoolean("Achieved"));
					m.setAchievementDate(rs.getDate("AchievementDate"));

					// Beansをリストに追加する
					dmbl.addMission(m);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}

		// リストを返す
		return dmbl;
	}

	// 日別ミッション割当テーブルを参照するメソッド
	public DisplayMissionsBeansList mission2(int studentNo) {

		// ユーザーのデイリーミッションを全て取得する
		String sql = "SELECT udm.StudentNo, udm.MissionId, udm.Achieved, mm.MissionName, mm.MissionType, mm.RequiredValue, mm.SubjectId, mm.MissionPoint, ma.AchievementDate FROM userDailyMissions udm JOIN missionMaster mm ON udm.MissionId = mm.MissionId LEFT JOIN missionAchievement ma ON ma.MissionId = udm.MissionId AND ma.StudentNo = udm.StudentNo WHERE udm.StudentNo = ? ORDER BY mm.MissionType, udm.MissionId";

		// リストのインスタンスを生成する
		DisplayMissionsBeansList dmbl = new DisplayMissionsBeansList();

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// ?にstudentNoをセットする
			pStmt.setInt(1, studentNo);

			// SQL実行
			try (ResultSet rs = pStmt.executeQuery()) {

				// whileでミッションを全件取得する
				while (rs.next()) {

					// Beansのインスタンスを生成し、各情報をセットする
					DisplayMissionsBeans m = new DisplayMissionsBeans();
					m.setMissionId(rs.getInt("MissionId"));
					m.setMissionName(rs.getString("MissionName"));
					m.setMissionType(rs.getInt("MissionType"));
					m.setRequiredValue(rs.getInt("RequiredValue"));
					m.setSubjectId(rs.getInt("SubjectId"));
					m.setMissionPoint(rs.getInt("MissionPoint"));
					m.setAchieved(rs.getBoolean("Achieved"));
					m.setAchievementDate(rs.getDate("AchievementDate"));

					// Beansをリストに追加する
					dmbl.addMission(m);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}

		// リストを返す
		return dmbl;
	}

	// 日別ミッション割当テーブルにミッションを上書きするメソッド
	public boolean mission3(int studentNo, int missionId, boolean achieved) {

		// 日別ミッション割当テーブルにミッションを上書きする
		String sql = "INSERT INTO userDailyMissions (StudentNo, MissionId, Achieved) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE Achieved = VALUES(Achieved)";

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			pStmt.setInt(1, studentNo);
			pStmt.setInt(2, missionId);
			pStmt.setBoolean(3, achieved);

			// SQL実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}

		return true;
	}

	// 日別ミッションテーブルの達成フラグを更新するメソッド
	public void mission4(int studentNo, int missionId, boolean achieved) {

		// 日別ミッション割当テーブルを更新する
		String sql = "UPDATE userDailyMissions SET achieved = ? WHERE studentNo = ? AND missionId = ?";

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// ?にstudentNo, missionId, achievedをセットする
			pStmt.setBoolean(1, achieved);
			pStmt.setInt(2, studentNo);
			pStmt.setInt(3, missionId);

			// UPDATE実行
			pStmt.executeUpdate();


		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}
	}

	// 日別ミッション割当テーブルを削除するメソッド
	public void mission5(int studentNo) {

		// ユーザーの日別ミッションのデータを削除する
		String sql = "DELETE FROM userDailyMissions WHERE StudentNo = ?";

		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {

			// ?にstudentNoをセットする
			pStmt.setInt(1, studentNo);

			// DELETE実行
			pStmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}
	}
	
	// ミッション達成テーブルに達成日を追加するメソッド
	public void mission6(int studentNo, int missionId, Date achievementDate) {
		
		// ミッション達成テーブルに達成日を追加する
		String sql = "INSERT INTO missionAchievement (StudentNo, MissionId, AchievementDate) VALUES (?, ?, ?)";
		
		// DB接続
		try (Connection conn = DbConnect.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(sql)) {
			
			// ?にstudentNo, missionId, achievementDateをセットする
			pStmt.setInt(1, studentNo);
			pStmt.setInt(2, missionId);
			pStmt.setDate(3, new java.sql.Date(achievementDate.getTime()));
			
			// INSERT実行
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
		}
	}
	
	// 利用者ポイント累計テーブルにミッションポイントを加算するメソッド
	public void mission7(int studentNo, int missionPoint) {

		String sql = "INSERT INTO userPointTotal (StudentNo, PointTotal) VALUES (?, ?) ON DUPLICATE KEY UPDATE PointTotal = PointTotal + VALUES(PointTotal)";
		
	    try (Connection conn = DbConnect.getConnection();
	         PreparedStatement pStmt = conn.prepareStatement(sql)) {

	        pStmt.setInt(1, studentNo);
	        pStmt.setInt(2, missionPoint);

	        pStmt.executeUpdate();

	    } catch (SQLException e) {
	        throw new RuntimeException("DBアクセス中にエラーが発生しました", e);
	    }
	}
	
	// 
}
