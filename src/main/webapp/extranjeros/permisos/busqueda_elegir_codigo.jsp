<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset			= null;
	Statement stmt	 		= conEnoc.createStatement();
	String COMANDO 			= "";
	
	String s_usuario       = request.getParameter("f_usuario");
    String s_codigo       	= request.getParameter("f_codigo");
    int contador 			= 0;	
%>


<!--   Buscar Alumno mediante su codigo postulante en datos_academicos -->
<%      COMANDO = "SELECT "+ 
                "CODIGO_PERSONAL, "+
                "APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
                "FROM ENOC.ALUM_PERSONAL "+ 
				"WHERE "+
				"CODIGO_PERSONAL = '"+s_codigo+"' "+
				"and PAIS_ID != 91 and PAIS_ID != 0 ";
	    rset = stmt.executeQuery(COMANDO);
    	if ( rset.next() ){
%>	  
         
<form name="datos1" action="permiso.jsp" method="POST">
  <table style='margin:0 auto;' class="table">
                   <tr>
                        <th align="CENTER" valign="MIDDLE">
					   		<b>ELEGIR</b>
						</th>
					</tr>
					<tr>	
						<td align="CENTER" valign="MIDDLE">
                           <select name='f_codigo_personal' size='2'>
						   		
<%
				contador++;
				out.print("<option value='"+rset.getString("CODIGO_PERSONAL")+"' ");
				out.print(" selected" );
				out.print(" > ");
				out.print( rset.getString("nombre"));
				out.print( "("+rset.getString("CODIGO_PERSONAL")+")");
				out.println( "</option> ");     
%>
                           </select>
						</td>
					</tr>
					<tr>
						
      <td align="CENTER" valign="MIDDLE"> 
        <input type='submit' value='Aceptar'>
						</td>
                   </tr>
                </table>
           </form>
          <script type='text/javascript' language='JavaScript'>
          	document.datos1.submit();
          </script>
<%      
      }else{	  
%> 
		<center><b>El alumno no es Extrajero</b></center>

<%
    }
%>

<!-- fin de estructura -->
<%
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0; url=buscar.jsp'>
<%@ include file= "../../cierra_enoc.jsp" %>

	