<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.FinTabla"%>
<%@page import="aca.plan.spring.MapaCursoPre"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.candado.spring.Candado"%>
<%@page import="aca.candado.spring.CandTipo"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
	<body>
<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String formaPago 			= request.getParameter("sltFormaPago")==null?"P":request.getParameter("sltFormaPago");
	String candidato			= request.getParameter("sltCandidato")==null?"N":request.getParameter("sltCandidato");
	String eventoId				= request.getParameter("sltEvento")==null?"0":request.getParameter("sltEvento");
	String dedicatoria			= request.getParameter("txtDedicatoria")==null?"":request.getParameter("txtDedicatoria");
	
	String accion 				= (String)session.getAttribute("accion");
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	
	String msj					= (String)request.getAttribute("msj");	
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String periodoId 			= (String)request.getAttribute("periodoId");
	String cargaId	 			= (String)request.getAttribute("cargaId");
	String bloqueId 			= (String)request.getAttribute("bloqueId");
	String periodoNombre		= (String)request.getAttribute("periodoNombre");
	String cargaNombre			= (String)request.getAttribute("cargaNombre");
	String bloqueNombre			= (String)request.getAttribute("bloqueNombre");
	String tipoAlumno			= (String)request.getAttribute("tipoAlumno");	
	boolean autorizado			= (boolean)request.getAttribute("autorizado");
	String dias					= (String)request.getAttribute("dias");
	
	boolean autorizaExt			= true;
	
	int days					= (int)request.getAttribute("days");		
	AlumPlan alumPlan			= (AlumPlan)request.getAttribute("plan");
	AlumPersonal personal		= (AlumPersonal)request.getAttribute("personal");
	AlumAcademico academico		= (AlumAcademico)request.getAttribute("academico");	
	MapaPlan mapaPlan			= (MapaPlan)request.getAttribute("mapaPlan");	
	
	CandTipo tipo 				= new CandTipo();
	
	ArrayList<KrdxCursoAct> lisCurso					= (ArrayList<KrdxCursoAct>)request.getAttribute("lisCurso");
	ArrayList<MapaCurso> lisMaterias					= (ArrayList<MapaCurso>)request.getAttribute("lisMaterias");
	ArrayList<AlumEvento> lisEventos					= (ArrayList<AlumEvento>)request.getAttribute("lisEventos");
	ArrayList<CandAlumno> lisCandados					= (ArrayList<CandAlumno>)request.getAttribute("lisCandados");
	
	HashMap<String,String> mapaMaestros					= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,CatModalidad> mapaModalidades		= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,MapaCurso> mapaCurso 				= (HashMap<String,MapaCurso>)request.getAttribute("mapaCurso");
	HashMap<String,Candado> mapaCandados				= (HashMap<String,Candado>)request.getAttribute("mapaCandados");		
	HashMap<String,CandTipo> mapaCanTipo				= (HashMap<String,CandTipo>)request.getAttribute("mapaCandTipo");
	HashMap<String,FinTabla> mapaCostos 				= (HashMap<String,FinTabla>)request.getAttribute("mapaCostos");
	
	String internado 	= "0";
	if ( mapaCostos.containsKey(cargaId+mapaPlan.getCarreraId()+academico.getModalidadId()) ){
		internado = mapaCostos.get(cargaId+mapaPlan.getCarreraId()+academico.getModalidadId()).getInternado();
	}
	
	float total = Float.parseFloat(internado);
	total = total*Float.parseFloat(dias);
	
	String muestraTotal = formato.format(total);
	
	if(autorizaExt==false){
%>
	<script>
		window.myFunction(alert("Tu FM3 o FM-M está vencido.")); 
	</script>	
<%	} %>
	<div class="container-fluid">
		<h3>Carga de Materias<small> ( <b><%=codigoAlumno%></b> - <%=nombreAlumno%> | <b>Carga:</b>
		<%=cargaId%> - <%=cargaNombre%> | <b>Bloque:</b> <%=bloqueId%> - <%=bloqueNombre%> )</small>
		</h3>
		
		<div class="alert alert-success">
			<a class="btn btn-primary" href="materias"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
<%		if(academico.getResidenciaId().equals("E")){ %>
			[Externo]&nbsp;&nbsp;	
<%		}else{ %>
			[Interno <%=academico.getRequerido().equals("S")?"Permanente ":"Temporal "%>($<%=internado%>)]&nbsp;&nbsp;	
			[Dias: <%=dias%>]&nbsp;&nbsp;
			[Total: $<%=muestraTotal%>]&nbsp;&nbsp;	
<%		} %>
		</div>
		
		<div class="row" style="margin: 0px 0px 0 0px;">
		<div class="col-md-4">
			<a class="button">
				<img class="clickButton2" onclick="mostrarHorario('<%=cargaId%>');" style="height:40;margin-bottom:8px;" src="../../imagenes/horario.png" title="Horario asignado"/>
			</a>
		</div>
<%	
		String cobro = "";
		if(lisMaterias.size() <= 4 && accion.equals("0") && days > 20){
			cobro = "disabled";
%>
		<div class="col-md-4 alert alert-info">
			<h3 style="text-align:center;">¡Te faltan <%=lisMaterias.size()%> materias para terminar!</h3>
			<h4>Son las siguientes:</h4>
			<ul>
<%
			for(MapaCurso curso : lisMaterias){
%>
				<li><%=curso.getNombreCurso()%></li>
			
<%			} %>
			</ul><br>
			<form name="frmGraduacion" method="post">
			<input type="hidden" name="Accion">
				<h4>¿Piensas graduar este año?</h4>
				<select name="sltCandidato" class="input input-small" onchange="javascript:recargar()">
					<option value="S" <% if (candidato.equals("S")) out.print(" selected");%>>Si</option>
					<option value="N" <% if (candidato.equals("N")) out.print(" selected");%>>No</option>
				</select><br><br>
<% 	
			/* if(candidato.equals("S")){  Este es el if correcto para habilitar la captura del alumno en un evento de graduación*/
			// Se deshabilito la captura del evento de graduación 14-jul-2021
			if(candidato.equals("X")){
%>
				<h4>Evento</h4>
				<select name="sltEvento" class="input input-xxlarge" onchange="javascript:recargar()">
<%
				for(AlumEvento evento: lisEventos){
%>
					<option value="<%=evento.getEventoId()%>" <%=eventoId.equals(evento.getEventoId())?" selected":""%>><%=evento.getEventoNombre()%></option>
<% 				} %>
				</select><br><br>
				<h4>Dedicatoria</h4>
				<input type="text" name="txtDedicatoria" class="form-control" ><br>
<%			} %>
				<input type="button" class="btn btn-primary" value="Grabar" onclick="javascript:grabar()">
			</form>
		</div>
<%		}else if (days > 20){ %>
		<div class="col-md-4 alert alert-success">
			<h3 style="text-align:center;">¡Listo!</h3>
		</div>
<%		} %>
		</div>
		
		<table class="table table-condensed">
		<thead>
			<tr>
				<th style="text-align:center">#</th>
				<th>Acta</th>
				<th>Materia</th>
				<th style="text-align:center">Cr.</th>			
			</tr>
		</thead>
		<tbody>
<%
		int row = 0;
		for(KrdxCursoAct kardex : lisCurso){
			
			// Buscar los datos de la materia
			if (mapaCurso.containsKey(kardex.getCursoId()) && kardex.getTipoCalId().equals("M")){
				row++;
				MapaCurso curso = mapaCurso.get(kardex.getCursoId());		
%>	
			<tr>
				<td style="text-align:center"><%=row%></td>
				<td><%=kardex.getCursoCargaId()%></td>
				<td><%=curso.getNombreCurso()%></td>
				<td style="text-align:center"><%=curso.getCreditos()%></td>			
			</tr>
<%
			}
		}	
%>			
		</tbody>	
		</table>
<% 		
	boolean tieneCandado = false;
	boolean muestra = false;
	if(lisCandados.size() > 0){
		for(CandAlumno candado : lisCandados){
			if(mapaCanTipo.containsKey(candado.getCandadoId().substring(0, 2))){
				tipo = mapaCanTipo.get(candado.getCandadoId().substring(0, 2));
			}
			if(tipo.getEstado().equals("A")){
				muestra = true;
				tieneCandado = true;
				break;
			}
		}
		if(muestra){
%>
		<table class="table table-condensed">
		<thead>
			<tr>
				<th style="text-align:center">#</th>
				<th>Candado</th>
				<th>Lugar</th>
				<th>Telefono</th>
				<th>Fecha</th>
				<th>Comentario</th>			
			</tr>
		</thead>
		<tbody>
<%			row = 0;
			for(CandAlumno candado : lisCandados){
				row++;
				tipo = new CandTipo();
				if (mapaCanTipo.containsKey(candado.getCandadoId().substring(0,2))){
					tipo = mapaCanTipo.get(candado.getCandadoId().substring(0,2));
				}
				if(tipo.getEstado().equals("A")){
%>	
			<tr>
				<td style="text-align:center"><%=row%></td>
				<td><%=mapaCandados.get(candado.getCandadoId()).getNombreCandado()%></td>
				<td><%=tipo.getLugar()%></td>
				<td><%=tipo.getTelefono()%></td>
				<td><%=mapaCandados.get(candado.getCandadoId()).getNombreCandado()%></td>
				<td><%=candado.getfCreado()%></td>
				<td><%=candado.getComentario()%></td>			
			</tr>
<%				}
			}		
		}		
	}	
%>			
		</tbody>	
		</table>
<%		if(msj.equals("-")){%>
		<form name="frmCalculo" method="post" action="https://virtual-um.um.edu.mx/financiero/calculoCobroEstimado.html" target="_blank">
		<input type="hidden" name="txtMatricula" value="<%=codigoAlumno%>">
		<input type="hidden" name="txtCarga" value="<%=cargaId%>@<%=bloqueId%>@<%=alumPlan.getPlanId()%>">
		<div class="alert alert-success">
			Forma de Pago:&nbsp;
			<select name="sltFormaPago" class="input input-medium">
				<option value="C" <% if (formaPago.equals("C")) out.print(" selected");%>>Contado</option>
				<option value="P" <% if (formaPago.equals("P")) out.print(" selected");%>>Pagaré</option>
			</select>
			&nbsp;
<%			if ( tieneCandado == false && autorizado == true){ %>		
			<input type="button" class="btn btn-primary" value="Ver cobro" onclick="javascript:verCobro()" <%=cobro%>>
<%			}else if (autorizado == false){
				out.print("¡Tienes documentos pendientes en archivo!");
			}
%>		
		</div>	
		</form>
<%		}else{%>
		<div class="alert alert-danger">
			<h4>¡<%=msj%>!
<%
			if (autorizado == false){
				out.print(" y &nbsp;¡Tienes documentos pendientes en archivo!");
			}	
%>
			</h4>
		</div>
<% 		} %>
	</div>
	</body>
	<script>
		function mostrarHorario(cargaId){
			jQuery("#popupContact").css({
				"height": "85%",
				"width": "90%"
			});
			window.popup.location="../../portales/alumno/horario?CargaId="+cargaId+"&Inscripcion=true";
		}
		
		function recargar(){
			document.frmGraduacion.Accion.value= "0";
			document.frmGraduacion.submit();
		}
		
		function grabar(){
			document.frmGraduacion.Accion.value= "1";
			document.frmGraduacion.submit();
		}

		function verCobro(){
			document.frmCalculo.submit();
			
			jQuery.get("../../agregaProcesoNotificacion?codigoAlumno=<%=codigoAlumno %>&cargaId=<%=cargaId %>&bloqueId=<%=bloqueId %>&periodoId=<%=periodoId %>", function(data){
				window.location.href = "verCobro";
			});			
		}
	</script>

</html>