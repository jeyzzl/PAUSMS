<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitAlumno"%>
<%@ page import= "aca.tit.spring.TitFirma"%>
<%@ page import= "aca.tit.spring.TitTramite"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<%	 
	String institucion 			= (String) session.getAttribute("valInstitucion");
	String estado 				= (String) session.getAttribute("valEstado");	
	String tramite 				= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
	
	TitTramite titTramite		= (TitTramite)request.getAttribute("titTramite");

	ArrayList<TitAlumno> lisAlumnos		= (ArrayList<TitAlumno>)request.getAttribute("lisAlumnos");
	ArrayList<TitFirma> lisFirmas		= (ArrayList<TitFirma>)request.getAttribute("lisFirmas");
	HashMap<String,String> mapaAlumnos	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,MapaPlan> mapaPlanes	= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");	
%>
<body>
<div class="container-fluid">
	<h2>Alumnos<small class="text-muted fs-6"> ( <%=titTramite.getDescripcion() %> - Tramite <%=titTramite.getTramiteId()%> - Recibo <%=titTramite.getRecibo()%> )</small></h2>
	<div class="alert alert-info d-flex align-items-center">	
		<a class="btn btn-primary" href="tramite?Institucion=<%=institucion%>&Estado=<%=estado%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<input type="text" class="input-medium search-query form-control" placeholder="Buscar" id="buscar" style="width:200px">
<%	if(estado.equals("A") || estado.equals("F")){ %>		
		&nbsp;&nbsp;
		<a href="javascript:document.frmTitulos.submit();" class="btn btn-primary">Grabar Folios</a>
<%	} %>		
	</div>
	<form name="frmTitulos" action="grabarRecibos?Institucion=<%=institucion%>&Tramite=<%=titTramite.getTramiteId()%>&Estado=<%=estado%>" method="post">
	<table id="table" class="table table-sm table-bordered"> 
	<thead class="table-info">
	<tr>
		<th width="2%">#</th>
		<th width="4%">Matrícula</th>
		<th width="15%">Alumno</th>
		<th width="12%">folio/SEP</th>
		<th width="5%">Plan</th>		
		<th width="5%">Carrera</th>		
		<th width="4%">Fecha</th>
		<th width="2%">Edo.</th>				
		<th width="4%">XML</th>
<%
	for (TitFirma firma : lisFirmas){
		String firmaCorta = "XXX";
		if (firma.getCodigoPersonal().equals("9801085")) firmaCorta = "JBL";
		if (firma.getCodigoPersonal().equals("9800812")) firmaCorta = "AEPG";
		if (firma.getCodigoPersonal().equals("9800052")) firmaCorta = "ICO";
		if (firma.getCodigoPersonal().equals("9800146")) firmaCorta = "RIB";
		if (firma.getCodigoPersonal().equals("9801097")) firmaCorta = "JHMW";
%>			
		<th width="2%" class="center"><%=firmaCorta%></th>
<%	} %>
		<th width="2%" class="center">Env.</th>		
		<th width="2%" class="center">Con.</th>		
		<th width="17%">Respuesta/XML-SEP</th>
		<th width="17%">Respuesta</th>
		<th width="7%">Zip</th>
	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (TitAlumno titulo : lisAlumnos){
		row++;	
		String nombreAlumno = "";
		if (mapaAlumnos.containsKey(titulo.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(titulo.getCodigoPersonal());
		}
		String carreraNombre = "-";
		if (mapaPlanes.containsKey(titulo.getPlanId())){
			carreraNombre = mapaPlanes.get(titulo.getPlanId()).getCarreraSe();
		}
		
		String xml = "-";
		if (!titulo.getXml().equals("XML")) xml = "SI"; else xml = "NO";
		
		int numFirmas = 0;
		for (TitFirma firma : lisFirmas){
			if( !titulo.getXml().equals("XML") && !titulo.getXml().contains(firma.getSello()) ){
				numFirmas++;
			}
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=titulo.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td class="d-flex align-items-center">
<% 		if (numFirmas==0){%>
				<%=titulo.getXml().contains(titulo.getFolioControl())?"<i class='fas fa-check-circle' style='color:green'></i>":"<i class='fas fa-times-circle' style='color:red'></i>"%>&nbsp;	
				<input name="FolioControl<%=titulo.getCodigoPersonal()%>" value="<%=titulo.getFolioControl()%>" size="7" class="form-control" style="width:167px;"/>				
<%		}else{ %>
			<input name="FolioControl<%=titulo.getCodigoPersonal()%>" type="hidden" value="<%=titulo.getFolioControl()%>"/>
			<%=titulo.getFolioControl()%>
<%		} %>				
			</td>
			<td><%=carreraNombre%></td>			
			<td><span title="<%=carreraNombre%>"><%=titulo.getPlanId()%></span></td>			
			<td><%=titulo.getFecha()%></td>
			<td><%=titulo.getEstado()%></td>				
			<td>
<%		if(xml.equals("SI")){ %>			
				<a href="../titulacion/bajarXml?Folio=<%=titulo.getFolio()%>"><i class="fas fa-download"></i></a>
<% 		
		}else{
			out.print(xml);
		}
%>			
<%		if((estado.equals("A") || estado.equals("F"))&& !titulo.getRespuesta().equals("ZIP-SEP")){%>
				&nbsp;<a href="javascript:CrearXml('<%=tramite%>','<%=titulo.getFolio()%>')"><i class="far fa-file"></i></a>
<%		} %>		
			</td>
<%
		for (TitFirma firma : lisFirmas){		
			if( !titulo.getXml().equals("XML") && !titulo.getXml().contains(firma.getSello()) ){
%>			
			<td width="3%" class="text-center"><span class="badge bg-success rounded-pill">SI</span></td>
<%			}else{ %>
			<td width="3%" class="text-center"><span class="badge bg-warning rounded-pill">NO</span></td>
<%			}
		}
%>
			<td class="center">
<%		if (numFirmas == lisFirmas.size()){	
			if (institucion.equals("UM")){%>		
				<a href="enviarXML?Folio=<%=titulo.getFolio()%>&Institucion=<%=institucion%>&Tramite=<%=tramite%>&Estado=<%=estado%>">
<%			}else{%>			
				<a href="enviarCovopromXML?Folio=<%=titulo.getFolio()%>&Institucion=<%=institucion%>&Tramite=<%=tramite%>&Estado=<%=estado%>">
<%			}%>			
				<i class="fas fa-envelope"></i>								
				</a>
<%		}%>			
			</td>			
			<td class="center">
<%		if (!titulo.getRespuesta().equals("X")){%>
<%			if (institucion.equals("UM")){%>				
				<a href="descargarXML?Folio=<%=titulo.getFolio()%>&Institucion=<%=institucion%>&Tramite=<%=tramite%>&Estado=<%=estado%>">
<% 			}else{%>
				<a href="descargarCovopromXML?Folio=<%=titulo.getFolio()%>&Institucion=<%=institucion%>&Tramite=<%=tramite%>&Estado=<%=estado%>">
<% 			}%>							
				<i class="fas fa-search" ></i>	
				</a>
<% 		}else{
			out.println("Sin enviar");
		}
%>				
			</td>			
			<td><%=titulo.getRespuesta()%></td>
			<td><%=titulo.getFechaRes() != null ? titulo.getFechaRes() : ""%></td>
			<td>
		<% 	if(titulo.getRespuesta().equals("XML-SEP")||titulo.getRespuesta().equals("ZIP-SEP")){%>
				<a href="../titulacion/bajarXmlSep?Folio=<%=titulo.getFolio()%>"><i class="fas fa-download"></i></a>
		<% 	}%>			
			</td>
		</tr>
<%	} %>	
		</tbody>
	</table>
	</form>
</div>
<script type="text/javascript">
	function CrearXml(tramite, folio) {
		if (confirm("Esta operación reinicia el contenido del XML, ¿Deseas continuar?")){
			document.location.href = "crearXml?Tramite="+tramite+"&Folio="+folio+"&Origen=alumnos";
		}	
	}
</script>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
<script type="text/javascript">
	$('#buscar').search();
</script>
</body>
