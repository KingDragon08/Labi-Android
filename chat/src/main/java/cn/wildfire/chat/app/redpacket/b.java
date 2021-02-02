package cn.wildfire.chat.app.redpacket;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import com.imchat.ezn.R;

public class b {
    public static SSLSocketFactory a(Context paramContext) {
        SSLSocketFactory sSLSocketFactory = null;
        /*
        try {
            InputStream inputStream = paramContext.getResources().openRawResource(R.raw.njrmf360);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509Certificate = (X509Certificate)certificateFactory.generateCertificate(inputStream);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("ca", x509Certificate);
            String str = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(str);
            trustManagerFactory.init(keyStore);
            SSLContext sSLContext = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
            sSLContext.init(null, trustManagerFactory.getTrustManagers(), null);
            sSLSocketFactory = sSLContext.getSocketFactory();
        } catch (IOException iOException) {
            iOException.printStackTrace();
        } catch (CertificateException certificateException) {
            certificateException.printStackTrace();
        } catch (KeyStoreException keyStoreException) {
            keyStoreException.printStackTrace();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        } catch (KeyManagementException keyManagementException) {
            keyManagementException.printStackTrace();
        } catch (NoSuchProviderException noSuchProviderException) {
            noSuchProviderException.printStackTrace();
        }
         */
        return sSLSocketFactory;
    }
}
