
package Grafika;

import Main.Main;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class PMFrame extends javax.swing.JFrame {

    private final Main main;

    private final String[] column_names = new String[]{"Domain", "Comment"};

    //Δημιουργια και παραμετροποιηση frame
    public PMFrame (Main main) {
        this.main = main;
        initComponents();
        this.setLocationRelativeTo(null);
        this.jScrollPane.requestFocus();
        this.updateEntriesTable();
    }

    //Μεθοδος για update του πινακα με των entries 
    public void updateEntriesTable () {

        DefaultTableModel model = (DefaultTableModel) entriesTable.getModel();

        ArrayList<HashMap<String, String>> entries_data = main.getEntries();

        for (HashMap<String, String> entry_data : entries_data) {
            model.addRow(new Object[]{entry_data.get("domain"), entry_data.get("comment")});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EntryPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        domainField = new javax.swing.JTextField();
        commentField = new javax.swing.JTextField();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        showButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        entriesTable = new javax.swing.JTable();

        titleLabel.setText("Στοιχεία εγγραφής:");

        domainField.setText("Domain");

        commentField.setText("Comment");

        usernameField.setText("Username");

        passwordField.setText("Password");

        javax.swing.GroupLayout EntryPanelLayout = new javax.swing.GroupLayout(EntryPanel);
        EntryPanel.setLayout(EntryPanelLayout);
        EntryPanelLayout.setHorizontalGroup(
            EntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(domainField)
                    .addComponent(commentField, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(usernameField)
                    .addComponent(passwordField))
                .addContainerGap())
        );
        EntryPanelLayout.setVerticalGroup(
            EntryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(domainField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commentField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PassManager");
        setIconImage(icon.getImage());

        showButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        showButton.setForeground(new java.awt.Color(51, 51, 51));
        showButton.setText("Show");
        showButton.setPreferredSize(new java.awt.Dimension(120, 50));
        showButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showButtonActionPerformed(evt);
            }
        });

        addButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        addButton.setForeground(new java.awt.Color(51, 51, 51));
        addButton.setText("Add");
        addButton.setPreferredSize(new java.awt.Dimension(120, 50));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        editButton.setForeground(new java.awt.Color(51, 51, 51));
        editButton.setText("Edit");
        editButton.setPreferredSize(new java.awt.Dimension(120, 50));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Ubuntu Mono", 1, 18)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(51, 51, 51));
        deleteButton.setText("Delete");
        deleteButton.setPreferredSize(new java.awt.Dimension(120, 50));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(showButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DefaultTableModel model = new DefaultTableModel(0, 2) ;
        model.setColumnIdentifiers(column_names);
        entriesTable.setModel(model);
        jScrollPane.setViewportView(entriesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Action του κουμπιου delete
    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        DefaultTableModel model = (DefaultTableModel) entriesTable.getModel();

        //Eντοπισμος της επιλεγμενης γραμμης στον πινακα
        int selected_row = entriesTable.getSelectedRow(); 

        if (selected_row > -1) { //Αν εχει γινει επιλογη

            //Ο επιλεγμενος domain που βρισκεται στη πρωτη στηλη της επιλεγμενης γραμμης
            String selected_domain = (String) model.getValueAt(selected_row, 0);

            //εμφανηση μηνυματος για επιβεβαιωση
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this entry?", "Delete password entry",
                                                       JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {

                //Κληση της delete της main για τη διαδικασια διαγραφης της εγγραφης
                main.deleteEntry(selected_domain);

                //Αφαιρεση της γραμμης απο τον πινακα
                model.removeRow(selected_row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to delete.", "No entry selected", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    //Action του κουμπιου edite
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        //Τα TextField γινονται editable
        domainField.setEditable(true);
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        commentField.setEditable(true);

        DefaultTableModel model = (DefaultTableModel) entriesTable.getModel();

        //Eντοπισμος της επιλεγμενης γραμμης στον πινακα
        int selected_row = entriesTable.getSelectedRow();

        if (selected_row > -1) { //Αν εχει γινει επιλογη

            //Ο επιλεγμενος domain που βρισκεται στη πρωτη στηλη της επιλεγμενης γραμμης
            String selected_domain = (String) model.getValueAt(selected_row, 0);

            //Κληση της getEntry με το επιλεγμενο domain της main για να παρουμε τα στοιχεια της εγγραφης
            HashMap<String, String> entry = main.getEntry(selected_domain);

            String old_domain = entry.get("domain");

            //Ενημερωση των TextField με τα στοιχεια της εγγραφης
            domainField.setText(entry.get("domain"));
            usernameField.setText(entry.get("username"));
            passwordField.setText(entry.get("password"));
            commentField.setText(entry.get("comment"));

            int option = JOptionPane.showConfirmDialog(this, EntryPanel, "Edit password entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {

                if (!main.domainEntryExists(domainField.getText()) || domainField.getText().equals(old_domain)) { //Αν δεν υπαρχει αλλη εγγραφη εκτος απο αυτη για αυτο το domain

                    //Κληση της setEntry με τα στοιχεια της εγγραφης για ανανεωση της
                    main.setEntry(selected_domain, domainField.getText(), usernameField.getText(), passwordField.getText().toCharArray(), commentField.getText());

                    //Ενημερωση της εγγραφης στον πινακα
                    model.setValueAt(domainField.getText(), selected_row, 0);
                    model.setValueAt(commentField.getText(), selected_row, 1);
                    
                } else {
                    JOptionPane.showMessageDialog(this, "There is already an entry for this domain.", "Entry exists", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to edit.", "No entry selected", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_editButtonActionPerformed

    //Action του κουμπιου show
    private void showButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showButtonActionPerformed

        //Τα TextField γινονται μη editable
        domainField.setEditable(false);
        usernameField.setEditable(false);
        passwordField.setEditable(false);
        commentField.setEditable(false);

        DefaultTableModel model = (DefaultTableModel) entriesTable.getModel();

        //Eντοπισμος της επιλεγμενης γραμμης στον πινακα
        int selected_row = entriesTable.getSelectedRow();

        if (selected_row > -1) { //Αν εχει επιλεχτει γραμμη

            //Ο επιλεγμενος domain που βρισκεται στη πρωτη στηλη της επιλεγμενης γραμμης
            String selected_domain = (String) model.getValueAt(selected_row, 0);

            //Κληση της getEntry με το επιλεγμενο domain της main για να παρουμε τα στοιχεια της εγγραφης
            HashMap<String, String> entry = main.getEntry(selected_domain);

            //Ενημερωση των TextField με τα στοιχεια της εγγραφης
            domainField.setText(entry.get("domain"));
            usernameField.setText(entry.get("username"));
            passwordField.setText(entry.get("password"));
            commentField.setText(entry.get("comment"));

            JOptionPane.showMessageDialog(this, EntryPanel, "Decrypted password entry", JOptionPane.PLAIN_MESSAGE);

        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry to show.", "No entry selected", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_showButtonActionPerformed

    //Action του κουμπιου 
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

        //Τα TextField γινονται editable
        domainField.setEditable(true);
        usernameField.setEditable(true);
        passwordField.setEditable(true);
        commentField.setEditable(true);
        //Αρχικοποηση των TextField
        domainField.setText("Domain");
        usernameField.setText("Username");
        passwordField.setText("Password");
        commentField.setText("Comment");

        int option = JOptionPane.showConfirmDialog(this, EntryPanel, "Add new password entry", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {

            //Αμα δεν υπαρχει ηδη εγγραφη για αυτο το domain
            if (!main.domainEntryExists(domainField.getText())) {

                //Κληση της createEntry της main με τα στοιχεια της εγγραφης για δημιουργια, κωδικοποιηση και αποθηκευση της εγγραφης
                main.createEntry(domainField.getText(), usernameField.getText(), passwordField.getText().toCharArray(), commentField.getText());

                //Προσθηκη της εγγραφης στον πινακα
                DefaultTableModel model = (DefaultTableModel) entriesTable.getModel();
                model.addRow(new Object[]{domainField.getText(), commentField.getText()});

            } else {
                JOptionPane.showMessageDialog(this, "There is already an entry for this domain.", "Entry exists", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_addButtonActionPerformed
    private final ImageIcon icon = new ImageIcon("Assets/key.png");
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EntryPanel;
    private javax.swing.JButton addButton;
    private javax.swing.JTextField commentField;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField domainField;
    private javax.swing.JButton editButton;
    private javax.swing.JTable entriesTable;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTextField passwordField;
    private javax.swing.JButton showButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
