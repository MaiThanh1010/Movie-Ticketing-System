/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connection;

/**
 *
 * @author nguye
 */
import java.sql.Connection;
import java.sql.DriverManager;
public class DBUtils {
    private Connection conn;
    public DBUtils(){};
    public Connection createConn(){
       try{
            //Dang ly Driver;
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connection URL
            conn = 
            DriverManager.getConnection("jdbc:mysql://localhost:3306/qlrp?useSSL=false&useUnicode=true&characterEncoding=UTF-8","root","123456");
            if (conn==null)
                System.out.println("Ket noi khong thanh cong");
            else
                System.out.println("Ket noi thanh cong");
        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
