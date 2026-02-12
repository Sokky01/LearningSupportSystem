package logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import dao.AdminLoginDao;
import dao.UserRegisterInfoDAO;

public class UserRegisterLogic {

    public void registerCsv(InputStream is) {
        // 1. 文字コードを "MS932" から "UTF-8" に変更
        // 2. try-with-resources で Reader を確実にクローズ
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            
            String line;
            br.readLine(); // ヘッダー読み飛ばし

            // DAOのインスタンス化はループの外で行う（効率化）
            UserRegisterInfoDAO userDao = new UserRegisterInfoDAO();
            AdminLoginDao loginDao = new AdminLoginDao();
            Date toDay = new Date();

            while ((line = br.readLine()) != null) {
                // 空行対策
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                
                // JSP側の説明「学籍番号,出席番号,クラスID,名前」だと要素は4つ
                // パスワードも含めるなら CSV形式を 5列にする必要があります
                if (data.length < 5) {
                    System.out.println("スキップされた行（列不足）: " + line);
                    continue;
                }

                try {
                    int studentNo      = Integer.parseInt(data[0].trim());
                    int attendanceNo   = Integer.parseInt(data[1].trim());
                    String classId     = data[2].trim();
                    String studentName = data[3].trim();
                    String password    = data[4].trim();

                    // 各テーブルへ登録
                    userDao.insertUser(studentNo, attendanceNo, classId, studentName);
                    //loginDao.LastLogin(studentNo, toDay); // 初回登録日として記録
                    userDao.registerLogin(studentNo, password);

                } catch (NumberFormatException e) {
                    System.err.println("数値変換エラー（スキップ）: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}