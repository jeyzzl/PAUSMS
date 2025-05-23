<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<script type="text/javascript">
		function modificar(carga, bloque){
			document.location = "elige_bloque?CargaId="+carga+"&BloqueId="+bloque;
		}
		
		function cargaAlumno(){
			if(parseInt(document.form1.codigoPersonal.value,10) > 0){
				return true;
			}else{
				alert("Enter the student ID to view");
			}
			return false;
		}
	</script>
</head>
<%

	String codigoAlumno 		= (String)session.getAttribute("codigoAlumno");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");	
	String planId				= (String)request.getAttribute("planId");
	
	List<AlumEstado> lisEstados				= (List<AlumEstado>)request.getAttribute("lisEstados");
	HashMap<String, Carga> mapaCargas		= (HashMap<String,Carga>)request.getAttribute("mapaCargas");
	HashMap<String, CatTipoCal> mapaTipoCal = (HashMap<String, CatTipoCal>)request.getAttribute("mapaTipoCal");
	HashMap<String, String> mapaMaterias 	= (HashMap<String,String>)request.getAttribute("mapaMaterias");
	
	int nAccion = Integer.parseInt(accion);		
	String valor				= null;	
	
	switch (nAccion){
		case 0:{
			codigoAlumno = (String) session.getAttribute("codigoAlumno");			
			break;
		}
		case 1:{
			codigoAlumno = request.getParameter("codigoPersonal");
			session.setAttribute("codigoAlumno", codigoAlumno);
			break;
		}
	}	
%>
<div class="container-fluid">
	<h2>Student Status<small class="text-muted fs-5"> ( <%=codigoAlumno%> - <%=nombreAlumno%> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nuevoEstado">Add</a>	
	</div>
	<table style="width:75%" class="table table-sm table-bordered"> 
 	<tr class="table-info"> 
		<th width="10%" height="20"><b><spring:message code="aca.Operacion"/></b></th>
   		<th width="10%" height="20"><b><spring:message code="aca.Carga"/></b></th>
		<th width="45%" height="20"><b><spring:message code="aca.Nombre"/></b></th>
   		<th width="10%" height="20"><b><spring:message code="aca.Bloque"/></b></th>
   		<th width="10%" height="20" align="center"><b>Status</b></th>
   		<th width="10%" height="20" align="center"><b>Date</b></th>
 		<th width="5%" height="20" align="center"><b>Subjects</b></th>  		
 
 	</tr>
<%
	for(AlumEstado estado : lisEstados){
		
		String cargaNombre = "-";
		String numMaterias = "0";
		
		if (mapaCargas.containsKey(estado.getCargaId())){
			cargaNombre = mapaCargas.get(estado.getCargaId()).getNombreCarga();
		}
		
		if(mapaMaterias.containsKey(estado.getCargaId()+estado.getBloqueId())){
			numMaterias = mapaMaterias.get(estado.getCargaId()+estado.getBloqueId());
		}
		
		String estadoNombre = "-";
		String estadoColor = "bg-primary";
		if(mapaTipoCal.containsKey(estado.getEstado())){
			estadoNombre = mapaTipoCal.get(estado.getEstado()).getNombreTipoCal();
			if(estado.getEstado().equals("I")) { estadoColor = "bg-success"; }
		}
		
%>
  	<tr class="tr2">
  		<td style="text-align:center">
  	 		<a href="javascript:modificar('<%=estado.getCargaId()%>','<%=estado.getBloqueId()%>');")><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
<%
		if(estado.getEstado().equals("M")  && !numMaterias.equals("0")){		%>
  	 		<a href="inscribir?PlanId=<%=planId %>&CargaId=<%=estado.getCargaId() %>&Bloque=<%=estado.getBloqueId() %>" class="btn btn-sm btn-primary">Enroll</a>
<%		}else if(estado.getEstado().equals("I")){		%>
			<a href="asignar?PlanId=<%=planId %>&CargaId=<%=estado.getCargaId() %>&Bloque=<%=estado.getBloqueId() %>" class="btn btn-sm btn-warning">Assign</a>	
<%		}	 %>
  		</td> 
    	<td><%=estado.getCargaId()%></td>
		<td><%=cargaNombre%></td>
    	<td align="center"><span class="badge rounded-pill bg-black"><%=estado.getBloqueId()%></span></td>
    	<td align="center"><span class="badge rounded-pill <%=estadoColor%>"><%=estadoNombre%></span></td>
    	<td align="center"><%=estado.getFecha()%></td>
    	<td align="center"><span class="badge rounded-pill <%=numMaterias.equals("0")?"bg-warning":"bg-primary"%>"><%=numMaterias%></span></td>
  	</tr>		
<%	} %>
	</table>
</div>