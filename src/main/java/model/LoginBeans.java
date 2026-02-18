package model;

public class LoginBeans {

    private int studentNo;
    private String password;
   
    public LoginBeans() {
    }

    // 引数ありコンストラクタ
    public LoginBeans(int studentNo, String password) {
        this.studentNo = studentNo;
        this.password = password;
        
    }

    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}

