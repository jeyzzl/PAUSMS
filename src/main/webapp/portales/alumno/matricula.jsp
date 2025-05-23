<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.vista.AlumnoCurso"%>

<jsp:useBean id="AlumnoPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumnoPersonalU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumnoAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AlumnoAcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="AlumnoPlan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumnoPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="AlumnoEstado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="AlumnoEstadoU" scope="page" class="aca.alumno.EstadoUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="MatriculaCursos" scope="page" class="aca.matricula.MatriculaUtil"/>
<jsp:useBean id="Kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="Acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="archivo" scope="page" class="aca.archivo.ArchivoUtil"/>
<jsp:useBean id="cargaPermiso" scope="page" class="aca.carga.CargaPermiso"/>

<script>

	function cambiaCarga(){
		document.frmmatricula.Accion.value = "1";
		document.frmmatricula.submit();
	}	
	function validaMatricula(){
		document.frmmatricula.Accion.value = "2";
		document.frmmatricula.submit();
	}
	function cambiaGrupo(matricula){
		document.frmmatricula.fmatricula.value = matricula;
		document.frmmatricula.Accion.value = "2";
		document.frmmatricula.submit();
	}
	function chkEnter(e){
		if (e.keyCode) keycode=e.keyCode; else keycode=e.which;
		if (keycode == 13){
			validaMatricula();
		}		
	}
</script>
<%
	String sAccion 			= request.getParameter("Accion");
	String sMatricula 		= request.getParameter("fmatricula");
	String sError	 		= request.getParameter("Error");
	String sCargaId 		= request.getParameter("fcargaId");
	String sBloqueId 		= request.getParameter("fbloqueId");
	
	String codigoAlumno 	= request.getParameter("fmatricula");
	String cargaSession		= (String) session.getAttribute("cargaId");
	String cargaCambia		= request.getParameter("fCargaCambia");
	
	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	
	double saldo = 0;

	// Matricula del alumno a inscribir
	if (codigoAlumno==null) 
		codigoAlumno = (String) session.getAttribute("codigoPersonal");
	if (cargaCambia==null){
		cargaCambia = cargaSession;
	}else{
		session.setAttribute("cargaId", cargaCambia);
	}
	float credXCiclo 		= 84;
	float alumCrCiclo		=  0;

	aca.carga.CargaUtil cargaU = new aca.carga.CargaUtil();
	aca.carga.Carga carga	= new aca.carga.Carga();
	ArrayList lisCarga = cargaU.getListCargasActivas(conEnoc,"ORDER BY 4,2");

	if (sAccion == null) sAccion = "1";
	if (sError == null) sError = "0";
	int nAccion			= Integer.parseInt(sAccion);

	String URL1 		= "matricula?fmatricula="+sMatricula+"&Error=1&Accion=1";
	String URL2	 		= "matricula?fmatricula="+sMatricula+"&Error=2&Accion=1";
	String URL3 		= "matricula?fmatricula="+sMatricula+"&Error=3&Accion=1";
	String URL4 		= "matricula?fmatricula="+sMatricula+"&Error=4&Accion=1";
	String URL5 		= "matricula?fmatricula="+sMatricula+"&Error=5&Accion=1";
	String URL6 		= "matricula?fmatricula="+sMatricula+"&Error=6&Accion=1";
	
	String carrAlumno	= "";
	String salto 		= "";
	String sArchivo		= "";
	String titPre		= "";	
	String grupo		= request.getParameter("grupo");
	if (grupo==null) grupo = "%";
	
	carrAlumno = aca.alumno.PlanUtil.getCarreraId(conEnoc,codigoAlumno);
	
	// Variable para validar el candado de finanzas por deuda
	boolean candadoFinanzas 	= false;
			
	// Variable para validar el permiso
	boolean permisoFinanzas	= aca.financiero.FinPermisoUtil.tienePermiso(conEnoc, codigoAlumno);
			
	// Saldo global del alumno a la fecha de consulta
	double saldoGlobal 		= aca.financiero.FesContratoFinancieroUtil.getAlumSaldoGlobal(conEnoc, codigoAlumno);
			
	if ( saldoGlobal <= -3000 && permisoFinanzas == false){
		candadoFinanzas = true;
	}
	
	if (nAccion==1){ 
%>
<form action="matricula" method="post" name="frmmatricula" target="_self">
<input type='hidden' name='Accion'>
<table style="width:90%" border="1" align="center"   bordercolor="#000000">
  <tr><th align="center"><font size="2">Proceso de Matricula </font></th></tr>
  <tr><td>
	<table style="width:100%" >
    <tr>
      <td width="100%" align="center">Carga 
		<select onchange='javascript:cambiaCarga()' name="fCargaCambia">
<%		for (int i=0; i< lisCarga.size(); i++){
			carga = (aca.carga.Carga) lisCarga.get(i);
%>
		<option <% if(cargaCambia.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>"><%=carga.getNombreCarga()%></option>
<%		}%>          	    
   	    </select>
   	  </td>
    </tr>
    <tr>
    	<td align="center">
    		Matricula:
    		<input name="fmatricula" type="text" class="text" size="7" value="<%=codigoAlumno%>" onkeypress="chkEnter(event)" readonly>&nbsp;
    	</td>
    </tr>
<% 		if (sError.equals("1")){
%>	<tr><td align="center"><b><font color="#FF3749">No se encontro el alumno <%=sMatricula%></font></b></td></tr>
<% }		if (sError.equals("2")){
%>	<tr><td align="center"><b><font color="#FF3749">El Alumno: <%=sMatricula%> no tiene Plan de Estudios..��<br> Atte. Dir. Admisiones y Registro (Ext. 120)</font></b></td></tr>	
<% }		if (sError.equals("3")){
%>	<tr><td align="center"><b><font color="#FF3749">No esta habilitado el periodo de matricula para <br>esta fecha...&iexcl;&iexcl;</font> <br>Atte. Dir. de Admisiones y Registro(Ext 120).</b></td></tr>	
<% }		if (sError.equals("4")){
%>	<tr><td align="center"><b><font color="#FF3749">El Alumno: <%=sMatricula%> ya est� inscrito..��</font></b></td></tr>	
<% }		if (sError.equals("5")){
%>	<tr><td align="center"><b><font color="#FF3749">No tiene derecho para matricular al alumno: <%=sMatricula%></font></b></td></tr>
<% }		if (sError.equals("6")){%>
	<tr>
	  <td align="center">
	    <table style="width:70%" >
<%  
			AlumnoAcademico = AcademicoU.mapeaRegId(conEnoc, sMatricula);								
%>  	
		<tr>
          <td colspan="2" align="center">
            <strong>
              <%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,sMatricula,"NOMBRE")%> - 
              <%= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,carrAlumno) %>
            </strong>
          </td>
        </tr>
	    <tr>
          <td colspan="2" align="center">
            <strong>
            [ <% if(aca.alumno.AlumUtil.getPaisId(conEnoc,sMatricula).equals("91")){%>Mexicano<% }else{ %>Extranjero<% }%> ] -
            [ <% if( AlumnoAcademico.getResidenciaId().equals("E")){ %> Externo<% }else{ %>Interno<% }%> ] -
            [ <% if(AlumnoAcademico.getClasFin().equals("1")){%>ACFE<% }else{%>NO ACFE<% }%> ] -
            [ <%= AlumnoAcademico.getModalidad()%> ] -
            [<%=aca.catalogo.CatTipoAlumno.getNombreTipo(conEnoc,Integer.parseInt(AlumnoAcademico.getTipoAlumno()))%>]
            [<%=credXCiclo%>]
            </strong>
          </td>
        </tr>      
	  	<tr>
          <td colspan="2">Para continuar es necesario Arreglar los sig. asuntos:</td>
        </tr>
        <tr>
          <th>Candado</th>
          <th>Solucionar en: </th>
        </tr>
<%	
			aca.candado.CandAlumnoUtil cau	= new aca.candado.CandAlumnoUtil();
			ArrayList<aca.candado.CandAlumno> lisCandado	= null;
			lisCandado	= cau.getLista(conEnoc, sMatricula, "A"," ORDER BY FOLIO");
			for(int z=0; z<lisCandado.size(); z++){
				aca.candado.CandAlumno ca = (aca.candado.CandAlumno) lisCandado.get(z);
%>
        <tr>
          <td><%=aca.candado.CandTipoUtil.getNombreTipo(conEnoc,ca.getCandadoId().substring(0,2))%></td>
          <td><%=aca.candado.CandTipoUtil.getSolucion(conEnoc, ca.getCandadoId().substring(0,2))%></td>
        </tr>
<%			}
			sArchivo = archivo.autorizaAlumno(conEnoc,sMatricula);
			if (!sArchivo.substring(0,1).equals("A") ){
%>
  		<tr>
          <td>DOCUMENTOS</td>
          <td>Dir. Certificaci�n y Archivo Ext. 130</td>
        </tr>
<%
			}
%>                
        </table>
	  </td>
    </tr>
<% }%>
	<tr><td align="center">&nbsp;</td></tr>
	<tr><td align="center">&nbsp;</td></tr><tr>
      <td align="center"><input type="submit" onclick='javascript:validaMatricula()' name="bSiguente" value="Siguiente >"></td></tr>
    </table>
  </td></tr>
</table>
</form>

<% } else if (nAccion==2) {  
		// Cancela o aplica Candados por deuda
		//aca.candado.CandAlumnoUtil.validaCandadoDeudor(conEnoc, sMatricula );

		AlumnoPersonal.mapeaRegId(conEnoc,sMatricula);
		if (AlumnoPersonalU.existeReg(conEnoc, sMatricula)){
			AlumnoPlanU.mapeaRegId(conEnoc,sMatricula);
			if (AlumnoPlanU.existeReg(conEnoc, sMatricula, AlumnoPlan.getPlanId() )){
				if (CargaBloque.CargaActiva(conEnoc,(String)session.getAttribute("cargaId"))){
					AlumnoEstado.mapeaRegId(conEnoc,sMatricula,CargaBloque.getCargaId(),CargaBloque.getBloqueId());
					if (!AlumnoEstadoU.existeReg(conEnoc, sMatricula,CargaBloque.getCargaId(),CargaBloque.getBloqueId()) || !AlumnoEstado.getEstado().equals("I")){
						Acceso.mapeaRegId(conEnoc, (String)session.getAttribute("codigoPersonal"));
						//System.out.println(sMatricula+" Carrera: "+AlumnoUtil.getCarreraIdCodigo(conEnoc, sMatricula)+" Priv.: "+Acceso.getAccesos()+Acceso.getAccesos().indexOf(AlumnoUtil.getCarreraIdCodigo(conEnoc, sMatricula)));
						/*if (Acceso.getAdministrador().equals("S") || Acceso.getAccesos().indexOf(carrAlumno) != -1){ */
							// Si no tiene candados
							if ( aca.candado.CandAlumnoUtil.conCandados(conEnoc, sMatricula) == false/* || codigoUsuario.equals("9800140")*/){
								String URLOK = "matricula?Accion=3&fmatricula="+sMatricula+"&fcargaId="+CargaBloque.getCargaId()+"&fbloqueId="+CargaBloque.getBloqueId();
								session.setAttribute("codigoAlumno", sMatricula);
								salto = URLOK;
							}else{ 
								salto = URL6;
							}
						/*}else{ 
							salto = URL5;
						}*/
					}else{ 
						salto = URL4;
					}
				}else{
					salto = URL3;
				}
			}else{ 
				salto = URL2;
			}
		}else{
			salto = URL1; 

		}
		out.print("<div class='alert alert-success'><b>OK...<a class='btn btn-primary' href='"+salto+"'>Regresar</a></div>");
		//response.sendRedirect(salto);
%>		
<%-- 		<jsp:forward page = "<%=salto%>"/> --%>
<%		
	}else if (nAccion==3) {
		Carga.mapeaRegId(conEnoc,sCargaId);
		AlumnoPersonal = AlumUtil.mapeaRegId(conEnoc,sMatricula);
		AlumnoAcademico.mapeaRegId(conEnoc,sMatricula);
		Acceso = AccesoU.mapeaRegId(conEnoc, codigoUsuario);
		cargaPermiso.mapeaRegId(conEnoc, sCargaId, carrAlumno); 
		if(AlumnoAcademico.getModalidadId().trim().equals(Acceso.getModalidad().trim()) || Acceso.getModalidad().trim().equals("0")){
				AlumnoPlan.mapeaRegId(conEnoc,sMatricula);
				sArchivo = archivo.autorizaAlumno(conEnoc,sMatricula);
				if (sArchivo.substring(0,1).equals("A") ){
					sArchivo = "Doctos: Autorizado..��";
				}else{
					sArchivo = "<font color='red'>Faltan Documentos</font>";
				}
				
%>
<script type="text/javascript">
   function EnviarCursosDisp()
   {
    	document.frmmatricula.submit();
   }      
</script>
<table style="width:95%" border="1" align="center"   bordercolor="#000000">
  <tr>
    <th align="center"><font size="2"><spring:message code="aca.CargaAcademica"/>:<%= Carga.getNombreCarga()%> </th>
  </tr>
  <tr>
    <td>
  	  <table style="width:100%" >
        <tr>
          <td colspan="2" align="center">
          <font color="#000066" face="Verdana, Arial, Helvetica, sans-serif"><strong>
            Alumno: <%=sMatricula%> - <%=AlumnoPersonal.getNombreLegal()%> (Plan: <%=AlumnoPlan.getPlanId()%>)</strong>
            &nbsp; &nbsp; <a href="matricula?Accion=1"><strong>[ cambiar ]</strong></a>
          </font>
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2"><strong>
            <font color="#006600" size="1" face="Verdana, Arial, Helvetica, sans-serif">
		  	[<% if(AlumnoAcademico.getResidenciaId().equals("I")){ %>Interno<% }else{%>Externo<% }%>] -
		  	[<% if(AlumnoPersonal.getNacionalidad().equals("91")){ %>Mexicano<% }else{%>Extranjero<% }%>] - 
		  	[<% if(AlumnoAcademico.getClasFin().equals("1")){%>ACFE<% }else{%>NO ACFE<% }%>] - 
		  	[ <%=AlumnoPersonal.getFNacimiento()%> ] - [ <%=AlumnoAcademico.getModalidad()%> ] - 
		  	[ <%=sArchivo%> ] -
		  	[<%=aca.catalogo.CatTipoAlumno.getNombreTipo(conEnoc,Integer.parseInt(AlumnoAcademico.getTipoAlumno()))%>] -
<%
		//Creditos permitidos para la preparatoria en un a�o escolar.
		credXCiclo = aca.vista.AlumnoCursoUtil.getCreditosPermitidosXPeriodo(conEnoc,codigoAlumno,sCargaId);

		//Creditos llevados por el alumno durante el presente ciclo escolar
		alumCrCiclo = aca.vista.AlumnoCursoUtil.creditosDelCiclo(conEnoc,codigoAlumno,sCargaId);

		//System.out.println("Es de recuperacion:"+sCargaId+":"+carrAlumno);
		if( aca.carga.CargaPermisoUtil.esCarreraRecuperacion(conEnoc, sCargaId, carrAlumno)){
			out.println( "M�x.["+credXCiclo+" Cr.]"+" - Tiene["+alumCrCiclo+" Cr.]");
		}
%>		  	
			</font></strong>
		  </td>
        </tr>
        <!-- tr>
        	<td align="center" cospan="2">
        		<a href="radiografia.jsp?carga=<%=Carga.getCargaId() %>" class="ayuda <%=idJsp %> 001">[ Radiografia del Plan ]</a>
        	</td>
        </tr -->
	  </table>
	</td>
  </tr>
  <tr>
    <td>
	  <table style="width:100%">
		 	<tr><td width="50%" valign="top">			
		 		<form action="matricula?Accion=4&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&fbloqueId=<%=sBloqueId%>" method="post" name="frmmatricula" target="_self">
				<table  width="100%" >
					<tr><td align="center" colspan="5" bgcolor="#FFCC66"><strong>Cursos Disponibles</strong></td>
					</tr>
					<tr height="15"><td align="center" colspan="5"><strong><%=AlumnoPersonalU.getCarrera(conEnoc,sMatricula)%></strong></td></tr>
					<tr bgcolor="#FFCC66">
					  <td width="9%"><div align="center"><strong><spring:message code="aca.Ciclo"/></strong></div></td>
					  <td width="70%"><div align="center"><strong>Curso / Profesor</strong></div></td>
					  <td width="7%"><div align="center"><strong>Cr.</strong></div></td>
					  <td width="7%"><div align="center"><strong>Hr.</strong></div></td>
					  <td width="7%"><div align="center"><strong><spring:message code="aca.Operacion"/></strong></div></td>
				  	</tr>
				  	<tr>
				  	  <td colspan="7" align="center"> 
				  	    <strong>Grupo:</strong>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=%">[<strong>Todos</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=A">[<strong>A</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=B">[<strong>B</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=C">[<strong>C</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=D">[<strong>D</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=E">[<strong>E</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=F">[<strong>F</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=G">[<strong>G</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=H">[<strong>H</strong>]</a>				  	    
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=N">[<strong>N</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=U">[<strong>U</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=1">[<strong>1</strong>]</a>				  	    
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=2">[<strong>2</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=3">[<strong>3</strong>]</a>
				  	    <a href="matricula?Accion=2&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&grupo=4">[<strong>4</strong>]</a>&nbsp;&nbsp;
				  	    <font color="blue" size="1"><strong><% if (grupo.equals("%")) out.print("< Todos >"); else out.print("< "+grupo+" >"); %></strong></font>
				  	  </td>
				  	</tr>
				  	<tr>
				  	  <td colspan="5" align="center">
				  	    <input name="EnviarCursos" onClick="javascript:EnviarCursosDisp()" type="button" value="Enviar Cursos Marcados >>">				  	    						
				  	  </td>
				    </tr> 
<%				ArrayList lisCursosDisponibles = new ArrayList();
				String sOrden 			= "";
				String sCarreraId 		= "";
				String sCicloId 		= "";
				String sCursoIdCarga 	= "";
				String sCursoCargaId 	= "";
				String sNombreCurso 	= "";
				String sGrupo 			= "";
				String sLetra 			= "";
				String sProfesor 		= "";
				String sCarrera 		= "";
				String sLCarrera 		= "";
				String sHoras 			= "";
				String sCreditos 		= "";
				String sModalidad 		= "";
				String sBloque 			= "";
				String optativa			= "";
				String horario			= "";
				
				String sColorFondo 		= "";
				String sColorCurso 		= "";
				
				sModalidad = AlumnoAcademico.getModalidadId();				
				
				// Alumnos de verano pueden llevar materias presenciales
				if (AlumnoAcademico.getModalidadId().equals("3")) sModalidad = "1,3";
				
				// Alumnos de la prepa nocturna pueden llevar materias de la prepa en modalidad prescencial
				if (aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc,aca.alumno.PlanUtil.getCarreraId(conEnoc,sMatricula)).equals("107")&& AlumnoAcademico.getModalidadId().equals("4")){
					sModalidad = "1,4";					
				}
								
				int Contador=1;
				boolean preOK = false;
				boolean RecOk = false;
				String mensaje="";
				if (grupo.equals("%")){
					lisCursosDisponibles = MatriculaCursos.getListCursosDisp(conEnoc,sMatricula,sCargaId,AlumnoPlan.getPlanId(),sModalidad);
				}else{
					lisCursosDisponibles = MatriculaCursos.getListCursosDisp(conEnoc,sMatricula,sCargaId,AlumnoPlan.getPlanId(),sModalidad,grupo);
				}
				//System.out.println("Encontr�:"+lisCursosDisponibles.size());
				for (int i=0; i<lisCursosDisponibles.size();i++,Contador++){
					java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisCursosDisponibles.get(i),"~");
					
					sOrden 			= sToken.nextToken();
					sCarreraId 		= sToken.nextToken();
					sCicloId 		= sToken.nextToken();
					sCursoIdCarga 	= sToken.nextToken();
					sCursoCargaId	= sToken.nextToken();
					sNombreCurso 	= sToken.nextToken();
					sGrupo			= sToken.nextToken();
					sLetra			= sToken.nextToken();
					sProfesor 		= sToken.nextToken();
					sCarrera 		= sToken.nextToken();
					sLCarrera 		= sToken.nextToken();
					sHoras 			= sToken.nextToken();
					sCreditos 		= sToken.nextToken();
					sModalidad 		= sToken.nextToken();
					sBloque 		= sToken.nextToken();
					optativa 		= sToken.nextToken();
					horario 		= sToken.nextToken();
					
					// Agrega el nombre de la materia optativa al nombre del curso del plan de estudios del alumno
					if (optativa.length()>5){
						sNombreCurso = sNombreCurso+" ("+optativa+")";
					}
					
					titPre = "";
					String cursoId = sCursoIdCarga.substring(8);
					if (i%2!=0) sColorFondo = "bgcolor=\"#EEEEEE\""; else sColorFondo = "";
					
					preOK = MatriculaCursos.preCursosAprobados(conEnoc,sMatricula,AlumnoPlan.getPlanId(),sCursoIdCarga);
				
					// Para revisar si ya llevo la materia para llevarla en verano
					String cursoPendiente = "N";					
					
					if( aca.carga.CargaPermisoUtil.esCarreraRecuperacion(conEnoc, sCargaId, sCarreraId) && sModalidad == "1"){
						if(aca.vista.AlumnoCursoUtil.yaLlevoLaMateria(conEnoc, sMatricula, sCursoIdCarga )){
							cursoPendiente = "S";
							RecOk = true;
						}else if (Boolean.parseBoolean(session.getAttribute("admin")+"")){
							cursoPendiente = "A";
							RecOk = true;
						}else if(!aca.vista.AlumnoCursoUtil.terminoCiclo(conEnoc, sMatricula, sCargaId, credXCiclo) || sCargaId.substring(4).equals("1F")){
							RecOk = true;
						}
					}else{
						RecOk=true;						
					}
					
					if (preOK) sColorCurso = "000066"; else sColorCurso = "FF0000";
					if(cursoPendiente.equals("A")) sColorCurso = "008800";
%>				  			
				  <tr <%=sColorFondo%> title="<%=(!RecOk)?"Esta materia no es de recuperacion y no puede ser inscrita...":"" %>">
					  <td width="9%" rowspan="2" align="center"><font color="#<%=sColorCurso%>"><%=sCicloId%></font></td>
					  <td width="70%"><font color="#<%=sColorCurso%>"><b><%=sNombreCurso%></b></font></td>
					  <td width="7%" align="center"><font color="#<%=sColorCurso%>"><%=sCreditos%></font></td>
					  <td width="7%" align="center"><font color="#<%=sColorCurso%>"><%=sHoras%></font></td>
					  <td width="7%" align="center" rowspan="2">                       
<%					  	if ( !sProfesor.equals("Sin Maestro") && RecOk && preOK && horario.equals("si")){ %>
						<input type="hidden" name="cursoCargaId<%=Contador%>" value="<%=sCursoCargaId%>">
						<input type="hidden" name="cursoId<%=Contador%>" value="<%=sCursoIdCarga%>">
					  	<input type="checkbox" name="chkMateria<%=Contador%>" value="1">
<%						}else{
							if (sProfesor.equals("Sin Maestro")) mensaje="Sin Maestro";
							if (!preOK){
								mensaje = "Sin Prerre.";
								titPre = aca.plan.CursoUtil.getMateria(conEnoc,MatriculaCursos.preCursoFalta(conEnoc,sMatricula,sCursoIdCarga));
							}
							if(!RecOk){
								mensaje = "Sin Recup.";
								titPre = "Es periodo de recuperacion y la materia no ha sido llevada";
							}
							
							if(horario.equals("no")){mensaje="Sin Horario"; sColorCurso = "FF0000";}
%>							 <font color="#<%=sColorCurso%>" size="1" title='<%=titPre%>'><%=mensaje%></font>
<%						}
%>					</td>
				  </tr>
				  <tr <%=sColorFondo%>>
				    <td width="91%" colspan="3">
  						<font color="#<%=sColorCurso%>" size="1">
						<%=sProfesor%> <strong>[<%=sGrupo%>] - [Bloq.<%=sBloque%>] - [<%=sModalidad%>]
                        </strong> </font>						
					</td>
				  </tr>			
<%				}
%>				  	<input type="hidden" name="Total" value="<%=lisCursosDisponibles.size()%>">
					<tr valign="middle" height="60">
					  <td colspan="5" align="center"><input name="EnviarCursos" onClick="javascript:EnviarCursosDisp()" type="button" value="Enviar Cursos Marcados >>">
				      </td>
				  </tr>
				  </form>
			    </table>
			  </td>	  
          	  <td width="50%" valign="top">
			<form action="matricula?Accion=5&fmatricula=<%=sMatricula%>&fcargaId=<%=sCargaId%>&fbloqueId=<%=sBloqueId%>" method="post" name="frmmatricula2" target="_self">
			  	<table  width="100%" >
                  <tr>
                    <td align="center" bgcolor="#FFCC66" colspan="5"><strong>Cursos Matriculados</strong></td>
                  </tr>
                  <tr>
                    <td align="center" colspan="5">&nbsp;</td>
                  </tr>
                  <tr bgcolor="#FFCC66">
                    <td width="9%"><div align="center"><strong><spring:message code="aca.Ciclo"/></strong></div></td>
                    <td width="70%"><div align="center"><strong>Curso / Profesor</strong></div></td>
                    <td width="7%"><div align="center"><strong>Cr.</strong></div></td>
                    <td width="7%"><div align="center"><strong>Hr.</strong></div></td>
                    <td width="7%"><div align="center"><strong><spring:message code="aca.Operacion"/></strong></div></td>
                  </tr>
<%				ArrayList lisCursosMatriculados = new ArrayList();
				String s2CursoCargaId 	= "";
				String s2CursoId 		= "";
				String s2Grupo 			= "";
				String s2Eap 			= "";
				String s2Profesor		= "";
				String s2Ciclo 			= "";
				String s2Creditos 		= "";
				String s2Horario 		= "";
				String s2Hr 			= "";
				String s2NombreCurso 	= "";
				String s2TipocalId 		= "";
				String s2Modalidad 		= "";
				String s2Bloque 		= "";
				String s2Optativa 		= "";
				
				String s2ColorFondo		= "";
				String s2Color			= "";
				
				int Contador2=1, nCreditos=0, nHoras=0;
				lisCursosMatriculados = MatriculaCursos.getListCursosMat(conEnoc,sMatricula,sCargaId,AlumnoPlan.getPlanId(),AlumnoAcademico.getModalidadId());
				for (int i=0; i<lisCursosMatriculados.size();i++,Contador2++){
					java.util.StringTokenizer sToken = new java.util.StringTokenizer((String) lisCursosMatriculados.get(i),"~");
					s2CursoCargaId = sToken.nextToken();
					s2CursoId 	= sToken.nextToken();
					s2Grupo 		= sToken.nextToken();
					s2Eap 			= sToken.nextToken();
					s2Profesor		= sToken.nextToken();
					s2Ciclo 		= sToken.nextToken();
					s2Creditos		= sToken.nextToken();
					s2Horario		= sToken.nextToken();
					s2Hr 			= sToken.nextToken();
					s2NombreCurso 	= sToken.nextToken();
					s2TipocalId 	= sToken.nextToken();
					s2Modalidad 	= sToken.nextToken();
					s2Bloque 		= sToken.nextToken();
					s2Optativa 		= sToken.nextToken();
					
					// Agrega el nombre de la materia optativa al nombre del curso del plan de estudios del alumno
					if (s2Optativa.length()>5){
						s2NombreCurso = s2NombreCurso+" ("+s2Optativa+")";
					}
					
					nCreditos = nCreditos + Integer.parseInt(s2Creditos);
					nHoras = nHoras + Integer.parseInt(s2Hr);
					if (i%2!=0) sColorFondo = "bgcolor=\"#EEEEEE\""; else sColorFondo = "";
					if (s2TipocalId.equals("M") || s2TipocalId.equals("C")){
						s2Color = "0000CC";
					}else{ 
						s2Color = "009900";
					}
%>
                  <tr <%=sColorFondo%>>
                    <td width="9%" rowspan="2" align="center"><font color="#<%=s2Color%>"><%=s2Ciclo%></font></td>
                    <td width="70%"><font color="#<%=s2Color%>"><b><%=s2NombreCurso%></b></font></td>
                    <td width="7%" align="center"><font color="#<%=s2Color%>"><%=s2Creditos%></font></td>
                    <td width="7%" align="center"><font color="#<%=s2Color%>"><%=s2Hr%></font></td>
                    <td width="7%" align="center" rowspan="2">
                      <%					if (s2TipocalId.equals("M") || s2TipocalId.equals("C")){
%>
                      <font color="#<%=s2Color%>">
                      <input type="hidden" name="cursoCargaId2<%=Contador2%>" value="<%=s2CursoCargaId%>">
                      <input name="chkMateria2<%=Contador2%>" type="checkbox" value="1">
                      </font>
                      <%					}else{
%>
                      <b>Inscrita</b>
                      <%					}
%>
                    </td>
                  </tr>
                  <tr <%=sColorFondo%>>
                    <td width="91%" colspan="3"> <font color="#<%=s2Color%>" size="1"><%=s2Profesor%> [Bloq.<%=s2Bloque%>] - [<%=s2Modalidad%>]</font> </td>
                  </tr>                  
                  <%				}%>
				  <tr bgcolor="#FFCC66">
                    <td>&nbsp;</td>
                    <td align="center"> <strong>T o t a l e s . . .</strong></td>
                    <td align="center"><strong><%=nCreditos%></strong></td>
					<td align="center"><strong><%=nHoras%></strong></td>
					<td>&nbsp;</td>
                  </tr>
                  <input type="hidden" name="Total2" value="<%=lisCursosMatriculados.size()%>">
                  <tr valign="middle" height="60">
                    <td colspan="5" align="center"><input name="RegresarCursos" type="submit" value="<< Quitar Cursos">
                    </td>
                  </tr>
                </table>
			</form>			  
			  </td>
       	  	</tr>
<%				  
			if (sArchivo.substring(0,1).equals("D")){
				if( aca.carga.CargaPermisoUtil.esCarreraRecuperacion(conEnoc, sCargaId, sCarreraId) && sModalidad == "1"){
					if (Boolean.parseBoolean(session.getAttribute("admin")+"")){
						RecOk = true;
					}else if(!aca.vista.AlumnoCursoUtil.terminoCiclo(conEnoc, sMatricula, sCargaId, credXCiclo) || sCargaId.substring(4).equals("1F")){
						RecOk = true;
					}
				}else{
					RecOk=true;						
				}
 %>
			  <tr>										    
				<td colspan="2" align="center">
				  <form name="Calculo" method="post" action="muestraCalculo?matricula=<%=sMatricula%>">
				  Forma de Pago: 
				  <select name="sltFormaPago">
				    <option value="C">Contado</option>
				    <!-- option value="P">Pagar&eacute;</option -->
	              </select>&nbsp;&nbsp;&nbsp;
	              <input name="Calculo" type="submit" value="C&aacute;lculo Estimado" <%=!RecOk?"Disabled":"" %>>
	              <font color="blue"><b>Click aqu&iacute; cuando hayas terminado</b></font>
	              </form>
	            </td>					
			  </tr>
<%
			}
%>
	    </table>
	</td></tr>
</table>
<%  
		}else{
%>
<table style="margin: 0 auto;">
	<tr>
		<td align="center"><font size="3" color="blue">No tienes permiso para inscribir a este alumno.<br />El alumno es de modalidad <b><%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, AlumnoAcademico.getModalidadId()) %></b></font></td>
	</tr>
</table>
<%
		}
	}else if (nAccion == 4){
		String CURSOOK = "matricula?Accion=3&fmatricula="+sMatricula+"&fcargaId="+sCargaId+"&fbloqueId="+sBloqueId;
		int OKs=0,Grab=0;
		for (int i=1;i<=Integer.parseInt(request.getParameter("Total"));i++){
			if (request.getParameter("chkMateria"+i)!=null){
				Kardex.setCodigoPersonal(sMatricula);
				Kardex.setCursoCargaId(request.getParameter("cursoCargaId"+i));
				Kardex.setCursoId(request.getParameter("cursoId"+i));
				Kardex.setCursoId2(request.getParameter("cursoId"+i));
				Kardex.setTipoCalId("M");
				Kardex.setTipo("O");
				Grab++;
				if (Kardex.insertReg(conEnoc))OKs++;
			}
		}
		if (OKs == Grab){
			AlumnoEstado.setCodigoPersonal(sMatricula);
			AlumnoEstado.setCargaId(sCargaId);
			AlumnoEstado.setBloqueId(sBloqueId);
			AlumnoEstado.setEstado("M");
			if (AlumnoEstadoU.existeReg(conEnoc, sMatricula, sCargaId, sBloqueId)) AlumnoEstadoU.updateReg(conEnoc, AlumnoEstado);
			else AlumnoEstadoU.insertReg(conEnoc, AlumnoEstado);
			
			out.print("<div class='alert alert-success'><b>Error al grabar...<a class='btn btn-primary' href='"+CURSOOK+"'>Regresar</a></div>");
			//response.sendRedirect(CURSOOK);
%>					
<%-- 			<jsp:forward page = "<%=CURSOOK%>"/> --%>
<%		}else{ 
%>			<strong>Ocurrio un error al intentar matricular una de las materias...</strong>
<%		}
	}else if (nAccion == 5){
		String CURSOOK = "matricula?Accion=3&fmatricula="+sMatricula+"&fcargaId="+sCargaId+"&fbloqueId="+sBloqueId;
		int OKs=0,Borr=0;
		for (int i=1;i<=Integer.parseInt(request.getParameter("Total2"));i++){
			if (request.getParameter("chkMateria2"+i)!=null){
				Kardex.setCodigoPersonal(sMatricula);
				Kardex.setCursoCargaId(request.getParameter("cursoCargaId2"+i));
				Borr++;
				if (Kardex.deleteReg(conEnoc)) OKs++;	
			}
		}		
		if (OKs == Borr){
			if (OKs == Integer.parseInt(request.getParameter("Total2")))
			{
				AlumnoEstado.setCodigoPersonal(sMatricula);
				AlumnoEstado.setCargaId(sCargaId);
				AlumnoEstado.setBloqueId(sBloqueId);
				if (AlumnoEstadoU.deleteReg(conEnoc, sMatricula, sCargaId, sBloqueId)){
%>					<strong>Se quitaron las materias exitosamente, pero no se pudo cambiar el estado del alumno.</strong>
<%				}
			}
			
			out.print("<div class='alert alert-success'><b>OK...<a class='btn btn-primary' href='"+CURSOOK+"'>Regresar</a></div>");
			//response.sendRedirect(CURSOOK);
%>			
<%-- 			<jsp:forward page = "<%=CURSOOK%>"/> --%>
<%		}else{ 
%>			<strong>Ocurrio un error al intentar quitar una de las materias...</strong>
<%		}
	}
%>
<%@ include file= "../../cierra_enoc.jsp" %>