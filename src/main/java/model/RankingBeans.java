package model;
import java.io.Serializable;

public class RankingBeans implements Serializable {
		
		private int StudentNo;
		private String NickName;
		private int PointTotal;
		
		// getter/setter
		public int getStudentNo() {
	        return StudentNo;
	    }
		public void setStudentNo(int StudentNo) {
			this.StudentNo = StudentNo;
		}
		public String getNickName() {
			return NickName;
		}
		public void setNickName(String NickName) {
			 this.NickName = NickName;
		}
		public int getPointTotal() {
			return PointTotal;
		}
		public void setPointTotal(int PointTotal) {
			this.PointTotal = PointTotal;
		}
}
