<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ADatos" class="aca.alerta.AlertaDatos" scope="page" />
<jsp:useBean id="periodoU" class="aca.alerta.AlertaPeriodoUtil" scope="page" />
<jsp:useBean id="ADatosUtil" class="aca.alerta.AlertaDatosUtil" scope="page" />
<jsp:useBean id="historial" class="aca.alerta.AlertaHistorial" scope="page" />
<jsp:useBean id="historialU" class="aca.alerta.AlertaHistorialUtil" scope="page" />
<jsp:useBean id="AntroUtil" class="aca.alerta.AlertaAntroUtil" scope="page" />
<jsp:useBean id="CarreraU" class="aca.catalogo.CatCarreraUtil" scope="page" />
<head>
<script src="../../js/jquery-1.7.1.min.js"></script>
<style>
	body{
		background: white;
	} 
	
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	th{
		padding-right: 30px !important;
	}
</style>
<script>		
	function Mostrar(){		
		document.forma.submit();
	}		
</script>
</head>
<body>

<%
	String periodoId 	= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");

	if(session.getAttribute("periodoSanitaria") == null && periodoId.equals("0")){
		periodoId = aca.alerta.AlertaPeriodoUtil.getPeriodoActual(conEnoc);
		session.setAttribute("periodoSanitaria", periodoId);
	}else if (session.getAttribute("periodoSanitaria") != null && periodoId.equals("0")){
		periodoId = (String)session.getAttribute("periodoSanitaria");
	}
	 
	//System.out.println("Datos:"+periodoId+"-");
	String accion 		= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String msj			= "";
	String peso			= "-";
	String talla		= "-";
	String presion		= "-";
	String medidas		= "-";
	
	ArrayList<aca.alerta.AlertaDatos> datos 		 	= ADatosUtil.getAll(conEnoc, " WHERE PERIODO_ID = '"+periodoId+"' ORDER BY CODIGO_PERSONAL ");	
	ArrayList<aca.alerta.AlertaPeriodo> periodos	 	= periodoU.getAllActivos(conEnoc, " ORDER BY 1 DESC");
	
	java.util.HashMap<String, String> mapEdad 		 	= aca.alerta.AlertaDatosUtil.getMapEdadAlerta(conEnoc, periodoId);
	java.util.HashMap<String, String> mapResidencia  	= aca.alerta.AlertaDatosUtil.getMapResidenciaAlerta(conEnoc, periodoId);
	java.util.HashMap<String, String> mapDormitorio  	= aca.alerta.AlertaDatosUtil.mapDormitorioAlerta(conEnoc, periodoId);
	java.util.HashMap<String, String> mapCiclo  		= aca.alerta.AlertaDatosUtil.mapCicloAlerta(conEnoc, periodoId);
	java.util.HashMap<String, String> mapAlertaAntro	= AntroUtil.mapAlertaAll(conEnoc, periodoId);
	java.util.HashMap<String, String> mapInscritos		= aca.vista.InscritosUtil.getMapaInscritos(conEnoc);
	java.util.HashMap<String, String> mapCarreraAlumno	= aca.alerta.AlertaDatosUtil.mapCarreraActual(conEnoc, periodoId);
	
	java.util.HashMap<String, aca.catalogo.CatCarrera> mapCarrera		= CarreraU.getMapAll(conEnoc, "");
	java.util.HashMap<String, aca.alerta.AlertaHistorial> mapHistorial	= historialU.mapHistorial(conEnoc, periodoId);
%>

<div class="container-fluid">

	<h2><spring:message code="aca.Datos"/></h1>
	
	<%=msj%>
	<form name="forma" action="datos">
		<div class="alert alert-info" align="left">
			<select name="PeriodoId" id="PeriodoId" style="width:300px;" onchange="javascript:Mostrar()">
			<%
				for(aca.alerta.AlertaPeriodo periodo: periodos){
			%>
					<option value="<%=periodo.getPeriodoId()%>" <%if(periodoId.equals(periodo.getPeriodoId()))out.print("selected");%>><%=periodo.getPeriodoNombre()%></option>
			<%
				}
			%>
			</select>
			&nbsp;&nbsp;
			<a href="javascript:tableToExcel('table', 'Movimientos')" style="float:center;" class="btn btn-success"><i class="icon-white fas fa-arrow-down"></i> Bajar Lista a Excel</a>
		</div>
	</form>
	<table class="table table-sm table-bordered table-responsive" id="table">
		<thead>
			<tr>
				<th colspan="15"><spring:message code='aca.Datos'/></th>
				<th colspan="11"><spring:message code='aca.Sintomas'/></th>
				<th colspan="19"><spring:message code='aca.Historial'/></th>
				<th colspan="3"><spring:message code='aca.Antro'/></th>
			</tr>
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Matricula"/></th>
				<th><spring:message code="aca.Nombre"/></th>
				<th><spring:message code='aca.Genero'/></th>
				<th><spring:message code='aca.Inscrito'/></th>
				<th><spring:message code='aca.Carrera'/></th>
				<th><spring:message code="aca.Edad"/></th>
				<th><spring:message code='aca.SemTetra'/></th>
				<th><spring:message code="aca.Residencia"/></th>
				<th><spring:message code='aca.Dormitorio'/></th>
				<th><spring:message code='aca.Direccion'/></th>
				<th><spring:message code='aca.Procedencia'/></th>				
				<th><spring:message code="aca.Correo"/></th>
				<th><spring:message code="aca.Celular"/></th>
				<th><spring:message code="aca.Estado"/></th>
				<th><spring:message code='aca.FiebreEscalofrios'/></th>
				<th><spring:message code='aca.MialgiasDolorMuscular'/></th>
				<th><spring:message code='aca.ErupcionCutanea'/></th>
				<th><spring:message code='aca.MalestarGeneral'/></th>
				<th><spring:message code='aca.NauseasVomitos'/></th>
				<th><spring:message code='aca.Fatiga'/></th>
				<th><spring:message code='aca.Cefalea'/></th>
				<th><spring:message code='aca.Diaforesis'/></th>
				<th><spring:message code='aca.TosExpectoracion'/></th>
				<th><spring:message code='aca.Artralgias'/></th>
				<th><spring:message code='aca.Otro'/></th>
				<th><spring:message code='aca.AlergiaAComidas'/></th>
				<th><spring:message code='aca.¿Cuales?'/></th>
				<th><spring:message code='aca.AlergiaAMedicamentos'/></th>
				<th><spring:message code='aca.¿Cuales?'/></th>
				<th>¿Utilizas algun medicamento por prescripci&oacute;n?</th>
				<th><spring:message code='aca.¿Cuales?'/></th>
				<th><spring:message code='aca.TipoSangreRh'/></th>
				<th>¿Te gustaria ser donador altruista de sangre y que te contactemos en caso de necesidad?</th>
				<th>Enfermedades cr&oacute;nicas</th>
				<th><spring:message code='aca.¿Cuales?'/></th>
				<th>¿Cuando fue la ultima vez que visitaste al dentista?</th>
				<th>¿Cuantas veces te cepillas los dientes</th>
				<th>¿Presentas sangrado en las encias al cepillarte los dientes?</th>
				<th>¿Has experimentado alguna perdida de un diente?</th>
				<th>¿Tienes sensibilidad los dientes ya sea por el frio, lo dulce o al morder?</th>
				<th>¿Has tenido cirugias bucales mayores?</th>
				<th><spring:message code='aca.¿Cuales?'/></th>
<!-- 				<th>¿Te consideras una persona informada en el tema de la salud sexual?</th> -->
<!-- 				<th>¿Desearias recibir informacion de expertos?</th> -->
				<th>Alergias a algun alimento</th>
				<th>Alérgico a insectos</th>
				<th>Alergia a medicamentos</th>
				<th>Alérgico a látex</th>
				<th>¿Alguna vez te ha indicado tu médico, que tienes un problema cardiovascular, y que solamente puedes participar en ejercicios o actividad física bajo su permiso y/o autorización?</th>
				<th>¿Tienes problemas en los huesos o articulaciones (por ejemplo, en la espalda, rodillas o cadera) que pudiera agravarse al aumentar la actividad física?</th>
				<th>¿Existe alguna razón por la cual no deberías participar en un programa de actividad física diario y semanal?</th>
				<th>¿Estas bajo tratamiento (o estuviste en los últimos 6 meses), de un experto en nutrición(incluye entrenador, coach o bariata) que te receta(ó) suplementos ?</th>
				<th>Peso</th>
				<th>Talla</th>
				<th>Presion</th>
			</tr>
		</thead>
	<%
		int cont = 0;
			String genero	 	= "";
			String edad	  		= "";
			String residencia   = "";
			String dormitorio	= "";
			String ciclo 		= "";
		
			for(aca.alerta.AlertaDatos dato : datos){
		cont++;
		 		if(aca.alumno.AlumUtil.getGenero(conEnoc, dato.getCodigoPersonal()).equals("M"))
			genero = "Hombre";
		 		else 
		 			genero = "Mujer";
		 			
		 		if(mapEdad.containsKey(dato.getCodigoPersonal())){
		 			edad = mapEdad.get(dato.getCodigoPersonal());
		 		}
		 			
		 		if(mapResidencia.containsKey(dato.getCodigoPersonal())){
		 			residencia = mapResidencia.get(dato.getCodigoPersonal());
		 		}
		 		if(residencia.equals("E"))
		 			residencia = "Externo";
		 		else
		 			residencia = "Interno";
		 			
		 		if(mapDormitorio.containsKey(dato.getCodigoPersonal())){
		 			dormitorio = mapDormitorio.get(dato.getCodigoPersonal());
		 		}
		 			
		 		if (residencia.equals("Externo")) dormitorio = "-";
		 			
		 		ciclo = "0";
		 		if(mapCiclo.containsKey(dato.getCodigoPersonal())){
		 			ciclo = mapCiclo.get(dato.getCodigoPersonal());
		 		}
		 		String estaInscrito = "NO";
		 		if (mapInscritos.containsKey(dato.getCodigoPersonal()) ){
		 			estaInscrito = "SI";
		 		}
		 			
	 			String carreraId 		= "00000";
	 			String carreraNombre 	= "-";
	 			if (mapCarreraAlumno.containsKey(dato.getCodigoPersonal())){
	 				carreraId = mapCarreraAlumno.get(dato.getCodigoPersonal());
	 				if (mapCarrera.containsKey(carreraId)){
	 					carreraNombre = mapCarrera.get(carreraId).getNombreCarrera();
	 				}
	 			}
	%>
			<tr>
				<td><%=cont %></td>
				<td><%=dato.getCodigoPersonal() %></td>
				<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, dato.getCodigoPersonal(), "NOMBRE") %></td>
				<td><%=genero%></td>
				<td><%=estaInscrito%></td>
				<td><%=carreraNombre%></td>
				<td><%= edad%></td>
				<td><%= ciclo%></td>
				<td><%= residencia%></td>
				<td><%= dormitorio%></td>
				<td><%=dato.getDireccion() %></td>
				<td><%=dato.getProcedencia() %></td>
				<td><%=dato.getCorreo() %></td>
				<td><%=dato.getCelular() %></td>
				<td><%if(dato.getEstado().equals("A")) out.print("Autorizado");else out.print("No Autorizado"); %></td>
				<td><%if(dato.getSintomas().substring(0, 1).equals("1")) out.print("Si"); else out.print("-"); %></td>
				<td><%if(dato.getSintomas().substring(1, 2).equals("1")) out.print("Si");else out.print("-"); %></td>
				<td><%if(dato.getSintomas().substring(2, 3).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(3, 4).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(4, 5).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(5, 6).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(6, 7).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(7, 8).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getSintomas().substring(8, 9).equals("1")) out.print("Si");else out.print("-"); %></td>
				<td><%if(dato.getSintomas().substring(9, 10).equals("1")) out.print("Si"); else out.print("-");%></td>
				<td><%if(dato.getOtro()==null)out.print("&nbsp;"); %></td>
		<%	
				String respuesta 	= "-";
				String comentario1 	= "-";
				//String comentario2 	= "-";
				
				// Pregunta 14 (Alergia a alimentos)
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"14")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"14").getRespuesta();
					respuesta 	= respuesta.equals("S")?"SI":"NO"; 
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"14").getComentario1();					
				}
				out.print("<td>"+respuesta+"</td>");
				out.print("<td>"+comentario1+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 1 (Alergia a medicamentos)
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"1")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"1").getRespuesta();
					respuesta 	= respuesta.equals("S")?"SI":"NO";
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"1").getComentario1();					
				}
				out.print("<td>"+respuesta+"</td>");
				out.print("<td>"+comentario1+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 2 (Medicamento por prescripcion)
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"2")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"2").getRespuesta();
					respuesta 	= respuesta.equals("S")?"SI":"NO";
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"2").getComentario1();					
				}
				out.print("<td>"+respuesta+"</td>");
				out.print("<td>"+comentario1+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 3 (Tipo de sangre)
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"3")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"3").getRespuesta();
					if(respuesta.equals("a")) respuesta = "A+";
					else if(respuesta.equals("b")) respuesta = "A-";
					else if(respuesta.equals("c")) respuesta = "B+";
					else if(respuesta.equals("d")) respuesta = "B-";
					else if(respuesta.equals("e")) respuesta = "AB+";
					else if(respuesta.equals("f")) respuesta = "AB-";
					else if(respuesta.equals("g")) respuesta = "O+";
					else if(respuesta.equals("h")) respuesta = "O-";
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 4 (Donador de sangre)
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"4")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"4").getRespuesta();
					respuesta 	= respuesta.equals("S")?"SI":"NO";
				}
				out.print("<td>"+respuesta+"</td>");
				
				String respuestaTemp = "-";
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 5 (Enfermedades Crónicas  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"5")){
					respuestaTemp 	= mapHistorial.get(dato.getCodigoPersonal()+"5").getRespuesta();
					if(respuestaTemp.contains("a")) respuesta = "Hipertension arterial,";
					if(respuestaTemp.contains("b")) respuesta = "Diabetes Tipo 1," + respuesta;
					if(respuestaTemp.contains("c")) respuesta = "Diabetes Tipo 2," + respuesta;
					if(respuestaTemp.contains("d")) respuesta = "Hipercolesterolemia,";
					if(respuestaTemp.contains("e")) respuesta = "Trastornos sanguineos," + respuesta;
					if(respuestaTemp.contains("f")) respuesta = "Epilepsia," + respuesta;
					if(respuestaTemp.contains("g")) respuesta = "Defectos congenitos," + respuesta;
					if(respuestaTemp.contains("h")) respuesta = "Arritmias," + respuesta;
					if(respuestaTemp.contains("i")) respuesta = "Fiebre reumatica," + respuesta;
					if(respuestaTemp.contains("j")) respuesta = "Asma," + respuesta;
					if(respuestaTemp.contains("k")) respuesta = "Artritis reumatoide," + respuesta;
					if(respuestaTemp.contains("l")) respuesta = "Psoriasis," + respuesta;
					if(respuestaTemp.contains("m")) respuesta = "Lupus eritematoso," + respuesta;
					if(respuestaTemp.contains("n")) respuesta = "Otros," + respuesta;
					if(respuestaTemp.contains("o")) respuesta = "Depresi&oacute;n," + respuesta;
				}
				out.print("<td>"+respuesta+"</td>");
				out.print("<td>"+comentario1+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 6 (Última vista al dentista )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"6")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"6").getRespuesta();
					if (respuesta.equals("a")) respuesta = "1 a&ntilde;o"; 
					else if(respuesta.equals("b")) respuesta = "M&aacute;s de 3 a&ntilde;o"; 
					else if (respuesta.equals("c")) respuesta = "Nunca";
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 7 (Veces que te cepillas los dientes )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"7")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"7").getRespuesta();
					if (respuesta.equals("a")) respuesta = "1 vez"; 
					else if(respuesta.equals("b")) respuesta = "2 veces"; 
					else if (respuesta.equals("c")) respuesta = "3 o mas veces";
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 8 (Presentas sangrado al cepillar )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"8")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"8").getRespuesta();
					if (respuesta.equals("a")) respuesta = "Si"; 
					else if(respuesta.equals("b")) respuesta = "Ocasionalmente"; 
					else if (respuesta.equals("c")) respuesta = "No";
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 9 (Has perdido un diente  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"9")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"9").getRespuesta();
					if (respuesta.equals("a")) respuesta = "Si"; 
					else if(respuesta.equals("b")) respuesta = "No"; 
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 10 (Sensibilidad en dientes  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"10")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"10").getRespuesta();
					if (respuesta.equals("a")) respuesta = "Si"; 
					else if(respuesta.equals("b")) respuesta = "No"; 
				}
				out.print("<td>"+respuesta+"</td>");
				
				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 11 (Cirugías bucales mayores  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"11")){
					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"11").getRespuesta();
					if (respuesta.equals("a")) respuesta = "Si"; 
					else if(respuesta.equals("b")) respuesta = "No"; 
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"11").getComentario1();
				}
				out.print("<td>"+respuesta+"</td>");
				out.print("<td>"+comentario1+"</td>");
				
// 				respuesta 		= "-";
// 				comentario1 	= "-";
// 				// Pregunta 12 (Información tema sexualidad  )
// 				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"12")){
// 					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"12").getRespuesta();
// 					if (respuesta.equals("a")) respuesta = "Si"; 
// 					else if(respuesta.equals("b")) respuesta = "No"; 
// 				}
// 				out.print("<td>"+respuesta+"</td>");
				
// 				respuesta 		= "-";
// 				comentario1 	= "-";
// 				// Pregunta 13 (Recibir información  )
// 				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"13")){
// 					respuesta 	= mapHistorial.get(dato.getCodigoPersonal()+"13").getRespuesta();
// 					if (respuesta.equals("a")) respuesta = "Si"; 
// 					else if(respuesta.equals("b")) respuesta = "No"; 
// 				}
// 				out.print("<td>"+respuesta+"</td>");			
				

				respuesta 		= "-";
				comentario1 	= "-";
				// Pregunta 14 (Aleriga a alimentos  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"14")){
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"14").getComentario1();
				}
				out.print("<td>"+comentario1+"</td>");

				comentario1 	= "-";
				// Pregunta 16 (Aleriga a insectos  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"16")){
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"16").getComentario1();
				}
				out.print("<td>"+comentario1+"</td>");
				
				comentario1 	= "-";
				// Pregunta 1 (Aleriga a medicamentos  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"1")){
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"1").getComentario1();
				}
				out.print("<td>"+comentario1+"</td>");
				
				// Pregunta 18 (Aleriga a latex  )
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"18")){
					respuesta	= mapHistorial.get(dato.getCodigoPersonal()+"18").getRespuesta();
					if(respuesta.equals("N")){
						respuesta = "No";
					}else{
						respuesta = "Si";
					}
				}
				out.print("<td>"+respuesta+"</td>");

				respuesta 		= "-";
				comentario1 	= "-";
				
				// Pregunta 19 
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"19")){
					respuesta	= mapHistorial.get(dato.getCodigoPersonal()+"19").getRespuesta();
					if(respuesta.equals("N")){
						respuesta = "No";
					}else{
						respuesta = "Si";
					}
				}
				out.print("<td>"+respuesta+"</td>");

				respuesta 		= "-";
				
				// Pregunta 20
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"20")){
					respuesta	= mapHistorial.get(dato.getCodigoPersonal()+"20").getRespuesta();
					if(respuesta.equals("N")){
						respuesta = "No";
					}else{
						respuesta = "Si";
					}
				}
				out.print("<td>"+respuesta+"</td>");

				respuesta 		= "-";

				// Pregunta 21
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"21")){
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"21").getComentario1();
				}
				out.print("<td>"+comentario1+"</td>");

				comentario1 	= "-";
				
				// Pregunta 22
				if (mapHistorial.containsKey(dato.getCodigoPersonal()+"22")){
					comentario1	= mapHistorial.get(dato.getCodigoPersonal()+"22").getComentario1();
				}
				out.print("<td>"+comentario1+"</td>");

				respuesta 		= "-";
				comentario1 	= "-";


/*				
				// historial.mapeaRegId(conEnoc, periodoId, dato.getCodigoPersonal(), Integer.toString(i));
				
				if(i==1){
					if(historial.getRespuesta().equals("S")) respuesta = "Si"; else respuesta = "No";
				}else if(i==2){
					if(historial.getRespuesta().equals("S")) respuesta = "Si"; else respuesta = "No";
				}else if(i==3){
					if(historial.getRespuesta().equals("a")) respuesta = "A+";
					else if(historial.getRespuesta().equals("b")) respuesta = "A-";
					else if(historial.getRespuesta().equals("c")) respuesta = "B+";
					else if(historial.getRespuesta().equals("d")) respuesta = "B-";
					else if(historial.getRespuesta().equals("e")) respuesta = "AB+";
					else if(historial.getRespuesta().equals("f")) respuesta = "AB-";
					else if(historial.getRespuesta().equals("g")) respuesta = "O+";
					else if(historial.getRespuesta().equals("h")) respuesta = "O-";
				}else if(i==4){
					if(historial.getRespuesta().equals("S")) respuesta = "Si"; else respuesta = "No";
				}else if(i==5){
					respuesta = "";
					if(historial.getRespuesta().contains("a")) respuesta = "Hipertension arterial," + respuesta;
					if(historial.getRespuesta().contains("b")) respuesta = "Diabetes Tipo 1," + respuesta;
					if(historial.getRespuesta().contains("c")) respuesta = "Diabetes Tipo 2," + respuesta;
					if(historial.getRespuesta().contains("d")) respuesta = "Hipercolesterolemia," + respuesta;
					if(historial.getRespuesta().contains("e")) respuesta = "Trastornos sanguineos," + respuesta;
					if(historial.getRespuesta().contains("f")) respuesta = "Epilepsia," + respuesta;
					if(historial.getRespuesta().contains("g")) respuesta = "Defectos congenitos," + respuesta;
					if(historial.getRespuesta().contains("h")) respuesta = "Arritmias," + respuesta;
					if(historial.getRespuesta().contains("i")) respuesta = "Fiebre reumatica," + respuesta;
					if(historial.getRespuesta().contains("j")) respuesta = "Asma," + respuesta;
					if(historial.getRespuesta().contains("k")) respuesta = "Artritis reumatoide," + respuesta;
					if(historial.getRespuesta().contains("l")) respuesta = "Psoriasis," + respuesta;
					if(historial.getRespuesta().contains("m")) respuesta = "Lupus eritematoso," + respuesta;
					if(historial.getRespuesta().contains("n")) respuesta = "Otros," + respuesta;
					if(historial.getRespuesta().contains("o")) respuesta = "Depresi&oacute;n," + respuesta;
					
					
				}else if(i==6){
					if(historial.getRespuesta().equals("a")) respuesta = "1 a&ntilde;o"; else if(historial.getRespuesta().equals("b")) respuesta = "M&aacute;s de 3 a&ntilde;o"; else respuesta = "Nunca";
				}else if(i==7){
					if(historial.getRespuesta().equals("a")) respuesta = "1 vez"; else if(historial.getRespuesta().equals("b")) respuesta = "2 veces"; else respuesta = "3 o mas veces";
				}else if(i==8){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else if(historial.getRespuesta().equals("b")) respuesta = "Ocasionalmente"; else respuesta = "No";
				}else if(i==9){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else respuesta = "No";
				}else if(i==10){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else respuesta = "No";
				}else if(i==11){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else respuesta = "No";
				}else if(i==12){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else respuesta = "No";
				}else if(i==13){
					if(historial.getRespuesta().equals("a")) respuesta = "Si"; else respuesta = "No";
				}else if(i==14){
					if(historial.getRespuesta().equals("S")) respuesta = "Si"; else respuesta = "No";
				}
				
				// Sección de comentario de las respuestas
				out.print("<td>"+respuesta+"</td>");
				if(i==1){
					out.print("<td>"+historial.getComentario1()+"</td>");
				}else if(i==2){
					out.print("<td>"+historial.getComentario1()+"</td>");	
				}else if(i==5){
					out.print("<td>"+historial.getComentario1()+"</td>");
				}else if(i==14){
					out.print("<td>"+historial.getComentario1()+"</td>");
				}
*/				
		
			medidas = "";
			peso = "0"; talla="0"; presion="0";
			if(mapAlertaAntro.containsKey(dato.getCodigoPersonal())){
				medidas = mapAlertaAntro.get(dato.getCodigoPersonal());
				String[] med = medidas.split("_");
				peso		= med[0];
				talla		= med[1];
				presion		= med[2];
			}
			out.print("<td>" +peso+"</td>");
			out.print("<td>" +talla+"</td>");
			out.print("<td>" +presion+"</td>");
		}
		%>	
				
			</tr>
		
	</table>	
</div>
</body>
<link rel="stylesheet" href="../../js/tablesorter/themes/blue/style.css"/>
<script src="../../js/tableToExcel/tableToExcel.js"></script>
<script src="../../js/tablesorter/jquery.tablesorter.js"></script>
<script src="../../js/search.js"></script>
<script>
	jQuery('#table').tablesorter();

	jQuery('#buscar').search({
		table: jQuery("#table")}
	);
</script>

<%@ include file= "../../cierra_enoc.jsp" %>