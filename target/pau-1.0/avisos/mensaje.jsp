<%@page import="aca.mentores.MentAlumno"%>
<%@page import="aca.catalogo.CatFacultadUtil"%>
<%@page import="aca.msj.ModuloMensaje"%>
<%@ include file= "../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.*" %>

<jsp:useBean id="msj" scope="page" class="aca.msj.Mensajes"/>
<jsp:useBean id="modulo" scope="page" class="aca.msj.ModuloMensaje"/>
<jsp:useBean id="moduloU" scope="page" class="aca.msj.ModuloMensajeUtil"/>
<jsp:useBean id="carrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="facultad" scope="page" class="aca.catalogo.CatFacultad"/>
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="mentorAlum" scope="page" class="aca.mentores.MentAlumno"/>
<jsp:useBean id="mentorAlumU" scope="page" class="aca.mentores.MentAlumnoUtil"/>
<jsp:useBean id="alumMensaje" scope="page" class="aca.msj.AlumMensaje"/>
<jsp:useBean id="alumMensajeU" scope="page" class="aca.msj.AlumMensajeUtil"/>

<html>
<link href="../academico.css" rel="STYLESHEET" type="text/css">
<body>
<script>
	parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
</script>

<script>
	function checaCuantasLetras(i){
		//alert("entro a checaCuantasLetras");
		var txt = document.avisos.mensaje.value;
		//alert("mensaje: "+txt);
		var n=txt.length;
		//alert(n);
		if (n > i)
			document.avisos.mensaje.value=txt.substring(0,i-1);
	}
	
	function permitidos(a,b){
		var i=0,j=0,k=0,ocultos=0;
		if(document.avisos.internos.checked ||
		   document.avisos.hombres.checked ||
		   document.avisos.externos.checked ||
		   document.avisos.mujeres.checked){
				document.avisos.matriculas.style.visibility="hidden";
				document.avisos.aconsejados.style.visibility="hidden";
				for(i = 0; i < a; i++){
					document.avisos.facultades[i].style.visibility="hidden";
				}
				for(i = 0; i < b; i++){
					document.avisos.carreras[i].style.visibility="hidden";
				}
				
		}else{
			document.avisos.matriculas.style.visibility="visible";
			document.avisos.aconsejados.style.visibility="visible";
			for(i = 0; i < a; i++){
				document.avisos.facultades[i].style.visibility="visible";
			}
			for(i = 0; i < b; i++){
				document.avisos.carreras[i].style.visibility="visible";
			}
			
		}
		
		if(document.avisos.matriculas.value.length != 0){
			document.avisos.internos.style.visibility = "hidden";
			document.avisos.hombres.style.visibility = "hidden";
			document.avisos.externos.style.visibility = "hidden";
			document.avisos.mujeres.style.visibility = "hidden";
			document.avisos.aconsejados.style.visibility="hidden";
			for(i = 0; i < a; i++){
				document.avisos.facultades[i].style.visibility="hidden";
			}
			for(i = 0; i < b; i++){
				document.avisos.carreras[i].style.visibility="hidden";
			}
			
		}else{
			if(document.avisos.matriculas.style.visibility == "visible"){
				document.avisos.internos.style.visibility = "visible";
				document.avisos.hombres.style.visibility = "visible";
				document.avisos.externos.style.visibility = "visible";
				document.avisos.mujeres.style.visibility = "visible";
				document.avisos.aconsejados.style.visibility="visible";
				for(i = 0; i < a; i++){
					document.avisos.facultades[i].style.visibility="visible";
				}
				for(i = 0; i < b; i++){
					document.avisos.carreras[i].style.visibility="visible";
				}
			}
		}
		
		if(document.avisos.aconsejados.checked){
			document.avisos.internos.style.visibility = "hidden";
			document.avisos.hombres.style.visibility = "hidden";
			document.avisos.externos.style.visibility = "hidden";
			document.avisos.mujeres.style.visibility = "hidden";
			document.avisos.matriculas.style.visibility="hidden";
			
			for(i = 0; i < a; i++){
				document.avisos.facultades[i].style.visibility="hidden";
			}
			for(i = 0; i < b; i++){
				document.avisos.carreras[i].style.visibility="hidden";
			}
		}else{
			if(document.avisos.aconsejados.style.visibility == "visible"){
				document.avisos.internos.style.visibility = "visible";
				document.avisos.hombres.style.visibility = "visible";
				document.avisos.externos.style.visibility = "visible";
				document.avisos.mujeres.style.visibility = "visible";
				document.avisos.matriculas.style.visibility="visible";
				for(i = 0; i < a; i++){
					document.avisos.facultades[i].style.visibility="visible";
				}
				for(i = 0; i < b; i++){
					document.avisos.carreras[i].style.visibility="visible";
				}
			}
		}
		
		
		for(k = 0; k < a; k++){
			if(document.avisos.facultades[k].checked){
				document.avisos.internos.style.visibility = "hidden";
				document.avisos.hombres.style.visibility = "hidden";
				document.avisos.externos.style.visibility = "hidden";
				document.avisos.mujeres.style.visibility = "hidden";
				document.avisos.matriculas.style.visibility="hidden";
				document.avisos.aconsejados.style.visibility="hidden";
				for(j=0;j<a;j++){
					if(j != k){
						document.avisos.facultades[j].style.visibility="hidden";
					}
				}
				for(i = 0; i < b; i++){
					document.avisos.carreras[i].style.visibility="hidden";
				}
				k = a;
				
			}else{
				if(document.avisos.facultades[k].style.visibility == "hidden")
					ocultos++;
			}
		}
		if(ocultos == (a-1)){
			//alert("Muestra los de facultades");
			document.avisos.internos.style.visibility = "visible";
			document.avisos.hombres.style.visibility = "visible";
			document.avisos.externos.style.visibility = "visible";
			document.avisos.mujeres.style.visibility = "visible";
			document.avisos.matriculas.style.visibility="visible";
			document.avisos.aconsejados.style.visibility="visible";
			for(j=0;j<a;j++){
					document.avisos.facultades[j].style.visibility="visible";
			}
			for(i = 0; i < b; i++){
				document.avisos.carreras[i].style.visibility="hidden";
			}
		}
		ocultos=0;
		//alert("b = "+b);
		for(k = 0; k < b; k++){
			if(document.avisos.carreras[k].checked){
				//alert(k);
				document.avisos.internos.style.visibility = "hidden";
				document.avisos.hombres.style.visibility = "hidden";
				document.avisos.externos.style.visibility = "hidden";
				document.avisos.mujeres.style.visibility = "hidden";
				document.avisos.matriculas.style.visibility="hidden";
				document.avisos.aconsejados.style.visibility="hidden";
				for(j = 0; j < b; j++){
					if(j != k){
						document.avisos.carreras[j].style.visibility="hidden";
					}
				}
				for(j = 0; j < a; j++){
					document.avisos.facultades[j].style.visibility="hidden";
				}
				k = b;
				
			}else{
				if(document.avisos.carreras[k].style.visibility == "hidden"){
					ocultos++;
				}
			}
		}
		//alert(ocultos + " == " + b);
		if(ocultos == (b-1)){
			alert("Muestra los de carreras");
			document.avisos.internos.style.visibility = "visible";
			document.avisos.hombres.style.visibility = "visible";
			document.avisos.externos.style.visibility = "visible";
			document.avisos.mujeres.style.visibility = "visible";
			document.avisos.matriculas.style.visibility="visible";
			document.avisos.aconsejados.style.visibility="visible";
			for(j = 0; j < b; j++){
					document.avisos.carreras[j].style.visibility="visible";	
			}
			for(j=0;j<a;j++){
					document.avisos.facultades[j].style.visibility="visible";
			}
		}
		
	}
	
	function verificaModulo(){
		if(document.avisos.modulo.value == "")
			alert("Elija un Area!");
		else
			checaCuantasLetras(500);
	}
	
	String.prototype.LTrim=new Function("return this.replace(/^\\s+/,'')");
	
	function enviar(a,b){
		if(document.avisos.mensaje.value.LTrim() != ""){
			if(document.avisos.modulo.value != ""){
				var condicion;
				var i;
				condicion = "";
				document.avisos.accion.value = 1;
				if(document.avisos.internos.checked &&
				   document.avisos.externos.checked){
						document.avisos.res.value = " RESIDENCIA_ID = ('E' OR 'I')";
				}else{
					if(document.avisos.internos.checked)
						document.avisos.res.value = " RESIDENCIA_ID = 'I'";
					if(document.avisos.externos.checked)
						document.avisos.res.value = " RESIDENCIA_ID = 'E'";
				}
				
				if(document.avisos.hombres.checked &&
				   document.avisos.mujeres.checked){
				   		document.avisos.sex.value = " SEXO = ('F' OR 'M')";
				}else{
					if(document.avisos.hombres.checked)
						document.avisos.sex.value = " SEXO = 'M'";
					if(document.avisos.mujeres.checked)
						document.avisos.sex.value = " SEXO = 'F'";
				}
				
				if(document.avisos.matriculas.style.visibility == "visible" &&
				   document.avisos.matriculas.value.length != 0){
				   		document.avisos.mat.value = document.avisos.matriculas.value;
				   		document.avisos.accion.value = 2;
				}
				
				if(document.avisos.aconsejados.checked)
					document.avisos.accion.value = 3;
					
				condicion = "";
				
				if(document.avisos.res.value != ""){
					if(condicion == ""){
						condicion = " WHERE " + document.avisos.res.value;
					}else{
						condicion = condicion + " AND " + document.avisos.res.value;
					}
				}
				
				if(document.avisos.sex.value != ""){
					if(condicion == ""){
						condicion = " WHERE " + document.avisos.sex.value;
					}else{
						condicion = condicion + " AND" + document.avisos.sex.value;
					}
				}
				
				for(i = 0; i < a; i++){
					if(document.avisos.facultades[i].checked){
						if(condicion == ""){
							condicion = "WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '"+document.avisos.facultades[i].value+"')"; 
						}else{
							condicion = condicion + " AND" + document.avisos.facultades[i].value;
						}
					}
				}
				
				for(i = 0; i < b; i++){
					if(document.avisos.carreras[i].checked)
						if(condicion == ""){
							condicion = " WHERE CARRERA_ID = '"+ document.avisos.carreras[i].value+"'";	
						}else{
							condicion = condicion + " AND" + document.avisos.carreras[i].value;
						}
				}
				
				if(document.avisos.accion.value == 1){
					if(condicion != ""){
						//alert("SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "+condicion);
						document.avisos.query.value = "SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS "+condicion;
						document.avisos.submit();
					}else{
						alert("Seleccione a donde desea mandar el mensaje");
					}
				}else{
					if(document.avisos.accion.value == 2){
						document.avisos.submit();
						//alert("Matriculas"+document.avisos.mat.value);
					}else{
						if(document.avisos.accion.value == 3){
							document.avisos.submit();
							//alert("Aconsejados");
						}else{
							alert("Seleccione a donde desea mandar el mensaje");
						}
					}
				}
			}else{
				alert("Elija un Area!");
			}
		}else{
			alert("Escriba un mensaje");
		}
	}
</script>
<%
	String codigoPersonal 	= (String)session.getAttribute("codigoEmpleado");
	String moduloId 		= request.getParameter("modulo");
	String mensaje 			= request.getParameter("mensaje");
	String accion 			= request.getParameter("accion");
	String escribe 			= "";
	String noInscritos 		= "";
	
    ArrayList<ModuloMensaje> lisModulos = moduloU.getListUsuario(conEnoc, codigoPersonal, "ORDER BY NOMBRE");
	ArrayList<aca.catalogo.CatFacultad> lisFacultad = facultadU.getListAll(conEnoc, "ORDER BY NOMBRE_FACULTAD");
	ArrayList<aca.catalogo.CatCarrera> lisCarrera = carreraU.getListAll(conEnoc, "ORDER BY FACULTAD_ID, NOMBRE_CARRERA");
	ArrayList<MentAlumno> lisAconsejados = mentorAlumU.getListAll(conEnoc, "WHERE ID_MENTOR = '"+codigoPersonal+"' ORDER BY ENOC.ALUM_NOMBRE(MATRICULA)");
	
	if(accion == null)
		accion = "0";
	
	if(accion.equals("1")){
		String query = request.getParameter("query");
		int mensajeId = msj.getMaxReg(conEnoc);
		
		msj.setMensajeId(String.valueOf(mensajeId));
		msj.setModuloId(moduloId);
		msj.setUsuario(codigoPersonal);
		msj.setFecha(aca.util.Fecha.getHoy());
		msj.setMensaje(mensaje);
		if(msj.insertReg(conEnoc)){
			ArrayList<String> lisAlum = alumMensajeU.getListMatriculas(conEnoc, query);
			String mat = "";
			alumMensaje.setMensajeId(String.valueOf(mensajeId));
			alumMensaje.setFecha(aca.util.Fecha.getHoy());
			alumMensaje.setRevisado("N");
			for(int i = 0; i < lisAlum.size(); i++){
				mat = (String) lisAlum.get(i);
				alumMensaje.setCodigoPersonal(mat);
				if(alumMensaje.insertReg(conEnoc))
					escribe = "Guardó correctamente";
			}
		}else{
			escribe = "Error al guardar";
		}
	}
	
	if(accion.equals("2")){
		String matriculas = request.getParameter("matriculas");
		boolean bien = true;
		for(int i = 0; i < matriculas.length(); i+= 9){
			//System.out.println(matriculas.substring(i,i+7));
				bien &= aca.alumno.AlumUtil.esInscrito(conEnoc, matriculas.substring(i,i+7));
		}
		
		if(bien){
			int mensajeId = msj.getMaxReg(conEnoc);
			
			msj.setMensajeId(String.valueOf(mensajeId));
			msj.setModuloId(moduloId);
			msj.setUsuario(codigoPersonal);
			msj.setFecha(aca.util.Fecha.getHoy());
			msj.setMensaje(mensaje);
			if(msj.insertReg(conEnoc)){
				alumMensaje.setMensajeId(String.valueOf(mensajeId));
				alumMensaje.setFecha(aca.util.Fecha.getHoy());
				alumMensaje.setRevisado("N");
				for(int i = 0; i < matriculas.length(); i+= 9){
					
					alumMensaje.setCodigoPersonal(matriculas.substring(i,i+7));
					if(alumMensaje.insertReg(conEnoc))
						escribe = "Guardó correctamente";
				}
			}else{
				escribe = "Error al guardar";
			}
		}else{
			escribe = "Las matriculas no estan correctamente escritas o algún alumno no esta inscrito";
		}
	}
	
	if(accion.equals("3")){
		int mensajeId = msj.getMaxReg(conEnoc);
		
		msj.setMensajeId(String.valueOf(mensajeId));
		msj.setModuloId(moduloId);
		msj.setUsuario(codigoPersonal);
		msj.setFecha(aca.util.Fecha.getHoy());
		msj.setMensaje(mensaje);
		if(msj.insertReg(conEnoc)){
			
			alumMensaje.setMensajeId(String.valueOf(mensajeId));
			alumMensaje.setFecha(aca.util.Fecha.getHoy());
			alumMensaje.setRevisado("N");
			for(int i = 0; i < lisAconsejados.size(); i++){
				mentorAlum = (aca.mentores.MentAlumno) lisAconsejados.get(i);
				if(aca.alumno.AlumUtil.esInscrito(conEnoc, mentorAlum.getCodigoPersonal() )){
					alumMensaje.setCodigoPersonal(mentorAlum.getCodigoPersonal() );
					if(alumMensaje.insertReg(conEnoc))
						escribe = "Guardó correctamente";
				}
			}
		}else{
			escribe = "Error al guardar";
		}
	}
%>
<form name="avisos" method="post" action="mensaje">
	<input name="accion" type="hidden" value="">
	<input name="res" type="hidden" value="">
	<input name="sex" type="hidden" value="">
	<input name="mat" type="hidden" value="">
	<input name="fac" type="hidden" value="">
	<input name="car" type="hidden" value="">
	<input name="query" type="hidden" value="">
	<table style="width:100%; margin: 0 auto;">
<%
	if(!escribe.equals("")){
%>
		<tr>
			<td colspan="5"><%=escribe %></td>
		</tr>
<%
	}
%>
		<tr>
			<td align="center" colspan="5">[<%=codigoPersonal %>][<%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "NOMBRE") %>]</td>
		</tr>
		<tr><td colspan="5">&nbsp;</td></tr>
		<tr>
			<td align="center" colspan="5">Area:
				<select name="modulo" id="modulo" onchange="location.href='mensaje?modulo='+this.options[this.selectedIndex].value">
					<option value="" ></option>
<%
	for(int i = 0; i < lisModulos.size(); i++){//javascript:cambia(this.options[this.selectedIndex].value)
		modulo = (aca.msj.ModuloMensaje) lisModulos.get(i);
		if(lisModulos.size() == 1)
			moduloId = modulo.getModuloId();
%>
					<option value="<%=modulo.getModuloId() %>" <%if(modulo.getModuloId().equals(moduloId) || lisModulos.size() == 1) out.print("Selected");%> ><%=modulo.getNombre() %></option>
<%	} %>
				</select>
			</td>
		</tr>
		<tr><td colspan="5">&nbsp;</td></tr>
		<tr>
			<td colspan="5">Mensaje:</td>
		</tr>
		<tr>
			<td colspan="5"><textarea cols="68" name="mensaje" onkeyup="verificaModulo();" ></textarea></td>
		</tr>
		<tr>
			<td colspan="5"><spring:message code="aca.Fecha"/>: <%=aca.util.Fecha.getHoy() %></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td width="10%"><input type="checkbox" name="internos" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);">Internos</td>
			<td><input type="checkbox" name="hombres" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"><spring:message code="aca.Hombres"/></td>
			<td>
				<input type="button" name="grabar" value="Grabar" onclick="enviar(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);">
			</td>
		</tr>
		<tr>
			<td width="10%"><input type="checkbox" name="externos" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);">Externos</td>
			<td><input type="checkbox" name="mujeres" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"><spring:message code="aca.Mujeres"/></td>
		</tr>
		<tr>
			<th><font size="2">Matrículas</font></th>
			<td width="2"><input type="checkbox" name="aconsejados" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"></td>
			<th colspan="2" width="10%"><font size="2">Aconsejados</font></th>
		</tr>
		<tr>
			<td>
				<textarea cols='7' rows="10" name="matriculas" onkeyup="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"></textarea>
			</td>	
			<td colspan="2">
				<textarea cols='60' rows="10" name="nombres" readonly >
<%
	for(int i = 0; i < lisAconsejados.size(); i++){
		mentorAlum = (aca.mentores.MentAlumno) lisAconsejados.get(i);
		if(aca.alumno.AlumUtil.esInscrito(conEnoc, mentorAlum.getCodigoPersonal())){
%>
<%=mentorAlum.getCodigoPersonal() %>   <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, mentorAlum.getCodigoPersonal(), "NOMBRE") %>
<%
		}
	}
%>
				</textarea>
			</td>
		</tr>
		<tr>
			<th colspan="5"><font size="2">Facultades</font></th>
		</tr>
<%
	
	for(int i = 0; i < lisFacultad.size(); i++){
		facultad = (aca.catalogo.CatFacultad) lisFacultad.get(i);
%>
		<tr>
			<td>&nbsp;</td>
			<td width="10%"><input type="checkbox" name="facultades" value="<%=facultad.getFacultadId() %>" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"></td>
			<td><%=facultad.getNombreFacultad() %></td>
		</tr>
<%
	}
%>
		<tr>
			<td><br>&nbsp;</td>
		</tr>
		<tr>
			<th colspan="5"><font size="2">Carreras</font></th>
		</tr>
<%
	String fac = "";
	for(int i = 0; i < lisCarrera.size(); i++){
		carrera = (aca.catalogo.CatCarrera) lisCarrera.get(i);
		if(!fac.equals(carrera.getFacultadId())){
			fac = carrera.getFacultadId();
%>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="5" align="center" bgcolor="#CCCCCC"><font size="2"><b><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, fac) %></b></font></td>
		</tr>
<%
		}
%>
		<tr>
			<td>&nbsp;</td>
			<td width="10%"><input type="checkbox" name="carreras" value="<%=carrera.getCarreraId() %>" onchange="permitidos(<%=lisFacultad.size() %>,<%=lisCarrera.size() %>);"></td>
			<td><%=carrera.getNombreCarrera() %></td>
		</tr>
<%
	}
%>
	</table>
</form>
</body>
</html>
<%@ include file= "../cierra_enoc.jsp" %>