package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import util.Validador;
import java.awt.SystemColor;
import javax.swing.JCheckBox;

public class Login extends JFrame {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblNewLabel_2;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/pc.png")));
		setTitle("Radical Pc's");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 368, 227);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnLogin = new JButton("");
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setIcon(new ImageIcon(Login.class.getResource("/img/login.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
				
			}
		});
		btnLogin.setBounds(138, 129, 48, 48);
		contentPane.add(btnLogin);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(294, 129, 48, 48);
		contentPane.add(lblStatus);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(59, 38, 31, 14);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(100, 35, 175, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(20));

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(42, 69, 48, 14);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(100, 66, 175, 20);
		contentPane.add(txtSenha);
		txtSenha.setDocument(new Validador(250));
		
		getRootPane().setDefaultButton(btnLogin);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Login.class.getResource("/img/45448_computer_hardware_pc_icon.png")));
		lblNewLabel_2.setBounds(128, 24, 251, 105);
		contentPane.add(lblNewLabel_2);
	}


	private void status() {
		
		try {
			
			con = dao.conectar();
			if (con == null) {
				
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dboff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/on.png")));
			}
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void logar() {
		
		String capturaSenha = new String(txtSenha.getPassword());
		
		
		String read = "select * from usuarios where login=? and senha=md5(?)";
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Login");
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Informe a Senha");
		} else {
		
			try {
				con = dao.conectar();
				
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				
			
				rs = pst.executeQuery();

				if (rs.next()) {
					
					String perfil = rs.getString(5);
				
					if (perfil.equals("admin")) {
					
						Principal principal = new Principal();
						
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						
					
						principal.panelRodape.setBackground(Color.RED);
						principal.setVisible(true);
					
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
					} else {
						
						Principal principal = new Principal();
						principal.setVisible(true);
						
						principal.lblUsuario.setText(rs.getString(2));
						this.dispose();
						
					}
					
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha Invalido(s)");
				}
				
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			

		}

	}
}
