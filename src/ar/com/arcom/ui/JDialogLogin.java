package ar.com.arcom.ui;

import ar.com.arcom.handlers.EventoBoton;
import ar.com.arcom.handlers.Login;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JDialogLogin extends JDialog implements Login {
    private AdministradorUI frame;
    private EventoBoton eventoBoton;
    private final JPanel contentPane;
    private JLabel jlb_notific, jlb_user, jlb_pass;
    private JTextField jtf_user;
    private JPasswordField jtf_password;
    private JButton jbn_login, jbn_join, jbn_exit;
    private JCheckBox jcb0;

    public JDialogLogin(AdministradorUI frame) {
        this.frame = frame;
        eventoBoton = new EventoBoton(this, frame.getApplication());

        setUndecorated(true);
        setSize(254, 175);
        setResizable(false);
        setLocationRelativeTo(null);

        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        int height = pantalla.height;
        int width = pantalla.width;
        setLocation(width/2-(getWidth()/2), height/2-(getHeight()/2));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        initialize();
    }

    private void initialize() {
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        jtf_user = new JTextField();
        //jtf_user.setBorder(null);
        jtf_user.setText("flavio");
        jtf_user.setBounds(10, 16, 225, 25);
        jtf_user.setColumns(10);
        panel.add(jtf_user);

        jtf_password = new JPasswordField();
        //jtf_password.setBorder(null);
        jtf_password.setText("123456");
        jtf_password.setBounds(10, 43, 225, 25);
        panel.add(jtf_password);


        jbn_login = new JButton("Iniciar Sesión");
        jbn_login.setActionCommand("cmd_login");
        jbn_login.addActionListener(eventoBoton);
        jbn_login.setBackground(new Color(24, 119, 242));
        jbn_login.setForeground(Color.WHITE);
        jbn_login.setBorder(null);
        jbn_login.setBounds(10, 70, 225, 23);
        panel.add(jbn_login);

        jbn_join = new JButton("Registrarse");
        jbn_join.setActionCommand("cmd_sing");
        jbn_join.addActionListener(eventoBoton);
        jbn_join.setBackground(new Color(66, 183, 42));
        jbn_join.setForeground(Color.WHITE);
        jbn_join.setBorder(null);
        jbn_join.setBounds(10, 96, 225, 23);
        panel.add(jbn_join);

        jbn_exit = new JButton("Salir");
        jbn_exit.setActionCommand("cmd_exit");
        jbn_exit.addActionListener(eventoBoton);
        jbn_exit.setBackground(new Color(27, 30, 36));
        jbn_exit.setForeground(Color.WHITE);
        jbn_exit.setBorder(null);
        jbn_exit.setBounds(165, 130, 70, 23);
        panel.add(jbn_exit);

        jcb0 = new JCheckBox("Recordar Usuario");
        jcb0.setAlignmentX(Component.CENTER_ALIGNMENT);
        jcb0.setBounds(10, 130, 149, 23);
        panel.add(jcb0);
    }

    @Override
    public String getUser() {
        return jtf_user.getText();
    }
    @Override
    public String getPassword() {
        return String.valueOf(jtf_password.getPassword());
    }
    @Override
    public boolean stayLoggedIn() {
        return jcb0.isSelected();
    }
    @Override
    public void endUp(int type) {
        this.dispose();
    }
}