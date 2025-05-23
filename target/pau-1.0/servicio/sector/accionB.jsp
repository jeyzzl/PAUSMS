<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="institucion" scope="page" class="aca.ssoc.Institucion"/>

<script type="text/javascript">

	function Nuevo()	{		
		document.frmdocumento.Institucion_Id.value 		= "";
		document.frmdocumento.Institucion_Nombre.value	= "";
		document.frmdocumento.Sector_Id.value			= "";
		document.frmdocumento.Direccion.value			= "";
		document.frmdocumento.Telefono.value			= "";
		document.frmdocumento.Accion.value="1"; 
		document.frmdocumento.submit();		
	}
	
	function Grabar(){
		if(document.frmdocumento.Institucion_Id!="" && document.frmdocumento.Institucion_Nombre!=""){			
			document.frmdocumento.Accion.value="2";
			document.frmdocumento.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Modificar(){
		document.frmdocumento.Accion.value="3";
		document.frmdocumento.submit();
	}
	
	function Borrar( ){
		if(document.frmdocumento.Institucion_Id.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmdocumento.Accion.value="4";
				document.frmdocumento.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmdocumento.Institucion_Id.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmdocumento.Accion.value="5";
		document.frmdocumento.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	String Sector_Id		= request.getParameter("Sector_Id");
	String Institucion_Id	= request.getParameter("Institucion_Id");
	String Telefono			= request.getParameter("Telefono");
	if ( nAccion == 1 ){
		institucion.setInstitucion_Id(institucion.maximoReg(conEnoc, Sector_Id));
		institucion.setSector_Id(request.getParameter("Sector_Id"));
	}else{
		institucion.setInstitucion_Id(request.getParameter("Institucion_Id"));
		institucion.setTelefono(request.getParameter("Telefono"));
	}
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar			
			institucion.setInstitucion_Id(request.getParameter("Institucion_Id"));
			institucion.setInstitucion_Nombre(request.getParameter("Institucion_Nombre"));
			institucion.setSector_Id(request.getParameter("Sector_Id"));
			institucion.setDireccion(request.getParameter("Direccion"));
			institucion.setTelefono(request.getParameter("Telefono"));
			if (institucion.existeReg(conEnoc) == false){
				if (institucion.insertReg(conEnoc)){
					sResultado = "Grabado: "+institucion.getInstitucion_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Grabó: "+institucion.getInstitucion_Id();
				}
			}else{
				sResultado = "Ya existe: "+institucion.getInstitucion_Id();
			}
			
			break;
		}
		case 3: { // Modificar
			institucion.setInstitucion_Id(request.getParameter("Institucion_Id"));
			institucion.setInstitucion_Nombre(request.getParameter("Institucion_Nombre"));
			institucion.setSector_Id(request.getParameter("Sector_Id"));
			institucion.setDireccion(request.getParameter("Direccion"));
			institucion.setTelefono(request.getParameter("Telefono"));
			if (institucion.existeReg(conEnoc) == true){
				if (institucion.updateReg(conEnoc)){
					sResultado = "Modificado: "+institucion.getInstitucion_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Cambió: "+institucion.getInstitucion_Id();
				}
			}else{
				sResultado = "No existe: "+institucion.getInstitucion_Id();
			}
			break;
		}
		case 4: { // Borrar
			if (institucion.existeReg(conEnoc) == true){
				if (institucion.deleteReg(conEnoc)){
					sResultado = "Borrado: "+institucion.getSector_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Borró: "+institucion.getSector_Id();
				}	
			}else{
				sResultado = "No existe: "+institucion.getSector_Id();
			}
			break;
		}
		case 5: { // Consultar			
			if (institucion.existeReg(conEnoc) == true){
				institucion.mapeaRegId(conEnoc, request.getParameter("Institucion_Id"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+institucion.getInstitucion_Id(); 
			}	
			break;			
		}
	}	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<form action="accionB" method="post" name="frmdocumento" target="_self">
<input type="hidden" name="Accion">
<div class="container-fluid">
<h1>Añadir Institucion</h1>
<div class="alert alert-info">
	<a href="Institucion?Sector_Id=<%=Sector_Id%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a> 
</div>
<table style="width:50%" class="tabla"   bordercolor="#000000">
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong>Id:</strong></td>
          <td width="76%"><input name="Institucion_Id" type="text" class="text" id="Institucion_Id" size="2" style="border:none;" maxlength="40" value="<%=institucion.getInstitucion_Id()%>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="Institucion_Nombre" type="text" class="text" id="Institucion_Nombre" size="40" maxlength="40" value="<%=institucion.getInstitucion_Nombre()%>"></td>
        </tr>
        <tr> 
        <td><strong>Sector:</strong></td>
          <td><input name="Sector_Id" type="text" class="text" id="Sector_Id" style="border:none;" size="2" maxlength="40" value="<%=institucion.getSector_Id()%>"></td>
        </tr>
        <tr> 
          <td><strong>Direccion:</strong></td>
          <td><input name="Direccion" type="text" class="text" id="Direccion" size="40" maxlength="40" value="<%=institucion.getDireccion()%>"></td>
        </tr>
        <tr> 
          <td><strong><spring:message code='aca.Telefono'/>:</strong></td>
          <td><input name="Telefono" type="text" class="text" id="Telefono" size="10" maxlength="40" value="<%=institucion.getTelefono()%>"></td>
        </tr>
        <tr> 
          <td colspan="2" align="center"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" align="center"> 
		  <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a> &nbsp; 
		  <a href="javascript:Modificar()" class="btn btn-primary"><spring:message code='aca.Modificar'/></a> &nbsp; 
		  <a href="javascript:Borrar()" class="btn btn-danger"><spring:message code='aca.Borrar'/></a> &nbsp;
		  <input name="Telefono" type="hidden" value="<%=Telefono%>">
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
</div>
</form>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>