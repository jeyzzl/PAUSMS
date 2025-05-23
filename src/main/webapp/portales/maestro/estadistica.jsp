<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="acu" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<%
	String tema = request.getParameter("tema")==null?"0":request.getParameter("tema");
%>
	<script type="text/javascript" src="../../js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="../../js/highcharts.js"></script>
	<script type="text/javascript" src="../../js/highChart/modules/exporting.js"></script>
	<script type="text/javascript" src="../../js/highChart/puntos.js"></script>  
	<script type="text/javascript" src="../../js/highChart/column-stacked.js"></script> 
	<script type="text/javascript" src="../../js/highChart/column-basic.js"></script>  
	<script type="text/javascript" src="../../js/highChart/bar-stacked.js"></script>   
	<script type="text/javascript" src="../../js/highChart/area-stacked.js"></script>  
<%	if(!tema.equals("0")){ %>
	<script type="text/javascript" src="../../js/highChart/themes/<%=tema%>"></script>  
<%	} %>

<!-- inicio de estructura -->
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

 	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	String cargaId			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	
	int valor				= 0;
	int avanceMat			= 0;	
	String inicial 			= "0";
	String evaluar 			= "0";
	String extra 			= "0";
	String cerrada 			= "0";
	String registrada  		= "0";
	
	String nombreEmpleado	= (String)request.getAttribute("nombreEmpleado");
	String cargaNombre		= (String)request.getAttribute("cargaNombre");
	String numMaterias		= (String)request.getAttribute("numMaterias");
	int numAlumnos 			= (int)request.getAttribute("numAlumnos");
	int numInscritos		= (int)request.getAttribute("numInscritos");
	int numBajas			= (int)request.getAttribute("numBajas");
	int numAcreditados		= (int)request.getAttribute("numAcreditados");
	int numReprobados		= (int)request.getAttribute("numReprobados");
	int numPendientes		= (int)request.getAttribute("numPendientes");
		
	HashMap<String,String> mapaEstados 	= (HashMap<String,String>)request.getAttribute("mapaEstados");
	
	// Calcula el progreso en extrega de materias
	if (!numMaterias.equals("0")){
		if (mapaEstados.containsKey(cargaId+"1")){
			inicial = mapaEstados.get(cargaId+"1");
		}
		if (mapaEstados.containsKey(cargaId+"2")){
			evaluar = mapaEstados.get(cargaId+"2"); 
			valor += Integer.parseInt(mapaEstados.get(cargaId+"2"))*50;
		}
		if (mapaEstados.containsKey(cargaId+"3")){
			extra = mapaEstados.get(cargaId+"3");
			valor += Integer.parseInt(mapaEstados.get(cargaId+"3"))*75;
		}
		if (mapaEstados.containsKey(cargaId+"4")){
			cerrada = mapaEstados.get(cargaId+"4"); 
			valor += Integer.parseInt(mapaEstados.get(cargaId+"4"))*100;
		}
		if (mapaEstados.containsKey(cargaId+"5")){
			registrada = mapaEstados.get(cargaId+"5");
			valor += Integer.parseInt(mapaEstados.get(cargaId+"5"))*100;
		}
		avanceMat = valor/Integer.parseInt(numMaterias);
	}else{
		avanceMat = 0;
	}	
	
	double avanceNotas 	=  0;
	if (numAlumnos>0){
		if ((numInscritos+numPendientes)>0){			
			avanceNotas = 100-(((double)(numInscritos+numPendientes)/(double)numAlumnos)*100);
		}else{
			avanceNotas = 100;
		}
	}
	
	String materias= " ' Initial ',' Evaluate ',' Extra ',' Closed ',' Recorded '";
	String datos = "{name: 'Subjects', data: ["+inicial+","+evaluar+","+extra+","+cerrada+","+registrada+"]}";
	
	String alumnos= " ' Enrolled ',' Dropped ',' Accredited ',' Failed ',' Pending '";
	String datosA = "{name: 'Status', data: ["+numInscritos+","+numBajas+","+numAcreditados+","+numReprobados+","+numPendientes+"]}";					
%>
<div class="container-fluid">
	<h3><spring:message code="portal.maestro.estadistica.Empleado"/>: <%=codigoEmpleado%> - <%=nombreEmpleado%> <small class="text-muted">( <spring:message code="portal.maestro.estadistica.Periodo"/>:</b> <%=cargaNombre%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:90%" align="CENTER">
	  <tr><td class="text-center"><h3>U N I V E R S I T Y &nbsp; O F &nbsp; M O N T E M O R E L O S, &nbsp; A. C.</h3></td></tr>	 	    
	</table>
	<table style="width:30%" align="center" class="table table-sm table-fontsmall border">
	  <tr><th colspan="2"><spring:message code="portal.maestro.estadistica.RegistroMaterias"/></th></tr>
	  <tr> 
	    <td><b><spring:message code="portal.maestro.estadistica.Materias"/></b></td>
	    <td class="right"><b><%=numMaterias%></b></td>
	  </tr>    
	    <td><b><spring:message code="portal.maestro.estadistica.Inicial"/></b></td>
	    <td class="right"><b><% if (mapaEstados.containsKey(cargaId+"1")) out.println(mapaEstados.get(cargaId+"1")); else out.print("0");%></b></td>
	  </tr>  
	    <td><b><spring:message code="portal.maestro.estadistica.Evaluar"/></b></td>
	    <td class="right"><b><% if (mapaEstados.containsKey(cargaId+"2")) out.println(mapaEstados.get(cargaId+"2")); else out.print("0");%></b></td>
	  </tr>  
	    <td><b><spring:message code="portal.maestro.estadistica.Extra"/></b></td>
	    <td class="right"><b><% if (mapaEstados.containsKey(cargaId+"3")) out.println(mapaEstados.get(cargaId+"3")); else out.print("0");%></b></td>
	  </tr>  
	    <td><b><spring:message code="aca.Cerradas"/></b></td>
	    <td class="right"><b><% if (mapaEstados.containsKey(cargaId+"4")) out.println(mapaEstados.get(cargaId+"4")); else out.print("0");%></b></td>
	  </tr>  
	    <td><b><spring:message code="aca.Registradas"/></b></td>
	    <td class="right"><b><% if (mapaEstados.containsKey(cargaId+"5")) out.println(mapaEstados.get(cargaId+"5")); else out.print("0");%></b></td>
	  </tr>
	  </tr>  
	    <th><b><spring:message code="portal.maestro.estadistica.Progreso"/></b></th>
	    <th class="right"><b><%=avanceMat%>%</b></th>
	  </tr>
	</table>
	
	<div class="graficasInsc" style="width:700px; margin:auto;">
		<div class="grafic grafica1">
			<script>columnBasic('Subject Registration', 'For Professors','Enrolled', 'Number of subjects',[<%=materias%>], [<%=datos%>]);</script>
			<div id="inscritosColumn" style="min-width: 95%; height: 300px; margin: 0 auto"></div>
		</div>
	</div>
	<br>
	<table style="width:20%" align="center" class="table table-sm table-fontsmall border">
	  <tr><th colspan="2"><spring:message code="portal.maestro.estadistica.RegistroNotas"/></th></tr>
	  <tr> 
	    <td><b><spring:message code="portal.maestro.estadistica.Alumnos"/></b></td>
	    <td class="right"><b><%=numAlumnos%></b></td>
	  </tr>    
	    <td><b><spring:message code="aca.Inscritos"/></b></td>
	    <td class="right"><b><%=numInscritos%></b></td>
	  </tr>  
	    <td><b><spring:message code="portal.maestro.estadistica.Bajas"/></b></td>
	    <td class="right"><b><%=numBajas%></b></td>
	  </tr>  
	    <td><b><spring:message code="portal.maestro.estadistica.Acreditados"/></b></td>
	    <td class="right"><b><%=numAcreditados%></b></td>
	  </tr>  
	    <td><b><spring:message code="portal.maestro.estadistica.Reprobados"/></b></td>
	    <td class="right"><b><%=numReprobados%></b></td>
	  </tr>  
	    <td><b><spring:message code='aca.Pendientes'/></b></td>
	    <td class="right"><b><%=numPendientes%></b></td>
	  </tr>
	  </tr>  
	    <th><b><spring:message code="portal.maestro.estadistica.Progreso"/></b></th>
	    <th class="right"><b><%=getFormato.format(avanceNotas)%>%</b></th>
	  </tr>
	</table>	
	<div class="graficasAlum" style="width:700px; margin:auto;">
		<div class="grafic grafica2">
			<script>columnBasic('Grades Record', 'For Professors','studentColum', 'Student Number',[<%=alumnos%>], [<%=datosA%>]);</script>
			<div id="alumColums" style="min-width: 95%; height: 300px; margin: 0 auto"></div>
		</div>
	</div>

	<script type="text/javascript">
		(function($){
			var graficas = $('.graficasInsc').find('.grafic').hide();
			var grafica1 = $('.grafica1').show();
			$('.buttons').on('click','button',function(){
				graficas.hide();
				$this = $(this).attr('disabled', 'disabled').siblings().removeAttr('disabled').end();
				$('.'+$this.data('grafic')).fadeIn();
			})
		})(jQuery);
	
		(function($){
			var graficas = $('.graficasAlum').find('.grafic').hide();
			var grafica1 = $('.grafica2').show();
			$('.buttons').on('click','button',function(){
				graficas.hide();
				$this = $(this).attr('disabled', 'disabled').siblings().removeAttr('disabled').end();
				$('.'+$this.data('grafic')).fadeIn();
			})
		})(jQuery);
		
	</script>
	</div>
<!-- fin de estructura -->