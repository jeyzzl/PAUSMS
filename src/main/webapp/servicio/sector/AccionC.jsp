<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="plaza" scope="page" class="aca.ssoc.Plaza"/>

<script type="text/javascript">

	function Nuevo()	{		
		document.frmdocumento.Plaza_Id.value 		= "";
		document.frmdocumento.Plaza_Nombre.value	= "";
		document.frmdocumento.Institucion_Id.value	= "";
		document.frmdocumento.Accion.value="1"; 
		document.frmdocumento.submit();		
	}
	
	function Grabar(){
		if(document.frmdocumento.Plaza_Id!="" && document.frmdocumento.Plaza_Nombre!=""){			
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
		if(document.frmdocumento.Plaza_Id.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmdocumento.Accion.value="4";
				document.frmdocumento.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmdocumento.Plaza_Id.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmdocumento.Accion.value="5";
		document.frmdocumento.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	String Plaza_Id			= request.getParameter("Plaza_Id");
	String Institucion_Id	= request.getParameter("Institucion_Id");
	
	System.out.println("Plaza: "+Plaza_Id);
	System.out.println("Institucion: "+Institucion_Id);
	
	if ( nAccion == 1 ){
		plaza.setPlaza_Id(plaza.maximoReg(conEnoc, Institucion_Id));
		plaza.setInstitucion_Id(request.getParameter("Institucion_Id"));
	}else{
		plaza.setPlaza_Id(request.getParameter("Plaza_Id"));
	}
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar			
			System.out.println("Plaza: "+Plaza_Id);
			System.out.println("Institucion: "+Institucion_Id);
			plaza.setPlaza_Id(request.getParameter("Plaza_Id"));
			plaza.setPlaza_Nombre(request.getParameter("Plaza_Nombre"));
			plaza.setInstitucion_Id(request.getParameter("Institucion_Id"));
			if (plaza.existeReg(conEnoc) == false){
				if (plaza.insertReg(conEnoc)){
					sResultado = "Grabado: "+plaza.getPlaza_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Grabó: "+plaza.getPlaza_Id();
				}
			}else{
				sResultado = "Ya existe: "+plaza.getPlaza_Id();
			}
			
			break;
		}
		case 3: { // Modificar
			plaza.setPlaza_Id(request.getParameter("Plaza_Id"));
			plaza.setPlaza_Nombre(request.getParameter("Plaza_Nombre"));
			plaza.setInstitucion_Id(request.getParameter("Institucion_Id"));
			if (plaza.existeReg(conEnoc) == true){
				if (plaza.updateReg(conEnoc)){
					sResultado = "Modificado: "+plaza.getPlaza_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Cambió: "+plaza.getPlaza_Id();
				}
			}else{
				sResultado = "No existe: "+plaza.getPlaza_Id();
			}
			break;
		}
		case 4: { // Borrar
			if (plaza.existeReg(conEnoc) == true){
				if (plaza.deleteReg(conEnoc)){
					sResultado = "Borrado: "+plaza.getPlaza_Id();
					conEnoc.commit();
				}else{
					sResultado = "No Borró: "+plaza.getPlaza_Id();
				}	
			}else{
				sResultado = "No existe: "+plaza.getPlaza_Id();
			}
			break;
		}
		case 5: { // Consultar			
			if (plaza.existeReg(conEnoc) == true){
				plaza.mapeaRegId(conEnoc, request.getParameter("Plaza_Id"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+plaza.getPlaza_Id(); 
			}	
			break;			
		}
	}	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<form action="AccionC" method="post" name="frmdocumento" target="_self">
<input type="hidden" name="Accion">
<div class="container-fluid">
<h1>Añadir Plaza</h1>
<div class="alert alert-info">
	<a href="Plaza?Institucion_Id=<%=Institucion_Id%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
</div>
<table style="width:50%" class="tabla"   bordercolor="#000000">
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong>Id:</strong></td>
          <td width="76%"><input name="Plaza_Id" type="text" class="text" id="Plaza_Id" size="5"  style="border:none;" disabled maxlength="40" value="<%=plaza.getPlaza_Id()%>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="Plaza_Nombre" type="text" class="text" id="Plaza_Nombre" size="40" maxlength="40" value="<%=plaza.getPlaza_Nombre()%>"></td>
        </tr>
        <tr> 
        <td><strong><spring:message code="aca.Institucion"/>:</strong></td>
          <td><input name="Institucion_Id" type="text" class="text" id="Institucion_Id" style="border:none;" disabled size="2" maxlength="40" value="<%=plaza.getInstitucion_Id()%>"></td>
        </tr>
        <tr> 
          <td colspan="2" align="center"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" align="center"> 
		  <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a> &nbsp; 
		  <a href="javascript:Modificar()" class="btn btn-primary"><spring:message code='aca.Modificar'/></a> &nbsp; 
		  <a href="javascript:Borrar()" class="btn btn-danger"><spring:message code='aca.Borrar'/></a> &nbsp;
		  <input name="Plaza_Id" type="hidden" value="<%=Plaza_Id%>">
		  <input name="Institucion_Id" type="hidden" value="<%=Institucion_Id%>">
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