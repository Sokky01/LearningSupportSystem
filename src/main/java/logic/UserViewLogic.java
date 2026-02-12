package logic;
import java.util.List;

import dao.UserViewInfoDAO;
import model.userAccountListBeans;

public class UserViewLogic{
    public List<userAccountListBeans> execute(){//リスト取得

    //１.UserViewInfoDAOを呼び出す
    UserViewInfoDAO userDao = new UserViewInfoDAO();

    //２.userAccountListBeansを受け取る
    List<userAccountListBeans> ListUserBeans = userDao.findAll();

    //３.userAccountListBeansをUserViewServletに返す
    return ListUserBeans;

    }
    //検索できればやる
    public List<userAccountListBeans> execute2(String schoolGradeName,int StudentNo1) {
        
        // DAOを呼び出す
    	UserViewInfoDAO dao = new UserViewInfoDAO();
        List<userAccountListBeans> resultList = dao.execute2( schoolGradeName,StudentNo1);
        
        // DAOから返ってきたBeansをServletに渡す
        return resultList;
    }
}