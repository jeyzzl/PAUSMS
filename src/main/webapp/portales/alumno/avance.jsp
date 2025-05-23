<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.catalogo.spring.CatTipoCurso"%>
<%@ page import="aca.parametros.spring.Parametros"%>
<%@ page import="aca.catalogo.spring.CatAvance"%>
<%@ page import="aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "menu.jsp" %>

<html>
	<script type="text/javascript">
		function recarga(){	
	  		document.location.href="avance?PlanId="+document.frmGraduando.PlanId.value;
		}
	</script>
<%	
	DecimalFormat getformato	= new DecimalFormat("###,##0.00;-###,##0.00");
	
	String institucion 			= (String) session.getAttribute("institucion");	
	String codigoAlumno			= (String) session.getAttribute("codigoAlumno");
		
 	//String planId				= request.getParameter("PlanId")==null?"0":request.getParameter("PlanId").trim();
	String planId				= (String)request.getAttribute("planId");
	double pagare				= (double)request.getAttribute("pagare");
	double saldo				= (double)request.getAttribute("saldo");
	int compPla					= (int)request.getAttribute("compPlan");
	int compAlumno				= (int)request.getAttribute("compAlumno");
	String avance				= (String)request.getAttribute("avance");
	CatAvance avan				= (CatAvance)request.getAttribute("avan");
	
	Parametros parametros		= (Parametros)request.getAttribute("parametros");
	String documentos			= (String)request.getAttribute("documentos");
	
	List<String> lisPlanes							= (List<String>)request.getAttribute("lisPlanes");
	
	List<CatTipoCurso> lisCursos 					= (List<CatTipoCurso>)request.getAttribute("lisCursos");
	
	HashMap<String, MapaPlan> mapaPlanes 			= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaCredReq 			= (HashMap<String, String>)request.getAttribute("mapaCredReq");
	HashMap<String, String> mapaCredAc				= (HashMap<String, String>)request.getAttribute("mapaCredAc");
	HashMap<String, String> mapaCredInsc			= (HashMap<String, String>)request.getAttribute("mapaCredInsc");
	
	String nombreAlumno		= "";
	String carrera			= "";
	String modalidad		= "";
	String residencia		= "";	
	String finanzas			= "";
	String registro			= "";
	String permiso			= "";	
	double saldoVencido 	= 0;	
	int valor				= 0;
	
	nombreAlumno 	= (String) request.getAttribute("nombreAlumno");
	carrera			= (String) request.getAttribute("carrera");
	modalidad		= (String) request.getAttribute("modalidad"); 
	residencia 		= (String) request.getAttribute("residencia"); 
	
	if (residencia.equals("E")) residencia = "Externo"; else residencia = "Boarding Student";
	 	
	//System.out.println("Paso 1");
%>	
<head>	
	<link href="../../print.css" rel="stylesheet" type="text/css" media="print" >		
</head>
<body bgcolor="#FFFFFF">

<div class="container-fluid">
	<h3><spring:message code="portal.alumno.avance.AvanceEnPlan"/>:<small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h3>	
	<div class="alert alert-info">
		<form name="frmGraduando" class="form-inline mb-0">
		<spring:message code="portal.alumno.avance.Plan"/>: &nbsp; <select name="PlanId" onchange="javascript:recarga()" class="custom-select">
<%	
	for(int i=0;i<lisPlanes.size();i++){		
		String plan = (String)lisPlanes.get(i);
		String nombrePlan = "-";
		if (mapaPlanes.containsKey(plan)){
			nombrePlan = mapaPlanes.get(plan).getNombrePlan();
		}
%>
			<option value="<%=plan%>" <%=plan.equals(planId)?"Selected":""%> ><%=plan%>-<%=nombrePlan%></option>
<%	}%>
		</select>		
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="GraduandoFotos.pdf" target="_blanck"><spring:message code="portal.alumno.avance.RequisitosDeFotografiasParaGraduacion"/></a>
		</form>
	</div>					 
	
<%
	try{	  
	  saldoVencido 	= saldo + pagare;
	  	
	  if(saldoVencido >= 0){
		finanzas = "Authorized..¡¡";
	  }
	  else{
		finanzas = "Doesn't comply..¡¡";
		valor = 1;
	  }	

	// AUTORIZACIÓN DE AVANCE DE KARDEX ( OFICINA DE REGISTRO )
	  if(permiso.equals("S")){
		  permiso = "Authorized..¡¡"; 
	  }
	  else{
		  permiso = "Without Permission..¡¡";
	  }		

	}catch(Exception e){
	  System.out.println(e.toString());
	}
%>
	<div class="row">
		<div class="col"><b>[<%=modalidad%> ] &nbsp; [ <%=residencia%> ]</b></div>
	</div>	
	<hr>
	<div class="container-fluid">
	<div class="row">
		<b><spring:message code="aca.Documento"/>:</b>&nbsp;&nbsp;<%=documentos %>
	</div>
	<div class="row"><b><spring:message code="portal.alumno.avance.Finanzas"/>: &nbsp;&nbsp;</b> <%=finanzas %>&nbsp;&nbsp;&nbsp;&nbsp;
<%		if(valor != 1){%>
		<div class="col-14"><spring:message code="portal.alumno.avance.EdoCuenta"/>: [<%=getformato.format(saldo)%>]</div>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="col-14">Balance Cr: [<%=getformato.format(saldoVencido)%>]</div>
<%		}else{%>
		<div class="col-14"><spring:message code="portal.alumno.avance.EdoCuenta"/>: <font color="#AE2113"><b>[<%=getformato.format(saldo) %>]</b></font></div>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="col-14">Balance Db: <font color="#AE2113"><b>[<%=getformato.format(saldoVencido)%>]</b></font></div>
<%		}%>
	</div>	
	<div class="row"><b><spring:message code="portal.alumno.avance.EstadoDelAlumno"/>:&nbsp;&nbsp;</b><%=avan.getNombreAvance() %></div>
	</div>	
	&nbsp;&nbsp;	
	
	<div class="row">
		<div class="col-12"><b>Register</b></div>
	</div>	
	
	<div class="table-sm">
  	<table class="table">
		<thead>
	    	<tr>
		      <th scope="col"><font color="#AE2113" size="4"><spring:message code="portal.alumno.avance.Creditos"/></font></th>
		      <th scope="col"><font color="#AE2113" size="4"><spring:message code="portal.alumno.avance.Requeridos"/></font></th>
		      <th scope="col"><font color="#AE2113" size="4"><spring:message code="portal.alumno.avance.Acreditados"/></font></th>
		      <th scope="col"><font color="#AE2113" size="4"><spring:message code="portal.alumno.avance.Matriculados"/></font></th>
		      <th scope="col"><font color="#AE2113" size="4"><spring:message code="portal.alumno.avance.Faltantes"/></font></th>
		    </tr>
	  	</thead>
		  <%				
 		float sumaReq = 0;
 		float sumaAc = 0;
 		float sumaMat = 0;
 		for(CatTipoCurso curso : lisCursos){
			
 			float credRequeridos = 0;
 			if (mapaCredReq.containsKey(curso.getTipoCursoId())){
 				credRequeridos = Float.parseFloat(mapaCredReq.get(curso.getTipoCursoId()));
 			}
				
 			float credAc = 0;
 				if (mapaCredAc.containsKey(curso.getTipoCursoId())){
 				credAc = Float.parseFloat(mapaCredAc.get(curso.getTipoCursoId()));
 			}
				
 				float credInsc = 0;
 				if (mapaCredInsc.containsKey(curso.getTipoCursoId())){
 				credInsc = Float.parseFloat(mapaCredInsc.get(curso.getTipoCursoId()));
 			}
				
 			//float crAc = 0;
 			//float crMat = 0;
 			float credFaltan = credRequeridos - credAc - credInsc;
			
 			sumaReq	+= credRequeridos; 
 			sumaAc	+= credAc;
 			sumaMat	+= credInsc;
 %>	
		<tr>
	      <th><font size="3"><%=curso.getNombreTipoCurso()%></font></th>
	      <td><font size="3"><%=credRequeridos%></font></td>
	      <td><font size="3"><%=credAc%></font></td>
	      <td><font size="3"><%=credInsc%></font></td>
	      <td><font size="3"><%=credFaltan%></font></td>
	    </tr> 
<%		}%>
	</table>
	</div>	
	&nbsp;&nbsp;
	<div class="row">
		<div class="col-12"><spring:message code="portal.alumno.avance.PorcentajeTotalAlcanzado"/> [<%=getformato.format((sumaAc/sumaReq)*100)%>%] &nbsp;&nbsp;&nbsp;
  			<spring:message code="portal.alumno.avance.PorcentajeMaximo"/> [<%=getformato.format(((sumaAc+sumaMat)/sumaReq)*100)%>%]
  		</div>
	</div>		
	&nbsp;&nbsp;&nbsp;
	<div class="center"><strong><br><br>__________________________________</strong></div>
	<div class="center"><%=parametros.getCardex()%></div>
	<div class="center"><strong><spring:message code="portal.alumno.avance.DirectoraDeRegistro"/></strong></div>
<script>
	$('.nav-tabs').find('.gradua').addClass('active');
</script>
</div>
</body>
</html>