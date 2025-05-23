<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%	
	ResultSet rset			= null;
	ResultSet rset2			= null;
	Statement stmt			= conEnoc. createStatement();
	Statement stmt2			= conEnoc. createStatement();
	String COMANDO			= "";
	
	String s_id				= request.getParameter("f_id");
	String s_id_empleado	= request.getParameter("f_empleado");
	String s_nombre			= request.getParameter("f_nombre");
	String nombreCredencial = request.getParameter("nombreCredencial");
	String s_apellidoP 		= request.getParameter("f_apellidoP");
	String s_apellidoM  	= request.getParameter("f_apellidoM");
	String s_puesto 		= request.getParameter("f_puesto");
	String s_depto 			= request.getParameter("f_depto");
	String s_status			= request.getParameter("f_activo");
	String s_coteja			= request.getParameter("f_cotejado");
	String s_tipocred		= request.getParameter("f_tipocred");
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	
	String apellidos = s_apellidoP+" "+s_apellidoM;
// System.out.println("Datos:"+s_id_empleado+":"+s_id);
	COMANDO = "SELECT ID_EMPLEADO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = '"+s_id_empleado+"' "; 
	rset = stmt.executeQuery (COMANDO);
	if (rset.next()){		
		nAccion = 2;
	}
	
		switch (nAccion){
			case 1: { //GRABA DATOS
				conEnoc.setAutoCommit(false);				
				COMANDO =	"INSERT INTO ENOC.EMP_DATOS "+ 
							"(ID, ID_EMPLEADO, PUESTO, DEPARTAMENTO, STATUS, COTEJADO, NOMBRE, APELLIDOS, TIPOCRED) "+
							"values ( to_number('"+s_id+"'),'"+s_id_empleado+"','"+s_puesto+"','"+s_depto+"'," +
							"'"+s_status+"', '"+s_coteja+"', '"+nombreCredencial+"', '"+apellidos+"','"+s_tipocred+"')";

				if (stmt.executeUpdate(COMANDO) ==1){
					COMANDO = "UPDATE ARON.EMPLEADO SET "+
							"NOMBRE = '"+s_nombre+"', APPATERNO = '"+s_apellidoP+"', "+
							"APMATERNO = '"+s_apellidoM+"' "+
							"WHERE CLAVE = '"+s_id_empleado+"'";
					if (stmt2.executeUpdate(COMANDO) == 1){ conEnoc.commit();
					%><center><strong>Los Datos Fueron Grabados</strong></center>
<%
					}else{
						conEnoc.rollback();
					}
				}
				conEnoc.setAutoCommit(true);
				break;
			}
			case 2: { //MODIFICA DATOS				
				COMANDO =	"UPDATE ENOC.EMP_DATOS SET "+ 
							"PUESTO= '"+s_puesto+"', DEPARTAMENTO= '"+s_depto+"', STATUS= '"+s_status+"', COTEJADO='"+s_coteja+
							"', NOMBRE='"+nombreCredencial+"', APELLIDOS='"+apellidos+"', TIPOCRED='"+s_tipocred+"' " +
	  						"WHERE ID_EMPLEADO = '"+s_id_empleado+"'";
				rset = stmt.executeQuery(COMANDO);
				COMANDO = 	"UPDATE ARON.EMPLEADO SET "+
							"NOMBRE = '"+s_nombre+"', APPATERNO = '"+s_apellidoP+"', "+
							"APMATERNO = '"+s_apellidoM+"' "+
							"WHERE CLAVE = '"+s_id_empleado+"'";
				rset2 = stmt2.executeQuery(COMANDO);
				if(rset.next() && rset2.next()){
					%>	<center><strong>Los Datos Fueron Modificados</strong></center>	<%
				} 
				break;
			}
		}
		
		if (stmt != null) stmt.close();
		if (rset != null) rset.close();
		if (stmt2 != null) stmt2.close();
		if (rset2 != null) rset2.close();		
%>
<meta http-equiv='REFRESH' content='0;URL=dato_emp?ver=1&f_codigo_personal=<%=s_id_empleado%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
