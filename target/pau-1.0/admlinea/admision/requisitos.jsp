<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AdmAlumReq" scope="page" class="aca.admision.AdmAlumReq"/>

<script type = "text/javascript">
	
	function Grabar(){
		if(document.frmRequisitos.Folio.value!=""){
			document.frmRequisitos.Accion.value="2";
			document.frmRequisitos.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmRequisitos.Folio.value!=""){
			if(confirm("Estás seguro de eliminar el registro!")==true){
	  			document.frmRequisitos.Accion.value="4";
				document.frmRequisitos.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmRequisitos.Paep.focus(); 
	  	}
	}	
</script>
<%
	// Declaracion de variables
	String folio			= request.getParameter("Folio"); 
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Consultar			
			AdmAlumReq.setFolio(folio);
			if (AdmAlumReq.existeReg(conEnoc) == true){
				AdmAlumReq.mapeaRegId(conEnoc, folio);
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+AdmAlumReq.getFolio(); 
			}	
			break;
		}		
		case 2: { // Grabar
			AdmAlumReq.setFolio(folio);			
			AdmAlumReq.setPaep(request.getParameter("Paep"));
			AdmAlumReq.setPhca(request.getParameter("Phca"));
			if (request.getParameter("Prerrequisito")==null || request.getParameter("Prerrequisito").equals("null") ||
					request.getParameter("Prerrequisito").equals(""))
				AdmAlumReq.setPrerrequisito("N");
			else
				AdmAlumReq.setPrerrequisito(request.getParameter("Prerrequisito"));
			AdmAlumReq.setPromLic(request.getParameter("PromLic"));
			AdmAlumReq.setPromMae(request.getParameter("PromMae"));
			AdmAlumReq.setServicio(request.getParameter("Servicio"));
			
			if (AdmAlumReq.existeReg(conEnoc) == false){
				if (AdmAlumReq.insertReg(conEnoc)){
					sResultado = "Grabado: "+AdmAlumReq.getFolio();
				}else{
					sResultado = "No Grabó: "+AdmAlumReq.getFolio();
				}
			}else{
				if (AdmAlumReq.updateReg(conEnoc)){
					sResultado = "Grabado: "+AdmAlumReq.getFolio();
				}else{
					sResultado = "No Grabó: "+AdmAlumReq.getFolio();
				}				
			}
			break;
		}		
		case 4: { // Borrar			
			AdmAlumReq.setFolio(folio);	
			if (AdmAlumReq.existeReg(conEnoc) == true){
				if (AdmAlumReq.deleteReg(conEnoc)){
					sResultado = "Borrado: "+AdmAlumReq.getFolio();
				}else{
					sResultado = "No Borró: "+AdmAlumReq.getFolio();
				}	

			}else{
				sResultado = "No existe: "+AdmAlumReq.getFolio();
			}			
			break;
		}		
	}
%>
<table style="width:40%" align="center"  >
<tr><td align="center"><font size="3"><b><spring:message code="aca.Solicitante"/></b></font></td></tr>
<tr><td align="center"><%= aca.admision.AdmSolicitud.getNombre(conEnoc,folio) %></td></tr>
<tr><td align="center">&nbsp;</td></tr>
<tr><td align="center"><a href="proceso">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr>
</table>
<form action="requisitos" method="post" name="frmRequisitos" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="Folio" value="<%= folio %>">
<table style="width:40%" border="1" align="center"   bordercolor="#000000">
  <tr>
   	  <th align="center"><spring:message code="aca.RequisitosDelAlumno"/></th>
  </tr>
  <tr>
    <td>
	  <table style="width:100%"  class="tabla"">
	  	  <tr> 
            <td><strong><spring:message code='aca.Prerrequisito'/>:</strong></td>
            <td><input name="Prerrequisito" type="text" class="text" id="Prerrequisito" value="<%=AdmAlumReq.getPrerrequisito()%>" size="5" maxlength="30" readonly></td>
          </tr>	
          <tr> 
            <td><strong><spring:message code='aca.Paep'/>:</strong></td>
            <td><input name=Paep type="text" class="text" id="Paep" value="<%=AdmAlumReq.getPaep()%>" size="5" maxlength="3"></td>
          </tr>          
          <tr> 
            <td><strong><spring:message code='aca.Phca'/>:</strong></td>
            <td><input name="Phca" type="text" class="text" id="Phca" value="<%=AdmAlumReq.getPhca()%>" size="5" maxlength="3"></td>
          </tr>          
          <tr> 
            <td><strong><spring:message code='aca.PromLic'/>:</strong></td>
            <td><input name="PromLic" type="text" class="text" id="PromLic" value="<%=AdmAlumReq.getPromLic()%>" size="5" maxlength="3"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code='aca.PromMae'/>:</strong></td>
            <td><input name="PromMae" type="text" class="text" id="PromMae" value="<%=AdmAlumReq.getPromMae() %>" size="5" maxlength="3"></td>
          </tr>
          <tr> 
            <td><strong><spring:message code='aca.Servicio'/>:</strong></td>
            <td><input name="Servicio" type="text" class="text" id="Servicio" value="<%=AdmAlumReq.getServicio()%>" size="5" maxlength="3"></td>
          </tr>
          <tr> 
            <td colspan="2" align="center"><%=sResultado%></td>
          </tr>
          <tr> 
            <th colspan="2" align="center"> 
              &nbsp;<a href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; <a href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a>
            </th>
          </tr>
        </table>
	</td>
  </tr>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp"%>