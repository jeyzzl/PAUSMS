<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	aca.pron.spring.PronEjes pronEjes = (aca.pron.spring.PronEjes)request.getAttribute("pronEjes");

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
		<h3>Course plan<small class="text-muted">( <%=maestroNombre%> - <%=materia%> )</small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="cursos">Return</a>
<%		if(completoMateria.equals("Si") && completoUnidades.equals("Si") && completoEjes.equals("Si") && completoValores.equals("Si") && completoEsquema.equals("Si") && completoBiblio.equals("Si")){%>
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronEjes">Send</a>
<%			if(envio){%>
				<a class="btn btn-primary" href="pdfPron?CursoCargaId=<%=cursoCargaId%>" target="_blank">Print</a>
<%			}%>
<%      }%>
		</div>		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<ul class="nav nav-tabs" role="tablist">
					    <li class="nav-item nav-link"><a href="pronMateria?CursoCargaId=<%=cursoCargaId%>">Subject <%if(completoMateria.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronUnidad?CursoCargaId=<%=cursoCargaId%>">Units <%if(completoUnidades.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link active"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Axes <%if(completoEjes.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronValor?CursoCargaId=<%=cursoCargaId%>">Principles <%if(completoValores.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEsquema?CursoCargaId=<%=cursoCargaId%>">Schema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronBiblio?CursoCargaId=<%=cursoCargaId%>">Bibliography <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					</ul>
				</div>
			</div>
		</nav>
		<div class="alert alert-secondary">
			<form action="grabaPronEjes" method="POST">
				<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
				<div class="row">
					<div class="col-md-12">
						<strong>Integration of faith and learning:<br><br></strong><textarea rows="" cols="" placeholder="The biblical foundation of the different areas of knowledge and professional practice. The resolution of the apparent conflict between science and the Bible by considering God as the author of both. It is reflected in learning, research and the application of knowledge". class="form-control" name="Fe"><%=pronEjes.getFe()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Critical thinking:<br><br></strong><textarea rows="" cols="" placeholder="The resolution of ethical dilemmas and values from a biblical perspective and their argumentation and dissemination. It is reflected in the evaluation of knowledge, the implications of a thought or action and wise decision making". class="form-control" name="Pensamiento"><%=pronEjes.getPensamiento()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Care of the environment:<br><br></strong><textarea rows="" cols="" placeholder="Evidenced in the ability to respect the environment and, from all professional areas, to take the necessary actions to use natural resources wisely". class="form-control" name="Ambiente"><%=pronEjes.getAmbiente()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Leadership:<br><br></strong><textarea rows="" cols="" placeholder="Evidenced in the performance as a role model in behaviors and project implementation in the various areas of training". class="form-control" name="Liderazgo"><%=pronEjes.getLiderazgo()%></textarea><br>
					</div>
					
					<div class="col-md-12">
						<strong>Enterprise:<br><br></strong><textarea rows="" cols="" placeholder="Evidenced in the development of professional practices, the attention to the needs of the community from the profession, and the initiative in the personal integral formation of the student". class="form-control" name="Emprendimiento"><%=pronEjes.getEmprendimiento()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Sustainability:<br><br></strong><textarea rows="" cols="" placeholder="Projection of the financing of the student's entire educational project, using his talents and potential to obtain, through productive and creative work, the means to cover the costs of his education". class="form-control" name="Sustentabilidad"><%=pronEjes.getSustentabilidad()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Selfless service:<br><br></strong><textarea rows="" cols="" placeholder="Evidenced by the kind and generous attitude of offering knowledge, time and resources to assist those most in need". class="form-control" name="Servicio"><%=pronEjes.getServicio()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Investigation:<br><br></strong><textarea rows="" cols="" placeholder="Constant initiative to know in depth the needs of the community and the church and to explore creative alternatives, aligned with biblical principles, for their solution". class="form-control" name="Investigacion"><%=pronEjes.getInvestigacion()%></textarea><br>
					</div>
				</div>
				<input type="submit" class="btn btn-primary" value="Save">
			</form>
		</div>
	</div>
</body>