package myp.myp1101.model;

import java.io.Serializable;

public class UserProfile implements Serializable {
    
    // フィールド（データの入れ物）
    private String studentId;
    private String studentName;
    private String nickName;
    private String publicPreference;
    private String settingGradeId;
    private String password;
    private String maxGrade; // DAOでコメントアウトされていた項目も念のため用意

    // コンストラクタ
    public UserProfile() {}

    // --- セッターとゲッター (ここがエラーの原因だった部分です) ---

    // StudentId
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    // StudentName
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    // NickName
    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    // PublicPreference
    public String getPublicPreference() { return publicPreference; }
    public void setPublicPreference(String publicPreference) { this.publicPreference = publicPreference; }

    // SettingGradeId
    public String getSettingGradeId() { return settingGradeId; }
    public void setSettingGradeId(String settingGradeId) { this.settingGradeId = settingGradeId; }

    // Password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    // MaxGrade
    public String getMaxGrade() { return maxGrade; }
    public void setMaxGrade(String maxGrade) { this.maxGrade = maxGrade; }
}