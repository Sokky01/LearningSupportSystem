package logic;

import java.text.SimpleDateFormat;
import java.util.Date;

import dao.DisplayMissionsDAO;
import model.DisplayMissionsBeans;
import model.DisplayMissionsBeansList;

public class DisplayMissionsLogic {
	
	// DAOのインスタンス生成（各メソッドで再利用するため）
	private static final DisplayMissionsDAO dao = new DisplayMissionsDAO();
	
	// 日付判定を行うメソッド
	public static DisplayMissionsBeansList missionMain(int studentNo) {
		
		// 学籍番号を引数に最終ログインを取得（nullの可能性あり）
		Date lastLogin = lastLogin(studentNo);
		// 現在の日付を取得
		Date toDay = new Date();		
		// yyyy-MM-ddに整形
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		// 最終ログインと現在の日付を比較
		// lastLoginがnullでない かつ lastLoginとtoDayの値が一致する場合・・・
		if(lastLogin != null && sdf.format(lastLogin).equals(sdf.format(toDay))) {
			
			// 同じ日（日別ミッション割当テーブルを参照する）
			// ロジックを呼び出し、servletへ返す
			return mission2(studentNo);
			
		} else {
			
			// 別の日（新しくミッションを生成し、日別ミッション割当テーブルに保存する）
			// 古いデイリーミッションを削除する
			mission5(studentNo);
			
			// デイリーミッションのリストを生成する
			DisplayMissionsBeansList dmbl = mission1(studentNo);
			
			// 拡張for文でミッションを1件ずつ日別ミッション割当テーブルに保存する
			for (DisplayMissionsBeans m : dmbl.getMissions()) {
				mission3(studentNo, m.getMissionId(), m.isAchieved());
			}
			return dmbl;
	
		}
	}
	
	// 最終ログインを取得するメソッド
	public static Date lastLogin(int studentNo) {
		
		// 学籍番号を引数に、DAOのlastLoginメソッドを呼び出す
		// 最終ログイン日時を返す
		return dao.lastLogin(studentNo);
		
	}

	// ミッションとミッション達成を取得するメソッド（デイリーミッションを更新）
	public static DisplayMissionsBeansList mission1(int studentNo) {

		// 学籍番号を引数に、DAOのmission1メソッドを呼び出す
		// Beansを返す
		return dao.mission1(studentNo);
		
	}

	// 日別ミッションとミッション達成を取得するメソッド（デイリーミッションを取得）
	public static DisplayMissionsBeansList mission2(int studentNo) {

		// 学籍番号を引数に、DAOのmission2メソッドを呼び出す
		// Beansを返す
		return dao.mission2(studentNo);
		
	}

	// 日別ミッション割り当てテーブルにミッションを上書きするメソッド
	public static boolean mission3(int studentNo, int missionId, boolean achieved) {

		// 学籍番号を引数に、DAOのmission3メソッドを呼び出す
		// true or falseを返す
		return dao.mission3(studentNo, missionId, achieved);

	}

	// ミッション達成に関するメソッド
	public static void mission4(int studentNo, int missionId, boolean achieved, int missionPoint) {

		// 日別ミッション割り当てテーブルのミッションを達成済みに更新する
		// 学籍番号を引数に、DAOのmission4メソッドを呼び出す
		dao.mission4(studentNo, missionId, achieved);
		
		// ミッション達成テーブルに達成日を追加する処理
		// 現在の日付を取得
		Date toDay = new Date();
		// 学籍番号を引数に、DAOのmission6メソッドを呼び出す
		dao.mission6(studentNo, missionId, toDay);
		
		// ミッションポイント加算の処理
		dao.mission7(studentNo, missionPoint);
		
	}
	
	// 日別ミッション割り当てテーブルのミッションを削除するメソッド
	public static void mission5(int studentNo) {

		// 学籍番号を引数に、DAOのmission5メソッドを呼び出す
		dao.mission5(studentNo);

	}
	
}
