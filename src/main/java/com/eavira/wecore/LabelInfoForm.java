package com.eavira.wecore;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

@SuppressWarnings("deprecation")
public class LabelInfoForm extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LogManager.getLogger();

	private SSHClient sshj;

	private JLabel cleanDbDropdownJLabel;
	private JComboBox<String> cleanDbDropdownBox;

	private JLabel restoreDbDropdownJLabel;
	private JComboBox<String> restoreDbDropdownBox;

	private JLabel changeBackEndBuildJLabel;
	private JComboBox<String> changeBackEndBuildBox;

	private JLabel changeBackEndBranchJLabel;
	private JComboBox<String> changeBackEndBranchBox;

	private JLabel dbFilePathJLabel;
	private JTextField dbFilePathTextFeild;

	private JLabel changeFrontEndBuildJLabel;
	private JComboBox<String> changeFrontEndBuildBox;

	private JLabel changeFrontEndBranchJLabel;
	private JComboBox<String> changeFrontEndBranchBox;

	private JLabel backEndProjectPathJLabel;
	private JTextField backEndProjectPathTextFeild;

	private JLabel frontEndProjectPathJLabel;
	private JTextField frontEndProjectPathTextFeild;

	private JLabel backEndBranchNameJLabel;
	private JTextField backEndBranchNameTextFeild;

	private JLabel frontEndBranchNameJLabel;
	private JTextField frontEndBranchNameTextFeild;

	private JLabel serverHostNameJLabel;
	private JTextField serverHostNameTextFeild;

	private JLabel serverUserNameJLabel;
	private JTextField serverUserNameTextFeild;

	private JLabel serverPasswordJLabel;
	private JTextField serverPasswordTextFeild;

	private Container c;
	private JLabel title;
	private JLabel messageJLabel;
	private JButton sub;
	private JButton reset;

	private String cleaneDbOptions[] = { "Yes", "No" };

	public LabelInfoForm() {

		setTitle("Label Info");
		setBounds(300, 90, 700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		c = getContentPane();
		c.setLayout(null);

		title = new JLabel("Build Info");
		title.setFont(new Font("Arial", Font.PLAIN, 30));
		title.setSize(300, 30);
		title.setLocation(150, 30);
		c.add(title);

		cleanDbDropdownJLabel = new JLabel("Clean DB");
		cleanDbDropdownJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		cleanDbDropdownJLabel.setSize(190, 20);
		cleanDbDropdownJLabel.setLocation(20, 100);
		c.add(cleanDbDropdownJLabel);

		cleanDbDropdownBox = new JComboBox<String>(cleaneDbOptions);
		cleanDbDropdownBox.setFont(new Font("Arial", Font.PLAIN, 15));
		cleanDbDropdownBox.setSize(190, 20);
		cleanDbDropdownBox.setLocation(300, 100);
		cleanDbDropdownBox.addActionListener(this);
		c.add(cleanDbDropdownBox);
		cleanDbDropdownBox.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {

				restoreDbDropdownJLabel.hide();
				restoreDbDropdownBox.hide();
				restoreDbDropdownBox.setSelectedIndex(0);
				dbFilePathJLabel.hide();
				dbFilePathTextFeild.hide();
				dbFilePathTextFeild.setText("");

			}
		});

		changeBackEndBuildJLabel = new JLabel("Change BackEnd Build");
		changeBackEndBuildJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		changeBackEndBuildJLabel.setSize(250, 20);
		changeBackEndBuildJLabel.setLocation(20, 130);
		c.add(changeBackEndBuildJLabel);

		changeBackEndBuildBox = new JComboBox<String>(cleaneDbOptions);
		changeBackEndBuildBox.setFont(new Font("Arial", Font.PLAIN, 15));
		changeBackEndBuildBox.setSize(190, 20);
		changeBackEndBuildBox.setLocation(300, 130);
		changeBackEndBuildBox.addActionListener(this);
		c.add(changeBackEndBuildBox);

		changeBackEndBranchJLabel = new JLabel("Change BackEnd Branch");
		changeBackEndBranchJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		changeBackEndBranchJLabel.setSize(280, 20);
		changeBackEndBranchJLabel.setLocation(20, 160);
		c.add(changeBackEndBranchJLabel);

		changeBackEndBranchBox = new JComboBox<String>(cleaneDbOptions);
		changeBackEndBranchBox.setFont(new Font("Arial", Font.PLAIN, 15));
		changeBackEndBranchBox.setSize(190, 20);
		changeBackEndBranchBox.setLocation(300, 160);
		changeBackEndBranchBox.addActionListener(this);
		c.add(changeBackEndBranchBox);

		changeFrontEndBuildJLabel = new JLabel("Change FrontEnd Build");
		changeFrontEndBuildJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		changeFrontEndBuildJLabel.setSize(250, 20);
		changeFrontEndBuildJLabel.setLocation(20, 190);
		c.add(changeFrontEndBuildJLabel);

		changeFrontEndBuildBox = new JComboBox<String>(cleaneDbOptions);
		changeFrontEndBuildBox.setFont(new Font("Arial", Font.PLAIN, 15));
		changeFrontEndBuildBox.setSize(190, 20);
		changeFrontEndBuildBox.setLocation(300, 190);
		changeFrontEndBuildBox.addActionListener(this);
		c.add(changeFrontEndBuildBox);

		changeFrontEndBranchJLabel = new JLabel("Change FrontEnd Branch");
		changeFrontEndBranchJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		changeFrontEndBranchJLabel.setSize(280, 20);
		changeFrontEndBranchJLabel.setLocation(20, 220);
		c.add(changeFrontEndBranchJLabel);

		changeFrontEndBranchBox = new JComboBox<String>(cleaneDbOptions);
		changeFrontEndBranchBox.setFont(new Font("Arial", Font.PLAIN, 15));
		changeFrontEndBranchBox.setSize(190, 20);
		changeFrontEndBranchBox.setLocation(300, 220);
		changeFrontEndBranchBox.addActionListener(this);
		c.add(changeFrontEndBranchBox);

		backEndProjectPathJLabel = new JLabel("BackEnd Project Path ");
		backEndProjectPathJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		backEndProjectPathJLabel.setSize(300, 20);
		backEndProjectPathJLabel.setLocation(20, 250);
		c.add(backEndProjectPathJLabel);

		backEndProjectPathTextFeild = new JTextField();
		backEndProjectPathTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		backEndProjectPathTextFeild.setSize(190, 20);
		backEndProjectPathTextFeild.setLocation(300, 250);
		c.add(backEndProjectPathTextFeild);

		frontEndProjectPathJLabel = new JLabel("FrontEnd Project Path ");
		frontEndProjectPathJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frontEndProjectPathJLabel.setSize(300, 20);
		frontEndProjectPathJLabel.setLocation(20, 280);
		c.add(frontEndProjectPathJLabel);

		frontEndProjectPathTextFeild = new JTextField();
		frontEndProjectPathTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		frontEndProjectPathTextFeild.setSize(190, 20);
		frontEndProjectPathTextFeild.setLocation(300, 280);
		c.add(frontEndProjectPathTextFeild);

		restoreDbDropdownJLabel = new JLabel("ReStore DB");
		restoreDbDropdownJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		restoreDbDropdownJLabel.setSize(190, 20);
		restoreDbDropdownJLabel.setLocation(20, 310);
		c.add(restoreDbDropdownJLabel);

		restoreDbDropdownBox = new JComboBox<String>(cleaneDbOptions);
		restoreDbDropdownBox.setFont(new Font("Arial", Font.PLAIN, 15));
		restoreDbDropdownBox.setSize(190, 20);
		restoreDbDropdownBox.setLocation(300, 310);
		restoreDbDropdownBox.addActionListener(this);
		c.add(restoreDbDropdownBox);

		dbFilePathJLabel = new JLabel("DB File Path");
		dbFilePathJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		dbFilePathJLabel.setSize(300, 20);
		dbFilePathJLabel.setLocation(20, 340);
		c.add(dbFilePathJLabel);

		dbFilePathTextFeild = new JTextField();
		dbFilePathTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		dbFilePathTextFeild.setSize(190, 20);
		dbFilePathTextFeild.setLocation(300, 340);
		c.add(dbFilePathTextFeild);

		backEndBranchNameJLabel = new JLabel("BackEnd Branch Name");
		backEndBranchNameJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		backEndBranchNameJLabel.setSize(300, 20);
		backEndBranchNameJLabel.setLocation(20, 370);
		c.add(backEndBranchNameJLabel);

		backEndBranchNameTextFeild = new JTextField();
		backEndBranchNameTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		backEndBranchNameTextFeild.setSize(190, 20);
		backEndBranchNameTextFeild.setLocation(300, 370);
		c.add(backEndBranchNameTextFeild);

		frontEndBranchNameJLabel = new JLabel("FrontEnd Branch Name");
		frontEndBranchNameJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		frontEndBranchNameJLabel.setSize(300, 20);
		frontEndBranchNameJLabel.setLocation(20, 400);
		c.add(frontEndBranchNameJLabel);

		frontEndBranchNameTextFeild = new JTextField();
		frontEndBranchNameTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		frontEndBranchNameTextFeild.setSize(190, 20);
		frontEndBranchNameTextFeild.setLocation(300, 400);
		c.add(frontEndBranchNameTextFeild);

		serverHostNameJLabel = new JLabel("Server Host");
		serverHostNameJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		serverHostNameJLabel.setSize(300, 20);
		serverHostNameJLabel.setLocation(20, 430);
		c.add(serverHostNameJLabel);

		serverHostNameTextFeild = new JTextField();
		serverHostNameTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		serverHostNameTextFeild.setSize(190, 20);
		serverHostNameTextFeild.setText("172.16.13.219");
		serverHostNameTextFeild.setLocation(300, 430);
		c.add(serverHostNameTextFeild);

		serverUserNameJLabel = new JLabel("Server User Name");
		serverUserNameJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		serverUserNameJLabel.setSize(300, 20);
		serverUserNameJLabel.setLocation(20, 460);
		c.add(serverUserNameJLabel);

		serverUserNameTextFeild = new JTextField();
		serverUserNameTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		serverUserNameTextFeild.setSize(190, 20);
		serverUserNameTextFeild.setText("flotomate");
		serverUserNameTextFeild.setLocation(300, 460);
		c.add(serverUserNameTextFeild);

		serverPasswordJLabel = new JLabel("Server Password");
		serverPasswordJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		serverPasswordJLabel.setSize(300, 20);
		serverPasswordJLabel.setLocation(20, 490);
		c.add(serverPasswordJLabel);

		serverPasswordTextFeild = new JTextField();
		serverPasswordTextFeild.setFont(new Font("Arial", Font.PLAIN, 15));
		serverPasswordTextFeild.setSize(190, 20);
		serverPasswordTextFeild.setText("admin");
		serverPasswordTextFeild.setLocation(300, 490);
		c.add(serverPasswordTextFeild);

		sub = new JButton("Submit");
		sub.setFont(new Font("Arial", Font.PLAIN, 15));
		sub.setSize(100, 20);
		sub.setLocation(150, 600);
		sub.addActionListener(this);
		c.add(sub);

		reset = new JButton("Reset");
		reset.setFont(new Font("Arial", Font.PLAIN, 15));
		reset.setSize(100, 20);
		reset.setLocation(270, 600);
		reset.addActionListener(this);
		c.add(reset);

		messageJLabel = new JLabel("");
		messageJLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		messageJLabel.setSize(300, 20);
		messageJLabel.setLocation(100, 570);
		c.add(messageJLabel);

		setVisible(true);
	}

	public boolean onlyDigits(String str) {
		String regex = "[0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sub) {

			String serverHost = serverHostNameTextFeild.getText();
			if (StringUtils.isEmpty(serverHost)) {
				messageJLabel.setText("Server Host is Required");
				return;
			}
			String serverUserName = serverUserNameTextFeild.getText();
			if (StringUtils.isEmpty(serverUserName)) {
				messageJLabel.setText("Server UserName is Required");
				return;
			}
			String serverPassword = serverPasswordTextFeild.getText();
			if (StringUtils.isEmpty(serverPassword)) {
				messageJLabel.setText("Server Password is Required");
				return;
			}
			List<String> commands = new ArrayList<>();

			String cleanDb = (String) cleanDbDropdownBox.getSelectedItem();

			String changeBackEndBranch = (String) changeBackEndBranchBox.getSelectedItem();

			String changeBackEndBuild = (String) changeBackEndBuildBox.getSelectedItem();
			String changeFrontEndEndBuild = (String) changeFrontEndBuildBox.getSelectedItem();

			String changeFrontEndEndBranch = (String) changeFrontEndBranchBox.getSelectedItem();

			String backEndProjectPath = (String) backEndProjectPathTextFeild.getText();
			String frontEndProjectPath = (String) frontEndProjectPathTextFeild.getText();

			String restoreDb = (String) restoreDbDropdownBox.getSelectedItem();
			String dbFilePath = (String) dbFilePathTextFeild.getText();

			String backEndProjectBranchName = (String) backEndBranchNameTextFeild.getText();
			String frontEndProjectBranchName = (String) frontEndBranchNameTextFeild.getText();

			if (changeBackEndBuild.equalsIgnoreCase("yes") && changeBackEndBranch.equalsIgnoreCase("yes")) {
				if (StringUtils.isEmpty(backEndProjectBranchName)) {
					messageJLabel.setText("BackEnd Branch is Required");
					return;
				}
			}

			if (changeBackEndBuild.equalsIgnoreCase("yes")) {
				if (StringUtils.isEmpty(backEndProjectPath)) {
					messageJLabel.setText("BackEnd Path is Required");
					return;
				}
			}

			if (changeFrontEndEndBranch.equalsIgnoreCase("yes") && changeFrontEndEndBuild.equalsIgnoreCase("yes")) {
				if (StringUtils.isEmpty(frontEndProjectBranchName)) {
					messageJLabel.setText("FrontEnd Branch is Required");
					return;
				}
			}

			if (changeFrontEndEndBuild.equalsIgnoreCase("yes")) {
				if (StringUtils.isEmpty(frontEndProjectPath)) {
					messageJLabel.setText("FrontEnd Path is Required");
					return;
				}
			}

			if (restoreDb.equalsIgnoreCase("yes") && cleanDb.equalsIgnoreCase("no")) {
				if (StringUtils.isEmpty(dbFilePath)) {
					messageJLabel.setText("DB File Path is Required");
					return;
				}
			}

			if (cleanDb.equalsIgnoreCase("yes")) {
				try {
					runCommand(serverHost,serverUserName,serverPassword,
							"sudo -u postgres psql postgres -c \"DROP SCHEMA IF EXISTS apolo CASCADE;\" --dbname=flotoitsmdb");
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				commands.add(
						"sudo -u postgres psql postgres -c \"DROP SCHEMA IF EXISTS apolo CASCADE;\" --dbname=flotoitsmdb");
				commands.add(
						"sudo -u postgres psql postgres -c \"DROP SCHEMA IF EXISTS master CASCADE;\" --dbname=flotoitsmdb");
			}
			logger.info("Process Completed");
		} else if (e.getSource() == reset) {
			String def = "";
			cleanDbDropdownBox.setSelectedIndex(0);
			changeBackEndBuildBox.setSelectedIndex(0);
			changeFrontEndBuildBox.setSelectedIndex(0);
			changeBackEndBranchBox.setSelectedIndex(0);
			changeFrontEndBranchBox.setSelectedIndex(0);
			backEndProjectPathTextFeild.setText(def);
			frontEndProjectPathTextFeild.setText(def);
			restoreDbDropdownBox.setSelectedIndex(0);
			backEndBranchNameTextFeild.setText(def);
			frontEndBranchNameTextFeild.setText(def);
			dbFilePathTextFeild.setText(def);
		} else if (e.getSource() == cleanDbDropdownBox) {
			String cleanDb = (String) cleanDbDropdownBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				restoreDbDropdownJLabel.hide();
				restoreDbDropdownBox.hide();
				restoreDbDropdownBox.setSelectedIndex(0);
				dbFilePathJLabel.hide();
				dbFilePathTextFeild.hide();
				dbFilePathTextFeild.setText("");
			} else {
				restoreDbDropdownJLabel.show();
				restoreDbDropdownBox.show();
				dbFilePathJLabel.show();
				dbFilePathTextFeild.show();
			}
		} else if (e.getSource() == changeBackEndBuildBox) {
			String cleanDb = (String) changeBackEndBuildBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				changeBackEndBranchBox.show();
				changeBackEndBranchJLabel.show();
				backEndProjectPathJLabel.show();
				backEndProjectPathTextFeild.show();
				backEndBranchNameJLabel.show();
				backEndBranchNameTextFeild.show();
			} else {
				changeBackEndBranchBox.hide();
				changeBackEndBranchBox.setSelectedIndex(0);
				changeBackEndBranchJLabel.hide();
				backEndProjectPathJLabel.hide();
				backEndProjectPathTextFeild.hide();
				backEndProjectPathTextFeild.setText("");
				backEndBranchNameJLabel.hide();
				backEndBranchNameTextFeild.hide();
				backEndBranchNameTextFeild.setText("");
			}
		} else if (e.getSource() == changeFrontEndBuildBox) {
			String cleanDb = (String) changeFrontEndBuildBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				changeFrontEndBranchBox.show();
				changeFrontEndBranchBox.setSelectedIndex(0);
				changeFrontEndBranchJLabel.show();
				frontEndProjectPathJLabel.show();
				frontEndProjectPathTextFeild.show();
				frontEndProjectPathTextFeild.setText("");
				frontEndBranchNameJLabel.show();
				frontEndBranchNameTextFeild.show();
				frontEndBranchNameTextFeild.setText("");
			} else {
				changeFrontEndBranchBox.hide();
				changeFrontEndBranchJLabel.hide();
				frontEndProjectPathJLabel.hide();
				frontEndProjectPathTextFeild.hide();
				frontEndBranchNameJLabel.hide();
				frontEndBranchNameTextFeild.hide();

			}
		} else if (e.getSource() == changeBackEndBranchBox) {
			String cleanDb = (String) changeBackEndBranchBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				backEndBranchNameJLabel.show();
				backEndBranchNameTextFeild.show();
			} else {
				backEndBranchNameJLabel.hide();
				backEndBranchNameTextFeild.hide();
			}
		} else if (e.getSource() == changeFrontEndBranchBox) {
			String cleanDb = (String) changeFrontEndBranchBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				frontEndBranchNameJLabel.show();
				frontEndBranchNameTextFeild.show();
			} else {
				frontEndBranchNameJLabel.hide();
				frontEndBranchNameTextFeild.hide();
			}
		} else if (e.getSource() == restoreDbDropdownBox) {
			String cleanDb = (String) restoreDbDropdownBox.getSelectedItem();
			if (cleanDb.equals("Yes")) {
				dbFilePathTextFeild.show();
				dbFilePathTextFeild.setText("");
				dbFilePathJLabel.show();
			} else {
				dbFilePathTextFeild.hide();
				dbFilePathJLabel.hide();
			}
		}
	}

	private void runCommand(String host, String userName, String password, String commandToExecute) throws Exception {
		String resString = "";

		try {
			sshj = new SSHClient();
			sshj.addHostKeyVerifier(new PromiscuousVerifier());
			sshj.setConnectTimeout(30000);
			sshj.connect(host, 22);
			sshj.authPassword(userName, password);
			Session session = sshj.startSession();
			final Command cmd = session.exec("sudo -S -p '' " + commandToExecute);
			
			cmd.join(3, TimeUnit.MINUTES);
			session.close();

			resString = getDataFromStream(cmd, commandToExecute);
			System.out.println(resString);
		} catch (Exception e) {
			logger.error("Error while creating zip file : {}", e);
			throw e;
		}
	}

	private String getDataFromStream(Command cmd, String command) throws IOException {
		String resString = "";

		if (cmd.getExitStatus() == 1) {
			InputStream errorStream = cmd.getErrorStream();
			logger.debug("error in executing command : '{}'. exit status : {}", command, cmd.getExitStatus());
			logger.debug("error message :  {}", IOUtils.readFully(errorStream));
			System.out.println(IOUtils.readFully(errorStream));
		} else {
			InputStream inputStream = cmd.getInputStream();
			resString = IOUtils.readFully(inputStream).toString();
		}

		return resString;
	}

}
