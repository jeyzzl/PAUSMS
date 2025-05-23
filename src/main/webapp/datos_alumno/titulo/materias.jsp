<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.kardex.spring.KrdxAlumnoTitulo"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.vista.spring.AlumnoCurso"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<!-- inicio de estructura -->
<%
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	boolean esAdmin			 		= Boolean.parseBoolean(session.getAttribute("admin")+"");
	
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String cargaId					= (String) request.getAttribute("cargaId");
	String planId					= (String) request.getAttribute("planId");
	String carrera					= (String) request.getAttribute("carrera");
	String nombreAlumno				= (String) request.getAttribute("nombreAlumno");
	
	Acceso acceso 					= (Acceso)request.getAttribute("acceso");

	List<Carga> lisCarga 							= (List<Carga>) request.getAttribute("lisCarga");
	List<AlumnoCurso> listMateriasAlumnoPorCarga 	= (List<AlumnoCurso>) request.getAttribute("listMateriasAlumnoPorCarga");	
	HashMap<String, KrdxAlumnoTitulo> mapaTitulos	= (HashMap<String, KrdxAlumnoTitulo>) request.getAttribute("mapaTitulos");

	HashMap<String,String> mapaMaestros 			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,CatCarrera> mapaCarreta 			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreta");
	HashMap<String,CatModalidad> mapaModalidad 		= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidad");
	HashMap<String,CatTipoCal> mapaTipoCal 			= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipoCal");
	
	int horas = 0, creditos=0, duracion=0;
	
	String fechaIni					= "";
	String fechaFin					= "";
	
	boolean esTitulo				= false;
%>
<%  //if que comprueba si es hay algun alumno seleccionado 
	if(codigoAlumno.equals("X")){
%>
<div align="center"><spring:message code="cargasGrupos.permiso.Elegir" /> <spring:message code="aca.Alumno" /></div>
<%	}else{ %>
<div class="container-fluid">
	<h2>
		<spring:message code="datosAlumno.titulo.Titulo" />
		<small class="text-muted fs-5">( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> - <%=carrera%> )</small>
	</h2>
	<form name="datos1" action="materias" method="POST">
	<div class="alert alert-info d-flex align-items-center">		
		<b><spring:message code="aca.Carga" />: </b>&nbsp;
		<select name="CargaId" onchange="document.datos1.submit()" class="form-select" style="width:400px;">
<%
		// Lista de cargas del alumno
		int cont = 0;
		for(Carga carga : lisCarga){
			if (cargaId.equals("X") && cont==0){	cargaId = carga.getCargaId(); }
			if (carga.getCargaId().equals(cargaId)){
				out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ "["+carga.getCargaId()+"] "+carga.getNombreCarga()+"</option>");
			}else{
				out.print(" <option value='"+carga.getCargaId()+"'>"+"["+carga.getCargaId()+"] "+ carga.getNombreCarga()+"</option>");
			}
			cont++;
		}	
%>
		</select>			 
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<tr>
		<th width="5%">Op.</th>
		<th width="5%"><spring:message code="aca.Ciclo"/></th>
		<th width="40%"><spring:message code="aca.Curso"/>/<spring:message code="aca.Maestro" /></th>
		<th width="5%" class="text-end"><spring:message code="aca.TH"/>.</th>
		<th width="5%" class="text-end"><spring:message code="aca.Crd"/>.</th>	
		<th width="5%" class="text-center"><spring:message code="aca.Bloque" /></th>
		<th width="5%" class="text-center"><spring:message code="aca.Grupo" /></th>	
		<th width="5%" class="text-center"><spring:message code="aca.Modalidad" /></th>
		<th width="5%" class="text-center"><spring:message code="aca.Estado" /></th>
		<th width="5%" class="text-center"><spring:message code="datosAlumno.titulo.TitSuf"/></th>
	</tr>
<%
		for(AlumnoCurso curso : listMateriasAlumnoPorCarga){
			
			esTitulo = false;			
			KrdxAlumnoTitulo titulo = new KrdxAlumnoTitulo();
			if (mapaTitulos.containsKey(curso.getCursoCargaId())){
				esTitulo = true;
				titulo = mapaTitulos.get(curso.getCursoCargaId());
			}
			
			String maestroNombre = "-";
			if (mapaMaestros.containsKey(curso.getMaestro())){
				maestroNombre = mapaMaestros.get(curso.getMaestro());
			}
	
			String modalidadNombre = "-";
			if (mapaModalidad.containsKey(curso.getModalidadId())){
				modalidadNombre = mapaModalidad.get(curso.getModalidadId()).getNombreModalidad();
			}
			
			String tipoNombre = "-";
			if (mapaTipoCal.containsKey(curso.getTipoCalId())){
				tipoNombre = mapaTipoCal.get(curso.getTipoCalId()).getNombreTipoCal();
			}
			
			horas 		+= Integer.parseInt(curso.getHt())+Integer.parseInt(curso.getHp());
			creditos 	+= Integer.parseInt(curso.getCreditos());		
%>
	<tr>
		<td>
<%		if ( esTitulo){%>	
			<a class="fas fa-edit" href="titulo?CursoCargaId=<%=curso.getCursoCargaId()%>&CursoId=<%=curso.getCursoId()%>"></a>
<%		} %>			
<%		if ( esTitulo && acceso.getAdministrador().equals("S")){%>
			&nbsp;<a class="fas fa-trash-alt" href="borrar?CursoCargaId=<%=curso.getCursoCargaId()%>&CodigoAlumno=<%=codigoAlumno%>"></a>
<%		} %>			
		</td>
  		<td class="text-center"><%=curso.getCiclo()%></td>
  		<td title="<%=curso.getCursoCargaId()%>">
			<b><%=curso.getNombreCurso()%></b><br>
			<li><%=maestroNombre%>
  		</td>
  		<td class="text-end"><%=Integer.parseInt(curso.getHt())+Integer.parseInt(curso.getHp())%></td>
  		<td class="text-end"><%=curso.getCreditos()%></td>
  		<td class="text-center"><%=curso.getBloqueId()%></td>
  		<td class="text-center"><%=curso.getGrupo()%></td>
  		<td class="text-center"><%=modalidadNombre%></td>
  		<td class="text-center"><%=tipoNombre%></td>
  		<td class="text-center">
<%			if (esTitulo){ %>
		<spring:message code="aca.SI" /> 
<%			}else{%>
		<spring:message code="aca.NO" />
<%			}%>
<% 			if (esTitulo==false && curso.getTipoCalId().equals("I")){ %>
		<a href="titulo?CursoCargaId=<%=curso.getCursoCargaId()%>&CursoId=<%=curso.getCursoId()%>">
		  <img src="../../imagenes/llenar.png" alt="Grabar" width="15" height="15" >
		</a>		
<%			}else if(esTitulo==true){ %>
		<a href="formato?CursoCargaId=<%=curso.getCursoCargaId()%>&CursoId=<%=curso.getCursoId()%>">
		  <img src="../../imagenes/printer.png" alt="Formato" width="15" height="15" >
		</a>	
<% 			}else if(curso.getTitulo().equals("S") && esTitulo && esAdmin ){%>
		<a href="formato?CursoCargaId=<%=curso.getCursoCargaId()%>&CursoId=<%=curso.getCursoId()%>">
		  <img src="../../imagenes/filesave.png" alt="Formato" width="15" height="15" >
		</a>  
<%			} %>
  		</td>
	</tr>
<%		} //for %>
	<tr>
	  	<th colspan="3" align="center"><b><spring:message code="datosAlumno.titulo.HorasCred" /></b></th>
	  	<th class="text-end"><%=horas%></th>
	  	<th class="text-end"><%=creditos%></th>
	  	<th colspan="6">&nbsp;</th>
	</tr>
	</table>
</div>
<%	} %>
 