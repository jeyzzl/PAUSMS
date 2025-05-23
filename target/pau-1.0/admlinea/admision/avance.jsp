<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<html>
<head></head>
<%
	String codigoPersonal			= (String)session.getAttribute("codigoPersonal");
	String folio 					= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	AdmSolicitud admSolicitud 		= (AdmSolicitud)request.getAttribute("admSolicitud");
	Acceso acceso					= (Acceso) request.getAttribute("acceso");
	String siguienteMatricula 		= (String)request.getAttribute("siguienteMatricula");
	String alumnoNombre 			= (String)request.getAttribute("alumnoNombre");	
	boolean existePersonal	 		= (boolean)request.getAttribute("existePersonal");
	boolean existeUbicacion	 		= (boolean)request.getAttribute("existeUbicacion");
	boolean existeAcademico	 		= (boolean)request.getAttribute("existeAcademico");
	boolean existeBanco	 			= (boolean)request.getAttribute("existeBanco");
	if (existePersonal) siguienteMatricula = admSolicitud.getMatricula();
	List<AlumPersonal> lisAlumnoDuplicados 		= (List<AlumPersonal>)request.getAttribute("lisAlumnoDuplicados");
	HashMap<String, AlumPersonal> getMapProced 	= (HashMap<String, AlumPersonal>)request.getAttribute("getMapProced");	
	
	String nombreSolicitud = admSolicitud.getNombre()+" "+admSolicitud.getApellidoMaterno()+" "+admSolicitud.getApellidoPaterno();
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.ProcesoDeTraspaso"/> <small class="text-muted fs-6">( <b> <%=folio %> - <%=admSolicitud.getNombre()+" "+ (admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno() %></b>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp;
<%	if(!mensaje.equals("-")) out.print(mensaje);%>
	</div>
	<form name="frmSincronizar" action="sincronizar" method="post">
	<input name="Folio" type="hidden" value="<%=folio%>"/>
	<input name="Accion" type="hidden"/>
	<table class="table table-sm" style="width:100%">			
	<tr>
		<td align="center">
	<%	if(codigoPersonal.equals("9800308") || acceso.getAdministrador().equals("S")){ %>
			<b>Assigned <spring:message code="aca.Matricula"/>:</b>&nbsp;
			<input id="Matricula" name="Matricula" onkeyup="javascript:revisarMatricula();" onkeydown="revisarLongitud();" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) event.returnValue = false;" 
			type="text" value="<%=siguienteMatricula%>" style="width:110px;">&nbsp;	
			<span id="ComentarioMatricula"><%=existePersonal?alumnoNombre:"<br>Click on <b><i>Transfer</i></b> to save the student in the System"%></span>
	<%	}%>
		</td>
	</tr>
	</table>
	</form>
	<table class="table table-sm table-bordered" align="center" style="width:40%">
		<tr>
			<th colspan="2" class="text-center"><spring:message code="aca.DatosDelAlumno"/></th>
		</tr>
		<tr>
			<td><small class="text-muted fs-7">Transferred</small></td>
			<th></th>
		</tr>
		<tr>
			<td class="text-center" width="10%"><%=existePersonal?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
			<td>Personal Details</td>
		</tr>
		<tr>
			<td class="text-center"><%=existeUbicacion?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
			<td>Parental Information</td>
		</tr>		
		<tr>
			<td class="text-center"><%=existeAcademico?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
			<td>Educational Background</td>
		</tr>
		<tr>
			<td class="text-center"><%=existeBanco?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-warning rounded-pill'>NO</span>"%></td>
			<td>Banking Information</td>
		</tr>
	</table>
	<table style="margin: 0 auto;">
		<tr>
			<td>
				<div id='resultado' style="font-size:24px; color:green; text-align:center; background-color:transparent; border-width:0; font-family:Helvetica;"></div>
			</td>
		</tr>
	</table>
<%	// Usuarios que pueden autorizar el traspaso de datos al virtual
	if(String.valueOf(session.getAttribute("codigoPersonal")).equals("9800308") || acceso.getAdministrador().equals("S")){ %>
	<table style="margin: 0 auto;" id="btn">
	 <tr>
	   	<td colspan="3" align="center">
<%		if (existePersonal==false){%>	    			
	   		<input type="button" class="btn btn-primary" value="Transfer" onclick="javascript:Sincronizar('<%=folio%>','<%=nombreSolicitud%>');">
<%		}else{
			out.print("Student already registered!");
		} 
%>
		</td>
	</tr>
	</table>
	<br>
	<table class="table table-sm table-bordered" align="center" style="width:50%">
		<tr>
			<td colspan="3" align="center">
				<strong><spring:message code="aca.AlumnosInscritos"/> with similar name</strong> (Check for duplicates)
			</td>
		</tr>
		<tr>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>			
		</tr>
<%		for(AlumPersonal alumno : lisAlumnoDuplicados){%>
		<tr>
			<td align="center"><%=alumno.getCodigoPersonal()%></td>
			<td><a href="javascript:asignar('<%=folio%>','<%=alumno.getCodigoPersonal()%>');"><b><%=alumno.getNombre()%>&nbsp;<%=alumno.getApellidoPaterno()%>&nbsp;<%=alumno.getApellidoMaterno()%></b></a></td>			
		</tr>
<%		} %>
	</table>
<% 	} %>
</div>	
</body>
<script type="text/javascript">

	function Sincronizar(folio, nombre){				
		if(confirm("Do you want to transfer this student's data ("+folio+"-"+nombre+")?")){
			document.frmSincronizar.Accion.value = "1";
			document.frmSincronizar.submit();
		}
	}
	
	function revisarMatricula(){		
		var mat = jQuery("#Matricula").val();		
		if(mat.length==7){									
			jQuery.get("verificaMatricula?Matricula="+mat,function(r){			
				$("#ComentarioMatricula").html(jQuery.trim(r));
			});			
		}		
	}
	
	function revisarLongitud(){
		var c = event.keyCode;
		if(c!=35 && c!=36 && c!=37 && c!=39 && c!=8 && c!=46 && $("Matricula").selectionStart==7
			&& $("Matricula").value.length>=7){
			event.returnValue=false;
		}
		else{
			event.returnValue=true;
		}
	}
	
	function asignar(folio,matricula){
		if(confirm("Do you want to assigne the ID: "+matricula+" to this student?")){
			document.location.href="avanceAsignar?Folio="+folio+"&Matricula="+matricula;
		}
	}
</script>	
</html>