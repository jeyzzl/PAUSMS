
<%@ page import="aca.emp.spring.EmpDatos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	String codigoPersonal 	= request.getParameter("CodigoPersonal")==null?"0":request.getParameter("CodigoPersonal");
	String empleadoNombre 	= (String)request.getAttribute("empleadoNombre");
	boolean tieneFoto 		= (boolean)request.getAttribute("tieneFoto");
	boolean tieneFotoNormal 		= (boolean) request.getAttribute("tieneFotoNormal");
	
	String dimensiones				= (String) request.getAttribute("dimensiones");
	String dim[]					= dimensiones.split(",");
	String dimRecX[]				= dim[2].split(":");	
	String dimRecY[]				= dim[3].split(":");
	
%>
<head>
	<script type="text/javascript">
		function borrarFoto(codigoPersonal){		
			if (confirm("Are you sure you want to delete this picture?")){
				document.location.href="borrarFoto?Codigo="+codigoPersonal;
			}	
		}
		
		function borrarFotoCuadrada(codigoPersonal, tipo){		
			if (confirm("Are you sure you want to delete this picture?")){
				document.location.href="borrarFoto?Codigo="+codigoPersonal+"&Tipo="+tipo;
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
		float:right;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aquí es donde ponemos la imagen como fondo colocando su ubicación*/		
		position:relative;
		top:0px;
		left:0px;
	} 
	
	#sombra img {
		display:block;
		position:relative;
		top: 0px; /* Desfasamos la imagen hacia arriba */
		left:-2px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
	
</STYLE>

<body style="background-image: url(../../imagenes/fondoContenido.png);"  onload='inicio()'>
	<div class="container-fluid">
		<h2>Employee Picture <small class="text-muted fs-5">(<%=codigoPersonal%> - <%=empleadoNombre%>)</small></h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="../alta/listado">Back</a>&nbsp;&nbsp;
  			<a href="centrar" title="Centrar" class="btn btn-info">Align Photos</a>
		</div>
		<div class="row">			
			<div class="col-3">
				<!-- Imagen -->
				<div>
					<%=dimensiones%><br>
					<img src="../../empFoto?Codigo=<%=codigoPersonal%>&Folio=0" class="rounded" style="border:1px gray solid;" width="200">
				</div>
				<!-- Acciones -->
				<form name="frmRecortar" action="recortarFoto" method="post">
				<div>
					<a href="tomarfoto?CodigoPersonal=<%=codigoPersonal%>" title="Take Picture"><i class="fas fa-camera"></i></a>&nbsp;
					<a href="subir?CodigoEmpleado=<%=codigoPersonal%>" title="Upload Picture"><i class="fas fa-upload"></i></a>&nbsp;
					<a href="../../fotoBajar?Codigo=<%=codigoPersonal%>&Tipo=C" title="Downloead Picture"><i class="fas fa-download"></i></a>&nbsp;
					<input name="Codigo" type="hidden" value="<%=codigoPersonal%>">
					<input name="Tipo" type="hidden" value="C">
					X=
					<input name="X" type="text" value="<%=dimRecX[1]%>" style="width:37px">
					Y=
					<input name="Y" type="text" value="<%=dimRecY[1]%>" style="width:37px">&nbsp;
					<button class="btn btn-primary btn-sm" type="submit"><i class="fas fa-cut"></i></button>&nbsp;
					<a href="javascript:borrarFoto('<%=codigoPersonal%>')" title="Delete Picture"><i class="fas fa-trash"></i></a>			
				</div>
				</form>
				<!-- Imagen Cuadrada -->
				<h5>Small Picture</h5>
				<div>
					<img src="../../fotoCuadrada?Codigo=<%=codigoPersonal%>&Tipo=C" class="rounded" width="100" style="border:1px gray solid;">
				</div>
				<a href="javascript:borrarFotoCuadrada('<%=codigoPersonal%>','C')" title="Delete Picture"><i class="fas fa-trash"></i></a>
			</div>
		</div>
	</div>
</body>