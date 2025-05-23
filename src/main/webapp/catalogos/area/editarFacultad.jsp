<%@ page import= "java.util.List"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">	
	
	function Grabar(){
		if(document.frmfacultad.FacultadId.value!="" && document.frmfacultad.Titulo!="" && document.frmfacultad.NombreFacultad!=""){
			document.frmfacultad.Accion.value="2";
			document.frmfacultad.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
	
</script>
<%
	// Declaracion de variables	
	CatFacultad facultad 	= (CatFacultad) request.getAttribute("facultad");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<Maestros> lisMaestros		= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<html>
<div class="container-fluid">
	<h2><spring:message code="catalogos.area.Titulo4"/></h2>
	<form action="grabarFacultad" method="post" name="frmfacultad" target="_self">
	<input type="hidden" name="Accion">	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="facultad"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>		
	<div class="form-group"> 
        <label for="Clave"><spring:message code="aca.Clave"/>:</label>
        <input name="FacultadId" type="text" class="text form-control" style="width:200px" id="FacultadId" size="3" maxlength="3" required value="<%=facultad.getFacultadId()%>"> 
        <br>
        <label for="Titulo"><spring:message code="aca.Titulo"/>:</label>
        <input type="text" class="input input-xlarge form-control" style="width:300px" name="Titulo" id="Titulo" required value="<%=facultad.getTitulo()%>" size="40" maxlength="20">
        <br>
        <label for="Nombre"><spring:message code="aca.Nombre"/>:</label>
        <input name="NombreFacultad" type="text" class="input input-xlarge form-control" style="width:425px" id="NombreFacultad" required value="<%=facultad.getNombreFacultad()%>" size="40" maxlength="50">
        <br>
        <label for="NombreCorto"><spring:message code="aca.NombreCorto"/>:</label>
        <input name="NombreCorto" type="text" class="input input-xlarge form-control" style="width:300px" id="NombreCorto" required value="<%=facultad.getNombreCorto()%>" size="20" maxlength="20">
        <br>
        <label for="CodigoPersonal">Employee ID:</label><br>
	    <select name="CodigoPersonal" class="form-select chosen" style="width:400px" required>
	    	<option value="0" <%=facultad.getCodigoPersonal().equals("0")?"selected":""%>>Choose</option>
<%		for (Maestros maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(facultad.getCodigoPersonal())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
		<br><br> 
        <label for="CodigoPersonal">Employee ID:</label>
        <input name="CodigoPersonal" type="text" class="input input-medium form-control" style="width:200px" id="CodigoPersonal" required value="<%=facultad.getCodigoPersonal()%>" size="40" maxlength="7">
        <br>
        <label for="Referente"><spring:message code="aca.Referente"/>:</label>
        <input name="Referente" type="text" class="input input-medium form-control" style="width:200px" id="Referente" required value="<%=facultad.getInvReferente()%>" size="7" maxlength="7">
      </div>	
      &nbsp;	
      <div class="alert alert-info">      
         	<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;        
      </div>      	
</form>
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>
</html>