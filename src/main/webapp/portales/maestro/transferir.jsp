<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="java.text.SimpleDateFormat,java.util.Date"%>
<%@ page import="aca.archivos.ArchivoVO"%>

<jsp:useBean id="bArchivos" scope="page" class="aca.archivos.Archivos"/>
<jsp:useBean id="bArchivosProfesor" scope="page" class="aca.archivos.ArchivosProfesor"/>
<jsp:useBean id="krdxAlumnoEval" scope="page" class="aca.kardex.KrdxAlumnoEval"/>
<html>
<head>
<script type="text/javascript" src="portal.js"></script>
<%
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
	DriverManager.registerDriver (new org.postgresql.Driver());
	Connection conn2	= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	Statement stmt2		= conn2.createStatement();
%>
<link href="css/pa<%=colorPortal%>.css" rel="STYLESHEET" type="text/css">
<style type="text/css">
<!--
a{text-decoration:none;color:#008000;
}
a:hover{color:#000080;}
.CellStyle1{font-size:8pt; color:#B2B2B2;  text-align:center; }
.CellStyle2{ color:#008000;}
TEXTAREA{font-family:'arial';font-size:11;color:gray; border-width:1px;}
-->
</style>

<div class="container-fluid">
	<h2>List of files in Database Administration <small>name</small> </h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluar?cursoCargaId"><spring:message code="aca.Regresar"/></a>
	</div>
</div>



<script>
	function checa(){
		checados=0;
		if(document.forma.checador.value=="MandarAlumno"){
			if(document.forma.mats.value==undefined){
				for(i=0;i<document.forma.mats.length;i++)
					if(document.forma.mats[i].checked)
						checados++;
				if(checados>0){
					document.forma.matriculas.value="";
					document.forma.idDestino.value="";
					for(i=0;i<document.forma.mats.length;i++){
						if(document.forma.mats[i].checked)
							document.forma.matriculas.value=document.forma.matriculas.value+document.forma.mats[i].value+"_N,";
					}
					document.forma.idDestino.value=document.forma.idDest.value;
					verificaArchivo();
				}else
					alert("You need to choose a recipient");
			}else{
				if(document.forma.mats.checked){
					document.forma.matriculas.value="";
					document.forma.idDestino.value="";
					document.forma.idDestino.value=document.forma.idDest.value;
					document.forma.matriculas.value=document.forma.mats.value+"_N,";
					verificaArchivo();
				}
				else
					alert("You need to choose a recipient");
			}
		}
	}
	
	function submitAlumno(){
			document.forma.submit();
	}
	
	function checaTodos(){
		if(document.forma.mats.value==undefined){
			for(i=0;i<document.forma.mats.length;i++)
				document.forma.mats[i].checked=true;
		}else
			document.forma.mats.checked=true;
	}
	function checaTodosBorrar(){
	 if(document.forma.listaEliminar!=undefined){
	  if(document.forma.selTodosChk.checked){
		if(document.forma.listaEliminar.value==undefined){
			for(i=0;i<document.forma.listaEliminar.length;i++)
				document.forma.listaEliminar[i].checked=true;
		}else
			document.forma.listaEliminar.checked=true;
	  }else{
		if(document.forma.listaEliminar.value==undefined){
			for(i=0;i<document.forma.listaEliminar.length;i++)
				document.forma.listaEliminar[i].checked=false;
		}else
			document.forma.listaEliminar.checked=false;
	  }
	 } 
	}
	function checaNinguno(){
		if(document.forma.mats.value==undefined){
			for(i=0;i<document.forma.mats.length;i++)
				document.forma.mats[i].checked=false;
		}else
			document.forma.mats.checked=false;
	}
	function borra(parametros){
		var archivos="";
		var checados=0;
		if(document.forma.listaEliminar!=undefined){
		  if (confirm("Are you sure you want to delete these files?")){
			if(document.forma.listaEliminar.length!=undefined){
				for(i=0;i<document.forma.listaEliminar.length;i++)
					if(document.forma.listaEliminar[i].checked)
						checados++;
				if(checados>0){
					for(i=0;i<document.forma.listaEliminar.length;i++)
						if(document.forma.listaEliminar[i].checked)
							archivos+=document.forma.listaEliminar[i].value+"~";
					document.location.href="transferir?accion=borrar&archivosBorrar="+archivos+parametros;
				}
				else alert("The files to be deleted must be selected");
			}else{
				if(document.forma.listaEliminar.checked){
					archivos+=document.forma.listaEliminar.value+"~";
					document.location.href="transferir?accion=borrar&archivosBorrar="+archivos+parametros;
				}
				else alert("The files to be deleted must be selected");
			}
		  }
		}
	}
	
	function checaCuantasLetras(i){
		var txt=document.forma.comentario.value;
		var n=txt.length;
		if (n>i)
			document.forma.comentario.value=txt.substring(0,i);
	}
	//------------- para grabar la nota ---------------
	function grabarNota(obj, notaAnterior, matricula, cursoCarga, evaluacion){
		var inputNota = obj.parentNode.firstChild.nextSibling;
		var url = "grabar_nota?matricula="+matricula+"&cursoCarga="+cursoCarga+"&evaluacion="+evaluacion+"&nota="+inputNota.value;
		var req = initRequest();
		req.onreadystatechange = function() {
			if(req.readyState == 4){
				if(req.status==404) {
					alert("Page not found. Contact support");
					inputNota.value = notaAnterior;
				}
				if(req.status == 200){
					eval(req.responseText);
				}else if (req.status == 204){
					alert("Error while saving the grade");
					inputNota.value = notaAnterior;
				}
			}
		};
		req.open("get", url, true);
		req.send(null);
	}
	
	function initRequest() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			isIE = true;
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
</script>
</head>
<body>
<%	
	int error=0;
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.0");
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
	int transferido=-1;
	String accion=request.getParameter("accion");
	String vista=request.getParameter("vista");
	//System.out.println("Valor de vista:"+vista);
	String id=request.getParameter("id");
	String sCursoCargaId=request.getParameter("sCursoCargaId");
	String para=request.getParameter("al");
	if(para==null)para="";
	String origen=request.getParameter("origen");
	if(origen==null)origen="";
	String matricula=request.getParameter("matricula");
	String alumno=request.getParameter("alumno");
	if(alumno==null)alumno="";
	String nomProfesor=request.getParameter("nomProfesor");
	if(nomProfesor==null)nomProfesor="";
	String nomMateria=request.getParameter("nomMateria");
	if(nomMateria==null)nomMateria="";
	String path="/portales/archivos/";
	if(matricula==null) matricula=(String) session.getAttribute("codigoAlumno");
	if(accion==null)accion="";
	
	if(accion.equals("1")&&vista.equals("xEstrategia")){
		
		Date fecha = new Date();
		String ruta=application.getRealPath(path);
		MultipartRequest multi=null;
		
	        multi = new MultipartRequest(request,ruta,5*1024*1024); 
	        String nombre=multi.getFilesystemName("archivo");
			nomProfesor=multi.getParameter("nomProfesor");
			nomMateria=multi.getParameter("nomMateria");
			String comentario=multi.getParameter("comentario");
			if(comentario==null)comentario="";
			
			transferido=0;
			ArchivoVO archivo= new ArchivoVO();
			archivo.setCodigoPersonal(matricula);
			archivo.setFecha(fecha.toString());
			archivo.setId(id);
			archivo.setNombre(nombre);
			archivo.setComentario(comentario);
			
			bArchivos.guardaArchivoenBD(conn2,archivo,ruta,nombre);
			

	}
	
	if(accion.equals("1")&&vista.equals("MandarAlumno")){
		
		String profesor=(String) session.getAttribute("codigoEmpleado");
		Date fecha = new Date();
		String ruta=application.getRealPath(path); //System.out.println("Ruta:"+ruta);
		
		try{			
		//System.out.println("Entre a Try ");		
        MultipartRequest multi = new MultipartRequest(request,ruta,20*1024*1024); 
        String nombre=multi.getFilesystemName("archivo");
        String matriculas=multi.getParameter("matriculas");
		sCursoCargaId=multi.getParameter("sCursoCargaId");
		id=multi.getParameter("idDestino");
		String comentario=multi.getParameter("comentario");
		if(comentario==null)comentario="";
		//System.out.println("Antes de archivo");
		transferido=0;
		ArchivoVO archivo= new ArchivoVO();
		archivo.setCodigoPersonal(profesor);
		archivo.setFecha(fecha.toString());
		archivo.setId(id);
		archivo.setNombre(nombre);
		archivo.setAutorizacion(matriculas);
		archivo.setComentario(comentario);
		System.out.println("Before saving..");
		bArchivosProfesor.guardaArchivoBD(conn2,archivo,ruta,nombre);
		para=multi.getParameter("para");
		matricula=para;
		alumno=multi.getParameter("alumno");
		nomProfesor=multi.getParameter("nomProfesor");
		nomMateria=multi.getParameter("nomMateria");
		origen="Professor";
		System.out.println("Try end...");
		}catch(Exception e){
			error=1;
		}

	}	
	if(accion.equals("borrar")){
		if (vista==null) vista = "0";
		//System.out.println("Entre a borrar");
		String profesor=(String) session.getAttribute("codigoEmpleado");
        String archivosBorrar=request.getParameter("archivosBorrar");
        String archivoBorrar="";
		sCursoCargaId=request.getParameter("sCursoCargaId");
		id=request.getParameter("id");
		para=request.getParameter("al");
		alumno=request.getParameter("alumno");
		nomProfesor=request.getParameter("nomProfesor");
		nomMateria=request.getParameter("nomMateria");
		String idTemp="",matTemp="";
		int folioTemp=-1,empiezaMat=0,empiezaFolio=0,empiezaNombre=0;
		java.util.StringTokenizer token = new java.util.StringTokenizer(archivosBorrar, "~");
		String ruta=application.getRealPath(path);
		int tokens=token.countTokens(),i=0;
		//System.out.println("Tokens:"+tokens);
		for(i=0;i<tokens;i++){
			//System.out.println("Token i:"+i);
		   archivoBorrar = token.nextToken();
		   if(archivoBorrar.substring(0,1).equals("P"))archivoBorrar.substring(1,archivoBorrar.length());
		   empiezaMat=archivoBorrar.indexOf("M");
		   empiezaFolio=archivoBorrar.indexOf("F");
		   empiezaNombre=archivoBorrar.indexOf("N ");
		   idTemp=archivoBorrar.substring(0,empiezaMat);
		   matTemp=archivoBorrar.substring(empiezaMat+1,empiezaFolio);
		   folioTemp=Integer.parseInt(archivoBorrar.substring(empiezaFolio+1,empiezaNombre));
		   //System.out.println("Valores conn2:"+conn2+"|idTemp:"+idTemp+"|matTemp:"+matTemp+"|folioTemp:"+folioTemp+"Vista:"+vista);
		   if(vista.equals("MandarAlumno")){
			   //System.out.println("antes de bArchivosProfesor.eliminarArchivo");
			   bArchivosProfesor.eliminarArchivo(conn2,idTemp,matTemp,folioTemp);
		   }else{
			   //System.out.println("antes de bArchivos.eliminarArchivo");
			   bArchivos.eliminarArchivo(conn2,idTemp,matTemp,folioTemp);			   
		   }
		}
		transferido=i;
		origen="Professor";
	}
	ArchivoVO archivo= new ArchivoVO();
	archivo.setCodigoPersonal(matricula);
	archivo.setId(id);
	ArrayList<aca.archivos.ArchivoVO> archivos = null;
	if(vista.equals("xEstrategia")) archivos = bArchivos.obtenerArchivosxExtrategia(conn2,archivo);
	if(vista.equals("xMateria")) archivos = bArchivos.obtenerArchivosxMateria(conEnoc,conn2,id,matricula);
	if(vista.equals("xAlumno")) archivos = bArchivos.obtenerArchivosxExtrategiasEnAlumno(conEnoc,conn2,matricula,id);
	if(vista.equals("xAlumnoEstrategia")) archivos = bArchivos.obtenerArchivosxAlumnoExtrategia(conEnoc,conn2,id);
	if(vista.equals("MandarAlumno")) archivos = bArchivosProfesor.obtenerArchivosMandadosAlumno(conn2,id,matricula);
	if(vista.equals("paraAlumno")) archivos = bArchivosProfesor.obtenerArchivosDelAlumno(conEnoc,conn2,id,matricula);
	if(vista.equals("paraAlumnoxEstrategia")) archivos = bArchivosProfesor.obtenerArchivosDelAlumnoxEstrategia(conEnoc,conn2,id,matricula);
%>
<table cellpadding="10" style="width:100%">
	<tr>
	<td>

<%
						String pRegreso="";
						if(vista.equals("xMateria")||vista.equals("paraAlumno"))
							pRegreso="detallecal.jsp?materia="+nomMateria+"&profesor="+nomProfesor+"&matricula="+matricula+"&cursoCargaId="+id;
						if(vista.equals("xEstrategia")||vista.equals("paraAlumnoxEstrategia"))
							pRegreso="detallecal.jsp?materia="+nomMateria+"&profesor="+nomProfesor+"&matricula="+matricula+"&cursoCargaId="+id.substring(0,11);
						if(vista.equals("xAlumno")||vista.equals("MandarAlumno")||vista.equals("xAlumnoEstrategia"))
							pRegreso="../maestro/evaluar?CursoCargaId="+id.substring(0,11)+"&Maestro="+nomProfesor+"&Materia="+nomMateria+"&EvaluacionId=0";					
%>
						<a href='<%=pRegreso%>'><b> &nbsp;&nbsp; Return</b></a>
				<br>
  <table style="width:100%"  >
	<tr valign="top">
		<td class="encabezadoV" width="2" valign="top"></td>
		<th class="encabezadoV">
		<%		if(vista.equals("xAlumno"))out.print("File List in ");
				else if(vista.equals("xAlumnoEstrategia"))out.print("File List in ");
				else if(vista.equals("xEstrategia")||vista.equals("xMateria"))out.print("Send to ");
				else if(vista.equals("paraAlumnoxEstrategia")||vista.equals("paraAlumno")) out.print("File List in ");
				else if(vista.equals("xMateria")) out.print("Files sent in ");
				else out.print("Files sent to ");
%>
						<%=nomMateria%>
		</th>
		<td class="encabezadoV" width="2" valign="top"></td>
	</tr>
</table>
<table class="tablaMarco" cellpadding="10"   width="100%"><tr><td>
  <table style='margin:0 auto;'    width="100%" >
	<tr>
		<td>
			<form name="forma" enctype="multipart/form-data" action="transferir?accion=1&id=<%=id%>&
<%		    	if(vista.equals("xEstrategia")) out.print("vista=xEstrategia");
		    	if(vista.equals("MandarAlumno")) out.print("vista=MandarAlumno");
%>
		    	" METHOD="POST">
		    	<input type="hidden" name="checador"/>
		    	<input type="hidden" name="matriculas"/>
		<table     width="100%">
			<tr> 
     			<td  width="35"></td>
     			<td  width="20"></td>
     			<td></td>
     			<td  width="140"></td>
     			<td  width="60"></td>
		    </tr>
		    <tr> 
				<td  colspan='5'>
<%				if(vista.equals("xAlumno"))out.print("From: <b>"+alumno+"</b><br>To: <b>"+nomProfesor);
				else if(vista.equals("xAlumnoEstrategia"))out.print("To: <b>"+nomProfesor);
				else if(vista.equals("xEstrategia")||vista.equals("xMateria"))out.print("To: <b>"+nomProfesor);
				else if(vista.equals("paraAlumnoxEstrategia")||vista.equals("paraAlumno")) out.print("From: <b>"+nomProfesor);
				else out.print("From: <b>"+nomProfesor+"</b><br>To: <b>"+alumno);
%>
				<br>
				<%if(transferido==0)out.print("<div align='center'><font color='#c09500'>File sent.</font></div>");%>
				<%if(transferido>0)out.print("<div align='center'><font color='#c09500'>Deleted "+transferido+" files.</font></div>");%>
				<%if(error==1)out.print("<div align='center'><font color='red'>Maximum file size should be 5 Mb.</font></div>");%>
				</td>
			</tr>
<%			if(vista.equals("MandarAlumno")){
%>
			<tr> 
				<td colspan='5' align='center'>Save to: <br>
					<script>
						function irA(){document.location.href='transferir?matricula=<%=matricula%>&id='+document.forma.idDest.value+'&vista=MandarAlumno&origen=Profesor&sCursoCargaId=<%=sCursoCargaId%>&al=<%=para%>&alumno=<%=alumno%>&nomProfesor=<%=nomProfesor%>&nomMateria=<%=nomMateria%>';}
					</script>
					<select name='idDest'>
						<option value='<%=sCursoCargaId%>'><%=nomMateria%></option>
<%				aca.catalogo.EstrategiaUtil estrategiaU = new aca.catalogo.EstrategiaUtil();
				aca.carga.CargaGrupoEvaluacionUtil evaluacionU	= new aca.carga.CargaGrupoEvaluacionUtil();
				ArrayList<aca.carga.CargaGrupoEvaluacion> lisEvaluacion = evaluacionU.getLista(conEnoc, sCursoCargaId, "ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
				for (int i=0; i< lisEvaluacion.size(); i++){
						aca.carga.CargaGrupoEvaluacion cge = (aca.carga.CargaGrupoEvaluacion) lisEvaluacion.get(i);
%>  
						<option value="<%=cge.getCursoCargaId()+"-"+cge.getEvaluacionId()%>" <%if(id.equals(cge.getCursoCargaId()+"-"+cge.getEvaluacionId())) out.print("Selected");%>>
								&nbsp;&nbsp;&nbsp;|--&nbsp;<%=estrategiaU.getNombre(conEnoc, cge.getEstrategiaId())%> - <%if (cge.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(cge.getNombreEvaluacion());} %>
						</option>
<%				}%>
					</select><br>
				<a href="javascript:irA();">See Files</a></td>
			</tr>
<%			}
			//System.out.println("valor de vista antes de imagen borar:"+vista);
			if(!vista.equals("paraAlumno")&&!vista.equals("paraAlumnoxEstrategia")){%>
			<tr>
				<td colspan='5'><img style="cursor:pointer;" onClick="borra('&vista=<%=vista%>&matricula=<%=matricula%>&id=<%=id%>&origen=Profesor&sCursoCargaId=<%=sCursoCargaId%>&al=<%=para%>&alumno=<%=alumno%>&nomProfesor=<%=nomProfesor%>&nomMateria=<%=nomMateria%>&ccid=<%=request.getParameter("ccid") %>&evid=<%=request.getParameter("evid") %>');" src="/academico/imagenes/borrararch.gif" /></td>
			</tr>
			<%}%>
		    <tr height="20"> 
				<th  align='center'>
					<%if(!vista.equals("paraAlumno")&&!vista.equals("paraAlumnoxEstrategia")){%>
					<input type="checkbox" name="selTodosChk" onclick='checaTodosBorrar();' value="ok"/>
					<%}%>&nbsp;
				</th>
				<th  align='center'><spring:message code="aca.Numero"/></th>
				<th ><spring:message code="aca.Nombre"/></th>
				<th  align='center' ><spring:message code="aca.Fecha"/></th>
				<th  align='center'>Size</th>
				<th align="center" width="15%">Grade</th>
			</tr>
<%			
			String clave		= "";
			String com 	= "";
			char enter=10;
			char enter2=13;
			String click="";
			if (archivos!=null){
				String tempNombreAlumno="";
				String tempNombreAlumnoAnt="";
				for(int i=0;i<archivos.size();i++){
					archivo = (ArchivoVO)archivos.get(i);
					if(vista.equals("xAlumnoEstrategia")||vista.equals("xAlumno")||vista.equals("xMateria")||vista.equals("paraAlumno")||vista.equals("paraAlumnoxEstrategia")){
						tempNombreAlumno=archivo.getNombreAlumno();
						if(!tempNombreAlumno.equals(tempNombreAlumnoAnt)){
%>							<tr><td colspan='5'><b><%if(archivo.getNombreAlumno().equals(" - "))out.print(nomMateria); else out.print(archivo.getNombreAlumno());%></b></td></tr>
							<tr bgcolor='#667777'><td colspan=6  style="background-image:url('../../imagenes/graypoint.gif'); background-repeat:repeat;" height=1></td></tr>
<%						}
					}
					if(vista.equals("MandarAlumno")||vista.equals("paraAlumno")||vista.equals("paraAlumnoxEstrategia"))clave="P";
					if(vista.equals("xEstrategia"))clave="X";
					if(archivo.getComentario()!=null){
						com = archivo.getComentario().replaceAll(new java.lang.Character(enter).toString(),"<br>").replaceAll(new java.lang.Character(enter2).toString(),"");
						com = com.replaceAll("[^\\w.,·¡È…ÌÕÛ”˙⁄Ò—@\\n:;!()&/$%=|-]"," ");
						 
						click="onclick=\"window.open('bajar?id="+archivo.getId()+"&folio="+archivo.getFolio()+"&matricula="+archivo.getCodigoPersonal()+"&nombre="+archivo.getNombre()+"&origen="+origen+"&clave="+clave+"&comentario="+com+"','','scrollbars=yes,menubar=yes,height=300,width=600,resizable=yes,toolbar=no,location=no,status=no');\"";
					}else{
						click="onclick=\"window.open('bajar?id="+archivo.getId()+"&folio="+archivo.getFolio()+"&matricula="+archivo.getCodigoPersonal()+"&nombre="+archivo.getNombre()+"&origen="+origen+"&clave="+clave+"','','scrollbars=yes,menubar=yes,height=300,width=600,resizable=yes,toolbar=no,location=no,status=no');\"";
					}
%>
			<tr height="20" onMouseOver="this.style.backgroundColor='#e4f7fa'" style='cursor:pointer;'>
				<td  class='CellStyle2' align='center'>
					<%if(!vista.equals("paraAlumno")&&!vista.equals("paraAlumnoxEstrategia")){
						String idTemp=archivo.getId();
						if(vista.equals("MandarAlumno"))idTemp="P"+idTemp;
						String nombrereal=idTemp+"M"+archivo.getCodigoPersonal()+"F"+archivo.getFolio()+"N "+archivo.getNombre();
					%>
						<input type="checkbox" name="listaEliminar" value="<%=nombrereal%>"/>
					<%}%>
				</td>
				<td <%=click%> class='CellStyle1'><%=i+1%></td>
				<td <%=click%> class='CellStyle2'>
				
	<%				if(archivo.getAutorizacion()!=null) if(archivo.getAutorizacion().equals("N")&&!vista.equals("xEstrategia")&&!vista.equals("xMateria"))out.print("<img src='img/estrella.gif' /> ");
					if(archivo.getNombre()!=null)if(archivo.getNombre().length()>80)out.print(archivo.getNombre().substring(0,80)+"...");else out.print(archivo.getNombre());%>
				<font color="gray"><i><%
					if(archivo.getComentario()!=null){
						com=archivo.getComentario();						
						com=com.replaceAll(new java.lang.Character(enter).toString(),"<br>").replaceAll(new java.lang.Character(enter2).toString(),"");
						com = com.replaceAll("[^\\w.,·¡È…ÌÕÛ”˙⁄Ò—@\\n:;!()&/$%=|-]"," ");
						out.print("<br>"+com);
					}
				%><i></font>
				</td>
				<td <%=click%> class='CellStyle2' align='center'><%=archivo.getFecha()%></td>
				<td <%=click%> class='CellStyle2' align='right'><%if((archivo.getTamano()/1024)==0) out.print("<1 KB");else if((archivo.getTamano()/1024)>1024) out.print(df.format(((double)archivo.getTamano()/1024/1024))+" MB"); else out.print(archivo.getTamano()/1024+" KB");%></td>
<%
	krdxAlumnoEval.mapeaRegId(conEnoc, archivo.getCodigoPersonal(), request.getParameter("ccid"), request.getParameter("evid"));
					if(!tempNombreAlumno.equals(tempNombreAlumnoAnt)){
%>
				<td class="ayuda PMA 002">&nbsp;
					<input type="text" class="text" size="5" value="<%=krdxAlumnoEval.getNota()==null?0:krdxAlumnoEval.getNota() %>" />
					<a href="#" onclick="grabarNota(this, '<%=krdxAlumnoEval.getNota() %>', '<%=archivo.getCodigoPersonal() %>', '<%=request.getParameter("ccid") %>', '<%=request.getParameter("evid") %>');">[ Save ]</a>
				</td>
<%
					}else{
%>
				<td>&nbsp;</td>
<%
					}
%>
		    </tr>
			<tr bgcolor='#667777'> 
				<td colspan='5'  style="background-image:url('../../imagenes/graypoint.gif'); background-repeat:repeat;"  height=1></td>
			</tr>
<%					if(vista.equals("xAlumnoEstrategia")||vista.equals("xAlumno")||vista.equals("paraAlumno")||vista.equals("xMateria")||vista.equals("paraAlumnoxEstrategia"))tempNombreAlumnoAnt=tempNombreAlumno;
				}
			}
			if(vista.equals("xEstrategia")||vista.equals("MandarAlumno")){%>
		    <tr>
		    	<td colspan='5'><br>Max File Size: <b>5 Megabytes.</b><br>
		   		  <input style="border-width:1px;" name="archivo" id="archivo" type="file" size="50"/>
		   		</td>
		   	</tr>
		    <tr>
		    	<td colspan='5'>
		   		  <div align='left'>&nbsp;&nbsp;Comment:<br></div>
		   		  <textarea cols='68' name='comentario' onkeyup='checaCuantasLetras(500);'></textarea>
		   		</td>
		   	</tr>
		   	<tr>
		   		<td colspan='5'>
				  <%if(vista.equals("MandarAlumno")){%><input onclick='checa();' type="button" name="boton" value="Send"/><%}%>
				  <%if(vista.equals("xEstrategia")){%><input onclick='verificaArchivo();' type="button" name="boton" value="Send"/><%}%>
				  <div id="temp"></div>
				</td>
			</tr>
<%			}if(vista.equals("MandarAlumno")||vista.equals("xEstrategia")){%>
				<input type="hidden" name="para" value="<%=para%>"/>
				<input type="hidden" name="sCursoCargaId" value="<%=sCursoCargaId%>"/>
				<input type="hidden" name="alumno" value="<%=alumno%>"/>
				<input type="hidden" name="nomProfesor" value="<%=nomProfesor%>"/>
				<input type="hidden" name="nomMateria" value="<%=nomMateria%>"/>
				<input type="hidden" name="idDestino" value="<%=id%>"/>
<%			}if(vista.equals("MandarAlumno")){%>
				<tr>
					<td colspan='5'>
						<table>
							<tr>
								<td colspan='3'><br><br><b>To:</b><br>Select: <a style='cursor:pointer;' onclick='checaTodos();'>All</a>, <a style='cursor:pointer;' onclick='checaNinguno();'>None</a></td>
								<script>document.forma.checador.value="MandarAlumno";</script>
							</tr>
<%
				aca.kardex.ActualUtil acu	= new aca.kardex.ActualUtil();
				ArrayList<aca.kardex.KrdxCursoAct> lisAlumnos = acu.getListCurso(conEnoc, sCursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
				aca.alumno.AlumUtil alumnoU	= new aca.alumno.AlumUtil();
				for (int i=0; i<lisAlumnos.size();i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);	
%>						    <tr>
			    				<td align='center'><input type='checkbox' name="mats" value="<%=ac.getCodigoPersonal()%>" <%if(para.equals(ac.getCodigoPersonal()))out.print("Checked");%>/></td>
						    	<td align='center'><%=ac.getCodigoPersonal()%></td>
						    	<td><%=alumnoU.getNombre(conEnoc, ac.getCodigoPersonal(), "APELLIDO")%></td>
							</tr>
<%				}%>
						   	<tr>
		   						<td colspan='3' align='center'>
								  <input onclick='checa();' type="button" name="boton1" value="Send"/>
								  
								</td>
							</tr>
						</table>
					</td>
				</tr>
			<%}%>
		</table>
		</form>
		</td>
		</tr>
    </table></td></tr></table>
</td></tr></table>
<% // cerrar la conexion..
	if (stmt2!=null) stmt2.close();
	if (conn2!=null) conn2.close();

%><%@ include file= "../../cierra_enoc.jsp" %>

