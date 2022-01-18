package saarland.cispa.trust.cispabay.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.util.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManagerFactory;

import saarland.cispa.trust.cispabay.R;

public class UserFragment extends Fragment {
    private final String TAG = "CISPABay";
    private final String SERVER_URL = "https://cispbay.de/login";

    public UserFragment() {}

    public static UserFragment newInstance() {
        return new UserFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Button loginButton = (Button) view.findViewById(R.id.loginBtn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ((EditText) view.findViewById(R.id.usernameField)).getText().toString();
                String password = ((EditText) view.findViewById(R.id.passwordField)).getText().toString();

                String[] parameters = { username, password };
                AsyncTask<String, Void, JSONObject> response = new NetworkBackgroundService().execute(parameters);
            }
        });

        return view;
    }

    private class NetworkBackgroundService extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            JSONObject jsonObject = loginWithPinnedCertificate(username, password);
            return jsonObject;
        }

        protected void onPostExecute(JSONObject jsonObject) {
            try {
                if (jsonObject == null)
                    return;

                boolean loginSuccess = jsonObject.getBoolean("success");
                Log.d(TAG, "Login success: " + loginSuccess);

                if (loginSuccess) {
                    Log.d(TAG, "AuthToken: " + jsonObject.getJSONObject("data").getString("auth_token"));
                } else {
                    Log.d(TAG, "AuthToken: " + jsonObject.getJSONObject("data").getString("error"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Helper to consume an input stream
     */
    public static String dumpStream(HttpsURLConnection connection) throws IOException {
        InputStreamReader inputStreamReader;
        if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
            inputStreamReader = new InputStreamReader(connection.getInputStream());
        } else {
            inputStreamReader = new InputStreamReader(connection.getErrorStream());
        }

        String result = "";
        String line;
        BufferedReader br = new BufferedReader(inputStreamReader);
        while ((line = br.readLine()) != null) {
            result += line;
        }
        return result;
    }

    /*
    Helper to map SSL error code to a human-readable string
     */
    public enum SSLErrorPrimaryCodes {
        SSL_DATA_INVALID (4),
        SSL_EXPIRED (1),
        SSL_IDMISMATCH (2),
        SSL_INVALID (5),
        SSL_NOTYETVALID (0),
        SSL_UNTRUSTED (3);

        private final int value;

        SSLErrorPrimaryCodes(int value) {
            this.value = value;
        }

        static SSLErrorPrimaryCodes getValue(int value) {
            for(SSLErrorPrimaryCodes e: SSLErrorPrimaryCodes.values()) {
                if(e.value == value)
                    return e;
            }
            return null;
        }
    }

    /*
    Helper to log some infos about an HttpsURLConnection
     */
    private void logSSLInfos(HttpsURLConnection connection) throws SSLPeerUnverifiedException {
        Log.i(TAG, String.format("Connected to %s using %s", SERVER_URL, connection.getCipherSuite()));
        Certificate[] certificateChain = connection.getServerCertificates();
        for(int i = 0; i<certificateChain.length; i++) {
            X509Certificate certificate = (X509Certificate)certificateChain[i];
            Log.i(TAG, String.format("CertificateChain[%d]: %s", i, certificate.toString()));
            Log.i(TAG, "*******************************************");
        }
    }

    private JSONObject loginWithPinnedCertificate(String username, String password) {
        // @TODO - IMPLEMENT ME
        return null;
    }

    private SSLContext create_SSLContext_from_asset() {
        // @TODO - IMPLEMENT ME
        return null;
    }
}