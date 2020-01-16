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
package common.pyticas;

import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.config.Config;
import common.log.TICASLogger;

/**
 *
 * @author "Chongmyung Park <chongmyung.park@gmail.com>"
 */
public final class RunningDialog extends javax.swing.JDialog {

    private Random rand = new Random();
    private ProgressThread runnable;

    public class ProgressThread implements Runnable {

        private volatile boolean tobeStoped = false;

        public void terminate() {
            tobeStoped = false;
        }

        @Override
        public void run() {
            int v = 0;
            int step = 1;
            while (!tobeStoped) {
                pbProgressBar.setValue(v);
                v += step;
                if ((step > 0 && v >= 100) || (step < 0 && v <= 0)) {
                    step *= -1;
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ex) {
                    TICASLogger.getLogger(RunningDialog.class.getName()).warn("error", ex);
                }
            }

            for (; v < 100; v++) {
                pbProgressBar.setValue(v);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    TICASLogger.getLogger(RunningDialog.class.getName()).warn("error", ex);
                }
            }
        }
    }

    private Thread progressThread;
    
    public static void run(IRequest requestObject) {
        run(requestObject, null);
    }
    
    public static void run(IRequest requestObject, String label) {
        final RunningDialog pDialog = new RunningDialog(Config.mainFrame, true);
        pDialog.setLocationRelativeTo(Config.mainFrame);
        if(label != null) {
            pDialog.setLabel(label);
        }
        Timer t = new Timer();
        t.schedule(new RequestTask(pDialog, requestObject), 20);        
        pDialog.start();    
    }

    /**
     * Creates new form RunningDialog
     */
    public RunningDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setLabel(String label) {
        this.lbMessage.setText(label);
    }

    public void start() {
        this.runnable = new ProgressThread();
        this.progressThread = new Thread(this.runnable);
        this.progressThread.start();
        this.setVisible(true);
    }

    public void stop() {
        this.runnable.tobeStoped = true;
        try {
            this.progressThread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(RunningDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        lbMessage = new javax.swing.JLabel();
        pbProgressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Running..");
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setResizable(false);

        lbMessage.setText("Please wait...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbMessage)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pbProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pbProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }/**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RunningDialog dialog = new RunningDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.start();
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel lbMessage;
    private javax.swing.JProgressBar pbProgressBar;
    // End of variables declaration

}