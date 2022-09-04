package hamburguesas;

import java.util.List;
import java.util.ArrayList;


public class Combo implements Producto {
    private double descuento;
    private String nombreCombo;
    private List<ProductoMenu> itemsCombo;

    public Combo(String nombreCombo, double descuento) {
        this.nombreCombo = nombreCombo;
        this.descuento = descuento;
        this.itemsCombo = new ArrayList<ProductoMenu>();
    }

    public void agregarItemACombo(ProductoMenu itemCombo) {
        itemsCombo.add(itemCombo);
    }

    public String getNombre() {
        return nombreCombo;
    }

    public int getPrecio() {
        int precio = 0;
        for (ProductoMenu item : itemsCombo) {
            precio += item.getPrecio();
        }
        return (int) (precio * (1 - descuento));
    }

    public String generarTextoFactura() {
        String texto = nombreCombo + " $" + getPrecio();
        return texto;
    }
}
