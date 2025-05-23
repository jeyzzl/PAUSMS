<%@ page import="aca.conva.spring.ConvEvento"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.util.*"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head><link href="css/pa.css" rel="STYLESHEET" type="text/css">
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
</STYLE>
<%		
	String codigoPersonal 	= (String)session.getAttribute("codigoPersonal");
	String tipo 			= request.getParameter("Tipo")==null?"'I','E','-'":request.getParameter("Tipo");
	
	String convalidacionId 	= request.getParameter("convalidacionId");
	String estado			= request.getParameter("estado");
	String carreraId		= request.getParameter("carreraId");
	String nombreCarrera	= (String)request.getAttribute("nombreCarrera");

	String facultad 		= request.getParameter("facultad");
	String convalidaciones	= request.getParameter("convalidaciones")==null?"0":request.getParameter("convalidaciones");
	
	String year 			= aca.util.Fecha.getHoy().substring(6, 10);
	String fechaInicio 		= request.getParameter("FechaInicio")==null?"01/01/"+year:request.getParameter("FechaInicio");
	String fechaFinal  		= request.getParameter("FechaFinal")==null?"31/12/"+year:request.getParameter("FechaFinal");
	
	// Busca los periodos recientes
	String periodoId 		= (String)request.getAttribute("periodoId");
	int year1				= Integer.parseInt(periodoId.substring(0,2))-1;
	int year2				= Integer.parseInt(periodoId.substring(2,4))-1;
	String periodoOld		= String.valueOf(year1)+String.valueOf(year2);
	
	String sBgcolor			= "";	
	String queryCarrera		= "";
	String escribir			= "";	
		
	// Lista de carreras	
	List<ConvEvento> lisEventos				= (List<ConvEvento>)request.getAttribute("lisEventos");
	HashMap<String,String> mapAlumnos		= (HashMap<String,String>)request.getAttribute("mapAlumnos");
	HashMap<String,String> mapMaterias		= (HashMap<String,String>)request.getAttribute("mapMaterias");
	HashMap<String,String> mapPorEstado		= (HashMap<String,String>)request.getAttribute("mapPorEstado");

%>
<form name="forma" action="catalogo?carreraId=<%=carreraId %>&Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>" method='post' id="noayuda">
<div class="container-fluid">
<h2>Procesos de Convalidación <small class="text-muted fs-4">( <%=carreraId%>-<%=nombreCarrera%> )</small></h2>
<div class="alert alert-info d-flex align-items-center">
	<a href="listado?facultad=<%=facultad %>&Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	<select name="convalidaciones" onchange="javascript:document.location.href='catalogo?carreraId=<%=carreraId %>&facultad=<%=facultad %>&Tipo=<%=tipo%>&FechaInicio=<%=fechaInicio%>&FechaFinal=<%=fechaFinal%>&convalidaciones='+this.options[this.selectedIndex].value;" class="form-select" style="width: 400px">
		<option value="0"<%if(convalidaciones.equals("0"))out.print("selected"); %>>Todos</option>
		<option value="S"<%if(convalidaciones.equals("S"))out.print("selected"); %>>En proceso de Solicitud (S)</option>
		<option value="P"<%if(convalidaciones.equals("P"))out.print("selected"); %>>En Predictamen (P)</option>
		<option value="C"<%if(convalidaciones.equals("C"))out.print("selected"); %>>En Confirmadas (C)</option>
		<option value="G"<%if(convalidaciones.equals("G"))out.print("selected"); %>>Graduales (G)</option>
		<option value="F"<%if(convalidaciones.equals("F"))out.print("selected"); %>>Sin documento (F)</option>
		<option value="D"<%if(convalidaciones.equals("D"))out.print("selected"); %>>Sin Pago (D)</option>
		<option value="A"<%if(convalidaciones.equals("A"))out.print("selected"); %>>En Tramite (A)</option>
		<option value="T"<%if(convalidaciones.equals("T"))out.print("selected"); %>>Trámite Terminado (T)</option>
		<option value="X"<%if(convalidaciones.equals("X"))out.print("selected"); %>>Trámite Cancelado (X)</option>
		<option value="R"<%if(convalidaciones.equals("R"))out.print("selected"); %>>Registro de notas (R)</option>
	</select>
</div>
	<table style="width:80%; margin:0 auto;">
		<tr><td align="center" colspan="12" class="titulo"></td></tr>
	<tr>
		<td colspan="12" align="center">
			
		</td>
	</tr>
	</table>
	<table class="table table-sm table-bordered">  
	
<%		
		String nombre = "";
		if(convalidaciones.equals("")){
			nombre = "Todos";
		}else if(convalidaciones.equals("S")){
			nombre = "Convalidaciones en proceso de Solicitud (S)";
		}else if(convalidaciones.equals("P")){
			nombre = "Convalidaciones en Predictamen (P)";
		}else if(convalidaciones.equals("C")){
			nombre = "Convalidaciones Confirmadas (C)";
		}else if(convalidaciones.equals("C")){
			nombre = "Convalidaciones G (G)";	
		}else if(convalidaciones.equals("A")){
			nombre = "Convalidaciones en Tramite (A)";
		}else if(convalidaciones.equals("T")){
			nombre = "Convalidaciones con tramite Terminado (T)";
		}else if(convalidaciones.equals("X")){
			nombre = "Convalidaciones con tramite Cancelado (X)";
		}else if(convalidaciones.equals("R")){
			nombre = "Convalidaciones con Registro de notas (R)";
		}

%>
	<thead class="table">
	<tr><td colspan="12"><b><%=nombre %></b></td></tr>
	</thead>
	<thead class="table-info">
	<tr>						  
	  <th width="5%"><spring:message code="aca.Numero"/></th>
	  <th width="5%"><spring:message code="aca.Matricula"/></th>
	  <th width="40%"><spring:message code="aca.Nombre"/></th>
	  <th width="10%"><spring:message code="aca.Fecha"/></th>
	  <th width="5%">N° Mat.</th>
	  <th width="5%">Capturadas</th>
	  <th width="5%">Aceptadas</th>
	  <th width="5%">Rechazadas</th>
	  <th width="5%">Kardex</th>
	  <th width="5%">Plan ID</th>
	  <th width="7%"><spring:message code="aca.Estado"/></th>
	  <th width="5%">Tipo</th>
	  <th width="5%">Tipo Conv.</th>
	</tr>
	</thead>
<%
        int row = 0;
		for(ConvEvento convEvento : lisEventos){
		row++;
		
		String alumnoNombre = "-";
		if (mapAlumnos.containsKey(convEvento.getCodigoPersonal())){
			alumnoNombre = mapAlumnos.get(convEvento.getCodigoPersonal());
		}
		
		String numMaterias 	= "0";		
		if (mapMaterias.containsKey(convEvento.getConvalidacionId())){
			numMaterias = mapMaterias.get(convEvento.getConvalidacionId());
		}	
		
		String numCapturadas 	= "0";		
		if (mapPorEstado.containsKey(convEvento.getConvalidacionId()+"-")){
			numCapturadas = mapPorEstado.get(convEvento.getConvalidacionId()+"-");
		}
		
		String numAceptadas 	= "0";		
		if (mapPorEstado.containsKey(convEvento.getConvalidacionId()+"S")){
			numAceptadas = mapPorEstado.get(convEvento.getConvalidacionId()+"S");
		}
		
		String numRechazadas 	= "0";
		if (mapPorEstado.containsKey(convEvento.getConvalidacionId()+"N")){
			numRechazadas = mapPorEstado.get(convEvento.getConvalidacionId()+"N");
		}
		
		String numRegistradas	= "0";
		if (mapPorEstado.containsKey(convEvento.getConvalidacionId()+"R")){
			numRegistradas = mapPorEstado.get(convEvento.getConvalidacionId()+"R");
		}	
%>					
	<tr>						
	  <td><%=row%></td>
	  <td align="center"><%=convEvento.getCodigoPersonal() %></td>
	  <td><%=alumnoNombre%></td>
	  <td><%=convEvento.getFecha()%></td>
	  <td align="center"><%=numMaterias%></td>
	  <td align="center"><%=numCapturadas%></td>
	  <td align="center"><%=numAceptadas%></td>	  
	  <td align="center"><%=numRechazadas%></td>
	  <td align="center"><%=numRegistradas%></td>
	  <td align="center"><%=convEvento.getPlanId() %></td>
	  <td align="center"><%=convEvento.getEstado() %></td>
	  <td><%=convEvento.getTipo().equals("E")?"Externa":"Interna"%></td>
	  <td><%=convEvento.getTipoConv().equals("M")?"Materia":"Grado"%></td>
	</tr>
<%		
		}
%> 
  </table>
 </div>
</form>