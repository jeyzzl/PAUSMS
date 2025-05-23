<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	String cargaId 			= (String) session.getAttribute("cargaId");
	String carreraId		= request.getParameter("carreraId");
	String carreraNombre	= (String) request.getAttribute("carreraNombre");
			
	aca.alumno.AlumUtil alumnoU			= new aca.alumno.AlumUtil();
	aca.plan.PlanUtil planU				= new aca.plan.PlanUtil();
	aca.catalogo.CarreraUtil carreraU	= new aca.catalogo.CarreraUtil();
	aca.catalogo.ModalidadUtil modoU	= new aca.catalogo.ModalidadUtil();
	aca.kardex.ActualUtil actualU		= new aca.kardex.ActualUtil();
	
	Acceso acceso 			= (Acceso)request.getAttribute("acceso");
	
	List<String> lisMeses					= (List<String>)request.getAttribute("lisMeses");
	List<CargaAcademica> lisMaterias		= (List<CargaAcademica>)request.getAttribute("lisMaterias");
	HashMap<String,String> mapaEstrategias	= (HashMap<String,String>)request.getAttribute("mapaEstrategias");
	HashMap<String,String> mapaActividades 	= (HashMap<String,String>)request.getAttribute("mapaActividades");	
	HashMap<String,String> mapaValores 		= (HashMap<String,String>)request.getAttribute("mapaValores");
%>
<div class="container-fluid">
	<h2>Por facultad <small class="text-muted fs-5">(Carga: <%=cargaId%> - <%=carreraNombre%>)</small></h2>
	<div class="alert alert-info">	
		<a href="estrategias" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;		
	</div>
	<form id="noayuda" name="forma" action="estrategias" method='post'>	
	<table style="margin: 0 auto;" class="table table-condensed">
		  <tr>
		  	<th width="2%" class="text-center">#</th>
		    <th width="4%" class="text-center"><spring:message code="aca.Codigo"/></th>
		    <th width="15%" class="text-center"><spring:message code="aca.Maestro"/></th>
		    <th width="5%" class="text-center"><spring:message code="aca.Clave"/></th>
		    <th width="20%" class="text-center"><spring:message code="aca.Materia"/></th>
		<%	for(String mesEnc : lisMeses){	%> 
			<th width="4%" class="text-center">M<%=mesEnc%></th>
<%			} %>			
			<th width="2%" class="text-center">#<spring:message code="analisis.estrategia.Est"/></th>
			<th width="3%" class="text-center">%</th>
			<th width="2%" class="text-center">#<spring:message code="analisis.estrategia.Act"/></th>			
		  </tr>
		  
<%			
	double sumEva=0; 
	int totEval = 0;
	int cont = 0;
	int i = 0;
	for(i=0; i<lisMaterias.size(); i++){
		CargaAcademica cargaAca = (CargaAcademica) lisMaterias.get(i);
		
		String numActiv= "0";
		if (mapaActividades.containsKey(cargaAca.getCursoCargaId())){
			numActiv = mapaActividades.get(cargaAca.getCursoCargaId());
		}
		
		String suma = "0";
		if (mapaValores.containsKey(cargaAca.getCursoCargaId())){
			suma = mapaValores.get(cargaAca.getCursoCargaId());
		}
		
	  	if((acceso.getAccesos().indexOf(cargaAca.getCarreraId())!=-1) || (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))){		
			totEval = 0;
%>
		  <tr class="tr2"> 
		  	<td> <%=++cont %></td>
		    <td class="text-center"><%=cargaAca.getCodigoPersonal()%></td>
		    <td><%=cargaAca.getNombre() %></td>
			<td><%=cargaAca.getCursoId()%></td>
			<td><%=cargaAca.getNombreCurso()%></td>
	<%		String cursoCargaId = cargaAca.getCursoCargaId();
			
			for(String mesDatos : lisMeses){
				String numEst = "0"; 
				if (mapaEstrategias.containsKey(cursoCargaId+mesDatos)){
					numEst = mapaEstrategias.get(cursoCargaId+mesDatos);
					totEval += Integer.parseInt(numEst);
				}
	%>				
				<td class="text-center"><%=numEst%></td>				
	<%		}	%>			
			<td style="text-align:right;"><%=totEval%></td>
			<td style="text-align:right;"><%=suma%></td>
			<td style="text-align:right;"><%=numActiv %></td>			
		  </tr>
<%	  } // Si tiene privilegios de ver la carrera
	  
	} //fin del for
%>
</table>
</form>
</div>	
<!-- fin de estructura -->