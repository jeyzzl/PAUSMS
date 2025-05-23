<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.financiero.spring.FesCcobro"%>






<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%	

    java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 		= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String bloques 		= (String) session.getAttribute("bloques");
	
	List<CargaAlumno> listaCalculos 				= (List<CargaAlumno>) request.getAttribute("listaCalculos");
	HashMap<String, String> mapaNombreAlumno		= (HashMap<String, String>) request.getAttribute("mapaNombreAlumno");
	HashMap<String, String> mapaPlanes				= (HashMap<String, String>) request.getAttribute("mapaPlanes");
	HashMap<String,AlumAcademico> mapaEnCarga		= (HashMap<String, AlumAcademico>) request.getAttribute("mapaEnCarga");
	HashMap<String, FesCcobro> mapaCalculo			= (HashMap<String,FesCcobro>) request.getAttribute("mapaCalculo");
	HashMap<String,String> mapaPagos				= (HashMap<String,String>) request.getAttribute("mapaPagos");
	HashMap<String,String> mapaMaterias		    	= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String, String> mapaMateriasConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaMateriasConfirmadas");
	HashMap<String, String> mapaCreditosConfirmadas	= (HashMap<String,String>) request.getAttribute("mapaCreditosConfirmadas");
%>
<body>
<div class="container-fluid">
	<h2> Students with Cost Breakdown</h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="listado?CargaId=<%=cargaId%>&Bloques=<%=bloques%>" class="btn btn-primary">Return</a>
			
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>Student ID</th>
			<th>Name</th>
			<th>Plan</th>
			<th>Plan Name</th>
<!-- 			<th>Forma Pago</th> -->
<!-- 			<th class="right">Pago Inicial</th> -->
			<th>Subjects</th>
			<th>Sub. Selected</th>
			<th>Credits</th>
			<th>Boarding</th>	
			<th>Location</th>		
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno calculo : listaCalculos ){
		row++;
		
		String formaPago		= "-";
		if(mapaCalculo.containsKey(calculo.getCodigoPersonal()+calculo.getBloqueId())){
			formaPago 		= mapaCalculo.get(calculo.getCodigoPersonal()+calculo.getBloqueId()).getFormaPago();
		}
		
		double pagoInicial		= 0;
		String textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		if(mapaPagos.containsKey(calculo.getCodigoPersonal()+calculo.getBloqueId())){
			pagoInicial 		= Double.valueOf(mapaPagos.get(calculo.getCodigoPersonal()+calculo.getBloqueId()));
		}
		if (pagoInicial >= 0){
			textoPagoInicial = "<span class='badge bg-success'>"+getFormato.format(pagoInicial)+"</span>";
		}else{
			textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		}
		
		String nombreAlumno="";
		if (mapaNombreAlumno.containsKey(calculo.getCodigoPersonal())){
			nombreAlumno = mapaNombreAlumno.get(calculo.getCodigoPersonal());
		} 
		String nombrePlan="";
		if (mapaPlanes.containsKey(calculo.getPlanId())){
			nombrePlan = mapaPlanes.get(calculo.getPlanId());
		}
		
		String tipoResidencia 	= "-";
		String tipoRequerido 	= "";
		if(mapaEnCarga.containsKey(calculo.getCodigoPersonal())){
			tipoResidencia 	= mapaEnCarga.get(calculo.getCodigoPersonal()).getResidenciaId();
			tipoRequerido 	= mapaEnCarga.get(calculo.getCodigoPersonal()).getRequerido();
			if(tipoResidencia.equals("I")){
				tipoRequerido = tipoRequerido.equals("S")?"(Permanente)":"(Temporal)";
			}else{
				tipoRequerido = "";
			}
			
		}	
		
		String asistencia = "Online";
		if(calculo.getModo().equals("P")){
			asistencia = "Face to Face";
		}
		
		
		
		String materias = "<span class='badge bg-warning'>0</span>";
		if(mapaMaterias.containsKey(calculo.getCodigoPersonal()+calculo.getBloqueId())){
			materias = "<span class='badge bg-success'>"+mapaMaterias.get(calculo.getCodigoPersonal()+calculo.getBloqueId())+"</span>";
		}
		
		String confiramdas = "0";
		if(mapaMateriasConfirmadas.containsKey(calculo.getCodigoPersonal()+calculo.getBloqueId())){
			confiramdas = mapaMateriasConfirmadas.get(calculo.getCodigoPersonal()+calculo.getBloqueId());
		}
		
		String creditos = "0";
		if(mapaCreditosConfirmadas.containsKey(calculo.getCodigoPersonal()+calculo.getBloqueId())){
			creditos = mapaCreditosConfirmadas.get(calculo.getCodigoPersonal()+calculo.getBloqueId());
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=calculo.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>			
			<td><%=calculo.getPlanId()%></td>		
			<td><%=nombrePlan%></td>
<%-- 			<td><%=formaPago.equals("C")?"Contado": "Pagaré" %></td> --%>
<%-- 			<td class="right"><%=textoPagoInicial%></td> --%>
			<td><%=materias%></td>
			<td><%=confiramdas%></td>			
			<td><%=creditos%></td>			
			<td><%=tipoResidencia.equals("E")?"Day Student":"Boarding"%><!-- <%=tipoRequerido%> --></td>
			<td><%=asistencia%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>

</html>
