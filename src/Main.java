import java.sql.*;
import javax.swing.*;
public  class Main {

    public static void main(String[] args) {
        Connection con = null;
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=HCMUS;user=sa;password=12345");

            Statement st = con.createStatement();

            String strsql = "select * from TaiKhoanSinhVien";

            ResultSet rs = st.executeQuery(strsql);

            if(con!=null)
            {
                while (rs.next()) {
                    String strName = rs.getString(1);
                    String strDesc = rs.getString(2);

                    System.out.println(" - Name: " + strName + " - Desc: " + strDesc);
                }
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
        }

    }
}