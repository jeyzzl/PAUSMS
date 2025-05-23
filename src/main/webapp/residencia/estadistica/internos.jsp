<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.EstInternos"%>
<%@ page import= "aca.vista.spring.Inscritos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<% 
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<CatCarrera> lisCarreras				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<EstInternos> lisInternos				= (List<EstInternos>) request.getAttribute("lisInternos");
	List<Inscritos> lisInscritos				= (List<Inscritos>) request.getAttribute("lisInscritos");
	
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");	
	
	String fac			= "X";	
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	
	int i=0,j=0;
	int nInsc=0, nInt=0, nPor=0, nDormi1=0, nDormi2=0, nDormi3=0, nDormi4=0, nDormiX=0;
	int nInscF=0, nIntF=0, nPorF=0, nDormi1F=0, nDormi2F=0, nDormi3F=0, nDormi4F=0, nDormiXF=0;
	int nInscT=0, nIntT=0, nPorT=0, nDormi1T=0, nDormi2T=0, nDormi3T=0, nDormi4T=0, nDormiXT=0;
%>
<div class="container-fluid">
	<h2>Dormitory Students Statistics</h2>
	<form name="forma" action="internos?Accion=1" method="post">
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			Start Date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			End Date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>	
	</form>
	
	<table class="table" id="noayuda">
	<%
		for( CatCarrera carrera : lisCarreras){
			
			String temp 			= "0";
			String facultadNombre	= "-";
			String carreraNombre	= "-";
			if (mapaCarreras.containsKey(carrera.getCarreraId())){
				temp 			= mapaCarreras.get(carrera.getCarreraId()).getFacultadId();
				carreraNombre 	= mapaCarreras.get(carrera.getCarreraId()).getNombreCarrera();
				if (mapaFacultades.containsKey(temp)){
					facultadNombre = mapaFacultades.get(temp).getNombreFacultad();
				}
			}			
			if (!temp.equals(fac)){
				if (!fac.equals("X")){	
					// Suma Totales
					nInscT+=nInscF;
					nIntT+=nIntF;
					nDormi1T += nDormi1F;
					nDormi2T += nDormi2F;
					nDormi3T += nDormi3F;
					nDormi4T += nDormi4F;
					nDormiXT += nDormiXF;
	
					if (nInscF > 0) nPorF= nIntF*100/nInscF;
	%>
	  <tr> 
	  	<th colspan="2" width="5%" align="left"><strong> T o t a l <%=fac%></strong></th>
	    <th width="5%" align="left"><%=nInscF%></th>
	    <th width="5%" align="left"><%=nIntF%></th>
	    <th width="5%" align="left"><%=nPorF%></th>
		<th width="8%" align="left"><%=nDormi1F%></th>
		<th width="8%" align="left"><%=nDormi2F%></th>
		<th width="8%" align="left"><%=nDormi3F%></th>
		<th width="8%" align="left"><%=nDormi4F%></th>
		<th width="8%" align="left"><%=nDormiXF%></th>
	  </tr>			
	<%			}
				fac = temp;
				nInscF=0; nIntF=0; nPorF=0; nDormi1F=0; nDormi2F=0; nDormi3F=0; nDormi4F=0; nDormiXF=0;
	%>
	</table>
	<br>
	<table class="table">
	  <tr><td colspan="9" align="left"><strong>School: <%=fac%>: <%=facultadNombre%></strong></td></tr>
	</table>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	  <tr>
	    <th width="5%" align="left"><spring:message code="aca.Clave"/></th>
	    <th width="34%" align="left"><spring:message code="aca.Carrera"/></th>
	    <th width="5%" style="text-align:left;">Enrr.</th>
	    <th width="5%" style="text-align:left;">Dorm.S.</th>
	    <th width="5%" style="text-align:left;">%</th>
		<th width="8%" style="text-align:left;">Dorm.1</th>
		<th width="8%" style="text-align:left;">Dorm.2</th>
		<th width="8%" style="text-align:left;">Dorm.3</th>
		<th width="8%" style="text-align:left;">Dorm.4</th>
		<th width="8%" style="text-align:left;">Unassigned</th>
	  </tr>
	  </thead>
	<%
			}
	
			nInsc=0; nInt=0; nPor=0; nDormi1=0; nDormi2=0; nDormi3=0; nDormi4=0; nDormiX=0;
	
			// Cuenta los inscritos
			for (Inscritos inscritos : lisInscritos){			
				if (inscritos.getCarreraId().equals(carrera.getCarreraId())){
					nInsc++;
					if (inscritos.getResidenciaId().equals("I")) nInt++;
				}
			}
	
			if (nInsc > 0 && nInt>0) nPor= nInt*100/nInsc;
	
			for ( EstInternos internos : lisInternos){
				
				if (internos.getCarreraId().equals(carrera.getCarreraId())){	
					if (internos.getDormitorio().equals("1"))
						nDormi1++;
					else if (internos.getDormitorio().equals("2"))
						nDormi2++;
					else if (internos.getDormitorio().equals("3"))
						nDormi3++;
					else if (internos.getDormitorio().equals("4"))					
						nDormi4++;
					else
						nDormiX++;
				}
			}
			nInscF+=nInsc;
			nIntF+=nInt;
			nDormi1F += nDormi1;
			nDormi2F += nDormi2;
			nDormi3F += nDormi3;
			nDormi4F += nDormi4;
			nDormiXF += nDormiX;
	%>
	  <tr class="tr2"> 
	  	<td width="5%" align="left"><strong><%=carrera.getCarreraId()%></strong></td>
	    <td width="34%" align="left"><strong><%=carreraNombre%></strong></td>
	    <td width="5%" style="text-align:left;"><%=nInsc%></td>
	    <td width="5%" style="text-align:left;"><%=nInt%></td>
	    <td width="5%" style="text-align:left;"><%=nPor%></td>
		<td width="8%" style="text-align:left;"><%=nDormi1%></td>
		<td width="8%" style="text-align:left;"><%=nDormi2%></td>
		<td width="8%" style="text-align:left;"><%=nDormi3%></td>
		<td width="8%" style="text-align:left;"><%=nDormi4%></td>
		<td width="8%" style="text-align:left;"><%=nDormiX%></td>
	  </tr>  
	<%	} // fin de for de carreras
	
		// Suma Totales
		nInscT+=nInscF;
		nIntT+=nIntF;
		nDormi1T+=nDormi1F;
		nDormi2T+=nDormi2F;
		nDormi3T+=nDormi3F;
		nDormi4T+=nDormi4F;
		nDormiXT+=nDormiXF;
		//System.out.println("Total:"+nDormiXT);
		if (nInscF > 0) nPorF= nIntF*100/nInscF;
		if (nInscT > 0) nPorT= nIntT*100/nInscT;
	%>  
	  <tr> 
	  	<th colspan="2" width="5%" align="left"><strong> T o t a l <%=fac%></strong></th>
	    <th width="5%" align="left"><%=nInscF%></th>
	    <th width="5%" align="left"><%=nIntF%></th>
	    <th width="5%" align="left"><%=nPorF%></th>
		<th width="8%" align="left"><%=nDormi1F%></th>
		<th width="8%" align="left"><%=nDormi2F%></th>
		<th width="8%" align="left"><%=nDormi3F%></th>
		<th width="8%" align="left"><%=nDormi4F%></th>
		<th width="8%" align="left"><%=nDormiXF%></th>
	  </tr>
	  <tr><td colspan="11" align="right">&nbsp;</td></tr>
	  <tr> 
	  	<th colspan="2" width="5%" align="right"><strong> T O T A L S . . .</strong></th>
	    <th width="5%" align="right"><%=nInscT%></th>
	    <th width="5%" align="right"><%=nIntT%></th>
	    <th width="5%" align="right"><%=nPorT%></th>
		<th width="8%" align="right"><%=nDormi1T%></th>
		<th width="8%" align="right"><%=nDormi2T%></th>
		<th width="8%" align="right"><%=nDormi3T%></th>
		<th width="8%" align="right"><%=nDormi4T%></th>
		<th width="8%" align="right"><%=nDormiXT%></th>
	  </tr>
	</table>
	<br><div align="center">End...&iexcl;&iexcl;</div>
<!-- fin de estructura -->
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>