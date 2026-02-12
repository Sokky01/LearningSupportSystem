package model;

import java.util.Date;

public class DisplayMissionsBeans {
	private int missionId;			// ミッションID
	private int missionType;		// ミッションタイプ
	private String missionName;		// ミッション名
	private int requiredValue;		// 必須値
	private int subjectId;			// 科目ID
	private int missionPoint;		// ポイント数
	private int studentNo;			// 学籍番号
	private Date achievementDate;	// 達成日
	private boolean achieved;		// 達成フラグ
	
	// デフォルトコンストラクタ
	public DisplayMissionsBeans() {
	}
	
	// getter/setter
	public int getMissionId() { return missionId; }
    public void setMissionId(int missionId) { this.missionId = missionId; }

    public int getMissionType() { return missionType; }
    public void setMissionType(int missionType) { this.missionType = missionType; }

    public String getMissionName() { return missionName; }
    public void setMissionName(String missionName) { this.missionName = missionName; }

    public int getRequiredValue() { return requiredValue; }
    public void setRequiredValue(int requiredValue) { this.requiredValue = requiredValue; }

    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }

    public int getMissionPoint() { return missionPoint; }
    public void setMissionPoint(int missionPoint) { this.missionPoint = missionPoint; }

    public int getStudentNo() { return studentNo; }
    public void setStudentNo(int studentNo) { this.studentNo = studentNo; }

    public Date getAchievementDate() { return achievementDate; }
    public void setAchievementDate(Date achievementDate) { this.achievementDate = achievementDate; }

    public boolean isAchieved() { return achieved; }
    public void setAchieved(boolean achieved) { this.achieved = achieved; }
}
