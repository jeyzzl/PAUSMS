<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.apFisica.spring.ApFisicaAlumno"%>
<%@ page import= "aca.apFisica.spring.ApFisicaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<style>
	.radio{
		width: 50px;
       	height: 50px;
       	border-radius: 50%;
	}
</style>
<body>
<%  	
  	String fecha    	= request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha");           
    String grupoId      = request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");    
    //String codigoAlumno = request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
    String mensaje		= "";
    
    ApFisicaGrupo grupo	= (ApFisicaGrupo)  request.getAttribute("grupo");
    
    List<ApFisicaAlumno> lisAlumnos    		= (List<ApFisicaAlumno>) request.getAttribute("lisAlumnos");   	
   	HashMap<String, String> mapaAlumnos 	= (HashMap<String, String>) request.getAttribute("mapaAlumnos"); 
   	HashMap<String,CatCarrera> mapaCarreras = (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
   	int disponibles = Integer.parseInt(grupo.getCupo()) - lisAlumnos.size(); 
%>
 <div class="container-fluid">
 	<h3><%=grupo.getNombreGrupo()%>
 	<small class="text-muted fs-6">(<%=grupo.getInstructor()%> - <%=grupo.getLugar()%> -&nbsp;Cupo <%=grupo.getCupo()%> - Registrados: <%=lisAlumnos.size()%> Disponibles: <%=disponibles%>)</small></h3>   
    <div class="alert alert-info d-flex align-items-center">
    	<a href="grupo?Fecha=<%=fecha%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>&nbsp;&nbsp;&nbsp;&nbsp;
    	<a href="candidatos?CargaId=<%=grupo.getCargas()%>&Clave=<%=grupo.getClave()%>" class="btn btn-success"> Agregar Alumnos</a>
    	&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:170px">
    </div>
<%	
	if (!mensaje.equals("")){
		out.print("<div class='alert alert-info'>"+mensaje+"</div>");
	}
%>             
    <table class="table table-bordered" id="table">
    <thead>
    <tr>
	   	<th width="5%">#</th>
		<th width="5%">#</th>
	    <th width="10%">Foto</th>           
	    <th width="10%">Matricula</th>           
	    <th width="35%">Nombre</th>
	    <th width="35%">Carrera</th>
	    <th width="10%">Inscrito</th>
    </tr>
    </thead>
<%        
	int cont = 0;
	for(ApFisicaAlumno alumno : lisAlumnos){
		
		String alumNombre = "-";		
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String carNombre  = "-";		
		if(mapaCarreras.containsKey(alumno.getCarreraId())){
			carNombre  = mapaCarreras.get(alumno.getCarreraId()).getNombreCarrera();
		}		
		cont++;
%>
    <tr>
    	<td>
    		<a href="javascript:BorrarAlumno('<%=grupoId%>','<%=alumno.getCodigoPersonal()%>')"><i class="fas fa-trash-alt"></i></a>
    	</td>
     	<td><%=cont%></td>
     	<td>
     		<img class="radio border border-dark" src="../../fotoMenu?Codigo=<%=alumno.getCodigoPersonal()%>&Tipo=O" width="125">	
   		</td>
      	<td><%=alumno.getCodigoPersonal()%></td>
      	<td><%=alumNombre %></td>
		<td><%=carNombre%></td> 
      	<td><%=alumno.getFecha()%></td>            
    </tr>
<%  
	}
%>
	</table>        
</div>
</body>
<script>
    function BorrarAlumno(grupoId, alumnoId) {
        if (confirm("¿Estás seguro de eliminar al alumno "+alumnoId+"?") == true) {
          document.location.href = "borrarAlumno?GrupoId="+grupoId+"&CodigoAlumno="+alumnoId; 
        }
    }
</script>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
	$('#Fecha').datepicker();
</script>
</html>
