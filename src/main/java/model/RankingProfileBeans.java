package model;

import java.sql.Date;

public class RankingProfileBeans {
    private int studentNo;
    private String nickName;
    private int maxGrade;
    private String gradeName;  // ← 追加
    private int pointTotal;
    private Date learningDate;
    private String subject;
    private int learningTime;
    private int point;

    // コンストラクタ
    public RankingProfileBeans() {}

    // Getter and Setter
    public int getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(int studentNo) {
        this.studentNo = studentNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        this.maxGrade = maxGrade;
    }

    // ← 以下を追加
    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public int getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(int pointTotal) {
        this.pointTotal = pointTotal;
    }

    public Date getLearningDate() {
        return learningDate;
    }

    public void setLearningDate(Date learningDate) {
        this.learningDate = learningDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getLearningTime() {
        return learningTime;
    }

    public void setLearningTime(int learningTime) {
        this.learningTime = learningTime;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}