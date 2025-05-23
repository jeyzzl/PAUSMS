<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.internado.spring.IntDormitorio"%>
<%@ page import= "aca.internado.spring.IntAcceso"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.internado.spring.IntCuarto"%>
<%@ page import= "aca.internado.spring.IntAlumno"%>
<%	
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	String rolUsuario		= (String) session.getAttribute("rolInternado");
	boolean esAdmin			= (boolean)request.getAttribute("esAdmin");
	boolean esPreceptor		= (boolean)request.getAttribute("esPreceptor");
	boolean existeAlumno	= (boolean)request.getAttribute("existeAlumno");
	String dormitorioId		= (String)request.getAttribute("dormitorioId");	
	String accion 			= (String)request.getAttribute("accion");
	String cuartoId			= (String)request.getAttribute("cuartoId");
	String pasillo			= (String)request.getAttribute("pasillo");
	String pasilloMonitor	= (String)request.getAttribute("pasilloMonitor");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	
	String fechaIni			= (String)request.getAttribute("fechaIni");
	String fechaFin			= (String)request.getAttribute("fechaFin");
	String mensaje			= (String)request.getAttribute("mensaje");
	boolean mn				= (boolean)request.getAttribute("mn");
	
	IntDormitorio dormi		= (IntDormitorio)request.getAttribute("dormi");
	
	HashMap<String,String> mapAlumnoFecha = (HashMap<String,String>)request.getAttribute("mapAlumnoFecha");
	HashMap<String, String> mapPasillo 	  = (HashMap<String,String>)request.getAttribute("mapPasillo"); 
	
	String pasilloA = mapPasillo.containsKey("A")?mapPasillo.get("A"):"0";
	String pasilloB = mapPasillo.containsKey("B")?mapPasillo.get("B"):"0";
	String pasilloC = mapPasillo.containsKey("C")?mapPasillo.get("C"):"0";
	String pasilloD = mapPasillo.containsKey("D")?mapPasillo.get("D"):"0";	
	String pasilloE = mapPasillo.containsKey("E")?mapPasillo.get("E"):"0";
	String pasilloF = mapPasillo.containsKey("F")?mapPasillo.get("F"):"0";	
		
	// Lista de cuartos en el dormitorio 
	List<IntCuarto> lisCuartos = (List<IntCuarto>)request.getAttribute("lisCuartos");
	// Lista de alumnos en el dormitorio
	List<IntAlumno> lisAlumnos = (List<IntAlumno>)request.getAttribute("lisAlumnos");
	
	HashMap<String, IntAlumno> mapCamas 		= (HashMap<String,IntAlumno>)request.getAttribute("mapCamas");
	// Map de alumnos
	HashMap<String, AlumPersonal> mapAlumnos 		= (HashMap<String,AlumPersonal>)request.getAttribute("mapAlumnos");
	// Map de Datos Academicos
	HashMap<String, AlumAcademico> mapAcademico 	=  (HashMap<String,AlumAcademico>)request.getAttribute("mapAcademico");
	// Map de inscritos
	HashMap<String, String> mapInscritos 			= (HashMap<String,String>)request.getAttribute("mapInscritos");
	// Mapa de lugares ocupado en cada cuarto
	HashMap<String, String> mapOcupados 			= (HashMap<String,String>)request.getAttribute("mapOcupados");
%>
<%@ include file="portal.jsp" %>
<style>	
	.green{ color:green; background:white}

	.radio{
		width: 50px;
       	height: 50px;
       	border-radius: 50%;
	}
</style>
<script>	
	function RemoverAlumnos(button){
		if(confirm("This operation removes ALL students from this Dormitory, do you want to continue?") == true){
			window.location.href = button.href; // Navigate to the href of the button
		}			
	}
	function getFoto(codigo, cuarto){
		$("#icono"+codigo).addClass("green");
		$("#"+cuarto).html("<img src='../../fotoMenu?Codigo="+codigo+"' class='radio'/>");
	}
		
	function quitarFoto(codigo, cuarto){
		$("#"+cuarto).html("");
		$("#icono"+codigo).removeClass("green");
	}
	
	function agregar(c,pasillo){
		document.forma.cuarto.value = c;
		document.forma.accion.value = "agregar";
		document.forma.action = "asignarCuartos?Pasillo="+pasillo;
		document.forma.submit();
	}
	function eliminar(cp,c){
		if (confirm("Are you sure you want to delete the student with ID "+cp+" from room "+c+"?")){
			document.forma.cP.value = cp;
			document.forma.cuarto.value = c;
			document.forma.accion.value = "eliminar";
			document.forma.submit();
		}
	}
	function modificar(cp,r){
		document.forma.cP.value = cp;
		document.forma.r.value = r;
		document.forma.accion.value = "modificar";
		document.forma.submit();
	}
	function update(cp,r){
		document.forma.cP.value = cp;
		document.forma.r.value = r;
		document.forma.accion.value = "update";
		document.forma.submit();
	}
	function guardar(room, bed){		
		document.forma.cuarto.value=room;
		document.forma.orden.value=bed;
		document.forma.accion.value="guardar";
		document.forma.submit();		
	}
	function noTecla(){
		alert("Enter only the ID number. Names will display automatically");
		return false;
	}
	function buscar(cuarto){
		abrirVentana("bal",600,500,100,250,"no","yes","yes","no","no","buscarAlumno?cuarto="+cuarto,false);
	}
	function fullScreen(){
		abrirVentana("fs",screen.width-10,screen.height-100,1,1,"no","yes","yes","no","no","asignarCuartos",false);
	}
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
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
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}			
	function chkEnter(e,c){
		if (e.keyCode) keycode=e.keyCode; else keycode=e.which;
		if (keycode == 13){
			guardar(c);
		}
	}
	function Recargar(){		
		document.forma.submit();
	}
	
</script>
<body onunload='opener.forma.submit()'>
<div class="container-fluid">
	<h3>Registered students <%=dormi.getNombre()%>  <small class="text-muted fs-6">( <%=existeAlumno?"Add "+codigoAlumno+"-"+nombreAlumno:"Load a student"%> )</small></h3>
	<form name = "forma" method = "post" action = "asignarCuartos">
		<input type = "hidden" name = "Pasillo" value="<%=pasillo%>">
		<input type = "hidden" name = "accion">
		<input type = "hidden" name = "cuarto">
		<input type = "hidden" name = "orden">
		<input type = "hidden" name = "cP">
		<input type = "hidden" name = "nT">			
		<div class="alert alert-info d-flex align-items-center">
<%			
	if(esAdmin || esPreceptor){
		out.print("&nbsp;<a href='../../internado/dormitorios/dormitorios' class='btn btn-info'><i class='icon-white icon-arrow-left'></i>Menu</a>&nbsp; &nbsp;");
		out.print("&nbsp;<a onclick='RemoverAlumnos(this);' href='borrarAlumnosDormitorio' class='btn btn-danger'>Remove All Students</a>&nbsp; &nbsp;");
	}
%>
			<h5><spring:message code="portal.preceptor.asignarCuartos.pasillos"/>:</h5>&nbsp;&nbsp;	
			
<%
			if(rolUsuario.equals("A") || rolUsuario.equals("P")){
%>
			<a value="A" href="asignarCuartos?Pasillo=A" class="btn <%=pasillo.equals("A")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">A
 				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloA%></span>
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a value="B" href="asignarCuartos?Pasillo=B" class="btn <%=pasillo.equals("B")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">B
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloB%></span>
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a value="C" href="asignarCuartos?Pasillo=C" class="btn <%=pasillo.equals("C")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">C
 				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloC%></span>
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a value="D" href="asignarCuartos?Pasillo=D" class="btn <%=pasillo.equals("D")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">D
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloD%></span>
			</a>		
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a value="E" href="asignarCuartos?Pasillo=E" class="btn <%=pasillo.equals("E")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">E
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloE%></span>
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a value="F" href="asignarCuartos?Pasillo=F" class="btn <%=pasillo.equals("F")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill">F
				<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloF%></span>
			</a>
			<%
			}else{
%>
			<a value="<%=pasilloMonitor%>" href="asignarCuartos?Pasillo=<%=pasilloMonitor%>" class="btn <%=pasillo.equals("A")?"btn-dark":"btn-outline-dark"%> position-relative rounded-pill"><%=pasilloMonitor%>
 					<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary" style="font-size:7pt;"><%=pasilloMonitor%></span>
			</a>
<%
			}
%>
			&nbsp;&nbsp;
			<%=mensaje.equals("-")?"":" "+mensaje%>
		</div>	
		<table class="tabbox" style="width:100%; height:99%; margin:0 auto;">
		<tr valign='top'>
			<td colspan='2'>&nbsp; &nbsp;<span class='badge rounded-pill bg-danger'>(DS)</span> = Day Student &nbsp;&nbsp;&nbsp; <span class='badge rounded-pill bg-warning'>(NE)</span> = Not Enrolled</font></td>
		</tr>
		</table>
		<br/>
		<div class="row">	
<%			
		int cont=1,asig=0,nT=0,nG=0;	
		
		for (int i = 0; i<lisCuartos.size(); i++,cont++){
			IntCuarto cuarto = lisCuartos.get(i);
			
			String ocupados = "0";
			String colorCuarto = "alert-info";
			if (mapOcupados.containsKey(cuarto.getCuartoId())){
				ocupados = mapOcupados.get(cuarto.getCuartoId()); 
				if (ocupados.equals(cuarto.getCupo())){
					colorCuarto = "alert-danger";
				}
			}					
%>				
			<div class="col-xl-4 col-lg-4 col-md-6 col-sm-2">
				<div class="alert <%=colorCuarto%> d-flex justify-content-between">
					<h4>
						Room<b> <%=cuarto.getCuartoId()%></b> <small class="text-muted fs-5">( Capacity <%=cuarto.getCupo()%> )</small>
					</h4>								
					<div id="<%=cuarto.getCuartoId()%>" class="radio"></div>
				</div>					
				<table class="table table-sm table-bordered" >
<%		
			int row = 0;
			for (int j=0; j < Integer.valueOf(cuarto.getCupo()); j++){

				
				//IntAlumno dormiAlumno = lisAlumnos.get(j);			
				if(mapCamas.containsKey(cuarto.getCuartoId()+"-"+String.valueOf(j+1))){
					IntAlumno dormiAlumno = mapCamas.get(cuarto.getCuartoId()+"-"+String.valueOf(j+1));
				boolean dentroFechas = true;
				String fecha = "";
				if(mapAlumnoFecha.containsKey(dormiAlumno.getCodigoPersonal())){
					    
					 fecha = mapAlumnoFecha.get(dormiAlumno.getCodigoPersonal());
					SimpleDateFormat formatter 	= new SimpleDateFormat("dd/MM/yyyy");
					Date fechaIns 				= formatter.parse(fecha);
					Date fechaIncio 			= formatter.parse(fechaIni);
					Date fechaFinal 			= formatter.parse(fechaFin);
					
					if(fechaIns.getTime() < fechaIncio.getTime() ){					
						dentroFechas = false;
					}
				}
				
				if ( dormiAlumno.getCuartoId().equals(cuarto.getCuartoId())){
					row++;
					
					String matricula = "";
					String alumnoNombre = "-";
					if (mapAlumnos.containsKey(dormiAlumno.getCodigoPersonal())){
						AlumPersonal alumno = mapAlumnos.get(dormiAlumno.getCodigoPersonal());
						alumnoNombre = alumno.getNombre()+" "+(alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno();
						matricula = dormiAlumno.getCodigoPersonal();
					}
					
					String alumnoResidencia = "-";
					String alumnoDormitorio = "-";
					if (mapAcademico.containsKey(dormiAlumno.getCodigoPersonal())){					
						alumnoResidencia = mapAcademico.get( dormiAlumno.getCodigoPersonal()).getResidenciaId();
						alumnoDormitorio = mapAcademico.get( dormiAlumno.getCodigoPersonal()).getDormitorio();
					}
					
					boolean cama = dormiAlumno.getOrden().equals(String.valueOf(j+1));
					nT++;
%>				
				
				<tr>
					<td>
						<%=j+1%>.
						<a href="javascript:eliminar('<%=dormiAlumno.getCodigoPersonal()%>','<%=cuarto.getCuartoId()%>')" class="fas fa-trash-alt" title='Delete' style="color:red"></a>								 
						<a title="Personal data" target="_blank" href="datosPersonales?CodigoAlumno=<%=dormiAlumno.getCodigoPersonal()%>"><i class="fas fa-user"></i></a>&nbsp;
						<a title="Parents/mentor data" target="_blank" href="datosPadres?CodigoAlumno=<%=dormiAlumno.getCodigoPersonal()%>"><i class="fas fa-people-arrows"></i></i></a>&nbsp;
						<a title="Student Portal" target="_blank" href="resumen?CodigoAlumno=<%=dormiAlumno.getCodigoPersonal()%>"><i class="fas fa-id-card"></i></a>&nbsp;
						<i id="icono<%=matricula%>" class="fas fa-portrait" onmouseover="javascript:getFoto('<%=dormiAlumno.getCodigoPersonal()%>','<%=cuarto.getCuartoId()%>')" onmouseout="javascript:quitarFoto('<%=dormiAlumno.getCodigoPersonal()%>','<%=cuarto.getCuartoId()%>')"></i>
						<%=matricula%> | <%=alumnoNombre%> 
<%
					if (alumnoResidencia.equals("E")){
						out.print("<span class='badge rounded-pill bg-danger'>(DS)</span>");
					}

					if (!mapInscritos.containsKey( dormiAlumno.getCodigoPersonal())){
						out.print("<span class='badge rounded-pill bg-warning'>(NE)</span>");
					}
					
					if (!dormitorioId.equals(alumnoDormitorio) && !alumnoDormitorio.equals("0")){
						out.print("<span class='badge rounded-pill bg-success'>(H"+alumnoDormitorio+")</span>");
					}
					
					if(!dentroFechas){
						out.print("<span class='badge rounded-pill bg-dark'>("+fecha+")</span>");
					}
%>	
					</td>
				</tr>
<%				
					}
				}else{ %>
					<tr><td><%=j+1%>. <a href="javascript:guardar('<%=cuarto.getCuartoId()%>','<%=j+1%>')" class="btn btn-success btn-sm rounded-pill"><i class="fas fa-plus-square"></i></a></td></tr>
<%	
			  }												
			}
	%>		

				</table> 
			</div>					
<%			if (cont % 3==0){ %>		
		</div>
		<div class="row">
<%			}
		}
%>
		</div>
		<script>document.forma.nT.value='<%=nT%>';</script>
		<table style="width:'100%'     align='center'"><tr><td background="../../imagenes/shadow.gif" height="4"></td></tr></table>
	</form>		
</div>
</body>
<script>
	
</script>