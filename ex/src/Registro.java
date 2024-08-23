import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Registro extends JDialog{
    private JPanel RegistroPanel;
    private JTextField txt_Nombre;
    private JTextField txt_email;
    private JTextField txt_telefono;
    private JTextField txt_ciudad;
    private JPasswordField pf_contraseña;
    private JPasswordField pf_confContraseña;
    private JButton REGISTRARButton;
    private JButton CANCELARButton;

    public Registro(JFrame parent){
        super(parent);
        setTitle("REGISTRO");
        setContentPane(RegistroPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        REGISTRARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registroUsuario();
            }
        });
        CANCELARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    private void registroUsuario(){
        String nombre=txt_Nombre.getText();
        String email=txt_email.getText();
        String telefono=txt_telefono.getText();
        String ciudad=txt_ciudad.getText();
        String password=String.valueOf(pf_contraseña.getPassword());
        String confPassword=String.valueOf(pf_confContraseña.getPassword());
        if(nombre.isEmpty()||email.isEmpty()||telefono.isEmpty()||ciudad.isEmpty()||password.isEmpty()){
            JOptionPane.showMessageDialog(this,"Rellena todos los campos porfavor","De nuevo",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(confPassword)){
            JOptionPane.showMessageDialog(this,"La contraseña no coincide","De nuevo",JOptionPane.ERROR_MESSAGE);
            return;
        }
        usuario = addUsuarioDb(nombre,email,telefono,ciudad,password);
        if(usuario!=null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"ERROR AL REGISTRAR","De nuevo",JOptionPane.ERROR_MESSAGE);
        }
    }
    public Usuario usuario;
    private Usuario addUsuarioDb(String nombre, String email, String telefono, String ciudad, String password) {
        Usuario usuario = null;
        final String BD_URL = "jdbc:mysql://localhost/bd_laCanchita";
        final String Username= "root";
        final String PASSWORD="";
        try{
            Connection con= DriverManager.getConnection(BD_URL,Username,PASSWORD);
            Statement stmt = con.createStatement();
            String sql = "insert into tb_cliente values(?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,nombre);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,telefono);
            preparedStatement.setString(4,ciudad);
            preparedStatement.setString(5,password);
            int addedRows =preparedStatement.executeUpdate();
            if(addedRows>0){
                usuario = new Usuario();
                usuario.name=nombre;
                usuario.email=email;
                usuario.celular=telefono;
                usuario.ciudad=ciudad;
                usuario.password=password;
            }
            stmt.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuario;
    }

    public static void main(String[] args) {
        Registro registro = new Registro(null);
        Usuario usuario = registro.usuario;
        if(usuario!=null){
            System.out.println("Registro exitoso "+usuario.name);
        }
        else {
            System.out.println("Registro Cancelado");
        }
    }
}
