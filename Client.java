/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.util.ArrayList;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.JFrame;


/**
 * @author erlichsefi
 */
public class Client extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3970976847258960979L;

	
	/**
	 * Creates new form Client
	 */
	public Client() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToggleButton_connect = new javax.swing.JToggleButton();
		my_name = new javax.swing.JTextField();
		jLabel_name = new javax.swing.JLabel();
		jLabel_address = new javax.swing.JLabel();
		ip_ad = new javax.swing.JTextField();
		jToggleButton_showOnline = new javax.swing.JToggleButton();
		jToggleButton_clear = new javax.swing.JToggleButton();
		jToggleButton_reset = new javax.swing.JToggleButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea_Main = new javax.swing.JTextArea("");
		jLabel_To = new javax.swing.JLabel();
		dst = new javax.swing.JTextField();
		message_field = new javax.swing.JTextField("");
		jButton_send = new javax.swing.JButton();
		jButton_disconnect = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToggleButton_connect.setText("Connect");
		jToggleButton_connect.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				Connect(evt);
			}
		});
		jToggleButton_connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton1ActionPerformed(evt);
			}
		});

		my_name.setText("name");
		my_name.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				my_nameActionPerformed(evt);
			}
		});

		jLabel_name.setText("name:");

		jLabel_address.setText("address:");

		ip_ad.setText("127.0.0.1");
		ip_ad.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ip_adActionPerformed(evt);
			}
		});

		jToggleButton_showOnline.setText("Show online users");
		jToggleButton_showOnline.setEnabled(false);
		jToggleButton_showOnline.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				showonline(evt);
			}
		});

		jToggleButton_clear.setText("Clear");
		jToggleButton_clear.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				Clear(evt);
			}
		});
		jToggleButton_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton3ActionPerformed(evt);
			}
		});

		jToggleButton_reset.setText("Reset");
		jToggleButton_reset.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				Reset(evt);
			}
		});
		jToggleButton_reset.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButtonResetActionPerformed(evt);
			}
		});

		jTextArea_Main.setColumns(20);
		jTextArea_Main.setRows(5);
		jScrollPane1.setViewportView(jTextArea_Main);

		jLabel_To.setText("TO:");

		dst.setText("NAME");
		dst.setEditable(false);
		dst.setToolTipText("");

		message_field.setText("");
		message_field.setEditable(false);
		message_field.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				message_fieldActionPerformed(evt);
			}
		});

		jButton_send.setText("Send");
		jButton_send.setEnabled(false);
		jButton_send.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				Send(evt);
			}
		});

		jButton_disconnect.setText("Disconnect");
		jButton_disconnect.setEnabled(false);
		jButton_disconnect.setToolTipText("");
		jButton_disconnect.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				Disconnect(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jLabel_To)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(dst, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(message_field, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jButton_send))
								.addGroup(layout.createSequentialGroup()
										.addGap(17)
										.addGroup(layout.createParallelGroup(Alignment.TRAILING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(jLabel_name)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(my_name, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(jLabel_address)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(ip_ad, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(jToggleButton_showOnline))
												.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)
												.addGroup(Alignment.LEADING, layout.createSequentialGroup()
														.addComponent(jToggleButton_connect, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(jButton_disconnect, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(jToggleButton_reset, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(jToggleButton_clear, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)))
										.addGap(97)))
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jToggleButton_connect)
								.addComponent(jButton_disconnect, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(jToggleButton_reset)
								.addComponent(jToggleButton_clear))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jToggleButton_showOnline)
								.addComponent(jLabel_name)
								.addComponent(my_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel_address)
								.addComponent(ip_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel_To)
								.addComponent(dst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(message_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton_send))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jToggleButton1ActionPerformed

	private void my_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_my_nameActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_my_nameActionPerformed

	private void ip_adActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ip_adActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_ip_adActionPerformed

	private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jToggleButton3ActionPerformed
	private void jToggleButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_jToggleButton3ActionPerformed

	private void Clear(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Clear

		jTextArea_Main.setText("");
	}//GEN-LAST:event_Clear


	private void Reset(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Clear

		dispose();
		Client game = new Client();
		game.setVisible(true);
	}//GEN-LAST:event_Reset


	private void showonline(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SHOWONLINE
		System.out.println("showonline botten clicked");

	}//GEN-LAST:event_SHOWONLINE
	

	private void Connect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Connect
		client=new ClientCode(this.ip_ad.getText(),this.my_name.getText(),dst,jTextArea_Main);
		connected.add(client);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		message_field.setEditable(true);
		jButton_send.setEnabled(true);
		jToggleButton_connect.setEnabled(false);
		dst.setEditable(true);
		client.startRunning();

	}//GEN-LAST:event_Connect

	private void Send(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Send
		System.out.println("send botten clicked");
		if(client.getConnection()!=null) {
			//client.send(dst.getText());          //first send the dst
			client.sendMessage(message_field.getText());//then the message
		}

	}//GEN-LAST:event_Send

	private void Disconnect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Disconnect
		System.out.println("Disconnect botten clicked");
		


	}//GEN-LAST:event_Disconnect

	private void message_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_message_fieldActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_message_fieldActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Client().setVisible(true);
			}
		});
		
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private static ClientCode client;
	private ArrayList<ClientCode>connected=new ArrayList<ClientCode>();
	private javax.swing.JTextField dst;
	private javax.swing.JTextField ip_ad;
	private javax.swing.JButton jButton_send;
	private javax.swing.JButton jButton_disconnect;
	private javax.swing.JLabel jLabel_name;
	private javax.swing.JLabel jLabel_address;
	private javax.swing.JLabel jLabel_To;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTextArea_Main;
	private javax.swing.JToggleButton jToggleButton_connect;
	private javax.swing.JToggleButton jToggleButton_showOnline;
	private javax.swing.JToggleButton jToggleButton_clear;
	private javax.swing.JToggleButton jToggleButton_reset;
	private javax.swing.JTextField message_field;
	private javax.swing.JTextField my_name;
	// End of variables declaration//GEN-END:variables
}
