<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.mentores.spring.MentCarrera"%>
<%@ page import="aca.mentores.spring.MentAcceso"%>
<%@ page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");	
	String sPeriodo				= request.getParameter("periodo")==null?(String) session.getAttribute("ciclo"):request.getParameter("periodo");	
   	String sFacultad			= "X";
   	MentAcceso mentAcceso 		= (MentAcceso)request.getAttribute("mentAcceso");
   	Acceso acceso 				= (Acceso)request.getAttribute("acceso");
   	// Lista de periodos
	List<CatPeriodo> lisPeriodo = (List<CatPeriodo>)request.getAttribute("lisPeriodo");	
   	
   	// Lista de carrera
   	List<CatCarrera> lisCarrera	= (List<CatCarrera>)request.getAttribute("lisCarrera");
   	   	
	HashMap<String,String> mapaMentores 		= (HashMap<String,String>)request.getAttribute("mapaMentores");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	
%>
<div class="container-fluid">
    <h1>Schools and Degrees with Mentors</h1>
    <form id="forma" name="forma" action="entra_facultad" method="post" id="noayuda">    
   	<div class="alert alert-info d-flex align-items-center">
    	<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
    	Cycle:
		<select id="periodo" name="periodo" onchange="document.forma.submit();" class="form-select">
		<%for(CatPeriodo per : lisPeriodo){ %>
			<option value="<%=per.getPeriodoId() %>"<%=per.getPeriodoId().equals(sPeriodo)?" Selected":"" %>><%=per.getNombre() %></option>
		<%}%>
  		</select>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
 	<tr> 
    	<td colspan="3"><strong>Message: Select a degree</strong></td>
  	</tr>  
  	
<%	
	String facultadNombre = "-";
	for (int i= 0; i<lisCarrera.size(); i++){
		CatCarrera Carrera	= (CatCarrera) lisCarrera.get(i);
		if(mentAcceso.getAcceso().indexOf(Carrera.getCarreraId()) != -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){
			if(!sFacultad.equals(Carrera.getFacultadId())){
				sFacultad = Carrera.getFacultadId();
				
				if (mapaFacultades.containsKey(sFacultad)){
					facultadNombre = mapaFacultades.get(sFacultad).getNombreFacultad();
				}
%>
	<thead class="table-info">	 
	<tr> 
    	<th colspan="3"><strong><%=facultadNombre%></strong></th>
  	</tr>
  	</thead>
  	<tr>
  		<th class="th2" colspan="2"><spring:message code="aca.Carrera"/></th>
  		<th class="th2 right" width="15%"># Mentor.</th></tr>
	<%		} //fin del if facultad
			String numMentores = "0";
			if(mapaMentores.containsKey(Carrera.getCarreraId())){
				numMentores = mapaMentores.get(Carrera.getCarreraId());
			}
		
			if(!numMentores.equals("0")){
%>
	<tr> 
  		<td width="5%">&nbsp;</td>
    	<td width="80%"><li><a href="entra_mentor?carreraId=<%=Carrera.getCarreraId()%>">
	  		<%=Carrera.getNombreCarrera()%></a></li>
		</td>
		<td width="15%" style="text-align:right;"><%=numMentores%></td>
  	</tr>
<%	
			}
		}
	} //fin del for facultad
%>
	</table>
</div>	