<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="maestro" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumEstado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"/>

<%
	String cargaId = request.getParameter("cargaId");
	String carreraId = request.getParameter("carreraId");
	String nombreCarrera = request.getParameter("nombreCarrera");

	int nbInicio=0;
	int nbFinal=0;
	int nmInicio=0;
	int nmFinal=0;
	int nsInicio=0;
	int nsFinal=0;
	
	ArrayList<aca.bsc.Indicadores> vIndicadores = IndicadoresUtil.getListAll(conEnoc,"WHERE  NOMBRE LIKE 'Aprovechamiento%'");
	if (vIndicadores.size()>0){
		indicadores = (aca.bsc.Indicadores) vIndicadores.get(0);
		nbInicio = Integer.parseInt(indicadores.getNbInicio());
		nbFinal = Integer.parseInt(indicadores.getNbFinal());
		nmInicio = Integer.parseInt(indicadores.getNmInicio());
		nmFinal = Integer.parseInt(indicadores.getNmFinal());
		nsInicio = Integer.parseInt(indicadores.getNsInicio());
		nsFinal = Integer.parseInt(indicadores.getNsFinal());
	}
%>
<form action="indicadores" method='post' name='forma' id="noayuda">
<input type='hidden' name='idItemE'>
<input type='hidden' name='accion'>
<table >
	<tr>
		<td><a class="btn btn-primary" href="promediosDetalle?carga=<%=cargaId%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
	</tr>
</table>
<table style='margin:0 auto; width:100%;' class="table table-condensed table-striped table-bordered">
	<tr><td colspan='11' align='center'><font size='3'><b>[<%=cargaId%>] - <%=aca.carga.CargaUtil.getNombreCarga(conEnoc,cargaId)%></b></font></td></tr>
	<tr><td colspan='11' align='center' class="tabla">Aprovechamiento académico (<%=nombreCarrera%>)</td></tr>
	<tr>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="5%"><spring:message code="aca.Matricula"/></th>
		<th width="10%"><spring:message code="aca.Residencia"/></th>
		<th width="55%" align='center'><spring:message code="aca.Nombre"/></th>
		<th width="5%" align='center'>#Materias</th>
		<th width="5%" align='center'>#AC</th>
		<th width="5%" align='center'>#NA</th>
		<th width="5%" align='center'>#BA</th>
		<th width="5%" align='center'>#RA</th>
		<th width="5%" align='center'>#CP</th>
		<th width="5%" align='center'>#CD</th>
		<th width="5%" align='center'><spring:message code="aca.Promedio"/></th>
	</tr>	
<%	
	ArrayList<aca.vista.Estadistica> vAlumnos = estadisticaU.getListCarrera(conEnoc,cargaId,carreraId,"ORDER BY APELLIDO_PATERNO");
	String sBgcolor	= "";
	String bgColor="",fColor="";
	String nombre = "";
	String codigoPersonal="";
	int edo=0,nA=0,j=0;
	double p=0;	
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("##0.00;(##0.00)");
	for(int i=0;i<vAlumnos.size();i++){
		estadistica = (aca.vista.Estadistica) vAlumnos.get(i);
		nombre = estadistica.getNombreLegal();
		codigoPersonal = estadistica.getCodigoPersonal();
		p = aca.vista.AlumnoCursoUtil.promedioAlumnoCarga(conEnoc, estadistica.getCodigoPersonal(), cargaId);
		nA = aca.bsc.Aprovechamiento.getTotalMateriasDeAlumno(conEnoc,cargaId,carreraId,codigoPersonal);
		if (nbInicio<nbFinal){
			if (p >= nbInicio && p<nbFinal) edo=1;
			else if (p >= nmInicio && p<nmFinal) edo=2;
			else if (p >= nsInicio && p<nsFinal) edo=3;
			else edo = -1;
		}else{
			if (p < nbInicio && p>=nbFinal) edo=1;
			else if (p < nmInicio && p>=nmFinal) edo=2;
			else if (p < nsInicio && p>=nsFinal) edo=3;
			else edo = -1;
		}
		if (edo==1){bgColor = "#ff0000";fColor = "red";}
		else if (edo==2){bgColor = "#ffff00";fColor = "#000000";}
		else if (edo==3){bgColor = "#00ff00";fColor = "#000000";}
		else{bgColor = "#BBBBBB";fColor = "#000000";p=0;}
		if ((i % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
		String residencia = "";
		if(aca.alumno.EstadoUtil.getInterno(conEnoc, codigoPersonal, cargaId).equals("E"))
			residencia = "Externo";
		else 
			residencia = "Interno";
%>		
		<tr>
			<td><%=i+1%></td>			
			<td><%=codigoPersonal%></td>
			<td><%=residencia%></td>			
			<td><a href="materiasPorAlumno?carreraId=<%=carreraId%>&promedio=<%=getformato.format(p)%>&cargaId=<%=cargaId%>&codigoPersonal=<%=codigoPersonal%>&nombre=<%=nombre%>&nombreCarrera=<%=nombreCarrera%>"><%=nombre%></a></td>
			<td align='center'><%=nA%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"1")%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"2")%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"3")%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"4")%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"5")%></td>
			<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalAlumno(conEnoc,cargaId,carreraId,codigoPersonal,"6")%></td>
			<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=getformato.format(p)%></b></font></td>
		</tr>
<%	}
%>		
</table>
</form>
<p align='center'>..Fin!</p>
<%@ include file= "../../cierra_enoc.jsp" %>