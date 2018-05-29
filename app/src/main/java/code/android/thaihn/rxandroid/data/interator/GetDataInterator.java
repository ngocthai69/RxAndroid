package code.android.thaihn.rxandroid.data.interator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import code.android.thaihn.rxandroid.utils.Constants;

public class GetDataInterator {

    /**
     * Get content string form URL
     *
     * @param strUrl
     * @return
     * @throws IOException
     */
    public String getContentFromUrl(String strUrl) throws IOException {
        String content = "";
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        url = new URL(strUrl);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constants.METHOD_GET);
        httpURLConnection.setConnectTimeout(Constants.CONNECTION_TIME_OUT);
        httpURLConnection.setReadTimeout(Constants.READ_INPUT_TIME_OUT);
        httpURLConnection.setDoInput(true);
        httpURLConnection.connect();

//        URL url = null;
//        SSLContext sslcontext = null;
//        HttpsURLConnection httpsURLConnection = null;
//        try {
//            sslcontext = SSLContext.getInstance("TLSv1.2");
//            sslcontext.init(null, null, null);
//            SSLSocketFactory NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());
//            url = new URL(strUrl);
//
//            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);
//            httpsURLConnection = (HttpsURLConnection) url.openConnection();
//            httpsURLConnection.connect();
//
//            int responseCode = httpsURLConnection.getResponseCode();
//            if (responseCode == httpsURLConnection.HTTP_OK) {
//                content = parserResultFromContent(httpsURLConnection.getInputStream());
//            }
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }

        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            content = parserResultFromContent(httpURLConnection.getInputStream());
        }
        return content;
    }

    /**
     * Parser result from input stream
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String parserResultFromContent(InputStream is) throws IOException {
        String result = "";
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Constants.CHARSET_NAME_UTF8));
        String line = "";
        while ((line = reader.readLine()) != null) {
            result += line;
        }
        is.close();
        return result;
    }
}
