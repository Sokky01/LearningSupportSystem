package model;

public class AdminAccountListBeans {
	
	//仕様書に合わせるかデータベース仕様書に合わせるか
	
	private int ManagerId;
	private String ManagerName;
	private String ClassId;
	private String SchoolGrade;
	private String SchoolGradeName;
	private String SchoolGr;
	private String MajorId;
	private String MajorName;
	
	public AdminAccountListBeans(int ManagerId,String ManagerName, String ClassId,String SchoolGrade,String SchoolGradeName,String SchoolGr,String MajorId, String MajorName){
		this.ManagerId=ManagerId;
		this.ManagerName=ManagerName;
		this.ClassId=ClassId;
		this.SchoolGrade=SchoolGrade;
		this.SchoolGradeName=SchoolGradeName;
		this.SchoolGr=SchoolGr;
		this.MajorId=MajorId;
		this.MajorName=MajorName;
		
		
	}
	
	public int getManagerId() {
		return ManagerId;
	}
	
	public void setManagerId(int ManagerId) {
		this.ManagerId = ManagerId;
	}
	
	public String getManagerName() {
		return ManagerName;
	}
	
	public void setManagerName( String ManagerName) {
		this.ManagerName = ManagerName;
	}
	
	public String getClassId() {
		return ClassId;
	}
	
	public void setClassId(String ClassId) {
		this.ClassId = ClassId;
	}
	
	public String getSchoolGrade() {
		return SchoolGrade;
	}
	
	public void setSchoolGrade(String SchoolGrade) {
		this.SchoolGrade = SchoolGrade;
	}
	
	public String getSchoolGradeName() {
		return SchoolGradeName;
	}
	
	public void setSchoolGradeName(String SchoolGradeName) {
		this.SchoolGradeName = SchoolGradeName;
	}
	
	public String getSchoolGr() {
		return SchoolGr;
	}
	
	public void setSchoolGr(String SchoolGr) {
		this.SchoolGr = SchoolGr;
	}
	
	public String getMajorId() {
		return MajorId;
	}
	
	public void setMajorId( String MajorId) {
		this.MajorId = MajorId;
	}
	
	public String getMajorName() {
		return MajorName;
	}
	
	public void setMajorName(String MajorName) {
		this.MajorName = MajorName;
	}

}
