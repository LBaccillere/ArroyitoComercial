package jf.desarrollos.arroyitocomercial.Controlador;

import java.util.ArrayList;

import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Email;
import jf.desarrollos.arroyitocomercial.Modelo.Foto;
import jf.desarrollos.arroyitocomercial.Modelo.Horario;
import jf.desarrollos.arroyitocomercial.Modelo.SitioWeb;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;

public class Busqueda {

    public ArrayList<Comercio> getComerciosPorSubrubro(int idSubrubro) {

        ArrayList<Comercio> comercios = new ArrayList<>();

        if (!Almacen.getInstancia().getComercios().isEmpty()) {
            for (Comercio comercio : Almacen.getInstancia().getComercios()) {
                if (comercio.getSubrubro().getId() == idSubrubro) {
                    comercios.add(comercio);
                }
            }
        }

        return comercios;
    }

    public ArrayList<Comercio> getComerciosPorRubro(int idRubro) {

        ArrayList<Comercio> comercios = new ArrayList<>();

        if (!Almacen.getInstancia().getComercios().isEmpty()) {
            for (Comercio comercio : Almacen.getInstancia().getComercios()) {
                if (comercio.getRubro().getId() == idRubro) {
                    comercios.add(comercio);
                }
            }
        }

        return comercios;
    }

    public Comercio getComercio(int id) {

        Comercio comercio = null;

        if (!Almacen.getInstancia().getComercios().isEmpty()) {
            for (Comercio comercioAux : Almacen.getInstancia().getComercios()) {
                if (comercioAux.getId() == id) {
                    comercio = comercioAux;
                }
            }
        }

        return comercio;
    }

    public Horario getHorario(int id) {

        Horario horario = null;

        if (!Almacen.getInstancia().getComercios().isEmpty()) {
            for (Horario horarioAux : Almacen.getInstancia().getHorarios()) {
                if (horarioAux.getId() == id) {
                    horario = horarioAux;
                }
            }
        }

        return horario;
    }

    public Telefono getTelefono(int id) {

        Telefono telefono = null;

        if (!Almacen.getInstancia().getTelefonos().isEmpty()) {
            for (Telefono telefonoAux : Almacen.getInstancia().getTelefonos()) {
                if (telefonoAux.getId() == id) {
                    telefono = telefonoAux;
                }
            }
        }

        return telefono;
    }

    public Email getEmail(int id) {

        Email email = null;

        if (!Almacen.getInstancia().getEmails().isEmpty()) {
            for (Email emailAux : Almacen.getInstancia().getEmails()) {
                if (emailAux.getId() == id) {
                    email = emailAux;
                }
            }
        }

        return email;
    }

    public Direccion getDireccion(int id) {

        Direccion direccion = null;

        if (!Almacen.getInstancia().getDirecciones().isEmpty()) {
            for (Direccion direccionAux : Almacen.getInstancia().getDirecciones()) {
                if (direccionAux.getId() == id) {
                    direccion = direccionAux;
                }
            }
        }

        return direccion;
    }

    public SitioWeb getSitioWeb(int id) {

        SitioWeb sitioWeb = null;

        if (!Almacen.getInstancia().getSitiosWebs().isEmpty()) {
            for (SitioWeb sitioWebAux : Almacen.getInstancia().getSitiosWebs()) {
                if (sitioWebAux.getId() == id) {
                    sitioWeb = sitioWebAux;
                }
            }
        }

        return sitioWeb;
    }

    public Foto getFoto(int id) {

        Foto foto = null;

        if (!Almacen.getInstancia().getFotos().isEmpty()) {
            for (Foto fotoAux : Almacen.getInstancia().getFotos()) {
                if (fotoAux.getId() == id) {
                    foto = fotoAux;
                }
            }
        }

        return foto;
    }
}
