<%@ page import="java.util.List" %>
<%@page import="aca.vista.spring.Usuarios"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String origen			= (String) request.getAttribute("origen");
	String carrera			= (String) request.getAttribute("carrera");
	String sCarpeta 		= request.getParameter("carpeta");
%>
<head>
<script type="text/javascript">
	function recarga(completo){
		document.frmnombre.Accion.value = "1";
		document.frmnombre.Completo.value = completo;
		document.frmnombre.submit();
	}

	function Consultar(){
		document.frmnombre.Accion.value="1";
		document.frmnombre.Completo.value = null;
		document.frmnombre.submit();
	}
	
	function BuscarNombre(opcion){
		if (document.frmnombre.NombreCompleto.value!=""){
			document.frmnombre.Accion.value="1";
			document.frmnombre.Completo.value = null;
			document.frmnombre.submit();
		}
		else{
			alert("Input at least one entry");
			document.frmnombre.Nombre.focus();
		}
	}
	
	function BuscarCodigo( ){
		if(document.frmnombre.CodigoPersonal.value!=""){
			document.frmnombre.Accion.value="2";
			document.frmnombre.Completo.value = null;
			document.frmnombre.submit();
		}
		else{
			alert("Input the code!");
			document.frmnombre.CodigoPersonal.focus();
		}
	}		
	
	function nombreKeyPress(event){
		var key;
		if(window.event){ // IE
			key = event.keyCode;
		}
		else if(event.which){ // Netscape/Firefox/Opera
			key = event.which;
		}
		if(key == 13)
			if(document.frmnombre.NombreCompleto.value!=""){
				document.frmnombre.Accion.value="1";
				document.frmnombre.Completo.value = null;
				document.frmnombre.submit();
			}
			else{
				alert("Input at least one entry");
				document.frmnombre.Nombre.focus();
			}
	}
</script>
</head>
<%
	List<Usuarios> lisUsuarios	= (List<Usuarios>) request.getAttribute("lisUsuarios");
	
	String sAccion				= (String) request.getAttribute("sAccion");
	String opcion				= (String) request.getAttribute("opcion");
	
	int nAccion 				= (Integer) request.getAttribute("nAccion");
	String sResultado			= (String) request.getAttribute("sResultado");
	
	String nombreCompleto 		= (String) request.getAttribute("nombreCompleto");
	
	String sModulo				= (String) request.getAttribute("sModulo");
	String periodoId			= (String) request.getAttribute("periodoId");
	String codigoEmpleado		= (String) request.getAttribute("codigoEmpleado");
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String accion				= (String) request.getAttribute("accion");
	String msj					= (String) request.getAttribute("msj");
	String color				= (String) request.getAttribute("color");
	
	boolean completo 			= (boolean) request.getAttribute("completo");
	boolean existeUsuario 		= (boolean) request.getAttribute("existeUsuario");
	
	Usuarios usuarios 			= (Usuarios) request.getAttribute("usuarios");	
%>
<body onLoad="document.frmnombre.NombreCompleto.focus();">
<div class="container-fluid">
	<h2>Add Mentors</h2>
	<%=msj %>
	<form name="frmnombre" method="POST" action="buscar?Origen=<%=origen%>&carpeta=<%=sCarpeta%>&body=<%=request.getParameter("body") %>&carreraId=<%=carrera%>">
	<input name="Accion" type="hidden">
	<input name="Completo" type="hidden">
	<div class="alert alert-info">
		<a  class="btn btn-primary" href="mentor?carrera=<%=carrera%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;		
		<strong>Search by ID</strong>
		<input type="Text" class="text" name="CodigoPersonal" id="CodigoPersonal" size="8" maxlength="7" onkeypress="if(window.event.keyCode==13) BuscarCodigo()">&nbsp;
		<a class="btn btn-primary" href="javascript:BuscarCodigo()"><i class="fas fa-search" ></i></a>
		&nbsp;&nbsp;
		<strong>Search by name</strong>&nbsp;
		<input type="Text" class="text" name="NombreCompleto" value="<%=nombreCompleto.replaceAll("%"," ") %>" size="50" onkeypress="nombreKeyPress(event);">&nbsp;
		<a class="btn btn-primary" href="javascript:BuscarNombre('<%=opcion%>')"><i class="fas fa-search" ></i></a>
	</div>
	</form>
	<br>
<%	if(lisUsuarios.size()>30 && !completo){%>
		<table style="margin: 0 auto;">
			<tr>
				<td align="center">
					<b>Complete Search: <font size="3" color="#AE2113"><b><%=lisUsuarios.size()%></b></font></b><br>
					<input type="button" class="btn btn-primary" name="Completo" id="Completo" value="See all" onClick="javascript:recarga('1')">
				</td>
			</tr>
		</table>
<%	}else out.print("<br>");
%>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
  			<td width="7%" align="center" colspan="4"><b><%=sResultado%></b></tr>
  	</thead>
	<thead class="table-info">	 
		<tr>
  			<th width="7%" align="center">N°</th>
  			<th width="15%" align="center">ID</th>
  			<th width="65%" align="center">Name</th>
  			<th width="13%" align="center">Add</th>
		</tr>
  	</thead>
	<%	
		switch (nAccion){
			case 1:{
				for(int i=0; i<lisUsuarios.size(); i++){
					usuarios = (Usuarios) lisUsuarios.get(i);
	%>				
  					<tr>
    					<td align="center"><%=i+1%></td>
    					<td align="center"><%=usuarios.getCodigoPersonal()%></td>
    					<td>
		  					<div class="ayuda alumno <%=usuarios.getCodigoPersonal() %>">		  						
		  						<%=usuarios.getNombre()%>&nbsp;<%=usuarios.getApellidoPaterno()%>&nbsp;<%=usuarios.getApellidoMaterno()%>		  						
	  						</div>
						</td>
						<td><a class="btn btn-success btn-sm" href="grabarMentor?empleado=<%=usuarios.getCodigoPersonal() %>&carreraId=<%=carrera %>"><i class="icon-white icon-plus"></i></a></td>
  					</tr>
				<%	if(lisUsuarios.size()==1){
						usuarios = (Usuarios) lisUsuarios.get(0);
						if(usuarios.getCodigoPersonal().substring(0,1).equals("9")){
							session.setAttribute("codigoEmpleado", usuarios.getCodigoPersonal());
							session.setAttribute("codigoUltimo", usuarios.getCodigoPersonal());
						}
						else{
							session.setAttribute("codigoAlumno", usuarios.getCodigoPersonal());
							session.setAttribute("colorPortal",color);
							session.setAttribute("codigoUltimo", usuarios.getCodigoPersonal());
						}			
						sResultado = "Registered in your session: "+codigoPersonal;
						if(!origen.equals("X")){
							if(request.getParameter("body")!=null){%>
								<script lenguaje="javascript">
									document.location.href='../../<%=origen%>&carpeta=<%=sCarpeta%>';
				                </script>
						<%	}
							else{
								response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
							}
						}
					}
				
					if(i==29 && !completo) break;
				}
			break;
			}
			case 2:{
				if(!existeUsuario) break;
			%>
  				<tr class="tr2">
    				<td align="center">1</td>
    				<td align="center"><%=usuarios.getCodigoPersonal()%></td>
    				<td>  					
	  					<%=usuarios.getNombre()%>&nbsp;<%=usuarios.getApellidoPaterno()%>&nbsp;<%=usuarios.getApellidoMaterno()%>
	  				</td>
	  				<td><a class="btn btn-success btn-sm" href="grabarMentor?empleado=<%=usuarios.getCodigoPersonal() %>&carreraId=<%=carrera%>"><i class="icon-white icon-plus"></i></a></td>
  				</tr>
			<%
				if(codigoPersonal.substring(0,1).equals("9")){
					session.setAttribute("codigoEmpleado", codigoPersonal);
					session.setAttribute("codigoUltimo", codigoPersonal);
				}
				else{
					session.setAttribute("codigoAlumno", codigoPersonal);
					session.setAttribute("colorPortal", color);
					session.setAttribute("codigoUltimo", codigoPersonal);
				}
				sResultado = "Registered in your session: "+codigoPersonal;
				if(!origen.equals("X")){
					if(request.getParameter("body")!=null){%>
						<script lenguaje="javascript">
							document.location.href='../../<%=origen%>&carpeta=<%=sCarpeta%>';
		                </script>
				<%	}
					else{
						response.sendRedirect("../../"+origen+"&carpeta="+sCarpeta);
					}
				}
			break;
			}
		}
	%>
	</table>
</div>
</body>
<%	// borra la lista
	lisUsuarios = null;	
%>