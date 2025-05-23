<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	List<Carga> lisCargas 				= (List<Carga>) request.getAttribute("lisCargas");	
	HashMap<String,String> mapaTotales 	= (HashMap<String,String>) request.getAttribute("mapaTotales"); 
%>
<div class="container-fluid">
	<h2>Cuenta las materias inscritas por alumno</h2>
	<div class="alert alert-info">
		<a href="grabar" class="btn btn-primary">Buscar y grabar</a>&nbsp;&nbsp;		
	</div>
	<table class="table table-sm table-bordered">
	<thead>	
  	<tr> 
    	<th width="10%">#</th>
		<th width="10%">Carga</th>
		<th width="60%">Nombre</th>
		<th width="10%" class="text-end">Secuencia</th>
		<th width="10%" class="text-end">Total</th>
  	</tr>
  	</thead>
  	<tbody>
<%
	int row = 0;
	for (Carga carga : lisCargas){
		row++;
		
		String total = "0";
		if (mapaTotales.containsKey(carga.getCargaId())){
			total = mapaTotales.get(carga.getCargaId());
		}
%>  	
	<tr> 
    	<td width="10%"><%=row%></td>
		<td width="10%"><%=carga.getCargaId()%></td>
		<td width="60%"><%=carga.getNombreCarga()%></td>
		<td width="10%" class="text-end"><%=carga.getSecuencia()%></td>
		<td width="10%" class="text-end"><%=total%></td>
  	</tr>
<%	} %>  	
  	</tbody>
</div>