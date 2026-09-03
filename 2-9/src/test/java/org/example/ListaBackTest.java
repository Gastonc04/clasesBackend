package org.example;

import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.*;

class ListaBackTest {

    private Lista lista;

    @BeforeEach
    public void setup() {
        lista = new ListaBack();
    }

    @Test
    public void testStringEmpty() {
        String res = lista.toString();
        assertEquals("[]", res);
    }

    @Test
    public void textAdd() {
        lista.add(1);
        assertEquals(1, lista.size());
    }
}