<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Criando Contatos</title>
<link rel="icon" href="imagens/telefone.png">
<link rel="stylesheet" href="style.css">
</head>
<body>

	<div class="container_titulo3">
	<h1>Editar contato</h1>
	</div>
	
	<form name ="frmContato" action="update" id="novo-contato">
		<input type ="text" name="idcon" readonly value = "<%out.print(request.getAttribute("idcon"));%>" class="caixa">
		<input type ="text" name="nome"  value =  "<% out.print(request.getAttribute("nome"));%>"   class="caixa">
		<input type ="text" name="fone"  value  =  "<% out.print(request.getAttribute("fone"));%>"   class="caixa">
		<input type ="text" name="email"  value  =  "<% out.print(request.getAttribute("email"));%>"  class="caixa">
		<input type ="button" value="Salvar" onclick="validar()" class="link">	
	</form>

<script src="scripts/validador.js"></script>
</body>
</html>



