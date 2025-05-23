<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%  session.setAttribute("matricula", "X"); %>

<!-- inicio de estructura -->
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
%>
	<script>
	function cortar()
	{
		var texto1 = document.datos1.f_buscar_1.value.toUpperCase();
		var texto2 = document.datos1.f_buscar_2.value.toUpperCase();
		var texto3 = document.datos1.f_buscar_3.value.toUpperCase();
		if((texto1=="")&&(texto2=="")&&(texto3==""))
		{
			alert('Complete formulario');
			return true;
		}else{
			document.datos1.submit();
		}	
	}
	
	
	
	function validar_codigo()
	{
		if 	( 	( document.datos2.f_codigo.value!="")
				&&(validar_cod(document.datos2.f_codigo.value)!=false)
	   		)
		{
			document.datos2.submit();
		}
		else
		{
			alert("Ingrese bien el codigo.")
		}
	}

	function validar_cod(xcod)
	{
		largo = xcod.length;
		for (i=0; i<largo ;i++)
		{
			if ( no_se_encuentra( xcod.substring(i,i+1), "1234567890") )
			{
				return false;
			}
		}
			return true;
	}

	function no_se_encuentra(xletra, xtexto)
	{
		larg=xtexto.length;
		for(l=0; l<larg; l++)
		{
			if ( xletra==xtexto.substring(l,l+1) )
			{
				return false;
			}
		}
			return true;
	}	
	</script>
</head>
<div class="container-fluid">
<h1>Permisos</h1>
<div class="alert alert-info"></div>
 <table class="table">
	<tr>
       <th align="CENTER"><h3>B&uacute;squeda por Nombre</h3></th>
 	</tr>
 <form name="datos1" method="POST" action="busqueda_elegir.jsp">
 	<tr align='CENTER'>
 		<td>
			Pat. 
			<input type="Text" class="text" name="f_buscar_2" size="3" maxlength="20">
			Mat.
			<input type="Text" class="text" name="f_buscar_3" size="3" maxlength="20">
			Nom.  
			<input type="Text" class="text" name="f_buscar_1" size="3" maxlength="20">
			<input class="btn btn-primary" type="button" value="Buscar Postulante" onclick="cortar()">
      </td>
	</tr>
 </form>
 	<tr align='CENTER'>
 		<td><br></td>
	</tr>	
   	<tr>
       <th align="CENTER"><h3>B&uacute;squeda por C&oacute;digo</h3></th>
 	</tr>
 <form name="datos2" method="POST" action="busqueda_elegir_codigo.jsp">
  	<tr align='CENTER'>
 		<td>
 			Cod. Postulante : 			
        <input type="Text" class="text" name="f_codigo" size="8" maxlength="7">
        <input class="btn btn-primary" type="button" value="Buscar" onclick="validar_codigo()">
      </td>
	</tr>
 </form>
 </table>
 </div>
 <%
 	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
 %>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>