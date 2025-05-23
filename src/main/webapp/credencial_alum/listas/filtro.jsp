<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	ResultSet rset			= null;
	Statement stmt			= conEnoc.createStatement();
	String COMANDO 			= "";
	String paso 			= request.getParameter("paso");
	if (paso==null) paso 	= "1";
	if (paso.equals("1")){
%>	
		<div class="container-fluid">
		<table style="margin: 0 auto;  width:40%"   class="table table-sm table-bordered">
			<tr>
				<td><form action="filtro?paso=2" name="form" method="post">
					<table style="width:100%">
         				<tr align="center"> 
            				<th>Se traspasaran solo alumnos inscritos con foto y con datos cotejados </th>
        				</tr>
                   		<tr> 
            				<td height="26"><div align="center"><input class="btn btn-primary" type="submit" name="bRespaldar2" value="Traspasar"></div></td>
          				</tr>
        			</table>
				</form></td>
			</tr>
		</table>	
<% }else{		
		stmt.executeUpdate("DELETE FROM ENOC.ALUM_CREDENCIAL"); 
		COMANDO = "INSERT INTO ENOC.ALUM_CREDENCIAL(CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, COTEJADO) " + 
				  "SELECT DISTINCT(I.CODIGO_PERSONAL), AP.NOMBRE, AP.APELLIDO_PATERNO||' '||AP.APELLIDO_MATERNO, " +
				  "UPPER(ENOC.NOMBRE_CARRERA(I.CARRERA_ID)), AP.COTEJADO FROM ENOC.INSCRITOS I, ALUM_PERSONAL AP " + 
				  "WHERE AP.CODIGO_PERSONAL = I.CODIGO_PERSONAL AND AP.COTEJADO = 'S' ORDER BY 1";
		stmt.executeUpdate(COMANDO);
%>
		<br><br>
		<table style="margin: 0 auto;  width:600">
			<tr>
				<td>
					<table style="width:100%">
        				<tr><td>&nbsp;</td></tr>
            			<tr> 
          					<th>Todos los alumnos con foto y cotejados han sido transferidos</th>
        				</tr>
						<tr><td>&nbsp;</td></tr>
		   			</table>
		 		</td>
			</tr>
		</table>	
<%	}	 
	try { rset.close(); } catch (Exception ignore) { }
	try { stmt.close(); } catch (Exception ignore) { }
%>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>