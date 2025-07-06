package com.gestion.almacen.modelo;

public class Producto {
    
    private static int contadorId = 0;
    
    private int id;
    private String nombre;
    private String marca; // Campo 'descripcion' reemplazado por 'marca'
    private String categoria; // Nuevo campo
    private double precio;
    private int stock;
    private String unidadDeMedida;
    private String moneda;

    public Producto(String nombre, String marca, String categoria, double precio, int stock, String unidad, String moneda) {
        this.id = ++contadorId; 
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.unidadDeMedida = unidad;
        this.moneda = moneda;
    }

    // --- Getters y Setters para todos los campos ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getUnidadDeMedida() { return unidadDeMedida; }
    public void setUnidadDeMedida(String unidadDeMedida) { this.unidadDeMedida = unidadDeMedida; }
    public String getMoneda() { return moneda; }
    public void setMoneda(String moneda) { this.moneda = moneda; }
}