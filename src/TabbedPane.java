import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class TabbedPane extends JFrame {
    private static Connection con = null;
    private static String DATABASE_NAME;
    private static String DATABASE_USER_NAME;
    private static String DATABASE_PASSWORD;

    public TabbedPane()
    {
        createInputDBInforLayout();
    }

    private void setDisplay(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setTitle("Đăng nhập");
        setSize(350,250);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createUI(){

        JTabbedPane tabbedPane = new JTabbedPane();
        add(tabbedPane);
        JPanel panelSinhVien = new JPanel();
        tabbedPane.addTab("Sinh viên",panelSinhVien);
        createSigninLayout(panelSinhVien);
        JPanel panelGiaovu = new JPanel();
        tabbedPane.addTab("Giáo vụ",panelGiaovu);
        createSigninLayout(panelGiaovu);
    }
    private void createInputDBInforLayout()
    {

        JFrame frame = new JFrame();
        frame.setTitle("Cơ sở dữ liệu của bạn");
        frame.setSize(400,300);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        JLabel label1 = new JLabel("Tên CSDL: ");
        label1.setBounds(50,20,90,25);
        panel.add(label1);

        JTextField textField1 = new JTextField(20);
        textField1.setBounds(150,20,165,25);
        panel.add(textField1);

        JLabel label2 = new JLabel("Tên đăng nhập: ");
        label2.setBounds(50,50,100,25);
        panel.add(label2);

        JTextField textField2 = new JTextField(20);
        textField2.setBounds(150,50,165,25);
        panel.add(textField2);

        JLabel label3 = new JLabel("Mật khẩu");
        label3.setBounds(50,80,165,25);
        panel.add(label3);

        JPasswordField textField3 = new JPasswordField(20);
        textField3.setBounds(150,80,165,25);
        panel.add(textField3);

        JButton button = new JButton("Xác nhận");
        button.setBounds(130,150,100,25);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField1.getText().length()==0||textField2.getText().length()==0||textField3.getText().length()==0){
                    JOptionPane.showMessageDialog(button,"Chưa nhập đủ thông tin");
                }
                else
                {
                    DATABASE_NAME = textField1.getText();
                    DATABASE_USER_NAME = textField2.getText();
                    DATABASE_PASSWORD = String.valueOf(textField3.getPassword());

                    try{
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                        con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=" +DATABASE_NAME + ";user=" +DATABASE_USER_NAME+";password=" + DATABASE_PASSWORD);

                        JOptionPane.showMessageDialog(button,"Kết nối cơ sở dữ liệu thành công");

                        frame.dispose();

                        createUI();
                        setDisplay();
                    }
                    catch(Exception ex){
                        JOptionPane.showMessageDialog(button,"Kết nối cơ sở dữ liệu thất bại");
                    }

                }
            }
        });

        panel.add(button);

        panel.setVisible(true);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void createSigninLayout(JPanel panel){
        JLabel userLabel = new JLabel("Tài khoản: ");
        userLabel.setBounds(10,20,80,25);

        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);

        JLabel passwordLabel = new JLabel("Mật khẩu: ");
        passwordLabel.setBounds(10,50,80,25);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);

        JButton signin = new JButton("Đăng nhập");
        //signin.setToolTipText("Login");
        signin.setBounds(10,100,100,25);

        JLabel signupLabel = new JLabel("Tạo tài khoản mới?");
        signupLabel.setBounds(10,130,150,25);

        JButton signup = new JButton("Đăng ký");
        signup.setBounds(130,130,100,25);

        createLayout(panel,userLabel,userText,passwordLabel,passwordText,signin,signupLabel,signup);

        signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userLogin = userText.getText();
                String passLogin = String.valueOf(passwordText.getPassword());

                if (userLogin.length()==0){
                    JOptionPane.showMessageDialog(signin,"Chưa nhập tên đăng nhập");
                }
                else if(passLogin.length()==0){
                    JOptionPane.showMessageDialog(signin,"Chưa nhập mật khẩu");
                }
                else{
                    try {
                        Statement st = con.createStatement();

                        String strsql = "select * from TaiKhoanSinhVien";

                        ResultSet rs = st.executeQuery(strsql);

                        boolean a = false;


                        while (rs.next())
                        {
                            if(rs.getString(1).equals(userLogin)&&rs.getString(2).equals(passLogin))
                            {
                                a = true;
                                dispose();
                                JFrame newFrame = new JFrame();
                                newFrame.setLayout(null);
                                newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                               newFrame.setSize(300,400);
                               newFrame.setLocationRelativeTo(null);
                                newFrame.setVisible(true);
                                break;
                            }
                        }
                        if(a==false){
                            JOptionPane.showMessageDialog(signin, "Tên đăng nhập hoặc mật khẩu sai");
                        }
                    }
                    catch(Exception ex){
                        System.out.println("Error: " + ex.getMessage());
                    }
                }

            }
        });
    }

    private void createLayout(JComponent... arg){
        JPanel pane =(JPanel) arg[0];
        pane.setLayout(null);

        for(int i=1;i< arg.length;i++){
            pane.add(arg[i]);
        }

        pane.setVisible(true);
    }
    public static void main(String[] args)
    {
        TabbedPane tab = new TabbedPane();

    }
}
