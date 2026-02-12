package model;

import java.util.Date;

public class DisplayStudyTimeGraphBeans {
	private int StudentNo;
	private int SubjectId;
	private String SubjectName;
	private Date YearMonthDate;
	private int LearningTime;
	
	public DisplayStudyTimeGraphBeans(int studentNo, int subjectId, String subjectName, Date yearMonthDate, int learningTime) {
		this.StudentNo = studentNo;
		this.SubjectId = subjectId;
		this.SubjectName = subjectName;
		this.YearMonthDate = yearMonthDate;
		this.LearningTime = learningTime;
	}
	
	public int getStudentNo() {
		return StudentNo;
	}
	public void setStudentNo(int studentNo) {
		StudentNo = studentNo;
	}
	public int getSubjectId() {
		return SubjectId;
	}
	public void setSubjectId(int subjectId) {
		SubjectId = subjectId;
	}
	public String getSubjectName() {
		return SubjectName;
	}
	public void setSubjectName(String subjectName) {
		SubjectName = subjectName;
	}
	public Date getYearMonthDate() {
		return YearMonthDate;
	}
	public void setYearMonthDate(Date yearMonthDate) {
		YearMonthDate = yearMonthDate;
	}
	public int getLearningTime() {
		return LearningTime;
	}
	public void setLearningTime(int learningTime) {
		LearningTime = learningTime;
	}
	
	
}
