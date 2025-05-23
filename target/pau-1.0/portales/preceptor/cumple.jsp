<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.internado.spring.IntDormitorio"%>
<%@ page import= "aca.internado.spring.IntAlumno"%>
<%@ page import= "aca.internado.spring.IntCuarto"%>
<%	
	String dormitorioId 	= (String)request.getAttribute("dormitorioId");		
	System.out.println(dormitorioId);
	String mes				= (String) request.getAttribute("mes");	
	String mesNombre		= "";	
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor		= (boolean)request.getAttribute("esPreceptor");
	
	IntDormitorio intDormitorio		 = (IntDormitorio)request.getAttribute("intDormitorio");
	
	List<AlumPersonal> lisAlumnos   		= (List<AlumPersonal>) request.getAttribute("lisAlumnos");
	HashMap<String, IntAlumno> mapaInternos = (HashMap<String,IntAlumno>) request.getAttribute("mapaInternos");
	HashMap<String, IntCuarto> mapaCuartos = (HashMap<String,IntCuarto>) request.getAttribute("mapaCuartos");

	int 	numMes				= Integer.parseInt(mes);
	switch (numMes){
		case 1 : { mesNombre = "January"; 		break; }
		case 2 : { mesNombre = "February";		break; }
		case 3 : { mesNombre = "March"; 		break; }
		case 4 : { mesNombre = "April"; 		break; }
		case 5 : { mesNombre = "May";			break; }
		case 6 : { mesNombre = "June"; 			break; }
		case 7 : { mesNombre = "July"; 			break; }
		case 8 : { mesNombre = "August"; 		break; }
		case 9 : { mesNombre = "September";		break; }
		case 10: { mesNombre = "October"; 		break; }
		case 11: { mesNombre = "November"; 		break; }
		case 12: { mesNombre = "December"; 		break; }
	}	
%>
<style>
	.radio{
		width: 50px;
       	height: 50px;
       	border-radius: 50%;
	}
</style>
<%@ include file="portal.jsp" %>
<body>
<div class="container-fluid"><h3>Birthday <%=intDormitorio.getNombre()%></h3>
	<form action="cumple" method="post" name="frmCumple">	
	<div class="alert alert-info d-flex align-items-center">		
		<select name="Mes" id="Mes" class="form-select" onchange="javascript:document.frmCumple.submit();" style="width:140px">
			<option value="01" <%=mes.equals("01")?"Selected":""%>>January</option>
			<option value="02" <%=mes.equals("02")?"Selected":""%>>February</option>
			<option value="03" <%=mes.equals("03")?"Selected":""%>>March</option>
			<option value="04" <%=mes.equals("04")?"Selected":""%>>April</option>
			<option value="05" <%=mes.equals("05")?"Selected":""%>>May</option>
			<option value="06" <%=mes.equals("06")?"Selected":""%>>June</option>
			<option value="07" <%=mes.equals("07")?"Selected":""%>>July</option>
			<option value="08" <%=mes.equals("08")?"Selected":""%>>August</option>
			<option value="09" <%=mes.equals("09")?"Selected":""%>>September</option>
			<option value="10" <%=mes.equals("10")?"Selected":""%>>October</option>
			<option value="11" <%=mes.equals("11")?"Selected":""%>>November</option>			
			<option value="12" <%=mes.equals("12")?"Selected":""%>>December</option>
		</select>&nbsp;&nbsp;&nbsp;
		<input class="btn btn-primary" name="Buscar" type="submit" id="Buscar" value="<spring:message code="aca.Buscar"/>">		
	</div>
	</form>	
	<table class="table table-sm table-bordered">
	<thead>
	<tr class="table-dark">     
		<th width="5%">Number</th>
		<th width="10%">Student ID</th>	
	    <th width="30%">Name</th>  
	    <th width="10%">Day</th>
	    <th width="20%">Bedroom</th>	    
	    <th width="25%">Picture</th>
	</tr>
	</thead>
	<tbody>	
<%	 int row = 1 ;
	 for (AlumPersonal alumno : lisAlumnos){
		 String diacumple = alumno.getFNacimiento();
		 String cuarto 	= "-";
		 String pasillo = "-"; 
		 if (mapaInternos.containsKey(alumno.getCodigoPersonal())){
			 cuarto = mapaInternos.get(alumno.getCodigoPersonal()).getCuartoId();			 
			 if (mapaCuartos.containsKey(cuarto)){
				 pasillo = mapaCuartos.get(cuarto).getPasillo();
			 }
		 }
%>
	<tr> 
	    <td><%=row++%></td>
	    <td><%=alumno.getCodigoPersonal() %></td>    
	    <td><%=alumno.getNombre()+" "+(alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno()%></td>
	    <td><%=diacumple.substring(8,10)%></td>
	    <td>Bedroom:<span class="badge bg-info rounded-pill"><%=cuarto%></span> Hallway:<span class="badge bg-info rounded-pill"><%=pasillo%></span></td>
	    <td><img src="../../fotoMenu?Codigo=<%=alumno.getCodigoPersonal()%>" class="radio"></td>
	  </tr>
<% }%>  
	</tbody>
	</table>
</div>	
</body>
