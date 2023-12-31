package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import util.Validador;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Clientes extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnLimpar;
	private JButton btnBuscar;
	private JButton btnDelete;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtBairro1;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JLabel lblNewLabel_10;
	private JLabel lblNewLabel_11;
	private JTextField txtNumero;
	private JTextField txtComplemento1;
	private JList listaClientes;
	private JScrollPane scrollPane;

	
	public static void main(String[] args) {
		try {
			Clientes dialog = new Clientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Clientes() {
		setModal(true);
		setTitle("Clientes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/pc.png")));
		setBounds(100, 100, 539, 457);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(66, 56, 292, 134);
		contentPanel.add(scrollPane);

		listaClientes = new JList();
		listaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		scrollPane.setViewportView(listaClientes);
		{
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(10, 11, 46, 14);
			contentPanel.add(lblNewLabel);
		}

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(10, 42, 36, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_4 = new JLabel("Telefone");
		lblNewLabel_4.setBounds(239, 101, 64, 14);
		contentPanel.add(lblNewLabel_4);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(66, 8, 86, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listaClintes();
			}
		});
		txtNome.setBounds(66, 39, 292, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(30));

		txtTelefone = new JTextField();
		txtTelefone.setBounds(295, 98, 153, 20);
		contentPanel.add(txtTelefone);
		txtTelefone.setColumns(10);
		txtTelefone.setDocument(new Validador(15));

		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarCliente();
			}
		});
		btnCreate.setBorder(null);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setContentAreaFilled(false);
		btnCreate.setIcon(new ImageIcon(Clientes.class.getResource("/img/add (2).png")));
		btnCreate.setBounds(10, 312, 64, 64);
		contentPanel.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarCliente();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorder(null);
		btnUpdate.setIcon(new ImageIcon(Clientes.class.getResource("/img/update.png")));
		btnUpdate.setBounds(84, 312, 64, 64);
		contentPanel.add(btnUpdate);

		btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBorder(null);
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(158, 312, 64, 64);
		contentPanel.add(btnLimpar);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setBorder(null);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(new ImageIcon(Clientes.class.getResource("/img/del.png")));
		btnDelete.setBounds(232, 312, 64, 64);
		contentPanel.add(btnDelete);

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCliente();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setIcon(new ImageIcon(Clientes.class.getResource("/img/lupa.png")));
		btnBuscar.setBounds(400, 24, 48, 48);
		contentPanel.add(btnBuscar);

		

	
				getRootPane().setDefaultButton(btnBuscar);

		JLabel lblNewLabel_5 = new JLabel("CEP");
		lblNewLabel_5.setBounds(10, 176, 46, 14);
		contentPanel.add(lblNewLabel_5);

		txtCep = new JTextField();
		txtCep.setBounds(66, 173, 130, 20);
		contentPanel.add(txtCep);
		txtCep.setColumns(10);
		txtCep.setDocument(new Validador(10));

		JLabel lblNewLabel_6 = new JLabel("Endereço");
		lblNewLabel_6.setBounds(10, 201, 64, 14);
		contentPanel.add(lblNewLabel_6);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(66, 198, 382, 20);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);
		txtEndereco.setDocument(new Validador(50));

		JLabel lblNewLabel_7 = new JLabel("bairro");
		lblNewLabel_7.setBounds(10, 254, 46, 14);
		contentPanel.add(lblNewLabel_7);

		txtBairro1 = new JTextField();
		txtBairro1.setBounds(66, 251, 230, 20);
		contentPanel.add(txtBairro1);
		txtBairro1.setColumns(10);
		txtBairro1.setDocument(new Validador(20));

		JLabel lblNewLabel_8 = new JLabel("Cidade");
		lblNewLabel_8.setBounds(10, 283, 46, 14);
		contentPanel.add(lblNewLabel_8);

		txtCidade = new JTextField();
		txtCidade.setBounds(66, 280, 130, 20);
		contentPanel.add(txtCidade);
		txtCidade.setColumns(10);
		txtCidade.setDocument(new Validador(30));

		cboUf = new JComboBox();
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(232, 279, 64, 22);
		contentPanel.add(cboUf);

		JButton btnBuscarCep = new JButton("");
		btnBuscarCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/on.png")));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		

		
				getRootPane().setDefaultButton(btnBuscar);
				
		btnBuscarCep.setBounds(207, 172, 89, 23);
		contentPanel.add(btnBuscarCep);

		JLabel lblNewLabel_9 = new JLabel("UF");
		lblNewLabel_9.setBounds(206, 286, 46, 14);
		contentPanel.add(lblNewLabel_9);

		lblNewLabel_10 = new JLabel("Numero");
		lblNewLabel_10.setBounds(10, 226, 58, 14);
		contentPanel.add(lblNewLabel_10);

		lblNewLabel_11 = new JLabel("Complemento");
		lblNewLabel_11.setBounds(157, 226, 97, 14);
		contentPanel.add(lblNewLabel_11);

		txtNumero = new JTextField();
		txtNumero.setBounds(66, 226, 86, 20);
		contentPanel.add(txtNumero);
		txtNumero.setColumns(10);
		txtNumero.setDocument(new Validador(5));

		txtComplemento1 = new JTextField();
		txtComplemento1.setBounds(242, 223, 206, 20);
		contentPanel.add(txtComplemento1);
		txtComplemento1.setColumns(10);
		txtComplemento1.setDocument(new Validador(30));
	}

	
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = txtCep.getText();
		String resultado = null;
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro1.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					
					if (resultado.equals("0")) {
						JOptionPane.showMessageDialog(null, "CEP inválido");
					}
				  
				 }
				 

			}

			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

	private void adicionarCliente() {
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();

		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente ");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereco do Cliente ");
			txtEndereco.requestFocus();
		} else if (txtBairro1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Cliente ");
			txtBairro1.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Cidade do Cliente ");
			txtCidade.requestFocus();

		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente ");
			txtTelefone.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o UF do Cliente ");
			cboUf.requestFocus();


		} else {
			
			String create = "insert into clientes (nome,telefone,numero,cidade,cep,bairro1,uf,complemento1,endereco) values (?,?,?,?,?,?,?,?,?)";
		
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(create);
			
		
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtTelefone.getText());
				pst.setString(3, txtNumero.getText());
				pst.setString(4, txtCidade.getText());
				pst.setString(5, txtCep.getText());
				pst.setString(6, txtBairro1.getText());
				pst.setString(7, (String) cboUf.getSelectedItem());
				pst.setString(8, txtComplemento1.getText());
				pst.setString(9, txtEndereco.getText());

				
				
			
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente  adicionado com sucesso");
				
				limparCampos();
				con.close();
	
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void buscarCliente() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Cliente");
			txtNome.requestFocus();
		} else {

			String read = "select * from clientes where nome = ?";
			try {
				con = dao.conectar();
			
				pst = con.prepareStatement(read);
				pst.setString(1, txtNome.getText());
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
				
			
							
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtTelefone.setText(rs.getString(3));
					txtNumero.setText(rs.getString(4));
					txtCidade.setText(rs.getString(6));
					txtCep.setText(rs.getString(5));
					txtBairro1.setText(rs.getString(7));
					cboUf.setSelectedItem(rs.getString(8));
					txtComplemento1.setText(rs.getString(9));
					txtEndereco.setText(rs.getString(10));
					
					
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(false);
					
				} else {
					JOptionPane.showMessageDialog(null, "Cliente inexistente");
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);

				}

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listaClintes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaClientes.setModel(modelo);
	
		String readLista = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome ";
		try {

			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			ResultSet rs = pst.executeQuery();
		
				while (rs.next()) {
					
					scrollPane.setVisible(true);
					modelo.addElement(rs.getString(2));
					
					if (txtNome.getText().isEmpty()) {
						scrollPane.setVisible(false);

					}
				}
			con.close();

		} catch (Exception e) {
		
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		
		int linha = listaClientes.getSelectedIndex();
		
		if (linha >= 0) {
		
			String read2 = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
				
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtTelefone.setText(rs.getString(3));
					txtNumero.setText(rs.getString(4));
					txtCidade.setText(rs.getString(6));
					txtCep.setText(rs.getString(5));
					txtBairro1.setText(rs.getString(7));
					cboUf.setSelectedItem(rs.getString(8));
					txtComplemento1.setText(rs.getString(9));
					txtEndereco.setText(rs.getString(10));
							
		
					btnCreate.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					btnBuscar.setEnabled(false);
					scrollPane.setVisible(false);
					
				} else {
					scrollPane.setVisible(false);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarCliente() {
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNome.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente ");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereco do Cliente ");
			txtEndereco.requestFocus();
		} else if (txtBairro1.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Cliente ");
			txtBairro1.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Cidade do Cliente ");
			txtCidade.requestFocus();

		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente ");
			txtTelefone.requestFocus();

		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o UF do Cliente ");
			cboUf.requestFocus();

		} else {

			String update = "update clientes set nome=?,cep=?,endereco=?,bairro1=?,cidade=?,uf=?,numero=?,Complemento1=?,telefone=? where idcli=?";
		
			try {
				
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(10, txtID.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCep.getText());
				pst.setString(3, txtEndereco.getText());
				pst.setString(4, txtBairro1.getText());
				pst.setString(5, txtCidade.getText());
				pst.setString(6, (String) cboUf.getSelectedItem());
				pst.setString(7, txtNumero.getText());
				pst.setString(8, txtComplemento1.getText());
				pst.setString(9, txtTelefone.getText());
				
							
				pst.executeUpdate();				
				JOptionPane.showMessageDialog(null, "Dados do Cliente alterados com sucesso");
				
				con.close();
				limparCampos();

			} catch (Exception e) {

				System.out.println(e);
			}
		}
	};

	private void excluirContato() {
	
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from clientes where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
			
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso");

				con.close();

			} 
			catch(java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Cliente Possui OS Aberta");
				
			}
			catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro1.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		txtNumero.setText(null);
		txtComplemento1.setText(null);
		scrollPane.setVisible(false);

		txtTelefone.setEnabled(true);
		btnBuscar.setEnabled(true);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);

	}
}
