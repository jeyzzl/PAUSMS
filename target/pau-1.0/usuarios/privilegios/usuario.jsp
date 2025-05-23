<%@ page import="java.util.*"%>
<%@ page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.rol.spring.Rol"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
<script type="text/javascript">
	function Grabar() {
		frmusuario.submit();
	}
	function Eliminar() {
		if (confirm("¿Are you sure to remove all user privileges??")) {
			document.location.href = "eliminar";
		}
	}
	function grabaRol(){		
  		document.formas.submit();
	}	
</script>
<script type="text/javascript" src="../../js/qrTag.js"></script>
</head>
<%
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String codigo 			= (String) session.getAttribute("codigoUltimo");
	String mensaje 			= (String) request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String rol				= request.getParameter("rol")==null?"0":request.getParameter("rol");

	Acceso acceso 									= (Acceso) request.getAttribute("acceso");
	String usuarioNombre							= (String) request.getAttribute("usuarioNombre");
	
	List<aca.menu.spring.Modulo> lisModulos 		= (List<aca.menu.spring.Modulo>) request.getAttribute("lisModulos");	 
	List<aca.menu.spring.ModuloOpcion> lisOpciones	= (List<aca.menu.spring.ModuloOpcion>) request.getAttribute("lisOpciones");
	List<aca.rol.spring.Rol> lisRoles 				= (List<aca.rol.spring.Rol>) request.getAttribute("lisRoles");
	List<MapaPlan> lisPlanes						= (List<MapaPlan>)request.getAttribute("lisPlanes");
	HashMap<String,String> mapaMenus 				= (HashMap<String,String>) request.getAttribute("mapaMenus");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	String tipoClave 								= "-";
	if (!acceso.getClaveHexa().equals("X")) 
		tipoClave = "SHA-512 Hexa";
	else if (acceso.getClave().length()==88)
		tipoClave = "SHA-512 BASE64";
	else
		tipoClave = "MD5 BASE64";
		
%>
<div class="container-fluid">
	<h2>User privileges<small class="text-muted fs-5"> ( <%=codigo%> - <%=usuarioNombre%> ) </small></h2>
	<hr>
	<table id="noayuda" class="table table-sm table-nohover">	
	<tr>
		<td colspan="2" class="text-start">User: <strong><%=acceso.getUsuario()%>
		</strong>&nbsp; &nbsp; Password: <strong><%=tipoClave%></strong> &nbsp; &nbsp; 
		<a href="clave?Codigo=<%=codigo%>" class="btn btn-primary btn-sm">Edit</a>&nbsp; &nbsp;
<%	if (acceso.getAdministrador().equals("S") && codigoPersonal.equals("9800308")){%>		
<!-- 		<a href="grabarOpciones" class="btn btn-success btn-sm">GrabarOpciones</a> -->
<%	} %>		
		</td>
	</tr>
	<tr>
		<!--Primera columna de la tabla principal-->
		<td width="60%" valign="top">
			<table class = "table table-sm table-striped table-bordered" style="width:100%">
			<tr>
				<th width="24%" colspan="3" align="center">Data Privileges</th>
			</tr>
			<form name="frmusuario" method="post" action="grabarPrivilegios?Codigo=<%=codigo%>">
			<tr>
				<td width="24%" colspan="3" align="center">
				Admin <input type="checkbox" name="Admin" value="S" <%=acceso.getAdministrador().equals("S") ? "checked"	: " "%>>&nbsp;&nbsp;&nbsp; 
				Revision <input name="Coteja" type="checkbox" value="S" <%=acceso.getCotejador().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;&nbsp;
				Super <input name="Supervisor" type="checkbox" value="S" <%=acceso.getSupervisor().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;
				Convalidator <input name="Convalidador" type="checkbox" value="S" <%=acceso.getConvalida().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;
				Scholarships <input name="Becas" type="checkbox" value="S" <%=acceso.getBecas().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;
				Por.Stud. <input name="PortalAlumno" type="checkbox" value="S" <%=acceso.getPortalAlumno().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;
				Por.Empl. <input name="PortalMaestro" type="checkbox" value="S" <%=acceso.getPortalMaestro().equals("S") ? "checked" : " "%>>&nbsp;&nbsp;
				OnLine <input name="EnLinea" type="checkbox" value="S" <%=acceso.getEnLinea().equals("S") ? "checked":" "%>>&nbsp;&nbsp;
				Search <input name="Busca" type="checkbox" value="S" <%=acceso.getBuscaAdmin().equals("S") ? "checked":" "%>>&nbsp;&nbsp;
				Modality <input type="text" class="text" name="Modalidad" id="Modalidad" value="<%=acceso.getModalidad().equals("") ? "0" : acceso.getModalidad()%>"/>&nbsp;&nbsp;								
				<input name="Aceptar" type="submit" class="btn btn-primary btn-sm" id="Aceptar" value="Save" onClick="Grabar()">
				</td>
			</tr>
			</form>
			<form name="frmcambia" method="post" action="carreras?Codigo=<%=codigo%>">
			<tr>
				<td colspan="4">
					<input name="Edit" class="btn btn-primary btn-sm" type="submit" value="Edit">&nbsp;<%=mensaje%>					
				</td>
			</tr>
			</form>	
<%	
	int row=0;
	String facTemp="X";
	for (MapaPlan mapaPlan : lisPlanes) {		
		row++;
	
		String facultadId		= "X";
		String facultadNombre 	= "-";
		if (mapaCarreras.containsKey(mapaPlan.getCarreraId())){
			facultadId = mapaCarreras.get(mapaPlan.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadId)){
				facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
			}
		}			
		String estilo = "<span class='badge bg-secondary rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("A")) estilo = "<span class='badge bg-info rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("V")) estilo = "<span class='badge bg-success rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (mapaPlan.getEstado().equals("I")) estilo = "<span class='badge bg-warning rounded-pill'>"+mapaPlan.getPlanId()+"</span>";
		if (!facTemp.equals(facultadId)){
			facTemp = facultadId;
			out.print("<tr class='table-dark'><td colspan='4'>"+facultadNombre+"</td></tr>");
		}
%>
		<tr>
			<td class="text-center">
				<%=row%>
			</td>				
			<td class="text-center">											
				<span style="text-decoration:none;"><%=estilo%></span>				
			</td>
			<td class="text-start">
				<%=mapaPlan.getCarreraSe()%>
			</td>
		</tr>
		<%} %>
		</table>
		</td>
			<!--Segunda columna de la tabla principal-->
		<td valign="top">			
			<table class="table table-striped table-sm table-bordered" style="width:100%">
			<form name="formas" method="post" action="grabarRole?Codigo=<%=codigo%>" class="d-flex align-items-center">			
			<tr>
				<th colspan="2">
					<select name="rol" style="width:350px;" class="form-select">
					<%	for(aca.rol.spring.Rol roles : lisRoles){%>
						<option <%if(rol.equals(roles.getRolId()))out.print(" Selected ");%> value="<%=roles.getRolId()%>"><%=roles.getRolNombre()%></option>
					<%	}%>
					</select>				
				</th>		
				<th><a href="javascript:grabaRol()" class="btn btn-info btn-sm">Add</a></th>
			</tr>
			</form>
			<tr>
				<th colspan="2" align="center">Application privileges&nbsp;&nbsp;
					<a href="aplicacion" class="btn btn-primary btn-sm">Edit</a>&nbsp;					
					<a href="copiar" class="btn btn-primary btn-sm">Copy</a>&nbsp;
					<a class="btn btn-danger btn-sm" href="javascript:Eliminar()">Remove</a>
				</th>
			</tr>
<%
		String menuTemp = "";
		int cont = 0;
		for (int i = 0; i < lisModulos.size(); i++) {
			aca.menu.spring.Modulo modulo = (aca.menu.spring.Modulo) lisModulos.get(i);
			if (!mapaMenus.get(modulo.getModuloId()).equals(menuTemp)) {
%>
			<tr>
				<td style="border: 2px dashed gray;" colspan="4"><strong><%=mapaMenus.get(modulo.getModuloId())%></strong></td>
			</tr>
<%
				menuTemp = mapaMenus.get(modulo.getModuloId());
			}
%>
			<tr>
				<td width="92%" colspan="1"><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<%=modulo.getNombreIngles()%></strong>
				</td>
			</tr>
<%
	for (aca.menu.spring.ModuloOpcion opcion : lisOpciones ){												
		if (opcion.getModuloId().equals(modulo.getModuloId())){
%>
			<tr>
				<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <%=opcion.getNombreIngles()%></td>
			</tr>
<%
				} //fin del if
			} //fin del for
		} //fin del for
%>
			</table>
		</td>
	</tr>
	</table>
</div>