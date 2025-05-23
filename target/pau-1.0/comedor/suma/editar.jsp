<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumReceta"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>	
<% 	
	String fechaIni		=  request.getParameter("FechaIni")==null?aca.util.Fecha.getHoy():request.getParameter("FechaIni");
	String fechaFin		=  request.getParameter("FechaFin")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFin");
	String fecha		=  request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");
	String mensaje		=  request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");	
	
	ArrayList<SaumReceta> lisRecetas 		= (ArrayList<SaumReceta>)request.getAttribute("lisRecetas");
%>
<body>
<div class="container-fluid">
	<h2>Suma Recetas<small class="text-muted fs-4"> (<%=0%> )</small></h2>
	<div class="alert alert-info">
		<a href="receta?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>	
	</div>
<%
	if (!mensaje.equals("-")) out.print("<div class='alert alert-success'>"+mensaje+"</div>");
%>		
	<form id="frmSuma" name="frmSuma" method="post" action="grabar">
	<input type="hidden" name="Accion" value="<%=0%>"/>
	<input type="hidden" name="FechaIni" value="<%=fechaIni%>"/>
	<input type="hidden" name="FechaFin" value="<%=fechaFin%>"/>
	
	<div class="row">
		<div class="col">
			<fieldset>
				<label for="Fecha"><b>Fecha</b></label>
				<input type="text" class="input input-medium" data-date-format="dd/mm/yyyy" id="Fecha" name="Fecha" value="<%=fecha%>" required/>				
			</fieldset>
			<br/>
			<fieldset>
				<label for="Receta"><b>Receta</b></label>
				<select name="RecetaId" class="input input-xxlarge chosen" >
<% 			for (SaumReceta receta : lisRecetas){%>
					<option value="<%=receta.getId()%>"><%=receta.getNombre()%></option>
<% 			}%>									
				</select>			
			</fieldset>
			<br/>
			<fieldset>
				<label for="Rendimiento"><b>Rendimiento</b></label>
				<input type="text" class="input input-medium" name="Rendimiento" id="Rendimiento" value="<%=0%>" required/>
			</fieldset>			
			<br/>			
			<fieldset>
				<label for="Comida"><b>Tiempo</b></label>
				<select name="Comida" class="input input-medium">
					<option value="1">Desayuno</option>								
					<option value="2">Comida</option>
					<option value="3">Cena</option>
				</select>				
			</fieldset>
			<br/>
		</div>			
	</div>	
	<div class="alert alert-info">
		<a href="javascript:Agregar()" class="btn btn-primary">Agregar</a>
	</div>
	</form>
</div>		
</body>
<script type="text/javascript" src="../../js/chosen/chosen.jquery.js"></script>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	jQuery('#Fecha').datepicker();	
	jQuery(".chosen").chosen();
	
	function Agregar(){
		document.frmSuma.submit();
	}
</script>
</html>
