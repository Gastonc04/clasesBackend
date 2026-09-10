package Modelos;


// Sin Lombok
// Haciendo click derecho en la clase ponemos Generate... Getter and Setters y selecciono.
public class Pacientes {

    private int dni;
    private String nombre;
    private String apellido;
    private String codigo;


    //Crear este y el de abajo para hacer el constructor. OBLIGATORIO.
    public Pacientes() {
    }

    public Pacientes(int dni, String codigo, String nombre, String apellido) {
        this.dni = dni;
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
