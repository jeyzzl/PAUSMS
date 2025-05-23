<%@ page import="aca.conva.ConvEventoUtil"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.conva.spring.ConvEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<jsp:useBean id="FacultadU"  class="aca.catalogo.FacultadUtil" scope="page"/>
<jsp:useBean id="EventoU"  class="aca.conva.ConvEventoUtil" scope="page"/>

<script type="text/javascript">
	function Borrar() {
		if (confirm("¿Quieres borrar la convalidación?") == true) {
			document.frmFacultad.Accion.value = "1";
			document.frmFacultad.submit();
		}
	}
</script>
<%	
	String tipo 			= request.getParameter("Tipo")==null?"T":request.getParameter("Tipo");	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String periodoId 		= (String) request.getAttribute("periodoId");
	int year1				= Integer.parseInt(periodoId.substring(0,2))-1;
	int year2				= Integer.parseInt(periodoId.substring(2,4))-1;
	String periodoOld		= String.valueOf(year1)+String.valueOf(year2);
	String msj				= "";
	
	String year 			= aca.util.Fecha.getHoy().substring(6, 10);	
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");	

	// Lista de facultades
	List<CatFacultad> lisFacultades			= (List<CatFacultad>) request.getAttribute("lisFacultades");
	// Mapa de convalidaciones por facultad y estado
	HashMap<String,String> mapEstado 		= (HashMap<String,String>)request.getAttribute("mapEstado");
	HashMap<String,String> mapMaestros		= (HashMap<String,String>)request.getAttribute("mapMaestros");	
%>
<style type="text/css"></style>
<div class="container-fluid">
	<h1><spring:message code="aca.ListaDeFacultades"/></h1>
	<form action="facultad" method="post" name="frmFacultad" target="_self">
		<div class="alert alert-info d-flex align-items-center">
		<input type="hidden" name="Accion">
			<button onclick='javascript:Borrar();' class="btn btn-primary">Borrar convalidacion vacia</button>&nbsp;&nbsp;&nbsp;
			Fecha Ini. <input class="form-control" type="text" data-date-format="dd/mm/yyyy" id="FechaInicio" name="FechaInicio" value="<%=fechaInicio%>" size="9" style="width: 120px"/>&nbsp;&nbsp;
			Fecha Fin. <input class="form-control" type="text" data-date-format="dd/mm/yyyy" id="FechaFinal" name="FechaFinal" value="<%=fechaFinal%>" size="9" style="width: 120px"/>&nbsp;&nbsp;
			<select name="Tipo" class="form-select" style="width: 100px">
				<option <%if(tipo.equals("T"))out.print("Selected");%> value="T">Todos</option>
				<option <%if(tipo.equals("I"))out.print("Selected");%> value="I">Interna</option>
				<option <%if(tipo.equals("E"))out.print("Selected");%> value="E">Externa</option>
       		</select>&nbsp;&nbsp;&nbsp;&nbsp;
       		<input class="btn btn-primary" type="submit" value="Filtrar">
		</div>
	</form>
	<%if(!msj.equals("")){out.print("<div class='alert-success'>"+msj+"</div>");} %>
	
<table class="table table-sm table-bordered">  
	<thead class="table-info">
  <tr> 
    <th width="5%" align="center"><spring:message code="aca.Numero"/></th>
    <th width="25%" align="center"><spring:message code="aca.Facultad"/></th>
    <th width="25%" align="center"><spring:message code="aca.Director"/></th>
    <th width="5%" align="center">S</th>
    <th width="5%" align="center">P</th>
    <th width="5%" align="center">C</th>
    <th width="5%" align="center">G</th>
    <th width="5%" align="center">F</th>
    <th width="5%" align="center">D</th>
    <th width="5%" align="center">A</th>
    <th width="5%" align="center">T</th>
    <th width="5%" align="center">X</th>
    <th width="5%" align="center">R</th>    
  </tr>
	</thead>  
  
<%
	int cont		= 0;
	for (CatFacultad fac : lisFacultades){	
		cont++;		
		
		String numSol = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"S") ){
			numSol = mapEstado.get(fac.getFacultadId()+"S");
		}
		
		String numPre = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"P") ){
			numPre = mapEstado.get(fac.getFacultadId()+"P");
		}
		
		String numCon = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"C") ){
			numCon = mapEstado.get(fac.getFacultadId()+"C");
		}
		
		String numGra = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"G") ){
			numGra = mapEstado.get(fac.getFacultadId()+"G");
		}
		
		String numSinDoc = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"F") ){
			numSinDoc = mapEstado.get(fac.getFacultadId()+"F");
		}
		
		String numSinPag = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"D") ){
			numSinPag = mapEstado.get(fac.getFacultadId()+"D");
		}
		
		String numTra = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"A") ){
			numTra = mapEstado.get(fac.getFacultadId()+"A");
		}
		
		String numTer = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"T") ){
			numTer = mapEstado.get(fac.getFacultadId()+"T");
		}
		
		String numReg = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"R") ){
			numReg = mapEstado.get(fac.getFacultadId()+"R");
		}
		
		String numCan = "0";
		if (mapEstado.containsKey(fac.getFacultadId()+"X") ){
			numCan = mapEstado.get(fac.getFacultadId()+"X");
		}
		
		String maestroNombre = "-";
		if (mapMaestros.containsKey(fac.getCodigoPersonal())){
			maestroNombre = mapMaestros.get(fac.getCodigoPersonal());
		}		
%>
  <tr class="tr2" >
    <td align="left" height="25px"><font size="2"><%=fac.getFacultadId()%></font></td>    
    <td align="left"><a href="listado?facultad=<%=fac.getFacultadId()%>&Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>"><font size="2"><%=fac.getTitulo()%> de <%=fac.getNombreFacultad()%></font></a></td>    
    <td align="left"><font size="1"><%=maestroNombre%></font></td>
    <td align="left"><%=numSol %></td>
    <td align="left"><%=numPre%></td>
    <td align="left"><%=numCon%></td>
    <td align="left"><%=numGra%></td>
    <td align="left"><%=numSinDoc%></td>
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

<table style="width:100%" >
<tr> 
  <td align="center"><strong>¡ <spring:message code="aca.EligeUnaFacultad"/> !</strong></td>
</tr>
</table>
</div>
<script>
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>