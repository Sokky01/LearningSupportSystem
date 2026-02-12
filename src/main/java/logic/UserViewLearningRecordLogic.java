package logic;

import java.util.List;

import dao.UserLearningViewDao;
import model.userLearningRecordBeans;


public class UserViewLearningRecordLogic {

// 呼び出されたらexecuteメソッドが実行される
 public List<userLearningRecordBeans> execute(int studentNo) {
     
     // DAOを呼び出す
     UserLearningViewDao dao = new UserLearningViewDao();
    
     // DAOから返ってきたBeansをServletに渡す
     return dao.getUserLearningRecord(studentNo);
 }
}
