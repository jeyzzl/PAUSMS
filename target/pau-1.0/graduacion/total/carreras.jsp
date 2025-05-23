<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
	<head>
		<script type="text/javascript">
			function recarga() {
				document.frmGraduacion.submit();
			}
		</script>
	</head>
<%
	String eventoId				= (String)request.getAttribute("eventoId");
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
		
	List<AlumEvento> lisEventos 		= (List<AlumEvento>) request.getAttribute("lisEventos");
	List<CatCarrera> lisCarreras 		= (List<CatCarrera>) request.getAttribute("lisCarreras");
	
	HashMap<String,String> mapaTotales				= (HashMap<String,String>) request.getAttribute("mapaTotales");
	HashMap<String, String> mapaGenero 				= (HashMap<String,String>)request.getAttribute("mapaGenero");
	HashMap<String, String> mapaNiveles 			= (HashMap<String,String>)request.getAttribute("mapaNiveles");

	int tot = 0, totH = 0, totM = 0, totP = 0, totL = 0, totMaes = 0, totD = 0, totE = 0;
%>
<body>
<div class="container-fluid">
	<h1>Total de graduandos por carrera</h1>
	<form name="frmGraduacion" action="carreras" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Evento"/>:&nbsp;
		<select class="form-select" id="EventoId" name="EventoId" onchange="javascript:recarga()" style="width:500px;">
		<%				
			
			String fechaEvento			= "";
			for (AlumEvento evento: lisEventos) {
				
				if(evento.getEventoId().equals(eventoId)){
					fechaEvento = evento.getFecha();
				}
		%>
			<option value="<%=evento.getEventoId()%>" <%=evento.getEventoId().equals(eventoId)?"Selected":"" %>>
				<%=evento.getEventoNombre()%>[<%=evento.getFecha()%>]
			</option>
		<%	} %>
		</select>		
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
  		<th>Clave</th>
    	<th  align="center"><spring:message code="aca.Carrera"/></th>
    	<th class="text-end">Total</th>
    	<th class="text-end">Hombres</th>
    	<th class="text-end">Mujeres</th>
    	<th class="text-end">Preparatoria</th>
		<th class="text-end">Licenciatura</th>
		<th class="text-end">Maestría</th>
		<th class="text-end">Doctorado</th>
		<th class="text-end">Especialidad</th>    
	</tr>
	</thead>
	<tbody>	
<%	
	int row = 0;
	for (CatCarrera carrera : lisCarreras) {
		row++;	
		String total = "0";
		if(mapaTotales.containsKey(carrera.getCarreraId())){
			total = mapaTotales.get(carrera.getCarreraId());
			tot+= Integer.parseInt(total);
		}
		String hombres = "0", mujeres = "0";
		if (mapaGenero.containsKey(carrera.getCarreraId()+"F")){
			mujeres =  mapaGenero.get(carrera.getCarreraId()+"F");
			totM+=Integer.parseInt(mujeres);
		}
		if (mapaGenero.containsKey(carrera.getCarreraId()+"M")){
			hombres =  mapaGenero.get(carrera.getCarreraId()+"M");
			totH+=Integer.parseInt(hombres);
		}
		String prepa = "0";
		if (mapaNiveles.containsKey(carrera.getCarreraId()+"1")){
			prepa =  mapaNiveles.get(carrera.getCarreraId()+"1");
			totP+=Integer.parseInt(prepa);
		}
		String lic = "0";
		if (mapaNiveles.containsKey(carrera.getCarreraId()+"2")){
			lic =  mapaNiveles.get(carrera.getCarreraId()+"2");
			totL+=Integer.parseInt(lic);
		}
		String maest = "0";
		if (mapaNiveles.containsKey(carrera.getCarreraId()+"3")){
			maest =  mapaNiveles.get(carrera.getCarreraId()+"3");
			totMaes+=Integer.parseInt(maest);
		}
		String doct = "0";
		if (mapaNiveles.containsKey(carrera.getCarreraId()+"4")){
			doct =  mapaNiveles.get(carrera.getCarreraId()+"4");
			totD+=Integer.parseInt(doct);
		}
		String espe = "0";
		if (mapaNiveles.containsKey(carrera.getCarreraId()+"6")){
			espe =  mapaNiveles.get(carrera.getCarreraId()+"6");
			totE+=Integer.parseInt(espe);
		}
		
%>
		<tr align="left">
			<td><%=row%></td>
			<td><%=carrera.getCarreraId()%></td>
			<td><%=carrera.getNombreCarrera()%></td>
			<td class="text-end"><%=total%></td>
			<td class="text-end"><%=hombres%></td>
			<td class="text-end"><%=mujeres%></td>
			<td class="text-end"><%=prepa%></td>
			<td class="text-end"><%=lic%></td>
			<td class="text-end"><%=maest%></td>
			<td class="text-end"><%=doct%></td>
			<td class="text-end"><%=espe%></td>
		</tr>	
	<%	} %>
	</tbody>
	<tfoot class="table-info">	
	<tr>
		<th class="text-end" colspan="3">TOTALES </th>		
		<th class="text-end"><%=tot%></th>
		<th class="text-end"><%=totH%></th>
		<th class="text-end"><%=totM%></th>
		<th class="text-end"><%=totP%></th>
		<th class="text-end"><%=totL%></th>
		<th class="text-end"><%=totMaes%></th>
		<th class="text-end"><%=totD%></th>
		<th class="text-end"><%=totE%></th>
	</tr>
	</tfoot>
</div>
</body>
</html>