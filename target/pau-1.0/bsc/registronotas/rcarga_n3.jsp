<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="grupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="curso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="autil" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.CargaUtil"/>

<%
	int nbInicio=0;
	int nbFinal=0;
	int nmInicio=0;
	int nmFinal=0;
	int nsInicio=0;
	int nsFinal=0;
	String carreraId = request.getParameter("carreraId");
	String nombreCarrera = request.getParameter("nombreCarrera");
	String cargaId = request.getParameter("cargaId");
	ArrayList<aca.bsc.Indicadores> vIndicadores = IndicadoresUtil.getListAll(conEnoc,"where nombre = 'Registro de notas'");
	if (vIndicadores.size()>0){
		indicadores = vIndicadores.get(0);
		nbInicio = Integer.parseInt(indicadores.getNbInicio());
		nbFinal = Integer.parseInt(indicadores.getNbFinal());
		nmInicio = Integer.parseInt(indicadores.getNmInicio());
		nmFinal = Integer.parseInt(indicadores.getNmFinal());
		nsInicio = Integer.parseInt(indicadores.getNsInicio());
		nsFinal = Integer.parseInt(indicadores.getNsFinal());
	}
	
	String colorBien 	= "#59C33E";
	String colorMal 	= "#FF8181";
	String color23 		= "#E6DB11";
	String colorNot 	= "";
%>
<form action="indicadores" method='post' name='forma' id="noayuda">
	<input type='hidden' name='idItemE'>
	<input type='hidden' name='accion'>
	
<div class="container-fluid">
<h2>Registro de notas por profesor<small class="text-muted fs-4"> (<%=nombreCarrera%>)&nbsp;( [<%=cargaId%>] - <%=carga.getNombre(conEnoc,cargaId)%> )</small></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="rcarga_n2?cargaId=<%=cargaId%>"><i class="fas fa-arrow-left"></i></a>
</div>
	<%	
		ArrayList<aca.carga.CargaGrupo> listor = grupoU.getListaCarrera(conEnoc,cargaId,carreraId,"order by codigo_personal");
		long TotalMaterias = 0;
		long MateriasCerradas = 0;
		int p=0,edo=0,k,cont=0;
		long estado[]= new long[5];
		String codigoPersonal = "";
		String nombreMaestro = "";
		for(int i=0;i<listor.size();i++){
			grupo = listor.get(i);
			curso = MapaCursoU.mapeaRegId(conEnoc,grupoU.getCursoId(conEnoc,grupo.getCursoCargaId()));
			if (!codigoPersonal.equals(grupo.getCodigoPersonal())){
				cont=0;
				codigoPersonal = grupo.getCodigoPersonal();
				nombreMaestro = aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,codigoPersonal,"NOMBRE");
				TotalMaterias = grupoU.getTotalCargaCarreraMaestro(conEnoc,cargaId,carreraId,codigoPersonal);
				for (k=0;k<5;k++) estado[k] = grupoU.getCargaEstadoCarrera(conEnoc,cargaId,String.valueOf(k+1),carreraId,codigoPersonal);
				MateriasCerradas = estado[3]+estado[4];
				if (TotalMaterias==0) p=0;
				else p = (int)((double)MateriasCerradas*100/(double)TotalMaterias);
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
				if(edo==1) colorNot = colorMal;
				else if(edo==2) colorNot = color23;
				else if(edo==3) colorNot = colorBien;
	%>
				<table class="table table-bordered">
				<thead class="table-info">
					<tr>
						<th width="10%">Nómina</th>
						<th width="30%"><spring:message code="aca.Nombre"/></th>
						<th width="5%">#Alumnos</th>
						<th width="5%"><spring:message code="aca.Creada"/></th>
						<th width="5%">Ord.</th>
						<th width="5%">Extra.</th>		
						<th width="5%"><spring:message code="aca.Cerrada"/></th>		 		
						<th width="5%">Entregada</th>
						<th width="5%"><spring:message code="aca.Total"/></th>
						<th width="5%"><spring:message code="aca.Estado"/></th>
					</tr>
				</thead>
					<tr>
						<td	style="text-align:center; font-size:14;"><b><%=codigoPersonal%></b></td>			
						<td colspan='2'	style="font-size:14;"><b><%=nombreMaestro%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=estado[0]%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=estado[1]%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=estado[2]%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=estado[3]%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=estado[4]%></b></td>
						<td	style="text-align:center; font-size:14;"><b><%=TotalMaterias%></b></td>
						<td style="text-align:right; font-size:14; background-color:white; border-width:4px; border-color:<%=colorNot %>;"><b><%=p%>%</b></td>
					</tr>
	<%		}
			for(k=0;k<5;k++)estado[k] = 0;
			estado[Integer.parseInt(grupo.getEstado())-1] = 1;%>		
			<tr>
				<td><%=grupoU.getCursoId(conEnoc,grupo.getCursoCargaId())%></td>
				<td><%=curso.getNombreCurso()%></td>
				<td style="text-align:center;"><%=autil.getNumAlumnos(conEnoc,grupo.getCursoCargaId())%></td>
				<td style="text-align:center;vertical-align:middle;"><%=estado[0]==1?"<img src='../../imagenes/g1.gif'>":""%></td>
				<td style="text-align:center;vertical-align:middle;"><%=estado[1]==1?"<img src='../../imagenes/g1.gif'>":""%></td>
				<td style="text-align:center;vertical-align:middle;"><%=estado[2]==1?"<img src='../../imagenes/g1.gif'>":""%></td>
				<td style="text-align:center;vertical-align:middle;"><%=estado[3]==1?"<img src='../../imagenes/g1.gif'>":""%></td>
				<td style="text-align:center;vertical-align:middle;"><%=estado[4]==1?"<img src='../../imagenes/g1.gif'>":""%></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>	
	<%	}%>		
	</table>
	</div>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>