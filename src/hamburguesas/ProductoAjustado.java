package hamburguesas;
import java.util.List;

public class ProductoAjustado implements Producto {
    private ProductoMenu base;
    // list of ingredients to add
    private List<Ingrediente> agregados;
    // list of ingredients to remove
    private List<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu base) {
        this.base = base;
    }

    public String getNombre() {
        return base.getNombre();
    }

    public int getPrecio() {
        int precio = base.getPrecio();
        for (Ingrediente ingrediente : agregados) {
            precio += ingrediente.getCostoAdicional();
        }
        for (Ingrediente ingrediente : eliminados) {
            precio -= ingrediente.getCostoAdicional();
        }
        return precio;
    }

    public String generarTextoFactura() {
        String texto = base.generarTextoFactura();
        for (Ingrediente ingrediente : agregados) {
            texto += " + " + ingrediente.getNombre();
        }
        for (Ingrediente ingrediente : eliminados) {
            texto += " - " + ingrediente.getNombre();
        }
        return texto;
    }
}
