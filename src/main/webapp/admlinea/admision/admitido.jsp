<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>

<%@page import="aca.admision.spring.AdmCarta"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmProceso"%>
<%@page import="aca.admision.spring.AdmDocumento"%>
<%@page import="aca.admision.spring.AdmDocAlum"%>
<%@page import="aca.admision.spring.AdmPasos"%>
<%@page import="aca.admision.spring.AdmRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<style>
		table.tabla td{
			font:12pt times new roman, times, serif normal;
		}
	<%
		if(colorTablas.equals("default") || colorTablas.equals("")){		
			colorTablas = "#683EAD";
		}
		String color1 = colorAlum.modificarColor(colorTablas, 64);
		String color2 = colorAlum.modificarColor(colorTablas, -40);
		String over = colorAlum.modificarColor(color1, 15);
	%>
		.next{
			cursor: hand;cursor: pointer;
			background:<%=colorTablas%>;
			height:32px;
			width: 165px;
			
			border: 4px solid <%=color2%>;
			border-radius:1.4em;
			
			background: -webkit-gradient(linear, left top, left bottom, from(<%=color1%>), to(<%=colorTablas%>));
			background: -webkit-linear-gradient(<%=color1%>, <%=colorTablas%> 60%);
			background: -moz-linear-gradient(<%=color1%>, <%=colorTablas%> 60%);
			background: -o-linear-gradient(<%=color1%>, <%=colorTablas%> 60%);
		}
		.next:hover{
			background:<%=color1%>;
			background: -webkit-gradient(linear, left top, left bottom, from(<%=over%>), to(<%=color1%>));
			background: -webkit-linear-gradient(<%=over%>, <%=color1%> 60%);
			background: -moz-linear-gradient(<%=over%>, <%=color1%> 60%);
			background: -o-linear-gradient(<%=over%>, <%=color1%> 60%);
		}
		.image{
			background: white;
			width: 27px;
			height: 26px;
			border-radius:20em;
		}
		.flecha{
			position:relative;
			left: 7px;
			top: 4px;
			border-left: 16px solid <%=colorTablas%>; 
			border-right: 9px solid transparent; 
			border-bottom: 9px solid transparent; 
			border-top: 9px solid transparent; 
		}
	</style>
	<script>
		function formato(folio){
			var fecha 		= document.getElementById("fechaIngreso");
			alert("Date Validated...");												
			if(fecha.value.replace(/^\s*([\S\s]*?)\s*$/, '$1')=="" || fecha.value.replace(/^\s*([\S\s]*?)\s*$/, '$1')=="-"){
				alert("Introduzca una fecha v�lida");
				fecha.focus();
			}else{
				alert("Generate Letter...");
				document.location.href="admitido?Accion=1&Folio="+folio+"&Fecha="+fecha.value;
			}
		}

	</script>
</head>
<%	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	boolean bajarPdf 			= (boolean)request.getAttribute("bajarPdf");
	boolean espacios			= false;
	Fecha fecha 				= new Fecha();
	
	String facultadNombre		= (String) request.getAttribute("facultadNombre");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");	
	String numCondiciones		= (String) request.getAttribute("numCondiciones");
	AdmSolicitud admSolicitud	= (AdmSolicitud) request.getAttribute("admSolicitud");
	AdmAcademico admAcademico	= (AdmAcademico) request.getAttribute("admAcademico");
	AdmProceso admProceso		= (AdmProceso) request.getAttribute("admProceso");
	boolean presencial 			= admAcademico.getModalidad().equals("1");
	boolean esAsesor			= (boolean) request.getAttribute("esAsesor");
	boolean esAdmin				= (boolean) request.getAttribute("esAdmin");
	boolean voboPosgrado		= (boolean) request.getAttribute("voboPosgrado");
	boolean vistoBueno			= (boolean) request.getAttribute("vistoBueno");
	boolean nivelPosgrado		= (boolean) request.getAttribute("nivelPosgrado");
	AdmPasos admPasos			= (AdmPasos) request.getAttribute("admPasos");
	
	String modalidad = admAcademico.getModalidad();	 
	
	List<AdmDocAlum> lisDocumentos				= (List<AdmDocAlum>) request.getAttribute("lisDocumentos");	
	List<String> lisFechas 						= (List<String>) request.getAttribute("lisFechas");
	HashMap<String,AdmDocumento> mapaDocumentos	= (HashMap<String,AdmDocumento>)request.getAttribute("mapaDocumentos");
	HashMap<String,AdmRequisito> mapaRequisitosPorCarrera	= (HashMap<String,AdmRequisito>)request.getAttribute("mapaRequisitosPorCarrera");
%>
<body onLoad="document.getElementById('fechaIngreso').focus();">
<div class="container-fluid">	
	<h3>Informaci�n de la carta de Aceptaci�n<small>( Carrera solicitada: <%=carreraNombre%> )</small></h3>	
	<div class="alert">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<a class="btn btn-primary" href="condiciones?Folio=<%=folio%>"><span class='badge badge-inverse'><%=numCondiciones%></span> Condiciones</a>&nbsp;&nbsp;		
<%	if (bajarPdf){ out.print("&nbsp;<a class='btn btn-success' href='admitidoPDF?Folio="+folio+"'>PDF</a>");}%>	
<%	if (admSolicitud.getEstado().equals("5") && !admSolicitud.getFechaIngreso().equals("-") && admSolicitud.getCarta().equals("N")){ %>
		&nbsp;&nbsp;
		<a class="btn btn-success" href="enviarCarta?Folio=<%=folio%>">Enviar correo</a>
<%	} %>		
	</div>	
	<datalist id="fechas">
<%		
	for(int j=0; j<lisFechas.size(); j++){%>
		<option value="<%=lisFechas.get(j) %>"></option>
<%	}%>
	</datalist>		
	<br>
	<table class="tabla" style=width:816px;height:1056px;align:center>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td width="50px">&nbsp;</td>
			<td>
				<b><%=fecha.getDia(admProceso.getFechaAdmision())+" de "+ fecha.getMesNombre(admProceso.getFechaAdmision())+" de "+fecha.getYear(admProceso.getFechaAdmision())%></b>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<b>
					<%=(admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null ? "" : admSolicitud.getApellidoMaterno())+" "+(admSolicitud.getApellidoPaterno()==null ? "" : admSolicitud.getApellidoPaterno())).toUpperCase() %>
					<br>
					Presente
				</b>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				Hemos recibido su solicitu para ingresar a la <b><%=presencial ? facultadNombre : "Unidad de Educaci�n Virtual" %>,</b><br>
				dependiente de esta universidad, y tras analizarla, hacemos de su conocimiento que, usted a sido <%=admSolicitud.getGenero().equals("M") ? "aceptado" : "aceptada" %>			
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				Para matricularse en el programa que ha sido <%=admSolicitud.getGenero().equals("M") ? "aceptado" : "aceptada" %>, 
				utilice el <b>n�mero de registro <%=admSolicitud.getMatricula() %></b><br>
				que es su identificaci�n institucional como estudiante.  Recuerde que, solamente despu�s de<br>
				concluir  la matr�cula ser� formalmente un estudiante de la Universidad de Montemorelos.
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				Usted puede matricularse <%=presencial ? "personalmente en el campus universitario �nicamente durante" : "en l�nea �nicamente durante" %> 
				las<br> siguientes fechas:
				<input name="fechaIngreso" id="fechaIngreso" type="text" class="input-xxlarge" maxlength="150" value="<%=admSolicitud.getFechaIngreso()==null ? "" : admSolicitud.getFechaIngreso() %>" list="fechas">
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<table>
					Es indispensable <%=presencial ? "que para iniciar su proceso de matr�cula traiga y entregue en la oficina de<br>Certificaci�n y Archivo los siguientes documentos originales:" : "que para obtener su aceptaci�n definitiva e iniciar su proceso de matr�cula<br>hayamos recibido en esta oficina los siguientes documentos originales:" %>
				<% 	for(AdmDocAlum docAlum : lisDocumentos){
						AdmDocumento documento = new AdmDocumento();
						if (mapaDocumentos.containsKey(docAlum.getDocumentoId())){
							documento = mapaDocumentos.get(docAlum.getDocumentoId());
						}	
						
						boolean existeModalidad = false; 
						String modalidadDocumento = "";
						if(mapaRequisitosPorCarrera.containsKey(documento.getDocumentoId())){
							modalidadDocumento = mapaRequisitosPorCarrera.get(documento.getDocumentoId()).getModalidades();
						}
						
						String [] modalidades = modalidadDocumento.split("-");
						for(String mod : modalidades){
							if(mod.equals(modalidad)){
								existeModalidad = true;
								break;
							}
						}
						
						if(existeModalidad){
				%>
						<tr>
							<td><blockquote><b><%="* "+ documento.getDocumentoNombre()+" "+(docAlum.getComentario()==null||docAlum.getComentario().trim().equals("-") ? "" : "- "+docAlum.getComentario()) %></b></blockquote></td>
						</tr>
				<%		}
					} %>
				</table>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				Estar� gustosa de atender cualquier consulta o informaci�n adicional que requiera.
			</td>
		</tr>
		<tr>
			<td></td>
		<%	if(presencial){ %>
				<td>
					Nos sentimos felices de que en breve estar� con nosotros  para vivir<br>
					su experiencia UM junto  a esta gran familia universitaria.
				</td>
		<%	}
			else{%>
				<td>
					Nos sentimos felices de que en breve formar� parte de la gran familia universitaria para<br>
					vivir la experiencia UM con nosotros.
				</td>
		<%	} %>
		</tr>
		<tr>
			<td></td>
			<td>
				Deseamos que nuestro Dios gu�e  sus planes educacionales.
			</td>
		</tr>
<%	if (esAsesor || esAdmin){%>	
		<tr>
			<td align="center" colspan="2">				
				<input type="button" class="btn btn-primary btn-large" value="Grabar" onclick="javascript:formato('<%=folio %>');">				
			</td>
		</tr>
<%	} %>		
	</table>
	<br>
</div>	
</body>
</html>