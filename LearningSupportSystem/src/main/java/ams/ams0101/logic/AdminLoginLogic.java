package ams.ams0101.logic;

import ams.ams0101.dao.AdminLoginDao;
import ams.ams0101.model.beans.AdminLoginBeans;
public class AdminLoginLogic {
	
	 public AdminLoginBeans findAdminById(int AccountId) {
	        // ここでDBアクセスを行う想定
	    	//
		 //int AccountId =Integer.parseInt("AccountId");
	    	AdminLoginDao dao = new AdminLoginDao();
	    	AdminLoginBeans bean = dao.loginInfoAdd(AccountId);//IDのみ送りたい
	    	
	        return bean; 	
	  }
	 
	 
	/* public AdminLoginBeans findAdminById2(int AccountId) {
	        // ここでDBアクセスを行う想定
	    	//
		 //int AccountId =Integer.parseInt("AccountId");
	    	AdminLoginDao dao = new AdminLoginDao();
	    	AdminLoginBeans id = dao.loginInfoAdd(AccountId);//IDのみ送りたい
	    	
	        return id; 	
	  }*/
}
