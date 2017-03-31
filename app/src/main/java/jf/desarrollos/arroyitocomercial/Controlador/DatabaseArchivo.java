package jf.desarrollos.arroyitocomercial.Controlador;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DatabaseArchivo {
    private static final String TAG = DatabaseArchivo.class.getSimpleName();

    private static String nombreFicheroRubros = "rubros.txt";
    private static String nombreFicheroSubrubros = "subrubros.txt";
    private static String nombreFicheroComercios = "comercios.txt";

    public static void escribirFicheroRubros(Context contexto, String json) throws IOException {
        OutputStreamWriter fout =
                new OutputStreamWriter(contexto.openFileOutput(nombreFicheroRubros, contexto.MODE_PRIVATE));
        Log.i(TAG,"ESCRIBE RUBROS " + json);
        fout.write(json);
        fout.close();
    }

    public static String leerFicheroRubros(Context contexto) throws IOException {
        BufferedReader fin =
                new BufferedReader(new InputStreamReader(contexto.openFileInput(nombreFicheroRubros)));
        String json = fin.readLine();
        Log.i(TAG,"LEE RUBROS " + json);
        fin.close();
        return json;
    }

    public static void escribirFicheroSubrubros(Context contexto, String json) throws IOException {
        OutputStreamWriter fout =
                new OutputStreamWriter(contexto.openFileOutput(nombreFicheroSubrubros, contexto.MODE_PRIVATE));
        Log.i(TAG,"ESCRIBE SUBRUBROS " + json);
        fout.write(json);
        fout.close();
    }

    public static String leerFicheroSubrubros(Context contexto) throws IOException {
        BufferedReader fin =
                new BufferedReader(new InputStreamReader(contexto.openFileInput(nombreFicheroSubrubros)));
        String json = fin.readLine();
        Log.i(TAG,"LEE SUBRUBROS " + json);
        fin.close();
        return json;
    }

    public static void escribirFicheroComercios(Context contexto, String json) throws IOException {
        OutputStreamWriter fout =
                new OutputStreamWriter(contexto.openFileOutput(nombreFicheroComercios, contexto.MODE_PRIVATE));
        fout.write(json);
        Log.i(TAG,"ESCRIBE COEMRCIOS " + json);
        fout.close();
    }

    public static String leerFicheroComercios(Context contexto) throws IOException {
        BufferedReader fin =
                new BufferedReader(new InputStreamReader(contexto.openFileInput(nombreFicheroComercios)));
        String json = fin.readLine();
        Log.i(TAG,"LEE COMERCIOS " + json);
        fin.close();
        return json;
    }
}
