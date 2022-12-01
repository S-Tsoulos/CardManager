package MainFunctions;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class Certificates {

    private static final String signatureAlgorithm = "SHA1withRSA";

    //Μεθοδος για δημιουργια PKCS10CertificationRequest (Bouncy Castle)
    //Πηγη: https://github.com/joschi/cryptoworkshop-bouncycastle/blob/master/src/main/java/cwguide/JcaPKCS10Example.java
    public static PKCS10CertificationRequest generateRequest (KeyPair keys, String name, String surname, String username,
                                                              String email, X509Certificate CAcert, PrivateKey CAprivateKey)
            throws Exception {

        //Παραμετροι του αιτηματος πιστοποιητικου τα στοιχεια που δινονται
        X500NameBuilder x500NameBld = new X500NameBuilder(BCStyle.INSTANCE);
        x500NameBld.addRDN(BCStyle.CN, username);
        x500NameBld.addRDN(BCStyle.NAME, name);
        x500NameBld.addRDN(BCStyle.SURNAME, surname);
        x500NameBld.addRDN(BCStyle.EmailAddress, email);
        X500Name subject = x500NameBld.build();

        //Δημιουργια του builder με τις παραμετρους και το δημοσιο κλειδι
        PKCS10CertificationRequestBuilder requestBuilder = new JcaPKCS10CertificationRequestBuilder(subject, keys.getPublic());

        //Extra παραμετροι με τα στοιχεια του εκδοτη και το δημοσιο κλειδι
        ExtensionsGenerator extGen = new ExtensionsGenerator();
        extGen.addExtension(Extension.subjectKeyIdentifier, false, keys.getPublic().getEncoded());
        extGen.addExtension(Extension.certificateIssuer, false, new X500Name(CAcert.getSubjectDN().getName()));
        //BasicConstraints false επειδη το πιστοποιητικο δεν ειναι ανθυπογραφο
        extGen.addExtension(Extension.basicConstraints, false, new BasicConstraints(false));
        requestBuilder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, extGen.generate());

        //Δημιουργια και επιστροφη πιστοποιητικου
        return requestBuilder.build(new JcaContentSignerBuilder(signatureAlgorithm).setProvider("BC").build(CAprivateKey));
    }

    //Μεθοδος για δημιουργια PKCS10CertificationRequest (Bouncy Castle)
    //Πηγη: http://programmingquirks.com/sign-csr-with-extensions-using-bouncy-castle/
    public static X509Certificate getCertificate (PKCS10CertificationRequest csr, X509Certificate CAcert, PrivateKey CAprivateKey) throws
            OperatorCreationException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, CertificateException {

        //Προσθηκη του Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());

        //Παραμετροι πιστοποιητικου
        // το startDate απο την οποιο και μετα το πιστοποιητικο ειναι valid ειναι η τρεχουσα timestamp
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();

        //Tα στοιχεια του αιτηματος
        X500Name subject = csr.getSubject();

        //Δημιουργια SerialNumber βαση του τρεχων timestamp
        BigInteger certSerialNumber = BigInteger.valueOf(System.currentTimeMillis()); // <-- Using the current timestamp as the certificate serial number

        // το endDate απο την οποιο και μετα το πιστοποιητικο δεν ειναι valid ειναι 6 μηνες μετα
        calendar.add(Calendar.MONTH, 6); 
        Date endDate = calendar.getTime();

        //Δημιουργια ContentSigner απο το private κλειδι της PassManager CA
        ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(CAprivateKey);

        //Κατασκευη του δημοσιου κλειδιου που περιεχει το αιτημα
        SubjectPublicKeyInfo pkInfo = csr.getSubjectPublicKeyInfo();
        RSAKeyParameters rsa = (RSAKeyParameters) PublicKeyFactory.createKey(pkInfo);
        RSAPublicKeySpec rsaSpec = new RSAPublicKeySpec(rsa.getModulus(), rsa.getExponent());
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(rsaSpec);

        //Δημιουργια builder με τις παραμετρους αυτες
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                new X500Name("cn=" + CAcert.getSubjectDN().getName()),
                certSerialNumber,
                startDate,
                endDate,
                subject,
                publicKey
        );

        //Προσθηκη και των extensions που περιεχει το αιτημα στον builder
        org.bouncycastle.asn1.pkcs.Attribute[] attributes = csr.getAttributes();
        for (org.bouncycastle.asn1.pkcs.Attribute attr : attributes) {
            if (attr.getAttrType().equals(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest)) {
                Extensions extensions = Extensions.getInstance(attr.getAttrValues().getObjectAt(0));
                Enumeration e = extensions.oids();
                while (e.hasMoreElements()) {
                    ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
                    Extension ext = extensions.getExtension(oid);
                    certBuilder.addExtension(oid, ext.isCritical(), ext.getParsedValue());
                }
            }
        }
        //Δημιουργια και επιστροφη του πιστοποιητικου (Bouncy Castle Provider)
        return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));
    }

    //Μεθοδος για δημιουργια ανθυπογραφου πιστοποιητικου
    //Πηγη: https://stackoverflow.com/questions/29852290/self-signed-x509-certificate-with-bouncy-castle-in-java
    public static X509Certificate getSelfSignedCertificate (KeyPair keyPair, String subjectCN) throws CertificateException, IOException,
                                                                                                      OperatorCreationException {
        //Προσθηκη του Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());

        //Παραμετροι πιστοποιητικου
        // το startDate απο την οποιο και μετα το πιστοποιητικο ειναι valid ειναι η τρεχουσα timestamp
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();

        //X500 Common Name το ονομα που δοθηκε
        X500Name CAname = new X500Name("cn=" + subjectCN);

        //Δημιουργια SerialNumber βαση του τρεχων timestamp
        BigInteger certSerialNumber = BigInteger.valueOf(System.currentTimeMillis()); // <-- Using the current timestamp as the certificate serial number

        // το endDate απο την οποιο και μετα το πιστοποιητικο δεν ειναι valid ειναι 1 χρονος μετα
        calendar.add(Calendar.YEAR, 1);
        Date endDate = calendar.getTime();

        //Δημιουργια ContentSigner απο το private κλειδι του pair που δοθηκε (ο εαυτος της)
        ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(keyPair.getPrivate());

        //Δημιουργια του builder με τις παραμετρους και το δημοσιο κλειδι
        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                CAname,
                certSerialNumber,
                startDate,
                endDate,
                CAname,
                keyPair.getPublic());

        //BasicConstraints true επειδη το πιστοποιητικο ειναι ανθυπογραφο
        certBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true)); 

        //Δημιουργια και επιστροφη του πιστοποιητικου (Bouncy Castle Provider)
        return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certBuilder.build(contentSigner));
    }

    //Μεθοδος για φορτωση του ιδιωτικου κλειδιου της εφαρμογης (PassManager CA) απο το αρχειο keystore
    public static PrivateKey getCAprivateKey () throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException,
                                                       CertificateException, UnrecoverableKeyException {
        //Φορτωση του KeyStore απο το αρχειο
        FileInputStream input = new FileInputStream(keystore_file);
        KeyStore getkeystore = KeyStore.getInstance(KeyStore.getDefaultType());
        getkeystore.load(input, keystore_password.toCharArray());

        //Επιστροφη του "CA Private Key" απο το KeyStore
        return (PrivateKey) getkeystore.getKey("CA Private Key", keystore_password.toCharArray());
    }

    //Μεθοδος για φορτωση του ανθυπογραφου πιστοποιητικου της εφαρμογης (PassManager CA) απο το αρχειο keystore
    public static X509Certificate getCAcert () throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException,
                                                      CertificateException, UnrecoverableKeyException {
        //Φορτωση του KeyStore απο το αρχειο
        FileInputStream input = new FileInputStream(keystore_file);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(input, keystore_password.toCharArray());

        //Επιστροφη του "CA Cerificate" απο το KeyStore
        return (X509Certificate) keystore.getCertificate("CA Cerificate");
    }

    //Το ονομα αρχειου του KeyStore και ο κωδικος του
    private final static File keystore_file = new File("keystore.jks");
    private final static String keystore_password = "PMCAkeystorePassword";

    //Δημιουργια του ζευγους RSA κλειδιων και του ανθυπογραφου πιστοποιηκου εφαρμογης (PassManager CA)
    // και KeyStore στο οποιο φορτωνονται τα παραπανω και επειτα αποθηκευεται σε αρχειο
    public static void main (String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, CertificateException, IOException,
                                                   OperatorCreationException, KeyStoreException {

        //Δημιουργια PemWriter με για το stream του output console για εμφανιση των κλειδιων και του πιστοποιηκου στην οθονη
        PemWriter conPemWriter = new PemWriter(new PrintWriter(System.out));

        //Δημιουργια του ζευγους RSA κλειδιων με μηκος 2048
        KeyPair CAkeys = Keys.generateRSAKeyPair();
        //Δημιουργια του ανθυπογραφου πιστοποιηκου 
        X509Certificate CAcert = getSelfSignedCertificate(CAkeys, "PassManager CA");

        //Δημιουργια PemObject για αυτα ωστε να τυπωθουν στην οθονη με τον PemWriter
        PemObject privateCAkeyPEM = new PemObject("RSA PRIVATE KEY", CAkeys.getPrivate().getEncoded());
        PemObject publicCAkeyPEM = new PemObject("RSA PUBLIC KEY", CAkeys.getPublic().getEncoded());
        PemObject CAcertPEM = new PemObject("X.509 CERTIFICATE", CAcert.getEncoded());

        //Τυπωμα των PemObject αυτων  στην οθονη με τον PemWriter
        conPemWriter.writeObject(privateCAkeyPEM);
        conPemWriter.writeObject(publicCAkeyPEM);
        conPemWriter.writeObject(CAcertPEM);
        conPemWriter.flush();

        //Δημιουργια KeyStore
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, keystore_password.toCharArray());

        //Αποθηκευση του πιστοποιητικου στο keystore
        keyStore.setCertificateEntry(
                "CA Cerificate",
                CAcert);
        //Αποθηκευση του ιδιωτικου κλειδιου στο keystore
        keyStore.setKeyEntry(
                "CA Private Key",
                CAkeys.getPrivate(),
                keystore_password.toCharArray(),
                new X509Certificate[]{CAcert});
        //Δε δημιουργειται entry για το δημοσιο κλειδι στο keystore καθως εμπεριεχεται στο πιστοποιητικο

        PemFile.write("PassManager CA X.509 Certificate", CAcert.getEncoded(), "CA.crt");
        //
        //Αποθηκευση του keystore σε αρχειο
        FileOutputStream output = new FileOutputStream(keystore_file);
        keyStore.store(output, keystore_password.toCharArray());
    }
}