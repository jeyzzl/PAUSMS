<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.ssoc.spring.SsocDocAlum"%>
<%@ page import= "aca.ssoc.spring.SsocAsignacion"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<%
	
	String colorPortal 			= (String)session.getAttribute("colorPortal");
	String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");
	
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");	
	AlumPlan alumPlan 			= (AlumPlan) request.getAttribute("alumPlan"); 	
	String promCreditos 		= (String) request.getAttribute("promCreditos");
	String fechaInicio	 		= (String) request.getAttribute("fechaInicio");
	
	ArrayList mostrados			= new ArrayList();
	
	List<String> lisPlanes							= (List<String>) request.getAttribute("lisPlanes");	
	List<SsocDocAlum> lisDocumentos					= (List<SsocDocAlum>) request.getAttribute("lisDocumentos");
	List<String> lisFaltantes						= (List<String>) request.getAttribute("lisFaltantes");
	HashMap<String, MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaDocumentos			= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
	HashMap<String, SsocAsignacion> mapaAsignaciones= (HashMap<String,SsocAsignacion>) request.getAttribute("mapaAsignaciones");
	
	java.text.SimpleDateFormat sdf 	= new java.text.SimpleDateFormat("dd/MM/yyyy");	
	if (planId.equals("0")) planId = alumPlan.getPlanId();
	if(colorPortal==null)colorPortal="";	
%>

<head></head>
<body onload='muestraPagina();'>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>
<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<script>
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
	function recarga(){
		document.frmPlan.submit();
	}
</script>
<!----------------------- DOCUMENTOS -------------------------------->
<div class="container-fluid">
	<h3> <td colspan="10" valign="top" style="text-align:center;">Social Services Progress Status<small class="text-muted fs-6">( <%=matricula%> -  <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%>)</small></td></h3>	
	<div class="alert alert-info">
		<form name="frmPlan" action="servicio" method="post" class="form-inline mb-0">
		<spring:message code="aca.Plan"/>:&nbsp;
	   	<select name="PlanId" onchange='javascript:recarga()' class="custom-select">
	<%		for(int i=0;i<lisPlanes.size();i++){
				String nombrePlan = "-";
				if (mapaPlanes.containsKey(lisPlanes.get(i))){
					nombrePlan = mapaPlanes.get(lisPlanes.get(i)).getNombrePlan();
				}
	%>
				<option value='<%=(String)lisPlanes.get(i)%>' <%if(planId.equals((String)lisPlanes.get(i)))out.print("Selected");%> >[<%=lisPlanes.get(i)%>]-<%=nombrePlan%></option>
	<%	}	%>
		</select>
		&nbsp;&nbsp;
		<spring:message code="portal.alumno.servicio.Avance"/>: <%= promCreditos %> % &nbsp; Start Date : <%= fechaInicio %>
		</form>			  	
	</div>	
	<div class="alert alert-success"> 
		<spring:message code="portal.alumno.servicio.EsMuyImportanteQueConozcasEl"/>: &nbsp;
		<a class="btn btn-outline-dark btn-sm" href="ReglamentoSerSoc.pdf" target="_blank"><b><spring:message code="portal.alumno.servicio.Reglamento"/></b></a>
	    &nbsp;&nbsp;and the: &nbsp;
	    <a class="btn btn-outline-dark btn-sm" href="requisitos" target="_blank"><b><spring:message code="portal.alumno.servicio.Requisitos"/></b></a>
	    &nbsp;&nbsp;<spring:message code="portal.alumno.servicio.DescargaImprime"/>: &nbsp;    
	    <a class="btn btn-outline-dark btn-sm" href="solicitud_ss.pdf" target="_blank"><b><spring:message code="portal.alumno.servicio.Solicitud"/></b></a> &nbsp; &nbsp; 
	    <a class="btn btn-outline-dark btn-sm" href="informe_ss.pdf" target="_blank"><b><spring:message code="portal.alumno.servicio.Informe"/></b></a>
	</div>
	<table class="table table-condensed" style="width:100%"  >	
		<tr>
			<th width="2%" align='center'><font size="3"><spring:message code="aca.Numero"/></font></th>
			<th width="37%" align='center'><font size="3"><spring:message code="aca.Documento"/></font></th>
			<th width="7%" align='center'><font size="3"><spring:message code="aca.Fecha"/></font></th>
			<th width="4%" align='center'><font size="3"><spring:message code="aca.Horas"/></font></th>
			<th width="38%" align='center'><font size="3"><spring:message code="aca.Comentario"/></font></th>
		</tr>
<%
	int horas=0;
	int row=0;
	for(SsocDocAlum doc: lisDocumentos){
		row++;
		if(doc.getEntregado().equals("S")){
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey( String.valueOf(doc.getDocumentoId()) )){
				nombreDocumento = mapaDocumentos.get(String.valueOf(doc.getDocumentoId()));
			}
%>	
		<tr valign='top'>
			<td><font size="3"><%=row%>.</font></td>
			<td><font size="3"><%=nombreDocumento%></font></td>
			<td align='center'><font size="3"><%=doc.getFecha()%></font></td>
			<td align='center'><font size="3"><%if(doc.getNumHoras()==0)out.print("");else{ out.print(doc.getNumHoras());horas+=doc.getNumHoras();}%></font></td>
			<td><font size="3"><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></font></td>
		</tr>
<%			Integer docu = new Integer(doc.getAsignacionId());
			if(doc.getAsignacionId()!=0&&!mostrados.contains(docu)){
				SsocAsignacion asignacion = new SsocAsignacion();
				if (mapaAsignaciones.containsKey(doc.getAsignacionId())){
					asignacion = mapaAsignaciones.get(doc.getAsignacionId());
				}				
				mostrados.add(docu);
%>
		<tr>
			<td><td colspan='5'><font size="3"><b>&nbsp;&nbsp;&nbsp;<spring:message code="datosAlumno.portal.ServDependencia"/>: </b><%=asignacion.getDependencia()%></font></td>
		</tr>
		<tr>
			<td><td colspan='5'><font size="3"><b>&nbsp;&nbsp;&nbsp;<spring:message code="catalogos.division.Direccion"/>: </b><%=asignacion.getDireccion()%></font></td>
		</tr>
		<tr>
			<td><td colspan='5'><font size="3"><b>&nbsp;&nbsp;&nbsp;<spring:message code="aca.Telefono"/>: </b><%=asignacion.getTelefono()%></font></td>
		</tr>
		<tr>
			<td><td colspan='5'><font size="3"><b>&nbsp;&nbsp;&nbsp;<spring:message code="datosAlumno.portal.ServFResponsable"/>: </b><%=asignacion.getResponsable()%></font></td>
		</tr>
<%			}
		}
	}
	
	if(lisFaltantes.size()==0){
%>
			<tr>
			<td colspan='5'><font size="3">Without documents...<spring:message code="portal.alumno.servicio.SinDocumentos"/></font></td>
		</tr>	
<%	}	%>
		<tr>
			<td colspan='3' align='right'><font size="3"><b>Total Hours: </b></font></td>
			<td align='center'><font size="3"><%=horas%></font></td>
		</tr>		
<%		
	String faltante = "";	
	if(lisFaltantes.size() > 0){
%>
		<tr>
			<td colspan='6'><font size="3"><br><b><spring:message code="datosAlumno.portal.ServTeFaltan"/>: </b></font></td>
		</tr>	

<%	}	
	for(int i=0;i<lisFaltantes.size();i++){
		faltante = (String)lisFaltantes.get(i);		
		
		String nombreDocumento = "-";
		if (mapaDocumentos.containsKey(faltante)){
			nombreDocumento = mapaDocumentos.get(faltante);
		}
%>
	
		<tr valign='top'>
	  		<td><font size="3">&nbsp;</font></td>
	  		<td colspan="5"><font size="3"><%=nombreDocumento%></font></td>	  
		</tr>
<%	}%>
		<tr>
			<td colspan="6"><font size="3"><b>Missing Hours: </b><%if(horas>500)out.print("0");else out.print(500-horas);%></font></td>
		</tr>	
	</table>
</div>
</body>