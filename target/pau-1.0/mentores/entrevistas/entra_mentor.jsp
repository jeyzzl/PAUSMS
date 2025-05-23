<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.mentores.spring.MentCarrera"%>
<%@ page import="aca.mentores.spring.MentAcceso"%>
<%@ page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	String sPeriodo	 			= (String)session.getAttribute("ciclo");
	String nombreFacultad 		= "-";
	String nombreCarrera 		= "-";
	String carreraId			= request.getParameter("carreraId");
	String facultadId			= request.getParameter("facultadId"); 	
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	
	// Lista de mentores
	List<MentCarrera> lisMenCarrera	= (List<MentCarrera>)request.getAttribute("lisMenCarrera");
	
	//Consigue Numero de Entrevistas por mentor	
	HashMap <String,String> mapaEntrevistas 		= (HashMap<String,String>)request.getAttribute("mapaEntrevistas");
	HashMap <String,String> mapaMaestros			= (HashMap<String,String>)request.getAttribute("mapaMaestros");	
%>
<div class="container-fluid">
	<h3>Mentor Interview <small class="text-muted fs-5">( <%=facultadNombre%> - <%=carreraNombre%>)</small></h3>
	<div class="alert alert-info">
		<a href="entra_facultad" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
   	<tr> 
    	<td colspan ="3"><strong>Message:&nbsp;&nbsp;</strong>Click professor's ID</td>
 	</tr>
	<thead class="table-info">	 
  	<tr> 
	   	<th width="16%"><spring:message code="aca.Clave"/></th>
    	<th width="70%">Mentor <spring:message code="aca.Nombre"/></th>
    	<th width="25%"># Interviews</th>
  	</tr>
  	</thead>
<%	
	for(int i= 0; i<lisMenCarrera.size(); i++){
		MentCarrera mentor = (MentCarrera) lisMenCarrera.get(i);
		int numEntrevista = 0;
		if(mapaEntrevistas.containsKey(mentor.getMentorId()+carreraId)){
			numEntrevista = Integer.parseInt(mapaEntrevistas.get(mentor.getMentorId()+carreraId));
		} 
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(mentor.getMentorId())){
			maestroNombre = mapaMaestros.get(mentor.getMentorId());
		}
%>
	<tr> 
    	<td>
	 	  	<a href="frm_mentor_contacto?&idMentor=<%=mentor.getMentorId() %>&carreraId=<%=carreraId%>">
			<%=mentor.getMentorId() %></a></td>
	    	<td><%=maestroNombre %></td>
			<td style="text-align:left;"><%=numEntrevista%></td>
  	</tr>  
<%	
	} //fin del for	
%>
	</table>
</div>