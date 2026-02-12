package logic;

import dao.UserUpdateInfoDAO;
import model.userAccountListBeans;

public class UserUpdateLogic {

    public boolean execute(userAccountListBeans beans,int originalStudentNo) {

        // ① DAO呼び出し
        UserUpdateInfoDAO dao = new UserUpdateInfoDAO();

        // ② DAOにBeansを渡す
        boolean result = dao.profileInfoAdd(beans,originalStudentNo);

        // ③ True / False を Servlet に返す
        return result;
    }
    
    
    public userAccountListBeans execute2(int studentNo) {
 
    	UserUpdateInfoDAO dao = new UserUpdateInfoDAO();
    	 return dao.findByStudentNo(studentNo);
    	

    }
}


/*
public class UserUpdateLogic {
	
	 public List<userAccountUpdateBeans> execute(){//リスト取得

		    //１.UserViewInfoDAOを呼び出す
		 UserUpdateInfoDAO userDao = new UserUpdateInfoDAO();

		    //２.userAccountListBeansを受け取る
		    List<userAccountUpdateBeans> ListUserBeans = userDao.findAll();

		    
		    //３.userAccountListBeansをUserViewServletに返す
		   
		    return ListUserBeans;

		    }
	 public boolean execute2(userAccountUpdateBeans user){//リスト取得

		    //１.UserViewInfoDAOを呼び出す
		 UserUpdateInfoDAO userDao = new UserUpdateInfoDAO();

		    //２.userAccountListBeansを受け取る
		    List<userAccountUpdateBeans> ListUserBeans = userDao.findAll();
		    
		    for(userAccountUpdateBeans update : ListUserBeans) {
		    	//userDaoリストのstudentNoと入力されたstudentNoと比較
		    	if(user.getStudentNo()==update.getStudentNo()) {
		    		return true;
		    	}
		    	 
		    }
		    return false;
	 }

}*/
