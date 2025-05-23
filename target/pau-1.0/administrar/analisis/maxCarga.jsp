<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	List<Carga> lisCargas 						= (List<Carga>)request.getAttribute("lisCargas");
	HashMap<String,String> mapaCargaFail		= (HashMap<String,String>)request.getAttribute("mapaCargaFail");
	HashMap<String,String> mapaCargaPanso		= (HashMap<String,String>)request.getAttribute("mapaCargaPanso");
	HashMap<String,String> mapaCargaLaLibras	= (HashMap<String,String>)request.getAttribute("mapaCargaLaLibras");
	HashMap<String,String> mapaCargaMatadito	= (HashMap<String,String>)request.getAttribute("mapaCargaMatadito");
%>
<div class="container-fluid">
	<h2>Notas máximas en cargas activas</h2>
	<div class="alert alert-info">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Carga Id</th>
		<th>Nombre Carga</th>
		<th style="text-align:right;">De 0-69</th>
		<th style="text-align:right;">De 70-79</th>
		<th style="text-align:right;">De 80-89</th>
		<th style="text-align:right;">De 90-100</th>
		<th style="text-align:right;">Total</th>			
	</tr>
	</thead>
	<tbody>
<%
	int cont			= 0;
   	int totalFail 		= 0;
	int totalPanso 		= 0;
	int totalLaLibras 	= 0;
	int totalMatadito 	= 0;
	int totalTotal		= 0;
	
	for(Carga carga : lisCargas){
		cont++;
		
		String fail = "0";
		if(mapaCargaFail.containsKey(carga.getCargaId())){
// 			fail = mapaCargaFail.get(carga.getCargaId());
			fail = String.valueOf(mapaCargaFail.get(carga.getCargaId()));
		}
		
		String panso = "0";
		if (mapaCargaPanso.containsKey(carga.getCargaId())){
// 			panso = mapaCargaPanso.get(carga.getCargaId());
			panso = String.valueOf(mapaCargaPanso.get(carga.getCargaId()));
		}
		
		String laLibras = "0";
		if (mapaCargaLaLibras.containsKey(carga.getCargaId())){
// 			laLibras = mapaCargaLaLibras.get(carga.getCargaId());
			laLibras = String.valueOf(mapaCargaLaLibras.get(carga.getCargaId()));
		}
		
		String matadito = "0";
		if (mapaCargaMatadito.containsKey(carga.getCargaId())){
// 			matadito = mapaCargaMatadito.get(carga.getCargaId());
			matadito = String.valueOf(mapaCargaMatadito.get(carga.getCargaId()));
		}
		
		String total= String.valueOf(Integer.parseInt(fail)+Integer.parseInt(panso)+Integer.parseInt(laLibras)+Integer.parseInt(matadito));
		
		totalFail		 = totalFail + Integer.parseInt(fail);
 		totalPanso		 = totalPanso + Integer.parseInt(panso);
 		totalLaLibras	 = totalLaLibras + Integer.parseInt(laLibras);
 		totalMatadito	 = totalMatadito + Integer.parseInt(matadito);
 		totalTotal 		 = totalTotal + Integer.parseInt(total);		
%>
	<tr>
		<td><%= cont %></td>
		<td><a href="maxFacultades?CargaId=<%=carga.getCargaId()%>" class="btn btn-info btn-sm"><%=carga.getCargaId()%></a></td>
		<td><%=carga.getNombreCarga()%></td>
		<td style="text-align:right;"><span class="badge <%=fail.equals("0")?"bg-secondary":"bg-warning"%>"><%=fail%></span></td>
		<td style="text-align:right;"><span class="badge <%=panso.equals("0")?"bg-secondary":"bg-info"%>"><%=panso%></span></td>
		<td style="text-align:right;"><span class="badge <%=laLibras.equals("0")?"bg-secondary":"bg-success"%>"><%=laLibras%></span></td>
		<td style="text-align:right;"><span class="badge <%=matadito.equals("0")?"bg-secondary":"bg-dark"%>"><%=matadito%></span></td>		
		<td style="text-align:right;"><%=total%></td>
	</tr>
<%	} %>
	<tr class="table-info">
		<td colspan="3" class="center" style="font-weight: bold;">Total</td>
		<td style="text-align:right;"><%=totalFail%></td>
		<td style="text-align:right;"><%=totalPanso%></td> 
		<td style="text-align:right;"><%=totalLaLibras%></td> 
		<td style="text-align:right;"><%=totalMatadito%></td> 
		<td style="text-align:right;"><%=totalTotal%></td>
 	</tr>
 	</tbody>	
	</table>
</div>