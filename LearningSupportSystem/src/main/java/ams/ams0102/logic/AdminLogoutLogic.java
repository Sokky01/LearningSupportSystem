package ams.ams0102.logic;

import jakarta.servlet.http.HttpSession;

public class AdminLogoutLogic {
	public void Logout(HttpSession session){
		//セッション破棄
			session.invalidate();
			
		}

}
