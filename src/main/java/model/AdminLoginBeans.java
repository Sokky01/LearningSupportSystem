package model;

public class AdminLoginBeans {

	   private int AccountId;      // ID
	    private String Password;   // パスワード
	    private int Role;   // 役職（管理者かどうか等）
	    private String nickName;
	    private String managerName;
	    
	    public  AdminLoginBeans(int AccountId) {
	    	this.AccountId=AccountId;
	    }

	    // コンストラクタ（必要に応じて）
	    public AdminLoginBeans(int AccountId,String Password) {
	    	this.AccountId=AccountId;
	    	this.Password=Password;
	    }
	    
	    

	    public AdminLoginBeans(int AccountId,String Password,int Role) {
	    	this.AccountId=AccountId;
	    	this.Password=Password;
	    	this.Role=Role;
	    	
	    }/*上記のAdminLogicBeans追加した*/
	    
	    public AdminLoginBeans(int AccountId,String Password,int Role, String nickName, String managerName) {
	    	this.AccountId=AccountId;
	    	this.Password=Password;
	    	this.Role=Role;
	    	this.nickName = nickName;
	        this.managerName = managerName;
	    }
	    

	    // getter/setter
	    public int getAccountId() {
	        return AccountId;
	    }
	    public void setAccountId(int AccountId) {
	        this.AccountId = AccountId;
	    }

	    public String getPassword() {
	        return Password;
	    }
	    public void setPassword(String Password) {
	        this.Password = Password;
	    }

	    public int isRole() {
	        return Role;
	    }
	    public int getRole() { /*追加した*/
    	    return Role;
    }
	   
	    public void setRole(int Role) {
	        this.Role = Role;
	    }
	    public String getNickName() {
	        return nickName;
	    }
	    
	    public void setNickName(String nickName) {
	        this.nickName = nickName;
	    }
	    
	    public String getManagerName() {
	        return managerName;
	    }
	    
	    public void setmanagerName(String managerName) {
	        this.managerName = managerName;
	    }
}

