
package MainFunctions;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

//Κλαση με τις μεθοδους για τη δημιουργια κλειδιων για την ασσυμετρη κρυπτογραφηση
public class Keys {

    //Μεθοδος για δημιουργια κλειδιου συμμετρικης κρυπτογραφησης με τον αλγοριθμο PBKDF2WithHmacSHA1 της Bouncy Castle
    public static SecretKey generatePBKDF2Key (char[] P, byte[] S, int c, int dkLen) throws NoSuchAlgorithmException, NoSuchProviderException,
                                                                                            InvalidKeySpecException {
        //Προσθηκη του Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());

        SecretKeyFactory factorybc = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1", "BC");
        //Τα bytes επι 8 επειδη ο δημιουργος του PBEKeySpec παιρνει τη παραμετρο σε bits
        KeySpec spec = new PBEKeySpec(P, S, c, dkLen * 8);
        return factorybc.generateSecret(spec);
    }

    //Μεθοδος για δημιουργια ζευγους κλειδιων ασυμμετρης κρυπτογραφησης με τον αλγοριθμο RSA της Bouncy Castle μηκους 2048 bits
    public static KeyPair generateRSAKeyPair () throws NoSuchAlgorithmException, NoSuchProviderException {

        //Προσθηκη του Bouncy Castle Provider
        Security.addProvider(new BouncyCastleProvider());

        int KEY_SIZE = 2048;

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        generator.initialize(KEY_SIZE);

        return generator.generateKeyPair();
    }
}
