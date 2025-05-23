import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class fixHorario{
  public void ejecuta() throws Exception{ 
			Connection  cn = null;
			Statement   stmt = null;
			Statement   stmt2 = null;
	 		ResultSet   rset = null;
			try{				
			    System.out.print("Creando conexion...");
			    DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
			    cn=DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.10:1521:ora1","enoc","caminacondios");
				stmt=cn.createStatement();				
			 	System.out.println("OK!");
				rset = stmt.executeQuery("select curso_carga_id,salon,horario,tipo,ctrl_cruce from noe.acad_curso_horario order by curso_carga_id,salon asc"); 
				String horario="",horarioant="",curso="",cursoant="",hr="";
				String salon="",salonant="",tipo="",tipoant="",cruce="",cruceant="";
				ArrayList<String> horarios=new ArrayList<String>();
				String[] aHorario=new String[10];
				int j=0,rg=0;
				boolean hay=false;
			    while (rset.next()){
			        hr="";
			        j++;
			        curso=rset.getString(1);
			        salon=rset.getString(2);
			        horario=rset.getString(3);
			        tipo=rset.getString(4);
			        cruce=rset.getString(5);
			        if(j==685)
			            cruce=cruce+"";
			        if(curso.equals(cursoant)&&salon.equals(salonant)&&j>1)horarios.add(horario);
			        else{
			            if(horarios.size()>1){
			                for(int i=0;i<horarios.size();i++)
			                    aHorario[i]=(String)horarios.get(i);
			                for(int i=0;i<147;i++){
			                    hay=false;
			                    for(int k=0;k<horarios.size();k++)
			                        if(aHorario[k].charAt(i)=='1')hay=true;
			                    if(hay)hr+="1";
			                    else hr+="0";
			                }
			            }
			            if(j>1){
			                rg++;
			                if(hr.equals("")) hr=horarioant;
							stmt2=cn.createStatement();	
							stmt2.executeUpdate("insert into enoc.carga_grupo_horario values('"+cursoant+"','"+salonant+"','"+hr+"','"+tipoant+"','"+cruceant+"')"); 
							stmt2.close();
			                System.out.println(rg + " de "+ j);
			            }
			            horarios.clear();
			        	horarios.add(horario);
			        }
			        cursoant=curso;
			        horarioant=horario;
			        salonant=salon;
			        tipoant=tipo;
			        cruceant=cruce;
			    }
			    System.out.println("Total de registros: "+j);
			}catch (Exception ex){ex.printStackTrace();}
			finally{ 
			    cn.close();
			    stmt.close();
			    rset.close();
			};
	}
  
  public static void main(String[] arr) throws Exception{
      fixHorario h= new fixHorario();
      h.ejecuta();
  }
}