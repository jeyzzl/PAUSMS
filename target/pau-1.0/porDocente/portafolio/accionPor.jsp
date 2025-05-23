<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorDocumento" scope="page" class="aca.por.PorDocumento"/>

<script type="text/javascript">
	function Grabar(){
		document.frmPortafolio.Accion.value="3";
		document.frmPortafolio.submit();
	}
	
	function Modificar(){
		document.frmPortafolio.Accion.value="4";
		document.frmPortafolio.submit();
	}
	
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%	
	String porId			= request.getParameter("PorId")==null?"0":request.getParameter("PorId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String resultado	= "";
	
	if (accion.equals("1")){
		// Concultar el bean
		PorDocumento.setPorId(porId);
		PorDocumento.mapeaRegId(conEnoc, porId);
	}
	
	if (accion.equals("3")){
		// Grabar
		PorDocumento.setPorId(request.getParameter("PorId"));
		PorDocumento.setPorNombre(request.getParameter("PorNombre"));
		PorDocumento.setEstado(request.getParameter("Estado"));
		
		if (PorDocumento.existeReg(conEnoc) == false){
			if (PorDocumento.insertReg(conEnoc)){
				accion = "1";
				resultado = "Guardado";
			}else{
// 				accion = "2";
			}
		}else{
			resultado = "Existe";
			accion = "1";
		}
	}
	
	if (accion.equals("4")){
		// Modificar
		PorDocumento.setPorId(request.getParameter("PorId"));
		PorDocumento.setPorNombre(request.getParameter("PorNombre"));
		PorDocumento.setEstado(request.getParameter("Estado"));
		
		if (PorDocumento.existeReg(conEnoc) == true){
			if (PorDocumento.updateReg(conEnoc)){
				resultado = "Modificado";
			}else{
				resultado = "Nocambio";
				accion = "1";
			}
		}else{
			resultado = "NoExiste";
			accion = "1";
		}
	}
	
	// pageContext.setAttribute("resultado", sResultado);
%>
<div class="container-fluid">
	<h1><spring:message code="aca.AgregarPortafolios"/></h1>
	
	<% if (resultado.equals("Eliminado") || resultado.equals("Modificado") || resultado.equals("Guardado")){%>
   		<div class='alert alert-success'><%=resultado%></div>
  	<% }else if(resultado.equals("Existe")){%>
  		<div class='alert alert-warnig'><%=resultado%></div>
  	<%} %>
	
	<form name="frmPortafolio" action="accionPor" method="post" >
	<input type="hidden" name="Accion">
	<div class="alert alert-info">
		<a href="portafolio" class="btn btn-primary"><i class="icon-white icon-chevron-left"></i> <spring:message code="aca.Regresar"/></a>
	</div>
	
	<div class="row">	
		<div class="span3">
			<label for="PorId">Clave:</label>
           	<input name="PorId" type="text" class="form-control" style="width:280px" id="PorId" size="3" maxlength="3" required value="<%=PorDocumento.getPorId() %>">
          	<br><br>
            <label for="PorNombre">Nombre:</label>
            <input name="PorNombre" type="text" class="form-control" style="width:280px" id="PorNombre" required value="<%=PorDocumento.getPorNombre()%>" size="40" maxlength="40">            
      		<br><br>
      		<label for="Estado">Estado:</label>
            <select name="Estado" id="Estado" class="form-control" style="width:280px" >
            	<option value='A' <%if(PorDocumento.getEstado().equals("A")){out.print("selected");}%>><spring:message code="aca.Activo" /></option>
            	<option value='I' <%if(PorDocumento.getEstado().equals("I")){out.print("selected");}%>><spring:message code="aca.Inactivo" /></option>
            </select>
		</div>
	</div>	
	<br>
	<div class="alert alert-info">
<%if (accion.equals("1")){%>
	<a class="btn btn-primary" href="javascript:Modificar()"><spring:message code='aca.Modificar'/></a>
<%}else{%>
	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code='aca.Guardar'/></a>	
<%	}%>
	</div> 
</form>
	
</div>
<%@ include file= "../../cierra_enoc.jsp" %>