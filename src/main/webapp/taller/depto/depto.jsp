<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.financiero.spring.ContEjercicio"%>
<%@page import="aca.bec.spring.BecPeriodo"%>
<%@page import="aca.bec.spring.BecPlazas"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String ejercicioId			= (String)request.getAttribute("ejercicioId");
	String periodoId			= (String)request.getAttribute("periodoId");
	String filtro				= request.getParameter("Filtro")==null?"1.01":request.getParameter("Filtro");
	String usuarios 								= "";
	String basicas									= "";
	String industriales								= "";
	String temporales								= "";
	String preindustriales							= "";
	String posgrado									= "";
	String contacto									= "-";
	String correo									= "-";
	int cont 										= 0;	
	int sumaBasicas									= 0;
	int sumaIndustriales							= 0;
	int sumaTemporales								= 0;
	int sumaPreindustriales							= 0;
	int sumaPosgrado								= 0;
	int sumaFila									= 0;
	
	List<ContEjercicio> lisEjercicios 				= (List<ContEjercicio>)request.getAttribute("lisEjercicios");
	List<BecPeriodo> lisPeriodos 					= (List<BecPeriodo>)request.getAttribute("lisPeriodos");	
	List <ContCcosto> lisDepartamentos 				= (List<ContCcosto>)request.getAttribute("lisDepartamentos");
	HashMap<String, String> mapaAccesos				= (HashMap<String,String>)request.getAttribute("mapaAccesos");
	HashMap <String, BecPlazas> mapaPlazas			= (HashMap<String,BecPlazas>)request.getAttribute("mapaPlazas");
%>

<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
		body{
			background : white;
		}
	</style>
</head>	
<body>
<div class="container-fluid">
	<h2>Privilegios por Centro de Costo</h2>
	<form name="forma" action="depto" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Ejercicio:
		<select name="idEjercicio" id="idEjercicio" class="form-select" style="width:120px;" onchange="document.forma.submit()">
<%
	for(ContEjercicio ejercicio: lisEjercicios){	
%>
			<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
<%
	}
%>
		</select>&nbsp;					
		Periodo:
		<select name="periodoBecas" id="periodoBecas" class="form-select" style="width:250px;" onchange="document.forma.submit()">
<%
	for(BecPeriodo periodo: lisPeriodos){	
%>
			<option value="<%=periodo.getPeriodoId() %>" <%if(periodo.getPeriodoId().equals(periodoId))out.print("selected"); %>><%=periodo.getPeriodoNombre() %></option>
<%
	}
%>
		</select>&nbsp;			
		Filtro: <input name="Filtro" id="Filtro" class="form-control" value="<%=filtro%>" style="width:120px"/>&nbsp;
		<a href="javascript:Filtrar();" class="btn btn-primary"><i class="fas fa-filter"></i> Filtrar</a> 
	</div>
	<table id="table" class="table table-sm table-striped table-bordered">		 
	<thead class="table-info">
		<tr>
			<th width="3%">Op. </th>
			<th width="3%">#</th>
			<th width="5%"><spring:message code="aca.Costo"/></th>
			<th width="30%"><spring:message code="aca.Nombre"/></th>
			<th width="15%">Contacto</th>
			<th width="15%">Correo</th>
			<th width="5%">#Emp.</th>
			<th width="5%" title="Básicas" style="text-align: right;">Bas.</th>
			<th width="5%" title="Industriales" style="text-align: right;">Ind.</th>
			<th width="5%" title="Pre-Industriales" style="text-align: right;">Pre-Ind.</th>
			<th width="5%" title="Posgrado" style="text-align: right;">Pos.</th>
			<th width="5%" title="Temporales" style="text-align: right;">Temp.</th>
			<th width="10%" style="text-align: right;"><spring:message code="aca.Total"/></th>			
		</tr>
	</thead>
	<tbody>
<%	for(ContCcosto depto : lisDepartamentos){
	
		if (mapaAccesos.containsKey(depto.getIdCcosto())){ 
			usuarios = mapaAccesos.get(depto.getIdCcosto()); 
		}else{
			usuarios = "-";
		}
		int numEmpleados = usuarios.indexOf(",")+1;
		cont++;
		sumaFila 	= 0;	
		contacto 	= "-";
		correo		= "-";
		if(mapaPlazas.containsKey(depto.getIdCcosto())){			
			basicas 	 			= mapaPlazas.get(depto.getIdCcosto()).getNumPlazas();
			industriales 			= mapaPlazas.get(depto.getIdCcosto()).getNumIndustriales();
			preindustriales 		= mapaPlazas.get(depto.getIdCcosto()).getNumPreIndustriales();
			posgrado 				= mapaPlazas.get(depto.getIdCcosto()).getNumPosgrado();
			temporales 	 			= mapaPlazas.get(depto.getIdCcosto()).getNumTemporales();
			if (mapaPlazas.containsKey(depto.getIdCcosto())){
				contacto 				= mapaPlazas.get(depto.getIdCcosto()).getContacto();
				correo 					= mapaPlazas.get(depto.getIdCcosto()).getCorreo();
			}			
			sumaBasicas 			+= Integer.parseInt(basicas);
			sumaIndustriales		+= Integer.parseInt(industriales);
			sumaPreindustriales	 	+= Integer.parseInt(preindustriales);
			sumaPosgrado 			+= Integer.parseInt(posgrado);
			sumaTemporales  		+= Integer.parseInt(temporales);
			sumaFila	    		= Integer.parseInt(basicas)+Integer.parseInt(industriales)+Integer.parseInt(temporales)+Integer.parseInt(preindustriales)+Integer.parseInt(posgrado);
%>			

		<tr>
			<td align="center" style="cursor:pointer;" title="Editar plazas y temporales" onclick="location.href='plazas?Ccosto=<%=depto.getIdCcosto()%>'"><i class="fas fa-pencil-alt" style="color:green"></i></td>
			<td align="center"><%= cont %></td>
			<td align="center"><%=depto.getIdCcosto() %></td>
			<td><%= depto.getNombre()%></td>
			<td><%= contacto%></td>
			<td><%= correo%></td>
			<td align="center" onclick="location.href='acceso?Ccosto=<%=depto.getIdCcosto()%>'" class="empleados" style="cursor:pointer;" title="Ver nombre de empleados" >
			<span class="badge bg-dark"><%=numEmpleados%></span>
			</td>
			<td style="text-align: right;"><%=basicas%></td>
			<td style="text-align: right;"><%=industriales%></td>
			<td style="text-align: right;"><%=preindustriales%></td>
			<td style="text-align: right;"><%=posgrado%></td>
			<td style="text-align: right;"><%=temporales%></td>
			<td style="text-align: right;"><%=sumaFila%></td>				
<% 
		}else{
			basicas 			= "0";
			industriales 		= "0";
			preindustriales 	= "0";
			posgrado 			= "0";
			temporales 			= "0";
%>
		<tr>
			<td align="center" style="cursor:pointer;" title="Editar plazas y temporales" onclick="location.href='plazas?Ccosto=<%=depto.getIdCcosto()%>'"><i class="fas fa-pencil-alt" style="color:gray"></i></td>
			<td align="center"><%= cont %></td>
			<td align="center"><%=depto.getIdCcosto() %></td>
			<td><%= depto.getNombre()%></td>
			<td><%= contacto%></td>
			<td><%= correo%></td>
			<td align="center" onclick="location.href='acceso?Ccosto=<%=depto.getIdCcosto()%>'" class="empleados" style="cursor:pointer;" title="Ver nombre de empleados" >			
			<span class="badge bg-dark"><%=numEmpleados%></span>
			</td>
			<td style="text-align: right;"><%=basicas%></td>
			<td style="text-align: right;"><%=industriales%></td>
			<td style="text-align: right;"><%=preindustriales%></td>
			<td style="text-align: right;"><%=posgrado%></td>
			<td style="text-align: right;"><%=temporales%></td>
			<td style="text-align: right;"><%=sumaFila%></td>
			
<%		} %>			
		</tr>	
<%	} %>					
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>			
			<td colspan="2"><strong>TOTAL GENERAL</strong></td>
			<td style="text-align: right;"><%=sumaBasicas %></td>
			<td style="text-align: right;"><%=sumaIndustriales %></td>
			<td style="text-align: right;"><%=sumaPreindustriales %></td>
			<td style="text-align: right;"><%=sumaPosgrado%></td>
			<td style="text-align: right;"><%=sumaTemporales %></td>
			<td style="text-align: right;"><%=sumaBasicas+sumaIndustriales+sumaTemporales+sumaPreindustriales+sumaPosgrado%></td>
		</tr>
	</tbody>	
	</table>	
	</form>	
</div>	
</body>
</html>
<script>
	function Filtrar() {		
		if (forma.Filtro.value != "") {	
			document.forma.submit();
		}else{
			alert("El filtro no puede estar vacío!");
		}
	}
</script>