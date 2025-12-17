package lrs.lrs0701.model;

public class AdminLoginBeans {
	
	 private int AccountId;      // ID
	    private String Password;   // パスワード
	    private int Role;   // 役職（管理者かどうか等）
	    

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
	    
	    

	    // getter/setter
	    public int getAccountId() {
	        return AccountId;
	    }
	    public void setAccountId(int accountId) {
	        this.AccountId = accountId;
	    }

	    public String getPassword() {
	        return Password;
	    }
	    public void setPassword(String password) {
	        this.Password = password;
	    }

	    public int isRole() {
	        return Role;
	    }
	    
	    public int getRole() { /*追加した*/
	    	    return Role;
	    }
	    
	    public void setRole(int role) {
	        this.Role = role;
	    }

}
