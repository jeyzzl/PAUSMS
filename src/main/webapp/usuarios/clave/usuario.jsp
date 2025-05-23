<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.spring.Usuarios"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%
	String codigoUsuario		= (String) request.getAttribute("codigoUsuario");
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String respuesta			= (String) request.getAttribute("respuesta");
	String nombreCarrera		= (String) request.getAttribute("nombreCarrera");
	boolean alumnoCimum 		= (boolean)request.getAttribute("alumnoCimum");
	
	Usuarios usuarioDatos 		= (Usuarios) request.getAttribute("usuarioDatos");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
%>
<head>
<script type="text/javascript">
	function revisa(){
		if($("clave").value != ""){
			return true;
		}else{
			alert("Ingrese la clave para poder guardar");
		}
		return false;
	}
	
	function claveRedSync(){
		jQuery.get("usuarioAccion.jsp?Accion=5&codigoPersonal=<%=codigoPersonal %>", function(data){
			eval(data);
		});
	}
</script>
</head>
<body>
<%	if(!respuesta.equals("")){%>
	<table>
		<tr>
			<td align="center">
			</td>
		</tr>
	</table>
<%	}%>
<div class="container-fluid">
	<h2>User Password <small class="text-muted fs-6">( <%=codigoPersonal %> - <%=usuarioDatos.getNombre() %> <%=usuarioDatos.getApellidoMaterno().equals("-")?"":usuarioDatos.getApellidoMaterno()%> <%=usuarioDatos.getApellidoPaterno() %> )</small></h2>
	<form id="formaClave" name="formaClave" action="grabar" method="post">
<%	if(!respuesta.equals("")){%>	
	<div class="alert alert-info">	
<% 		if(respuesta.equals("1")){%>
			<font size=2 color=blue><b>Password has been saved!!!</b></font>
<% 		}else if(respuesta.equals("2")){%>
			<font size=2 color=blue><b>There was an error upon saving, please try again.</b></font>
<% 		}else if(respuesta.equals("3")){%>
			<font size=2 color=blue><b>Password has been saved!!!</b></font>
<% 		}%>			
	</div>
<%	}else{
		out.print("<hr>");
	}
%>	
	<table class="table table-sm table-bordered" style="width:50%;">
	<tr>
		<td width="50%"><img src="../../foto?Codigo=<%=codigoPersonal %>&Tipo=O" width="250px" /></td>
	</tr>				
		
<%	if(codigoPersonal.charAt(0) != '9'){%>
	<tr>
		<td><b><spring:message code="aca.Carrera"/></b><br><%=nombreCarrera%></td>		
	</tr>
<%	}%>							
	<tr>
		<td><b><spring:message code='aca.Usuario'/></b><br>  
<%	if ( codigoUsuario.equals("9800281")||codigoUsuario.equals("9800308") ){%>
		<input type="text" class="form-control" id="Cuenta" name="Cuenta" value="<%= usuarioDatos.getUsuario() %>"/>
<%	}else{
		out.print(acceso.getUsuario());
	} %>					  
		</td>
	</tr>
	<tr>
		<td>
			<b><spring:message code="aca.Clave"/></b><small class="text-muted">( *Do not use the plus sign (+) )<br>
			<input type="password" class="form-control" id="clave" name="clave"/>
		</td>
	</tr>	
<%	if( (codigoUsuario.equals("9800946") || codigoUsuario.equals("9820389")) && alumnoCimum==false){ %>	
	<tr class="table-info"><td>�This student does not have a plan in the conservatory!</td>
	</tr>
<%	}else{%>	
	<tr class="table-info">	
		<td>
			<input type="submit" class="btn btn-primary" value="Save Password" onclick="return revisa();" />&nbsp;&nbsp;	
		</td>
	</tr>
<%	} %>	
	</table>	
	</form>
</div>
</body>