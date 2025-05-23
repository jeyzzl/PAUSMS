<%@ page buffer= "none"%>
<%@ page import= "java.util.List"%>
<%@ page import="java.util.Arrays"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat,java.text.DecimalFormat" %>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.archivo.spring.ArchGrupos"%>
<%@ page import="aca.archivo.spring.ArchDocAlum"%>
<%@ page import="aca.emp.spring.EmpContacto"%>
<%@ page import="aca.financiero.spring.FinSaldo"%>
<%@ page import="aca.financiero.spring.FinCostos"%>
<%@ page import="aca.financiero.spring.FinCreditos"%>
<%@ page import="aca.leg.spring.LegDocumento"%>
<%@ page import="aca.leg.spring.LegExtdoctos"%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@ page import="aca.candado.spring.CandAlumno"%>
<%@ page import="aca.candado.spring.Candado"%>
<%@ page import="aca.catalogo.spring.CatReligion"%>
<%@ page import="aca.catalogo.spring.CatTipoCurso"%>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.matricula.spring.MatIngreso"%>
<%@ page import="aca.matricula.spring.MatCostos"%>
<%@ page import="aca.matricula.spring.MatAlumno"%>
<%@page import="aca.archivo.spring.ArchGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	DecimalFormat dmf			= new DecimalFormat("###,##0.00; (###,##0.00)");

	String grupoId 				= request.getParameter("GrupoId") == null ? "0" : request.getParameter("GrupoId");
	String eventoId 			= request.getParameter("EventoId") == null ? "0" : request.getParameter("EventoId");
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");
	String planId 				= (String) request.getAttribute("planId");
	String cargaId 				= (String) request.getAttribute("cargaId");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico	= (AlumAcademico) request.getAttribute("alumAcademico");
	AlumPlan alumPlan 			= (AlumPlan) request.getAttribute("alumPlan");
	MatAlumno matAlumno 		= (MatAlumno) request.getAttribute("matAlumno");
	AlumUbicacion alumUbicacion = (AlumUbicacion) request.getAttribute("alumUbicacion");
	MatIngreso matIngreso		= (MatIngreso) request.getAttribute("matIngreso");
	MatCostos matCostos			= (MatCostos) request.getAttribute("matCostos");
	EmpContacto empContacto		= (EmpContacto) request.getAttribute("empContacto");
	FinSaldo finSaldo 			= (FinSaldo) request.getAttribute("finSaldo");
	String paisNombre 			= (String) request.getAttribute("paisNombre");
	String estadoNombre 			= (String) request.getAttribute("estadoNombre");
	String razonNombre 			= (String) request.getAttribute("razonNombre");
	String tipoNombre 			= (String) request.getAttribute("tipoNombre");
	String asesorNombre 		= (String) request.getAttribute("asesorNombre");
	String autorizado	 		= (String) request.getAttribute("autorizado");
	boolean esExtranjero		= (boolean)request.getAttribute("esExtranjero");
	String fechaVencimiento		= (String) request.getAttribute("fechaVencimiento");
	String gruposCarrera		= (String) request.getAttribute("gruposCarrera");
	boolean finPermiso			= (boolean) request.getAttribute("finPermiso");	
	String tipoPlan				= (String) request.getAttribute("tipoPlan");
	
	FinCostos finCostos 		= (FinCostos) request.getAttribute("finCostos");
	FinCreditos finCreditos 	= (FinCreditos) request.getAttribute("finCreditos");
	
	List<AlumPlan> lisPlanes 				= (List<AlumPlan>) request.getAttribute("lisPlanes");
	List<ArchGrupos> lisGrupos				= (List<ArchGrupos>) request.getAttribute("lisGrupos");
	List<ArchDocAlum> lisDocumentos 		= (List<ArchDocAlum>) request.getAttribute("lisDocumentos");
	List<LegExtdoctos> lisExtDocumentos		= (List<LegExtdoctos>) request.getAttribute("lisExtDocumentos");
	List<CandTipo> lisTipos					= (List<CandTipo>) request.getAttribute("lisTipos");	
	List<CandAlumno> lisCandados 			= (List<CandAlumno>) request.getAttribute("lisCandados");	
	List<MapaCurso> lisCursosRezagados		= (List<MapaCurso>) request.getAttribute("lisCursosRezagados");	
	List<FinCostos> listFinCostos			= (List<FinCostos>) request.getAttribute("listFinCostos");	
	List<String> gruposDelPlan 				= (List<String>) request.getAttribute("gruposDelPlan");
	
	HashMap<String,Candado> mapaCandados 		= (HashMap<String,Candado>) request.getAttribute("mapaCandados");	
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaDocumentos 		= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
	HashMap<String,String> mapaStatus 			= (HashMap<String,String>) request.getAttribute("mapaStatus");
	HashMap<String,String> mapaUbicacion		= (HashMap<String,String>) request.getAttribute("mapaUbicacion");
	HashMap<String,String> mapaTotGrupo 		= (HashMap<String,String>) request.getAttribute("mapaTotGrupo");
	HashMap<String,String> mapaTotAlumno 		= (HashMap<String,String>) request.getAttribute("mapaTotAlumno");
	HashMap<String,String> mapaLegDocumentos	= (HashMap<String,String>) request.getAttribute("mapaLegDocumentos");
	HashMap<String,CatReligion> mapaReligiones	= (HashMap<String,CatReligion>) request.getAttribute("mapaReligiones");
	HashMap<String,CatTipoCurso> mapaTipoCurso	= (HashMap<String,CatTipoCurso>) request.getAttribute("mapaTipoCurso");
	HashMap<String,FinCreditos> mapaFinCreditos	= (HashMap<String,FinCreditos>) request.getAttribute("mapaFinCreditos");
	HashMap<String, Carga> mapaCargas			= (HashMap<String, Carga>) request.getAttribute("mapaCargas");
	HashMap<String,ArchGrupo> mapaGrupos 		= (HashMap<String,ArchGrupo>) request.getAttribute("mapaGrupos");
	HashMap<String,Boolean> mapaDocCompletos 	= (HashMap<String,Boolean>) request.getAttribute("mapaDocCompletos");
	
	String grupos[] 			= gruposCarrera.split(",");
	Arrays.sort(grupos);
	
	String saldoTotal 	= dmf.format(Float.parseFloat(finSaldo.getSaldoSP()))+" Db";
	String saldoVencido = dmf.format(Float.parseFloat(finSaldo.getSaldoVencido()))+" Db";
	
	if(Float.parseFloat(finSaldo.getSaldoSP()) < 0){
		saldoTotal 		= dmf.format(Float.parseFloat(finSaldo.getSaldoSP().substring(1)))+" Cr";
	}
	
	if(Float.parseFloat(finSaldo.getSaldoVencido()) < 0){
		saldoVencido 	= dmf.format(Float.parseFloat(finSaldo.getSaldoVencido().substring(1)))+" Cr";
	}
	
	if(saldoVencido.contains("Cr")){
		saldoVencido = "0";
	}
	
	boolean completo = false;
	
	boolean vencioFM3 		= false;
	if( ! alumPersonal.getNacionalidad().equals("91") ){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date hoy 			= new Date();
		String color		="",mensaje="";
		Date fechav 		= df.parse(fechaVencimiento);
		int dias 			= new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		if(dias>=30 && dias<61){
			color="#F39603";
			mensaje="TU FM3 VENCERÁ EN "+dias+" DIAS, Estás a tiempo de arreglar tu situaci&oacute;n migratoria.";
		}
		else if(dias<30){
			color="#CE0000";
			if(dias>=1) mensaje="TU FM3 VENCERÁ EN "+dias+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
			if(dias<=0){
				mensaje=" TU FM3 VENCIÓ HACE "+(dias*-1)+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
				vencioFM3 = true;
			}
		}else{
			color="";
			mensaje=dias + " dias restantes.";
		}							
	}
	
	String nombreReligion = "-"; 	
	if (mapaReligiones.containsKey(alumPersonal.getReligionId())){
		nombreReligion = mapaReligiones.get(alumPersonal.getReligionId()).getNombreCorto();
	}
	
%>
<head>			
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">	
</head>  	
<div id="container-fluid" style="margin: 10px 10px 0 10px; ">
	<div class="alert alert-info">
		<h3>Cédula del alumno<small class="text-muted fs-5"> ( <%=codigoAlumno%> - <%=alumnoNombre%>) </small></h3>
	</div>
	<form name="frmPlanes" action="alumno?Accion=1" method="post">
	<input type="hidden" name="EventoId" value="<%=eventoId%>">
	<input type="hidden" name="Matricula" value="<%=codigoAlumno%>">
	<div class="alert alert-info">
		<a href="listado?EventoId=<%=eventoId%>" class="btn btn-primary">&nbsp;Regresar</a>&nbsp;
		Plan:&nbsp;
		<select class="custom-select" name="PlanId" style="width:550px;" onChange="javascritp:ActualizarPlan()">
<% 		
	int row=0;
	for (AlumPlan plan : lisPlanes){
		row++;
		String nombrePlan = "-"; 	
		if (mapaPlanes.containsKey(plan.getPlanId())){
			nombrePlan = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
		}%>		
			<option value="<%=plan.getPlanId() %>" <%=plan.getPlanId().equals(planId) ? "selected" : ""%>>[<%=plan.getEstado().equals("0")?"S":"P"%>] - <%=plan.getPlanId()%> - <%=nombrePlan%></option>
<%	} %>
		</select>
		&nbsp;&nbsp;
		<%=tipoPlan%>	
		&nbsp;&nbsp;
		Semestre/tetra: <span class="badge bg-dark"><%=alumPlan.getCiclo()%></span>
		&nbsp;&nbsp;
		<select class="custom-select" name="Estado" style="width:155px;" onchange="javascritp:ActualizarPlan()">
			<option value="P" <%=matAlumno.getEstado().equals("P")?"selected='selected'":""%>>Pendiente</option>
			<option value="A" <%=matAlumno.getEstado().equals("A")?"selected='selected'":""%>>Aprobado</option>
			<option value="N" <%=matAlumno.getEstado().equals("N")?"selected='selected'":""%>>No aprobado</option>
		</select>
		&nbsp;&nbsp;
		Ubicación:
		<select class="custom-select" name="Modo" style="width:220px;" onchange="javascritp:ActualizarPlan()">
			<option value="P" <%=matAlumno.getModo().equals("P")?"selected='selected'":""%>>Campus UM</option>
			<option value="V" <%=matAlumno.getModo().equals("V")?"selected='selected'":""%>>Hogar/Virtual</option>			
		</select>
	</div>	
	</form>
	<div class="d-flex justify-content-around">
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Foto<h5></div>
		<div class="card-body">
			<img class="rounded border border-dark" src="../../foto?Codigo=<%=codigoAlumno%>&Tipo=O" width="100">
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Contacto<h5></div>
		<div class="card-body">
<!-- 			<p class="card-title">Alumno</p> -->
			<p style="font-size: 14px">
			<em>Correo: <small class="text-muted"><%=alumPersonal.getEmail()%></small></em><br>
			<em>Telefono: <small class="text-muted"><%=alumPersonal.getTelefono()%></small></em>
			</p>			
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Tutor<h5></div>
		<div class="card-body">
			<p style="font-size: 14px">
			<em>Nombre: <small class="text-muted"><%=alumUbicacion.gettNombre()%></small></em><br>
			<em>Email: <small class="text-muted"><%=alumUbicacion.gettEmail()%></small></em><br>
			<em>Telefono: <small class="text-muted"><%=alumUbicacion.gettTelefono()%></small></em>
			</p>			
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Asesor<h5></div>
		<div class="card-body"><p style="font-size: 14px">
			<em><%=asesorNombre%></em><br><small class="text-muted"><%=empContacto.getCorreo()%>
			<%=empContacto.getTelefono()%></small>
			</p>
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Nacionalidad<h5></div>
		<div class="card-body">
			<p style="font-size: 14px">
			<em><%=alumPersonal.getNacionalidad().equals("91")?"Mexicano <small class='text-muted'>("+estadoNombre+")</small>":"Extranjero <small class='text-muted'>("+paisNombre+")</small>"%></em>
			</p>
		</div>			
	</div>	
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Residencia<h5></div>
		<div class="card-body">
			<p style="font-size: 14px">
			<em><%=alumAcademico.getResidenciaId().equals("I")?"Interno <small class='text-muted'>(Dormitorio "+alumAcademico.getDormitorio()+")</small>":"Externo <small class='text-muted'>("+razonNombre+")</small>"%></em>
			</p>
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Clasificación<h5></div>
		<div class="card-body">
			<p style="font-size: 14px">
				<em><%=alumPersonal.getReligionId().equals("1")?"ACFE":"NO ACFE"%><small class='text-muted'> (<%=nombreReligion%>)</small></em><br>
				<em>Tipo<small class="text-muted fs-5"> (<%=tipoNombre%>)</small></em>
			</p>
		</div>			
	</div>
	<div class="card bg-light" style="max-width: 14rem;">
		<div class="card-header"><h5>Saldo<h5></div>
		<div class="card-body">
			<p style="font-size: 14px">		
			<em>Total: <small class="text-muted"> $<%=saldoTotal%></small></em><br>
			<em>Vencido: <small class="text-muted">$<%=saldoVencido%></small></em> 
			</p>
		</div>			
	</div>
	</div>
	<div class="alert alert-info mt-2">
		Documentos académicos<small class="text-muted"> ( <%=autorizado%> ) </small> &nbsp; &nbsp; &nbsp;
		Documentos requeridos:&nbsp;
<%		
		for (String grupo : gruposDelPlan){
			String nombreGrupo 	= "-";
			if (mapaGrupos.containsKey(grupo)){
				nombreGrupo 	= mapaGrupos.get(grupo).getGrupoNombre();
			}
			if (mapaDocCompletos.containsKey(grupo)){
				completo = mapaDocCompletos.get(grupo);
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
	</div>
	<table class="table table-sm table-bordered" width="90%">
	<tr> 
		<th width="4%" height="15"><spring:message code="aca.Numero"/></th>
		<th width="35%" height="15"><spring:message code="aca.Documento"/></th>
		<th width="18%" height="15"><spring:message code="aca.Estado"/></th>
		<th>Ubicaci&oacute;n</th>
		<th width="12%" height="15"><spring:message code="aca.Fecha"/></th>
		<th width="6%" height="15"><spring:message code="datosAlumno.portal.DocsCant"/></th>
		<th width="7%" height="15"><spring:message code="aca.Usuario"/></th>		
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
%>
	<tr class="tr2"> 
		<td width="4%" align="center"><font color="#000000" size="2"><%=doc.getIdDocumento()%></font></td>
		<td width="35%"><font color="#000000" size="1"><%=nombreDocumento%></font></td>
		<td width="18%"><font color="#000000" size="1"><%=nombreStatus%></font></td>
		<td align="center">
<%		if (ubicacionNombre.equals("Entregado")){%>
			<a href="docEntregados"><%=ubicacionNombre%></a>
<%
		}else{
			out.print(ubicacionNombre);
		} 
%>
		</td>
		<td width="12%" align="center"><font color="#000000" size="1"><%=doc.getFecha()%></font></td>
		<td width="6%" align="center"><font color="#000000" size="1"><%=doc.getCantidad()%></font></td>
		<td width="7%" align="center"><font color="#000000" size="1"><%=doc.getUsuario()%></font></td>			
	</tr>
<%	}%>					
	</table>	
<%	if (!grupoId.equals("0")){%>	
	<table class="table table-condensed table-bordered" width="90%"  >
		<tr><th colspan="5">Documentos requeridos en el grupo: <span class="badge bg-success"><%=grupoId%></span></th></tr>				  		  				
		<tr> 
			<th width="10%">#</th>
			<th width="10%">¿Cumple?</th>
			<th width="20%">Documento</th>
			<th width="20%">Estado</th>
			<th width="40%">&nbsp;</th>
		</tr>				
<%

		int cont= 1;
		for (ArchGrupos grupo : lisGrupos){
			
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey(grupo.getIdDocumento())){
				nombreDocumento = mapaDocumentos.get(grupo.getIdDocumento());
			}
			
			String nombreEstado = "-";
			if (mapaStatus.containsKey(grupo.getIdStatus())){
				nombreEstado = mapaStatus.get(grupo.getIdStatus());
			}
			
			String existe = "<i class='fas fa-trash-alt'></i>";
			for (ArchDocAlum docAlum :lisDocumentos){
				if (docAlum.getIdDocumento().equals(grupo.getIdDocumento()) && docAlum.getIdStatus().equals(grupo.getIdStatus())){
					existe = "<span class='glyphicon glyphicon-ok'></span>";
				}
			}
%>
	<tr> 
		<td align="center"><%=cont++%></td>
		<td align="center"><%=existe%></td>
		<td><%=nombreDocumento%></td>
		<td><%=nombreEstado%></td>
		<td>&nbsp;</td>
	</tr>
<%		}
	}	
%>	
	</table>	
<%	if (esExtranjero){%>	
	<div class="alert alert-info mt-2">Documentos Migratorios</div>
	<table class="table table-sm table-bordered">
	<thead>
		<tr> 
			<th width="10%">#</th>
			<th width="10%">Documento</th>
			<th width="20%">Número Doc.</th>
			<th width="20%">Vencimiento</th>						
		</tr>
	</thead>	
	<tbody>
<%	
		int line=0;
		for (LegExtdoctos docExt : lisExtDocumentos){
			line++;
			String docNombre = "-";
			if (mapaLegDocumentos.containsKey(docExt.getIdDocumento())){
				docNombre = mapaLegDocumentos.get(docExt.getIdDocumento());
			}
%>	
	<tr>
		<td><%=line%></td>	
		<td><%=docNombre%></td>
		<td><%=docExt.getNumDocto()%></td>
		<td><%=docExt.getFechaVence()%></td>
	</tr>
<%		} %>	
	</tbody>
	</table>
<% 	}%>	
	<div class="alert alert-info mt-2">Candados</div>
	<table class="table table-fullcondensed table-bordered">
		<tr>
			<th colspan="3">Aspectos a considerar antes de iniciar la inscripción</th>
		</tr>
<%		
	for(CandTipo candTipo : lisTipos){
		boolean tieneAutorizado = true;
		String nombreCandado = "";
		boolean tiene06 = false;
		
		if(candTipo.getEstado().equals("A")){
			for(int i=0; i<lisCandados.size();i++){
				CandAlumno candado = lisCandados.get(i);
					
				String tipoTemp = candado.getCandadoId().substring(0,2);			
				if(tipoTemp.equals("06")){ tiene06 = true; }
					
				String textoPermiso = "";
				if(tipoTemp.equals("04")  && finPermiso == true ){
					textoPermiso = "(Permiso de finanzas)";
				}
					
				if(candTipo.getTipoId().equals(tipoTemp)){
					tieneAutorizado = false;
					nombreCandado = "";
					if(mapaCandados.containsKey(candado.getCandadoId()) ){
						nombreCandado = mapaCandados.get(candado.getCandadoId()).getNombreCandado();
					}				
%>
		<tr>	
			<td class="center" width="5%">
<%				if (textoPermiso.equals("")){%>
				<img align="absmiddle" src="../../imagenes/candado.png"  />
<%				}else{ %>					
				<img align="absmiddle" src="../../imagenes/activa.png" width="15px"/>
<%				} %>
			</td>
			<td>
				&nbsp; <%=candTipo.getLugar()%>&nbsp;- <b><font color="#2E2EFE"><%=textoPermiso.equals("")?nombreCandado+" "+candTipo.getTelefono():textoPermiso%></font></b>
			</td>
		</tr>	
<% 
				}
			}
			if (tieneAutorizado){
%>		<tr>	
			<td class="center" width="5%">
				<img align="absmiddle" src="../../imagenes/activa.png" width="15px"/>
			</td>
			<td>
				<%=candTipo.getLugar()%>&nbsp; <b><font color="#2E2EFE"><%=nombreCandado%></font>
			</td>
		</tr>	
<%					
			}
			if(candTipo.getTipoId().equals("06") && vencioFM3){
				tieneAutorizado = false;
%>	
		<tr>		
			<td class="center"><img align="absmiddle" src="../../imagenes/candado.png"  /></td>
			<td>
				<%=candTipo.getLugar()%>&nbsp;-&nbsp;<b><font color="#AE2113">FM3 vencido</font></b>
				- <b><font color="#2E2EFE"><%=candTipo.getTelefono()%></font></b>
			</td>
		</tr>		
<% 			}
		}
	}
		if(autorizado.substring(0,1).equals("N")){ %> 
		<tr>
			<td class="center"><img align="absmiddle" src="../../imagenes/candado.png"/></td>
			<td colspan="7">
				VRA-Documentos-&nbsp;<b><font color="#AE2113">Certificación y Archivo</font></b>&nbsp;<b><font color="#2E2EFE">Ext. 3650 Correo: admision.um.edu.mx</font></b>
			</td>
		</tr>
<%		}else{%>
		<tr>
			<td class="center"><img align="absmiddle" src="../../imagenes/activa.png" width="15px"/></td>
			<td colspan="7">
				VRA-Documentos
			</td>
		</tr>
<%		}%>
	</table>
	<div class="alert alert-info mt-2">Materias Rezagadas</div>
		<table class="table table-condensed table-bordered" style="width:50%">
		<thead>
			<tr> 
				<th width="10%">#</th>
				<th width="5%">Ciclo</th>
				<th width="10%">Clave</th>
				<th width="60%">Materia</th>
				<th width="5%" class="right">Créditos</th>				
				<th width="10%">Tipo</th>
			</tr>
		</thead>
		<tbody>
<% 		int con = 1;
		int totalCred = 0;
		String tipoCurso = "-";
		for(MapaCurso curso : lisCursosRezagados){
			totalCred = totalCred+Integer.parseInt(curso.getCreditos());	
			
			tipoCurso = "";
			if(mapaTipoCurso.containsKey(curso.getTipoCursoId()) ){
				tipoCurso = mapaTipoCurso.get(curso.getTipoCursoId()).getCorto();
			}	
%>
			<tr>
				<td><%=con++%></td>
				<td><%=curso.getCiclo()%></td>
				<td><%=curso.getCursoClave()%></td>
				<td><%=curso.getNombreCurso()%></td>
				<td class="right"><%=curso.getCreditos()%></td>
				<td><%=tipoCurso%></td>
			</tr>
<% 		}%>
		</tbody>	
		<tfoot>
			<tr>
				<th colspan="4">Total creditos</th>
				<th class="right"><%=totalCred%></th>
				<th>&nbsp;</th>
			</tr>
		</tfoot>
	</table>
	<div class="alert alert-info mt-2"> 
    	Proyección Financiera&nbsp;
		<select class="custom-select" id="CargaId" style="width:400px;" onChange="javascript:ActualizarCarga('<%=codigoAlumno%>','<%=eventoId%>','<%=planId%>')">
<% 		for(FinCostos costos : listFinCostos){
			String nombreCarga = "-";
			if(mapaCargas.containsKey(costos.getCargaId())){
				nombreCarga = mapaCargas.get(costos.getCargaId()).getNombreCarga();
			}
		%>
			<option value="<%=costos.getCargaId()%>" <%=finCostos.getCargaId().equals(costos.getCargaId()) ? "selected" : ""%>><%=nombreCarga%></option>
<% 		}%>
		</select>
	</div>
	<table class="table table-condensed table-bordered" style="width:50%">
	<thead>
	<tr> 
		<th width="10%">#</th>
		<th width="30%">Concepto</th>
		<th width="20%" class="right">Importe</th>	
		<th width="40%">&nbsp;</th>
	</tr>		
	</thead>	
	<tbody>
	<tr> 
		<td>1</td>
		<td>Matricula</td>
		<td class="right">
<% 		if(alumPersonal.getPaisId().equals("91")){%>
			<%=dmf.format(Float.parseFloat(finCostos.getMatMex()))%>
			<input id="CostoMatricula" type="hidden" value="<%=finCostos.getMatMex()%>">
<% 		}else{%>
			<%=dmf.format(Float.parseFloat(finCostos.getMatExt()))%>
			<input id="CostoMatricula" type="hidden" value="<%=finCostos.getMatExt()%>">
<% 		}%>
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr> 
		<td>2</td>
		<td>
			Enseñanza
			<form action="grabaCreditos" name="formMatCostos" method="post">
				<input name="Matricula" type="hidden" value="<%=codigoAlumno%>">
				<input name="EventoId" type="hidden" value="<%=eventoId%>">
				<input name="PlanId" type="hidden" value="<%=planId%>">
				<input name="CargaId" type="hidden" value="<%=cargaId%>">
				<input id="Creditos" name="Creditos" type="text" value="<%=matCostos.getCreditos()%>" style="width: 45px;">
				<button type="submit" class="btn btn-primary btn-sm"><i class="fas fa-save"></i></button>
			</form>
		</td>
		<td class="right">
<% 		if(alumAcademico.getClasFin().equals("1")){%>
			<input id="FinCreditos" type="hidden" value="<%=finCreditos.getNorAcfe()%>">
<% 		}else{%>
			<input id="FinCreditos" type="hidden" value="<%=finCreditos.getNorNoAfce()%>">
<% 		}%>
			<div class="resultado"></div>
		</td>
		<td>&nbsp;</td>		
	</tr>
	<tr> 
		<td>3</td>
		<td>Internado</td>
		<td class="right">
<% 		if(alumAcademico.getResidenciaId().equals("E")){%>
			<%=dmf.format(Float.parseFloat("0"))%>
			<input id="CostoInternado" type="hidden" value="0">
<% 		}else{%>
			<%=dmf.format(Float.parseFloat(finCostos.getInternado()))%>
			<input id="CostoInternado" type="hidden" value="<%=finCostos.getInternado()%>">
<% 		}%>
		</td>		
		<td>&nbsp;</td>
	</tr>
	<tr> 
		<td>4</td>
		<td>Alimentos</td>
		<td class="right">
<% 		if(alumAcademico.getResidenciaId().equals("E")){%>
			<%=dmf.format(Float.parseFloat("0"))%>
			<input id="CostoAlimentos" type="hidden" value="0">
<% 		}else{%>
			<%=dmf.format(Float.parseFloat(finCostos.getComedor()))%>
			<input id="CostoAlimentos" type="hidden" value="<%=finCostos.getComedor()%>">
<% 		}%>
		</td>		
	</tr>
	<tr> 
		<td>5</td>
		<td>Costo total del periodo</td>
		<td class="right total"></td>
	</tr>
	<tr>	
		<th colspan="2">
			<input id="SaldoTotal" type="hidden" value="<%=saldoTotal%>">
			Saldo actual
		</th>
		<th class="right">
			<%=saldoTotal%>
		</th>
		<th>&nbsp;</th>		
	</tr>
	<tr>		
		<th colspan="2">Saldo total</th>
		<th class="right salTotal"></th>
		<th>&nbsp;</th>		
	</tr>
	</tbody>
	</table>
	<form action="grabarMatIngreso" name="formMatIngreso" method="post">
		<input type="hidden" name="Matricula" value="<%=matIngreso.getMatricula()%>">		
		<input type="hidden" name="EventoId" value="<%=matIngreso.getEventoId()%>">		
		<div class="alert alert-info mt-2">Proyección del Ingreso</div>
		<table class="table table-condensed table-bordered" style="width:50%">
			<thead>
				<tr> 
					<th width="10%">#</th>
					<th width="30%">Concepto</th>
					<th width="20%" class="right">Importe</th>
					<th width="40%">&nbsp;</th>
				</tr>		
			</thead>	
			<tbody>
				<tr> 
					<td>1</td>
					<td>Ingresos propios</td>
					<td class="right"><input id="Propios" name="Propios" value="<%=matIngreso.getPropios()%>" size="7"></td>
					<th>&nbsp;</th>
				</tr>
				<tr> 
					<td>2</td>
					<td>Servicio Becario</td>
					<td class="right"><input id="Becas" name="Becas" value="<%=matIngreso.getBecario()%>" size="7"></td>
					<th>&nbsp;</th>		
				</tr>
				<tr> 
					<td>3</td>
					<td>Colportaje</td>
					<td class="right"><input id="Colportaje" name="Colportaje" value="<%=matIngreso.getColportaje()%>" size="7"></td>
					<th>&nbsp;</th>		
				</tr>
				<tr> 
					<td>4</td>
					<td>Otro</td>
					<td class="right"><input id="Otro" name="Otro" value="<%=matIngreso.getOtro()%>" size="7"></td>
					<th>&nbsp;</th>		
				</tr>	
<%  			            
				float totalIngresos = 0;
				float propios 		= Float.parseFloat(matIngreso.getPropios());
				float becas 		= Float.parseFloat(matIngreso.getBecario());
				float colportaje 	= Float.parseFloat(matIngreso.getColportaje());
				float otro 			= Float.parseFloat(matIngreso.getOtro());
				
				totalIngresos = propios+becas+colportaje+otro;
%>
				<tr>		
					<th colspan="2">Total de ingresos proyectados</td>
					<th class="right total"><%=dmf.format(Float.parseFloat(String.valueOf(totalIngresos)))%></th>
					<th><button class="btn btn-primary" type="submit">Grabar</button></th>		
				</tr>
			</tbody>
		</table>	
	</form>
</div>
<script type="text/javascript">
	window.onload = function CargaCreditos(){  		
		var creditos = document.getElementById("Creditos").value;
		var finCreditos = document.getElementById("FinCreditos").value;
		var res = (creditos*finCreditos);
		var tmp = parseInt(res);
		res = res.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')+".00";

		const resultado = document.querySelector('.resultado');
	 	resultado.textContent = res;

	 	var matricula = parseInt(document.getElementById("CostoMatricula").value);
		var internado = parseInt(document.getElementById("CostoInternado").value);
	 	var alimentos = parseInt(document.getElementById("CostoAlimentos").value);

	 	var resTotal = tmp+matricula+internado+alimentos;
	 	resTotal = resTotal.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')+".00";
	 	
	 	const total = document.querySelector('.total');
	 	total.textContent = resTotal;

	 	const salTotal = document.querySelector('.salTotal');
	 	var salTot = document.getElementById("SaldoTotal").value;

		if(salTot.includes("Db")){
			var tmpSaldoTotal = salTot.replace(' Db', '');
			tmpSaldoTotal = tmpSaldoTotal.replace(',', '');
			var saldo = parseFloat(tmpSaldoTotal);
			var res = tmp+matricula+internado+alimentos;
			var totalPeriodo = res+saldo;
			totalPeriodo = totalPeriodo.toFixed(2);
			totalPeriodo = totalPeriodo.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			salTotal.textContent = totalPeriodo+" Db";
	    }else if(salTot.includes("Cr")){
			var tmpSaldoTotal = salTot.replace(' Cr', '');
			tmpSaldoTotal = tmpSaldoTotal.replace(',', '');
			var saldo = parseFloat(tmpSaldoTotal);
			var res = tmp+matricula+internado+alimentos;
			var totalPeriodo = res-saldo;
			totalPeriodo = totalPeriodo.toFixed(2);
			
			if(totalPeriodo >= 0){
				totalPeriodo = totalPeriodo.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+" Db";
			}else{
				totalPeriodo = Math.abs(totalPeriodo);
				totalPeriodo = totalPeriodo.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")+" Cr";
			}
			
			salTotal.textContent = totalPeriodo;
		}
	}	
	
	function ActualizarPlan(){  		
  		document.frmPlanes.submit();
	}	

	function ActualizarCarga(matricula, eventoId, planId){  		
		var cargaId = document.getElementById("CargaId").value;		
		document.location.href="alumno?Matricula="+matricula+"&EventoId="+eventoId+"&CargaId="+cargaId+"&PlanId="+planId;
	}	
	
</script>
