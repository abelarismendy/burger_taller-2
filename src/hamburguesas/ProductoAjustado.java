package hamburguesas;
import java.util.List;
import java.util.ArrayList;

public class ProductoAjustado implements Producto {
    private ProductoMenu base;
    // list of ingredients to add
    private ArrayList<Ingrediente> agregados;
    // list of ingredients to remove
    private ArrayList<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu base) {
        this.base = base;
        this.agregados = new ArrayList<Ingrediente>();
        this.eliminados = new ArrayList<Ingrediente>();
    }

    public String getNombre() {
        return base.getNombre();
    }

    public int getPrecio() {
        int precio = base.getPrecio();
        for (Ingrediente ingrediente : agregados) {
            precio += ingrediente.getCostoAdicional();
        }
        return precio;
    }

    public String generarTextoFactura() {
        String texto = getNombre() + " $" + getPrecio();
        for (Ingrediente ingrediente : agregados) {
            texto += " + " + ingrediente.getNombre();
        }
        for (Ingrediente ingrediente : eliminados) {
            texto += " - " + ingrediente.getNombre();
        }
        return texto;
    }

    public void agregarIngrediente(Ingrediente ingrediente) {
        agregados.add(ingrediente);
    }

    public void eliminarIngrediente(Ingrediente ingrediente) {
        eliminados.add(ingrediente);
    }
}
