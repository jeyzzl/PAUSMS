<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	ResultSet rset			= null;
	Statement stmt			= conEnoc.createStatement();
	String COMANDO			= "";
	String paso 			= request.getParameter("paso");
	if (paso==null) paso 	= "1";
	if (paso.equals("1")){
%>	
	<div class="container-fluid">
	<h2>Traspasar alumnos inscritos</h2>
	<div class="alert alert-info"></div>
		<table style="width:60%" class="table table-sm table-bordered">
			<tr><td><form action="traspaso?paso=2" name="form" method="post">
				<table style="width:100%">
          			<tr> 
            			<th style="text-align: center">Se traspasaran alumnos inscritos a la lista de credenciales</th>
          			</tr>
          			<tr><td align="center"><div><br><input class="btn btn-primary" type="submit" name="bRespaldar2" value="   Traspasar  "></br></div></td>
          			</tr>
        			<tr> 
            	    <th style="text-align: center"></br>Incluyendo alumnos sin foto y con datos no cotejados.</br></th>
          		   </tr>
        		</table>
			</form></td></tr>
		</table>	

<% }else{
		stmt.executeUpdate("DELETE FROM ENOC.ALUM_CREDENCIAL"); 
		COMANDO = "INSERT INTO ENOC.ALUM_CREDENCIAL(CODIGO_PERSONAL, NOMBRES, APELLIDOS, CARRERA, COTEJADO)  " + 
					"SELECT DISTINCT(I.CODIGO_PERSONAL), AP.NOMBRE, AP.APELLIDO_PATERNO||' '||AP.APELLIDO_MATERNO, " +
					"UPPER(ENOC.NOMBRE_CARRERA(I.CARRERA_ID)), AP.COTEJADO FROM ENOC.INSCRITOS I, ALUM_PERSONAL AP " + 
					"WHERE AP.CODIGO_PERSONAL = I.CODIGO_PERSONAL "+
					"ORDER BY 1";
		stmt.executeUpdate(COMANDO);
%>
		<br><br><br><br><br><br>
		<table style="width:60%"   class="tabla">
			<tr><td>
				<table style="width:100%">
        <tr> 
          <th>Todos los alumnos fueron transferidos. </th>
        </tr>
      </table>
		  	</td></tr>
		</table>
	</div>
<% }	
	try { rset.close(); } catch (Exception ignore) { }
	try { stmt.close(); } catch (Exception ignore) { }	
%>
<%@ include file= "../../cierra_enoc.jsp" %>