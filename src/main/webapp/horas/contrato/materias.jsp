
<%@ page import="aca.emp.spring.EmpHoras"%>
<%@ page import="aca.emp.spring.EmpContrato"%>
<%@ page import="aca.emp.spring.EmpTipoPago"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String yearActual			= aca.util.Fecha.getHoy().substring(6,10);
	String year 				= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	String mensaje	 			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	EmpContrato empContrato 	= (EmpContrato)request.getAttribute("empContrato");
	String empleadoNombre 		= (String)request.getAttribute("empleadoNombre");
	String contratoId	 		= (String)request.getAttribute("contratoId");
	double totalContrato	 	= (double)request.getAttribute("totalContrato");
	
	List<String> lisCarreras 	= (List<String>)request.getAttribute("lisCarreras");
	List<EmpHoras> lisMaterias 	= (List<EmpHoras>)request.getAttribute("lisMaterias");
	
	HashMap<String, String> mapaCursos			= (HashMap<String, String>)request.getAttribute("mapaCursos");
	HashMap<String, String> mapaElegidas		= (HashMap<String, String>)request.getAttribute("mapaElegidas");
	HashMap<String,EmpTipoPago> mapaPagos 		= (HashMap<String,EmpTipoPago>)request.getAttribute("mapaPagos");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, String> mapaImportes		= (HashMap<String, String>)request.getAttribute("mapaImportes");
	
	float dias 		= aca.util.Fecha.diasEntreFechas(empContrato.getFechaIni(), empContrato.getFechaFin()) + 1;  
	float salario 	= 0;
	if (Float.valueOf(empContrato.getCosto()) > 0 && dias > 0){
		salario	= Float.valueOf(empContrato.getCosto()) / dias;
	}
	
	float salario15 = salario * 15;
	float salario16 = salario * 16;
	float salario30 = salario * 30;
%>
<head>	
</head>
<body>
<div class="container-fluid">
	<h2>Materias<small class="text-muted fs-4">( <%=empContrato.getCodigoPersonal()%>-<%=empleadoNombre%> Contrato: <%=contratoId%>)</small></h2>	
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="contratos?EmpleadoId=<%=empContrato.getCodigoPersonal()%>&Year=<%=year%>">Regresar</a>&nbsp;&nbsp;&nbsp;
		<a class="btn btn-warning" href="contratoPdf?ContratoId=<%=contratoId%>&Year=<%=year%>">PDF</a>&nbsp;&nbsp;&nbsp;
		<span class="badge badge-inverse">Datos:</span> <b>Inicio</b> [<%=empContrato.getFechaIni()%>] - <b>Fin</b> [<%=empContrato.getFechaFin()%>]&nbsp;
		- <b>Días</b> [<%=dias%>] - <b>Salario diario</b> [<%=formato.format(salario)%>] - <b>Salario 15</b> [<%=formato.format(salario15)%>]&nbsp;
		- <b>Salario 16</b> [<%=formato.format(salario16)%>]&nbsp;
<%	if (salario30 < Double.valueOf(empContrato.getCosto())){%>		
		- <b>Salario 30</b> [<%=formato.format(salario30)%>]
<% 	}%>
	</div>
<%	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		Actualizó exitosamente.
	</div>	
<%	}%>
	<form action="grabarContrato" method="POST">
		<input type="hidden" name="Year" value="<%=year%>">
		<input type="hidden" name="ContratoId" value="<%=contratoId%>">
		<input type="hidden" name="CodigoPersonal" value="<%=empContrato.getCodigoPersonal()%>">
		<input type="hidden" name="ContratoId" value="<%=contratoId%>">
		<table class="table table">
		<tr>
			<th>Op.</th>
			<th>Folio</th>
			<th>Carga</th>
			<th>Facultad</th>		
			<th>Carrera</th>	
			<th>Materia</th>
			<th>Fecha Ini.</th>
			<th>Fecha Fin.</th>
			<th>Estado</th>
			<th>Pago</th>
			<th class="text-end">Frec.</th>
			<th class="text-end">Semanas</th>
			<th class="text-end">Precio</th>
			<th class="text-end">Total</th>
		</tr>
<%
	int row = 0;
	for(EmpHoras horas : lisMaterias){
		row++;
		String estadoNombre = "-";
		if (horas.getEstado().equals("S")) estadoNombre = "<span class='badge badge-warning'>Solicitud</span>";
		if (horas.getEstado().equals("A")) estadoNombre = "<span class='badge badge-success'>Autorizado VRA</span>";
		if (horas.getEstado().equals("N")) estadoNombre = "<span class='badge badge-inverse'>Nómina</span>";
		
		double total = Double.valueOf(horas.getFrecuencia())*Double.valueOf(horas.getSemanas())*Double.valueOf(horas.getPrecio());
		
		String tipoPago = "";
		if (mapaPagos.containsKey(horas.getTipoPagoId())){
			tipoPago = mapaPagos.get(horas.getTipoPagoId()).getCorto();
		}
		
		String colorRow = "<span class='badge badge-warning'>"+row+"</span>";
		if (horas.getFechaIni().substring(6, 10).equals(year)){
			colorRow 	= "<span class='badge badge-success'>"+row+"</span>";
		}
		
		String facultadId 		= "0";
		String facultadNombre 	= "-";
		String carreraNombre 	= "0";
		if(mapaCarreras.containsKey(horas.getCarreraId())){
			carreraNombre = mapaCarreras.get(horas.getCarreraId()).getNombreCorto();
			facultadId = mapaCarreras.get(horas.getCarreraId()).getFacultadId();
			if(mapaFacultades.containsKey(facultadId)){
				facultadNombre = mapaFacultades.get(facultadId).getNombreCorto();
			}
		}
%>	
		<tr>
			<td>
<%		if (horas.getEstado().equals("N")){ %>
				<input class="CheckContrato" type="checkbox" name="<%=horas.getCodigoPersonal()+horas.getFolio()%>" value="S" <%=mapaElegidas.containsKey(horas.getCodigoPersonal()+horas.getFolio())?"checked":""%>>
<%		} %>				
			</td>
			<td><%=colorRow%></td>
			<td><%=horas.getCargaId()%></td>
			<td><%=facultadNombre%></td>
			<td title="<%=horas.getCarreraId()%>"><%=carreraNombre%></td>
			<td>			
				<%=mapaCursos.containsKey(horas.getCursoId())?mapaCursos.get(horas.getCursoId()):horas.getCursoId()%> ( <%=horas.getMateria()%> )
			</td>
			<td><%=horas.getFechaIni()%></td>
			<td><%=horas.getFechaFin()%></td>
			<td><%=estadoNombre%></td>
			<td><%=tipoPago%></td>
			<td class="text-end"><%=horas.getFrecuencia()%></td>
			<td class="text-end"><%=horas.getSemanas()%></td>
			<td class="text-end"><%=horas.getPrecio()%></td>
			<td class="text-end"><%=formato.format(Double.valueOf(total))%></td>
		</tr>
<%	} %>		
		<tr>
			<th colspan="13" class="text-end">T O T A L --></th>
			<th class="text-end"><%=formato.format(totalContrato)%></th>
		</tr>
		</table>
		<table class="table table" style="width:40%">
		<tr>
			<th>#</th>
			<th>Carrera</th>
			<th>Importe</th>			
			<th>%</th>
		</tr>
<%		
	row=0;
	for (String carrera : lisCarreras){
		row++;
		String carreraCorto 	= "-";
		String carreraNombre	= "-"; 
		if (mapaCarreras.containsKey(carrera)){
			carreraNombre 	= mapaCarreras.get(carrera).getNombreCarrera();
			carreraCorto 	= mapaCarreras.get(carrera).getNombreCorto();
		}
		
		String importe = "0";
		if (mapaImportes.containsKey(carrera)){
			importe = mapaImportes.get(carrera);
		}
		
		double por = 0;
		if (totalContrato>0) por = Double.valueOf(importe)*100/totalContrato;
%>
		<tr>
			<td><%=row%></td>
			<td><%=carreraNombre%><small class="text-muted">( <%=carreraCorto%>)</small></td>
			<td><%=formato.format(Double.valueOf(importe))%></td>			
			<td><%=formato.format(por)%></td>
		</tr>	
<%	} %>
		</div>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" value="Grabar">
		</div>
	</form>
</div>
</body>