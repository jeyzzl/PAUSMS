<%@page import="aca.cred.credencialDependienteUM"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset			= null;
	Statement stmt			= conEnoc. createStatement();
	String COMANDO			= "";
	
	String s_id_dep			= request.getParameter("f_dep");
	String s_id_empleado	= request.getParameter("f_empleado");
	String nombreEmpleado	= "";
	String s_nomina			= "";
	String depto			= "";
	String empNombre		= "";
	String empApellidos		= "";
	String puesto 			= "";
	
	String s_folio 			= s_id_dep.split("-")[1];
	//String s_folio			= s_id_dep.substring(8,9);
	
	String s_nombre			= request.getParameter("f_nombre").trim();
	String s_apellido		= request.getParameter("f_apellido").trim();
	String s_bday 			= request.getParameter("f_fecha");
	String s_relacion		= request.getParameter("f_relacion");
	String s_cotejado		= request.getParameter("f_cotejado").toUpperCase();
	
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	
	COMANDO = "SELECT ID_DEPENDIENTE, EMP_CLAVE(ID_EMPLEADO) AS  NOMINA, "+
		" EMP_NOMBRE(SUBSTR(ID_DEPENDIENTE,1,7)) AS EMPLEADO,"+ 
		" (SELECT DEPARTAMENTO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = SUBSTR(ID_DEPENDIENTE,1,7)) AS DEPTO,"+
		" (SELECT NOMBRE FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = SUBSTR(ID_DEPENDIENTE,1,7)) AS NOMBRE,"+
		" (SELECT APELLIDOS FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = SUBSTR(ID_DEPENDIENTE,1,7)) AS APELLIDOS,"+
		" (SELECT PUESTO FROM ENOC.EMP_DATOS WHERE ID_EMPLEADO = SUBSTR(ID_DEPENDIENTE,1,7)) AS PUESTO"+
		" FROM ENOC.EMP_DEPENDIENTE WHERE ID_DEPENDIENTE= '"+s_id_dep+"'"; 
	rset = stmt.executeQuery (COMANDO);
	if (rset.next()){		
		s_nomina 		= rset.getString("NOMINA");
		nombreEmpleado	= rset.getString("EMPLEADO");		
		empNombre		= rset.getString("NOMBRE");
		empApellidos	= rset.getString("APELLIDOS");
		depto 			= rset.getString("DEPTO");
		puesto			= rset.getString("PUESTO");
		nAccion			= 2;
	}
	switch (nAccion){
		case 1: { //GRABA DATOS			
			COMANDO = "INSERT INTO ENOC.EMP_DEPENDIENTE "+ 
					"(ID_DEPENDIENTE, ID_EMPLEADO, RELACION, COTEJADO, FOLIO )"+
					"values ('"+s_id_dep+"', to_number('"+s_id_empleado+"')," +
					"'"+s_relacion+"','"+s_cotejado+"',TO_NUMBER('"+s_folio+"','999'))";
			if(stmt.executeUpdate(COMANDO)==1){				
					%><center><strong>Los Datos Fueron Grabados</strong></center><%
			}
			break;
		}
		case 2: { //MODIFICA DATOS
			conEnoc.setAutoCommit(false);
		  	COMANDO = "UPDATE ENOC.EMP_DEPENDIENTE SET " + 
					"RELACION= '"+s_relacion+"', "+
					"COTEJADO='"+s_cotejado+"' "+
					"WHERE ID_DEPENDIENTE = '"+s_id_dep+"'";
		  	// System.out.println(COMANDO);
			if(stmt.executeUpdate(COMANDO)==1){
				COMANDO = "UPDATE ARON.EMPLEADO_DEPENDIENTES SET " +
						"NOMBRE= '"+request.getParameter("s_nombreCompleto")+"',"+
						"BDAY= TO_DATE('"+s_bday+"', 'DD/MM/YYYY') " +
						"WHERE EMPLEADO_ID = '"+s_id_empleado+"' "+
						"AND ID = TO_NUMBER('"+s_folio+"','999')";
				if(stmt.executeUpdate(COMANDO)==1){
					conEnoc.commit();
				}else{
					conEnoc.rollback();
				}
					%>	<center><strong>Los Datos Fueron Modificados</strong></center><%
			}
			conEnoc.setAutoCommit(true);
			break;
		}		
	}
	
	credencialDependienteUM credDep = new credencialDependienteUM();
	credDep.setIdDependiente(s_id_dep);
	credDep.setIdEmpleado(s_nomina);
	credDep.setDepNombre(s_nombre);
	credDep.setDepApellidos(s_apellido);
	credDep.setEmpNombre(empNombre);
	credDep.setEmpApellidos(empApellidos);
	credDep.setRelacion(s_relacion);
	credDep.setDepartamento(depto);
	credDep.setPuesto(puesto);
	credDep.setFecha(s_bday);
	credDep.setCotejado(s_cotejado);
	credDep.setFolio(s_folio);
	if (credDep.existeReg(conEnoc)){
		// Update
		if(credDep.updateReg(conEnoc)){
			System.out.println("Datos Actualizados..!!");
		}
	}else{
		//insert
		if (credDep.insertReg(conEnoc)){
			System.out.println("Datos Insertados..!!");
		}
	}
	
	if (stmt != null) stmt.close();
	if (rset != null) rset.close();
%>
<meta http-equiv='REFRESH' content='0;URL=dato_dep.jsp?f_empleado=<%=s_id_empleado%>&f_folio=<%=s_folio%>'>
<%@ include file= "../../cierra_enoc.jsp" %>
