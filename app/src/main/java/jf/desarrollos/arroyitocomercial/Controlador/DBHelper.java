package jf.desarrollos.arroyitocomercial.Controlador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;

import jf.desarrollos.arroyitocomercial.Modelo.Ciudad;
import jf.desarrollos.arroyitocomercial.Modelo.Comercio;
import jf.desarrollos.arroyitocomercial.Modelo.Direccion;
import jf.desarrollos.arroyitocomercial.Modelo.Email;
import jf.desarrollos.arroyitocomercial.Modelo.Foto;
import jf.desarrollos.arroyitocomercial.Modelo.Horario;
import jf.desarrollos.arroyitocomercial.Modelo.Provincia;
import jf.desarrollos.arroyitocomercial.Modelo.Rubro;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.HorarioComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SincronizacionEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SubrubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.RubroEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.ProvinciaEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.CiudadEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.TelefonoComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.EmailComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.SitioWebComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.DireccionComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Contract.FotoComercioEntry;
import jf.desarrollos.arroyitocomercial.Modelo.Sincronizacion;
import jf.desarrollos.arroyitocomercial.Modelo.SitioWeb;
import jf.desarrollos.arroyitocomercial.Modelo.Subrubro;
import jf.desarrollos.arroyitocomercial.Modelo.Telefono;
import jf.desarrollos.arroyitocomercial.Vista.Actividad.SplashActivity;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "arroyito_comercial.db";
    public static final int DATABASE_VERSION = 1;

    /*private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + TABLE_TEAM + " ADD COLUMN " + COLUMN_COACH + " string;";
    */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + ComercioEntry.TABLE_NAME + " ( " +
                ComercioEntry.ID + " INTEGER AUTO_INCREMENT, " +
                ComercioEntry.RAZON_SOCIAL + " TEXT NOT NULL, " +
                ComercioEntry.NOMBRE_FANTASIA + " TEXT NULL, " +
                ComercioEntry.ID_RUBRO + " INTEGER NOT NULL, " +
                ComercioEntry.ID_SUBRUBRO + " INTEGER NOT NULL, " +
                ComercioEntry.DESCRIPCION + " text, " +
                ComercioEntry.ACTIVO + " TEXT NOT NULL DEFAULT 'no'," +
                ComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                ComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                ComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + ComercioEntry.ID + ") " +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + RubroEntry.TABLE_NAME + " (" +
                RubroEntry.ID + " INTEGER NOT NULL," +
                RubroEntry.NOMBRE + " TEXT NOT NULL," +
                RubroEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                RubroEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                RubroEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + RubroEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + SubrubroEntry.TABLE_NAME + " (" +
                SubrubroEntry.ID + " INTEGER," +
                SubrubroEntry.NOMBRE + " TEXT NOT NULL," +
                SubrubroEntry.ID_RUBRO + " INTEGER NOT NULL," +
                SubrubroEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                SubrubroEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                SubrubroEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + SubrubroEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + SincronizacionEntry.TABLE_NAME + " (" +
                SincronizacionEntry.ID + " INTEGER AUTO_INCREMENT," +
                SincronizacionEntry.CLASE + " TEXT NOT NULL DEFAULT ''," +
                SincronizacionEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                SincronizacionEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                SincronizacionEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + SincronizacionEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + HorarioEntry.TABLE_NAME + " (" +
                HorarioEntry.ID + " INTEGER NOT NULL," +
                HorarioEntry.DIA + " TEXT NOT NULL DEFAULT ''," +
                HorarioEntry.APERTURA_MANIANA + " time NOT NULL DEFAULT '00:00:00'," +
                HorarioEntry.CIERRE_MANIANA + " time NOT NULL DEFAULT '00:00:00'," +
                HorarioEntry.APERTURA_TARDE + " time NOT NULL DEFAULT '00:00:00'," +
                HorarioEntry.CIERRE_TARDE + " time NOT NULL DEFAULT '00:00:00'," +
                HorarioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                HorarioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                HorarioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + HorarioComercioEntry.TABLE_NAME + " (" +
                HorarioComercioEntry.ID + " INTEGER," +
                HorarioComercioEntry.ID_HORARIO + " INT NOT NULL," +
                HorarioComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                HorarioComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                HorarioComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                HorarioComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + ProvinciaEntry.TABLE_NAME + " (" +
                ProvinciaEntry.ID + " INTEGER," +
                ProvinciaEntry.NOMBRE + " TEXT NOT NULL," +
                ProvinciaEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                ProvinciaEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                ProvinciaEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + ProvinciaEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + CiudadEntry.TABLE_NAME + " (" +
                CiudadEntry.ID + " INTEGER," +
                CiudadEntry.NOMBRE + " TEXT NOT NULL," +
                CiudadEntry.ID_PROVINCIA + " INTEGER NOT NULL," +
                CiudadEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                CiudadEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                CiudadEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + CiudadEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + DireccionEntry.TABLE_NAME + " (" +
                DireccionEntry.ID + " INTEGER," +
                DireccionEntry.NOMBRE + " TEXT NOT NULL," +
                DireccionEntry.ALTURA + " TEXT NOT NULL," +
                DireccionEntry.NUMERO_PISO + " TEXT NOT NULL," +
                DireccionEntry.DEPARTAMENTO + " TEXT NOT NULL," +
                DireccionEntry.BARRIO + " TEXT NOT NULL," +
                DireccionEntry.ID_CIUDAD + " TEXT NOT NULL," +
                DireccionEntry.ID_PROVINCIA + " TEXT NOT NULL," +
                DireccionEntry.TIPO + " TEXT NOT NULL," +
                DireccionEntry.LATITUD + " INTEGER NOT NULL," +
                DireccionEntry.LONGITUD + " INTEGER NOT NULL," +
                DireccionEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                DireccionEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                DireccionEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + DireccionEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + DireccionComercioEntry.TABLE_NAME + " (" +
                DireccionComercioEntry.ID + " INTEGER," +
                DireccionComercioEntry.ID_DIRECCION + " INT NOT NULL," +
                DireccionComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                DireccionComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                DireccionComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                DireccionComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TelefonoEntry.TABLE_NAME + " (" +
                TelefonoEntry.ID + " INTEGER," +
                TelefonoEntry.NUMERO + " TEXT NOT NULL," +
                TelefonoEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                TelefonoEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                TelefonoEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + TelefonoEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TelefonoComercioEntry.TABLE_NAME + " (" +
                TelefonoComercioEntry.ID + " INTEGER," +
                TelefonoComercioEntry.ID_TELEFONO + " INT NOT NULL," +
                TelefonoComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                TelefonoComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                TelefonoComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                TelefonoComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + EmailEntry.TABLE_NAME + " (" +
                EmailEntry.ID + " INTEGER," +
                EmailEntry.DIRECCION + " TEXT NOT NULL," +
                EmailEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                EmailEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                EmailEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + EmailEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + EmailComercioEntry.TABLE_NAME + " (" +
                EmailComercioEntry.ID + " INTEGER," +
                EmailComercioEntry.ID_EMAIL + " INT NOT NULL," +
                EmailComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                EmailComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                EmailComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                EmailComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + SitioWebEntry.TABLE_NAME + " (" +
                SitioWebEntry.ID + " INTEGER," +
                SitioWebEntry.URL + " TEXT NOT NULL," +
                SitioWebEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                SitioWebEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                SitioWebEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + SitioWebEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + SitioWebComercioEntry.TABLE_NAME + " (" +
                SitioWebComercioEntry.ID + " INTEGER," +
                SitioWebComercioEntry.ID_SITIO_WEB + " INT NOT NULL," +
                SitioWebComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                SitioWebComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                SitioWebComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                SitioWebComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + FotoEntry.TABLE_NAME + " (" +
                FotoEntry.ID + " INTEGER," +
                FotoEntry.NOMBRE_ARCHIVO + " TEXT NOT NULL," +
                FotoEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                FotoEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                SitioWebEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + SitioWebEntry.ID + ")" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + FotoComercioEntry.TABLE_NAME + " (" +
                FotoComercioEntry.ID + " INTEGER," +
                FotoComercioEntry.ID_FOTO + " INT NOT NULL," +
                FotoComercioEntry.ID_COMERCIO + " INT NOT NULL," +
                FotoComercioEntry.ULTIMA_OPERACION + " TEXT NOT NULL DEFAULT 'A'," +
                FotoComercioEntry.TIMESTAMP_MODIFICACION + " TEXT NOT NULL DEFAULT ''," +
                FotoComercioEntry.TIMESTAMP + " TEXT NOT NULL DEFAULT ''," +
                "PRIMARY KEY (" + HorarioComercioEntry.ID + ")" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /*db.execSQL(DATABASE_ALTER_TEAM_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SubrubroEntry.TABLE_NAME);*/
        onCreate(sqLiteDatabase);
    }

    public String getUltimaSincronizacion (String nombreTabla){

        int ultimoRegistro = 1;
        String where = SincronizacionEntry.CLASE + " = ?";
        String[] whereArgs = {nombreTabla};
        String orderBy = String.valueOf(SincronizacionEntry.ID)+" DESC";
        String limit = String.valueOf(ultimoRegistro);
        Cursor c = this.getReadableDatabase()
                .query(
                        SincronizacionEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        orderBy,
                        limit);

        String timestampModificacion = "";
        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            timestampModificacion = c.getString(c.getColumnIndex(SincronizacionEntry.TIMESTAMP_MODIFICACION));
        }

        c.close();
        this.close();
        return timestampModificacion;
    }

    public long insertarSincronización(Sincronizacion sincronizacion) {
        long id = this.getWritableDatabase().insert(
                SincronizacionEntry.TABLE_NAME,
                null,
                sincronizacion.toContentValues()
        );
        this.close();
        return id;
    }

    public void actualizarSincronizacion(Sincronizacion sincronizacion) {
        String where = SincronizacionEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(sincronizacion.getId())};
        this.getWritableDatabase().update(
                SincronizacionEntry.TABLE_NAME,
                sincronizacion.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarComercio(Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                ComercioEntry.TABLE_NAME,
                null,
                comercio.toContentValues()
        );

        this.close();
        return id;
    }

    public Comercio getComercio(int id) {

        Comercio comercio;
        String where = ComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        ComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idComercio = c.getInt(c.getColumnIndex(ComercioEntry.ID));
            String razonSocial = c.getString(c.getColumnIndex(ComercioEntry.RAZON_SOCIAL));
            String nombreFantasia = c.getString(c.getColumnIndex(ComercioEntry.NOMBRE_FANTASIA));
            int idRubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_RUBRO));
            int idSubrubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_SUBRUBRO));
            String descripcion = c.getString(c.getColumnIndex(ComercioEntry.DESCRIPCION));
            String activo = c.getString(c.getColumnIndex(ComercioEntry.ACTIVO));
            String ultimaOperacion = c.getString(c.getColumnIndex(ComercioEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP));

            Rubro rubro;
            if (idRubro == 0) {
                rubro = null;
            } else {
                rubro = getRubro(idRubro);
            }

            Subrubro subrubro;
            if (idSubrubro == 0) {
                subrubro = null;
            } else {
                subrubro = getSubrubro(idSubrubro);
            }

            comercio = new Comercio(idComercio, razonSocial, nombreFantasia, rubro, subrubro, descripcion, activo, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            comercio = null;
        }

        c.close();
        this.close();
        return comercio;
    }

    public ArrayList<Comercio> getComerciosPorSubrubro(int idSubrubroBusqueda) {

        ArrayList<Comercio> comercios = new ArrayList<>();
        String where = ComercioEntry.ID_SUBRUBRO + " = ?";
        String[] whereArgs = {Integer.toString(idSubrubroBusqueda)};
        Cursor c = this.getReadableDatabase()
                .query(
                        ComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        ComercioEntry.NOMBRE_FANTASIA,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idComercio = c.getInt(c.getColumnIndex(ComercioEntry.ID));
                String razonSocial = c.getString(c.getColumnIndex(ComercioEntry.RAZON_SOCIAL));
                String nombreFantasia = c.getString(c.getColumnIndex(ComercioEntry.NOMBRE_FANTASIA));
                int idRubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_RUBRO));
                int idSubrubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_SUBRUBRO));
                String descripcion = c.getString(c.getColumnIndex(ComercioEntry.DESCRIPCION));
                String activo = c.getString(c.getColumnIndex(ComercioEntry.ACTIVO));
                String ultimaOperacion = c.getString(c.getColumnIndex(ComercioEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP));

                Rubro rubro;
                if (idRubro == 0) {
                    rubro = null;
                } else {
                    rubro = getRubro(idRubro);
                }

                Subrubro subrubro;
                if (idSubrubro == 0) {
                    subrubro = null;
                } else {
                    subrubro = getSubrubro(idSubrubro);
                }

                comercios.add(new Comercio(idComercio,razonSocial, nombreFantasia, rubro, subrubro, descripcion, activo, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return comercios;
    }

    public ArrayList<Comercio> getComerciosPorRubro(int idRubroBusqueda) {

        ArrayList<Comercio> comercios = new ArrayList<>();
        String where = ComercioEntry.ID_RUBRO + " = ?";
        String[] whereArgs = {Integer.toString(idRubroBusqueda)};
        Cursor c = this.getReadableDatabase()
                .query(
                        ComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        ComercioEntry.NOMBRE_FANTASIA,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idComercio = c.getInt(c.getColumnIndex(ComercioEntry.ID));
                String razonSocial = c.getString(c.getColumnIndex(ComercioEntry.RAZON_SOCIAL));
                String nombreFantasia = c.getString(c.getColumnIndex(ComercioEntry.NOMBRE_FANTASIA));
                int idRubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_RUBRO));
                int idSubrubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_SUBRUBRO));
                String descripcion = c.getString(c.getColumnIndex(ComercioEntry.DESCRIPCION));
                String activo = c.getString(c.getColumnIndex(ComercioEntry.ACTIVO));
                String ultimaOperacion = c.getString(c.getColumnIndex(ComercioEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP));

                Rubro rubro;
                if (idRubro == 0) {
                    rubro = null;
                } else {
                    rubro = getRubro(idRubro);
                }

                Subrubro subrubro;
                if (idSubrubro == 0) {
                    subrubro = null;
                } else {
                    subrubro = getSubrubro(idSubrubro);
                }

                comercios.add(new Comercio(idComercio,razonSocial, nombreFantasia, rubro, subrubro, descripcion, activo, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return comercios;
    }

    public ArrayList<Comercio> getComercios() {
        Log.i(TAG,"getComercios ");
        ArrayList<Comercio> comercios = new ArrayList<>();

        String orderBy = ComercioEntry.NOMBRE_FANTASIA + " ASC";

        Cursor c = this.getReadableDatabase()
                .query(
                        ComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);
        Log.i(TAG,"CANTIDAD COMERCIO "+c.getCount());
        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idComercio = c.getInt(c.getColumnIndex(ComercioEntry.ID));
                String razonSocial = c.getString(c.getColumnIndex(ComercioEntry.RAZON_SOCIAL));
                String nombreFantasia = c.getString(c.getColumnIndex(ComercioEntry.NOMBRE_FANTASIA));
                int idRubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_RUBRO));
                int idSubrubro = c.getInt(c.getColumnIndex(ComercioEntry.ID_SUBRUBRO));
                String descripcion = c.getString(c.getColumnIndex(ComercioEntry.DESCRIPCION));
                String activo = c.getString(c.getColumnIndex(ComercioEntry.ACTIVO));
                String ultimaOperacion = c.getString(c.getColumnIndex(ComercioEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(ComercioEntry.TIMESTAMP));
                Log.i(TAG,"ID COMERCIO "+idComercio);
                Rubro rubro;
                if (idRubro == 0) {
                    rubro = null;
                } else {
                    rubro = getRubro(idRubro);
                }

                Subrubro subrubro;
                if (idSubrubro == 0) {
                    subrubro = null;
                } else {
                    subrubro = getSubrubro(idSubrubro);
                }

                comercios.add(new Comercio(idComercio,razonSocial, nombreFantasia, rubro, subrubro, descripcion, activo, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return comercios;
    }

    public void eliminarComercio(int id) {
        String where = ComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                ComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarComercio(Comercio comercio) {
        String where = ComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(comercio.getId())};
        this.getWritableDatabase().update(
                ComercioEntry.TABLE_NAME,
                comercio.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarRubro(Rubro rubro) {
        long id = this.getWritableDatabase().insert(
                RubroEntry.TABLE_NAME,
                null,
                rubro.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Rubro> getRubros() {

        ArrayList<Rubro> rubros = new ArrayList<>();

        String orderBy = RubroEntry.NOMBRE + " ASC";

        Cursor c = this.getReadableDatabase()
                .query(
                        RubroEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idRubro = c.getInt(c.getColumnIndex(RubroEntry.ID));
                String nombre = c.getString(c.getColumnIndex(RubroEntry.NOMBRE));
                String ultimaOperacion = c.getString(c.getColumnIndex(RubroEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(RubroEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(RubroEntry.TIMESTAMP));

                rubros.add(new Rubro(idRubro, nombre, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return rubros;
    }

    public Rubro getRubro(int id) {

        Rubro rubro;
        String where = RubroEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        RubroEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idRubro = c.getInt(c.getColumnIndex(RubroEntry.ID));
            String nombre = c.getString(c.getColumnIndex(RubroEntry.NOMBRE));
            String ultimaOperacion = c.getString(c.getColumnIndex(RubroEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(RubroEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(RubroEntry.TIMESTAMP));

            rubro = new Rubro(idRubro, nombre, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            rubro = null;
        }

        c.close();
        this.close();
        return rubro;
    }

    public void eliminarRubros(int id) {
        String where = RubroEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                RubroEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarRubro(Rubro rubro) {
        String where = RubroEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(rubro.getId())};
        this.getWritableDatabase().update(
                RubroEntry.TABLE_NAME,
                rubro.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarSubrubro(Subrubro subrubro) {
        long id = this.getWritableDatabase().insert(
                SubrubroEntry.TABLE_NAME,
                null,
                subrubro.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Subrubro> getSubrubros() {

        ArrayList<Subrubro> subrubros = new ArrayList<>();

        String orderBy = SubrubroEntry.NOMBRE + " ASC";

        Cursor c = this.getReadableDatabase()
                .query(
                        SubrubroEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idSubrubro = c.getInt(c.getColumnIndex(SubrubroEntry.ID));
                String nombre = c.getString(c.getColumnIndex(SubrubroEntry.NOMBRE));
                int idRubro = c.getInt(c.getColumnIndex(SubrubroEntry.ID_RUBRO));
                String ultimaOperacion = c.getString(c.getColumnIndex(SubrubroEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(SubrubroEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(SubrubroEntry.TIMESTAMP));

                Rubro rubro;
                if (idRubro == 0){
                    rubro = null;
                } else {
                    rubro = getRubro(idRubro);
                }

                Subrubro subrubro = new Subrubro(idSubrubro, nombre, ultimaOperacion, timestampModificacion, timestamp);
                subrubro.setRubro(rubro);
                subrubros.add(subrubro);
            }
        } finally {
            c.close();
        }

        this.close();

        return subrubros;
    }

    public Subrubro getSubrubro(int id) {

        Subrubro subrubro;
        String where = RubroEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        SubrubroEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idSubrubro = c.getInt(c.getColumnIndex(SubrubroEntry.ID));
            String nombre = c.getString(c.getColumnIndex(SubrubroEntry.NOMBRE));
            int idRubro = c.getInt(c.getColumnIndex(SubrubroEntry.ID_RUBRO));
            String ultimaOperacion = c.getString(c.getColumnIndex(SubrubroEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(SubrubroEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(SubrubroEntry.TIMESTAMP));

            Rubro rubro;
            if (idRubro == 0){
                rubro = null;
            } else {
                rubro = getRubro(idRubro);
            }

            subrubro = new Subrubro(idSubrubro, nombre, ultimaOperacion, timestampModificacion, timestamp);
            subrubro.setRubro(rubro);
        } else {
            subrubro = null;
        }

        c.close();
        this.close();
        return subrubro;
    }

    public void eliminarSubrubros(int id) {
        String where = SubrubroEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                SubrubroEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarSubrubros(Subrubro subrubro) {
        String where = SubrubroEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(subrubro.getId())};
        this.getWritableDatabase().update(
                SubrubroEntry.TABLE_NAME,
                subrubro.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarHorario(Horario horario) {
        long id = this.getWritableDatabase().insert(
                HorarioEntry.TABLE_NAME,
                null,
                horario.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Horario> getHorarios() {

        ArrayList<Horario> horarios = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        HorarioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(HorarioEntry.ID));
                String dia = c.getString(c.getColumnIndex(HorarioEntry.DIA));
                Time aperturaMañana =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.APERTURA_MANIANA)));
                Time cierreMañana =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.CIERRE_MANIANA)));
                Time aperturaTarde =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.APERTURA_TARDE)));
                Time cierreTarde =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.CIERRE_TARDE)));
                String ultimaOperacion = c.getString(c.getColumnIndex(HorarioEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(HorarioEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(HorarioEntry.TIMESTAMP));

                horarios.add(new Horario(id, dia, aperturaMañana, cierreMañana, aperturaTarde, cierreTarde, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return horarios;
    }

    public Horario getHorario(int id) {

        Horario horario;
        String where = HorarioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};

        Cursor c = this.getReadableDatabase()
                .query(
                        HorarioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                int idHorario = c.getInt(c.getColumnIndex(HorarioEntry.ID));
                String dia = c.getString(c.getColumnIndex(HorarioEntry.DIA));
                Time aperturaMañana =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.APERTURA_MANIANA)));
                Time cierreMañana =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.CIERRE_MANIANA)));
                Time aperturaTarde =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.APERTURA_TARDE)));
                Time cierreTarde =  Time.valueOf(c.getString(c.getColumnIndex(HorarioEntry.CIERRE_TARDE)));
                String ultimaOperacion = c.getString(c.getColumnIndex(HorarioEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(HorarioEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(HorarioEntry.TIMESTAMP));

                horario = new Horario(idHorario, dia, aperturaMañana, cierreMañana, aperturaTarde, cierreTarde, ultimaOperacion, timestampModificacion, timestamp);
            } else {
                horario = null;
            }
        } catch (Exception e) {
            horario = null;
            Log.i(TAG,"getHorario "+e.getMessage());
        } finally {
            c.close();
        }

        this.close();

        return horario;
    }

    public void eliminarHorario(int id) {
        String where = HorarioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                HorarioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarHorario(Horario horario) {
        String where = HorarioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(horario.getId())};
        this.getWritableDatabase().update(
                HorarioEntry.TABLE_NAME,
                horario.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarHorarioComercio(int idHorarioComercio, Horario horario, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                HorarioComercioEntry.TABLE_NAME,
                null,
                horario.toContentValuesHorarioComercio(idHorarioComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirHorariosALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        HorarioComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idHorario = c.getInt(c.getColumnIndex(HorarioComercioEntry.ID_HORARIO));
                int idComercio = c.getInt(c.getColumnIndex(HorarioComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                Horario horario;
                if (idHorario != 0) {
                    horario = busqueda.getHorario(idHorario);
                } else {
                    horario = null;
                }

                if (comercio != null && horario != null){
                    comercio.setHorario(horario);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirHorariosALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeHorarioComercio(int id) {

        boolean existe = false;
        String where = HorarioComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        HorarioComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarHorarioComercio(int id) {
        String where = HorarioComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                HorarioComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarHorarioComercio(int idHorarioComercio, Horario horario, Comercio comercio) {
        String where = HorarioComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idHorarioComercio)};
        this.getWritableDatabase().update(
                HorarioComercioEntry.TABLE_NAME,
                horario.toContentValuesHorarioComercio(idHorarioComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarProvincia(Provincia provincia) {
        long id = this.getWritableDatabase().insert(
                ProvinciaEntry.TABLE_NAME,
                null,
                provincia.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Provincia> getProvincias() {

        ArrayList<Provincia> provincias = new ArrayList<>();

        String orderBy = ProvinciaEntry.NOMBRE + " ASC";

        Cursor c = this.getReadableDatabase()
                .query(
                        ProvinciaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idProvincia = c.getInt(c.getColumnIndex(ProvinciaEntry.ID));
                String nombreProvincia = c.getString(c.getColumnIndex(ProvinciaEntry.NOMBRE));
                String ultimaOperacion = c.getString(c.getColumnIndex(ProvinciaEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(ProvinciaEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(ProvinciaEntry.TIMESTAMP));

                provincias.add(new Provincia(idProvincia, nombreProvincia, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return provincias;
    }

    public Provincia getProvincia(int id) {

        Provincia provincia;
        String where = ProvinciaEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        ProvinciaEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idProvincia = c.getInt(c.getColumnIndex(ProvinciaEntry.ID));
            String nombre = c.getString(c.getColumnIndex(ProvinciaEntry.NOMBRE));
            String ultimaOperacion = c.getString(c.getColumnIndex(ProvinciaEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(ProvinciaEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(ProvinciaEntry.TIMESTAMP));

            provincia = new Provincia(idProvincia, nombre, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            provincia = null;
        }

        c.close();
        this.close();
        return provincia;
    }

    public void eliminarProvincia(int id) {
        String where = ProvinciaEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                ProvinciaEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarProvincia(Provincia provincia) {
        String where = ProvinciaEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(provincia.getId())};
        this.getWritableDatabase().update(
                ProvinciaEntry.TABLE_NAME,
                provincia.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarCiudad(Ciudad ciudad) {
        long id = this.getWritableDatabase().insert(
                CiudadEntry.TABLE_NAME,
                null,
                ciudad.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Ciudad> getCiudades() {

        ArrayList<Ciudad> ciudades = new ArrayList<>();

        String orderBy = CiudadEntry.NOMBRE + " ASC";

        Cursor c = this.getReadableDatabase()
                .query(
                        CiudadEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        orderBy,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idCiudad = c.getInt(c.getColumnIndex(CiudadEntry.ID));
                String nombre = c.getString(c.getColumnIndex(CiudadEntry.NOMBRE));
                int idProvincia = c.getInt(c.getColumnIndex(CiudadEntry.ID_PROVINCIA));
                String ultimaOperacion = c.getString(c.getColumnIndex(CiudadEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(CiudadEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(CiudadEntry.TIMESTAMP));


                Provincia provincia;
                if (idProvincia == 0){
                    provincia = null;
                } else {
                    provincia = getProvincia(idProvincia);
                }

                Ciudad ciudad = new Ciudad(idCiudad, nombre, provincia, ultimaOperacion, timestampModificacion, timestamp);
                ciudad.setProvincia(provincia);
                ciudades.add(ciudad);
            }
        } finally {
            c.close();
        }

        this.close();

        return ciudades;
    }

    public Ciudad getCiudad(int id) {

        Ciudad ciudad;
        String where = CiudadEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        CiudadEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idCiudad = c.getInt(c.getColumnIndex(CiudadEntry.ID));
            String nombre = c.getString(c.getColumnIndex(CiudadEntry.NOMBRE));
            int idProvincia = c.getInt(c.getColumnIndex(CiudadEntry.ID_PROVINCIA));
            String ultimaOperacion = c.getString(c.getColumnIndex(CiudadEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(CiudadEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(CiudadEntry.TIMESTAMP));

            Provincia provincia;
            if (idProvincia == 0){
                provincia = null;
            } else {
                provincia = getProvincia(idProvincia);
            }

            ciudad = new Ciudad(idCiudad, nombre, provincia, ultimaOperacion, timestampModificacion, timestamp);
            ciudad.setProvincia(provincia);
        } else {
            ciudad = null;
        }

        c.close();
        this.close();
        return ciudad;
    }

    public void eliminarCiudad(int id) {
        String where = CiudadEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                CiudadEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarCiudad(Ciudad ciudad) {
        String where = CiudadEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(ciudad.getId())};
        this.getWritableDatabase().update(
                CiudadEntry.TABLE_NAME,
                ciudad.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarTelefono(Telefono telefono) {
        long id = this.getWritableDatabase().insert(
                TelefonoEntry.TABLE_NAME,
                null,
                telefono.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Telefono> getTelefonos() {

        ArrayList<Telefono> telefonos = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        TelefonoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        TelefonoEntry.NUMERO,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idTelefono = c.getInt(c.getColumnIndex(TelefonoEntry.ID));
                String numero = c.getString(c.getColumnIndex(TelefonoEntry.NUMERO));
                String ultimaOperacion = c.getString(c.getColumnIndex(TelefonoEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(TelefonoEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(TelefonoEntry.TIMESTAMP));
                Log.i(TAG,"idTelefono"+idTelefono);
                telefonos.add(new Telefono(idTelefono, numero, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return telefonos;
    }

    public Telefono getTelefono(int id) {

        Telefono telefono;
        String where = TelefonoEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        TelefonoEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idTelefono = c.getInt(c.getColumnIndex(TelefonoEntry.ID));
            String numero = c.getString(c.getColumnIndex(TelefonoEntry.NUMERO));
            String ultimaOperacion = c.getString(c.getColumnIndex(TelefonoEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(TelefonoEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(TelefonoEntry.TIMESTAMP));

            telefono = new Telefono(idTelefono, numero, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            telefono = null;
        }

        c.close();
        this.close();
        return telefono;
    }

    public void eliminarTelefono(int id) {
        String where = TelefonoEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                TelefonoEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarTelefono(Telefono telefono) {
        String where = TelefonoEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(telefono.getId())};
        this.getWritableDatabase().update(
                TelefonoEntry.TABLE_NAME,
                telefono.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarTelefonoComercio(int idTelefonoComercio, Telefono telefono, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                TelefonoComercioEntry.TABLE_NAME,
                null,
                telefono.toContentValuesTelefonoComercio(idTelefonoComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirTelefonosALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        TelefonoComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idTelefono = c.getInt(c.getColumnIndex(TelefonoComercioEntry.ID_TELEFONO));
                int idComercio = c.getInt(c.getColumnIndex(TelefonoComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                Telefono telefono;
                if (idTelefono != 0) {
                    telefono = busqueda.getTelefono(idTelefono);
                } else {
                    telefono = null;
                }

                if (comercio != null && telefono != null){
                    comercio.setTelefono(telefono);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirTelefonosALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeTelefonoComercio(int id) {

        boolean existe = false;
        String where = TelefonoComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        TelefonoComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarTelefonoComercio(int id) {
        String where = TelefonoComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                TelefonoComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarTelefonoComercio(int idTelefonoComercio, Telefono telefono, Comercio comercio) {
        String where = TelefonoComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idTelefonoComercio)};
        this.getWritableDatabase().update(
                TelefonoComercioEntry.TABLE_NAME,
                telefono.toContentValuesTelefonoComercio(idTelefonoComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarEmail(Email email) {
        long id = this.getWritableDatabase().insert(
                EmailEntry.TABLE_NAME,
                null,
                email.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Email> getEmails() {

        ArrayList<Email> emails = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        EmailEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idEmail = c.getInt(c.getColumnIndex(EmailEntry.ID));
                String direccion = c.getString(c.getColumnIndex(EmailEntry.DIRECCION));
                String ultimaOperacion = c.getString(c.getColumnIndex(EmailEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(EmailEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(EmailEntry.TIMESTAMP));

                Log.i(TAG,"idEmail"+idEmail);
                emails.add(new Email(idEmail, direccion, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return emails;
    }

    public Email getEmail(int id) {

        Email email;
        String where = EmailEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        EmailEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idEmail = c.getInt(c.getColumnIndex(EmailEntry.ID));
            String direccion = c.getString(c.getColumnIndex(EmailEntry.DIRECCION));
            String ultimaOperacion = c.getString(c.getColumnIndex(EmailEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(EmailEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(EmailEntry.TIMESTAMP));

            email = new Email(idEmail, direccion, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            email = null;
        }

        c.close();
        this.close();
        return email;
    }

    public void eliminarEmail(int id) {
        String where = EmailEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                EmailEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarEmail(Email email) {
        String where = EmailEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(email.getId())};
        this.getWritableDatabase().update(
                EmailEntry.TABLE_NAME,
                email.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarEmailComercio(int idEmailComercio, Email email, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                EmailComercioEntry.TABLE_NAME,
                null,
                email.toContentValuesEmailComercio(idEmailComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirEmailsALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        EmailComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idEmail = c.getInt(c.getColumnIndex(EmailComercioEntry.ID_EMAIL));
                int idComercio = c.getInt(c.getColumnIndex(EmailComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                Email email;
                if (idEmail != 0) {
                    email = busqueda.getEmail(idEmail);
                } else {
                    email = null;
                }

                if (comercio != null && email != null){
                    comercio.setEmail(email);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirEmailsALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeEmailComercio(int id) {

        boolean existe = false;
        String where = EmailComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        EmailComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarEmailComercio(int id) {
        String where = EmailComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                EmailComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarEmailComercio(int idEmailComercio, Email email, Comercio comercio) {
        String where = EmailComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idEmailComercio)};
        this.getWritableDatabase().update(
                EmailComercioEntry.TABLE_NAME,
                email.toContentValuesEmailComercio(idEmailComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarDireccion(Direccion direccion) {
        long id = this.getWritableDatabase().insert(
                DireccionEntry.TABLE_NAME,
                null,
                direccion.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Direccion> getDirecciones() {

        ArrayList<Direccion> direcciones = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        DireccionEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idDireccion = c.getInt(c.getColumnIndex(DireccionEntry.ID));
                String nombre = c.getString(c.getColumnIndex(DireccionEntry.NOMBRE));
                int altura = c.getInt(c.getColumnIndex(DireccionEntry.ALTURA));
                int piso = c.getInt(c.getColumnIndex(DireccionEntry.NUMERO_PISO));
                String departamento = c.getString(c.getColumnIndex(DireccionEntry.DEPARTAMENTO));
                String barrio = c.getString(c.getColumnIndex(DireccionEntry.BARRIO));
                int idCiudad = c.getInt(c.getColumnIndex(DireccionEntry.ID_CIUDAD));
                int idProvincia = c.getInt(c.getColumnIndex(DireccionEntry.ID_PROVINCIA));
                String tipo = c.getString(c.getColumnIndex(DireccionEntry.TIPO));
                String latitud = c.getString(c.getColumnIndex(DireccionEntry.LATITUD));
                String longitud = c.getString(c.getColumnIndex(DireccionEntry.LONGITUD));
                String ultimaOperacion = c.getString(c.getColumnIndex(DireccionEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP));

                Log.i(TAG,"idDireccion "+idDireccion);
                Provincia provincia;
                if (idProvincia != 0){
                    provincia = getProvincia(idProvincia);
                } else {
                    provincia = null;
                }

                Ciudad ciudad;
                if (idCiudad != 0){
                    ciudad = getCiudad(idCiudad);
                } else {
                    ciudad = null;
                }

                direcciones.add(new Direccion(idDireccion, nombre, altura, piso, departamento, barrio, ciudad, provincia, tipo,
                        latitud, longitud, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return direcciones;
    }

    public ArrayList<Direccion> getDireccionesDelComercio(int id) {

        ArrayList<Direccion> direcciones = new ArrayList<>();
        String where = DireccionEntry.ID_COMERCIO + " = ?";
        String[] whereArgs = {Integer.toString(id)};

        Cursor c = this.getReadableDatabase()
                .query(
                        DireccionEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idDireccion = c.getInt(c.getColumnIndex(DireccionEntry.ID));
                String nombre = c.getString(c.getColumnIndex(DireccionEntry.NOMBRE));
                int altura = c.getInt(c.getColumnIndex(DireccionEntry.ALTURA));
                int piso = c.getInt(c.getColumnIndex(DireccionEntry.NUMERO_PISO));
                String departamento = c.getString(c.getColumnIndex(DireccionEntry.DEPARTAMENTO));
                String barrio = c.getString(c.getColumnIndex(DireccionEntry.BARRIO));
                int idCiudad = c.getInt(c.getColumnIndex(DireccionEntry.ID_CIUDAD));
                int idProvincia = c.getInt(c.getColumnIndex(DireccionEntry.ID_PROVINCIA));
                String tipo = c.getString(c.getColumnIndex(DireccionEntry.TIPO));
                String latitud = c.getString(c.getColumnIndex(DireccionEntry.LATITUD));
                String longitud = c.getString(c.getColumnIndex(DireccionEntry.LONGITUD));
                String ultimaOperacion = c.getString(c.getColumnIndex(DireccionEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP));

                Provincia provincia;
                if (idProvincia != 0){
                    provincia = getProvincia(idProvincia);
                } else {
                    provincia = null;
                }

                Ciudad ciudad;
                if (idCiudad != 0){
                    ciudad = getCiudad(idCiudad);
                } else {
                    ciudad = null;
                }

                direcciones.add(new Direccion(idDireccion, nombre, altura, piso, departamento, barrio, ciudad, provincia, tipo,
                        latitud, longitud, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return direcciones;
    }

    public Direccion getDireccion(int id) {

        Direccion direccion;
        String where = DireccionEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        DireccionEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idDireccion = c.getInt(c.getColumnIndex(DireccionEntry.ID));
            String nombre = c.getString(c.getColumnIndex(DireccionEntry.NOMBRE));
            int altura = c.getInt(c.getColumnIndex(DireccionEntry.ALTURA));
            int piso = c.getInt(c.getColumnIndex(DireccionEntry.NUMERO_PISO));
            String departamento = c.getString(c.getColumnIndex(DireccionEntry.DEPARTAMENTO));
            String barrio = c.getString(c.getColumnIndex(DireccionEntry.BARRIO));
            int idCiudad = c.getInt(c.getColumnIndex(DireccionEntry.ID_CIUDAD));
            int idProvincia = c.getInt(c.getColumnIndex(DireccionEntry.ID_PROVINCIA));
            String tipo = c.getString(c.getColumnIndex(DireccionEntry.TIPO));
            String latitud = c.getString(c.getColumnIndex(DireccionEntry.LATITUD));
            String longitud = c.getString(c.getColumnIndex(DireccionEntry.LONGITUD));
            String ultimaOperacion = c.getString(c.getColumnIndex(DireccionEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(DireccionEntry.TIMESTAMP));

            Provincia provincia;
            if (idProvincia != 0){
                provincia = getProvincia(idProvincia);
            } else {
                provincia = null;
            }

            Ciudad ciudad;
            if (idCiudad != 0){
                ciudad = getCiudad(idCiudad);
            } else {
                ciudad = null;
            }

            direccion = new Direccion(idDireccion, nombre, altura, piso, departamento, barrio, ciudad, provincia, tipo,
                    latitud, longitud, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            direccion = null;
        }

        c.close();
        this.close();
        return direccion;
    }

    public void eliminarDireccion(int id) {
        String where = DireccionEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                DireccionEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public boolean eliminarDireccionesDelComercio(int idComercio) {
        boolean eliminadoConExito;
        try{
            String where = DireccionEntry.ID_COMERCIO + " = ?";
            this.getWritableDatabase().delete(
                    DireccionEntry.TABLE_NAME,
                    where,
                    new String[]{Integer.toString(idComercio)}
            );
            this.close();
            eliminadoConExito = true;
        } catch (Exception e) {
            eliminadoConExito = false;
        }
        return eliminadoConExito;
    }

    public void actualizarDireccion(Direccion direccion) {
        String where = DireccionEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(direccion.getId())};
        this.getWritableDatabase().update(
                DireccionEntry.TABLE_NAME,
                direccion.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarDireccionComercio(int idDireccionComercio, Direccion direccion, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                DireccionComercioEntry.TABLE_NAME,
                null,
                direccion.toContentValuesDireccionComercio(idDireccionComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirDireccionesALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        DireccionComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idDireccion = c.getInt(c.getColumnIndex(DireccionComercioEntry.ID_DIRECCION));
                int idComercio = c.getInt(c.getColumnIndex(DireccionComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                Direccion direccion;
                if (idDireccion != 0) {
                    direccion = busqueda.getDireccion(idDireccion);
                } else {
                    direccion = null;
                }

                if (comercio != null && direccion != null){
                    comercio.setDireccion(direccion);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirDireccionesALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeDireccionComercio(int id) {

        boolean existe = false;
        String where = DireccionComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        DireccionComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarDireccionComercio(int id) {
        String where = DireccionComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                DireccionComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarDireccionComercio(int idDireccionComercio, Direccion direccion, Comercio comercio) {
        String where = DireccionComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idDireccionComercio)};
        this.getWritableDatabase().update(
                DireccionComercioEntry.TABLE_NAME,
                direccion.toContentValuesDireccionComercio(idDireccionComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarSitioWeb(SitioWeb sitioWeb) {
        long id = this.getWritableDatabase().insert(
                SitioWebEntry.TABLE_NAME,
                null,
                sitioWeb.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<SitioWeb> getSitiosWebs() {

        ArrayList<SitioWeb> sitiosWebs = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        SitioWebEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(SitioWebEntry.ID));
                String url = c.getString(c.getColumnIndex(SitioWebEntry.URL));
                String ultimaOperacion = c.getString(c.getColumnIndex(SitioWebEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(SitioWebEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(SitioWebEntry.TIMESTAMP));

                sitiosWebs.add(new SitioWeb(id, url, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return sitiosWebs;
    }
    public SitioWeb getSitioWeb(int id) {

        SitioWeb sitioWeb;
        String where = SitioWebEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        SitioWebEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idSitioWeb = c.getInt(c.getColumnIndex(SitioWebEntry.ID));
            String url = c.getString(c.getColumnIndex(SitioWebEntry.URL));
            String ultimaOperacion = c.getString(c.getColumnIndex(SitioWebEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(SitioWebEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(SitioWebEntry.TIMESTAMP));

            sitioWeb = new SitioWeb(idSitioWeb, url, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            sitioWeb = null;
        }

        c.close();
        this.close();
        return sitioWeb;
    }

    public void eliminarSitioWeb(int id) {
        String where = SitioWebEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                SitioWebEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarSitioWeb(SitioWeb sitioWeb) {
        String where = EmailEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(sitioWeb.getId())};
        this.getWritableDatabase().update(
                SitioWebEntry.TABLE_NAME,
                sitioWeb.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarSitioWebComercio(int idSitioWebComercio, SitioWeb sitioWeb, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                SitioWebComercioEntry.TABLE_NAME,
                null,
                sitioWeb.toContentValuesSitioWebComercio(idSitioWebComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirSitiosWebsALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        SitioWebComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idSitioWeb = c.getInt(c.getColumnIndex(SitioWebComercioEntry.ID));
                int idComercio = c.getInt(c.getColumnIndex(SitioWebComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                SitioWeb sitioWeb;
                if (idSitioWeb != 0) {
                    sitioWeb = busqueda.getSitioWeb(idSitioWeb);
                } else {
                    sitioWeb = null;
                }

                if (comercio != null && sitioWeb != null){
                    comercio.setSitioWeb(sitioWeb);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirSitiosWebsALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeSitioWebComercio(int id) {

        boolean existe = false;
        String where = SitioWebComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        SitioWebComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarSitioWebComercio(int id) {
        String where = SitioWebComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                SitioWebComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarSitioWebComercio(int idSitioWebComercio, SitioWeb sitioWeb, Comercio comercio) {
        String where = SitioWebComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idSitioWebComercio)};
        this.getWritableDatabase().update(
                SitioWebComercioEntry.TABLE_NAME,
                sitioWeb.toContentValuesSitioWebComercio(idSitioWebComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarFoto(Foto foto) {
        long id = this.getWritableDatabase().insert(
                FotoEntry.TABLE_NAME,
                null,
                foto.toContentValues()
        );
        this.close();
        return id;
    }

    public ArrayList<Foto> getFotos() {

        ArrayList<Foto> fotos = new ArrayList<>();

        Cursor c = this.getReadableDatabase()
                .query(
                        FotoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int id = c.getInt(c.getColumnIndex(FotoEntry.ID));
                String nombreArchivo = c.getString(c.getColumnIndex(FotoEntry.NOMBRE_ARCHIVO));
                String ultimaOperacion = c.getString(c.getColumnIndex(FotoEntry.ULTIMA_OPERACION));
                String timestampModificacion = c.getString(c.getColumnIndex(FotoEntry.TIMESTAMP_MODIFICACION));
                String timestamp = c.getString(c.getColumnIndex(FotoEntry.TIMESTAMP));
                Log.i(TAG,"ID FOTO "+id);
                fotos.add(new Foto(id, nombreArchivo, ultimaOperacion, timestampModificacion, timestamp));
            }
        } finally {
            c.close();
        }

        this.close();

        return fotos;
    }

    public Foto getFoto(int id) {

        Foto foto;
        String where = FotoEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        FotoEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            c.moveToFirst();
            int idFoto = c.getInt(c.getColumnIndex(FotoEntry.ID));
            String nombreArchivo = c.getString(c.getColumnIndex(FotoEntry.NOMBRE_ARCHIVO));
            String ultimaOperacion = c.getString(c.getColumnIndex(FotoEntry.ULTIMA_OPERACION));
            String timestampModificacion = c.getString(c.getColumnIndex(FotoEntry.TIMESTAMP_MODIFICACION));
            String timestamp = c.getString(c.getColumnIndex(FotoEntry.TIMESTAMP));

            foto = new Foto(idFoto, nombreArchivo, ultimaOperacion, timestampModificacion, timestamp);
        } else {
            foto = null;
        }

        c.close();
        this.close();
        return foto;
    }

    public void eliminarFoto(int id) {
        String where = FotoEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                FotoEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarFoto(Foto foto) {
        String where = EmailEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(foto.getId())};
        this.getWritableDatabase().update(
                FotoEntry.TABLE_NAME,
                foto.toContentValues(),
                where,
                whereArgs
        );
        this.close();
    }

    public long insertarFotoComercio(int idFotoComercio, Foto foto, Comercio comercio) {
        long id = this.getWritableDatabase().insert(
                FotoComercioEntry.TABLE_NAME,
                null,
                foto.toContentValuesFotoComercio(idFotoComercio, comercio)
        );
        this.close();
        return id;
    }

    public boolean transferirFotosALosComercios() {
        boolean transferenciaConExito;

        Cursor c = this.getReadableDatabase()
                .query(
                        FotoComercioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        try {
            while (c.moveToNext()) {
                int idFoto = c.getInt(c.getColumnIndex(FotoComercioEntry.ID));
                int idComercio = c.getInt(c.getColumnIndex(FotoComercioEntry.ID_COMERCIO));

                Busqueda busqueda = new Busqueda();
                Comercio comercio;
                if (idComercio != 0) {
                    comercio = busqueda.getComercio(idComercio);
                } else {
                    comercio = null;
                }

                Foto foto;
                if (idFoto != 0) {
                    foto = busqueda.getFoto(idFoto);
                } else {
                    foto = null;
                }

                if (comercio != null && foto != null){
                    comercio.setFoto(foto);
                }
            }
            transferenciaConExito = true;
        } catch (Exception e){
            Log.i(TAG,"ERROR transferirFotosALosComercios " + e.getMessage());
            transferenciaConExito = false;
        } finally {
            c.close();
        }

        this.close();
        return transferenciaConExito;
    }

    public boolean existeFotoComercio(int id) {

        boolean existe = false;
        String where = FotoComercioEntry.ID + " = ?";
        String[] whereArgs = {Integer.toString(id)};
        Cursor c = this.getReadableDatabase()
                .query(
                        FotoComercioEntry.TABLE_NAME,
                        null,
                        where,
                        whereArgs,
                        null,
                        null,
                        null,
                        null);

        //Nos aseguramos de que existe al menos un registro
        if(c.getCount() > 0) {
            existe = true;
        }
        c.close();
        this.close();
        return existe;
    }

    public void eliminarFotoComercio(int id) {
        String where = FotoComercioEntry.ID + " = ?";
        this.getWritableDatabase().delete(
                FotoComercioEntry.TABLE_NAME,
                where,
                new String[]{Integer.toString(id)}
        );
        this.close();
    }

    public void actualizarFotoComercio(int idFotoComercio, Foto foto, Comercio comercio) {
        String where = FotoComercioEntry.ID + " = ?";
        String[] whereArgs = {String.valueOf(idFotoComercio)};
        this.getWritableDatabase().update(
                FotoComercioEntry.TABLE_NAME,
                foto.toContentValuesFotoComercio(idFotoComercio, comercio),
                where,
                whereArgs
        );
        this.close();
    }
}