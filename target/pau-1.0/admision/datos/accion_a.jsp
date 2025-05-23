<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.log.spring.LogAlumno"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.spring.CatInstitucion"%>
<%@page import="aca.sep.spring.SepLugar"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatExtension"%>
<%@page import="aca.catalogo.spring.CatNivelInicio"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%	idJsp = "070_a"; %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type = "text/javascript">
	function Grabar(){
		if(document.frmacademico.CodigoPersonal.value!=""){			
			document.frmacademico.submit();
		}else{
			alert("Fill out the entire form");
		}
	}		
	function Borrar( ){		
		if(confirm("You are sure to delete the record?")==true){
			document.location.href="borrarAcademico";
		}		
	}	
</script>
<%
	String usuario 						= (String)session.getAttribute("codigoPersonal");	
	String codigoAlumno					= (String)session.getAttribute("codigoAlumno");
	AlumAcademico alumAcademico			= (AlumAcademico)request.getAttribute("alumAcademico");
	LogAlumno logAlumno					= (LogAlumno)request.getAttribute("logAlumno");
	Acceso acceso						= (Acceso)request.getAttribute("acceso");
	boolean existeAcademico				= (boolean)request.getAttribute("existeAcademico");
	String maxUpdate					= (String)request.getAttribute("maxUpdate");
	String cotejado						= (String)request.getAttribute("cotejado");
	String nombreAlumno					= (String)request.getAttribute("nombreAlumno");
	String usuarioActualizo				= (String)request.getAttribute("usuarioActualizo");
	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	boolean esInscrito					= (boolean)request.getAttribute("esInscrito");
	
	List<CatTipoAlumno> lisTiposAlumno		= (List<CatTipoAlumno>)request.getAttribute("lisTiposAlumno");
    List<CatInstitucion> lisInstituciones 	= (List<CatInstitucion>)request.getAttribute("lisInstituciones");
    List<SepLugar> lisSepLugares 			= (List<SepLugar>)request.getAttribute("lisSepLugares");
    List<CatModalidad> lisModalidades		= (List<CatModalidad>)request.getAttribute("lisModalidades");
    List<CatExtension> lisExtensiones		= (List<CatExtension>)request.getAttribute("lisExtensiones");
	List<CatNivelInicio> lisNivelInicio		= (List<CatNivelInicio>)request.getAttribute("lisNivelInicio");
%>
<div class="container-fluid">
	<h2>Academic Data <small class="text-muted fs-5">(Student: <%=codigoAlumno%>&nbsp; <%=nombreAlumno%>)</small></h2>
	<div class="alert alert-info">
		<a href="accion_u" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_e" title="Background Data"><i class="fas fa-file-signature fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_o" title="Bank Data"><i class="fas fa-credit-card fa-lg"></i></a>
	</div>

<form action="grabarAcademico" method="post" name="frmacademico" target="_self">
	<input type="hidden" name="CodigoPersonal" value="<%=codigoAlumno%>">
	<table class="table table-condensed" style="width:90%">
		<tr>
		
			<td><strong>Residence Status</strong></td>
			<td><input type="hidden" name="ResidenciaId" value="<%=alumAcademico.getResidenciaId()%>"> 
<%
	 	if (alumAcademico.getResidenciaId().equals("E")) {
	 		out.print("Day Student"); 
	 	} else {
	 		out.print("Boarding Student"); 
	 	}
 %></td>
			<td><strong><spring:message code="aca.Dormitorio"/></strong></td>
			
			<td>
				<input id="Dormitorio" name="Dormitorio" type="hidden" value="<%=alumAcademico.getDormitorio()==null?"0":alumAcademico.getDormitorio()%>"> 
				<%=alumAcademico.getDormitorio()==null?"0":alumAcademico.getDormitorio()%>
			</td>
		</tr>
		<tr>
			<td><strong>Financial Class</strong></td>
			<td><input type="hidden" name="ClasFin" value="<%=alumAcademico.getClasFin()%>"> 
<%
	 	if (alumAcademico.getClasFin().equals("1")) {
	 		out.print("ACFE");
	 	} else {
	 		out.print("NOT ACFE");
	 	}
 %></td>
			<td><strong>Application Date</strong></td>
			<td>
				<input name="FSolicitud" type="text" class="text" id="FSolicitud" data-date-format="dd/mm/yyyy" value="<%=alumAcademico.getFSolicitud()%>" onfocus="focusFecha(this);" size="12" maxlength="10" placeholder="DD/MM/YYYY"> 
				(DD/MM/YYYY)
			</td>
		</tr>
		<tr>
			<td><strong>Financial Type</strong></td>
			<td><input type="hidden" name="Obrero" value="<%=alumAcademico.getObrero()%>">
		<%
			if (alumAcademico.getObrero().equals("H"))
				out.print("Employee's child");
			else if (alumAcademico.getObrero().equals("E"))
				out.print("Employee");
			else if (alumAcademico.getObrero().equals("I"))
				out.print("Worker's child");
			else if (alumAcademico.getObrero().equals("O"))
				out.print("Worker");
			else if (alumAcademico.getObrero().equals("N"))
				out.print("Other");
			else
				out.print("Other");
		%>
			</td>
			<td><strong>Admission Date</strong></td>
			<td>
				<input name="FAdmision" type="text" data-date-format="dd/mm/yyyy" class="text" id="FAdmision" value="<%=alumAcademico.getFAdmision()%>" onfocus="focusFecha(this);" size="12" maxlength="10"> 
				(DD/MM/YYYY)
			</td>
		</tr>
		<tr>
			<td><strong>Sponsorship</strong></td>
			<td><input type="hidden" name="ObreroInstitucion" value="<%=alumAcademico.getObreroInstitucion()%>">
		<%		
			boolean pusoInstitucion = false;
			for (CatInstitucion institucion : lisInstituciones) {
				if (institucion.getInstitucionId().equals(alumAcademico.getObreroInstitucion())) {
					out.print(institucion.getNombreInstitucion());
					pusoInstitucion = true;
				}
			}
			if (!pusoInstitucion) out.print("None");
		%>
			</td>
			<td><strong>Entry Date</strong></td>
			<td>
				<input name="FAlta" type="text" class="text" data-date-format="dd/mm/yyyy" id="FAlta" value="<%=alumAcademico.getFAlta()%>" onfocus="focusFecha(this);" size="12" maxlength="10">
				(DD/MM/YYYY)
			</td>
		</tr>
		<tr>
			<td><strong>Student Type</strong></td>
			<td>
				<select name="TipoAlumno" id="TipoAlumno">
				<%
					boolean pusoTipo = false;
					for (CatTipoAlumno tipoAlumno : lisTiposAlumno) {
						if (tipoAlumno.getTipoId().equals(alumAcademico.getTipoAlumno())) {
							out.print(" <option value='"+tipoAlumno.getTipoId()+"' Selected>"+ tipoAlumno.getNombreTipo() +"</option>");
							pusoTipo = true;
						}else{
							out.print(" <option value='"+tipoAlumno.getTipoId()+"'>"+ tipoAlumno.getNombreTipo() +"</option>");
						}
					}
					if (!pusoTipo) out.print("Regular");
				%>
				</select>
			</td>
			<td><strong>Modality</strong></td>
			<td>
			<%	if (alumAcademico.getModalidadId().trim().equals(""))
						alumAcademico.setModalidadId("1"); 
			%>
						<select name="ModalidadId" id="ModalidadId">
			<%				for (CatModalidad modalidad : lisModalidades) {
								if (modalidad.getModalidadId().equals(alumAcademico.getModalidadId())) {
									out.print(" <option value='"+ modalidad.getModalidadId() + "' Selected>"+ modalidad.getNombreModalidad() + "</option>");
								} else {
									out.print(" <option value='"+ modalidad.getModalidadId() + "'>"+ modalidad.getNombreModalidad() + "</option>");
								}
							} %>
						</select>		
			</td>
		</tr>
		<tr>
			<td><strong>Affiliation:</strong></td>
			<td>
				<select name="ExtensionId" id="ExtensionId">
				<%
					if (alumAcademico.getExtensionId().equals("")||alumAcademico.getExtensionId()==null) alumAcademico.setExtensionId("10");
					for (CatExtension extension : lisExtensiones) {
						if (extension.getExtensionId().equals(alumAcademico.getExtensionId())) {
							out.print(" <option value='" + extension.getExtensionId()+ "' Selected>" + extension.getNombreExtension()+ "</option>");
						} else {
							out.print(" <option value='" + extension.getExtensionId()+ "'>" + extension.getNombreExtension()+ "</option>");
						}
					}
				%>
				</select>
			</td>
			<td><strong>Entry Level:</strong></td>
			<td>
				<select name="NivelInicioId" id="NivelInicioId">
				<%
					for (CatNivelInicio nivelInicio : lisNivelInicio) {
						if (nivelInicio.getNivelInicioId().equals(alumAcademico.getNivelInicioId())) {
							out.print(" <option value='" + nivelInicio.getNivelInicioId()+ "' Selected>" + nivelInicio.getNombreNivel()+ "</option>");
						} else {
							out.print(" <option value='" + nivelInicio.getNivelInicioId()+ "'>" + nivelInicio.getNombreNivel()+ "</option>");
						}
					}
				%>
				</select>
			</td>
		</tr>
	</table>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary" style="float:left">Save</a>&nbsp;
<%	if(existeAcademico){%>				
		<a href="javascript:Borrar()" class="btn btn-primary">Delete</a>&nbsp;&nbsp;
<%		if (!maxUpdate.equals("0")){%>
		<span style="font-size:10px">Updated: <%=logAlumno.getFecha()%> by <%=usuarioActualizo%></span>
<%		}else{
			out.print("<span style='font-size:10px'> Not updated </span>");
		}
	}else{
		out.print("Student not registered");
	}
%>
	</div>
<script>
	jQuery('#FSolicitud').datepicker();
	jQuery('#FAdmision').datepicker();
	jQuery('#FAlta').datepicker();
</script>
</form>
</div>