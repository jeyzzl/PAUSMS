<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>

<%!
	private String getRandomString(){
        String alphaNum="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sbRan = new StringBuffer(11);
        int num;
        for(int i = 0; i < 11; i++){
          num = (int)(Math.random()* (alphaNum.length() - 1));
          sbRan.append(alphaNum.substring(num, num+1));
        }
        return sbRan.toString();
      }
%>
<%
	String codigoPersonal 	= request.getParameter("CodigoPersonal");
	session.setAttribute("codigoAlumno", request.getParameter("Matricula"));
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String nombre			= request.getParameter("Nombre");
	String eventoId			= request.getParameter("EventoId");
	

	
	//Verifica modalidad en linea
	boolean enLinea = false;
	if(!academico.getModalidadId().equals("1") && !academico.getModalidadId().equals("4")){
		enLinea = true;
	}
	
	if(matricula.substring(0,2).equals("98")){
		enLinea = false;
	}
	
	//----------------------------------AUTORIZAR FOTO------------------------------------------------>

	String from = application.getRealPath("/WEB-INF/fotos2/")+"/"+ matricula + ".jpg";
	String to   = application.getRealPath("/WEB-INF/fotos/")+"/"+ matricula + ".jpg";
	
	boolean envio = aca.util.moverArchivo.validateFile(from);
	
	String accion = request.getParameter("Accion")==null?"":request.getParameter("Accion");
	if(accion.equals("1")){//Hacer oficial la foto que envi� el alumno
		
		if(envio){
			aca.util.moverArchivo.fileMove(from,to);
		}
	
	}
	//------------------------Existe foto en foto2 (envi�)-----------
	envio = aca.util.moverArchivo.validateFile(from);
	//------------------------Si viene del listado-----------
	String linkReturn = request.getParameter("return");
	
	boolean back = false; 
	if(linkReturn!=null && linkReturn.equals("true")){
		back=true;
	}
	//-----------------------------------------
	
	
	boolean tieneFoto 			= false; 
	
	// Verifica si existe la imagen	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+matricula+".jpg");
	java.io.File foto = new java.io.File(dirFoto);
	if (foto.exists()){
		tieneFoto = true;
	}
%>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <script type="text/javascript">
	
	function credencial(){
		abrirVentana("credencial",415,295,0,0,"no","yes","yes","no","no","credencial");
	}
		
	function actualizarCredencial(){
		document.location.href="actualizar_credencial";		
	}
			
	function camara(matricula, nombre, eventoId){
		document.location.href="tomarFoto?Matricula="+matricula+"&Nombre="+nombre+"&EventoId="+eventoId;
	}
	
	function borrarFoto(matricula, nombre, eventoId){
		if (confirm("�Estas seguro de borrar la fotografia del alumno?")){
			document.location.href="borrar?Matricula="+matricula+"&Nombre="+nombre+"&EventoId="+eventoId;
		}	
	}
	
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL){
		var sF="";
		TOP = (screen.height - iH)/2-50
		LEFT = (screen.width - iW)/2
		sF+=iW?'width='+iW+',':'';
		sF+=iH?'height='+iH+',':'';
		sF+=R?'resizable='+R+',':'';
		sF+=S?'status='+S+',':'';
		sF+=SC?'scrollbars='+SC+',':'';
		sF+=T?'titlebar='+T+',':'';
		sF+=TB?'toolbar='+TB+',':'';
		sF+=TB?'menubar='+TB+',':'';
		sF+=TOP?'top='+TOP+',':'';
		sF+=LEFT?'left='+LEFT+',':'';
		newwindow = window.open(URL,strName?strName:'',sF)
		newwindow.focus();
	}			
	function refrescar(){
		document.location.href="datos.jsp?x=<%=getRandomString()%>";
	}
	
	function autorizarFoto(){
		if(confirm("Est�s seguro que desea autorizar �sta foto como Oficial? ")==true){
			document.forma.Accion.value="1";
			document.forma.submit();
		}	
	}
</script>	
</head>

<STYLE TYPE="text/css">
	.tabbox{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
	
	#sombra {
		float:center;
		/*padding:0 5px 5px 0; Esta es la profundidad de nuestra sombra, s� haces m�s grandes estos valores, el efecto de sombra es mayor tambi�n */
		/*background: url(../../imagenes/sombra.gif) no-repeat bottom right; Aqu� es donde ponemos la imagen como fondo colocando su ubicaci�n*/
		height: 185px;
		position:relative;
	} 
	
	#sombra img {
		display:block;
		position:relative;
		top: 0px; /* Desfasamos la imagen hacia arriba */
		left: 0px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
</STYLE>

<body style="background-image: url(../../imagenes/fondoContenido.png);"  onload='inicio()'>

<form action="datos.jsp?return=<%=linkReturn%>" method="post" name="forma" target="_self">
<input type="hidden" name="Accion">
<div class="alert alert-info">
	<a href="credencial" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
</div>
<%	
	if(back){%>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="../fotosEnlinea/enLinea.jsp"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		</div>	
<%	}
%>
<div align="center">
	<h1>Datos Personales</h1>	
</div>
<table class="noayuda tabbox" width="100%" height="99%"  cellpadding="10" align="center"  bordercolor="#CCCCCC">
<tr valign="top">
  <td>
  	<% if(back){%>
  		<table style="width:80%; margin:0 auto;">
  			<tr>
  				<td><a class="btn btn-primary" href="../fotosEnlinea/enLinea.jsp"><font size="3">&lsaquo;&lsaquo; Regresar</font></a></td>
  			</tr>
  		</table>
  	<% }%> 
  		<br>
  		<div align="center">
  			<label>Matr�cula <b><%=matricula%></b></label>
  			<% session.setAttribute("mat",matricula);%>
  			<label><spring:message code="aca.Nombre"/> <b><%=nombre%> </b></label>
  			<% if(enLinea){ %>
                	<% if(envio){%>
                	<div id="sombra" align="center">
                		<img src='../../admision/datos/imagenEnviada.jsp?id=<%=getRandomString()%>' width="130">
                	</div>
                	<% }%>
            <% } %>
		
  		</div>
  		<div id="sombra" align="center">
        	<img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="130">
		</div>
		<div align="center" >
			<a href="javascript:camara('<%= matricula%>','<%=nombre%>','<%=eventoId %>')" title="Tomar la Foto"><img src='../../imagenes/camaraweb.png' width="20" ></a>	
			<a href="subir?Accion=1&Matricula=<%= matricula%>&Nombre=<%=nombre%>&EventoId=<%=eventoId %>" title="Subir Foto de un archivo"><img src='../../imagenes/upload.png' width="30"  style="position:relative;top:3px;"></a>
<%			if (tieneFoto){%>                	                	
       			<a href="../../fotoBajar?mat=<%=matricula%>" title="Descargar la Foto"><img src='../../imagenes/descargar.png' width="30" ></a>
   		       	<a href="javascript:borrarFoto('<%=matricula%>','<%=nombre%>','<%=eventoId%>')" title="Borrar la Foto"><img src='../../imagenes/borrar2.gif' width="25" ></a>
<% 			}%>				
		</div>
  </td>
</tr>
</table>
</form>
<table style="width:100%"    align="center"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table>
</body>