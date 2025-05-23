<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.podium.spring.Examen"%>
<%@page import="aca.podium.spring.ExaArea"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmEvaluacionNota"%>
<%@page import="aca.admision.spring.AdmPrueba"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	String folio	 			= (String)request.getAttribute("folio");
	String examenId				= (String) request.getAttribute("examenId");
	String nombre	 			= (String)request.getAttribute("nombre");
	String carreraNivel			= (String)request.getAttribute("carreraNivel");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	AdmAcademico adm 			= (AdmAcademico)request.getAttribute("adm");	
	AdmPrueba admPrueba			= (AdmPrueba) request.getAttribute("admPrueba");
	boolean tienePrueba			= (boolean) request.getAttribute("tienePrueba");

	
	boolean mostrar		= false;
	
	float resLog		= (float)request.getAttribute("resLog");
	float resMat		= (float)request.getAttribute("resMat");
	float resEsp		= (float)request.getAttribute("resEsp");
	float resIng		= (float)request.getAttribute("resIng");
	float resBio		= (float)request.getAttribute("resBio");
	float resFis		= (float)request.getAttribute("resFis");
	float resQui		= (float)request.getAttribute("resQui");
	float resEnsayo		= (float)request.getAttribute("resEnsayo");
	
	float puntosPodium	= resLog+resMat+resEsp+resIng+resBio+resFis+resQui;
	
	boolean paseLog		= (boolean)request.getAttribute("paseLog");
	boolean paseMat		= (boolean)request.getAttribute("paseMat");
	boolean paseEsp		= (boolean)request.getAttribute("paseEsp");
	boolean paseIng		= (boolean)request.getAttribute("paseIng");
	boolean paseBio		= (boolean)request.getAttribute("paseBio");
	boolean paseFis		= (boolean)request.getAttribute("paseFis");
	boolean paseQui		= (boolean)request.getAttribute("paseQui");
	
	boolean tieneLog	= (boolean)request.getAttribute("tieneLog");
	boolean tieneMat	= (boolean)request.getAttribute("tieneMat");
	boolean tieneEsp	= (boolean)request.getAttribute("tieneEsp");
	boolean tieneIng	= (boolean)request.getAttribute("tieneIng");
	boolean tieneBio	= (boolean)request.getAttribute("tieneBio");
	boolean tieneFis	= (boolean)request.getAttribute("tieneFis");
	boolean tieneQui	= (boolean)request.getAttribute("tieneQui");

	boolean pregrado	= (boolean)request.getAttribute("pregrado");
	
	
	List<Examen> lisExamenes						= (List<Examen>) request.getAttribute("lisExamenes");
	HashMap<String,AdmEvaluacionNota> mapaNotas		= (HashMap<String,AdmEvaluacionNota>)request.getAttribute("mapaNotas");	
	HashMap<Integer,ExaArea> mapaAreas				= (HashMap<Integer,ExaArea>)request.getAttribute("mapaAreas");
	
	String notaTpt 		= "0";
	String fechaTpt		= "";
	if(mapaNotas.containsKey(folio+"7")){
		notaTpt 			= mapaNotas.get(folio+"7").getNota();
		fechaTpt 			= mapaNotas.get(folio+"7").getFecha();
	}
	
	String antecedente 		= "0";
	String fechaAntecedente	= "";
	if(mapaNotas.containsKey(folio+"8")){
		antecedente 		= mapaNotas.get(folio+"8").getNota();
		fechaAntecedente 	= mapaNotas.get(folio+"8").getFecha();
	}
	
	String entrevista 		= "0";
	String fechaEntrevista	= "";
	if(mapaNotas.containsKey(folio+"10")){
		entrevista 			= mapaNotas.get(folio+"10").getNota();
		fechaEntrevista 	= mapaNotas.get(folio+"10").getFecha();
	}
	
	String integral 		= "0";
	String fechaIntegral	= "";
	if(mapaNotas.containsKey(folio+"9")){
		integral 			= mapaNotas.get(folio+"9").getNota();
		fechaIntegral 		= mapaNotas.get(folio+"9").getFecha();
	}
	
	// Validar si se muestran los resultados
	if (adm.getCarreraId().equals("10301")){
		if( tieneLog && tieneMat && tieneEsp && tieneIng && tieneBio && tieneFis && tieneQui && 
			!notaTpt.equals("0") && !antecedente.equals("0") && !entrevista.equals("0") && !integral.equals("0")){
			mostrar = true;
		}
	}else if (adm.getCarreraId().equals("10313")||adm.getCarreraId().equals("10314")){
		if( tieneLog && tieneMat && tieneEsp && tieneIng && tieneBio && tieneFis && tieneQui &&
			!notaTpt.equals("0") && !antecedente.equals("0") && !entrevista.equals("0")){
			mostrar = true;
		}
	}else if (adm.getCarreraId().equals("10303")){
		if( tieneLog && tieneMat && tieneEsp && tieneIng && tieneBio && tieneFis && tieneQui ){
			mostrar = true;
		}
	}else{
		if( tieneLog && tieneMat && tieneEsp && tieneIng ){
			mostrar = true;
		}
	}
	
	String textoTpt 		= notaTpt;
	if (mostrar==false && !notaTpt.equals("0")){
		textoTpt = "Evaluated";
		textoTpt = "<span class='badge bg-info'>"+textoTpt+"</span>";
	}else if (notaTpt.equals("0")){
		textoTpt = "Pending"; 
		textoTpt = "<span class='label label-warning'>"+textoTpt+"</span>";
	}else{
		textoTpt = "<span class='badge bg-dark'>"+textoTpt+"</span>";	
	}
	String textoAntecedente = antecedente;
	if (mostrar==false && !antecedente.equals("0")){
		textoAntecedente = "Evaluated";
		textoAntecedente = "<span class='badge bg-info'>"+textoAntecedente+"</span>";
	}else if (antecedente.equals("0")){
		textoAntecedente = "Pending";
		textoAntecedente = "<span class='label label-warning'>"+textoAntecedente+"</span>";
	}else{
		textoAntecedente = "<span class='badge bg-dark'>"+textoAntecedente+"</span>";	
	}
	
	String textoEntrevista 	= entrevista;
	if (mostrar==false && !entrevista.equals("0")){
		textoEntrevista = "Evaluated"; 
		textoEntrevista = "<span class='badge bg-info'>"+textoEntrevista+"</span>";
	}else if (entrevista.equals("0")){
		textoEntrevista = "Pending";
		textoEntrevista = "<span class='label label-warning'>"+textoEntrevista+"</span>";
	}else{
		textoEntrevista = "<span class='badge bg-dark'>"+textoEntrevista+"</span>";	
	}
	
	String textoIntegral 	= integral;
	if (mostrar==false && !integral.equals("0")){
		textoIntegral = "Evaluado";
		textoIntegral = "<span class='badge bg-info'>"+textoIntegral+"</span>";
	}else if (integral.equals("0")){
		textoIntegral = "Pending";
		textoIntegral = "<span class='label label-warning'>"+textoIntegral+"</span>";
	}else{
		textoIntegral = "<span class='badge bg-dark'>"+textoIntegral+"</span>";
	}
	
%>
<head>
	<link href="../admision.css" rel="STYLESHEET" type="text/css">
	<script type="text/javascript" src="../js/iframeResizer.contentWindow.min.js"></script>
</head>
<body>
<div class="container-fluid">
	<form name="frmResultados" action="resultadosEvaluacion" method="post">
		<input type="hidden" name="Folio" value="<%=folio%>">
		<div class="page-header">
			<h2>Evaluations<small class="text-muted fs-5">(P. ID: <%=folio%> &nbsp; Name: <%=nombre%> - <%=adm.getCarreraId().equals("00000")?"&nbsp;":adm.getCarreraId()%> - <%=adm.getCarreraId().equals("00000")?"&nbsp;":carreraNombre%> )</small></h2>
			<div class="alert alert-info">
		  		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
	  			Select:
	  			<select name="ExamenId" onchange="javascript:document.frmResultados.submit();" style="width:185px">
<%	for(Examen exa : lisExamenes){ %>		
					<option value="<%=exa.getId()%>" <%=exa.getId()==Integer.parseInt(examenId)?"selected":""%>>Test <%=exa.getFecha()%></option>
<% 	}%>			
				</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%	if (lisExamenes.size() >= 1 ){	
		if (carreraNivel.equals("3")||carreraNivel.equals("4")){
			if(resEnsayo > 0){
%>						
				<a href="resultadosPDF3?ExamenId=<%=examenId%>&Folio=<%=folio%>" class="btn btn-warning"><i class='fas fa-file-pdf' aria-hidden='true'></i> Results</a>
<%			} %>
<%		}else {%>			
				<a href="resultadosPDF?ExamenId=<%=examenId%>&Folio=<%=folio%>" class="btn btn-warning"><i class='fas fa-file-pdf' aria-hidden='true'></i>  Results</a>
<%		}						
	}
%>						
	  		</div>
		</div>	
	</form>	
	<table class="table table-bordered">
	<thead class="table-info">
	 	<tr>
	 		<th>#1</th>
	 		<th>PODIUM test</th>
	 		<th>Other tests</th> 		
	 		<th class="right">Questions</th> 		
	 		<th class="right">Minimum Score</th>
	 		<th class="right">Maximum Score</th>
	 		<th class="right">Obtained Score</th> 		
	 	</tr> 	
	 </thead>
 <%
 	int totPreguntas = 0; int totPuntos = 0; float puntosRequeridos = 0; 
 	if (mapaAreas.containsKey(1)){
 		if(pregrado) {
			totPreguntas 		+= mapaAreas.get(1).getFacilPre()+mapaAreas.get(1).getMedioPre()+mapaAreas.get(1).getDificilPre();	 			
			totPuntos 			+= mapaAreas.get(1).getPuntosPre()*totPreguntas;
			puntosRequeridos	+= mapaAreas.get(1).getMinimoPre();
		}else {
	 		totPreguntas 		+= mapaAreas.get(1).getFacilPos()+mapaAreas.get(1).getMedioPos()+mapaAreas.get(1).getDificilPos();	 			
	 		totPuntos 			+= mapaAreas.get(1).getPuntosPos()*totPreguntas;
	 		puntosRequeridos	+= mapaAreas.get(1).getMinimoPos();
		}
 	}
 	String color = resLog >= puntosRequeridos?"Approved":"Insufficient";
 %>	
	 	<tr>
	 		<td>a)</td>
	 		<td>Logic</td>
	 		<td><span class="badge <%=paseLog?"bg-success":"bg-warning"%>"><%=tieneLog?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td>
	 		<td class="right"><%=puntosRequeridos%></td> 		
	 		<td class="right"><%=totPuntos%></td> 		
	 		<td class="right"><%=formato.format(resLog)%></td> 		
	 	</tr> 	
 	<%
 	totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 	if (mapaAreas.containsKey(2)){
 		if(pregrado) {
 			totPreguntas 		+= mapaAreas.get(2).getFacilPre()+mapaAreas.get(2).getMedioPre()+mapaAreas.get(2).getDificilPre(); 			
 			totPuntos 			+= mapaAreas.get(2).getPuntosPre()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(2).getMinimoPre();
		}else {
 			totPreguntas 		+= mapaAreas.get(2).getFacilPos()+mapaAreas.get(2).getMedioPos()+mapaAreas.get(2).getDificilPos(); 			
 			totPuntos 			+= mapaAreas.get(2).getPuntosPos()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(2).getMinimoPos();
		}
	}
%>
	 	<tr>
	 		<td>b)</td>
	 		<td>Mathematics</td>
	 		<td><span class="badge <%=paseMat?"bg-success":"bg-warning"%>"><%=tieneMat?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td> 		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td> 		
	 		<td class="right"><%=formato.format(resMat)%></td> 		
	 	</tr>
 <%
 	totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 	if (mapaAreas.containsKey(3)){
 		if(pregrado) {
 			totPreguntas 		= mapaAreas.get(3).getFacilPre()+mapaAreas.get(3).getMedioPre()+mapaAreas.get(3).getDificilPre();; 			
 			totPuntos 			+= mapaAreas.get(3).getPuntosPre()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(3).getMinimoPre();
		}else {
 			totPreguntas 		= mapaAreas.get(3).getFacilPos()+mapaAreas.get(3).getMedioPos()+mapaAreas.get(3).getDificilPos(); 			
 			totPuntos 			+= mapaAreas.get(3).getPuntosPos()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(3).getMinimoPos();
		}
	}

	if (mapaAreas.containsKey(4)){
		if(pregrado) {
 			totPreguntas 		= mapaAreas.get(4).getFacilPre()+mapaAreas.get(4).getMedioPre()+mapaAreas.get(4).getDificilPre();			
 			totPuntos 			+= mapaAreas.get(4).getPuntosPre()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(4).getMinimoPre();
		}else {
 			totPreguntas 		= mapaAreas.get(4).getFacilPos()+mapaAreas.get(4).getMedioPos()+mapaAreas.get(4).getDificilPos(); 			
 			totPuntos 			+= mapaAreas.get(4).getPuntosPos()*totPreguntas;
 			puntosRequeridos	+= mapaAreas.get(4).getMinimoPos();
		}
	}
	
 	%>
	 	<tr>
	 		<td>c)</td>
	 		<td>Spanish</td>
	 		<td><span class="badge <%=paseEsp?"bg-success":"bg-warning"%>"><%=tieneEsp?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td> 		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td>
	 		<td class="right"><%=formato.format(resEsp)%></td>
	 	</tr>
<%
 	totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 	if (mapaAreas.containsKey(5)){
 		if(pregrado) {
			totPreguntas 		= mapaAreas.get(5).getFacilPre()+mapaAreas.get(5).getMedioPre()+mapaAreas.get(5).getDificilPre();			
			totPuntos 			+= mapaAreas.get(5).getPuntosPre()*totPreguntas;
			puntosRequeridos	+= mapaAreas.get(5).getMinimoPre();
		}else {
			totPreguntas 		= mapaAreas.get(5).getFacilPos()+mapaAreas.get(5).getMedioPos()+mapaAreas.get(5).getDificilPos();			
			totPuntos 			+= mapaAreas.get(5).getPuntosPos()*totPreguntas;
			puntosRequeridos	+= mapaAreas.get(5).getMinimoPos();
		}
 	}
 	if (mapaAreas.containsKey(6)){
 		if(pregrado) {
			totPreguntas 		= mapaAreas.get(6).getFacilPre()+mapaAreas.get(6).getMedioPre()+mapaAreas.get(6).getDificilPre();			
			totPuntos 			+= mapaAreas.get(6).getPuntosPre()*totPreguntas;
			puntosRequeridos	+= mapaAreas.get(6).getMinimoPre();
		}else {
			totPreguntas 		= mapaAreas.get(6).getFacilPos()+mapaAreas.get(6).getMedioPos()+mapaAreas.get(6).getDificilPos();			
			totPuntos 			+= mapaAreas.get(6).getPuntosPos()*totPreguntas;
	 		puntosRequeridos	+= mapaAreas.get(6).getMinimoPos();
		}
 	}
%>
	 	<tr>
	 		<td>d)</td>
	 		<td>English</td>
	 		<td><span class="badge <%=paseIng?"bg-success":"bg-warning"%>"><%=tieneIng?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td>		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td> 		 		
	 		<td class="right"><%=formato.format(resIng)%></td> 		
	 	</tr>
<% if(adm.getCarreraId().equals("10301") || adm.getCarreraId().equals("10314") || adm.getCarreraId().equals("10313") || adm.getCarreraId().equals("10303")){%>
	<%
 		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 		if (mapaAreas.containsKey(7)){
 			if(pregrado) {
	 			totPreguntas 		+= mapaAreas.get(7).getFacilPre()+mapaAreas.get(7).getMedioPre()+mapaAreas.get(7).getDificilPre();	 			
	 			totPuntos 			+= mapaAreas.get(7).getPuntosPre()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(7).getMinimoPre();
			}else {
	 			totPreguntas 		+= mapaAreas.get(7).getFacilPos()+mapaAreas.get(7).getMedioPos()+mapaAreas.get(7).getDificilPos(); 			
	 			totPuntos 			+= mapaAreas.get(7).getPuntosPos()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(7).getMinimoPos();
			}
 		}
 	%>
	 	<tr>
	 		<td>e)</td>
	 		<td>Biology</td>
	 		<td><span class="badge <%=paseBio?"bg-success":"bg-warning"%>"><%=tieneBio?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td> 		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td>
	 		<td class="right"><%=formato.format(resBio)%></td> 		
	 	</tr>
 	<%
 		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 		if (mapaAreas.containsKey(8)){
 			if(pregrado) {
	 			totPreguntas 		+= mapaAreas.get(8).getFacilPre()+mapaAreas.get(8).getMedioPre()+mapaAreas.get(8).getDificilPre();	 			
	 			totPuntos 			+= mapaAreas.get(8).getPuntosPre()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(8).getMinimoPre();
			}else {
	 			totPreguntas 		+= mapaAreas.get(8).getFacilPos()+mapaAreas.get(8).getMedioPos()+mapaAreas.get(8).getDificilPos();	 			
	 			totPuntos 			+= mapaAreas.get(8).getPuntosPos()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(8).getMinimoPos();
			}
 		}
 	%>
	 	<tr>
	 		<td>f)</td>
	 		<td>Physics</td>
	 		<td><span class="badge <%=paseFis?"bg-success":"bg-warning"%>"><%=tieneFis?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td> 		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td>		
	 		<td class="right"><%=formato.format(resFis)%></td> 		
	 	</tr>
 	<%
 		totPreguntas = 0; totPuntos = 0; puntosRequeridos = 0;
 		if (mapaAreas.containsKey(9)){
 			if(pregrado) {
	 			totPreguntas 		+= mapaAreas.get(9).getFacilPre()+mapaAreas.get(9).getMedioPre()+mapaAreas.get(9).getDificilPre();	 			
	 			totPuntos 			+= mapaAreas.get(9).getPuntosPre()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(9).getMinimoPre();
			}else {
	 			totPreguntas 		+= mapaAreas.get(9).getFacilPos()+mapaAreas.get(9).getMedioPos()+mapaAreas.get(9).getDificilPos();	 			
	 			totPuntos 			+= mapaAreas.get(9).getPuntosPos()*totPreguntas;
	 			puntosRequeridos	+= mapaAreas.get(9).getMinimoPos();
			}
 		}
 	%>
	 	<tr>
	 		<td>g)</td>
	 		<td>Chemistry</td>
	 		<td><span class="badge <%=paseQui?"bg-success":"bg-warning"%>"><%=tieneQui?"YES":"NO"%></span></td> 		
	 		<td class="right"><%=totPreguntas%></td> 		
	 		<td class="right"><%=puntosRequeridos%></td>
	 		<td class="right"><%=totPuntos%></td> 		
	 		<td class="right"><%=formato.format(resQui)%></td> 		
	 	</tr>
<% } %>
		<tr>
	 		<th style="text-align: right" width="90%" colspan="6">TOTAL</th>
	 		<th style="text-align: right" width="10%"><%=formato.format(puntosPodium)%></th>
	 	</tr> 
	</table>
	<table class="table table-bordered">
		<tbody>
<% if(adm.getCarreraId().equals("10301") || adm.getCarreraId().equals("10314") || adm.getCarreraId().equals("10313")){%>
			<tr>
		 		<th>#</th>
		 		<th>Other evaluations</th>
		 		<th>Registration Date</th>
		 		<th class="right">Score</th> 				
		 	</tr> 
		 	<tr class="alert alert-success">
		 		<td>2</td>
		 		<td>Personality Test</td>
		 		<td><%=fechaTpt%></td>
		 		<td class="right"><%=textoTpt%></td>
		 	</tr>
		 	<tr class="alert alert-success">
		 		<td>3</td>
		 		<td>Academic background</td>
		 		<td><%=fechaAntecedente%></td>
		 		<td class="right"><%=textoAntecedente%></td>
		 	</tr>
		 	<tr class="alert alert-success">
		 		<td>4</td>
		 		<td>Interview</td>
		 		<td><%=fechaEntrevista%></td>
		 		<td class="right"><%=textoEntrevista%></td>
		 	</tr>
<% } %>
<% if(adm.getCarreraId().equals("10301")){%>
		 	<tr class="alert alert-success">
		 		<td>5</td>
		 		<td>Integral Evaluation(Physical, Cultural, Social and Spiritual)</td>
		 		<td><%=fechaIntegral%></td>
		 		<td class="right"><%=textoIntegral%></td>
		 	</tr>
<% }%> 	
		</tbody>
<%
	float total = 0;
	if(!entrevista.equals("0") && !antecedente.equals("0") && !notaTpt.equals("0")){
		total = Float.parseFloat(entrevista)+Float.parseFloat(antecedente)+Float.parseFloat(notaTpt)+Float.parseFloat(integral);
	%>
		<tfoot>
			<tr>
				<th style="text-align: right" colspan="3">Total</th>
				<th style="text-align: right"><%=formato.format(total)%></th>
			</tr>
		</tfoot>
<%	}%>	</table>
<table id="table" class="table  table-bordered">
	<thead>
	<tr>
		<th>Results</th>
		<th>Comments</th>
		<th>Follow-up</th>
	</tr>
	</thead>
	<tbody>
		<tr>
		<%if(tienePrueba){ %>
		 	 <td><%=admPrueba.getNombre() %></td>
			 <td><%=admPrueba.getDescripcion() %></td>
		 		<%if(admPrueba.getPruebaId().equals("1") || admPrueba.getPruebaId().equals("2")){ %>
		  			 <td>No</td>
		 		 <%}else{ %>
		 			 <td>Si</td>
		  		<%}%>  
		 </tr>
	  <%} %>  
	 </tbody>
 </table>
		  
<%	if(!entrevista.equals("0") && !antecedente.equals("0") && !notaTpt.equals("0")){ %>	
	<div class="alert alert-success text-right">Total Score: <%=puntosPodium+total%></div>
<%	} %>
	<div class="alert alert-success">
		If you have any doubts or questions about your results, please contact the Admissions Office at (826) 2630900 Ext. 1503 or by e-mail: ruben_oro@um.edu.mx
	</div>
</div>	
</body>