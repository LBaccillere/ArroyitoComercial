package jf.desarrollos.arroyitocomercial.Utilidades;

public class Constantes {

    //public static final String SERVIDOR = "http://jfdesarrollos.com/Arroyito%20Comercial/";
    //public static final String SERVIDOR = "http://192.168.0.13/Arroyito%20Comercial/";
    //public static final String SERVIDOR = "http://192.168.0.5/Arroyito%20Comercial/";
    public static final String SERVIDOR = "http://arroyitocomercial.com/App/rest/";
    public static final String SERVIDOR2 = "http://arroyitocomercial.com/";

    public static final String GET_SQL = SERVIDOR + "get-service.php?";
    public static final String GET_SQL_IMAGEN = SERVIDOR + "get-photo.php?";
    public static final String SET_TOKEN = SERVIDOR + "set-service.php";
    public static final String SET_SQL_EMAIL_NUEVO_COMERCIO = SERVIDOR + "set-service-nuevo-comercio.php";
    public static final String SET_SQL_EMAIL_CONTACTO = SERVIDOR + "set-service-contacto.php";
    public static final String SET_SQL_REGISTRAR_TOKEN = SERVIDOR2 + "index.php?url=registro/registrar-usuario-app";

    public static final String GET_EVENTOS_POR_COMERCIO = GET_SQL + "objeto=evento&consulta=SELECT%20*%20FROM%20evento%20WHERE%20id_comercio=";
    public static final String GET_EVENTOS = GET_SQL + "objeto=evento&consulta=SELECT%20*%20FROM%20evento%20WHERE%20(fecha_fin>NOW())%20ORDER%20BY%20timestamp%20DESC";
    public static final String GET_NOTIFICACIONES = GET_SQL + "objeto=notificacion&consulta=SELECT%20*%20FROM%20notificacion%20ORDER%20BY%20timestamp%20DESC";
    public static final String GET_BANNERS = GET_SQL + "objeto=publicidad&consulta=SELECT%20*%20FROM%20publicidad";
    public static final String GET_OFERTAS_POR_COMERCIO = GET_SQL + "objeto=oferta&consulta=SELECT%20*%20FROM%20oferta%20WHERE%20id_comercio=";
    public static final String GET_OFERTAS = GET_SQL + "objeto=oferta&consulta=SELECT%20*%20FROM%20oferta%20WHERE%20(fecha_fin>NOW())%20ORDER%20BY%20timestamp%20DESC";
    public static final String GET_COMERCIOS_A_SINCRONIZAR = GET_SQL + "objeto=comercio&consulta=SELECT%20*%20FROM%20comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_RUBROS_A_SINCRONIZAR = GET_SQL + "objeto=rubro&consulta=SELECT%20*%20FROM%20rubro%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_SUBRUBROS_A_SINCRONIZAR = GET_SQL + "objeto=subrubro&consulta=SELECT%20*%20FROM%20subrubro%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_HORARIOS_A_SINCRONIZAR = GET_SQL + "objeto=horario&consulta=SELECT%20*%20FROM%20horario%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_HORARIOS_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=horario_comercio&consulta=SELECT%20*%20FROM%20horario_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_TELEFONOS_A_SINCRONIZAR = GET_SQL + "objeto=telefono&consulta=SELECT%20*%20FROM%20telefono%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_TELEFONOS_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=telefono_comercio&consulta=SELECT%20*%20FROM%20telefono_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_EMAILS_A_SINCRONIZAR = GET_SQL + "objeto=email&consulta=SELECT%20*%20FROM%20email%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_EMAILS_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=email_comercio&consulta=SELECT%20*%20FROM%20email_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_SITIOS_WEBS_A_SINCRONIZAR = GET_SQL + "objeto=sitio_web&consulta=SELECT%20*%20FROM%20sitio_web%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_SITIOS_WEBS_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=sitio_web_comercio&consulta=SELECT%20*%20FROM%20sitio_web_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_FOTOS_A_SINCRONIZAR = GET_SQL + "objeto=foto&consulta=SELECT%20*%20FROM%20foto%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_FOTOS_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=foto_comercio&consulta=SELECT%20*%20FROM%20foto_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_DIRECCIONES_A_SINCRONIZAR = GET_SQL + "objeto=direccion&consulta=SELECT%20*%20FROM%20direccion%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_DIRECCIONES_COMERCIO_A_SINCRONIZAR = GET_SQL + "objeto=direccion_comercio&consulta=SELECT%20*%20FROM%20direccion_comercio%20WHERE%20FROM_UNIXTIME(timestamp_modificacion)>=";
    public static final String GET_PROVINCIAS_A_SINCRONIZAR = GET_SQL + "objeto=provincia&consulta=SELECT%20p.*%20FROM%20direccion%20AS%20d%20INNER%20JOIN%20provincia%20AS%20p%20ON%20d.id_provincia%20=%20p.id%20AND%20FROM_UNIXTIME(p.timestamp_modificacion)>=";
    public static final String GET_CIUDADES_A_SINCRONIZAR = GET_SQL + "objeto=ciudad&consulta=SELECT%20c.*%20FROM%20direccion%20AS%20d%20INNER%20JOIN%20ciudad%20AS%20c%20ON%20d.id_ciudad%20=%20c.id%20WHERE%20FROM_UNIXTIME(c.timestamp_modificacion)>=";

    public static final String MENSAJE_SIN_CONEXION = "No es posible conectarse ahora.";
    public static final String MENSAJE_ERROR_DATOS = "Error al traer los datos. Intente actualizar más tarde";
    public static final String MENSAJE_ERROR_SITIO_WEB = "El sitio web es incorrecto. Podrás reportarlo en nuestro contacto.";
}
