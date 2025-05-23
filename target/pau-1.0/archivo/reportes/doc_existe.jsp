
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.archivo.spring.ArchDocumentos"%>
<%@ page import="aca.vista.spring.Inscritos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Documento(){
		document.frmdoc.Accion.value = "1";
		document.frmdoc.submit();
	}
</script>
<%	
	String documentoId		= (String) request.getAttribute("documentos");
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String sBgcolor			= "";
	
	List<ArchDocumentos> lisDoc			= (List<ArchDocumentos>)request.getAttribute("lisDoc");
	List<Inscritos> lisAlumnos			= (List<Inscritos>)request.getAttribute("lisAlumnos");

	HashMap<String, String> mapDocumento		= (HashMap<String, String>)request.getAttribute("mapDocumento");
	HashMap<String, String> mapImagenes			= (HashMap<String, String>)request.getAttribute("mapImagenes");
	HashMap<String, CatCarrera> mapCarrera 		= (HashMap<String, CatCarrera>)request.getAttribute("mapCarrera");
	
	String COMANDO = "";
%>
<div class="container-fluid">
	<h2>Students per Document</h2>
	<form action="doc_existe" method="post" name="frmdoc">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<select name="DocumentoId" id="DocumentoId" class="form-select" style="width:450px">
<%			for(int i=0; i<lisDoc.size(); i++){
				ArchDocumentos archDoc = (ArchDocumentos) lisDoc.get(i);
				if (archDoc.getIdDocumento().equals(documentoId)){
					out.print(" <option value='"+archDoc.getIdDocumento()+"' Selected>"+ archDoc.getDescripcion()+"</option>");
				}else{
					out.print(" <option value='"+archDoc.getIdDocumento()+"'>"+ archDoc.getDescripcion()+"</option>");
				}				
			}
%>          
		</select>
		&nbsp; &nbsp;<a href="javascript:Documento()" class="btn btn-primary"><i class="fas fa-sync"></i></a>
	</div>
	<input name="Accion" type="hidden">
<%	if(accion.equals("1")){	%>
	<table class="table table-sm table-bordered">
	<tr> 
    	<td colspan="6"><h3>Enrolled Students</h3></td>
  	</tr>
  	<tr> 
  		<th>#</th>
   		<th><spring:message code="aca.Matricula"/></th>
    	<th><spring:message code="aca.Nombre"/></th>
    	<th><spring:message code="aca.Carrera"/></th>
    	<th>Exist</th>
    	<th>Digitalized</th>
  	</tr>
<%
	int row = 0;
	for (Inscritos alumnos : lisAlumnos){
		
		String carrera="";
		
		String tieneDocumento = "NO";
		if (mapDocumento.containsKey(alumnos.getCodigoPersonal())){
			tieneDocumento = "YES";
		}
		
		String tieneImagen = "NO";
		if (mapImagenes.containsKey(alumnos.getCodigoPersonal())){
			tieneImagen = "YES";
		}		
		
		if (mapCarrera.containsKey(alumnos.getCarreraId())){
			CatCarrera car = mapCarrera.get(alumnos.getCarreraId());
			carrera = car.getNombreCarrera();
		}
		if (tieneDocumento.equals("YES")){			
			row++;
			tieneDocumento = "<span class='badge bg-success'>YES</span>";
			if (tieneImagen.equals("YES")) tieneImagen = "<span class='badge bg-success'>YES</span>";
%>
	<tr> 
  		<td align="center"><%=row%></td>
    	<td align="center"><%=alumnos.getCodigoPersonal()%></td>
    	<td><%=alumnos.getApellidoPaterno() %> <%=alumnos.getApellidoMaterno() %>, <%=alumnos.getNombre() %></td>
    	<td><%=carrera%></td>
    	<td><%=tieneDocumento%></td>    
    	<td><%=tieneImagen%></td>	
	</tr>
<%	
		}
	}
%>
	</table>
	</form>
</div>
<%	}	%>