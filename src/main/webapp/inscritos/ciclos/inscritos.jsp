<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="aca.carga.Carga"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>
<%@ page import="aca.util.Fecha"%>
<%@ page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>


<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>


<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<html>
	<head>		
		<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
	</head>	
<%	
	DecimalFormat frmDecimal= new DecimalFormat("###,##0.00; -###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");
	String cargas			= (String) request.getAttribute("lisCargas");
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();	
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String periodoId 		= (String) request.getAttribute("periodoId");
	
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	boolean tieneAcceso		= (boolean) request.getAttribute("tieneAcceso");
	int maximoCiclo 		= (int) request.getAttribute("maximoCiclo");
	
	List<CatModalidad> lisModalidad 		=(List<CatModalidad>) request.getAttribute("lisModalidad");
	List<String> listaCarreraPorCiclo 		=(List<String>) request.getAttribute("listaCarreraPorCiclo"); 
	
	HashMap<String, String> mapaInscritosPorCiclo 			= (HashMap<String, String>) request.getAttribute("mapaInscritosPorCiclo");
	HashMap<String, String> mapaFacultadPorCarrera 			= (HashMap<String, String>) request.getAttribute("mapaFacultadPorCarrera");
	HashMap<String, CatFacultad> mapaFacultades 			= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarrera 				= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarrera");
	
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	} 
		
	if(tieneAcceso){
		
		if(cargas.equals("")) cargas="' '";
%>
<div class="container-fluid">
	<h2>Inscritos por Ciclos</h2>
	<form id="forma" name="forma" action="inscritos?Accion=1" method="post">	
		<div class="alert alert-info">
			<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargasActivas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalidades:</b>
<%
			for(CatModalidad mod:lisModalidad){
				
				out.print("["+mod.getNombreModalidad()+"] ");	
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
		</div>
		<div class="alert alert-info">		 
			Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>		
		<body onLoad="document.getElementById('tablaT').innerHTML=document.getElementById('tablaTotales').innerHTML">			
	<%	
		String codigoTemp = "";
		String tmpFacultad = "";
		int [] arrCiclosFacultad = new int[maximoCiclo+1];
		int totalAlumnos = 0;
		int totalTotales = 0;
		int [] arrTotales = new int[maximoCiclo+1];
		for(int i=0; i<listaCarreraPorCiclo.size(); i++){
			String carreraId = listaCarreraPorCiclo.get(i);
			if(acceso.getAccesos().indexOf(carreraId)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
				String facultad = mapaFacultadPorCarrera.get(carreraId);
				if(!tmpFacultad.equals(facultad)){
					tmpFacultad = facultad;
					if(i!=0){ %>
						<td style="text-align:center" colspan="2"><b><i>Totales</i></b></td>
						<%	for(int k=1; k<=maximoCiclo; k++){ %>
					  			<td style="text-align:center"><font size="2"><b><i><%=arrCiclosFacultad[k]==0?"":arrCiclosFacultad[k] %></i></b></font></td>
					  	<%	} %>
					  		<td style="text-align:center"><font size="3"><b><%=totalTotales %></b></font></td>
					  	<%	arrCiclosFacultad = new int[maximoCiclo+1];
							totalTotales = 0;
						} %>
						<table>
							<tr><td class="titulo"><%=mapaFacultades.get(facultad).getNombreFacultad() %></td></tr>
						</table>
						<table class="table table-sm table-bordered">
							<tr class="table-info">
								<th><spring:message code="aca.Clave"/></th>
								<th width="55%">Carreras</th>
						  	<%	for(int k=1; k<=maximoCiclo; k++){ %>
						  			<th class="text-center"><%=k %>°</th>
						  	<%	} %>
								<th class="text-center"><spring:message code="aca.Total"/></th>
						  	</tr>
				<%	} %>
					<tr>
						<td><%=carreraId %></td>
						<td><%=mapaCarrera.get(carreraId).getNombreCarrera() %></td>
					<%	int totalCarrera = 0;
						for(int k=1; k<=maximoCiclo; k++){
							if(mapaInscritosPorCiclo.containsKey(carreraId+"->"+k)){
								int cantidad = Integer.parseInt(mapaInscritosPorCiclo.get(carreraId+"->"+k));
								arrTotales[k]+=cantidad;
								arrCiclosFacultad[k]+=cantidad;
								totalCarrera+=cantidad; %>
								<td class="text-center"><%=cantidad %></td>
						<%	}
							else{ %>
								<td>&nbsp;</td>
						<%	}
						}
						totalTotales+=totalCarrera; %>
						<td style="text-align:center;"><font size="2"><b><%=totalCarrera %></b></font></td>
					</tr>
			<%	}
			} %>
				<td style="text-align:center" colspan="2"><b><i>Totales</i></b></td>
			<%	for(int k=1; k<=maximoCiclo; k++){ %>
		  			<td style="text-align:center"><font size="2"><b><i><%=arrCiclosFacultad[k]==0?"":arrCiclosFacultad[k] %></i></b></font></td>
		  	<%	} %>
		  		<td style="text-align:center"><font size="3"><b><%=totalTotales %></b></font></td>
	  		</table>
	  		<div id="tablaTotales">
			<table class="center table table-sm table-fontsmall" width="100%">
				<tr>
						<th rowspan="2">Semestres</th>
					<%	for(int k=1; k<=maximoCiclo; k++){ %>
				  			<th class="text-center"><%=k %>°</th>
				  	<%	} %>
						<th class="text-center">Total de alumnos</th>
					</tr>
					<tr>
					<%	for(int k=1; k<=maximoCiclo; k++){
							totalAlumnos+=arrTotales[k]; %>
				  			<td class="text-center"><%=arrTotales[k] %></td>
				  	<%	} %>
						<td class="text-center"><b><%=totalAlumnos %></b></td>
				</tr>
			</table>
			</div>
</form>
</body>
</div>	
<%	}
	else{ %>
		<br>
		<b><font color="#000099">No tiene acceso</font></b>
<%	} %>
</div>
</html>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>