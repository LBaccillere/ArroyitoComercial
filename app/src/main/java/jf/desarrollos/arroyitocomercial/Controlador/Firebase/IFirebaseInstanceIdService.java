package jf.desarrollos.arroyitocomercial.Controlador.Firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import jf.desarrollos.arroyitocomercial.Controlador.Volley.AppController;
import jf.desarrollos.arroyitocomercial.Utilidades.Constantes;

public class IFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = IFirebaseInstanceIdService.class.getSimpleName();

    public IFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {

        Log.i(TAG, "onTokenRefresh");
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "FCM Token: " + fcmToken);
    }

}
