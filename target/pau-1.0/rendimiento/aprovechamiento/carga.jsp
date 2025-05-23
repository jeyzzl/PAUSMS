<%@ page import="java.text.*" %>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.kardex.ActualUtil"%>
<%
	DecimalFormat formato 		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String periodoId 	= request.getParameter("periodoId")==null?(String)session.getAttribute("periodo"):request.getParameter("periodoId");	
	String cargaId 		= request.getParameter("cargaId")==null?(String)session.getAttribute("cargaId"):request.getParameter("cargaId");
	
	String	sCodigo				= "X";
	String sTipoCal 			= "";
	
	int i			= 0, con	= 0, nAlum	= 0;
	int nAc			= 0, nRa	= 0, nCp	= 0;
	int nCd			= 0, nBa	= 0, nNa	= 0;
	int nTalum		= 0, nTac 	= 0, nTra	= 0;
	int nTcp		= 0, nTcd	= 0, nTba	= 0;
	int nTna		= 0;
	
	double 	dCred	= 0, dHt	= 0, dHp	= 0;
	double	dAc		= 0, dRa	= 0, dCp	= 0;
	double	dCd		= 0, dBa	= 0, dNa	= 0;
	double	dTotal	= 0;
	
	List<CatPeriodo> lisPeriodos 		= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 				= (List<Carga>) request.getAttribute("lisCargas");
	List<CargaAcademica> lisMaterias	= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	HashMap<String,String> mapaTotal	= (HashMap<String,String>)request.getAttribute("mapaTotal");
	HashMap<String,String> mapaPorTipos	= (HashMap<String,String>)request.getAttribute("mapaPorTipos");
%>
<div class="container-fluid">
	<h1>Aprovechamiento académico</h1>
	<div class="alert alert-info">
	<form name="forma" method="get">
	<input name="CargaId" type="hidden" >
		<b><spring:message code="aca.Periodo"/>:</b>
		<select onchange="this.form.submit()" name="periodoId" class="input input-medium">
		<%	for(int j=0; j<lisPeriodos.size(); j++){
				CatPeriodo periodo = lisPeriodos.get(j);%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
		<%	}%>
		</select>
		&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select onchange='this.form.submit()' name="cargaId" style="width:350px;" class="input input-xlarge">
			<%	for(Carga carga: lisCargas){%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
			<%	}%>
		</select>
	</form>
	</div>
	<table class="table table-bordered">
  
<%	for(i=0; i<lisMaterias.size(); i++) {
		CargaAcademica cargaAca = (CargaAcademica) lisMaterias.get(i);
		if(!cargaAca.getCodigoPersonal().equals(sCodigo)){
			con = 0;
			if (!sCodigo.equals("X")){
%>
	<thead>
	<tr> 
	    <th colspan="3"><div align="right">TOTALES:</div></th>
	    <th width="39" class="right"><%=dCred%></th>
	    <th width="37" class="right"><%=dHt%></th>
	    <th width="34" class="right"><%=dHp%></th>
	    <th width="56" class="right"><%=nTalum%></th>
	    <th width="35"><%=nTac%></th>
	    <% dTotal = (double) nTac*100/nTalum; %>
	    <th width="38" class="right"><%=formato.format(dTotal)%></th>
	    <th width="31" class="right"><%=nTra%></th>
	    <% dTotal = (double) nTra*100/nTalum; %>
	    <th width="39" class="right"><%=formato.format(dTotal)%></th>
	    <th width="28" class="right"><%=nTcp%></th>
	    <% dTotal = (double) nTcp*100/nTalum; %>
	    <th width="36" class="right"><%=formato.format(dTotal)%></th>
	    <th width="31" class="right"><%=nTcd%></th>
	    <% dTotal = (double) nTcd*100/nTalum; %>
	    <th width="39" class="right"><%=formato.format(dTotal)%></th>
	    <th width="30" class="right"><%=nTba%></th>
	    <% dTotal = (double) nTba*100/nTalum; %>
	    <th width="38" class="right"><%=formato.format(dTotal)%></th>
	    <th width="31" class="right"><%=nTna%></th>
	    <% dTotal = (double) nTna*100/nTalum; %>
	    <th width="39" class="right"><%=formato.format(dTotal)%></th>
	</tr>
	</thead>
	<tr> 
	    <td colspan="19" align="center">&nbsp;</td>
    <% 	        dCred= 	0; dHt= 	0; dHp= 	0; nTalum=	0; nTac=	0;
			    nTra=	0; nTcp=	0; nTcd=	0; nTba=	0; nTna=	0;
			}// fin if de sCodigo
			sCodigo= cargaAca.getCodigoPersonal();
%>
	</tr>
	</table>
	<table class="table table-bordered">
	<thead>
  	<tr> 
 	   <td colspan="19"><strong><em>Maestro: [<%=sCodigo%>]<%=cargaAca.getNombre()%></em></strong></td>
  	</tr>
	</thead>
	<thead class="table-info">
  	<tr> 
    	<th width="34" height="21">N&deg;</th>
	    <th width="76">PLAN</th>
	    <th width="195">MATERIA</th>
	    <th class="right">CRD.</th>
	    <th class="right">HT</th>
	    <th class="right">HP</th>
	    <th class="right">#ALUM</th>
	    <th class="right">#AC</th>
	    <th class="right">%AC</th>
	    <th class="right">#RA</th>
	    <th class="right">%RA</th>
	    <th class="right">#CP</th>
	    <th class="right">%CP</th>
	    <th class="right">#CD</th>
	    <th class="right">%CD</th>
	    <th class="right">#BA</th>
	    <th class="right">%BA</th>
	    <th class="right">#NA</th>
	    <th class="right">%NA</th>
	</tr>
	</thead>
<%		} // fin del if codigoPersonal
		dCred	= dCred + Double.parseDouble(cargaAca.getCreditos());
		dHt		= dHt + Double.parseDouble(cargaAca.getHt());
		dHp		= dHp + Double.parseDouble(cargaAca.getHp());

		nAlum = 0;
		if (mapaTotal.containsKey(cargaAca.getCursoCargaId())){
			nAlum = Integer.parseInt(mapaTotal.get(cargaAca.getCursoCargaId()));
		}
		nTalum = nTalum + nAlum;
		
		nAc = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"1")){
			nAc = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"1"));
		}
		
		nTac= nTac + nAc;
		dAc= (double) nAc * 100/nAlum;
		
		nNa = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"2")){
			nNa = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"2"));
		}
		
		nTna= nTna + nNa;
		dNa= (double) nNa * 100/nAlum;
		
		nBa = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"3")){
			nBa = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"3"));
		}			
		
		nTba= nTba + nBa;
		dBa= (double) nBa * 100/nAlum;
		
		nRa = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"4")){
			nRa = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"4"));
		}
		
		nTra= nTra + nRa;
		dRa= (double) nRa * 100/nAlum;

		nCp = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"5")){
			nCp = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"5"));
		}
		
		nTcp= nTcp + nCp;
		dCp= (double) nCp * 100/nAlum;

		nCd = 0;
		if (mapaPorTipos.containsKey(cargaAca.getCursoCargaId()+"6")){
			nCd = Integer.parseInt(mapaPorTipos.get(cargaAca.getCursoCargaId()+"6"));
		}
		
		nTcd= nTcd + nCd;
		dCd= (double) nCd * 100/nAlum;

		con = con + 1;
%>
	<tr class="tr2"> 
   		<td><%=con%></td>
    	<td><%=cargaAca.getPlanId()%></td>
    	<td><%=cargaAca.getNombreCurso()%>[<%=cargaAca.getModalidadId()%>]</td>
    	<td class="right"><%=cargaAca.getCreditos()%></td>
    	<td class="right"><%=cargaAca.getHt()%></td>
    	<td class="right"><%=cargaAca.getHp()%></td>
    	<td class="right"><%=nAlum%></td>
    	<td width="35" class="right"><%=nAc%></td>
    	<td width="38" class="right"><%=formato.format(dAc)%></td>
    	<td width="31" class="right"><%=nRa%></td>
    	<td width="39" class="right"><%=formato.format(dRa)%></td>
    	<td width="28" class="right"><%=nCp%></td>
    	<td width="36" class="right"><%=formato.format(dCp)%></td>
    	<td width="31" class="right"><%=nCd%></td>
    	<td width="39" class="right"><%=formato.format(dCd)%></td>
    	<td width="30" class="right"><%=nBa%></td>
    	<td width="38" class="right"><%=formato.format(dBa)%></td>
    	<td width="31" class="right"><%=nNa%></td>
    	<td width="39" class="right"><%=formato.format(dNa)%></td>
	</tr>
<%	} //fin del for%>
	</table>
</div>