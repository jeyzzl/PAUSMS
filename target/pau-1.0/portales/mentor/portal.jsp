<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@page import="aca.mentores.spring.MentContacto"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.alumno.spring.AlumActualiza"%>
<%@page import="aca.financiero.spring.FinSaldo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<script>
	function refrescar(){
		document.frmMentor.submit();
	}	
</script>
<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00; -###,##0.00");

	String codigoMentor 			= (String) session.getAttribute("codigoPersonal");
	String periodoId 				= (String) request.getAttribute("periodoId");
	String alertaPeriodoId 			= (String) request.getAttribute("alertaPeriodoId");
	String mentorNombre 			= (String) request.getAttribute("mentorNombre");	
	String fecha	 				= request.getParameter("Fecha")==null?(String)session.getAttribute("fecha"):request.getParameter("Fecha");
	
	String disciplina				= "";
	String inscrito					= "";
	String academico				= "";
	String financiero				= "";
	String codigoAlumno				= "";

	String sBgcolor					= "";
	int credito						= 0;
	int numReporte					= 0;
	int cargo						= 0;
	int contador					= 0;
	int i 							= 0;

	List<CatPeriodo> lisPeriodos					= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<MentAlumno> lisAlumnos 					= (List<MentAlumno>) request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaConducta				= (HashMap<String,String>) request.getAttribute("mapaConducta");
	HashMap<String,String> mapaInscritos			= (HashMap<String,String>) request.getAttribute("mapaInscritos");
	HashMap<String,AlumActualiza> mapaActualiza		= (HashMap<String,AlumActualiza>) request.getAttribute("mapaActualiza");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaCargas				= (HashMap<String,String>) request.getAttribute("mapaCargas");
	HashMap<String,String> mapaPromedios			= (HashMap<String,String>) request.getAttribute("mapaPromedios");
	HashMap<String,String> mapaAvances				= (HashMap<String,String>) request.getAttribute("mapaAvances");
	HashMap<String,FinSaldo> mapaSaldos				= (HashMap<String,FinSaldo>) request.getAttribute("mapaSaldos");
	HashMap<String,String> mapaEntrevistas			= (HashMap<String,String>) request.getAttribute("mapaEntrevistas");
	HashMap<String,String> mapaAlumCovid 			= (HashMap<String,String>)request.getAttribute("mapaAlumCovid");
	HashMap<String,String> mapDocAlumPorPeriodo 	= (HashMap<String,String>)request.getAttribute("mapDocAlumPorPeriodo");
%>
<div class="container-fluid">
	<h2>Mentor Portal&nbsp;<small class="text-muted fs-5">( <%=mentorNombre%>)</small></h2>
	<form name="frmMentor" action="portal" target="_self">
	<div class="alert alert-info d-flex align-items-center">
		Cycle:&nbsp;
	    <select id="PeriodoId" class="form-select" name="PeriodoId" style="width:200px;" onchange="javascript:refrescar()">
<% 		for(CatPeriodo periodo : lisPeriodos){%>
	       	<option value="<%=periodo.getPeriodoId()%>" <% if (periodoId.equals(periodo.getPeriodoId())) out.print("Selected");%> ><%=periodo.getNombre()%></option>
<% 		}%>
	    </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Active in date:&nbsp; 
		<input type="text" id="Fecha" name="Fecha" class="input form-control" style="width:120px;" style="margin:0;" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fecha%>" />&nbsp;
		<a class="btn btn-primary btn-small" onclick="javascript:refrescar();"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<table class="table table-stripped table-sm table-fontsmall">
  	<tr>
    	<td colspan="23">    		
	  		[ <a href="editarEntrevista?PeriodoId=<%=periodoId%>"><b>Interview a student</b></a> ]&nbsp;&nbsp;
	  		[ <a href="entrevistas?PeriodoId=<%=periodoId%>"><b>All Interviews</b></a> ]&nbsp;&nbsp;
	  		[ <a href="rep_mentor_alumno_foto?PeriodoId=<%=periodoId%>"><b>Pictures</b></a> ]
		</td>
  	</tr>
  	<tr>
  		<th>#</th>
		<th>Data</th>
		<th>Portal</th>
		<th>Student ID</th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Return</th>
		<th title="Inscrito">Enr.</th>
		<th class="left">#Int.</th>		
		<th class="right"><spring:message code="aca.FechaInicio"/></th>
		<th class="right"><spring:message code='aca.FechaFinal'/></th>
		<th class="right">Balance</th>
		<th><spring:message code="aca.Carga"/></th>
		<th class="right">Avrg.</th>
		<th class="left">Progress</th>
		<th class="right">Units</th>
		<th class="right">Praises</th>
		<th class="right">Updated</th>
				
  	</tr>
<%  //ALUMNOS ASIGNADOS A MENTORIA
	
	int row=0;
	for( MentAlumno mental : lisAlumnos){
		row++;
		
		codigoAlumno	= mental.getCodigoPersonal();		
		credito			= 0;
		if (mapaConducta.containsKey(codigoAlumno+"C")){
			credito 	= Integer.parseInt(mapaConducta.get(codigoAlumno+"C"));
		}
		
		cargo 	= 0;
		if (mapaConducta.containsKey(codigoAlumno+"D")){
			cargo = Integer.parseInt(mapaConducta.get(codigoAlumno+"D"));
		}
		
		String textoCredito = "<span class='badge bg-dark'>"+credito+"</span>";
		if (credito>0) textoCredito = "<span class='badge bg-success'>"+credito+"</span>";
		
		String textoCargo = "<span class='badge bg-dark'>"+credito+"</span>";
		if (cargo>0) textoCargo = "<span class='badge bg-warning'>"+cargo+"</span>";
		
		disciplina = "";
		if (cargo >= credito){
			numReporte = cargo - credito;
			if(numReporte >= 7){	disciplina="red";	}
			else if(numReporte >= 1 && numReporte < 7){	disciplina="yellow";}
			else if (numReporte < 1){		disciplina="green";		}
			numReporte = 0;
		}
		
		//ESTADO DE INSCRITOS
		if(mapaInscritos.containsKey(codigoAlumno)){ 
			inscrito="YES";
		}else{
			inscrito="NO";		
		}
		
		String cargaAlumno 	= mental.getCargaId();
		String cargaIcono	= "<i class='fas fa-check-circle' ></i>";
		if(cargaAlumno.equals("000000")){						
			if (mapaCargas.containsKey(codigoAlumno)){
				cargaAlumno = mapaCargas.get(codigoAlumno);
			}
			cargaIcono	= "<i class='fas fa-circle' ></i>";
		}
		
		String avance 		= "0";
		String textoAvance	= "-";
		if (mapaAvances.containsKey(mental.getCodigoPersonal()+cargaAlumno)){
			avance = mapaAvances.get(mental.getCodigoPersonal()+cargaAlumno);
		}
		if (Double.valueOf(avance) >= 100){			
			textoAvance = "progress-bar-success";
		}else if (Double.valueOf(avance) >= 40 && Double.valueOf(avance) < 100){		
			textoAvance = "progress-bar-warning";
		}else{		
			textoAvance = "progress-bar-danger";
		}
		
		double numPromedio = 0;
		String promedio = "0";
		if (mapaPromedios.containsKey(codigoAlumno+cargaAlumno)){			
			promedio = mapaPromedios.get(codigoAlumno+cargaAlumno);
			numPromedio = Double.valueOf(promedio);
		}
		
		if (numPromedio == -1) academico = "white";
		if (numPromedio >=0 && numPromedio < 70) academico="red";
		if (numPromedio >= 70 && numPromedio < 85) academico="yellow";
		if(numPromedio >= 85){ academico="green"; }		
		
		//ESTADO FINANCIERO
		financiero="green";		
		String infoSaldo = "";
		double saldo = 0;
		if (mapaSaldos.containsKey(codigoAlumno)){
			saldo = Double.valueOf(mapaSaldos.get(codigoAlumno).getSaldoVencido());
		}
		
		if(saldo>0) infoSaldo = "<span class='label label-warning'>Db</span>"; else infoSaldo = "<span class='label label-success'>Cr</span>";
		String strSaldo		= getFormato.format((saldo*-1))+" "+infoSaldo;
		
		if(saldo > 0 && saldo <= 3000){
			financiero="yellow";		
		}else if(saldo > 3000){
			financiero="red";		
		}
		
		String actualiza = "";	
		
		if( mapaActualiza.containsKey(codigoAlumno)){		
			actualiza = mapaActualiza.get(codigoAlumno).getFecha();
		}else{
			actualiza = "Never"; 
		}	
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(codigoAlumno)){
			alumnoNombre = mapaAlumnos.get(codigoAlumno);			
		}
		
		String numEntrevista = "<span class='label label-warning'>0</span>";
		if (mapaEntrevistas.containsKey(codigoAlumno)){
			numEntrevista = "<span class='label label-success'>"+mapaEntrevistas.get(codigoAlumno)+"</span>";			
		}	
		String colorRetorno = "style='color:red'";
		if (mapaAlumCovid.containsKey(codigoAlumno)){
			colorRetorno = "style='color:black'";
		}

		boolean cartaResponsiva = false;
		if (mapDocAlumPorPeriodo.containsKey(codigoAlumno+3)){
			cartaResponsiva = true;
		}
%>
	<tr>	
		<td align="right"><span class="badge bg-success"><%=row%></span></td>
    	<td align="center"><a href="mentor_opciones?matricula=<%=codigoAlumno%>" class="ayuda alumno <%=codigoAlumno%>"><i class="fas fa-edit"></i></a></td>
  		<td align="center"><a href="subir?f_codigo_personal=<%=codigoAlumno%>" title="View Student Portal" target="_blank"><i class="fas fa-user"></i></a></td>
		<td align="center"><%=codigoAlumno%></td>
		<td><a href="entrevistas_alumno?CodigoAlumno=<%=codigoAlumno%>" title="View interview records"><%=alumnoNombre%></a> </td>
		<td>
	<%	if (!alertaPeriodoId.equals("0")){%>
			<a href="retorno?CodigoAlumno=<%=codigoAlumno%>&PeriodoId=<%=alertaPeriodoId%>" title="Ficha de retorno"><i class="fas fa-pencil-alt" <%=colorRetorno%>></i></a>
	<%	}else{%>
			<i class="fas fa-ban"></i>
	<%	}%>	
	<%	if (cartaResponsiva){%>
			<a href="descargaCartaResponsiva?CodigoAlumno=<%=codigoAlumno%>&PeriodoId=<%=alertaPeriodoId%>" title="Responsive Letter"><i class="fas fa-file-download" <%=colorRetorno%>></i></a>
	<%	}%>	
		</td>
    	<td class="ayuda mensaje ¿Está inscrito?"><%=inscrito%></td>
    	<td class="left"><%=numEntrevista%></td>    	
    	<td class="right"><%=mental.getFechaInicio() %></td> 
    	<td class="right"><%=mental.getFechaFinal() %></td>
    	<td class="right"><%=strSaldo.replace("+"," ")%></td>
    	<td class="left"><a href="elegirCarga?codigoAlumno=<%=codigoAlumno%>&folio=<%=mental.getFolio()%>&CargaId=<%=mental.getCargaId()%>"><%=cargaIcono%><%=cargaAlumno%></a></td>
    	<td class="right"><%=getFormato.format(numPromedio)%></td>
    	<td>
    		<div class="progress progress-striped">
  				<div class="progress-bar <%=textoAvance%>" role="progressbar" aria-valuenow="<%=avance%>" aria-valuemin="0" aria-valuemax="100" style="width: <%=avance%>%">
  				<span class="sr-only"><%=avance%>% Evaluated</span>
  			 	</div>
  			 	<%=avance%>%
			</div>
    	</td>
    	<td class="right"><a href="unidad?codigoAlumno=<%=codigoAlumno%>" title="Units are a resource for disapproving a students discipline"><%=textoCargo%></a></td>
    	<td class="right"><a href="unidad?codigoAlumno=<%=codigoAlumno%>" title="Praises are a resource for approving a students discipline"><%=textoCredito%></a></td>  
    	<td class="right"><%=actualiza%></td>    	    
  	</tr>
<%}%>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">		
		jQuery('#Fecha').datepicker();
</script>