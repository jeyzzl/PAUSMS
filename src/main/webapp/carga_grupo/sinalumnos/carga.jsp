<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import= "aca.carga.spring.Carga" %>
<%@page import= "aca.carga.spring.CargaGrupo" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String cargaId 								= request.getParameter("CargaId");
	List<Carga> lisCarga 						= (List<Carga>) request.getAttribute("lisCarga");
	HashMap<String, String> mapaMaterias    	= (HashMap<String, String>) request.getAttribute("mapaMaterias");		
%>

<div class="container-fluid">
	<h2>Subjects without students</h2>
	<div class="alert alert-info">
		<input type="text" class="form-control search-query" placeholder="<spring:message code='aca.Buscar...'/>" id="buscar" style="width:200px;">
	</div>
	<table class="table table-bordered" id="table">
	<thead class="table-info">
	<tr>	
		<th width="10%"><h5><spring:message code="aca.Ciclo"/></h5></th>
	    <th width="25%"><h5><spring:message code="aca.Carga"/></h5></th>
	    <th width="60%"><h5><spring:message code="aca.Nombre"/></h5></th>
	    <th width="5%" class="right"><h5><spring:message code="cargaGrupo.sinAlumnos.#Mat"/></h5></th>
	</tr>
	</thead>
	<tbody>
  <% 
		  			
 	int row = 0;  
  	for(Carga carga : lisCarga){		
		row++;
		
		String numMat 	= "0";
		if (mapaMaterias.containsKey(carga.getCargaId())){
			numMat 		= mapaMaterias.get(carga.getCargaId());
		}		
%>
		<tr> 
    		<td><div align="center"><%=carga.getCiclo()%></div></td>
    		<td><div align="left"><%=carga.getCargaId()%></div></td>			
    		<td><a href="sinalumno?CargaId=<%=carga.getCargaId()%>"><%=carga.getNombreCarga()%></a></td>
    		<td class="right"><%=numMat%></td>
  		</tr>
<%		 
	} //fin del for 
%>
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
</script>