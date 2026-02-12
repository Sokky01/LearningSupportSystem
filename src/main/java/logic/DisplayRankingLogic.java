package logic;

import java.util.List;

import dao.RankingDAO;
import model.RankingBeans;

public class DisplayRankingLogic {
	
	// DAOのインスタンス生成（各メソッドで再利用するため）
	private static final RankingDAO dao = new RankingDAO();

	// ランキングのDAOを呼び出すメソッド
	public static List<RankingBeans> rankingMain() {
		
		// RankingDAOのrankingbeansメソッドを呼び出す
		return dao.rankingbeans();
		
	}
		
}
