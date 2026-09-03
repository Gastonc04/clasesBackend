class Persona {
    public void mostrar() {
        System.out.println("Persona");
    }
}

public class Empleado extends Persona {

    @Override
    public void mostar(int a) {
        System.out.println("Empleado");
    }

}