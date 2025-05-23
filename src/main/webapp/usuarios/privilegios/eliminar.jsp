<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.alumno.AlumUtil"%>
<%@ page import= "aca.catalogo.CarreraUtil"%>
<%@ page import= "aca.menu.ModuloUtil"%>
<%@ page import= "aca.menu.OpcionUtil"%>
<%@ page import= "java.util.* "%>

<jsp:useBean id="ModuloU" scope="page" class="aca.menu.ModuloUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="LogOperacion" scope="page" class="aca.log.LogOperacion"/>
<jsp:useBean id="LogOperacionU" scope="page" class="aca.log.LogOperacionUtil"/>


<head>
<script type="text/javascript">
	function Grabar(){
		frmusuario.Accion.value="1";
		frmusuario.submit();
	}
</script>
<script type="text/javascript" src="../../js/qrTag.js"></script>
</head>
<%
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal"); 
	String codigo						= (String) session.getAttribute("codigoUltimo");
	String Numero					    = request.getParameter("Numero")==null?"0":request.getParameter("Numero");	

	if(Numero.equals("1")){		
		String datos = "Usuario modificado:"+codigo + ", Quitar todos los privilegios";
		if (Usuario.borrarPriv(conEnoc, codigo)){
			LogOperacion.setTabla("MODULO_OPCION");
			LogOperacion.setFecha(aca.util.Fecha.getHoy());
			LogOperacion.setIp(request.getRemoteAddr());
			LogOperacion.setDatos(datos);
			LogOperacion.setUsuario(codigoPersonal);
			LogOperacionU.insertReg(conEnoc, LogOperacion);
		}		
		out.print("<div class='alert alert-success'>Borrado...<a class='btn btn-primary' href='usuario'>Regresar</a></div>");
		//response.sendRedirect("usuario");
	}
	
	OpcionUtil opcionU			= new OpcionUtil();
	ArrayList<aca.menu.Opcion> lisOpcion 	= opcionU.getListUser(conEnoc, codigo, "''");	
%>

<%@ include file= "../../cierra_enoc.jsp" %>