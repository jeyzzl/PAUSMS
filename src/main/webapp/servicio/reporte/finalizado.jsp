<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.catalogo.spring.CatPais"%>
<%@ page import="aca.ssoc.spring.SsocInicio"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<% 
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String documentoId		= "23";
	String facultadTemp		= "X";
	String carrera			= "";
	String carreraTemp		= "X";		
	String bgColor			= ""; 		
	int cont 				= 0;
	
	List<SsocInicio> lisAlumnos						= (List<SsocInicio>) request.getAttribute("lisAlumnos");
	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,MapaPlan> mapaPlanes		 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,AlumPersonal> mapaAlumnos		= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaDocumentos		 	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaAlumnosSector	 	= (HashMap<String,String>) request.getAttribute("mapaAlumnosSector");
	HashMap<String,String> mapaSectores		 		= (HashMap<String,String>) request.getAttribute("mapaSectores");
%>
<body>
<div class="container-fluid">
<h2>Alumnos con Servicio Social Terminado</h2>
	<form name="forma" action="finalizado" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>&nbsp;
		Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on">
    		<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
  		</span>
		Fecha Final: <input id="FechaFin" name="FechaFin" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on">
    		<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
  		</span>
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
	</div>
	</form>
	<table style="margin: 0 auto;" class="table table-sm table-bordered">  
<%

for (int i=0; i<lisAlumnos.size(); i++){	
	cont++;
	SsocInicio inicio = (SsocInicio) lisAlumnos.get(i);
	
	String facultadId 	= "0";
	String carreraId 	= "0";
	if (mapaPlanes.containsKey(inicio.getPlanId())){
		carreraId = mapaPlanes.get(inicio.getPlanId()).getCarreraId();
		if (mapaCarreras.containsKey(carreraId)){
			facultadId = mapaCarreras.get(carreraId).getFacultadId();
		}
	}
	String nomFacultad = "-";
	if(mapaFacultades.containsKey(facultadId)){
		nomFacultad	= mapaFacultades.get(facultadId).getNombreFacultad();
	}
	
	String alumnoNombre = "-";
	String genero		= "M";
	if(mapaAlumnos.containsKey(inicio.getCodigoPersonal())){
		alumnoNombre	= mapaAlumnos.get(inicio.getCodigoPersonal()).getNombre()+""+mapaAlumnos.get(inicio.getCodigoPersonal()).getApellidoPaterno()+" "+mapaAlumnos.get(inicio.getCodigoPersonal()).getApellidoMaterno();
		genero 			= mapaAlumnos.get(inicio.getCodigoPersonal()).getSexo();
	}
	
	String fechaDocumento = "-";
	if(mapaDocumentos.containsKey(inicio.getCodigoPersonal()+inicio.getPlanId())){
		fechaDocumento = mapaDocumentos.get(inicio.getCodigoPersonal()+inicio.getPlanId());
	}
	
	String sector 		= "0";
	String sectorNombre = "-";
	if (mapaAlumnosSector.containsKey(inicio.getCodigoPersonal())){
		sector = mapaAlumnosSector.get(inicio.getCodigoPersonal());
		if (mapaSectores.containsKey(sector)){
			sectorNombre = mapaSectores.get(sector);
		}
	}
	
	if (!facultadTemp.equals(facultadId)){
		facultadTemp = facultadId;
		
%>  
	<tr class="alert alert-success"><td colspan="10" style="font-size:17pt;"> <%=nomFacultad%></td></tr>
	<tr> 
    	<th width="2%"><spring:message code="aca.Numero"/></th>
    	<th width="7%"><spring:message code="aca.Matricula"/></th>
    	<th width="25%"><spring:message code="aca.Nombre"/></th>
    	<th width="5%">Sexo</th>
    	<th width="10%">Plan </th>
    	<th width="10%">F. Inicio </th>
    	<th width="10%">F. Termina </th>
    	<th width="7%">%Inicio</th>    	
    	<th width="7%"><spring:message code="aca.Ciclo"/></th>
    	<th width="17%">Sector</th>
	</tr>
<%		 	
	}
	if(i%2 == 0){ bgColor = "bgcolor='#CCCCCC'"; }else{ bgColor = "";}	
%>
	<tr> 
    	<td align="center"><%=cont%></td>
    	<td><%=inicio.getCodigoPersonal() %></td>
    	<td align="left"><%=alumnoNombre%></td> 
    	<td align="left"><%=genero.equals("F")?"Mujer":"Hombre"%></td>
    	<td><%=inicio.getPlanId()%></td>
    	<td><%=inicio.getFecha() %></td>
    	<td><%=fechaDocumento%></td>
    	<td><%=inicio.getPorcentaje()%>%</td>
    	<td><%= inicio.getSemestre() %></td>
    	<td><%= sectorNombre%></td>
	</tr>
<% 
}
%>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
</body>