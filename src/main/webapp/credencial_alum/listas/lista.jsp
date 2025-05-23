<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%	
	ResultSet rset 			= null;
	Statement stmt			= conEnoc.createStatement();
	String COMANDO			= "";
	String mensaje 			= "";
	String ver 				= request.getParameter("ver");
	String sModulo		    = request.getParameter("moduloId"); 
    String sCarpeta    		= request.getParameter("carpeta");
	
	int i		= 0;
	if (ver==null) ver = "1";
	if (ver.equals("1")) mensaje = "SIN FOTO";
	if (ver.equals("2")) mensaje = "SIN COTEJAR";
 	if (ver.equals("3")) mensaje = "SIN FOTO Y SIN COTEJAR";
%>
<div class="container-fluid">
<h1>Lista de alumnos sin foto</h1>
<div class="alert alert-info"></div>
<table  cellpadding="1" cellspacing="1" class="table table-striped table-bordered" >
  <tr class="table-info"> 
    <th width="10%"> Matricula</th>
    <th width="45%"> Nombre</th>
    <th width="45%"> Carrera</th>
  </tr>
  <%
	if (ver.equals("1")){
		COMANDO = "SELECT DISTINCT(I.CODIGO_PERSONAL), AP.NOMBRE||' '||AP.APELLIDO_PATERNO||' '||AP.APELLIDO_MATERNO, " +
					"UPPER(ENOC.NOMBRE_CARRERA(I.CARRERA_ID)) FROM ENOC.INSCRITOS I, ENOC.ALUM_PERSONAL AP " + 
					"WHERE AP.CODIGO_PERSONAL = I.CODIGO_PERSONAL ORDER BY 3, 1 ";
		rset = stmt.executeQuery(COMANDO);
	}else if (ver.equals("2")){
		COMANDO = "SELECT DISTINCT(I.CODIGO_PERSONAL), AP.NOMBRE||' '||AP.APELLIDO_PATERNO||' '||AP.APELLIDO_MATERNO, " +
					"UPPER(ENOC.NOMBRE_CARRERA(I.CARRERA_ID)) FROM ENOC.INSCRITOS I, ENOC.ALUM_PERSONAL AP " + 
					"WHERE AP.CODIGO_PERSONAL = I.CODIGO_PERSONAL AND AP.COTEJADO <> 'S' ORDER BY 3, 1 ";
		rset = stmt.executeQuery(COMANDO);
	}else if (ver.equals("3")){
		COMANDO = "SELECT DISTINCT(I.CODIGO_PERSONAL), AP.NOMBRE||' '||AP.APELLIDO_PATERNO||' '||AP.APELLIDO_MATERNO, " +
					"UPPER(ENOC.NOMBRE_CARRERA(I.CARRERA_ID)) FROM ENOC.INSCRITOS I, ENOC.ALUM_PERSONAL AP " + 
					"WHERE AP.CODIGO_PERSONAL = I.CODIGO_PERSONAL AND AP.COTEJADO <> 'S'  ORDER BY 3, 1 ";
		rset = stmt.executeQuery(COMANDO);	
	}
	while (rset.next()){
		i++;
%>
	  <tr> 
	    <td align="center"><%=rset.getString(1)%></td>
	    <td><%=rset.getString(2)%></td>
	    <td><%=rset.getString(3)%></td>
	  </tr>
<%	}	
	try { rset.close(); } catch (Exception ignore) { }
	try { stmt.close(); } catch (Exception ignore) { }
%>
</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>