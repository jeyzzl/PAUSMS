<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>

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
	
	function guardar(cant){
		document.forma.Accion.value="1";
		document.forma.submit();
	}	
</script>
</head>
<%
	
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");

	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String convalidacionId	= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");	
	String estado			= request.getParameter("Estado")==null?"0":request.getParameter("Estado");
	
	ConvEvento convEvento 	= (ConvEvento)request.getAttribute("convEvento");

	String institucion 		= request.getParameter("Institucion")==null?convEvento.getInstitucion():request.getParameter("Institucion");
	String programa 		= request.getParameter("Programa")==null?convEvento.getPrograma():request.getParameter("Programa");
	String periodo			= request.getParameter("Periodo")==null?convEvento.getPeriodo()==null?"-":convEvento.getPeriodo():request.getParameter("Periodo");

	String planId			= (String)request.getAttribute("planId");
	String mensaje			= (String)request.getAttribute("mensaje");
	String alert			= (String)request.getAttribute("alert");
	String nombrePlan		= (String)request.getAttribute("nombrePlan");

	List<ConvMateria> lisMaterias 				= (List<ConvMateria>)request.getAttribute("lisMaterias");	
	HashMap<String,MapaCurso> mapaCursos		= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	
	int guardadas = (int)request.getAttribute("guardadas");	

	AlumPersonal alumPersonal = (AlumPersonal)request.getAttribute("alumPersonal");

	String disable 			= (String)request.getAttribute("disable");
%>
<body>
<div class="container-fluid">
	<h3>Solicitud de convalidacion externa<small class="text-muted fs-6"> (<%=codigoAlumno %> - <%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%>)</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="solicitud"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if(!mensaje.equals("")){ %>
	<div class="alert alert-<%=alert%>" role="alert">
		<%=mensaje%>
	</div>			
<%	} %>	
	<form name="forma" action="reporte?ConvalidacionId=<%=convalidacionId %>" method="post">
	<input type="hidden" name="Imprimir" value="" />
	<input type="hidden" name="Accion" value="<%=accion%>" />
	<input type="hidden" name="Estado" value="<%=estado%>" />	
	<table style="width:100%" align="center">		
		<tr>
			<td><h3>DATOS GENERALES</h3></td>
		</tr>
		<tr>	
			<td ><b>Fecha de tr&aacute;mite:</b> <%=fecha.getFechaTexto(convEvento.getFecha()) %></td>
		</tr>
		<tr>
			<td>
				<b>Programa acad&eacute;mico en el que est&aacute; inscrito:</b> [ <strong><%=planId%></strong> ] <%=nombrePlan %>
			</td>
		</tr>				
		<tr>
			<td colspan="2">
				<strong>Instituci&oacute;n donde realiz&oacute; los estudios:</strong>
				<input type="text" class="text" name="Institucion" value="<%=institucion%>" size="50" maxlength="70" <%=disable%>/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<strong>Programa acad&eacute;mico estudiado:</strong>
				<input type="text" class="text" name="Programa" value="<%=programa%>" size="50" maxlength="70" <%=disable%>/>
			</td>
		</tr>	
		<tr>
			<td colspan="2">
				<strong>Periodo :</strong>
				<input type="text" class="text" name="Periodo" value="<%=periodo%>" size="50" maxlength="70" <%=disable%>/>
			</td>
		</tr>						
	</table>
	<br>			
	<table class="table table-sm table-bordered">
		<tr><td colspan="7"><h3>PROPUESTA DE CONVALIDACI&Oacute;N</h3></td></tr>
		<tr>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="30%">MATERIAS TOMADAS PREVIAMENTE</th>
			<th width="5%">CRS</th>
			<th width="10%" style="text-align:left">NOTA</th>
			<th width="10%">FECHA</th>						
			<th width="30%">MATERIAS QUE RECIBIR&Iacute;AN EL CR&Eacute;DITO</th>
			<th width="10%" style="text-align:right">NOTA</th>
		</tr>
<%
	String color 	= "";
	int row=0;
	for( ConvMateria convMateria : lisMaterias){		
		String cursoNombre = "-";
		if (mapaCursos.containsKey(convMateria.getCursoId())){
			cursoNombre = mapaCursos.get(convMateria.getCursoId()).getNombreCurso();
		}
%>
		<tr>		
			<td style="text-align:center"><%=row%></td>
			<td>				
				<input class="materias<%=row%>" type="text" name="materiaO<%=row%>" value="<%=convMateria.getMateria_O()==null ? "" : convMateria.getMateria_O() %>" size="30" maxlength="70" list="materias" <%=disable%>/>
			</td>
			<td align="center">
				<input onkeypress="return isNumberKey(event)" class="creditos<%=row%>" type="text" name="creditosO<%=row%>" value="<%=convMateria.getCreditos_O() %>" size="2" maxlength="2" <%=disable%>/>
			</td>
			<td>
				<input class="notas<%=row %>" type="text" name="notaO<%=row%>" value="<%=convMateria.getNota_O()%>" size="10" maxlength="10" style="text-align:right" <%=disable%>/>
			</td>
			<td>							
              	<input class="Fecha fechas<%=row%>" name="FNota<%=row%>" type="text" id="FNota<%=row%>" size="12" maxlength="10" value="<%=convMateria.getfNota()==null?"":convMateria.getfNota() %>" data-date-format="dd/mm/yyyy" <%=disable%>>
               </td>										
			<td><%=cursoNombre%></td>
			<td style="text-align:right"><%=convMateria.getNota_O()%></td>
		</tr>
<%
		row++;
	}
	
%>				
		<tr>
			<td colspan="7">	
<%
		if(estado.equals("S") || estado.equals("C")){
%>	
				<a href="javascript:guardar('<%=lisMaterias.size()%>')" class="btn btn-primary">Guardar</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
<%
		}
		if(estado.equals("C") || estado.equals("R")){
%>		
				<a href="formato?ConvalidacionId=<%=convalidacionId %>&Estado=<%=estado%>" class="btn btn-primary">Interna Materias</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="formatoExterna?ConvalidacionId=<%=convalidacionId %>&Estado=<%=estado%>" class="btn btn-primary">Externa Materias</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="formatoExternaCiclos?ConvalidacionId=<%=convalidacionId %>&Estado=<%=estado%>" class="btn btn-primary">Externa Ciclos</a>				
<%		} %>
			</td>	
		</tr>
	</table>
	</form>
</div>
</body>          
</html>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>
	jQuery(".Fecha").datepicker();
</script>