<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%  
	String cargaId 						= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");    
    List<CargaBloque> lisBloques    	= (List<CargaBloque>) request.getAttribute("lisBloques");
%>
<div class="container-fluid">
    <h2>Blocks in Load<small class="text-muted fs-5"> (<%=cargaId%>)</small></h2>
    <div class="alert alert-info">
        <a class="btn btn-primary" href="carga"><i class="fas fa-arrow-left"></i></a>
    </div>
   <table class="table table-bordered">
	<thead class="table-info">
    <tr>        
        <th><spring:message code="aca.Clave"/></th>
        <th><spring:message code="aca.Bloque"/></th>
        <th><spring:message code="aca.Inicio"/></th>
        <th><spring:message code="aca.Cierre"/></th>
        <th><spring:message code="aca.Fin"/></th>        
    </tr>
    </thead>
<%	
    for (CargaBloque bloque : lisBloques){                
%>               
    <tr class="tr2">         
        <td align="center"><%=bloque.getBloqueId()%></td>
        <td><%=bloque.getNombreBloque()%></td>
        <td align="center"><%=bloque.getFInicio()%></td>
        <td align="center"><%=bloque.getFCierre()%></td>   
        <td align="center"><%=bloque.getFFinal()%></td>
    </tr> 
<%         
    }
%>
    </table>
</div>