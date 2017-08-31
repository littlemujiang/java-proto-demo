package httpconn;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpUtils {
	private static final Logger logger=LoggerFactory.getLogger(HttpUtils.class);
			

    public final static String GET = "GET";
    public final static String POST = "POST";
    public final static String PUT = "PUT";
    public final static String DELETE = "DELETE";
    public final static int OK=200;

    private HttpUtils() {
    }

    private static HttpUtils instance = null;

    public static HttpUtils getInstance() {
        if (instance == null) {
            synchronized (HttpUtils.class) {
                if (instance == null) {
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 发送HTTP请求
     *
     * @param url     请求地址
     * @param method  请求方式 Http.GET Http.POST Http.PUT Http.DELETE
     * @param headers 请求头 没有设为null
     * @param body    请求体 没有设为null
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public HttpResult send(String url, String method, Map<String, String> headers, Map<String, String> body)
            throws JSONException, IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        JSONObject jsonBody = new JSONObject();
        Set<Entry<String, String>> entrys = null;
        if (body != null && !body.isEmpty()) {
            entrys = body.entrySet();
            for (Entry<String, String> entry : entrys) {
                jsonBody.put(entry.getKey(), entry.getValue());
            }
            return send(url, method, headers, jsonBody.toString());
        }
        return send(url, method, headers, "");
    }

    /**
     * 发送超时将会从新发送，最多发送三次
     *
     * @param url
     * @param method
     * @param headers
     * @param jsonBody
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private HttpResult send(String url, String method, Map<String, String> headers, String jsonBody)
            throws JSONException, IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        HttpResult httpResult;
        try {
            httpResult = send(url, method, headers, jsonBody, 3000, 6000);
        } catch (ConnectException e) {
            try {
                httpResult = send(url, method, headers, jsonBody, 5000, 10000);
            } catch (ConnectException e1) {
                httpResult = send(url, method, headers, jsonBody, 6000, 12000);
            } catch (SocketTimeoutException e1) {
                httpResult = send(url, method, headers, jsonBody, 6000, 12000);
            }
        } catch (SocketTimeoutException e) {
            try {
                httpResult = send(url, method, headers, jsonBody, 5000, 10000);
            } catch (ConnectException e1) {
                httpResult = send(url, method, headers, jsonBody, 6000, 12000);
            } catch (SocketTimeoutException e1) {
                httpResult = send(url, method, headers, jsonBody, 6000, 12000);
            }
        }
        return httpResult;
    }

    /**
     * 发送基本HTTP请求
     *
     * @param url
     * @param method
     * @param headers
     * @param jsonBody
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws JSONException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private HttpResult send(String url, String method, Map<String, String> headers, String jsonBody, int connectTimeout, int readTimeout)
            throws JSONException, IOException, NoSuchAlgorithmException, KeyManagementException, CertificateException {
        HttpResult httpResult = new HttpResult();
        int code = 0;
        BufferedReader resultReader = null;
        StringBuilder result = new StringBuilder();

        URL realUrl = new URL(url);

        HttpURLConnection conn = getURLConnection(realUrl, method, headers, jsonBody, connectTimeout, readTimeout);
        code = conn.getResponseCode();
        if (code >= 200 && code < 300) {
            resultReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        } else {
            resultReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
        }
//        if (conn != null) {
//            conn.disconnect();
//        }

        httpResult.setCode(code);

        if (resultReader != null) {
            String readLine;
            while ((readLine = resultReader.readLine()) != null) {
                result.append(readLine);
            }
            resultReader.close();
        }

        httpResult.setBody(result.toString());
        StringBuffer sb=new StringBuffer();
        sb.append("url:").append(url).append("\t");
        sb.append("method:").append(method).append("\t");
        sb.append("headers:").append(headers).append("\t");
        sb.append("jsonBody").append(jsonBody).append("\t");
        sb.append("result:").append(JSON.toJSONString(httpResult));
        logger.info(sb.toString());
        return httpResult;
    }

    HttpURLConnection getURLConnection(URL url, String method, Map<String, String> headers, String jsonBody, int connectTimeout, int readTimeout) throws CertificateException, NoSuchAlgorithmException, KeyManagementException, IOException {
        String protocol = url.getProtocol();
        HttpURLConnection conn;
        if (protocol.toLowerCase().equals("https")) {
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            conn = c;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setRequestProperty("content-type", "application/json");
        conn.setRequestProperty("charset", "UTF-8");
        if (headers != null && !headers.isEmpty()) {
            Set<Entry<String, String>> entrys = headers.entrySet();
            for (Entry<String, String> entry : entrys) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        if (POST.equals(method) || PUT.equals(method)) {
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.write(jsonBody.getBytes("utf-8"));
            dos.flush();
            dos.close();
            os.close();
        } else {
            conn.setDoOutput(false);
        }
        return conn;
    }
    
    public static String httpByGet(String url) {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();
			httpConnection.setRequestMethod("GET");
			// 设置通用的请求属性
			httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			// 设置post请求必须设置如下两行
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			// 获取输出流
			out = new PrintWriter(httpConnection.getOutputStream());
			out.flush();
			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					logger.error("", e);
				}
		}
		return null;
	}

	public static String HttpByPost(String url) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();
			httpConnection.setRequestMethod("POST");
			// 设置通用的请求属性
			httpConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			// 设置post请求必须设置如下两行
			httpConnection.setDoOutput(true);
			httpConnection.setDoInput(true);
			// 获取输出流
			out = new PrintWriter(httpConnection.getOutputStream());
			out.flush();
			in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "utf-8"));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					logger.error("", e);
				}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("realm", "saas.lenovo.com.cn");
		params.put("password", "das12345");
		params.put("source", "ace");
		params.put("deviceidtype", "imei");
		params.put("deviceid", "863664000005859");
		params.put("lang", "zh_CN");
		params.put("clientip", "127.0.0.1");
//		String string = HttpsUtils.sengHTTPSGet("https://passport.lenovo.com/authen/1.2/st/user/getustbypwd?email=dashboard@lenovo.com", params);
//		System.out.println(string);
//		LoginITCodeUtils.loginByItCode("shiyu3", "znQt-8802");
//		String str = HttpByPost("https://www.sina.com");
//		System.out.println(str);
//		String body = null;
//			HttpResult result = HttpUtils.getInstance().send("https://passport.lenovo.com/authen/1.2/st/user/getustbypwd?email=dashboard@lenovo.com", POST, null, body);
//			System.out.println(result);
//		String str = httpByGet("http://www.baidu.com");
//		System.out.println(str);
//		String str = httpByGet(
//				"http://localhost:8080/ace-portal/userManageAction.do?method=insertNewUser&login_name=zhanglu22@lenovo.com&user_name=qwe&phone=234234&email=zhanglu22@lenovo.com");
//		logger.info(str);
		
	}
}
