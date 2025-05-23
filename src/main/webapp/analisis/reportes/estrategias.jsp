<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.HashMap"%>
<%@ page import= "java.util.List"%>

<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%	
	String sCargaId 			= (String) request.getAttribute("cargaId");
	
	int fechaI					= 0;
	int fechaF					= 0;	
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");

	List<Carga> lisCarga 		= (List<Carga>) request.getAttribute("lisCargas");
	List<String> lisFechas 			= (List<String>) request.getAttribute("lisFechas");
	List<CargaAcademica> lisMaterias = (List<CargaAcademica>) request.getAttribute("lisMaterias");
	List<String> lisEvaluacion 	= (List<String>) request.getAttribute("lisEvaluacion");

	HashMap<String, String> mapActividades = (HashMap<String,String>) request.getAttribute("mapActividades");
	HashMap<String,String> mapEval 	= (HashMap<String,String>) request.getAttribute("mapEval");
	HashMap<String,String> mapVirtual	= (HashMap<String,String>) request.getAttribute("mapVirtual");
	HashMap<String,String> mapE42	= (HashMap<String,String>) request.getAttribute("mapE42");
	HashMap<String,String> mapaCarrera	= (HashMap<String,String>) request.getAttribute("mapaCarrera");
	
	aca.plan.PlanUtil planU				= new aca.plan.PlanUtil();
	aca.catalogo.CarreraUtil carreraU	= new aca.catalogo.CarreraUtil();
	aca.catalogo.ModalidadUtil modoU	= new aca.catalogo.ModalidadUtil();
	aca.kardex.ActualUtil actualU		= new aca.kardex.ActualUtil();
	
	String sCarrera			= "x";
	String sBgcolor			= "";	
	String sResultado		= "";
	int i = 0, row = 0;
	
	Carga carga = new Carga();
	
%>
<div class="container-fluid">
<h1>Analisis de Evaluacion</h1>
<form id="noayuda" name="forma" action="estrategias" method='post'>
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="../reportes/menu"><i class="fas fa-arrow-left"></i></a>	
		<spring:message code="aca.Carga"/>: [ <%=sCargaId%> ] 
		<select name="CargaId" onchange='document.forma.submit()' class="input input-xlarge">
<%		for( i=0;i<lisCarga.size();i++){
			carga = lisCarga.get(i);
%>			<option value='<%=carga.getCargaId()%>' <%if (sCargaId.equals(carga.getCargaId()))out.print("selected");%>>
				<%=carga.getNombreCarga()%>
			</option>
<%		}%>
		</select>
	</div>
</form>
	<table style="width:1000;">
<%
	double suma=0, sumEva=0; 
	int j=0,k=0, numEva=0, numEvaTotal = 0, numAct=0;
	String sEva="",fColor="",fColorF="";
	
	for(i = 0; i < lisMaterias.size(); i++){
		CargaAcademica cargaAca =  lisMaterias.get(i);
		
	  if((acceso.getAccesos().indexOf(cargaAca.getCarreraId())!=-1) || (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))){
		suma=0;sEva="";k=0; numEvaTotal = 0; numAct = 0;
		
		if(!sCarrera.equals(cargaAca.getCarreraId())){
			sCarrera = cargaAca.getCarreraId();
			row=0;
			
			String nombreCarrera = "";
			if(mapaCarrera.containsKey(cargaAca.getCarreraId())){
				nombreCarrera = mapaCarrera.get(cargaAca.getCarreraId());
			}
%>
			</table>
			<table class="table table-bordered">
			<thead>
			  <tr><td colspan="14" align="center"><strong><spring:message code="aca.Carrera"/>: <%=cargaAca.getCarreraId()%> - <%=nombreCarrera%></strong></td></tr>
			</thead>
			<thead class="table-info">
			  <tr> 
			  	<th width="2%" align="center"><spring:message code="aca.Numero"/></th>
			    <th width="4%" align="center"><spring:message code="aca.Codigo"/></th>
			    <th width="15%" align="center"><spring:message code="aca.Maestro"/></th>
			    <th width="5%" align="center"><spring:message code="aca.Clave"/></th>
			    <th width="20%" align="center"><spring:message code="aca.Materia"/></th>
			<%
				fechaI = Integer.parseInt(((String)lisFechas.get(0)).trim());
				fechaF = Integer.parseInt(((String)lisFechas.get(1)).trim());
				
				for(j=fechaI; j<=fechaF; j++){
				%>
					<th width="4%" align="center">M<%if(j<10) out.print("0"+j); else out.print(j); %></th>
				<%
				}
			%>
				<th width="4%" align="center"><spring:message code="analisis.estrategia.MXx"/></th>
				<th width="3%" align="center"><spring:message code="analisis.estrategia.Suma"/></th>
				<th width="2%" align="center">#<spring:message code="analisis.estrategia.Est"/></th>
				<th width="2%" align="center">#<spring:message code="analisis.estrategia.Act"/></th>
				<th width="2%" align="center">#<spring:message code="analisis.estrategia.Eva"/></th>
				<th width="2%" align="center"><spring:message code="aca.Virtual"/></th>
				<th width="2%" align="center">E42</th>
			  </tr>
			</thead>
<%		}
		row++;
%>  
		  <tr class="tr2" <%=sBgcolor%>> 
		  	<td align="center"><%=row%></td>
		    <td align="center"><font size="1"><%=cargaAca.getCodigoPersonal()%></font></td>
		    <td><font size="1"><%=cargaAca.getNombre() %></font></td>
			<td><font size="1"><%=cargaAca.getCursoId()%></font></td>
			<td><font size="1"><%=cargaAca.getNombreCurso()%></font></td>
	<%		String cursoCargaId = cargaAca.getCursoCargaId();
			
			for(j=fechaI; j<=fechaF; j++){
				sumEva = 0; numEva = 0; 
				
				for(int l=0; l<lisEvaluacion.size(); l+=4){
					if(cursoCargaId.equals((String)lisEvaluacion.get(l))){
						if(j == Integer.parseInt(((String)lisEvaluacion.get(l+2)).trim())){
							
							sumEva += Double.parseDouble(((String)lisEvaluacion.get(l+1)).trim());
							suma += Double.parseDouble(((String)lisEvaluacion.get(l+1)).trim());
							numEva++;
							numEvaTotal++;
							
							if(mapActividades.containsKey(lisEvaluacion.get(l)+"/"+lisEvaluacion.get(l+3))){
								numAct += Integer.parseInt(mapActividades.get(lisEvaluacion.get(l)+"/"+lisEvaluacion.get(l+3)));
							}
						}
					}
				}
	%>
				<td><font size="1"><%=(int)(sumEva+.5)%>% <%=numEva%></font></td>
	<%		}
			sumEva = 0; numEva = 0; 
			for(int l=0; l<lisEvaluacion.size(); l+=4){
				if(cursoCargaId.equals((String)lisEvaluacion.get(l))){
					if(fechaI >= Integer.parseInt(((String)lisEvaluacion.get(l+2)).trim())&& fechaF <= Integer.parseInt(((String)lisEvaluacion.get(l+2)).trim())){
						sumEva += Integer.parseInt(((String)lisEvaluacion.get(l+1)).trim());
						suma += Integer.parseInt(((String)lisEvaluacion.get(l+1)).trim());
						numEva++;
						numEvaTotal++;
						
						if(mapActividades.get(lisEvaluacion.get(l)+"/"+lisEvaluacion.get(l+3))!=null){
							numAct += Integer.parseInt(mapActividades.get(lisEvaluacion.get(l)+"/"+lisEvaluacion.get(l+3)));
						}
					}
				}
			}
			if((int)(suma+.5)<100){
				fColor = "<font size='1' color='700000'>";
				fColorF="</font>";
			}else{
				fColor="";
				fColorF="";
			}
			
			String numEval = "0";
			if (mapEval.containsKey(cargaAca.getCursoCargaId())){
				numEval = mapEval.get(cargaAca.getCursoCargaId());
			}
			
			String evalVirtual = "NO";
			if (mapVirtual.containsKey(cargaAca.getCursoCargaId())){
				evalVirtual = "SI";
			}
			String evalE42 = "NO";
			if (mapE42.containsKey(cargaAca.getCursoCargaId())){
				evalE42 = "SI";
			}
	%>
			<td><font size="1"><%=(int)(sumEva+.5)%>% <%=numEva%></font></td>
			<td align='center'><%=fColor%><%=(int)(suma+.5)%><%=fColorF%></td>
			<td align='center'><font size="1"><%=numEvaTotal%></font></td>
			<td align='center'><font size="1"><%=numAct %></font></td>
			<td align='center'><font size="1"><%=numEval%></font></td>
			<td align='center'><font size="1"><%=evalVirtual%></font></td>
			<td align='center'><font size="1"><%=evalE42%></font></td>
		  </tr>
<%	  } // Si tiene privilegios de ver la carrera
	  
	} //fin del for
%>
	</table>
</div>
