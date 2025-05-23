<%@ page import= "java.util.*"%>
<%@ page import= "aca.ssoc.spring.SsocDocumentos"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.ssoc.spring.SsocAsignacion"%>
<%@ page import= "aca.ssoc.spring.SsocDocAlum"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	String matricula 			= (String)session.getAttribute("codigoAlumno");
	String alumnoNombre			= (String)request.getAttribute("alumnoNombre");
	SsocDocAlum ssocDocAlum		= (SsocDocAlum)request.getAttribute("ssocDocAlum");
	String mensaje				= (String)request.getAttribute("mensaje");
	String carreraId			= (String)request.getAttribute("carreraId");
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
	
	int folio					= request.getParameter("Folio")==null?0:Integer.parseInt(request.getParameter("Folio"));
	String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	
	List<SsocDocumentos> lisRequisitos 				= (List<SsocDocumentos>)request.getAttribute("lisRequisitos");
	List<SsocAsignacion> lisAsignaciones			= (List<SsocAsignacion>)request.getAttribute("lisAsignaciones");	
	
	String nivelAcceso = "N";
	if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
		nivelAcceso = "A";
	}else if (acceso.getAccesos().contains(carreraId)){
		nivelAcceso = "C";
	}	
%>
<body>
<div class="container-fluid">
	<h2>Editar Documentos<small class="text-muted fs-4">( <%=matricula%> - <%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="social?PlanId=<%=planId%>" class="btn btn-primary">Regresar</a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Grabado
	</div>
<% }%>
	<form name="frmDocumento" action="grabarDocumento" method="post">
		<input type="hidden" name="PlanId" value="<%=planId%>"/>
		<input type="hidden" name="Folio" value="<%=folio%>"/>
		<div class="row container-fluid">
			<div class="span5">
				<label for="Entregado">Entregado:</label>
				<input type='checkbox' name="Entregado" value="S" <%if(ssocDocAlum.getEntregado().equals("S")) out.print("checked");%>/>
				<br><br>
				<label for="DocumentoId">Documento:</label>	
				<select name="DocumentoId" class="form-select" style="width:377px;">
<%		
			for(SsocDocumentos documento : lisRequisitos){
				if(nivelAcceso.equals("A")||nivelAcceso.equals(documento.getAcceso())){
%>					<option value='<%=documento.getDocumentoId()%>' <%if(ssocDocAlum.getDocumentoId()==Integer.parseInt(documento.getDocumentoId()))out.print("Selected");%>><%=documento.getDocumentoNombre()%></option>
<%				}
			}	%>
			  	</select>
				<br>
				<label for="AsignacionId">Asignacion:</label>			
				<select name="AsignacionId" class="form-select" style="width:377px;">
					<option value='0'></option>
<%			for(SsocAsignacion asignacion : lisAsignaciones){ %>					
					<option value='<%=asignacion.getAsignacionId()%>' <%if(ssocDocAlum.getAsignacionId()==Integer.parseInt(asignacion.getAsignacionId()))out.print("Selected");%>><%=asignacion.getDependencia()%></option>
<%				}	%>
			  	</select>
				<br>
				<label for="Fecha"><spring:message code="aca.Fecha"/> (DD/MM/AAAA): </label>			
				<input id="Fecha" name="Fecha" type="text" class="form-control" value="<%=ssocDocAlum.getFecha()%>" size="10" data-date-format="dd-mm-yyyy" style="width:120px;"/> 
				<br>
				<label for="NumHoras">Horas:</label>
				<input name="NumHoras" type="text" class="form-control" size='5' value='<%=ssocDocAlum.getNumHoras()%>' style="width:120px;"/>
				<br>
				<label for="Comentario">Comentario:</label>					
				<input name="Comentario" type="text" class="form-control" size="49" value="<%if(ssocDocAlum.getComentario()==null)out.print("");else out.print(ssocDocAlum.getComentario());%>" style="width:377px;"/>		  	
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<a href='javascript:Grabar()' class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
		</div>	
	</form>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>
<script type="text/javascript">	
	function Grabar() {		
		if(document.frmDocumento.DocumentoId.value != "" && document.frmDocumento.NumHoras.value != "" && document.frmDocumento.Comentario.value != "" && document.frmDocumento.Fecha.value != ""){			
			document.frmDocumento.submit();
		}else{
			alert("Llene todos los campos");
		}
	}
</script>
</body>