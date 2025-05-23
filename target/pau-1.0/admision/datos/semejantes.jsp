<%@ page import= "java.util.List"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<% idJsp="070_a";%>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">	
	function Busca(){
		if(document.alumnos.Nomb.value != 0 & document.alumnos.Pat.value != 0 & document.alumnos.Mat.value != 0)
		{
			document.alumnos.Accion.value="1";
			document.alumnos.submit();
		}
		else
		{
			document.alumnos.Accion.value = "0";
			alert("Fill out the entire form");
		}
	}		
</script>

<!-- inicio de estructura -->
<%	
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	String alumnoNombre		= (String)request.getAttribute("alumnoNombre");
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
 	String nombre 			= "";
 	String paterno 			= "";
 	String materno			= "";
 	if(nAccion == 1){
 		nombre = request.getParameter("Nomb");
 		paterno = request.getParameter("Pat");
 		materno = request.getParameter("Mat");
 	}
%>
<div class="container-fluid">
	<h2>Search <small class="text-muted fs-5">( Student:<%=codigoAlumno%>&nbsp;<%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="alumno" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>
	</div>
	<form name="alumnos" method="post" action="semejantes">
	<input type="hidden" name="Accion">
	
  	<table class="table table-sm" style="width:50%">
	  	<tr>
	  		<td><b>Name :</b></td>
	  		<td><input name="Nomb" type="text" class="text" id="Nomb" size="30" maxlength="50" value="<%=nombre%>"></td>
	  	</tr>	  
	  	<tr>
	  		<td><b>Father's Name :</b></td>
	  		<td><input name="Pat" type="text" class="text" id="Pat" size="30" maxlength="50" value="<%=paterno%>"></td>
	  	</tr>	  
	  	<tr>
	  		<td><b>Mother's Name :</b></td>
	  		<td><input name="Mat" type="text" class="text" id="Mat" size="30" maxlength="50" value="<%=materno%>"></td>
	  	</tr>
	  	<tr>
	  		<th colspan="2" Style="text-align:center"><a href="javascript:Busca()" class="btn btn-primary">Search</a></th>
	  	</tr>
  	</table>	  
 <% 
 	if(nAccion == 1){
 %>
	<table class="table table-sm table-bordered" style="width:50%;">
	<tr class="table-info"> 
    	<th align="center">Similar Names</th>
    </tr>
<%		
		AlumPersonal alumnoP = new AlumPersonal();
		
		List<AlumPersonal> lisAlumnos = (List<AlumPersonal>) request.getAttribute("lisAlumnos");
		
		for(int i = 0; i < lisAlumnos.size();i++){
			alumnoP = (AlumPersonal)lisAlumnos.get(i);
%>	
	<tr>
		<td>
	       	<a href="alumno?Accion=3&matricula=<%=alumnoP.getCodigoPersonal()%>"><%= alumnoP.getCodigoPersonal()+" - "+alumnoP.getNombre()+" "+alumnoP.getApellidoPaterno()+" "+alumnoP.getApellidoMaterno()%></a>
	    </td>
	</tr>    
<% 	
		}
	}
%>
	</table>
	</form>
</div>