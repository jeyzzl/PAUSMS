<%@page import="aca.mensaje.mensaje"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.bec.spring.BecInforme"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<%
	String idEjercicio 			= (String) session.getAttribute("ejercicioId");
	String informeId			= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
		
	BecInforme becInforme 		= (BecInforme) request.getAttribute("becInforme"); 	
%>
<style>
	body{
		background:white;
	}	
</style>
<body>
<div class="container-fluid">
	<h2>Catálogo de informes</h2>
	<div class="alert alert-info">
		<a href="informe" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form name="frmInforme" action="guardar" method="post">		
	<table class="table" align="center">
	<tr>
		<th width="10%">Informe Id</th>
		<th width="90%"><input type="text" name="InformeId" id="InformeId" class="form-control" style="width:880px;"  value="<%=becInforme.getInformeId()%>" size="5" readonly="readonly" /></th>
	</tr>
	<tr>
		<th ><spring:message code="aca.Nombre"/></th> 
		<th><input type="text" name="Nombre" id="Nombre" class="form-control" style="width:880px;"  value="<%=becInforme.getInformeNombre()%>" size="50" /></th>
	</tr>
	<tr>
		<th>Fecha de inicio</th>
		<th>
			<input type="text" data-date-format="dd/mm/yyyy" id="fechaIni" name="fechaIni" class="form-control" style="width:880px;" value="<%=becInforme.getFechaIni()%>" size="10" /> dd/mm/yyyy
		</th>
	</tr>
	<tr>
		<th ><spring:message code='aca.FechaFinal'/></th>
		<th>
			<input type="text" data-date-format="dd/mm/yyyy" id="fechaFin" name="fechaFin" class="form-control" style="width:880px;" value="<%=becInforme.getFechaFin()%>" size="10" /> dd/mm/yyyy
		</th>
	</tr>
	<tr>
		<th >Nivel</th>
		<th>
			<select  id="nivel" name="nivel" class="form-select" style="width:880px;">										
				<option value="P" <%if(becInforme.getNivel().equals("P"))out.print("selected"); %>>Preparatoria</option>
				<option value="U" <%if(becInforme.getNivel().equals("U"))out.print("selected"); %>>Universidad</option>
			</select>
		</th>
	</tr>
	<tr>
		<th >Orden</th>
	        <td>
				<select name="orden" id="orden" class="form-select" style="width:880px;">
					<option value="1" <%if(becInforme.getOrden().equals("1"))out.print("selected"); %>>1. Enero</option>
					<option value="2" <%if(becInforme.getOrden().equals("2"))out.print("selected"); %> >2. Febrero</option>
					<option value="3" <%if(becInforme.getOrden().equals("3"))out.print("selected"); %>>3. Marzo</option>
					<option value="4" <%if(becInforme.getOrden().equals("4"))out.print("selected"); %>>4. Abril</option>
					<option value="5" <%if(becInforme.getOrden().equals("5"))out.print("selected"); %>>5. Mayo</option>
					<option value="6" <%if(becInforme.getOrden().equals("6"))out.print("selected"); %>>6. Junio</option>
					<option value="7" <%if(becInforme.getOrden().equals("7"))out.print("selected"); %>>7. Julio</option>
					<option value="8" <%if(becInforme.getOrden().equals("8"))out.print("selected"); %> >8. Agosto</option>
					<option value="9" <%if(becInforme.getOrden().equals("9"))out.print("selected"); %>>9. Septiembre</option>
					<option value="10"<%if(becInforme.getOrden().equals("10"))out.print("selected"); %>>10. Octubre</option>
					<option value="11"<%if(becInforme.getOrden().equals("11"))out.print("selected"); %>>11. Noviembre</option>
					<option value="12"<%if(becInforme.getOrden().equals("12"))out.print("selected"); %>>12. Diciembre</option>
	            </select>
			</td>
	</tr>
	<tr>
		<th><spring:message code="aca.Estado"/></th>
		<th>
			<select name="estado" id="estado" class="form-select" style="width:880px;">
				<option value="U" <%if(becInforme.getEstado().equals("U"))out.print("selected"); %>><spring:message code='aca.Usuario'/></option>
				<option value="A" <%if(becInforme.getEstado().equals("A"))out.print("selected"); %>>Administrador</option>
			</select>
		</th>
	</tr>
	</table>	
	<div class="alert alert-info">
		<th style="border:none;"><input type="button" value="Grabar" class="btn btn-success"   onclick="javascript:Guardar();"/></th>		
	</div>	
	</form>
</div>
</body>
<script type="text/javascript">
	function Guardar() {	
		if (document.frmInforme.InformeId.value != ""
				&& document.frmInforme.Nombre.value != ""
				&& document.frmInforme.fechaIni.value != ""
				&& document.frmInforme.fechaFin.value != ""
				&& document.frmInforme.nivel.value != ""
				&& document.frmInforme.orden.value != ""
				&& document.frmInforme.estado.value!= ""){			
			document.frmInforme.submit();
		} else {
			alert("¡Completa todos los campos!");
		}		
	}
</script>
<script>
	jQuery('#fechaIni').datepicker();
	jQuery('#fechaFin').datepicker();
</script>