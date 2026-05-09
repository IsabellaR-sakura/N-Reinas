# N-Reinas Backtracking ♛
> ### Proyecto EDA: equipo #1
>> - Evelyn Gómez Aristizabal
>> - Ana Sofia Henao Bedoya
>> - Isabella Ramírez Tobón
>> - Juan Pablo Alzate Atehortua
>> - Julián Marín Ramírez

## Descripción
El objetivo es colocar `N` reinas en un tablero `N x N` sin que se ataquen entre sí
> Diferente fila, columna y diagonal

El algoritmo de Backtracking explora **recursivamente** las combinaciones posibles y **retrocede** si no se encuentra una solución

En el proyecto abordamos dos métodos:
- Mostrar la primer solución `src/Logica/Reinas_primerSolucion.java`
- Encontrar todas las soluciones posibles `src/Logica/Reinas.java`

Además de la **interfaz gráfica** para visualizar el tablero, las reinas y explorar las soluciones de forma interactiva
## Pseudocódigo
Planteamiento general para problemas de backtracking
```java
Función backtracking(...): {
	si condiciónSalida entonces: {
		guardarSolución()
    volver
  }
	recorrer opción en conjuntoOpciones: {
		si opciónValida(opción) entonces: {
			elegir(opción)
			backtracking(...)
			deshacer(opción)
		}
	}
	volver
}
```
### Se plantea la solución para 8-Reinas inicialmente
Arreglo global `solucion` inicia: `{-1,-1,-1,-1,-1,-1,-1,-1}`

Va llenando con las posiciones elegidas:
- fila → posición en el arreglo solucion
- columna → valor guardado en la posición

Llamado inicial: `solve8Reinas(0)`
```java
Función solve8Reinas(fila): {
	si fila igual 8 entonces: {
		guardar(solucion)
		volver
	}
	para columna desde 0 hasta 7 {
		si esSeguro(fila, columna) entonces: {
			solucion en [fila]: poner valor columna
			solve8Reinas( fila+1 )
			solucion en [fila]: poner valor -1
		}
	}
	volver
}
```
> [!NOTE]
> En cada llamada recursiva se ubica una reina en una fila distinta, la función `esSeguro(fila,col)` verifica que no exista otra reina en la misma columna o en las diagonales (donde la distancia entre filas es igual a la distancia entre columnas)