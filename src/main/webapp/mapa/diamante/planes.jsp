<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.plan.spring.MapaNuevoPlan"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.plan.spring.MapaNuevoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function yearPlanes(){
		var year = document.getElementById("yearPlanes").value;
		document.location.href = "planes?yearPlanes="+year;
	}
</script>

<%
	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	String year				= request.getParameter("yearPlanes")==null?"2017":request.getParameter("yearPlanes");
	
	// Lista de planes
	List<MapaNuevoPlan> listPlanes	= (List<MapaNuevoPlan>)request.getAttribute("listPlanes");
	
	//String respuesta		= "";	
	String facultadId		= "";
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	boolean esAdmin			= (Boolean)request.getAttribute("esAdmin");
	boolean tabla			= false;
	
	HashMap<String,String> mapaCursos 				= (HashMap<String,String>)request.getAttribute("mapaCursos");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatFacultad> mapaFacultad		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultad");
	HashMap<String,String> mapaPorcentajePlan		= (HashMap<String,String>)request.getAttribute("mapaPorcentajePlan");
	HashMap<String,String> mapaPorcentajeEstado		= (HashMap<String,String>)request.getAttribute("mapaPorcentajeEstado");
	HashMap<String,String> mapaTotales				= (HashMap<String,String>)request.getAttribute("mapaTotales");
	HashMap<String,String> mapaUnidades				= (HashMap<String,String>)request.getAttribute("mapaUnidades");
	
//-------------------------------------Navegador------------------------------------
	//System.out.println(request.getHeader("User-Agent"));

	String infoNav = request.getHeader("User-Agent");

	boolean recomendar = false;

	String nav = "";

	if(infoNav.contains("MSIE")){
		recomendar = true;
		nav = "Internet Explorer";
	}

	if(infoNav.contains("Firefox")){
		String [] arrNav = infoNav.split("/");
		String [] arrVer = arrNav[arrNav.length-1].split("\\.");
		int version = Integer.parseInt(arrVer[0]);	
		if(version<5) recomendar = true;
		
		nav = "Mozilla Firefox " + arrNav[arrNav.length-1];
	}

	if(recomendar){%>
		<div id="navegador">
			<table style="width:100%" height="20"   >
				<tr>
					<td align="center" colspan="2">
						Usted está utilizando <b><%=nav%></b>.<br><br>Para un <b>rendimiento óptimo</b> del sistema, necesitas utilizar una de las versiones más nuevas de <b>Google Chrome</b> o <b>Mozilla Firefox</b>.
						Los puedes descargar dando click en el icono de cada navegador que se muestran a continuación:
					</td>
				</tr>
				<tr>
					<td align="center">
					 	<a href="http://www.google.com/chrome?hl=es-419&brand=CHJL&utm_campaign=es-419&utm_source=es-419-mx-ha-BKWS&utm_medium=ha"><img width="50px" src="../../imagenes/chrome.png"></a>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="http://www.mozilla.com/es-ES/firefox/"><img width="50px" src="../../imagenes/firefox.png"></a>
					</td>
				</tr>
			</table>
		</div>	
	<%
	}%>
	<head>	
		<script type="text/javascript">
			function gotoMaterias(planId, versionId){
				location.href = 'materias?planId='+planId+'&versionId='+versionId;
			}
	
			function borrar(planId, versionId){
				if(confirm("Esta seguro que desea borrar el plan "+planId)){
					document.location = 'borrar?PlanId='+planId+'&VersionId='+versionId;
				}
			}
		</script>
	</head>
<body>
<%-- <% --%>
<!--  	if(!respuesta.equals("")){ -->
<%-- %> --%>
<!-- 	<table> -->
<!-- 		<tr> -->
<%-- 			<td><%=respuesta %></td> --%>
<!-- 		</tr> -->
<!-- 	</table> -->
<%-- <% --%>
<!--  	} -->
<%-- %> --%>
<div class="container-fluid">
	<h2><spring:message code="aca.Planes"/></h2>
	<div class="alert alert-info d-flex align-items-center">
<%	if(esAdmin){ %>
		<a class="btn btn-success" href="editaPlan"><spring:message code='aca.Nuevo'/></a> &nbsp;
<%	}%>	 
		<a class="btn btn-info" href="estadistica"><spring:message code="aca.Estadistica"/></a>&nbsp;&nbsp;
		<select id="yearPlanes" onchange="yearPlanes()" class="form-select" style="width:120px;"> 	
			<option value="0" <%=year.equals("0")?" Selected":""%>><spring:message code="aca.Todos"/></option>
			<option value="2017" <%=year.equals("2017")?" Selected":""%>>2017</option>
		</select>
	</div>	
<%	if(recomendar){ %>
	<p align="center"><font color="blue" size="3"><b><spring:message code="aca.NavegadorError"/></b></font></p>
<%	}
	if(!recomendar){
		if(esAdmin){
%>
<%
		}
%>
	<table style="width:100%" >
<%		
		int row = 0;
		for(MapaNuevoPlan mapaNuevoPlan : listPlanes){
			row++;
			
			String numCursos = "0";
			String colorPlan = "<span class='badge bg-warning'>"+mapaNuevoPlan.getPlanId()+"</span>"; 
			if(mapaCursos.containsKey(mapaNuevoPlan.getPlanId())){
				numCursos = mapaCursos.get(mapaNuevoPlan.getPlanId());
				if(!numCursos.equals("0")){
					colorPlan = "<span class='badge bg-dark'>"+mapaNuevoPlan.getPlanId()+"</span>";
				}
			}	
			
			String facultadTmp 		= "-";
			String nombreCarrera 	= "-";
			if(mapaCarreras.containsKey(mapaNuevoPlan.getCarreraId())){
				facultadTmp 	= mapaCarreras.get(mapaNuevoPlan.getCarreraId()).getFacultadId();
				nombreCarrera 	= mapaCarreras.get(mapaNuevoPlan.getCarreraId()).getNombreCarrera();
			}
			
			int porcentajeCuatro 	= 0;
			int porcentajeTres		= 0;
			int porcentajeDos		= 0;
			int porcentajeUno		= 0;
			
			String porcentajeFCuatro 	= "0";
			String porcentajeFTres 		= "0";
			String porcentajeFDos 		= "0";
			String porcentajeFUno 		= "0";
			
			int porcentajePorPlan = 0;
			if(mapaPorcentajePlan.containsKey(mapaNuevoPlan.getPlanId())){
				porcentajePorPlan = Integer.parseInt(mapaPorcentajePlan.get(mapaNuevoPlan.getPlanId())); 
			}
			
			if(mapaPorcentajeEstado.containsKey(mapaNuevoPlan.getPlanId()+"4")){
				porcentajeCuatro 	= Integer.parseInt(mapaPorcentajeEstado.get(mapaNuevoPlan.getPlanId()+"4"));
				porcentajeFCuatro 	= String.valueOf(((float)porcentajeCuatro*100/porcentajePorPlan));
				if(porcentajeFCuatro.indexOf(".") != -1){
					porcentajeFCuatro = porcentajeFCuatro.substring(0, porcentajeFCuatro.indexOf(".")+2);
				}
			}
			
			if(mapaPorcentajeEstado.containsKey(mapaNuevoPlan.getPlanId()+"3")){
				porcentajeTres	= Integer.parseInt(mapaPorcentajeEstado.get(mapaNuevoPlan.getPlanId()+"3"));
				porcentajeFTres	= String.valueOf(((float)porcentajeTres*100/porcentajePorPlan));
				if(porcentajeFTres.indexOf(".") != -1){
					porcentajeFTres = porcentajeFTres.substring(0, porcentajeFTres.indexOf(".")+2);
				}
			}
			
			if(mapaPorcentajeEstado.containsKey(mapaNuevoPlan.getPlanId()+"2")){
				porcentajeDos 	= Integer.parseInt(mapaPorcentajeEstado.get(mapaNuevoPlan.getPlanId()+"2"));
				porcentajeFDos	= String.valueOf(((float)porcentajeDos*100/porcentajePorPlan));
				if(porcentajeFDos.indexOf(".") != -1){
					porcentajeFDos = porcentajeFDos.substring(0, porcentajeFDos.indexOf(".")+2);
				}
			}
			
			if(mapaPorcentajeEstado.containsKey(mapaNuevoPlan.getPlanId()+"1")){
				porcentajeUno 	= Integer.parseInt(mapaPorcentajeEstado.get(mapaNuevoPlan.getPlanId()+"1"));
				porcentajeFUno	= String.valueOf(((float)porcentajeUno*100/porcentajePorPlan));
				if(porcentajeFUno.indexOf(".") != -1){
					porcentajeFUno = porcentajeFUno.substring(0, porcentajeFUno.indexOf(".")+2);
				}
			}
			
			String totales = "-";			
			if(mapaTotales.containsKey(mapaNuevoPlan.getPlanId())){
				totales = mapaTotales.get(mapaNuevoPlan.getPlanId());
			}
			
			String unidades = "-";
			if(mapaUnidades.containsKey(mapaNuevoPlan.getPlanId())){
				unidades = mapaUnidades.get(mapaNuevoPlan.getPlanId());
			}
			
			String nombreFacultad = "-";
			if(!facultadId.equals(facultadTmp)){
				facultadId = facultadTmp;
				tabla	   = false;
			
				if(mapaFacultad.containsKey(facultadId)){
					nombreFacultad = mapaFacultad.get(facultadId).getNombreFacultad();
				}
%>
	</table>
	<div><h3><b><%=nombreFacultad%></b></h3></div>
		<% tabla = true;
		if(tabla == true){
%>
	<table style="width:100%" class="table table-bordered table-striped table-sm">
<%		} %>		
		<tr class="table-info">
<%      
				if(esAdmin){
%>
			<th width="60px"><spring:message code="aca.Op"/></th>
<%
				}
%>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Plan"/> ID</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Carrera"/></th>
			<th><spring:message code="aca.Version"/></th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code="aca.Terminado"/></th>
			<th><spring:message code="aca.Administrador"/></th>
			<th><spring:message code="aca.Coordinador"/></th>
			<th><spring:message code="aca.Maestro"/></th>
			<th class="ayuda mensaje Cantidad de materias que tienen por lo menos una unidad"><spring:message code="aca.Unidades"/></th>
<%
				if(esAdmin){
%>
			<th><spring:message code="aca.Imprimir"/></th>
<%				} %>
		</tr>
<%			} %>
		<tr class="button">
<%			if(esAdmin){ %>
			<td style="text-align: center">
				<a href="editaPlan?planId=<%=mapaNuevoPlan.getPlanId() %>&versionId=<%=mapaNuevoPlan.getVersionId() %>"title="Modificar"><i class="fas fa-edit"></i></a>
				&nbsp;
				<a href="javascript:borrar('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');" title="Eliminar"><i class="fas fa-trash-alt"></i></a>
			</td>
		<%	} %>
			<td width="20px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=row %></td>
			<td width="55px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=colorPlan%></td>
			<td onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getNombre() %></td>
			<td onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=nombreCarrera %></td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getVersionNombre() %></td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getEstado().equals("A")?"Activo":mapaNuevoPlan.getEstado().equals("T")?"Terminado":"Inactivo" %></td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=porcentajeFCuatro %> %</td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=porcentajeFTres %> %</td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=porcentajeFDos %> %</td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=porcentajeFUno %> %</td>
			<td width="80px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');" class="ayuda mensaje Cantidad de materias que tienen por lo menos una unidad"><%=unidades %> <spring:message code="aca.De"/> <%=totales %></td>
<%
			if(esAdmin){
				if(mapaNuevoPlan.getIdioma().equals("E")){ %>
			<td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraPlanPdf?planId=<%=mapaNuevoPlan.getPlanId() %>&versionId=<%=mapaNuevoPlan.getVersionId() %>';" /></td>
<%				}else{   %>
			<td width="30px" align="center"><img class="button" src="../../imagenes/printer.gif" onclick="location.href='muestraPlanPdfI?planId=<%=mapaNuevoPlan.getPlanId() %>&versionId=<%=mapaNuevoPlan.getVersionId() %>';" /></td>
<% 				
				}
			}
%>
		</tr>	
<%		
		}
%>
	</table>
	<br>
	<table style="width:100%">
		<tr><td class="end"></td></tr>
	</table>
<%
	}
%>
</div>
</body>