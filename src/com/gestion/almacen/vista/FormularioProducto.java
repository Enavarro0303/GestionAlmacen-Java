package com.gestion.almacen.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.gestion.almacen.modelo.Producto;

public class FormularioProducto extends JDialog {
    
    private static final long serialVersionUID = 1L;
    private JLabel lblNombre, lblMarca, lblCategoria, lblPrecio, lblStock, lblUnidad, lblMoneda;
    private JTextField txtNombre, txtMarca;
    private JComboBox<String> comboCategoria;
    private JTextField txtPrecio, txtStock;
    private JButton btnGuardar, btnCancelar;
    private JComboBox<String> comboUnidad;
    private JComboBox<String> comboMoneda;
    
    private Producto productoExistente;
    private Producto nuevoProducto;

    public FormularioProducto(JFrame owner) {
        super(owner, "Añadir Nuevo Producto", true);
        this.productoExistente = null;
        initComponents();
    }
    
    public FormularioProducto(JFrame owner, Producto productoAEditar) {
        super(owner, "Editar Producto", true);
        this.productoExistente = productoAEditar;
        initComponents();
        cargarDatosProducto();
    }

    private void initComponents() {
        setSize(450, 400);
        setLocationRelativeTo(getOwner());
        setLayout(null);

        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 30, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(140, 30, 250, 25);
        add(txtNombre);

        lblMarca = new JLabel("Marca:");
        lblMarca.setBounds(30, 70, 100, 25);
        add(lblMarca);
        txtMarca = new JTextField();
        txtMarca.setBounds(140, 70, 250, 25);
        add(txtMarca);

        lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 110, 100, 25);
        add(lblCategoria);
        
        // --- LÓGICA DE CATEGORÍA DINÁMICA ---
        DefaultComboBoxModel<String> categoriaModel = new DefaultComboBoxModel<>();
        categoriaModel.addElement("Herramientas Manuales");
        categoriaModel.addElement("Herramientas Eléctricas");
        categoriaModel.addElement("Iluminación");
        categoriaModel.addElement("Tornillería");
        categoriaModel.addElement("Pinturas");

        comboCategoria = new JComboBox<>(categoriaModel);
        comboCategoria.setEditable(true);
        comboCategoria.setBounds(140, 110, 250, 25);
        add(comboCategoria);

        // Listener para AÑADIR una nueva categoría a la lista
        comboCategoria.addActionListener(e -> {
            if ("comboBoxEdited".equals(e.getActionCommand())) {
                String nuevaCategoria = (String) comboCategoria.getEditor().getItem();
                if (nuevaCategoria != null && !nuevaCategoria.trim().isEmpty()) {
                    // Evitar duplicados
                    if (((DefaultComboBoxModel<String>) comboCategoria.getModel()).getIndexOf(nuevaCategoria) == -1) {
                        comboCategoria.addItem(nuevaCategoria);
                    }
                }
            }
        });

        // Listener para ELIMINAR una categoría de la lista con la tecla Suprimir
        comboCategoria.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    String itemSeleccionado = (String) comboCategoria.getSelectedItem();
                    if (itemSeleccionado != null) {
                        int confirmacion = JOptionPane.showConfirmDialog(
                            FormularioProducto.this, 
                            "¿Desea eliminar la categoría '" + itemSeleccionado + "' de la lista?",
                            "Confirmar Eliminación",
                            JOptionPane.YES_NO_OPTION);
                        
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            comboCategoria.removeItem(itemSeleccionado);
                        }
                    }
                }
            }
        });


        lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(30, 150, 100, 25);
        add(lblPrecio);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(140, 150, 100, 25);
        add(txtPrecio);

        lblMoneda = new JLabel("Moneda:");
        lblMoneda.setBounds(250, 150, 60, 25);
        add(lblMoneda);
        String[] monedas = {"PEN", "USD"};
        comboMoneda = new JComboBox<>(monedas);
        comboMoneda.setBounds(310, 150, 80, 25);
        add(comboMoneda);

        lblStock = new JLabel("Stock:");
        lblStock.setBounds(30, 190, 100, 25);
        add(lblStock);
        txtStock = new JTextField();
        txtStock.setBounds(140, 190, 100, 25);
        add(txtStock);

        lblUnidad = new JLabel("Unidad:");
        lblUnidad.setBounds(250, 190, 60, 25);
        add(lblUnidad);
        String[] unidades = {"UND", "KG", "MTR", "LTS", "CAJA"};
        comboUnidad = new JComboBox<>(unidades);
        comboUnidad.setBounds(310, 190, 80, 25);
        add(comboUnidad);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(100, 300, 100, 30);
        add(btnGuardar);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(220, 300, 100, 30);
        add(btnCancelar);
        
        btnCancelar.addActionListener(e -> dispose());
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = txtNombre.getText();
                    String marca = txtMarca.getText();
                    String categoria = (String) comboCategoria.getSelectedItem();
                    double precio = Double.parseDouble(txtPrecio.getText());
                    int stock = Integer.parseInt(txtStock.getText());
                    String unidad = (String) comboUnidad.getSelectedItem();
                    String moneda = (String) comboMoneda.getSelectedItem();
                    
                    if (productoExistente != null) {
                        productoExistente.setNombre(nombre);
                        productoExistente.setMarca(marca);
                        productoExistente.setCategoria(categoria);
                        productoExistente.setPrecio(precio);
                        productoExistente.setStock(stock);
                        productoExistente.setUnidadDeMedida(unidad);
                        productoExistente.setMoneda(moneda);
                    } else {
                        nuevoProducto = new Producto(nombre, marca, categoria, precio, stock, unidad, moneda);
                    }
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(FormularioProducto.this, "Error: El precio y el stock deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    private void cargarDatosProducto() {
        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombre());
            txtMarca.setText(productoExistente.getMarca());
            comboCategoria.setSelectedItem(productoExistente.getCategoria());
            txtPrecio.setText(String.valueOf(productoExistente.getPrecio()));
            txtStock.setText(String.valueOf(productoExistente.getStock()));
            comboUnidad.setSelectedItem(productoExistente.getUnidadDeMedida());
            comboMoneda.setSelectedItem(productoExistente.getMoneda());
        }
    }
    
    public Producto getNuevoProducto() {
        return nuevoProducto;
    }
}