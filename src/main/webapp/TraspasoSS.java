import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Created on 3/10/2005
 *
*/

/**
 * @author Pedro
 *
 */
public class TraspasoSS {
    
    public static void main(String args[]) throws SQLException{
        Connection conn=null;
        Connection connO=null;
        	
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:odbc:horas");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("conectado");
        PreparedStatement ps =null;
	    ResultSet rs=null;
        PreparedStatement psO =null;
	    ResultSet rsO=null;
	    String matricula,nombre,escuela,primero,ultimo,informes,comando,mes="",anno="";
	    String horas,observaciones,sin[] = new String[10],total,fecha="04/10/2005";
	    String comentario="TR.";
	    int ninformes=0,in[] = new int[12],informe=0,cin=0;
	    try {
	        ps = conn.prepareStatement("select * from horas");
	        try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
            }
		 	connO = DriverManager.getConnection("jdbc:oracle:thin:@172.16.254.25:1521:ora1", "enoc", "caminacondios");
		 		 	
		 	rs=ps.executeQuery();
    	    while(rs.next()){
    	        matricula=rs.getString(1);
                nombre=rs.getString(2);
                escuela=rs.getString(3);
                primero=rs.getString(4);
                ultimo=rs.getString(5);
                informes=rs.getString(6);
                horas=rs.getString(7);
                observaciones=rs.getString(8);
    	        sin[0]=rs.getString(9);
    	        sin[1]=rs.getString(10);
    	        sin[2]=rs.getString(11);
    	        sin[3]=rs.getString(12);
    	        sin[4]=rs.getString(13);
    	        sin[5]=rs.getString(14);
    	        sin[6]=rs.getString(15);
    	        sin[7]=rs.getString(16);
    	        sin[8]=rs.getString(17);
    	        sin[9]=rs.getString(18);
    	        total=rs.getString(19);
    	        
    	        if(total!=null){
    	            if(Double.parseDouble(total)!=0){
    	                System.out.print(matricula+" ");
    	                if(informes!=null)ninformes=new Double(Double.parseDouble(informes)).intValue();
    	                if(informes==null&&horas!=null)ninformes=1;
    	                if(matricula.equals("0930608"))
	                        matricula="0930608";
    	                if(ninformes==0){
    	                    for(int i=0;i<10;i++){
    	                        if(sin[i]!=null){
    	                           ninformes=i;
    	                           break;
    	                        }
    	                    }
    	                    if(ninformes==0){mes="11";anno="2004";}
	                        if(ninformes==1){mes="12";anno="2004";}
	                        if(ninformes==2){mes="01";anno="2005";}
	                        if(ninformes==3){mes="02";anno="2005";}
	                        if(ninformes==4){mes="03";anno="2005";}
	                        if(ninformes==5){mes="04";anno="2005";}
	                        if(ninformes==6){mes="05";anno="2005";}
	                        if(ninformes==7){mes="06";anno="2005";}
	                        if(ninformes==8){mes="07";anno="2005";}
	                        if(ninformes==9){mes="08";anno="2005";}
	                        fecha="01/"+mes+"/"+anno;
    	                }else{
    	                    mes=12-ninformes-1+"";
    	                    if(Integer.parseInt(mes)<=0)mes=(12+Integer.parseInt(mes))+"";
    	                	if(mes.length()==1)mes="0"+mes;
    	                	anno="2004";
                        	if(ninformes>11)anno=Integer.parseInt(anno)-1+"";
                        	fecha="01/"+mes+"/"+anno;
    	                }
    	                comando="insert into ENOC.ssoc_docalum(codigo_personal,folio,documento_id,asignacion_id,fecha,num_horas,comentario,entregado) " + 
                        "values(?,(select COALESCE(max(FOLIO),0)+1 from ENOC.ssoc_docalum where codigo_personal=?),?,?,to_date(?,'dd/mm/yyyy'),?,?,?)"; 
                        psO = connO.prepareStatement(comando);psO.setString(1,matricula);psO.setString(2,matricula);
                        psO.setInt(3,1);psO.setInt(4,0);psO.setString(5,fecha);psO.setInt(6,0); psO.setString(7,comentario);
                        psO.setString(8,"S");psO.executeUpdate();psO.close();
                        
    	                comando="insert into ENOC.ssoc_docalum(codigo_personal,folio,documento_id,asignacion_id,fecha,num_horas,comentario,entregado) " + 
                        "values(?,(select COALESCE(max(FOLIO),0)+1 from ENOC.ssoc_docalum where codigo_personal=?),?,?,to_date(?,'dd/mm/yyyy'),?,?,?)"; 
                        psO = connO.prepareStatement(comando);psO.setString(1,matricula);psO.setString(2,matricula);
                        psO.setInt(3,2);psO.setInt(4,0);psO.setString(5,fecha);psO.setInt(6,0); psO.setString(7,comentario);
                        psO.setString(8,"S");psO.executeUpdate();psO.close();

    	                comando="insert into ENOC.ssoc_docalum(codigo_personal,folio,documento_id,asignacion_id,fecha,num_horas,comentario,entregado) " + 
                        "values(?,(select COALESCE(max(FOLIO),0)+1 from ENOC.ssoc_docalum where codigo_personal=?),?,?,to_date(?,'dd/mm/yyyy'),?,?,?)"; 
                        psO = connO.prepareStatement(comando);psO.setString(1,matricula);psO.setString(2,matricula);
                        psO.setInt(3,3);psO.setInt(4,0);psO.setString(5,fecha);psO.setInt(6,0); psO.setString(7,comentario);
                        psO.setString(8,"S");psO.executeUpdate();psO.close();
                        System.out.print(" "+ninformes+".- ");
                        if(informes!=null||(informes==null&&horas!=null)){
                            if(informes==null&&horas!=null)ninformes=1;
    	                    else ninformes=new Double(Double.parseDouble(informes)).intValue();
    	                    if(horas!=null)
    	                        for(int i=0;i<ninformes;i++){
    	                            System.out.print(i+1+",");
    	                            in[i]=new Double(Double.parseDouble(horas)).intValue()/ninformes;
    	                            if(i==ninformes-1) in[i]+=new Double(Double.parseDouble(horas)).intValue()-(new Double(Double.parseDouble(horas)).intValue()/ninformes)*ninformes;
    	                            mes=12-ninformes+i+"";
    	    	                    if(Integer.parseInt(mes)<=0)mes=(12+Integer.parseInt(mes))+"";
    	                            if(mes.length()==1)mes="0"+mes;
    	                            anno="2004";
    	                            if(ninformes-i>11)anno=Integer.parseInt(anno)-1+"";
    	                            fecha="01/"+mes+"/"+anno;
    	                            informe++;
    	                            comando="insert into ENOC.ssoc_docalum(codigo_personal,folio,documento_id,asignacion_id,fecha,num_horas,comentario,entregado) " + 
    	                            "values(?,(select COALESCE(max(FOLIO),0)+1 from ENOC.ssoc_docalum where codigo_personal=?),?,?,to_date(?,'dd/mm/yyyy'),?,?,?)"; 
    	                            psO = connO.prepareStatement(comando);psO.setString(1,matricula);psO.setString(2,matricula);
    	                            psO.setInt(3,informe+3);psO.setInt(4,0);psO.setString(5,fecha);psO.setInt(6,in[i]);
    	                            psO.setString(7,comentario);psO.setString(8,"S");psO.executeUpdate();psO.close();
    	                        }
    	                }
    	                for(int i=0;i<10;i++){
    	                    if(sin[i]!=null){
    	                        if(i==0){mes="12";anno="2004";}
    	                        if(i==1){mes="01";anno="2005";}
    	                        if(i==2){mes="02";anno="2005";}
    	                        if(i==3){mes="03";anno="2005";}
    	                        if(i==4){mes="04";anno="2005";}
    	                        if(i==5){mes="05";anno="2005";}
    	                        if(i==6){mes="06";anno="2005";}
    	                        if(i==7){mes="07";anno="2005";}
    	                        if(i==8){mes="08";anno="2005";}
    	                        if(i==9){mes="09";anno="2005";}
    	                        fecha="01/"+mes+"/"+anno;
    	                        informe++;
    	                        comando="insert into ENOC.ssoc_docalum(codigo_personal,folio,documento_id,asignacion_id,fecha,num_horas,comentario,entregado) " + 
    	        	    		"values(?,(select COALESCE(max(FOLIO),0)+1 from ENOC.ssoc_docalum where codigo_personal=?),?,?,to_date(?,'dd/mm/yyyy'),?,?,?)"; 
    	                        psO = connO.prepareStatement(comando);psO.setString(1,matricula);psO.setString(2,matricula);
    	                        psO.setInt(3,informe+3);psO.setInt(4,0);psO.setString(5,fecha);psO.setInt(6, new Double(Double.parseDouble(sin[i])).intValue());
    	                        psO.setString(7,comentario);psO.setString(8,"S");psO.executeUpdate();psO.close();
    	                    }
    	                }
    	                System.out.println("... "+informe);
    	                informe=0;
    	                cin=0;
    	            }
    	        }
    	        else System.out.println("-------- NULOOOOOOOOO: " + matricula);
    	    }
    	   
    	    /*psO=connO.prepareStatement("select * from ENOC.ssoc_documentos"); 
		 	rsO=psO.executeQuery();
		 	
		 	while(rsO.next()){
		 	    System.out.println(rsO.getString(2));
		 	}
		 	*/
    	    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            if(conn!=null)conn.close();
            if(conn!=null)connO.close();
            System.out.println("Cerrados...");
        }
    }
}