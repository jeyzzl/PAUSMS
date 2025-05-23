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
	<div class="alert alert-info">
		<a href="accionPor" class="btn btn-primary"><spring:message code="aca.Nuevo"/></a>
		<a class="btn btn-success" href="traspasoPortafolio">Traspasar <i class="icon-random icon-white"></i></a>
	</div> 
	</form>	
  	<table class="table table-sm table-bordered">
  	<tr class="table-info">
		<th align="center" width="5%"><spring:message code="aca.Op"/></th>
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
		<td align="center">
		<a href="accionPor?Accion=1&PorId=<%= por.getPorId() %>" class="btn btn-sm btn-success"><i class="fas fa-check-square"></i></a>
		</td>  
		<td align="left"><%= por.getPorId() %></td>  
		<td align="left"><a href="porSeccion?porId=<%=por.getPorId()%>"><%= por.getPorNombre() %></a></td>
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