<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>

<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.archivo.spring.ArchDocAlum"%>
<%@page import="aca.archivo.spring.ArchDocStatus"%>
<%@page import="aca.archivo.spring.ArchGrupos"%>
<%@page import="aca.archivo.spring.ArchPermisos"%>
<%@page import="aca.archivo.spring.ArchGrupoDocumento"%>
<%@page import="aca.archivo.spring.ArchGrupo"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.tit.spring.TitAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String grupoId 				= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String colorPortal 			= (String)session.getAttribute("colorPortal");	
	String planId				= (String) request.getAttribute("planId");	
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");
	String gruposCarrera		= (String) request.getAttribute("gruposCarrera");
	String autorizaAlumno 		= (String) request.getAttribute("autorizaAlumno");	

	List<CargaAlumno> lisPlanesActivos			= (List<CargaAlumno>) request.getAttribute("lisPlanesActivos");
	List<ArchDocStatus> lisValidos				= (List<ArchDocStatus>) request.getAttribute("lisValidos");
	List<String> lisPlanesAlumno				= (List<String>) request.getAttribute("lisPlanesAlumno");
	List<ArchDocAlum> lisDocumentos 			= (List<ArchDocAlum>) request.getAttribute("lisDocumentos");
	List<String> lisPlanes						= (List<String>)request.getAttribute("lisPlanes");
	List<TitAlumno> lisAlumno 					= (List<TitAlumno>) request.getAttribute("lisAlumno");
	List<ArchPermisos> listaPermisos 			= (List<ArchPermisos>) request.getAttribute("listaPermisos");	
	List<ArchGrupoDocumento> lisPorGrupo		= (List<ArchGrupoDocumento>) request.getAttribute("lisPorGrupo");	
	List<String> gruposDelPlan 					= (List<String>) request.getAttribute("gruposDelPlan");
	
	HashMap<String,ArchGrupo> mapaGrupos 		= (HashMap<String,ArchGrupo>) request.getAttribute("mapaGrupos");
	HashMap<String,String> mapaDocumentos 		= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
	HashMap<String,String> mapaStatus 			= (HashMap<String,String>) request.getAttribute("mapaStatus");
	HashMap<String,String> mapaUbicacion 		= (HashMap<String,String>) request.getAttribute("mapaUbicacion");	
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaNumDocumentos	= (HashMap<String,String>) request.getAttribute("mapaNumDocumentos");
	
	HashMap<String,Boolean> mapaDocCompletos 		= (HashMap<String,Boolean>) request.getAttribute("mapaDocCompletos");
	HashMap<String, ArchDocAlum> mapaDocAlumno 		= (HashMap<String,ArchDocAlum>) request.getAttribute("mapaDocAlumno");
	HashMap<String, ArchDocAlum> mapaBuscaDocumento = (HashMap<String,ArchDocAlum>) request.getAttribute("mapaBuscaDocumento");
	
	boolean completo = false;
	String autorizaGrupo = "";
	
	if(colorPortal==null) colorPortal="";	
%>
<html>
<head>	
</head>
<body>
<div class="container-fluid">
	<h3><spring:message code="Documentos"/>
	<small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%> &nbsp; 
			<b><spring:message code="portal.alumno.documentos.PlanesActivos"/>:</b>
<%
	String nombrePlan = "-";
	int row = 0;
	for (CargaAlumno carga : lisPlanesActivos) {
		if (mapaPlanes.containsKey(carga.getPlanId())){
			nombrePlan = mapaPlanes.get(carga.getPlanId()).getNombrePlan();
		}
		row++;
		if (row == 1)
			out.print(carga.getPlanId()+ " - "+nombrePlan);
		else
			out.print("," + carga.getPlanId()+ " - "+nombrePlan);
	}
%>			
			)
		</small>
	</h3>
	<form name="frmDocumentos" class="form-inline mb-0">			
	<div class="alert alert-info d-flex align-items-center">				
		<spring:message code="portal.alumno.documentos.Plan"/>: &nbsp;
		<select name="PlanId" onchange="javascript:recarga()" class="form-select" style="width:520px">	
	<%
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
	 	</select>	 		 		
	</div>	
	</form>
	<div class="alert alert-success">		
		<spring:message code="portal.alumno.documentos.DocumentosRequeridos"/>: 
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
			</a>
<%
			}else if (!completo){
%>
			<a href="documentos?PlanId=<%=planId%>&GrupoId=<%=grupo%>">
				<span class='badge bg-warning'><%=grupo%>-<%=nombreGrupo%></span> 	
			</a>
<%
			}else{
				out.print("<span class='badge bg-dark'>Grupo "+grupo+"</span> ");
			}
		}
%>
		&nbsp;&nbsp;&nbsp;
<%
		if (alumnoAutorizado){
%>
			Authorized by the group: <%=autorizaGrupo.substring(0, autorizaGrupo.length() - 1)%>
<%
		}
%>
		&nbsp;&nbsp;&nbsp;
		<%-- <spring:message code="portal.alumno.documentos.Permisos"/>: <a href="<%=listaPermisos.size() > 0 ? "listaPermisosAlumno" : "#"%>" class="badge bg-pill badge bg-success" style="font-size:12px;"><%=listaPermisos.size()%></a> --%>
	</div>	
	<%	if (!grupoId.equals("0")){
		String nombreGrupo 	= "-";
		String comentario 	= "-"; 
		if (mapaGrupos.containsKey(grupoId)){
			nombreGrupo 	= mapaGrupos.get(grupoId).getGrupoNombre();
			comentario 		= mapaGrupos.get(grupoId).getComentario();
		}
%>
	<table class="table table-sm">
		<tr>
			<td colspan="4"><%=comentario%></td>	  		  				
			<td colspan="5" style="text-align: center"><spring:message code="portal.alumno.documentos.CondicionesParaEstarAutorizado"/></td>
		</tr>			  		  				
		<tr> 
			<th width="2%">#</th>			
			<th width="2%">&nbsp;</th>			
			<th width="21%"><spring:message code="portal.alumno.documentos.Documento"/></th>
			<th width="15%"><spring:message code="portal.alumno.documentos.Estado"/></th>
			<th width="12%"><spring:message code="portal.alumno.documentos.Opcion"/> 1</th>
			<th width="12%"><spring:message code="portal.alumno.documentos.Opcion"/> 2</th>
			<th width="12%"><spring:message code="portal.alumno.documentos.Opcion"/> 3</th>
			<th width="12%"><spring:message code="portal.alumno.documentos.Opcion"/> 4</th>
			<th width="12%"><spring:message code="portal.alumno.documentos.Opcion"/> 5</th>		
		</tr>				
<%
		int cont= 1;
		for (ArchGrupoDocumento grupo : lisPorGrupo){
			String existe = "<i class='fas fa-times' ></i>";
		
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey(grupo.getDocumentoId())){
				nombreDocumento = mapaDocumentos.get(grupo.getDocumentoId());
			}
			
			String statusId 	= "0";
			String nombreStatus = "X";			
			for (ArchDocStatus valido : lisValidos){
				if (valido.getIdDocumento().equals(grupo.getDocumentoId())){
					if (mapaDocAlumno.containsKey(valido.getIdDocumento()+valido.getIdStatus())){
						existe = "<i class='fas fa-check'  style='color:green'></i>";
						statusId = mapaDocAlumno.get(valido.getIdDocumento()+valido.getIdStatus()).getIdStatus();
					}
				}
			}
			
			if (mapaStatus.containsKey(statusId)){
				nombreStatus = mapaStatus.get(statusId);
			}else if (mapaBuscaDocumento.containsKey(grupo.getDocumentoId())){
				nombreStatus = mapaBuscaDocumento.get(grupo.getDocumentoId()).getIdStatus();
				if (mapaStatus.containsKey(nombreStatus)){
					nombreStatus = mapaStatus.get(nombreStatus)+" <b>�doesn't comply!</b>";
				}	
			}else{
				nombreStatus = "<b>�Don't have!</b>";
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
	</table>
<%	} %>
	<br>
	<table class="table table-condensed" width="90%">		  		  				
	<tr>
	  	<td colspan="8" style="text-align:left;"> 
	  		<%-- <a href="img/cartapoder.pdf" class="btn btn-outline-primary btn-sm" target="_blank"><i class="fas file-alt-pdf-o fa-1x" ></i><b>Power of Attorney</b></a>&nbsp;&nbsp;&nbsp;    	    
	     	<a href="digital" class="btn btn-outline-primary btn-sm"><b><img src="img/scanner.gif" > Academic Documents</a>&nbsp;&nbsp;&nbsp; --%>
<% 	if(codigoPersonal.equals(matricula)){%> 
	    	<%-- <a href="documentosExternos" class="btn btn-outline-primary btn-sm"><i class="fas fa-home" ></i> <b>Residence Documents</b></a> --%>
<%	}%>	  
<%-- 		  <b><spring:message code="aca.Estado"/>:</b> <%=autorizaAlumno%>    --%>
	    </td>
	</tr>
	<tr> 
		<th width="4%" height="15"><font size="4"><spring:message code="aca.Numero"/></font></th>
		<th width="35%" height="15"><font size="4"><spring:message code="aca.Documento"/></font></th>
		<th width="18%" height="15" ><font size="4"><spring:message code="aca.Estado"/></font></th>
		<th><font size="4"><spring:message code="portal.alumno.documentos.Ubicacion"/></font></th>
		<th width="12%" height="15"><font size="4"><spring:message code="aca.Fecha"/></font></th>
		<th width="6%" height="15"><font size="4"><spring:message code="datosAlumno.portal.DocsCant"/></font></th>
		<th width="7%" height="15"><font size="4"><spring:message code="aca.Usuario"/></font></th>
		<th width="7%" height="15"><font size="4"><spring:message code="aca.Numero"/> <spring:message code="datosAlumno.portal.DocsImg"/></font></th>
	</tr>				
<%
	for (ArchDocAlum doc : lisDocumentos){
		
		String nombreDocumento = "-";
		if (mapaDocumentos.containsKey(doc.getIdDocumento())){
			nombreDocumento = mapaDocumentos.get(doc.getIdDocumento());
		}
		
		String nombreStatus = "-";
		if (mapaStatus.containsKey(doc.getIdStatus())){
			nombreStatus = mapaStatus.get(doc.getIdStatus());
		}
		
		String ubicacionNombre = "";
		if (mapaUbicacion.containsKey(doc.getUbicacion())){
			ubicacionNombre = mapaUbicacion.get(doc.getUbicacion());
		}
		
		String numDocumentos = "0";
		if (mapaNumDocumentos.containsKey(doc.getMatricula()+doc.getIdDocumento())){
			numDocumentos 	= mapaNumDocumentos.get(doc.getMatricula()+doc.getIdDocumento());
		}
%>
	<tr class="tr2"> 
		<td width="4%" align="left"><font color="#000000" size="3"><%=doc.getIdDocumento()%></font></td>
		<td width="35%"><font color="#000000" size="3"><%=nombreDocumento%></font></td>
		<td width="18%"><font color="#000000" size="3"><%=nombreStatus%></font></td>
		<td><font size="3">
<%		if (ubicacionNombre.equals("Entregado")){%>
			<a href="docEntregados"><%=ubicacionNombre%></a>
<%
		}else{
			out.print(ubicacionNombre);
		} 
%>
		</font>
		</td>
		<td width="12%"><font color="#000000" size="3"><%=doc.getFecha()%></font></td>
		<td width="6%"><font color="#000000" size="3"><%=doc.getCantidad()%></font></td>
		<td width="7%"><font color="#000000" size="3"><%=doc.getUsuario()%></font></td>
		<td><font size="3"><%=numDocumentos%></font></td>					
	</tr>
<%	} %>
	</table>
</div>
<script type="text/javascript">
	function recarga(){	
		document.location.href="documentos?PlanId="+document.frmDocumentos.PlanId.value;
	}

	$('.nav-tabs').find('.doc').addClass('active');
</script>