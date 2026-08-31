package com.ivo.practica;

/**
 * Resolvé cada método sin modificar su firma.
 * Los tests unitarios sirven como especificación del comportamiento esperado.
 */
public final class Ejercicios {

    private Ejercicios() {
    }

    // =========================
    // VECTORES
    // =========================

    /** Ejercicio 1: sumar todos los elementos de un vector. */
    public static int sumarVector(int[] vector) {
        int suma = 0;
        for (int i = 0; i < vector.length; i++) {
            suma += vector[i];
        }
        return suma;
    }

    /** Ejercicio 2: devolver el mayor elemento del vector. */
    public static int maximoVector(int[] vector) {
        int mayor = vector[0];

        for (int i = 0; i < vector.length; i++) {
            if (vector[i] > mayor) {
                mayor = vector[i];
            }
        }
        return mayor;
    }

    /** Ejercicio 3: contar cuántos elementos son pares. */
    public static int contarPares(int[] vector) {
        int pares = 0;

        for (int num : vector) {
            if (num % 2 == 0) {
                pares += 1;
            }
        }
        return pares;
    }

    /** Ejercicio 4: devolver un nuevo vector con los elementos en orden inverso. */
    public static int[] invertirVector(int[] vector) {
        int[] inverso = new int[vector.length];

        for (int i = 0; i < vector.length; i++) {
            inverso[i] = vector[vector.length - 1 - i];
        }
        return inverso;
    }

    /** Ejercicio 5: indicar si un valor existe dentro del vector. */
    public static boolean contiene(int[] vector, int buscado) {
        boolean encontrado = false;
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == buscado) {
                encontrado = true;
                break;
            }
        }
        return encontrado;
    }

    // =========================
    // MATRICES
    // =========================

    /** Ejercicio 6: sumar todos los elementos de una matriz. */
    public static int sumarMatriz(int[][] matriz) {
        int i,j;
        int suma = 0;
        for (i = 0; i < matriz.length; i++) {
            for (j = 0; j < matriz[i].length; j++) {
                suma += matriz[i][j];
            }
        }
        return suma;
    }

    /** Ejercicio 7: sumar todos los valores de la fila indicada. */
    public static int sumarFila(int[][] matriz, int fila) {
        int suma = 0;
        for (int j = 0; j < matriz[fila].length; j++) {
            suma += matriz[fila][j];
        }
        return suma;
    }

    /** Ejercicio 8: devolver los elementos de la diagonal principal. */
    public static int[] diagonalPrincipal(int[][] matriz) {
        int[] elementos = new int 
        int i,j;
        int suma = 0;
        for (i = 0; i < matriz.length; i++) {
            for (j = 0; j < matriz[i].length; j++) {
                suma += matriz[i][j];
            }
        }
        return suma;
    }

    /** Ejercicio 9: devolver la matriz transpuesta. */
    public static int[][] transponer(int[][] matriz) {
        throw new UnsupportedOperationException("Ejercicio 9 pendiente");
    }

    /** Ejercicio 10: obtener el valor máximo de toda la matriz. */
    public static int maximoMatriz(int[][] matriz) {
        throw new UnsupportedOperationException("Ejercicio 10 pendiente");
    }

    // =========================
    // GENERACIÓN DE NÚMEROS
    // =========================

    /**
     * Ejercicio 11: generar un vector de n enteros aleatorios en [minInclusive, maxInclusive].
     * Tip: Random o ThreadLocalRandom y min + nextInt(max - min + 1).
     */
    public static int[] generarVectorAleatorio(int n, int minInclusive, int maxInclusive) {
        throw new UnsupportedOperationException("Ejercicio 11 pendiente");
    }

    /**
     * Ejercicio 12: generar una matriz de enteros aleatorios en [minInclusive, maxInclusive].
     */
    public static int[][] generarMatrizAleatoria(int filas, int columnas, int minInclusive, int maxInclusive) {
        throw new UnsupportedOperationException("Ejercicio 12 pendiente");
    }

    // Métodos auxiliares opcionales. Podés usarlos o borrarlos.
    static void validarVectorNoVacio(int[] vector) {
        if (vector == null || vector.length == 0) {
            throw new IllegalArgumentException("El vector no puede ser null ni estar vacío");
        }
    }

    static void validarMatrizNoVacia(int[][] matriz) {
        if (matriz == null || matriz.length == 0 || matriz[0] == null || matriz[0].length == 0) {
            throw new IllegalArgumentException("La matriz no puede ser null ni estar vacía");
        }
    }
}
