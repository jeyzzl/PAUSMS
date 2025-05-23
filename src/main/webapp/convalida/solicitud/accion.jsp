<%@ page import= "java.util.List"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.conva.spring.ConvMateria"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript"> src ="../../validacion.js"> </script>
<%
//variables
	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	String planId			= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	String nombreCarrera 	= (String)request.getAttribute("nombreCarrera");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String convalidacionId	= request.getParameter("convalidacionId")==null?"0":request.getParameter("convalidacionId");
	String planActivo		= (String)request.getAttribute("planActivo");
	
	List<MapaCurso> lisMaterias	= (List<MapaCurso>)request.getAttribute("lisMaterias");
	int cicloTem				= 0;
	int ciclo					= 0;	
%>
<script>
	function grabar(){
		document.frmExterna.Accion.value = 1;
		document.frmExterna.submit();
	}
</script>
<html>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<div class="container-fluid">
  	<h2>Por Materia <small class="text-muted fs-6">[<%=codigoAlumno%>] [<%=nombreAlumno%>] -- [<%=planActivo%>] [<%=nombreCarrera%>]</small></h2>
	<div class="alert alert-info">
		<a href="solicitud" class="btn btn-primary">regresar</a>
	</div>
	<div class="form-group">
		<h4>Instrucciones:</h4>		
	    1. Seleccionar las materias para las cu&aacute;les deseas iniciar el tramite.
	    <br>
	    2. Presionar el botón <strong>Grabar,</strong> que aparece en la parte inferior de la pantalla, para registrar la solicitud de convalidación.
	    <br>
	    3. Regresar a la pantalla anterior y presionar el botón de <strong>Confirmar</strong> para enviar la solicitud.
	</div>
	<form name="frmExterna" method="post" action="accion">
	<input name="Accion" type="hidden" value="">
	<input name="PlanId" type="hidden" value="<%=planId%>">
	<input name="convalidacionId" type="hidden" value="<%=convalidacionId%>">
	<table class="table table-sm">
<% 
	int row = 0;
	for(MapaCurso curso : lisMaterias){
		ciclo	= Integer.parseInt(curso.getCiclo());
		row++;
		if(ciclo != cicloTem){
			cicloTem = ciclo;			
%>
	  	<tr>
	    	<td colspan="12"><strong>Semestre <%=ciclo%></strong></td>
	  	</tr>
	  	<tr class="table-info">
	    	<th width="4%" height="16"><span class="Estilo5">Aplicar</span></th>
	    	<th width="7%"><span class="Estilo5"><spring:message code="aca.Clave"/></th>
	    	<th width="30%"><span class="Estilo5"><spring:message code="aca.Materia"/></span></th>
	  	</tr>
<%		} %>  
		<tr>
		  	<td align="center"><input name="check-<%=curso.getCursoId()%>" id="check-<%=curso.getCursoId()%>" type="checkbox" value="SI"></td>
		 	<td><%=curso.getCursoClave()%></td>    
		  	<td><%=curso.getNombreCurso()%><input name="curso-<%=curso.getCursoId()%>" type="hidden" id="curso-<%=curso.getCursoId()%>" value="<%=curso.getCursoId()%>"></td>    
		</tr>
<% 	} %>
		<tr align="center">
		   	<td colspan="12"><input class="btn btn-primary" type="button" name="Submit" value="Grabar" onClick="grabar()">
		   	<input name="planId" type="hidden" id="planId" value="<%=planId%>"></td>
		</tr>	
	</table>
	</form>
	</div>
</body>
</html>