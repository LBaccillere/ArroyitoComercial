package jf.desarrollos.arroyitocomercial.Modelo;

import android.provider.BaseColumns;

public class Contract {

    public static abstract class ComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="comercio";

        public static final String ID = "id";
        public static final String RAZON_SOCIAL = "razon_social";
        public static final String NOMBRE_FANTASIA = "nombre_fantasia";
        public static final String ID_RUBRO = "id_rubro";
        public static final String ID_SUBRUBRO = "id_subrubro";
        public static final String DESCRIPCION = "descripcion";
        public static final String ACTIVO = "activo";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class RubroEntry implements BaseColumns {

        public static final String TABLE_NAME ="rubro";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class ProvinciaEntry implements BaseColumns {

        public static final String TABLE_NAME ="provincia";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class CiudadEntry implements BaseColumns {

        public static final String TABLE_NAME ="ciudad";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String ID_PROVINCIA = "id_provincia";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class DireccionEntry implements BaseColumns {

        public static final String TABLE_NAME ="direccion";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String ALTURA = "altura";
        public static final String NUMERO_PISO = "numero_piso";
        public static final String DEPARTAMENTO = "departamento";
        public static final String BARRIO = "barrio";
        public static final String ID_CIUDAD = "id_ciudad";
        public static final String ID_PROVINCIA = "id_provincia";
        public static final String TIPO = "tipo";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class SubrubroEntry implements BaseColumns {

        public static final String TABLE_NAME ="subrubro";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String ID_RUBRO = "id_rubro";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class DireccionComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="direccion_comercio";

        public static final String ID = "id";
        public static final String ID_DIRECCION = "id_direccion";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class HorarioEntry implements BaseColumns {

        public static final String TABLE_NAME ="horario";

        public static final String ID = "id";
        public static final String DIA = "dia";
        public static final String APERTURA_MANIANA = "apertura_maniana";
        public static final String CIERRE_MANIANA = "cierre_maniana";
        public static final String APERTURA_TARDE = "apertura_tarde";
        public static final String CIERRE_TARDE = "cierre_tarde";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class HorarioComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="horario_comercio";

        public static final String ID = "id";
        public static final String ID_HORARIO = "id_horario";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class SincronizacionEntry implements BaseColumns {

        public static final String TABLE_NAME ="sincronizacion";

        public static final String ID = "id";
        public static final String CLASE = "clase";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class FotoEntry implements BaseColumns {

        public static final String TABLE_NAME ="foto";

        public static final String ID = "id";
        public static final String NOMBRE_ARCHIVO = "nombre_archivo";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class FotoComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="foto_comercio";

        public static final String ID = "id";
        public static final String ID_FOTO = "id_foto";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class TelefonoEntry implements BaseColumns {

        public static final String TABLE_NAME ="telefono";

        public static final String ID = "id";
        public static final String NUMERO = "numero";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class TelefonoComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="telefono_comercio";

        public static final String ID = "id";
        public static final String ID_TELEFONO = "id_telefono";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class EmailEntry implements BaseColumns {

        public static final String TABLE_NAME ="email";

        public static final String ID = "id";
        public static final String DIRECCION = "direccion";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class EmailComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="email_comercio";

        public static final String ID = "id";
        public static final String ID_EMAIL = "id_email";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class SitioWebEntry implements BaseColumns {

        public static final String TABLE_NAME ="sitio_web";

        public static final String ID = "id";
        public static final String URL = "url";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }

    public static abstract class SitioWebComercioEntry implements BaseColumns {

        public static final String TABLE_NAME ="sitio_web_comercio";

        public static final String ID = "id";
        public static final String ID_SITIO_WEB = "id_sitio_web";
        public static final String ID_COMERCIO = "id_comercio";
        public static final String ULTIMA_OPERACION = "ultima_operacion";
        public static final String TIMESTAMP_MODIFICACION = "timestamp_modificacion";
        public static final String TIMESTAMP = "timestamp";
    }
}
