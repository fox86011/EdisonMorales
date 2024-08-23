import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LOGIN extends JDialog{
    private JTextField txt_email;
    private JButton OKButton;
    private JButton CANCELButton;
    private JPanel LOGINPANEL;
    private JPasswordField pf_password;
    public LOGIN(JFrame parent){
        super(parent);
        setTitle("LOGIN");
        setContentPane(LOGINPANEL);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email=txt_email.getText();
                String password=String.valueOf(pf_password.getPassword());
                getAuthenticatedUser(email,password);
            }
        });

    }
    public Usuario usuario;
    private Usuario getAuthenticatedUser(String email, String password){
        Usuario usuario = null;
        final String BD_URL = "jdbc:mysql://localhost/bd_laCanchita";
        final String Username= "root";
        final String PASSWORD="";
        try{
            Connection con= DriverManager.getConnection(BD_URL,Username,PASSWORD);
            Statement stmt = con.createStatement();
            String sql = "select email from tb_cliente where email=? and password=? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                usuario = new Usuario();
                usuario.name=resultSet.getString("nombre");
                usuario.email=resultSet.getString("email");
                usuario.celular=resultSet.getString("telefono");
                usuario.ciudad=resultSet.getString("ciudad");
                usuario.password=resultSet.getString("password");
            }
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    public static void main(String[] args) {
        LOGIN login = new LOGIN(null);
        Usuario usuario = login.usuario;
        if (usuario!=null){

        }
        else{

        }
    }
}
