<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.cultural.spring.CompAsistencia"%>
<%@page import="aca.cultural.spring.CompAsistenciaAlumno"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String eventoId				= (String)request.getAttribute("eventoId");
	String codigoPersonal		= (String)request.getAttribute("codigoPersonal");		
	String tipo					= request.getParameter("Tipo")==null?"E":request.getParameter("Tipo");
	String size					= request.getParameter("Size")==null?"250px":request.getParameter("Size");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<CompAsistencia> lisEventos 			= (List<CompAsistencia>)request.getAttribute("lisEventos");	
	List<CompAsistenciaAlumno> lisAlumnos		= (List<CompAsistenciaAlumno>)request.getAttribute("lisAlumnos");
	HashMap<String, AlumPersonal> mapaAlumnos 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaInscritos		= (HashMap<String,String>)request.getAttribute("mapaInscritos");
%>
<body>
<div class="container-fluid">
	<h2>Eventos Culturales</h2>
	<form name="frmEvento" action="grabar" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Elegir:&nbsp;
		<select id="EventoId" name="EventoId" class="form-select" style="width:400px;" onchange="javascript:Refrescar();">
<%	for (CompAsistencia asistencia : lisEventos){ %>		
			<option value="<%=asistencia.getEventoId()%>" <%=eventoId.equals(asistencia.getEventoId())?"selected":""%>><%=asistencia.getNombre()%> - <%=asistencia.getFecha()%> - <%=asistencia.getHora()%></option>		
<%	} %>		
		</select>
		&nbsp;&nbsp;		 
		<select id="Tipo" name="Tipo" class="form-select" style="width:120px;" onchange="javascript:Refrescar();">
			<option value="E" <%=tipo.equals("E")?"selected":""%>>Entrada</option>		
			<option value="S" <%=tipo.equals("S")?"selected":""%>>Salida</option>		
		</select>
		&nbsp;&nbsp;		
		<select id="Size" name="Size" class="form-select" style="width:120px;" onchange="javascript:Refrescar();">
			<option value="250px" <%=size.equals("250px")?"selected":""%>>Chica</option>		
			<option value="350px" <%=size.equals("350px")?"selected":""%>>Mediana</option>
			<option value="450px" <%=size.equals("450px")?"selected":""%>>Grande</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="form-control" id="CodigoPersonal" name="CodigoPersonal" placeholder="Matricula" style="width:120px;">
		&nbsp;&nbsp;
		<button class="btn btn-primary" type="submit"><i class="fas fa-save"></i></button>	
		&nbsp;&nbsp;
		<small class="text-mutes fs-6"><%=mensaje.equals("-")?"":mensaje%></small>
	</div>
	</form>
	<div class="row">
		<div class="col-8">
			<div class="row">
				<div class="col-12">
					<form name="frmGrabarLista" action="grabarLista" method="post">
						<input type="hidden" name="EventoId" value="<%=eventoId%>">
						<input type="hidden" name="Tipo" value="<%=tipo%>">
						<input type="hidden" name="Size" value="<%=size%>">
						<div class="alert alert-success d-flex align-items-center">
							Mariculas:&nbsp;
							<textarea class="form-control" name="Codigos" rows="1" cols="200" style="width:300px"></textarea>&nbsp;&nbsp;
							<button class="btn btn-primary" type="submit">Grabar</button>																
						</div>
					</form>
				</div>
				<div class="col-12">
					<table class="table table-bordered">
					<thead class="table-info">
					<tr> 
						<th>#</th>
						<th>Op.</th>
						<th>Codigo</th>
						<th>Nombre</th>		
						<th>Plan</th>		
						<th>Entrada</th>
						<th>Salida</th>		
						<th>¿Inscrito?</th>
					</tr>
					</thead>
					<tbody>
<%
				int row=0;
				for (CompAsistenciaAlumno alumno : lisAlumnos){
					AlumPersonal alumPersonal = new AlumPersonal();
					if(mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
						alumPersonal = mapaAlumnos.get(alumno.getCodigoPersonal());
					}
					String esInscrito = "<span class='badge bg-warning rounded-pill'>NO</span>";
					if (mapaInscritos.containsKey(alumno.getCodigoPersonal())){
						esInscrito = "<span class='badge bg-dark rounded-pill'>SI</span>";
					}
%>
					<tr> 
						<th><%=++row%></th>
						<th><a class="btn btn-danger btn-sm rounded-pill" onclick="borrar('<%=alumno.getCodigoPersonal()%>');"><i class="fas fa-times"></i></a></th>
						<th><%=alumno.getCodigoPersonal()%></th>
						<th><%=alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()%></th>
						<th><%=alumno.getPlanId()%></th>
						<th><%=alumno.getEntradaHora()%></th>
						<th><%=alumno.getSalidaHora()%></th>			
						<th><%=esInscrito%></th>
					</tr>
<%				} %>
					</tbody>
					</table>			
				</div>
			</div>
		</div>
		<div class="col-4">
			<table class="table table-bordered">
			<thead class="table-info">
			<tr> 
				<th class="text-center">				
					Registrados&nbsp;<span class="badge bg-dark rounded-pill fs-6"><%=lisAlumnos.size()%></span>		
				</th>				
			</tr>
			</thead>
			<tbody>
<%		if(lisAlumnos.size() >= 1){%>
			<tr> 
				<td class="text-center">
					<img class="rounded border border-dark" src='../../foto?Codigo=<%=lisAlumnos.get(0).getCodigoPersonal()%>&id=<%=new java.util.Date().getTime()%>' width="<%=size%>">
				</td>				
			</tr>
<% 		}%>
<%		if(lisAlumnos.size() >= 2){%>
			<tr> 
				<td class="text-center">
					<img class="rounded border border-dark" src='../../foto?Codigo=<%=lisAlumnos.get(1).getCodigoPersonal()%>&id=<%=new java.util.Date().getTime()%>' width="<%=size%>">
				</td>				
			</tr>
<%		}%>					
			</tbody>
			</table>			
		</div>
	</div>	
</div>
</body>
<script type="text/javascript">
	function Refrescar(){	
		var eventoId 	= document.getElementById("EventoId").value;
		var tipo 		= document.getElementById("Tipo").value;
		var size 		= document.getElementById("Size").value;
  		document.location.href="listado?EventoId="+eventoId+"&Tipo="+tipo+"&Size="+size;
	}

	function borrar(codigoPersonal){	
		var eventoId 	= document.getElementById("EventoId").value;
		if(confirm("Quieres borrar este alumno?")){
  			document.location.href="borrarAlumno?EventoId="+eventoId+"&CodigoPersonal="+codigoPersonal;
		}
	}
	
	document.frmEvento.CodigoPersonal.focus();
	
</script>
</html>