<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>

<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"></jsp:useBean>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"></jsp:useBean>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"></jsp:useBean>
<jsp:useBean id="planU" scope="page" class="aca.alumno.PlanUtil"></jsp:useBean>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"></jsp:useBean>
<jsp:useBean id="catAptitud" scope="page" class="aca.catalogo.CatAptitud"></jsp:useBean>
<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"></jsp:useBean>
<jsp:useBean id="alumAptitud" scope="page" class="aca.alumno.AlumAptitud"></jsp:useBean>
<jsp:useBean id="alumAptitudU" scope="page" class="aca.alumno.AlumAptitudUtil"></jsp:useBean>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>
<%
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	String colorPortal 	= (String)session.getAttribute("colorPortal");
	String cargaId	 	= request.getParameter("carga");
	
	String residencia 	= aca.alumno.AcademicoUtil.getResidencia(conEnoc, matricula);
	String mensaje		= "";
	
	if(cargaId==null){
		cargaId = aca.carga.CargaUtil.getMejorCarga(conEnoc,matricula);
	}
	
	estadistica.mapeaRegId(conEnoc, matricula, cargaId);
	alumno = AlumUtil.mapeaRegId(conEnoc, matricula);
	catAptitud.mapeaRegId(conEnoc);
	if(alumAptitudU.existeReg(conEnoc, matricula, cargaId))
		alumAptitud.mapeaRegId(conEnoc, matricula, cargaId);
	
	ArrayList<String> planes			= planU.getPlanesAlumno(conEnoc, matricula);
	ArrayList<String> cargas 			= new ArrayList<String>();
	ArrayList<aca.carga.Carga> lisCarga = cargaU.getListAlumno(conEnoc, matricula);
	
	// Busca las cargas en donde el alumno tenga registros
	int i=0;
	for( i=0;i<lisCarga.size();i++){
		aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);

		if(alumAptitudU.existeReg(conEnoc, matricula, carga.getCargaId())){
			cargas.add(carga.getCargaId());				
		}
	}
	lisCarga 	= null;	
%>
<head>
	<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
</head>
<div class="container-fluid">
	<h2>Datos Aptitud Fisica <small class="text-muted fs-5">( <%=matricula %> - <%=alumno.getNombre() %> <%=alumno.getApellidoPaterno() %> <%=alumno.getApellidoMaterno() %> )</small></h2>
	<form id="forma" name="forma" action="aptitud" methdo="post">
	<input type="hidden" id="Accion" name="Accion" />	
	<div class="alert alert-info">		
		<b><spring:message code='aca.Carrera'/>:</b> [ <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, estadistica.getCarreraId()) %> ]&nbsp;&nbsp;
		<b><spring:message code='aca.Residencia'/>:</b> [ <%=residencia.equals("E")?"Externo":"Interno" %> ]&nbsp;&nbsp;	
		<b><spring:message code="aca.Edad"/>:</b> [ <%=aca.alumno.AlumUtil.getEdad(conEnoc, matricula) %> ]&nbsp;&nbsp;
		<b><spring:message code='aca.Genero'/>:</b> [ <%=alumno.getSexo().equals("M")?"Masculino":"Femenino" %> ]&nbsp;&nbsp;
		<!-- img height="50px" src="../../imagenes/LogoAF.png" /-->
	</div>	
	<table class="table table-condensed" style="width:100%">
		<tr>
			<th colspan="20">Estudio Comparativo de la Composición Corporal</th>
		</tr>
		<tr>
			<th>#</th>
			<th>Periodo</th>
			<th><spring:message code='aca.Talla'/></th>
			<th><spring:message code='aca.Peso'/></th>
			<th>Peso Ideal</th>
			<th><spring:message code='aca.IMC'/></th>
			<th>Diagnostico</th>
			<th>%Grasa Corporal</th>
			<th>Abdomen</th>
			<th>Dieta</th>
			<th><spring:message code="aca.Recomendaciones"/></th>
		</tr>
<%
	float ganancia = 0;
	for(int j=0; j<cargas.size(); j++){
		alumAptitud.mapeaRegId(conEnoc, matricula, cargas.get(j));
		String nombreCarga = aca.carga.CargaUtil.getNombreCarga(conEnoc,cargas.get(j));
		
		int talla = Integer.parseInt(alumAptitud.getTalla());
		double pesoIdeal = ((talla-150)*0.75);
		if(alumno.getSexo().equals("M")){
			pesoIdeal+=50;
		}else{
			pesoIdeal+=45;
		}
		
		String strPesoIdeal = (pesoIdeal-5)+" - "+(pesoIdeal+5);
		
		String diagnostico = "";
		float imc = Float.parseFloat(alumAptitud.getImc());
		if(imc==0){
			diagnostico="-";
		}else if(imc>Float.parseFloat(catAptitud.getImcMinBajom()) && imc<=Float.parseFloat(catAptitud.getImcMaxBajom())){
			diagnostico="Bajo Peso";
		}else if(imc>=Float.parseFloat(catAptitud.getImcMinNormalm()) && imc<=Float.parseFloat(catAptitud.getImcMaxNormalm())){
			diagnostico="Normal";
		}else if(imc>=Float.parseFloat(catAptitud.getImcMinSobrem()) && imc<=Float.parseFloat(catAptitud.getImcMaxSobrem())){
			diagnostico="Sobrepeso";
		}else if(imc>=Float.parseFloat(catAptitud.getImcMinObeso1m()) && imc<=Float.parseFloat(catAptitud.getImcMaxObeso1m())){
			diagnostico="Obesidad";
		}else if(imc>=Float.parseFloat(catAptitud.getImcMinObeso2m()) && imc<=Float.parseFloat(catAptitud.getImcMaxObeso2m())){
			diagnostico="Obesidad Mórbida";
		}else if(imc>=Float.parseFloat(catAptitud.getImcMinObeso3m()) && imc<=Float.parseFloat(catAptitud.getImcMaxObeso3m())){
			diagnostico="Deshabilitado";
		}
		
		String dieta = alumAptitud.getDieta()==null?"-":alumAptitud.getDieta();
		if(dieta.equals("1"))dieta="No Vegetariano";
		else if(dieta.equals("2"))dieta="Vegetariano";
		
		
		if(cargas.size()>=2){
			alumAptitud.mapeaRegId(conEnoc, matricula, cargas.get(cargas.size()-2));
			float peso1 = Float.parseFloat(alumAptitud.getPeso());
			alumAptitud.mapeaRegId(conEnoc, matricula, cargas.get(cargas.size()-1));
			float peso2 = Float.parseFloat(alumAptitud.getPeso());
			ganancia = peso2-peso1;
		}

%>
		<tr>
			<td><%=j+1%></td>
			<td>[<%=cargas.get(j)%>] <%=nombreCarga%></td>
			<td><%=talla%></td>
			<td><%=alumAptitud.getPeso()%></td>
			<td><%=pesoIdeal%></td>
			<td><%=alumAptitud.getImc()%></td>			
			<td align="right"><%=diagnostico %></td>
			<td align="right"><%=alumAptitud.getGrasa() %></td>
			<td align="right"><%=alumAptitud.getAbdomen() %></td>
			<td align="right"><%=dieta%></td>			
			<td align="right">
		<%if(imc<25){ %>
			<a href="recomendaciones/Recomendaciones1.pdf" target="_blank"><font size="2">Leer</font>&nbsp;</a>
		<%}else{ %>
			<a href="recomendaciones/Recomendaciones2.pdf" target="_blank"><font size="2">Leer</font>&nbsp;</a>
			</td>
		<%} %>
		</tr>		
<%		
	}
%>
	</table>
	
	<div class="alert-info"><h3>Ganancia (Kg): <%=getformato.format(ganancia).replaceAll(",",".") %></h3></div>
	<br>
	<table style="width:100%" class="table table-condensed">
		<tr>
			<th colspan = "3" align="center" >Valores de referencia IMC (&Iacute;ndice de Masa Corporal)</th>
		</tr>	
		<tr>
			<th width="40%">Clasificación</th>
			<th width="30%">Hombre</th>
			<th width="30%">Mujer</th>
		</tr>
		<tr>
			<td>Bajo Peso</td>
			<td align="center"><%=catAptitud.getImcMinBajom() %> < <%=catAptitud.getImcMaxBajom() %></td>
			<td align="center"><%=catAptitud.getImcMinBajof() %> < <%=catAptitud.getImcMaxBajof() %></td>
		</tr>
		<tr>
			<td>Normal</td>
			<td align="center"><%=catAptitud.getImcMinNormalm() %> - <%=catAptitud.getImcMaxNormalm() %></td>
			<td align="center"><%=catAptitud.getImcMinNormalf() %> - <%=catAptitud.getImcMaxNormalf() %></td>
		</tr>
		<tr>
			<td>Sobrepeso</td>
			<td align="center"><%=catAptitud.getImcMinSobrem() %> - <%=catAptitud.getImcMaxSobrem() %></td>
			<td align="center"><%=catAptitud.getImcMinSobref() %> - <%=catAptitud.getImcMaxSobref() %></td>
		</tr>
		<tr>
			<td>Obesidad Tipo I</td>
			<td align="center"><%=catAptitud.getImcMinObeso1m() %> - <%=catAptitud.getImcMaxObeso1m() %></td>
			<td align="center"><%=catAptitud.getImcMinObeso1f() %> - <%=catAptitud.getImcMaxObeso1f() %></td>
		</tr>
		<tr>
			<td>Obesidad Tipo II</td>
			<td align="center"><%=catAptitud.getImcMinObeso2m() %> - <%=catAptitud.getImcMaxObeso2m() %></td>
			<td align="center"><%=catAptitud.getImcMinObeso2f() %> - <%=catAptitud.getImcMaxObeso2f() %></td>
		</tr>
		<tr>
			<td>Obesidad Tipo III</td>
			<td align="center"><%=catAptitud.getImcMinObeso3m() %> - <%=catAptitud.getImcMaxObeso3m() %></td>
			<td align="center"><%=catAptitud.getImcMinObeso3f() %> - <%=catAptitud.getImcMaxObeso3f() %></td>
		</tr>
	</table>		
	<br>
	<table style="width:100%" class="table table-condensed">
		<tr>
			<th colspan="3">Valores de referencia en porcentaje de grasa corporal</th>
		</tr>
		<tr>
			<th width="40%">Clasificación</th>
			<th width="30%">Hombre</th>
			<th width="30%">Mujer</th>
		</tr>
		<tr>
			<td>Aceptable</td>
			<td align="center"><%=catAptitud.getGrasaMinAceptablem() %> - <%=catAptitud.getGrasaMaxAceptablem() %></td>
			<td align="center"><%=catAptitud.getGrasaMinAceptablef() %> - <%=catAptitud.getGrasaMaxAceptablef() %></td>
		</tr>
		<tr>
			<td>Sobrepeso</td>
			<td align="center"><%=catAptitud.getGrasaMinSobrem() %> - <%=catAptitud.getGrasaMaxSobrem() %></td>
			<td align="center"><%=catAptitud.getGrasaMinSobref() %> - <%=catAptitud.getGrasaMaxSobref() %></td>
		</tr>
		<tr>
			<td>Obesidad</td>
			<td align="center"><%=catAptitud.getGrasaMinObesom() %> - <%=catAptitud.getGrasaMaxObesom() %></td>
			<td align="center"><%=catAptitud.getGrasaMinObesof() %> - <%=catAptitud.getGrasaMaxObesof() %></td>
		</tr>
	</table>	
	<br>	
	<div class="alert-info"><h3>Abdomen Riesgo: [ Hombres mayor o igual a <%=catAptitud.getAbdomenRiesgom() %>cm ] &nbsp; - &nbsp; [ Mujeres mayor o igual a <%=catAptitud.getAbdomenRiesgof() %>cm ]</h3></div>							
	<br>
	<table class="table table-condensed" style="width:100%">	
		<tr>
			<th colspan="4"><b>Resultados de la Prueba</b></th>			
		</tr>
		<tr>
			<th width="20%">Prueba</th>
			<th width="20%">Resultado</th>
			<th width="30%">Hombres(Valor de referencia)</th>
			<th width="30%">Mujeres(Valor de referencia)</th>
		</tr>
		<tr class="tr2">
			<td>Fuerza (Lagartijas):</td>
			<td align="center"><%=alumAptitud.getFuerza() %></td>
			<td align="center"><%=catAptitud.getFuerzam() %></td>
			<td align="center"><%=catAptitud.getFuerzaf() %></td>
		</tr>
		<tr class="tr2">
			<td>Flexibilidad:</td>
			<td align="center"><%=alumAptitud.getFlexibilidad() %></td>
			<td align="center"><%=catAptitud.getFlexibilidadm() %></td>
			<td align="center"><%=catAptitud.getFlexibilidadf() %></td>
		</tr>
		<tr class="tr2">
			<td>Resistencia (Abdominales):</td>
			<td align="center"><%=alumAptitud.getResistencia() %></td>
			<td align="center"><%=catAptitud.getResistenciam() %></td>
			<td align="center"><%=catAptitud.getResistenciaf() %></td>
		</tr>
		<tr class="tr2">
			<td>Cardiorespiratoria (Carrera):</td>
			<td align="center"><%=alumAptitud.getCardio() %></td>
			<td align="center"><%=catAptitud.getCardiom() %></td>
			<td align="center"><%=catAptitud.getCardiof() %></td>
		</tr>
	</table>			
</form>
</div>
<script>
	$('.nav-tabs').find('.apFisica').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>