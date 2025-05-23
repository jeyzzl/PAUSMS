<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="maestro" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="alumnoU" scope="page" class="aca.alumno.AlumUtil"/>
<%
	String cargaId = request.getParameter("cargaId");
	String carreraId = request.getParameter("carreraId");
	String nombreCarrera = request.getParameter("nombreCarrera");
	String codigoPersonal = request.getParameter("codigoPersonal");
	String nombreMaestro = request.getParameter("nombreMaestro");
	String cursoCargaId = request.getParameter("cursoCargaId");
	String nombreCurso = request.getParameter("nombreCurso");
	String cursoId = request.getParameter("cursoId");
	int nbInicio=0;
	int nbFinal=0;
	int nmInicio=0;
	int nmFinal=0;
	int nsInicio=0;
	int nsFinal=0;
	
	ArrayList<aca.bsc.Indicadores> vIndicadores = IndicadoresUtil.getListAll(conEnoc,"WHERE NOMBRE LIKE 'Aprovechamiento%'");
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
<style>
	.bordered td{
		border:1px solid black;
	}
</style>
<form action="indicadores" method='post' name='forma' id="noayuda">
<input type='hidden' name='idItemE'>
<input type='hidden' name='accion'>
<table style='width: 90%; margin:0 auto;' class="table table-condensed table-nohover">
	<tr><td colspan='12'><a class="btn btn-primary" href="promedioPorMaestros?cargaId=<%=cargaId%>&carreraId=<%=carreraId%>&nombreCarrera=<%=nombreCarrera%>">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td></tr>
	<tr><td colspan='12' align='center'><font size='3'><b>[<%=cargaId%>]  <%=aca.carga.CargaUtil.getNombreCarga(conEnoc,cargaId)%></b></font></td></tr>
	<tr><td colspan='12' align='center' class="th2">Aprolishamiento académico (<%=nombreCarrera%>)</td></tr>
	<tr><td colspan='12' align='center'><font size='2'><b>[<%=cursoCargaId%>] <%=codigoPersonal%> - <%=nombreMaestro%></b></font></td></tr>
	<tr>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="5%"><spring:message code="aca.Clave"/></th>
		<th width="45%" align='center'><spring:message code="aca.Nombre"/></th>
		<th width="5%" align='center'>Cred.</th>
		<th width="5%" align='center'>#Alumnos</th>
		<th width="5%" align='center'>#AC</th>
		<th width="5%" align='center'>#NA</th>
		<th width="5%" align='center'>#BA</th>
		<th width="5%" align='center'>#RA</th>
		<th width="5%" align='center'>#CP</th>
		<th width="5%" align='center'>#CD</th>
		<th width="5%" align='center'><spring:message code="aca.Promedio"/></th>
	</tr>	
<%	
	ArrayList<String> lisAlumnos = aca.bsc.Aprovechamiento.getAlumnosMateria(conEnoc,cargaId,carreraId,cursoCargaId);
	String sBgcolor	= "";
	String bgColor="",fColor="";
	int edo=0,nA=0,j=0;
	double p=0;	
	nA = aca.bsc.Aprovechamiento.numeroAlumnosMateria(conEnoc,cargaId,carreraId,cursoCargaId);
	p = aca.bsc.Aprovechamiento.promedioMateria(conEnoc,cargaId,carreraId,cursoCargaId);
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
%>
	<tr class="bordered" bgcolor="#D8D8D8">
		<td>&nbsp;</td>
		<td align='center'><font><b><%=cursoId%></b></font></td>			
		<td class="th2">&nbsp;&nbsp;&nbsp;<font><b><%=nombreCurso%></b></font></td>
		<td align='center'><font><b><%=(String) lisAlumnos.get(4)%></b></font></td>
		<td align='center'><font><b><%=nA%></b></font></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"1")%></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"2")%></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"3")%></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"4")%></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"5")%></td>
		<td align='center'><%=aca.bsc.Aprovechamiento.numeroTipoCalMateria(conEnoc,cargaId,carreraId,cursoCargaId,"6")%></td>
		<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=p%></b></font></td>
	</tr>
<%
	for(int i=0;i<lisAlumnos.size();i+=5){
		String extra="";
		codigoPersonal = (String) lisAlumnos.get(i);
		String nota = 			(String) lisAlumnos.get(i+1);
		String notaExtra = 		(String) lisAlumnos.get(i+2);
		String tipoCalId = 		(String) lisAlumnos.get(i+3);
		if (Integer.parseInt(notaExtra)>0){
			p = Integer.parseInt(notaExtra);
			extra="(extra)";
		}else p = Integer.parseInt(nota);
		if (nbInicio<nbFinal){
			if (p >= nbInicio && p<=nbFinal) edo=1;
			else if (p >= nmInicio && p<=nmFinal) edo=2;
			else if (p >= nsInicio && p<=nsFinal) edo=3;
			else edo = -1;
		}else{
			if (p <= nbInicio && p>=nbFinal) edo=1;
			else if (p <= nmInicio && p>=nmFinal) edo=2;
			else if (p <= nsInicio && p>=nsFinal) edo=3;
			else edo = -1;
		}
		if (edo==1){bgColor = "#ff0000";fColor = "#ffffff";}
		else if (edo==2){bgColor = "#ffff00";fColor = "#000000";}
		else if (edo==3){bgColor = "#00ff00";fColor = "#000000";}
		else{bgColor = "#BBBBBB";fColor = "#000000";p=0;}
		if ((j % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
		j++;
		String e[] = new String[6];
		String inscrito = "";
		for (int k=0;k<6;k++)e[k]="";
		if (!tipoCalId.equals("I"))
			e[Integer.parseInt(tipoCalId)-1] = "<b>x</b>";
		else inscrito = "(inscrito)";
		if (tipoCalId.equals("3")){bgColor = "#BBBBBB";fColor = "#000000";}
		
%>		
		<tr >
			<td><%=j%></td>			
			<td><%=codigoPersonal%></td>			
			<td><%=alumnoU.getNombre(conEnoc,codigoPersonal,"APELLIDO")%></td>
			<td align='center'><%=inscrito%></td>
			<td align='center'><%=extra%></td>
			<td align='center'><%=e[0]%></td>
			<td align='center'><%=e[1]%></td>
			<td align='center'><%=e[2]%></td>
			<td align='center'><%=e[3]%></td>
			<td align='center'><%=e[4]%></td>
			<td align='center'><%=e[5]%></td>
			<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=p%></b></font></td>
		</tr>
<%	}
%>		
</table>
</form>
<p align='center'>.. Fin!!</p>
<%@ include file= "../../cierra_enoc.jsp" %>