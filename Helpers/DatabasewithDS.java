package Helpers;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;

/** This class is not used.*/
public class DatabasewithDS {


    /**This function connects to the database
     *
     * @param args a default parameter
     */
    public static void main(String[] args) {
        DataSource ds = getMySQLDataSource();

        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select country from country");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**This function will connect to th e database
     *
     * @return a MysqlDataSource
     */
    public static MysqlDataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL("jdbc:mysql://wgudb.ucertify.com:3306/WJ06rpB");
            mysqlDS.setUser(props.getProperty("U06rpB"));
            mysqlDS.setPassword(props.getProperty("53688851020"));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return mysqlDS;
    }
}