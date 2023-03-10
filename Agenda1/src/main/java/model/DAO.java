package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class DAO {
			/* Parameters de conexão */
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "Dba@123456";
	
	
	private Connection conectar() 
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
			
		}catch(Exception e){
			System.out.println(e);
			return null;
		}	
	}
	
	
	/*Test conexão*/
	public void testeConexao(){
		try
		{
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e){
			System.out.println(e);
		}
	}
	
	
	
	
	
	
	
	public void inserirContato(JavaBeans contato){
		String create = "insert into contatos (nome,fone,email) values (?,?,?)";
		
		try
		{
		
		
			//Abrir conexão
			Connection con =  conectar();
			
			
			//Preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			
			
			
			//Substituir os paramentro (?) pelo conteudo das várivaies JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			
			//Executar a query
			pst.executeUpdate();
			
			
			
			//Encerrar a conexão
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
	
	
	
/*------------------------------------------------------------------**********-----------------------------------------------------------------------------*/	
/*------------------------------------------------------------------CRUD READ-----------------------------------------------------------------------------*/
/*------------------------------------------------------------------**********----------------------------------------------------------------------------*/

	
	public ArrayList<JavaBeans> listarContatos()
	{
		//Criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<JavaBeans>();
		
		
		String read = "select * from contatos order by nome";
		
		try
		{
			//Abrindo a conexão
			Connection con =  conectar();
			
			
			//Preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(read);
			
			
			//Executa a query e armazena no rs.
			ResultSet rs = pst.executeQuery();
			
			
			//O laço abaixo será executado enquanto houver contatos.(o next serve para isso, e esta na classe ResultSet)
			// no banco de dados utilizar o comando [describe nome_da_tabela] para ver a ordem dos campos
			while(rs.next())
			{
				//Variavel idcon ira receber o primeiro campo do banco de dados pois tem o 1 entre os parênteses.
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				
				//populando o ArrayList.
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			
			con.close();
			return contatos;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}

	}
	
	
	
	
/*------------------------------------------------------------------**********-----------------------------------------------------------------------------*/	
/*------------------------------------------------------------------CRUD UPDATE-----------------------------------------------------------------------------*/
/*------------------------------------------------------------------**********----------------------------------------------------------------------------*/

//SELECIONAR CONTATO
public void selecionarContato(JavaBeans contato)
{
	String read2= "select * from contatos where idcon = ?";
	
	try
	{
		Connection con = conectar();
		PreparedStatement pst = con.prepareStatement(read2);
		pst.setString(1, contato.getIdcon());
		ResultSet rs = pst.executeQuery();
		while(rs.next())
		{
			//setar
			contato.setIdcon( rs.getString(1) );
			contato.setNome( rs.getString(2) );
			contato.setFone( rs.getString(3) );
			contato.setEmail( rs.getString(4) );
		}
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}



	//EDITAR O CONTATO
	public void alterarContato(JavaBeans contato)
	{
		String create = "update contatos set nome=?, fone=?, email=? where idcon=?";
		
		try
		{
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	
	
/*------------------------------------------------------------------**********-----------------------------------------------------------------------------*/	
/*----------------------------------------------------------------CRUD DELETE-----------------------------------------------------------------------------*/
/*------------------------------------------------------------------**********----------------------------------------------------------------------------*/

public void deletarContato(JavaBeans contato)
{
	String delete = "delete from contatos where idcon = ?";
	
	try
	{
		Connection con = conectar();
		PreparedStatement pst = con.prepareStatement(delete);
		pst.setString(1, contato.getIdcon());
		
		pst.executeUpdate();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	
}


	}









