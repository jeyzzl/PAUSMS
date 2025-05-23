<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.matricula.spring.MatAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");	
	String matricula 				= (String) session.getAttribute("codigoAlumno");
	boolean tieneFotoNormal 		= (boolean) request.getAttribute("tieneFotoNormal");
	boolean tieneFotoCuadrada		= (boolean) request.getAttribute("tieneFotoNormal");
	boolean esInscrito 				= (boolean) request.getAttribute("esInscrito");
	AlumPersonal alumPersonal		= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumPlan alumPlan				= (AlumPlan) request.getAttribute("alumPlan");
	AlumAcademico alumAcademico		= (AlumAcademico) request.getAttribute("alumAcademico");
	String facultadId				= (String) request.getAttribute("facultadId");
	String carreraId				= (String) request.getAttribute("carreraId");
	String religionNombre			= (String) request.getAttribute("religionNombre");
	String carreraNombre			= (String) request.getAttribute("carreraNombre");
	String fechaActualizacion		= (String) request.getAttribute("fechaActualizacion");
	String dimensiones				= (String) request.getAttribute("dimensiones");
	String dim[]					= dimensiones.split(",");
	String dimRecX[]				= dim[2].split(":");	
	String dimRecY[]				= dim[3].split(":");
	
	String codigoRFID				= request.getParameter("CodigoRFID")==null?"-":request.getParameter("CodigoRFID");
	String codigoCred				= request.getParameter("CodigoCred")==null?"-":request.getParameter("CodigoCred");
	String accion					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	String mensaje					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String prepa					= "";
	
	String edoCivil="";	
	if (alumPersonal.getEstadoCivil().equals("S")) edoCivil = "Single";
	else if (alumPersonal.getEstadoCivil().equals("C")) edoCivil = "Married";
	else if (alumPersonal.getEstadoCivil().equals("D")) edoCivil = "Divorced";
	else if (alumPersonal.getEstadoCivil().equals("V")) edoCivil = "Widowed";	
	
	// Verifica si el alumno es de prepa.
	if(facultadId.equals("107")){
		prepa = "Bachillerato";	
		session.setAttribute("carrera",prepa.toUpperCase());
	}else{
		session.setAttribute("carrera",carreraId);
	}
	
	session.setAttribute("matricula",alumPersonal.getCodigoPersonal());
	session.setAttribute("mat",alumPersonal.getCodigoPersonal());
	session.setAttribute("nombre",alumPersonal.getNombre());
	session.setAttribute("apellidos",alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno());
	
	//Verifica modalidad en linea
	boolean enLinea = false;
	if(!alumAcademico.getModalidadId().equals("1") && !alumAcademico.getModalidadId().equals("4")){
		enLinea = true;
	}
	
	if(matricula.substring(0,2).equals("98")){
		enLinea = false;
	}
	
	
	//------------------------Si esta en el virtual o en el academico-----------
	String direccion 		= request.getRequestURL().toString();
	boolean virtual = true;/*
	if(!direccion.contains("virtual")){
		virtual = false;
		enLinea = false;
	}
	*/
	//----------------------------------AUTORIZAR FOTO------------------------------------------------>

	String from = application.getRealPath("/WEB-INF/fotos2/")+"/"+ matricula + ".jpg";
	String to   = application.getRealPath("/WEB-INF/fotos/")+"/"+ matricula + ".jpg";
	
	boolean envio = aca.util.moverArchivo.validateFile(from);
	
	if(accion.equals("1")){//Hacer oficial la foto que envi� el alumno
		
		if(envio){
			aca.util.moverArchivo.fileMove(from,to);
		}
	
	}
	//------------------------Existe foto en foto2 (envi�)-----------
	envio = aca.util.moverArchivo.validateFile(from);
	//------------------------Si viene del listado-----------
	String linkReturn = request.getParameter("return");
	
	boolean back = false; 
	if(linkReturn!=null && linkReturn.equals("true")){
		back=true;
	}
	//-----------------------------------------
	
	MatAlumno matAlumno = (MatAlumno) request.getAttribute("matAlumno");
%>
<head>
  <script type="text/javascript">
	
	function credencial(){
		abrirVentana("credencial",415,295,0,0,"no","yes","yes","no","no","credencial");
	}
		
	function actualizarCredencial(){
		document.location.href="actualizar_credencial";
	}
			
	function camara(){
		document.location.href="tomarfoto";
	}
	
	function borrarFoto(codigoAlumno, tipo){		
		if (confirm("�Are you sure you want to erase the photo?")){
			document.location.href="borrar?Codigo="+codigoAlumno+"&Tipo="+tipo;
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
	
	function refrescar(){
		document.location.href="datos";
	}
	
	function autorizarFoto(){
		if(confirm("Are you sure you want to set this photo as the official? ")==true){
			document.forma.Accion.value="1";
			document.forma.submit();
		}	
	}
	function GrabarCodigo(){
		document.forma.Accion.value="2";
		document.forma.submit();
	}
	
	function GrabarCredencial(){
		document.forma.Accion.value="3";
		document.forma.submit();
	}
</script>
</head>

<STYLE TYPE="text/css">
	.tabbox{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
	
	#sombra {
		float:right;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, s� haces m�s grandes estos valores, el efecto de sombra es mayor tambi�n */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aqu� es donde ponemos la imagen como fondo colocando su ubicaci�n*/		
		position:relative;
		top:0px;
		left:0px;
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
	
</STYLE>
<body style="background-image: url(../../imagenes/fondoContenido.png);"  onload='inicio()'>
<div class="container-fluid">
	<h2>Student<small class="text-muted fs-6">
		( <%=matricula%> | <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> |&nbsp;
		<% if (alumPersonal.getSexo().equals("M")) out.print("Male");else out.print("Female");%> |&nbsp;
		<%=edoCivil%> |&nbsp;
		Birthday: <%=alumPersonal.getFNacimiento()%> )	
	</small>
	</h2>
	<div class="alert alert-info">
	<% if(back){%>
		<a class="btn btn-primary" href="../fotosEnlinea/enLinea">Back</a>
  	<% }%>
  	<% if (codigoPersonal.equals("9800308")){%> 
  		<a href="centrar" title="Centrar" class="btn btn-info btn-sm">Align Photos</a>
  	<% }%>
  	<% if (!mensaje.equals("-")){ 
  		out.print(mensaje);
  	}%>
	</div>
	<div class="row">
		<div class="col-9">
			<form action="datos?return=<%=linkReturn%>" method="post" name="forma" target="_self">
			<input type="hidden" name="Accion">
			<br>
			<table class="table table-sm table-bordered" style="width:100%">
			<%-- <thead class="table-info">	    	
        	<tr valign="top"> 
        		<th width="20%">Column</th>
            	<th>Value</th>             	          			
			</tr>
			</thead> --%>
	    	<%-- <tr> 
	        	<td>Hexadecimal code:</td>
	            <td><input name="CodigoRFID" type="text" size="20" value="<%=alumPersonal.getCodigoSe()%>"/> &nbsp; &nbsp; <a href="javascript:GrabarCodigo()" class="btn btn-primary btn-sm">Save</a></td>
	        </tr>
	        <tr> 
	        	<td>Decimal code:</td>
	            <td><input name="CodigoCred" type="text" size="20" value="<%=alumPersonal.getCredencial()%>"/></td>	       		
	        </tr>                        --%>
            <tr> 
                <td>Email:</td>
                <td><b><%=alumPersonal.getEmail()%></b></td>
            </tr>
            <%-- <tr> 
                <td>Id:</td>
                <td><b><%=alumPersonal.getCurp()%></b></td>
            </tr> --%>
            <tr> 
                <td>Denomination:</td>
                <td>
                	<b><%=religionNombre%>
                <% if (alumPersonal.getReligionId().equals("1") && alumPersonal.getBautizado().equals("S")) out.print("<span class='text-muted'>(Baptized)</span>");%></b></td>
            </tr>              
            <tr> 
                <td>Latest Update:</td>
                <td>
                	<b><%=fechaActualizacion%></b>
<% 		if (tieneFotoNormal){%>                
                	<b><input type='button' class="btn btn-primary btn-sm" value='Update' onclick='actualizarCredencial()'></b>&nbsp;
<% 		}%>
                </td>              
            </tr>              
<% 		if (codigoPersonal.equals("---")){%>
            <tr> 
                <td>Credential:</td>
                <td><b><input type='button' value='See Credential' onclick='credencial()'></b></td>
            </tr>
<% 		}%>
            <tr valign="top"> 
                <td>Course:</td>
                <td><b><%=carreraId%> - <%=carreraNombre%></b></td>
            </tr>
            <tr valign="top"> 
                <td><spring:message code="aca.Plan"/>:</td>
                <td><b><%=alumPlan.getPlanId()%></b></td>
            </tr>
            <tr valign="top"> 
                <td>Modality:</td>
                <td><b><%=alumAcademico.getModalidadId()%></b></td>
            </tr>
            <tr valign="top"> 
                <td>Residence:</td>
                <td><b> 
            <%	if (alumAcademico.getResidenciaId().equals("E")){
            		out.print("Day Student");            		
            	}else{ 
            		out.print("Boarding (Dorm "+alumAcademico.getDormitorio()+")");
            	}
            %>
                  </b></td>
            </tr>           
            <tr valign="top"> 
                <td>Enrollment:</td>
                <td><b> 
                  <%=esInscrito?"ENROLLED":"NOT ENROLLED"%>
                 </b>
                </td>
            </tr>
			</table>
			</form>		
		</div>
		<div class="col-3">
	<%	if(enLinea){ %>
	<%		if(envio){%>           	
            <img src='../../admision/datos/imagenEnviada' width="130"><br>
            &nbsp;<input type="button" value="Autorizar &#8658;" onclick="autorizarFoto();" style="position:relative;top:10px;">
    <%		}%>
    <%	} %>    			
			<div>
				<%=dimensiones%><br>
				<img src="../../foto?Codigo=<%=alumPersonal.getCodigoPersonal()%>&Tipo=O" class="rounded" width="200" style="border:1px gray solid;">
			</div>			
			<form name="frmRecortar" action="recortarFoto" method="post">
			<div>		
			  	<a href="tomarfoto" title="Take Picture"><img src='../../imagenes/camaraweb.png' width="20" ></a>&nbsp;
               	<a href="subir" title="Upload File"><img src='../../imagenes/upload.png' width="30"  style="position:relative;top:3px;"></a>&nbsp;
               	
<% 		if (tieneFotoNormal){%>                	                	
               	<a href="../../fotoBajar?Codigo=<%=matricula%>&Tipo=C" title="Download Picture"><img src='../../imagenes/descargar.png' width="30" ></a>&nbsp;              	
				<input name="Codigo" type="hidden" value="<%=matricula%>">
				<input name="Tipo" type="hidden" value="O">
				X=<input name="X" type="text" value="<%=dimRecX[1]%>" style="width:37px">
				Y=<input name="Y" type="text" value="<%=dimRecY[1]%>" style="width:37px">
				<button class="btn btn-primary btn-sm" type="submit"><i class="fas fa-cut"></i></button>&nbsp;&nbsp;
				<a href="javascript:borrarFoto('<%=alumPersonal.getCodigoPersonal()%>','O')" title="Delete Picture"><img src='../../imagenes/borrar2.gif' width="25" ></a>			
<% 			}%>	
			</div>
			</form>			
			<h5 class="mt-2">Square photo</h5>
			<div><img src="../../fotoCuadrada?Codigo=<%=alumPersonal.getCodigoPersonal()%>&Tipo=C" class="rounded" width="100" style="border:1px gray solid;"></div>
			<div>
			</div>
			<a href="javascript:borrarFoto('<%=alumPersonal.getCodigoPersonal()%>','C')" title="Delete Picture"><img src='../../imagenes/borrar2.gif' width="25" ></a>
<%		System.out.println(matAlumno.getEventoId());
		if(!matAlumno.getEventoId().equals("0")){%>
			<br>
			<a href="../../credencial_alum/credencial/guardar?EventoId=<%=matAlumno.getEventoId()%>&CodigoAlumno=<%=matAlumno.getCodigoPersonal()%>&Estado=A" class="btn small-btn btn-primary my-3">Mark Event</a>
<%		}%>
		</div>		
	</div>
</div>
</body> 