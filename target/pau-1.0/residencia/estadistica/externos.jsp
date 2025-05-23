<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.EstExternos"%>
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
	String modalidades 		= (String) session.getAttribute("modalidadesReportes");
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");	

	String temp				= "X";
	String fac				= "X";
	String carr				= "X";	 
	
	
	/*long tiempoInicio=0;
	long tiempoFinal=0;
	tiempoInicio= System.currentTimeMillis();
	*/
	int i=0,j=0;
	int nInsc=0, nExt=0, nPor=0, sinRazon=0, nPadres=0, nCasados=0, nMayor=0, nRota=0, nEmp=0, nFam=0, nEmpEst=0, nUnid=0, nEmpre=0, nInt=0;
	int nInscF=0, nExtF=0, nPorF=0, sinRazonF=0, nPadresF=0, nCasadosF=0, nMayorF=0, nRotaF=0, nEmpF=0, nFamF=0, nEmpEstF=0, nUnidF=0, nEmpreF=0, nIntF=0;
	int nInscT=0, nExtT=0, nPorT=0, sinRazonT=0, nPadresT=0, nCasadosT=0, nMayorT=0, nRotaT=0, nEmpT=0, nFamT=0, nEmpEstT=0, nUnidT=0,nEmpreT=0, nIntT=0;
	
	List<CatCarrera> lisCarreras				= (List<CatCarrera>) request.getAttribute("lisCarreras");
	List<EstExternos> lisExternos				= (List<EstExternos>) request.getAttribute("lisExternos");
	List<Inscritos> lisInscritos				= (List<Inscritos>) request.getAttribute("lisInscritos");
	
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");		
%>

<div class="container-fluid">
	<h2>Off-campus Students Statistics</h2>
	<form name="forma" action="externos?Accion=1" method="post">
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
	
	<table class="table">
<%	
	for(CatCarrera carrera : lisCarreras){
		
		// Valor temporal de carrera
		carr = carrera.getCarreraId();
		
		// Valor temporal de facultad
		temp = carrera.getFacultadId();
		
		if (!temp.equals(fac)){
			
			if (!fac.equals("X")){
				// Suma Totales
				nInscT+=nInscF;
				nExtT+=nExtF;
				sinRazonT+=sinRazonF;
				nPadresT+=nPadresF;
				nCasadosT+=nCasadosF;
				nMayorT+=nMayorF;
				nRotaT+=nRotaF;
				nEmpT+=nEmpF;
				nFamT+=nFamF;
				nEmpEstT+=nEmpEstF;
				nUnidT+=nUnidF;
				nEmpreT+=nEmpreF;
				nIntT+=nIntF;
				
				if (nInscF > 0) nPorF= nExtF*100/nInscF;
%>
	  	<tr> 
		  	<th colspan="2" align="center"><strong> T o t a l <%=fac%></strong></th>
		    <th align="left"><%=nInscF%></th>
		    <th align="left"><%=nExtF%></th>
		    <th align="left"><%=nPorF%></th>
			<th align="left"><%=nPadresF%></th>
			<th align="left"><%=nCasadosF%></th>
			<th align="left"><%=nMayorF%></th>
			<th align="left"><%=nRotaF%></th>
			<th align="left"><%=nEmpF%></th>
			<th align="left"><%=nFamF%></th>
			<th align="left"><%=nEmpEstF%></th>
			<th align="left"><%=nUnidF%></th>
			<th align="left"><%=nEmpreF%></th>
			<th align="left"><%=nIntF%></th>
			<th align="left"><%=sinRazonF %></th>
	  	</tr>			
<%			
			}
				fac = temp;
				nInscF=0; nExtF=0; nPorF=0; sinRazonF=0; nPadresF=0; nCasadosF=0; nMayorF=0; nRotaF=0; nEmpF=0; nFamF=0; nEmpEstF=0; nUnidF=0; nEmpreF=0; nIntF=0;
	
				//Map Nombre de Facultad
				String nomFacultad	= "-";
				String facultadId	= "-";
				
				if(mapaFacultades.containsKey(carrera.getFacultadId())){
					
					//nomCarrera  = mapCarrera.get(carrera.getCarreraId()).getNombreCarrera();
					facultadId	= mapaCarreras.get(carrera.getCarreraId()).getFacultadId();
					nomFacultad	= mapaFacultades.get(facultadId).getNombreFacultad();
					//carreraId	= carrera.getCarreraId();
					
					//carreraId	= carrera.getCarreraId();
					//nomFacultad = mapFacultad.get(carrera.getCarreraId()).getNombreFacultad() ;			
				}
%>
	</table>
	<br>
	<table class="table">
		<tr><td colspan="16" align="left"><strong>School: <%=fac%>: <%=nomFacultad%></strong></td></tr>
	</table>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
	  	<tr>
		    <th align="left"><spring:message code="aca.Clave"/></th>
		    <th align="left"><spring:message code="aca.Carrera"/></th>
		    <th align="left">Enrr.</th>
		    <th align="left">Off-Campus.</th>
		    <th align="left">%</th>
			<th align="left">[1]Parents</th>
			<th align="left">[2]Married</th>
			<th align="left">[3]Old Enough</th>
			<th align="left">[4]Rotations</th>
			<th align="left">[5]Emp.R.</th>
			<th align="left">[6]Rel.R.</th>
			<th align="left">[7]Emp/Stud</th>
			<th align="left">[8]Unit</th>
			<th align="left">[9]Empre.</th>
			<th align="left">[10]Dorm.S.</th>	
			<th align="left">[0]S/Reason</th>
	  	</tr>
	  </thead>
<%
			}
			nInsc=0; nExt=0; nPor=0; sinRazon=0; nPadres=0; nCasados=0; nMayor=0; nRota=0; nEmp=0; nFam=0; nEmpEst=0; nUnid=0; nEmpre=0; nInt=0;
			
			// Cuenta los inscritos		
			for (Inscritos inscritos : lisInscritos){				
				if (inscritos.getCarreraId().equals(carr)){
					nInsc++;
					if (inscritos.getResidenciaId().equals("E")) nExt++;
				}
			}
			if (nInsc > 0 && nExt>0 ) nPor= nExt*100/nInsc;
			
			for (EstExternos externos : lisExternos){
				
				if (externos.getCarreraId().equals(carr)){
				
					if (externos.getRazon().equals("0"))
						sinRazon++;
					else if (externos.getRazon().equals("1"))
						nPadres++;			
					else if (externos.getRazon().equals("2"))
						nCasados++;
					else if (externos.getRazon().equals("3"))
						nMayor++;
					else if (externos.getRazon().equals("4"))
						nRota++;
					else if (externos.getRazon().equals("5"))
						nEmp++;
					else if (externos.getRazon().equals("6"))
						nFam++;
					else if (externos.getRazon().equals("7"))
						nEmpEst++;
					else if (externos.getRazon().equals("8"))
						nUnid++;
					else if (externos.getRazon().equals("9"))
						nEmpre++;
					else if (externos.getRazon().equals("10"))
						nInt++;
				}
			}
			nInscF+=nInsc;
			nExtF+=nExt;
			sinRazonF+=sinRazon;
			nPadresF+=nPadres;
			nCasadosF+=nCasados;
			nMayorF+=nMayor;
			nRotaF+=nRota;
			nEmpF+=nEmp;
			nFamF+=nFam;
			nEmpEstF+=nEmpEst;
			nUnidF+=nUnid;
			nEmpreF+=nEmpre;
			nIntF+=nInt;
			
			String nombreCarrera = "";
			if ( mapaCarreras.containsKey(carrera.getCarreraId())){
				nombreCarrera = mapaCarreras.get(carrera.getCarreraId()).getNombreCarrera();
			} 
%>  
	  	<tr class="tr2"> 
		  	<td align="left"><strong><%=carrera.getCarreraId()%></strong></td>
		    <td align="left"><strong><%=nombreCarrera%></strong></td>
		    <td style="text-align:left;"><%=nInsc%></td>
		    <td style="text-align:left;"><%=nExt%></td>
		    <td style="text-align:left;"><%=nPor%></td>
			<td style="text-align:left;"><%=nPadres%></td>
			<td style="text-align:left;"><%=nCasados%></td>
			<td style="text-align:left;"><%=nMayor%></td>
			<td style="text-align:left;"><%=nRota%></td>
			<td style="text-align:left;"><%=nEmp%></td>
			<td style="text-align:left;"><%=nFam%></td>
			<td style="text-align:left;"><%=nEmpEst%></td>
			<td style="text-align:left;"><%=nUnid%></td>
			<td style="text-align:left;"><%=nEmpre%></td>
			<td style="text-align:left;"><%=nInt%></td>
			<td style="text-align:left;"><%=sinRazon %></td>
	  	</tr>  
<%	
		}// fin de for de carreras	
	
			// Suma Totales
			nInscT+=	nInscF;
			nExtT+=		nExtF;
			sinRazonT+= sinRazonF;
			nPadresT+=	nPadresF;
			nCasadosT+=	nCasadosF;
			nMayorT+=	nMayorF;
			nRotaT+=	nRotaF;
			nEmpT+=		nEmpF;
			nFamT+=		nFamF;
			nEmpEstT+= 	nEmpEstF;
			nUnidT+=	nUnidF;
			nEmpreT+=	nEmpreF;
			nIntT+=		nIntF;
			if (nInscF > 0) nPorF= nExtF*100/nInscF;
			if (nInscT > 0) nPorT= nExtT*100/nInscT;
%>  
	  	<tr> 
		  	<th colspan="2" align="center"><strong> T o t a l <%=fac%></strong></th>
		    <th align="center"><%=nInscF%></th>
		    <th align="center"><%=nExtF%></th>
		    <th align="center"><%=nPorF%></th>
			<th align="center"><%=nPadresF%></th>
			<th align="center"><%=nCasadosF%></th>
			<th align="center"><%=nMayorF%></th>
			<th align="center"><%=nRotaF%></th>
			<th align="center"><%=nEmpF%></th>
			<th align="center"><%=nFamF%></th>
			<th align="center"><%=nEmpEstF%></th>
			<th align="center"><%=nUnidF%></th>
			<th align="center"><%=nEmpreF%></th>
			<th align="center"><%=nIntF%></th>
			<th align="center"><%=sinRazonF %></th>
	  	</tr>
	  	<tr><td colspan="11" align="center">&nbsp;</td></tr>
	  	<tr> 
		  	<th colspan="2" align="left"><strong> T O T A L S . . .</strong></th>
		    <th align="center"><%=nInscT%></th>
		    <th align="center"><%=nExtT%></th>
		    <th align="center"><%=nPorT%></th>
			<th align="center"><%=nPadresT%></th>
			<th align="center"><%=nCasadosT%></th>
			<th align="center"><%=nMayorT%></th>
			<th align="center"><%=nRotaT%></th>
			<th align="center"><%=nEmpT%></th>
			<th align="center"><%=nFamT%></th>
			<th align="center"><%=nEmpEstT%></th>
			<th align="center"><%=nUnidT%></th>
			<th align="center"><%=nEmpreT%></th>
			<th align="center"><%=nIntT%></th>
			<th align="center"><%=sinRazonT %></th>
		</tr>
	</table>
 <% 
/*tiempoFinal = System.currentTimeMillis();
	System.out.println("Tiempo: " +(tiempoFinal-tiempoInicio));
*/
%>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>