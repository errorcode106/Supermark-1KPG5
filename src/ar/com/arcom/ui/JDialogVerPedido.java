package ar.com.arcom.ui;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Producto;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.mysql.MySQLHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class JDialogVerPedido extends JDialog implements EndUp {
    private EventoBoton eventoBoton;
    private Application application;
    private final JPanel contentPane;
    private JTextField textField, jtf_nombre, jtf_descripcion, jtf_precio, jtf_stock;
    private JTable table;
    private Producto producto, productoAux;

    public JDialogVerPedido(List<List<String>> lista, Application application) {
        this.eventoBoton = new EventoBoton(application, this);
        this.producto = producto;
        this.application = application;

        setSize( 560, 473);
        setResizable(true);
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
        panel.setPreferredSize(new Dimension(10, 80));
        panel.setMinimumSize(new Dimension(10, 100));
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(2, 0, 0, 0));

        JLabel lblId = new JLabel("Cliente");
        lblId.setBackground(new Color(24, 119, 242));
        lblId.setOpaque(true);
        lblId.setForeground(Color.WHITE);
        lblId.setHorizontalAlignment(SwingConstants.CENTER);
        lblId.setFont(new Font("Dialog", Font.BOLD, 20));
        panel.add(lblId);

        JPanel panel_3 = new JPanel();
        panel.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 4, 0, 0));

        JLabel lblPedidoNumero = new JLabel("PEDIDO DE NUMERO:");
        panel_3.add(lblPedidoNumero);

        textField = new JTextField();
        textField.setBorder(null);
        textField.setText("0");
        textField.setOpaque(false);
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setEditable(false);
        textField.setColumns(10);
        panel_3.add(textField);

        JLabel lblFechaEmision = new JLabel(" FECHA DE EMISION:");
        panel_3.add(lblFechaEmision);

        JTextField jtf_fecha = new JTextField();
        jtf_fecha.setBorder(null);
        jtf_fecha.setText("2022/07/07 22:22:22 ");
        jtf_fecha.setOpaque(false);
        jtf_fecha.setHorizontalAlignment(SwingConstants.LEFT);
        jtf_fecha.setEditable(false);
        jtf_fecha.setColumns(10);
        panel_3.add(jtf_fecha);

        JPanel panel_primaryButton = new JPanel();
        contentPane.add(panel_primaryButton, BorderLayout.SOUTH);
        panel_primaryButton.setLayout(new GridLayout(0, 1, 0, 0));

        JButton btn_aceptar = new JButton("Cerrar");
        btn_aceptar.setMinimumSize(new Dimension(84, 50));
        btn_aceptar.setMaximumSize(new Dimension(84, 70));
        btn_aceptar.setPreferredSize(new Dimension(84, 50));
        btn_aceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_aceptar.setActionCommand("cmd_products");
        //btn_aceptar.addActionListener(eventoBoton);
        btn_aceptar.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_aceptar.setBorder(null);
        btn_aceptar.setForeground(Color.WHITE);
        btn_aceptar.setBackground(new Color(66, 183, 42));
        panel_primaryButton.add(btn_aceptar);

        JPanel panel_table = new JPanel();
        contentPane.add(panel_table, BorderLayout.CENTER);
        panel_table.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel_table.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();

        table.setModel(new DefaultTableModel(
                new Object[][] {
                        {null, null, null, null, null, null},
                },
                new String[] {
                        "ID",
                        "NOMBRE",
                        "DESCRIPCION",
                        "PRECIO",
                        "CANTIDAD",
                        "SUBTOTAL"
                }
        ){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        table.setRowSelectionAllowed(true);

        scrollPane.setViewportView(table);

        JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(10, 30));
        panel_table.add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new GridLayout(0, 2, 0, 0));

        JLabel lblNewLabel = new JLabel("TOTAL:");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        panel_1.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("$555,75 ");
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        panel_1.add(lblNewLabel_1);

        JPanel panel_2 = new JPanel();
        panel_table.add(panel_2, BorderLayout.NORTH);
        panel_2.setLayout(new GridLayout(2, 0, 0, 0));
    }

    @Override
    public void endUp(int valor) {
        switch (valor){
            case 0 -> {
                dispose();
            }
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            default -> {}
        }
    }
}