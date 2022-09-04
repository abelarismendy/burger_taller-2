package hamburguesas;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Restaurante {
    private ArrayList<ProductoMenu> menuBase;
    private ArrayList<Ingrediente> ingredientes;
    private List<Pedido> pedidos;
    private List<Combo> combos;
    private Pedido pedidoEnCurso;

    public Restaurante() {
        this.menuBase = new ArrayList<ProductoMenu>();
        this.ingredientes = new ArrayList<Ingrediente>();
        this.pedidos = new ArrayList<Pedido>();
        this.combos = new ArrayList<Combo>();
    }

    public void iniciarPedido(String nombreCliente, String direccionCliente) {
        this.pedidoEnCurso = new Pedido(nombreCliente, direccionCliente);
    }

    public void cerrarYGuardarPedido() {
        pedidos.add(pedidoEnCurso);
        pedidoEnCurso = null;
    }

    public Pedido getPedidoEnCurso() {
        return pedidoEnCurso;
    }

    public ArrayList<ProductoMenu> getMenuBase() {
        return menuBase;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    private void cargarIngredientes(File archivoIngredientes) {
        // read file and create ingredients and add them to the list
        try {
            Scanner myReader = new Scanner(archivoIngredientes);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] datos = data.split(";");
                String nombre = datos[0];
                int costoAdicional = Integer.parseInt(datos[1]);
                Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional);
                ingredientes.add(ingrediente);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void cargarMenu(File archivoMenu) {
        // read file and create menu items and add them to the list
        try {
            Scanner myReader = new Scanner(archivoMenu);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] datos = data.split(";");
                String nombre = datos[0];
                int precio = Integer.parseInt(datos[1]);
                ProductoMenu producto = new ProductoMenu(nombre, precio);
                menuBase.add(producto);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void cargarCombos(File archivoCombos) {
        // read file and create combos and add them to the list
        try {
            Scanner myReader = new Scanner(archivoCombos);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] datos = data.split(";");
                String nombre = datos[0];
                double descuento = Double.parseDouble(datos[1]);
                Combo combo = new Combo(nombre, descuento);
                for (int i = 2; i < datos.length; i++) {
                    String nombreProducto = datos[i];
                    ProductoMenu producto = buscarProductoMenu(nombreProducto);
                    combo.agregarItemACombo(producto);
                }
                combos.add(combo);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private ProductoMenu buscarProductoMenu(String nombreProducto) {
        for (ProductoMenu producto : menuBase) {
            if (producto.getNombre().equals(nombreProducto)) {
                return producto;
            }
        }
        return null;
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos) {
        cargarIngredientes(archivoIngredientes);
        cargarMenu(archivoMenu);
        cargarCombos(archivoCombos);
    }

}
