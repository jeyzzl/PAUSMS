<%@ page import="java.util.List "%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@page import="aca.emp.Empleado"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>


<script type = "text/javascript">
	
	function Grabar(){
		if(document.frmcandado.TipoId.value!="" && document.frmcandado.Nombre != ""){
			document.frmcandado.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}	
</script>
<%	
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String nombre 				= (String)request.getAttribute("nombre");
 	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	if (mensaje.equals("1")) mensaje = "Saved"; else if (mensaje.equals("2")) mensaje = "Error saving";
 	
	CandTipo candTipo 			= (CandTipo)request.getAttribute("candTipo");
%>
<div class="container-fluid">
	<h2><%=candTipo.getNombreTipo().equals("-")?"New Type":candTipo.getNombreTipo()%>&nbsp;
		<small class="text-muted fs-4">[ <%=codigoPersonal%>  ]
		</small>
	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="candados"><i class="fas fa-arrow-left"></i></a>
	</div>
	<%
		if (!mensaje.equals("-")) {
	%>
	<div class="alert alert-info"><%=mensaje%></div>
	<%
		}
	%>
	<form name="frmcandado" id="frmcandado" method="post" action="grabarCandado">
		<input type="hidden" name="accion">
		<table style="width: 50%" class="table table-condensed">
			<tr>
				<td><b>ID:</b></td>
				<td><input name="TipoId" id="TipoId" type="text" class="form-control"
					size="2" maxlength="2" value="<%=candTipo.getTipoId()%>" readonly>
				</td>
			</tr>
			<tr>
				<td><b>Department:</b></td>
				<td><input type="text" class="form-control" name="Lugar" id="Lugar" value="<%=candTipo.getLugar()%>"></td>
				
			</tr>
			<tr>
				<td><b><spring:message code="aca.Nombre" />:</b></td>
				<td><input type="text" class="text form-control" name="Nombre" id="Nombre" value="<%=candTipo.getNombreTipo()%>"></td>
			</tr>
			<tr>
				<td><b>Contact:</b></td>
				<td><input type="text" class="form-control" name="Tel" id="Tel" value="<%=candTipo.getTelefono()%>"></td>
				
			</tr>
<!-- 			<tr> -->
<!-- 				<td><b>Responsable:</b></td> -->
<%-- 				<td colspan="3"><input type="text" class="form-control" name="Responsable" id="Responsable" value="<%=candTipo.getResponsable()%>" maxlength="140" size="50"></td> --%>
<!-- 			</tr> -->
			<tr>
				<td><b>Status:</b></td>
				<td colspan="3">
					<select name="Estado" class="form-select">
						<option value="A" <%=candTipo.getEstado().equals("A") ? "selected" : ""%>>Active</option>
						<option value="I" <%=candTipo.getEstado().equals("I") ? "selected" : ""%>>Inactive</option>
					</select>
				</td>
			</tr>
			<tr>
				<th style="text-align: center;" colspan="4" align="center">
					<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>&nbsp;&nbsp;&nbsp;
				</th>
			</tr>
		</table>
	</form>
</div>