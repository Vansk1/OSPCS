package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Principal extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	public JButton btnUsuarios;
	public JLabel lblUsuario;
	public JButton btnRelatorios;
	public JPanel panelRodape;
	private JButton btnClientes;
	private JButton btnServico;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/pc.png")));
		setResizable(false);
		setTitle("Radical Pc's");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setContentAreaFilled(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre.setBounds(535, 11, 65, 65);
		contentPane.add(btnSobre);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.windowBorder);
		panelRodape.setBounds(0, 401, 624, 40);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(287, 7, 308, 18);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		JLabel lblNewLabel = new JLabel("Usuário:");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 11, 58, 14);
		panelRodape.add(lblNewLabel);
		
		lblUsuario = new JLabel("New label");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(66, 11, 120, 14);
		panelRodape.add(lblUsuario);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setEnabled(false);
		btnUsuarios.setToolTipText("Usuarios");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/Addus.png")));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setBounds(76, 38, 133, 135);
		contentPane.add(btnUsuarios);
		
		btnServico = new JButton("");
		btnServico.setBorderPainted(false);
		btnServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
				
				servicos.usuario = lblUsuario.getText();
				
			}
		});
		btnServico.setContentAreaFilled(false);
		btnServico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServico.setToolTipText("Ordem de Serviço");
		btnServico.setIcon(new ImageIcon(Principal.class.getResource("/img/servic.png")));
		btnServico.setBounds(76, 208, 133, 137);
		contentPane.add(btnServico);
		
		btnClientes = new JButton("");
		btnClientes.setBorderPainted(false);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setContentAreaFilled(false);
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClientes.setToolTipText("Cliente");
		btnClientes.setBounds(309, 52, 126, 121);
		contentPane.add(btnClientes);
		
		btnRelatorios = new JButton("");
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios Relatorios = new Relatorios();
				Relatorios.setVisible(true);
			}
		});
		btnRelatorios.setBorderPainted(false);
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setEnabled(false);
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/relatorios.png")));
		btnRelatorios.setToolTipText("Relatorio");
		btnRelatorios.setBounds(309, 205, 152, 151);
		contentPane.add(btnRelatorios);
		
		JButton btnTecnicos = new JButton("");
		btnTecnicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTecnicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tecnico Tecnicos = new Tecnico();
				Tecnicos.setVisible(true);

			}
		});
		btnTecnicos.setIcon(new ImageIcon(Principal.class.getResource("/img/tec.png")));
		btnTecnicos.setToolTipText("Técnicos");
		btnTecnicos.setContentAreaFilled(false);
		btnTecnicos.setBorderPainted(false);
		btnTecnicos.setBounds(474, 138, 126, 121);
		contentPane.add(btnTecnicos);
		

		
	} 
	

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data)); 
	}
} 
