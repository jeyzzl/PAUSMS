<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="pestana" scope="page" class="aca.portal.Alumno"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="mentLog" scope="page" class="aca.mentores.MentLog"/>
<jsp:useBean id="Alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<%@page import="java.text.DecimalFormat"%>
<html>
<head>
	<link href="css/v.css" rel="STYLESHEET" type="text/css">
<%
	System.out.println("Pase por menuPortal.jsp");
	DecimalFormat getformato= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String act 				= request.getParameter("ac");	
	String modalidad		= "0";
	String formaPago		= aca.vista.InscritosUtil.getAlumFormaPago(conEnoc,codigoAlumno);
	
	Alumno = AlumUtil.mapeaRegId(conEnoc,codigoAlumno);
	boolean enLinea			= false;
	boolean validaCurp		= aca.alumno.AlumUtil.validarCurp(Alumno.getCurp());
	
	// Busca la modalidad del alumno y evalua si es el quien entró al portal del alumno..
	if (codigoPersonal.equals(codigoAlumno))
		modalidad = aca.alumno.AcademicoUtil.getModalidadId(conEnoc,codigoPersonal);
	
	if (modalidad.equals("5")||modalidad.equals("7")||modalidad.equals("8")||modalidad.equals("9")||
		modalidad.equals("10")||modalidad.equals("11")||modalidad.equals("12")||modalidad.equals("13")||
		modalidad.equals("14")||modalidad.equals("15")||modalidad.equals("16")||modalidad.equals("17")||
		modalidad.equals("18")){
		enLinea = true;
	}		
	//System.out.println("Alumno:"+codigoAlumno+":"+modalidad+":"+enLinea);
	if(act==null)act="1";	
	int ac=Integer.parseInt(act);	
	
	// Variables para calcular el saldo
	double saldo 			= 0;
	double pagare 			= 0;
	double saldoVencido 	= 0;
	double deudaContrato	= 0;
	
	//Registro de acceso de mentores a alumnos
	if(codigoPersonal.substring(0,2).equals("98")){
		mentLog.setMentorId(codigoPersonal);
		mentLog.setCodigoPersonal((String)session.getAttribute("codigoAlumno"));
		mentLog.setFolio(mentLog.maximoReg(conEnoc, codigoPersonal, mentLog.getCodigoPersonal()));
		mentLog.setTab(act);
		if(!mentLog.insertReg(conEnoc)){
			System.err.println("No se guardo el mentor "+codigoPersonal+" con el alumno "+mentLog.getCodigoPersonal()+" a la fecha "+aca.util.Fecha.getHoy()+" en el tab "+act);
		}
	}
	
	pagare			= aca.financiero.EstadoCuenta.getPagareNoVencido(conEnoc, codigoAlumno);
	saldo 			= alumnoUtil.getSaldo(conEnoc, codigoAlumno);
	saldoVencido 	= saldo + pagare;
	
	deudaContrato 	= aca.financiero.FesContratoFinancieroUtil.getAlumDeudaContrato(conEnoc, codigoAlumno);
	double deudaSaldoVencido	= aca.financiero.FesContratoFinancieroUtil.getCancelaPortal(conEnoc, codigoAlumno);	
	
	if ( !codigoPersonal.equals("9800660") &&  act.equals("1")){
	
		if (deudaContrato>0){ 
%>
<script type="text/javascript">
	alert("TU CONTRATO FINANCIERO ESTA VENCIDO Y PRESENTA UNA DEUDA DE: $<%=getformato.format(deudaContrato)%>, \n"+
		"PASA A LA CAJA A LIQUIDAR O A LA OFICINA DE FINANZAS EST. PARA HACER LOS ARREGLOS NECESARIOS,  \n"+
		"¡ A PARTIR DEL 19 DE ABRIL SERÁ RESTRINGIDO EL ACCESO AL PORTAL DEL ALUMNO Y AL E42 ! }");
</script>	
		
<%
		}else if(saldoVencido < 0 && !aca.financiero.FinPermisoUtil.tienePermiso(conEnoc, codigoAlumno) && act.equals("1")){		
%>
<script type="text/javascript">
	alert("Tu saldo deudor es de: $<%=getformato.format(saldoVencido)%>\n"+
		"Debes pagarlo antes del 18 de abril en la oficina de finanzas en horario de oficina.\n"+
		"¡ A partir del 19 de abril será restringido el acceos al portal del alumno y al E42 si tienes saldo deudor!");
/*	"TE RECORDAMOS QUE EL 4 DE OCTUBRE  INICIAN EXAMENES PARCIALES, PARA OBTENER EL PASE DE FINANZAS ESTUDIANTILES, DEBES PAGAR LO VENCIDO INCLUYENDO EL PAGARE DE OCTUBRE." */
</script>
<%
		}
}	


	if (Alumno.getNacionalidad().equals("91") && validaCurp==false && ac==1){
%>
<script type="text/javascript">
	alert("La Clave Única de Registro de Población(CURP) que tienes capturada \n en tus datos personales no es válida. \n ¡ Actualiza tus Datos !");
</script>
<%			
	}else if (!Alumno.getNacionalidad().equals("91") && validaCurp==false && ac==1){
%>
<script type="text/javascript">
	alert("¡ Recuerda que debes tramitar tu CURP !");
</script>
<%
	}
%>
<script type="text/javascript" src="../../png.js">
</script>
<script>
	function cambia(num){
		document.location.href="menuPortal.jsp?ac="+num;
		//window.parent.parent.parent.js.alerta("holaaaa");
	}
	
	function menuAlterno(){
		var menu = document.getElementById("menuAlterno");
		if(menu.style.visibility == "" || menu.style.visibility == "hidden"){
			menu.style.visibility = "visible";
			menu.style.width = "550px";
		}else{
			menu.style.visibility = "hidden";
			menu.style.width = "3px";
		}
	}
</script>
<style>
#tabs {position:absolute;top:0px;left:0px;}
#fondo {position:absolute;top:0px;left:0px;}
#menuAlterno{
	position: absolute;
	z-index: 100;
	top: -4px;
	left: 20px;
	background: #f4f7fc;
	visibility: hidden;
	width: 3px;
	overflow: hidden;
}

#ma{
	font-size: 10px;
}

td{
	padding-bottom: 0px;
	padding-top: 0px;
}
</style>
</head>

<body style="background-image: url('img/tabs/fondo.png');">
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>
	<!-- div id="fondo"><img src="img/tabs/fondo.png" ></div -->
		
<%	
	if ( (deudaContrato <= 0 && deudaSaldoVencido > -100) || ( codigoPersonal.substring(0,2).equals("98") )){
%>
	<div id="tabs">
	<table class="TablaTabs" width='100%'    >
		<tr>
			<td valign="top">
				<div><img src="../../imagenes/flechaB.jpg"  width="20px" onclick="menuAlterno();" /></div>
				<div id="menuAlterno">
					<table id="ma" width="100%">
						<tr>
							<td onClick="cambia(1); parent.contenidoP.location.href='cambiaPortal?pagina=resumen';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Inicio*
							</td>
							<td onClick="cambia(2); parent.contenidoP.location.href='cambiaPortal?pagina=datos.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Datos
							</td>
							<td onClick="cambia(3); parent.contenidoP.location.href='cambiaPortal?pagina=materias';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Materias
							</td>
							<td onClick="cambia(4); parent.contenidoP.location.href='cambiaPortal?pagina=tareas';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Tareas
							</td>
							<td onClick="cambia(5); parent.contenidoP.location.href='cambiaPortal?pagina=calificaciones.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Notas
							</td>
							<td onClick="cambia(6); parent.contenidoP.location.href='cambiaPortal?pagina=financiero';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Edo. Cta.
							</td>
							<td onClick="cambia(7); parent.contenidoP.location.href='cambiaPortal?pagina=documentos.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Documentos
							</td>
							<td onClick="cambia(8); parent.contenidoP.location.href='cambiaPortal?pagina=servicio';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Serv. Soc.
							</td>
						</tr>
						<tr>
							<td onClick="cambia(9); parent.contenidoP.location.href='cambiaPortal?pagina=titulacion';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Titulación
							</td>
							<td onClick="cambia(10); parent.contenidoP.location.href='cambiaPortal?pagina=disciplina.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Disciplina
							</td>
							<td onClick="cambia(11); parent.contenidoP.location.href='cambiaPortal?pagina=avance';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Gradua.
							</td>
							<td onClick="cambia(12); parent.contenidoP.location.href='cambiaPortal?pagina=cumple';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Cumple.
							</td>
							<td onClick="cambia(13); parent.contenidoP.location.href='cambiaPortal?pagina=convalidacion';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Conv.
							</td>
							<td onClick="cambia(14); parent.contenidoP.location.href='cambiaPortal?pagina=aptitud';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Aptitud F&iacute;sica
							</td>
							<% if (modalidad.equals("1")||modalidad.equals("5")){ %>
							<td onClick="cambia(15); parent.contenidoP.location.href='inscripcion?pagina=matricula.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Inscripci&oacute;n
							</td>
							<%} %>
							<td onClick="cambia(16); parent.contenidoP.location.href='cambiaPortal?pagina=becas';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Becas
							</td>
							<td onClick="cambia(17); parent.contenidoP.location.href='cambiaPortal?pagina=opciones';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Cultura
							</td>
							<td onClick="cambia(18); parent.contenidoP.location.href='cambiaPortal?pagina=opciones';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Opciones
							</td>
							<td onClick="cambia(19); parent.contenidoP.location.href='cambiaPortal?pagina=voto.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Federación
							</td>
<!-- 							<td onClick="cambia(20); parent.contenidoP.location.href='cambiaPortal?pagina=votoGradua';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;"> -->
<!-- 								Graduandos -->
<!-- 							</td> -->
							<td onClick="cambia(20); parent.contenidoP.location.href='cambiaPortal?pagina=votoMoodie.jsp';" onmouseover="this.style.backgroundColor='#95b3de';" onmouseout="this.style.backgroundColor='#f4f7fc';" style="cursor:pointer; border-left:dotted 1px gray;">
								Moodies
							</td>
						</tr>
					</table>
				</div>
			</td>
			<td align='left'>
			<div id="header">
			  <ul>
<%
				out.print(pestana.dibujaTab2(1,ac,"cambiaPortal?pagina=resumen","Inicio"));
				out.print(pestana.dibujaTab2(2,ac,"cambiaPortal?pagina=datos.jsp","Datos"));
				out.print(pestana.dibujaTab2(3,ac,"cambiaPortal?pagina=materias","Materias"));
				out.print(pestana.dibujaTab2(4,ac,"cambiaPortal?pagina=tareas","Tareas"));
				out.print(pestana.dibujaTab2(5,ac,"cambiaPortal?pagina=calificaciones","Notas"));
				out.print(pestana.dibujaTab2(6,ac,"cambiaPortal?pagina=financiero.jsp","Edo. Cta."));
				out.print(pestana.dibujaTab2(7,ac,"cambiaPortal?pagina=documentos.jsp","Documentos"));
				out.print(pestana.dibujaTab2(8,ac,"cambiaPortal?pagina=servicio","Serv. Soc."));
				out.print(pestana.dibujaTab2(9,ac,"cambiaPortal?pagina=titulacion","Titulación"));
				out.print(pestana.dibujaTab2(10,ac,"cambiaPortal?pagina=disciplina.jsp","Disciplina"));
				out.print(pestana.dibujaTab2(11,ac,"cambiaPortal?pagina=avance.jsp","Gradua."));
				out.print(pestana.dibujaTab2(12,ac,"cambiaPortal?pagina=cumple","Cumple."));
				out.print(pestana.dibujaTab2(13,ac,"cambiaPortal?pagina=convalidacion","Conv."));
				out.print(pestana.dibujaTab2(14,ac,"cambiaPortal?pagina=aptitud","Aptitud F&iacute;sica"));
				//System.out.println(modalidad);
				if ( enLinea ){
					out.print(pestana.dibujaTab2(15,ac,"cambiaPortal?pagina=inscripcion","Inscripci&oacute;n"));
				}
				out.print(pestana.dibujaTab2(16,ac,"cambiaPortal?pagina=becas","Becas"));
				out.print(pestana.dibujaTab2(17,ac,"cambiaPortal?pagina=disponibles.jsp","Cultura"));
				out.print(pestana.dibujaTab2(18,ac,"cambiaPortal?pagina=opciones","Opciones"));
				out.print(pestana.dibujaTab2(19,ac,"cambiaPortal?pagina=voto.jsp","Federación"));
// 				out.print(pestana.dibujaTab2(20,ac,"cambiaPortal?pagina=votoGradua","Graduandos"));
				out.print(pestana.dibujaTab2(20,ac,"cambiaPortal?pagina=votoMoodie.jsp","Moodies"));
%>
 			</ul>
		</div>			
		</td>
		</tr>
	</table>
	</div>
<%	} %>	
<%@ include file= "../../cierra_enoc.jsp" %>