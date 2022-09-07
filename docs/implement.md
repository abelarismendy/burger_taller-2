# Parte 1: Implementar el modelo de clases de la lógica

La primera tarea para realizar es implementar completamente el modelo de clases con la lógica de la aplicación: 
debe incluir todas las clases e interfaces mostradas en el diagrama de clases, y debe necesariamente implementar 
los métodos definidos en los modelos.

Usted puede hacer modificaciones al modelo y a los métodos siempre y cuando los declare: su entrega debe incluir 
un documento donde explique cada una de las desviaciones de su implementación con respecto al modelo y 
justificando la modificación

## Cambios en la implementación

Durante la implementación de la clase Restaurante observamos era necesario añadir un nuevo método para buscar productos en el menu. Esto, con el fin de que cuando se esten cargando los combos, se pueda acceder a cada producto a través de su nombre y así poder incluirlo en el combo. El método implementado es:

```java
    private ProductoMenu buscarProductoMenu(String nombreProducto) {
        for (ProductoMenu producto : menuBase) {
            if (producto.getNombre().equals(nombreProducto)) {
                return producto;
            }
        }
        return null;
    }
```

Además, en la clase ProductoAjustado se implementaron dos metodos para poder agregar ingredientes a cada una de las listas de ingredientes (agregados y eliminados). Ya que, de otra forma no habia manera de agregar ingredientes a estas listas.

```java
    public void agregarIngrediente(Ingrediente ingrediente) {
        agregados.add(ingrediente);
    }

    public void eliminarIngrediente(Ingrediente ingrediente) {
        eliminados.add(ingrediente);
    }
```

# [&#8592;](../README.md)