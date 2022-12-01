package MainFunctions;

import java.security.*;
import javax.crypto.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

//Κλαση για την κρυπτογραφηση/αποκρυπτογραφηση με τον αλγοριθμο AES
public class AES {

    public static byte[] Encrypt (String plain, Key key) throws
            NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, NoSuchProviderException {

        //Προσθηκη του παροχου Bouncy Castle
        Security.addProvider(new BouncyCastleProvider());

        //Δημιορυγια Cipher με τον αλγοριμο AES απο την Bouncy Castle
        Cipher cipher = Cipher.getInstance("AES", "BC");
        //Ορισμος του Cipher για κωδικοποιηση με το κλειδι που δοθηκε
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(plain.getBytes()); //Επιστροφη κρυπτογραφημενων bytes
    }

    public static byte[] Decrypt (byte[] encrypted, Key key) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {

        //Προσθηκη του παροχου Bouncy Castle
        Security.addProvider(new BouncyCastleProvider());

        //Δημιορυγια Cipher με τον αλγοριμο AES απο την Bouncy Castle
        Cipher cipher = Cipher.getInstance("AES", "BC");
        //Ορισμος του Cipher για κωδικοποιηση με το κλειδι που δοθηκε
        cipher.init(Cipher.DECRYPT_MODE, key, cipher.getParameters());

        return cipher.doFinal(encrypted); //Επιστροφη αποκρυπτογραφημενων bytes
    }
}
