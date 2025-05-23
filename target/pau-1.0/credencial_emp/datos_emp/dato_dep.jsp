<%@ page buffer= "none" %>
<%@page import="aca.emp.spring.Empleado"%>
<%@page import="aca.emp.spring.EmpDependiente"%>
<%@page import="aca.emp.spring.EmpleadoDependientes"%>
<%@page import="aca.cred.spring.CredDependiente"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Valida_graba(){
		if((document.form1.f_dep.value!="") && (document.form1.f_empleado.value!="") && 
			(document.form1.f_nombre.value!="") && (document.form1.f_relacion.value != "") && 
			(document.form1.f_fecha.value!="" )){
			document.form1.Accion.value = 1;
			document.form1.submit();
		}else{
			alert("Complete el formulario Correctamente ..! ");
		}
	}	
	function Valida_modifica(){
		if((document.form1.f_dep.value!="") && (document.form1.f_empleado.value!="") && 
		(document.form1.f_nombre.value!="") && (document.form1.f_apellido.value != "") && 
		(document.form1.f_fecha.value!="" )){
			document.form1.Accion.value = 2;
			document.form1.submit();
		}else{
			alert("Complete el formulario Correctamente ..! ");
		}
	}
	
	function borrarFoto( nomina, folio){
		if (confirm("¿Estás seguro de borrar la fotografía del dependiente?")){
			document.location.href="borrarDep?Codigo="+nomina+"&Folio="+folio;
		}	
	}
	
	function camara(){
		location.href="tomarfotoDep.jsp";
	}
	
</script>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<!-- inicio de estructura -->
<%
	Empleado empleado	 					= (Empleado) request.getAttribute("empleado");
	EmpDependiente enocDependiente 			= (EmpDependiente) request.getAttribute("enocDependiente");
	EmpleadoDependientes aronDependiente 	= (EmpleadoDependientes) request.getAttribute("aronDependiente");
	CredDependiente credDependiente 		= (CredDependiente) request.getAttribute("credDependiente");
	
	String departamento						= (String) request.getAttribute("departamento");
	String puesto							= (String) request.getAttribute("puesto");
	boolean existe							= (boolean) request.getAttribute("existe");
	boolean tieneFoto 						= (boolean) request.getAttribute("tieneFoto");
	System.out.println("TieneFoto:"+tieneFoto);
	String s_idempleado 	= request.getParameter("f_empleado")==null?(String)session.getAttribute("empleadoId"):request.getParameter("f_empleado");
	String s_folio			= request.getParameter("f_folio")==null?(String)session.getAttribute("dependienteId"):request.getParameter("f_folio");
	String empleadoClave	= empleado.getClave();	
	String empleadoNombre 	= empleado.getNombre()+" "+empleado.getAppaterno()+" "+empleado.getApmaterno();	
%>
<div class="container-fluid">
	<h2>Datos del dependiente</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="dato_emp?f_codigo_personal=<%=empleadoClave%>"><spring:message code="aca.Regresar" /></a>
	</div>
	<form name="form1" method="post" action="add_dep">
		<input name="Accion" type="hidden">
		<table width="80%" class="tabla">
			<tr align="CENTER" valign="TOP">
				<th colspan="3" align="CENTER"><b><font size="2" face="Arial, Helvetica, sans-serif">&nbsp;</font></b></th>
			</tr>
			<tr align="CENTER" valign="TOP">
				<td width="92" height="260" align="CENTER">
					<table align="CENTER">
						<tr>
							<%
								session.setAttribute("mat", empleadoClave + "-" + s_folio);
							%>
							<td>
								<div id="sombra">
									<img src="../../empFoto?Codigo=<%=empleadoClave%>&Folio=<%=s_folio%>" width="250" border="1">
								</div>
							</td>
						</tr>
						<tr>
							<td align="CENTER">
								<a href="javascript:camara()" title="Tomar la Foto">
									<img src='../../imagenes/camaraweb.png' width="20">
								</a>&nbsp; 
								<a href="subirDep" title="Subir Foto de un archivo">
									<img src='../../imagenes/upload.png' width="30">
								</a>&nbsp; 
								<%if (tieneFoto) {%>
								<a href="../../empFotoBajar?Codigo=<%=empleadoClave%>&Folio=<%=s_folio%>" title="Descargar la Foto">
									<img src='../../imagenes/descargar.png' width="30">
								</a>&nbsp; 
								<a href="javascript:borrarFoto('<%=empleadoClave%>','<%=s_folio%>')" title="Borrar la Foto">
									<img src='../../imagenes/borrar2.gif' width="25">
								</a> 
								<%} %>
							</td>
						</tr>
					</table>
				</td>
				<td align="CENTER" width="516">
					<table align="CENTER" width="100%">
						<tr>
							<td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><strong>#Depen:</strong></font></td>
							<td align="LEFT">
								<input name="f_dep" type="text" class="text" id="f_dep" value="<%=empleadoClave + "-" + s_folio%>" size="11" maxlength="9" readonly> 
								<input name="f_empleado" type="hidden" value="<%=s_idempleado%>">
							</td>
						</tr>
						<tr>
							<td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre" /> Completo:</b></font></td>
							<td> <input name="s_nombreCompleto" type="text" class="text" value="<%=empleadoNombre%>" size="60" readonly></td>
						</tr>
						<tr>
							<td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre" />:</b></font></td>
							<td align="LEFT">
								<input name="f_nombre" type="text" class="text" id="f_nombre" value="<%=credDependiente.getDepNombre() == null ? "" : credDependiente.getDepNombre()%>" size="40" maxlength="15">
							</td>
						</tr>
						<tr>
							<td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Apellido:</b></font></td>
							<td align="LEFT">
								<input name="f_apellido" type="text" class="text" id="f_apellido" value="<%=credDependiente.getDepApellidos() == null ? "" : credDependiente.getDepApellidos()%>" size="40" maxlength="40">
							</td>
						</tr>
						<tr>
							<td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Relación:</font></b></td>
							<td align="LEFT">
								<input name="f_relacion" type="text" class="text" id="f_relacion" value="<%=enocDependiente.getRelacion()%>" size="60" maxlength="20">
							</td>
						</tr>
						<tr>
							<td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif"><spring:message code="aca.Fecha" />:</font></b></td>
							<td align="LEFT">
								<input name="f_fecha" type="text" class="text" id="f_fecha" data-date-format="dd/mm/yyyy" value="<%=aronDependiente.getBday()%>" size="13" maxlength="10">
							</td>
						</tr>
						<tr>
							<td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Empleado</font></b></td>
							<td align="LEFT"><%=empleadoNombre%></td>
						</tr>
						<tr>
							<td align="LEFT"><font size="2"
								face="Arial, Helvetica, sans-serif"><b>Puesto:</b></font></td>
							<td align="LEFT"><%=puesto%></td>
						</tr>
						<tr>
							<td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Departamento:</b></font></td>
							<td align="LEFT"><%=departamento%></td>
						</tr>
						<tr>
							<td align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">Cotejado:</font></b></td>
							<td align="LEFT">
								<select name="f_cotejado" class="input-small">
									<option value="S" <%=enocDependiente.getCotejado().equals("S") ? " selected" : ""%>>SI</option>
									<option value="N" <%=enocDependiente.getCotejado().equals("N") ? " selected" : ""%>>NO</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td align="CENTER" colspan="2"><input class="btn btn-primary" type="button" value="Grabar" onclick="javascript:Valida_graba();" /></td>
						</tr>
						<tr>
							<td align="center" colspan="2">
								<spring:message code="aca.FechaActualizacion" />:<br>
								<font color="blue"><b><%=credDependiente.getfActualiza()%></b></font>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
<script>
	jQuery('#f_fecha').datepicker();
</script>