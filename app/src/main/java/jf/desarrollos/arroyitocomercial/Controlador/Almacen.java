package jf.desarrollos.arroyitocomercial.Controlador;

import android.util.Log;

import java.util.ArrayList;

import jf.desarrollos.arroyitocomercial.Modelo.Ciudad;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Email;
import jf.desarrollos.arroyitocomercial.Modelo.Foto;
import jf.desarrollos.arroyitocomercial.Modelo.Horario;
import jf.desarrollos.arroyitocomercial.Modelo.Provincia;
import jf.desarrollos.arroyitocomercial.Modelo.Rubro;
import jf.desarrollos.arroyitocomercial.Modelo.SitioWeb;
import jf.desarrollos.arroyitocomercial.Modelo.Subrubro;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;
import jf.desarrollos.arroyitocomercial.Vista.Actividad.SplashActivity;

public class Almacen {
    private static final String TAG = Almacen.class.getSimpleName();

    private ArrayList<Comercio> comercios = new ArrayList<>();
    private ArrayList<Rubro> rubros = new ArrayList<>();
    private ArrayList<Subrubro> subrubros = new ArrayList<>();
    private ArrayList<Horario> horarios = new ArrayList<>();
    private ArrayList<Provincia> provincias = new ArrayList<>();
    private ArrayList<Ciudad> ciudades = new ArrayList<>();
    private ArrayList<Direccion> direcciones = new ArrayList<>();
    private ArrayList<Telefono> telefonos = new ArrayList<>();
    private ArrayList<Email> emails = new ArrayList<>();
    private ArrayList<SitioWeb> sitiosWebs = new ArrayList<>();
    private ArrayList<Foto> fotos = new ArrayList<>();

    private Almacen(){}

    private static Almacen INSTANCIA = null;

    public static Almacen getInstancia() {
        if (INSTANCIA == null) {
            synchronized(Almacen.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = new Almacen();
                }
            }
        }
        return INSTANCIA;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public ArrayList<Comercio> getComercios() {
        ArrayList<Comercio> comerciosActivos = new ArrayList<>();
        for (Comercio comercio : comercios) {
            if (comercio.getActivo().equals("si") && !comercio.getUltimaOperacion().equals("E")) {
                comerciosActivos.add(comercio);
            }
        }
        return comerciosActivos;
    }

    public void setComercios(ArrayList<Comercio> comercios) {
        this.comercios = comercios;
    }

    public ArrayList<Rubro> getRubros() {
        ArrayList<Rubro> rubrosActivos = new ArrayList<>();
        for (Rubro rubro : rubros) {
            if (!rubro.getUltimaOperacion().equals("E")) {
                rubrosActivos.add(rubro);
            }
        }
        return rubrosActivos;
    }

    public void setRubros(ArrayList<Rubro> rubros) {
        this.rubros = rubros;
    }

    public ArrayList<Subrubro> getSubrubros() {
        ArrayList<Subrubro> subrurbosActivos = new ArrayList<>();
        for (Subrubro subrubro : subrubros) {
            if (!subrubro.getUltimaOperacion().equals("E")) {
                subrurbosActivos.add(subrubro);
            }
        }
        return subrurbosActivos;
    }

    public void setSubrubros(ArrayList<Subrubro> subrubros) {
        this.subrubros = subrubros;
    }

    public ArrayList<Horario> getHorarios() {
        ArrayList<Horario> horariosActivos = new ArrayList<>();
        for (Horario horario : horarios) {
            if (!horario.getUltimaOperacion().equals("E")) {
                horariosActivos.add(horario);
            }
        }
        return horariosActivos;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }

    public ArrayList<Provincia> getProvincias() {
        ArrayList<Provincia> provinciasActivas = new ArrayList<>();
        for (Provincia provincia : provincias) {
            if (!provincia.getUltimaOperacion().equals("E")) {
                provinciasActivas.add(provincia);
            }
        }
        return provinciasActivas;
    }

    public void setProvincias(ArrayList<Provincia> provincias) {
        this.provincias = provincias;
    }

    public ArrayList<Ciudad> getCiudades() {
        ArrayList<Ciudad> ciudadesActivas = new ArrayList<>();
        for (Ciudad ciudad : ciudades) {
            if (!ciudad.getUltimaOperacion().equals("E")) {
                ciudadesActivas.add(ciudad);
            }
        }
        return ciudadesActivas;
    }

    public void setCiudades(ArrayList<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }

    public ArrayList<Direccion> getDirecciones() {
        ArrayList<Direccion> direccionesActivas = new ArrayList<>();
        for (Direccion direccion : direcciones) {
            if (!direccion.getUltimaOperacion().equals("E")) {
                direccionesActivas.add(direccion);
            }
        }
        return direccionesActivas;
    }

    public void setDirecciones(ArrayList<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public ArrayList<Telefono> getTelefonos() {
        ArrayList<Telefono> telefonosActivos = new ArrayList<>();
        for (Telefono telefono : telefonos) {
            if (!telefono.getUltimaOperacion().equals("E")) {
                telefonosActivos.add(telefono);
            }
        }
        return telefonosActivos;
    }

    public void setTelefonos(ArrayList<Telefono> telefonos) {
        this.telefonos = telefonos;
    }

    public ArrayList<Email> getEmails() {
        ArrayList<Email> emailsActivos = new ArrayList<>();
        for (Email email : emails) {
            if (!email.getUltimaOperacion().equals("E")) {
                emailsActivos.add(email);
            }
        }
        return emailsActivos;
    }

    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }

    public ArrayList<SitioWeb> getSitiosWebs() {
        ArrayList<SitioWeb> sitiosWebsActivos = new ArrayList<>();
        for (SitioWeb sitioWeb : sitiosWebs) {
            if (!sitioWeb.getUltimaOperacion().equals("E")) {
                sitiosWebsActivos.add(sitioWeb);
            }
        }
        return sitiosWebsActivos;
    }

    public void setSitiosWebs(ArrayList<SitioWeb> sitiosWebs) {
        this.sitiosWebs = sitiosWebs;
    }

    public ArrayList<Foto> getFotos() {
        ArrayList<Foto> fotosActivas = new ArrayList<>();
        for (Foto foto : fotos) {
            if (!foto.getUltimaOperacion().equals("E")) {
                fotosActivas.add(foto);
            }
        }
        return fotosActivas;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        this.fotos = fotos;
    }
}
