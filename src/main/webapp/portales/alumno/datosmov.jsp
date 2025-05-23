<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.ContCcosto"%>
<%@page import="aca.bec.spring.BecInforme"%>
<%@page import="aca.bec.spring.BecInformeAlumno"%>
<%@page import="aca.bec.spring.BecPuestoAlumno"%>
<%@page import="aca.residencia.spring.ResDatos"%>
<%@page import="aca.leg.spring.LegExtdoctos"%>
<%@page import="java.util.Date,java.text.SimpleDateFormat" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menumov.jsp" %>
<%
	String cancelaPortal 			= session.getAttribute("cancelaPortal")==null?"N":(String) session.getAttribute("cancelaPortal");
	String matricula				= session.getAttribute("codigoAlumno")==null?"0":(String) session.getAttribute("codigoAlumno");
	String codigoPersonal			= session.getAttribute("codigoPersonal")==null?"X":(String) session.getAttribute("codigoPersonal");
	String opcionSesion 			= session.getAttribute("datosOpcion")==null?"3":(String) session.getAttribute("datosOpcion");
	String colorPortal 				= session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
	
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
	boolean tieneFoto 				= (boolean) request.getAttribute("tieneFoto");
	boolean tieneFotoEnviada 		= (boolean) request.getAttribute("tieneFotoEnviada");
	
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
	// Mapa de planes
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	// Mapa de departamentos
	HashMap<String,ContCcosto> mapaDeptos 		= (HashMap<String,ContCcosto>) request.getAttribute("mapaDeptos");
	// Mapa de departamentos
	HashMap<String,String> mapaHorasAlumno 		= (HashMap<String,String>) request.getAttribute("mapaHorasAlumno");
	// Mapa de categorias
	HashMap<String,String> mapaCategorias 		= (HashMap<String,String>) request.getAttribute("mapaCategorias");	
	// Mapa de categorias
	HashMap<String,String> mapaDocumentos 		= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
	
	if (mentorCorto.equals("0000000")) mentorCorto = "<span  class='badge bg-warning'>¡ Sin asignar !</span>";
	
	int horasconv			= 0;
	String nombrePlan 		= "-";		
	
	String edoCivil="";	
	
	if (alumPersonal.getEstadoCivil().equals("S")) edoCivil = "Soltero";
	else if (alumPersonal.getEstadoCivil().equals("C")) edoCivil = "Casado";
	else if (alumPersonal.getEstadoCivil().equals("D")) edoCivil = "Divorciado";
	else if (alumPersonal.getEstadoCivil().equals("V")) edoCivil = "Viudo";		
	
	boolean esExtranjero = false;
	if (!alumPersonal.getNacionalidad().equals("91")){
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
		if (confirm("¿Estas seguro de borrar la fotografia del alumno?")){
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
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aquí es donde ponemos la imagen como fondo colocando su ubicación*/
		
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
	.font20 { font-size: 20pt; }
	//class="nav-link font20
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row mt-2">		
		<div class="col-12 d-flex align-items-start">
			<h1><small><a class="d-flex align-items-center badge rounded-pill <%=opcion.equals("3")?"bg-dark":"bg-info"%>" href="datosmov?opcion=3">1. Residencia&nbsp;</a></small></h1>&nbsp;&nbsp;			
			<h1><small><a class="d-flex align-items-center badge rounded-pill <%=opcion.equals("2")?"bg-dark":"bg-info"%>" href="datosmov?opcion=2">2. Tutor&nbsp;</a></small></h1>&nbsp;&nbsp;
			<h1><small><a class="d-flex align-items-center badge rounded-pill <%=opcion.equals("1")?"bg-dark":"bg-info"%>" href="datosmov?opcion=1">3. Servicio Becario&nbsp;</a></small></h1>
		</div>						
	</div>	
<%	
	if (opcion.equals("1")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
		
%>	
	<div class ="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2" style="height:4%">
		<h2>ALUMNO <small>(<%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> - <%=alumPersonal.getCodigoPersonal()%>)</small></h2>
	</div>
	<div class="container-fluid">
		<div class ="d-flex align-items-center alert-alto-m mt-2" style="height:2%">
			<h3><%=alumPlan.getPlanId()%> - <%=nombrePlan%></h3>
		</div>
	</div>
	&nbsp;&nbsp;
	<div class="container-fluid">		
		<form action="datosmov" name="forma" method="get">
		<div class="row border">
			<form action="datos" name="forma" method="get">
			<div class="col-5"> 
			<h3><select name="puestoId" id="puestoId" colspan="2" class="input-xxlarge" onchange="document.forma.submit()">			
<%
		for(BecPuestoAlumno puesto: lisPuestos){
			if(puestoId=="0"){
				puestoId = puesto.getPuestoId();
			}
			String nombreDepto = "-";
			if (mapaDeptos.containsKey(puesto.getIdEjercicio()+puesto.getIdCcosto())){
				nombreDepto = mapaDeptos.get(puesto.getIdEjercicio()+puesto.getIdCcosto()).getNombre();
			}										
%>
			<option value="<%= puesto.getPuestoId()%>" <%if(puesto.getPuestoId().equals(puestoId)) out.print("selected"); %>>
		  	<%=nombreDepto%> || <%=puesto.getFechaIni() %>
			</option>
<%
		}
%>			
			</select></h3>
			</div>
			</form>
		</div>	 
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
			if (puestoAlumno.getEstado().equals("P")) estadoPuesto = "Precontratado";
			if (puestoAlumno.getEstado().equals("C")) estadoPuesto = "Contratado";
			if (puestoAlumno.getEstado().equals("I")) estadoPuesto = "Inactivo";
			
			String lugar = "-";
			if (mapaDeptos.containsKey(puestoAlumno.getIdEjercicio()+puestoAlumno.getIdCcosto())){
				lugar = mapaDeptos.get(puestoAlumno.getIdEjercicio()+puestoAlumno.getIdCcosto()).getNombre();
			}
			
			String categoriaNombre = "-";
			if (mapaCategorias.containsKey(puestoAlumno.getCategoriaId())){
				categoriaNombre = mapaCategorias.get( puestoAlumno.getCategoriaId() );
			}
%>		
		<div class="row border">
			<div class="col-3"><font size="6px">Lugar:</font></div>
			<div class="col-9"><font size="6px"><%=lugar%></font></div>
		</div>	
		<div class="row border">
			<div class="col-3"><font size="6px">Puesto:</font></div>
			<div class="col-9"><font size="6px"><%=categoriaNombre%></font></div>
		</div>
		<div class="row border">
			<div class="col-3"><font size="6px">Vigencia:</font></div>
			<div class="col-9"><font size="6px"><%=puestoAlumno.getFechaIni()%> al <%=puestoAlumno.getFechaFin()%></font></div>
		</div>
		<div class="row border">
			<div class="col-3"><font size="6px">Estado:</font></div>
			<div class="col-9"><font size="6px"><%=estadoPuesto%></font></div>
		</div>
		<div class="row border">
			<div class="col-3"><font size="6px">Horas Convenio:</font></div>
			<div class="col-9"><font size="6px"><%=horasTotal%></font></div>
		</div>		
		<div class="row border">
			<div class="col-12"><font size="6px">Horas:
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
			 </font></div>
		</div>
		<div class="row border">
			<div class="col-12"><font size="6px">
				Evaluación:
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
						out.print(" <b>"+mes+" [ "+nota+" ]</b></a>&nbsp;&nbsp;");
					}				 	
				}
%>				
				&nbsp;&nbsp;
				Horas Faltantes: <b><%=(Integer.parseInt(horasTotal)-horasconv)%></b>		
			</font></div>			
		</div>
<!-- 		<div class="row border"> -->
<!-- 			<div class="col-12"> -->
<!-- 				<div style='width:20px;height:20px;background:red;display:inline-block;'></div> <font size="6px">Sin Estado</font>&nbsp;&nbsp; -->
<!-- 				<div style='width:20px;height:20px;background:#3A9727;display:inline-block;'></div> <font size="6px">Enviado</font>&nbsp;&nbsp; -->
<%-- 				<div style='width:20px;height:20px;background:#0055cc;display:inline-block;'></div> <font size="6px"><spring:message code='aca.Autorizado'/></font>&nbsp;&nbsp; --%>
<!-- 				<div style='width:20px;height:20px;background:black;display:inline-block;'></div> <font size="6px">Contabilizado</font> -->
<!-- 			</div>			 -->
<!-- 		</div>	 -->
	</div>
<%	} %>	

<%	if (opcion.equals("2")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
%>						
	<div class="container-fluid">
     	<div class ="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2" style="height:4%">
        	<h2>ALUMNO <small> ( <%=matricula%> - <%=alumPersonal.getNombreLegal() %>)</small></h2>
     	</div>     	
		<div class ="d-flex align-items-center alert-alto-m mt-2" style="height:2%">
			<h3><%=alumPlan.getPlanId()%> - <%=nombrePlan%></h3>
		</div>		
    	<div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="aca.Padre"/>:</font></div>
         	<div class="col-9"><font size="6px"><%=alumUbicacion.getPNombre()%></font></div>
    	</div>
        <div class="row border">
   			<div class="col-3"><font size="6px"><spring:message code="aca.Madre"/>:</font></div>
        	<div class="col-9"><font size="6px"><%=alumUbicacion.getMNombre()%></font></div>
   		</div>
       <div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="aca.Tutor"/>:</font></div>
         	<div class="col-9"><font size="6px"><%=alumUbicacion.getTNombre()%></font></div>
       </div>
    	<div class="row border">
        	<div class="col-3"><font size="6px"><spring:message code="aca.Pais"/>:</font></div>
        	<div class="col-9"><font size="6px"><%=paisNombre%></font></div>
    	</div>
        <div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="aca.Estado"/>:</font></div>
        	<div class="col-9"><font size="6px"><%=estadoNombre%></font></div>
    	</div>
       <div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="aca.Ciudad"/>:</font></div>
         	<div class="col-9"><font size="6px"><%=ciudadNombre%></font></div>
    	</div>
    	<div class="row border">
        	<div class="col-3"><font size="6px"><spring:message code="catalogos.division.Direccion"/>:</font></div>
       		<div class="col-9"><font size="6px"><%=alumUbicacion.getTDireccion()%></font></div>
       	</div>
    	<div class="row border">
        	<div class="col-3"><font size="6px"><spring:message code="catalogos.extension.Colonia"/>:</font></div>
    		<div class="col-9"><font size="6px"><%=alumUbicacion.getTColonia()%></font></div>
    	</div>
    	<div class="row border">
    		<div class="col-3"><font size="6px">Cod.Postal:</font></div>
        	<div class="col-9"><font size="6px"><%=alumUbicacion.getTCodigo()%></font></div>
    	</div>
    	<div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="datosAlumno.portal.DatosApartado"/>:</font></div>
        	<div class="col-9"><font size="6px"><%=alumUbicacion.getTApartado()%></font></div>
    	</div>
    	<div class="row border">
    		<div class="col-3"><font size="6px"><spring:message code="aca.Telefono"/>:</font></div>
        	<div class="col-9"><font size="6px"><%=alumUbicacion.getTTelefono()%></font></div>
    	</div>
	</div>
<%	} %>			    

		
<%	if (opcion.equals("3")){
		nombrePlan = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			nombrePlan = mapaPlanes.get(alumPlan.getPlanId()).getNombrePlan();
		}
%>	
	<div class="container-fluid">
		<div class ="alert alert-info alert-info d-flex align-items-center alert-alto-m mt-2" style="height:4%">
       		<h2>ALUMNO <small>( <%=matricula%> - <%=alumPersonal.getNombreLegal()%>)</small></h2>
    	</div>    	
    	<div class ="d-flex align-items-center alert-alto-m mt-2" style="height:2%">
       		<h3><%=alumPlan.getPlanId()%> - <%=nombrePlan%>)</h3>
    	</div>    	    	
                 	
<%		if (alumAcademico.getResidenciaId().equals("I")){%>
    	
        <div class="row border  mt-2">
              	<div class="col-3"><font size="6px"><spring:message code="aca.ResidenciaActual"/>:</font></div>
               <div class="col-9"><font size="6px"><%if (alumAcademico.getResidenciaId().equals("E"))out.print("Externo"); else out.print("Interno"); %></font></div>
		</div>
        <div class="row border  mt-2">
           	<div class="col-3"><font size="6px"><spring:message code="aca.Dormitorio"/>:</font></div>
           	<div class="col-9"><font size="6px"><%=alumAcademico.getDormitorio()%></font></div>
        </div>		          
<%		}else{ %>
        <div class="row border">
            	<div class="col-3"><font size="6px"><spring:message code="aca.Residencia"/>:</font></div>
               <div class="col-6"><font size="6px"><%if (alumAcademico.getResidenciaId().equals("E"))out.print("Externo"); else out.print("Interno"); %></font></div>             
		</div> 
        	<div class="row border mt-2">
            <div class="col-3"><font size="6px"><spring:message code="aca.Calle"/>:</font></div>
            <div class="col-9"><font size="6px"><%=resDatos.getCalle()%> # <%=resDatos.getNumero()%></font></div>
           </div>
		<div class="row border mt-2">
			<div class="col-3"><font size="6px"><spring:message code="catalogos.extension.Colonia"/>:</font></div>
			<div class="col-9"><font size="6px"><%=resDatos.getColonia()%></font></div>
		</div>
		<div class="row border mt-2">
			<div class="col-3"><font size="6px"><spring:message code="aca.Telefono"/>:</font></div>
			<div class="col-9"><font size="6px">(<%=resDatos.getTelArea()%>) <%=resDatos.getTelNum()%></font></div>
		</div>
		<div class="row border mt-2">
			<div class="col-3"><font size="6px"><spring:message code="aca.Tutor"/>:</font></div>
			<div class="col-9"><font size="6px"><%=resDatos.getNombreTut() %> <%=resDatos.getApellidoTut()%></font></div>
		</div>
        <div class="row border mt-2">
        	<div class="col-3"><font size="6px"><spring:message code="aca.Municipio"/>:</font></div>
           	<div class="col-9"><font size="6px"><%=resDatos.getMpio()%></font></div>
        </div>
        <div class="row border mt-2">
			<div class="col-3"><font size="6px"><spring:message code="datosAlumno.visita.Razon"/>:</font></div>
            <div class="col-9"><font size="6px"><%=razonNombre%></font></div>
        </div>
        <div class="row border mt-2">
           	<div class="col-3"><font size="6px"><spring:message code="aca.Usuario"/>:</font></div>
            <div class="col-9"><font size="6px"><%=resDatos.getUsuario() %></font></div>
        </div>
		<div class="row border mt-2">
			<div class="col-3"><font size="6px"><spring:message code="aca.Fecha"/>:</font></div>
			<div class="col-9"><font size="6px"><%=resDatos.getFecha()%></font></div>
		</div>        
<%		}
%>               
 	</div>
<%	}%>
</div>
<%
	}
%>