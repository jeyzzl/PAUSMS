<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.plan.MapaPlan"%>
<jsp:useBean id="planUtil"  class="aca.plan.PlanUtil" scope="page"/>
<jsp:useBean id="CarreraU"  class="aca.catalogo.CarreraUtil" scope="page"/>
<jsp:useBean id="EventoU"  class="aca.conva.ConvEventoUtil" scope="page"/>

<%	
	String tipo 			= request.getParameter("Tipo")==null?"'I','E','-'":request.getParameter("Tipo");
	String facultad 		= request.getParameter("facultad");
	
	String periodoId 		= (String)request.getAttribute("periodoId");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");
	int year1				= Integer.parseInt(periodoId.substring(0,2))-1;
	int year2				= Integer.parseInt(periodoId.substring(2,4))-1;
	String periodoOld		= String.valueOf(year1)+String.valueOf(year2);
	
	String year 			= aca.util.Fecha.getHoy().substring(6, 10);
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
	
	// Lista de carreras
	List<CatCarrera> lisCarreras		= (List<CatCarrera>)request.getAttribute("lisCarreras");
	
	// Mapa de convalidaciones por carrera y estado
	HashMap<String,String> mapEstado 	= (HashMap<String,String>)request.getAttribute("mapEstado");	
	HashMap<String,String> mapMaestros	= (HashMap<String,String>)request.getAttribute("mapMaestros");
%>
<div class="container-fluid">
<h2>Programas de <%=facultadNombre%></h2>
<div class="alert alert-info">
	<a href="facultad?Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
</div>
<table class="table table-sm table-bordered">  
	<thead class="table-info">
  <tr>
    <th width="5%" align="center"><spring:message code="aca.Clv"/></th>
    <th width="25%" align="center"><spring:message code="aca.Nombre"/></th>
    <th width="25%" align="center"><spring:message code="aca.Coordinador"/></th>
    <th width="5%" align="center">S</th>
    <th width="5%" align="center">P</th>
    <th width="5%" align="center">C</th>
    <th width="5%" align="center">G</th>
    <th width="5%" align="center">F</th>
    <th width="5%" align="center">D</th>
    <th width="5%" align="center">A</th>
    <th width="5%" align="center">T</th>
    <th width="5%" align="center">R</th>
    <th width="5%" align="center">X</th>    
  </tr>
  </thead>
<%
	int cont = 0;
	for (CatCarrera carrera : lisCarreras){
		cont++;		
		
		String numSol = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"S") ){
			numSol = mapEstado.get(carrera.getCarreraId()+"S");
		}
		
		String numPre = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"P") ){
			numPre = mapEstado.get(carrera.getCarreraId()+"P");
		}
		
		String numCon = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"C") ){
			numCon = mapEstado.get(carrera.getCarreraId()+"C");
		}
		
		String numGra = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"G") ){
			numGra = mapEstado.get(carrera.getCarreraId()+"G");
		}
		
		String numSinDoc = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"F") ){
			numSinDoc = mapEstado.get(carrera.getCarreraId()+"F");
		}
		
		String numSinPag = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"D") ){
			numSinPag = mapEstado.get(carrera.getCarreraId()+"D");
		}
		
		String numTra = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"A") ){
			numTra = mapEstado.get(carrera.getCarreraId()+"A");
		}
		
		String numTer = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"T") ){
			numTer = mapEstado.get(carrera.getCarreraId()+"T");
		}
		
		String numReg = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"R") ){
			numReg = mapEstado.get(carrera.getCarreraId()+"R");
		}
		
		String numCan = "0";
		if (mapEstado.containsKey(carrera.getCarreraId()+"X") ){
			numCan = mapEstado.get(carrera.getCarreraId()+"X");
		}
		String maestro = "-";
		if (mapMaestros.containsKey(carrera.getCodigoPersonal())){
			maestro = mapMaestros.get(carrera.getCodigoPersonal());
		}
%>  
  <tr class="tr2" >
    <td align="center" height="30px"><strong><%=carrera.getCarreraId()%></strong></td>
    <td><strong>  
    	<a href="catalogo?carreraId=<%=carrera.getCarreraId() %>&facultad=<%=facultad %>&Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>">
    	<font size="2">
    <%
	    if (carrera.getNombreCarrera().length()>70){
			out.print(carrera.getNombreCarrera().substring(0,69));
		}else{
			out.print(carrera.getNombreCarrera());
		}
    %>   
		</font></a>		
	</strong>
	</td>	
    <td><strong><%=maestro%></strong></td>    
    <td style="text-align:"><%=numSol %></td>
    <td align="left"><%=numPre%></td>
    <td align="left"><%=numCon%></td>
    <td align="left"><%=numSinDoc%></td>
    <td align="left"><%=numGra%></td>    
    <td align="left"><%=numSinPag%></td>
    <td align="left"><%=numTra%></td>
    <td align="left"><%=numTer%></td>
    <td align="left"><%=numReg%></td>
    <td align="left"><%=numCan%></td>
  </tr>  
<%
	}
%>
</table>
</div>