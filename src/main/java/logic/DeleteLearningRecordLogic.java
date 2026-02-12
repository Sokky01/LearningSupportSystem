
package logic;

import dao.LearningDeleteDao;
import model.DeleteLearningRecordBeans;

public class DeleteLearningRecordLogic {

    public int execute(DeleteLearningRecordBeans bean) {

        LearningDeleteDao dao = new LearningDeleteDao();
        return dao.resetLearningTime(bean);
    }
}
