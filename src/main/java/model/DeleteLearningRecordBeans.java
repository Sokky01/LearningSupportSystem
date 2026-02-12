package model;

public class DeleteLearningRecordBeans {
	private int studentNo;
	private int subjectId;
	
	public DeleteLearningRecordBeans(int StudentNo, int SubjectId) {
		this.studentNo = StudentNo;				
		this.subjectId = SubjectId;
	}
	public int getStudentNo() {return studentNo;}
	public int getSubjectId() {return subjectId;}
}
