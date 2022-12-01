

package Grafika;

import Main.Main;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class LoginFrame extends javax.swing.JFrame {

    private final Main main;

    //Δημιουργια και παραμετροποιηση frame
    public LoginFrame (Main main, String username, String password) {
        this.main = main;
        initComponents();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("Assets/banner.png"));
        } catch (IOException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image dimg = img.getScaledInstance(logoPanel.getWidth(), logoPanel.getHeight(), Image.SCALE_SMOOTH);
        bannerPannel.add(new JLabel(new ImageIcon(dimg)));
        this.usernameField.setText(username);
        this.passwordField.setText(password);
        this.setLocationRelativeTo(null);
        this.loginButton.requestFocusInWindow();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        actionsP = new javax.swing.JPanel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        bannerPannel = new javax.swing.JPanel();
        logoPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PassManager");
        setFocusCycleRoot(false);
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
                    usernameField.setText("Username");
                    usernameField.setForeground(Color.DARK_GRAY);
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

        loginButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        loginButton.setForeground(new java.awt.Color(51, 51, 51));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
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

        javax.swing.GroupLayout actionsPLayout = new javax.swing.GroupLayout(actionsP);
        actionsP.setLayout(actionsPLayout);
        actionsPLayout.setHorizontalGroup(
            actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionsPLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(passwordField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        actionsPLayout.setVerticalGroup(
            actionsPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, actionsPLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerButton)
                .addGap(15, 15, 15))
        );

        bannerPannel.setName(""); // NOI18N
        bannerPannel.setPreferredSize(new java.awt.Dimension(150, 150));
        bannerPannel.setLayout(new java.awt.GridBagLayout());

        logoPanel.setBackground(new java.awt.Color(255, 51, 0));
        logoPanel.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout logoPanelLayout = new javax.swing.GroupLayout(logoPanel);
        logoPanel.setLayout(logoPanelLayout);
        logoPanelLayout.setHorizontalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        logoPanelLayout.setVerticalGroup(
            logoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        bannerPannel.add(logoPanel, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(actionsP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bannerPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(bannerPannel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(actionsP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Action του κουμπιου 
    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        main.showRegistration(usernameField.getText(), passwordField.getPassword()); //κληση της showRegistration της main για εμφανιση του RegisterFrame
    }//GEN-LAST:event_registerButtonActionPerformed

    //Action του κουμπιου login
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        //Κληση της register της main για τη διαδικασια της αυθεντικοποιησης του χρηστη
        main.login(usernameField.getText(), passwordField.getPassword());
    }//GEN-LAST:event_loginButtonActionPerformed

    private final ImageIcon icon = new ImageIcon("Assets/key.png");
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionsP;
    private javax.swing.JPanel bannerPannel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel logoPanel;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
