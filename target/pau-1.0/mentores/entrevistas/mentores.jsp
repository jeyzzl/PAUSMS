<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%	
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");	
	String sPeriodo			= request.getParameter("periodo")==null?(String) session.getAttribute("ciclo"):request.getParameter("periodo");  	
   	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");	
	List<String> lisMentores 					= (List<String>)request.getAttribute("lisMentores");
	HashMap<String,String> mapaEntrevistas 		= (HashMap<String,String>)request.getAttribute("mapaEntrevistas");
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");	
	HashMap<String,String> mapaMentores 		= (HashMap<String,String>)request.getAttribute("mapaMentores");
%>
<div class="container-fluid">
    <h1>Mentor List</h1>
    <form id="forma" name="forma" action="mentores" method="post" id="noayuda">    
    <div class="alert alert-info d-flex align-items-center">
    	<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
    	Cycle:
		<select id="periodo" name="periodo" onchange="document.forma.submit();" class="form-select">
		<%for(CatPeriodo per : lisPeriodos){%>
			<option value="<%=per.getPeriodoId() %>"<%=per.getPeriodoId().equals(sPeriodo)?" Selected":"" %>><%=per.getNombre() %></option>
		<%}%>
  		</select>
	</div>
	</form>
	<table class="table table-condensed" style="widht:50%;"> 	
  	<tr>
  		<th class="th2">#</th>
  		<th class="th2"><spring:message code="aca.Clave"/></th>
  		<th class="th2">Mentor</th>
  		<th class="th2">#Int.</th>
  		<th class="th2">#Students</th>
  	</tr>  
<%
	int totEntrevistas 	= 0;
	int totAlum			= 0;
	for (int i= 0; i<lisMentores.size(); i++){
		String mentor =  lisMentores.get(i);
		
		String mentorNombre = "-";
		if(mapaMentores.containsKey(mentor)){
			mentorNombre = mapaMentores.get(mentor);
		}
		String total = "-";
		if(mapaAlumnos.containsKey(mentor)){
			total = mapaAlumnos.get(mentor);
			totAlum += Integer.parseInt(total);
		}
		
		String entrevistas = "0";
		if(mapaEntrevistas.containsKey(mentor)){
			  entrevistas = mapaEntrevistas.get(mentor);
			  totEntrevistas += Integer.parseInt(entrevistas);
		}
%>		
	<tr> 
  		<td width="10%"><%=i+1%></td>
    	<td width="10%"><a href="entrevistas?MentorId=<%=mentor%>&PeriodoId=<%=sPeriodo%>"><%=mentor%></a></td>
		<td width="70%"><a href="entrevistas?MentorId=<%=mentor%>&PeriodoId=<%=sPeriodo%>"><%=mentorNombre%></a></td>
		<td width="5%" style="text-align:right;"><%=entrevistas%></td>
		<td width="5%" style="text-align:right;"><%=total%></td>
		
  	</tr>
<%	
	}	
%>
	<tr> 
  		<td colspan="3""><b>TOTAL</b></td>
		<td width="5%" style="text-align:right;"><%=totEntrevistas%></td>
		<td width="5%" style="text-align:right;"><%=totAlum%></td>
  	</tr>
	</table>
</div> 