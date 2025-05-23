<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat" %>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">

	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "cargas?PeriodoId="+periodoId;
	}	
	
	function cambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "cargas?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}
		
</script>
<body>
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

	String periodoId			= (String)request.getAttribute("periodoId");
	String cargaId				= (String)request.getAttribute("cargaId");
	Acceso acceso				= (Acceso)request.getAttribute("acceso");
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 				= (List<Carga>)request.getAttribute("lisCargas");
	List<AlumEstado> lisBajas					= (List<AlumEstado>)request.getAttribute("lisBajas");	
	
	HashMap<String, String> mapaAlumnosBajas 		= (HashMap<String, String>)request.getAttribute("mapaAlumnosBajas");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	
	HashMap<String, String> mapaCreditosOrigen		= (HashMap<String, String>)request.getAttribute("mapaCreditosOrigen");
	HashMap<String, String> mapaCreditosAlta		= (HashMap<String, String>)request.getAttribute("mapaCreditosAlta");
	HashMap<String, String> mapaCreditosBaja		= (HashMap<String, String>)request.getAttribute("mapaCreditosBaja");
%>
<div class="container-fluid">
	<h3>Alumnos de Baja <small class="text-muted fs-5">( <%=cargaId %> )</small></h3>
	<form name="frmCarga" action="cargas" method="post">
	<div class="alert alert-info">		
		Periodo:
		<select id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();" style="width:180px">
		<%for(CatPeriodo periodo : lisPeriodos){%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
		<%}%>
		</select>
		&nbsp;	
		Carga:
		<select id="CargaId" name="CargaId" onchange="javascript:cambioCarga();" style="width:350px">
		<%for(Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
		<%}%>
		</select>
		&nbsp;&nbsp;
		<a href="menu" class="btn btn-primary">Menu</a>
	</div>	
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
	    <th >#</th>
	    <th>Facultad</th>
	    <th>Carrera</th>
	    <th>Matricula</th>
	    <th>Nombre</th>
	    <th>Plan</th>
	    <th>Blq.</th>
	    <th class="right">Cr.Mat.</th>
	    <th class="right">Cr.Alta</th>
	    <th class="right">Cr.Baja</th>
	    <th class="right">Total</th>
	    <th>Estado</th>
  	</tr>
	</thead>
	<tbody> 
<%
	int row = 0;
	for (AlumEstado estado : lisBajas){	
		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().indexOf(estado.getCarreraId()) != -1){
			row++;
			String facultad = "-";
			if(mapaFacultades.containsKey(estado.getFacultadId())){
				facultad = mapaFacultades.get(estado.getFacultadId()).getNombreCorto();
			}
			
			String carrera = "-";
			if(mapaCarreras.containsKey(estado.getCarreraId())){
				carrera = mapaCarreras.get(estado.getCarreraId()).getNombreCarrera() ;
			}
			
			String nombreAlumno = "-";
			if(mapaAlumnosBajas.containsKey(estado.getCodigoPersonal())){
				nombreAlumno = mapaAlumnosBajas.get(estado.getCodigoPersonal());
			}
			
			String creditosOrigen = "0";
			if (mapaCreditosOrigen.containsKey(estado.getCodigoPersonal()+estado.getBloqueId())){
				creditosOrigen = mapaCreditosOrigen.get(estado.getCodigoPersonal()+estado.getBloqueId());
			}
			
			String creditosAlta = "0";
			if (mapaCreditosAlta.containsKey(estado.getCodigoPersonal()+estado.getBloqueId())){
				creditosAlta = mapaCreditosAlta.get(estado.getCodigoPersonal()+estado.getBloqueId());
			}
			
			String creditosBaja = "0";
			if (mapaCreditosBaja.containsKey(estado.getCodigoPersonal()+estado.getBloqueId())){
				creditosBaja = mapaCreditosBaja.get(estado.getCodigoPersonal()+estado.getBloqueId());
			}
		
			float creditosTotales 	= Float.valueOf(creditosOrigen) + Float.valueOf(creditosAlta) - Float.valueOf(creditosBaja);
			String colorCreditos	= "<span class='badge bg-dark'>"+getFormato.format(creditosTotales)+"</span>";
			if ( creditosTotales > 0 ){
				colorCreditos = "<span class='badge bg-warning'>"+getFormato.format(creditosTotales)+"</span>";
			}
%>
	<tr>
	    <td><%=row%></td>
	    <td><%=facultad%></td>
	    <td><%=carrera%></td>
	    <td><%=estado.getCodigoPersonal()%></td>
	    <td><%=nombreAlumno%></td>
	    <td><%=estado.getPlanId()%></td>	    
	    <td><span class="badge bg-dark"><%=estado.getBloqueId()%></span></td>
	    <td class="right"><%=creditosOrigen%></td>
	    <td class="right"><%=creditosAlta%></td>
	    <td class="right"><%=creditosBaja%></td>
	    <td class="right"><%=colorCreditos%></td>
	    <td><%=estado.getEstado().equals("3")?"BAJA":"-"%></td>
  	</tr>
<%
		}
	}	
%>	
	</tbody>
	</table>
</div>
</body>
</html>
