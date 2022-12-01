package Main;

import Grafika.*;
import MainFunctions.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.bouncycastle.operator.ContentVerifierProvider;

import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentVerifierProviderBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCSException;

public class Main {

    //Το τρεχων frame της εφαρμογης
    private JFrame gui;

    //Ονομασιες για τα αρχεια
    private final File users_dir = new File("UserData");
    private final File authHashes_file = new File("authHashes.txt");
    private final String entries_foldername = "Entries";

    //Username και sKey του συνδεδεμενου χρηστη
    private String user;
    private Key sKey;

    //Αρχικα εμφανιζεται το LoginFrame
    public Main () {
        gui = new LoginFrame(this, "Username", "Password");
        gui.setVisible(true);
    }

    //Η διαδικασια εγγραφης των χρηστών
    public void register (String name, String surname, String username, char[] password, String email) {

        //Ελεγχεται αν υπαρχει ηδη χρηστης με αυτο το username και εμφανιζεται καταλληλο μηνυμα
        if (usernameExists(username)) {
            JOptionPane.showMessageDialog(gui, "Username already exists.", "Fail", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                //Αρχικα φτιαχωεται ενα ζευγος δημοσιου και ιδιοτικου κλειδιου RSA με μεγεθος 2048
                KeyPair keys = Keys.generateRSAKeyPair();
                RSAPrivateKey priv = (RSAPrivateKey) keys.getPrivate();
                RSAPublicKey pub = (RSAPublicKey) keys.getPublic();

                //και ενα Certification Request με τα στοιχεια του χρηστη
                PKCS10CertificationRequest CSRequest = Certificates.generateRequest(
                        keys,
                        name,
                        surname,
                        username,
                        email,
                        Certificates.getCAcert(),
                        Certificates.getCAprivateKey());

                //Φτιαχνεται ο φακελος ολων των χρηστων αν δεν υπαρχει
                if (users_dir.exists() == false) {
                    users_dir.mkdir();
                }
                // και ο φακελος για αυτον τον χρηστη
                File user_home = new File(users_dir.getPath() + "/" + username);
                user_home.mkdir();

                //Επειτα επαληθεύται το CertificationRequest μεσω ενος VerifierProvider που φτιαχνεται για την εφαρμογη απο το δημοσιο κλειδι της
                ContentVerifierProvider CA_verifier = new JcaContentVerifierProviderBuilder().setProvider("BC").build(Certificates.getCAcert().getPublicKey());
                if (CSRequest.isSignatureValid(CA_verifier)) {
                    System.out.println("SHA1withRSA" + ": PKCS#10 request verified.");

                    //Αν επαληθευτεί το αιτημα εκδιδεται απο την PassManager CA το πιστοποιτητικο του χρηστη βαση των στοιχειων του αιτηματος
                    X509Certificate cert = Certificates.getCertificate(CSRequest, Certificates.getCAcert(), Certificates.getCAprivateKey());

                    // και τα κλειδια και το πιστοποιητικο του χρηστη γραφονται μεσω της κλασης PemWriter της Bouncy Castle σε αρχεια στο φακελο του
                    PemFile.write("X.509 CERTIFICATE", cert.getEncoded(), user_home.getPath() + "/cert.crt");
                    PemFile.write("RSA PRIVATE KEY", priv.getEncoded(), user_home.getPath() + "/private.pem");
                    PemFile.write("RSA PUBLIC KEY", pub.getEncoded(), user_home.getPath() + "/public.pem");

                    //Δημιουργια sKey και authHash για τον χρηστη ακολουθώντας το προτυπο PBKDF2 ( μορφη DK = PBKDF2 (P, S, c, dkLen) )
                    SecretKey sKey = Keys.generatePBKDF2Key(password, username.getBytes(), 2000, 16);
                    SecretKey authHash = Keys.generatePBKDF2Key(toChars(sKey.getEncoded()), toBytes(password), 1000, 16);

                    //Το authHash γραφεται στο αρχειο με τα authHashes των χρηστων
                    storeAuthHash(username, authHash.getEncoded());

//    Η PBKDF2KeyImpl δεν υλοποιει την μεθοδο destroy της SecretKey που υπαρχει στη Java 8 για καταστροφη των αντικειμενων απο την μνημη
//                    sKey.destroy();
//                    authHash.destroy();
//    -> DestroyFailedException
                    // Εμφανιση μηνυματος
                    JOptionPane.showMessageDialog(gui, "Your registration completed successfully.\n"
                                                  + "Exported files paths:\n"
                                                  + "Certificate: " + user_home.getPath() + "\\cert.crt\n"
                                                  + "Private Key: " + user_home.getPath() + "\\private.pem\n"
                                                  + "Public Key: " + user_home.getPath() + "\\public.pem\n",
                                                  "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("SHA1withRSA" + ": Failed verify check.");
                    JOptionPane.showMessageDialog(gui, "Certification Request Failed Verification.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NoSuchAlgorithmException | NoSuchProviderException | OperatorCreationException | IOException | InvalidKeySpecException |
                     CertificateException | KeyStoreException | UnrecoverableKeyException | PKCSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Η διαδικασια αυθεντικοποιησης των χρηστών
    public void login (String username, char[] password) {
        try {

            //Δημιουργια sKey και authHash για τον χρηστη ακολουθώντας το προτυπο PBKDF2 ( μορφη DK = PBKDF2 (P, S, c, dkLen) )
            SecretKey sKey = Keys.generatePBKDF2Key(password, username.getBytes(), 2000, 16);
            SecretKey authHash = Keys.generatePBKDF2Key(toChars(sKey.getEncoded()), toBytes(password), 1000, 16);

            //Συγκριση των bytes του authHash που δημιουργηθηκε με τα στοιχεια που εδωσε ο χρηστης
            // με αυτο που δημιουργηθηκε κατα την εγγραφη του και ειναι αποθηκευμενο στο αρχειο authHashes.txt
            if (Arrays.equals(getAuthHash(username), authHash.getEncoded())) {

                //Ευρεση του φακελου του χρηστη
                File user_home = new File(users_dir.getPath() + "/" + username);

                //Φορτωση του πιστοποιητικου του χρηστη και επιβεβαιωση της εγγυροτητας του (βαση ημερομηνιας)
                // και της γνισιώτητας του δηλαδη οτι εκδοθηκε απο τη PassManager CA
                X509Certificate userCert = PemFile.loadCertificate(user_home.getPath() + "/cert.crt");

                userCert.checkValidity();
                userCert.verify(Certificates.getCAcert().getPublicKey());

                this.sKey = sKey;

                this.user = username;

                // κλεισιμο του LoginFrame και ανοιγμα του PMFrame
                gui.dispose();
                gui = new PMFrame(this);
                gui.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(gui, "Invalid Credentials.", "Fail", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | IOException | KeyStoreException |
                 UnrecoverableKeyException | InvalidKeyException | SignatureException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {

            JOptionPane.showMessageDialog(gui, "Invalid Certificate", "Fail", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Διαδικασια δημιουργιας κρυπτογραφημενης εγγραφης με τα στοιχεια του λογαριασμου του χρηστη για ενα domain
    public void createEntry (String domain, String username, char[] password, String comment) {

        //Το αρχειο της εγγραφης παιρνει ονομα τη τιμη του domain αφαιρωντας ολους τους μη αλφαριθμιτικους χαρακτηρες
        String filename = domain.replaceAll("[^\\w]", "");

        File user_entries_dir = new File(users_dir + "/" + user + "/" + entries_foldername);

        //Δημιουργειται αν δεν υπαρχει ο φακελος Entries στον φακελο του χρηστη
        if (user_entries_dir.exists() == false) {
            user_entries_dir.mkdir();
        }

        //Και μεσα σε αυτον δημιουργειται ο φακελος της εγγραφης
        File entry_file = new File(user_entries_dir + "/" + filename);

        if (entry_file.exists() == false) {
            try {
                entry_file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Ανοιγμα output ροης για το αρχειο
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(entry_file, true)))) {

            //Το username και το password κρυπτογραφουνται συμμετρικα με το κλειδι του χρηστη
            byte[] encryptedUserBytes = AES.Encrypt(username, sKey);
            byte[] encryptedPassBytes = AES.Encrypt(String.valueOf(password), sKey);

            //Μετατρεπονται σε Base64 String
            String encryptedUsername = Base64.getEncoder().encodeToString(encryptedUserBytes);
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPassBytes);

            //Γραφονται στο αρχειο μεσω της ροης τα στοιχεια της εγγραφης το καθενα σε μια σειρα
            out.print("");
            out.println(domain);
            out.println(encryptedUsername);
            out.println(encryptedPassword);
            out.println(comment);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | NoSuchProviderException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Διαδικασια για την ευρεση και αποκρυπτογραφηση μιας εγγραφης και των στοιχειων της απο αρχειο βαση του domain (αντιστροφη διαδικαασια απο πριν)
    public HashMap<String, String> getEntry (String domain) {

        HashMap<String, String> entryValues = null;

        //Το αρχειο της εγγραφης εχει ονομα τη τιμη του domain αφαιρωντας ολους τους μη αλφαριθμιτικους χαρακτηρες    
        String filename = domain.replaceAll("[^\\w]", "");

        File entry_file = new File(users_dir + "/" + user + "/" + entries_foldername + "/" + filename);

        //Ανοιγμα input ροης για το αρχειο
        try (BufferedReader in = new BufferedReader(new FileReader(entry_file))) {

            entryValues = new HashMap();

            //Διαβαζονται οι 4 πρωτες γραμμες που περιεχουν τα στοιχεια της εγγραφης
            in.readLine();
            String encryptedUsername = in.readLine();
            String encryptedPassword = in.readLine();
            String comment = in.readLine();

            //Μετατροπη των username και password απο Base64 String σε Bytes
            byte[] encryptedUserBytes = Base64.getDecoder().decode(encryptedUsername);
            byte[] encryptedPassBytes = Base64.getDecoder().decode(encryptedPassword);

            //Αποκρυπτογραφηση των Bytes τους και αναπαρσταση του σε String
            String username = new String(AES.Decrypt(encryptedUserBytes, sKey));
            String password = new String(AES.Decrypt(encryptedPassBytes, sKey));

            //Προσθηκη στο HashMap για επιστροφη
            entryValues.put("domain", domain);
            entryValues.put("username", username);
            entryValues.put("password", password);
            entryValues.put("comment", comment);

        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchProviderException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entryValues;
    }

    //Διαδικασια για την ευρεση ολων των εγγραφων και των στοιχειων τους του χρηστη χωρις αποκρυπτογραφηση του username και password
    public ArrayList<HashMap<String, String>> getEntries () {

        ArrayList<HashMap<String, String>> entries = new ArrayList();

        //Το path του φακελου με τις εγραφες
        File entries_folder = new File(users_dir + "/" + user + "/" + entries_foldername);

        File[] entries_files = entries_folder.listFiles();

        if (entries_files != null) {
            //Διαβασμα ολων των εγγραφων
            for (File entry_file : entries_files) {
                try (BufferedReader in = new BufferedReader(new FileReader(entry_file))) {

                    HashMap<String, String> entryValues = new HashMap();

                    String domain = in.readLine();
                    in.readLine();
                    in.readLine();
                    String comment = in.readLine();

                    entryValues.put("domain", domain);
                    entryValues.put("comment", comment);

                    entries.add(entryValues);

                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return entries;
    }

    //Διαδικασια για την ανανέωση των στοιχειων μιας κρυπτογραφημενης εγγραφης
    public void setEntry (String old_domain, String domain, String username, char[] password, String comment) {

        //Αν το domain αλλαξε τοτε σβηνεται το παλιο αρχειο της εγγραφης
        if (old_domain.equals(domain) == false) {
            String old_filename = old_domain.replaceAll("[^\\w]", "");
            File old_entry_file = new File(users_dir + "/" + user + "/" + entries_foldername + "/" + old_filename);
            try {
                old_entry_file.delete();
            } catch (Exception ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Το αρχειο της εγγραφης εχει ονομα τη τιμη του domain αφαιρωντας ολους τους μη αλφαριθμιτικους χαρακτηρες
        String filename = domain.replaceAll("[^\\w]", "");
        //Το path του αρχειο της εγγραφης
        File entry_file = new File(users_dir + "/" + user + "/" + entries_foldername + "/" + filename);

        //Ανοιγμα ροης για το αρχειο
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(entry_file)))) {

            //Το username και το password κρυπτογραφουνται συμμετρικα με το κλειδι του χρηστη
            byte[] encryptedUserBytes = AES.Encrypt(username, sKey);
            byte[] encryptedPassBytes = AES.Encrypt(String.valueOf(password), sKey);

            //Μετατρεπονται σε Base64 String
            String encryptedUsername = Base64.getEncoder().encodeToString(encryptedUserBytes);
            String encryptedPassword = Base64.getEncoder().encodeToString(encryptedPassBytes);

            //Γραφονται στο αρχειο μεσω της ροης τα στοιχεια της εγγραφης το καθενα σε μια σειρα
            out.print("");
            out.println(domain);
            out.println(encryptedUsername);
            out.println(encryptedPassword);
            out.println(comment);

        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | NoSuchPaddingException |
                 BadPaddingException | NoSuchProviderException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Διαγραφη εγγραφης βαση domain
    public void deleteEntry (String domain) {

        //Το αρχειο της εγγραφης εχει ονομα τη τιμη του domain αφαιρωντας ολους τους μη αλφαριθμιτικους χαρακτηρες    
        String filename = domain.replaceAll("[^\\w]", "");

        File entry_file = new File(users_dir + "/" + user + "/" + entries_foldername + "/" + filename);

        //Διαγραφεται το αρχειο της εγγραφης
        try {
            entry_file.delete();
        } catch (Exception ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Αποθηκευση του authHash ενος χρηστη στο αρχειο με τα authHashes
    public void storeAuthHash (String username, byte[] authHash) {

        //Αν δεν υπαρχει το αρχειο με τα authHashes δημιουργειται
        if (authHashes_file.exists() == false) {
            try {
                authHashes_file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Ανοιγμα ροης για το αρχειο
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(authHashes_file, true)))) {

            //Γραφεται σε μια γραμμη το username και το authHash χωρισμενα με tab
            out.print(username + "\t" + Base64.getEncoder().encodeToString(authHash));

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Ευρεση authHash βαση username
    public byte[] getAuthHash (String username) {
        //Ανοιγμα ροης
        try (BufferedReader in = new BufferedReader(new FileReader(authHashes_file))) {

            String line;

            //Διαβασμα γραμμη γραμμη
            while ((line = in.readLine()) != null) {

                //Αν το username ειναι το αναζητουμενο τοτε επιστρεφεται το authHash
                String user = line.split("\t")[0]; //split της γραμμης βαση του κενου tab για να παρω το πρωτο που ειναι το username
                String authHash = line.split("\t")[1]; //και το δευτερο που ειναι το authHash
                if (user.equals(username)) {
                    return Base64.getDecoder().decode(authHash); //Μετατρέπεται απο Base64 String σε Bytes
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //Ελεγχος αν υπαρχει authHash για το username
    public boolean usernameExists (String username) {
        if (authHashes_file.exists()) {
            //Ανοιγμα ροης
            try (BufferedReader in = new BufferedReader(new FileReader(authHashes_file))) {

                String line;

                //Διαβασμα γραμμη γραμμη
                while ((line = in.readLine()) != null) {

                    //Αν το username ειναι το αναζητουμενο τοτε επιστρεφει true
                    String user = line.split("\t")[0]; //split της γραμμης βαση του κενου tab για να παρω το πρωτο που ειναι το username
                    if (user.equals(username)) {
                        return true;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    //Ελεγχος αν υπαρχει εγγραφη για το domain
    public boolean domainEntryExists (String domain) {

        //Το αρχειο της εγγραφης εχει ονομα τη τιμη του domain αφαιρωντας ολους τους μη αλφαριθμιτικους χαρακτηρες
        String filename = domain.replaceAll("[^\\w]", "");

        File entry_file = new File(users_dir + "/" + user + "/" + entries_foldername + "/" + filename);

        return entry_file.exists(); //Επιστροφη του boolean αν υπαρχει το αρχειο η οχι
    }

    //Μετατροπη πινακα byte σε pinaka char (γιατι passwords ερχοναι σε μορφη char[] απο τα JTextFields αλλα ο PBKDF2 παιρνει παραμετρο σε byte[])
    //https://www.javacodegeeks.com/2010/11/java-best-practices-char-to-byte-and.html
    public static char[] toChars (byte[] bytes) {
        char[] buffer = new char[bytes.length >> 1];
        for (int i = 0; i < buffer.length; i++) {
            int bpos = i << 1;
            char c = (char) (((bytes[bpos] & 0x00FF) << 8) + (bytes[bpos + 1] & 0x00FF));
            buffer[i] = c;
        }
        return buffer;
    }

    //Αντιστροφα με πριν
    //https://stackoverflow.com/a/9670279/4301848
    private byte[] toBytes (char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                                          byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    //Καταστροφη τρεχοντος παραθυρου και εμφανιση RegistrationFrame
    public void showRegistration (String username, char[] password) {
        gui.dispose();
        gui = new RegisterFrame(this, username);
        gui.setVisible(true);
    }

    //Καταστροφη τρεχοντος παραθυρου και εμφανιση LoginFrame
    public void showLogin (String username) {
        gui.dispose();
        gui = new LoginFrame(this, username, "Password");
        gui.setVisible(true);
    }

    public static void main (String[] args) {
        Main main = new Main();
    }

}
