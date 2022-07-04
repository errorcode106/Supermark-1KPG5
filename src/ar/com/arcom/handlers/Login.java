package ar.com.arcom.handlers;

public interface Login {
    public String getUser();
    public String getPassword();
    public boolean stayLoggedIn();
    public void endUp(int valor);
}
