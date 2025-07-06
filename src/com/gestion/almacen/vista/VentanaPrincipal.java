package com.gestion.almacen.vista;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.gestion.almacen.modelo.Producto;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuGestion;
    private JMenuItem itemSalir, itemAgregarProducto, itemEliminarProducto, itemEditarProducto;
    
    private ArrayList<Producto> inventario;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Almacén");
        setSize(1024, 600); // Aumentamos el ancho para las nuevas columnas
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inventario = new ArrayList<>();
        
        // --- Código del menú sin cambios ---
        menuBar = new JMenuBar();
        menuArchivo = new JMenu("Archivo");
        menuGestion = new JMenu("Gestión");
        itemSalir = new JMenuItem("Salir");
        itemAgregarProducto = new JMenuItem("Agregar Producto...");
        itemEliminarProducto = new JMenuItem("Eliminar Producto Seleccionado");
        itemEditarProducto = new JMenuItem("Editar Producto Seleccionado");

        itemAgregarProducto.addActionListener(e -> {
            FormularioProducto formulario = new FormularioProducto(this);
            formulario.setVisible(true);
            Producto productoRecibido = formulario.getNuevoProducto();
            if (productoRecibido != null) {
                inventario.add(productoRecibido);
                actualizarTabla();
            }
        });

        itemEliminarProducto.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un producto para eliminar.", "Ningún producto seleccionado", JOptionPane.WARNING_MESSAGE);
            } else {
                int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este producto?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    inventario.remove(filaSeleccionada);
                    actualizarTabla();
                }
            }
        });
        
        itemEditarProducto.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Por favor, seleccione un producto para editar.", "Ningún producto seleccionado", JOptionPane.WARNING_MESSAGE);
            } else {
                Producto productoAEditar = inventario.get(filaSeleccionada);
                FormularioProducto formulario = new FormularioProducto(VentanaPrincipal.this, productoAEditar);
                formulario.setVisible(true);
                actualizarTabla();
            }
        });

        menuArchivo.add(itemSalir);
        menuGestion.add(itemAgregarProducto);
        menuGestion.add(itemEditarProducto);
        menuGestion.add(itemEliminarProducto);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuGestion);
        add(menuBar, BorderLayout.NORTH);
        
        // --- Actualización de la Tabla ---
        String[] columnas = {"ID", "Nombre", "Marca", "Categoría", "Precio", "Moneda", "Stock", "Unidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : inventario) {
            Object[] fila = { 
                p.getId(), 
                p.getNombre(), 
                p.getMarca(), 
                p.getCategoria(), 
                p.getPrecio(), 
                p.getMoneda(), 
                p.getStock(), 
                p.getUnidadDeMedida() 
            };
            modeloTabla.addRow(fila);
        }
    }
}