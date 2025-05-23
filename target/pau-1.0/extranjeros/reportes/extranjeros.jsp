<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.vista.spring.Estadistica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String cargaNombre 		= (String)request.getAttribute("cargaNombre");
	String codigoTemp		= "0";
	
	List<CatFacultad> lisFacultades 				= (List<CatFacultad>)request.getAttribute("lisFacultades");
	List<CatCarrera> lisCarreras 					= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<Estadistica> lisExtranjeros			 	= (List<Estadistica>)request.getAttribute("lisExtranjeros");	
	HashMap<String,CatPais> mapaPaises				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,String> mapaDocumentos			= (HashMap<String,String>)request.getAttribute("mapaDocumentos");	
%>
<div class="container-fluid">
	<h2>Alumnos Extranjeros Inscritos<small class="text-muted fs-4">( <%=cargaId%> - <%=cargaNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="elige_carga"><spring:message code="aca.Regresar"/></a>
	</div>	
<%
	for (CatFacultad facultad : lisFacultades){
%>
	<div  >
		<td><b><h1><%=facultad.getFacultadId()%>:<%=facultad.getNombreFacultad()%></h1></b></td>
	</div>
	<table class="table table-sm table-bordered">
<%
		for (CatCarrera carrera : lisCarreras){
			if (carrera.getFacultadId().equals(facultad.getFacultadId())){
%>	
	<tr>
		<td colspan="9"><h4><%=carrera.getCarreraId()%>:<%=carrera.getNombreCarrera()%></h4></td>
	</tr>
	<tr class="table-primary">
		<th  width="5%">#</th>
		<th  "table-info"width="5%">Matrícula</th>
		<th  "table-info"width="40%">Alumno</th>
		<th width="5%">Sexo</th>		
		<th width="15%">Nacionalidad</th>
		<th width="5%">Modalidad</th>
		<th width="5%">FM-M</th>		
		<th width="5%">FM3</th>
		<th width="5%">Pasaporte</th>
		<th width="10%">Registrado</th>
	</tr>
<%				int row = 0;
				for (Estadistica estadistica : lisExtranjeros){					
					if (estadistica.getCarreraId().equals(carrera.getCarreraId()) && !codigoTemp.equals(estadistica.getCodigoPersonal())){
						codigoTemp = estadistica.getCodigoPersonal();
						row++;
						
						String paisNombre = "-";						
						if (mapaPaises.containsKey(estadistica.getNacionalidad())){
							paisNombre = mapaPaises.get(estadistica.getNacionalidad()).getNombrePais();
						}
						
						String modalidadNombre = "-";
						if (mapaModalidades.containsKey(estadistica.getModalidadId())){
							modalidadNombre = mapaModalidades.get(estadistica.getModalidadId()).getNombreModalidad();
						}
						
						String venceFmm = "-";
						if (mapaDocumentos.containsKey(estadistica.getCodigoPersonal()+"1")){
							venceFmm = mapaDocumentos.get(estadistica.getCodigoPersonal()+"1");
						}
						
						String venceFm3 = "-";
						if (mapaDocumentos.containsKey(estadistica.getCodigoPersonal()+"2")){
							venceFm3 = mapaDocumentos.get(estadistica.getCodigoPersonal()+"2");
						}
						
						String vencePasaporte = "-";
						if (mapaDocumentos.containsKey(estadistica.getCodigoPersonal()+"3")){
							vencePasaporte = mapaDocumentos.get(estadistica.getCodigoPersonal()+"3");
						}
						String registro = "<span class='label label-warning'>NO</span>";
						if (!venceFmm.equals("-") || !venceFm3.equals("-") || !vencePasaporte.equals("-")){
							registro = "<span class='label label-success'>SI</span>";
						}
%>
	<tr>
		<td><%=row%></td>
		<td><%=estadistica.getCodigoPersonal()%></td>
		<td><%=estadistica.getApellidoPaterno()%> <%=estadistica.getApellidoMaterno()%> <%=estadistica.getNombre()%></td>
		<td><%=estadistica.getSexo()%></td>		
		<td><%=paisNombre%></td>
		<td><%=modalidadNombre%></td>
		<td><%=venceFmm%></td>
		<td><%=venceFm3%></td>
		<td><%=vencePasaporte%></td>
		<td><%=registro%></td>
	</tr>
<%						
					}
				}	
			}
		}%>
	</table>
<%	} %>		
	 
</div>