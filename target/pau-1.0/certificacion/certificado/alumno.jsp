<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.cert.spring.CertAlum"%>
<%@page import="aca.cert.spring.CertPlan"%>
<%@page import="aca.cert.spring.CertCiclo"%>
<%@page import="aca.cert.spring.CertCurso"%>
<%@page import="aca.cert.spring.CertRelacion"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.kardex.KrdxCursoAct"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.cert.spring.CertAlumNota"%>
<%@page import="aca.carga.spring.Carga"%>

<jsp:useBean id="fecha" scope="page" class="aca.util.Fecha"/>
<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

	<script type="text/javascript">
		function guardar(){
			var lineasSem = "";
			
			jQuery(".lineas:checked").each(function(index){
				var val = jQuery(this).val();
				
				lineasSem += val +"-";
			});
			
			lineasSem = lineasSem.substr(0,lineasSem.length-1);
			var inputs = document.getElementsByTagName("input");
			var esCorrecto = true;
			for(var i = 0; i < inputs.length; i++){
				console.log("cali: "+inputs[i].value);
				if((inputs[i].value == "Err") || (inputs[i].value == "AC"))
					esCorrecto = false;
			}
			if(esCorrecto){
				document.forma.action = "alumno?Accion=1&lineas="+lineasSem;				
				
			}else{
				alert("El valor de alguna nota no es correcto.\nColoque un valor correcto para poder continuar");
			}
			
			document.forma.submit();
		}
		
		function imprimir(plan){
			var lineasSem = "";
			jQuery(".lineas:checked").each(function(index){
				var val = jQuery(this).val();
				lineasSem += val +"-";
			});
			lineasSem 	= lineasSem.substr(0,lineasSem.length-1);
			var mat 	= jQuery("#pdfmaterias").val();
			var prom 	= jQuery("#pdfpromedio").val();
			var foto 	= jQuery("#foto").val();
			var leyenda	= jQuery("#Leyenda").val();
			var curp	= jQuery("#Curp").val();
			var ciclo	= jQuery("#Ciclo").val();
			var espacio = document.getElementById("Espacios").value;
			
			location.href="certificadoPDF?plan="+plan+"&pdfmaterias="+mat+"&pdfpromedio="+prom+"&foto="+foto+"&lineas="+lineasSem+"&Leyenda="+leyenda+"&Curp="+curp+"&Ciclo="+ciclo+"&Espacios="+espacio;
		}
		
		function cerrar(){
			var esCorrecto = true;
			if (confirm("¿ Estas seguro de cerrar las notas grabadas?")==true){
				document.forma.action = "alumno?Accion=3";
			}else				
				esCorrecto = false;
							
			return esCorrecto;
		}
		
		function abrir(){		
			var esCorrecto = true;			
			if(confirm("¿ Estás seguro de abrir las notas cerradas?")==true){
				document.forma.action = "alumno?Accion=4";
			}else
				esCorrecto = false;
								
			document.forma.submit();
		}
		
		function borrar(){			
			var esCorrecto = true;			
			if (confirm("¿ Estas seguro de eliminar las notas registradas en el certificado ?")==true){				
				document.forma.action = "alumno?Accion=5";
			}else
				esCorrecto = false;
			return esCorrecto;
		}		
		function Borrar( folio ){
			if(confirm("¿ Estas seguro de eliminar esta nota ?")==true){
	  			document.forma.action = "alumno?Accion=6&folio="+folio;
	  			document.forma.submit();
	  		}
	    }
		
		function Todos(clase){
			jQuery("."+clase).prop('checked',true);
		}
		
		function Ninguno(clase){			
			jQuery("."+clase).prop('checked',false);
		}
	</script>
</head>
<% 
	java.text.DecimalFormat getformato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	boolean esAlumno 			= (boolean)request.getAttribute("esAlumno");
	boolean existeCertPlan 		= (boolean)request.getAttribute("existeCertPlan");
	boolean updateCerAlum 		= (boolean)request.getAttribute("updateCerAlum");
	boolean insertaCerAlum 		= (boolean)request.getAttribute("insertaCerAlum");
	
	String strFecha 			= (String)request.getAttribute("strFecha");
	String strCheck 			= (String)request.getAttribute("strCheck");
	String planId 				= (String)request.getAttribute("planId");
	String curso 				= (String)request.getAttribute("curso");
	String codigoAlumno 		= (String)request.getAttribute("codigoAlumno");
	String fechaNota 			= (String)request.getAttribute("fechaNota");
	String optativa 			= (String)request.getAttribute("optativa");
	String cursoLlevado 		= (String)request.getAttribute("cursoLlevado");
	int matGrabadas 			= (int)request.getAttribute("matGrabadas");
	int matSugeridas 			= (int)request.getAttribute("matSugeridas");
	int numAlumNotas 			= (int)request.getAttribute("numAlumNotas");
	float totCreditos 			= (float)request.getAttribute("totCreditos");
	float credProm 				= (float)request.getAttribute("credProm");
	float notaCreditos 			= (float)request.getAttribute("notaCreditos");
	double pglobal 				= (double)request.getAttribute("pglobal");
	double pglobalF 			= (double)request.getAttribute("pglobalF");
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");
	CertPlan certPlan 			= (CertPlan)request.getAttribute("certPlan");
	CertAlum certAlum 			= (CertAlum)request.getAttribute("certAlum");
	
	List<String> lisPlan 		= (List<String>)request.getAttribute("lisPlan");
	List<CertCiclo> lisCiclos 	= (List<CertCiclo>)request.getAttribute("lisCiclos");
	List<CertCurso> lisCursos 	= (List<CertCurso>)request.getAttribute("lisCursos");
	
	HashMap<String, CertAlumNota> mapCertAlumNotas 	= (HashMap<String, CertAlumNota>)request.getAttribute("mapCertAlumNotas");
	HashMap<String,CertRelacion> mapCertRelacion  	= (HashMap<String, CertRelacion>)request.getAttribute("mapCertRelacion");
	HashMap<String,AlumnoCurso> mapTieneCurso		= (HashMap<String, AlumnoCurso>)request.getAttribute("mapTieneCurso");
	HashMap<String, Carga> mapaCargas				= (HashMap<String, Carga>)request.getAttribute("mapaCargas");
	HashMap<String, AlumPlan> mapAlumIngreso		= (HashMap<String, AlumPlan>)request.getAttribute("mapAlumIngreso");
	HashMap<String, String> mapaNombrePlan			= (HashMap<String, String>)request.getAttribute("mapaNombrePlan");
	
%>
<div class="container-fluid" >
<%	if(esAlumno){%>		
	<h2>Certificado del Alumno<small class="text-muted fs-6">( <%=codigoAlumno %> - <%=alumPersonal.getNombreLegal()%> )</small></h2>	
	<form id="forma" name="forma" action="alumno" method="post">	
		<input type="hidden" name="todos">
		<input type="hidden" name="ninguno">
		<input type="hidden" name="semestre">
		<div class="alert alert-info d-flex align-items-center">			
			Plan:&nbsp;
			<select id="plan" name="plan" class="form-select" onchange="document.forma.submit();" style="width:450px">
<%		String [] arr = certAlum.getLinea().split("-");
		List<String> SemLineas = new ArrayList<String>();
		for(String str: arr)SemLineas.add(str);
	
		String todos 	= request.getParameter("todos")==null?"":request.getParameter("todos");
		String ninguno  = request.getParameter("ninguno")==null?"":request.getParameter("ninguno");
		String semestre = request.getParameter("semestre")==null?"":request.getParameter("semestre");

		for(int i=0; i < lisPlan.size(); i++){
			String nombrePlan = "";
			if(mapaNombrePlan.containsKey(lisPlan.get(i))){
				nombrePlan = mapaNombrePlan.get(lisPlan.get(i));
			}%>						
				<option value='<%=(String)lisPlan.get(i) %>' <%if(planId.equals((String)lisPlan.get(i)))out.print("Selected");%> ><%=lisPlan.get(i)%>-<%=nombrePlan %></option>
<%		} %>
			</select>
			&nbsp;&nbsp;
			Fecha:&nbsp;
			<input name="Fecha" type="text" class="form-control" data-date-format="dd/mm/yyyy" id="Fecha" size="11" maxlength="10" onfocus="focusFecha(this);" value="<%= strFecha %>" style="width:110px">
			(DD/MM/AAAA)	
			&nbsp; &nbsp;
			Completo: &nbsp;
			<select id="Completo" name="Completo" class="form-select" onChange="this.value!='A'?btnGuardar.style.visibility='':btnGuardar.style.visibility='hidden';" style="width:150px;">
				<option value="A" <% if (certAlum.getAvance().equals("A")) out.print("selected");%>><spring:message code='aca.Pendiente'/></option>
				<option value="C" <% if (certAlum.getAvance().equals("C")) out.print("selected");%>>Integramente</option>
				<option value="P" <% if (certAlum.getAvance().equals("P")) out.print("selected");%>>Parcialmente</option>
			</select>								
		</div>
<%		if(existeCertPlan){ %>			
		<div class="alert alert-info d-flex align-items-center">
			&nbsp;&nbsp;
			<select class="form-select" name="foto" id="foto" style="width:120px;">
				<option value="Con">Con foto</option>
				<option value="Sin">Sin foto</option>
			</select>
			&nbsp;&nbsp;
			<select name="Leyenda" id="Leyenda" class="form-select" style="width:150px;">
				<option value="S">Con Leyenda</option>
				<option value="N">Sin Leyenda</option>
			</select>
			&nbsp;&nbsp;				
			<select name="Curp" id="Curp" class="form-select" style="width:130px;">
				<option value="S">Con CURP</option>
				<option value="N">Sin CURP</option>
			</select>
			&nbsp;&nbsp;				
			<select name="Ciclo" id="Ciclo" class="form-select" style="width:120px;">
				<option value="J">Junto</option>
				<option value="S">Separado</option>
			</select>
			&nbsp;&nbsp;
			Espacios: <input name="Espacios" id="Espacios" size="2" maxlength="3" value="0" class="form-control" style="width:50px">
		</div>
		<table class="table table-sm table-bordered" style="width:80%">			
			<tr>
				<td><b>Equivalencia(s):</b></td>
			</tr>
			<tr> 
	     	  <td>         
	       		 <textarea id="Equivalencia" name="Equivalencia" cols="120" rows="2"><%=certAlum.getEquivalencia() %></textarea>
	     	  </td>          
	   		</tr>   		
			<tr>
				<td>				
					<table style="width:100%" class="table table-sm table-bordered table-nohover">
						<tr>
							<th><spring:message code="aca.Numero"/></th>					  
<%						if(certPlan.getCurso().equals("S")){ %>
							<th>CURSO ESCOLAR</th>
<%						}
						if(certPlan.getClave().equals("S")){ %>
							<th>CLAVE</th>
<%						} %>
							<th>MATERIAS</th>
<%						if(certPlan.getFst().equals("S")){ %>
							<th><%=certPlan.getTitulo1() %></th>
<%						}
						if(certPlan.getFsp().equals("S")){ %>
							<th><%=certPlan.getTitulo2() %></th>
<%						} %>
							<th><%=certPlan.getTitulo3() %></th>
							<th>CALIF.</th>
							<th>CON LETRA</th>
							<th><img src="../../imagenes/filesave.gif" width="20"></th>
							<th>PROMEDIA</th>
						</tr>
						<tr>
							<td colspan="9">&nbsp;</td>					
<%					float creditos 		= 0;
					for(int i = 0; i < lisCiclos.size(); i++){
						CertCiclo certCiclo = lisCiclos.get(i); %>
							<tr>
<%						if(certPlan.getCurso().equals("S")){ %>
								<td>&nbsp;</td>
<%						}
						if(certPlan.getClave().equals("S")){ %>
								<td>&nbsp;</td>
<%						} %>
								<td colspan="<%=!certPlan.getFst().equals("S")?"5":"7"%>">
									<input value="<%=certCiclo.getCicloId() %>" class="lineas" type="checkbox" <%if(SemLineas.contains(certCiclo.getCicloId()))out.print("checked='checked'");%> /> Linea &nbsp;
									<b><%=certCiclo.getTitulo() %></b>
								</td>
								<td style="text-align:right;" colspan="2">
								  	<a onclick="Todos('<%=certCiclo.getCicloId()%>');" class="btn btn-primary btn-sm"><spring:message code='aca.Todos'/></a>
								  	&nbsp;/&nbsp;
								  	<a onclick="Ninguno('<%=certCiclo.getCicloId()%>');" class="btn btn-sm btn-primary"><spring:message code='aca.Ninguno'/></a>
								</td>
<%						if(certPlan.getFst().equals("S")){ %>
								<td>&nbsp;</td>
<%						}
						if(certPlan.getFsp().equals("S")){ %>
								<td>&nbsp;</td>
<%						} %>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
<%						int notass = 0;
						for(int j = 0; j < lisCursos.size(); j++){
							CertCurso certCurso = lisCursos.get(j);
							CertAlumNota certAlumNota = new CertAlumNota();
							AlumnoCurso alumnoCurso = new AlumnoCurso();
							if(certCurso.getCicloId().equals(certCiclo.getCicloId())){
								boolean existeNota = false;
								fechaNota = " "; optativa = "*"; cursoLlevado = "";
								// Si tiene el dato grabado
								if(mapCertAlumNotas.containsKey(codigoAlumno+certCurso.getCursoId())){
									certAlumNota = mapCertAlumNotas.get(codigoAlumno+certCurso.getCursoId());
									existeNota = true;
									//System.out.println("Si existe..."+j+":"+certCurso.getCursoId());
									curso = certAlumNota.getCurso();
									matGrabadas++;
								// Si no esta grabado el curso	
								}else{							
									CertRelacion certRelacion = new CertRelacion();								
									certRelacion.setCursoCert(certCurso.getCursoId());
									// Si existe la relacion
									if(mapCertRelacion.containsKey(certCurso.getCursoId())){
										certRelacion = mapCertRelacion.get(certCurso.getCursoId());
										alumnoCurso = new AlumnoCurso();
										
										// Buscar datos del curso acreditado..			
										if(mapTieneCurso.containsKey(codigoAlumno+certRelacion.getCursoId()+"1")){
											alumnoCurso = mapTieneCurso.get(codigoAlumno+certRelacion.getCursoId()+"1");										
											matSugeridas++;									
																			
											creditos = Float.parseFloat(certCurso.getCreditos());										                            
											
											// Obtiene el nombre de la Optativa si es optativa 
											optativa = alumnoCurso.getOptativa();					
											cursoLlevado = alumnoCurso.getNombreCurso();
											
											// Si no es curso importado busca las fechas de la carga
											if ( !alumnoCurso.getCursoCargaId().equals("000000-0000") ) {
												Carga carga = new Carga();
												if(mapaCargas.containsKey(alumnoCurso.getCursoCargaId().substring(0,6))){
													carga = mapaCargas.get(alumnoCurso.getCursoCargaId().substring(0,6));
												}
												curso = Integer.parseInt(fecha.getMes(carga.getFFinal())) >= 11 ? (fecha.getYear(carga.getFFinal())+"-"+(Integer.parseInt(fecha.getYear(carga.getFFinal()))+1)) : ((Integer.parseInt(fecha.getYear(carga.getFFinal()))-1)+"-"+fecha.getYear(carga.getFFinal()));
												fechaNota = carga.getFFinal();										
											// Si es curso importado obtiene el curso en base a la fecha de evaluacion...
											}else{										
												if (alumnoCurso.getTitulo().equals("S")){
													curso = alumnoCurso.getFTitulo();
												}else if ( alumnoCurso.getFExtra()!=null && !alumnoCurso.getNotaExtra().equals("0") ){
													curso = alumnoCurso.getFExtra();
												}else{
													curso = alumnoCurso.getFEvaluacion();
												}
												fechaNota = curso;
												curso = Integer.parseInt(fecha.getMes(fechaNota)) >= 11 ? (fecha.getYear(fechaNota)+"-"+(Integer.parseInt(fecha.getYear(fechaNota))+1)) : ((Integer.parseInt(fecha.getYear(fechaNota))-1)+"-"+fecha.getYear(fechaNota));
											}
										}else{
											curso = "";										
										}
									}else{
										optativa = "-";
									}
								}%>
							<tr class="tr2" <%=existeNota?"bgcolor='#EEEEEE'":""%> >
								<td>
<%								if(mapCertAlumNotas.containsKey(codigoAlumno+certCurso.getCursoId())){
									out.print(matGrabadas);
								} 
%>
								</td>
<%							if(certPlan.getCurso().equals("S") ){
								if(mapCertAlumNotas.containsKey(codigoAlumno+certCurso.getCursoId()) && existeNota && certAlumNota.getEstado().equals("C")){ %>
								<td><%=certAlumNota.getCurso() %> - <%=fechaNota%></td>
<%								}else{ %>
								<td>
							  		<input rel="<%=certCiclo.getTitulo()%>-curso-<%=certCurso.getCursoId()%>" id="curso-<%=certCurso.getCursoId() %>" name="curso-<%=certCurso.getCursoId() %>" type="text" class="text" value="<%=curso%>" maxlength="10" size="9" /> - 
						  			<%=fechaNota%>				  			
						  		</td>
<%								}
							}
							if(certPlan.getClave().equals("S")){ %>
								<td><b><%=certCurso.getClave() %></b></td>
<%							} %>
								<td class="ayuda mensaje Llevó: <%=cursoLlevado%>">
<% 							if(existeNota){
								optativa = certAlumNota.getOptativa();
							}							
							if (!certCurso.getTipoCursoId().equals("7") && !certCurso.getTipoCursoId().equals("2")  ){
								out.print("<b>"+certCurso.getCursoNombre()+"</b>");
	 						}else if (certAlumNota.getEstado().equals("C")){						
								out.print("<b>"+certCurso.getCursoNombre()+certAlumNota.getOptativa()+"</b>"); 
		 					}else{ %>
						  			<b><%= certCurso.getCursoNombre()%><input id="optativa-<%=certCurso.getCursoId()%>" name="optativa-<%=certCurso.getCursoId() %>" type="text" class="text" value="<%= optativa!=null?optativa:"" %>" maxlength="90" size="40" /></b>
<%							} %>						  
								</td>
<%							if(certPlan.getFst().equals("S")){ %>
								<td align="center"><b><%=certCurso.getFst() %></b></td>
<%							}
							if(certPlan.getFsp().equals("S")){ %>
								<td align="center"><b><%=certCurso.getFsp() %></b></td>
<% 							} 
							totCreditos += Float.parseFloat(certCurso.getCreditos());%>
								<td align="center"><b><%=certCurso.getCreditos()%></b></td>
<%							String nota = "";  
							if( existeNota && certAlumNota.getEstado().equals("C")){
								creditos += Float.parseFloat(certCurso.getCreditos());
								nota = certAlumNota.getNota(); %>									
								<td align="center"><b><%=nota %></b></td>
<% 							}else{
								if(existeNota){
									nota = certAlumNota.getNota();
								}else{
									nota = alumnoCurso.getCursoCargaId().trim().equals("0") ? "":((alumnoCurso.getNotaExtra().trim().equals("")||Float.parseFloat(alumnoCurso.getNotaExtra())==0)?alumnoCurso.getNota():alumnoCurso.getNotaExtra());
									AlumPlan alumPlan = new AlumPlan();
									if(mapAlumIngreso.containsKey(alumnoCurso.getCodigoPersonal())){
										alumPlan = mapAlumIngreso.get(alumnoCurso.getCodigoPersonal());
									}
									
									if(alumPlan.getEscala().equals("10") && !nota.equals("")){
										nota = String.valueOf( Float.parseFloat(nota) / 10);
									}	
								}	
								if(!nota.equals("")){							
									creditos += Float.parseFloat(certCurso.getCreditos());
								} %>									
								<td align="center">
								  <input id="nota-<%=certCurso.getCursoId() %>" name="nota-<%=certCurso.getCursoId() %>" type="text" class="text" value="<%=nota%>" maxlength="5" size="3" style="text-align:right" onclick="myFunction('<%=certCurso.getCursoId()%>')"/>
								</td>
<%							}								
							String letras = "";
							if (existeNota){
								letras =  certAlumNota.getNotaLetra(); 
							}else{						
								letras = aca.cert.CertCursoUtil.numeroALetra(nota);
							}%>
								<td>
<%							if( existeNota && certAlumNota.getEstado().equals("C")){
								out.print(letras);	
							}else{ %>
							  	<input id="letra-<%=certCurso.getCursoId() %>" name="letra-<%=certCurso.getCursoId()%>" type="text" class="text"  value="<%=letras%>"maxlength="30" size="12"/>
<%							} %>						  				
								</td>
								<td align="center"><%=existeNota?"SI":"NO"%>
<%							if(existeNota){ %>
									<a class="fas fa-trash-alt" href="javascript:Borrar('<%=certAlumNota.getFolio() %>')"></a>
<%							} %>
								</td>
<% 									
							strCheck = "";
							if (existeNota){
								if (certAlumNota.getPromedia().equals("S")){
									strCheck = "checked";									
									if (certPlan.getCreditos().equals("0") ){										
										pglobal 	+= Double.valueOf(certAlumNota.getNota())* Double.valueOf(certCurso.getCreditos());
										credProm 	+= Float.parseFloat(certCurso.getCreditos());
										
									}else{										
										pglobal 	+= Double.valueOf(certAlumNota.getNota())* Double.valueOf(certCurso.getCreditos());
										credProm 	+= Float.parseFloat(certCurso.getCreditos());
									}
								}
							}
							if(todos.equals("S") && certCiclo.getTitulo().equals(semestre))strCheck="checked";
							if(ninguno.equals("S") && certCiclo.getTitulo().equals(semestre))strCheck=""; %>
								<td style="text-align:center;">
<%							if( existeNota && certAlumNota.getEstado().equals("C")){
									out.print(certAlumNota.getPromedia().equals("S")?"SI":"NO");
								}else{ %>
								  <input class="<%=certCiclo.getCicloId()%>" type='checkbox' name="mats-<%=certCurso.getCursoId() %>" <%=strCheck %> id="<%=certCurso.getCursoId() %>"/>
<%							} %>						  
								</td>
<%							if (existeNota && certAlumNota.getPromedia().equals("S")){
								float creditosTemp 	= 0;
								float notaTemp 		= 0;
								notaCreditos 		= 0;
								try{ 
									notaTemp 		= Float.parseFloat(nota);
									creditosTemp 	= Float.parseFloat(certCurso.getCreditos());
								}catch(Exception ex){ 
									notaTemp = 0;
									creditosTemp 	= 0;
								}								
								notaCreditos = creditosTemp*notaTemp;%>
								<td><%=getformato.format(notaCreditos)%></td>
<%							}%>
							</tr>
<%						}
					}
				} // termina for					
			// Promedio de las materias 
			if(credProm==0){
				pglobalF=0;
			}else{
				pglobalF = pglobal/credProm;	//si credProm es 0 no se hara el calculo, lo hago manual
			}
			
			String diaNombre 	= aca.util.NumberToDay.convertirLetras(Integer.parseInt(fecha.getDia(strFecha))).trim();
			if(diaNombre.equals("diecis&eacute;is")){
				diaNombre = "diecis&eacute;is";
			}else if(diaNombre.equals("veinti&uacute;n")){
				diaNombre = "veinti&uacute;n";
			}else if(diaNombre.equals("veintid&oacute;s")){
				diaNombre = "veintid&oacute;s";
			}else if(diaNombre.equals("veintitr&eacute;s")){
				diaNombre = "veintitr&eacute;s";
			}
			
			
			if (diaNombre.equals("primer")){
				diaNombre = " al "+diaNombre+" d&iacute;a "; 
			}else{
				diaNombre = " a los "+diaNombre+" d&iacute;s ";
			}			
			String yearName		= aca.util.NumberToLetter.convertirLetras(Integer.parseInt(fecha.getYear(strFecha))).trim();
%>
							<tr align = "right">
							  <td COLSPAN="5"><font size="3"><b>PROMEDIO PONDERADO</b></font></td>
							  <td align="center"><font size="3"><b><%=totCreditos%></b></font></td>
							  <td align="center"><font size="3"><b><%= getformato.format(pglobalF)%></b></font> (<%=getformato.format(pglobal)%> / <%=credProm%>)</td>
							  <td colspan="3">&nbsp;</td>
							  <td><%=getformato.format(pglobal)%></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td>
		  Insertar encabezado: 
				  <select id="Encabezado" name="Encabezado">
					<option value="0" <% if (certAlum.getEncabezado().equals("0")) out.print("selected");%>>Automatico</option>
					<option value="1" <% if (certAlum.getEncabezado().equals("1")) out.print("selected");%>>Desp&eacute;s de Semestres</option>
					<option value="2" <% if (certAlum.getEncabezado().equals("2")) out.print("selected");%>>Despu&eacute;s de Requisitos</option>	
				  </select>
				</td></tr>	
				<tr>
					<td><%=certPlan.gettFinal().replace("$creditos$","<b>"+creditos+"</b>").replace("$promedio$","<b>"+getformato.format(pglobalF)+"</b>")%></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						Para los fines y usos que convengan, se extiende el presente <b>CERTIFICADO
						<%=certAlum.getAvance().equals("C")?"COMPLETO":"PARCIAL"%></b>, en la ciudad de Montemorelos, Nuevo Le&oacute;n,
						M&eacute;xico<%=diaNombre%>del 
						mes de <%=fecha.getMesNombre(strFecha) %> del a&ntilde;o <%=yearName%>.
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
<%			String numMaterias = certAlum.getNumCursos()==null?"0":certAlum.getNumCursos();%>
				<tr>	
				  <td align="center" style="font-size:12pt;"><b>
				    Numero de Materias: &nbsp;
				    <input id="numCursos" name="numCursos" type="text" class="text" value="<%=numMaterias%>" maxlength="3" size="3" />&nbsp; &nbsp;
				    Materias del Certificado: <%=matGrabadas%>, Materias del Kardex: <%=matSugeridas%> &nbsp; Total: <%=matGrabadas+matSugeridas%></b>		    
				  </td>
				</tr>
				<tr>
					<td>&nbsp;
						<input type="hidden" value="<%=matGrabadas%>" name="pdfmaterias" id="pdfmaterias">
						<input type="hidden" value="<%=getformato.format(pglobalF)%>" name="pdfpromedio" id="pdfpromedio">
					</td>
				</tr>
				<tr class="table-info">
					<td align="center">
						<a href="javascript:guardar();" class="btn btn-primary"><spring:message code="aca.Guardar"/>:</a>
				<!--<input id="btnGuardar" <%=!certAlum.getAvance().equals("")&&!certAlum.getAvance().equals("A")?"":"style='visibility:hidden;'" %> class="btn btn-primary" type="button" value="Guardar" onclick="guardar();" />-->
<%	
				if (numAlumNotas >=1 && certAlum.getPlanId() != ""){%>
					<a href="javascript:imprimir('<%=planId%>');" class="btn btn-primary">Mostrar PDF</a>		
			<%	} %>
					<input class="btn btn-primary" type="button" value="Cerrar" onclick="return cerrar();" />
					<input class="btn btn-primary" type="button" value="Abrir" onclick="return abrir();" />
					<input class="btn btn-primary" type="button" value="Borrar" onclick="return borrar();" />				
				</td>
			</tr>
		</table>
	</form>
<%		}else{ %>
	<form id="forma" name="forma" action="alumno" method="post">
		<table>
			<tr>
				<td class="titulo">No existen datos de certificaci&oacute;n para le plan <%=planId %></td>
			</tr>			
		</table>
	</form>
<%		}
	}else{ %>
		<table class="center">
			<tr>
				<td class="titulo">Cargue un alumno en sessi&oacute;n para poder ver su certificado</td>
			</tr>
		</table>
<%	} %>
<script>
	function myFunction(id) {
		  // Get the checkbox
		  var checkBox = document.getElementById(id);
		  var nota = document.getElementById("nota-"+id);

		  // If the checkbox is checked, display the output text
		  if (checkBox.checked == true){
			alert("La nota de esta materia debe ser númerica para poder promediarla");
		  }
		} 
</script>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>