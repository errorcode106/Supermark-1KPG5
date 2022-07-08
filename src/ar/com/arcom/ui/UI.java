package ar.com.arcom.ui;

import ar.com.arcom.Application;
import ar.com.arcom.bin.Articulo;
import ar.com.arcom.bin.Cliente;
import ar.com.arcom.handlers.EndUp;
import ar.com.arcom.handlers.UIHelper;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UI extends JFrame implements UIHelper, EndUp {
    public final int CLIENTE_UI = 1, ADMINISTRADOR_UI = 6;
    public final int CLIENTE_VER_CARRITOS = 3, CLIENTE_VER_PRODUCTOS = 2, CLIENTE_MODIFICAR_CARRITO = 4, CLIENTE_COMPRAR = 5;
    public final int ADMINISTRADOR_VER_PRODUCTOS = 11, ADMINISTRADOR_MODIFICAR_PRODUCTO = 12, ADMINISTRADOR_VER_USUARIOS = 13;

    private Application application;
    private EventoBoton eventoBoton;

    private JPanel contentPane, panel_table,panel_total;
    private JTable table;

    private int id, id_aBaseDatos;

    private JTextPane txt_info;
    private JLabel lbl_icon,lbl_logo,lbl_total,lbl_total_precio;
    private JButton btn_secundary_1, btn_secundary_2, btn_secundary_3, btn_primary;
    private JScrollPane scrollPane;

    public UI (Application application) throws HeadlessException {
        super();
        this.application = application;
        eventoBoton = new EventoBoton(this, application);

        // Inicialización del Panel central (ContentPane).
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);
        getContentPane().setLayout(new BorderLayout(0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));

        // Se configura la ventana principal: tamaño posicion y comportamiento al apretar boton cierre.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setSize(500, 600);
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(pantalla.width/2-(getWidth()/2), pantalla.height/2-(getHeight()/2));

        id = 0;
        id_aBaseDatos = 0;
        // Se cargan y se inicializan los componentes de la ventana principal y otros.
        initialize();
    }
    private void initialize(){
        loginScreen();
        init();

        if (application.getUsuario().getType() == 1) {
            configuraUI(ADMINISTRADOR_UI);
        }
        else {
            configuraUI(CLIENTE_UI);
        }
    }
    private void init(){
        setTitle(application.TITULO);
        // inicializa la tabla de datos
        panel_table = new JPanel();
        panel_table.setLayout(new BorderLayout(0, 0));

        // inicio de info bar
        txt_info = new JTextPane();
        txt_info.setText("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0) + application.getUsuario().getNombre().substring(1));
        txt_info.setForeground(Color.WHITE);
        txt_info.setOpaque(false);
        txt_info.setMargin(new Insets(10, 10, 10, 10));
        txt_info.setEditable(false);

        // inicio de infobar
        JPanel panel_info = new JPanel();
        panel_info.setBackground(new Color(24, 119, 242));
        panel_info.setLayout(new BorderLayout(0, 0));
        panel_table.add(panel_info, BorderLayout.NORTH);
        panel_info.add(txt_info, BorderLayout.CENTER);
        lbl_icon = new JLabel("");
        lbl_icon.setIcon(new ImageIcon("src/ar/com/arcom/icon/user_green.png"));
        panel_info.add(lbl_icon, BorderLayout.WEST);
        //fin de infobar

        lbl_logo = new JLabel();
        lbl_logo.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl_logo.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_logo.setIcon(new ImageIcon("src/ar/com/arcom/icon/logo.png"));
        panel_table.add(lbl_logo, BorderLayout.CENTER);
        getContentPane().add(panel_table, BorderLayout.CENTER);

        // Inicializa los botones primarios y secundarios
        JPanel panel_Buttons = new JPanel();
        panel_Buttons.setOpaque(false);
        panel_Buttons.setMinimumSize(new Dimension(10, 50));
        panel_Buttons.setPreferredSize(new Dimension(10, 70));
        getContentPane().add(panel_Buttons, BorderLayout.SOUTH);
        panel_Buttons.setLayout(new GridLayout(2, 1, 0, 0));
        JPanel panel_secundaryButtons = new JPanel();
        panel_secundaryButtons.setOpaque(false);
        panel_secundaryButtons.setPreferredSize(new Dimension(10, 40));
        panel_Buttons.add(panel_secundaryButtons);
        panel_secundaryButtons.setLayout(new GridLayout(0, 3, 0, 0));

        btn_secundary_1 = new JButton("Cerrar Sesión");
        btn_secundary_1.setActionCommand("cmd_logoff");
        btn_secundary_1.addActionListener(eventoBoton);
        btn_secundary_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_secundaryButtons.add(btn_secundary_1);
        btn_secundary_1.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_secundary_1.setMinimumSize(new Dimension(71, 40));
        btn_secundary_1.setForeground(Color.WHITE);
        btn_secundary_1.setBorder(null);
        btn_secundary_1.setBackground(new Color(24, 119, 242));

        btn_secundary_2 = new JButton("");
        btn_secundary_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_secundary_2.setEnabled(false);
        btn_secundary_2.setActionCommand("cmd_edit");
        btn_secundary_2.addActionListener(eventoBoton);
        panel_secundaryButtons.add(btn_secundary_2);
        btn_secundary_2.setMinimumSize(new Dimension(71, 40));
        btn_secundary_2.setForeground(Color.WHITE);
        btn_secundary_2.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_secundary_2.setBorder(null);
        btn_secundary_2.setBackground(new Color(24, 119, 242));

        btn_secundary_3 = new JButton("Ver carrito");
        btn_secundary_3.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_secundary_3.setActionCommand("cmd_to_shopping_cart");
        btn_secundary_3.addActionListener(eventoBoton);
        panel_secundaryButtons.add(btn_secundary_3);
        btn_secundary_3.setMinimumSize(new Dimension(71, 40));
        btn_secundary_3.setForeground(Color.WHITE);
        btn_secundary_3.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_secundary_3.setBorder(null);
        btn_secundary_3.setBackground(new Color(24, 119, 242));

        JPanel panel_primaryButton = new JPanel();
        panel_primaryButton.setSize(new Dimension(0, 50));
        panel_primaryButton.setOpaque(false);
        panel_primaryButton.setPreferredSize(new Dimension(10, 50));
        panel_primaryButton.setMinimumSize(new Dimension(10, 70));
        panel_Buttons.add(panel_primaryButton);
        panel_primaryButton.setLayout(new GridLayout(0, 1, 0, 0));

        btn_primary = new JButton("Ver productos");
        btn_primary.setMinimumSize(new Dimension(84, 50));
        btn_primary.setMaximumSize(new Dimension(84, 70));
        btn_primary.setPreferredSize(new Dimension(84, 50));
        btn_primary.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn_primary.setActionCommand("cmd_products");
        btn_primary.addActionListener(eventoBoton);
        panel_primaryButton.add(btn_primary);
        btn_primary.setFont(new Font("Dialog", Font.PLAIN, 20));
        btn_primary.setBorder(null);
        btn_primary.setForeground(Color.WHITE);
        btn_primary.setBackground(new Color(66, 183, 42));
    }
    private void update(){
        txt_info.setText("Usuario: " + application.getUsuario().getNombre().toUpperCase().charAt(0) + application.getUsuario().getNombre().substring(1));
    }
    public Application getApplication() {
        return application;
    }

    // Métodos asociados inicio de sesion
    public void loginScreen(){
        setVisible(false);
        JDialogLogin JDialogLogin = new JDialogLogin(this);
        JDialogLogin.setModal(true);
        JDialogLogin.setVisible(true);
        setVisible(true);
        if (txt_info != null) update();
    }
    private void agregarQuitar(){
        Articulo articulo = ((Cliente)(application.getUsuario())).getCarrito().getArticulo(id);
        if (articulo != null) {
            JDialogAgregarQuitar jDialogAgregarQuitar = new JDialogAgregarQuitar(articulo,application);
            jDialogAgregarQuitar.setModal(true);
            jDialogAgregarQuitar.setVisible(true);
        }
        configuraUI(3);
    }

    // ----------------------------------------------------------------
    // Métodos de opciones
    // ----------------------------------------------------------------
    private void menuPrincipalCliente(){
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);

        if (panel_total != null) panel_table.remove(panel_total);

        panel_table.updateUI();

        panel_table.add(lbl_logo);
        lbl_logo.setIcon(new ImageIcon("src/ar/com/arcom/icon/logo.png"));

        btn_secundary_1.setText("Cerrar Sesión");
        btn_secundary_1.setActionCommand("cmd_logoff");

        btn_secundary_2.setText("");
        btn_secundary_2.setActionCommand("cmd_edit");
        btn_secundary_2.setEnabled(false);

        btn_secundary_3.setText("Ver carrito");
        btn_secundary_3.setActionCommand("cmd_to_shopping_cart");

        btn_primary.setText("Ver productos");
        btn_primary.setActionCommand("cmd_products");

        getContentPane().repaint();
    }
    private void menuVerCarritoCliente(){
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);
        if (panel_total != null) panel_table.remove(panel_total);
        panel_table.updateUI();

        // inicializa la tabla de datos
        lbl_logo.setIcon(null);
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setOpaque(false);
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.setBorder(null);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.setGridColor(new Color(192, 192, 192));
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setModel(new DefaultTableModel(
                createListArticulos(((Cliente)(application.getUsuario())).getCarrito().getArticulos()),
                new String[]{
                        "ID",
                        "Producto",
                        "Descripcion",
                        "Precio",
                        "Cantidad",
                        "Sub Total"
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
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                id = (Integer) table.getValueAt(table.getSelectedRow(),0);
            }

        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);
        getContentPane().add(panel_table, BorderLayout.CENTER);
        panel_table.add(scrollPane, BorderLayout.CENTER);

        panel_total = new JPanel();
        panel_total.setFocusable(false);
        panel_table.add(panel_total, BorderLayout.SOUTH);
        panel_total.setLayout(new GridLayout(0, 2, 0, 0));

        lbl_total = new JLabel(" TOTAL:");
        lbl_total.setHorizontalAlignment(SwingConstants.LEFT);
        lbl_total.setFont(new Font("Dialog", Font.BOLD, 30));
        panel_total.add(lbl_total);

        lbl_total_precio = new JLabel(((Cliente)(application.getUsuario())).getCarrito().getTotales() + " ");
        lbl_total_precio.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl_total_precio.setFont(new Font("Dialog", Font.BOLD, 30));
        panel_total.add(lbl_total_precio);

        // Inicializa los botones primarios y secundarios
        btn_secundary_1.setText("Volver");
        btn_secundary_1.setActionCommand("cmd_inicio_cliente");

        btn_secundary_2.setText("Modificar");
        btn_secundary_2.setEnabled(true);
        btn_secundary_2.setActionCommand("cmd_edit");

        btn_secundary_3.setText("Agregar");
        btn_secundary_3.setActionCommand("cmd_products");

        btn_primary.setText("Comprar");
        btn_primary.setActionCommand("cmd_buy");

        getContentPane().repaint();
    }
    private void menuVerProductosCliente(){
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);
        if (panel_total != null) panel_table.remove(panel_total);
        panel_table.updateUI();

        lbl_logo.setIcon(null);
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setOpaque(false);
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.setBorder(null);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.setGridColor(new Color(192, 192, 192));
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setModel(new DefaultTableModel(
                createListProductos(eventoBoton.getActionHandler().obtenerProductos(true)),
                new String[]{
                        "ID",
                        "Producto",
                        "Descripcion",
                        "Precio",
                        "Existencias"
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
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setRowSelectionAllowed(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                id_aBaseDatos = (Integer)table.getValueAt(table.getSelectedRow(),0);
            }

        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(false);
        scrollPane.setViewportView(table);
        getContentPane().add(panel_table, BorderLayout.CENTER);
        panel_table.add(scrollPane, BorderLayout.CENTER);

        btn_secundary_1.setText("Volver");
        btn_secundary_1.setActionCommand("cmd_inicio_cliente");

        btn_secundary_2.setText("");
        btn_secundary_2.setEnabled(false);
        btn_secundary_2.setActionCommand("cmd_edit");

        btn_secundary_3.setText("Agregar");
        btn_secundary_3.setActionCommand("cmd_add");

        btn_primary.setText("Ver carrito");
        btn_primary.setActionCommand("cmd_to_shopping_cart");

        getContentPane().repaint();
    }

    private Object[][] createListProductos(List<List<String>> productos) {

        Object[][] objects = new Object[productos.size()][6];

        for(int i = 0; i < objects.length; i++){
            objects[i] = new Object[]{
                    Integer.parseInt(productos.get(i).get(0)),
                    productos.get(i).get(1),
                    productos.get(i).get(2),
                    Float.parseFloat(productos.get(i).get(3)),
                    Integer.parseInt(productos.get(i).get(4))
            };
        }
        return objects;
    }
    private Object[][] createListArticulos(List<Articulo> articulos){
        Object[][] objects = new Object[articulos.size()][6];

        for(int i = 0; i < objects.length; i++){
            objects[i] = new Object[]{
                    articulos.get(i).getId(),
                    articulos.get(i).getNombre(),
                    articulos.get(i).getDescripcion(),
                    articulos.get(i).getPrecio(),
                    articulos.get(i).getCantidad(),
                    articulos.get(i).getTotal()
            };
        }
        return objects;
    }

    // Métodos asociados al administrador
    private void createAdminInterface() {
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);

        if (panel_total != null) panel_table.remove(panel_total);

        panel_table.updateUI();

        panel_table.add(lbl_logo);
        lbl_logo.setIcon(new ImageIcon("src/ar/com/arcom/icon/logo.png"));

        btn_secundary_1.setText("Cerrar Sesión");
        btn_secundary_1.setActionCommand("cmd_logoff");

        btn_secundary_2.setText("");
        btn_secundary_2.setActionCommand("cmd_edit");
        btn_secundary_2.setEnabled(false);

        btn_secundary_3.setText("Ver Usuarios");
        btn_secundary_3.setActionCommand("cmd_users");

        btn_primary.setText("Ver productos");
        btn_primary.setActionCommand("cmd_products_admin");

        getContentPane().repaint();
    }
    private void menuVerProductosAdministrador(){
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);
        if (panel_total != null) panel_table.remove(panel_total);
        panel_table.updateUI();

        lbl_logo.setIcon(null);
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setOpaque(false);
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.setBorder(null);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.setGridColor(new Color(192, 192, 192));
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setModel(new DefaultTableModel(
                createListProductos(eventoBoton.getActionHandler().obtenerProductos(true)),
                new String[]{
                        "ID",
                        "Producto",
                        "Descripcion",
                        "Precio",
                        "Existencias"
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
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setRowSelectionAllowed(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                id_aBaseDatos = (Integer)table.getValueAt(table.getSelectedRow(),0);
            }

        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(false);
        scrollPane.setViewportView(table);
        getContentPane().add(panel_table, BorderLayout.CENTER);
        panel_table.add(scrollPane, BorderLayout.CENTER);

        btn_secundary_1.setText("Volver");
        btn_secundary_1.setActionCommand("cmd_inicio_administrador");

        btn_secundary_2.setText("");
        btn_secundary_2.setEnabled(false);
        btn_secundary_2.setActionCommand("cmd_edit");

        btn_secundary_3.setText("Modificar");
        btn_secundary_3.setActionCommand("cmd_edit");

        btn_primary.setText("Agregar nuevo producto");
        btn_primary.setActionCommand("cmd_add_new_product");

        getContentPane().repaint();
    }
    private void menuVerUsuarios(){
        int i = 0;
        while (i < panel_table.getComponents().length && panel_table.getComponents()[i].getClass() != JScrollPane.class) i++;
        if (i<panel_table.getComponents().length) panel_table.remove(scrollPane);
        if (panel_total != null) panel_table.remove(panel_total);
        panel_table.updateUI();

        lbl_logo.setIcon(null);
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.setOpaque(false);
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        table.setBorder(null);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.setGridColor(new Color(192, 192, 192));
        table.setForeground(new Color(0, 0, 0));
        table.setBackground(new Color(255, 255, 255));
        table.setModel(new DefaultTableModel(
                createListProductos(eventoBoton.getActionHandler().obtenerProductos(true)),
                new String[]{
                        "ID",
                        "Usuario",
                        "Pedidos",
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
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.setRowSelectionAllowed(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                id_aBaseDatos = (Integer)table.getValueAt(table.getSelectedRow(),0);
            }

        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setCellSelectionEnabled(false);
        scrollPane.setViewportView(table);
        getContentPane().add(panel_table, BorderLayout.CENTER);
        panel_table.add(scrollPane, BorderLayout.CENTER);

        btn_secundary_1.setText("Volver");
        btn_secundary_1.setActionCommand("cmd_inicio_administrador");

        btn_secundary_2.setText("");
        btn_secundary_2.setEnabled(false);
        btn_secundary_2.setActionCommand("cmd_ver_compras");

        btn_secundary_3.setText("Ver pedidos");
        btn_secundary_3.setActionCommand("cmd_ver_pedidos");

        btn_primary.setText("Ver productos en su carrito");
        btn_primary.setActionCommand("cmd_ver_carrito");

        getContentPane().repaint();
    }
    // ----------------------------------------------------------------
    // Métodos generales
    // ----------------------------------------------------------------

    // ----------------------------------------------------------------
    // Métodos de implementacion
    // ----------------------------------------------------------------
    @Override
    public void configuraUI(int valor) {
        switch(valor){
            case CLIENTE_UI -> menuPrincipalCliente();
            case CLIENTE_VER_PRODUCTOS -> menuVerProductosCliente();
            case CLIENTE_VER_CARRITOS -> menuVerCarritoCliente();
            case CLIENTE_MODIFICAR_CARRITO -> agregarQuitar();
            case CLIENTE_COMPRAR -> {}
            case ADMINISTRADOR_UI -> createAdminInterface();
            case ADMINISTRADOR_VER_PRODUCTOS -> menuVerProductosAdministrador();
            //case ADMINISTRADOR_MODIFICAR_PRODUCTO -> menuVerProductosAdministrador();
            case ADMINISTRADOR_VER_USUARIOS -> menuVerUsuarios();
            default -> {}
        }
    }
    @Override
    public int getID(String valor, boolean aDeBaseDatos) {
        return (aDeBaseDatos) ? id_aBaseDatos : id;
    }
    @Override
    public int getCantidad(int id, String valor) {
        String respuesta = JOptionPane.showInputDialog("Ingrese la cantidad.");
        int i = 0;
        while(i < respuesta.length() && Character.isDigit(respuesta.charAt(i))) i++;
        if (i < respuesta.length()) respuesta ="0";
        return Integer.parseInt(respuesta);
    }
    @Override
    public void error(int valor) {

    }
    @Override
    public String getNombre() {
        return null;
    }
    @Override
    public String getDescipcion() {
        return null;
    }
    @Override
    public float getPrecio() {
        return 0;
    }
    @Override
    public int getStock() {
        return 0;
    }
    @Override
    public void endUp(int valor) {
        setVisible(false);
        loginScreen();
    }
}
