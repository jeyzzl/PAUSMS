<%@ page import= "java.util.List"%>
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
	String carreraId		= (String)request.getAttribute("carreraId");
	String nombreCarrera 	= (String)request.getAttribute("nombreCarrera");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String convalidacionId	= request.getParameter("convalidacionId");
	String ciclosParam    	= request.getParameter("CicloParam")==null?"-":request.getParameter("CicloParam");
	
	List<MapaCurso> lisMaterias	= (List<MapaCurso>)request.getAttribute("lisMaterias");
	String planActivo			= (String)request.getAttribute("planActivo");
	int cicloTem				= 0;
	int ciclo					= 0;
%>
<script>
	function grabar(){
		document.frmimportcalif.Accion.value = 1;
		document.frmimportcalif.submit();
	}
</script>
<html>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body>
<div class="container-fluid">
	<h3>Por ciclo <small class="text-muted fs-6">[<%=codigoAlumno%>] [<%=nombreAlumno%>] -- [<%=planActivo%>] [<%=nombreCarrera%>]</small></h3>
	<div class="alert alert-info">
		<a href="solicitud" class="btn btn-primary">regresar</a>
	</div>
	<div class="form-group">
		<h4>¡ Recuerda !</h4>		
	    * Seleccionar el ciclo para el cu&aacute;l deseas iniciar el tramite.
	    <br>
	    * Presionar el botón <strong>Grabar,</strong> que aparece en la parte inferior de la pantalla, para registrar la solicitud de convalidación.
	    <br>
	    * Regresar a la pantalla anterior y presionar el botón de <strong>Confirmar</strong> para enviar la solicitud.
	</div>
	
	<form name="frmimportcalif" method="post" action="accionCiclo">
		<input name="Accion" type="hidden" value="">
		<input name="PlanId" type="hidden" value="<%=planId%>">
		<input name="convalidacionId" type="hidden" value="<%=convalidacionId%>">
<% 
			for(MapaCurso curso : lisMaterias){
				ciclo	= Integer.parseInt(curso.getCiclo());
				if(ciclo != cicloTem){
					cicloTem = ciclo;
					ciclosParam += ciclo+"-";
%>
			<div class="alert alert-info" role="alert">
				<h5><input name="ClicoId<%=ciclo%>" type="checkbox"  value="<%=ciclo%>"> Semestre <%=ciclo%></h5>
			</div>
			<div>
				<h4>Clave&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Materia</h4>
			</div>	<br>		
<%
				}
%>  			
			<div>
				<%=curso.getCursoClave()%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <%=curso.getNombreCurso()%>
			</div><br>
<% 

			}
				
				lisMaterias		= null;
%>
			<input class="btn btn-primary" type="button" name="Submit" value="Grabar" onClick="grabar()">
			<input name="planId" type="hidden" id="planId" value="<%=planId%>">
			<input name="CicloParam" type="hidden" value="<%=ciclosParam%>">
		</form>
	</div>
</body>
</html>