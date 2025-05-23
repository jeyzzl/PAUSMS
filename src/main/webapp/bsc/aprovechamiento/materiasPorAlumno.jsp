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
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"/>
<jsp:useBean id="tipo" scope="page" class="aca.catalogo.CatTipoCal"/>
<jsp:useBean id="TipoCalU" scope="page" class="aca.catalogo.CatTipoCalUtil"/>

<%
	String cargaId = request.getParameter("cargaId");
	String carreraId = request.getParameter("carreraId");
	String nombreCarrera = request.getParameter("nombreCarrera");
	String codigoPersonal  = request.getParameter("codigoPersonal");
	String nombreAlumno = request.getParameter("nombre");
	String promedio = request.getParameter("promedio");

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
<form action="indicadores" method='post' name='forma' id="noayuda">
<input type='hidden' name='idItemE'>
<input type='hidden' name='accion'>
<table class="goback">
	<tr>
		<td colspan='11'>
			<a class="btn btn-primary" href="promedioPorAlumnos?cargaId=<%=cargaId%>&carreraId=<%=carreraId%>&nombreCarrera=<%=nombreCarrera%>">
				&lsaquo;&lsaquo; Regresar
			</a>
		</td>
	</tr>
</table>
<table style='width:90%; margin:0 auto;'>
	
	<tr><td colspan='11' align='center'><font size='3'><b>[<%=cargaId%>] - <%=aca.carga.CargaUtil.getNombreCarga(conEnoc,cargaId)%></b></font></td></tr>
	<tr><td colspan='11' align="center" >Aprovechamiento académico (<%=nombreCarrera%>)</td></tr>
	<tr><td colspan='11' align='center'><font size='3'><b><%=codigoPersonal%> - <%=nombreAlumno%></b></font></td></tr>
</table>
<table style='margin:0 auto; width=90%;'    class="table table-condensed table-bordered table-striped">	
	<tr>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="15%">Acta</th>
		<th width="10%">Curso</th>
		<th align='center'><spring:message code="aca.Nombre"/></th>
		<th width="5%" align='center'><spring:message code="aca.Nota"/></th>
		<th width="5%" align='center'><spring:message code="aca.Estado"/></th>
	</tr>	
<%	
	ArrayList<String> listor = aca.bsc.Aprovechamiento.getMateriasDeAlumno(conEnoc,cargaId,carreraId,codigoPersonal);
	String sBgcolor	= "";
	String bgColor="",fColor="";
	String nombre = "";
	int edo=0,nA=0,j=0;
	double p=0;	
	for(int i=0;i<listor.size();i+=5){
		p = Double.parseDouble((String)listor.get(i+3));
		tipo = TipoCalU.mapeaRegId(conEnoc, (String)listor.get(i+4));
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
%>		
		<tr>
			<td><%=i+1%></td>			
			<td><%=(String)listor.get(i)%></td>			
			<td><%=(String)listor.get(i+1)%></td>
			<td><%=(String)listor.get(i+2)%></td>
			<td	align='center' bgcolor='<%=bgColor%>'><font color='<%=fColor%>'><b><%=p%></b></font></td>
			<td align='center'><%=tipo.getNombre()%></td>
		</tr>
<%	}
%>		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td align='right'>Promedio:</td>
			<td align='center'><b><%=promedio%></b></td>
		</tr>
</table>
</form>
<p align='center'>..Fin!</p>
<%@ include file= "../../cierra_enoc.jsp" %>