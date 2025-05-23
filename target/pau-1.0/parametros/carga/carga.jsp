<%@page import="aca.carga.spring.CargaDao"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function SubirCarga( cargaId ){
	  	document.location.href="subir?CargaId="+cargaId;
	}	
</script> 
<%

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");		
	String cargaId 				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String mensaje				= request.getParameter("Mensaje")==null?"�Elige una carga!":request.getParameter("Mensaje");	
	
	List<Carga> lisCarga		= (List<Carga>)request.getAttribute("lisCarga");	
	boolean desbloquea 			= codigoPersonal.equals("9800058")?true:false;	
%>
<body>
<div class="container-fluid">
	<h2>List of Academic Loads</h2>
	<div class="alert alert-info d-flex align-items-center">
		Filter:&nbsp;<input id="buscar" type="text" class="search-query form-control" placeholder="Search..." style="width:170px;">
<%	if (!mensaje.equals("-")){%>
		<strong>&nbsp;&nbsp;&nbsp;Message: </strong><%=mensaje%>
<%	} %>		
	</div>	
	<table id="table" class="table table-sm table-bordered table-striped">
	<thead class="table-info">
	<tr>
		<th width="4%" align="center"><h5><spring:message code="aca.Numero"/></h5></th>
		<th width="5%" align="center"><h5><spring:message code="aca.Carga"/></h5></th>
		<th width="39%" align="center"><h5><spring:message code="aca.Nombre"/></h5></th>
		<th width="9%" align="center"><h5>Period</h5></th>
		<th width="13%" align="center"><h5>Start</h5></th>
		<th width="12%" align="center"><h5>End</h5></th>
		<th width="12%" align="center"><h5>Extra.</h5></th>
<%	if(desbloquea){%> 
		<th width="12%" align="center"><h5>End Serv.</h5></th>
<%	}%>					
		<th width="7%" align="center"><h5><spring:message code="aca.Estado"/></h5></th>
	</tr>
	</thead>
	<tbody>
<%
	int cont = 0;
	for (Carga carga : lisCarga){
		cont++;
%>
	<tr> 
		<td align="center">	  
			<%=cont%>
		</td>
		<td align="center"><%=carga.getCargaId()%></td>
		<td>
			<a href="javascript:SubirCarga('<%=carga.getCargaId()%>')">
		 	<%=carga.getNombreCarga()%>
			</a>
		</td>
		<td align="center"><%=carga.getPeriodo()%></td>
		<td align="center"><%=carga.getFInicio() %></td>
		<td align="center"><%=carga.getFFinal()%></td>
		<td align="center"><%=carga.getFExtra()%></td>
<%
		if(desbloquea){
			String temp="";
			String fechaServicios = carga.getFinServicios();		
			if (fechaServicios.equals("X")){ 
				temp = "<i class='icon-pencil'></i>"; 
			}else{ 
				temp= fechaServicios;
			} 
%> 
		<td align="center"><a href="editar?CargaId=<%=carga.getCargaId()%>" class="button"><%=temp%></a></td>
<%		} %>				
		<td align="center">
<%		if (carga.getEstado().equals("1")) out.print("Activa"); else out.print("Cerrada"); %>
		</td>
	</tr>  
<%	} %> 
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>