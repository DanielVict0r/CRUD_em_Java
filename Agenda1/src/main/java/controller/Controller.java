package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import model.DAO;
import model.JavaBeans; 
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

//Recebe as requisições pelo nome
@WebServlet(urlPatterns = {"/Controller", "/main","/insert", "/select", "/update", "/delete", "/report"})
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();
        public Controller() 
        {
            super();
        }






	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if(action.equals("/main"))
		{
			contatos(request, response);
			
		}
		
		else if(action.equals("/insert")){
		
			novoContato(request, response);
		}
		else if(action.equals("/select")){
		
			listarContato(request, response);
		}
		else if(action.equals("/update")){
		
			editarContato(request, response);
		}
		else if(action.equals("/delete")){
		
			removerContato(request, response);
		}
		else if(action.equals("/report")){
		
			gerarRelatorio(request, response);
		}


		else
		{
			response.sendRedirect("index.html");
		}
		
		//teste de conexão
		dao.testeConexao();
	}
	
	
	
	
	//Listar os contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Criando um objeto que ira receber os dados do JavaBeans;
		ArrayList<JavaBeans> lista = dao.listarContatos();
		
		
		
		//TESTE de recebimento da lista
		for(int i =0 ; i < lista.size(); i++)
		{
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
		}
		
		//Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}
	
	
	
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		//setar as variáveis javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.inserirContato(contato);
		
		
		//redirecionar para o agenda.jsp;
		response.sendRedirect("main");
	}

	
	
	
	
	
	//EDITAR CONTATO
	protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//Recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		
		//Teste
		System.out.println(idcon);
		
		
		//Setar a varivael javaBeans
		contato.setIdcon(idcon);
		
		//Executar o método selecionarContato (DAO)
		dao.selecionarContato(contato);
		
		/*
		//Teste de recebimento
		System.out.println(contato.getIdcon());
		System.out.println(contato.getNome());
		System.out.println(contato.getFone());
		System.out.println(contato.getEmail());	
		*/
		
		//Setar os atributos do formulário com o conteúdo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		//Encaminha ao dumento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	
	
	
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//teste de recebimento
		//esta pegando os valore dos inputes pelo name
		/*
		System.out.println(request.getParameter("idcon"));
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		*/
		
		
		//setar as variavéis javabeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		dao.alterarContato(contato);
		
		//redirecionar para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");
	}
	
	
	
	
	protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//recebimento do id dos contatos a ser excluido (validador.js)
		String idcon = request.getParameter("idcon");
		
		//Teste
		//System.out.println(idcon);
		
		//Setar a variavel idcon JavaBeans
		contato.setIdcon(idcon);
		
		//executar o método deletarContato (DAO) passando o objeto contato
		dao.deletarContato(contato);
		
		//redirecionar para o documento agenda.jsp (atualizando as alterações)
		response.sendRedirect("main");

	}
	
	
	//Gerar relatorio em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Document documento = new Document();
		try
		{
			//Informando que o tipo de resposta será um documento em pdf.
			response.setContentType("apllication/pdf");
			
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename= "+"contatos.pdf");
			
			//criar documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			//abrir o documento para colocar o conteúdo
			documento.open();
			documento.add(new Paragraph ("Lista de contatos: "));
			documento.add(new Paragraph ("  "));
			
			//criar uma tabela. O numero 3 quer dizer que vai estar sem colunas.
			PdfPTable tabela = new PdfPTable(3);
			
			//cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph ("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph ("Telefone"));
			PdfPCell col3 = new PdfPCell(new Paragraph ("E-mail"));
			
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			//poupular a tabela
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i=0; i<lista.size(); i++)
			{
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());


			}
			
			documento.add(tabela);
			
			documento.close();
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			documento.close();
		}
	}

}



