package com.gestion.almacen.main;

import javax.swing.SwingUtilities;
import com.gestion.almacen.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaPrincipal().setVisible(true);
            }
        });
    }
}