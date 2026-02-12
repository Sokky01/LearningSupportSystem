package logic;
import java.util.List;

import dao.AdminViewInfoDao;
import model.AdminAccountListBeans;
public class AdminViewLogic {
	public List<AdminAccountListBeans> execute() {//リスト取得
		
		
		//1．	AdminViewinfoDAOを呼び出す
		AdminViewInfoDao Viewinfodao = new AdminViewInfoDao();
		
		List<AdminAccountListBeans> ListBeans = Viewinfodao.findAll();
		
		//2．	AdminViewServletへadminAccountListBeansを送る
		return ListBeans;
	}

}
