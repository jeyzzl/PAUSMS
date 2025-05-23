<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	String cargaId 		= (String)request.getAttribute("cargaId");
	String facultadId 	= (String)request.getAttribute("facultadId");
	
	CatCarrera carrera 	= (CatCarrera)request.getAttribute("carrera");
	
	List<aca.Mapa> listaAlumnos 			= (List<aca.Mapa>)request.getAttribute("listaAlumnos");
	HashMap<String,String> mapaNotaBaja 	= (HashMap<String,String>)request.getAttribute("mapaNotaBaja");
    HashMap<String,String> mapaNotaMedia 	= (HashMap<String,String>)request.getAttribute("mapaNotaMedia");
    HashMap<String,String> mapaNotaBuena 	= (HashMap<String,String>)request.getAttribute("mapaNotaBuena");
    HashMap<String,String> mapaNotaAlta 	= (HashMap<String,String>)request.getAttribute("mapaNotaAlta");
	
%>
<div class="container-fluid">
	<h2>Notas máximas por alumno <small class="text-muted fs-4">(<%=carrera.getNombreCarrera()%>)</small></h2>
	<div class="alert alert-info">
		<a href="maxCarreras?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th >#</th>
			<th>Matricula</th>
			<th>Nombre</th>
			<th style="text-align:right;">De 0-69</th>
			<th style="text-align:right;">De 70-79</th>
			<th style="text-align:right;">De 80-89</th>
			<th style="text-align:right;">De 90-100</th>
			<th style="text-align:right;">Total</th>
		</tr>
	</thead>
	<tbody>
<%
	int row = 1;
	int totalBaja	= 0;
	int totalMedia	= 0;
	int totalBuena	= 0;
	int totalAlta	= 0;
	int totalTotal	= 0;
	for(aca.Mapa alumno : listaAlumnos){
		
		String baja = "0";
		if(mapaNotaBaja.containsKey(carrera.getCarreraId()+alumno.getLlave())){
			baja = mapaNotaBaja.get(carrera.getCarreraId()+alumno.getLlave());
		}
		
		String media = "0";
		if(mapaNotaMedia.containsKey(carrera.getCarreraId()+alumno.getLlave())){
			media = mapaNotaMedia.get(carrera.getCarreraId()+alumno.getLlave());
		}
		
		String buena = "0";
		if(mapaNotaBuena.containsKey(carrera.getCarreraId()+alumno.getLlave())){
			buena = mapaNotaBuena.get(carrera.getCarreraId()+alumno.getLlave());
		}
		
		String alta = "0";
		if(mapaNotaAlta.containsKey(carrera.getCarreraId()+alumno.getLlave())){
			alta = mapaNotaAlta.get(carrera.getCarreraId()+alumno.getLlave());
		}
		
		String total = String.valueOf(Integer.parseInt(baja)+Integer.parseInt(media)+Integer.parseInt(buena)+Integer.parseInt(alta));
		totalBaja	= totalBaja + Integer.parseInt(baja);
		totalMedia	= totalMedia + Integer.parseInt(media);
		totalBuena	= totalBuena + Integer.parseInt(buena);
		totalAlta	= totalAlta + Integer.parseInt(alta);
		totalTotal	= totalTotal + Integer.parseInt(total);
%>

		<tr>
			<td><%=row %></td>
			<td><a href="portal?CodigoAlumno=<%=alumno.getLlave()%>" target="_blank"><span class="badge bg-info"><%=alumno.getLlave()%></span></a></td>
			<td><%=alumno.getValor()%></td>
			<td style="text-align:right;"><span class="badge <%=baja.equals("0")?"bg-secondary":"bg-warning"%>"><%=baja%></span></td>
			<td style="text-align:right;"><span class="badge <%=media.equals("0")?"bg-secondary":"bg-info"%>"><%=media%></span></td>
			<td style="text-align:right;"><span class="badge <%=buena.equals("0")?"bg-secondary":"bg-success"%>"><%=buena%></span></td>
			<td style="text-align:right;"><span class="badge <%=alta.equals("0")?"bg-secondary":"bg-dark"%>"><%=alta%></span></td>	
			<td style="text-align:right;"><%=total%></td>			
		</tr>
	<%
		row ++;
	} 
%>
		<tr class="table-info">
			<td colspan="3" class="center" style="font-weight: bold;">Total</td>
			<td style="text-align:right;"><%=totalBaja%></td>
 			<td style="text-align:right;"><%=totalMedia%></td> 
 			<td style="text-align:right;"><%=totalBuena%></td> 
 			<td style="text-align:right;"><%=totalAlta%></td> 
 			<td style="text-align:right;"><%=totalTotal%></td>
 		</tr>
 	</tbody>	
	</table>
</div>

