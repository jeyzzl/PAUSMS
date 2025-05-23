<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumFamilia"%>
<%@page import="aca.alumno.spring.AlumHermanos"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "menu.jsp" %>

<html>
<head>
	<script>		
		function autorizar(){
			new Ajax.Request("hermanosAccion?Accion=4", {
				method: "get",
				onSuccess: function(req){
					if(req.responseText.indexOf("Error")!=-1) mensaje("<font size='2' color='#AE2113'>Ocurrió un error al autorizar,<br>actualiza la página e inténtalo de nuevo.</font>");
					else document.location.href=document.location;
				},
				onFailure: function(req){
					alert("Ocurri&oacute; un error al autorizar");
				}
			});
		}		
	</script>
</head>
<%
	String matricula 			= session.getAttribute("codigoAlumno").toString();
	AlumFamilia familia 		= (AlumFamilia)request.getAttribute("familia");	
	String apellidos 			= (String)request.getAttribute("apellidos");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<AlumHermanos> listaHermanos 			= (List<AlumHermanos>) request.getAttribute("lista");
	HashMap<String,AlumPersonal> mapaFamilia 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaFamilia");
	
	if(session.getAttribute("codigoPersonal").equals(matricula)){
%>
<body>
<div class="container-fluid">
	<h4><a href="resumen"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;Familia <%=apellidos%> <small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%>)</small></h4>
	<hr>
	<form name="frmHermano" action="grabarHermano" method="post">
	<input type="hidden" name="Apellidos" value="<%=apellidos%>"/>
	<input type="hidden" name="CodigoAlumno" value="<%=matricula%>"/>
	<div class="alert alert-info d-flex align-items-center">
		Hermano:&nbsp;<input id="CodigoNuevo" name="CodigoNuevo" class="form-control" type="text" maxlength="7" placeholder="Matrícula" style="width:100px">&nbsp;
		<a href="javascript:GrabarHermano()" class="btn btn-primary"><i class="icon-plus-sign"></i> Agregar</a>
	</div>
	</form>
<%		if (!mensaje.equals("-")){ %>
	<div class="alert alert-success"><%=mensaje%></div>
<%		
		}
		if(listaHermanos.size()>=1){%>
	<table style="width:50%" class="table table-fullcondensed">
		<tr>
			<th width="10%"><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th width="30%"><spring:message code="aca.Estado"/></th>
		</tr>
<%			
			for(AlumHermanos alumHermano : listaHermanos){					
				String estado = "";
				if(alumHermano.getEstado().equals("E")) estado = "<font color='#AE2113'><b>Pendiente</b></font>";
				else if(alumHermano.getEstado().equals("A")) estado = "<font color='green'><b>Aceptó</b></font>";
				else if(alumHermano.getEstado().equals("F")) estado = "<font color='green'><b>Confirmado Finanzas</b></font>";
				
				String nombreHermano = "";
				if (mapaFamilia.containsKey(alumHermano.getCodigoPersonal())){
					AlumPersonal alumno = mapaFamilia.get(alumHermano.getCodigoPersonal());
					nombreHermano 		= alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
				}
%>
		<tr>
			<td class="mat" style="text-align:center;">
<%
				if(familia.getEstado().equals("I") && alumHermano.getEstado().equals("E")){
%>
			<a href="javascript:noAutorizar('<%=alumHermano.getCodigoPersonal()%>','<%=matricula%>');" class="fas fa-trash-alt" title="Eliminar"></a>
<%				}%>
					<%=alumHermano.getCodigoPersonal()%>
			</td>
			<td><%=nombreHermano%></td>
			<td>
<%				if(matricula.equals(alumHermano.getCodigoPersonal())){
					if(alumHermano.getEstado().equals("E")){%>
					Pendiente&nbsp;&nbsp;
					<a href="autorizarHermano?FamiliaId=<%=alumHermano.getFamiliaId()%>&CodigoAlumno=<%=alumHermano.getCodigoPersonal()%>" class="btn btn-primary"><i class="fas fa-check-square" ></i></a>
<%					}else if(alumHermano.getEstado().equals("A")){%>
					<font color='green'><b>Aceptó</b></font>&nbsp;&nbsp;
<% 						if (!familia.getEstado().equals("F")){%>					
					<a href="javascript:BorrarHermano('<%=alumHermano.getFamiliaId()%>','<%=alumHermano.getCodigoPersonal()%>')" class="btn btn-primary"><i class="fas fa-trash" ></i></a>					
<%						
						}
					}
				}else{%>
					<%=estado%>
<%				}%>
			</td>
		</tr>
<%			}	%>
	</table>
<%		}%>
<%	}%>
</div>
</body>
</html>
<script type="text/javascript">	
	function GrabarHermano(){
		document.frmHermano.submit();
	}
	
	function BorrarHermano(familiaId, codigoAlumno){
		if (confirm("¿Estás seguro de borrar tu registro?")){
			document.location.href="borrarHermano?FamiliaId="+familiaId+"&CodigoAlumno="+codigoAlumno;
		}
		
	}	
</script>