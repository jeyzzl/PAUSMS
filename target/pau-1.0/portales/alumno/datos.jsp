<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat"%>

<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumImagen"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumBanco"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.bec.spring.BecContrato"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>	
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.residencia.spring.ResDatos"%>
<%@page import="aca.leg.spring.LegExtdoctos"%>
<%@page import="aca.vigilancia.spring.VigAuto"%>
<%@page import="aca.vigilancia.spring.VigDoc"%>
<%@page import="aca.catalogo.spring.CatBanco"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<%
	String cancelaPortal 			= session.getAttribute("cancelaPortal")==null?"N":(String) session.getAttribute("cancelaPortal");
	String matricula				= session.getAttribute("codigoAlumno")==null?"0":(String) session.getAttribute("codigoAlumno");
	String codigoPersonal			= session.getAttribute("codigoPersonal")==null?"X":(String) session.getAttribute("codigoPersonal");
	String opcionSesion 			= session.getAttribute("datosOpcion")==null?"1":(String) session.getAttribute("datosOpcion");
	String colorPortal 				= session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
	boolean tieneFoto 				= (boolean) request.getAttribute("tieneFoto");
	boolean tieneFotoEnviada 		= (boolean) request.getAttribute("tieneFotoEnviada");
	int numAutos			 		= (int) request.getAttribute("numAutos");

	// Seccion de codigo a desplegar
	String opcion 					= request.getParameter("opcion")==null?opcionSesion:request.getParameter("opcion");
	String fechaHoy 				= aca.util.Fecha.getHoy();	
	String direccion 				= request.getRequestURL().toString();
	boolean virtual 				= false;
	
	if(direccion.contains("academico.um.edu.mx")){
		virtual = true;
	}	
	if(!matricula.equals(codigoPersonal) && !codigoPersonal.equals("9800308")){
		virtual=false;
	}
	
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumImagen alumImagen 			= (AlumImagen) request.getAttribute("alumImagen");
	AlumBanco alumBanco				= (AlumBanco) request.getAttribute("alumBanco");

	AlumAcademico alumAcademico 	= (AlumAcademico) request.getAttribute("alumAcademico");
	AlumUbicacion alumUbicacion 	= (AlumUbicacion) request.getAttribute("alumUbicacion");
	AlumPlan alumPlan 				= (AlumPlan) request.getAttribute("alumPlan");
	ResDatos resDatos				= (ResDatos) request.getAttribute("resDatos");
	BecPuestoAlumno	puestoAlumno	= (BecPuestoAlumno) request.getAttribute("puestoAlumno");
	String puestoReciente			= (String) request.getAttribute("puestoReciente");
	String puestoId 				= (String) request.getAttribute("puestoId");
	String mentorId					= (String) request.getAttribute("mentorId");
	String mentorNombre				= (String) request.getAttribute("mentorNombre");
	String mentorCorto				= (String) request.getAttribute("mentorCorto");
	String nombreReligion			= (String) request.getAttribute("nombreReligion");
	String nombreInstitucion		= (String) request.getAttribute("nombreInstitucion");
	String nombreTipo				= (String) request.getAttribute("nombreTipo");
	String nombreNacionalidad		= (String) request.getAttribute("nombreNacionalidad");
	String fechaVencimiento			= (String) request.getAttribute("fechaVencimiento");
	boolean alumnoInscrito			= (boolean) request.getAttribute("alumnoInscrito");
	String paisNombre				= (String) request.getAttribute("paisNombre");
	String estadoNombre				= (String) request.getAttribute("estadoNombre");
	String ciudadNombre				= (String) request.getAttribute("ciudadNombre");
	String razonNombre				= (String) request.getAttribute("razonNombre");	
	
	if (session!=null){
		session.setAttribute("datosOpcion",opcion);
		session.setAttribute("mat",alumPersonal.getCodigoPersonal());
	}
	
	// Lista de planes
	List<CargaAlumno> lisPlanes 				= (List<CargaAlumno>) request.getAttribute("lisPlanes");
	// Lista de planes
	List<LegExtdoctos> lisExtDoc 				= (List<LegExtdoctos>) request.getAttribute("lisExtDoc");
	// Lista de puestos
	List<BecPuestoAlumno> lisPuestos 			= (List<BecPuestoAlumno>) request.getAttribute("lisPuestos");
	List<VigAuto> lisAutos						= (List<VigAuto>) request.getAttribute("lisAutos");
	List<VigDoc> lisDocAutos 					= (List<VigDoc>) request.getAttribute("lisDocAutos");
	
	// Mapa de planes
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	// Mapa de departamentos
	// HashMap<String,ContCcosto> mapaDeptos 		= (HashMap<String,ContCcosto>) request.getAttribute("mapaDeptos");
	// Mapa de departamentos
	HashMap<String,String> mapaHorasAlumno 		= (HashMap<String,String>) request.getAttribute("mapaHorasAlumno");
	// Mapa de categorias
	HashMap<String,String> mapaCategorias 		= (HashMap<String,String>) request.getAttribute("mapaCategorias");	
	// Mapa de categorias
	HashMap<String,String> mapaDocumentos 		= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
	// Mapa de contratos del alumno
	HashMap<String,BecContrato> mapaContratos 	= (HashMap<String,BecContrato>) request.getAttribute("mapaContratos");
	HashMap<String,String> mapaImagenes 		= (HashMap<String,String>) request.getAttribute("mapaImagenes");
	HashMap<String,String> mapaDocAutos			= (HashMap<String,String>) request.getAttribute("mapaDocAutos");
	HashMap<String,CatBanco> mapaBanco			= (HashMap<String,CatBanco>) request.getAttribute("mapaBanco");

	
	if (mentorCorto.equals("0000000")) mentorCorto = "<span  class='badge bg-warning'>� Not assigned !</span>";
	
	int horasconv			= 0;
	String nombrePlan 		= "-";		
	
	String edoCivil="";	
	
	if (alumPersonal.getEstadoCivil().equals("S")) edoCivil = "Single";
	else if (alumPersonal.getEstadoCivil().equals("C")) edoCivil = "Married";
	else if (alumPersonal.getEstadoCivil().equals("D")) edoCivil = "Divorced";
	else if (alumPersonal.getEstadoCivil().equals("V")) edoCivil = "Widowed";		
	
	boolean esExtranjero = false;
	if (!alumPersonal.getNacionalidad().equals("153")){
		esExtranjero = true;
	}
	
	//Verifica modalidad en linea
	boolean enLinea = false;
	if(!alumAcademico.getModalidadId().equals("1") && !alumAcademico.getModalidadId().equals("4")){
		enLinea = true;
	}
	
	if (cancelaPortal.equals("N")){
%>

<script type="text/javascript">	
	function credencial(){
		abrirVentana("credencial",415,295,0,0,"no","yes","yes","no","no","credencial.jsp");
	}
			
	function camara(){
		location.href="tomarfoto.jsp";
	}
	
	function borrarFoto(){
		if (confirm("�Are you sure you want to delete the student's picture??")){
			location.href="borrar";
		}	
	}
	
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL){
		var sF="";
		TOP = (screen.height - iH)/2-50
		LEFT = (screen.width - iW)/2
		sF+=iW?'width='+iW+',':'';
		sF+=iH?'height='+iH+',':'';
		sF+=R?'resizable='+R+',':'';
		sF+=S?'status='+S+',':'';
		sF+=SC?'scrollbars='+SC+',':'';
		sF+=T?'titlebar='+T+',':'';
		sF+=TB?'toolbar='+TB+',':'';
		sF+=TB?'menubar='+TB+',':'';
		sF+=TOP?'top='+TOP+',':'';
		sF+=LEFT?'left='+LEFT+',':'';
		newwindow = window.open(URL,strName?strName:'',sF)
		newwindow.focus();
	}
</script>
<style type="text/css">
	#sombra {
		float:left;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, s� haces m�s grandes estos valores, el efecto de sombra es mayor tambi�n */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aqu� es donde ponemos la imagen como fondo colocando su ubicaci�n*/
		
		position:relative;
		top:0px;
		left: 0px;
	}	
	#sombra img {
		display:block;
		position:relative;
		top: 0px; /* Desfasamos la imagen hacia arriba */
		left:-2px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row mt-1">		
		<div class="col-6 d-flex justify-content-start">			
			<a class="btn btn-sm  <%=opcion.equals("1")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=1">1. <spring:message code="portal.alumno.datos.Personales"/></a>&nbsp;
			<a class="btn btn-sm <%=opcion.equals("2")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=2">2. <spring:message code="portal.alumno.datos.Academicos"/>&nbsp;</a>&nbsp;
			<a class="btn btn-sm  <%=opcion.equals("3")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=3">3. <spring:message code="portal.alumno.datos.ServicioBecario"/>&nbsp;</a>&nbsp;
			<a class="btn btn-sm  <%=opcion.equals("4")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=4">4. <spring:message code="portal.alumno.datos.Tutor"/>&nbsp;</a>&nbsp;
			<a class="btn btn-sm  <%=opcion.equals("5")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=5">5. <spring:message code="portal.alumno.datos.Residencia"/>&nbsp;</a>&nbsp;			
<%-- <%	  if(!nombreNacionalidad.equals("Papua New Guinean")){%> --%>
			<a class="d-flex align-items-center btn btn-sm <%=opcion.equals("6")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=6">6. <spring:message code="portal.alumno.datos.Nacionalidad"/>&nbsp;</a>&nbsp;
<%-- <% 	  }	%> --%>
<%	  if(numAutos >= 1){%>
			<a class="d-flex align-items-center btn <%=opcion.equals("7")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=7">7. Auto</a>
<% 	  }	%>
			<a class="btn btn-sm  <%=opcion.equals("8")?"btn-dark":"btn-outline-secondary"%>" href="datos?opcion=8">8. Bank Data&nbsp;</a>&nbsp;
		</div>		
	</div>
<!-- OPCION 1 -->
<%		if (opcion.equals("1")){ %>
	<div class ="alert alert-info d-flex align-items-center alert-alto-m mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i></h5>
	</div>
	<div class="row">
		<div class="col-1">
<% 			if (tieneFoto){	%>
			<div>						
<%-- 				<img class="rounded border border-dark" src='../../foto?Codigo=<%=alumPersonal.getCodigoPersonal()%>&id=<%=new java.util.Date().getTime()%>' width="200"> --%>
				<img class="img-responsive" src='../../foto?Codigo=<%=alumPersonal.getCodigoPersonal()%>&id=<%=new java.util.Date().getTime()%>' width="200">
			</div>   			 
<% 			
			}else{
				if (enLinea && virtual){
					if (tieneFotoEnviada){
%>
			<div>
				<img class="rounded border border-dark" src='foto?mat=<%=alumPersonal.getCodigoPersonal()%>&Tipo=E&id=<%=new java.util.Date().getTime()%>' width="200">
			</div>
			<a href="../../fotoBajar?Codigo=<%=matricula%>&Tipo=E" title="Download Picture"><img src='../../imagenes/descargar.png' width="30" ></a>&nbsp;
            <a href="javascript:borrarFoto()" title="Delete Picture"><img src='../../imagenes/borrar2.gif' width="25" ></a>
<%					}else{ %>
			<div><img class="rounded border border-dark" src='foto?mat=<%=alumPersonal.getCodigoPersonal()%>&Tipo=E&id=<%=new java.util.Date().getTime()%>' width="200"></div>
			<a href="javascript:camara()" title="Take Picture"><img src='../../imagenes/camaraweb.png' width="20" ></a>&nbsp;
<%						
					}					
				}else{
%>
			<div><img class="rounded border border-dark" src='../../foto?Codigo=<%=alumPersonal.getCodigoPersonal()%>&id=<%=new java.util.Date().getTime()%>' width="200"></div>
<%				}      			
      		}
%>		
		</div>
	</div>	
	<div class="container-fluid">
	<div class="row border mt-2">
		<div class="col-2"><b><spring:message code="aca.EstadoCivil"/>:</b></div>
		<div class="col-10"><%=edoCivil%></div>
	</div>
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Genero"/>:</b></div>
		<div class="col-10"><%=alumPersonal.getSexo().equals("M")?"Male":"Female"%></div>
	</div>
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Fecha"/> of <spring:message code="aca.Nacimiento"/>:</b></div>
		<div class="col-10"><%=alumPersonal.getFNacimiento()%></div>
	</div>	
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Email"/>:</b></div>
		<div class="col-10"><%=alumPersonal.getEmail()%></div>
	</div>	
	<div class="row border">
		<div class="col-2"><b>Phone:</b></div>
		<div class="col-10"><%=alumPersonal.getTelefono()%></div>
	</div>	
	<div class="row border">
		<div class="col-2"><b>GOB ID:</b></div>
		<div class="col-10">
			<%=alumPersonal.getCurp()%>
<%		    
			boolean validaCurp = aca.alumno.AlumUtil.validarCurp(alumPersonal.getCurp()); 
			if (esExtranjero){
// 			   	out.println("Apply for one if not available");
			}else if ( validaCurp == false ){ 
// 	 			out.println("<font color='red'>�Not Registered!</font>");
 			}else if(validaCurp== true ){
//  				out.println("<font color='purple'>�Correct!</font>");
 			}
%>		
		</div>
	</div>
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Religion"/>:</b></div>
		<div class="col-10"><%=nombreReligion%></div>
	</div>
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Alumno"/> <spring:message code="aca.Tipo"/></b></div>
		<div class="col-10">
<% 	
			String inst = "0";
			if(alumAcademico.getTipoAlumno().trim().equals("2") || alumAcademico.getTipoAlumno().trim().equals("6")){
				inst = " - "+nombreInstitucion;
			}				
%>
			<%=nombreTipo%>
		</div>
	</div>
	<div class="row border">
		<div class="col-2"><b><spring:message code="portal.alumno.datos.Residencia"/>:</b></div>
		<div class="col-10"><%=alumAcademico.getResidenciaId().equals("I")?"Boarding Student":"External"%></div>
	</div>
	
<!-- 	<div class="row border"> -->
<%-- 		<div class="col-2"><b><spring:message code="portal.alumno.datos.UsoDeImagen"/>:</b></div> --%>
<!-- 		<div class="col-10"> -->
<% 				if(alumImagen.getConsentimiento().equals("S")){%>
<!-- 				<span class="badge rounded-pill bg-success">YES</span> -->
<% 				}else{%>
<!-- 				<span class="badge rounded-pill bg-warning ">NO</span> -->
<% 				}%>
<!-- 		</div> -->
<!-- 	</div> -->
<%			if(!alumPersonal.getNacionalidad().equals("153")){%>	
	<div class="row border">
		<div class="col-2"><b><spring:message code="aca.Nacionalidad"/>:</b></div>
		<div class="col-10"><%=nombreNacionalidad%></div>		
	</div>
<!-- 	<div class="row border"> -->
<%-- 		<div class="col-2"><spring:message code="datosAlumno.portal.Vencimiento" />:</div> --%>
<%-- 		<div class="col-10"><%=fechaVencimiento%></div>		 --%>
<!-- 	</div> -->
<%
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date hoy = new Date();
				String color="",mensaje="";
				Date fechav = df.parse(fechaVencimiento);
				int dias = new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
				if(dias>29 && dias<61){
					color="#F39603";
					mensaje="FM3 EXPIRES IN "+dias+" DAYS, You are in time to fix your immigration status.";
				}
				else if(dias<30){
					color="#CE0000";
					if(dias>=1) mensaje="FM3 EXPIRES IN "+dias+" DAYS, It is urgent to fix your immigration status.";
					if(dias<=0) mensaje=" FM3 EXPIRED "+(dias*-1)+" DAY AGO, It is urgent to fix your immigration status.";
				}
				else {
					color="";
					mensaje=dias + " remaining days.";
				}
%>	
<!-- 	<div class="row border"> -->
<%-- 		<div class="col-10" style="color:<%=color%>"><%=mensaje%></div>				 --%>
<!-- 	</div> -->
<%			} %>	
	
	</div>	
<%		
		}
%>
<!-- OPCION 2 -->
<%		if (opcion.equals("2")){
			
			nombrePlan = "-";
			if (mapaPlanes.containsKey(alumPlan.getPlanId())){
				nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
			}
			
			%>
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
	</div>	
	<div class="container-fluid">
	<div class="row border mt-2">
		<div class="col-2"><b>Active Plans:</b></div>
		<div class="col-10">	
<% 		int row=0;
			for (CargaAlumno carga : lisPlanes){
				row++;
				nombrePlan = "-";
				if (mapaPlanes.containsKey(carga.getPlanId())){
					nombrePlan = mapaPlanes.get(carga.getPlanId()).getNombrePlan();
				}
				if (row==1){								
					out.print(carga.getPlanId()+"- "+nombrePlan);
				}else{ 
					out.print("<br>"+carga.getPlanId()+"- "+nombrePlan);
				}
			}	
	%>	</div>
	</div>	
	<div class="row border">
		<div class="col-2"><spring:message code="aca.Modalidad"/>:</div>
		<div class="col-10"><%=alumAcademico.getModalidadId().equals("1")?"Face to Face":"Online"%></div>		
	</div>		
	<div class="row border">
		<div class="col-2"><spring:message code="aca.Inscrito"/>:</div>
		<div class="col-10"><%if (alumnoInscrito){ out.println("ENROLLED");}else{ out.println("Not Enrolled");}%></div>		
	</div>
	</div>						

<%		} %>			
<!-- OPCION 3 -->
<%	if (opcion.equals("3")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
		
%>	
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
	</div>
	<div class="container-fluid">		
<!-- 		<div class="row border"> -->
<!-- 			<form action="datos" name="forma" method="get"> -->
<!-- 			<div class="col-5"> -->
<!-- 			<select name="puestoId" id="puestoId" colspan="2" class="input-xxlarge" onchange="document.forma.submit()">			 -->
<%
		for(BecPuestoAlumno puesto: lisPuestos){
			if(puestoId.equals("0")){
				puestoId = puesto.getPuestoId();
			}
			String nombreDepto = "-";
			// if (mapaDeptos.containsKey(puesto.getIdEjercicio()+puesto.getIdCcosto())){
			// 	nombreDepto = mapaDeptos.get(puesto.getIdEjercicio()+puesto.getIdCcosto()).getNombre();
			// }										
%>
<%-- 			<option value="<%= puesto.getPuestoId()%>" <%if(puesto.getPuestoId().equals(puestoId)) out.print("selected"); %>> --%>
<%-- 		  	<%=nombreDepto%> || <%=puesto.getFechaIni() %> --%>
<!-- 			</option> -->
<%		} %>			
<!-- 			</select>&nbsp;&nbsp;			 -->
<!-- 			</div> -->
<!-- 			</form> -->
<!-- 		</div>	  -->
<%	
			// Llena la lista de los informes anteriores
			List<BecInforme> lisInformes = (List<BecInforme>) request.getAttribute("lisInformes");
			//Llena el map de las horas del alumno
			HashMap<String, String> mapHorasAlumno 	= (HashMap<String, String>)request.getAttribute("mapaHorasAlumno");
			HashMap<String, String> mapEstadoAlumno	= (HashMap<String, String>)request.getAttribute("mapaEstadoAlumno");
			
			//Llena el map de las evaluaciones del  alumnos
			java.util.HashMap <String, String> mapEvalAlumno 	= (HashMap<String, String>)request.getAttribute("mapaEvalAlumno");
			String horasTotal 	= (String) request.getAttribute("horasTotal");
			
			String estadoPuesto = "";
			if (puestoAlumno.getEstado().equals("P")) estadoPuesto = "Pre-hired";
			if (puestoAlumno.getEstado().equals("C")) estadoPuesto = "Hired";
			if (puestoAlumno.getEstado().equals("I")) estadoPuesto = "Inactive";
			
			String lugar = "-";
			// if (mapaDeptos.containsKey(puestoAlumno.getIdEjercicio()+puestoAlumno.getIdCcosto())){
			// 	lugar = mapaDeptos.get(puestoAlumno.getIdEjercicio()+puestoAlumno.getIdCcosto()).getNombre();
			// }
			
			String categoriaNombre = "-";
			if (mapaCategorias.containsKey(puestoAlumno.getCategoriaId())){
				categoriaNombre = mapaCategorias.get( puestoAlumno.getCategoriaId());
			}
%>		
		<div class="row border">
			<div class="col-2"><spring:message code="portal.alumno.datos.Lugar"/></div>
			<div class="col-10"><%=lugar%></div>
		</div>	
		<div class="row border">
			<div class="col-2"><spring:message code="portal.alumno.datos.Puesto"/>:</div>
			<div class="col-10"><%=categoriaNombre%></div>
		</div>
		<div class="row border">
			<div class="col-2"><spring:message code="portal.alumno.datos.Vigencia"/>:</div>
			<div class="col-10"><%=puestoAlumno.getFechaIni()%> to <%=puestoAlumno.getFechaFin()%></div>
		</div>
		<div class="row border">
			<div class="col-2"><spring:message code="portal.alumno.datos.Estado"/>:</div>
			<div class="col-10"><%=estadoPuesto%></div>
		</div>
		<div class="row border">
			<div class="col-2"><spring:message code="portal.alumno.datos.HorasConvenio"/>:</div>
			<div class="col-10"><%=horasTotal%></div>
		</div>		
		<div class="row border">
			<div class="col-12"><spring:message code="portal.alumno.datos.Horas"/>:
<%
			for(BecInforme informe: lisInformes){
				String mes		= "";
				if ("123456789101112".contains(informe.getOrden()))
					mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(informe.getOrden()));
				else
					mes = "-";
				String horas	 = "";
				String estados   = "";
				String color	 = "";	
				if (mapEstadoAlumno.containsKey(informe.getInformeId())){
				    estados = mapEstadoAlumno.get(informe.getInformeId());
				    if(estados.equals("1")){
				    	color = "#3A9727";
				    }else if(estados.equals("2")){
				    	color = "#0055cc";
				    }else if(estados.equals("3")){
				    	color = "black";
				    }
				    if(estados.isEmpty()){
				    	color = "red";
				    }/*1 verde 2 azul 3 negro nada rojo*/
				}
				if (mapHorasAlumno.containsKey(informe.getInformeId())){
					horas = mapHorasAlumno.get(informe.getInformeId());
					out.print("<span style='color:"+color+";'>"+mes+" [ "+horas+" ]</span>&nbsp;&nbsp;");						
					horasconv+=Integer.parseInt(horas);
				}					
			}
%>			
				<b>Total = <%=horasconv %></b>
				 &nbsp;&nbsp; <a href="promedios"><i class="fas search-plus fa-2x" ></i></a>
			 </div>
		</div>
		<div class="row border">
			<div class="col-12">
				<spring:message code="portal.alumno.datos.Evaluacion"/>:
<%
				for(BecInforme informe: lisInformes){
					String mes		= "";
					if ("123456789101112".contains(informe.getOrden()))
						mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(informe.getOrden()));
					else
						mes = "-";
					String nota	= "";
					if (mapEvalAlumno.containsKey(matricula+puestoAlumno.getPuestoId()+informe.getInformeId())){
						nota = mapEvalAlumno.get(matricula+puestoAlumno.getPuestoId()+informe.getInformeId());
						out.print("<b>"+mes+" ["+nota+"]</b></a>&nbsp;");
					}				 	
				}
%>				&nbsp; <a href="promedios"><i class="fas fa-search" ></i></a>&nbsp;
				<spring:message code="portal.alumno.datos.HorasFaltantes"/>: <b><%=(Integer.parseInt(horasTotal)-horasconv)%></b>		
			</div>			
		</div>
		<div class="row border">
			<div class="col-12">
				<div style='width:7px;height:7px;background:red;display:inline-block;'></div> <font size="2px">No Status</font>
				<div style='width:7px;height:7px;background:#3A9727;display:inline-block;'></div> <font size="2px">Sent</font>
				<div style='width:7px;height:7px;background:#0055cc;display:inline-block;'></div> <font size="2px"><spring:message code='aca.Autorizado'/></font>
				<div style='width:7px;height:7px;background:black;display:inline-block;'></div> <font size="2px">Posted</font>
			</div>			
		</div>
<%		if (mapaContratos.containsKey(puestoId)){ %>
		<div class="row border">
			<div class="col-12">
				<em> <spring:message code="portal.alumno.datos.DescargaAquiTuConvenioDeServicioBecario"/><em><a href="descargarConvenio?CodigoAlumno=<%=matricula%>&Puesto=<%=puestoId%>" style="color:green;"><i class="fas fa-download fa-2x"></i></a>
			</div>			
		</div>
<% 		}%>
	</div>
<%	} %>	
<!-- OPCION 4 -->				
<%	if (opcion.equals("4")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
%>		
	<div class ="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
    </div>				
	<div class="container-fluid">     	
    	<div class="row border">
    		<div class="col-2"><spring:message code="aca.Padre"/>:</div>
         	<div class="col-10"><%=alumUbicacion.getpNombre()%></div>
    	</div>
        <div class="row border">
   			<div class="col-2"><spring:message code="aca.Madre"/>:</div>
        	<div class="col-10"><%=alumUbicacion.getmNombre()%></div>
   		</div>
       <div class="row border">
    		<div class="col-2"><spring:message code="aca.Tutor"/>:</div>
         	<div class="col-10"><%=alumUbicacion.gettNombre()%></div>
       </div>
    	<div class="row border">
        	<div class="col-2"><spring:message code="aca.Pais"/>:</div>
        	<div class="col-10"><%=paisNombre%></div>
    	</div>
        <div class="row border">
    		<div class="col-2"><spring:message code="aca.Estado"/>:</div>
        	<div class="col-10"><%=estadoNombre%></div>
    	</div>
       <div class="row border">
    		<div class="col-2"><spring:message code="aca.Ciudad"/>:</div>
         	<div class="col-10"><%=ciudadNombre%></div>
    	</div>
    	<div class="row border">
        	<div class="col-2"><spring:message code="catalogos.division.Direccion"/>:</div>
       		<div class="col-10"><%=alumUbicacion.gettDireccion()%></div>
       	</div>
    	<div class="row border">
        	<div class="col-2"><spring:message code="catalogos.extension.Colonia"/>:</div>
    		<div class="col-10"><%=alumUbicacion.gettColonia()%></div>
    	</div>
    	<div class="row border">
    		<div class="col-2"><spring:message code="catalogos.extension.CodigoPostal"/>:</div>
        	<div class="col-10"><%=alumUbicacion.gettCodigo()%></div>
    	</div>
    	<div class="row border">
    		<div class="col-2"><spring:message code="datosAlumno.portal.DatosApartado"/>:</div>
        	<div class="col-10"><%=alumUbicacion.gettApartado()%></div>
    	</div>
    	<div class="row border">
    		<div class="col-2"><spring:message code="aca.Telefono"/>:</div>
        	<div class="col-10"><%=alumUbicacion.gettTelefono()%></div>
    	</div>
	</div>
<%	} %>			    
<!-- OPCION 5 -->
<%	if (opcion.equals("5")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
%>	
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
     </div>
	<div class="container-fluid">
<% 		if (tieneFoto){	%>
		<div class="row border">
    		<div class="col-12">
    			<div id="sombra">
					<img src="../../foto?Codigo=<%=matricula%>&id=<%=new java.util.Date().getTime()%>" width="250" alt="Picture" class="img-circle"> 				
				</div>
    		</div>        	
    	</div>
<%		} %>
<%		if (alumAcademico.getResidenciaId().equals("I")){%> 
		<div class="row border">
    		<div class="col-2"><spring:message code="aca.ResidenciaActual"/>:</div>
        	<div class="col-10"><%=alumAcademico.getResidenciaId().equals("E")?"External":"Dormitory"%></div>
    	</div>
    	<div class="row border">
    		<div class="col-2"><spring:message code="aca.Dormitorio"/>:</div>
        	<div class="col-10"><%=alumAcademico.getDormitorio()%></div>
        </div>
<%		} %>	       
<%		if (alumAcademico.getResidenciaId().equals("E")){ %>
        <div class="row border">
        	<div class="col-2"><spring:message code="aca.Calle"/>:</div>
        	<div class="col-10"><%=resDatos.getCalle()%> # <%=resDatos.getNumero()%></div>
        </div>
		<div class="row border">
			<div class="col-2"><spring:message code="catalogos.extension.Colonia"/>:</div>
			<div class="col-10"><%=resDatos.getColonia()%></div>
		</div>
		<div class="row border">
			<div class="col-2"><spring:message code="aca.Telefono"/>:</div>
			<div class="col-10">(<%=resDatos.getTelArea()%>) <%=resDatos.getTelNum()%></div>
		</div>
		<div class="row border">
			<div class="col-2"><spring:message code="aca.Tutor"/>:</div>
			<div class="col-2"><%=resDatos.getNombreTut() %> <%=resDatos.getApellidoTut()%></div>
		</div>
        <div class="row border">
        	<div class="col-2"><spring:message code="aca.Municipio"/>:</div>
            <div class="col-10"><%=resDatos.getMpio()%></div>
        </div>
        <div class="row border">
        	<div class="col-2"><spring:message code="datosAlumno.visita.Razon"/>:</div>
            <div class="col-10"><%=razonNombre%></div>
        </div>
        <div class="row border">
        	<div class="col-2"><spring:message code="aca.Usuario"/>:</div>
            <div class="col-10"><%=resDatos.getUsuario() %></div>
        </div>
		<div class="row border">
			<div class="col-2"><spring:message code="aca.Fecha"/>:</div>
			<div class="col-10"><%=resDatos.getFecha()%></div>
		</div>		                
<%		}%>		
	</div>
<%	}%>
<!-- OPCION 6 -->
<%	if (opcion.equals("6")){
	
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}	
%>
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
    </div>	
    <h6> <spring:message code="portal.alumno.datos.Nacionalidad"/>: <%=esExtranjero?nombreNacionalidad:"Papua New Guinean"%></h6>	
	<table class="table table-sm table-nobordered">		
    <tr valign="top">
    	<th>#</th>
    	<th><spring:message code="portal.alumno.datos.Documento"/></th>
    	<th><spring:message code="portal.alumno.datos.Numero"/></th>
    	<th><spring:message code="portal.alumno.datos.Vigencia"/></th>
    	<th width="40%">&nbsp;</th>
	</tr>
<%
		int row = 0;
		for (LegExtdoctos docExt : lisExtDoc){
			row++;
			
			String nombreDocumento = "-";
			if (mapaDocumentos.containsKey(docExt.getIdDocumento())){
				nombreDocumento = mapaDocumentos.get(docExt.getIdDocumento());
			}
%>
		<tr valign="top">
        	<td><%=row%></th>
        	<td><%=docExt.getIdDocumento()%>-<%=nombreDocumento%></td>
            <td><%=docExt.getNumDocto()%></td>
            <td><%=docExt.getFechaVence()%></td>
            <td>&nbsp;</td>
        </tr>
<%		} %>
	</table>
<%	}%>
<!-- OPCION 7 -->
<%	
	if (opcion.equals("7")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}	
%>
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
     	<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>  <small class="text-muted fs-6">( <%=matricula%> - <%=alumPlan.getPlanId()%> - <%=nombrePlan%> )</small></h5>
    </div>	
    <h6> Registered Vehicles</h6>	
	<table class="table table-sm table-bordered">		
    <tr valign="top">
    	<th>#</th>
    	<th>Date</th>
    	<th>Brand</th>
    	<th>Model</th>
    	<th>License Plates</th>
    	<th>Color</th>
<%		for (VigDoc doc : lisDocAutos){%>
		<th><%=doc.getCorto()%></th>
<%		} %>    	
	</tr>
<%
		int rowAuto = 0;
		for (VigAuto vigAuto : lisAutos){
			rowAuto++;		
%>
		<tr valign="top">
        	<td><%=rowAuto%></th>
        	<td><%=vigAuto.getFecha()%></td>
            <td><%=vigAuto.getMarca()%></td>
            <td><%=vigAuto.getModelo()%></td>
            <td><%=vigAuto.getPlacas()%></td>
            <td><%=vigAuto.getColor()%></td>
<%			for (VigDoc doc :lisDocAutos){
	
				String vigencia = "01/01/2020";
				boolean existeDoc = false;
				if (mapaDocAutos.containsKey(vigAuto.getAutoId()+"-"+doc.getDocumentoId())){
					vigencia = mapaDocAutos.get(vigAuto.getAutoId()+"-"+doc.getDocumentoId());
					existeDoc = true;
				}
				int dias = aca.util.Fecha.diasEntreFechas(aca.util.Fecha.getHoy(), vigencia);
				
				String color = "-";
				if (existeDoc){
					if(dias>30){
					   color = "color:green";
					}else if(dias<0){
						color = "color:red";
					}else if(dias>=0 && dias<=30){
						color = "color:orange";
					}
				}else{
					color = "color:#CCCCCC";
				}	
%>	
			<td>
				<i class="fas fa-calendar-day" style="<%=color%>" data-bs-toggle="tooltip" data-bs-placement="left"  title="<%=vigencia%>"></i>
<% 				if(mapaImagenes.containsKey(vigAuto.getAutoId()+"-"+doc.getDocumentoId()+"-1")){%>
				<span class="badge rounded-pill bg-dark">1</span>
<% 				}else{%>
				<span class="badge rounded-pill bg-secondary">1</span>
<% 				}%>
<% 				if(mapaImagenes.containsKey(vigAuto.getAutoId()+"-"+doc.getDocumentoId()+"-2")){%>
				<span class="badge rounded-pill bg-dark">2</span>
<% 				}else{%>
				<span class="badge rounded-pill bg-secondary">2</span>
<% 				}%>
			</td>		
<%			} %>            
        </tr>
<%		} %>
	</table>
<%	}%>
<!-- OPCION 8 -->
<%
	if(opcion.equals("8")){

		String nombreBanco = "";
		String swiftCode = "";
		if(mapaBanco.containsKey(alumBanco.getBancoId())){
			nombreBanco = mapaBanco.get(alumBanco.getBancoId()).getNombre();
			swiftCode = mapaBanco.get(alumBanco.getBancoId()).getSwift();
		}
		
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}	
%>
	<div class ="alert alert-info alert-info d-flex align-items-center mt-2">
		<h5><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> <b><%=alumPersonal.getCodigoPersonal()%></b> : <i><%=alumPlan.getPlanId()%></i> - <%=nombrePlan%></h5>
	</div>
	<div class="container-fluid">     	
    	<div class="row border">
    		<div class="col-2"><b>Bank:</b></div>
         	<div class="col-10"><%=nombreBanco%></div>
    	</div>
        <div class="row border">
   			<div class="col-2"><b>Branch:</b></div>
        	<div class="col-10"><%=alumBanco.getBancoRama() %></div>
   		</div>
       <div class="row border">
    		<div class="col-2"><b>Account Name:</b></div>
         	<div class="col-10"><%=alumBanco.getCuentaNombre() %></div>
       </div>
    	<div class="row border">
        	<div class="col-2"><b>Account Number:</b></div>
        	<div class="col-10"><%=alumBanco.getCuentaNumero() %></div>
    	</div>
        <div class="row border">
    		<div class="col-2"><b>SWIFT Code:</b></div>
        	<div class="col-10"><%=swiftCode%></div>
    	</div>
       <div class="row border">
    		<div class="col-2"><b>BSB Number:</b></div>
         	<div class="col-10"><%=alumBanco.getNumeroBBS() %></div>
    	</div>
    	<div class="row border">
        	<div class="col-2"><b>Account Type:</b></div>
       		<div class="col-10"><%=alumBanco.getCuentaTipo() %></div>
       	</div>
	</div>
<%
	}
%>
</div>

<%
	}
%>
<script>
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
	  return new bootstrap.Tooltip(tooltipTriggerEl)
	})
	</script>
