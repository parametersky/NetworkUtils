package com.kcc.httputils;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by ford-pro2 on 16/7/16.
 */
public class HttpGet extends HttpRequest {

    public static final String TAG = "HttpGet";

    private boolean isSSH = false;

    public HttpGet(String url, int timeout) {
        super(url, timeout);
    }

    public HttpGet(String url) {
        super(url);
    }

    public HttpResponse connect() {
        HttpResponse response = new HttpResponse();
        HttpURLConnection connection = null;

        try {
            URL mUrl = new URL(url);
            String protocol = mUrl.getProtocol();
            if (protocol.equals("https")) {
                isSSH = true;
            }


            CertificateFactory cf = CertificateFactory.getInstance("X.509");
//            InputStream in = new BufferedInputStream(getCertfile());
            Certificate ca = cf.generateCertificate(getCertfile());
            Log.i(TAG, "connect: ca="+ ((X509Certificate)ca).getSubjectDN());

            String keystoretype = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keystoretype);
            keyStore.load(null,null);
            keyStore.setCertificateEntry("ca",ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null,tmf.getTrustManagers(),null);

            connection = (HttpURLConnection) mUrl.openConnection();
            if(isSSH) {
                ((HttpsURLConnection)connection).setSSLSocketFactory(context.getSocketFactory());
                ((HttpsURLConnection)connection).setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                        return hv.verify("localhost",session);
//                        return true;
                    }
                });
            }
            connection.setDoInput(true);
            connection.setConnectTimeout(timeout);

            List<HttpCookie> cookies = cookieManager.getCookieStore().get(URI.create(url));
            if (cookies != null && cookies.size() > 0){
                Log.i(TAG, "connect: with cookie: "+ TextUtils.join(";",cookies));
                connection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

            }

            connection.connect();

            List<String> cookeis_header = connection.getHeaderFields().get(COOKIES_HEADER);
            if (cookeis_header != null){
                for (String cookie :
                        cookeis_header) {
                    Log.i(TAG, "connect: get Cookie String:"+ cookie);
                    Log.i(TAG, "connect: parse Cookie:"+HttpCookie.parse(cookie).get(0));
                    Log.i(TAG, "connect: create URI for url: "+ URI.create(url));
                    cookieManager.getCookieStore().add(URI.create(url), HttpCookie.parse(cookie).get(0));
                }
            }

            response.setCode(connection.getResponseCode());
            response.setContent(getString(connection.getInputStream()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setCode(-1);
            response.setError(ERROR_URL);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                response.setError(getString(connection.getErrorStream()));
            } catch (IOException e1) {
                e1.printStackTrace();
                response.setError(ERROR_UNKNOWN);
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return response;
    }


}
