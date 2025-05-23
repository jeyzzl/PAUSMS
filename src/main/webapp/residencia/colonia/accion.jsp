<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Colonia" scope="page" class="aca.residencia.ResColonia"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmColonia.ColoniaId.value 		= " ";
		document.frmColonia.ColoniaNombre.value 	= " ";
		document.frmColonia.Accion.value="1";
		document.frmColonia.submit();		
	}
	
	function Grabar(){
		if(document.frmColonia.ColoniaId.value!="" && document.frmColonia.ColoniaNombre!=""){			
			document.frmColonia.Accion.value="2";
			document.frmColonia.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
	
	function Borrar( ){
		if(document.frmColonia.ColoniaId.value!=""){
			if(confirm("Are you sure you want to delete the record?")==true){
	  			document.frmColonia.Accion.value="4";
				document.frmColonia.submit();
			}			
		}else{
			alert("Type in the key");
			document.frmColonia.ColoniaId.focus(); 
	  	}
	}

	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Colonia.setColoniaId(Colonia.maximoReg(conEnoc));
	else
		Colonia.setColoniaId(request.getParameter("ColoniaId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Fill out the entire form";
			break;
		}		
		case 2: { // Grabar
			Colonia.setColoniaId(request.getParameter("ColoniaId"));
			Colonia.setColoniaNombre(request.getParameter("ColoniaNombre"));
			if (Colonia.existeReg(conEnoc) == false){
				if (Colonia.insertReg(conEnoc)){
					sResultado = "Saved: "+Colonia.getColoniaId();
				}else{
					sResultado = "Error saving: "+Colonia.getColoniaId();
				}
			}else{
				if (Colonia.updateReg(conEnoc)){
					sResultado = "Updated: "+Colonia.getColoniaId();
				}else{
					sResultado = "Error updating: "+Colonia.getColoniaId();
				}
			}
			
			break;
		}
		case 4: { // Borrar
			Colonia.setColoniaId(request.getParameter("ColoniaId"));
			if (Colonia.existeReg(conEnoc) == true){
				if (Colonia.deleteReg(conEnoc)){
					sResultado = "Deleted: "+Colonia.getColoniaId();
				}else{
					sResultado = "Error deleting: "+Colonia.getColoniaId();
				}	
			}else{
				sResultado = "Not found: "+Colonia.getColoniaId();
			}
			break;
		}
		case 5: { // Consultar
			String coloniaId = request.getParameter("ColoniaId");
			Colonia.mapeaRegId(conEnoc, coloniaId);
			break;			
		}
	}	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<div class="container-fluid">
	<h1>Neighborhood Catalog</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary"  href="colonia"><i class="fas fa-arrow-left"></i></a> 
	</div>
	<form action="accion" method="post" name="frmColonia" target="_self">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="span3">
				<label for="ColoniaId"><spring:message code="aca.Clave"/>:</label>			
				<input name="ColoniaId" type="text" class="text" id="ColoniaId" size="3" maxlength="3" value="<%=Colonia.getColoniaId() %>">
				<br><br>
				<label for="ColoniaNombre"><spring:message code="aca.Nombre"/>:</label>					
				<input name="ColoniaNombre" type="text" class="text" id="ColoniaNombre" size="40" maxlength="70" value="<%= Colonia.getColoniaNombre() %>">
				<br><br>
				<label ><%=sResultado%></label>
			</div>		
		</div>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; 
		  	<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		</div>
	</form>
</div>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>