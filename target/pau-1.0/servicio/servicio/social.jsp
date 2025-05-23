<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.ssoc.spring.SsocDocAlum"%>
<%@ page import= "aca.ssoc.spring.SsocDocumentos"%>
<%@ page import= "aca.ssoc.spring.SsocAsignacion"%>
<%@ page import= "aca.ssoc.RequisitoVO"%>
<%@ page import= "aca.ssoc.AsignacionVO"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String	planId				= (String) request.getAttribute("planId");
	String	carreraId			= (String) request.getAttribute("carreraId");
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
	AlumPlan alumPlan			= (AlumPlan)request.getAttribute("alumPlan");
	boolean tieneDocumentos		= (boolean) request.getAttribute("tieneDocumentos");
	boolean tienePrerrequisitos	= (boolean) request.getAttribute("cumplePrerrequisitos");
	String porcentaje			= (String) request.getAttribute("porcentaje");
	String fechaServicio		= (String) request.getAttribute("fechaServicio");
	
	int folio					= request.getParameter("folio")==null?0:Integer.parseInt(request.getParameter("folio"));
	int accion					= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));	
	int i						= 0;	
	String cat					= request.getParameter("cat")==null?"":request.getParameter("cat");	
	
	String espera				= request.getParameter("espera")==null?"":request.getParameter("espera");		
	String dependencia			= request.getParameter("dependencia")==null?"":request.getParameter("dependencia");	
	String direccion			= request.getParameter("direccion")==null?"":request.getParameter("direccion");	
	String telefono				= request.getParameter("telefono")==null?"":request.getParameter("telefono");	
	String responsable			= request.getParameter("responsable")==null?"":request.getParameter("responsable");		
	String mat					= "";
	
	List<AlumPlan> lisPlanes 						= (List<AlumPlan>)request.getAttribute("lisPlanes");	
	List<SsocDocAlum> lisDocumentos 				= (List<SsocDocAlum>)request.getAttribute("lisDocumentos");
	List<SsocDocAlum> lisFaltantes					= (List<SsocDocAlum>)request.getAttribute("lisFaltantes");
	List<SsocAsignacion> lisAsignaciones			= (List<SsocAsignacion>)request.getAttribute("lisAsignaciones");
	HashMap<String,SsocDocumentos> mapaDocumentos	= (HashMap<String,SsocDocumentos>)request.getAttribute("mapaDocumentos");	
	HashMap<String,SsocAsignacion> mapaAsignaciones	= (HashMap<String,SsocAsignacion>)request.getAttribute("mapaAsignaciones");
	HashMap<String,String> mapaRegistradas			= (HashMap<String,String>)request.getAttribute("mapaRegistradas");
	HashMap<String,String> mapaEmpleados			= (HashMap<String,String>)request.getAttribute("mapaEmpleados");
	
	ArrayList<Integer> mostrados		= new ArrayList<Integer>();	
	java.text.SimpleDateFormat sdf 		= new java.text.SimpleDateFormat("dd/MM/yyyy");	
	
	String nivelAcceso = "N";
	if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
		nivelAcceso = "A";
	}else if (acceso.getAccesos().contains(carreraId)){
		nivelAcceso = "C";
	}
	//System.out.println("Datos:"+nivelAcceso+":"+carreraId+":"+tienePrerrequisitos);
%>
<script>
	function cambiaPlan(Plan){
		document.location.href='social?PlanId='+Plan;
	}

	function presDocumento(){
		if(window.event.keyCode==13)
			GuardaDocumento();	
	}
	
	function presAsignacion(){
		if(window.event.keyCode==13)
			GuardaAsignacion();	
	}
		
	function borrar(f,id,Plan){
		if(f=="documentos"){
			if (confirm("¿Esta seguro que desea borrar el documento?")){
				document.location.href='social?Accion=3&folio='+id+'&cat='+f+'&PlanId='+Plan;
			}
		}
		if(f=="asignaciones"){
			if (confirm("¿Esta seguro que desea borrar la asignacion?")){
				document.location.href='social?Accion=3&id='+id+'&cat='+f+'&PlanId='+Plan;
			}
		}
	}
	
	function modificar(f,id,p){
		if(f=="documentos"){
			document.location.href='social?Accion=4&PlanId='+p+'&folio='+id+'&cat='+f
		}
		if(f=="asignaciones"){
			document.location.href='social?Accion=4&PlanId='+p+'&folio='+id+'&cat='+f
		}
	}

	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
			sF+=iW?'width='+iW+',':'';
			sF+=iH?'height='+iH+',':'';
			sF+=R?'resizable='+R+',':'';
			sF+=S?'status='+S+',':'';
			sF+=SC?'scrollbars='+SC+',':'';
			sF+=T?'titlebar='+T+',':'';
			sF+=TB?'toolbar='+TB+',':'';
			sF+=TB?'menubar='+TB+',':'';
			sF+=TOP?'top='+TOP+',':'';
			sF+=LEFT?'left='+LEFT+',':'';
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}
	
	function abrir(pag){
		abrirVentana("servicio",800,600,10,10,"no","yes","yes","no","no",pag,false);
	}
	
	function abrirDocs(pag){
		abrirVentana("documentos",450,450,250,450,"no","yes","yes","no","no",pag,false);
	}
/*
	function cambiaDatos(Plan,PlanNew){
		document.location.href='social?cambia=2&PlanId='+Plan+'&PlanNuevo='+PlanNew;
	}
*/
	function BorrarDocumento(planId, folio){		
		if (confirm("¿Estás seguro de borrar el documento?")){
			document.location.href="borrarDocumento?PlanId="+planId+"&Folio="+folio;
		}	
	}
</script>
<body>
<div class="container-fluid">
	<form name="formDocumento" method='post' action="social?cat=documentos&PlanId=<%=planId%>&Accion=2">
	<h2 style="display:inline;" style="line-height: 1px">Servicio social
		<small class="text-muted fs-5">
			( <%=matricula%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )&nbsp;
			[ Avance: <%= porcentaje %> % ]  		 
		</small>
	</h2>	
	<div class="alert alert-info d-flex align-items-center">
		<font size='2'><strong>Plan:&nbsp; </strong></font>
		<select name="PlanId" id="PlanId" class="form-select" onChange="javascript:cambiaPlan(this.options[this.selectedIndex].value);" style="width:200px;">
<%	
	if(lisPlanes.isEmpty()){
		out.print("<option>No tiene planes</option>");
	}else{
		for ( AlumPlan plan : lisPlanes){				
			if(planId.equals(plan.getPlanId()))
				out.print("<option value='"+plan.getPlanId()+"'Selected>"+plan.getPlanId()+"</option>");
			else
				out.print("<option value='"+plan.getPlanId()+"'>"+plan.getPlanId()+"</option>");
		}
	}	
%>	
		</select>
	 	&nbsp;&nbsp;
<% 	if (nivelAcceso.equals("A") || (nivelAcceso.equals("C") && tienePrerrequisitos) ){%>
		<a class="btn btn-primary" href="editarDocumento?PlanId=<%=planId%>"><b><i class="fas fa-plus-circle" ></i> Documento</b></a>
<%	}else{ %>
		*Este alumno no cumple con los prerrequisitos (Solicitud llena y autorizada - Carta de asignación de plaza)
<%	} %>		
<% 	if (nivelAcceso.equals("A")){%>
		&nbsp;<a class="btn btn-primary" href="agregarAsignacion?PlanId=<%=planId%>"><b><i class="fas fa-plus-circle" ></i> Asignaci&oacute;n</b></a>
<%	} %>
		&nbsp;&nbsp;	
		<a href="javascript:abrir('reglamento')" class="btn btn-success"><b><i class="fas fa-search"></i> Reglamento</b></a>
		&nbsp;		
		<a href="javascript:abrir('requisitos')" class="btn btn-success"><b><i class="fas fa-search"></i> Requisitos</b></a>	
	</div>		
	<table class="table table-sm table-bordered" style="margin-top:5px; margin-bottom:0;">
	<thead class="table-info">
		<tr>
			<th width="3%" class='text-center'><spring:message code="aca.Operacion"/></th>
			<th width="3%" class='text-center'><spring:message code="aca.Numero"/></th>
			<th width="10%" class='text-center'><spring:message code="aca.Documento"/></th>
			<th width="6%" class='text-center'>Asignaci&oacute;n</th>
			<th width="10%" class='text-center'><spring:message code="aca.Fecha"/></th>
			<th width="10%" class='text-center'>Horas</th>
			<th width="20%" class='text-center'><spring:message code="aca.Comentario"/></th>
			<th width="10%" class='text-center'>Usuario</th>
		</tr>
	</thead>
	<tbody>	
<%	
	int horas = 0;		
	for(SsocDocAlum documento : lisDocumentos){
		String documentoNombre 	= "-x";
		String documentoAcceso 	= "A";
		if (mapaDocumentos.containsKey( String.valueOf(documento.getDocumentoId()) )){				
			documentoNombre 	= mapaDocumentos.get( String.valueOf(documento.getDocumentoId())).getDocumentoNombre();
			documentoAcceso		= mapaDocumentos.get( String.valueOf(documento.getDocumentoId())).getAcceso();
		}
		String nombreEmpleado = "-";
		if(mapaEmpleados.containsKey(documento.getUsuario())){
			nombreEmpleado = mapaEmpleados.get(documento.getUsuario());
		}
%>		
		<tr valign='top'>
				<td align='center'>
<% 			if ( nivelAcceso.equals("A") || nivelAcceso.equals(documentoAcceso) ){ %>
				  <a href="editarDocumento?PlanId=<%=planId%>&Folio=<%=documento.getFolio()%>"><img onclick="" class="button" alt='Modificar'  src='../../imagenes/editar.gif'></img></a>&nbsp;
				  <a href="javascript:BorrarDocumento('<%=planId%>','<%=documento.getFolio()%>');"><img class="button" alt='Eliminar'  src='../../imagenes/no.png'></img></a>
<% 			}%>
				</td>
				<td align='center'><%=documento.getDocumentoId()%></td>
				<td><%if(documento.getEntregado().equals("N"))out.print("<font color=red>");%> <%=documentoNombre%></td>
				<td align='center'> <span class="badge bg-dark"><%=documento.getAsignacionId()%> </span></td>
				<td align='center'><%=documento.getFecha()%></td>
				<td align='center'><%if(documento.getNumHoras()==0)out.print("");else{out.print(documento.getNumHoras());horas+=documento.getNumHoras();}%></td>
				<td><%if(documento.getComentario()==null)out.print("&nbsp;");else out.print(documento.getComentario());%></td>
				<td align='center'><%=nombreEmpleado%></td>
			</tr>
<%
		Integer docu = new Integer(documento.getAsignacionId());
		if(documento.getAsignacionId()!=0 && mostrados.contains(docu)){
			SsocAsignacion asignacion = new SsocAsignacion();
			if (mapaAsignaciones.containsKey(documento.getAsignacionId())){
				asignacion = mapaAsignaciones.get(documento.getAsignacionId());
			}
			mostrados.add(docu);
%>
		<tr>
			<td><td><td colspan='6'><b>Dependencia: </b><%=asignacion.getDependencia()%></td>
		</tr>
		<tr>
			<td><td><td colspan='6'><b>Dirección: </b><%=asignacion.getDireccion()%></td>
		</tr>
		<tr>
			<td><td><td colspan='6'><b><spring:message code="aca.Tel"/>éfono: </b><%=asignacion.getTelefono()%></td>
		</tr>
		<tr>
			<td><td><td colspan='6'><b>Responsable: </b><%=asignacion.getResponsable()%></td>
		</tr>
<%			}
%>
<%		}
		if(lisDocumentos.size()==0){
%>
		<tr>
			<td colspan='8'>Sin documentos...</td>
		</tr>	
<%		}%>
		<tr>
			<td colspan='5' class='text-end'><b>Horas Totales: </b></td>
			<td class='text-center'><%=horas%></td>
			<td class='text-center'>&nbsp;</td>
			<td class='text-center'>&nbsp;</td>
		</tr>			
<%				
		if(lisFaltantes.size()> 0){
%>
		<tr>
			<td colspan='8'><br><b>Te faltan: </b></td>
		</tr>	
<%		}
		for(SsocDocAlum faltante : lisFaltantes){			
			String documentoNombre = "-";
			if (mapaDocumentos.containsKey(faltante.getDocumentoId())){
				documentoNombre = mapaDocumentos.get(faltante.getDocumentoId()).getDocumentoNombre();
			}
%>		
		<tr valign='top'>
			<td>&nbsp;</td>
		  	<td colspan="8"><%=documentoNombre%></td>
		</tr>
<%		} %>
		<tr>
			<td colspan="5"><b>Horas Faltantes: </b><%if(horas>500)out.print("0");else out.print(500-horas);%></td>
			<td><%if(horas>500)out.print("0");else out.print(500-horas);%></td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</tbody>			
	</table>
<!----------------------- ASIGNACIONES -------------------------------->
	<h3 style="display:inline;" style="line-height: 1px">Asignaciones</h3>	
	<table class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th width="6%" align='center'><spring:message code="aca.Operacion"/></th>
			<th width="3%" align='center'><spring:message code="aca.Numero"/></th>
			<th width="20%" align='center'>Dependencia</th>
			<th width="20%" align='center'><spring:message code='aca.Direccion'/></th>
			<th width="15%" align='center'>Teléfono</th>
			<th width="20%" align='center'>Responsable</th>
			<th width="20%" align='center'>Sector</th>
		</tr>
	</thead>
	<tbody>	
<%	
	String sector = "";
	for(SsocAsignacion asignacion : lisAsignaciones){
		if(asignacion.getSector().equals("1")){
			sector = "Privada";
		}else if(asignacion.getSector().equals("2")){
			sector = "Publica";
		}else if(asignacion.getSector().equals("3")){
			sector = "Social";
		}else if(asignacion.getSector().equals("4")){
			sector = "Educativo";
		}
%>	
		<tr valign='top'>
			<td align='center'>
<% if (nivelAcceso.equals("A")){ %>			
					<a href="agregarAsignacion?PlanId=<%=planId%>&asId=<%=asignacion.getAsignacionId()%>" class="fas fa-edit"></a>
<%  }%>
<%		if (mapaRegistradas.containsKey(asignacion.getAsignacionId())==false ){%>
			  		<a class="fas fa-trash-alt" href="borrarAsignacion?PlanId=<%=planId%>&asId=<%=asignacion.getAsignacionId()%>"></a>
<%		}%>			  	
			</td>
			<td align='center'><span class="badge bg-dark"><%=asignacion.getAsignacionId()%></span></td>
			<td><%=asignacion.getDependencia()%></td>
			<td><%=asignacion.getDireccion()%></td>
			<td><%=asignacion.getTelefono()%></td>
			<td><%=asignacion.getResponsable()%></td>
			<td><%=sector%></td>
		</tr>
<%	}
	if(lisAsignaciones.size() == 0){
%>
		<tr>
			<td colspan='6'>Sin asignaciones...</td>
		</tr>	
<%	}	
%>
	</tbody>
	</table>
	</form>
</div>
</body>
