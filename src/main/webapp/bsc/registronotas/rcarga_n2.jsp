<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>

<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="facultad" scope="page" class="aca.catalogo.CatFacultad"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.CargaUtil"/>

<html>
<%
	String cargaId = request.getParameter("cargaId");
	
	int nbInicio=0;
	int nbFinal=0;
	int nmInicio=0;
	int nmFinal=0;
	int nsInicio=0;
	int nsFinal=0;
	
	ArrayList<aca.bsc.Indicadores> vIndicadores = IndicadoresUtil.getListAll(conEnoc,"where nombre = 'Registro de notas'");
	
	if (vIndicadores.size()>0){
		indicadores = (aca.bsc.Indicadores) vIndicadores.get(0);
		nbInicio = Integer.parseInt(indicadores.getNbInicio());
		nbFinal = Integer.parseInt(indicadores.getNbFinal());
		nmInicio = Integer.parseInt(indicadores.getNmInicio());
		nmFinal = Integer.parseInt(indicadores.getNmFinal());
		nsInicio = Integer.parseInt(indicadores.getNsInicio());
		nsFinal = Integer.parseInt(indicadores.getNsFinal());
	}
	
	HashMap<String,String> mapFac 		= aca.carga.CargaGrupoUtil.getTotGpoFac(conEnoc, "'"+cargaId+"'");
	HashMap<String,String> mapFacEdo	= aca.carga.CargaGrupoUtil.getTotGpoFacEdo(conEnoc, "'"+cargaId+"'");
	HashMap<String,String> mapCarr		= aca.carga.CargaGrupoUtil.getTotGpoCarr(conEnoc, "'"+cargaId+"'");
	HashMap<String,String> mapCarrEdo	= aca.carga.CargaGrupoUtil.getTotGpoCarrEdo(conEnoc, "'"+cargaId+"'");
	
	String colorBien 	= "#59C33E";
	String colorMal 	= "#FF8181";
	String color23 		= "#E6DB11";
	String colorNot 	= "";
%>
	<body>
		<form action="indicadores" method='post' name='forma' id="noayuda">
		<input type='hidden' name='idItemE'>
		<input type='hidden' name='accion'>
	<div class="container-fluid">
		<h2>
			Registro de notas por facultad<small class="text-muted fs-4"> ( [<%=cargaId%>] - <%=carga.getNombre(conEnoc,cargaId)%> )</small>
		</h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="rcarga"><i class="fas fa-arrow-left"></i></a>
		</div>
		<%	ArrayList<aca.catalogo.CatFacultad> listor = grupoU.getFacultades(conEnoc,cargaId);
			long TotalMaterias = 0;
			long MateriasCerradas = 0;
			int p=0,edo=0,k;
			
			long estado[] = new long[6];
			for(int i=0;i<listor.size();i++){
				facultad = (aca.catalogo.CatFacultad) listor.get(i);
				
				if(mapFac.containsKey(cargaId+facultad.getFacultadId())) TotalMaterias = Long.parseLong(mapFac.get(cargaId+facultad.getFacultadId()));
				else TotalMaterias = 0;
				
				for(k=1;k<6;k++){
					if(mapFacEdo.containsKey(cargaId+facultad.getFacultadId()+String.valueOf(k))) estado[k] = Long.parseLong(mapFacEdo.get(cargaId+facultad.getFacultadId()+String.valueOf(k)));
					else estado[k] = 0;
				}
				MateriasCerradas = estado[4]+estado[5];
				if (TotalMaterias==0) p=0;
				else p = (int)((double)MateriasCerradas*100/(double)TotalMaterias);
				if (nbInicio<nbFinal){
					if(p>=nbInicio && p<=nbFinal) edo=1;
					else if(p>=nmInicio && p<=nmFinal) edo=2;
					else if(p>=nsInicio && p<=nsFinal) edo=3;
					else edo = -1;
				}else{
					if(p<=nbInicio && p>=nbFinal) edo=1;
					else if(p<=nmInicio && p>=nmFinal) edo=2;
					else if(p<=nsInicio && p>=nsFinal) edo=3;
					else edo = -1;
				}
				if(edo==1) colorNot = colorMal;
				else if(edo==2) colorNot = color23;
				else if(edo==3) colorNot = colorBien;
			%>
				<table class="table table-bordered">
					<thead>
					<tr>
						<th colspan="2"><b><%=facultad.getNombreFacultad()%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=estado[1]%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=estado[2]%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=estado[3]%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=estado[4]%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=estado[5]%></b></th>
						<th	style="text-align:right; font-size:15;"><b><%=TotalMaterias%></b></th>
						<th style="text-align:right; font-size:15; background-color:white; border-width:4px; border-color:<%=colorNot %>;"><b><%=p%>%</b></th>
					</tr>
					</thead>
					<thead class="table-info">
					<tr>
						<th width="1%"><spring:message code="aca.Clave"/></th>
						<th width="30%" align='center'><spring:message code="aca.Nombre"/></th>
						<th width="1%" align='center'><spring:message code="aca.Creada"/></th>
						<th width="1%" align='center'>Ordinaria</th>
						<th width="1%" align='center'><spring:message code="aca.Extra"/></th>
						<th width="1%" align='center'><spring:message code="aca.Cerrada"/></th>
						<th width="1%" align='center'>Entregada</th>
						<th width="1%" align='center'><spring:message code="aca.Total"/></th>
						<th width="1%" align='center'><spring:message code="aca.Estado"/></th>
					</tr>
					</thead>
			<%	ArrayList<aca.catalogo.CatCarrera> listorCarr = grupoU.getCarreras(conEnoc,cargaId,facultad.getFacultadId());
				for(int m=0;m<listorCarr.size();m++){
					aca.catalogo.CatCarrera carrera = (aca.catalogo.CatCarrera) listorCarr.get(m);
					TotalMaterias = Long.parseLong(mapCarr.get(cargaId+carrera.getCarreraId()));
					
					for (k=1;k<6;k++){
						if(mapCarrEdo.containsKey(cargaId+carrera.getCarreraId()+String.valueOf(k))) estado[k] = Long.parseLong(mapCarrEdo.get(cargaId+carrera.getCarreraId()+String.valueOf(k)));
						else estado[k] = 0;
					}
					
					MateriasCerradas = estado[4]+estado[5];
					
					if (TotalMaterias==0) p=0;
					else p = (int)((double)MateriasCerradas*100/(double)TotalMaterias);
					if (nbInicio<nbFinal){
						if(p>=nbInicio && p<=nbFinal) edo=1;
						else if(p>=nmInicio && p<=nmFinal) edo=2;
						else if(p>=nsInicio && p<=nsFinal) edo=3;
						else edo = -1;
					}else{
						if(p<=nbInicio && p>=nbFinal) edo=1;
						else if(p<=nmInicio && p>=nmFinal) edo=2;
						else if(p<=nsInicio && p>=nsFinal) edo=3;
						else edo = -1;
					}
					if(edo==1) colorNot = colorMal;
					else if(edo==2) colorNot = color23;
					else if(edo==3) colorNot = colorBien;
				%>
					<tr class="button" onclick="document.location.href='rcarga_n3?carreraId=<%=carrera.getCarreraId()%>&cargaId=<%=cargaId%>&nombreCarrera=<%=carrera.getNombreCarrera()%>';">
						<td align='center'><b><%=carrera.getCarreraId()%></b></td>			
						<td><b><%=carrera.getNombreCarrera()%></b></td>
						<td	style="text-align:right;"><b><%=estado[1]%></b></td>
						<td	style="text-align:right;"><b><%=estado[2]%></b></td>
						<td	style="text-align:right;"><b><%=estado[3]%></b></td>
						<td	style="text-align:right;"><b><%=estado[4]%></b></td>
						<td	style="text-align:right;"><b><%=estado[5]%></b></td>
						<td	style="text-align:right;"><b><%=TotalMaterias%></b></td>
						<td	style="text-align:right; background-color:white; border-width:4px; border-color:<%=colorNot %>;"><b><%=p%>%</b></td>
					</tr>
			<%	}
			}%>
		</table>
		</div>
		</form>
	</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>