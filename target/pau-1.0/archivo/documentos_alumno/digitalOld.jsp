<%@ page import="java.io.*" %>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="religion" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="estado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="AlumEstadoU" scope="page" class="aca.alumno.EstadoUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="ArchDocAlumnoUtil" scope="page" class="aca.archivo.ArchDocAlumnoUtil"/>
<%
	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	String IdDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
	String usuario			= request.getParameter("usuario");
	
	alumno = alumnoUtil.mapeaRegId(conEnoc,matricula);
	plan = AlumPlanU.mapeaRegId(conEnoc,matricula);
	academico = AcademicoU.mapeaRegId(conEnoc,matricula);	
	
	Statement stmt 			= conEnoc.createStatement();	
	ResultSet rset 			= null;	
	String fecha			= ""; 
	
	ArrayList<aca.archivo.ArchDocAlumno> lisDocu 		= ArchDocAlumnoUtil.getListOne(conEnoc, matricula, "ORDER BY 2");
%>

<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
	}
</STYLE>
<SCRIPT>
	function openwindow(open){
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width=800, height=600, top=0, left=0")
	}
	function eliminar(doc,hoja){
		if (confirm("Se eliminara este documento digitalizado. ¿Estas seguro?")){
			location.href= "digital.jsp?accion=eliminar&f_documento="+doc+"&ver=0&nhoja="+hoja;
		}
	}
</SCRIPT>
<body class="tabbox">
<div class="container-fluid">
	<h3>Documentos del Alumno <small>( <%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%> - <%=alumnoUtil.getCarrera(conEnoc,matricula)%> - <%=plan.getPlanId()%>)</small></h3>
	<div class="alert alert-info">
		<a href="documentos" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<table class="table table-condensed" style="width:100%">
				
<%
	String s_matricula 		= "X";
	String s_nombre			= "X";
	String s_iddocumento	= "X";
	String s_documento		= "X";
	String s_nhoja			= "";
	
	// coneccion a ATLAS
	DriverManager.registerDriver (new org.postgresql.Driver());
	Connection conn3		= DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
	Statement stmt3			= conn3.createStatement();
	PreparedStatement ps	= null;	
	ResultSet rset3			= null;

	String idDoc			= request.getParameter("f_documento");
	String accion			= request.getParameter("accion")==null?"":request.getParameter("accion");
	String nh				= request.getParameter("nhoja");
	String COMANDO 			= "";
			
	String imagen			= aca.pg.archivo.ArchDocAlum.getImagenHoja(conn3, matricula, idDoc, nh);
	// Verifica si hay una imagen almacenada en la hoja
	
	
	if (accion.equals("eliminar")){		
		
		conn3.setAutoCommit(false);
		if (!imagen.equals("0")){	 
			COMANDO = "SELECT LO_UNLINK(IMAGEN) FROM ARCH_DOCALUM WHERE MATRICULA = ? "+ 
				"AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
			ps = conn3.prepareStatement(COMANDO);
			ps.setString(1, matricula);
			ps.setString(2, idDoc);
			ps.setString(3, nh);
			rset3 = ps.executeQuery();
			if (rset3.next()){
				COMANDO = 	"DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? "+ 
				"AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
				ps = conn3.prepareStatement(COMANDO);
				ps.setString(1, matricula);
				ps.setString(2, idDoc);
				ps.setString(3, nh);
				if (ps.executeUpdate()==1){
					conn3.commit();
				}else{
					conn3.rollback();
				}
			}
		}else{
			COMANDO = 	"DELETE FROM ARCH_DOCALUM WHERE MATRICULA = ? "+ 
					"AND IDDOCUMENTO = TO_NUMBER(?,'999') AND HOJA = TO_NUMBER(?,'99')";
			ps = conn3.prepareStatement(COMANDO);
			ps.setString(1, matricula);
			ps.setString(2, idDoc);
			ps.setString(3, nh);
			if (ps.executeUpdate()==1){
				conn3.commit();
			}else{
				conn3.rollback();
			}
		}
		
		conn3.setAutoCommit(true);
	}
	
	int n_col = 1,numDocumentos=0;
	 COMANDO = 	"SELECT DA.MATRICULA AS MATRICULA, "+
				"COALESCE(DA.IDDOCUMENTO,0) AS IDDOCUMENTO, "+
				"DOC.DESCRIPCION AS DOCUMENTO, "+
				"DA.IDSTATUS AS IDSTATUS, "+
				"DS.DESCRIPCION AS STATUS, "+
				"COALESCE(TO_CHAR(DA.FECHA,'DD/MM/YYYY'),'01/01/1900') AS FECHA, "+
				"COALESCE(DA.CANTIDAD, 0) AS CANTIDAD, "+
				"COALESCE(DA.USUARIO, 'VACIO') AS USUARIO "+
				"FROM ENOC.ARCH_DOCALUM DA, ENOC.ARCH_DOCUMENTOS DOC, ENOC.ARCH_STATUS DS "+ 
				"WHERE MATRICULA = '"+matricula+"' AND "+
				"DOC.IDDOCUMENTO = DA.IDDOCUMENTO AND "+
				"DS.IDSTATUS = DA.IDSTATUS ORDER BY DA.IDDOCUMENTO";
	 
	rset = stmt.executeQuery(COMANDO);
	while(rset.next()){
		s_documento 	= rset.getString("DOCUMENTO");
		s_iddocumento 	= rset.getString("IDDOCUMENTO");
		usuario			= rset.getString("USUARIO");
		fecha			= rset.getString("FECHA");
		
		COMANDO = "SELECT IMAGEN, HOJA FROM ARCH_DOCALUM "+ 
				"WHERE MATRICULA = '"+matricula+"' AND "+
				"IDDOCUMENTO = TO_NUMBER('"+s_iddocumento+"','999') "+
				"ORDER BY HOJA";
		rset3 = stmt3.executeQuery(COMANDO);
		while(rset3.next()){
			numDocumentos++;
			s_nhoja = rset3.getString("hoja");
			if ( n_col % 4 == 1){ 
%>				<tr>
<%			}
%>  				<td>
						<a target="_self" href="digital.jsp?f_documento=<%=s_iddocumento%>&usuario=<%=usuario%>&ver=0&nhoja=<%=s_nhoja%>"> 
		      				<%=s_documento+" "+s_nhoja%>
		      			</a>
		      			<i class="fas fa-trash-alt" title="Eliminar" onclick="eliminar('<%=s_iddocumento%>','<%=s_nhoja%>');"></i>
					</td>
<%			if ( n_col % 4 == 0){ 
%>				</tr>				
<%			}
			n_col++;
		}
	}
%>
	</table>			
		
<%
	
	s_iddocumento			= request.getParameter("f_documento");
	String Ver				= request.getParameter("ver");
	s_nhoja					= request.getParameter("nhoja");
	s_documento				= "";	
	String s_origen 		= null;
	String f_insert			= "";
	String f_update			= "";

	if (s_iddocumento==null || accion.equals("eliminar")){
%>  				<tr>
					    <td align="center" valign="top">
						<b><font face="Trebuchet MS" size="3">&nbsp; 
<%						 	if (numDocumentos>0) out.print("Elija un documento");
							else out.print("Aun no digitalizan tus documentos");
%>							
						</font></b></td>
					</tr>
<%  }else{

		String s_width = " ";
	    File Comp;

		s_documento = "Not Found";
		s_documento = aca.archivo.ArchDocumentosUtil.getDescripcion(conEnoc,s_iddocumento);
		
		COMANDO = "SELECT"
		+ " IMAGEN, FUENTE, ORIGEN, USUARIO,"
		+ " TO_CHAR(F_INSERT,'DD/MM/YYYY') AS F_I,"
		+ " TO_CHAR(F_UPDATE,'DD/MM/YYYY') AS F_U"
		+ " FROM ARCH_DOCALUM"
		+ " WHERE MATRICULA = '"+matricula+"'"
		+ " AND IDDOCUMENTO = TO_NUMBER('"+s_iddocumento+"','999')"
		+ " AND HOJA = "+s_nhoja;
		
		
		rset3 = stmt3.executeQuery(COMANDO);
		if (rset3.next()){
			usuario 	= rset3.getString("USUARIO");
			
			s_origen 	= rset3.getString("ORIGEN");
			f_insert 	= rset3.getString("F_I");
			f_update 	= rset3.getString("F_U");
			
			if (s_origen==null) s_origen = "O";
			if (s_origen.equals("C")){
			    s_origen = "Tomado de una copia - Usuario que digitalizo: " +aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, usuario, "NOMBRE")+" - Creado: "+f_insert;
			}else{
				s_origen = "Tomado del original - Usuario que digitalizo: " +aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, usuario, "NOMBRE")+" - Creado: "+f_insert;			
			}
			if (!f_insert.equals(f_update)){ s_origen = s_origen+" - Modificado:"+f_update; }
		}	
%>
	<table>
	<tr class="oculto">
		<td colspan="4" width="100%" height="18">	  	
	  		<img src="../../portales/alumno/img/exp.gif" class="button" onClick="javascript:openwindow('foto.jsp?matricula=<%=matricula %>&documento=<%=s_iddocumento %>&hoja=<%=s_nhoja %>')" width="16" height="17">
	  		<font size="1" face="Verdana, Arial, Helvetica, sans-serif"><b>&nbsp;&nbsp;&nbsp;&nbsp;<%=s_origen%></b></font>
		</td>
	</tr>
	<tr>
		<td width="100%" colspan="4">
			<img name="iDoc" id="iDoc" src="foto.jsp?matricula=<%=matricula %>&documento=<%=s_iddocumento %>&hoja=<%=s_nhoja %>" width="730" nosave>
		</td>
  	</tr>
	</table>
<%	} // fin de visualizar imagen en pantalla
	if (rset3!=null) rset3.close();
	if (stmt3!=null) stmt3.close();
	if (rset!= null) rset.close();
	if (stmt!= null) stmt.close();
	if (conn3!=null) conn3.close();
%>
<%@ include file= "../../cierra_enoc.jsp" %>