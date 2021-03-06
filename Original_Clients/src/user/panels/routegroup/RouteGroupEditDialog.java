/*
 * Copyright (C) 2018 NATSRL @ UMD (University Minnesota Duluth)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package user.panels.routegroup;

import user.TeTRESConfig;
import user.types.RouteGroupInfo;
import common.util.FileHelper;
import javax.swing.JOptionPane;

/**
 *
 * @author "Chongmyung Park <chongmyung.park@gmail.com>"
 */
public class RouteGroupEditDialog extends javax.swing.JDialog {

    private RouteGroupInfo exRouteGroupInfo;

    /**
     * Creates new form RouteGroupEditDialog
     */
    public RouteGroupEditDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * Creates new form RouteGroupEditDialog
     */
    public RouteGroupEditDialog(RouteGroupInfo routeGroupInfo, java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.init(routeGroupInfo);
    }

    private void init(RouteGroupInfo routeGroupInfo) {
        if(routeGroupInfo == null) {
            return;
        }
        this.exRouteGroupInfo = routeGroupInfo;
        this.tbxRouteGroupName.setText(this.exRouteGroupInfo.name);
    }

    private void saveOrUpdate() {
        String name = this.tbxRouteGroupName.getText();
        if(name.isEmpty() || !FileHelper.isFilenameValid(name)) {
            JOptionPane.showMessageDialog(TeTRESConfig.mainFrame, "Invalid Name");
            return;
        }

        // new
        if(this.exRouteGroupInfo == null) {
            RouteGroupInfo routeGroupInfo = new RouteGroupInfo(name);
            RouteGroupInfoHelper.save(routeGroupInfo);
            RouteGroupInfoHelper.loadRouteGroups();
            this.dispose();

        } else { // update
            String oldName = this.exRouteGroupInfo.name;
            if(oldName.equals(name)) {
                this.dispose();
            }
            this.exRouteGroupInfo.name = name;
            boolean isUpdated = RouteGroupInfoHelper.update(this.exRouteGroupInfo, oldName);
            if(isUpdated) {
                RouteGroupInfoHelper.loadRouteGroups();
                this.dispose();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tbxRouteGroupName = new javax.swing.JTextField();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Route Group Edit Dialog");

        jLabel1.setText("Name");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnOK, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(tbxRouteGroupName)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tbxRouteGroupName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addGap(7, 7, 7))
        );

        pack();
    }private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {
        this.saveOrUpdate();
    }

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {
        this.dispose();
    }


    // Variables declaration - do not modify
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField tbxRouteGroupName;
    // End of variables declaration


}
