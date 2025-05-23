<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.internado.spring.IntAlumReporte"%>
<%@ page import= "aca.internado.spring.IntReporte"%>

<script type="text/javascript">	
	function Graba(){
		document.frmReporte.Accion.value="1";
		document.frmReporte.submit();
	}	
	function Modifica(){		
		document.frmReporte.Accion.value="2";
		document.frmReporte.submit();
	}
</script>
<%	
	String codigoUsuario		= (String) request.getAttribute("codigoUsuario");
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	boolean existeAlumno 		= (boolean) request.getAttribute("existeAlumno");
	String dormitorioId 		= (String) request.getAttribute("dormitorioId");
	boolean sAdmin				= (boolean) request.getAttribute("esAdmin");
	boolean esPreceptor			= (boolean) request.getAttribute("esPreceptor");

	String folio 				= (String) request.getAttribute("folio");	
	String accion				= (String) request.getAttribute("accion");
	String fecha				= (String) request.getAttribute("fecha");	
	String mensaje 				= (String) request.getAttribute("mensaje");	

	List<IntReporte> lisReportes = (List<IntReporte>) request.getAttribute("lisReportes");

	IntAlumReporte alumReporte = (IntAlumReporte) request.getAttribute("reporte");
%>

<%@ include file="portal.jsp" %>

<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />	
</head>
<div class="container-fluid">
	<h3>Student Reports <small class="text-muted fs-4">( <%=codigoAlumno %> - <%= nombreAlumno%> )</small> </h3>
	<div class="alert alert-info">
		<a href="disciplina" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp; &nbsp;
<%
	if(sAdmin || esPreceptor){ out.print("&nbsp;<a href='../../internado/dormitorios/dormitorios.jsp' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp; &nbsp;"); }
%>	
	</div>	
	<div class="row container-fluid">
		<div class="span2 col">	
			<form id="frmReporte" name="frmReporte" action="disciplina_alta" method="post">
			<input name="Accion" type="hidden"/>
			<input name="CodigoAlumno" class="form-control" type="hidden" value="<%=codigoAlumno%>"/>
			<input name="Folio" type="hidden"  class="form-control" value="<%=folio%>"/>						
			<label for="Fecha"><spring:message code="aca.Fecha"/></label>
			   <input id="Fecha" name="Fecha" class="form-control" required type="text" data-date-format="dd/mm/yyyy" value="<%=fecha%>" maxlength="10"/>	  
		    <label for="Reporte">Report</label>
		    <select name="ReporteId"  id="ReporteId" class="form-select" class="chosen">
<%		for(IntReporte reporte : lisReportes){ %>
				<option value="<%=reporte.getReporteId() %>" <% if(reporte.getReporteId().equals(alumReporte.getReporteId() ))out.print("selected"); %>><%=reporte.getReporteNombre() %></option>
<%		} %>
			</select>					
			<label for="Cantidad"><spring:message code='aca.Cantidad'/></label>
			<input id="Cantidad" name="Cantidad" class="form-control" maxlength="1" required type="text" value="<%=alumReporte.getCantidad()%>"/>			
		</div>
		<div class="span2 col">
			<div class="control-group ">		
				<label for="Comentario"><spring:message code="aca.Comentario"/></label>
				<textarea name="Comentario" id="Comentario" class="form-control"  style="height:160px"required cols="100" rows="3" maxlength="900"><%=alumReporte.getComentario()==null?"":alumReporte.getComentario() %></textarea>
			</div>		
		</div>
	</div>
	<br>
	<div class="alert alert-info">	
<% 		if (existeAlumno){ %>	
			<a href="javascript:Graba()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>&nbsp; &nbsp;
<%		} %>	
		</div>	
	</form>
	<%=mensaje%>
</div>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('.disciplina').addClass('active');
	jQuery('#Fecha').datepicker();
</script>