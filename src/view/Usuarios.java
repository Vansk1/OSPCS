package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import util.Validador;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

public class Usuarios extends JDialog {
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JTextField txtID;
	private ResultSet rs; 
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JLabel lblNewLabel_2;
	private JComboBox cboPerfil2;
	private JCheckBox chkSenha;


	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Usuarios() {
		setModal(true);
		setTitle("Radical Pc's");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/pc.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOME");
		lblNewLabel.setBounds(10, 61, 48, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(10, 97, 48, 14);
		contentPanel.add(lblLogin);
		
		txtNome = new JTextField();
		txtNome.setBounds(81, 59, 292, 17);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));
		
		JLabel lblSenha = new JLabel("SENHA");
		lblSenha.setBounds(10, 134, 48, 14);
		contentPanel.add(lblSenha);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(81, 95, 127, 17);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(20));
		
		btnCreate = new JButton("");
		btnCreate.setToolTipText("Adicionar");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/add (2).png")));
		btnCreate.setBounds(88, 202, 48, 48);
		contentPanel.add(btnCreate);
		
		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorderPainted(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(264, 202, 48, 48);
		contentPanel.add(btnLimpar);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(81, 132, 190, 17);
		contentPanel.add(txtSenha);
		txtSenha.setDocument(new Validador(250));
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(12, 27, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(81, 24, 59, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/lupa.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarContato();
			}
		});
		btnBuscar.setToolTipText("Pesquisar Contato");
		btnBuscar.setBounds(218, 83, 48, 48);
		contentPanel.add(btnBuscar);
		
		getRootPane().setDefaultButton(btnBuscar);
		
		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setToolTipText("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			editarUsuario();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/update.png")));
		btnUpdate.setBounds(146, 202, 48, 48);
		contentPanel.add(btnUpdate);
		
		btnDelete = new JButton("");
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setToolTipText("excluir usuario");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			excluirUsuario();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(new ImageIcon(Usuarios.class.getResource("/img/del.png")));
		btnDelete.setBounds(204, 202, 48, 48);
		contentPanel.add(btnDelete);
		
		lblNewLabel_2 = new JLabel("PERFIL");
		lblNewLabel_2.setBounds(12, 163, 46, 14);
		contentPanel.add(lblNewLabel_2);
		
		cboPerfil2 = new JComboBox();
		cboPerfil2.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil2.setBounds(81, 160, 231, 17);
		contentPanel.add(cboPerfil2);
		
		chkSenha = new JCheckBox("Alterar a senha");
		chkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSenha.setEditable(true);
				txtSenha.setText(null);
				txtSenha.requestFocus();
				txtSenha.setBackground(Color.red);	
			}
		}
		);
		chkSenha.setVisible(false);
		chkSenha.setBounds(278, 130, 118, 23);
		contentPanel.add(chkSenha);

		
	}
	

	private void adicionarUsuario() {

		String capturaSenha = new String(txtSenha.getPassword());

		JComboBox cboPerfil;

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.isEmpty()) {
		JOptionPane.showMessageDialog(null, "Preencha a senha do Usuário");
		txtLogin.requestFocus(); 
		} else if (cboPerfil2.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil do Usuário");
			cboPerfil2.requestFocus(); 	
	} else {

			 String create = "insert into usuarios(nome,login,senha,perfil) values (?,?, md5(?),?)";	
			try {

				con = dao.conectar();
			 	pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil2.getSelectedItem().toString());
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Usuário Adicionado com Sucesso");

				limparCampos();

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Usuario não adicionado. \nEste login ja esta sendo utilizado");
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);

			}
		}

	}
	

	private void buscarContato() {
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Login do Usuario");
			txtLogin.requestFocus(); 
		} else {

			String read = "select * from usuarios where login = ? ";
			try {

				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				rs = pst.executeQuery();
				if (rs.next()) {

					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtSenha.setText(rs.getString(4));
					cboPerfil2.setSelectedItem(rs.getString(5));
					btnCreate.setEnabled(false);
					btnDelete.setEnabled(true);
					btnUpdate.setEnabled(true);
					btnBuscar.setEnabled(false);
					chkSenha.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário Inexistente");

					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}

				con.close();
			} catch (Exception e) {

				System.out.println(e);
			}
		}
	}
	
	

	private void editarUsuarioSenha(){

		String capturaSenha = new String(txtSenha.getPassword());

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");	
		}else if (capturaSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha");	
		} else if (cboPerfil2.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil");	
		} else {

			String update = "update usuarios set nome=?, login=?, Senha=md5(?), perfil=? where iduser = ?";

			try {

				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil2.getSelectedItem().toString());
				pst.setString(5, txtID.getText());			
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Usuário alterado com Sucesso");

				con.close();

				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Usuario não atualizado. \nEste login ja esta sendo utilizado");
			} catch (Exception e2) {
				txtLogin.setText(null);
				txtLogin.requestFocus();
				System.out.println(e2);
			}
		}
	}
	
	
	private void editarUsuario(){

		String capturaSenha = new String(txtSenha.getPassword());

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login");	
		} else if (cboPerfil2.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil");	
		} else {

			String update = "update usuarios set nome=?, login=?, perfil=? where iduser = ?";

			try {

				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil2.getSelectedItem().toString());
				pst.setString(4, txtID.getText());			
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Usuário alterado com Sucesso");

				con.close();

				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Usuario não atualizado. \nEste login ja esta sendo utilizado");
			} catch (Exception e2) {
				txtLogin.setText(null);
				txtLogin.requestFocus();
				System.out.println(e2);
			}
		}
	}

	private void excluirUsuario() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desse usuário?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String delete = "delete from usuarios where iduser=?" ;
					try {
						
						con = dao.conectar();
						pst = con.prepareStatement(delete);
						pst.setString(1, txtID.getText());
						pst.executeUpdate();
						
						limparCampos();
						JOptionPane.showMessageDialog(null, "Usuário excluído");
					} catch (Exception e) {
						System.out.println(e);
					}
		}
	}
	
	
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		cboPerfil2.setSelectedItem("");

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
		chkSenha.setVisible(false);
		txtSenha.setBackground(Color.white);
	} 
}


