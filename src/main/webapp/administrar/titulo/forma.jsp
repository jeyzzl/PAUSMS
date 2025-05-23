<%String institucion = (String)session.getAttribute("institucion"); %>
<html>
<head>
	<title>Formato Titulo de Suficiencia</title>		
</head>
<body>	
	<img src="../../imagenes/logo_small.jpg" alt="">
		<div style="align:0 auto">
			<h1><%=institucion.toUpperCase()%></h1>
			<h2>Acta de Examen a título de suficiencia</h2>
		</div>
		<b>Carrera:<br>
		Especialidad:<br>
		Nombre de la Materia:<br></b>
		En la Ciudad de Montemorelos, Nuevo Leon, Mexico, siendo los _____ dias del mes de _____________ del dos mil________.
		Se levanto la presente acta en donde se consignan la(s) calificacion (es) obtenidas por:<br>
		<div style="align:0 auto">
		<b>Estudiante:<br>
		No. de Matricula:<br>
		Grado:<br></b>
		</div>
		y que corresponde al semestre   1°  2°  del ciclo escolar __________________.
		
		<div style="align:0 auto"><h3>INTEGRANTES DEL JURADO</h3>
		<b>Presidente:<br>
		Secretario:<br>
		Miembro:<br></b></div>
		<br>
		<br>
		<table style="text-align: left; width: 100%;" border="1">
  			<tbody>
    			<tr align="center">
      				<td><span style="font-weight: bold;">Comentarios de Jurado</span></td>
    			</tr>
    			<tr>
      			<td><br>
      			<br>
      			<br>
      			<br>
      			<br>
      			<br>
      			<br>
      			<br>
      			<br>
      			<br>
			Calificacion:_______________________ &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
			&nbsp; &nbsp;Firma del Presidente</td>
    			</tr>
  			</tbody>
		</table>
	<div style="align:0 auto"><i>Favor de proveer datos completos y exactos, evitando las tachaduras</i></div>				
</body>
</html>