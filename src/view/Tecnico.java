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
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tecnico extends JDialog {
	

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;


	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtNometec;
	private JTextField txtIDTec;
	private ResultSet rs;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JScrollPane scrollPane;
	private JList listaTecnicos;


	public static void main(String[] args) {
		try {
			Tecnico dialog = new Tecnico();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Tecnico() {
		setModal(true);
		setTitle("Tecnicos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tecnico.class.getResource("/img/pc.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("NOME");
		lblNewLabel.setBounds(12, 111, 48, 14);
		contentPanel.add(lblNewLabel);
		
		txtNometec = new JTextField();
		txtNometec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnicos();
			}
		});
		txtNometec.setBounds(81, 109, 292, 17);
		contentPanel.add(txtNometec);
		txtNometec.setColumns(10);
		txtNometec.setDocument(new Validador(30));
		
		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.setToolTipText("Adicionar");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setBorderPainted(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarTecnicos();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setIcon(new ImageIcon(Tecnico.class.getResource("/img/add (2).png")));
		btnCreate.setBounds(51, 202, 48, 48);
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
		btnLimpar.setIcon(new ImageIcon(Tecnico.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(208, 202, 48, 48);
		contentPanel.add(btnLimpar);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setBounds(14, 72, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtIDTec = new JTextField();
		txtIDTec.setEditable(false);
		txtIDTec.setBounds(81, 69, 59, 20);
		contentPanel.add(txtIDTec);
		txtIDTec.setColumns(10);
		
		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setBorderPainted(false);
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setIcon(new ImageIcon(Tecnico.class.getResource("/img/lupa.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarTecnicos();
			}
		});
		btnBuscar.setToolTipText("Pesquisar Contato");
		btnBuscar.setBounds(162, 50, 48, 48);
		contentPanel.add(btnBuscar);
		
		
		getRootPane().setDefaultButton(btnBuscar);
		
		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setToolTipText("update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			editarTecnicos();
			}
		});
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setIcon(new ImageIcon(Tecnico.class.getResource("/img/update.png")));
		btnUpdate.setBounds(132, 202, 48, 48);
		contentPanel.add(btnUpdate);
		
		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setToolTipText("excluir usuario");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			excluirTecnicos();
			}
		});
		btnDelete.setBorderPainted(false);
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(new ImageIcon(Tecnico.class.getResource("/img/del.png")));
		btnDelete.setBounds(288, 202, 48, 48);
		contentPanel.add(btnDelete);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setWheelScrollingEnabled(false);
		scrollPane.setBounds(81, 126, 292, 57);
		contentPanel.add(scrollPane);
		
		listaTecnicos = new JList();
		listaTecnicos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarListaTec();
			}
		});
		scrollPane.setViewportView(listaTecnicos);

		
		
		
	}
	
	
	private void adicionarTecnicos() {

		if (txtNometec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Técnico");
			txtNometec.requestFocus(); 	
	} else {
		
			 String create = "insert into tecnicos (nome) values (?)";
				
			try {
			
				 con = dao.conectar();
				 pst = con.prepareStatement(create);
				pst.setString(1, txtNometec.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Técnico Adicionado com Sucesso");

				limparCampos();
			
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Técnico não adicionado. \nEste Nome ja esta sendo utilizado");
				txtNometec.setText(null);
				txtNometec.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);

			}
		}

	}
	
	private void listarTecnicos() {

			DefaultListModel<String> modelo = new DefaultListModel<>();
			listaTecnicos.setModel(modelo);
		
			String readlista = "select * from tecnicos where nome like '" + txtNometec.getText() + "%'" + "order by nome"; 
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();
		
			while (rs.next()) {
				scrollPane.setVisible(true);
	
				modelo.addElement(rs.getNString(2));
				if (txtNometec.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
				
			}
		}catch (Exception e) {
			System.out.println(e);
		}
		}
	
	private void buscarListaTec() {
		
		if (txtNometec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			txtNometec.requestFocus(); 
		} else {
			int linha = listaTecnicos.getSelectedIndex();
			if (linha >= 0) {
			String read2 = "select * from tecnicos where nome like '" + txtNometec.getText() + "%'" + "order by nome limit " + linha + " , 1";
			
			try {
			
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();

				if (rs.next()) {

					txtIDTec.setText(rs.getString(1));
					txtNometec.setText(rs.getString(2));
					if (txtNometec.getText().isEmpty()) {
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
	

	private void buscarTecnicos() {

		if (txtNometec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Tecnico");
			txtNometec.requestFocus();
		} else {

			String read = "select * from tecnicos where nome = ? ";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtNometec.getText());

				rs = pst.executeQuery();
				if (rs.next()) {
		
					txtIDTec.setText(rs.getString(1));
					txtNometec.setText(rs.getString(2));
					btnCreate.setEnabled(false);
					btnDelete.setEnabled(true);
					btnUpdate.setEnabled(true);
					btnBuscar.setEnabled(false);
					
				} else {
					JOptionPane.showMessageDialog(null, "Técnico não existe");
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	
	private void editarTecnicos(){

		if (txtNometec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome");
			txtNometec.requestFocus();
		} else {

			String update = "update tecnicos set nome=? where idtec = ?";

			try {

				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNometec.getText());
				pst.setString(2, txtIDTec.getText());
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Técnico alterados com Sucesso");
				con.close();

				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException el) {
				JOptionPane.showMessageDialog(null, "Nome não atualizado. \nEste Nome ja esta sendo utilizado");
			} catch (Exception e2) {
				txtNometec.setText(null);
				txtNometec.requestFocus();
				System.out.println(e2);
			}
		}
	}
	

	private void excluirTecnicos() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desse Técnico?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String delete = "delete from tecnicos where idtec=?" ;
					try {
						
					
						con = dao.conectar();
						pst = con.prepareStatement(delete);
						pst.setString(1, txtIDTec.getText());
						pst.executeUpdate();

						limparCampos();
						JOptionPane.showMessageDialog(null, "Técnico excluído");
					} catch (Exception e) {
						System.out.println(e);
					}
		}
	}
	
	
	private void limparCampos() {
		txtIDTec.setText(null);
		txtNometec.setText(null);
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
	} 
}
