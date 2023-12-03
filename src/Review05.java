
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        //データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;
        ResultSet rs = null;

        try {
            //ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            //DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true","root","MySQL1192");

            String selectSql = "SELECT * FROM person where id = ?";
            spstmt = con.prepareStatement(selectSql);

            System.out.println("検索キーワードを入力してください>");
            int str1 = keyInNum();

            spstmt.setInt(1,str1);

            rs = spstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("Name");
                int age = rs.getInt("Age");

                System.out.println(name);
                System.out.println(age);

            }
            }catch(ClassNotFoundException e) {
                System.err.println("JDBCドライバのロードに失敗しました。");
                e.printStackTrace();
            }catch(SQLException e) {
                System.err.println("データベースに異常が発生しました。");
                e.printStackTrace();
            }finally {
                if(rs != null) {
                    try {
                        rs.close();
                    }catch(SQLException e) {
                        System.out.println("ResultSetを閉じるときにエラーが発生しました。");
                        e.printStackTrace();
                    }
                }
                if(spstmt != null) {
                    try {
                        spstmt.close();
                    }catch(SQLException e) {
                       System.err.println("PreparedStatementをエラーが発生しました。");
                       e.printStackTrace();
                }
            }
             if(con != null) {
                 try {
                     con.close();
                 }catch(SQLException e) {
                   System.err.println("データベース切断時にエラーが発生しました。");
                   e.printStackTrace();
             }

             }
        }


    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }

    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        }catch(NumberFormatException e) {

        }return result;
    }

}
