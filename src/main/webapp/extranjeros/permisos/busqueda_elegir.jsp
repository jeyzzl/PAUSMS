<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
	
    String s_buscar_1       = request.getParameter("f_buscar_1");
    String s_buscar_2       = request.getParameter("f_buscar_2");
    String s_buscar_3       = request.getParameter("f_buscar_3");
	String s_usuario        = request.getParameter("f_usuario1");
    int contador 			= 0;
%>	
<!--   Buscar Alumno -->
<%      COMANDO = "SELECT "+                
                "CODIGO_PERSONAL, "+
                "APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "+
                "FROM ENOC.ALUM_PERSONAL "+ 
				"WHERE "+
                "lower(APELLIDO_PATERNO) like lower('"+s_buscar_2+"%') and "+
                "lower(APELLIDO_MATERNO) like lower('"+s_buscar_3+"%') and "+
                "lower(NOMBRE) like lower('%"+s_buscar_1+"%') "+
				"and PAIS_ID != 91 and PAIS_ID != 0 "+
                "order by 2 ";
	    rset = stmt.executeQuery(COMANDO);
    	if ( rset.next() )
      	{
%>	  
         
<form name="datos1" action="permiso.jsp" method="POST">
  <table style='margin:0 auto;' class="table">
		<tr>
			<td align="CENTER">
				<A ref href="buscar.jsp" class="btn btn-primary">&lsaquo;&lsaquo; Regresar</A><br>
			</td>
		</tr>			
		<tr>
			<th align="CENTER" valign="MIDDLE">
				<b>ELEGIR</b>
			</th>
		</tr>
		<tr>	
			<td align="CENTER" valign="MIDDLE">
                           <select name='f_codigo_personal' size='4'>
<%        do
          {
          contador++;
          out.print("<option value='"+rset.getString("CODIGO_PERSONAL")+"' ");
          if ( contador==1) out.print(" selected" );
          out.print(" > ");
          out.print( rset.getString("NOMBRE"));
          out.print( "("+rset.getString("CODIGO_PERSONAL")+")");
          out.println( "</option> ");
          }while ( rset.next() );
%>
                           </select>
						</td>
					</tr>
					<tr>		
						
      <td align="CENTER" valign="MIDDLE"> 
        <input class="btn btn-primary" type='submit' value='Aceptar'>
						</td>
                   </tr>
                </table>
           </form>
 
 
<%  	if (contador==1){ %>
<p> 
  <script type='text/javascript' language='JavaScript'>
   	document.datos1.submit();			
  </script>
</p>  
<% 		}
    }else  { 
%>
<p align="center"> <b>No existen Extranjeros con los datos proporcionados</b></p> 
<meta http-equiv='REFRESH' content='0; url=buscar.jsp'>
<%	}%>
<%
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<%@ include file= "../../cierra_enoc.jsp" %>