<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Tipo" scope="page" class="aca.musica.MusiTipoCta"/>

<script type="text/javascript">
	
	function Nuevo(){
		document.frmtipocta.TipoCtaId.value 		= " ";
		document.frmtipocta.TipoCtaNombre.value 	= " ";
		document.frmtipocta.Accion.value="1";
		document.frmtipocta.submit();
	}
	
	function Grabar(){
		if(document.frmtipocta.TipoCtaId.value!="" && document.frmtipocta.TipoCtaNombre!=""){
			document.frmtipocta.Accion.value="2";
			document.frmtipocta.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmtipocta.TipoCtaId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmtipocta.Accion.value="4";
				document.frmtipocta.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmtipocta.TipoCtaId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmtipocta.Accion.value="5";
		document.frmtipocta.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Tipo.setTipoCtaId(Tipo.maximoReg(conEnoc));
	else
		Tipo.setTipoCtaId(request.getParameter("TipoCtaId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			Tipo.setTipoCtaNombre(request.getParameter("TipoCtaNombre"));
			if (Tipo.existeReg(conEnoc) == false){
				if (Tipo.insertReg(conEnoc)){
					sResultado = "Grabado: "+Tipo.getTipoCtaId();					
				}else{
					sResultado = "No Grabó: "+Tipo.getTipoCtaId();
				}
			}else{
				if (Tipo.existeReg(conEnoc) == true){
					if (Tipo.updateReg(conEnoc)){
						sResultado = "Modificado: "+Tipo.getTipoCtaId();
					}else{
						sResultado = "No Cambió: "+Tipo.getTipoCtaId();
					}
				}else{
					sResultado = "No existe: "+Tipo.getTipoCtaId();
				}
			}
			
			break;
		}
		case 4: { // Borrar
			if (Tipo.existeReg(conEnoc) == true){
				if (Tipo.deleteReg(conEnoc)){
					sResultado = "Borrado: "+Tipo.getTipoCtaId();
				}else{
					sResultado = "No Borró: "+Tipo.getTipoCtaId();
				}	
			}else{
				sResultado = "No existe: "+Tipo.getTipoCtaId();
			}
			break;
		}
		case 5: { // Consultar			
			if (Tipo.existeReg(conEnoc) == true){
				Tipo.mapeaRegId(conEnoc, request.getParameter("TipoCtaId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Tipo.getTipoCtaId();
			}
			break;
		}
	}	
%>
<html>
<body>
<div class="container-fluid">
	<h2>Catalogo de Tipo de Cuenta</h2>
	<form action="accion" method="post" name="frmtipocta" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		<a class="btn btn-primary" href="tipo"><spring:message code="aca.Regresar"/></a> 
	</div>
<table style="width:50%" class="table">
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
          <td width="76%"><input name="TipoCtaId" type="text" class="text" id="TipoCtaId" size="3" maxlength="3" value="<%=Tipo.getTipoCtaId()%>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="TipoCtaNombre" type="text" class="text" id="TipoCtaNombre" size="40" maxlength="40" value="<%=Tipo.getTipoCtaNombre()%>"></td>
        </tr>
        <tr> 
          <td colspan="2"  style="text-align:left;"><%=sResultado%></td>
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