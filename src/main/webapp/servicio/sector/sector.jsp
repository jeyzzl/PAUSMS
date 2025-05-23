<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.ssoc.spring.SsocSector"%>

<%
	List<SsocSector> lisSector = (List<SsocSector>)request.getAttribute("lisSector");
	HashMap<String,String> mapaNumSector = (HashMap<String,String>)request.getAttribute("mapaNumSector");
%>
<!-- inicio de estructura -->
<body>
<div class="container-fluid">
	<h1>Catálogo de Sectores</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion"><spring:message code="aca.Anadir"/></a>
	</div>
	<table  style="width:100%" class="table table-condensed">
    <tr> 
    	<th width="16%" height="18"><spring:message code="aca.Operacion"/></th>
    	<th width="6%" height="18">Id</th>
    	<th width="35%" height="18"><spring:message code='aca.Descripcion'/></th>
    	<th width="35%" height="18">Num. Uso</th>
    </tr>
<%
	for (int i=0; i< lisSector.size(); i++){
		SsocSector sec = (SsocSector) lisSector.get(i);
		
		String numSector = "-";
		if (mapaNumSector.containsKey(sec.getSectorId())){
			numSector = mapaNumSector.get(sec.getSectorId());
		}
 %>  
    <tr> 
    	<td width="16%" align="center">
      		<a href="accion?SectorId=<%=sec.getSectorId()%>&SectorNombre=<%=sec.getSectorNombre()%> " title="<spring:message code="aca.Modificar"/>"><i class="fas fa-edit"></i></a>&nbsp;
      		<%if(numSector.equals("0")) {%>
      		<a href="javascript:Borrar('<%=sec.getSectorId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-times"></i></a>
      		<%}%>
    	<td width="6%" align="center"><font color="#000000" size="2"><%=sec.getSectorId()%></font></td>
    	<td width="55%">
<%--     		<a href="Institucion?SectorId=<%=sec.getSectorId()%>" title="Ir a <%=sec.getSectorNombre()%>"><%=sec.getSectorNombre()%></a></font> --%>
    		<%=sec.getSectorNombre()%>
    	</td>
    	<td><%=numSector%></td>
	</tr> 
<%
	}
%>
 	</table>
</div>
</body>