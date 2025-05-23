<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="aca.salida.spring.SalClub"%>

<jsp:useBean id="AlumnoU" scope="page" class="aca.salida.SalidaAlumnoUtil"/>
<jsp:useBean id="Alumno" scope="page" class="aca.salida.SalidaAlumno"/>
<jsp:useBean id="AlumnoCar" scope="page" class="aca.alumno.AlumIngreso"/>
<jsp:useBean id="GrupoU" scope="page" class="aca.salida.SalidaGrupoUtil" />
<jsp:useBean id="EstadoU" scope="page" class="aca.alumno.EstadoUtil" />
<jsp:useBean id="CatFacU" scope="page" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean id="CatCarU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="SalClubDao" scope="page" class="aca.salida.spring.SalClubDao" />

<%
	String grupoId		= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");

	aca.salida.spring.SalClub grupo = (aca.salida.spring.SalClub)request.getAttribute("grupo");
	
	List<SalClub> lisClubes 		= (List<SalClub>) request.getAttribute("lisClubes");	
%>
<div class="container-fluid">
	<h2> <a class="btn btn-primary" href="solicitud"><i class="fas fa-arrow-left icon-white"></i></a> Grupos</h2>
	<form action="grabarGrupo" method="post">
	<input type="hidden" name="GrupoId" value="<%=grupoId%>">	
	<div class="alert alert-info">
		<a href="crearGrupo?GrupoId=0" class="btn btn-primary">Nuevo</a>&nbsp;			
		<strong>Grupo:</strong>
		<input type="text" name="GrupoNombre" value="<%=grupo.getGrupoNombre()%>" class="input input-xlarge">
		&nbsp;&nbsp;&nbsp;
		<strong>Alumnos:</strong>
		<input type="text" id="Alumnos" name="Alumnos" value="<%=grupo.getAlumnos()%>" class="input input-xxlarge"  placeholder="Ej. 1060754,1100689" onkeyup="validar();">
		&nbsp;&nbsp;&nbsp;
		<input type="submit" class="btn btn-primary" value="Guardar">
		<strong style="color: red;">* En alumnos debes ingresar unicamente las matriculas separadas por una coma</strong>						
	</div>	
	</form>
	<table class="table table-bordered">
	<tr>
		<th width="5%">Op.</th>
		<th width="5%">#</th>
		<th width="30%">Grupo</th>
		<th width="65%">Miembros</th>
	</tr>
<%
	int row = 0;
	for (SalClub gpo : lisClubes){
		row++;
%>
	<tr>
		<td>
			<a href="crearGrupo?GrupoId=<%=gpo.getGrupoId()%>"><i class="fas fa-pencil-alt"></i></a>&nbsp;
			<a href="javascript:Borrar('<%=gpo.getGrupoId()%>');"><i class="fas fa-trash-alt"></i></a>	
		</td>
		<td><%=row%></td>
		<td><%=gpo.getGrupoNombre()%></td>
		<td><%=gpo.getAlumnos()%></td>
	</tr>
<%		
	}
%>	
	</table>	
</div>
<script type="text/javascript">
	function Borrar(grupoId){
		if (confirm("¿Estás seguro de borrar el grupo?")){
			document.location.href = "borrarGrupo?GrupoId="+grupoId;
		}
	}
	
	 function validar() {
	      let cadena = document.getElementById("Alumnos").value;
	      
	      let tmp = cadena.replace(/,/g,'');

	      const pattern = new RegExp('^[0-9]*$');

          if(!pattern.test(tmp)){ 
	      	alert("Se detecto un caracter incorrecto, por favor eliminalo. Recuerda que solo debes ingresar digitos y el signo de coma.");
          }
      }
</script>