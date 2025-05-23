<%@page import="aca.util.Fecha"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>

<%@page import="aca.apFisica.spring.ApFisicaGrupo"%>
<%@page import="aca.apFisica.spring.ApFisicaAlumno"%>
<%@page import= "aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "menu.jsp" %>

<link rel="stylesheet" href="../bootstrap-fileupload.min.css" />
<%
	String cursoCargaId				= (String)session.getAttribute("cursoCargaId");

	ApFisicaGrupo grupoAp 			= (ApFisicaGrupo) request.getAttribute("grupoAp");
	ApFisicaAlumno apFisicaAlumno 	= (ApFisicaAlumno) request.getAttribute("apFisicaAlumno");
	
	String cargaId					= (String) request.getAttribute("cargaId");	
	// Clave de la materia (7 digitos) que inscribio el alumno  
	String cursoId					= (String) request.getAttribute("cursoId");
	String materia					= (String) request.getAttribute("materia");
	String accion					= (String) request.getAttribute("accion");
	String grupoId					= (String) request.getAttribute("grupoId");	
	String codigoAlumno				= (String)request.getAttribute("codigoAlumno");	
	boolean tieneGrupo				= (boolean) request.getAttribute("tieneGrupo");
	
	List<ApFisicaGrupo> lisGrupos 	= (List<ApFisicaGrupo>) request.getAttribute("lisGrupos");
	List<AlumnoCurso> lisCursos 	= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	
	HashMap<String,String> mapaActivos 				= (HashMap<String,String>) request.getAttribute("mapaActivos");
	HashMap<String,Integer> mapaGrupoRegistrados 	= (HashMap<String,Integer>) request.getAttribute("mapaGrupoRegistrados");
	HashMap<String,String> mapMateriasOrigen 		= (HashMap<String,String>) request.getAttribute("mapMateriasOrigen");

	
	int accionFmt = (int) request.getAttribute("accionFmt");

%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
	
</style>
	
<div class="container-fluid">
	<h4><%=materia%><small class="text-muted fs-6">( Haz clic en el nombre del grupo que desees elegir )</small></h4>
	<div class="alert alert-info">
		<a href="materias" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i> Regresar</a>
	</div>
<% 
if(tieneGrupo){%>
	<div class="alert alert-success">
		<h5>Mi grupo: <%=grupoAp.getNombreGrupo()+" - "+grupoAp.getDescripcion()%></h5>
		<h5>Liga: <%=grupoAp.getLiga()%></h5>
		<h5>Instructor: <%=grupoAp.getInstructor()%></h5>
	</div>
<% 	}	
	if(accionFmt==1){%>
		<div class="alert alert-info"><h5>Has seleccionado tu grupo :D </h5></div>
<%	}		
	if(accionFmt==2){%>
		<div class="alert alert-danger"><h5>No se pudo guardar tu grupo, intenta de nuevo :( </h5></div>
<%	}
	if(accionFmt==3){%>
		<div class="alert alert-info"><h5><spring:message code='aca.SeModifico'/></h5></div>
<%	}		
	if(accionFmt==4){%>
		<div class="alert alert-danger"><h5><spring:message code='aca.NoSePudoModificar'/></h5></div>
<%	}
	if(accionFmt==5){%>
		<div class="alert alert-info"><h5><spring:message code='aca.SeElimino'/></h5></div>
<%	}
	if(accionFmt==6){%>
		<div class="alert alert-danger"><h5><spring:message code='aca.NoSePudoEliminar'/></h5></div>
<%	}
	if(accionFmt==7){%>
		<div class="alert alert-danger"><h5>Lo sentimos, ya no tenemos cupo en este grupo :( tardaste en elegir :P </h5></div>
<%	}%>
	<div id="refresh">		
		<form name="forma" action="grupos">
			<input name="CargaId" type="hidden" value="<%=cargaId%>"/>
			<input name="CursoId" type="hidden" value="<%=cursoId%>"/>
			<input name="grupo" type="hidden" value="<%=grupoId%>"/>
			<input name="accion" type="hidden" value="<%=accion%>"/>
			<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId%>"/>
			<table class="table table-condensed table-bordered">
			<thead class="table-dark">
			<tr>
				<th width="2%">#</th>
				<th width="4%">Apertura</th>
				<th width="4%">Cierre</th>
				<th width="9%">Grupo</th>
				<th width="16%">Descripcion</th>			
				<th width="12%">Lugar</th>
				<th width="5%">Dia</th>
				<th width="3%">Hora</th>
				<th width="12%">Instructor</th>
				<th width="3%">Libres</th>
				<th width="5%">Registrados</th>
				<th width="20%">Liga</th>
			</tr>
			</thead>
			<tbody>	
<%
			int cont = 1;
			for(ApFisicaGrupo grupo : lisGrupos){
				int registrados = 0;
				if(mapaGrupoRegistrados.containsKey(grupo.getGrupoId())){
					registrados = mapaGrupoRegistrados.get(grupo.getGrupoId());
				}
				int faltantes 	= Integer.parseInt(grupo.getCupo()) - registrados;
%>
			 	<input type="hidden" value="<%=grupo.getGrupoId()%>" id="grupoId">
				<tr <%if(grupo.getGrupoId().equals(apFisicaAlumno.getGrupoId()) ){ %> bgcolor="#CCCC99"<%} %>>
					<td><%=cont%></td>
					<td><%= grupo.getfInicio()%></td>
					<td><%=grupo.getfCierre()%></td>
					<td>
<%				if (mapaActivos.containsKey(grupo.getGrupoId()) && faltantes > 0){%>			
					<a href="javascript:Guardar('<%=grupo.getGrupoId()%>','');"><%=grupo.getNombreGrupo()%></a>
<%				}else{
					out.print(grupo.getNombreGrupo());	
				}
%>
					</td>
					<td><%=grupo.getDescripcion() %></td>			
					<td><%=grupo.getLugar() %></td>
					<td><%=grupo.getDia1() %></td>
					<td><%=grupo.getHora() %></td>
					<td><%=grupo.getInstructor()%></td>
					<td class="faltantes"><%=faltantes<=0?"<span class='badge bg-warning rounded-pill'>"+faltantes+"</span>":"<span class='badge bg-info rounded-pill'>"+faltantes+"</span>"%></td>
					<td><span class="badge bg-success rounded-pill"><%=registrados %></span>&nbsp; <a href="amigos?grupoId=<%=grupo.getGrupoId()%>&CargaId=<%=cargaId%>&CursoId=<%=cursoId%>"><i class="fas fa-search"></i></a></td>
					<td>
<% 				if(grupo.getGrupoId().equals(apFisicaAlumno.getGrupoId())){%>
					<%=grupo.getLiga()%>
<% 				}else{
					out.print(" ");	
				}
%>					</td>
				</tr>
<%
				cont++;
			}
%>
		</tbody>	
		</table>
		</form>
	</div>
</div>
<script src="../jquery.isotope.min.js"></script>
<script src="../js/fake-element.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript">
	function Guardar(grupoId) {
		if (confirm("¿Está seguro de registrarse en este Grupo?") == true) {
			document.forma.grupo.value = grupoId;
			document.forma.accion.value = "2";
			document.forma.submit();
		}
	}
</script>
<script type="text/javascript">
 
    /*var auto_refresh = setInterval(
    function ()
    {
        $('#refresh').load('/myServlet').fadeIn("slow");
    }, 20000);*/
    
    var result 		= jQuery('.faltantes');
    jQuery("#grupoId").change(function(){    	
    	$this = jQuery(this);
    	var value 	= $this.val();    	
   		jQuery.get("faltantes?grupoId="+value, function(r){
   			result.html(nombre);
   		});
    });  
</script>
<script src="../bootstrap-fileupload.min.js"></script>