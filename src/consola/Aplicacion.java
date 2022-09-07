package consola;

import java.io.*;
import java.util.ArrayList;

import hamburguesas.Combo;
import hamburguesas.Ingrediente;
import hamburguesas.Pedido;
import hamburguesas.Producto;
import hamburguesas.ProductoAjustado;
import hamburguesas.ProductoMenu;
import hamburguesas.Restaurante;


public class Aplicacion {

	public Restaurante restaurante;

	public void ejecutarAplicacion() {
		restaurante = new Restaurante();
		File archivoIngredientes = new File("../data/ingredientes.txt");
		File archivoMenu = new File("../data/menu.txt");
		File archivoCombos = new File("../data/combos.txt");
		restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos);

		boolean continuar = true;

		while (continuar) {
			try {
				mostrarMenu();
				int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
				continuar = ejecutarOpcion(opcion);
			} catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}

	public void mostrarMenu() {
		System.out.println("Bienvenido al restaurante");
		System.out.println("1. Mostrar menú");
		System.out.println("2. Iniciar pedido");
		System.out.println("3. Agregar producto a pedido");
		System.out.println("4. Cerrar pedido y generar factura");
		System.out.println("5. Consultar la información de un pedido dado su id");
		System.out.println("6. Salir");
	}

	public boolean ejecutarOpcion(int opcionSeleccionada) {
		switch (opcionSeleccionada) {
		case 1:
			ejecutarMostrarMenu();
		case 2:
			ejecutarIniciarPedido();
		case 3:
			ejecutarAgregarProductoAPedido();
		case 4:
			ejecutarCerrarPedidoYGenerarFactura();
		case 5:
			ejecutarConsultarInformacionPedido();
		case 6:
			System.out.println("Saliendo de la aplicación...");
			return false;
		default:
			System.out.println("Debe seleccionar uno de los números de las opciones.");
		}
		return true;
	}

	private void ejecutarMostrarMenu() {
		ArrayList<ProductoMenu> menu = restaurante.getMenuBase();
		for (ProductoMenu producto : menu) {
			System.out.println(producto.getNombre() + " - " + producto.getPrecio());
		}
	}

	private void ejecutarIniciarPedido() {
		String nombreCliente = input("Por favor ingrese el nombre del cliente");
		String direccionCliente = input("Por favor ingrese la dirección del cliente");
		if (restaurante.getPedidoEnCurso() != null) {
			System.out.println("Ya hay un pedido en curso, por favor cierre el pedido actual antes de iniciar uno nuevo.");
		} else {
			restaurante.iniciarPedido(nombreCliente, direccionCliente);
		}
	}

	private void ejecutarAgregarProductoAPedido() {
		if (restaurante.getPedidoEnCurso() == null) {
			System.out.println("No hay un pedido en curso, por favor inicie un pedido antes de agregar productos.");
		} else {
			System.out.println("Desea agregar un combo o un producto individual?");
			System.out.println("1. Combo");
			System.out.println("2. Producto individual");
			try {
				int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
				if (opcion == 1) {
					ejecutarAgregarComboAPedido();
				} else if (opcion == 2) {
					ejecutarAgregarProductoIndividualAPedido();
				} else {
					System.out.println("Debe seleccionar uno de los números de las opciones.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				ejecutarAgregarProductoAPedido();
			}
		}
	}

	private void ejecutarAgregarComboAPedido() {
		ArrayList<Combo> combos = restaurante.getCombos();
		System.out.println("Por favor seleccione un combo: ");
		for (int i = 0; i < combos.size(); i++) {
			System.out.println((i + 1) + ". " + combos.get(i).getNombre() + " - " + combos.get(i).getPrecio());
		}
		try {
			int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
			if (opcion > 0 && opcion <= combos.size()) {
				Pedido pedido = restaurante.getPedidoEnCurso();
				Combo comboSeleccionado = combos.get(opcion - 1);
				pedido.agregarProducto(comboSeleccionado);
				System.out.println("Combo agregado al pedido.");
			} else {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				ejecutarAgregarComboAPedido();
			}
		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
			ejecutarAgregarComboAPedido();
		}
	}

	private void ejecutarAgregarProductoIndividualAPedido() {
		ArrayList<ProductoMenu> productos = restaurante.getMenuBase();
		System.out.println("Por favor seleccione un producto: ");
		for (int i = 0; i < productos.size(); i++) {
			System.out.println((i + 1) + ". " + productos.get(i).getNombre() + " - " + productos.get(i).getPrecio());
		}
		try {
			int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
			if (opcion > 0 && opcion <= productos.size()) {
				Pedido pedido = restaurante.getPedidoEnCurso();
				ProductoMenu productoSeleccionado = productos.get(opcion - 1);
				Producto productoModificado = preguntarSiDeseaAgregarAdicionales(productoSeleccionado);
				pedido.agregarProducto(productoModificado);
				System.out.println("Producto agregado al pedido.");
			} else {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				ejecutarAgregarProductoIndividualAPedido();
			}
		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
			ejecutarAgregarProductoIndividualAPedido();
		}
	}

	private Producto preguntarSiDeseaAgregarAdicionales(ProductoMenu productoSeleccionado) {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Agregar un ingrediente extra");
		System.out.println("2. Quitar ingrediente");
		System.out.println("3. Continuar (no agregar ni quitar más ingredientes)");
		try {
			int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
			if (opcion == 1) {
				ProductoAjustado productoModificado = new ProductoAjustado(productoSeleccionado);
				System.out.println("Por favor seleccione un ingrediente extra: ");
				productoModificado.agregarIngrediente(preguntarIngrediente());
				return preguntarSiDeseaAgregarAdicionales(productoModificado);
			} else if (opcion == 2) {
				System.out.println("Por favor seleccione un ingrediente a quitar: ");
				ProductoAjustado productoModificado = new ProductoAjustado(productoSeleccionado);
				productoModificado.eliminarIngrediente(preguntarIngrediente());
				return preguntarSiDeseaAgregarAdicionales(productoModificado);
			} else if (opcion == 3) {
				return productoSeleccionado;
			} else {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				return preguntarSiDeseaAgregarAdicionales(productoSeleccionado);
			}
		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
			return preguntarSiDeseaAgregarAdicionales(productoSeleccionado);
		}
	}

	private ProductoAjustado preguntarSiDeseaAgregarAdicionales(ProductoAjustado productoModificado) {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Agregar un ingrediente extra");
		System.out.println("2. Quitar ingrediente");
		System.out.println("3. Continuar (no agregar ni quitar más ingredientes)");
		try {
			int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
			if (opcion == 1) {
				System.out.println("Por favor seleccione un ingrediente extra: ");
				productoModificado.agregarIngrediente(preguntarIngrediente());
				return preguntarSiDeseaAgregarAdicionales(productoModificado);
			} else if (opcion == 2) {
				System.out.println("Por favor seleccione un ingrediente a quitar: ");
				productoModificado.eliminarIngrediente(preguntarIngrediente());
				return preguntarSiDeseaAgregarAdicionales(productoModificado);
			} else if (opcion == 3) {
				return productoModificado;
			} else {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				return preguntarSiDeseaAgregarAdicionales(productoModificado);
			}
		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
			return preguntarSiDeseaAgregarAdicionales(productoModificado);
		}
	}

	private Ingrediente preguntarIngrediente() {
		ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
		for (int i = 0; i < ingredientes.size(); i++) {
			System.out.println((i + 1) + ". " + ingredientes.get(i).getNombre());
		}
		try {
			int opcion = Integer.parseInt(input("Por favor ingrese una opción"));
			if (opcion > 0 && opcion <= ingredientes.size()) {
				return ingredientes.get(opcion - 1);
			} else {
				System.out.println("Debe seleccionar uno de los números de las opciones.");
				return preguntarIngrediente();
			}
		} catch (NumberFormatException e) {
			System.out.println("Debe seleccionar uno de los números de las opciones.");
			return preguntarIngrediente();
		}
	}

	private void ejecutarCerrarPedidoYGenerarFactura() {
		if (restaurante.getPedidoEnCurso() != null) {
			Pedido pedido = restaurante.getPedidoEnCurso();
			int numeroPedido = pedido.getIdPedido();
			String rutaArchivo = "../facturas/" + numeroPedido + ".txt";
			File archivo = new File(rutaArchivo);
			pedido.guardarFactura(archivo);
			restaurante.cerrarYGuardarPedido();

			System.out.println("Pedido cerrado y factura generada.");
		} else {
			System.out.println("No hay un pedido en curso.");
		}
	}
	
	private void ejecutarConsultarInformacionPedido() {
		ArrayList<Pedido> pedidos = restaurante.getPedidos();
		if (pedidos.size() > 0) {
			try {
				int numeroPedido = Integer.parseInt(input("Por favor ingrese el número de pedido"));
				Pedido pedido = restaurante.getPedido(numeroPedido);
				if (pedido != null) {
					File archivo = new File("../facturas/" + numeroPedido + ".txt");
					if (!archivo.exists()) {
						System.out.println("No se ha encontradoo la factura del pedido " + numeroPedido + ".");
						System.out.println("Generando factura...");
						pedido.guardarFactura(archivo);
						System.out.println("Factura generada.");
						System.out.println("Factura del pedido " + numeroPedido + ":");
					}

					try {
						BufferedReader br = new BufferedReader(new FileReader(archivo));
						String linea;
						while ((linea = br.readLine()) != null) {
							System.out.println(linea);
						}
						br.close();
					} catch (IOException e) {
						System.out.println("No se ha podido leer la factura del pedido " + numeroPedido + ".");
					}

				} else {
					System.out.println("No existe un pedido con ese número.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Debe ingresar un número de pedido válido.");
			}
		} else {
			System.out.println("No hay pedidos registrados.");
		}
	}

		/**
	 * Este método sirve para imprimir un mensaje en la consola pidiéndole
	 * información al usuario y luego leer lo que escriba el usuario.
	 * 
	 * @param mensaje El mensaje que se le mostrará al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		app.ejecutarAplicacion();
	}
}
