<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="alumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoAlumno");
	String resultado		= request.getParameter("resultado");
	
	int accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	
	switch(accion){
		case 2:{	//Modificar registro			
			out.print("<div class='alert alert-success'><b>Registrado...<a class='btn btn-primary' href='registro?resultado="+resultado+"'>Regresar</a></div>");
			
		}break;
	}
	
	alumPersonal = alumUtil.mapeaRegId(conEnoc, codigoPersonal);
%>
<head>
<script type="text/javascript">
	function revisaCodigo(){
		if(document.forma.codigoSe.value != ""){
			if(document.forma.codigoSe.value.length == 7){
				return true;
			}else{
				alert('El "Código SE" debe ser de 7 caracteres');
			}
		}else{
			alert('Llene el campo de "Código SE" para para poder guardar!');
		}
		return false;
	}
</script>
</head>
<body>
<%
	if(resultado != null){
%>
<table style="margin: 0 auto;" class="oculto">
	<tr>
		<td><%=resultado %></td>
	</tr>
</table>
<%
	}
%>
<table style="margin: 0 auto; width:95%;">
	<tr>
		<td class="titulo">C&oacute;digo de Registro S.E. N.L.</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
</table>
<form id="forma" name="forma" action="registro.jsp?Accion=2" method="post">
<table style="margin: 0 auto; width:40%;" class="tabla">
	<tr><th colspan="2">&nbsp;</th></tr>
	<tr>
		<td><b>Matr&iacute;cula UM:</b></td>
		<td class="ayuda alumno <%=codigoPersonal %>"><%=codigoPersonal %></td>
	</tr>
	<tr>
		<td><b><spring:message code="aca.Nombre"/>:</b></td>
		<td><%=alumPersonal.getNombre() %> <%=alumPersonal.getApellidoPaterno() %> <%=alumPersonal.getApellidoMaterno() %></td>
	</tr>
	<tr>
		<td><b><spring:message code="aca.Genero"/>:</b></td>
		<td><%=alumPersonal.getSexo().equals("M")?"Masculino":"Femenino" %></td>
	</tr>
	<tr>
		<td><b><spring:message code='aca.Fecha'/>:</b></td>
		<td><%=alumPersonal.getFNacimiento() %></td>
	</tr>
	<tr>
		<td><b><spring:message code='aca.Carrera'/>:</b></td>
		<td><%=alumUtil.getCarrera(conEnoc, codigoPersonal) %></td>
	</tr>
	<tr>
		<td><b>C&oacute;digo SE:</b></td>
		<td><input type="text" class="text" id="codigoSe" name="codigoSe" value="<%=alumPersonal.getCodigoSe()==null?"":alumPersonal.getCodigoSe() %>" size="8" maxlength="7" /></td>
	</tr>
	<tr>
		<th colspan="2" align="center"><input type="submit" value="Guardar" onclick="return revisaCodigo();" /></th>
	</tr>
</table>
</form>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>