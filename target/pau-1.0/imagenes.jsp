<%@ page import="java.sql.*" buffer="none" %>
<%@ page import="java.io.*" %>
<jsp:useBean id="bArchivos" scope="page" class="aca.archivos.TraspasoArchivos"/>
<html><body>
<%
	Connection conn=null;
	java.sql.PreparedStatement ps = null, ps2=null;
	java.sql.ResultSet rs=null, rs2=null;
    try {
	   	DriverManager.registerDriver (new org.postgresql.Driver());
    	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
    	System.out.println("conectado a postgres");
    	conn.setAutoCommit(false);
    	String ruta = application.getRealPath("/portales/alumno/img")+"/";
    	System.out.println("Ruta:"+ruta);
    	File fichero=new File(ruta,".");
    	File[] listaArchivos=fichero.listFiles();
    	String matricula="",iddocumento="",hoja="",sql="";
    	File archivo;    	
    	for(int i=0;i<listaArchivos.length;i++){
    		archivo=listaArchivos[i];
    		System.out.println("Nombre: "+archivo.getName());
    		if(archivo.getName().indexOf(".jpg")==archivo.getName().length()-4 && archivo.getName().length()>=13){
    			matricula=archivo.getName().substring(0,7);
    			hoja=archivo.getName().substring(archivo.getName().indexOf(".")-1,archivo.getName().indexOf("."));
    			iddocumento=archivo.getName().substring(7,archivo.getName().indexOf(".")-1);
    			if(matricula.equals(""))matricula="0000000";
    			if(iddocumento.equals(""))iddocumento="0";
    			if(hoja.equals(""))hoja="0";
    			System.out.println("-> "+matricula+","+iddocumento+","+hoja);
    			ps = conn.prepareStatement("select matricula from arch_docalum where matricula=? and iddocumento=? and hoja=?"); 
    			ps.setString(1,matricula);
    			ps.setInt(2,Integer.parseInt(iddocumento));
    			ps.setInt(3,Integer.parseInt(hoja));
    			rs=ps.executeQuery();
    			System.out.println("Primer script ejecutado");
    			if(rs.next()){
    				System.out.println("encontrado primer script");
    				ps2=conn.prepareStatement("select matricula from arch_docalum where matricula=? and iddocumento=? and hoja=? and imagen is null"); 
    				ps2.setString(1,matricula);
        			ps2.setInt(2,Integer.parseInt(iddocumento));
        			ps2.setInt(3,Integer.parseInt(hoja));
    				rs2=ps2.executeQuery();
    				System.out.println("segundo script ejecutado");
    				if(rs2.next()){
    					System.out.println("encontrado segundo script");
    					sql="update arch_docalum set imagen = ? where matricula=? and iddocumento = ? and hoja = ?"; 
    					ps2=conn.prepareStatement(sql);
    					
    					FileInputStream fis = new FileInputStream(archivo);
    					org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
    					long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
    					org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
    					byte buf[] = new byte[(int)archivo.length()];
    					int le; 
    					while ((le=fis.read(buf)) !=-1){
    						obj.write(buf,0,le);
    					}    					
    					ps2.setLong(1,oid);
        				ps2.setString(2,matricula);
            			ps2.setInt(3,Integer.parseInt(iddocumento));
            			ps2.setInt(4,Integer.parseInt(hoja));
            			ps2.executeUpdate();
            			conn.commit();
            			fis.close();
            			out.println("Actualizado -> "+matricula+","+iddocumento+","+hoja);
   					}
    				else System.out.println("registro ya tiene imagen");
    			}else{
    				System.out.println("no encontrado primer script");
    				sql="insert into arch_docalum(matricula,iddocumento,imagen,hoja,fuente,tipo,origen,f_insert,f_update) Values (?,?,?,?,'A','I','O',now(),now())"; 
					ps2=conn.prepareStatement(sql);

					FileInputStream fis = new FileInputStream(archivo);
					org.postgresql.largeobject.LargeObjectManager lom = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
					long oid = lom.createLO(org.postgresql.largeobject.LargeObjectManager.READ | org.postgresql.largeobject.LargeObjectManager.WRITE);
					org.postgresql.largeobject.LargeObject obj = lom.open(oid, org.postgresql.largeobject.LargeObjectManager.WRITE);
					byte buf[] = new byte[(int)archivo.length()];
					int le; 
					while ((le=fis.read(buf)) !=-1){
						obj.write(buf,0,le);
					}
					
    				ps2.setString(1,matricula);
        			ps2.setInt(2,Integer.parseInt(iddocumento));
					ps2.setLong(3,oid);
        			ps2.setInt(4,Integer.parseInt(hoja));
        			ps2.executeUpdate();
        			conn.commit();
        			fis.close();
        			out.println("Nuevo -> "+matricula+","+iddocumento+","+hoja);
    			}
    		}

    		if ((i % 1000) == 0 && i != 0 ){
    			conn.close();
    			DriverManager.registerDriver (new org.postgresql.Driver());
    	    	conn=DriverManager.getConnection(aca.conecta.Conectar.coneccion(),aca.conecta.Conectar.usuario(),aca.conecta.Conectar.password());
    	    	System.out.println("reconectado a postgres :"+ i);
    	    	conn.setAutoCommit(false);
    		}
    		
    	}
    }
    catch (Exception e) {
		e.printStackTrace();
	}finally{
    	//conn.commit();
    	conn.setAutoCommit(true);
    	try { ps.close(); } catch (Exception ignore) { }
    	try { ps2.close(); } catch (Exception ignore) { }
    	try { rs.close(); } catch (Exception ignore) { }
    	try { rs2.close(); } catch (Exception ignore) { }
    	try { conn.close(); } catch (Exception ignore) { }    	
    	
    }
%>
Listo...
</body></html>