<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorSeccionEmpU" scope="page" class="aca.por.PorSeccionEmpUtil"/>
<jsp:useBean id="PorRequisitoEmp" scope="page" class="aca.por.PorRequisitoEmp"/>
<jsp:useBean id="PorRequisitoEmpU" scope="page" class="aca.por.PorRequisitoEmpUtil"/>

<%	
	String porId 		= request.getParameter("porId")==null?"0":request.getParameter("porId");
	
	HashMap <String, String> mapReq = PorRequisitoEmpU.mapPorRequisitoEmp(conEnoc);
	
	// Lista de portafolios 
	ArrayList<String> listEmp	=  PorSeccionEmpU.getListEmp(conEnoc, porId);
%>
<div class="container-fluid">
	<h1><spring:message code="aca.ListaDeEmpleados"/></h1>
	
	<div class="alert alert-info">
		<a href="portafolio" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
	
  	<table class="table table-sm table-bordered">
  	<tr class="table-info">
    	<th align="left" width="5%">N°</th>
    	<th align="left" width="25%"><spring:message code="aca.Clave"/></th>
    	<th align="left" width="45%"><spring:message code="aca.Nombre"/></th>
    	<th align="left" width="20%"><spring:message code="aca.Requisitos"/></th>
  	</tr>
<% 
  	int row = 0;	
	for (String codigoPersonal : listEmp) {
  		  row++;
  		  
  		  String requisitos = "-";
  		  if(mapReq.containsKey(codigoPersonal)){
  			  requisitos = mapReq.get(codigoPersonal);
  		  }
  %>
	<tr>
		<td align="left"><%=row%></td>  
		<td align="left"><%=codigoPersonal%></td>
		<td align="left"><a href="datos?porId=<%=porId%>&codigoPersonal=<%=codigoPersonal%>"><%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoPersonal, "")%></a></td>
<%
		if (requisitos != "-"){		
%>
		<td align="left"><a href="requisitos?porId=<%=porId%>&codigoPersonal=<%=codigoPersonal%>"><%=requisitos%></a></td> 
<%		
		}else{
%>
		<td align="left"><%=requisitos%></td>
<%
		}
%>	
	</tr>	
<%	} %>  
  </table>  
</div>
<%@ include file= "../../cierra_enoc.jsp" %>