<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getformato = new DecimalFormat("##0.00;(##0.00)");

	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");
	String cargaId 				= (String)request.getAttribute("cargaId");
	String carreraId 			= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
	
	List<String> lisMeses 		= (List<String>) request.getAttribute("lisMeses");	
	List<String> lisMaterias 	= (List<String>) request.getAttribute("lisMaterias");
	List<String> lisEstrategias	= (List<String>) request.getAttribute("lisEstrategias");
	
	String facultadId 			= "x";
	String carreraTemp 			= "x";
	String cursoCargaId 		= "x";

	int nMes, numMat, numEst, cien, sinCien;
	int noSumaCien=0, sumaMes=0;
	double sumaEst=0;

	String facultad 			= "";
	String carrera 				= "";	
	String valor 				= "";
	String mes 					= "";

	int i = 0, mi = 0, mf = 0, j, k;
	String sResultado = "";
	
	List<CatCarrera> lisCarreras 					= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<Carga> lisCargas 							= (List<Carga>) request.getAttribute("lisCargas");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaMatPorFacultades		= (HashMap<String,String>)request.getAttribute("mapaMatPorFacultades");
	HashMap<String,String> mapaMatPorCarreras		= (HashMap<String,String>)request.getAttribute("mapaMatPorCarreras");
	HashMap<String,String> mapaEstPorFacultades		= (HashMap<String,String>)request.getAttribute("mapaEstPorFacultades");
	HashMap<String,String> mapaEstPorCarreras		= (HashMap<String,String>)request.getAttribute("mapaEstPorCarreras");
	HashMap<String,String> mapaEstPorMesyFacultades	= (HashMap<String,String>)request.getAttribute("mapaEstPorMesyFacultades");
	HashMap<String,String> mapaEstPorMesyCarreras	= (HashMap<String,String>)request.getAttribute("mapaEstPorMesyCarreras");
	HashMap<String,String> mapaSumaPorFacultades	= (HashMap<String,String>)request.getAttribute("mapaSumaPorFacultades");
	HashMap<String,String> mapaSumaPorCarreras		= (HashMap<String,String>)request.getAttribute("mapaSumaPorCarreras");
%>
<div class="container-fluid">
	<h1><spring:message code="aca.AnalisisEstrategias"/></h1>
	<div class="alert alert-info d-flex align-items-center">	
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<strong>Carga: [ <%=cargaId%> ] </strong>&nbsp;
		<select name="cargaId" class="form-select" onchange="document.forma.submit()" style="width: 350px;">
<%	for (Carga carga : lisCargas) { %>
			<option value='<%=carga.getCargaId()%>' <%if (cargaId.equals(carga.getCargaId())) out.print("selected");%>>
				<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
			</option>
<%	} %>
		</select>
	</div>
	<form name="forma" action="estrategias" method='post'>
	<table class="table table-sm">
		<tr>
			<th width="30%" style="font-size: 20px" align="center"><b><spring:message code="aca.Facultad"/>/<spring:message code="aca.Programa"/></b></th>
			<th class="right" width="7%" title="N&uacute;mero de Materias"><b><spring:message code="aca.Mat"/></b></th>
			<th class="right" width="7%" title="N&uacute;mero de Estrategias"><b><spring:message code="aca.Est"/></b></th>
			<th class="right" width="7%" title="Promedio de Estrategias"><b><spring:message code="aca.Prom"/></b></th>
			<%
				for (String mesEnc: lisMeses) {
					%>
					<th class="right" width="4%" title="Mes <%=mesEnc%>"><b>M<%=mesEnc%></b></th>
					<%
				}
			%>		
			<th class="right" width="7%" title="Materias que no alcanzan el 100% de la evaluaci&oacute;n"><b>Eval.0-99</b></th>
			<th class="right" width="7%" title="Materias con 100% de evaluaci&oacute;n"><b>Eval.100</b></th>
		</tr>
<!-- 	</table> -->
<%
	for(i=0; i<lisCarreras.size(); i++) {
		CatCarrera car = (CatCarrera) lisCarreras.get(i);
		if ((acceso.getAccesos().indexOf(car.getCarreraId()) != -1) || (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))) {
			if (!facultadId.equals(car.getFacultadId())) {
				facultadId = car.getFacultadId();

				j = 0;
				numMat = 0;
				if (mapaMatPorFacultades.containsKey(facultadId)){
					numMat = Integer.parseInt(mapaMatPorFacultades.get(facultadId));
				}
				
				numEst = 0;
				if (mapaEstPorFacultades.containsKey(facultadId)){
					numEst = Integer.parseInt(mapaEstPorFacultades.get(facultadId));
				}
				
				String facultadNombre = "";
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre = mapaFacultades.get(facultadId).getNombreFacultad();
				}
				
				String sumaCien = "0";
				if (mapaSumaPorFacultades.containsKey(facultadId)){
					sumaCien = mapaSumaPorFacultades.get(facultadId);
				}	
				
				noSumaCien = numMat-Integer.parseInt(sumaCien);
%>
<!-- <table style="margin: 0 auto;  width:90%" class="table table-condensed"> -->
	<tr> 
		<th width="30%" style="font-size: 20px"><b><%=facultadNombre%></b></th>
		<th width="7%" class="right"><%=numMat%></th>
		<th width="7%" class="right"><%=numEst%></th>
		<th width="7%" class="right">
		<%
				if (numEst > 0 && numMat > 0) {
					out.print(getformato.format((double) numEst / (double) numMat).replace(',', '.'));
				} else {
					out.print("-");
				}
		%>
		</th>
		<%
			sumaMes = 0;
			for (String mesFac : lisMeses) {				
				String numPorMes = "0";
				if (mapaEstPorMesyFacultades.containsKey(facultadId+mesFac)){
					numPorMes = mapaEstPorMesyFacultades.get(facultadId+mesFac);
				}
		%>
				<th width="4%" class="right"><%=numPorMes%></th>
		<%	} %>		
		<th width="7%" class="right"><%=noSumaCien%></th>
		<th width="7%" class="right"><%=sumaCien%></th>
	</tr>
	<%	
			} // Termina if (!facultadId.equals(car.getFacultadId()))	
			if (!carreraTemp.equals(car.getCarreraId())) {
				carreraTemp = car.getCarreraId();

				//Presenta Informacion de la carrera					
				numMat = 0;
				if (mapaMatPorCarreras.containsKey(carreraTemp)){
					numMat = Integer.parseInt(mapaMatPorCarreras.get(carreraTemp));
				}
				
				numEst = 0;
				if (mapaEstPorCarreras.containsKey(carreraTemp)){
					numEst = Integer.parseInt(mapaEstPorCarreras.get(carreraTemp));
				}
				
				String carreraNombre = "";
				if (mapaCarreras.containsKey(carreraTemp)){
					carreraNombre = mapaCarreras.get(carreraTemp).getNombreCarrera();
				}	
				
				String sumaCien = "0";
				if (mapaSumaPorCarreras.containsKey(carreraTemp)){
					sumaCien = mapaSumaPorCarreras.get(carreraTemp);
				}
				
				noSumaCien = numMat-Integer.parseInt(sumaCien);
	%>
	<tr>
		<td width="30%" class="left"><a href="estrategias_fac?carreraId=<%=carreraTemp%>"><b><%=carreraNombre%></b></a></td>
		<td width="7%" class="right"><%=numMat%></td>
		<td width="7%" class="right"><%=numEst%></td>
		<td width="7%" class="right">
		<%
				if (numEst > 0 && numMat > 0) {
					out.print(getformato.format((double) numEst / (double) numMat).replace(',', '.'));
				} else {
					out.print("-");
				}
		%>
		</td>
		<%
				sumaMes = 0;
				for (String mesCarr : lisMeses) {
					nMes = Integer.parseInt(mesCarr);
				
					String numPorMes = "0";
					if (mapaEstPorMesyCarreras.containsKey(carreraTemp+mesCarr)){
						numPorMes = mapaEstPorMesyCarreras.get(carreraTemp+mesCarr);
					}				
		%>
				<td width="4%" class="right"><%=numPorMes%></td>
		<%
				}
		%>		
		<td width="7%" class="right"><%=noSumaCien%></td>
		<td width="7%" class="right"><%=sumaCien%></td>
	</tr>
	<%
			}
		} // Si tiene privilegios de ver la carrera 
	} //fin del for
	%>
</table>
</form>
</div>