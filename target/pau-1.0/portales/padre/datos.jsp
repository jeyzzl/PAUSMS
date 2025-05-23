<%@page import="aca.bec.BecPuestoAlumnoUtil"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="religion" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="estado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="Tutor" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="Direccion" scope="page" class="aca.alumno.AlumDireccion"/>
<jsp:useBean id="PuestoAlumno" scope="page" class="aca.bec.BecPuestoAlumno"/>
<jsp:useBean id="PuestoAlumnoU" scope="page" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean id="BecInformeU"  class="aca.bec.BecInformeUtil" scope="page"/>

<%-- <jsp:include page="../menuPadre.jsp" /> --%>
<%@ include file= "../menuPadre.jsp" %>
<%
	String direccion 		= request.getRequestURL().toString();
	boolean virtual 		= false;
	if(direccion.contains("academico")){
		virtual = true;
	}
	
	if(!session.getAttribute("codigoAlumno").equals(session.getAttribute("codigoPersonal")) && !session.getAttribute("codigoPersonal").equals("9800308")){
		virtual=false;
	}

	String matricula		= "";	
	String hijo 			= (String) request.getParameter("Hijo")==null?"0":request.getParameter("Hijo");
	
	// Elige el codigo del alumno
	if (!hijo.equals("0")){
		matricula = hijo;
		session.setAttribute("codigoAlumno", matricula);
	}else{
		matricula = (String) session.getAttribute("codigoAlumno");
	}
	
	String puestoReciente	= PuestoAlumnoU.maximoPuesto(conEnoc, matricula);
	String puestoId 		= request.getParameter("puestoId")==null?puestoReciente:request.getParameter("puestoId");	
	String mentorId			= aca.mentores.MentAlumno.getMentorId(conEnoc, matricula, aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc));
	String mentorNombre		= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,mentorId,"NOMBRE");
	
	
	String sCalle 			= "";
	String sNumero 			= "";
	String sColonia 		= "";
	String sTelArea			= "";
	String sTelNumero		= "";
	String sTutNombre 		= "";
	String sTutApellidos	= "";
	String sMunicipio		= "";
	String sDescripcion 	= "";
	String ssUsuario		= "";
	String sFecha			= "";
	int horasconv			= 0;
	
	
	Direccion.mapeaRegId(conEnoc, matricula);
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	plan.mapeaRegId(conEnoc,matricula);
	academico = AcademicoU.mapeaRegId(conEnoc,matricula);
	String edoCivil="";
	
	if (alumno.getEstadoCivil().equals("S")) edoCivil = "Soltero";
	else if (alumno.getEstadoCivil().equals("C")) edoCivil = "Casado";
	else if (alumno.getEstadoCivil().equals("D")) edoCivil = "Divorciado";
	else if (alumno.getEstadoCivil().equals("V")) edoCivil = "Viudo";
	
	CargaBloque.CargaActiva(conEnoc,(String)session.getAttribute("cargaId"));
	estado.mapeaRegId(conEnoc,matricula,CargaBloque.getCargaId(),CargaBloque.getBloqueId());
	alumno = AlumUtil.mapeaRegId(conEnoc, matricula);
	Tutor.mapeaRegId(conEnoc,matricula);	
	
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";	
	boolean esExtranjero = false;
	if (!alumno.getNacionalidad().equals("91")){
		esExtranjero = true;
	}
	
	// Verifica si existe la imagen	
	boolean tieneFoto 			= false; 
	String dirFoto 		= application.getRealPath("/WEB-INF/fotos/"+matricula+".jpg");
	java.io.File foto 	= new java.io.File(dirFoto);
	if (foto.exists()){
		tieneFoto = true;
	}
	
	// Verifica si el alumno tiene una foto enviada (sirve )
	boolean tieneFotoEnviada	= false; 
	String dirFoto2 	= application.getRealPath("/WEB-INF/fotos2/"+matricula+".jpg");
	java.io.File foto2 	= new java.io.File(dirFoto2);
	if (foto2.exists()){
		tieneFotoEnviada = true;
	}
	
	//Verifica modalidad en linea
	boolean enLinea = false;
	if(!academico.getModalidadId().equals("1") && !academico.getModalidadId().equals("4")){
		enLinea = true;
	}	
%>
<html>
<head>
<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
<link href="../../print.css" rel="STYLESHEET" type="text/css" media="print">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script type="text/javascript">	
	function credencial(){
		abrirVentana("credencial",415,295,0,0,"no","yes","yes","no","no","credencial.jsp");
	}
			
	function camara(){
		location.href="tomarfoto.jsp";
	}
	
	function borrarFoto(){
		if (confirm("¿Estas seguro de borrar la fotografia del alumno?")){
			location.href="borrar.jsp";
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
</head>
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
</style>
</head>

<body onload='muestraPagina();'>
<br>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>
<table style="width:100%; height:100%; margin:0 auto;" cellpadding="10">
<tr valign="top">
	<td>
		<table style="width:90%; margin:0 auto;">
		<tr>
			<td width="50%">
				<table style="width:100%; height:100%" class="table table-fullcondensed table-nobordered">
				<tr valign="top">
					<th colspan="3">
					<spring:message code="datosAlumno.portal.Titulo1"/> <%if(session.getAttribute("codigoPersonal").equals(matricula)){%><button class="btn btn-sm" style="position:relative;top:-5;" onClick="document.location.href='hermanos.jsp'"><img height="20" src="../../imagenes/hermanos.png"> <b>Hermanos</b><%}%></button>
					</th>
				</tr>
				<tr valign="top"> 
					<td width="90">&nbsp;&nbsp;Matrícula:</td>
					<td><%=alumno.getCodigoPersonal()%></td>
                		<%session.setAttribute("mat",alumno.getCodigoPersonal());%>
               		<td width="129" rowspan="14" >
<% 			if (tieneFoto){	%>
						<div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="150"></div>   			 
<% 			
			}else{
				if (enLinea && virtual){
					if (tieneFotoEnviada){
%>
						<div id="sombra"><img src='../../admision/datos/imagenEnviada.jsp?id=<%=new java.util.Date().getTime()%>' width="150"></div>
						<a href="../../fotoBajar.jsp?mat=<%=matricula%>" title="Descargar la Foto"><img src='../../imagenes/descargar.png' width="30" ></a>&nbsp;
            			<a href="javascript:borrarFoto()" title="Borrar la Foto"><img src='../../imagenes/borrar2.gif' width="25" ></a>
<%						
					}else{
%>
						<div id="sombra"><img src='../../admision/datos/imagenEnviada.jsp?id=<%=new java.util.Date().getTime()%>' width="150"></div>
						<a href="javascript:camara()" title="Tomar la Foto"><img src='../../imagenes/camaraweb.png' width="20" ></a>&nbsp;
<%						
					}					
				}else{
%>
						<div id="sombra"><img src="../../foto?Codigo=<%=matricula%>&Tipo=O" width="150"></div>	
<%				}      			
      		}
%>	               		                		
                	</td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Nombre"/>:</td>
				  	<td><%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%></td>
				</tr>					
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.EstadoCivil"/>:</td>
					 <td><%=edoCivil%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Genero"/>:</td>
					<td><%if (alumno.getSexo().equals("M")) out.print("Masculino");else out.print("Femenino");%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Fecha"/> <spring:message code="aca.De"/> <spring:message code="aca.Nacimiento"/>:</td>
					<td><%=alumno.getFNacimiento()%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Bautizado"/>:</td>
					<td><%if (alumno.getBautizado().equals("S"))out.print("Si");else out.print("No");%></td>
				</tr>					
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Email"/>:</td>
					<td><%=alumno.getEmail()%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Curp"/>:</td>
					<td>
					    <%=alumno.getCurp()%>&nbsp;&nbsp;
<%					    boolean validaCurp = aca.alumno.AlumUtil.validarCurp(alumno.getCurp()); 
					    if (esExtranjero){
					    	out.println("Si no lo tienes, ¡ Tramitalo !");
					    }else if ( validaCurp == false ){ 
	 						out.println("<font color='red'>¡ No es válido !</font>");
 						}else if(validaCurp== true ){
 							out.println("<font color='purple'>¡ Es Correcto !</font>");
 						}
%> 					    
					</td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Religion"/>:</td>
					<td><%=aca.catalogo.CatReligionDao.getNombreReligion(conEnoc,alumno.getReligionId())%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Mentor"/>:</td>
					<td><%= mentorNombre %></td>
				</tr>					
				<tr valign="top">
					<td>&nbsp;&nbsp;<spring:message code="aca.Tipo"/> <spring:message code="aca.De"/> <spring:message code="aca.Alumno"/></td>
					<% 	String tipoAlum = academico.getTipoAlumno().trim();
						String inst = "";
						if(tipoAlum.equals("2") || tipoAlum.equals("6")){
							inst = " - "+aca.catalogo.CatInstitucion.getNombreInstitucion(conEnoc, academico.getObreroInstitucion());
						}
					%>
						<td><%=aca.catalogo.CatTipoAlumno.getNombreTipo(conEnoc, Integer.parseInt(tipoAlum))+inst%></td>
				</tr>
					<%if(alumnoUtil.esExtrangero(conEnoc,alumno.getCodigoPersonal())){%>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="aca.Nacionalidad"/>:</td>
					<td><%=aca.alumno.AlumUtil.getNacionalidad(conEnoc,alumno.getCodigoPersonal())%></td>
				</tr>
				<tr> 
					<td>&nbsp;&nbsp;<spring:message code="datosAlumno.portal.Vencimiento" />:</td>
					<td><%=alumnoUtil.getVencimientoFM3(conEnoc,alumno.getCodigoPersonal())%></td>
				</tr>
				<tr> 
					<td></td>
						<%
							SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							Date hoy = new Date();
							String color="",mensaje="";
							Date fechav = df.parse(alumnoUtil.getVencimientoFM3(conEnoc,alumno.getCodigoPersonal()));
							int dias = new Long((fechav.getTime()-hoy.getTime())/1000/60/60/24).intValue();
							if(dias>29 && dias<61){
								color="#F39603";
								mensaje="TU FM3 VENCERÁ EN "+dias+" DIAS, Estás a tiempo de arreglar tu situaci&oacute;n migratoria.";
							}
							else if(dias<30){
								color="#CE0000";
								if(dias>=1) mensaje="TU FM3 VENCERÁ EN "+dias+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
								if(dias<=0) mensaje=" TU FM3 VENCIÓ HACE "+(dias*-1)+" DIAS, Es urgente que arregles tu situaci&oacute;n migratoria.";
							}
							else {
								color="";
								mensaje=dias + " dias restantes.";
							}
						%>
					<td style="color:<%=color%>"><%=mensaje%></td>
				</tr>					
					<%}
					else{%>
				<tr><td></td></tr>
				<tr><td></td></tr>
				<% 	}%>				
				</table>
			</td>
			<td width="50%" valign="top">	
				<table style="width:100%" class="table table-nobordered table-fullcondensed">
					<tr valign="top">
						<th colspan="3"><spring:message code="datosAlumno.portal.Titulo5"/></th>
					</tr>
					<tr valign="top"> 
						<td nowrap width="150">&nbsp;&nbsp;<spring:message code="aca.Carrera"/>:</td>
						<td  ><b><%=alumnoUtil.getCarrera(conEnoc,matricula)%></b></td>
					 </tr>
					<tr valign="top"> 
						<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Plan"/>:</td>
						<td  ><b><%=plan.getPlanId()%></b></td>
					 </tr>
					<tr valign="top"> 
						<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Modalidad"/>:</td>
						<td  ><b><%=academico.getModalidad()%></b></td>
					 </tr>
					<tr valign="top"> 
						<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Inscrito" />:</td>						
						<td  ><b><%if (aca.alumno.AlumUtil.esInscrito(conEnoc, matricula)){ out.println("INSCRITO");}else{ out.println("No Inscrito");}%></b></td>
					 </tr>
				</table>
			
	

				<table style="width:100%" class="table table-nobordered table-fullcondensed">
					<tr valign="top">
						<th colspan="2">Datos del servicio becario</th>
					</tr>
						<tr valign="top">
						<form action="datos" name="forma" method="get">
							<th colspan="2">
<%
	ArrayList<aca.bec.BecPuestoAlumno> allPuestos = PuestoAlumnoU.getListAllEjerciciosAlum(conEnoc, matricula, " ORDER BY PUESTO_ID DESC");
%>
						<select name="puestoId" id="puestoId" class="input-xxlarge" onchange="document.forma.submit()">
							<%
								for(aca.bec.BecPuestoAlumno puesto: allPuestos){
									if(puestoId=="0"){
									puestoId = puesto.getPuestoId();
									}
							%>
									<option value="<%= puesto.getPuestoId()%>" <%if(puesto.getPuestoId().equals(puestoId)) out.print("selected"); %>>
									  <%=aca.financiero.ContCcostoUtil.getNombre(conEnoc, puesto.getIdEjercicio(), puesto.getIdCcosto()) %> || <%=puesto.getFechaIni() %>
									</option>
							<%
							  }
							%>
						</select> &nbsp; &nbsp;
						</th>
						</form>
					</tr>
<%
	PuestoAlumno.mapeaRegIdPuesto(conEnoc, puestoId);
	// Llena la lista de los informes anteriores
	ArrayList<aca.bec.BecInforme> lisInformes = BecInformeU.getListPuestoAlumno(conEnoc, matricula, PuestoAlumno.getPuestoId(), " ORDER BY ORDEN");
	//Llena el map de las horas del alumno
	java.util.HashMap <String, String> mapHorasAlumno 	= aca.bec.BecInformeAlumnoUtil.mapHorasPuestoAlumno(conEnoc, matricula, PuestoAlumno.getPuestoId());
	java.util.HashMap <String, String> mapEstadoAlumno	= aca.bec.BecInformeAlumnoUtil.mapEstado(conEnoc, matricula, PuestoAlumno.getPuestoId());
	//Llena el map de las evaluaciones del  alumnos
	java.util.HashMap <String, String> mapEvalAlumno 	= aca.bec.BecInformeAlumnoUtil.mapEvaluacionAlumno(conEnoc, matricula, PuestoAlumno.getPuestoId());
	String horasTotal = aca.bec.BecAcuerdoUtil.getHorasConv(conEnoc, matricula, PuestoAlumno.getPuestoId());
	
	String estadoPuesto = "";
	if (PuestoAlumno.getEstado().equals("P")) estadoPuesto = "Precontratado";
	if (PuestoAlumno.getEstado().equals("C")) estadoPuesto = "Contratado";
	if (PuestoAlumno.getEstado().equals("I")) estadoPuesto = "Inactivo";
%>			
					<tr valign="top">
					
						<td nowrap width="60%">
						  &nbsp;&nbsp;Lugar: <b><%=aca.financiero.ContCcostoUtil.getNombre(conEnoc, PuestoAlumno.getIdEjercicio(), PuestoAlumno.getIdCcosto())%></b>						  						  
						</td>
						<td nowrap width="40%">					   
						  Puesto: <b><%=aca.bec.BecCategoriaUtil.getNombreCategoria(conEnoc,PuestoAlumno.getCategoriaId())%></b>&nbsp; &nbsp;						  
						</td>
					</tr>
					<tr valign="top">						
						<td nowrap width="60%">
						  &nbsp;&nbsp;Vigencia: <b><%=PuestoAlumno.getFechaIni()%> al <%=PuestoAlumno.getFechaFin()%></b>						  
						</td>
						<td nowrap width="40%">
						  Estado: <b><%=estadoPuesto%></b>
						</td>
					</tr>
					<tr valign="top">
						<td nowrap width="60%">
						  &nbsp;&nbsp;Horas:
<%
				for(aca.bec.BecInforme informe: lisInformes){
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
						</td>
						<td nowrap width="40%">
						  Horas Convenio: <b><%=horasTotal%></b>
						</td>
					</tr>
					<tr valign="top" onClick="document.location.href='promedios.jsp'" title="Detalles de la evaluación">
						<td nowrap width="60%">
						  &nbsp;&nbsp;Evaluación:
<%
				for(aca.bec.BecInforme informe: lisInformes){
					String mes		= "";
					if ("123456789101112".contains(informe.getOrden()))
						mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(informe.getOrden()));
					else
						mes = "-";
					String nota	= "";
					if (mapEvalAlumno.containsKey(informe.getInformeId())){
						nota = mapEvalAlumno.get(informe.getInformeId());
						out.print(" <b>"+mes+" [ "+nota+" ]</b></a>&nbsp;&nbsp;");
					}				 	
				}
%>						  
						</td>
						<td nowrap width="40%">
						  Horas Faltantes: <b><%=(Integer.parseInt(horasTotal)-horasconv)%></b>
						</td>
					</tr>
					<tr valign="top">
						<th colspan="4">
							<div style='width:7px;height:7px;background:red;display:inline-block;'></div> <font size="2px">Sin Estado</font>
							<div style='width:7px;height:7px;background:#3A9727;display:inline-block;'></div> <font size="2px">Enviado</font>
							<div style='width:7px;height:7px;background:#0055cc;display:inline-block;'></div> <font size="2px"><spring:message code='aca.Autorizado'/></font>
							<div style='width:7px;height:7px;background:black;display:inline-block;'></div> <font size="2px">Contabilizado</font>
					</tr>
				</table>	
			</td>			
		</tr>		
		<tr>
			<td width="50%" valign="top">		
				<table class="table table-nobordered table-fullcondensed" style="width:100%">
		        <tr valign="top">
            		<th colspan="3"><spring:message code="datosAlumno.portal.Titulo6"/></th>
        		</tr>
			    <tr valign="top">
            		<td nowrap width="150">&nbsp;&nbsp;<spring:message code="aca.Padre"/>:</td>
			        <td><b><%=Tutor.getPNombre()%></b></td>
			    </tr>
            	<tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Madre"/>:</td>
            		<td><b><%=Tutor.getMNombre()%></b></td>
			    </tr>
            	<tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Tutor"/>:</td>
            		<td  ><b><%=Tutor.getTNombre()%></b></td>
			    </tr>
			    <tr valign="top">
            		<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Pais"/>:</td>
			        <td  ><b><%=aca.catalogo.PaisUtil.getPais(conEnoc, Tutor.getTPais())%></b>
					</td>
			    </tr>
            	<tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Estado"/>:</td>
            		<td  ><b><%=aca.catalogo.EstadoUtil.getNombreEstado(conEnoc,Tutor.getTPais(),Tutor.getTEstado())%></b></td>
			    </tr>
            	<tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Ciudad"/>:</td>
            		<td  ><b><%=aca.catalogo.CiudadUtil.getNombreCiudad(conEnoc, Tutor.getTPais(),Tutor.getTEstado(),Tutor.getTCiudad())%></b></td>
			    </tr>
			    <tr valign="top">
            		<td nowrap>&nbsp;&nbsp;<spring:message code="catalogos.division.Direccion"/>:</td>
			        <td  ><b><%=Tutor.getTDireccion()%></b></td>
            	</tr>
			    <tr valign="top">
            		<td nowrap>&nbsp;&nbsp;<spring:message code="catalogos.extension.Colonia"/>:</td>
			    	<td  ><b><%=Tutor.getTColonia()%></b></td>
			    </tr>
			    <tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="catalogos.extension.CodigoPostal"/>:</td>
			        <td  ><b><%=Tutor.getTCodigo()%></b></td>
			    </tr>
			    <tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="datosAlumno.portal.DatosApartado"/>:</td>
			        <td  ><b><%=Tutor.getTApartado()%></b></td>
			    </tr>
			    <tr valign="top">
			    	<td nowrap>&nbsp;&nbsp;<spring:message code="aca.Telefono"/>:</td>
			        <td  ><b><%=Tutor.getTTelefono()%></b></td>
			    </tr>
			    </table>
			</td>		
		</tr>
		</table>
	</td>
</tr>
</table>

<script>
	$('.nav-tabs').find('.Datos').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>