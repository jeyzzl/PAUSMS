<%@ page import= "java.util.List"%>
<%@ page import= "aca.catalogo.spring.CatArea"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">	

	function Grabar() {
		if (document.frmareas.AreaId.value != ""
				&& document.frmareas.NombreArea != "") {
			document.frmareas.Accion.value = "2";
			document.frmareas.submit();
		} else {
			alert("<spring:message code='catalogos.area.JSCompletar'/>");
		}
	}
</script>
<%
	// Declaracion de variables	
	CatArea area 		= (CatArea)request.getAttribute("area");
	String mensaje		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<Maestros> lisMaestros		= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<div class="container-fluid">
	<h2><spring:message code="catalogos.area.Titulo2"/></h2>
	<form name="frmareas" action="grabarArea" method="post">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="areas"><i class="fas fa-arrow-left"></i></a> &nbsp;&nbsp;
		<%if (!mensaje.equals("-")) out.print(mensaje);%>
	</div>		
	<div class="form-group">
		<label for="Clave"><spring:message code="aca.Clave"/>:</label>
	    <input type="text" class="input input-mini form-control" style="width:200px" name="AreaId" id="AreaId" required value="<%=area.getAreaId()%>" maxlength="1" readonly>
	    <br>
	    <label for="Nombre"><spring:message code="aca.Nombre"/>:</label>
	    <input type="text" class="input input-xlarge form-control" style="width:400px" name="NombreArea" id="NombreArea" required value="<%=area.getNombreArea()%>" maxlength="40">
	    <br>
	    <label for="CodigoPersonal">Employee ID:</label><br>
	    <select name="CodigoPersonal" class="form-select chosen" style="width:400px" required>
	    	<option value="0" <%=area.getCodigoPersonal().equals("1")?"selected":""%>>Choose</option>
<%		for (Maestros maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(area.getCodigoPersonal())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
	    <br><br>    	    
	    <label for="TipoPromedio">Area Grading:</label>
	    <select class="input input-medium form-select" style="width:200px" name="TipoPromedio"  id="TipoPromedio" required>
	    	<option value="1" <% if (area.getTipoPromedio().equals("1")) out.print("selected"); %> ><spring:message code="catalogos.area.tipoPromedio1"/></option>
	    	<option value="2" <% if (area.getTipoPromedio().equals("2")) out.print("selected"); %> ><spring:message code="catalogos.area.tipoPromedio2"/></option>
	    </select>	    
	</div>
	&nbsp;
	<div class="alert alert-info">	    
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
    </div>	
	</form>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>