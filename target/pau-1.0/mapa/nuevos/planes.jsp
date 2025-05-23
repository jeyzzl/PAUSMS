<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.plan.MapaNuevoPlan"%>
<%@page import="aca.catalogo.CatCarrera"%>
<%@page import="aca.plan.MapaNuevoCurso"%>

<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoPlanU" class="aca.plan.MapaNuevoPlanUtil" scope="page"/>

<script type="text/javascript">
	function yearPlanes(){
		var year = document.getElementById("yearPlanes").value;
		document.location.href = "planes?yearPlanes="+year;
	}
</script>

<%
	ArrayList<MapaNuevoPlan> listPlanes	= null;
	String respuesta		= "";
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String facultadId		= "";
	
	String yearSelect		= request.getParameter("yearPlanes")==null?"t":request.getParameter("yearPlanes");
	String year 			= "0";
	
	if(yearSelect.equals("u")){
		year = "2017";
	}
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	boolean esAdmin			= Boolean.parseBoolean(session.getAttribute("admin")+"");
	boolean esSuper			= aca.acceso.AccesoUtil.esSupervisor(conEnoc, codigoPersonal);
	boolean tabla			= false;
	
	switch(accion){
		case 3:{// Borrar
			mapaNuevoPlan.mapeaRegId(conEnoc, request.getParameter("planId"), request.getParameter("versionId"));
			if(mapaNuevoPlanU.deleteReg(conEnoc, request.getParameter("planId"), request.getParameter("versionId"))){
				respuesta = "<font size=2 color=blue><b>Se borr&oacute; correctamente el plan</b></font>";
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar.<br>Verifique que el plan no tenga materias e intentelo de nuevo</b></font>";
			}
		}break;
		case 5:{
			respuesta = "<font size=2 color=blue><b>Se modific&oacute; correctamente el plan</b></font>";
		}break;
		case 6:{
			respuesta = "<font size=2 color=blue><b>Se guard&oacute; correctamente el plan</b></font>";
		}break;
	}
	
	if(esAdmin || esSuper || aca.acceso.AccesoUtil.esCotejador(conEnoc, codigoPersonal)){
		if(year.equals("0")){
			listPlanes	= mapaNuevoPlanU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
		}else{
			listPlanes	= mapaNuevoPlanU.getListPorYear(conEnoc, year, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
		}
	}else{
		if(year.equals("0")){
			listPlanes	= mapaNuevoPlanU.getListMaestro(conEnoc, codigoPersonal, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE");
		}else{
			listPlanes	= mapaNuevoPlanU.getListMaestroPorYear(conEnoc, codigoPersonal, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE", year);
		}		
	}

	
	
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
				document.location.href = 'materias?planId='+planId+'&versionId='+versionId;
			}
	
			function borrar(planId, versionId){
				if(confirm("Esta seguro que desea borrar el plan "+planId)){
					document.location.href = 'planes?Accion=3&planId='+planId+'&versionId='+versionId;
				}
			}
		</script>
	</head>
<body>
<%
	if(!respuesta.equals("")){
%>
	<table>
		<tr>
			<td><%=respuesta %></td>
		</tr>
	</table>
<%
	}
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Planes"/></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="editaPlan"><spring:message code='aca.Nuevo'/></a> &nbsp; <a class="btn btn-info" href="estadistica"><spring:message code='aca.Estadistica'/></a>&nbsp;&nbsp;
		<select id="yearPlanes" onchange="yearPlanes()" class="form-select" style="width:120px;">
<%if(yearSelect.equals("t")){%>
			<option selected value="t" ><spring:message code='aca.Todos'/></option>
			<option value="u" >2017</option>
<%}else {%>
			<option value="t" ><spring:message code='aca.Todos'/></option>
			<option selected value="u" >2017</option>
<%}%>
		</select>
	</div>	
	<%	if(recomendar){
		
	
	%>
	<p align="center"><font color="blue" size="3"><b><spring:message code='aca.NavegadorError'/></b></font></p>
<%}
	if(!recomendar){
		if(esAdmin){
%>
<%
		}
%>
	<table style="width:90%" >
<%
		for(int i = 0; i < listPlanes.size(); i++){
			mapaNuevoPlan = (MapaNuevoPlan) listPlanes.get(i);
			String facultadTmp = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, mapaNuevoPlan.getCarreraId());
			if(!facultadId.equals(facultadTmp)){
				facultadId = facultadTmp;
				tabla	   = false;		
%>
	</table>
	<div><h3><b><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId)%></b></h3></div>
		<% tabla = true;
		if(tabla == true){
%>
	<table class="table table-bordered table-striped table-sm">
	<thead class="table-info">
<%		
			
		} %>
		
		<tr>
<%      
				if(esAdmin){
%>
			<th width="40px"><spring:message code="aca.Op"/></th>
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
<%
				}
%>
		</tr>
	</thead>
<%			} %>
		<tr class="button">
<%
			if(esAdmin){
%>
			<td style="text-align: center">
				<a href="editaPlan?planId=<%=mapaNuevoPlan.getPlanId() %>&versionId=<%=mapaNuevoPlan.getVersionId() %>" class="fas fa-edit" title="Modificar"></a>
				&nbsp;
				<a href="javascript:borrar('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');" class="fas fa-trash-alt" title="Eliminar"></a>
			</td>
		<%	} %>
			<td width="20px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=i+1 %></td>
			<td width="55px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getPlanId() %></td>
			<td onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getNombre() %></td>
			<td onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, mapaNuevoPlan.getCarreraId()) %></td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getVersionNombre() %></td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=mapaNuevoPlan.getEstado().equals("A")?"Activo":mapaNuevoPlan.getEstado().equals("T")?"Terminado":"Inactivo" %></td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "4") %> %</td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "3") %> %</td>
			<td width="72px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "2") %> %</td>
			<td width="50px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');"><%=aca.plan.MapaNuevoCursoUtil.porcentajeEnEstado(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId(), "1") %> %</td>
			<td width="80px" align="center" onclick="gotoMaterias('<%=mapaNuevoPlan.getPlanId() %>', '<%=mapaNuevoPlan.getVersionId() %>');" class="ayuda mensaje Cantidad de materias que tienen por lo menos una unidad"><%=aca.plan.MapaNuevoCursoUtil.getMateriasConUnidades(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId()) %> <spring:message code="aca.De"/> <%=aca.plan.MapaNuevoCursoUtil.getMateriasTotales(conEnoc, mapaNuevoPlan.getPlanId(), mapaNuevoPlan.getVersionId()) %></td>
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

<%@ include file= "../../cierra_enoc.jsp" %>