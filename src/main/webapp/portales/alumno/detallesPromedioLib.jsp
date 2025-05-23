<%@page import="aca.catalogo.CatGradePoint"%>
<%@page import="java.util.HashMap"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.DecimalFormat"%>

<jsp:useBean id="AlumnoCursoUtil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="GPU" scope="page" class="aca.catalogo.CatGradePointUtil"/>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<html>	
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String matricula 		= session.getAttribute("codigoAlumno").toString();	
	String planId 			= request.getParameter("PlanId");

	AlumPlan.mapeaRegId(conEnoc, matricula, planId);
	String semTetra 		= AlumUtil.getCarreraId(conEnoc, AlumPlan.getPlanId()).substring(0,3).equals("107")?"Tetra.":"Sem.";
	String escala 			= AlumPlan.getEscala();	
	float promedio 			= 0;
	int row=0;
	


	ArrayList<aca.catalogo.CatGradePoint> lisGP		= GPU.getListAll(conEnoc, ""); 
	
	ArrayList<aca.vista.AlumnoCurso> listaCursos 		= AlumnoCursoUtil.getListAll(conEnoc, "WHERE CODIGO_PERSONAL='"+matricula+"' AND PLAN_ID='"+planId+"' AND TIPOCAL_ID IN ('1')"
																+" AND (SELECT TIPOCURSO_ID FROM ENOC.MAPA_CURSO WHERE CURSO_ID=ALUMNO_CURSO.CURSO_ID) IN ('1', '2', '7','9')"
																+" AND CONVALIDACION IN ('N', 'I') AND ((NOTA!=0 OR CONVALIDACION='S' OR CREDITOS>0) OR NOTA_EXTRA!=0)"
																+" ORDER BY CICLO");
																// +" ORDER BY CICLO, (SELECT COALESCE(ORDEN,0) FROM ENOC.CERT_CURSO WHERE CURSO_ID = CERT_CURSOID(ALUMNO_CURSO.CURSO_ID)), NOMBRE_CURSO");
	HashMap<String, String> map = new HashMap<String, String>();
	int inicio = 0, fin = 0;
	for(aca.catalogo.CatGradePoint lista : lisGP){
		inicio = Integer.parseInt(lista.getInicio());
		fin = Integer.parseInt(lista.getFin());
		for(int i = inicio; i<=fin; i++){
			map.put(Integer.toString(i), lista.getPuntos());
		}
	}
%>
<body>
<div class="container-fluid">
	<h2>Cálculo del promedio ponderado<small class="text-muted fs-5"> ( <%=matricula%> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc,matricula,"NOMBRE") %> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="calificaciones"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;
	</div>
	<table class="table table-fullcondensed table-bordered table-fontsmall">
		<tr>
			<th>#</th>
			<th><%=semTetra%></th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Nombre"/> curso</th>
			<th class="right">Crédtios</th>
			<th class="right">Puntos</th>
			<th class="right">Créditos<br>por Puntos</th>
		</tr>
<%	
	float sumaCreditos = 0;
	float sumaCreditosPorNota = 0;
	float sumaPuntos	= 0;
	for(aca.vista.AlumnoCurso alumnoCurso : listaCursos){
		String notas = "";
		float nota 				= Float.parseFloat(alumnoCurso.getNota());
		float nota2 			= Float.parseFloat(alumnoCurso.getNota());
		float extra 			= Float.parseFloat(alumnoCurso.getNotaExtra());
		float creditos 			= Float.parseFloat(alumnoCurso.getCreditos());
		float creditosPorNota 	= extra==0?nota*creditos:extra*creditos;
		
		sumaCreditos+=creditos;
		sumaCreditosPorNota+=creditosPorNota;
		row++;
		
		if(escala.equals("10")){
			nota 	= Float.parseFloat(alumnoCurso.getNota())/10;
			extra	= Float.parseFloat(alumnoCurso.getNotaExtra())/10;
		}
		String puntos = "0";
		notas = Float.toString(nota2).substring(0,Float.toString(nota2).indexOf("."));
		if(map.containsKey(notas)){
			puntos = map.get(notas);
		}
		 sumaPuntos +=  creditos*Float.parseFloat(puntos);
%>
		<tr>
			<td style="text-align:center; font-weight:bold;"><%=row%></td>
			<td style="text-align:center;"><%=alumnoCurso.getCiclo()%></td>
			<td><%=alumnoCurso.getCursoId()%></td>
			<td><%=alumnoCurso.getNombreCurso()%></td>
			<td style="text-align:right;"><%=alumnoCurso.getCreditos()%></td>
			<td style="text-align:right;"><%=puntos%></td>
			<td style="text-align:right;"><%=getFormato.format(creditos*Float.parseFloat(puntos))%></td>
		</tr>
<%	}
			
	if(escala.equals("10")) promedio = (sumaCreditosPorNota/sumaCreditos)/10;
	else promedio = sumaCreditosPorNota/sumaCreditos;
%>
		<tr>
			<th colspan="4">Totales</th>
			<th class="right"><b><%=sumaCreditos%></b></th>
			<th >&nbsp;</th>
			<th class="right"><b><%=getFormato.format(sumaPuntos)%></b></th>
		</tr>
	</table>
	<table style="margin: 0 auto;" class="tabla">
	  	<tr><td align="center"><font size="3" color="green"><b>Fórmula: &sum;(Créditos*Nota) / &sum;(Créditos)</b></font></td></tr>
	  	<tr><td>&nbsp;</td></tr>
	  	<tr>
	  		<td style="font-size:17px;" align="center">
	  			<b>
	  				<u>(<%=getFormato.format(sumaPuntos)%>)</u>
	  				<font style="position:relative;top:7;"> = 
	  					<font color="green" size="4"><%=getFormato.format(sumaPuntos/sumaCreditos)%><font size="1" color="black"> Promedio</font></font></font><br>
	  				<font style="position:relative;left:-50;"><%=getFormato.format(sumaCreditos)%></font>
	  			</b>
	  		</td>
	  	</tr>
	</table>		
	</body>
</html>
<script>
	$('.nav-tabs').find('.notas').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>