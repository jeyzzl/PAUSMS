<%@page import="java.util.GregorianCalendar"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.financiero.spring.FinSaldo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function el(nombre){return document.getElementById(nombre);}

	function guardar(){
		if( el("fInicio").value != "" && el("fLimite").value != ""){
		   	document.location.href = "grabarPermiso?Accion=1&fInicio="+el("fInicio").value+"&fLimite="+el("fLimite").value+"&comentario="+el("comentario").value;
		}else{
			alert("Ingrese todos los datos requeridos (*)");
		}
	}
</script>
<%
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String error 			= request.getParameter("Error")==null?"N":request.getParameter("Error");

	String matricula		= (String) session.getAttribute("codigoAlumno");
	
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String autorizacion		= (String) request.getAttribute("autorizacion");
	FinSaldo finSaldo 		= (FinSaldo) request.getAttribute("finSaldo");
	
	double saldoVencido		= Double.valueOf(finSaldo.getSaldoVencido())*-1;
	String etiqueta 		= saldoVencido>0?"Crédito":"Débito";
	
	Fecha fecha = new Fecha();
	
	if(accion.equals("1") && error.equals("S")){	// Error al guardar
%>
	<div class="alert alert-warning">
		<h3>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</h3>
	</div>
<%
	}
	//System.out.println("Antes de llenar lista");
	GregorianCalendar calendario = new GregorianCalendar();
	calendario.add(java.util.Calendar.DATE, 21);
%>
<div class="container-fluid">
	<h2>Permisos Finanzas <small class="text-muted fs-4">( <b><%=matricula%></b> - <%=nombreAlumno%> | <b>Saldo:</b> <%=saldoVencido%> <%=etiqueta%> )</small></h2>	
	<div class="alert alert-info">
		<a class="btn btn-info" href="permisos"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<td align="center"><strong><%=autorizacion%></strong></td>
		</tr>	
	</thead>	
		<tr>
			<th><spring:message code="aca.Datos"/></th>
		</tr>
		<tr>
			<td align="center">
				<table>					
					<tr>
						<td><spring:message code="aca.FechaInicio"/><b><font color="#AE2113"> *</font></b></td>
						<td><input type="text" class="text" id="fInicio" name="fInicio" maxlength="10" size="12" value="<%=Fecha.getHoy() %>" /></td>
					</tr>
					<tr>
						<td>Fecha L&iacute;mite<b><font color="#AE2113"> *</font></b></td>
						<td><input type="text" class="text" id="fLimite" name="fLimite" maxlength="10" size="12" value="<%=fecha.getStringFecha(calendario) %>" /></td>
					</tr>
					<tr>
						<td><spring:message code="aca.Comentario"/></td>
						<td><input type="text" class="text" id="comentario" name="comentario" maxlength="100" value="-" /></td>
					</tr>
				</table>
			</td>
		</tr>		
		<tr>
			<td style="text-align:left;">
				<input class="btn btn-primary" type="button" value="Guardar" onclick="javascript:guardar();" />
			</td>
		</tr>
	</table>
</div>