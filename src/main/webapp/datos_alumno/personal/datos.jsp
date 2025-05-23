<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%
	List<CargaAlumno> lisPlanes = (List<CargaAlumno>) request.getAttribute("lisPlanes");
	List<CatTipoAlumno> lista 	= (List<CatTipoAlumno>) request.getAttribute("lista");

	String codigoPersonal = (String) session.getAttribute("codigoPersonal");//Usuario

	String matricula = (String) session.getAttribute("codigoAlumno");//Alumno
	
	String modalidadUsuario = (String) request.getAttribute("modalidadUsuario");
	String modalidadAlumno = (String) request.getAttribute("modalidadAlumno");
	boolean cargaActiva = (boolean) request.getAttribute("cargaActiva");
	boolean puedeEditar = (boolean) request.getAttribute("puedeEditar");
	String nombreCarrera = (String) request.getAttribute("nombreCarrera");
	String nombreReligion = (String) request.getAttribute("nombreReligion");
	String nombreNacionalidad = (String) request.getAttribute("nombreNacionalidad");
	String nombreInstitucion = (String) request.getAttribute("nombreInstitucion");
	String nombreTipo = (String) request.getAttribute("nombreTipo");
	String mensaje = (String) request.getAttribute("mensaje");
	boolean alumnoInscrito = (boolean) request.getAttribute("alumnoInscrito");
	
	boolean mostrar = true;
	if(modalidadUsuario.equals("0")){
		mostrar = false;	
	}else if(modalidadUsuario.equals(modalidadAlumno)){
		mostrar = false;
	}else{
		matricula = "0000000";
	}
	
	AlumPersonal alumno = (AlumPersonal) request.getAttribute("alumno");
	AlumUbicacion ubicacion = (AlumUbicacion) request.getAttribute("ubicacion");
	AlumAcademico academico = (AlumAcademico) request.getAttribute("academico");
	String edoCivil="";
	if (alumno.getEstadoCivil().equals("S")) edoCivil = "Single";
	else if (alumno.getEstadoCivil().equals("C")) edoCivil = "Married";
	else if (alumno.getEstadoCivil().equals("D")) edoCivil = "Divorced";
	else if (alumno.getEstadoCivil().equals("V")) edoCivil = "Widowed";
	session.setAttribute("matricula",alumno.getCodigoPersonal());
	session.setAttribute("nombre",alumno.getNombre());
	session.setAttribute("apellidos",alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno());
	session.setAttribute("carrera",nombreCarrera.toUpperCase());
	
	// Valida el curp del alumno
	boolean validaCurp = aca.alumno.AlumUtil.validarCurp(alumno.getCurp());
	
	String mensajeCurp	= "";
	if (!alumno.getNacionalidad().equals("91")){
		if (validaCurp)
			mensajeCurp = alumno.getCurp()+" <font color='green'>Correct!</font>";
		else
			mensajeCurp = "<font color='green'>Process it!</font>";
	}else{
		if (validaCurp)
			mensajeCurp = alumno.getCurp()+" <font color='green'>Correct!</font>";
		else
			mensajeCurp = alumno.getCurp()+" <font color= 'red'>�Not Valid!</font>";
	} 
%>
<STYLE TYPE="text/css">	
	#sombra {
		float:left;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, s� haces m�s grandes estos valores, el efecto de sombra es mayor tambi�n */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aqu� es donde ponemos la imagen como fondo colocando su ubicaci�n*/
		
		position:relative;
	} 
	
	#sombra img {
		display:block;
		position:relative;
		top: -1px; /* Desfasamos la imagen hacia arriba */
		left:-2px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
</STYLE>

<body>
<div class="container-fluid">
	<h2>Student Data<small class="text-muted fs-4"> ( <%=alumno.getCodigoPersonal()%> - <%=alumno.getNombre()%> <%=alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno()%> <%=alumno.getApellidoPaterno()%>) </small></h2>
	<hr>
<% if(mensaje.equals("1")){%>
   	<div class="alert alert-success">Saved.</div>   
<% }%>
<%	if(mostrar){ %>
    	<div class="alert alert-warning"><h4><strong>You do not have the correct Mode to view this Student.</strong></h4></div>   
<%	} %>	
	<form action="editarDatos" name="form" method="post">		
    <legend>Personal Data :</legend>
    <table class="table table-sm table-bordered" width="100%" >                                      
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Marital Status:</td>
        <td ONSELECTSTART='return false' nowrap><b><%=edoCivil%></b></td>
        <%session.setAttribute("mat",alumno.getCodigoPersonal());%>
        <td align='center' width="129" rowspan="11" nowrap>
     		<div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="150"></div>
        </td>
    </tr>
    <tr> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Sex:</td>
        <td ONSELECTSTART='return false' nowrap><b> 
          <%if (alumno.getSexo().equals("M")) out.print("Male");else out.print("Female");%>
          </b></td>
    </tr>
    <tr> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Date of Birth:</td>
        <td ONSELECTSTART='return false' nowrap><b><%=alumno.getFNacimiento()%></b></td>
    </tr>
    <tr> 
        <td width="15%" nowrap>Baptized:</td>
        <td nowrap><b> 
          <%if (alumno.getBautizado().equals("S"))out.print("Yes");else out.print("No");%>
        	</b>
        </td>
    </tr>              
    <tr> 
<% 	if(puedeEditar){%>
		<td ONSELECTSTART='return false' width="15%" nowrap>E-mail:</td>
        <td ONSELECTSTART='return false' nowrap><input name="email" type="text" value="<%=alumno.getEmail()%>"></td>
<% 	}else{%>
        <td ONSELECTSTART='return false' width="15%" nowrap>E-mail:</td>
        <td ONSELECTSTART='return false' nowrap><b><%=alumno.getEmail()%></b></td>
<% 	}%>
    </tr>
    <tr> 
<% 			if(puedeEditar){%>
              <td ONSELECTSTART='return false' width="15%" nowrap><spring:message code="aca.Tel"/>Phone Number:</td>
              <td ONSELECTSTART='return false' nowrap><input name="telefono" type="text" value="<%=alumno.getTelefono()%>"></td>
<% 			}else{%>
              <td ONSELECTSTART='return false' width="15%" nowrap><spring:message code="aca.Tel"/>Phone Number:</td>
              <td ONSELECTSTART='return false' nowrap><b><%=alumno.getTelefono() %></b></td>
<% 			}%>
	</tr>
    <tr> 
    	<td ONSELECTSTART='return false' width="15%" nowrap>Curp:</td>
    	<td ONSELECTSTART='return false' nowrap><b><%= mensajeCurp %> </b></td>
    </tr>
    <tr> 
    	<td ONSELECTSTART='return false' width="15%" nowrap>Religion:</td>
    	<td ONSELECTSTART='return false' nowrap><b><%=nombreReligion%></b></td>
    </tr>
    <tr> 
    	<td ONSELECTSTART='return false' width="15%" nowrap>Clasification?</td>
    	<td ONSELECTSTART='return false' nowrap><b><% if(academico.getClasFin().equals("1"))out.print("ACFE"); else out.print("No ACFE"); %></b></td>
    </tr>
    <tr> 
    	<td ONSELECTSTART='return false' width="15%" nowrap>Nationality:</td>
    	<td ONSELECTSTART='return false' nowrap><b><%= nombreNacionalidad %></b></td>
    </tr>
    <tr valign="top">
		<td>Student Type</td>
<% 	String tipoAlum = academico.getTipoAlumno().trim();
	if(tipoAlum.equals("")) tipoAlum = "0"; 
	String inst = "";
	if(tipoAlum.equals("2") || tipoAlum.equals("6")){
		inst = " - "+nombreInstitucion;
	}
%>
<%		
		if(codigoPersonal.equals("1050161") || codigoPersonal.equals("1120329")){%>
		<td class="d-flex">
			<select id="TipoId" name="TipoId" class="form-select" style="width: 300px;">
<% 			for(CatTipoAlumno tipo : lista){%>
				<option value="<%=tipo.getTipoId()%>" <%=tipo.getNombreTipo().equals(nombreTipo) ? "selected" : ""%>><%=tipo.getNombreTipo()%></option>
<% 			}%>
			</select>&nbsp;&nbsp;
			<a class="btn btn-primary" onclick="cambiarTipo();">Save</a>
		</td>
<% 		} else {%>
		<td><b><%=nombreTipo+inst%></b></td>
<% 		}%>
	</tr>
    </table>            
    <legend>Academic Data :</legend>
    <table class="table table-sm table-bordered">
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Active Plans:</td>
<%
	int row = 0;
	String planActivo = "-";
	for (CargaAlumno carga : lisPlanes) {
		row++;
		if (row == 1) planActivo = carga.getPlanId(); else planActivo += "," + carga.getPlanId();
	}
%> 
        <td ONSELECTSTART='return false' nowrap><b><%= planActivo%></b></td>
    </tr>
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Modality:</td>
        <td ONSELECTSTART='return false' nowrap><b><%=academico.getModalidadId()%></b></td>
    </tr>
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Residency:</td>
        <td ONSELECTSTART='return false' nowrap><b> 
          <%if (academico.getResidenciaId().equals("E"))out.print("Day Student"); else out.print("On-Campus"); %>
          </b></td>
    </tr>
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap>Enrolled:</td>
        <td ONSELECTSTART='return false' nowrap><b> 
          <%if (alumnoInscrito){ out.println("Enrolled");}else{ out.println("Not Enrolled");}%>
         </b></td>
    </tr>
    <tr valign="top"> 
        <td ONSELECTSTART='return false' width="15%" nowrap><spring:message code="aca.Dormitorio"/>:</td>
        <td ONSELECTSTART='return false' nowrap><b><%=academico.getDormitorio()%></b></td>
    </tr>
    </table>            
<% 		if(puedeEditar){%>
			<input type="submit" class="btn btn-primary" value="Save">
<% 		}%>
	</form>
</div>
<script type="text/javascript">
	function cambiarTipo(){
		var tipoId = document.getElementById("TipoId").value;
		location.href = "cambiarTipo?TipoId="+tipoId;
	}
</script>
</body>