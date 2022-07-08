package ar.com.arcom.ui;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.ActionHandler;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.mysql.MySQLHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class JDialogAgregarQuitar extends JDialog implements EndUp {
    private EventoBoton eventoBoton;
    private Application application;
    private final JPanel contentPane;
    private JTextField textField;
    JLabel jlb_precio_unitario, jlb_total_num;
    private int cantidad;
    private Articulo articulo;

    public JDialogAgregarQuitar(Articulo articulo, Application application) {
        this.eventoBoton = new EventoBoton(application, this);
        cantidad = articulo.getCantidad();
        this.articulo = articulo;
        this.application = application;

        setSize(344, 210);
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
        jbn_cancel.setActionCommand("cmd_cancel");
        jbn_cancel.addActionListener(eventoBoton);
        jbn_cancel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jbn_cancel.setFont(new Font("Dialog", Font.PLAIN, 20));
        jbn_cancel.setMinimumSize(new Dimension(71, 40));
        jbn_cancel.setForeground(Color.WHITE);
        jbn_cancel.setBorder(null);
        jbn_cancel.setBackground(new Color(128, 128, 128));
        jbn_cancel.setBounds(10, 128, 150, 30);
        panel.add(jbn_cancel);

        JLabel jlb_nombre = new JLabel(articulo.getNombre().toUpperCase());
        jlb_nombre.setHorizontalAlignment(SwingConstants.CENTER);
        jlb_nombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jlb_nombre.setBounds(10, 10, 300, 23);
        panel.add(jlb_nombre);

        jlb_total_num = new JLabel("$" + String.valueOf(cantidad*articulo.getPrecio()));
        jlb_total_num.setFont(new Font("Tahoma", Font.PLAIN, 20));
        jlb_total_num.setHorizontalAlignment(SwingConstants.RIGHT);
        jlb_total_num.setBounds(112, 95, 198, 23);
        panel.add(jlb_total_num);

        JButton jbn_aceptar = new JButton("Aceptar");
        jbn_aceptar.addActionListener(eventoBoton);
        jbn_aceptar.setMinimumSize(new Dimension(71, 40));
        jbn_aceptar.setForeground(Color.WHITE);
        jbn_aceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
        jbn_aceptar.setBorder(null);
        jbn_aceptar.setBackground(new Color(24, 119, 242));
        jbn_aceptar.setAlignmentX(0.5f);
        jbn_aceptar.setActionCommand("cmd_acept");
        jbn_aceptar.setBounds(160, 128, 150, 30);
        panel.add(jbn_aceptar);

        jlb_precio_unitario = new JLabel("$" + articulo.getPrecio() + " C/U");
        jlb_precio_unitario.setHorizontalAlignment(SwingConstants.CENTER);
        jlb_precio_unitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
        jlb_precio_unitario.setBounds(10, 34, 300, 23);
        panel.add(jlb_precio_unitario);

        JLabel jlb_total = new JLabel("TOTAL");
        jlb_total.setHorizontalAlignment(SwingConstants.LEFT);
        jlb_total.setFont(new Font("Tahoma", Font.PLAIN, 18));
        jlb_total.setBounds(10, 95, 92, 23);
        panel.add(jlb_total);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 89, 300, 14);
        panel.add(separator);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setOpaque(false);
        textField.setText(String.valueOf(cantidad));
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setColumns(10);
        textField.setBounds(45, 61, 230, 23);
        panel.add(textField);

        JButton jbn_add = new JButton("+");
        jbn_add.addActionListener(eventoBoton);
        jbn_add.setForeground(Color.WHITE);
        jbn_add.setBorder(null);
        jbn_add.setBackground(new Color(66, 183, 42));
        jbn_add.setActionCommand("cmd_plus");
        jbn_add.setBounds(285, 61, 25, 23);
        panel.add(jbn_add);

        JButton jbn_quitar = new JButton("-");
        jbn_quitar.addActionListener(eventoBoton);
        jbn_quitar.setForeground(Color.WHITE);
        jbn_quitar.setBorder(null);
        jbn_quitar.setBackground(new Color(227, 30, 36));
        jbn_quitar.setActionCommand("cmd_minus");
        jbn_quitar.setBounds(10, 62, 25, 23);
        panel.add(jbn_quitar);
    }

    @Override
    public void endUp(int valor) {
        switch (valor){
            case 0 -> {
                dispose();
            }
            case 1 -> {
                if (cantidad > articulo.getCantidad()){
                    MySQLHelper mySQLHelper = new MySQLHelper();
                    if(mySQLHelper.consultaStock(articulo.getId(), (cantidad - articulo.getCantidad()))){
                        mySQLHelper.quitaStock(articulo.getId(), (cantidad - articulo.getCantidad()));
                        ((Cliente)(application.getUsuario())).getCarrito().agregaCantidad(articulo.getId(),
                                (cantidad - articulo.getCantidad()));
                        ((Cliente)(application.getUsuario())).getCarrito().recalcularTotales();
                        dispose();
                    } else JOptionPane.showMessageDialog(null, "No hay suficiente Stock para añadir");
                }
                else if (cantidad < articulo.getCantidad()){
                    MySQLHelper mySQLHelper = new MySQLHelper();
                    mySQLHelper.quitaStock(articulo.getId(), (articulo.getCantidad() - cantidad));
                    ((Cliente)(application.getUsuario())).getCarrito().quitarCantidad(articulo.getId(),
                            (articulo.getCantidad() - cantidad));
                    ((Cliente)(application.getUsuario())).getCarrito().recalcularTotales();
                    dispose();
                }
            }
            case 2 -> {
                cantidad++;
                textField.setText(String.valueOf(cantidad));
                jlb_total_num.setText("$" +cantidad*articulo.getPrecio());
                contentPane.updateUI();
            }
            case 3 -> {
                if (cantidad > 0) cantidad--;
                textField.setText(String.valueOf(cantidad));
                jlb_total_num.setText("$" + cantidad*articulo.getPrecio());
                contentPane.updateUI();
            }
            default -> {}
        }
    }
}