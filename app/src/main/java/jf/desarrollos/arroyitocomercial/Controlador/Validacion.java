package jf.desarrollos.arroyitocomercial.Controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion {

    public static boolean esEmailValido(String correo) {
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}
