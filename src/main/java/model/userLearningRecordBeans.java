package model;

public class userLearningRecordBeans {
	private int studentNo;
	private int subjectId;
	private String subjectName;
	private int subjectGoal;
	private int subjectTotal;
	private int studyGoalTotal;
	private int studyTimeTotal;
	
	public userLearningRecordBeans(int StudentNo, int SubjectId, String  SubjectName, int SubjectGoal, int SubjectTotal, int StudyGoalTotal, int StudyTimeTotal) {
		this.studentNo = StudentNo;				
		this.subjectId = SubjectId;
		this.subjectName = SubjectName;
		this.subjectGoal = SubjectGoal;
		this.subjectTotal = SubjectTotal;
		this.studyGoalTotal = StudyGoalTotal;
		this.studyTimeTotal = StudyTimeTotal;
	}
	public int getStudentNo() {return studentNo;}
	public int getSubjectId() {return subjectId;}
	public String getSubjectName() { return subjectName; }
	public int getSubjectGoal() {return subjectGoal;}
	public int getSubjectTotal() {return subjectTotal;}
	public int getStudyGoalTotal() {return studyGoalTotal;}
	public int getStudyTimeTotal() {return studyTimeTotal;}
	
	}
	
