package aca.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import sys.general.Config;
import sys.views.AllTabColumns;

public class CompararColumnas {
	
	public static void comparar(ArrayList<ArrayList<AllTabColumns>> ColumnasIni, ArrayList<ArrayList<AllTabColumns>> ColumnasFin, ArrayList<String> tablas){
		ArrayList<String> ColumnasDiferentes = new ArrayList();
		
		for(int i=0; i<ColumnasIni.size(); i++){
			ArrayList<AllTabColumns> TablasIni = ColumnasIni.get(i);
			int valIni = TablasIni.size();
			ArrayList<AllTabColumns> TablasFin = ColumnasFin.get(i);			
			//if(TablasIni.size() == TablasFin.size()){
				for(AllTabColumns ColumnFin : TablasFin){
					String colum1 = ColumnFin.getColumnName();					
					for(int k=0; k<TablasIni.size(); k++){
						AllTabColumns ColumnIni = TablasIni.get(k);
						String colum2 = ColumnIni.getColumnName();
						if(colum2.equals(colum1)){
							TablasIni.remove(ColumnIni);
						}
					}
				}
				if(!TablasIni.isEmpty()){
						ColumnasDiferentes.add(tablas.get(i));
						System.out.println("Tabla: "+tablas.get(i));
						for(int k=0; k<TablasIni.size(); k++){
							System.out.print(TablasIni.get(k).getColumnName()+" "+TablasIni.get(k).getDataType()+"("+TablasIni.get(k).getDataLength()+")"+TablasIni.get(k).getDataPrecision()+"Nulo:"+TablasIni.get(k).getNullable()+", ");
						}
						System.out.println();
						if(valIni == TablasIni.size()){
							System.out.println("A borrar: ");
							for(int k=0; k<TablasFin.size(); k++){
								System.out.print(TablasFin.get(k).getColumnName()+", ");
							}
							System.out.println("\n");
						}
						else{
							System.out.println();
						}
				}
			//}
			/*else {
				ColumnasDiferentes.add(tablas.get(i));
				System.out.println("Tabla: "+tablas.get(i));
				for(int k=0; k<TablasFin.size(); k++){
					System.out.print(TablasFin.get(k).getColumnName()+", ");
				}
				System.out.println("\n");
			}*/
		}
		System.out.println("Diferentes: "+ColumnasDiferentes.size());
		//System.out.println(ColumnasDiferentes);		
	}
	
	public static void compararSobrantes(ArrayList<ArrayList<AllTabColumns>> ColumnasIni, ArrayList<ArrayList<AllTabColumns>> ColumnasFin, ArrayList<String> tablas){
		ArrayList<String> ColumnasSobrantes = new ArrayList();		
		
		for(int i=0; i<ColumnasIni.size(); i++){
			ArrayList<AllTabColumns> TablasFin = ColumnasIni.get(i);
			int valIni = TablasFin.size();
			ArrayList<AllTabColumns> TablasIni = ColumnasFin.get(i);			
			//if(TablasIni.size() == TablasFin.size()){
				for(AllTabColumns ColumnFin : TablasIni){
					String colum1 = ColumnFin.getColumnName();					
					for(int k=0; k<TablasFin.size(); k++){
						AllTabColumns ColumnIni = TablasFin.get(k);
						String colum2 = ColumnIni.getColumnName();
						if(colum2.equals(colum1)){
							TablasFin.remove(ColumnIni);
						}
					}
				}
				if(!TablasFin.isEmpty()){
					ColumnasSobrantes.add(tablas.get(i));
					System.out.println("Tabla: "+tablas.get(i));
					for(int k=0; k<TablasFin.size(); k++){
						System.out.print(TablasFin.get(k).getColumnName()+", ");
					}
					System.out.println();
					if(valIni == TablasFin.size()){
						System.out.println("A borrar: ");
						for(int k=0; k<TablasFin.size(); k++){
							System.out.print(TablasFin.get(k).getColumnName()+", ");
						}
						System.out.println("\n");
					}
					else{
						System.out.println();
					}
			}
		}
		System.out.println("Sobrantes: "+ColumnasSobrantes.size());
		
		
		
		
		
	}
	
	public static void main (String [] agg){	
		
		ArrayList<String> tablas = new CompararTablas().cargarTablas(1);
		ArrayList<ArrayList<AllTabColumns>> ColumnasIni = new ArrayList();
		ArrayList<ArrayList<AllTabColumns>> ColumnasFin = new ArrayList();
		
		System.out.println("Iguales en Tablas: "+tablas.size()+"\n"+tablas+"\n");

		Connection conn			= null;
		Statement stmt			= null;
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.20:1521:ora1","enoc","caminacondios");
			stmt = conn.createStatement();
			//System.out.println("OK!");
			
			for(String tabla : tablas){
					ArrayList<AllTabColumns> lisIni	= new ArrayList();
					Statement st 		= conn.createStatement();
					ResultSet rs 		= null;
					String comando	= "";
				
					try{
					comando = "SELECT"+
		 			" OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER, DATA_LENGTH, DATA_PRECISION," +
		 			" DATA_SCALE, NULLABLE, COLUMN_ID, DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE," +
		 			" DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH," +
		 			" GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED, V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM"+
		 			" FROM SYS.ALL_TAB_COLUMNS" +
		 			" WHERE OWNER = '"+Config.OWNER+"'" +
		 			" AND TABLE_NAME = '"+tabla+"' ORDER BY 1";
					
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						AllTabColumns atc = new AllTabColumns();
						atc.mapeaReg(rs);
						lisIni.add(atc);
					}
					ColumnasIni.add(lisIni);
					
					}catch(Exception ex){
						System.out.println("Error - sys.vews.AllTabColumnsUtil|getListTable|:"+ex);
					}finally{
						try { rs.close(); } catch (Exception ignore) { }
						try { st.close(); } catch (Exception ignore) { }
					}
			}
		}catch(Exception e){
			
		}finally{
			try{
				stmt.close();
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
//-------------------------------------------------------------------------------------------------------------------------------
		
		
		try{				
			//System.out.print("Creando conexion...");
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			conn = DriverManager.getConnection("jdbc:oracle:thin:@148.235.6.71:1521:xe","enoc","caminacondios");
			stmt = conn.createStatement();
			//System.out.println("OK!");
			
			for(String tabla : tablas){
					ArrayList<AllTabColumns> lisIni	= new ArrayList();
					Statement st 		= conn.createStatement();
					ResultSet rs 		= null;
					String comando	= "";
				
					try{
					comando = "SELECT"+
		 			" OWNER, TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER, DATA_LENGTH, DATA_PRECISION," +
		 			" DATA_SCALE, NULLABLE, COLUMN_ID, DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE," +
		 			" DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE, CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH," +
		 			" GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED, V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM"+
		 			" FROM SYS.ALL_TAB_COLUMNS" +
		 			" WHERE OWNER = '"+Config.OWNER+"'" +
		 			" AND TABLE_NAME = '"+tabla+"' ORDER BY 1";
					
					rs = st.executeQuery(comando);
					while (rs.next()){
						
						AllTabColumns atc = new AllTabColumns();
						atc.mapeaReg(rs);
						lisIni.add(atc);
					}
					ColumnasFin.add(lisIni);
					
					}catch(Exception ex){
						System.out.println("Error - sys.vews.AllTabColumnsUtil|getListTable|:"+ex);
					}finally{
						try { rs.close(); } catch (Exception ignore) { }
						try { st.close(); } catch (Exception ignore) { }
					}
			}
		}catch(Exception e){
			
		}finally{
			try{
				stmt.close();
				conn.close();
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
		//Compara las columnas de las tablas que existen--------------------------
		comparar(ColumnasIni, ColumnasFin, tablas);
		
		
		//Verifica si sobran columnas en la segunda tabla que no existan en la primera---------------------------------------
		//compararSobrantes(ColumnasIni, ColumnasFin, tablas);
		
	}
}
