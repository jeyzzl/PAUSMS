<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="religion" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="estado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="bOp" scope="page" class="aca.portal.Alumno"/>
<head><link href="../../academico.css" rel="STYLESHEET" type="text/css">
<title>Opciones del Portal</title>
<script>
	function prev(){	
		document.location.href="opciones?color="+document.formaColor.colores.value;
	}
	function irA(){	
		document.location.href="opciones?color="+document.formaColor.colores.value+"&accion=cambiaColor";
	}
</script>
</head>
<%
	String matricula = (String) session.getAttribute("codigoPersonal");
	String cambio = request.getParameter("cambio");

	boolean cambia=false;
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
	String color=request.getParameter("color");
	if(color==null)color="";
	String accion=request.getParameter("accion");
	if(accion==null)accion="";
	if(!color.equals("")){
		if(color.equals("P"))color="";
		if(accion.equals("cambiaColor")){
			cambia=true;
			session.setAttribute("colorPortal",color);
			if(color.equals(""))color="P";
			bOp.guardaColor(conEnoc,matricula,color);
			if(color.equals("P"))color="";
		}
		colorPortal=color;
	}
%>
<body onload='muestraPagina();'>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
		window.parent.window.parent.frames.leftFrame.document.body.style.backgroundColor=document.bgColor;
	</script>
<%if(cambia){%>
	<script>
		parent.tabs.document.location.href=parent.tabs.document.location.href;
		window.parent.window.parent.frames.leftFrame.document.location.reload();
	</script>
<%}%>
<table><tr><td>&nbsp;</td></tr></table>
<table style=" align: 0 auto; width:50%" class="tabla">
  <tr valign="top">
    <td class="encabezadoV" width="2" valign="top"></td>
	<th class="encabezadoV">Tus Opciones:</th>
	<td class="encabezadoV" width="2" valign="top"></td>
  </tr>
  <tr><td colspan='2' align='center'>
     <form name="formaColor">
	   <br><b>Colores:</b><br>
	     <select name="colores" size='16' onclick='prev();'>
		   <option value='P' <%if(colorPortal.equals(""))out.print("Selected");%>>Portal del Alumno</option>
		   <option value='1' <%if(colorPortal.equals("1"))out.print("Selected");%>>Azul Artico</option>
		   <option value='2' <%if(colorPortal.equals("2"))out.print("Selected");%>>Azul Clásico</option>
		   <option value='3' <%if(colorPortal.equals("3"))out.print("Selected");%>>Caqui y Azul</option>
		   <option value='4' <%if(colorPortal.equals("4"))out.print("Selected");%>>Caqui y Verde</option>
		   <option value='5' <%if(colorPortal.equals("5"))out.print("Selected");%>>Dorado</option>
		   <option value='6' <%if(colorPortal.equals("6"))out.print("Selected");%>>Desierto</option>
		   <option value='7' <%if(colorPortal.equals("7"))out.print("Selected");%>>Gris</option>
		   <option value='8' <%if(colorPortal.equals("8"))out.print("Selected");%>>Gris y Naranja</option>
		   <option value='9' <%if(colorPortal.equals("9"))out.print("Selected");%>>Marron</option>
		   <option value='10' <%if(colorPortal.equals("10"))out.print("Selected");%>>Otoño</option>
		   <option value='11' <%if(colorPortal.equals("11"))out.print("Selected");%>>Rosado</option>
		   <option value='12' <%if(colorPortal.equals("12"))out.print("Selected");%>>Verde Oliva y Azul</option>
		   <option value='13' <%if(colorPortal.equals("13"))out.print("Selected");%>>Verde Oliva y Naranja</option>
		   <option value='14' <%if(colorPortal.equals("14"))out.print("Selected");%>>Verde Lima</option>
		   <option value='15' <%if(colorPortal.equals("15"))out.print("Selected");%>>Verde Marino</option>
         </select>
	   <br>
       <input onclick='irA();' type="button" name="boton" value="Guardar"/>
     </form>
   </td></tr>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>