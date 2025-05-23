<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Refrescar(){
		document.frmUsuarios.submit();
	}
	function grabarMaestros(){
		if (confirm("This operation generates the keys of the teachers, are you sure to continue?")){
			document.location.href="grabarMaestros";
		}		
	}		
	function grabarAlumnos(){
		if (confirm("This operation generates the keys of the students, are you sure to continue?")){
			document.location.href="grabarAlumnos";
		}		
	}
</script>
<%		
	String codigoPersonal	= (String)session.getAttribute("codigoPersonal");
	String tipo 			= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String admin 			= (String)request.getAttribute("admin");

	List<Maestros> lisMaestros 						= (List<Maestros>)request.getAttribute("lisMaestros");	
	List<AlumPersonal> lisAlumnos					= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	HashMap<String,Acceso> mapaAccesos 				= (HashMap<String,Acceso>)request.getAttribute("mapaAccesos");	
	HashMap<String,String> mapaOpciones				= (HashMap<String,String>)request.getAttribute("mapaOpciones");
	HashMap<String,String> mapaAlumPlan				= (HashMap<String,String>)request.getAttribute("mapaAlumPlan");
	HashMap<String,String> mapaFoto					= (HashMap<String,String>)request.getAttribute("mapaFoto");
	
%>
<div class="container-fluid">
	<h2>List of Users <small class="text-muted fs-5">( <%= tipo.equals("E")?"Employes":"Students"%> )</small></h2>
	<form name="frmUsuarios" action="usuarios" method="post">
	<div class="alert alert-info d-flex align-items-center">		
		<select name="Tipo" class="form-select" onchange="javascript:Refrescar();" style="width:140px;">
			<option value="E" <%=tipo.equals("E")?"selected":""%>>Employes</option>
			<option value="A" <%=tipo.equals("A")?"selected":""%>>Students</option>
		</select>&nbsp;&nbsp;<input type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" id="buscar" style="width:200px;">&nbsp;&nbsp;
<%	if (admin.equals("S") && tipo.equals("E")){%>		
		<a href="javascript:grabarMaestros()" class="btn btn-primary">Generate accounts</a>
<%	}else if (admin.equals("S") && tipo.equals("A")){%>		
		<a href="javascript:grabarAlumnos()" class="btn btn-primary">Generate accounts</a>
<%	} %>
<%-- <%	if(codigoPersonal.equals("9800308") && tipo.equals("A")){
%>
		<a href="restaurarContrasenas" class="mx-3 btn btn-primary">Reset student accounts</a>
<%	}%> --%>
	&nbsp;&nbsp;
	<%=mensaje.equals("-")?"":mensaje%>
	</div>	
	</form>	
	<table id="table" class="table table-bordered table-sm">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="7%">Code</th>
		<th width="35%">Name</th>
		<th width="3%">Exist?</th>
		<% if (admin.equals("S") && tipo.equals("A")){ %>
		<th width="7%">Plan</th>
		<th width="3%">Photo</th>
		<% } %>
		<th width="7%">Account</th>
		<% if (admin.equals("S") && tipo.equals("E")){ %>
		<th width="7%">Admin</th>
		<th width="7%">Super</th>
		<th width="9%">Portal Emp.</th>
		<th width="9%">Portal St.</th>
		<% } %>
		<th width="9%">Num. Access</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	if (tipo.equals("E")){		
		for (Maestros maestro : lisMaestros){
			row++;
			Acceso acceso = new Acceso();
			boolean existe = false;
			if (mapaAccesos.containsKey(maestro.getCodigoPersonal())){
				acceso = mapaAccesos.get(maestro.getCodigoPersonal());
				existe = true;
			}
		String totalMtro = "0";	
		String colorMat	= "<span class='badge bg-warning rounded-pill'>"+totalMtro+"</span>";
		if (mapaOpciones.containsKey(maestro.getCodigoPersonal()) ){
			totalMtro = mapaOpciones.get(maestro.getCodigoPersonal());
			colorMat	= "<span class='badge bg-dark rounded-pill'>"+totalMtro+"</span>";
		 
		 }
	
%>
	<tr>
		<td><%=row%></td>
		<td><%=maestro.getCodigoPersonal()%></td>
		<td><%=(maestro.getApellidoMaterno().equals("-")?"":maestro.getApellidoMaterno())+" "+maestro.getApellidoPaterno()+" "+maestro.getNombre()%></td>
		<td><%=existe?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
		<td><%=acceso.getUsuario()%></td>
		<td><%=acceso.getAdministrador().equals("S")?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>			
		<td><%=acceso.getSupervisor().equals("S")?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
		<td><%=acceso.getPortalMaestro().equals("S")?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
		<td><%=acceso.getPortalAlumno().equals("S")?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>	
		<td><%=colorMat%></td>	
	</tr>	
<%			
		}			
	}else{
		for (AlumPersonal alumno : lisAlumnos){
			row++;
			Acceso acceso = new Acceso();
			boolean existe = false;
			if (mapaAccesos.containsKey(alumno.getCodigoPersonal())){
				acceso = mapaAccesos.get(alumno.getCodigoPersonal());
				existe = true;
			}
			String totalAlum = "0";	
			String colorAlum	= "<span class='badge bg-warning rounded-pill'>"+totalAlum+"</span>";
			if (mapaOpciones.containsKey(alumno.getCodigoPersonal()) ){
				totalAlum = mapaOpciones.get(alumno.getCodigoPersonal());
				colorAlum	= "<span class='badge bg-dark rounded-pill'>"+totalAlum+"</span>";
			 }
			
			String plan = mapaAlumPlan.containsKey(alumno.getCodigoPersonal())?mapaAlumPlan.get(alumno.getCodigoPersonal()):"<span class='badge bg-warning rounded-pill'>NONE</span>";
			
			String foto = mapaFoto.containsKey(alumno.getCodigoPersonal())?mapaFoto.get(alumno.getCodigoPersonal()):"<span class='badge bg-warning rounded-pill'>NONE</span>";
			
%>
	<tr>
		<td><%=row%></td>
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=(alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno()+" "+alumno.getNombre()%></td>
		<td><%=existe?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
		<td><%=plan %></td>
		<td><%=foto %></td>
		<td><%=acceso.getUsuario()%></td>
		<td><%=colorAlum%></td>				
	</tr>	
<%	
		}
	}
%>
	</tbody>
	</table>
</div>	
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
<!-- fin de estructura -->