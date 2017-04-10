package com.kcc.httputils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;

/**
 * Created by ford-pro2 on 16/7/16.
 */
public class HttpRequest {
    protected String url;
    private static final String TAG = "HttpRequest";
    /**
     * default timeout for http connection
     */
    protected int timeout = 3000;
    public static final String ERROR_URL = "Malformed URL !!!";
    public static final String ERROR_UNKNOWN = "Unknown Error !!!";
    public static final String COOKIES_HEADER = "Set-Cookie";
    protected static CookieManager cookieManager = null;
    public InputStream getCertfile() {
        return certfile;
    }

    public void setCertfile(InputStream certfile) {
        this.certfile = certfile;
    }

    private InputStream certfile = null;
    public HttpRequest(String url, int timeout) {
        this(url);
        this.timeout = timeout;
    }

    public HttpRequest(String url) {
        this.url = url;
        CookieHandler.setDefault(new CookieManager());
//        if (cookieManager == null) {
//            cookieManager = new CookieManager();
//            cookieManager = new CookieManager(new CookieStore() {
//                Map<URI, List<HttpCookie>> map = new HashMap<URI, List<HttpCookie>>();
//
//                @Override
//                public void add(URI uri, HttpCookie cookie) {
//                    List<HttpCookie> list = map.get(uri);
//                    if (list == null) {
//                        Log.i(TAG, "add: find no list");
//                        list = new ArrayList<HttpCookie>();
//                        list.add(cookie);
//                        map.put(uri, list);
//                    } else {
//                        Log.i(TAG, "add: find list");
//                        list.add(cookie);
//                    }
//                }
//
//                @Override
//                public List<HttpCookie> get(URI uri) {
//                    List<HttpCookie> list = map.get(uri);
//                    if (list == null) {
//                        Log.i(TAG, "add: find no list");
//                    } else {
//                        Log.i(TAG, "add: find list");
//                    }
//                    return list;
//                }
//
//                @Override
//                public List<HttpCookie> getCookies() {
//                    return null;
//                }
//
//                @Override
//                public List<URI> getURIs() {
//                    return null;
//                }
//
//                @Override
//                public boolean remove(URI uri, HttpCookie cookie) {
//                    return false;
//                }
//
//                @Override
//                public boolean removeAll() {
//                    return false;
//                }
//            }, new CookiePolicy() {
//                @Override
//                public boolean shouldAccept(URI uri, HttpCookie cookie) {
//                    Log.i(TAG, "shouldAccept: ");
////                HttpCookie.domainMatches(cookie.getDomain(), uri.getHost());
//                    return true;
//                }
//            });
//        }
    }

    protected HttpResponse connect(){
        return null;
    }

    public String getString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int count = 0;
        String content = "";
        BufferedInputStream bis = new BufferedInputStream(is);
        while ((count = bis.read(buffer)) > 0) {
            content += new String(buffer, 0, count, "UTF8");
        }
        return content;
    }

    //    public protected connect
    public class HttpResponse {
        /**
         * result code returned. include standard HTTP response code and other error code
         * -1: MalformedURL Error
         */
        private int code = -1;
        /**
         * content string when code is {@link HttpURLConnection#HTTP_OK}
         */
        private String content;
        /**
         * error string when code is not HTTP_OK
         */
        private String error;

        private HttpResponse(int code, String content, String error) {
            this.code = code;
            this.content = content;
            this.error = error;
        }

        public HttpResponse() {
            code = -1;
            content = "";
            error = "";
        }


        public int getCode() {
            return code;
        }

        public String getContent() {
            return content;
        }

        public String getError() {
            return error;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setError(String error) {
            this.error = error;
        }

        @Override
        public String toString() {
            return "HttpResponse{" +
                    "code=" + code +
                    ", content='" + content + '\'' +
                    ", error='" + error + '\'' +
                    '}';
        }
    }
}
