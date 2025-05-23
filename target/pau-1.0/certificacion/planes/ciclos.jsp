<%@ page import= "java.util.HashMap"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.cert.spring.CertPlan"%>
<%@ page import= "aca.cert.spring.CertRelacion"%>
<%@ page import= "aca.cert.spring.CertCurso"%>
<%@ page import= "aca.cert.spring.CertCiclo"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String facultad		= (String) request.getAttribute("facultad"); 
	String planId 		= (String) request.getAttribute("planId"); 
	String accion 		= (String) request.getAttribute("accion"); 

	String ciclo		= (String) request.getAttribute("ciclo"); 
	String curso 		= (String) request.getAttribute("curso"); 
	String cursoCert 	= (String) request.getAttribute("cursoCert"); 
	boolean borro		= (boolean) request.getAttribute("borro"); 

	CertPlan certPlan   = (CertPlan) request.getAttribute("certPlan");
	
	List<CertCiclo> lisCiclos 						= (List<CertCiclo>) request.getAttribute("lisCiclos"); 
	List<CertCurso> lisCursos 						= (List<CertCurso>) request.getAttribute("lisCursos"); 
	HashMap<String,CertRelacion> mapCertRelacion 	= (HashMap<String,CertRelacion>) request.getAttribute("mapCertRelacion"); 
	HashMap<String, MapaCurso> mapaCursosPlan 		= (HashMap<String, MapaCurso>) request.getAttribute("mapaCursosPlan"); 
	HashMap<String, CatTipoCurso> mapaTipoCurso 	= (HashMap<String, CatTipoCurso>) request.getAttribute("mapaTipoCurso"); 
	HashMap<String, Integer> mapCursosPorPlan	 	= (HashMap<String, Integer>) request.getAttribute("mapCursosPorPlan"); 
	
	String nombreTipo = "";
	
	if(accion.equals("1")){
%>
	<div class="alert alert-info">
		<font size="3" color="blue">Se guard&oacute; correctamente el ciclo!!!</font>
	</div>
<%
	}else if(accion.equals("2")){
%>
	<div class="alert alert-info">
		<font size="3" color="blue">Se modific&oacute; correctamente el ciclo!!!</font>
	</div>
<%
	}else if(accion.equals("3")){
		if(borro){
%>
	<div class="alert alert-info">	
		<font size="3" color="blue">Se borr&oacute; correctamente el ciclo!!!</font>
	</div>
<%
		}else{
%>
	<div class="alert alert-info">
		<font size="3" color="red"><b>Error!!!</b> al borrar. Verifique que el ciclo no tenga materias asignadas</font>
	</div>
<%
		}
	}else if(accion.equals("4")){
%>
	<div class="alert alert-info">	
		<font size="3" color="blue">Se guard&oacute; correctamente el curso!!!</font>
	</div>
<%
	}else if(accion.equals("5")){
%>
	<div class="alert alert-info">
		<font size="3" color="blue">Se modific&oacute; correctamente el curso!!!</font>
	</div>
<%
	}else if(accion.equals("6")){
		if(borro){
%>
	<div class="alert alert-info">
		<font size="3" color="blue">Se borr&oacute; correctamente el curso!!!</font>
	</div>
<%
		}else{
%>
	<div class="alert alert-info">	
		<font size="3" color="red"><b>Error!!!</b> al borrar. Int&eacute;ntelo de nuevo!</font>
	</div>
<%
		}
	}else if(accion.equals("7")){
		if(borro){
			System.out.println("Se borro correctamente");
		}else{
			System.out.println("No borro");
		}
	}
%>
<head>
	<style type="text/css">
		div#unir{
			visibility: hidden;
			position: absolute;
			border: solid 1px #6186b7;
			background: white;
		}
		
		div#mensaje{
			visibility: hidden;
			position: absolute;
		}
	</style>
	<script type="text/javascript" src="../../js/prototype.js"></script>
	<script type="text/javascript">
		var materiaAnterior = "";
	
		function modificar(plan, ciclo,facultad){
			document.location.href = "edita_ciclo?plan="+plan+"&ciclo="+ciclo+"&facultad="+facultad;
		}
		
		function borrar(plan, ciclo, facultad){
			if(confirm("¿Está seguro que desea borrar definitivamente el ciclo?"))
				document.location.href = "ciclos?Accion=3&plan="+plan+"&ciclo="+ciclo+"&facultad="+facultad;
		}
		
		function modificarCurso(plan, ciclo, curso,facultad){
			document.location.href = "agregar_cursos?plan="+plan+"&ciclo="+ciclo+"&cursoId="+curso+"&facultad="+facultad;
		}
		
		function borrarCurso(plan, ciclo, curso,facultad){
			if(confirm("¿Está seguro que desea borrar definitivamente el curso?"))
				document.location.href = "ciclos?Accion=6&plan="+plan+"&curso="+curso+"&facultad="+facultad;
		}
		
		function muestraUnir(event, materia){
			var div = $("unir");
			if(div.style.visibility == "visible" && materiaAnterior == materia){
				div.style.visibility = "hidden";
			}else{
				div.innerHTML = '<iframe src="unir_materia?plan=<%=certPlan.getPlanId() %>&materia='+materia+'" frameborder="no"  width="400px" height="400px"></iframe>';
				div.style.visibility = "visible";
				var obj = Event.element(event);
				div.style.left = (Position.positionedOffset(obj)[0]-400)+"px";
				div.style.top = (Position.positionedOffset(obj)[1]+obj.offsetHeight)+"px";
			}
			materiaAnterior = materia;
		}
		
		function grabarUnion(certCurso, curso, plan){
			var url = "ciclosAccion?Accion=1&certCurso="+certCurso+"&curso="+curso+"&plan="+plan;
			var req = initRequest();
			req.onreadystatechange = function() {
				if(req.readyState == 4){
					if(req.status==404) {
						alert("Esta pagina no existe. Reportelo a Sistemas");
					}
					if(req.status == 200){
						eval(req.responseText);
					}else if (req.status == 204){
						muestraMensaje("Ocurrió un error al solicitar la ayuda");
					}
				}
			};
			req.open("get", url, true);
			req.send(null);
		}
		function borrarUnion(cursoCert, plan){
			if(confirm("¿Está seguro que desea borrar la relacion?"))
				document.location.href = "ciclos?Accion=7&cursoCert="+cursoCert+"&plan="+plan;
		}
		
		function yaExiste(certCurso, curso, plan){
			if(confirm("Este curso ya está unido. ¿Desea cambiar la union?")){
				var url = "ciclosAccion?Accion=2&certCurso="+certCurso+"&curso="+curso+"&plan="+plan;
				var req = initRequest();
				req.onreadystatechange = function() {
					if(req.readyState == 4){
						if(req.status==404) {
							alert("Esta pagina no existe. Reportelo a Sistemas");
						}
						if(req.status == 200){
							eval(req.responseText);
						}else if (req.status == 204){
							muestraMensaje("Ocurrió un error al solicitar la ayuda");
						}
					}
				};
				req.open("get", url, true);
				req.send(null);
			}else{
				muestraUnir('', materiaAnterior);
			}
		}
		
		function mostrarUnionCompletada(objeto, curso, plan){
			var td = $(objeto);
			td.innerHTML = curso+' <img class="button" onclick="muestraUnir(event, \''+objeto+'\');" src="../../imagenes/eslabon.gif" width="25px" title="Unir" />'+
							'<img class="button" onclick="borrarUnion( \''+objeto+'\', \''+plan+'\');" title="Eliminar" src="../../imagenes/no.png" width="15px" title="desUnir" />';
			mensaje(objeto, "Guardado!!!");
			muestraUnir('', materiaAnterior);
		}
		
		function mensajeError(objeto, frase){
			var obj = $(objeto);
			var div = $("mensaje");
			div.style.visibility = "visible";
			div.style.backgroundColor = "red";
			div.style.color = "white";
			div.style.left = (Position.positionedOffset(obj)[0]+obj.offsetWidth)+"px";
			div.style.top = (Position.positionedOffset(obj)[1])+"px";
			div.innerHTML = "&nbsp;"+frase+"&nbsp;";
			setTimeout("ocultaMensaje();", 5000);
			muestraUnir('', materiaAnterior);
		}
		
		function mensaje(objeto, frase){
			var obj = $(objeto);
			var div = $("mensaje");
			div.style.visibility = "visible";
			div.style.backgroundColor = "#fad163";
			div.style.color = "black";
			div.style.left = (Position.positionedOffset(obj)[0]+obj.offsetWidth)+"px";
			div.style.top = (Position.positionedOffset(obj)[1])+"px";
			div.innerHTML = "&nbsp;"+frase+"&nbsp;";
			setTimeout("ocultaMensaje();", 1000);
		}
		
		function ocultaMensaje(){
			$("mensaje").style.visibility = "hidden";
		}
		
		function initRequest() {
			if (window.XMLHttpRequest) {
				return new XMLHttpRequest();
			} else if (window.ActiveXObject) {
				isIE = true;
				return new ActiveXObject("Microsoft.XMLHTTP");
			}
		}
	</script>
</head>
<div class="container-fluid">
	<h2>Ciclos y Materias<small class="text-muted fs-4">(<%=certPlan.getCarrera() %> [<%=certPlan.getPlanId() %>])</small></h2>
	<div class="alert alert-info">
		<a href="carrera_plan?facultad=<%=facultad%>" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>
		<a href="edita_ciclo?plan=<%=planId%>&facultad=<%=facultad%>" class="btn btn-primary btn-sm"><i class="fas fa-plus-circle"></i> Nuevo Ciclo</a>
	</div>
	<table class="table table-striped table-sm table-bordered">
		<tr>
			<th colspan="2">Accion</th>
<%				if(certPlan.getClave().equals("S")){%>
			<th><spring:message code="aca.Clave"/></th>
<%				}%>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Tipo Curso</th>
<%				if(certPlan.getFst().equals("S")){%>
			<th><%=certPlan.getTitulo1() %></th>
<%				}

				if(certPlan.getFsp().equals("S")){%>
			<th><%=certPlan.getTitulo2() %></th>
<%				} %>
			<th><spring:message code='aca.Creditos'/></th>
			<th>Union</th>
		</tr>
<%	
	for(CertCiclo certCiclo : lisCiclos){
		int materias = 0;
		if(mapCursosPorPlan.containsKey(certCiclo.getCicloId())){
			materias = mapCursosPorPlan.get(certCiclo.getCicloId());
		}
%>
		<tr>
			<td>
<%				if(materias == 0){ %>
				<i title="Eliminar" class="fas fa-trash-alt" onclick="borrar('<%=planId %>','<%=certCiclo.getCicloId()%>','<%=facultad%>');" width="15px" ></i>
<%				} %>
				<i title="Editar" class="fas fa-edit" onclick="javascript:modificar('<%=planId %>','<%=certCiclo.getCicloId()%>','<%=facultad%>');" width="15px" ></i>
			</td>
			<td colspan="7">
				<b><%=certCiclo.getTitulo() %></b>&nbsp;&nbsp;<a class="btn btn-primary btn-sm" href="agregar_cursos?plan=<%=planId%>&ciclo=<%=certCiclo.getCicloId()%>&facultad=<%=facultad%>"><i class="fas fa-plus-circle"></i> Nueva Materia</a>
			</td>
		</tr>
<%		int j = 0;
			
		for(CertCurso certCurso : lisCursos){
			
			if(mapaTipoCurso.containsKey(certCurso.getTipoCursoId())){
				nombreTipo = mapaTipoCurso.get(certCurso.getTipoCursoId()).getNombreTipoCurso();
			}
			
			if(certCurso.getCicloId().equals(certCiclo.getCicloId())){
				CertRelacion certRelacion = new CertRelacion();
				
				if(mapCertRelacion.containsKey(certCurso.getCursoId())){
					certRelacion = mapCertRelacion.get(certCurso.getCursoId());
				}
%>
		<tr>
			<td>&nbsp;</td>
			<td align="right">
<%				if(!mapCertRelacion.containsKey(certCurso.getCursoId())){%>
				<i title="Eliminar" class="fas fa-trash-alt" onclick="borrarCurso('<%=planId %>','<%=certCiclo.getCicloId() %>','<%=certCurso.getCursoId() %>','<%=facultad%>');" width="15px" ></i>
<%				}%>
				<i title="Editar" class="fas fa-edit" onclick="modificarCurso('<%=planId %>','<%=certCiclo.getCicloId() %>','<%=certCurso.getCursoId() %>','<%=facultad%>');" width="15px" ></i>
			</td>
<%				if(certPlan.getClave().equals("S")){%>
			<td><%=certCurso.getClave() %></td>
<%				}%>
			<td><%= certCurso.getCursoNombre() %></td>
			<td><%=nombreTipo%></td>
<%				if(certPlan.getFst().equals("S")){%>
			<td align="center"><%=certCurso.getFst() %></td>
<%				}

				if(certPlan.getFsp().equals("S")){%>
			<td align="center"><%=certCurso.getFsp() %></td>
<%				}					
				if(mapaCursosPlan.containsKey(certRelacion.getCursoId())){
					curso = mapaCursosPlan.get(certRelacion.getCursoId()).getNombreCurso();
				}					
				if (curso.equals("0000000")) curso = "";%>
			<td align="center"><%=certCurso.getCreditos() %></td>
			<td id="<%=certCurso.getCursoId() %>" align="justified">
				<%=(curso+" ") %> 
			<img class="button" onclick="muestraUnir(event, '<%=certCurso.getCursoId() %>');" src="../../imagenes/eslabon.gif" width="25px" title="Unir" />
				<%certRelacion.setCursoCert(certCurso.getCursoId());
			  	if(mapCertRelacion.containsKey(certCurso.getCursoId())){%>
				<i class="fas fa-trash-alt" onclick="borrarUnion( '<%=certCurso.getCursoId() %>', '<%=planId %>');" width="15px" title="Desunir" ></i>
<% 				}%>
			</td>
		</tr>
<%				curso = "";
			}
			j++;
		}
	}%>
	</table>			
</div>
<div id="unir"></div>
<div id="mensaje"></div>
