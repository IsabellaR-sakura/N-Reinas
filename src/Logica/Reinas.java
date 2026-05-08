package Logica;
import java.util.ArrayList;
import java.util.Arrays;

public class Reinas {
	/*
	 * En este arreglo se guardan las reinas posicionadas de la siguiente manera
	 * Indice -> Fila
	 * Valor -> Columna
	 * De manera tal que si tenemos reinas[2] = 3
	 * Esto quiere decir que hay una reina en la fila -> 2, columna -> 3
	 */
	
	
	

    private int[] reinas;
    private int N;
    private int[][] soluciones;

    public Reinas(int n) {
        this.N = n;
        this.reinas = new int[N];
        this.soluciones = new int[0][];

        for (int i = 0; i < N; i++) {
            reinas[i] = -1;
        }
    }

    public int[][] getSoluciones() {
        return soluciones;
    }

	/* 
	 * Método para verificar si una posición es válida
	 * Lo que hacemos es tomar la fila y columna en que vamos a colocar nuestra reina
	 * y verificar si una de las otras reinas del arreglo está ya sea en la misma
	 * diagonal o en la misma columna 
	 */
    
    
    
    public boolean validarPosicion(int fila, int columna) {

        int i = 0;
        boolean salir = false;

        while (i < fila && !salir) {

            if (reinas[i] == columna) {
                salir = true;
            }

            if (Math.abs(fila - i) == Math.abs(columna - reinas[i])) {
                salir = true;
            }

            i++;
        }

        return !salir;
    }
    
	/* 
	 * Método recursivo de Backtracking
	 * Buscamos soluciones recursivamente y hacemos
	 * backtracking
	 */

    public void solucionar(int fila) {

        if (fila >= N) {

            // Guardar solución
        	 soluciones = Arrays.copyOf(soluciones, soluciones.length + 1);
             soluciones[soluciones.length - 1] = reinas.clone();
             return;
        }

		//Empezamos a iterar por filas y columnas
		for (int i = 0; i < N; i++) {
			//Si la fila actual y la columna actual son válidas
			if(validarPosicion(fila, i)) {
				//Entonces colocamos la reina en esa fila y esa columna
				reinas[fila] = i;
				//Continuamos con la siguiente fila
				solucionar(fila + 1);
			}
		}
		//Si nos salimos de la solución actual entonces hacemos backtracking
		reinas[fila] = -1;
	}
}


