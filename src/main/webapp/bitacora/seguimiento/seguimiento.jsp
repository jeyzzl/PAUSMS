<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.bitacora.spring.BitEstado"%>
<%@page import="aca.bitacora.spring.BitArea"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitTramiteAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	function cambiarArea(){	
		document.form.submit();
	}
	
	function refrescar(){
		document.form.submit();
	}	
</script>
<%
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String tramiteSesion	= (String) request.getAttribute("tramiteSesion");

	String areaId			= (String) request.getAttribute("areaId");
	String tramiteId 		= (String) request.getAttribute("tramiteId");
	String estadoId  		= (String) request.getAttribute("estadoId");
	String hoy 				= aca.util.Fecha.getHoy();
	
	List<BitEstado> lisEstados  					= (List<BitEstado>) request.getAttribute("lisEstados");
	List<BitArea> lisAreas							= (List<BitArea>) request.getAttribute("lisAreas");
	List<BitTramite> lisTramites  					= (List<BitTramite>) request.getAttribute("lisTramites");
	List<BitTramiteAlumno> lisTramitesAlumnos		= (List<BitTramiteAlumno>) request.getAttribute("lisTramitesAlumnos");
	
	HashMap<String,BitTramite> mapaTramite 			= (HashMap<String,BitTramite>) request.getAttribute("mapaTramite");
	HashMap<String,String> mapaEstado 				= (HashMap<String,String>) request.getAttribute("mapaEstado");
	HashMap<String,String> mapaHijos 				= (HashMap<String,String>) request.getAttribute("mapaHijos");
	HashMap<String,String> mapaTramSinEtiSinOrig	= (HashMap<String,String>) request.getAttribute("mapaTramSinEtiSinOrig");
	HashMap<String,String> mapaCuentaEtiquetas		= (HashMap<String,String>) request.getAttribute("mapaCuentaEtiquetas");
	HashMap<String,String> mapaAreas				= (HashMap<String,String>) request.getAttribute("mapaAreas");
	HashMap<String,AlumPersonal> mapaAlumnos		= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	
	String fechaInicio	= (String) request.getAttribute("fechaInicio");
	String fechaFinal	= (String) request.getAttribute("fechaFinal");
	
	if (areaId.equals("0") && lisAreas.size() > 0){
		areaId = lisAreas.get(0).getAreaId();
	}	
%>
<div class="container-fluid">
	<h2>BSE / Seguimiento</h2>
	<form name="form" action="seguimiento">
	<div class="alert alert-info d-flex align-items-center">
		Area:
		<select name="AreaId" style="width:200px;" class="form-select" onchange="javaScritp:cambiarArea()">
<%	int row = 0;
	for (BitArea area : lisAreas){
%>
			<option value="<%=area.getAreaId()%>" <%=areaId.equals(area.getAreaId())?"selected":""%>><%=area.getAreaNombre()%></option>
<%	}%>					
		</select>&nbsp;&nbsp;&nbsp;
		Trámite:
			<select style="width:200px;" class="form-select" name="TramiteId" id="TramiteId" onchange="javaScritp:refrescar()">
				<option value="0" <%=tramiteId.equals("0")?"selected":""%>>Todos</option>
<% 	for(BitTramite tramite : lisTramites){%>
				<option <%if(tramiteId.equals(tramite.getTramiteId()))out.print("Selected");%> value="<%=tramite.getTramiteId()%>">
					<%=tramite.getTramiteId()%>-<%=tramite.getNombre()%>
				</option>
<%	}%>
			</select>&nbsp;&nbsp;&nbsp;
			Estado:
			<select style="width:200px;" class="form-select" name="EstadoId" id="EstadoId" onchange="javaScritp:refrescar()">
				<option value="0" <%=estadoId.equals("0")?"selected":""%>>Todos</option>
<%			for(BitEstado estado : lisEstados){%>
				<option <%if(estadoId.equals(estado.getEstado()))out.print("Selected");%> value="<%=estado.getEstado()%>">
					<%=estado.getEstadoNombre()%>
				</option>
<%			}%>
			</select>&nbsp;&nbsp;&nbsp;
			Fecha Ini. <input type="text" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="9" class="form-control" style="width:120px;"/>&nbsp;&nbsp;
			Fecha Fin. <input type="text" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="9" class="form-control" style="width:120px;"/>&nbsp;&nbsp;			
			<a href="javascript:refrescar()" class="btn btn-primary" ><i class="fas fa-sync-alt"></i></a>
	</div>
	<a href="nuevaEtiqueta?AreaId=<%=areaId%>&TramiteId=<%=tramiteId%>&EstadoId=<%=estadoId%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>" class="btn btn-primary">Nueva Etiqueta</a>
	</form>&nbsp;
	<table class="table table-sm table-bordered" style="width:100%">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Opc</th>
			<th>Folio</th>
			<th>Clave</th>
			<th>Trámite</th>
			<th>Min.</th>
			<th>Max.</th>			
			<th>Prom.</th>
			<th>Real</th>
			<th>Matrícula</th>
			<th>Nombre</th>
			<th>Paterno</th>
			<th>Materno</th>
			<th>Turnado de</th>
			<th>Inicio</th>
			<th>Término</th>
			<th>Estado</th>
			<th>Tikets</th>
			<th>Ref.</th>
			<th>Dep.</th>
		</tr>
	<thead>	
<%
	row = 0;
	for (BitTramiteAlumno tramite : lisTramitesAlumnos){
		row++;
		
		//Cuenta la duración en dias del tramite 
		int dias = 0;
		if (tramite.getFechaFinal()==null){
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), hoy);
		}else{
			dias = aca.util.Fecha.diasEntreFechas(tramite.getFechaInicio(), tramite.getFechaFinal());
		}
		
		String numEtiquetas = "<span class='badge bg-warning'>0</span>";
		if(mapaCuentaEtiquetas.containsKey(tramite.getFolio())){
			numEtiquetas = "<span class='badge bg-dark'>"+mapaCuentaEtiquetas.get(tramite.getFolio())+"</span>";
		}
		
		// Cantidad de tramites hijos del tramite actual
		String dependientes = "0";
		if (mapaHijos.containsKey(tramite.getFolio()) ){
			 dependientes = mapaHijos.get(tramite.getFolio());
		}
		
		String minimo 		= "0";
		String maximo 		= "0";
		String promedio 	= "0";
		String nombreTramite= "-";		
		if (mapaTramite.containsKey(tramite.getTramiteId())){
			minimo 			= mapaTramite.get(tramite.getTramiteId()).getMinimo();
			maximo 			= mapaTramite.get(tramite.getTramiteId()).getMaximo();
			promedio 		= mapaTramite.get(tramite.getTramiteId()).getPromedio();
			nombreTramite 	= mapaTramite.get(tramite.getTramiteId()).getNombre();			
		}
		
		String colorTramite = "bg-dark";
		if (!tramite.getAreaOrigen().equals("0")){
			colorTramite = "bg-success";
		}
		
		String colorReal = "bg-success";
		if (dias>Float.parseFloat(promedio)){
			colorReal = "bg-warning";
		}
		
		String estadoNombre = "-";
		if (mapaEstado.containsKey(tramite.getEstado())){
			estadoNombre = mapaEstado.get(tramite.getEstado());
		}
		
		String turnar = "Mesa de entrada";
		if (!tramite.getAreaOrigen().equals("0")){
			if(mapaAreas.containsKey(tramite.getAreaOrigen())){
				turnar = mapaAreas.get(tramite.getAreaOrigen());
			}
		}
		
		AlumPersonal alumPersonal 	= new AlumPersonal();
		if(mapaAlumnos.containsKey(tramite.getCodigoPersonal())){
			alumPersonal	= mapaAlumnos.get(tramite.getCodigoPersonal());
		}		
%>
		<tr>
			<td><%=row%></td>
			<td>
<% 				if(mapaTramSinEtiSinOrig.containsKey(tramite.getFolio())){%>
				<a href="borrarTramite?Folio=<%=tramite.getFolio()%>&AreaId=<%=areaId%>&TramiteId=<%=tramiteId%>&EstadoId=<%=estadoId%>" title="Borrar"><i class="fas fa-times"></i></a>
<% 				}%>
			</td>
			<td>
				<a href="etiquetas?Folio=<%=tramite.getFolio()%>"><span class="badge <%=colorTramite%>"><%=tramite.getFolio()%></span></a>
			</td>
			<td><span class="badge bg-dark"><%=tramite.getTramiteId()%></span></td>
			<td><%= nombreTramite%></td>
			<td class = "right"><%= minimo %></td>
			<td class = "right"><%= maximo %></td>
			<td class = "right"><%= promedio %></td>
			<td class = "right"><span class="badge <%=colorReal%>" ><%= dias%></span></td>
			<td><%=tramite.getCodigoPersonal() %></td>
			<td><%=alumPersonal.getNombre()%></td>
			<td><%=alumPersonal.getApellidoPaterno()%></td>
			<td><%=alumPersonal.getApellidoMaterno()%></td>
			<td><%=turnar%></td>
			<td><%=tramite.getFechaInicio()%></td>
			<td><%=tramite.getFechaFinal()==null?"-":tramite.getFechaFinal()%></td>
			<td><%=estadoNombre%></td>
			<td><%=numEtiquetas%></td>
			<td>
			<%	if (!tramite.getFolioOrigen().equals("-")){	%>	
				<span class="badge bg-dark"><%=tramite.getFolioOrigen()%></span>
			<%	}%>	
			</td>
			<td><%=dependientes%></td>
		</tr>
<%	}%>		
		
	</table>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>