package model;
import java.util.Date;
public class UserRegisterBeans {
    private int studentNo;      // 後で String に変更可
    private int attendanceNo;
    private String classId;
    private String studentName;
    private String password;
    private Date lastLogin;

    // デフォルトコンストラクタ
    public UserRegisterBeans() {}

    // フルコンストラクタ
    public UserRegisterBeans(int studentNo, int attendanceNo, String classId, String studentName, String password){
        this.studentNo = studentNo;
        this.attendanceNo = attendanceNo;
        this.classId = classId;
        this.studentName = studentName;
        this.password = password;
    }
    public UserRegisterBeans(int studentNo, int attendanceNo, String classId, String studentName, String password,Date lastLogin){
        this.studentNo = studentNo;
        this.attendanceNo = attendanceNo;
        this.classId = classId;
        this.studentName = studentName;
        this.password = password;
        this.lastLogin=lastLogin;
    }

    // getter / setter
    public int getStudentNo() { return studentNo; }
    public void setStudentNo(int studentNo) { this.studentNo = studentNo; }

    public int getAttendanceNo() { return attendanceNo; }
    public void setAttendanceNo(int attendanceNo) { this.attendanceNo = attendanceNo; }

    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

public Date getlastLogin() { return lastLogin; }
public void setlastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
}
