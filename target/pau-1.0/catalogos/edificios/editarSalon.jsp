<%@ page import= "aca.catalogo.spring.CatSalon"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">	

	
	function Grabar(){
		if(document.frmsalon.EdificioId.value!="" && document.frmsalon.NombreSalon!="" ){	
			document.frmsalon.submit();
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}	
	
</script>
<%
	// Declaracion de variables	
	String edificioId 	= request.getParameter("EdificioId")==null?"0":request.getParameter("EdificioId");
	String mensaje		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatSalon salon 		= (CatSalon) request.getAttribute("salon");
	
	if (mensaje.equals("1")) mensaje = "Saved"; else if (mensaje.equals("1")) mensaje = "Error saving";
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<div class="container-fluid">
	<h1><spring:message code="catalogos.edificio.Titulo4"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="salones?EdificioId=<%=edificioId%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-info">
		<%=mensaje%>		
	</div>	
	<%	} %>
	<form action="grabarSalon" method="post" name="frmsalon" target="_self">	
	<div class="form-group">
       <label for="aca.Edificio">Building</label>
       <input name="EdificioId" type="text" class="input input-mini form-control" id="EdificioId" size="6" maxlength="6" value="<%=edificioId%>" readonly>
       <br>
       <label for="aca.Salon">Classroom</label>
       <input name="SalonId" type="text" class="input input-mini form-control" id="SalonId" size="6" maxlength="6" required value="<%=salon.getSalonId()%>" readonly>
       <br> 
       <label for="aca.Nombre">Name</label>
       <input name="NombreSalon" type="text" class="input input-xlarge form-control" id="NombreSalon" required value="<%=salon.getNombreSalon()%>" size="40" maxlength="50">
       <br>
       <label for="aca.NumeroAlumno">Capacity</label>
       <input name="NumAlumnos" type="text" class="input input-mini form-control" id="NumAlumnos" required value="<%=salon.getNumAlumnos()%>" size="7" maxlength="3">
       <br>
       <label for="aca.Estado">Status</label>
        <select class="input-medium form-select" id="Estado" name="Estado">
			<option value="A" <%=salon.getEstado().equals("A") ? "Selected" : ""%>>Active</option>
			<option value="I" <%=salon.getEstado().equals("I") ? "Selected" : ""%>>Inactive</option>					
		</select>
		<br>
   		<div class="alert alert-info">           
           <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>&nbsp;           
       	</div>         
	</div>
</form>
</div>
</html>
