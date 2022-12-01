package MainFunctions;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

//Κλαση με τις μεθοδους για εγγραφη πιστοποιητικων και κλειδιων σε αρχεια με τον PemWrite της Bouncy Castle
public class PemFile {

    //Μεθοδος για την εγγραφη ενος αντικειμενου σε ενα αρχειο με τον PemWriter
    public static void write (String description, byte[] bytes, String filename) throws FileNotFoundException, IOException {

        File outfile = new File(filename);

        if (!outfile.exists()) {
            outfile.createNewFile();
        }

        try (PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            PemObject pem = new PemObject(description, bytes);
            pemWriter.writeObject(pem);
        }
    }

    //Μεθοδος για το διαβασμα ενος αντικειμενου απο ενα αρχειο με τον PemReader
    public static PemObject read (String filename) throws FileNotFoundException, IOException {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(filename)))) {
            PemObject pem = pemReader.readPemObject();
            return pem;
        }
    }

    //Μεθοδος για το διαβασμα και κατασκευη ενος X.509 certificate απο ενα αρχειο
    //http://www.programcreek.com/java-api-examples/index.php?source_dir=usc-master/usc-channel-impl/src/main/java/org/opendaylight/usc/crypto/dtls/DtlsUtils.java
    public static X509Certificate loadCertificate (String filename) throws IOException, CertificateException, NoSuchProviderException {
        PemObject pem = read(filename);
        if (pem.getType().endsWith("X.509 CERTIFICATE")) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");
            return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(pem.getContent()));
        }
        throw new IllegalArgumentException(filename + " doesn't specify a valid certificate");
    }

    //Πηγη: https://stackoverflow.com/questions/11787571/how-to-read-pem-file-to-get-private-and-public-key
    //
    //Μεθοδος για το διαβασμα και κατασκευη ενος ιδιωτικου κλειδιου απο ενα αρχειο
    public static PrivateKey loadPrivateKey (String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PemObject pem = read(filename);
        if (pem.getType().endsWith("RSA PRIVATE KEY")) {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(pem.getContent());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }
        throw new IllegalArgumentException(filename + "filename +  doesn't specify a valid private key");
    }

    //Μεθοδος για το διαβασμα και κατασκευη ενος δημοσιου κλειδιου απο ενα αρχειο
    public static PublicKey loadPublicKey (String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        PemObject pem = read(filename);
        if (pem.getType().endsWith("RSA PUBLIC KEY")) {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(pem.getContent());
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
        throw new IllegalArgumentException("'resource' doesn't specify a valid private key");
    }
}
