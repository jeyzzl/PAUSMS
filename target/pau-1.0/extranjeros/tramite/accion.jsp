<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Tramite" scope="page" class="aca.leg.LegTramite"/>
<jsp:useBean id="TramiteU" scope="page" class="aca.leg.LegTramiteUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmTramite.TramiteId.value 		= " ";
		document.frmTramite.TramiteNombre.value 	= " ";
		document.frmTramite.Costo.value 	= " ";
		document.frmTramite.Accion.value="1";
		document.frmTramite.submit();		
	}
	
	function Grabar(){
		if(document.frmTramite.TramiteId.value!="" && document.frmTramite.TramiteNombre!="" && document.frmTramite.Costo!="" ){			
			document.frmTramite.Accion.value="2";
			document.frmTramite.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Modificar(){
		document.frmTramite.Accion.value="3";
		document.frmTramite.submit();
	}
	
	function Borrar( ){
		if(document.frmTramite.TramiteId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmTramite.Accion.value="4";
				document.frmTramite.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmTramite.TramiteId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmTramite.Accion.value="5";
		document.frmTramite.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Tramite.setTramiteId(Tramite.maximoReg(conEnoc));
	else
		Tramite.setTramiteId(request.getParameter("TramiteId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			Tramite.setTramiteNombre(request.getParameter("TramiteNombre"));
		    Tramite.setCosto(request.getParameter("Costo"));
			if (Tramite.existeReg(conEnoc) == false){
				if (TramiteU.insertReg(conEnoc, Tramite)){
					sResultado = "Grabado: "+Tramite.getTramiteId();					
				}else{
					sResultado = "No Grabó: "+Tramite.getTramiteId();
				}
			}else{
				sResultado = "Ya existe: "+Tramite.getTramiteId();
			}
			
			break;
		}
		case 3: { // Modificar
			Tramite.setTramiteNombre(request.getParameter("TramiteNombre"));
			Tramite.setCosto(request.getParameter("Costo"));
			if (Tramite.existeReg(conEnoc) == true){
				if (TramiteU.updateReg(conEnoc, Tramite)){
					sResultado = "Modificado: "+Tramite.getTramiteId();					
				}else{
					sResultado = "No Cambió: "+Tramite.getTramiteId();
				}
			}else{
				sResultado = "No existe: "+Tramite.getTramiteId();
			}
			break;
		}
		case 4: { // Borrar
			if (Tramite.existeReg(conEnoc) == true){
				if (TramiteU.deleteReg(conEnoc,Tramite.getTramiteId())){
					sResultado = "Borrado: "+Tramite.getTramiteId();					
				}else{
					sResultado = "No Borró: "+Tramite.getTramiteId();
				}	
			}else{
				sResultado = "No existe: "+Tramite.getTramiteId();
			}
			break;
		}
		case 5: { // Consultar			
			if (Tramite.existeReg(conEnoc) == true){
				Tramite.mapeaRegId(conEnoc, request.getParameter("TramiteId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Tramite.getTramiteId(); 
			}	
			break;			
		}
	}	
 %>

<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<form action="accion" method="post" name="frmTramite" target="_self">
<input type="hidden" name="Accion">
<table style="width:50%" border="1" align="center"   bordercolor="#000000">
  <tr>
  	  <th align="center"><font size="2">Catalogo de Tr&aacute;mites [ <a href="tramite"><spring:message code="aca.Listado"/>:</a> 
        ]</font></th>
  </tr>
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
          <td width="76%"><input name="TramiteId" type="text" class="text" id="TramiteId" size="2" maxlength="2" value="<%=Tramite.getTramiteId() %>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="TramiteNombre" type="text" class="text" id="TramiteNombre" size="30" maxlength="50" value="<%=Tramite.getTramiteNombre() %>"></td>
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Costo"/></strong></td>
          <td><input name="Costo" type="text" class="text" id="Costo" size="5" maxlength="8" value="<%=Tramite.getCosto() %>"></td>
        </tr>
        <tr> 
          <td colspan="2" align="center"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" align="center"> 
		  <a href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  <a href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; 
		  <a href="javascript:Modificar()"><spring:message code='aca.Modificar'/></a> &nbsp; 
		  <a href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		  <a href="javascript:Consultar()"><spring:message code='aca.Consultar'/></a>
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>