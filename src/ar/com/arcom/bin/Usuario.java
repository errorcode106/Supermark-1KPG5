package ar.com.arcom.bin;

public abstract class Usuario {
    private String usuario;
    private  int type;

    public Usuario(String usuario, int type) {
        this.usuario = usuario;
        this.type = type;
    }

    public String getNombre() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public int getType() {
        return type;
    }
}
