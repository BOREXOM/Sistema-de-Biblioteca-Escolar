// Usuario.java (Modelo/entidad para usuarios - COMPLETADA con todos los getters/setters)
package negocio;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String rol;

    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    // Setters (opcionales, pero útiles si necesitas modificar)
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}