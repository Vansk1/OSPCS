package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.Cursor;

public class Relatorios extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private ResultSet rs;
	private PreparedStatement pst;
	

	private final JPanel contentPanel = new JPanel();
	private JButton btnRelClientes;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;


	public static void main(String[] args) {
		try {
			Relatorios dialog = new Relatorios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/pc.png")));
		setBounds(100, 100, 506, 326);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		btnRelClientes = new JButton("");
		btnRelClientes.setToolTipText("Clientes");
		btnRelClientes.setContentAreaFilled(false);
		btnRelClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelClientes.setBorderPainted(false);
		btnRelClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/client.png")));
		btnRelClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnRelClientes.setBounds(41, 46, 180, 85);
		contentPanel.add(btnRelClientes);
		
		btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Aguardando Técnico");
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(Relatorios.class.getResource("/img/agua.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aguardandoTecnico();
			}
		});
		btnNewButton.setBounds(252, 49, 190, 79);
		contentPanel.add(btnNewButton);
		
		btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("Serviço Entregue");
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/entre.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiçoEntregue();
			}
		});
		btnNewButton_1.setBounds(41, 155, 180, 73);
		contentPanel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("");
		btnNewButton_2.setToolTipText("Serviço em Andamento");
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setIcon(new ImageIcon(Relatorios.class.getResource("/img/and.png")));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiçoEmAndamento();
			}
		});
		btnNewButton_2.setBounds(252, 155, 190, 73);
		contentPanel.add(btnNewButton_2);
	}
	private void relatorioClientes() {

		Document document = new Document();
	
		try {
			PdfWriter.getInstance(document,new FileOutputStream("cliente.pdf"));
			
			document.open();		
			
			Date dataRelatorio = new Date();		
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Clientes"));
			document.add(new Paragraph(" "));
			
	
			String readClientes = "select nome,fone from clientes order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readClientes);
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(2);
				
				PdfPCell col1 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Fone"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				while (rs.next()){
				
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
				}
				document.add(tabela);
				
				document.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
		System.out.println(e);
		}
	
		document.close();
	
		
		try {
			Desktop.getDesktop().open(new File("cliente.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void aguardandoTecnico() {

		Document document = new Document();
	
		try {
			PdfWriter.getInstance(document,new FileOutputStream("Aguardando Inicio do Serviço.pdf"));
		
			document.open();		
		
			Date dataRelatorio = new Date();		
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Aguardando Inicio do Serviço"));
			document.add(new Paragraph(" "));
			
		
			
			
			String readServicos = "select servicos.os,servicos.modelo,servicos.DefeitoInformado, date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada, clientes.nome as cliente, clientes.telefone from servicos inner join clientes on servicos.idcli = clientes.idcli where statusOS = 'Finalizado';";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readServicos);				
				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(6);
			
				
				
				PdfPCell col1 = new PdfPCell (new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col3 = new PdfPCell (new Paragraph("Observação"));
				PdfPCell col4 = new PdfPCell (new Paragraph("Data de Entrada do Serviço"));
				PdfPCell col5 = new PdfPCell (new Paragraph("Cliente"));
				PdfPCell col6 = new PdfPCell (new Paragraph("Telefone	"));
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				while (rs.next()){
					
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}
				document.add(tabela);
				
				document.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
		System.out.println(e);
		}
	
		document.close();
	
		
		
		try {
			Desktop.getDesktop().open(new File("Aguardando Inicio do Serviço.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void ServiçoEntregue() {
		
		Document document = new Document();
		document.setPageSize(PageSize.A4.rotate());
	
		try {
			PdfWriter.getInstance(document,new FileOutputStream("Serviços Entregue.pdf"));
			
			document.open();		
			
			Date dataRelatorio = new Date();		
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Serviço Entregue"));
			document.add(new Paragraph(" "));
			
		
			
			
			String readServicos = "select servicos.os,servicos.modelo,servicos.serie,servicos.DefeitoInformado, servicos.DefeitoDiagnosticado,tecnicos.nome as tecnicos, servicos.valor, date_format(servicos.dataOS,'%d/%m/%Y - %H:%i') as data_entrada, date_format(servicos.saida,'%d/%m/%Y') as data_saida, clientes.nome as cliente, clientes.telefone from servicos inner join clientes on servicos.idcli = clientes.idcli inner join tecnicos on servicos.idtec = tecnicos.idtec where statusOS = 'Finalizado';";	

			try {
				con = dao.conectar(); 
				
				pst = con.prepareStatement(readServicos);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(10);
				
				
				
				PdfPCell col1= new PdfPCell (new Paragraph("Os"));
				PdfPCell col2= new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col3= new PdfPCell (new Paragraph("Serie"));
				PdfPCell col4= new PdfPCell (new Paragraph("Defeito Informado"));
				PdfPCell col5= new PdfPCell (new Paragraph("Defeito Diagnósticado Pelo Técnicos"));
				PdfPCell col6= new PdfPCell (new Paragraph("Técnicos"));
				PdfPCell col7= new PdfPCell (new Paragraph("Valor"));
				PdfPCell col8= new PdfPCell (new Paragraph("Telefone"));
				PdfPCell col9= new PdfPCell (new Paragraph("Data saída"));
				PdfPCell col10= new PdfPCell (new Paragraph("Cliente"));
				
				
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				tabela.addCell(col7);
				tabela.addCell(col8);
				tabela.addCell(col9);
				tabela.addCell(col10);
				
				while (rs.next()){
					
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
					tabela.addCell(rs.getString(7));
					tabela.addCell(rs.getString(11));
					tabela.addCell(rs.getString(9));
					tabela.addCell(rs.getString(10));
					
					
				}
				document.add(tabela);
			
				document.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
		System.out.println(e);
		}
		
		document.close();
		
		
		
		try {
			Desktop.getDesktop().open(new File("Serviços Entregue.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void ServiçoEmAndamento() {
		Document document2 = new Document();
		
		document2.setPageSize(PageSize.A4.rotate());
		Document document = new Document();
	
		try {
			PdfWriter.getInstance(document,new FileOutputStream("Em_andamento.pdf"));
			
			document.open();		
			
			Date dataRelatorio = new Date();		
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			document.add(new Paragraph("Em_andamento"));
			document.add(new Paragraph(" "));
			
			
			
			
			String readServicos = "select servicos.os, servicos.usuario as usuário, servicos.modelo,servicos.DefeitoInformado,servicos.statusOS as status_OS, tecnicos.nome as tecnicos, servicos.valor, clientes.nome as cliente, clientes.telefone from servicos inner join clientes on servicos.idcli = clientes.idcli inner join tecnicos on servicos.idtec = tecnicos.idtec where os = 1;";

 
 
			try {
				con = dao.conectar();
				
				pst = con.prepareStatement(readServicos);
				
				rs = pst.executeQuery();
				
				PdfPTable tabela = new PdfPTable(5);
				
				
				
			
				PdfPCell col1= new PdfPCell (new Paragraph("OS"));
				PdfPCell col2= new PdfPCell (new Paragraph("Usuário"));
				PdfPCell col3= new PdfPCell (new Paragraph("Modelo"));
				PdfPCell col4= new PdfPCell (new Paragraph("Defeito"));
				PdfPCell col5= new PdfPCell (new Paragraph("Tecnicos"));
				
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()){
					
					
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
			
				}
				document.add(tabela);
			
				document.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		} catch (Exception e) {
		System.out.println(e);
		}
		
		document.close();
		
		
		 
		try {
			Desktop.getDesktop().open(new File("Em_andamento.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
