<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<jsp:useBean id="kardex" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="log" scope="page" class="aca.log.LogOperacion"/>
<jsp:useBean id="logU" scope="page" class="aca.log.LogOperacionUtil"/>
<script type="text/javascript">
	
	function Modificar(){
		if(confirm("Estas seguro de cambiar esta nota!")==true){
			document.frmkardex.Accion.value="2";
			document.frmkardex.submit();
		}
	}	
</script>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String cursoCargaId		= request.getParameter("cursoCargaId");
	String nombreMateria	= request.getParameter("materia");
	String plan				= request.getParameter("plan");
	
	String nota				= "";
	String fechaNota		= "";
	String notaExtra		= "";
	String fechaExtra		= "";
	String comentario		= "";
	String tipoCal			= "";	 
	
	kardex.setCodigoPersonal(codigoAlumno);
	kardex.setCursoCargaId(cursoCargaId);
	if (kardex.existeReg(conEnoc)){
		kardex.mapeaRegId(conEnoc,kardex.getCodigoPersonal(),kardex.getCursoCargaId());
		
		// Limpiar valores de atributos
		if (kardex.getNota()==null || kardex.getNota().equals("null")) nota = "";
		else nota = kardex.getNota();
		if (kardex.getFNota()==null || kardex.getFNota().equals("null")) fechaNota = aca.util.Fecha.getHoy();
		else fechaNota = kardex.getFNota();
		if (kardex.getNotaExtra()==null || kardex.getNotaExtra().equals("null")) notaExtra = "";
		else notaExtra = kardex.getNotaExtra();
		if (kardex.getFExtra()==null || kardex.getFExtra().equals("null")) fechaExtra = kardex.getNotaExtra().equals("0")?"":aca.util.Fecha.getHoy();
		else fechaExtra = kardex.getFExtra();
		if (kardex.getComentario()==null || kardex.getComentario().equals("null")) comentario = "";
		else comentario = kardex.getComentario();
		tipoCal = kardex.getTipoCalId();
	}
	int nAccion 			= 1;
	if (request.getParameter("Accion")!=null)
		nAccion				= Integer.parseInt(request.getParameter("Accion"));

	String sResultado		= "";

	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Modificación de Nota";
			break;
		}		
		case 2: { // Modificar
			conEnoc.setAutoCommit(false);
			/********* LOG ****/
			String datos = "CursoCargaId:"+cursoCargaId+", CodigoAlumno:"+codigoAlumno+",Nota:"+kardex.getNota()+", Extra: "+kardex.getNotaExtra()+" NewNota:"+request.getParameter("nota")+" NewExtra:"+request.getParameter("notaExtra");
			log.setDatos(datos);
			log.setIp(request.getRemoteAddr());
			log.setUsuario(codigoPersonal);
			log.setTabla("KRDX_CURSO_ACT");
			
			nota 		= request.getParameter("nota");
			fechaNota	= request.getParameter("fechaNota");
			notaExtra	= request.getParameter("notaExtra");
			fechaExtra	= request.getParameter("fechaExtra");
			tipoCal		= request.getParameter("tipoCal");
			comentario	= request.getParameter("comentario");
			
			kardex.setNota(nota);
			kardex.setFNota(fechaNota);
			kardex.setNotaExtra(notaExtra);
			kardex.setFExtra(fechaExtra);
			kardex.setTipoCalId(tipoCal);
			kardex.setComentario(comentario);
			kardex.setCorreccion("S");
			
			if (kardex.existeReg(conEnoc)){
				if (kardex.updateReg(conEnoc)){
					sResultado = "Nota Modificada: "+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
					log.setOperacion("update");
					logU.insertReg(conEnoc, log);
					conEnoc.commit();
				}else{
					sResultado = "No Grabó: "+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
				}
			}else{
				sResultado = "No existe el registro: "+kardex.getCodigoPersonal()+"-"+kardex.getCursoCargaId();
			}
			conEnoc.setAutoCommit(true);
			break;
		}
	}
%>
<table style="width:77%" border="1" align="center"  >
<tr>
  <td align="center">
    <font size="2">
	  <strong>Alumno: <%=codigoAlumno%> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc,codigoAlumno,"NOMBRE")%> - <%=plan%><br>
	  <em><%=nombreMateria%> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc,aca.carga.CargaGrupoUtil.getCodigoPersonal(conEnoc,cursoCargaId),"NOMBRE")%></em>
	  </strong>
	</font>
  </td>
</tr>
</table>
<form action="grabar.jsp" method="post" name="frmkardex" target="_self">
<input type="hidden" name="Accion">
<table style="width:55%" border="1" align="center"   bordercolor="#000000">
  <tr>
  	  <th align="center"><font size="2">Correcci&oacute;n de Notas </font><font size="2"> [ <a href="notas.jsp">Materias del alumno</a> 
        ]</font></th>
  </tr>
  <tr>
    <td>
	  <table style="width:100%" >
          <tr> 
            <td width="18%"><strong>Nota Ord.:</strong></td>
            <td width="82%"><input name="nota" type="text" class="text" id="nota" size="3" maxlength="3" value="<%=nota%>"></td>
			
          </tr>
          <tr> 
            <td><strong>F. Eval.:</strong></td>
            <td><input name="fechaNota" type="text" class="text" id="fechaNota" value="<%=fechaNota%>" size="11" maxlength="10"> 
            (DD/MM/AAAA)</td>
          </tr>
		  <tr> 
            <td><strong>Nota Extra :</strong></td>
            <td><input name="notaExtra" type="text" class="text" id="notaExtra" value="<%=notaExtra%>" size="3" maxlength="3"></td>
          </tr>
		  <tr> 
            <td><strong>F. Extra :</strong></td>
            <td><input name="fechaExtra" type="text" class="text" id="fechaExtra" value="<%=fechaExtra%>" size="11" maxlength="10"> 
            (DD/MM/AAAA)</td>
          </tr>
		  <tr> 
            <td><strong>Tipo Cal.:</strong></td>
            <td><select name="tipoCal" id="tipoCal">
			    <option value="1" <%if (tipoCal.equals("1")) out.println("selected");%>>AC</option>
			    <option value="2" <%if (tipoCal.equals("2")) out.println("selected");%>>NA</option>
			    <option value="4" <%if (tipoCal.equals("4")) out.println("selected");%>>RA</option>
			    <option value="6" <%if (tipoCal.equals("6")) out.println("selected");%>>CD</option>
                </select>			</td>
          </tr> 
		  <tr> 
            <td><strong><spring:message code="aca.Comentario"/>:</strong></td>
            <td><input name="comentario" type="text" class="text" id="comentario" value="<%=comentario%>" size="50" maxlength="70"></td>
          </tr>
          <tr> 
            <td colspan="2" align="center"><%=sResultado%></td>
          </tr>
          <tr>
            <th colspan="2" align="center"> &nbsp;<a href="javascript:Modificar()"><spring:message code="aca.Grabar"/></a>
              <input name="cursoCargaId" type="hidden" id="cursoCargaId" value="<%=cursoCargaId%>">
              <input name="materia" type="hidden" id="materia" value="<%=nombreMateria%>">
		    <input name="plan" type="hidden" id="plan" value="<%=plan%>">			</th>
          </tr>
        </table>
	</td>
  </tr>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>