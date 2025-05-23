<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.mentores.spring.MentAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String periodoNombre					= (String)request.getAttribute("periodoNombre");
	List<String> lisAlumnos					= (List<String>) request.getAttribute("lisAlumnos");
	List<MentAlumno> listMent				= (List<MentAlumno>) request.getAttribute("lisAconsejados");	
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>)request.getAttribute("mapaMaestros");	
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	int cont = 0;	
%>

<div class="container-fluid">
	<h2>Alumnos que cambiaron de Mentor <small class="text-muted fs-4">(periodo <%=periodoNombre%>)</small></h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
  	<tr> 
    	<th width="1%" align="center"><spring:message code="aca.Numero"/></th>
    	<th width="5%"><spring:message code="aca.Matricula"/></th>
    	<th width="20%"><spring:message code="aca.Alumno"/></th>
    	<th width="20%">Mentor 1</th>
    	<th width="20%">Mentor 2</th>
    	<th width="20%">Mentor 3</th>    
  	</tr>
  	</thead>
<%	String alumno = "";
	
	for(int i=0; i<lisAlumnos.size(); i++){
		alumno = lisAlumnos.get(i);
		String mentor1="-", mentor2="-", mentor3="-";
		String nomMentor1="-", nomMentor2="-", nomMentor3="-";
		String fecha1="", fecha2="", fecha3="";
		String disp1="-", disp2="-", disp3="-";
		for( int j = 0; j<listMent.size(); j++){
			
			if (alumno.contains(listMent.get(j).getCodigoPersonal())){
				
				if(mentor1.equals("-")){
					mentor1 = listMent.get(j).getMentorId();
					fecha1 	= listMent.get(j).getFecha();
					

				}else if(!mentor1.equals(listMent.get(j).getCodigoPersonal())){
					mentor2 = listMent.get(j).getMentorId();
					fecha2 	= listMent.get(j).getFecha();

				} else if(!mentor1.equals(listMent.get(j).getCodigoPersonal()) || !mentor2.equals(listMent.get(j).getCodigoPersonal())){
					mentor3 = listMent.get(j).getMentorId();
					fecha3 	= listMent.get(j).getFecha();

				}
			}
		}
		
		if (mapaMaestros.containsKey(mentor1)){
			nomMentor1 	= mapaMaestros.get(mentor1); 
			disp1		= "("+fecha1+")" +" - "+ mapaMaestros.get(mentor1);			
		}
		
		if (mapaMaestros.containsKey(mentor2) && !mapaMaestros.get(mentor2).equals(nomMentor1) ){
			nomMentor2 	= mapaMaestros.get(mentor2); 
			disp2		= "("+fecha2+")" +" - "+ mapaMaestros.get(mentor2);			
		}
		
		if (mapaMaestros.containsKey(mentor3) && !mapaMaestros.get(mentor3).equals(nomMentor2)){
			nomMentor3 	= mapaMaestros.get(mentor3); 
			disp3		= "("+fecha3+")" +" - "+ mapaMaestros.get(mentor3);
		}
		
		String alumnoNombre	= "-";
		if (mapaAlumnos.containsKey(alumno)){
			alumnoNombre = mapaAlumnos.get(alumno);
		}

		if (!disp2.equals("-")){
%>
	<tr> 
    	<td align="center"><%=++cont%></td>
    	<td align="center"><%=lisAlumnos.get(i) %></td>
    	<td><%=alumnoNombre%></td> 
    	<td><%=disp1%></td> 	
    	<td><%=disp2%></td> 	
    	<td><%=disp3%></td> 	
	</tr> 
 <%
		}
	}
%> 
	</table>
</div>