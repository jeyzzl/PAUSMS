<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.baja.BajaPaso"%>
<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.carga.CargaBloque"%>
<%@page import="aca.alumno.AlumEstado"%>
<%@page import="aca.alumno.AlumPlan"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.carga.Carga"%>
<%@page import="aca.alumno.AlumAcademico"%>
<%@page import="aca.acceso.Acceso"%>

<jsp:useBean id="bajaPaso" scope="page" class="aca.baja.BajaPaso"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="CargaBloqueU" scope="page" class="aca.carga.CargaBloqueUtil"/>
<jsp:useBean id="bajaPasoU" scope="page" class="aca.baja.BajaPasoUtil"/>
<jsp:useBean id="comAutorizacion" scope="page" class="aca.internado.ComAutorizacion"/>
<jsp:useBean id="comAutorizacionU" scope="page" class="aca.internado.ComAutorizacionUtil"/>
<jsp:useBean id="bajaAlumno" scope="page" class="aca.baja.BajaAlumno"/>
<jsp:useBean id="bajaAlumnoU" scope="page" class="aca.baja.BajaAlumnoUtil"/>
<jsp:useBean id="alumU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="bajaAlumpaso" scope="page" class="aca.baja.BajaAlumpaso"/>
<jsp:useBean id="bajaAlumpasoU" scope="page" class="aca.baja.BajaAlumpasoUtil"/>
<jsp:useBean id="alumPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="cursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumEstadoU" scope="page" class="aca.alumno.EstadoUtil"/>
<jsp:useBean id="AlumEstado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>

<script>
	function grabaCarga(){
		document.forma.submit();
	}
	
	function grabaBloque(){
		document.forma.submit();
	}	
</script>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	
	String accion				= request.getParameter("Accion");
	String carreraId			= aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoAlumno);
	String institucion 			= (String)session.getAttribute("institucion");
	String cargaId 				= request.getParameter("carga")==null?(String) session.getAttribute("cargaId"):request.getParameter("carga");	
	
	// Lista de cargas
	ArrayList<aca.alumno.AlumEstado> lisCargas = AlumEstadoU.getListaCargaInscrito(conEnoc, codigoAlumno);
	
	boolean existeCarga = false;
	for(aca.alumno.AlumEstado alumEstado : lisCargas){
		if (alumEstado.getCargaId().equals(cargaId)){
			existeCarga = true;
			break;
		}
	}	
	
	if ( (cargaId==null || cargaId.equals("xxxxxx") || !existeCarga) && lisCargas.size()>0){
		cargaId = lisCargas.get(0).getCargaId();
	}
	
	AlumEstado = AlumEstadoU.mapeaRegId(conEnoc, codigoAlumno, cargaId);
	String modalidad 			=  AlumEstado.getModalidadId();
	
	String blo = "0";
	for(aca.alumno.AlumEstado bloq : lisCargas){
		if(blo.equals("0"))
		blo = bloq.getBloqueId();
	}
	
	String bloqueId				=  request.getParameter("bloqueId")==null?blo:request.getParameter("bloqueId");
	String cargaTmp 			= "";
	
	int i = 0, contador = 1;
	
	boolean guarda = false;
	
	if(aca.acceso.AccesoUtil.getAccesos(conEnoc, codigoPersonal).indexOf(carreraId) != -1 | Boolean.parseBoolean(session.getAttribute("admin")+"")){
	
		if(accion == null)
			accion = "0";
		
		if(accion.equals("1")){
			guarda = true;
			bajaAlumno.setBajaId(bajaAlumnoU.maximo(conEnoc));
			bajaAlumno.setCodigoPersonal(codigoAlumno);
			bajaAlumno.setCargaId(cargaId);
			bajaAlumno.setCarreraId(carreraId);
			bajaAlumno.setPlanId(alumU.getPlanActivo(conEnoc, codigoAlumno));
			bajaAlumno.setFInicio(Fecha.getHoy());
			bajaAlumnoU.insertReg(conEnoc, bajaAlumno);
		}
		
		alumPlan.mapeaRegId(conEnoc, codigoAlumno);
%>
<head>
	<link href="../../academico.css" rel="STYLESHEET" type="text/css">	
	<STYLE TYPE="text/css">
		.tabbox
			{
				font-family: Arial, Helvetica, sans-serif;
				background: #eeeeee;
			}
		.Estilo1 {font-size: 7pt}
		.Estilo3 {color: #000000}
		
		.img {
		  width:100%;
		  height:900px;
		  background-repeat: no-repeat;
		  background-position: 445px 150px; 
		  background-size: 400px 400px;
		  background-image: url("../../imagenes/logo_fondo.png");
		}	
	</STYLE>
	
	<script type="text/javascript">
		function inicio(){
			var img = document.getElementById("fondo");
			var theWidth, theHeight;
			if (window.innerWidth){
			  theWidth = window.innerWidth 
			  theHeight = window.innerHeight 
			} 
			else if (document.documentElement && document.documentElement.clientWidth){
			  theWidth = document.documentElement.clientWidth 
			  theHeight = document.documentElement.clientHeight 
			} 
			else if (document.body){
			  theWidth = document.body.clientWidth 
			  theHeight = document.body.clientHeight 
			}
			img.style.left = (((theWidth)/2)-(img.offsetWidth/2))+"px";
			img.style.top = ((((theHeight/2)-(img.offsetHeight/2))<0)?0:((theHeight/2)-(img.offsetHeight/2)))+"px";
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
	<div class="container-fluid" >
		<form id="forma" name="forma" action="solicitud.jsp?Accion=1" method="post">
			<div class="well oculto">
				<b><spring:message code='aca.Carga'/>:</b>
				<select onchange='javascript:grabaCarga()' name="carga" style="width:350px;" class="input input-xlarge">
<%	
				for(aca.alumno.AlumEstado cargas : lisCargas){			
					if(!cargas.getCargaId().equals(cargaTmp)){
%>
					<option <%if(cargaId.equals(cargas.getCargaId()))out.print(" Selected ");%> value="<%=cargas.getCargaId()%>">[<%=cargas.getCargaId() %>] <%=aca.carga.CargaUtil.getNombreCarga(conEnoc, cargas.getCargaId())%></option>
<%		
					}
					cargaTmp = cargas.getCargaId();
				}
%>
				</select>
			 &nbsp;
				<b><spring:message code='aca.Bloque'/>:</b>
				<select onchange='javascript:grabaBloque()' name="bloqueId" style="width:350px;" class="input input-xlarge">
<%	
				for(aca.alumno.AlumEstado bloque : lisCargas){				
%>
					<option <%if(bloqueId.equals(bloque.getBloqueId()))out.print(" Selected ");%> value="<%=bloque.getBloqueId()%>">[<%=bloque.getBloqueId() %>] <%=CargaBloqueU.getNombre(conEnoc, cargaId, bloque.getBloqueId())%></option>
<%	
				}
%>
				</select>
			</div>
			<table >
				<tr>
					<td colspan="4" align="center" ><img alt=""  src="../../imagenes/encabezado.png" width="530" height="100"><br><br><br><br></td>
				</tr>
			</table>
			<h4>Baja Total <small>[<%=codigoAlumno %>] <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE") %> | <%=Fecha.getHoy()%></small></h4> 
			<br>
			<div>
				<b><spring:message code='aca.Modalidad'/>:</b> <%= aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, modalidad) %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				[<%=alumPlan.getPlanId() %>] <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, codigoAlumno)) %>
			</div>
			<br><br>
<!-- 			<div class="img"> -->
				<table style="width:950px"  border="1">
					<tr> 	
 						<td colspan="4" ><h1>Materias que se daran de Baja</h1><br></td> 	
	 				</tr>	
					<tr>
						<td width="5%"><strong>#</strong></td>
						<td width="40%" align="left"><strong><spring:message code='aca.Nombre'/></strong></td>
						<td width="45%" align="left"><strong><spring:message code='aca.Maestro'/></strong></td>
						<td width="10%" align="center"><strong><spring:message code='aca.Tipo'/></strong><br><br></td>
					</tr>
<%
				ArrayList<aca.vista.AlumnoCurso> lisCursos = cursoU.getListAlumnoCargaBloque(conEnoc, codigoAlumno, cargaId, bloqueId, "AND TIPOCAL_ID = 'I' ORDER BY NOMBRE_CURSO");
				for(int j = 0; j < lisCursos.size(); j++){
					alumnoCurso = (aca.vista.AlumnoCurso) lisCursos.get(j);
%>
					<tr>
						<td width="5%"><%=j+1 %></td>
						<td width="35px" align="left"><%=alumnoCurso.getNombreCurso() %></td>
						<td width="35px" align="left"><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, alumnoCurso.getMaestro(), "NOMBRE")%></td>
						<td width="35px" align="center"><%=alumnoCurso.getTipoCalId() %></td>
					</tr>			
<%
				}
%>
				</table><br><br>
				<table style="width:950px">	
<%
			ArrayList<aca.baja.BajaPaso> lisPasos = bajaPasoU.getListAll(conEnoc, "ORDER BY PASO_ID");
			int cont = 0;
			for(aca.baja.BajaPaso paso : lisPasos){
				bajaPaso = paso;
				switch(Integer.parseInt(bajaPaso.getPasoId())){
					case 1:{//Retencion y Mentoria
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
								<br><br><br>
									<br>______________________________ <br><br>
									 Firma <%= bajaPaso.getPasoNombre() %>
									<br><br><br><br>
<%
						if(guarda){						
							bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
							bajaAlumpaso.setPasoId("1");
							bajaAlumpaso.setFecha(Fecha.getHoy());
							bajaAlumpaso.setEstado("N");
							bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
						}
						contador++;
%>						
								</td>
<%		
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}break;
					case 2:{//Firma del coordinador	
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>						
								<td align="center">
									<br>______________________________ <br><br>
									 Firma <%= bajaPaso.getPasoNombre() %>
								</td>
<%
						if(guarda){						
							bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
							bajaAlumpaso.setPasoId("2");
							bajaAlumpaso.setFecha(Fecha.getHoy());
							bajaAlumpaso.setEstado("N");
							bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
						}
						contador++;	
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}break;
					case 3:{//Comedor
						if(comAutorizacionU.existeReg(conEnoc, codigoAlumno, cargaId, bloqueId)){
							cont++;
							if(cont == 1 || cont == 4){
%>
								<tr>
<%
							}
%>
									<td align="center">
										<br>______________________________ <br><br>
										 Firma <%= bajaPaso.getPasoNombre() %>
									</td>
<%
							if(guarda){							
								bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
								bajaAlumpaso.setPasoId("3");
								bajaAlumpaso.setFecha(Fecha.getHoy());
								bajaAlumpaso.setEstado("N");
								bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
							}
							contador++;
							if(cont == 3 || cont == 6){
%>
								</tr>
<%
							}
						}
					}break;
					case 4:{//Dormitorio
						if(aca.internado.AlumnoUtil.esInterno(conEnoc, codigoAlumno)){
							cont++;
							if(cont == 1 || cont == 4){
%>
								<tr>
<%
							}
%>
									<td align="center">
										<br>______________________________ <br><br>
										 Firma <%= bajaPaso.getPasoNombre() %>
									</td>

<%
							if(guarda){
								//System.out.println("guarda 4");
								bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
								bajaAlumpaso.setPasoId("4");
								bajaAlumpaso.setFecha(Fecha.getHoy());
								bajaAlumpaso.setEstado("N");
								bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
							}
							contador++;
							if(cont == 3 || cont == 6){
%>
								</tr>
<%
							}
						}
					}break;
					case 5:{//Direccion Juridica
						if(!aca.alumno.AlumUtil.getPaisId(conEnoc, codigoAlumno).equals("91")){
							cont++;
							if(cont == 1 || cont == 4){
%>
								<tr>
<%
							}
%>
									<td align="center">
										<br>______________________________ <br><br>
										 Firma <%= bajaPaso.getPasoNombre() %>
									</td>		
<%
							if(guarda){
								//System.out.println("guarda 5");
								bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
								bajaAlumpaso.setPasoId("5");
								bajaAlumpaso.setFecha(Fecha.getHoy());
								bajaAlumpaso.setEstado("N");
								bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
							}
							contador++;
							if(cont == 3 || cont == 6){
%>
								</tr>
<%
							}
						}
					}break;
					case 6:{//Finanzas Estudiantiles
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
									<br>______________________________ <br><br>
									 Firma <%= bajaPaso.getPasoNombre() %>
								</td>
<%
						if(guarda){
							//System.out.println("guarda 6");
							bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
							bajaAlumpaso.setPasoId("6");
							bajaAlumpaso.setFecha(Fecha.getHoy());
							bajaAlumpaso.setEstado("N");
							bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
						}
						contador++;
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}break;
					case 7:{
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
									<br>______________________________ <br><br>
									 Firma <%= bajaPaso.getPasoNombre() %>
								</td>
<%
						if(guarda){
							//System.out.println("guarda 7");
							bajaAlumpaso.setBajaId(bajaAlumno.getBajaId());
							bajaAlumpaso.setPasoId("7");
							bajaAlumpaso.setFecha(Fecha.getHoy());
							bajaAlumpaso.setEstado("N");
							bajaAlumpasoU.insertReg(conEnoc, bajaAlumpaso);
						}
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}break;
				}
			}
%>
			</table>
			<br><br><br><br>
<%
				if(!bajaAlumnoU.existeReg(conEnoc, codigoAlumno, cargaId, carreraId)){
%>
				<input type="submit" class="button" value="Iniciar Tramite" />
<%
				}else{
%>
				<table style="width:100%">
					<tr>
						<td align="center">
							<br>______________________________________ <br><br>
							<br><br>
							<span><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoAlumno, "NOMBRE") %></span>
						</td>
					</tr>
				</table>
<%
			}
%>		
<!-- 			</div> -->
		</form>
	</div>					
<script type="text/javascript">
	inicio();
</script>
<%
	}else{
%>
	<table>
		<tr>
			<td><font color="red" size="3"><b>Este alumno no pertenece a su carrera!!!</b></font></td>
		</tr>
	</table>
<%
	}
%>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>