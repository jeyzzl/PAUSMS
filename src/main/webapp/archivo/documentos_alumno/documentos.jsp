<%@ page import="java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumEstado"%>
<%@ page import="aca.archivo.spring.ArchDocAlum"%>
<%@ page import="aca.archivo.spring.ArchDocumentos"%>
<%@ page import="aca.archivo.spring.ArchStatus"%>
<%@ page import="aca.archivo.spring.ArchPermisos"%>
<%@ page import="aca.archivo.spring.ArchGrupos"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.archivo.spring.ArchGrupo"%>
<%@ page import="aca.archivo.spring.ArchGrupoDocumento"%>
<%@ page import="aca.archivo.spring.ArchDocStatus"%>
<%@ page import="aca.archivo.spring.ArchRevalida"%>

<%-- <%@ include file= "../../conectadbp.jsp" %> --%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String accion = (String) request.getParameter("Accion");
	if(accion!=null){
		session.setAttribute("codigoAlumno", request.getParameter("matricula"));
	}
	
	// Declaracion de Variables
	String s_opcion 	= "I";
	
	String matricula	= (String) session.getAttribute("codigoAlumno");
	String grupoId 		= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
	String planId		= (String) request.getAttribute("planId");
	
	String folio 			= (String) request.getAttribute("folio");
	String alumCarrera 		= (String) request.getAttribute("alumCarrera");
	String autorizaAlumno 	= (String) request.getAttribute("autorizaAlumno");	
	
	AlumPersonal alumno 	= (AlumPersonal)request.getAttribute("alumno");
	AlumPlan alumPlan 		= (AlumPlan)request.getAttribute("plan");
	AlumAcademico academico = (AlumAcademico)request.getAttribute("academico");
	//AlumEstado estado 		= (AlumEstado)request.getAttribute("estado");
	ArchRevalida revalida 	= (ArchRevalida)request.getAttribute("revalida");
	
	int numImgGeneral 	= (int) request.getAttribute("numImgGeneral");
	int numImgDocAlum	= (int) request.getAttribute("numImgDocAlum");
	
	// List que trae los datos
	List<ArchDocAlum> lisDocumentos = (List<ArchDocAlum>)request.getAttribute("lisDocumentos");
	
	// HashMaps
	HashMap<String,String> mapaDocumentos	= (HashMap<String, String>)request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaStatus	 	= (HashMap<String, String>)request.getAttribute("mapaStatus");
	HashMap<String,String> mapaUbicacion	= (HashMap<String, String>)request.getAttribute("mapaUbicacion");
	
	String gruposCarrera		= (String) request.getAttribute("gruposCarrera");
	String grupos[] = gruposCarrera.split(",");
	Arrays.sort(grupos);
	
	List<ArchPermisos> listaPermisos 		= (List<ArchPermisos>) request.getAttribute("listaPermisos");
	List<ArchGrupos> lisGrupos				= (List<ArchGrupos>) request.getAttribute("lisGrupos");
	List<String> lisPlanes					= (List<String>)request.getAttribute("lisPlanes");
	List<String> gruposDelPlan 				= (List<String>) request.getAttribute("gruposDelPlan");
	List<ArchDocStatus> lisValidos			= (List<ArchDocStatus>) request.getAttribute("lisValidos");
	List<ArchGrupoDocumento> lisPorGrupo	= (List<ArchGrupoDocumento>) request.getAttribute("lisPorGrupo");
	
	HashMap<String,String> mapaTotGrupo 				= (HashMap<String,String>) request.getAttribute("mapaTotGrupo");
	HashMap<String,String> mapaTotAlumno 				= (HashMap<String,String>) request.getAttribute("mapaTotAlumno");
	HashMap<String,MapaPlan> mapaPlanes 				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	
	HashMap<String,Boolean> mapaDocCompletos 			= (HashMap<String,Boolean>) request.getAttribute("mapaDocCompletos");
	HashMap<String,ArchGrupo> mapaGrupos 				= (HashMap<String,ArchGrupo>) request.getAttribute("mapaGrupos");
	HashMap<String, ArchDocAlum> mapaDocAlumno 			= (HashMap<String,ArchDocAlum>) request.getAttribute("mapaDocAlumno");
	HashMap<String, ArchDocAlum> mapaBuscaDocumento 	= (HashMap<String,ArchDocAlum>) request.getAttribute("mapaBuscaDocumento");
	
	boolean completo = false;
	String autorizaGrupo = "";
	
%>
<body onload='document.forma.matricula.focus()' marginwidth="0" marginheight="0" leftmargin="0" topmargin="0">
<div class="container-fluid">
	<h2>Student Documents <small class="text-muted fs-5">( <%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()%> - <%=academico.getModalidadId() %> )</small></h2>	
	<form name="frmDocumentos">
		<div class="alert alert-info d-flex align-items-center">
		Plan: &nbsp;
		<select name="PlanId" class="form-select" onchange="javascript:recarga()" style="width:750px;">	
	<%
		String nombrePlan = "";
		for(int i=0;i<lisPlanes.size();i++){		
			String plan = (String)lisPlanes.get(i);
			nombrePlan = "-";
			if (mapaPlanes.containsKey(plan)){
				nombrePlan = mapaPlanes.get(plan).getNombrePlan();
			}
	%>	
			<option value="<%=plan%>" <%=plan.equals(planId)?"Selected":""%> ><%=plan%>-<%=nombrePlan%></option>
	<%
		}
	%>	
	 	</select>&nbsp;	 	
	 &nbsp;<a class="btn btn-primary btn-sm" href="elegir_documento?f_codigo_personal=<%=matricula%>&s_opcion=<%=s_opcion%>"><i class="fas fa-plus-square"></i> Add Document</a>&nbsp;
	 &nbsp;<a class="btn btn-primary btn-sm" href="digital">
  				<img src="../../imagenes/scanner4.png"  width="20"> Digitize(<b><%=numImgDocAlum%></b>)  			
 			</a>&nbsp;
	  	&nbsp; <a class="btn btn-primary btn-sm" href="general"><i class="fas fa-folder"></i> Unclassified (<b><%=numImgGeneral%></b>)</a>&nbsp;
<%	if(!folio.equals("0")){ %>
  		&nbsp; <a target="_blank" href="../../admlinea/admision/documentos?Folio=<%=folio%>" class="btn btn-primary btn-sm"><i class="fas fa-folder-open"></i> Online Admission Documents</a>&nbsp;
  		&nbsp; <a target="_blank" href="../../admlinea/admision/solicitudPDF?Folio=<%=folio%>" class="btn btn-primary btn-sm"><i class="fas fa-user"></i> Online Application Form</a>&nbsp;
<%	} %>
<% if(revalida.getRevalida().equals("N")){ %>
  		&nbsp; <a href="documentos?PlanId=<%=planId%>&GrupoId=<%=grupoId%>&CodigoPersonal=<%=matricula%>" class="btn btn-warning btn-sm"><i class="fas fa-check"></i> Verify</a>
<%	}else {%>
  		&nbsp; <a onclick="eliminar('<%=matricula%>');"  class="btn btn-success btn-sm"><i class="fas fa-times"></i> Verify</a>
<%	} %>
		</div>
	</form>
	
	<div class="alert alert-success d-flex align-items-center">			
		Document Verification: &nbsp;
<%
		boolean alumnoAutorizado = false; 
		for (String grupo : gruposDelPlan){
			String nombreGrupo 	= "-";
			if (mapaGrupos.containsKey(grupo)){
				nombreGrupo 	= mapaGrupos.get(grupo).getGrupoNombre();
			}
			if (mapaDocCompletos.containsKey(grupo)){
				completo = mapaDocCompletos.get(grupo);
			}
			if (completo){
				autorizaGrupo += grupo+",";
				alumnoAutorizado = true;
			}
			
			if (completo){
		%>
			<a href="documentos?PlanId=<%=planId%>&GrupoId=<%=grupo%>">
				<span class='badge bg-dark'><%=grupo%>-<%=nombreGrupo%></span> 	
			</a>&nbsp;&nbsp;&nbsp;
		<%
			}else if (!completo){
		%>
			<a href="documentos?PlanId=<%=planId%>&GrupoId=<%=grupo%>">
				<span class='badge bg-warning'><%=grupo%>-<%=nombreGrupo%></span> 	
			</a>&nbsp;&nbsp;&nbsp;
		<%
			}else{
				out.print("<span class='badge bg-dark'>Group "+grupo+"</span> ");
			}
		}
		%>
		&nbsp;&nbsp;&nbsp;
		<%		if (alumnoAutorizado){ %>
		Authorized by the group: <%=autorizaGrupo.substring(0, autorizaGrupo.length() - 1)%>
		<% 		}%>			
		&nbsp;&nbsp;&nbsp;
		Permissions: <a href="<%=listaPermisos.size() > 0 ? "listaPermisosAlumno" : "#"%>" class="badge bg-success" style="font-size:12px;"><%=listaPermisos.size()%></a>
	</div>	
	<%	if (!grupoId.equals("0")){
		String nombreGrupo 	= "-";
		String comentario 	= "-"; 
		if (mapaGrupos.containsKey(grupoId)){
			nombreGrupo 	= mapaGrupos.get(grupoId).getGrupoNombre();
			comentario 		= mapaGrupos.get(grupoId).getComentario();
		}
%>
	<div class="alert alert-info d-flex align-items-center"><b>Group: <%=grupoId%> - <%=nombreGrupo%></b></div>
	<table class="table table-sm">
	<thead>
		<tr>
			<td colspan="4"><%=comentario%></td>	  		  				
			<td colspan="5" style="text-align: center">Conditions for Verification</td>
		</tr>			  		  				
		<tr> 
			<th width="2%">#</th>			
			<th width="2%">&nbsp;</th>		
			<th width="21%">File</th>
			<th width="15%">Status</th>
			<th width="12%">Option 1</th>
			<th width="12%">Option 2</th>
			<th width="12%">Option 3</th>
			<th width="12%">Option 4</th>
			<th width="12%">Option 5</th>		
		</tr>
	</thead>
	<tbody>					
<%
		int cont= 1;
		for (ArchGrupoDocumento grupo : lisPorGrupo){
			String existe = "<i class='fas fa-times' ></i>";
		
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey(grupo.getDocumentoId())){
				nombreDocumento = mapaDocumentos.get(grupo.getDocumentoId());
			}
			
			String statusId		= "0";
			String nombreStatus = "X";			
			for (ArchDocStatus valido : lisValidos){
				if (valido.getIdDocumento().equals(grupo.getDocumentoId())){
					if (mapaDocAlumno.containsKey(valido.getIdDocumento()+valido.getIdStatus())){
						existe 		= "<i class='fas fa-check'  style='color:green'></i>";
						statusId 	= mapaDocAlumno.get(valido.getIdDocumento()+valido.getIdStatus()).getIdStatus();
					}
				}
			}
			
			if (mapaStatus.containsKey(statusId)){
				nombreStatus = mapaStatus.get(statusId);
			}else if (mapaBuscaDocumento.containsKey(grupo.getDocumentoId())){
				nombreStatus = mapaBuscaDocumento.get(grupo.getDocumentoId()).getIdStatus();
				if (mapaStatus.containsKey(nombreStatus)){
					nombreStatus = mapaStatus.get(nombreStatus)+" does not meet the requirements";
				}	
			}else{
				nombreStatus = "Not found!";
			}
%>
	<tr> 
		<td align="center"><%=cont++%></td>		
		<td align="center"><%=existe%></td>		
		<td><%=nombreDocumento%></td>
		<td><%=nombreStatus%></td>
<%	
		int opciones = 0;
		for (ArchDocStatus valido : lisValidos){
			if (valido.getIdDocumento().equals(grupo.getDocumentoId())){
				opciones++;				
			
				String nombreEstado = "-";						
				if (mapaStatus.containsKey(valido.getIdStatus())){
						nombreEstado = mapaStatus.get(valido.getIdStatus());
				}			
				
				existe = "<i class='fas fa-times-circle'  style='color:red'></i>";
				if (mapaDocAlumno.containsKey(valido.getIdDocumento()+valido.getIdStatus())){
					existe = "<i class='fas fa-check-circle' ></i>";
				}
				out.print("<td>"+existe+" "+nombreEstado+"</td>");
			}
		}
		for (int i=0; i < 5-opciones; i++){
			out.print("<td>-</td>");
		}
%>	
	</tr>
<%		}%>
	</tbody>			
	</table>
<%	} %>
	<form id="forma" name="forma" action="documentos?Accion=grabar" method='post'>
		<table class="table table-sm" width="90%">
			<tr>
				<td colspan="9" style="font-size:14px">
				  <b>Studies:</b> <%=alumCarrera%> [<%=alumPlan.getPlanId()%>]&nbsp;&nbsp;
				  <b><spring:message code="aca.Status"/>:</b> <%=autorizaAlumno%>
				</td>
			</tr>	  	
		  	<tr>
				<th><spring:message code="aca.Operacion"/></th>
				<th><spring:message code="aca.Numero"/></th>
				<th><spring:message code="aca.Documento"/></th>
				<th><spring:message code="aca.Status"/></th>
				<th>Location</th>
				<th><spring:message code="aca.Fecha"/></th>
				<th class="text-end">Amount.</th>
				<th class="text-center"><spring:message code='aca.Usuario'/></th>
				<th class="text-center">No. Img.</th>
		  	</tr>		
<%		// Aplicacion para acomodar los datos
		for (int i=0; i< lisDocumentos.size(); i++){
			ArchDocAlum doc = lisDocumentos.get(i);		
%> 				
		  	<tr class="tr2">
		    	<td align="center">
					<a title="Digitalize document" onclick="location.href='addimg?DocumentoId=<%=doc.getIdDocumento()%>&Prev=1&Hoja=1';" ><i class="fas fa-upload text-primary"></i></a>
					&nbsp;&nbsp;
					<a href="grabar?IdDocumento=<%=doc.getIdDocumento()%>&IdStatus=<%=doc.getIdStatus()%>&Cantidad=<%=doc.getCantidad()%>&Matricula=<%=doc.getMatricula()%>&Ubicacion=<%=doc.getUbicacion()%>" valign="top"><i class="fas fa-edit"></i></a>							
					&nbsp;&nbsp;
<% 			if(numImgDocAlum < 1){%>
					<a href="accion?Accion=2&IdDocumento=<%=doc.getIdDocumento()%>" onclick="javascript:if(confirm('Are you sure you want to delete this document?')) return true; else return false;" valign="top"><i class="fas fa-trash"></i></a>
<% 			}%>
			    </td>
				<td align="center"><%=doc.getIdDocumento()%></td>
				<td><%=mapaDocumentos.get(doc.getIdDocumento())%></td>
				<td><%=mapaStatus.get(doc.getIdStatus())%></td>
				<td><%=mapaUbicacion.get(doc.getUbicacion())==null?"-":mapaUbicacion.get(doc.getUbicacion())%></td>
				<td><%=doc.getFecha()%></td>
				<td class="text-end"><%=doc.getCantidad()%></td>
				<td class="text-center"><%=doc.getUsuario()%></td>
				<td class="text-center">
				  <%-- <%=aca.pg.archivo.ArchDocAlum.numImagenes(conn2,doc.getMatricula(),doc.getIdDocumento())%> --%>
				</td>
		  	</tr>
<%				}	%>
		  	<tr>
		  	  <td colspan="9">&nbsp;</td>
		  	</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	function recarga(){	
		document.location.href="documentos?PlanId="+document.frmDocumentos.PlanId.value;
	}
	
	function eliminar(codigoPersonal){
		if(confirm("Are you sure you want to remove the verification?")){
			location.href='documentos?BorrarRevalida='+codigoPersonal;
		}
	}
</script>
<%-- <%@ include file= "../../cierradbp.jsp" %> --%>