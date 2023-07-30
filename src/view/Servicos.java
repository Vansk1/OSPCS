package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;

public class Servicos extends JDialog {

	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtModelo;
	private JTextField txtDefeitoInformado;
	private JTextField txtDefeitoDiagnosticado;
	private JTextField txtValor;
	private JTextField txtMarca;
	private JTextField txtSerie;
	private JTextField txtAcessorios;
	private JComboBox cboStatusOS;
	private JButton btnCreate;
	private JButton btnLimpar;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnBuscar;
	private JTextField txtOS;
	private JLabel lblDataos;
	private JTextField txtID;
	private JTextField txtCliente;
	private JTextField txtObservacao;
	private JLabel lblNewLabel_10;
	private JButton btnBuscarCliente;
	private JComboBox cboPagamento;
	private JLabel lblNewLabel_14;
	private JTextField txtUser;

	
	public String usuario;
	private JList listaServicos;
	private JScrollPane scrollPane;
	private JTextField txtIDtec;
	private JTextField txtTecnicos;
	private JList listaServicosTec;
	private JScrollPane scrollPane_2;
	private JDateChooser txtdateSaida;
	private JDateChooser txtdateEntrada;
	private JButton btnNewButton;

	
	public static void main(String[] args) {
		try {
			Servicos dialog = new Servicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Servicos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtUser.setText(usuario);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/pc.png")));
		setBounds(100, 100, 829, 464);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Modelo");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel.setBounds(26, 80, 74, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			txtModelo = new JTextField();
			txtModelo.setColumns(30);
			txtModelo.setBounds(78, 78, 95, 20);
			contentPanel.add(txtModelo);
			txtModelo.setDocument(new Validador(30));
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Defeito Informado");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_1.setBounds(26, 153, 127, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			txtDefeitoInformado = new JTextField();
			txtDefeitoInformado.setColumns(200);
			txtDefeitoInformado.setBounds(170, 153, 347, 37);
			contentPanel.add(txtDefeitoInformado);
			txtDefeitoInformado.setDocument(new Validador(50));
		}
		{
			JLabel lblNewLabel_2 = new JLabel("DefeitoDiagnosticado");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_2.setBounds(26, 201, 147, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			txtDefeitoDiagnosticado = new JTextField();
			txtDefeitoDiagnosticado.setColumns(200);
			txtDefeitoDiagnosticado.setBounds(170, 201, 347, 37);
			contentPanel.add(txtDefeitoDiagnosticado);
			txtDefeitoDiagnosticado.setDocument(new Validador(50));
		}
		{
			JLabel lblNewLabel_3 = new JLabel("statusOS");
			lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_3.setBounds(26, 311, 74, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			cboStatusOS = new JComboBox();
			cboStatusOS.setEditable(true);
			cboStatusOS.setModel(new DefaultComboBoxModel(new String[] {"", "Aguardando Técnico", "Em andamento", "Finalizado"}));
			cboStatusOS.setBounds(110, 308, 178, 22);
			contentPanel.add(cboStatusOS);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("Valor");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_4.setBounds(271, 400, 33, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			txtValor = new JTextField();
			txtValor.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					
					String caracteres = "0987654321.";
					if (!caracteres.contains(e.getKeyChar() + "")) {
						e.consume();
					}
				}
			});
			txtValor.setText("0");
			txtValor.setColumns(10);
			txtValor.setBounds(314, 398, 108, 20);
			contentPanel.add(txtValor);
		}
		{
			JLabel lblNewLabel_7 = new JLabel("Marca");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_7.setBounds(183, 80, 46, 14);
			contentPanel.add(lblNewLabel_7);
		}
		{
			txtMarca = new JTextField();
			txtMarca.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					
				 {
				
					}
				}
			});
			txtMarca.setColumns(200);
			txtMarca.setBounds(229, 78, 86, 20);
			contentPanel.add(txtMarca);
			txtMarca.setDocument(new Validador(10));
		}
		{
			JLabel lblNewLabel_11 = new JLabel("Nº de Série");
			lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_11.setBounds(597, 80, 117, 14);
			contentPanel.add(lblNewLabel_11);
		}
		{
			txtSerie = new JTextField();
			txtSerie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0987654321.";
					if (!caracteres.contains(e.getKeyChar() + "")) {
						e.consume();
					}
				}
			});
			txtSerie.setColumns(30);
			txtSerie.setBounds(667, 78, 136, 20);
			contentPanel.add(txtSerie);
			txtSerie.setDocument(new Validador(10));
		}
		{
			JLabel lblNewLabel_12 = new JLabel("Acessorios");
			lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_12.setBounds(345, 80, 108, 14);
			contentPanel.add(lblNewLabel_12);
		}
		{
			txtAcessorios = new JTextField();
			txtAcessorios.setColumns(30);
			txtAcessorios.setBounds(426, 78, 161, 20);
			contentPanel.add(txtAcessorios);
			txtAcessorios.setDocument(new Validador(30));
		}
		{
			btnDelete = new JButton("");
			btnDelete.setEnabled(false);
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirOS();
				}
			});
			btnDelete.setIcon(new ImageIcon(Servicos.class.getResource("/img/del.png")));
			btnDelete.setToolTipText("Excluir contato");
			btnDelete.setContentAreaFilled(false);
			btnDelete.setBorder(null);
			btnDelete.setBounds(197, 357, 64, 64);
			contentPanel.add(btnDelete);
		}
		{
			btnCreate = new JButton("");
			btnCreate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionarServico();
				}
			});
			btnCreate.setIcon(new ImageIcon(Servicos.class.getResource("/img/add (2).png")));
			btnCreate.setToolTipText("Adicionar contato");
			btnCreate.setContentAreaFilled(false);
			btnCreate.setBorder(null);
			btnCreate.setBounds(10, 357, 64, 64);
			contentPanel.add(btnCreate);
		}
		{
			btnUpdate = new JButton("");
			btnUpdate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editarOS();
				}
			});
			btnUpdate.setIcon(new ImageIcon(Servicos.class.getResource("/img/update.png")));
			btnUpdate.setToolTipText("Editar contato");
			btnUpdate.setEnabled(false);
			btnUpdate.setContentAreaFilled(false);
			btnUpdate.setBorder(null);
			btnUpdate.setBounds(63, 357, 64, 64);
			contentPanel.add(btnUpdate);
		}
		{
			btnLimpar = new JButton("");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
			btnLimpar.setToolTipText("Limpar Campos");
			btnLimpar.setContentAreaFilled(false);
			btnLimpar.setBorderPainted(false);
			btnLimpar.setBounds(123, 357, 64, 64);
			contentPanel.add(btnLimpar);
		}

		btnBuscar = new JButton("");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCliente();
			}
		});
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/lupa.png")));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorder(null);
		btnBuscar.setBounds(358, 12, 58, 49);
		contentPanel.add(btnBuscar);

		txtOS = new JTextField();
		txtOS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		txtOS.setEditable(false);
		txtOS.setColumns(10);
		txtOS.setBounds(527, 37, 69, 20);
		contentPanel.add(txtOS);

		JLabel lblOs = new JLabel("OS");
		lblOs.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblOs.setBounds(471, 40, 46, 14);
		contentPanel.add(lblOs);
		{
			lblDataos = new JLabel("DataOS");
			lblDataos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblDataos.setBounds(459, 14, 58, 14);
			contentPanel.add(lblDataos);
		}

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(249, 12, 86, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("ID");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_6.setBounds(215, 12, 46, 14);
		contentPanel.add(lblNewLabel_6);

		JLabel lblNewLabel_9 = new JLabel("Cliente");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_9.setBounds(200, 40, 58, 14);
		contentPanel.add(lblNewLabel_9);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarCliente();
			}
		});
		txtCliente.setColumns(10);
		txtCliente.setBounds(249, 34, 86, 20);
		contentPanel.add(txtCliente);

		txtObservacao = new JTextField();
		txtObservacao.setColumns(200);
		txtObservacao.setBounds(170, 254, 347, 37);
		contentPanel.add(txtObservacao);
		txtObservacao.setDocument(new Validador(50));
		{
			lblNewLabel_10 = new JLabel("Observações");
			lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_10.setBounds(26, 251, 147, 14);
			contentPanel.add(lblNewLabel_10);
		}
		{
			btnBuscarCliente = new JButton("Buscar OS");
			btnBuscarCliente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					buscarOS();
				}
			});
			btnBuscarCliente.setBounds(606, 36, 108, 23);
			contentPanel.add(btnBuscarCliente);
		}

		cboPagamento = new JComboBox();
		cboPagamento.setEditable(true);
		cboPagamento.setModel(new DefaultComboBoxModel(new String[] { "", "Debito", "Credito", "Pix" }));
		cboPagamento.setBounds(576, 399, 108, 22);
		contentPanel.add(cboPagamento);

		JLabel lblNewLabel_4 = new JLabel("Forma de pagamento");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_4.setBounds(426, 402, 138, 14);
		contentPanel.add(lblNewLabel_4);

		JLabel lblHoraDeSaida = new JLabel("Data de saida");
		lblHoraDeSaida.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblHoraDeSaida.setBounds(306, 311, 88, 14);
		contentPanel.add(lblHoraDeSaida);
		{
			lblNewLabel_14 = new JLabel("Usuario");
			lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
			lblNewLabel_14.setBounds(594, 311, 58, 14);
			contentPanel.add(lblNewLabel_14);
		}
		{
			txtUser = new JTextField();
			txtUser.setEditable(false);
			txtUser.setColumns(30);
			txtUser.setBounds(647, 309, 142, 20);
			contentPanel.add(txtUser);
		}

		txtdateSaida = new JDateChooser();
		txtdateSaida.setBounds(404, 310, 178, 20);
		contentPanel.add(txtdateSaida);

		scrollPane = new JScrollPane();
		scrollPane.setWheelScrollingEnabled(false);
		scrollPane.setVisible(false);
		scrollPane.setBounds(249, 53, 86, 49);
		contentPanel.add(scrollPane);
		
				listaServicos = new JList();
				scrollPane.setViewportView(listaServicos);
				listaServicos.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarLista();
					}
				});
				listaServicos.setBorder(null);

		txtIDtec = new JTextField();
		txtIDtec.setEditable(false);
		txtIDtec.setColumns(10);
		txtIDtec.setBounds(87, 10, 86, 20);
		contentPanel.add(txtIDtec);

		JLabel lblNewLabel_6_1 = new JLabel("IDtec");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_6_1.setBounds(31, 12, 46, 14);
		contentPanel.add(lblNewLabel_6_1);

		txtTecnicos = new JTextField();
		txtTecnicos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnico();
			}
		});
		txtTecnicos.setColumns(30);
		txtTecnicos.setBounds(87, 34, 86, 20);
		contentPanel.add(txtTecnicos);

		JLabel lblNewLabel_9_1 = new JLabel("Técnico");
		lblNewLabel_9_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_9_1.setBounds(26, 36, 58, 14);
		contentPanel.add(lblNewLabel_9_1);
		{
			scrollPane_2 = new JScrollPane();
			scrollPane_2.setWheelScrollingEnabled(false);
			scrollPane_2.setVisible(false);
			scrollPane_2.setBounds(87, 53, 86, 49);
			contentPanel.add(scrollPane_2);
			{
				listaServicosTec = new JList();
				scrollPane_2.setViewportView(listaServicosTec);
				listaServicosTec.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarListaTec();
					}
				});
				listaServicosTec.setBorder(null);
			}
		}

		txtdateEntrada = new JDateChooser();
		txtdateEntrada.getCalendarButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtdateEntrada.setEnabled(false);
		txtdateEntrada.setBounds(527, 12, 187, 20);
		contentPanel.add(txtdateEntrada);
		
		btnNewButton = new JButton("Imprimir OS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnNewButton.setBounds(700, 397, 89, 23);
		contentPanel.add(btnNewButton);
	}

	private void adicionarServico() {
		

		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o modelo dá máquina");
			txtModelo.requestFocus();
		} else if (txtMarca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a marca");
			txtMarca.requestFocus();
		} else if (txtSerie.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o número de Série da Máquina");
			txtSerie.requestFocus();
	
		} else if (txtAcessorios.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o acessório");
			txtAcessorios.requestFocus();
		} else if (txtDefeitoInformado.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito Informado pelo cliente");
			txtDefeitoInformado.requestFocus();
	
		} else if (cboStatusOS.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha O Status");
			cboStatusOS.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o valor do serviço");
			txtValor.requestFocus();
			

		} else {
			
			String create = "insert into servicos(marca,modelo,serie,acessorios,observacao,defeitoInformado,defeitoDiagnosticado,statusOS,valor,formaPagamento,idcli,usuario) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtMarca.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtSerie.getText());
				pst.setString(4, txtAcessorios.getText());
				pst.setString(5, txtObservacao.getText());
				pst.setString(6, txtDefeitoInformado.getText());
				pst.setString(7, txtDefeitoDiagnosticado.getText());
				pst.setString(8, cboStatusOS.getSelectedItem().toString());
				pst.setString(9, txtValor.getText());
				pst.setString(10, cboPagamento.getSelectedItem().toString());
				
				pst.setString(11, txtID.getText());
				pst.setString(12, txtUser.getText());
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Máquina cadastrada com sucesso");
				
				limparCampos();
			
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Pc ou Notebook não atualizado. \nEsta Numero de Série já está sendo utilizada");
			} catch (Exception e1) {
				txtSerie.setText(null);
				txtSerie.requestFocus();
				System.out.println(e1);
			}
		}

	}

	private void listarCliente() {

		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaServicos.setModel(modelo);

		String readlista = "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				
				scrollPane.setVisible(true);
				
				modelo.addElement(rs.getNString(2));
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarLista() {

		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			txtCliente.requestFocus(); 
		} else {
			int linha = listaServicos.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from clientes where nome like '" + txtCliente.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {

					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();

					if (rs.next()) {
			
						txtID.setText(rs.getString(1)); // 1 = ID
						txtCliente.setText(rs.getString(2));
						if (txtCliente.getText().isEmpty()) {
							scrollPane.setVisible(false);
						}

						scrollPane.setVisible(false);
					

					} else {
						JOptionPane.showMessageDialog(null, "Usuario não encontrado");

						btnCreate.setEnabled(true);
						btnBuscar.setEnabled(false);
					}
				
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void listarTecnico() {
	
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaServicosTec.setModel(modelo);

		String readlista = "select * from tecnicos where nome like '" + txtTecnicos.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

			while (rs.next()) {
			
				scrollPane_2.setVisible(true);
				
				modelo.addElement(rs.getNString(2));
				if (txtTecnicos.getText().isEmpty()) {
					scrollPane_2.setVisible(false);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarListaTec() {

		if (txtTecnicos.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			txtTecnicos.requestFocus(); 
		} else {
			int linha = listaServicosTec.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from tecnicos where nome like '" + txtTecnicos.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {
		
					con = dao.conectar();
				
					pst = con.prepareStatement(read2);
					
					rs = pst.executeQuery();

					if (rs.next()) {
						
						txtIDtec.setText(rs.getString(1));
						txtTecnicos.setText(rs.getString(2));
						if (txtTecnicos.getText().isEmpty()) {
							scrollPane_2.setVisible(false);
						}

						scrollPane_2.setVisible(false);


					} else {
						JOptionPane.showMessageDialog(null, "Usuario não encontrado");

						btnCreate.setEnabled(true);
						btnBuscar.setEnabled(false);
					}

					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void buscarCliente() {

		if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Cliente");
			txtCliente.requestFocus();
		} else {
	
			String read = "select * from clientes where nome = ?";
			try {

				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtCliente.getText());

				ResultSet rs = pst.executeQuery();

				if (rs.next()) {

					txtID.setText(rs.getString(1)); 

					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente não cadastrado");

					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarOS() {

		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o PC ou Notebook do Cliente");
			txtModelo.requestFocus();
		} else if (cboStatusOS.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o status da OS");
			cboStatusOS.requestFocus();	
		} else if (cboStatusOS.getSelectedItem().equals("Finalizado") && txtdateSaida.getDate() == null) {
			JOptionPane.showMessageDialog(null, "Preencha a data de saida do serviço");
			txtdateSaida.requestFocus();
		} else if (cboStatusOS.getSelectedItem().equals("Em andamento") && txtTecnicos.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Preencha o Nome do tecnico responsavel pelo serviço");
			txtTecnicos.requestFocus();
		} else if (txtDefeitoDiagnosticado.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito Diagnosticado pelo Técnico");
			txtDefeitoDiagnosticado.requestFocus();
		} else if (cboStatusOS.getSelectedItem().equals("Finalizado") && cboPagamento.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a forma de pagamento");
			cboPagamento.requestFocus();
		} else if (cboStatusOS.getSelectedItem().equals("Finalizado") && txtValor.getText().equals("0.00")){
			JOptionPane.showMessageDialog(null, "Preencha o Valor do serviço");
			txtValor.requestFocus();
	
		} else {


			String update = "update servicos set marca=?,modelo=?,serie=?,acessorios=?,observacao=?,defeitoInformado=?,defeitoDiagnosticado=?,statusOS=?,valor=?,formaPagamento=?,saida=?,idtec=?,idcli=?,usuario=?  where os=?";
		
			try {
			
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtMarca.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtSerie.getText());
				pst.setString(4, txtAcessorios.getText());
				pst.setString(5, txtObservacao.getText());
				pst.setString(6, txtDefeitoInformado.getText());
				pst.setString(7, txtDefeitoDiagnosticado.getText());
				pst.setString(8, cboStatusOS.getSelectedItem().toString());
				pst.setString(9, txtValor.getText());
				pst.setString(10, cboPagamento.getSelectedItem().toString());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");

				if (txtdateSaida.getDate() == null) {

				pst.setString(11, null);

				} else {

				String dataFormatada = formatador.format(txtdateSaida.getDate());

				pst.setString(11, dataFormatada);

				}
				pst.setString(13, txtID.getText());
				pst.setString(12, txtIDtec.getText());
				pst.setString(14, txtUser.getText());
				pst.setString(15, txtOS.getText());
				pst.executeUpdate();
			
				JOptionPane.showMessageDialog(null, "Dados da OS do cliente alterados com sucesso");
				
				con.close();
				limparCampos();
		
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	private void excluirOS() {
	
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão dá Máquina?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from servicos where os=?";
		
			try {
			
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtOS.getText());
				pst.executeUpdate();

				limparCampos();

				JOptionPane.showMessageDialog(null, "Pc ou Notebook excluído");
			
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscarOS() {
		String numOS = JOptionPane.showInputDialog("Numero da OS");
		String read = "select * from servicos where OS = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			rs = pst.executeQuery();

			btnCreate.setEnabled(false);
			btnUpdate.setEnabled(true);
			btnDelete.setEnabled(true);

			if (rs.next()) {

				txtMarca.setText(rs.getString(3));
				txtModelo.setText(rs.getString(2));
				txtSerie.setText(rs.getString(4));
				txtAcessorios.setText(rs.getString(5));
				txtObservacao.setText(rs.getString(6));
				txtDefeitoInformado.setText(rs.getString(7));
				txtDefeitoDiagnosticado.setText(rs.getString(8));
				cboStatusOS.setSelectedItem(rs.getString(9));
				txtValor.setText(rs.getString(10));
				cboPagamento.setSelectedItem(rs.getString(11));
				
				String setarData = rs.getString(13);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				txtdateEntrada.setDate(dataFormatada);
				String setarDataSaida = rs.getString(12);

				if (setarDataSaida == null) {
					txtdateSaida.setDate(null);
				} else {
			
					Date dataSaidaFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataSaida);
					txtdateSaida.setDate(dataSaidaFormatada);
				}
				
				txtOS.setText(rs.getString(1));
				txtID.setText(rs.getString(14));
				txtIDtec.setText(rs.getString(15));
				txtUser.setText(rs.getString(16));


			} else {
				JOptionPane.showMessageDialog(null, "Ordem de serviço não encontrada");

				btnCreate.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

	private void imprimirOS() {

		Document document = new Document();
		try {

			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String readOS = "select servicos.os, date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada, servicos.usuario as usuário, servicos.modelo,servicos.serie, servicos.DefeitoInformado,servicos.statusOS as status_OS,servicos.DefeitoDiagnosticado, tecnicos.nome as tecnicos, servicos.valor, date_format(servicos.saida,'%d/%m/%Y') as data_saida, clientes.nome as cliente, clientes.telefone from servicos inner join clientes on servicos.idcli = clientes.idcli inner join tecnicos on servicos.idtec = tecnicos.idtec where os = ?";
		
			try {
		
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				rs = pst.executeQuery();

				if (rs.next()) {					
					
					Image imagem = Image.getInstance(Servicos.class.getResource("/img/pcOS.jpg"));
					imagem.scaleToFit(192,148);
					imagem.setAbsolutePosition(20, 670);
					document.add(imagem);
					document.add(new Paragraph(" "));
					
					
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					document.add(new Paragraph(" "));
					Image imagem2 = Image.getInstance(Servicos.class.getResource("/img/pcnot.png"));
					imagem2.scaleToFit(300,3000);
					imagem2.setAbsolutePosition(200, 90);
					document.add(imagem2);
					
					
					document.add(new Paragraph(" "));
					Paragraph ordemdeservico = new Paragraph("Ordem de Serviço: " + rs.getString(1));
					ordemdeservico.setAlignment(Element.ALIGN_CENTER);
					document.add(ordemdeservico);
					
					document.add(new Paragraph(" "));
					Paragraph usuario = new Paragraph("Usuário: " + rs.getString(3));
					usuario.setAlignment(Element.ALIGN_CENTER);
					document.add(usuario);
					
					document.add(new Paragraph(" "));
					Paragraph cliente = new Paragraph("Cliente: " + rs.getString(12));
					cliente.setAlignment(Element.ALIGN_LEFT);
					document.add(cliente);
					
					document.add(new Paragraph(" "));
					Paragraph telefone = new Paragraph("Telefone: " + rs.getString(13));
					telefone.setAlignment(Element.ALIGN_LEFT);
					document.add(telefone);
				
					document.add(new Paragraph(" "));
					Paragraph dataentrada = new Paragraph("Data de entrada: " + rs.getString(2));
					dataentrada.setAlignment(Element.ALIGN_LEFT);
					document.add(dataentrada);
						
					document.add(new Paragraph(" "));
					Paragraph Modelo = new Paragraph("Modelo: " + rs.getString(4));
					Modelo.setAlignment(Element.ALIGN_LEFT);
					document.add(Modelo);
					
					document.add(new Paragraph(" "));
					Paragraph serie = new Paragraph("N de Série: " + rs.getString(5));
					serie.setAlignment(Element.ALIGN_LEFT);
					document.add(serie);					
						
					document.add(new Paragraph(" "));
					Paragraph DefeitoInformado = new Paragraph("Defeito: " + rs.getString(6));
					DefeitoInformado.setAlignment(Element.ALIGN_LEFT);
					document.add(DefeitoInformado);					
					document.add(new Paragraph(" "));
					
					Paragraph DefeitoDiagnosticado = new Paragraph("Defeito: " + rs.getString(8));
					DefeitoDiagnosticado.setAlignment(Element.ALIGN_LEFT);
					document.isInline();
					document.add(DefeitoDiagnosticado);
					document.add(new Paragraph(" "));

					Paragraph statusOS = new Paragraph("Status: " + rs.getString(7));
					statusOS.setAlignment(Element.ALIGN_LEFT);
					document.isInline();
					document.add(statusOS);
					document.add(new Paragraph(" "));

					Paragraph tecnicos = new Paragraph("Técnicos: " + rs.getString(9));
					tecnicos.setAlignment(Element.ALIGN_LEFT);
					document.add(tecnicos);
					document.add(new Paragraph(" "));

					Paragraph valor = new Paragraph("Valor: " + rs.getString(10));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					document.add(new Paragraph(" "));

					Paragraph datasaida = new Paragraph("Data de Saída: " + rs.getString(11));
					datasaida.setAlignment(Element.ALIGN_LEFT);
					document.add(datasaida);
					document.add(new Paragraph(" "));
					
					
					

					
					
					
					
					
				}
				// fechar a conexão com o banco
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void limparCampos() {
		txtOS.setText(null);
		txtModelo.setText(null);
		txtDefeitoInformado.setText(null);
		txtDefeitoDiagnosticado.setText(null);
		cboStatusOS.setSelectedItem("");
		txtValor.setText(null);
		txtMarca.setText(null);
		txtSerie.setText(null);
		txtAcessorios.setText(null);
		txtObservacao.setText(null);
		txtCliente.setText(null);
		txtID.setText(null);
		txtTecnicos.setText(null);
		cboPagamento.setSelectedItem("");
		txtIDtec.setText(null);
		scrollPane.setVisible(false);
		scrollPane_2.setVisible(false);
		txtdateEntrada.setDate(null);
		txtdateSaida.setDate(null);
		txtTecnicos.setText(null);
		// setar botões
		// btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
	}
}
