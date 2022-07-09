package ar.com.arcom.ui;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Producto;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.mysql.MySQLHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JDialogModificar extends JDialog implements EndUp {
    private EventoBoton eventoBoton;
    private Application application;
    private final JPanel contentPane;
    private JTextField textField, jtf_nombre, jtf_descripcion, jtf_precio, jtf_stock;
    JLabel jlb_total_num;
    private Producto producto, productoAux;

    public JDialogModificar(Producto producto, Application application) {
        this.eventoBoton = new EventoBoton(application, this);
        this.producto = producto;
        this.application = application;

        setSize(350, 246);
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
        contentPane.add(panel);
        panel.setLayout(null);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setOpaque(false);
        textField.setText(String.valueOf(producto.getId()));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setColumns(10);
        textField.setBounds(40, 14, 51, 20);
        panel.add(textField);

        jtf_nombre = new JTextField();
        jtf_nombre.setColumns(10);
        jtf_nombre.setText(producto.getNombre());
        jtf_nombre.setBounds(12, 67, 145, 20);
        panel.add(jtf_nombre);

        JLabel lblId = new JLabel("ID");
        lblId.setBounds(12, 16, 26, 16);
        panel.add(lblId);

        JLabel lblNombre = new JLabel("NOMBRE");
        lblNombre.setBounds(12, 44, 145, 16);
        panel.add(lblNombre);

        jtf_descripcion = new JTextField();
        jtf_descripcion.setColumns(10);
        jtf_descripcion.setText(producto.getDescripcion());
        jtf_descripcion.setBounds(169, 66, 145, 20);
        panel.add(jtf_descripcion);

        JLabel lblDescripcion = new JLabel("DESCRIPCION");
        lblDescripcion.setBounds(169, 44, 145, 16);
        panel.add(lblDescripcion);

        jtf_precio = new JTextField();
        jtf_precio.setColumns(10);
        jtf_precio.setText(String.valueOf(producto.getPrecio()));
        jtf_precio.setBounds(12, 120, 145, 20);
        panel.add(jtf_precio);

        JLabel lblPrecio = new JLabel("PRECIO");
        lblPrecio.setBounds(12, 97, 145, 16);
        panel.add(lblPrecio);

        JLabel lblExistencias = new JLabel("EXISTENCIAS");
        lblExistencias.setBounds(169, 98, 145, 16);
        panel.add(lblExistencias);

        jtf_stock = new JTextField();
        jtf_stock.setColumns(10);
        jtf_stock.setText(String.valueOf(producto.getStock()));
        jtf_stock.setBounds(169, 121, 145, 20);
        panel.add(jtf_stock);

        JPanel panel_primaryButton = new JPanel();
        contentPane.add(panel_primaryButton, BorderLayout.SOUTH);
        panel_primaryButton.setLayout(new GridLayout(0, 2, 0, 0));

        JButton btn_cancelar = new JButton("Cancelar");
        btn_cancelar.setMinimumSize(new Dimension(84, 50));
        btn_cancelar.setMaximumSize(new Dimension(84, 70));
        btn_cancelar.setPreferredSize(new Dimension(84, 50));
        btn_cancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_cancelar.setActionCommand("cmd_cancelar");
        btn_cancelar.addActionListener(eventoBoton);
        btn_cancelar.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_cancelar.setBorder(null);
        btn_cancelar.setForeground(Color.WHITE);
        btn_cancelar.setBackground(Color.RED);
        panel_primaryButton.add(btn_cancelar);

        JButton btn_aceptar = new JButton("Aceptar");
        btn_aceptar.setMinimumSize(new Dimension(84, 50));
        btn_aceptar.setMaximumSize(new Dimension(84, 70));
        btn_aceptar.setPreferredSize(new Dimension(84, 50));
        btn_aceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_aceptar.setActionCommand("cmd_acept_edit");
        btn_aceptar.addActionListener(eventoBoton);
        btn_aceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_aceptar.setBorder(null);
        btn_aceptar.setForeground(Color.WHITE);
        btn_aceptar.setBackground(new Color(66, 183, 42));
        panel_primaryButton.add(btn_aceptar);
    }

    @Override
    public void endUp(int valor) {
        switch (valor){
            case 0 -> {
                dispose();
            }
            case 1 -> {
                MySQLHelper mySQLHelper = new MySQLHelper();
                if (true){
                    if (!producto.getNombre().equals(jtf_nombre.getText()))
                        mySQLHelper.modificaNombre(Integer.parseInt(textField.getText()),jtf_nombre.getText());
                    if (!producto.getDescripcion().equals(jtf_descripcion.getText()))
                        mySQLHelper.modificaDescripcion(Integer.parseInt(textField.getText()),jtf_descripcion.getText());
                    if (producto.getPrecio() != Float.parseFloat(jtf_precio.getText()))
                        mySQLHelper.modificaPrecio(Integer.parseInt(textField.getText()),jtf_precio.getText());
                    if (producto.getStock() != Integer.parseInt(jtf_stock.getText()))
                        mySQLHelper.modificaStock(Integer.parseInt(textField.getText()),jtf_stock.getText());
                    dispose();
                } else JOptionPane.showMessageDialog(null, "ERROR");
            }
            case 2 -> {}
            case 3 -> {}
            default -> {}
        }
    }
}