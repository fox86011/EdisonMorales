import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dashborn extends JFrame{
    private JPanel Dashborn;
    private JButton REGISTROButton;
    private JLabel lbAdmin;

    public Dashborn(){
        setTitle("dashbornd");
        setContentPane(Dashborn);
        setMinimumSize(new Dimension(500,429));
        setSize(1200,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        boolean hasRegistredUsers = connectToDataBase();
        if(hasRegistredUsers){
            LOGIN login =new LOGIN(this);
            Usuario usuario = login.usuario;
            if(usuario!=null){
                lbAdmin.setText("Usuario: "+ usuario.name);
                setLocationRelativeTo(null);
                setVisible(true);
            }else {
                dispose();
            }
        }
    }
    private boolean connectToDataBase(){
        boolean hasRegistredUsers = false;
        final String MYSQL_SERVER_URL ="jdbc:mysql:/localhost/";
        final String BD_URL = "jdbc:mysql://localhost/bd_laCanchita";
        final String Username= "root";
        final String PASSWORD="";
        try {
            Connection con = DriverManager.getConnection(MYSQL_SERVER_URL,Username,PASSWORD);
            Statement statement = con.createStatement();
            statement.executeUpdate("create database if not exists bd_laCanchita");
            statement.close();
            con.close();
            con = DriverManager.getConnection(BD_URL,Username,PASSWORD);
            statement = con.createStatement();
            String sql = "create table if not exist tb_cliente ("
                    +"id int(10) not null primary key auto_incremet,"
                    +"name varchar(200) not null,"
                    +"email varchar (200) not null unique,"
                    +"telefono varchar(200),"
                    +"ciudad varchar(200),"
                    +"password varchar(200) not null"
                    +")";
            statement.executeUpdate(sql);
            statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from tb_cliente");
            if(resultSet.next()){
                int numUser = resultSet.getInt(1);
                if(numUser>0){
                    hasRegistredUsers=true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return hasRegistredUsers;
    }
}
