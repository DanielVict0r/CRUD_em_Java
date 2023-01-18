<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ page  import ="model.JavaBeans" %>
<%@ page import ="java.util.ArrayList" %>


<%
ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");


//TESTE
/*
for(int i = 0; i<lista.size(); i++)
{
		out.println(lista.get(i).getIdcon());
		out.println(lista.get(i).getNome());
		out.println(lista.get(i).getFone());
		out.println(lista.get(i).getEmail());

}
*/


%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda</title>
<link rel="stylesheet" href="style.css">
</head>
<body>

<div class="container_titulo2">
<h1>Agenda de contatos</h1>
</div>


	

	


<div id="tabela">



<div class="container_opcao1">
	<a href="novo.html" class="link">Novo contato</a>
	<a href="report" class="link">Relátorio</a>
</div>




	<table id="tabela2">
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Telefone</th>
				<th>@mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		
		<tbody>
			
			<% for (int i = 0; i<lista.size(); i++) { %>
				<tr>
				<td><%=   lista.get(i).getIdcon()  %></td>
				<td><%=   lista.get(i).getNome()   %></td>
				<td><%=   lista.get(i).getFone()   %></td>
				<td><%=   lista.get(i).getEmail()  %></td>
				
				<td>  
				<a href="select?idcon=<%=lista.get(i).getIdcon() %>" class="botao1">  Editar</a>      
				<a href="javascript: confirmar( <%=lista.get(i).getIdcon() %>)"  class="botao2">  Excluir</a>    
				</td>	
				
						
				</tr>
			<% } %>

		
		</tbody>
	</table>
	
	</div>
	
		
		
	<script src="scripts/confirmador.js"></script>
</body>
</html>



























