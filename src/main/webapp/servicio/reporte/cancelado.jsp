<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.ssoc.spring.SsocInicio"%>


<% 

	List<SsocInicio> lisCancelado	= (List<SsocInicio>) request.getAttribute("lisCancelado");
	
	HashMap<String,String> mapaAlumnos		 		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaDocumentos		 	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
	
%>
<body>
<div class="container-fluid">
	<h2>Alumnos con Servicio Social Terminado</h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>
	</div>  
	<table style="margin: 0 auto; width:100%" class="table table-bordered"> 
	  <tr> 
	    <th width="2%"><spring:message code="aca.Numero"/></th>
	    <th width="7%"><spring:message code="aca.Matricula"/></th>
	    <th width="32%"><spring:message code="aca.Nombre"/></th>
	    <th width="10%"> Plan </th>
	    <th width="10%"> F. Inicio </th>
	    <th width="10%"> F. Cancelado </th>
	    <th width="5%">Porcentaje</th>
	    <th width="7%">Semestre</th>
	  </tr>
<%
	int row = 0;
	for (SsocInicio inicio : lisCancelado){
		row++;
		
		String alumnoNombre = "-";
		if(mapaAlumnos.containsKey(inicio.getCodigoPersonal())){
			alumnoNombre	= mapaAlumnos.get(inicio.getCodigoPersonal());
		}
		
		String fechaDocumento = "-";
		if(mapaDocumentos.containsKey(inicio.getCodigoPersonal()+inicio.getPlanId())){
			fechaDocumento = mapaDocumentos.get(inicio.getCodigoPersonal()+inicio.getPlanId());
		}
%>
	  <tr> 
	    <td align="center"><font size="1"><%=row%></font></td>
	    <td align="center"><font size="1"><%=inicio.getCodigoPersonal()%></font></td>
	    <td align="left"><font size="1"><%=alumnoNombre%></font></td> 
	    <td align="center"><font size="1"><%=inicio.getPlanId()%></font></td>
	    <td align="center"><font size="1"><%=inicio.getFecha()%></font></td>
	    <td align="center"><font size="1"><%=fechaDocumento%></font></td>
	    <td align="center"><font size="1"><%=inicio.getPorcentaje()%>%</font></td>
	    <td align="center"><font size="1"><%=inicio.getSemestre()%></font></td>
	  </tr>
<% 
}
%>
	</table>
</div>
</body>