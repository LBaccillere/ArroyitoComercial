package jf.desarrollos.arroyitocomercial.Controlador;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Operacion {
    private static final String TAG = Operacion.class.getSimpleName();

    public static String quitarTildes(String str){
        final String ORIGINAL = "ÁáÉéÍíÓóÚú";
        final String REPLACEMENT = "AaEeIiOoUu";
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }

    public static String formatearFecha(String fecha) {

        String fechaResultado;

        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        Date date;
        try {
            date = parseador.parse(fecha);
            fechaResultado = formateador.format(date);
        } catch (ParseException e) {
            fechaResultado = "00/00/0000";
        }

        return fechaResultado;
    }

    public static String formatearHora(String hora) {

        String fechaResultado;

        SimpleDateFormat parseador = new SimpleDateFormat("HH:mm:ss");

        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");

        Date date;
        try {
            date = parseador.parse(hora);
            fechaResultado = formateador.format(date);
        } catch (ParseException e) {
            fechaResultado = "00:00";
        }

        return fechaResultado;
    }

    public static long diferenciaDeFechas(String fechaUno, String fechaDos) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        Log.i(TAG, "MILLSECS_PER_DAY 1 " + MILLSECS_PER_DAY);


        Date fecha1 = null;
        Date fecha2 = null;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            fecha1 = formatoDelTexto.parse(fechaUno);
            Log.i(TAG, "fecha 1 " + fecha1);
            fecha2 = formatoDelTexto.parse(fechaDos);
            Log.i(TAG, "fecha 2 " + fecha2);
        } catch (ParseException ex) {
            ex.printStackTrace();

        }


        Log.i(TAG, "fecha 1 ge time " + fecha1.getTime());
        Log.i(TAG, "fecha 2 ge time " + fecha2.getTime());
        long diferencia = ( fecha1.getTime() - fecha2.getTime() )/MILLSECS_PER_DAY;
        Log.i(TAG, "diferencia " + diferencia);
        long hora = diferencia/3600000;
        Log.i(TAG, "diferencia en hs " + hora);
        return diferencia;
    }
}
