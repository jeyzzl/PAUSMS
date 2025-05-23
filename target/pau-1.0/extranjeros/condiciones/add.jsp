<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	ResultSet rset				= null;
	Statement stmt	 			= conEnoc.createStatement();
	String COMANDO 				= "";
	
	String s_grupo				= request.getParameter("f_grupo");
  	String s_documento			= request.getParameter("f_documento");
    String s_fecha				= request.getParameter("f_fecha");

	   COMANDO = "Select grupo, iddocumento from ENOC.leg_condiciones "+ 
	              "where grupo = '"+s_grupo+"' "+
				  "and iddocumento = "+s_documento+" ";
				  
	    rset = stmt.executeQuery (COMANDO);	
		if (rset.next()){		
		
			COMANDO = "update ENOC.leg_condiciones " + 
					"set valida_fecha = to_char('"+s_fecha+"') " +
					"where grupo = '"+s_grupo+"' " +
					"and iddocumento = '"+s_documento+"' ";
		    rset = stmt.executeQuery (COMANDO);
			if(rset.next()){
%>
			  <center><b>Los Datos fueron Actualizados con Exito.</b></center>

<%			}else{		
%>
			<center><b>&iexcl;&iexcl;..No se realizo la operacion..!!</b></center>
<%
			} 
%>

<%		}else{

		/*	System.out.println("Entro a insertar");
			System.out.println(s_grupo);
			System.out.println(s_documento);
			System.out.println(s_fecha);
		*/	
			
		    COMANDO ="Insert into ENOC.leg_condiciones( "+ 
	              	 "grupo, iddocumento, valida_fecha) " +
					 " VALUES(to_number('"+
					 s_grupo+"'), to_number('"+
					 s_documento+"'),to_char('"+
					 s_fecha+"'))";
	      rset = stmt.executeQuery (COMANDO);
		  if(rset.next()){
%>
			<center><b>Los Datos fueron insertados con exito.</b></center>

<%	  	  }else{
%>
		<center><b> &iexcl;&iexcl;..No se realizo la operacion..!!</b> </center>
<%		  }
	    }
		
		if (stmt != null) stmt.close();
		if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0; url=grabar.jsp?f_grupo=<%=s_grupo%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
