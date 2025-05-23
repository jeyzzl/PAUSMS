<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatHorario"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<body>
<%	
	CatHorario horario 		= (CatHorario) request.getAttribute("horario");
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<CatFacultad> lisFacultades 				= (List<CatFacultad>) request.getAttribute("lisFacultades");	
%>
<div class="container-fluid">
	<h2>Timetable</h2>	  
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<span><%=mensaje.equals("-") ? "" : mensaje %></span>
	</div>
	
	<form name="frmHorario" action="grabar" method="post">
	<table class="table table-nohover" style="margin: 0 auto;">
	<tr>
		<td><b>Timetable ID</b></td>
		<td>
			<input type="text" class="text form-control w-25" name="HorarioId" id="HorarioId" value="<%=horario.getHorarioId()%>" width="50%" readonly/>
		</td>
	</tr>
	<tr>
		<td><b><spring:message code='aca.Descripcion'/></b></td>
		<td>
			<textarea type="text" class="form-control w-50" style="width:275px;" id="Descripcion" name="Descripcion"><%=horario.getDescripcion()%></textarea>
		</td>
	</tr>
	<tr>
		<td><b>School:</b></td>
		<td>
			<select onchange="document.forma.submit();" id="FacultadId" name="FacultadId" class="form-select w-25">
				<option value="000" <%=horario.getFacultadId().equals("000")?"Selected":"" %>>All</option>
<%		for(CatFacultad facultad : lisFacultades){%>
				<option value="<%=facultad.getFacultadId() %>" <%=facultad.getFacultadId().equals(horario.getFacultadId())?"Selected":"" %>><%=facultad.getNombreFacultad()%></option>
<%		} %>
			</select>
		</td>
	</tr>
	<tr>
		<td><b><spring:message code="aca.Status"/></b></td>
		<td>
			<select  id="Estado" name="Estado" class="form-select w-25">										
				<option value="A" <%if(horario.getEstado().equals("A"))out.print("selected"); %>><spring:message code='aca.Activo'/></option>
				<option value="I" <%if(horario.getEstado().equals("I"))out.print("selected"); %>><spring:message code='aca.Inactivo'/></option>				
			</select>
		</td>
	</tr>
	</table>
	<div class="alert alert-info">
		<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="fas fa-check"></i> Save</a>
	</div>		
	</form>		
</div>
</body>
<script>
	function Guardar() {
		if (document.frmHorario.HorarioId.value != "" && document.frmHorario.FacultadId.value != "") {			
			document.frmHorario.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>
</html>