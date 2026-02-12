package model;

public class userAccountUpdateBeans {
	 //StudentNo Stringにあとで変えること！ 
    private int StudentNo ;
	private int AttendanceNo ;
	private String ClassId;
	private String StudentName;
    

    // デフォルトコンストラクタ
    public userAccountUpdateBeans() {}

    // フルコンストラクタ
    public userAccountUpdateBeans(int StudentNo,int AttendanceNo,String ClassId,String StudentName){
    	this.StudentNo = StudentNo;
        this.AttendanceNo = AttendanceNo;
        this.ClassId = ClassId;
        this.StudentName = StudentName;

    }

    public int getStudentNo(){
        return StudentNo;
    }

    public void setStudentNo(int StudentNo){
        this.StudentNo = StudentNo;
    }

    public int getAttendanceNo(){
        return AttendanceNo;
    }

    public void setAttendanceNo(int AttendanceNo){
        this.AttendanceNo = AttendanceNo;
    }

    public String getClassId(){
        return ClassId;
    }

    public void setClassId(String ClassId){
        this.ClassId = ClassId;
    }

    public String getStudentName(){
        return StudentName;
    }

    public void setStudentName(String StudentName){
        this.StudentName = StudentName;
    }

}
