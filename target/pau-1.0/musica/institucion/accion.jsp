<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Institucion" scope="page" class="aca.musica.MusiInstitucion"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frminstitucion.InstitucionId.value 		= " ";
		document.frminstitucion.InstitucionNombre.value 	= " ";
		document.frminstitucion.Accion.value="1";
		document.frminstitucion.submit();		
	}
	
	function Grabar(){
		if(document.frminstitucion.InstitucionId.value!="" && document.frminstitucion.InstitucionNombre!=""){			
			document.frminstitucion.Accion.value="2";
			document.frminstitucion.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frminstitucion.InstitucionId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frminstitucion.Accion.value="4";
				document.frminstitucion.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frminstitucion.InstitucionId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frminstitucion.Accion.value="5";
		document.frminstitucion.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Institucion.setInstitucionId(Institucion.maximoReg(conEnoc));
	else
		Institucion.setInstitucionId(request.getParameter("InstitucionId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			Institucion.setInstitucionNombre(request.getParameter("InstitucionNombre"));
			if (Institucion.existeReg(conEnoc) == false){
				if (Institucion.insertReg(conEnoc)){
					sResultado = "Grabado: "+Institucion.getInstitucionId();					
				}else{
					sResultado = "No Grabó: "+Institucion.getInstitucionId();
				}
			}else{
				if (Institucion.existeReg(conEnoc) == true){
					if (Institucion.updateReg(conEnoc)){
						sResultado = "Modificado: "+Institucion.getInstitucionId();						
					}else{
						sResultado = "No Cambió: "+Institucion.getInstitucionId();
					}
				}else{
					sResultado = "No existe: "+Institucion.getInstitucionId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (Institucion.existeReg(conEnoc) == true){
				if (Institucion.deleteReg(conEnoc)){
					sResultado = "Borrado: "+Institucion.getInstitucionId();					
				}else{
					sResultado = "No Borró: "+Institucion.getInstitucionId();
				}	
			}else{
				sResultado = "No existe: "+Institucion.getInstitucionId();
			}
			break;
		}
		case 5: { // Consultar			
			if (Institucion.existeReg(conEnoc) == true){
				Institucion.mapeaRegId(conEnoc, request.getParameter("InstitucionId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Institucion.getInstitucionId(); 
			}	
			break;			
		}
	}	
%>
<html>
<body>
<div class="container-fluid">
	<h2>Catalogo de Instituciones</h2>
	<form action="accion" method="post" name="frminstitucion" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		<a class="btn btn-primary" href="institucion"><spring:message code="aca.Regresar"/></a> 
	</div>
<table style="width:50%" class="table">
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
          <td width="76%"><input name="InstitucionId" type="text" class="text" id="InstitucionId" size="3" maxlength="3" value="<%=Institucion.getInstitucionId()%>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="InstitucionNombre" type="text" class="text" id="InstitucionNombre" size="40" maxlength="40" value="<%=Institucion.getInstitucionNombre()%>"></td>
        </tr>
        <tr> 
          <td colspan="2" align="left"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" style="text-align:left;"> 
		  <a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
		  <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		  <a class="btn btn-primary" href="javascript:Consultar()"><spring:message code='aca.Consultar'/></a>
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>