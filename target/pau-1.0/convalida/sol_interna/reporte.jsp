<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>

<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script src="isDate.js"></script>
<script type="text/javascript">
	function isNumberKey(evt){
   		var charCode = (evt.which) ? evt.which : event.keyCode
   		if (charCode > 31 && (charCode < 48 || charCode > 57))
      		return false;

   		return true;
	}

	
	function checkDate(date){
		var rs = true;
		
		jQuery(date).each(function(){
			var $this = jQuery(this);
			if(isDate($this.val()) == false){
				rs = false;
			}
		});
		
		return rs;
	}
	
	function guardar(){
		document.forma.Accion.value="1";
		document.forma.submit();
	}
	
	function imprimir(){
		document.forma.Imprimir.value="1";
		document.forma.submit();
	}	
	
</script>
</head>
<%
	String nomInstitucion 	= (String)request.getAttribute("nomInstitucion");
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	
	String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	
	String planId			= (String)request.getAttribute("planId");	
	String mensaje			= (String)request.getAttribute("mensaje");	
	
	String institucion 		= (String)request.getAttribute("institucion");
	String programa 		= (String)request.getAttribute("programa");
	String nombrePlan 		= (String)request.getAttribute("nombrePlan");
	String fecha 			= (String)request.getAttribute("fecha");
	
	AlumPersonal alumPersonal = (AlumPersonal)request.getAttribute("alumPersonal");
	
	// Lista de Materias para convalidar
	List<ConvMateria> lisMaterias = (List<ConvMateria>)request.getAttribute("lisMaterias");
	
	// Mapa de materias
	HashMap<String,MapaCurso> mapaMaterias = (HashMap<String,MapaCurso>)request.getAttribute("mapaMaterias");
%>
<body>
<div class="container-fluid">
	<h3>SOLICITUD DE CONVALIDACI&Oacute;N DE MATERIAS<small class="text-muted fs-6"> (<%=codigoAlumno %> - <%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%>)</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="interna"><i class="fas fa-arrow-left"></i></a>
	</div>
<%
	if(!mensaje.equals("0")){
		out.print("<div class='alert alert-success'>"+mensaje+"</div>");
	}
%>
	<form name="forma" action="reporte?ConvalidacionId=<%=convalidacionId %>" method="post">
	<input type="hidden" name="Imprimir" value="" />
	<input type="hidden" name="Accion" value="" />
	<br>	
	<table class="table table-sm table-bordered">  
		<tr>
			<td ><b>Fecha de tr&aacute;mite:</b> <%=fecha%></td>
		</tr>
		<tr>
			<td>
				<b>Programa acad&eacute;mico en el que est&aacute; inscrito:</b> [ <strong><%=planId%></strong> ] <%=nombrePlan%>
			</td>
		</tr>				
		<tr>
			<td colspan="2">
				<strong>Programa acad&eacute;mico estudiado:</strong>
				<%=programa%>
			</td>
		</tr>
	</table>
	<br>	
	<table class="table table-sm table-bordered">  
		<thead class="table">
		<tr><th colspan="7"><h3>PROPUESTA DE CONVALIDACI&Oacute;N</h3></th></tr>
		</thead>
		<thead class="table-info">
		<tr>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="30%">MATERIAS TOMADAS PREVIAMENTE</th>
			<th width="5%">CRS</th>
			<th width="10%" style="text-align:left">NOTA</th>
			<th width="10%">FECHA</th>						
			<th width="30%">MATERIAS QUE RECIBIR&Iacute;AN EL CR&Eacute;DITO</th>
			<th width="10%" style="text-align:right">NOTA</th>
		</tr>
		</thead>
<%	int row = 0;	 
	for(ConvMateria convMateria : lisMaterias){
		row++;
		String cursoOrigen 		= convMateria.getMateria_O()==null?"":convMateria.getMateria_O();
		String notaOrigen 		= convMateria.getNota_O()==null?"":convMateria.getNota_O();
		String creditosOrigen 	= convMateria.getCreditos_O()==null?"":convMateria.getCreditos_O();
		
		String nombreOrigen = "";
		if (mapaMaterias.containsKey(cursoOrigen) ){
			nombreOrigen = mapaMaterias.get(cursoOrigen).getNombreCurso();
		}
		
		String nombreConvalida = "";
		if (mapaMaterias.containsKey(convMateria.getCursoId()) ){
			nombreConvalida = mapaMaterias.get(convMateria.getCursoId()).getNombreCurso();
		}
%>
		<tr>
			<td style="text-align:center"><%=row%></td>
			<td style="text-align:left"><%= nombreOrigen %></td>
			<td style="text-align:left"><%= creditosOrigen %></td>
			<td style="text-align:left"><%= notaOrigen %></td>
			<td style="text-align:left"><%= convMateria.getfNota() %></td>
			<td style="text-align:left"><%=nombreConvalida%></td>
			<td style="text-align:right"><%=notaOrigen %></td>
		</tr>
<%	}%>			
	</table>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="button" name="guarda" value="Guardar" onclick="guardar()" />
			<a href="formato?ConvalidacionId=<%=convalidacionId %>" class="btn btn-primary">Formato</a>
		</div>
	</form>
</div>
</body>          
</html>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>
	jQuery(".Fecha").datepicker();
</script>