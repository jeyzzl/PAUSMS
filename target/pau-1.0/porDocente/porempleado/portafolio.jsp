<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorDocumentoU" scope="page" class="aca.por.PorDocumentoUtil"/>

<script type="text/javascript">
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");

	// Lista de portafolios 
	ArrayList<aca.por.PorDocumento> lisPortafolio	=  PorDocumentoU.getListAll(conEnoc, " ORDER BY POR_ID");
%>
<div class="container-fluid">
	<h1><spring:message code="aca.Portafolios"/></h1>
	<form name="frmPortafolio" action="portafolio" method="post" >
	<input type="hidden" name="Accion">
	</form>	
  	<table class="table table-sm table-bordered">
  	<tr class="table-info">
    	<th align="left" width="5%"><spring:message code="aca.Clave"/></th>
    	<th align="left" width="45%"><spring:message code="aca.Portafolio"/></th>
    	<th align="left" width="45%"><spring:message code="aca.Estado"/></th>
  	</tr>
  <% 
  	int row = 0;	
	for (aca.por.PorDocumento por : lisPortafolio) {
  		  row++;		
  %>
	<tr>
		<td align="left"><%= por.getPorId() %></td>  
		<td align="left"><a href="listaEmp?porId=<%=por.getPorId()%>"><%= por.getPorNombre() %></a></td>
		<td align="left">
		<%if(por.getEstado().equals("A")){ %>
			Activo
		<%}else { %>
			Inactivo
		<%} %>
		
		</td>
	</tr>	
<%	} %>  
  </table>  
</div>
<%@ include file= "../../cierra_enoc.jsp" %>