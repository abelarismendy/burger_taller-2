# Parte 3: Modificaciones a la aplicación

En esta parte usted debe incorporar las siguientes modificaciones a su aplicación (asegúrese de que su entrega 
incluya tanto la [versión original](https://github.com/DPOO202202-AM/taller-2/releases/tag/v2.4) como la versión modificada):
1. Debe ser capaz de manejar las bebidas por separado del resto de productos del menú, y la lista de bebidas 
disponibles debe cargarse desde un archivo diferente al resto del menú.
2. La factura de un pedido debe incluir la cantidad de calorías del pedido completo, las cuales deben estar 
basadas en las calorías de cada elemento que lo conformen.
3. Cuando se cierre un pedido se debe verificar si alguien había hecho un pedido idéntico. Implemente esta 
funcionalidad sobreescribiendo el método equals de pedido y de las otras clases que sean necesarias.
**Piense con mucho cuidado cómo va a saber de qué tipo específico es un elemento particular dentro de 
un pedido**

## Implementación

### 1. Bebidas

Se incluye un nuevo archivo ([bebidas.txt](../data/bebidas.txt)) en la carpeta data para cargar las bebidas por separado. Se añade un nuevo atributo a la clase Restaurante:
```java
this.bebidas = new ArrayList<ProductoMenu>();
```

Además, se añade un metodo que permite cargar las bebidas. Funciona exactamente igual que el metodo de cargar menú pero esta vez se añade a la lista de bebidas.

```java
    private void cargarBebidas(File archivoBebidas) {
        // read file and create menu items and add them to the list
        try {
            Scanner myReader = new Scanner(archivoBebidas);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] datos = data.split(";");
                String nombre = datos[0];
                int precio = Integer.parseInt(datos[1]);
                ProductoMenu producto = new ProductoMenu(nombre, precio);
                bebidas.add(producto);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
```
# [&#8592;](../README.md)