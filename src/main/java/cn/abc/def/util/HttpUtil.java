package cn.abc.def.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

public class HttpUtil {
    private static int connectTimeOut = 5000;
    private static int readTimeOut = 10000;
    private static String requestEncoding = "UTF-8";

    public HttpUtil() {
    }

    public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) throws IOException {
        CloseableHttpResponse response = null;
        if(StringUtils.isEmpty(recvEncoding)) {
            recvEncoding = requestEncoding;
        }

        try {
            CloseableHttpClient e = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(reqUrl);
            ArrayList nvps = new ArrayList();
            if(parameters != null) {
                Iterator entity1 = parameters.entrySet().iterator();

                while(entity1.hasNext()) {
                    Entry content = (Entry)entity1.next();
                    if(content.getValue() != null) {
                        nvps.add(new BasicNameValuePair(content.getKey().toString(), content.getValue().toString()));
                    }
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, recvEncoding));
            response = e.execute(httpPost);
            HttpEntity entity11 = response.getEntity();
            String content1 = EntityUtils.toString(entity11, requestEncoding);
            String var10 = content1;
            return var10;
        } catch (ClientProtocolException var18) {
            var18.printStackTrace();
        } finally {
            try {
                if(response != null) {
                    response.close();
                }
            } catch (IOException var17) {
                var17.printStackTrace();
            }

        }

        return null;
    }

    public static String doPost(String reqUrl, Map<String, String> parameters) throws IOException {
        return doPost(reqUrl, parameters, requestEncoding);
    }

    public static String postJson(String reqUrl, String json, String recvEncoding) throws IOException, HttpException {
        HttpURLConnection url_con = null;
        String responseContent = null;
        String vchartset = StringUtils.isEmpty(recvEncoding)?requestEncoding:recvEncoding;

        try {
            URL e = new URL(reqUrl);
            url_con = (HttpURLConnection)e.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setRequestProperty("Content-Type", "application/json");
            url_con.setConnectTimeout(connectTimeOut);
            url_con.setReadTimeout(readTimeOut);
            url_con.setDoOutput(true);
            byte[] b = json.getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            InputStream in = url_con.getInputStream();
            byte[] echo = new byte[10240];
            int len = in.read(echo);
            responseContent = (new String(echo, 0, len)).trim();
            int code = url_con.getResponseCode();
            if(code != 200) {
                System.out.println(responseContent);
                throw new HttpException(code);
            }
        } catch (IOException var15) {
            System.out.println("网络故障:" + var15.toString());
            throw var15;
        } finally {
            if(url_con != null) {
                url_con.disconnect();
            }

        }

        return responseContent;
    }

    public static String postJsonContent(String reqUrl, String json, String recvEncoding) throws IOException, HttpException {
        HttpPost httpPost = new HttpPost(reqUrl);
        String resData = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            StringEntity e = new StringEntity(json, "utf-8");
            e.setContentEncoding("UTF-8");
            e.setContentType("application/json");
            httpPost.setEntity(e);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            resData = EntityUtils.toString(response.getEntity());
            if(response.getStatusLine().getStatusCode() != 200) {
                System.out.println(resData);
            }
        } catch (IOException var10) {
            System.out.println("网络故障:" + var10.toString());
            throw var10;
        } finally {
            ;
        }

        httpclient.close();
        return resData;
    }

    public static String doGet(String reqUrl, Map<String, String> parameters, String recvEncoding) throws HttpHostConnectException, IOException {
        CloseableHttpResponse response = null;
        if(StringUtils.isEmpty(recvEncoding)) {
            recvEncoding = requestEncoding;
        }

        String var10;
        try {
            StringBuffer e = new StringBuffer();
            if(parameters != null) {
                Iterator httpclient = parameters.entrySet().iterator();

                while(httpclient.hasNext()) {
                    Entry httpget = (Entry)httpclient.next();
                    e.append(httpget.getKey().toString());
                    e.append("=");
                    e.append(URLEncoder.encode(httpget.getValue().toString(), recvEncoding));
                    e.append("&");
                }
            }

            if(e.length() > 0) {
                e = e.deleteCharAt(e.length() - 1);
                if(reqUrl.indexOf("?") >= 0) {
                    reqUrl = reqUrl + "&" + e.toString();
                } else {
                    reqUrl = reqUrl + "?" + e.toString();
                }
            }

            CloseableHttpClient httpclient1 = HttpClients.createDefault();
            HttpGet httpget1 = new HttpGet(reqUrl);
            response = httpclient1.execute(httpget1);
            if(response.getStatusLine().getStatusCode() != 200) {
                return null;
            }

            HttpEntity entity1 = response.getEntity();
            String content = EntityUtils.toString(entity1, requestEncoding);
            var10 = content;
        } catch (ClientProtocolException var19) {
            var19.printStackTrace();
            return null;
        } finally {
            try {
                if(response != null) {
                    response.close();
                }
            } catch (IOException var18) {
                var18.printStackTrace();
            }

        }

        return var10;
    }

    public static String getForRedirectLocation(String reqUrl, Map<String, String> parameters, String recvEncoding) throws HttpHostConnectException, IOException {
        CloseableHttpResponse response = null;
        if(StringUtils.isEmpty(recvEncoding)) {
            recvEncoding = requestEncoding;
        }

        try {
            StringBuffer e = new StringBuffer();
            if(parameters != null) {
                Iterator httpclient = parameters.entrySet().iterator();

                while(httpclient.hasNext()) {
                    Entry httpget = (Entry)httpclient.next();
                    e.append(httpget.getKey().toString());
                    e.append("=");
                    e.append(URLEncoder.encode(httpget.getValue().toString(), recvEncoding));
                    e.append("&");
                }
            }

            if(e.length() > 0) {
                e = e.deleteCharAt(e.length() - 1);
                if(reqUrl.indexOf("?") >= 0) {
                    reqUrl = reqUrl + "&" + e.toString();
                } else {
                    reqUrl = reqUrl + "?" + e.toString();
                }
            }

            CloseableHttpClient var24 = HttpClients.createDefault();
            HttpGet var25 = new HttpGet(reqUrl);
            HttpParams httpParams = var25.getParams();
            httpParams.setParameter("http.protocol.handle-redirects", Boolean.valueOf(false));
            response = var24.execute(var25);
            if(response.getStatusLine().getStatusCode() != 302) {
                return null;
            }

            String strLocation = null;
            Header[] headers = response.getAllHeaders();

            for(int i = 0; i < headers.length; ++i) {
                Header header = headers[i];
                if("location".equalsIgnoreCase(header.getName())) {
                    strLocation = header.getValue();
                    break;
                }
            }

            String var13 = strLocation;
            return var13;
        } catch (ClientProtocolException var22) {
            var22.printStackTrace();
        } finally {
            try {
                if(response != null) {
                    response.close();
                }
            } catch (IOException var21) {
                var21.printStackTrace();
            }

        }

        return null;
    }

    public static String doGet(String reqUrl, Map<String, String> parameters) throws HttpHostConnectException, IOException {
        return doGet(reqUrl, parameters, requestEncoding);
    }
}

class HttpException extends Exception {
	private static final long serialVersionUID = 1L;
	int statuscode;

    public int getStatuscode() {
        return this.statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public HttpException() {
        super("网络连接错误");
    }

    public HttpException(int statuscode) {
        super("验证码错误");
        this.setStatuscode(statuscode);
    }
}