package ar.com.arcom.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JDialogAñadirQuitar extends JDialog {
    private UI frame;
    private EventoBoton eventoBoton;
    private final JPanel contentPane;
    private JLabel jlb_notific, jlb_user, jlb_pass;
    private JTextField textField;
    private JButton jbn_login, jbn_join, jbn_exit;

    public JDialogAñadirQuitar() {
        //this.frame = frame;
        //eventoBoton = new EventoBoton(this, frame.getApplication());

        //setUndecorated(true);
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

        JButton jbn_cancel = new JButton("Cancelar");
        jbn_cancel.setActionCommand("cmd_logoff");
        //jbn_cancel.addActionListener(eventoBoton);
        jbn_cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbn_cancel.setFont(new Font("Dialog", Font.PLAIN, 20));
        jbn_cancel.setMinimumSize(new Dimension(71, 40));
        jbn_cancel.setForeground(Color.WHITE);
        jbn_cancel.setBorder(null);
        jbn_cancel.setBackground(new Color(128, 128, 128));
        jbn_cancel.setBounds(10, 128, 150, 30);
        contentPane.add(jbn_cancel);

        JLabel jlb_nombre = new JLabel("NOMBRE DEL PRODUCTO");
        jlb_nombre.setHorizontalAlignment(SwingConstants.CENTER);
        jlb_nombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jlb_nombre.setBounds(10, 10, 300, 23);
        contentPane.add(jlb_nombre);

        JLabel jlb_total_num = new JLabel("$200");
        jlb_total_num.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jlb_total_num.setHorizontalAlignment(SwingConstants.RIGHT);
        jlb_total_num.setBounds(112, 95, 198, 23);
        contentPane.add(jlb_total_num);

        JButton jbn_aceptar = new JButton("Aceptar");
        jbn_aceptar.setMinimumSize(new Dimension(71, 40));
        jbn_aceptar.setForeground(Color.WHITE);
        jbn_aceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
        jbn_aceptar.setBorder(null);
        jbn_aceptar.setBackground(new Color(24, 119, 242));
        jbn_aceptar.setAlignmentX(0.5f);
        jbn_aceptar.setActionCommand("cmd_logoff");
        jbn_aceptar.setBounds(160, 128, 150, 30);
        contentPane.add(jbn_aceptar);

        JLabel jlb_precio_unitario = new JLabel("$1,55 C/U");
        jlb_precio_unitario.setHorizontalAlignment(SwingConstants.CENTER);
        jlb_precio_unitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jlb_precio_unitario.setBounds(10, 34, 300, 23);
        contentPane.add(jlb_precio_unitario);

        JLabel jlb_total = new JLabel("TOTAL");
        jlb_total.setHorizontalAlignment(SwingConstants.LEFT);
        jlb_total.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jlb_total.setBounds(10, 95, 92, 23);
        contentPane.add(jlb_total);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 89, 300, 14);
        contentPane.add(separator);

        textField = new JTextField();
        textField.setOpaque(false);
        textField.setColumns(10);
        textField.setBounds(45, 61, 230, 23);
        contentPane.add(textField);

        JButton jbn_add = new JButton("+");
        jbn_add.setForeground(Color.WHITE);
        jbn_add.setBorder(null);
        jbn_add.setBackground(new Color(66, 183, 42));
        jbn_add.setActionCommand("cmd_login");
        jbn_add.setBounds(285, 61, 25, 23);
        contentPane.add(jbn_add);

        JButton jbn_quitar = new JButton("-");
        jbn_quitar.setForeground(Color.WHITE);
        jbn_quitar.setBorder(null);
        jbn_quitar.setBackground(new Color(227, 30, 36));
        jbn_quitar.setActionCommand("cmd_login");
        jbn_quitar.setBounds(10, 62, 25, 23);
        contentPane.add(jbn_quitar);
    }
}