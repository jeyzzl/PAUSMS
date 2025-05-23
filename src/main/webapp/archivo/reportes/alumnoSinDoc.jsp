<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.archivo.spring.ArchDocumentos"%>
<%@ page import="aca.vista.spring.Inscritos"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.carga.spring.Carga"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<script type="text/javascript">
	function Documento(){
		document.frmdoc.submit();
	}
	function Carga(){
		document.frmdoc.submit();
	}
	function Facultad(){
		document.frmdoc.submit();
	}
</script>
<%	
	String documentoId		= (String) request.getAttribute("documentoId");
	String cargaId			= (String) request.getAttribute("cargaId");
	String facultadId		= (String) request.getAttribute("facultadId");
	
	List <ArchDocumentos> lisDoc		= (List <ArchDocumentos>) request.getAttribute("lisDoc");
	List <Inscritos> lisAlumnos		 	= (List <Inscritos>) request.getAttribute("lisAlumnos");
	List <Carga> lisCargas		 		= (List <Carga>) request.getAttribute("lisCargas");
	List <CatFacultad> lisFacultades	= (List <CatFacultad>) request.getAttribute("lisFacultades");
	
	HashMap<String, String> mapDocumento	= (HashMap<String, String>) request.getAttribute("mapDocumento");
	HashMap<String, CatCarrera> mapCarrera	= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");
	int cont = 0;
	
%>
<div class="container-fluid">
	<h2>Enrolled Students without Documents</h2>	
	<div class="alert alert-info d-flex align-items-center">	
		<form name="frmdoc" class="row row-cols-lg-auto align-items-center" action="alumnoSinDoc" method="post">
			<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
			&nbsp;&nbsp;
			Load:
			<select name="CargaId" class="form-select" onChange="javascritp:Carga()" style="width:350px">
<%				for(Carga carga : lisCargas){
					if (cont++ == 0){
						out.print(" <option value='0'>All</option>");
					}
					if (carga.getCargaId().equals(cargaId)){
						out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getCargaId()+"-"+carga.getNombreCarga()+"</option>");
					}else{
						out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getCargaId()+"-"+carga.getNombreCarga()+"</option>");
					}				
				}
				cont = 0;
%>          
			</select>	
			&nbsp;&nbsp;
			School:
			<select name="FacultadId" class="form-select" onChange="javascritp:Facultad()" style="width:150px">
<%				for(CatFacultad facultad : lisFacultades){
					if (cont++ == 0){
						out.print(" <option value='0'>All</option>");
					}
					if (facultad.getFacultadId().equals(facultadId)){
						out.print(" <option value='"+facultad.getFacultadId()+"' Selected>"+facultad.getNombreCorto()+"</option>");
					}else{
						out.print(" <option value='"+facultad.getFacultadId()+"'>"+facultad.getNombreCorto()+"</option>");
					}				
				}
%>          
			</select>	
			&nbsp;&nbsp;
			Documents:
			<select name="DocumentoId" class="form-select" onChange="javascritp:Documento()" style="width:400px">
<%				for(ArchDocumentos archDoc : lisDoc){
					if (archDoc.getIdDocumento().equals(documentoId)){
						out.print(" <option value='"+archDoc.getIdDocumento()+"' Selected>"+ archDoc.getDescripcion()+"</option>");
					}else{
						out.print(" <option value='"+archDoc.getIdDocumento()+"'>"+ archDoc.getDescripcion()+"</option>");
					}				
				}
%>          
			</select>
		</form>		
	</div>
	<table class="table table-sm table-bordered">
		<tr> 
	    	<td colspan="4"><h3>Enrolled Students</h3></td>
	  	</tr>
	  	<tr> 
	  		<th>#</th>
	   		<th><spring:message code="aca.Matricula"/></th>
	    	<th><spring:message code="aca.Nombre"/></th>
	    	<th><spring:message code="aca.Carrera"/></th>
	  	</tr>
<%
	int row = 0;
	for (Inscritos alumnos : lisAlumnos){
		boolean show = true;
		if (!mapDocumento.containsKey(alumnos.getCodigoPersonal())){
			String carrera="";
			
			if (mapCarrera.containsKey(alumnos.getCarreraId())){
				CatCarrera car = mapCarrera.get(alumnos.getCarreraId());
				carrera = car.getNombreCarrera();
				if (!facultadId.equals("0")){
					if(!car.getFacultadId().equals(facultadId)){
						show = false;
					}
				}
			}
			if(show){
				row++;
%>
		<tr> 
	  		<td align="center"><%=row%></td>
	    	<td align="center"><%=alumnos.getCodigoPersonal()%></td>
	    	<td><%=alumnos.getApellidoPaterno() %> <%=alumnos.getApellidoMaterno() %>, <%=alumnos.getNombre() %></td>
	    	<td><%=carrera%></td>
		</tr>
<%			}
		}
	}%>
		</table>	
</div>