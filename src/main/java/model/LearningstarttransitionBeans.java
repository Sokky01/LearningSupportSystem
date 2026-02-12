package model;

public class LearningstarttransitionBeans {
	private int subjectid;
	private String subjectname;
	
	public LearningstarttransitionBeans(int subjectid,String subjectname) {
		this.subjectid = subjectid;
		this.subjectname = subjectname;
	}
	
	public int getSubjectid() {
		return subjectid;
	}
	public String getSubjectname() {
		return subjectname;
	}
	
}
