<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
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
	String codigoTemp		= "0";

	List<CatPais> lisPaises 						= (List<CatPais>)request.getAttribute("lisPaises");
	List<Estadistica> lisExtranjeros			 	= (List<Estadistica>)request.getAttribute("lisExtranjeros");	
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,String> mapaDocumentos			= (HashMap<String,String>)request.getAttribute("mapaDocumentos");	
%>
<div class="container-fluid">
	<h2>Extranjeros por país de origen</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="elegir"><spring:message code="aca.Regresar"/></a>
	</div>	
<%
	for (CatPais pais : lisPaises){
%>
	<div class="alert alert-info">
		<td><h3><%=pais.getPaisId()%>:<%=pais.getNombrePais()%></h3></td>
	</div>
	<table class="table table-bordered  table-sm">
	<tr>
		<th width="5%">#</th>
		<th width="10%">Matrícula</th>
		<th width="45%">Alumno</th>
		<th width="5%">Sexo</th>		
		<th width="10%">Modalidad</th>
		<th width="5%">FM-M</th>		
		<th width="5%">FM3</th>
		<th width="5%">Pasaporte</th>
		<th width="10%">Registrado</th>
	</tr>
<%		int row = 0;
		for (Estadistica estadistica : lisExtranjeros){					
			if (estadistica.getPaisId().equals(pais.getPaisId()) && !codigoTemp.equals(estadistica.getCodigoPersonal())){
				codigoTemp = estadistica.getCodigoPersonal();
				row++;			
				
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
		<td><%=modalidadNombre%></td>
		<td><%=venceFmm%></td>
		<td><%=venceFm3%></td>
		<td><%=vencePasaporte%></td>
		<td><%=registro%></td>
	</tr>
<%			
			}
		}%>
	</table>
<%	} %>		
	 
</div>