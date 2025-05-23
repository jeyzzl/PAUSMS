<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
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
			document.location.href= "traspaso?Accion=1";
		}
	}
	function subir(){
		if (confirm("¿ Deseas subir todos los datos ?")){
			document.location.href= "traspaso?Accion=2";
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
		conn2 = DriverManager.getConnection("jdbc:postgresql://172.16.251.11:5432/archivo","postgres","jete17");
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
		COMANDO = "SELECT COUNT(*) AS TOTAL FROM ENOC.ARCH_DOCALUM"; 
		rset2 = stmt2.executeQuery(COMANDO);
		if (rset2.next()){			 	
			total = rset2.getInt(1);
		}		
		System.out.println("Obteniendo rset de documentos...");
		COMANDO = "SELECT MATRICULA,IDDOCUMENTO,HOJA,FUENTE,TIPO,ORIGEN,TO_CHAR(F_INSERT,'DD/MM/YYYY') AS FECHA_INSERT,TO_CHAR(F_UPDATE,'DD/MM/YYYY') AS FECHA_UPDATE FROM ENOC.ARCH_DOCALUM";
		rset2 = stmt2.executeQuery(COMANDO);
		while (rset2.next()){
			String matricula 		= rset2.getString("MATRICULA");
			String documentoId 		= rset2.getString("IDDOCUMENTO");
			String hoja 			= rset2.getString("HOJA");
			String fuente 			= rset2.getString("FUENTE");
			String tipo 			= rset2.getString("TIPO");			
			String origen 			= rset2.getString("ORIGEN");
			String fechaInsert 		= rset2.getString("FECHA_INSERT");
			String fechaUpdate		= rset2.getString("FECHA_UPDATE");
			
			String comando = "INSERT INTO ENOC.ARCH_DOCALUM(MATRICULA,IDDOCUMENTO,HOJA,FUENTE,TIPO,ORIGEN,F_INSERT,F_UPDATE)"
				+" VALUES('"+matricula+"',"+documentoId+","+hoja+",'"+fuente+"','"+tipo+"','"+origen+"',TO_DATE('"+fechaInsert+"','DD/MM/YYYY'),TO_DATE('"+fechaUpdate+"','DD/MM/YYYY'))";				
			pstm3 = conn3.prepareStatement(comando);			
		   	if (pstm3.executeUpdate() == 1){
		   		numRow++;
		   		//conn3.commit();
		   		if ((numRow % 100) == 0) System.out.println("Renglón:"+numRow);
		   	}
		}		
		System.out.println("Datos Grabados:"+total+":"+numRow);
		mensaje = "Se traspasaron "+numRow+" renglones a la tabla de arch_documento";
	}	
	
	if (!error2 && accion.equals("2")){		

		//File folder = new File("C:\\Users\\JoseTorres\\Desktop\\respaldo2004");
		String path = application.getRealPath("archivo/traspaso/Archivo2011-2");
		if (java.io.File.separator.equals("\\")){
			path = path.replace("\\", "/");		
		}
		File folder = new File(path);
		
		// All LargeObject API calls must be within a transaction
		conn3.setAutoCommit(false);
		
		
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	
	        } else {
	        	String[] name = fileEntry.getName().split("[-]"); 	        
	        	
	            //System.out.println(fileEntry.getName()+" "+name.length+" "+name[0]);
			
				String matricula 		= name[0];
				String documentoId 		= name[1];
				String fuente			= name[2];
				String[] hoja 			= name[3].split("\\.");
				
				//System.out.println(fileEntry.getName()+" Matricula "+matricula+" documentId  "+documentoId+" hoja "+hoja[0]);
				
				/*  GRABAR IMAGEN EN BYTEA
				// All LargeObject API calls must be within a transaction
				conn3.setAutoCommit(false);
				
				File file = new File(fileEntry.getPath());
				FileInputStream fis = new FileInputStream(file);

				
				InputStream filecontent = new FileInputStream(file);
	            byte[] b = new byte[filecontent.available()];
	            filecontent.read(b);
	            
	           	bTmp = b;
	           	
	           	PreparedStatement ps = conn3.prepareStatement("UPDATE ENOC.arch_docalum SET IMAGEN=?, F_UPDATE = now() WHERE matricula = '"+matricula+"' AND IDDOCUMENTO='"+documentoId+"' AND HOJA= TO_NUMBER(?, '99')");
				ps.setBinaryStream(1, fis, file.length());
				ps.setString(2, hoja[0].toString());
								
			   	ps.executeUpdate();
				
		     	ps.close();
				//fis.close();
		   		conn3.commit();
				*/
				
				
				
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
				
				//Now insert the row into imagesLO	
				PreparedStatement ps = conn3.prepareStatement("UPDATE ENOC.ARCH_DOCALUM SET IMAGEN = ?"+
						" WHERE MATRICULA = '"+matricula+"' AND IDDOCUMENTO = '"+documentoId+"' AND HOJA = TO_NUMBER(?,'99')");
				ps.setLong(1, oid);
				ps.setString(2, hoja[0].toString());
					
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
		System.out.println(mensaje);
	}
	
%>
<div class="container-fluid">	
	<h1>Documentos(Datos/Imágenes)</h1>
	<div class="alert alert-info">	
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
		<a href="javascript:Traspasar();" class="btn btn-success"><i class="icon-arrow-down icon-white"></i>Traspasar datos</a>
		<a href="javascript:subir();" class="btn btn-success"><i class="icon-arrow-down icon-white"></i>Subir imágenes</a>
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