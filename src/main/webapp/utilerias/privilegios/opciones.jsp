<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	ResultSet rset		= null;
	Statement stmt	 	= conEnoc.createStatement();
	String COMANDO 		= "";

	ResultSet rset2		= null;
	Statement stmt2	 	= conEnoc.createStatement();

	// Variables 
	String moduloid		= "X";
	String opcionid		= "X";
	String nombre		= "X";	
	String mapa			= "X";	
	String usuarios		= "X";
	String usuariosNew	= "X";
	String token		= "X";
	String modifica		= "X";
	int ntoken = 0;
%>
<div class="container-fluid">
<h1>Traspaso de Privilegios</h1>
<div class="alert alert-info"></div>
<table style="width:90%"  >
</table>  
<table style="width:90%"  class="tabla">
  <tr> 
    <th width="6%">Modulo</th>
	<th width="7%"><spring:message code="aca.Operacion"/></th>
	<th width="19%">Nombe</th>
	<th width="68%">Derechos</th>
	<th width="4%">Up.</th>
  </tr>
<%/*			
	COMANDO = "SELECT MODULO_ID, OPCION_ID, NOMBRE_OPCION, COALESCE(MAPA,'X') AS MAPA "+
		"FROM ENOC.MODULO_OPCION WHERE OPCION_ID = '100' "+ 
		"ORDER BY 1,2";
    rset = stmt.executeQuery(COMANDO);
	while(rset.next()){ 
		moduloid	= rset.getString("MODULO_ID");
		opcionid	= rset.getString("OPCION_ID");
		nombre 		= rset.getString("NOMBRE_OPCION");
		mapa 		= rset.getString("MAPA");
		if (!mapa.equals("X")){
			COMANDO = "SELECT USUARIOS FROM NOE.SIST_ACCESOS WHERE ID_ACCESOS= '"+mapa+"'"; 
			rset2 = stmt2.executeQuery(COMANDO);
			if (rset2.next()){
				mapa = rset2.getString("USUARIOS");
				// Separar los tokens
				java.util.StringTokenizer tokenUsuario = new java.util.StringTokenizer(mapa, "-");
				ntoken = (int) tokenUsuario.countTokens(); 

				usuariosNew = "-";
				for (int i=0;i<ntoken;i++){
					token = tokenUsuario.nextToken();
					COMANDO = "SELECT CODIGO_PERSONAL FROM NOE.DATOS_PERSONALES WHERE LOGIN ='"+token+"'";
					rset2 = stmt2.executeQuery(COMANDO);
					if (rset2.next()){
						usuariosNew = usuariosNew+rset2.getString("CODIGO_PERSONAL")+"-";
					}else{
						usuariosNew = usuariosNew+token+"-";
					}				
				}        		
			}else{
				usuariosNew = "-9800308-9800058-";
			}
		}else{
			usuariosNew = "-9800308-9800058-";
		}
		COMANDO = "UPDATE ENOC.MODULO_OPCION SET USUARIOS = '"+usuariosNew+"' "+ 
				"WHERE OPCION_ID = '"+opcionid+"' "+
				"AND MODULO_ID = '"+moduloid+"'";
		if (stmt2.executeUpdate(COMANDO)==1){
			modifica = "SI";
		}else{
			modifica = "NO";
		}
*/		
%>
  <tr> 
    <td width="6%"><font size="1" face="Arial"><%=moduloid%></font></td>
	<td width="7%"><font size="1" face="Arial"><%=opcionid%></font></td>
	<td width="19%"><font size="1" face="Arial"><%=nombre%></font></td>
	<td width="68%"><font size="1" face="Arial"><%=usuariosNew%></font></td>
	<td width="4%"><font size="1" face="Arial"><%=modifica%></font></td>
  </tr>     
<%	/*
	} // fin de while */
%>
</table>
<%
	if (rset!=null){rset.close();}
	if (stmt!=null){stmt.close();}
	if (rset2!=null){rset2.close();}
	if (stmt2!=null){stmt2.close();}
%>
<%@ include file= "../../cierra_enoc.jsp" %>