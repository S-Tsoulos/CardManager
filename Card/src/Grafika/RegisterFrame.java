

package Grafika;

import Main.Main;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JOptionPane;

public final class RegisterFrame extends javax.swing.JFrame {

    private final Main main;

    //Δημιουργια και παραμετροποιηση frame
    public RegisterFrame (Main main, String username) {
        this.main = main;
        initComponents();
        this.usernameField.setText(username);
        this.setLocationRelativeTo(null);
        this.backButton.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        actionsP = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        registerButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        password2Field = new javax.swing.JPasswordField();
        surnameField = new javax.swing.JTextField();
        emailField = new javax.swing.JFormattedTextField(new EmailFormatter());
        nameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PassManager");
        setIconImage(icon.getImage());
        setResizable(false);

        actionsP.setToolTipText("");

        usernameField.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        usernameField.setForeground(java.awt.Color.DARK_GRAY);
        usernameField.setText("Username");
        usernameField.setPreferredSize(new java.awt.Dimension(75, 24));
        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Username")) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Username");
                }
            }
        });

        passwordField.setFont(new java.awt.Font("Ubuntu Light", 0, 22)); // NOI18N
        passwordField.setForeground(java.awt.Color.DARK_GRAY);
        passwordField.setText("Password");
        passwordField.setPreferredSize(new java.awt.Dimension(75, 24));
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(passwordField.getPassword(),"Password".toCharArray())) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText("Password");
                }
            }
        });

        registerButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        registerButton.setForeground(new java.awt.Color(51, 51, 51));
        registerButton.setText("Register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        backButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        backButton.setForeground(new java.awt.Color(51, 51, 51));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        password2Field.setFont(new java.awt.Font("Ubuntu Light", 0, 22)); // NOI18N
        password2Field.setForeground(java.awt.Color.DARK_GRAY);
        password2Field.setText("Password");
        password2Field.setPreferredSize(new java.awt.Dimension(75, 24));
        password2Field.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(password2Field.getPassword(),"Password".toCharArray())) {
                    password2Field.setText("");
                    password2Field.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (password2Field.getPassword().length == 0) {
                    password2Field.setForeground(Color.GRAY);
                    password2Field.setText("Password");
                }
            }
        });

        surnameField.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        surnameField.setForeground(java.awt.Color.DARK_GRAY);
        surnameField.setText("Surname");
        surnameField.setPreferredSize(new java.awt.Dimension(75, 24));
        surnameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (surnameField.getText().equals("Surname")) {
                    surnameField.setText("");
                    surnameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (surnameField.getText().isEmpty()) {
                    surnameField.setForeground(Color.GRAY);
                    surnameField.setText("Surname");
                }
            }
        });

        emailField.setForeground(java.awt.Color.DARK_GRAY);
        emailField.setText("Email");
        emailField.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Email")) {
                    emailField.setText("");
                    emailField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.GRAY);
                    emailField.setText("Email");
                }
            }
        });

        nameField.setFont(new java.awt.Font("Ubuntu Light", 0, 18)); // NOI18N
        nameField.setForeground(java.awt.Color.DARK_GRAY);
        nameField.setText("Name");
        nameField.setPreferredSize(new java.awt.Dimension(75, 24));
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setForeground(Color.GRAY);
                    nameField.setText("Name");
                }
            }
        });

        javax.swing.GroupLayout actionsPLayout = new javax.swing.GroupLayout(actionsP);
        actionsP.setLayout(actionsPLayout);
        actionsPLayout.setHorizontalGroup(
            actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(password2Field, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(surnameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailField)
                    .addComponent(nameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        actionsPLayout.setVerticalGroup(
            actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPLayout.createSequentialGroup()
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(emailField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(password2Field, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(registerButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(backButton)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(actionsP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(actionsP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Action του κουμπιου back
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        main.showLogin(usernameField.getText()); //κληση της showLogin της main για εμφανιση του LoginFrame
    }//GEN-LAST:event_backButtonActionPerformed

    //Action του κουμπιου register
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        if (Arrays.equals(passwordField.getPassword(), password2Field.getPassword())) { //Αν οι δυο κωδικοι ταιριαζουν

            //Κληση της register της main για τη διαδικασι της εγγραφης
            main.register(nameField.getText(), surnameField.getText(), usernameField.getText(), passwordField.getPassword(), emailField.getText());
            //κληση της showLogin της main για εμφανιση του LoginFrame
            main.showLogin(usernameField.getText());

        } else {
            JOptionPane.showMessageDialog(this, "Passwords doesn't match!", "Password missmatch", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_registerButtonActionPerformed
    private final ImageIcon icon = new ImageIcon("Assets/key.png");
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsP;
    private javax.swing.JButton backButton;
    private javax.swing.JFormattedTextField emailField;
    private javax.swing.JTextField nameField;
    private javax.swing.JPasswordField password2Field;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    private javax.swing.JTextField surnameField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

    //https://stackoverflow.com/questions/6009371/jformattedtextfield-question
    public class EmailFormatter extends AbstractFormatter {

        @Override public Object stringToValue (String string) throws ParseException {
            Matcher matcher = regexp.matcher(string);
            if (matcher.matches()) {
                return string;
            }
            throw new ParseException("Not an email", 0);
        }

        @Override public String valueToString (Object value) {
            return (String) value;
        }

        final private Pattern regexp = Pattern.compile(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    }
}
