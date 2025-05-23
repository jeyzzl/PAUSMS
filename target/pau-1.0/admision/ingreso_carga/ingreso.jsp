<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil" />
<jsp:useBean id="Personal" scope="page" class="aca.alumno.AlumPersonal" />
<jsp:useBean id="FesCcobroUtil" scope="page" class="aca.financiero.FesCcobroUtil" />
<jsp:useBean id="CatModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="Acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>

<%@page import="java.util.HashMap"%>

<script>
	function Mostrar(){
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	
	if(cargas.equals(""))cargas="''";
	if(modalidades.equals(""))modalidades="''";
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	} 
	
	String primerIng				= request.getParameter("primerIng")==null?"um":request.getParameter("primerIng");

	int con							= 0;
	int nInternos					= 0;
	int nExternos					= 0;
	int nHombres					= 0;
	int nMujeres					= 0;
	
	String condicion 				= "";
	
	// Busca los privilegios del usuario
	Acceso = AccesoU.mapeaRegId(conEnoc, codigoPersonal);	
	
	// Determina la condición de nuevo ingreso a la UM, Plan o ambos.
	if (primerIng.toUpperCase().equals("UM")){
		condicion = " TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_UM(MATRICULA) AND MATEO.FES_CCOBRO.FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
	}else if (primerIng.toUpperCase().equals("PLAN") ){
		condicion = "TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID) AND TO_CHAR(FECHA,'DD/MM/YYYY') != ENOC.ALUM_INGRESO_UM(MATRICULA) AND MATEO.FES_CCOBRO.FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
	}else if (primerIng.toUpperCase().equals("UMFORMAL") ){
		condicion = "TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_UM_FORMAL(MATRICULA) AND MATEO.FES_CCOBRO.FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
	}else if (primerIng.toUpperCase().equals("PLANFORMAL") ){
		condicion = "TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID) AND TO_CHAR(FECHA,'DD/MM/YYYY') != ENOC.ALUM_INGRESO_UM_FORMAL(MATRICULA) AND MATEO.FES_CCOBRO.FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
	}else if (primerIng.toUpperCase().equals("PLANUM") ){
		condicion = "TO_CHAR(FECHA,'DD/MM/YYYY') = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID) AND MATEO.FES_CCOBRO.FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')";
	} 
	
	Carga = CargaU.mapeaRegId(conEnoc,(String)session.getAttribute("cargaId"));
	String lisModo[] 		= modalidades.replace("'", "").split(",");
	
	ArrayList<aca.financiero.FesCcobro> listaFesCcobro = FesCcobroUtil.getListAll(conEnoc, "WHERE CARGA_ID IN ("+cargas+") AND "+condicion+" AND INSCRITO='S' AND TRIM(TO_CHAR(MODALIDAD_ID,'99')) IN ("+modalidades+") AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL=MATRICULA AND CARGA_ID = (MATEO.FES_CCOBRO.CARGA_ID) AND ESTADO IN('I','3'))");
	
	HashMap<String, aca.alumno.AlumPersonal> mapaAlumPersonal = AlumUtil.getMapAllCargaAlumEstado(conEnoc, cargas.substring(1, cargas.length()-1));	
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<div class="container-fluid">
	<h2>First Year Enrolled Students</h2>
	<form id="forma" name="forma" action="ingreso?Accion=1" method="post">
	<div class="alert alert-info">
		<b>Loads:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<b>Modalities:</b>
<%
			for(String mod:lisModo){
				String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
				out.print("["+nombreModalidad+"] ");	
			}		
%>
			<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
	</div>
	<div class="alert alert-info d-flex align-items-center">
		Start Date: <input id="FechaIni" name="FechaIni" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px"/>&nbsp;&nbsp;
		End Date: <input id="FechaFin" name="FechaFin" type="text" class="form-control" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px"/>&nbsp;&nbsp;
		First Year: 
		<select name="primerIng" class="form-select" onChange="forma.submit();" style="width:150px;">
			<option value="um" <%=primerIng.equals("um")?"Selected":""%>>UM</option>
			<option value="plan" <%=primerIng.equals("plan")?"Selected":""%>><spring:message code="aca.Plan"/></option>
			<option value="umformal" <%=primerIng.equals("umformal")?"Selected":""%>>UM Formal</option>
			<option value="planformal" <%=primerIng.equals("planformal")?"Selected":""%>><spring:message code="aca.Plan"/> Formal</option>
			<option value="planUM" <%=primerIng.equals("planUM")?"Selected":""%>>Plan y UM</option>
		</select>
		&nbsp;&nbsp;			
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>	
	<table class="table table-bordered table-sm table-striped">
	<thead class="table-info">  	
	<tr> 
		<th><h4><spring:message code="aca.Numero"/></h4></th>
		<th><h4><spring:message code="aca.Matricula"/></h4></th>
		<th><h4><spring:message code="aca.Nombre"/></h4></th>
		<th><h4><spring:message code="aca.Apellidos"/></h4></th>
		<th><h4><spring:message code="aca.FechaNac"/></h4></th>
		<th><h4><spring:message code="aca.Genero"/></h4></th>
		<th><h4><spring:message code="aca.Religion"/></h4></th>
		<th><h4><spring:message code="aca.Residencia"/></h4></th>
		<th><h4><spring:message code="aca.Dormitorio"/></h4></th>
		<th><h4><spring:message code="aca.Tipo"/></h4></th>
		<th><h4><spring:message code="aca.Carga"/></h4></th>
		<th><h4><spring:message code="aca.Modalidad"/></h4></th>
		<th><h4><spring:message code="aca.Plan"/></h4></th>
		<th><h4><spring:message code="aca.Facultad"/></h4></th>
		<th><h4><spring:message code="aca.Carrera"/></h4></th>		    
		<th><h4><spring:message code="aca.Inscrito"/></h4></th>
	</tr>
	</thead>
	<tbody>
<%	
	int i = 0;
	for(i=0; i<listaFesCcobro.size(); i++){
		
		aca.financiero.FesCcobro fesCcobro = (aca.financiero.FesCcobro)listaFesCcobro.get(i);
		
		if( Acceso.getAccesos().indexOf(fesCcobro.getCarreraId())!= -1 || Acceso.getAdministrador().equals("S") || Acceso.getSupervisor().equals("S") ){
		
			con++;			
			String genero 			= mapaAlumPersonal.get(fesCcobro.getMatricula()).getSexo();
			String alumnoNombre		= mapaAlumPersonal.get(fesCcobro.getMatricula()).getNombre();
			String alumnoApellidos	= mapaAlumPersonal.get(fesCcobro.getMatricula()).getApellidoPaterno()+" "+mapaAlumPersonal.get(fesCcobro.getMatricula()).getApellidoMaterno();				
%>
	<tr> 
	    <td><%=con%></td>
	    <td><font size="1"><%=fesCcobro.getMatricula()%></font></td>
	    <td><font size="1"><%=alumnoNombre%></font></td>
	    <td><font size="1"><%=alumnoApellidos%></font></td>
	    <td><font size="1"><%=aca.alumno.AlumUtil.getFNacimiento(conEnoc,fesCcobro.getMatricula())%></font></td>
	    <td align="center"><font size="1"><%=genero%></font></td>
	    <td align="center"><font size="1"><%= aca.catalogo.ReligionUtil.getNombreReligion(conEnoc, fesCcobro.getReligion())%></font></td>
	    <td align="center"><font size="1"><%=fesCcobro.getResidencia().equals("E")?"Externo":"Interno"%></font></td>
	    <td align="center"><font size="1"><%=fesCcobro.getDormitorio() %></font></td>
	    <td><font size="1"><%=fesCcobro.gettAlumno()%></font></td>
	    <td align="center"><font size="1"><%=fesCcobro.getCargaId()%></font></td>
	    <td align="center"><font size="1"><%=fesCcobro.getModalidad()%></font></td>
	    <td align="center"><font size="1"><%=fesCcobro.getPlanId()%></font></td>
	    <td align="left"><font size="1"><%=fesCcobro.getFacultad() %></font></td>			    
	    <td align="left"><font size="1"><%=fesCcobro.getCarrera() %></font></td>
	    <td align="left"><font size="1"><%=fesCcobro.getFecha() %></font></td>
	</tr>
<% 			if (fesCcobro.getResidencia().equals("I")){ nInternos++; }else{ nExternos++; }
			if (genero.equals("M")){nHombres++; }else{ nMujeres++; }
		}
	}
%>
	</tbody>
	</table>	
    <table style="margin: 0 auto; width:100%;" class="table table-sm table-bordered">
	<tr> 
		<th  colspan="1">Enrolled:<%=i%></th>
		<th  colspan="1">On-Campus: <%=nInternos%></th>
		<th  colspan="1">Day Students: <%=nExternos%></th>
		<th  colspan="1">Men:  <%=nHombres%></th>
		<th  colspan="1">Women:	<%=nMujeres%></th>
	</tr>
	</table>    
</div>
<script>
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>
<%@ include file = "../../cierra_enoc.jsp"%>