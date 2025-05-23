<%@page import="aca.leg.LegRequisitos"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Requisito" scope="page" class="aca.leg.LegRequisitos"/>
<jsp:useBean id="RequisitoUtil" scope="page" class="aca.leg.LegRequisitosUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmRequisito.RequisitoId.value 		= " ";
		document.frmRequisito.RequisitoNombre.value 	= " ";
		document.frmRequisito.Accion.value			= "1";
		document.frmRequisito.submit();		
	}
	
	function Grabar(){
		if(document.frmRequisito.RequisitoId.value!="" && document.frmRequisito.RequisitoNombre!=""  ){			
			document.frmRequisito.Accion.value="2";
			document.frmRequisito.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmRequisito.RequisitoId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmRequisito.Accion.value="4";
				document.frmRequisito.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmRequisito.CuentaId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmRequisito.Accion.value="5";
		document.frmRequisito.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado	= "";
	
	if ( nAccion == 1 )
		Requisito.setRequisitoId(RequisitoUtil.maximoReg(conEnoc));
	else
		Requisito.setRequisitoId(request.getParameter("RequisitoId"));
	
	Requisito.setRequisitoNombre(request.getParameter("RequisitoNombre"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			if (RequisitoUtil.existeReg(conEnoc, Requisito.getRequisitoId()) == false){
				if (RequisitoUtil.insertReg(conEnoc, Requisito)){
					sResultado = "Grabado: "+Requisito.getRequisitoId();					
				}else{
					sResultado = "No Grabó: "+Requisito.getRequisitoId();
				}
			}else{				
				if (RequisitoUtil.updateReg(conEnoc, Requisito)){
					sResultado = "Modificado: "+Requisito.getRequisitoId();					
				}else{
					sResultado = "No Cambió: "+Requisito.getRequisitoId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (RequisitoUtil.existeReg(conEnoc, Requisito.getRequisitoId()) == true){
				if (RequisitoUtil.deleteReg(conEnoc, Requisito.getRequisitoId())){
					sResultado = "Borrado: "+Requisito.getRequisitoId();					
				}else{
					sResultado = "No Borró: "+Requisito.getRequisitoId();
				}	
			}else{
				sResultado = "No existe: "+Requisito.getRequisitoId();
			}
			break;
		}
		case 5: { // Consultar
			if (RequisitoUtil.existeReg(conEnoc, Requisito.getRequisitoId()) == true){
				Requisito.mapeaRegId(conEnoc, request.getParameter("RequisitoId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Requisito.getRequisitoId();
			}
			break;
		}
	}	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<form action="accion" method="post" name="frmRequisito" target="_self">
<input type="hidden" name="Accion">
<table style="width:50%" border="1" align="center"   bordercolor="#000000">
  <tr>
  	  <th align="center"><font size="2">Catalogo de Requisitos [ <a href="requisito"><spring:message code="aca.Listado"/>:</a> 
        ]</font></th>
  </tr>
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
          <td width="76%"><input name="RequisitoId" type="text" class="text" id="RequisitoId" size="2" maxlength="2" value="<%=Requisito.getRequisitoId() %>"></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="RequisitoNombre" type="text" class="text" id="RequisitoNombre" size="20" maxlength="100" value="<%=Requisito.getRequisitoNombre() %>"></td>
        </tr>
      
        <tr> 
          <td colspan="2" align="center"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" align="center"> 
		  <a href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  <a href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
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