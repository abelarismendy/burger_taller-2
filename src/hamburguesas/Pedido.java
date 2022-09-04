package hamburguesas;
import java.util.List;
import java.util.ArrayList;
// import java.io.File;

public class Pedido {
    static int numeroPedidos = 0;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private List<Producto> itemsPedido;

    public Pedido(String nombreCliente, String direccionCliente) {
        this.idPedido = numeroPedidos;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.itemsPedido = new ArrayList<Producto>();
        numeroPedidos++;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void agregarProducto(Producto nuevoItem) {
        itemsPedido.add(nuevoItem);
    }

    private int getPrecioNetoPedido() {
        int precioNeto = 0;
        for (Producto item : itemsPedido) {
            precioNeto += item.getPrecio();
        }
        return precioNeto;
    }

    private int getPrecioIVAPedido() {
        return (int) (getPrecioNetoPedido() * 0.19);
    }

    private int getPrecioTotalPedido() {
        return getPrecioNetoPedido() + getPrecioIVAPedido();
    }

    private String generarTextoFactura() {
        String datosPedido = "Pedido #" + idPedido + " para " + nombreCliente + " residente en " + direccionCliente ;
        String datosPrecio = "Precio neto: $" + getPrecioNetoPedido() + " IVA: $" + getPrecioIVAPedido() + " Total: $" + getPrecioTotalPedido();
        String datosItems = "Items del pedido:\n";
        for (Producto item : this.itemsPedido) {
            datosItems += item.generarTextoFactura() + "\n";
        }
        return datosPedido + "\n" + datosItems + "\n" + datosPrecio;
    }

    // public String guardarFactura(File archivo) {
    //     String textoFactura = generarTextoFactura();



    //     String mensaje = "Factura guardada en " + archivo.getAbsolutePath();

    //     return mensaje;
    // }
}
