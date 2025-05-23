<%@ page import="java.util.List"%>
<%@ page import="aca.archivo.spring.ArchPermisos"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">	
	
	function Grabar(){
		if(document.frmDocumento.Folio!="" && document.frmDocumento.FechaIni.value != "" && document.frmDocumento.FechaLim.value != ""){			
			document.frmDocumento.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Actualizar(){
		if(document.frmdocumento.Folio.value!=""){
			document.frmdocumento.Accion.value="4";
			document.frmdocumento.submit();
		}
	}
	
	function Cuenta(){
		var limite = jQuery("#Comentario").attr("maxlength");
		var letras = jQuery("#Comentario").val().length;
		//alert("Dato:"+letras);		
		var resto = limite - letras;
		jQuery('#resto').text(resto.toString());
		
	}
</script>
<%
	// Declaracion de variables	
	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String nombreEmpleado		= (String) request.getAttribute("nombreEmpleado");
	ArchPermisos permiso 		= (ArchPermisos) request.getAttribute("permiso");
	List<MapaPlan> lisPlanes	= (List<MapaPlan>) request.getAttribute("lisPlanes");	
%>
<!-- inicio de estructura -->
<html>
<div class="container-fluid">
	<h2>Add Permit<small class="text-muted">(<%=codigoPersonal%>- <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="permiso?<%=codigoPersonal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="frmDocumento" action="grabar" method="post">		    
    	<label><spring:message code='aca.Folio'/>:</label>
	    <input name="Folio" type="text" class="form-control" id="Folio" size="3" maxlength="5" value="<%=permiso.getFolio()%>" style="width:120px;" disabled>
	    <br>
	    <label>Plan:</label>
	    <select name="PlanId" class="form-control" style="width:400px;">
<%	for (MapaPlan plan : lisPlanes){%>
			<option value="<%=plan.getPlanId()%>" <%=permiso.getPlanId().equals(plan.getPlanId())?"selected":""%>><%=plan.getPlanId()%>-<%=plan.getNombrePlan()%></option>
<%	} %>	    	
	    </select>			    
		<br>
	    <label>Initial Date:</label>
	    <input name="FechaIni" type="text" class="form-control" id="FechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=permiso.getFechaIni()%>" style="width:120px;">
	    <br>
	    <label>Deadline:</label>
	    <input name="FechaLim" type="text" class="form-control" id="FechaLim" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=permiso.getFechaLim()%>" style="width:120px;">
	    <br>		    
	    <label>Comment: [<span id="resto"><%=300-permiso.getComentario().length()%></span>]</label>
	    <textarea name="Comentario" id="Comentario" class="form-control" rows="3" cols="40" maxlength="300" onKeyDown="javascript:Cuenta()" onKeyUp="javascript:Cuenta()"><%=permiso.getComentario()%></textarea>     	
     	<br>
	    User Registration: <%=permiso.getUsuarioAlta()%>&nbsp; &nbsp;User Low: <%=permiso.getUsuarioBaja()%>
	    <br>
     	<div class="alert alert-info d-flex align-items-center">
			<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
		</div>	  
	</form>
</div>
</body>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaLim').datepicker();	
</script>
</html>