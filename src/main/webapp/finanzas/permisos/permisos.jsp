<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.financiero.spring.FinPermiso"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.carga.spring.CargaPermiso"%>
<%@page import="aca.financiero.spring.FinSaldo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">		
	function borrar(matricula, folio){
		if(confirm("¿Está seguro que desea borrar este permiso?")){
			document.location.href = "borrarPermiso?Accion=3&matricula="+matricula+"&folio="+folio;
		}
	}
</script>
<%
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String error 			= request.getParameter("Error")==null?"N":request.getParameter("Error");

	String matricula		= (String) session.getAttribute("codigoAlumno");
	
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");
	String autorizacion		= (String) request.getAttribute("autorizacion");
	FinSaldo finSaldo 		= (FinSaldo) request.getAttribute("finSaldo");
	
	double saldoVencido		= Double.valueOf(finSaldo.getSaldoVencido())*-1;
	String etiqueta 		= saldoVencido>0?"Crédito":"Débito";

	boolean mostrarPermisos = (boolean) request.getAttribute("mostrarPermisos");

	AlumPersonal personal	= (AlumPersonal)request.getAttribute("personal");
	
	ArrayList<FinPermiso> lisPermisos = (ArrayList<FinPermiso>)request.getAttribute("lisPermisos");
	
	HashMap<String, String> mapNombrePermiso = (HashMap<String, String>) request.getAttribute("mapNombrePermiso");

	if(accion.equals("1") && error.equals("N")){	// Guardado		 
%>
	<div class="alert alert-success">
		<h3>Se guard&oacute; correctamente el permiso para <%=personal.getCodigoPersonal() %></h3>
	</div>
<%	} %>
<div class="container-fluid">
	<h2>Permisos Finanzas <small class="text-muted fs-5">( <b><%=matricula%></b> - <%=nombreAlumno%> )</h2></small>
	<div class="alert alert-info">
<%	if(mostrarPermisos){ %>
		<a class="btn btn-info" href="editar"><i class="fas fa-plus"></i> Añadir</a> &nbsp; &nbsp;
		Saldo a la fecha: <%=saldoVencido%> <%=etiqueta%><small class="text-muted fs-6"> ( <%=autorizacion%> )</small>
<%	} else{ %>
		<h4>¡Este alumno no es de su Facultad!</h4>
<%	} %>
	</div>		
<%	if(mostrarPermisos){ %>		
		<table id="table" class="table table-sm table-bordered">
<%	
		if(lisPermisos.size() > 0 ){
%>
		<tr>
			<td align="center">
				<table id="table" class="table table-sm table-bordered">
				<thead class="table-info">	 
					<tr>
						<th>&nbsp;</th>
						<th>F. Inicio</th>
						<th>F. L&iacute;mite</th>
						<th><spring:message code='aca.Usuario'/></th>
						<th><spring:message code="aca.Comentario"/></th>
					</tr>
				</thead>
<%
			for(FinPermiso permiso : lisPermisos){
				
				String nombreEmpleado = "-";
				if(mapNombrePermiso.containsKey(permiso.getUsuario())){
					nombreEmpleado = mapNombrePermiso.get(permiso.getUsuario());
				}
%>
					<tr>
						<td><img title="Eliminar" src="../../imagenes/no.png" onclick="borrar('<%=permiso.getCodigoPersonal()%>','<%=permiso.getFolio()%>');" class="button" /></td>
						<td><%=permiso.getFInicio()%></td>
						<td><%=permiso.getFLimite()%></td>
						<td><%=nombreEmpleado%></td>
						<td><%=permiso.getComentario()%></td>
					</tr>
<%
			}
%>
				</table>
			</td>
		</tr>
<%
		}
	}
%>
	</table>
</div>