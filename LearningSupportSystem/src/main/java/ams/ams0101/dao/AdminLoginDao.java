package ams.ams0101.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import ams.ams0101.model.beans.AdminLoginBeans;

public class AdminLoginDao {
	
	//データベース接続に使用する情報
			private final String JDBC_URL = "jdbc:mariadb://127.0.0.1:3306/lssdb";
			private final String DB_USER = "root";
			private final String DB_PASS = "mysql";

			
			public AdminLoginBeans loginInfoAdd(int AccountId) {
				// TODO 自動生成されたメソッド・スタブ
			
				AdminLoginBeans beans=null;
				
				
				//JDBCドライバを読み込む
				 try {
					 Class.forName("org.mariadb.jdbc.Driver");
				 } catch (ClassNotFoundException e) {
					 throw new IllegalStateException(
							 "JDBCドライバを読み込めませんでした");
				 }
				 //データベースに接続
				 try (Connection conn = DriverManager.getConnection(
						 JDBC_URL,DB_USER,DB_PASS)) {
					 //SELECT準備

					 String sql="SELECT AccountId,Password,Role FROM loginMaster WHERE AccountId = ? ";
			         PreparedStatement pStmt = conn.prepareStatement(sql);
			         pStmt.setInt(1,  AccountId);
			         
			         


			                //SELECT実行
			         ResultSet rs = pStmt.executeQuery();
			       
			                //結果表に格納されたコードの内容を表示
			         while(rs.next()){
			        	        
			                 int accountId =rs.getInt("AccountId");
			                 String password=rs.getString("Password");
			                 int role=rs.getInt("Role");  
			               
			               //取得した値をadminLoginBeansインスタンスに格納
			                 beans = new AdminLoginBeans(accountId,password,role);
			                
			              

			         }//while
			    
				 }catch(SQLException e){//try{
			         e.printStackTrace();
			}//catch
				 return beans;
				
				
			}
			
			public AdminLoginBeans loginInfoAdd2(int AccountId) {
				// TODO 自動生成されたメソッド・スタブ
			
				AdminLoginBeans beans=null;
				
				
				//JDBCドライバを読み込む
				 try {
					 Class.forName("org.mariadb.jdbc.Driver");
				 } catch (ClassNotFoundException e) {
					 throw new IllegalStateException(
							 "JDBCドライバを読み込めませんでした");
				 }
				 //データベースに接続
				 try (Connection conn = DriverManager.getConnection(
						 JDBC_URL,DB_USER,DB_PASS)) {
					 //SELECT準備

					 String sql="SELECT AccountId FROM loginMaster WHERE AccountId = ? ";
			         PreparedStatement pStmt = conn.prepareStatement(sql);
			         pStmt.setInt(1,  AccountId);
			         
			         


			                //SELECT実行
			         ResultSet rs = pStmt.executeQuery();
			       
			                //結果表に格納されたコードの内容を表示
			         while(rs.next()){
			        	        
			                 int accountId =rs.getInt("AccountId");
			                 
			               
			               //取得した値をadminLoginBeansインスタンスに格納
			                 beans = new AdminLoginBeans(accountId);
			                
			              

			         }//while
			    
				 }catch(SQLException e){//try{
			         e.printStackTrace();
			}//catch
				 return beans;
				
				
			}


			
			
			

			
			//最終ログイン更新
			public void LastLogin(int StudentNo, Date LastLogin) {
				//JDBCドライバを読み込む
				 try {
					 Class.forName("org.mariadb.jdbc.Driver");
				 } catch (ClassNotFoundException e) {
					 throw new IllegalStateException(
							 "JDBCドライバを読み込めませんでした");
				 }
				 //データベースに接続
				 try (Connection conn = DriverManager.getConnection(
						 JDBC_URL,DB_USER,DB_PASS)) {
			         
					 String sql = "UPDATE userMaster SET LastLogin=? WHERE StudentNo=?";  
					 PreparedStatement pStmt = conn.prepareStatement(sql);
					 
					 pStmt.setDate(1, new java.sql.Date(LastLogin.getTime()));

					 pStmt.setInt(2, StudentNo);
					 
					//SELECT実行
			          pStmt.executeUpdate();
					 
					 
					 
				 }catch(SQLException e){//try{
			         e.printStackTrace();
			}//catch
				 
			  }

}
