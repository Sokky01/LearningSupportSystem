package ams.ams0101.logic;

import java.util.Date;

import ams.ams0101.dao.AdminLoginDao;

public class AdminMissonLogic {
	public static  void Mission(int studentNo) { 
		// 現在の日付を取得
				Date toDay = new Date();		
    
	//現在日時と前のログインを比較する
    DisplayMissionsLogic.missionMain(studentNo);
    
    //現在の日時に更新する
	AdminLoginDao dao = new AdminLoginDao();
    dao.LastLogin(studentNo,toDay);
    
	}

}
