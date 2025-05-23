<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<property name="hibernate.jdbc.use_streams_for_binary" value="false"/> <!-- true for bytea false for OID-->

<%@ page import="java.util.*" %>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.zip.*" %>
<%@ page import="java.lang.Object.*" %>
<%@ page import="org.postgresql.largeobject.*" %>
<%@ page import="java.sql.*" %>
<jsp:useBean id="ArchDocAlumnoUtil" scope="page" class="aca.archivo.ArchDocAlumnoUtil"/>
<script>
	function Traspasar(){
		if (confirm("¿ Deseas traspasar todos los datos ?")){
			document.location.href= "traspasoGeneral?Accion=1";
		}
	}
	function subir(){
		if (confirm("¿ Deseas subir todos los datos ?")){
			document.location.href= "traspasoGeneral?Accion=2";
		}
	}
</script>
<%
	String accion 		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int numRow 			= 0;
	int total			= 0;
	boolean error 		= false;
	boolean error2		= false;
	String mensaje 		= "";
	byte[]  bTmp 			= new byte[0];
	DriverManager.registerDriver (new org.postgresql.Driver());

	/* Conectar a postgres (ATLAS)*/
	Connection  conn2 				= null;
	Statement   stmt2 				= null;	
	ResultSet   rset2 				= null;
	
	/* Conectar a postgres (LOCAL)*/
	Connection  conn3 				= null;
	PreparedStatement pstm3 		= null;	
	
	String COMANDO 					= "";
	
	/* --- CREAR CONEXION DIRECTA --- */
	try{		
		Class.forName("org.postgresql.Driver");
		conn2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/archivo","postgres","jete17");
		stmt2=conn2.createStatement();	   
		System.out.println("Conectado ATLAS..");
		
	}catch (Exception ex){
		System.out.println("No se pudo conectar Externo");
		ex.printStackTrace(); 
		error = true;
	}
	
	/* --- CREAR CONEXION DIRECTA --- */
	try{		
		Class.forName("org.postgresql.Driver");
		conn3 = DriverManager.getConnection("jdbc:postgresql://172.16.7.73:5432/archivo","postgres","jete17");
		System.out.println("Conectado LOCAL..");
		
	}catch (Exception ex){
		System.out.println("No se pudo conectar a Local");
		ex.printStackTrace(); 
		error = true;
	}
	
	
	if (!error && accion.equals("1")){		
		COMANDO = "SELECT COUNT(*) AS TOTAL FROM ARCH_GENERAL";
		rset2 = stmt2.executeQuery(COMANDO);
		if (rset2.next()){			 	
			total = rset2.getInt(1);
		}		
		System.out.println("Obteniendo rset de documentos...");
		COMANDO = "SELECT MATRICULA, FOLIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO FROM ARCH_GENERAL";
		rset2 = stmt2.executeQuery(COMANDO);
		while (rset2.next()){
			String matricula 		= rset2.getString("MATRICULA");
			String folio 			= rset2.getString("FOLIO");
			String fecha 			= rset2.getString("FECHA");
			String usuario 			= rset2.getString("USUARIO");
			
			String comando = "INSERT INTO ARCH_GENERAL(MATRICULA,FOLIO,FECHA,USUARIO)"
				+" VALUES('"+matricula+"',"+folio+",TO_DATE('"+fecha+"','DD/MM/YYYY'),'"+usuario+"')";				
			pstm3 = conn3.prepareStatement(comando);
		   	if (pstm3.executeUpdate() == 1){
		   		numRow++;
		   		//conn3.commit();
		   		if ((numRow % 100) == 0) System.out.println("Renglón:"+numRow);
		   	}
		}		
		System.out.println("Datos Grabados:"+total+":"+numRow);
		mensaje = "Se traspasaron "+numRow+" renglones a la tabla de arch_general";
	}
	
	
	if (!error2 && accion.equals("2")){		

		// File folder = new File("C:\\Users\\JoseTorres\\Desktop\\respaldo2004");		
		String path = application.getRealPath("archivo/traspaso/resGen");
		if (java.io.File.separator.equals("\\")){
			path = path.replace("\\", "/");		
		}
		File folder = new File(path);		
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	System.out.println("Entre a carpeta");
	        } else {
	        	System.out.println("Entre a File");
	        	String[] name = fileEntry.getName().split("[-]");
	        	
	            System.out.println(fileEntry.getName()+" Long:"+name.length);
	            
				String matricula 	= name[0];
				String folio 		= name[1].replace(".jpg","");
				
				//System.out.println("Paso 3: "+matricula+":"+folio);
				// System.out.println(fileEntry.getName()+" Matricula "+matricula+" Folio:"+folio);			
				
			/*** CODIGO PARA GRABAR IMAGENES DE TIPO BYTEA******/
			/*
				File file = new File(fileEntry.getPath());
				FileInputStream fis = new FileInputStream(file);
				
				InputStream filecontent = new FileInputStream(file);
	            byte[] b = new byte[filecontent.available()];
	            filecontent.read(b);	            
	           	bTmp = b;
	           	
	           	PreparedStatement ps = conn3.prepareStatement("UPDATE arch_GENERAL2 SET IMAGEN=? WHERE matricula = '"+matricula+"' AND FOLIO="+folio);
				ps.setBinaryStream(1, fis, file.length());			
				
			   	ps.executeUpdate();				
		     	ps.close();
				//fis.close();
		   		conn3.commit();
		   	*/
		   		
		 		// All LargeObject API calls must be within a transaction
				conn3.setAutoCommit(false);
		   	
				// Get the Large Object Manager to perform operations with
				org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn3).getLargeObjectAPI();
			
				//create a new large object
				long oid = lom.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
			
				//open the large object for write
				org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
			
				// Now open the file
				File file = new File(fileEntry.getPath());
				FileInputStream fis = new FileInputStream(file);
			
				// copy the data from the file to the large object
				byte buf[] = new byte[(int)file.length()];
				int le; while ((le=fis.read(buf)) !=-1){obj.write(buf,0,le);}		
			
				//Now insert the image into row
				
				PreparedStatement ps = conn3.prepareStatement("UPDATE arch_GENERAL SET IMAGEN=? WHERE matricula = '"+matricula+"' AND FOLIO="+folio);
				ps.setLong(1,oid);
				
			   	if (ps.executeUpdate()==1){			   		
			   		conn3.commit();
			   		if (numRow%100==0){
			   			System.out.println("Grabó:"+numRow);
			   		}			   		
					fis.close();
					ps.close();
					numRow++;
			   	}
			 	
	        }
	        
	    }		
	    conn3.setAutoCommit(true);
	    
		mensaje = "Se traspasaron "+numRow+" renglones a la tabla de arch_documento";
	}
	
%>
<div class="container-fluid">	
	<h1>Documentos sin clasificar(Datos/Imágenes)</h1>
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp;
		<a href="javascript:Traspasar();" class="btn btn-success"><i class="icon-arrow-down icon-white"></i>Traspasar datos</a>&nbsp;&nbsp;&nbsp;
		<a href="javascript:subir();" class="btn btn-success"><i class="icon-arrow-down icon-white"></i>Subir imagenes</a>
	</div>
	<div class="alert alert-info">
		<%=mensaje%>
	</div>
</div>
<%
		if (conn2!=null) conn2.close();
		if (stmt2!=null) stmt2.close();
		if (rset2!=null) rset2.close();
		
		if (conn3!=null) conn3.close();
		if (pstm3!=null) pstm3.close();		
%>
<%@ include file= "../../cierra_enoc.jsp" %>