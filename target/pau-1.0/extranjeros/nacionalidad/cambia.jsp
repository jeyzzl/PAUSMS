<%@ page import= "java.util.List"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar(){	
		document.frmNacionalidad.submit();			
	}
</script>
<% 
	String codigoPersonal		= (String) session.getAttribute("codigoAlumno");
	String paisNombre		 	= (String) request.getAttribute("paisNombre");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	List<CatPais> lisPaises		= (List<CatPais>) request.getAttribute("lisPaises");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h2>Change Nationality<small class="text-muted fs-4"> ( <%=codigoPersonal%> - <%= alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%>)</small></h2>
	<div class="alert alert-info">	
		<%=mensaje.equals("-")?"*":mensaje%>
	</div>		
  	<form id="frmNacionalidad" name="frmNacionalidad" method="post" action="grabar">
  	<h5><label for="Nacionalidad"><b>Registered Nationality:</b> <%=paisNombre%></label> </h5>	
  	<br>
  	<h5><label for="Sexo"><b>Gender:</b> <%=alumPersonal.getSexo().equals("F")?"Female":"Male"%></label>  </h5> 	
  	<br>
  	<h5><label for="Nacimiento"><b>Birthdate:</b> <%=alumPersonal.getFNacimiento()%></label>   </h5>	
  	<br>
  	<h5><label for="Nueva"><b>Change Nationality:</label> </h5>
  	<select name="Nacionalidad" class= "form-select" id="Nacionalidad"  style="width:120px"; "onchange="document.frmAlta.submit();">
<%	for( CatPais pais : lisPaises){ %>
		<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(alumPersonal.getNacionalidad())?" Selected":""%>><%=pais.getNombrePais()%> </option>
<%	} %>
	</select>	
	<br><br>
	<div class="alert alert-info">	
		<a href="javascript:Grabar();" class="btn btn-primary" >Save</a>	
	</div>       
    </form>
    </div>	 
</body>