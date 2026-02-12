package logic;

import java.util.List;

import dao.GraphDisplayDAO;
import model.DisplayStudyTimeGraphBeans;

public class DisplayStudyTimeGraphLogic {
	public List<DisplayStudyTimeGraphBeans> execute(int studentNo) {
		GraphDisplayDAO GDD = new GraphDisplayDAO();
		return GDD.learningHistory(studentNo);
	}
}
