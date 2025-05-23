<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="aca.carga.spring.CargaAlumno"%>

<%	
	String matricula 		= (String)request.getAttribute("matricula");
	boolean inscrito 		= (boolean)request.getAttribute("inscrito");
	String carreraNombre	= (String)request.getAttribute("carreraNombre");
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	String grado 			= (String)request.getAttribute("grado");
	String semestre		 	= (String)request.getAttribute("semestre");
	String residencia	 	= (String)request.getAttribute("residencia");	
	
	// Lista de planes activos
	List<CargaAlumno> lisPlanes 	= (List<CargaAlumno>) request.getAttribute("lisPlanes");
%>
<head>
<style type="text/css">	
	#sombra {
		float:left;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aquí es donde ponemos la imagen como fondo colocando su ubicación*/
		
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
</style>
</head>
<%	
	int row=0;
	String planesActivos = "-";
	for (CargaAlumno cargaAlumno : lisPlanes){
	row++;
	if (row == 1) planesActivos = cargaAlumno.getPlanId(); else planesActivos += "," + cargaAlumno.getPlanId();
}
	String esInscrito = "-";
	if(inscrito) esInscrito = "<span class='badge bg-success'>Yes</span>"; else esInscrito = "<span class='badge bg-warning'>No</span>";
%>
<body>
<div class="container-fluid">
 	<h2>Yearbook/Query</h2>
	<div class="alert alert-info"></div>
	<table width="60%">
		<tr>
			<th></th>
			<th></th>
		</tr>
		<tr>
			<td class="col-md-3 col-md-offset-3"><div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="350"></div></td>
			<td class="col-md-7 col-md-offset-7">
				<table class="table table-condensed">
					<tr height="40">
						<td><b>ID:</b></td>
						<td><%=matricula%></td>
					</tr>
					<tr height="40">
				    	<td><b><spring:message code="aca.Nombre"/>:</b></td>
				        <td><%=nombreAlumno%></td>
				    </tr>
				    <tr height="40">
						<td><b>Degree:</b></td>
						<td><%=carreraNombre%></td>
					</tr>
					<tr height="40">
						<td><b>Active Plans:</b></td>
						<td><%=planesActivos%></td>
					</tr>
					<tr height="40">
						<td><b>Enrolled:</b></td>
						<td><%=esInscrito%></td>
					</tr>
					<tr height="40">
						<td><b>Level:</b></td>
						<td><%=grado%>°</td>
					</tr>	
					<tr height="40">
						<td><b>Semester:</b></td>
						<td><%=semestre%>°</td>
					</tr>
					<tr height="40">
						<td><b>Residence:</b></td>
						<td><%=residencia.equals("E")?"External":"Boarding"%></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>