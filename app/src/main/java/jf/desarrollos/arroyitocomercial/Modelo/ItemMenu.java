package jf.desarrollos.arroyitocomercial.Modelo;

public class ItemMenu {

    private String titulo;
    private int icono;

    public ItemMenu() {
    }

    public ItemMenu(String titulo, int icono) {
        this.titulo = titulo;
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }
}
