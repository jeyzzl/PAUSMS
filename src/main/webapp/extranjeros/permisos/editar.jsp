<%@ page import= "aca.leg.spring.LegPermisos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar(){
		if (document.frmPermisos.Folio.value != "" && document.frmPermisos.Codigo.value != "" &&
			document.frmPermisos.FInicial.value != "" && document.frmPermisos.FFinal.value != "") {
			document.frmPermisos.submit();
		}else{
			alert("Favor de llenar todos los campos");
		}
	}
</script>

<%
	String codigo			= (String) request.getAttribute("codigo");
	String folio			= (String) request.getAttribute("folio");
	String nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	boolean edita 			= (boolean) request.getAttribute("edita");
	String mensaje		 	= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	LegPermisos legPermiso	= (LegPermisos) request.getAttribute("legPermiso");
%>
<div class="container-fluid">
	<h2><%=edita==true?"Edit":"Add"%><small class="text-muted fs-4"> ( [ <%=codigo%> ] - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="lista"><spring:message code="aca.Regresar"/></a>
	</div>
	<%if(!mensaje.equals("-")){ %>
		<div class="alert alert-info"><%=mensaje%></div>
	<%}%>
	<form name="frmPermisos" method="post" action="grabarPermiso">
	<input type="hidden" name="Codigo" value="<%=codigo%>">
	<input type="hidden" name="Folio" value="<%=folio%>">
	<div class="input-group">
		<label for="FechaInicial"><b>Start Date:</b></label>
		<input type="text" class="text" name="FInicial" id="FInicial" maxlength="20" size="10" value="<%=legPermiso.getFechaIni()%>">
	       (DD/MM/AAAA)<br><br>
	       <label for="FechaFinal"><b>End Date:</b></label>
	       <input type="text" class="text" name="FFinal" id="FFinal" maxlength="10" size="10" value="<%=legPermiso.getFechaLim()%>">
	       (DD/MM/AAAA)<br><br>
	</div>
	<div class="alert alert-info">
	 	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
	</div>
	</form>
</div>