package com.ivo.practica;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EjerciciosTest {

    @Test
    void ejercicio01_sumarVector() {
        assertEquals(15, Ejercicios.sumarVector(new int[]{1, 2, 3, 4, 5}));
        assertEquals(0, Ejercicios.sumarVector(new int[]{-2, 2, -5, 5}));
    }

    @Test
    void ejercicio02_maximoVector() {
        assertEquals(9, Ejercicios.maximoVector(new int[]{3, 9, 2, 7}));
        assertEquals(-1, Ejercicios.maximoVector(new int[]{-8, -1, -4}));
    }

    @Test
    void ejercicio03_contarPares() {
        assertEquals(4, Ejercicios.contarPares(new int[]{1, 2, 4, 6, 7, 8}));
        assertEquals(0, Ejercicios.contarPares(new int[]{1, 3, 5}));
    }

    @Test
    void ejercicio04_invertirVector() {
        assertArrayEquals(new int[]{4, 3, 2, 1}, Ejercicios.invertirVector(new int[]{1, 2, 3, 4}));
        assertArrayEquals(new int[]{7}, Ejercicios.invertirVector(new int[]{7}));
    }

    @Test
    void ejercicio05_contiene() {
        assertTrue(Ejercicios.contiene(new int[]{4, 8, 15, 16}, 15));
        assertFalse(Ejercicios.contiene(new int[]{4, 8, 15, 16}, 23));
    }

    @Test
    void ejercicio06_sumarMatriz() {
        int[][] m = {{1, 2, 3}, {4, 5, 6}};
        assertEquals(21, Ejercicios.sumarMatriz(m));
    }

    @Test
    void ejercicio07_sumarFila() {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(15, Ejercicios.sumarFila(m, 1));
        assertThrows(IllegalArgumentException.class, () -> Ejercicios.sumarFila(m, 3));
    }

    @Test
    void ejercicio08_diagonalPrincipal() {
        int[][] m = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertArrayEquals(new int[]{1, 5, 9}, Ejercicios.diagonalPrincipal(m));
    }

    @Test
    void ejercicio09_transponer() {
        int[][] m = {{1, 2, 3}, {4, 5, 6}};
        int[][] esperado = {{1, 4}, {2, 5}, {3, 6}};
        assertArrayEquals(esperado, Ejercicios.transponer(m));
    }

    @Test
    void ejercicio10_maximoMatriz() {
        assertEquals(12, Ejercicios.maximoMatriz(new int[][]{
                {-3, 5, 1},
                {12, 0, 7}
        }));
    }

    @Test
    void ejercicio11_generarVectorAleatorio() {
        int[] vector = Ejercicios.generarVectorAleatorio(8, 1, 10);
        assertEquals(8, vector.length);
        for (int valor : vector) {
            assertTrue(valor >= 1 && valor <= 10, "Valor fuera de rango: " + valor);
        }

        assertThrows(IllegalArgumentException.class,
                () -> Ejercicios.generarVectorAleatorio(0, 1, 10));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicios.generarVectorAleatorio(5, 10, 1));
    }

    @Test
    void ejercicio12_generarMatrizAleatoria() {
        int[][] matriz = Ejercicios.generarMatrizAleatoria(3, 4, -2, 5);
        assertEquals(3, matriz.length);
        for (int[] fila : matriz) {
            assertEquals(4, fila.length);
            for (int valor : fila) {
                assertTrue(valor >= -2 && valor <= 5, "Valor fuera de rango: " + valor);
            }
        }

        assertThrows(IllegalArgumentException.class,
                () -> Ejercicios.generarMatrizAleatoria(0, 2, 1, 5));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicios.generarMatrizAleatoria(2, 0, 1, 5));
        assertThrows(IllegalArgumentException.class,
                () -> Ejercicios.generarMatrizAleatoria(2, 2, 8, 3));
    }
}
