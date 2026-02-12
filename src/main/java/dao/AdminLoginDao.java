package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.AdminLoginBeans;

public class AdminLoginDao {
	    /**
	     * ログイン情報を取得する
	     */
	    public AdminLoginBeans loginInfoAdd(int accountIdInput) {
	        AdminLoginBeans beans = null;
	        String sql = "SELECT AccountId, Password, Role FROM loginMaster WHERE AccountId = ?";

	        // DbConnectから接続を取得
	        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {

	            pStmt.setInt(1, accountIdInput);

	            try (ResultSet rs = pStmt.executeQuery()) {
	                if (rs.next()) {
	                    int accountId = rs.getInt("AccountId");
	                    String password = rs.getString("Password");
	                    int role = rs.getInt("Role");
	                    // 取得した値をAdminLoginBeansインスタンスに格納
	                    beans = new AdminLoginBeans(accountId, password, role);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return beans;
	    }

	    /**
	     * ログインIDの存在判定
	     */
	    public boolean loginIdhantei(int accountIdInput) {
	        String sql = "SELECT count(*) FROM loginMaster WHERE AccountId = ?";

	        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {

	            pStmt.setInt(1, accountIdInput);

	            try (ResultSet rs = pStmt.executeQuery()) {
	                if (rs.next()) {
	                    // count(*) が 1以上なら存在する
	                    return rs.getInt(1) > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    /**
	     * 最終ログイン更新
	     */
	    public void LastLogin(int studentNo, Date lastLoginDate) {
	        String sql = "UPDATE userMaster SET LastLogin=? WHERE StudentNo=?";

	        try (Connection conn = DbConnect.getConnection();
	             PreparedStatement pStmt = conn.prepareStatement(sql)) {

	            pStmt.setDate(1, new java.sql.Date(lastLoginDate.getTime()));
	            pStmt.setInt(2, studentNo);

	            pStmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
