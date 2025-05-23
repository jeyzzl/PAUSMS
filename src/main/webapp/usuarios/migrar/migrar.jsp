<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="ModuloU" scope="page" class="aca.menu.ModuloUtil" />

<head>
<script type="text/javascript">
	function mostrar(){
		document.forma.submit();
	}	
</script>
</head>
<%	
	String usuario			= request.getParameter("Usuario")==null?"salomon":request.getParameter("Usuario");

	Statement stmtOra		= conEnoc.createStatement();
	ResultSet rsOra			= null;	
	String comando 			= "";
	
	Connection conPos		= null;
	DriverManager.registerDriver (new org.postgresql.Driver());	
	try{
		conPos = DriverManager.getConnection("jdbc:postgresql://172.16.251.11/rigel","postgres","jete17");
	}catch(Exception ex){
		System.out.println("Error:usuarios.migrar|"+ex);
	}
	
	Statement stmtPos		= conPos.createStatement();
	ResultSet rsPos			= null;
	
	
	// Lista de Tablas en Oracle	
	ArrayList<String> lisTablas = new ArrayList<String>();	
	comando = "SELECT TABLE_NAME FROM SYS.DBA_TABLES WHERE OWNER = '"+usuario.toUpperCase()+"' ORDER BY 1";
	rsOra = stmtOra.executeQuery(comando);	
	while (rsOra.next()){		
		lisTablas.add(rsOra.getString("TABLE_NAME"));
	}	
	
	// Mapa de tablas en postgres
	java.util.HashMap<String,String> mapaTablas = new java.util.HashMap<String,String>();
	comando = "SELECT TABLENAME, SCHEMANAME FROM PG_TABLES WHERE SCHEMANAME = '"+usuario.toLowerCase()+"'";
	rsPos = stmtPos.executeQuery(comando);
 	while (rsPos.next()){
 		mapaTablas.put(rsPos.getString("TABLENAME"), rsPos.getString("SCHEMANAME"));
 	}
	
	// Numero de columnas en oracle
	java.util.HashMap<String,String> mapaColOra = new java.util.HashMap<String,String>(); 
 	comando = "SELECT TABLE_NAME, COUNT(COLUMN_NAME) AS TOTAL FROM ALL_TAB_COLUMNS WHERE OWNER = '"+usuario.toUpperCase()+"' GROUP BY TABLE_NAME";
 	rsOra = stmtOra.executeQuery(comando);
 	while (rsOra.next()){
 		mapaColOra.put(rsOra.getString("TABLE_NAME"), rsOra.getString("TOTAL"));
 	} 	
 	
 	// Numero de columnas de postgres
 	java.util.HashMap<String,String> mapaColPos = new java.util.HashMap<String,String>();
 	comando = "SELECT TABLE_NAME, COUNT(*) AS TOTAL FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+usuario+"' GROUP BY TABLE_NAME";
 	rsPos = stmtPos.executeQuery(comando);
 	while (rsPos.next()){
 		mapaColPos.put(rsPos.getString("TABLE_NAME"), rsPos.getString("TOTAL"));
 	}
 	
 	//System.out.println("Contando renglones en Oracle:");
 	// Numero de registros en oracle
 	java.util.HashMap<String,String> mapaRegOra = new java.util.HashMap<String,String>();
 	for (String tabla : lisTablas){
 		comando = "SELECT COUNT(*) AS TOTAL FROM "+usuario+"."+tabla+"";
 	  	rsOra = stmtOra.executeQuery(comando);
 	  	if (rsOra.next()){
 	  		mapaRegOra.put(tabla, rsOra.getString("TOTAL"));
 	  	}
 	}
 	
 	//System.out.println("Contando renglones en Postgres:");
 	// Numero de registros en POSTGRES
  	java.util.HashMap<String,String> mapaRegPos = new java.util.HashMap<String,String>();
  	for (String tabla : lisTablas){
  		//System.out.println("Pos:"+tabla);
  		// Si la tabla existe en postgres cuenta los renglones
  		if (mapaTablas.containsKey(tabla.toLowerCase())){
  			comando = "SELECT COUNT(*) AS TOTAL FROM "+usuario+"."+tabla;
  	  		rsPos = stmtPos.executeQuery(comando);
  	  		if (rsPos.next()){
	  	  		mapaRegPos.put(tabla, rsPos.getString("TOTAL"));
  		  	}
  		}
  		
  	} 	
 	//System.out.println("Size:"+mapaColPos.size()); 	
%>
<div class="container-fluid">
	<h2>Migración Oracle-Postgres<small class="text-muted fs-4">(2017)</small></h2>
	<form name="forma" action="migrar">
	<div class="alert alert-info">
		<select name="Usuario" onchange="javascript:mostrar()">
			<option value="enoc" <%=usuario.equals("enoc")?"selected":""%>>Enoc</option>
			<option value="noe" <%=usuario.equals("noe")?"selected":""%>>Noe</option>
			<option value="daniel" <%=usuario.equals("daniel")?"selected":""%>>Daniel</option>
			<option value="moises" <%=usuario.equals("moises")?"selected":""%>>Moises</option>
			<option value="mateo" <%=usuario.equals("mateo")?"selected":""%>>Mateo</option>
			<option value="aron" <%=usuario.equals("aron")?"selected":""%>>Aron</option>
			<option value="pedro" <%=usuario.equals("pedro")?"selected":""%>>Pedro</option>
			<option value="salomon" <%=usuario.equals("salomon")?"selected":""%>>Salomon</option>
		</select>
	</div>
	</form>
	<table class="table table-condensed table-nohover table-striped">
	<tr>
		<th align="center">#</th>
		<th align="center">Tabla</th>
		<th align="center" width="10%" class="right">Ora.Col.</th>
		<th align="center" width="10%" class="right">Pos.Col.</th>
		<th align="center" width="10%" class="center">Validar</th>
		<th align="center" width="10%" class="right">Ora.Reg.</th>
		<th align="center" width="10%" class="right">Pos.Reg.</th>
		<th align="center" width="10%" class="center">Validar</th>
	</tr>
<%
	int row=0;
	for (String tabla : lisTablas){
		row++;
		
		String colOra = "0";
		if (mapaColOra.containsKey(tabla)){
			colOra = mapaColOra.get(tabla);
		}
		
		String colPos = "0";
		if (mapaColPos.containsKey(tabla.toLowerCase())){
			colPos = mapaColPos.get(tabla.toLowerCase());
		}
		
		String validaCol = "<span class='label label-pill label-success'>OK</span>";
		if (Integer.parseInt(colOra) != Integer.parseInt(colPos)){
			validaCol = "<span class='label label-pill label-warning'>alert</span>";
		}
		
		String regOra = "0";
		if (mapaRegOra.containsKey(tabla)){
			regOra = mapaRegOra.get(tabla);
		}
		
		String regPos = "0";
		if (mapaRegPos.containsKey(tabla)){
			regPos = mapaRegPos.get(tabla);
		}
		
		String validaReg = "<span class='label label-pill label-success'>OK</span>";
		if (Integer.parseInt(regOra) != Integer.parseInt(regPos)){
			validaReg = "<span class='label label-pill label-warning'>alert</span>";
		}
		
%>
	<tr>
		<td align="center"><%=row%></td>
		<td align="center"><%=tabla%></td>
		<td align="center" class="right"><%=colOra%></td>
		<td align="center" class="right"><%=colPos%></td>
		<td align="center" class="center"><%=validaCol%></td>
		<td align="center" class="right"><%=regOra%></td>
		<td align="center" class="right"><%=regPos%></td>
		<td align="center" class="right"><%=validaReg%></td>
	</tr>	
<%	}%>
	</table>
</div>
<%		
	if (stmtOra != null) stmtOra.close();
	if (rsOra != null) rsOra.close();
	if (stmtPos != null) stmtPos.close();
	if (rsPos != null) rsPos.close();
	if (conPos != null) conPos.close();
	

%>
<%@ include file="../../cierra_enoc.jsp"%>