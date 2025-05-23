<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.*" %>
<%@page import="java.util.Arrays"%>
<jsp:useBean id="vacunas" class="aca.alerta.AlertaVacuna" scope="page" />
<jsp:useBean id="VacunasU" class="aca.alerta.AlertaVacunaUtil" scope="page" />

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>


<html>
<style>
	body{
		background: white;
	} 
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	.sintomas label, .sintomas input{
		display: inline-block;
		margin-right: 5px;
	}
	.table td{
		font-size: 18px;
	}
	#fecha1, #fecha2, #fecha3{
		width: 150px;
		height: 30px;
	}
</style>
<head>
</head>
<body>

<%
		String codigoPersonal = request.getParameter("matricula")==null?(String) session.getAttribute("matricula"):request.getParameter("matricula"); 
		String folio		  = request.getParameter("folio")==null?(String) session.getAttribute("Folio"):request.getParameter("folio"); 
		String accion 		  = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int accionFmt		  = 0;	  
		String periodoId 	= (String) session.getAttribute("periodoSanitaria");
		if(session.getAttribute("periodoSanitaria") == null){
			periodoId = aca.alerta.AlertaPeriodoUtil.getPeriodoActual(conEnoc);
			session.setAttribute("periodoSanitaria", periodoId);
		}	
		periodoId 	= (String) session.getAttribute("periodoSanitaria");
		
		String fechaHoy 	= aca.util.Fecha.getHoy();
		String fecha 		= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
		
		String msj			= "";
		if(accion.equals("1")){
			for(int i = 1; i<7; i++){
				int a = 1;
				int b = a+1;
				int d = a+2;
					vacunas.setCodigoPersonal(codigoPersonal);
					vacunas.setVacuna(Integer.toString(i));
					vacunas.setAplicada(request.getParameter("aplicada"+i));
					vacunas.setFecha1(request.getParameter("fecha"+a+(i-1)));
					vacunas.setFecha2(request.getParameter("fecha"+b+(i-1)));
					vacunas.setFecha3(request.getParameter("fecha"+d+(i-1)));
					if(!VacunasU.existeReg(conEnoc, Integer.toString(i), codigoPersonal)){
						if(VacunasU.insertReg(conEnoc, vacunas)==true){
							
						}else{
							msj = "<div class='alert alert-info'>Hubo un error al guardar los datos</div>";
							break;
						}
					}else{
						if(VacunasU.updateReg(conEnoc, vacunas)==true){
							msj = "<div class='alert alert-info'>Se guardo Correctamente los cambios</div>";
						}else{
							msj = "<div class='alert alert-danger'>Hubo un error al guardar los datos</div>";
							break;
						}	
					}
					
				}%>
<%		}
		
%>
	<div class="container-fluid">
		<h2><spring:message code='aca.Vacunas'/><small>( <%=aca.alerta.AlertaPeriodoUtil.getPeriodoNombre(conEnoc, periodoId) %> )</small><small> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "")%></small></h2>
		<div class="alert alert-info">	
			<a href="datos.jsp" class="btn btn-primary"><spring:message code='aca.Datos'/></a>&nbsp;&nbsp;
			<a href="antro?matricula=<%=codigoPersonal%>&Folio=<%=folio%>" class="btn btn-success"><span class="icon icon-white icon-arrow-left" ></span>&nbsp;<spring:message code='aca.Anterior'/></a>
		</div>
	
		<form id="datos" style="width: 100%" name="datos" action="vacunas" method="post">
			<input name="Accion" type="hidden" value="<%=accion%>"/>
			<input type="hidden" required id="matricula" name="matricula" value="<%=codigoPersonal %>" />
			<input type="hidden" required id="folio" name="folio" value="<%=folio %>" />
			<table class="table table-borderd">
				<tr>
					<td>#</td>
					<td><spring:message code='aca.Vacuna'/></td>
					<td><spring:message code='aca.Aplicada'/></td>
					<td><spring:message code='aca.Fecha1'/></td>
					<td><spring:message code='aca.Fecha2'/></td>
					<td><spring:message code='aca.Fecha3'/></td>
				</tr>
				<tr>
					<td>1</td>
					<td><spring:message code='aca.HepatitisB'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "1", codigoPersonal);
					%>
					<td>
						<select name="aplicada1">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha10" id="fecha10" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha20" id="fecha20" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha30" id="fecha30" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
				<tr>
					<td>2</td>
					<td><spring:message code='aca.HepatitisA'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "2", codigoPersonal);
					%>
					<td>
						<select name="aplicada2">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha11" id="fecha11" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha21" id="fecha21" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha31" id="fecha31" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
				<tr>
					<td>3</td>
					<td><spring:message code='aca.Tetanos'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "3", codigoPersonal);
					%>
					<td>
						<select name="aplicada3">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha12" id="fecha12" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha22" id="fecha22" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha32" id="fecha32" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
				<tr>
					<td>4</td>
					<td><spring:message code='aca.SarampionRubeola'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "4", codigoPersonal);
					%>
					<td>
						<select name="aplicada4">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha13" id="fecha13" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha23" id="fecha23" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha33" id="fecha33" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
				<tr>
					<td>5</td>
					<td><spring:message code='aca.Varicela'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "5", codigoPersonal);
					%>
					<td>
						<select name="aplicada5">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha14" id="fecha14" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha24" id="fecha24" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha34" id="fecha34" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
				<tr>
					<td>6</td>
					<td><spring:message code='aca.Influenza'/></td>
					<%
						vacunas = VacunasU.mapeaRegId(conEnoc, "6", codigoPersonal);
					%>
					<td>
						<select name="aplicada6">
							<option></option>
							<option value="S" <%if(vacunas.getAplicada().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
							<option value="N" <%if(vacunas.getAplicada().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>						
						</select>
					</td>
					<td><input type="text" name="fecha15" id="fecha15" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha1()%>"></td>
					<td><input type="text" name="fecha25" id="fecha25" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha2()%>"></td>
					<td><input type="text" name="fecha35" id="fecha35" data-date-format="dd/mm/yyyy" size="9" value="<%= vacunas.getFecha3()%>"></td>
				</tr>
			</table>
			<div class="alert alert-info">
				<a onclick="probar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
	</div>
</body>
</html>
<script>
function probar() {
		document.datos.Accion.value ="1";
		document.datos.submit();
}
</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>
	jQuery('.nav-pills').find('.vacunas').addClass('active');
	
	jQuery('#fecha10').datepicker();
	jQuery('#fecha20').datepicker();
	jQuery('#fecha30').datepicker();
	jQuery('#fecha11').datepicker();
	jQuery('#fecha21').datepicker();
	jQuery('#fecha31').datepicker();
	jQuery('#fecha12').datepicker();
	jQuery('#fecha22').datepicker();
	jQuery('#fecha32').datepicker();
	jQuery('#fecha13').datepicker();
	jQuery('#fecha23').datepicker();
	jQuery('#fecha33').datepicker();
	jQuery('#fecha14').datepicker();
	jQuery('#fecha24').datepicker();
	jQuery('#fecha34').datepicker();
	jQuery('#fecha15').datepicker();
	jQuery('#fecha25').datepicker();
	jQuery('#fecha35').datepicker();

</script>
<%@ include file= "../../cierra_enoc.jsp" %>