<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import=" java.text.DecimalFormat"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String periodoId 				= (String)request.getAttribute("periodoId");
	String cargaId 					= (String)request.getAttribute("cargaId");
	
	List<CatPeriodo> lisPeriodos 					= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 							= (List<Carga>) request.getAttribute("lisCargas");	
	List<CatCarrera> lisCarreras					= (List<CatCarrera>) request.getAttribute("lisCarreras");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, String> mapaMaterias			= (HashMap<String, String>) request.getAttribute("mapaMaterias");
	HashMap<String, String> mapaCreadas				= (HashMap<String, String>) request.getAttribute("mapaCreadas");
	HashMap<String, String> mapaOrdinarias			= (HashMap<String, String>) request.getAttribute("mapaOrdinarias");
	HashMap<String, String> mapaExtras				= (HashMap<String, String>) request.getAttribute("mapaExtras");
	HashMap<String, String> mapaCerradas			= (HashMap<String, String>) request.getAttribute("mapaCerradas");
	HashMap<String, String> mapaRegistradas			= (HashMap<String, String>) request.getAttribute("mapaRegistradas");
	HashMap<String, String> mapaAlumnos 			= (HashMap<String, String>) request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaCerrados 			= (HashMap<String, String>) request.getAttribute("mapaCerrados");
	
	int totMaterias=0, totCreadas=0, totOrdinarias=0, totExtras=0, totCerradas=0, totRegistradas=0, totAlumnos=0, totAlumCerr=0;
	float tfMaterias=0,tfcreadas=0,tfOrdinarias=0, tfExtras=0,tfCerradas=0, tfRegistradas=0,tfAlumnos=0, tfAlumCer=0, pCreadas=0,pordinaria = 0,pextra=0,pcerrada=0,pregistrada=0, pralumcer=0;
%>
<div class="container-fluid">
	<h2>Entrega de planillas</h2>
	<form name="frmPlanillas" action="entregas" method="post" target="_self">	
	<div class="alert alert-info d-flex align-items-center">
		período:&nbsp;
		<select onchange="document.frmPlanillas.submit();" name="PeriodoId" class="form-select" style="width:150px">
<%	for(CatPeriodo periodo : lisPeriodos){ %>
 			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%>><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
		</select>
		&nbsp;&nbsp;
		Cargas académicas:&nbsp;
		<select onchange="document.frmPlanillas.submit();" name="CargaId" class="form-select" style="width:400px">
		<%	for (Carga carga : lisCargas){%>
				<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?" Selected ":""%>><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
		<%	}%>        	    
    	</select>			
	</div>
	</form>			
	<table class="table table-sm table-bordered">
		<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Materias</th>		
		<th>Creada</th>
		<th style="text-align:right;">%</th>
		<th>Ordinaria</th>
		<th style="text-align:right;">%</th>
		<th>Extra</th>
		<th style="text-align:right;">%</th>
		<th>Cerrada</th>
		<th style="text-align:right;">%</th>
		<th>Registrada</th>
		<th style="text-align:right;">%</th>
		<th>Alumno</th>
		<th>Cerrado</th>
		<th style="text-align:right;">%</th>
	</tr>
	</thead>
<%	
	int row=0;	

	for (CatCarrera carrera : lisCarreras){
		row++;
		
		String facultadCorto = "-";
		if (mapaFacultades.containsKey(carrera.getFacultadId())){
			facultadCorto 	= mapaFacultades.get(carrera.getFacultadId()).getNombreCorto();
		}
		
		String materias = "0";
		if (mapaMaterias.containsKey(carrera.getCarreraId())){
			materias 	= mapaMaterias.get(carrera.getCarreraId());
		}

		String alumnos = "0";
		if(mapaAlumnos.containsKey(carrera.getCarreraId())){
			alumnos = mapaAlumnos.get(carrera.getCarreraId());
		}
		
		String creadas = "0";
		float porcent = 0;
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		if (mapaCreadas.containsKey(carrera.getCarreraId())){
			creadas 	= mapaCreadas.get(carrera.getCarreraId());
			porcent = Float.valueOf(decimalFormat.format((Float.parseFloat(creadas)*100)/Float.parseFloat(materias)));
			
		}
		
		String ordinarias = "0";
		float porcentOrd = 0;
		if (mapaOrdinarias.containsKey(carrera.getCarreraId())){
			ordinarias 	= mapaOrdinarias.get(carrera.getCarreraId());
			DecimalFormat dfO = new DecimalFormat("#.##");
		porcentOrd = Float.valueOf(dfO.format((Float.parseFloat(ordinarias)*100)/Float.parseFloat(materias)));
			
		}
		
		String extras = "0";
		float porcentEx = 0;
		if (mapaExtras.containsKey(carrera.getCarreraId())){
			extras 	= mapaExtras.get(carrera.getCarreraId());
			DecimalFormat dfExtra = new DecimalFormat("#.##");
			porcentEx = Float.valueOf(dfExtra.format((Float.parseFloat(extras)*100)/Float.parseFloat(materias)));
		}
		
		String cerradas = "0";
		float porcentcer = 0;
		if (mapaCerradas.containsKey(carrera.getCarreraId())){
			cerradas 	= mapaCerradas.get(carrera.getCarreraId());
			DecimalFormat dfCerr = new DecimalFormat("#.##");
			porcentcer = Float.valueOf(dfCerr.format((Float.parseFloat(cerradas)*100)/Float.parseFloat(materias)));
		}
		
		String registradas = "0";
		float porcentreg = 0;
		if (mapaRegistradas.containsKey(carrera.getCarreraId())){
			registradas 	= mapaRegistradas.get(carrera.getCarreraId());
			DecimalFormat dfreg = new DecimalFormat("#.##");
			porcentreg = Float.valueOf(dfreg.format((Float.parseFloat(registradas)*100)/Float.parseFloat(materias)));
			
		}

		String alumCerrados = "0";
		float poralumcer = 0;
		if(mapaCerrados.containsKey(carrera.getCarreraId())){
			alumCerrados = mapaCerrados.get(carrera.getCarreraId());
			DecimalFormat dfAlumCerr = new DecimalFormat("#.##");
			poralumcer =  Float.valueOf(dfAlumCerr.format((Float.parseFloat(alumCerrados)*100)/Float.parseFloat(alumnos)));
		}

		totMaterias 	+= Integer.parseInt(materias);
		totCreadas		+= Integer.parseInt(creadas);
		totOrdinarias	+= Integer.parseInt(ordinarias);
		totExtras		+= Integer.parseInt(extras);
		totCerradas		+= Integer.parseInt(cerradas);
		totRegistradas	+= Integer.parseInt(registradas);
		totAlumnos 		+= Integer.parseInt(alumnos);
		totAlumCerr 	+= Integer.parseInt(alumCerrados);
		
		tfMaterias   	+= Float.parseFloat(materias);
		tfcreadas		+= Float.parseFloat(creadas);
		tfOrdinarias	+= Float.parseFloat(ordinarias);
		tfExtras		+= Float.parseFloat(extras);
		tfCerradas		+= Float.parseFloat(cerradas);
		tfRegistradas	+= Float.parseFloat(registradas);
		tfAlumnos 		+= Float.parseFloat(alumnos);
		tfAlumCer 		+= Float.parseFloat(alumCerrados);
		
		
	 	DecimalFormat dfpcreadas = new DecimalFormat("#.##");
		 pCreadas = Float.valueOf(dfpcreadas.format(tfcreadas*100/tfMaterias));
		
		DecimalFormat dfordinaria = new DecimalFormat("#.##");
		pordinaria = Float.valueOf(dfordinaria.format((tfOrdinarias)*100/totMaterias));
		
	     DecimalFormat dfextra = new DecimalFormat("#.##");
		 pextra = Float.valueOf(dfextra.format((tfExtras)*100/tfMaterias));
		
		DecimalFormat dcerrada = new DecimalFormat("#.##");
		pcerrada = Float.valueOf(dcerrada.format((tfCerradas)*100/tfMaterias));
	
		DecimalFormat dregistrada = new DecimalFormat("#.##");
		pregistrada = Float.valueOf(dregistrada.format((tfRegistradas)*100/tfMaterias));

		DecimalFormat dalumcerrado = new DecimalFormat("#.##");
		pralumcer = Float.valueOf(dalumcerrado.format((tfAlumCer)*100/tfAlumnos));

%>
	<tr>
		<td><%=row%></td>
		<td style="text-align:center;"><%=facultadCorto%></td>
		<td><a href="materias?CarreraId=<%=carrera.getCarreraId()%>&CarreraNombre=<%=carrera.getNombreCarrera()%>"><%=carrera.getCarreraId()%> - <%=carrera.getNombreCarrera()%></a></td>
		<td style="text-align:right;"><%=materias%></td>			
		<td style="text-align:right;"><%=creadas%></td>
		<td style="text-align:right;"><%=porcent%></td>
		<td style="text-align:right;"><%=ordinarias%></td>
		<td style="text-align:right;"><%=porcentOrd%></td>
		<td style="text-align:right;"><%=extras%></td>
		<td style="text-align:right;"><%=porcentEx%></td>
		<td style="text-align:right;"><%=cerradas%></td>
		<td style="text-align:right;"><%=porcentcer%></td>
		<td style="text-align:right;"><%=registradas%></td>
		<td style="text-align:right;"><%=porcentreg%></td>
		<td style="text-align:right;"><%=alumnos%></td>
		<td style="text-align:right;"><%=alumCerrados%></td>
		<td style="text-align:right;"><%=poralumcer%></td>
		
	</tr>
<%	} %>
	<tr class="table-info">
		<th colspan="3">T O T A L E S </th>		
		<th style="text-align:right;"><%=totMaterias%></th>	
		<th style="text-align:right;"><%=totCreadas%></th>
		<th style="text-align:right;"><%=pCreadas%></th>	
		<th style="text-align:right;"><%=totOrdinarias%></th>
		<th style="text-align:right;"><%=pordinaria%></th>
		<th style="text-align:right;"><%=totExtras%></th>
		<th style="text-align:right;"><%=pextra%></th>
		<th style="text-align:right;"><%=totCerradas%></th>	
		<th style="text-align:right;"><%=pcerrada%></th>
		<th style="text-align:right;"><%=totRegistradas%></th>
		<th style="text-align:right;"><%=pregistrada%></th>
		<th style="text-align:right;"><%=totAlumnos%></th>
		<th style="text-align:right;"><%=totAlumCerr%></th>
		<th style="text-align:right;"><%=pralumcer%></th>
	</tr>
	</table>	
</div>