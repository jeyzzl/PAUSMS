<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>

<jsp:useBean id="indicadores" scope="page" class="aca.bsc.Indicadores"/>
<jsp:useBean id="IndicadoresUtil" scope="page" class="aca.bsc.IndicadoresUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="grupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>


<head>
	<script>
		function grabaPeriodo(periodoId){
	  		document.location.href="rcarga?cambioPeriodo=1&PeriodoId="+periodoId;
		}
	</script>
</head>
<%
	if(request.getParameter("cambioPeriodo")!=null&&!request.getParameter("cambioPeriodo").equals("")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	String periodoActual 	= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
	String periodoId 		= (String)session.getAttribute("periodo");
	String cargas			= aca.carga.CargaUtil.getCargasPeriodo(conEnoc, periodoId);
	
	int nbInicio=0;
	int nbFinal=0;
	int nmInicio=0;
	int nmFinal=0;
	int nsInicio=0;
	int nsFinal=0;
	
	ArrayList<aca.bsc.Indicadores> vIndicadores 		= IndicadoresUtil.getListAll(conEnoc,"WHERE NOMBRE = 'Registro de notas'");	
	if (vIndicadores.size()>0){
		indicadores = (aca.bsc.Indicadores) vIndicadores.get(0);
		nbInicio = Integer.parseInt(indicadores.getNbInicio());
		nbFinal = Integer.parseInt(indicadores.getNbFinal());
		nmInicio = Integer.parseInt(indicadores.getNmInicio());
		nmFinal = Integer.parseInt(indicadores.getNmFinal());
		nsInicio = Integer.parseInt(indicadores.getNsInicio());
		nsFinal = Integer.parseInt(indicadores.getNsFinal());
	}	
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos 	= CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");
	ArrayList<aca.carga.Carga> lisCarga 				= cargaU.getListPeriodo(conEnoc,periodoId,"ORDER BY CARGA_ID");
	HashMap<String,String> mapTotGpo 					= aca.carga.CargaGrupoUtil.getTotGpo(conEnoc, cargas);
	HashMap<String,String> mapTotGpoEdo 				= aca.carga.CargaGrupoUtil.getTotGpoEdo(conEnoc, cargas);	

	String colorBien 	= "#59C33E";
	String colorMal 	= "#FF8181";
	String color23 		= "#E6DB11";
	String colorNot 	= "";
%>
<div class="container-fluid">
<h1>Registro de notas</h1>
<form action="indicadores" method='post' name='forma' id="noayuda">
<input type='hidden' name='idItemE'>
<input type='hidden' name='accion'>
<div class="alert alert-info">
	Periodo:
	  <select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
<%	for(int j=0; j<listaPeriodos.size()-1; j++){
		aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
	 </select>
</div>
<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th width="8%"><spring:message code="aca.Carga"/></th>
		<th width="36%"><spring:message code="aca.Nombre"/></th>
		<th width="8%"><spring:message code="aca.Creada"/></th>
		<th width="8%">Ord.</th>
		<th width="8%">Extra.</th>		
		<th width="8%"><spring:message code="aca.Cerrada"/></th>		
		<th width="8%">Entregada</th>
		<th width="8%"><spring:message code="aca.Total"/></th>
		<th width="8%"><spring:message code="aca.Estado"/></th>
	</tr>
	</thead>
<%	
	long TotalMaterias = 0;
	long MateriasCerradas = 0;
	int p=0,edo=0,k;	
	long estado[]= new long[6];
	
	for(int i=0;i<lisCarga.size();i++){
		carga = (aca.carga.Carga) lisCarga.get(i);
		if (mapTotGpo.containsKey(carga.getCargaId())){
			TotalMaterias = Long.parseLong(mapTotGpo.get(carga.getCargaId()));			
		}
		
		for (k=1;k<6;k++){
			 
			if (mapTotGpoEdo.containsKey(carga.getCargaId()+String.valueOf(k)) ){
				estado[k] = Long.parseLong(mapTotGpoEdo.get(carga.getCargaId()+String.valueOf(k)));
			}else{
				estado[k] = 0;
			}		
		}
		//estado[k] = grupoU.getCargaEstado(conEnoc,carga.getCargaId(),String.valueOf(k+1));
		MateriasCerradas = estado[4]+estado[5];	
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
		<tr class="button" onclick="document.location.href='rcarga_n2?cargaId=<%=carga.getCargaId()%>';">
			<td align='right'><%=carga.getCargaId()%></td>			
			<td><%=carga.getNombreCarga()%></td>
			<td style="text-align:right"><%=estado[1]%></td>
			<td style="text-align:right"><%=estado[2]%></td>
			<td style="text-align:right"><%=estado[3]%></td>
			<td style="text-align:right"><%=estado[4]%></td>
			<td style="text-align:right"><%=estado[5]%></td>
			<td style="text-align:right"><%=TotalMaterias%></td>
			<td	style="text-align:right; background-color:white; border-width:4px; border-color:<%=colorNot %>;"><b><%=p%>%</b></td>
		</tr>
<%	}
%>		
</table>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>