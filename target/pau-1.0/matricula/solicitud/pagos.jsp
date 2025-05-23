<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String cargaId 				= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String facultadId 			= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");	
	String facultadNombre 		= (String) request.getAttribute("facultadNombre");
	String bloques		 		= (String) request.getAttribute("bloques");
	
	List<CargaAlumno> lisPagos 						= (List<CargaAlumno>) request.getAttribute("lisPagos");
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CargaBloque> mapaBloques			= (HashMap<String,CargaBloque>) request.getAttribute("mapaBloques");
	HashMap<String, FesCcobro> mapaCalculo			= (HashMap<String,FesCcobro>) request.getAttribute("mapaCalculo");
	HashMap<String,String> mapaPagos				= (HashMap<String,String>) request.getAttribute("mapaPagos");
	HashMap<String,String> mapaMaterias				= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String, AlumAcademico> mapaEnCarga		= (HashMap<String,AlumAcademico>) request.getAttribute("mapaEnCarga");
	
%>
<body>
<div class="container-fluid">
	<h1>Alumnos en pago<small class="text-muted fs-4">( <%=facultadNombre%> )</small></h1>
	<form name="frmProceso" action="pagos" method="post">	
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<div class="alert alert-info">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;		
		<a href="materias?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>&Bloques=<%=bloques%>" class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></a>    
	</div>	
	</form>
	<table class="table table-fullcondensed table-bordered">
	<thead class="table-info">
		<tr class="noHover">
			<th>#</th>
			<th>Matricula</th>
			<th>Alumno</th>			
			<th>Plan</th>	
			<th>Carrera</th>
			<th>Bloque</th>
			<th>Forma Pago</th>
			<th class="right">Pago Inicial</th>
			<th>Materias</th>
			<th>Residencia</th>
			<th>Ubicación</th>
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	for(CargaAlumno carga : lisPagos){
		row++;
		
		String planNombre 	= "<span class='badge bg-secondary'>0</span>";
		if(mapaPlanes.containsKey(carga.getPlanId())){
			planNombre 		= mapaPlanes.get(carga.getPlanId()).getCarreraSe();
		}
		
		String alumnoNombre 	= "<span class='badge bg-secondary'>0</span>";		
		if(mapaAlumnos.containsKey(carga.getCodigoPersonal())){
			alumnoNombre 		= mapaAlumnos.get(carga.getCodigoPersonal());
		}
		
		String bloqueNombre 	= "-";
		if(mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloqueNombre 		= mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
		String formaPago		= "-";
		if(mapaCalculo.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			formaPago 		= mapaCalculo.get(carga.getCodigoPersonal()+carga.getBloqueId()).getFormaPago();
		}
		
		double pagoInicial		= 0;
		String textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		if(mapaPagos.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			pagoInicial 		= Double.valueOf(mapaPagos.get(carga.getCodigoPersonal()+carga.getBloqueId()));
		}
		if (pagoInicial >= 0){
			textoPagoInicial = "<span class='badge bg-success'>"+getFormato.format(pagoInicial)+"</span>";
		}else{
			textoPagoInicial = "<span class='badge bg-warning'>"+getFormato.format(pagoInicial)+"</span>";
		}
		
		String materias = "<span class='badge bg-warning'>0</span>";
		if(mapaMaterias.containsKey(carga.getCodigoPersonal()+carga.getBloqueId())){
			materias = "<span class='badge bg-success'>"+mapaMaterias.get(carga.getCodigoPersonal()+carga.getBloqueId())+"</span>";
		}
		
		String residencia = "Interno";
		if(mapaEnCarga.containsKey(carga.getCodigoPersonal())){
			if(mapaEnCarga.get(carga.getCodigoPersonal()).getResidenciaId().equals("E")){
				residencia = "Externo";
			}
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=carga.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>			
			<td><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td><%=formaPago.equals("C")?"Contado": "Pagaré" %></td>
			<td class="right"><%=textoPagoInicial%></td>
			<td><%=materias%></td>
			<td><%=residencia%></td>
			<td><%=carga.getModo().equals("V")?"Virtual/Hogar":"Campus UM"%></td>
		</tr>
<% 		}%>	
	</tbody>
	</table>	
</div>
</body>
<script type="text/javascript">
	function Actualizar(){		
		document.frmProceso.submit();
	}
</script>
</html>
