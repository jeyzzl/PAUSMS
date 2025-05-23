<%@page import="java.util.List" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*" %>
<%@page import="aca.financiero.spring.ContEjercicio" %>
<%@page import="aca.bec.spring.BecInforme" %>
<%@page import="aca.financiero.spring.ContCcosto" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%	
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigo			= (String) session.getAttribute("codigoPersonal");
	
	String ejercicioId 		= (String)request.getAttribute("ejercicioId");
	String informeId 		= (String)request.getAttribute("informeId");	
	String acceso			= (String)request.getAttribute("acceso");
	boolean admin       	= (boolean)request.getAttribute("admin");
	String niveles			= (String)request.getAttribute("niveles");
	
	// Llena la lista de ejercicios
	List<ContEjercicio> lisEjercicios 		= (List<ContEjercicio>) request.getAttribute("lisEjercicios");	
	// Llena la lista de informes
	List<BecInforme> lisInformes 			= (List<BecInforme>) request.getAttribute("lisInformes");	
	// Llena la lista de departamentos
	List<ContCcosto> lisDeptos 				= (List<ContCcosto>) request.getAttribute("lisDeptos");
	
	HashMap <String, String> mapaEstados	= (HashMap<String,String>)request.getAttribute("mapaEstados");
	HashMap <String, String> mapaTotales 	= (HashMap<String,String>)request.getAttribute("mapaTotales");
	
	String estado1 		= "";
	String estado2 		= "";
	String estado3 		= "";
	String totalAlumnos = "";	
	String faltan 		="";
	int totalEstado1 =0, totalEstado2 =0, totalEstado3 =0, totalt =0, totalf =0;	
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
	.align-right{
		text-align:right !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />

<div class="container-fluid">
	<h2>Informe mensual</h2>
	<br />
	<form action="informemensual" name="forma" method="get">
	<input name="Accion" id="Accion" type="hidden" value="1">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
		<div style="float:right">
		<select name="EjercicioId" id="EjercicioId" style="width:150px;" onchange="document.forma.submit()">
		<%	for(ContEjercicio ejercicio: lisEjercicios){ %>
				<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
		<%	} %>
		</select> &nbsp; &nbsp;
		<select name="InformeId" id="InformeId" style="width:320px;" onchange="document.forma.submit()">
		<%
			for(BecInforme informe: lisInformes){
		%>
			<option value="<%=informe.getInformeId()%>" <%if(informeId.equals(informe.getInformeId()))out.print("selected"); %>><%=informe.getInformeNombre() %></option>
		<%
			}
		%>
		</select> &nbsp;	
		</div>
	</div>
	</form>	
	<table class="table puestosAlum" style="margin-left:0px;margin-bottom:0;">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Clave"/></th>
		<th>Centros de costos</th>
		<th class="text-end"># Alum.</th>
		<th class="text-end">Enviados</th>
		<th class="text-end">Autorizados</th>
		<th class="text-end">Contabilizados</th>			
		<th class="text-end">Faltan</th>
	</tr>
<%
	int cont = 0;
	for(ContCcosto depto : lisDeptos ){
		cont++;
		estado1		="0";
		estado2		="0";
		estado3		="0";
		totalAlumnos="0";
				
		if (mapaTotales.containsKey(depto.getIdCcosto())){
			totalAlumnos = mapaTotales.get(depto.getIdCcosto());
		}
		
		if (mapaEstados.containsKey(depto.getIdCcosto()+"1")){
			estado1 = mapaEstados.get(depto.getIdCcosto()+"1");
		}
		if(mapaEstados.containsKey(depto.getIdCcosto()+"2")){
			estado2 = mapaEstados.get(depto.getIdCcosto()+"2");
		}
		if(mapaEstados.containsKey(depto.getIdCcosto()+"3")){
			estado3 = mapaEstados.get(depto.getIdCcosto()+"3");
		}
		
		totalEstado1 	+= Integer.parseInt(estado1);
		totalEstado2 	+= Integer.parseInt(estado2);
		totalEstado3 	+= Integer.parseInt(estado3);
		totalt 			+= Integer.parseInt(totalAlumnos);
		faltan			= Integer.toString((Integer.parseInt(totalAlumnos))-(Integer.parseInt(estado3)));	
		totalf 			+= Integer.parseInt(faltan);
%>			
	<tr>
		<td><%=cont %></td>
		<td><%=depto.getIdCcosto() %></td>
		<td><a title="Informe de horas" href="alumnoshoras?DeptoId=<%=depto.getIdCcosto()%>&InformeId=<%=informeId%>&EjercicioId=<%=ejercicioId%>"> <%=depto.getNombre() %></a></td>
		<td class="align-right"><%=totalAlumnos%></td>
		<td class="align-right"><%=estado1 %></td>
		<td class="align-right"><%=estado2 %></td>
		<td class="align-right"><%=estado3 %></td>				
		<td class="align-right"><%=faltan%></td>					
	</tr>				
<%	} %>	
	<tr>					
		<th colspan="3"><b>T O T A L E S</b></th>
		<th class="align-right"><b><%=totalt%></b></th>
		<th class="align-right"><b><%=totalEstado1%></b></th>
		<th class="align-right"><b><%=totalEstado2%></b></th>
		<th class="align-right"><b><%=totalEstado3%></b></th>
		<th class="align-right"><b><%=totalf %></b></th>
	</tr>		
	</table>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
</script>