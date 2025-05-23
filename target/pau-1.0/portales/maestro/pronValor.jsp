<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	aca.pron.spring.PronValor pronValor = (aca.pron.spring.PronValor)request.getAttribute("pronValor");

	String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	boolean envio		= (boolean)request.getAttribute("envio");	
	
	String materia 			= (String)request.getAttribute("materia");	
	String maestroNombre	= (String)request.getAttribute("maestroNombre");

	String completoMateria 	= (String)request.getAttribute("completoMateria");
	String completoUnidades = (String)request.getAttribute("completoUnidades");
	String completoEjes 	= (String)request.getAttribute("completoEjes");
	String completoValores 	= (String)request.getAttribute("completoValores");
	String completoEsquema 	= (String)request.getAttribute("completoEsquema");
	String completoBiblio 	= (String)request.getAttribute("completoBiblio");
%>
<body>
	<div class="container-fluid">
		<h3>Plan de curso<small class="text-muted">( <%=maestroNombre%> - <%=materia%> )</small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
<%		if(completoMateria.equals("Si") && completoUnidades.equals("Si") && completoEjes.equals("Si") && completoValores.equals("Si") && completoEsquema.equals("Si") && completoBiblio.equals("Si")){%>
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronValor">Enviar</a>
<%			if(envio){%>
				<a class="btn btn-primary" href="pdfPron?CursoCargaId=<%=cursoCargaId%>" target="_blank">Imprimir</a>
<%			}%>
<%      }%>
		</div>		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<ul class="nav nav-tabs" role="tablist">
					    <li class="nav-item nav-link"><a href="pronMateria?CursoCargaId=<%=cursoCargaId%>">Materia <%if(completoMateria.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronUnidad?CursoCargaId=<%=cursoCargaId%>">Unidades <%if(completoUnidades.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronEjes?CursoCargaId=<%=cursoCargaId%>">Ejes <%if(completoEjes.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link active"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Valores <%if(completoValores.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEsquema?CursoCargaId=<%=cursoCargaId%>">Esquema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>  
						<li class="nav-item nav-link"><a href="pronBiblio?CursoCargaId=<%=cursoCargaId%>">Bibliograf�a <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					</ul>
				</div>
			</div>
		</nav>	
		<div class="alert alert-secondary">
			<form action="grabaPronValor" method="POST">
				<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
				<div class="row">
					<div class="col-md-12">
						<strong>Amor:<br><br></strong><textarea rows="" cols="" placeholder="Principio fundamental de todos los valores, originado en Dios y trasmitido a trav�s de la relaci�n cotidiana con �l y las acciones de compasi�n que matizan todo el ejercicio profesional y el servicio abnegado". class="form-control" name="Amor"><%=pronValor.getAmor()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Lealtad:<br><br></strong><textarea rows="" cols="" placeholder="Se manifiesta en poner a Dios en primer lugar, en el uso de los talentos al servicio de �l, en el cumplimiento de los deberes de la vida". class="form-control" name="Lealtad"><%=pronValor.getLealtad()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Confianza:<br><br></strong><textarea rows="" cols="" placeholder="Es tener fe en Dios y la certeza de su revelaci�n en la Santa Biblia, en la conducci�n de la vida diaria, y en el aprendizaje". class="form-control" name="Confianza"><%=pronValor.getConfianza()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Reverencia:<br><br></strong><textarea rows="" cols="" placeholder="Reconocer a Dios como Creador y Sustentador del universo. Se expresa en la manera como se habla con �l, en la actitud de servicio a �l, en el comportamiento en cualquier lugar dedicado a la adoraci�n a �l y a la predicaci�n de su Palabra". class="form-control" name="Reverencia"><%=pronValor.getReverencia()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Obediencia:<br><br></strong><textarea rows="" cols="" placeholder="Es escuchar la voz de Dios y cumplir su voluntad en la verdadera adoraci�n y crecimiento espiritual, en el cuidado del cuerpo, en la convivencia con los dem�s". class="form-control" name="Obediencia"><%=pronValor.getObediencia()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Armonia:<br><br></strong><textarea rows="" cols="" placeholder="Es la relaci�n proactiva de paz, de convivencia emp�tica con los padres, con la familia, con los amigos, con los diferentes grupos de la sociedad". class="form-control" name="Armonia"><%=pronValor.getArmonia()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Respeto:<br><br></strong><textarea rows="" cols="" placeholder="Es el reconocimiento del valor y la dignidad de las personas y las obras creadas por Dios. Incluye el cuidado de la naturaleza y el cuidado del medio". class="form-control" name="Respeto"><%=pronValor.getRespeto()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Pureza:<br><br></strong><textarea rows="" cols="" placeholder="Es la fidelidad a la voluntad divina expresada en una vida moral pura, honesta y aut�ntica en el cuidado de la salud, en el desarrollo de las actividades acad�micas, en la relaci�n con la conciencia propia, en la relaci�n con Dios y con los dem�s". class="form-control" name="Pureza"><%=pronValor.getPureza()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Honestidad:<br><br></strong><textarea rows="" cols="" placeholder="Es la actuaci�n �ntegra y con transparencia permanentes. Se refleja en el respeto por la propiedad ajena y en el cuidado por lo que se encomienda en la vida acad�mica, social, espiritual y laboral". class="form-control" name="Honestidad"><%=pronValor.getHonestidad()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Veracidad:<br><br></strong><textarea rows="" cols="" placeholder="Es actuar siempre conforme a la verdad en el marco de la voluntad de Dios. Se manifiesta en la forma de expresarse de los dem�s, ante los dem�s en la transmisi�n de los hechos y del conocimiento". class="form-control" name="Veracidad"><%=pronValor.getVeracidad()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Contentamiento:<br><br></strong><textarea rows="" cols="" placeholder="Actitud de satisfacci�n y gratitud por lo que Dios otorga. Tambi�n es gratitud a los semejantes por la convivencia". class="form-control" name="Contentamiento"><%=pronValor.getContentamiento()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Servicio:<br><br></strong><textarea rows="" cols="" placeholder="Es el despliegue entusiasta de todas las potencialidades del ser para amar en forma activa, abnegada, altruista, cooperativa y compasiva a Dios, a la Iglesia, a la Instituci�n y a la sociedad circundante y mundial". class="form-control" name="Servicio"><%=pronValor.getServicio()%></textarea><br>
					</div>
				</div>
				<input type="submit" class="btn btn-primary" value="Guardar">
			</form>
		</div>
	</div>
</body>
