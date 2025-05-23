package aca.archivo;
import java.sql.*;
import java.util.*;
public class ArchivoUtil {
	
	public String autorizaAlumno(Connection conn, String s_codigo_personal) throws SQLException, Exception{
		boolean bln_ok				= false;
		String s_carr				= "X";	
	
		int n_cont					= 0;
		int n_encontro 				= 0;
		int n_grupo					= 0;
		int n_temp					= 0;
		
		String s_autorizado			= "";
		String s_grupos				= "";
		String COMANDO;
		ResultSet rset 		= null;
		ResultSet rset2 		= null;
		ResultSet rset3 		= null;
		ResultSet rset4 		= null;
		ResultSet rset5 		= null;
		Statement stmt 		= conn.createStatement();
		
		try {
	
			COMANDO = "SELECT CODIGO_PERSONAL AS CODIGO, "+ 				
						"ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) AS CARR "+
						"FROM ENOC.ALUM_PERSONAL "+ 
						"WHERE CODIGO_PERSONAL = '"+s_codigo_personal+"'";
			rset = stmt.executeQuery(COMANDO);
			if (rset.next()){
				s_carr 		= rset.getString("carr");
			}
			
			int combinaciones[] = new int[200]; 
			for (int j=1;j<200;j++) combinaciones[j] = 0;
			int i = 1; n_cont = 0;
		
			COMANDO = "SELECT GRUPO, IDDOCUMENTO FROM ENOC.ARCH_GRUPOS ORDER BY GRUPO"; 
			rset2 = stmt.executeQuery(COMANDO);	
			while(rset2.next()){	
				if (rset2.getInt("grupo")!= n_temp && n_temp != 0 ){
					combinaciones[i] = n_cont;
					i++; n_cont = 0;
				}
				n_temp = rset2.getInt("grupo");
				n_cont++;
			}
			combinaciones[i]=n_cont;
	
			// Arreglo que define los grupos validos para la carrera del alumno.
			int grupos[] = new int[200];
			for (int j=1;j<200;j++) grupos[j] = 0;
			
			// Obtiene la linea de grupos validos en la carrera del alumno.
			COMANDO = " SELECT replace(COALESCE(grupos,' '),' ',';') grupos From ENOC.arch_grupos_carrera"
					+ " WHERE carrera = '"+s_carr+"'";
			rset3 = stmt.executeQuery(COMANDO);
			if (rset3.next()){
				s_grupos = rset3.getString("grupos");			
				StringTokenizer Texto = new StringTokenizer(s_grupos,";");
				while (Texto.countTokens()>0){	
					grupos[Integer.parseInt(Texto.nextToken())] = 1;	
				}		
			}else{
				s_grupos = "*";
			}
		
			COMANDO = "SELECT ENOC.USUARIO_NOMBRE(USUARIO_ALTA) AS USUARIO "+
					"FROM ENOC.ARCH_PERMISOS "+ 
					"WHERE MATRICULA = '"+s_codigo_personal+"' "+			
					"AND TO_DATE(TO_CHAR(now(),'DD-MM-YY'),'DD-MM-YY') BETWEEN FECHA_INI AND FECHA_LIM "+
					"AND ESTADO = 'A'";
			rset4 = stmt.executeQuery(COMANDO);	
			if (rset4.next()||s_grupos.equals("*")){
				if (s_grupos.equals("*")){
					s_autorizado  = "Autorizado..! (No valida documentos.)";
				}else{
					s_autorizado = "Autorizado..!  Permiso otorgado por: "+rset4.getString("usuario");
				}
				bln_ok = true;
			}else{	
				n_cont = 0;		
				while( (n_cont<200) && (bln_ok==false)){
					if (grupos[n_cont]==1){
						COMANDO = "select count(*) encontrados From ENOC.arch_grupos "+
							"where grupo = "+n_cont+" "+
							"and iddocumento||','||idstatus in "+
								"(Select iddocumento||','||idstatus "+
								"from ENOC.arch_docalum "+ 
								"Where matricula ='"+s_codigo_personal+"')";
						rset5 = stmt.executeQuery(COMANDO);
						if (rset5.next()){
							n_encontro = rset5.getInt("encontrados");
							if (n_encontro == combinaciones[n_cont]){
								bln_ok = true;
								n_grupo = n_cont;
							}
						}else{
							n_encontro = 0;
						}
					}
					n_cont++;	
				}
				if ( bln_ok == false){
					s_autorizado = "No Autorizado..!";
				}else{
					s_autorizado = "Autorizado..!   Grupo:"+n_grupo;
				}			
			}
		}catch(Exception e){
			System.out.println("Error:"+e);
		}finally {
			try { rset.close(); } catch (Exception ignore) { }
			try { rset2.close(); } catch (Exception ignore) { }
			try { rset3.close(); } catch (Exception ignore) { }
			try { rset4.close(); } catch (Exception ignore) { }
			try { rset5.close(); } catch (Exception ignore) { }			
			try { stmt.close(); } catch (Exception ignore) { }
		}
		
		return s_autorizado;
	}	
	
	
	
	public ArrayList<String> documentosFaltantes(Connection conn, String s_codigo_personal) throws SQLException, Exception{
		String s_carr				= "X";	
		ArrayList<String> lis					= new ArrayList<String>();
		ArrayList<String> lisDocAlum			= new ArrayList<String>();
	
		int n_cont					= 0;
		int n_temp					= 0;
		int n_grupoFAnt				= 0;
		int n_grupoF				= 0;
		int n_grupoInicio			= 0;
		float f_suma				= 0;
		double d_prom				= 0;
		
		String s_grupos				= "";
		String COMANDO;
		String comando1;
		String comando2;
		ResultSet rset 		= null;
		ResultSet rset2		= null;
		ResultSet rset3		= null;
		ResultSet rset4		= null;
		ResultSet rset5		= null;
		ResultSet rset6		= null;
		ResultSet rset7		= null;
		
		Statement stmt 		= conn.createStatement();	
		Statement stmt2		= conn.createStatement();
		try {
			COMANDO = "SELECT CODIGO_PERSONAL AS CODIGO, "+ 				
						"ENOC.ALUM_CARRERA_ID(CODIGO_PERSONAL) AS CARR "+
						"FROM ENOC.ALUM_PERSONAL "+ 
						"WHERE CODIGO_PERSONAL = '"+s_codigo_personal+"'";
			rset = stmt.executeQuery(COMANDO);
			if (rset.next()){
				s_carr 		= rset.getString("carr");
			}
			int combinaciones[] = new int[200]; 
			for (int j=1;j<200;j++) combinaciones[j] = 0;
			int i = 1; n_cont = 0;
	
			COMANDO = "SELECT GRUPO, IDDOCUMENTO FROM ENOC.ARCH_GRUPOS ORDER BY GRUPO"; 
			rset2 = stmt.executeQuery(COMANDO);	
			while(rset2.next()){	
				if (rset2.getInt("grupo")!= n_temp && n_temp != 0 ){
					combinaciones[i] = n_cont;
					i++; n_cont = 0;
				}
				n_temp = rset2.getInt("grupo");
				n_cont++;
			}
			combinaciones[i]=n_cont;
	
			// Arreglo que define los grupos validos para la carrera del alumno.
			int grupos[] = new int[200];
			comando2 = "Select COALESCE(iddocumento, 50)||','||COALESCE(idstatus, 20) " +
	  		   		   "from ENOC.arch_docalum " + 
	  		   		   "Where matricula ='"+s_codigo_personal+"'";
			rset3 = stmt2.executeQuery(comando2);
			while(rset3.next())
			{
				lisDocAlum.add(rset3.getString(1));
			}
			for (int j=1;j<200;j++) grupos[j] = 0;
	
			// Obtiene la linea de grupos validos en la carrera del alumno.
			COMANDO = "select replace(COALESCE(grupos,' '),' ',';') grupos From ENOC.arch_grupos_carrera "+
					"where carrera = '"+s_carr+"'";
			rset4 = stmt.executeQuery(COMANDO);
			if (rset4.next()){
				s_grupos = rset4.getString("grupos");
				StringTokenizer Texto = new StringTokenizer(s_grupos,";");
				StringTokenizer text = new StringTokenizer(s_grupos,";");
				n_grupoInicio = Integer.parseInt(text.nextToken());
				while (Texto.countTokens()>0){	
					grupos[Integer.parseInt(Texto.nextToken())] = 1;			
				}
				
				n_cont = 0;	
				d_prom = 0;
				while(n_cont<200){
					if (grupos[n_cont]==1){
						comando1 = "select iddocumento||','||idstatus From ENOC.arch_grupos "+
								  "where grupo = "+n_cont;
						rset5 = stmt.executeQuery(comando1);
				
						f_suma = 0;
						int k = 0;
						while(rset5.next()){
							for(int l = 0; l < lisDocAlum.size();l++)
							{
								if(rset5.getString(1).equals(lisDocAlum.get(l)))
								{
									f_suma++;
								}
							}
							k++;
						}
						if(d_prom < (f_suma/k))
						{
							n_grupoFAnt = n_grupoF;
							d_prom = f_suma/k;
							n_grupoF = n_cont;
						}
					}
					n_cont++;	
				}
			
				if(d_prom != 1)
				{
					if(n_grupoF == 0)
					{
						n_grupoF = n_grupoInicio;
						n_grupoFAnt = n_grupoInicio + 1;
						
					}
					else
					{
						if(n_grupoFAnt == 0)
						{
							n_grupoFAnt = n_grupoInicio;
							
						}
					}
					
					comando1 = "SELECT AG.IDDOCUMENTO IDDOCUMENTO, " +
							   "AD.DESCRIPCION DESCRIPCION, AST.DESCRIPCION STATUS " +
							   "FROM ENOC.ARCH_GRUPOS AG, ENOC.ARCH_DOCUMENTOS AD, ENOC.ARCH_STATUS AST " + 
							   "WHERE AG.IDDOCUMENTO=AD.IDDOCUMENTO " +
							   "AND AG.IDSTATUS=AST.IDSTATUS " +
							   "AND AG.GRUPO = "+n_grupoF+
							   "AND COALESCE(ag.iddocumento, 50)||','||COALESCE(ag.idstatus, 20) " +
							   "NOT IN (Select COALESCE(iddocumento, 50)||','||COALESCE(idstatus, 20) " +
							   "from ENOC.arch_docalum Where matricula ='"+s_codigo_personal+"')	" + 
							   "ORDER BY 1";
					
					comando2 = "SELECT AG.IDDOCUMENTO IDDOCUMENTO, " +
							   "AD.DESCRIPCION DESCRIPCION, AST.DESCRIPCION STATUS " +
							   "FROM ENOC.ARCH_GRUPOS AG, ENOC.ARCH_DOCUMENTOS AD, ENOC.ARCH_STATUS AST " + 
							   "WHERE AG.IDDOCUMENTO=AD.IDDOCUMENTO " +
							   "AND AG.IDSTATUS=AST.IDSTATUS " +
							   "AND AG.GRUPO = "+n_grupoFAnt+
							   "AND COALESCE(ag.iddocumento, 50)||','||COALESCE(ag.idstatus, 20) " +
							   "NOT IN (Select COALESCE(iddocumento, 50)||','||COALESCE(idstatus, 20) " +
							   "from ENOC.arch_docalum Where matricula ='"+s_codigo_personal+"')	" + 
							   "ORDER BY 1";
					
					rset6 = stmt.executeQuery(comando1);
					rset7 = stmt2.executeQuery(comando2);
					
					while(rset6.next())
					{
						lis.add("1");
						lis.add(rset6.getString(1));
						lis.add(rset6.getString(2));
						lis.add(rset6.getString(3));
					}
					while(rset7.next())
					{
						lis.add("2");
						lis.add(rset7.getString(1));
						lis.add(rset7.getString(2));
						lis.add(rset7.getString(3));
					}
				}
				else
					lis = null;
			}else
				lis = null;
		}catch(Exception e) {
			System.out.println("Error:"+e);
		}finally {
			try { stmt.close(); } catch (Exception ignore) { }
			if (stmt2!=null) stmt2.close();
			if (rset!=null) rset.close();
			if (rset!=null) rset.close();
			if (rset2!=null) rset2.close();
			if (rset3!=null) rset3.close();
			if (rset4!=null) rset4.close();
			if (rset5!=null) rset5.close();
			if (rset6!=null) rset6.close();
			if (rset7!=null) rset7.close();
		}
		
		return lis;
	}	
}