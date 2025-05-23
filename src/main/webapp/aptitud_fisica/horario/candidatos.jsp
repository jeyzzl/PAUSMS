<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.apFisica.spring.ApFisicaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<body>
<%
	String grupoId      = session.getAttribute("grupoId")==null?"0":(String)session.getAttribute("grupoId");  	
  	String cargaId      = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
    String clave        = request.getParameter("Clave")==null?"0":request.getParameter("Clave");
    
    ApFisicaGrupo grupo	= (ApFisicaGrupo)  request.getAttribute("grupo");
    int registrados		= (int)  request.getAttribute("registrados");
    int disponibles		= Integer.parseInt(grupo.getCupo())-registrados;
    
  	List<AlumnoCurso> lisAlum				 = (List<AlumnoCurso>) request.getAttribute("lisAlum");
  	HashMap<String, String> mapaAlumnos 	 	 = (HashMap<String, String>) request.getAttribute("mapaAlumnos");
  	HashMap<String,String> nombreCarrera     = (HashMap<String,String>) request.getAttribute("nombreCarrera");
%>
<div class="container-fluid">
    <h2>Alumnos <small class="text-muted h5">( Grupo: <%=grupo.getNombreGrupo()%> - Cupo:<%=grupo.getCupo()%> - Registrados:<%=registrados%> - Disponibles: <%=disponibles%>)</small></h2>
    <form name="forma" action="grupo">      
      <%
        int cont = 1;
      %>
      
      <div >
        <div class="alert alert-info">
			<a href="lista?GrupoId=<%=grupoId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>	&nbsp;
			<input type="text" class="input-medium search-query" placeholder="Buscar..." id="buscar">			
        </div>     
        <table class="table table-bordered" id="table">
        <thead>
          <tr>
            <th width="3%">#</th>
            <th width="3%">Agregar</th>
            <th width="4%">Matrícula</th>           
            <th width="12%">Nombre</th>
            <th width="5%">Curso</th>
            <th width="10%">Materia</th>
            <th width="14%">Carrera</th>
          </tr>
        </thead>
<%
	for( AlumnoCurso alumno : lisAlum){	
		
		String alumNombre = "-";
		if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String carNombre  = "-";
		if(nombreCarrera.containsKey(alumno.getCarreraId())){
			carNombre  = nombreCarrera.get(alumno.getCarreraId());
		}
%>
          <tr>
            <td><%=cont++%></td>
            <td>
            	<a href="grabarAlumno?CodigoAlumno=<%=alumno.getCodigoPersonal()%>&CursoCargaId=<%=alumno.getCursoCargaId()%>&CargaId=<%=cargaId%>&Clave=<%=clave%>">
            		<i class="fa fa-plus-circle" aria-hidden="true"></i>            		
            	</a>
            </td>
            <td><%=alumno.getCodigoPersonal() %></td>
            <td><%=alumNombre %></td>
            <td><%=alumno.getCursoCargaId() %></td>
            <td><%=alumno.getNombreCurso() %></td>
            <td><%=carNombre %></td>
          </tr>
        <%  
          }
        %>
      </table>
      </div>  
    </form>
</div>
</body>
<!-- <!-- <script>  -->
<!-- //     function Borrar(grupoId,fecha) { -->
<!-- //       if (confirm("Si elimina este grupo eliminará toda la informacion que contenga el grupo") == true) { -->
<!-- //         document.location.href = "borrar?GrupoId="+ grupoId+"&Fecha="+fecha; -->
<!-- //       } -->
<!-- //     } -->
<!-- //     jQuery('#Fecha').datepicker(); -->
<!-- <!-- </script>  -->
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#table")});
 </script>
</html>
