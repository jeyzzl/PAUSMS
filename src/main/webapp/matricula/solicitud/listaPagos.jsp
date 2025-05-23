<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.carga.spring.CargaBloque"%>



<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%	
    java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String bloques 		= (String) session.getAttribute("bloques");
	
	List<CargaAlumno> lisPagos 					= (List<CargaAlumno>) request.getAttribute("lisPagos");
	HashMap<String, String> mapaNombreAlumno	= (HashMap<String, String>) request.getAttribute("mapaNombreAlumno");
	HashMap<String, String> mapaPlanes			= (HashMap<String, String>) request.getAttribute("mapaPlanes");
	HashMap<String, FesCcobro> mapaCalculo			= (HashMap<String,FesCcobro>) request.getAttribute("mapaCalculo");
	HashMap<String,String> mapaPagos				= (HashMap<String,String>) request.getAttribute("mapaPagos");
	HashMap<String,AlumAcademico> mapaEnCarga	= (HashMap<String, AlumAcademico>) request.getAttribute("mapaEnCarga");
	
%>
<body>
<div class="container-fluid">
	<h2>Listado de Alumnos en Pagos</h2>
	<form name="frmProceso" action="listado" method="post">	
		<div class="alert alert-info">
			<a href="listado?CargaId=<%=cargaId%>&Bloques=<%=bloques%>" class="btn btn-primary">Regresar</a>
		</div>	
	</form>
	<table style="width:70%" class="table table-sm table-bordered">
	<thead class="table-info"> 
		<tr class="noHover">
			<th>#</th>
			<th>Matricula</th>
			<th>Nombre Alumno</th>
			<th>Plan</th>
			<th>Nombre Plan</th>
			<th>Forma Pago</th>
			<th class="right">Pago Inicial</th>	
			<th>Internado</th>	
			<th>Ubicación</th>				
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno pago : lisPagos){
		row++;
		
		String formaPago		= "-";
		if(mapaCalculo.containsKey(pago.getCodigoPersonal()+pago.getBloqueId())){
			formaPago 		= mapaCalculo.get(pago.getCodigoPersonal()+pago.getBloqueId()).getFormaPago();
		}
		
		double pagoInicial		= 0;
		String textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		if(mapaPagos.containsKey(pago.getCodigoPersonal()+pago.getBloqueId())){
			pagoInicial 		= Double.valueOf(mapaPagos.get(pago.getCodigoPersonal()+pago.getBloqueId()));
		}
		if (pagoInicial >= 0){
			textoPagoInicial = "<span class='badge bg-success'>"+getFormato.format(pagoInicial)+"</span>";
		}else{
			textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		}
		
		String alumno="-";
		if(mapaNombreAlumno.containsKey(pago.getCodigoPersonal())){
			alumno = mapaNombreAlumno.get(pago.getCodigoPersonal());
		}
		
		String nombrePlanId ="-";
		if(mapaPlanes.containsKey(pago.getPlanId())){
			nombrePlanId = mapaPlanes.get(pago.getPlanId());
		}
		
		String tipoResidencia 	= "-";
		String tipoRequerido 	= "";
		if(mapaEnCarga.containsKey(pago.getCodigoPersonal())){
			tipoResidencia 	= mapaEnCarga.get(pago.getCodigoPersonal()).getResidenciaId();
			tipoRequerido 	= mapaEnCarga.get(pago.getCodigoPersonal()).getRequerido();
			if(tipoResidencia.equals("I")){
				tipoRequerido = tipoRequerido.equals("S")?"(Permanente)":"(Temporal)";
			}else{
				tipoRequerido = "";
			}
		}
		
		String asistencia = "Hogar/Virtual";
		if(pago.getModo().equals("P")){
			asistencia = "Campus UM";
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=pago.getCodigoPersonal()%></td>
			<td><%=alumno%></td>			
			<td><%=pago.getPlanId()%></td>		
			<td><%=nombrePlanId%></td>
			<td><%=formaPago.equals("C")?"Contado": "Pagaré" %></td>
			<td class="right"><%=textoPagoInicial%></td>			
			<td><%=tipoResidencia.equals("E")?"Externo":"Interno"%><%=tipoRequerido%></td>
			<td><%=asistencia%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
</html>
