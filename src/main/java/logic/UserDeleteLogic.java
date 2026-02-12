package logic;

import dao.UserDeleteInfoDAO;
import model.userAccountListBeans;

public class UserDeleteLogic {
	
	 public boolean execute(int originalStudentNo) {
		 
		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
		 boolean result = dao.delete(originalStudentNo);
	    return result;
	    	

	    }
	 
	 public userAccountListBeans execute2(int studentNo) {
		 
		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
	    	 return dao.findByStudentNo(studentNo);
	    	

	    }
	 
      public boolean execute3(int originalStudentNo) {
		 
		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
		 boolean delete1=dao.delete2(originalStudentNo);
		 
	    return delete1;
      }
      
      
      
      public boolean execute4(int originalStudentNo) {
 		 
 		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
 		 boolean delete1=dao.delete3(originalStudentNo);
 		 
 	    return delete1;
       }
	    	
      
      
      public boolean execute5(int originalStudentNo) {
  		 
  		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
  		 boolean delete1=dao.delete4(originalStudentNo);
  		 
  	    return delete1;
        }
      
      
      public boolean execute6(int originalStudentNo) {
   		 
   		 UserDeleteInfoDAO dao = new UserDeleteInfoDAO();
   		 boolean delete1=dao.delete5(originalStudentNo);
   		 
   	    return delete1;
         }
  	    	
 	    	

    
	 
	 

}