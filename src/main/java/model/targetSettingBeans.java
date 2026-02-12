package model;

public class targetSettingBeans {

	private int studentNo;
	private int subjectId;
	private String subjectName;
	private int targetTime;
	private int subjectTotal;
	private int studyGoalTotal;
	private int studyTimeTotal;
	
	public targetSettingBeans(int StudentNo, int SubjectId, String SubjectName, int TargetTime, int SubjectTotal,int StudyGoalTotal, int StudyTimeTotal ) {
		this.studentNo = StudentNo;
		this.subjectId = SubjectId;
		this.subjectName = SubjectName;
		this.targetTime = TargetTime;
		this.subjectTotal = SubjectTotal;
		this.studyGoalTotal = StudyGoalTotal;
		this.studyTimeTotal = StudyTimeTotal;
	}
	public int getStudentNo() {return studentNo;}
	public int getSubjectId() {return subjectId;}
	public String getSubjectName() {return subjectName;}
	public int getTargetTime() {return targetTime;}
	public int getSubjectTotal() {return subjectTotal;}
	public int getStudyGoalTotal() {return studyGoalTotal;}
	public int getStudyTimeTotal() {return studyTimeTotal;}
}
