<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.leg.spring.LegExtdoctos"%>
<%@ page import= "aca.leg.spring.LegDocumento"%>
<%@ page import= "aca.leg.spring.LegEstado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar() {
		if (document.frmExt.IdDocumento.value != "" && document.frmExt.Codigo.value != "") {
			document.frmExt.submit();
		}else{
			alert("Favor de llenar todos los campos");
		}
	}
</script>
<%
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String nombreDoc 			= (String) request.getAttribute("nombreDoc");
	boolean edita 				= (boolean) request.getAttribute("edita");
	LegExtdoctos legExtdoctos	= (LegExtdoctos) request.getAttribute("legExtdoctos");
	
	List<LegEstado> lisEstados 					= (List<LegEstado>) request.getAttribute("lisEstados");
	List<LegDocumento> lisDocumentos 			= (List<LegDocumento>) request.getAttribute("lisDocumentos");
	HashMap<String, String> mapaExtDocumento 	= (HashMap<String, String>) request.getAttribute("mapaExtDocumento");
%>
<div class="container-fluid">
	<h2><%=edita == true ? "Editar" : "Añadir"%><small class="text-muted fs-4"> ( [ <%=codigoAlumno%> ] - <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmExt" method="post" action="grabar">
	<input type="hidden" name="Codigo" value="<%=codigoAlumno%>">
	<div class="input-group">
		<h3><spring:message code="aca.Documento"/></h3>
<%	if(!edita){%>
	    <select name="IdDocumento" id="IdDocumento">
<%
		for(LegDocumento legDoc : lisDocumentos){
			if(!mapaExtDocumento.containsKey(legDoc.getIdDocumentos())){						
				out.print(" <option value='"+legDoc.getIdDocumentos()+"'>"+ legDoc.getDescripcion()+"</option>");						
			}
		}
%>     	</select><br><br>
<%	}else{%>
		<input type="hidden" class="text" name="IdDocumento" id="IdDocumento" value="<%=legExtdoctos.getIdDocumento()%>">
		<small class="text-muted"><%=nombreDoc%></small><br><br>
<% }%>
		<h3>Num.Documento</h3>
		<input type="text" class="text" name="Numero" maxlength="20" size="13" value="<%=legExtdoctos.getNumDocto()%>"><br><br>
<% if(edita){%>
		<h3>Fecha Alta</h3><%=legExtdoctos.getFecha()%><br><br>
<% }%>
		<h3>F. Vence</h3> 
		<input type="text" class="text" name="FVence" id="FVence" maxlength="20" size="10" value="<%=legExtdoctos.getFechaVence() %>">
	    <font color="#FF0000" size="1">(DD/MM/AAAA)</font><br><br>
	    <h3>F. Tramite</h3>
	    <input type="text" class="text" name="FTramite" id="FTramite" maxlength="10" size="10" value="<%=legExtdoctos.getFechaTramite()%>">
	    <font color="#FF0000" size="1">(DD/MM/AAAA)</font><br><br>
	    <h3>Estado</h3>
	    <select name="Estado">
<% 		for(LegEstado estado : lisEstados){%>
	   		<option value="<%=estado.estadoId%>" <%=estado.estadoId.equals(legExtdoctos.getEstado()) ? "selected" : "" %>><%=estado.estadoNombre%></option>
<% 		}%>
	    </select><br><br>	        
	    <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
	</div>
	</form>
</div>