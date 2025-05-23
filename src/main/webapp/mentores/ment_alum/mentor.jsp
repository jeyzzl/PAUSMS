<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

<%@page import="aca.mentores.spring.MentCarrera"%>

<%
	String periodoId	= (String) request.getAttribute("periodoId");
	String carreraId	= (String) request.getAttribute("carreraId");
	String accion		= (String) request.getAttribute("accion");
	String fecha		= (String) request.getAttribute("fecha");
	String mensaje		= request.getParameter("mensaje")==null?"-":request.getParameter("mensaje");
	String facultadId	= (String) request.getAttribute("facultadId");
	
	boolean tieneAcceso = (boolean) request.getAttribute("tieneAcceso");
	
	String nombreCarrera	= (String) request.getAttribute("nombreCarrera");
	String nombreFacultad	= (String) request.getAttribute("nombreFacultad");
	String nombrePeriodo	= (String) request.getAttribute("nombrePeriodo");

	List<MentCarrera> lisMentCarrera = (List<MentCarrera>) request.getAttribute("lisMentCarrera");
	
	HashMap<String,String> mapEnt 			= (HashMap<String, String>) request.getAttribute("mapEntrevistas");
	HashMap<String,String> mapMaestroNombre = (HashMap<String, String>) request.getAttribute("mapMaestroNombre");
	HashMap<String,String> mapaAlumPorMent 	= (HashMap<String, String>) request.getAttribute("mapaAlumPorMent");
	HashMap<String,String> mapaAlumActivos 	= (HashMap<String, String>) request.getAttribute("mapaAlumActivos");	
%>
<div class="container-fluid">
	<h3><%=nombreCarrera%> <small class="text-muted fs-6">( <%=nombreFacultad%> - <%=nombrePeriodo %> | Date: <%=fecha %> )</small></h3>	
<%	if (!mensaje.equals("-")){ out.print(mensaje); }%>	
	<div class="alert alert-info">
		<a href="carrera" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<a href="buscar?carreraId=<%=carreraId %>" class="btn btn-success" ><i class="fas fa-plus"></i> Add Mentor</a>
		<a href="carreraMentor?carreraId=<%=carreraId %>" class="btn btn-info" ><i class="fas fa-search"></i> Search Mentor</a>
	</div>	
	<div class="alert alert-info">Click the<strong> number of students</strong> to add more</div>
	<table class="table table-condensed">
  		<tr>
			<th>#</th>
			<%if(tieneAcceso){%>
				<th>Action</th>
			<%}%>
    		<th>Employee ID</th>
    		<th>Mentor</th>
    		<th class="text-end">Students Assigned</th>
		    <th class="text-end">Total Interviews</th>
		</tr>
		<%
			int totEntrevistas 	= 0;			
			int totAlumnos 		= 0;	 
			
			for(int i= 0; i<lisMentCarrera.size(); i++){
				MentCarrera mentor = lisMentCarrera.get(i);			

				int numAlumnos	= 0;	
				if(mapaAlumPorMent.containsKey(mentor.getMentorId()+facultadId)){
					numAlumnos	= Integer.parseInt(mapaAlumPorMent.get(mentor.getMentorId()+facultadId));
				}
				
				int numActivos	= 0;
				if(mapaAlumActivos.containsKey(mentor.getMentorId()+carreraId)){
					numActivos	= Integer.parseInt(mapaAlumActivos.get(mentor.getMentorId()+carreraId));
				}				
				totAlumnos += numActivos;
				
				String entMentor= "0";
				if (mapEnt.containsKey(mentor.getMentorId())){
					entMentor = mapEnt.get(mentor.getMentorId());
					totEntrevistas += Integer.parseInt(entMentor);
				}			
		
				String sNombre = "";
				
				if(mapMaestroNombre.containsKey(mentor.getMentorId())){
					sNombre = mapMaestroNombre.get(mentor.getMentorId());
				}
		%>
  				<tr> 
					<td><%=i+1 %></td>
					<td>
						<%if( numAlumnos < 1 ){ %>
							<a href="javascript:borrar('<%=mentor.getMentorId() %>','<%=carreraId %>');" class="btn btn-sm btn-danger"><i class="far fa-trash-alt icon-white"></i></a>
						<%} %>
					</td>
				    <td><%=mentor.getMentorId()%></td>
				    <td><%=sNombre%></td>
				    <td style="text-align:right"><a href="mentor_alumno?mentor=<%=mentor.getMentorId() %>&carrera=<%=carreraId %>"><%=numActivos > 0 ? "<span class='badge bg-dark rounded-pill'>"+numActivos+"</span>" : "<span class='badge bg-warning rounded-pill'>"+numActivos+"</span>"%></a></td>
				    <td style="text-align:right">
				    	<a href="totEnt?mentor=<%=mentor.getMentorId() %>&carrera=<%=carreraId %>"><%=entMentor%></a>
				   	</td>    
				</tr>
		<%		
			}
		%>
		<tr>
			<td colspan="4"><h4>TOTAL</h4></td>
			<td style="text-align:right"><h4><%=totAlumnos%></h4></td>
			<td style="text-align:right"><h4><%=totEntrevistas %></h4></td>
		<tr>
	</table>

<script>
	function borrar(mentor, carrera){
		if(confirm("Are you sure to delete the mentor of this degree?")){
			document.location.href = "borrarMentor?MentorId="+mentor+"&CarreraId="+carrera;
		}
	}
</script>

</div>