<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.HashMap"%>

<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>

<style>
	.bordered td{
		border:1px solid black;
	}
</style>
<%
	String cargaId = request.getParameter("cargaId");
	String carreraId = request.getParameter("carreraId");
	String nombreCarrera = request.getParameter("nombreCarrera");
	String promedio = request.getParameter("promedio")==null?(String)session.getAttribute("promedioAprovechamiento"):request.getParameter("promedio");
	
	session.setAttribute("promedioAprovechamiento", promedio);

	HashMap<String, String> map = aca.kardex.KrdxCursoAct.alumnos(conEnoc, cargaId, carreraId);
	
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
	
	// variables para suma de totales
	int nal= 0, nac=0, nna=0, nba=0, nra=0, ncp=0, ncd=0;
	int tal= 0, tac=0, tna=0, tba=0, tra=0, tcp=0, tcd=0;
	
%>
<form action="indicadores" method='post' name='forma' id="noayuda">
<input type='hidden' name='idItemE'>
<input type='hidden' name='accion'>
<table class="goback">
	<tr>
		<td><a class="btn btn-primary" href="promediosDetalle?carga=<%=cargaId%>"><spring:message code='aca.Regresar'/></a></td>
		<td colspan='10'>
			<a class="btn btn-primary" href="promedioPorAlumnos?cargaId=<%=cargaId%>&carreraId=<%=carreraId%>&nombreCarrera=<%=nombreCarrera%>">
			Ver listado por alumnos
			</a>
		</td>
	</tr>
</table>
<br>
<br>
<table style='margin:0 auto; width:90%;' class="table table-condensed table-nohover table-bordered">
	<tr><td colspan='10' align='center'><font size='3'><b>[<%=cargaId%>] <%=aca.carga.CargaUtil.getNombreCarga(conEnoc,cargaId)%></b></font></td></tr>
	<tr><td colspan='10' align='center' class="th2">Aprovechamiento académico (<%=nombreCarrera%>)</td></tr>
	<tr>
		<th width="5%"><spring:message code="aca.Clave"/></th>
		<th width="55%" align='center'><spring:message code="aca.Nombre"/></th>
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
	ArrayList<String> vMat = aca.bsc.Aprovechamiento.getMaterias(conEnoc,cargaId,carreraId);
	String sBgcolor	= "";
	String bgColor="",fColor="";
	int edo=0,nA=0,j=0;
	double p=0;	
	String nombreMaestro="";
	String codigoPersonal="";
	String cursoId="";
	String cursoCargaId="";
	String nombreCurso="";
	String sMaestro="";
	for(int i=0;i<vMat.size();i+=4){
		cursoId = 			(String)vMat.get(i+1);
		cursoCargaId = 		(String)vMat.get(i+2);
		nombreCurso = 		(String)vMat.get(i+3);			
		if (!((String)vMat.get(i)).equals(sMaestro)){
			codigoPersonal = (String)vMat.get(i);
			nombreMaestro = aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, codigoPersonal,"NOMBRE");
			sMaestro = codigoPersonal; j=0;

			// Inicializa los valores 
			nal= 0; nac=0; nna=0; nba=0; nra=0; ncp=0; ncd=0;
			
			// totales por maestro
			int Cant1 = 0;
			int Cant2 = 0;
			int Cant3 = 0;
			int Cant4 = 0;
			int Cant5 = 0;
			int Cant6 = 0;
			
			for(int k=0;k<vMat.size();k+=4){
				if(((String)vMat.get(k)).equals(codigoPersonal)){
					Cant1 += Integer.parseInt(map.get(vMat.get(k+2)+"1")==null?"0":map.get(vMat.get(k+2)+"1"));
					Cant2 += Integer.parseInt(map.get(vMat.get(k+2)+"2")==null?"0":map.get(vMat.get(k+2)+"2"));
					Cant3 += Integer.parseInt(map.get(vMat.get(k+2)+"3")==null?"0":map.get(vMat.get(k+2)+"3"));
					Cant4 += Integer.parseInt(map.get(vMat.get(k+2)+"4")==null?"0":map.get(vMat.get(k+2)+"4"));
					Cant5 += Integer.parseInt(map.get(vMat.get(k+2)+"5")==null?"0":map.get(vMat.get(k+2)+"5"));
					Cant6 += Integer.parseInt(map.get(vMat.get(k+2)+"6")==null?"0":map.get(vMat.get(k+2)+"6"));
				}
			}
			nA  = 	Cant1+Cant2+Cant3+Cant4+Cant5+Cant6; 
			nal = 	nA;
			nac =	Cant1;
			nna	= 	Cant2;
			nba	= 	Cant3;
			nra	= 	Cant4;
			ncp	= 	Cant5;
			ncd	= 	Cant6;
			
			// calcula totales de la carrera
			tal += nal;
			tac += nac;
			tna += nna;
			tba += nba;
			tra += nra;
			tcp += ncp;
			tcd += ncd;

			// calcula el promedio del maestro en sus materias
			p 	= 	aca.bsc.Aprovechamiento.promedioMaestro(conEnoc,cargaId,carreraId,codigoPersonal);
			
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
				<td align='center'><%=codigoPersonal%></td>			
				<td>&nbsp;&nbsp;&nbsp;<%=nombreMaestro%></td>
				<td align='center' class="th2"><%=nal%></td>
				<td align='center' class="th2"><%=nac%></td>
				<td align='center' class="th2"><%=nna%></td>
				<td align='center' class="th2"><%=nba%></td>
				<td align='center' class="th2"><%=nra%></td>
				<td align='center' class="th2"><%=ncp%></td>
				<td align='center' class="th2"><%=ncd%></td>
				<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=p%></b></font></td>
			</tr>
<%
		}
		
		int cant1 = Integer.parseInt(map.get(cursoCargaId+"1")==null?"0":map.get(cursoCargaId+"1"));
		int cant2 = Integer.parseInt(map.get(cursoCargaId+"2")==null?"0":map.get(cursoCargaId+"2"));
		int cant3 = Integer.parseInt(map.get(cursoCargaId+"3")==null?"0":map.get(cursoCargaId+"3"));
		int cant4 = Integer.parseInt(map.get(cursoCargaId+"4")==null?"0":map.get(cursoCargaId+"4"));
		int cant5 = Integer.parseInt(map.get(cursoCargaId+"5")==null?"0":map.get(cursoCargaId+"5"));
		int cant6 = Integer.parseInt(map.get(cursoCargaId+"6")==null?"0":map.get(cursoCargaId+"6"));
		
		nA = cant1+cant2+cant3+cant4+cant5+cant6;
		p = aca.bsc.Aprovechamiento.promedioMateria(conEnoc,cargaId,carreraId,cursoCargaId);
		
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
		if (edo==1){bgColor = "#ff0000";fColor = "red";}
		else if (edo==2){bgColor = "#ffff00";fColor = "#000000";}
		else if (edo==3){bgColor = "#00ff00";fColor = "#000000";}
		else{bgColor = "#BBBBBB";fColor = "#000000";p=0;}
		if ((j % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
		j++;
%>		
		<tr>
			<td><%=cursoId%></td>			
			<td><a href="alumnosPorMaestro?carreraId=<%=carreraId%>&cargaId=<%=cargaId%>&cursoCargaId=<%=cursoCargaId%>&nombreMaestro=<%=nombreMaestro%>&codigoPersonal=<%=codigoPersonal%>&nombreCarrera=<%=nombreCarrera%>&nombreCurso=<%=nombreCurso%>&cursoId=<%=cursoId%>"><%=nombreCurso%></a></td>
			<td align='center'><%=nA%></td>
			<td align='center'><%=cant1%></td>
			<td align='center'><%=cant2%></td>
			<td align='center'><%=cant3%></td>
			<td align='center'><%=cant4%></td>
			<td align='center'><%=cant5%></td>
			<td align='center'><%=cant6%></td>
			<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=p%></b></font></td>
		</tr>
<%	}
		p	= Double.parseDouble(promedio);
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

%>
		<tr><td colspan="10">&nbsp;</td></tr>
		<tr>
			<td align='center' colspan="2"><font><b> T O T A L E S . . .</b></font></td>
			<td align='center'><font><b><%=tal%></b></font></td>
			<td align='center'><font><b><%=tac%></b></font></td>
			<td align='center'><font><b><%=tna%></b></font></td>
			<td align='center'><font><b><%=tba%></b></font></td>
			<td align='center'><font><b><%=tra%></b></font></td>
			<td align='center'><font><b><%=tcp%></b></font></td>
			<td align='center'><font><b><%=tcd%></b></font></td>
			<td	align='center'><font><b><%=p%></b></font></td>
		</tr>		
</table>
</form>

<%@ include file= "../../cierra_enoc.jsp" %>