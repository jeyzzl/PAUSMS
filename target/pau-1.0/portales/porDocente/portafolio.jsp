<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menuPortal.jsp" %>

<jsp:useBean id="PorDocumentoU" scope="page" class="aca.por.PorDocumentoUtil"/>

<script type="text/javascript">
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%
	String codigoPersonal = (String) session.getAttribute("codigoPersonal");
	
	// Lista de portafolios con estado activo
	ArrayList<aca.por.PorDocumento> lisPortafolio	=  PorDocumentoU.listPorEstado(conEnoc, "'A'"," ORDER BY POR_ID");
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Portafolio"/><small class="text-muted fs-4">( <%=codigoPersonal%>-<%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, codigoPersonal, "NOMBRE") %>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-success" href="traspasoPortafolio.jsp">Traspasar <i class="icon-random icon-white"></i></a>
	</div>
  	<table style="width:80%"  class="table table-condensed">
  	<tr>
		<th align="center" width="5%"><spring:message code="aca.Numero"/></th>
    	<th align="left" width="5%"><spring:message code="aca.Clave"/></th>
    	<th align="left" width="90%"><spring:message code="aca.Portafolio"/></th>
  	</tr>
  <% 
  	int row = 0;	
	for (aca.por.PorDocumento por : lisPortafolio) {
  		  row++;		
  %>
	<tr>
		<td align="center"><%= row %></td>  
		<td align="left"><%= por.getPorId() %></td>  
<%-- 		<td align="left"><a href="seccion.jsp?PortafolioId=<%=por.getPorId()%>"><%= por.getPorNombre() %></a></td> --%>
		<td align="left"><a href="seccion?PortafolioId=<%=por.getPorId()%>"><%= por.getPorNombre() %></a></td>
	</tr>	
<%	} %>  
  </table>  
</div>
<script>
	jQuery('.portafolio').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp" %>