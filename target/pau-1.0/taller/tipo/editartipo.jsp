<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.financiero.spring.ContEjercicio" %>
<%@ page import="aca.financiero.spring.ContCcosto" %>
<%@ page import="aca.bec.spring.BecFija" %>
<%@ page import="aca.bec.spring.BecTipo" %>
<%@ page import="aca.bec.spring.BecPlazas" %>
<%@ page import="aca.catalogo.spring.CatTipoAlumno" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.* "%>

<jsp:useBean id="BecAccesoU" scope="page" class="aca.bec.BecAccesoUtil"/>
<jsp:useBean id="ContCcostoU" scope="page" class="aca.financiero.ContCcostoUtil"/>
<jsp:useBean id="TipoAlumnoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="BecPlazasU" scope="page" class="aca.bec.BecPlazasUtil"/>
<jsp:useBean id="BecaTipo" scope="page" class="aca.bec.BecTipo"/>
<jsp:useBean id="BecaTipoU" scope="page" class="aca.bec.BecTipoUtil"/>
<jsp:useBean id="AlumAca" scope="page" class="aca.alumno.AlumAcademico"/>
<script>

		function elegirTodos(){
			var elegir = jQuery("#elegir");
			if (elegir.html()=="Todos"){
				jQuery(".tipoalum").attr("checked",true);
				elegir.html("Ninguno");
			}else{				
				jQuery(".tipoalum").attr("checked",false);
				elegir.html("Todos");
			}	
		}

		function modificarTipo(){			
			document.frmTipoAlumno.submit();			
		}
		
</script>
<%	
	String ejercicioId		= request.getParameter("EjercicioId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String tipo 			= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
	String opcion			= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");	
	BecTipo becaTipo 		= (BecTipo)request.getAttribute("becTipo");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	int cont 				= 1;
	
	List <CatTipoAlumno> lisTipos					= (List <CatTipoAlumno>)request.getAttribute("lisTipos");
	HashMap<String, String> mapaAccesos 			= (HashMap<String, String>)request.getAttribute("mapaAccesos");
%>
<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
		body{
		background:white;
	}
	</style>
</head>	
<body>
<div class="container-fluid">
	<h2>Tipo de alumnos <small class="text-muted fs-4">( <%=becaTipo.getNombre()%> )</small></h2>
	<div class="alert alert-info">
		<a href="becas?ejercicioId=<%=ejercicioId%>" class="btn btn-primary" align="center"><i class="fas fa-arrow-left"></i></a>			
		<a class="btn btn-success" onclick="javascript:modificarTipo();" ><i class="fas fa-check"></i> Guardar tipo de alumnos</a>			
		<a class="btn btn-primary" href="editartipo?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipo%>&Opcion=<%=opcion.equals("0")?"1":"0"%>">
	  		<%=opcion.equals("0")?"Mostrar Todos":"Mostrar elegidos"%>
		</a>
	</div>	
	<br>
	<form name="frmTipoAlumno" action="grabartipo" method="post">
	<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
	<input type="hidden" name="Tipo" value="<%=tipo%>">	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-info" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>					
			<th width="15%">Tipo Id</th>
			<th width="50%"><spring:message code="aca.Nombre"/></th>
		</tr>
	</thead>
	<tbody>						
<% 
	for(CatTipoAlumno tipoAlumno : lisTipos){
 		if(!becaTipo.getTipoAlumno().contains(tipoAlumno.getTipoId()) && opcion.equals("0") ){
 			continue;
 		}		
%>
		<tr>					
			<td style="text-align:center;"><input type="checkbox" class="tipoalum" id="<%=tipoAlumno.getNombreTipo() %>" name="<%=tipoAlumno.getTipoId()%>" value="S" <%if(becaTipo.getTipoAlumno().contains(tipoAlumno.getTipoId())){%>checked<%} %> /></td>					
			<td align="center"><%=tipoAlumno.getTipoId() %></td>
			<td align="center"><%=tipoAlumno.getNombreTipo()%></td>
		</tr>				
<% 	} %>
	</tbody>
	</table>
	</form>
</div>
</body>
</html>