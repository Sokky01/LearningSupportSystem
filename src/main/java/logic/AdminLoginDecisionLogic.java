package logic;

import model.AdminLoginBeans;

public class AdminLoginDecisionLogic {
	public  boolean pw(String Password,AdminLoginBeans admin){ 
        /*pwの判定*/
         boolean pw = true;
         
         /*Beansからのものを受け取る*/
        String passwordBeans = admin.getPassword();
       
       /*pwの判定（打ち込んだものとBeansもの）*/
       if(!Password.equals(passwordBeans)){//pwが不一致
           pw=false; 
        }
       return pw;
           
    }
	
	public boolean role(AdminLoginBeans admin) {
		/*decision変数をTureで設定   役職の判定*/
        boolean decision = true;
        
        /*Beansからのものを受け取る*/
        int roleBeans = admin.getRole();
        
        if(roleBeans == 0){//roleBeansを数字でも判断できるようにする

            decision=false;  //pwが一致していないまたは役職が学生
            }
         
         return decision;
	}

}
