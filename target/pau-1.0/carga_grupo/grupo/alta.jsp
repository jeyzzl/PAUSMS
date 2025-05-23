<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.plan.spring.MapaCursoElectiva"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	
	function Nuevo(){
		document.frmmateria.action ="alta";	
		document.frmmateria.submit();		
	}
	
	function Grabar(){
		if(document.frmmateria.Optativa.value!="Elige"){
			if(document.frmmateria.CursoId.value!="" && document.frmmateria.CursoId.value!="xxxxxxx" && document.frmmateria.BloqueId.value!="" ){				
				document.frmmateria.submit();
			}else{
				alert("<spring:message code='aca.JSCompletar'/>");
			}
		}else{
			alert("<spring:message code="cargasGrupo.grupo.JSMensaje"/>");	
		}	
	}
		
	function Plan( ){	
		document.frmmateria.CursoId.value = "";
		document.frmmateria.action ="alta";
		document.frmmateria.submit();
	}
	
	function Curso( ){	
		document.frmmateria.action="alta";
		document.frmmateria.submit();
	}
</script>
<%
	// Declaracion de variables
	
	String cargaId 			= (String) session.getAttribute("cargaId");

	String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String modalidadId		= request.getParameter("ModalidadId")==null?"1":request.getParameter("ModalidadId");
	String bloqueId			= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String optativa 		= request.getParameter("Optativa")==null?"0":request.getParameter("Optativa");
	String fechaInicio	    = request.getParameter("FechaInicio")==null?aca.util.Fecha.getHoy():request.getParameter("FechaInicio");
	String fechaFinal	 	= request.getParameter("FechaFinal")==null?aca.util.Fecha.getHoy():request.getParameter("FechaFinal");
	String grupo			= request.getParameter("Grupo")==null?"U":request.getParameter("Grupo");
	String precio 			= request.getParameter("Precio")==null?"0":request.getParameter("Precio");
	String comentario		= request.getParameter("Comentario")==null?"0":request.getParameter("Comentario");
	
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String planId 			= (String) request.getAttribute("planId");
	String cursoId 			= (String) request.getAttribute("cursoId");	
	String practica 		= (String) request.getAttribute("practica");
	String tienePrecio		= (String) request.getAttribute("tienePrecio");
	boolean existeGrupo		= (boolean) request.getAttribute("existeGrupo");
	
	Carga carga 			= (Carga)request.getAttribute("carga");
	CargaGrupo cargaGrupo	= (CargaGrupo)request.getAttribute("cargaGrupo");
	
	if (existeGrupo){
		modalidadId 		= cargaGrupo.getModalidadId();
		bloqueId			= cargaGrupo.getBloqueId();
		optativa 			= cargaGrupo.getOptativa();	
		fechaInicio			= cargaGrupo.getfInicio();
		fechaFinal			= cargaGrupo.getfFinal();
		grupo				= cargaGrupo.getGrupo();
		precio				= cargaGrupo.getPrecio();
		comentario			= cargaGrupo.getComentario();
	}
	
	String sResultado	= "";	
	String tipoCurso	= "0";
	
	// ArrayList de planes 
	List<MapaPlan> lisPlanes 				= (List<MapaPlan>) request.getAttribute("lisPlanes");	
	
	// ArrayList de materias
	List<MapaCurso> lisMaterias 			= (List<MapaCurso>) request.getAttribute("lisMaterias");	
	
				
	List<MapaCursoElectiva>	lisOptativas	= (List<MapaCursoElectiva>) request.getAttribute("lisOptativas");
	
	// ArrayList de bloques
	List<CargaBloque> lisBloques 			= (List<CargaBloque>) request.getAttribute("lisBloques");
	
	List<CatModalidad> lisModalidades 		= (List<CatModalidad>) request.getAttribute("lisModalidades");
	
	HashMap<String,MapaCurso> mapaMaterias 	= (HashMap<String,MapaCurso>) request.getAttribute("mapaMaterias");
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<body>
<div class="container-fluid">
	<h2><spring:message code="cargasGrupos.grupo.Titulo2"/><small class="text-muted fs-5"> ( <%=cargaId%> - <%=carga.getNombreCarga()%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="elegir"><spring:message code="catalogos.area.Titulo5"/></a>
<%	if (!mensaje.equals("-")){%>		
		&nbsp;&nbsp;&nbsp;&nbsp;<%=mensaje%>
<%	} %>		
	</div>
	<form name="frmmateria" action="grabarGrupo" method="post"  target="_self">	
	<input type="hidden" name="CarreraId" value="<%=carreraId%>">					
    <div class="row">
       	<div class="col">
       		<h3>Plan</h3>
       		<select name="PlanId" id="PlanId" onChange="javascript:Plan()" class="form-select">
<%				for( MapaPlan plan : lisPlanes){ %>
				<option value="<%=plan.getPlanId()%>" <%=plan.getPlanId().equals(planId)?"selected":""%>><%=plan.getPlanId()%>-<%=plan.getCarreraSe()%></option>
<%				}%>
	       	</select>
		</div>
        <div class="col">
        	<h3>Subjects</h3>
        	<select name="CursoId" id="CursoId" class="form-select chosen" onChange="javascript:Curso();">
<%								              	
				for( MapaCurso curso : lisMaterias){							 							
					if(curso.getTipoCursoId().equals("9"))continue;							
					if (curso.getCursoId().equals(cursoId)){
						tipoCurso = curso.getTipoCursoId();
						out.print(" <option value='"+curso.getCursoId()+"' Selected>("+curso.getCiclo()+") "+curso.getNombreCurso()+" "+ "["+ curso.getCursoClave() +"]"  +"</option>");
					}else{
						out.print(" <option value='"+curso.getCursoId()+"'>("+curso.getCiclo()+") "+curso.getNombreCurso()+" "+"["+ curso.getCursoClave()+"]" +"</option>");
					}	
				}				
%>
	  		</select> 
		</div>
<%
		if(tipoCurso.equals("2")){
%>
           <div class="col">
           	<h3><spring:message code="cargasGrupo.grupo.MateriasElectivas"/></h3>
		   	<select name="Optativa" id="Optativa" class="form-select"> 
			  	 <option value="Elige"><spring:message code="cargasGrupo.grupo.EligeMateria"/></option> 
<%
				// ArrayList de optativas
				for (MapaCursoElectiva electiva : lisOptativas){	
					String materia = electiva.getCursoNombre();
					if(electiva.getCursoNombre()==null){								
						materia = "-";
						if (mapaMaterias.containsKey(electiva.getCursoElec())){
							materia = mapaMaterias.get(electiva.getCursoElec()).getNombreCurso(); 
						}
					}								
					out.print(" <option value='"+materia+"'>"+ materia+"</option>");								
				}				
%>
           		</select>
          	</div>		          		
 <%		} 		
		if(!tipoCurso.equals("2") ){  %>
			  <input name="Optativa" type="hidden" class="text" id="Optativa" value="X">
<%		} %>
				
	</div>	
	<br>	
	<div class="row">
		<div class="col">
			<h3><spring:message code="aca.Modalidad"/></h3>
			<select name="ModalidadId" id="ModalidadId" class="form-select">
<%								
		  	for(CatModalidad modalidad : lisModalidades){						  		 					
				if (modalidad.getModalidadId().equals(modalidadId)){
					out.print(" <option value='"+modalidad.getModalidadId()+"' Selected>"+ modalidad.getNombreModalidad()+"</option>");
				}else{
					out.print(" <option value='"+modalidad.getModalidadId()+"'>"+ modalidad.getNombreModalidad()+"</option>");
				}
			}								
%>
           	</select>
		</div>						
		<div class="col">
			<h3><spring:message code="aca.Bloque"/></h3>
			<select name="BloqueId" id="BloqueId" class="form-select">
<%								  	
	  	for( CargaBloque bloque : lisBloques){	  		 					  		
		  	if (bloque.getBloqueId().equals(bloqueId)){
				out.print(" <option value='"+bloque.getBloqueId()+"' Selected>"+ bloque.getNombreBloque()+" [Block. "+bloque.getBloqueId()+"] | Starts: "+bloque.getFInicio()+" | Closes: "+bloque.getFCierre() +" | Ends: "+bloque.getFFinal()+"</option>");
			}else{
				out.print(" <option value='"+bloque.getBloqueId()+"'>"+ bloque.getNombreBloque()+" [Block. "+bloque.getBloqueId()+"] | Starts: "+bloque.getFInicio()+" | Closes: "+bloque.getFCierre()+" | Ends: "+bloque.getFFinal()+"</option>");
			}				  		
	  	}				
%>
             	</select>
		</div>					
	</div>
	<br>
	<div class="row">						
		<div class="col">
			<h3>Start Date <small class="text-muted fs-5">(<spring:message code="aca.DDMMAAAA"/>)</small></h3>
			<input name="FechaInicio" type="text" class="text" data-date-format="dd/mm/yyyy" id="FechaInicio" value="<%=fechaInicio%>">
		</div>
		<div class="col">
			<h3>End Date <small class="text-muted fs-5">(<spring:message code="aca.DDMMAAAA"/>)</small></h3>
				<input name="FechaFinal" type="text" class="text" data-date-format="dd/mm/yyyy" id="FechaFinal" value="<%=fechaFinal%>" >
		</div>						
	</div>
	<br>
	<div class="row">
		<div class="col">
			<h3>Group</h3>
			<input name="Grupo" type="text" class="text" id="Grupo" value="<%=grupo%>" maxlength="2">
		</div>
		<div class="col">
			<h3>Price</h3>
			<input name="Precio" type="text" id="Precio" value="<%=precio%>" size="8" maxlength="8" placeholder="$" <%=tienePrecio.equals("N")?"readonly":""%> readonly>
		</div>
	</div>
	<br>
	<div class="row">
		<div class="col">
			<h3><spring:message code="aca.Comentario"/></h3>
			<input name="Comentario" type="text" id="Comentario" value="<%=comentario%>" size="48" maxlength="40">
		</div>
		<div class="col">
			<h3>Assistance/Room</h3>
			<select name="Modo" id="Modo" class="form-select">
				<option value="P">In Person</option>	
				<option value="V">Online</option>
			</select>
		</div>
	</div>
	<br>
	<div class="alert alert-info">					
		 <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
	</div>												
	</form>
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
	jQuery('#FechaInicio').datepicker();
	jQuery('#FechaFinal').datepicker();
</script>