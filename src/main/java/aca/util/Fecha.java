// Utileria de fecha
package aca.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;

public class Fecha{	

	TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
	
	public String getFecha( String Formato ){
		String s_fecha = "";
		Calendar fecha = new GregorianCalendar();
		String dia = null;
		String mes = null;
		if (Formato.equals("1")){
			dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
			if (dia.length() == 1) dia = "0" + dia;
			mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
			if (mes.length() == 1) mes = "0" + mes;
			
			s_fecha = dia+"/"+mes+"/"+Integer.toString(fecha.get(Calendar.YEAR));
		}else{			
		
			switch(fecha.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 		dia = "Sunday";		break;
			case Calendar.MONDAY: 		dia = "Monday";		break;
			case Calendar.TUESDAY:		dia = "Tuesday";	break;
			case Calendar.WEDNESDAY:	dia = "Wednesday";	break;
			case Calendar.THURSDAY:		dia = "Thursday";	break;
			case Calendar.FRIDAY:		dia = "Friday";		break;
			case Calendar.SATURDAY:		dia = "Saturday";	break;
			}
			
			switch(fecha.get(Calendar.MONTH)) {
			case Calendar.JANUARY:		mes = "January";	break;
			case Calendar.FEBRUARY:		mes = "February";	break;
			case Calendar.MARCH:		mes = "March";		break;
			case Calendar.APRIL:		mes = "April";		break;
			case Calendar.MAY:			mes = "May";		break;
			case Calendar.JUNE:			mes = "June";		break;
			case Calendar.JULY:			mes = "July";		break;
			case Calendar.AUGUST:		mes = "August";		break;
			case Calendar.SEPTEMBER:	mes = "September";	break;
			case Calendar.OCTOBER:		mes = "October";	break;
			case Calendar.NOVEMBER:		mes = "November";	break;
			case Calendar.DECEMBER:		mes = "December";	break;
			}
		
			s_fecha = dia + ", "+Integer.toString(fecha.get(Calendar.DAY_OF_MONTH))+"/ "+mes+"/ "+
				Integer.toString(fecha.get(Calendar.YEAR));
		}	
		return s_fecha;
	}
	
	public String getFechaPDF( String Formato ){
		String s_fecha = "";
		Calendar fecha = new GregorianCalendar();
		String dia = null;
		String mes = null;
		if (Formato.equals("1")){
			dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
			if (dia.length() == 1) dia = "0" + dia;
			mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
			if (mes.length() == 1) mes = "0" + mes;
			
			s_fecha = dia+"/"+mes+"/"+Integer.toString(fecha.get(Calendar.YEAR));
		}else{			
		
			switch(fecha.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 		dia = "Sunday";		break;
			case Calendar.MONDAY: 		dia = "Monday";		break;
			case Calendar.TUESDAY:		dia = "Tuesday";	break;
			case Calendar.WEDNESDAY:	dia = "Wednesday";	break;
			case Calendar.THURSDAY:		dia = "Thursday";	break;
			case Calendar.FRIDAY:		dia = "Friday";		break;
			case Calendar.SATURDAY:		dia = "Saturday";	break;
			}
			
			switch(fecha.get(Calendar.MONTH)) {
			case Calendar.JANUARY:		mes = "January";	break;
			case Calendar.FEBRUARY:		mes = "February";	break;
			case Calendar.MARCH:		mes = "March";		break;
			case Calendar.APRIL:		mes = "April";		break;
			case Calendar.MAY:			mes = "May";		break;
			case Calendar.JUNE:			mes = "June";		break;
			case Calendar.JULY:			mes = "July";		break;
			case Calendar.AUGUST:		mes = "August";		break;
			case Calendar.SEPTEMBER:	mes = "September";	break;
			case Calendar.OCTOBER:		mes = "October";	break;
			case Calendar.NOVEMBER:		mes = "November";	break;
			case Calendar.DECEMBER:		mes = "December";	break;
			}
		
			s_fecha = dia + " "+Integer.toString(fecha.get(Calendar.DAY_OF_MONTH))+" of "+mes+" of "+
				Integer.toString(fecha.get(Calendar.YEAR));
		}	
		return s_fecha;
	}
	
	public static String getFechaOficial( String Fecha ){
		String fecha = "";
		
		String dia	= String.valueOf(Integer.parseInt(Fecha.substring(0,2)));				
		String mes 	= Fecha.substring(3,5);
		String year = Fecha.substring(6,10);
		
		int numMes	= Integer.parseInt(mes);
		switch(numMes) {
			case 1:		mes = "january";	break;
			case 2:		mes = "february";	break;
			case 3:		mes = "march";		break;
			case 4:		mes = "april";		break;
			case 5:		mes = "may";		break;
			case 6:		mes = "june";		break;
			case 7:		mes = "july";		break;
			case 8:		mes = "august";		break;
			case 9:		mes = "september";	break;
			case 10:	mes = "october";	break;
			case 11:	mes = "november";	break;
			case 12:	mes = "december";	break;
		}
		
		fecha = dia + " of "+mes+" of "+year;
		
		return fecha;
	}
	
    public static String getFechaOficialIngles(String fecha) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Formatters for each part of the date we need
        SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", Locale.ENGLISH);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

        try {
            // Parse the input date
            Date date = inputFormat.parse(fecha);

            // Extract each part of the date
            String dayOfWeek = dayOfWeekFormat.format(date);
            int day = Integer.parseInt(dayFormat.format(date));
            String month = monthFormat.format(date);
            String year = yearFormat.format(date);

            // Determine the ordinal suffix
            String suffix;
            if (day >= 11 && day <= 13) {
                suffix = "th";
            } else {
                switch (day % 10) {
                    case 1:  suffix = "st"; break;
                    case 2:  suffix = "nd"; break;
                    case 3:  suffix = "rd"; break;
                    default: suffix = "th"; break;
                }
            }

            // Construct the final formatted date
            return String.format("%s, %d%s %s %s", dayOfWeek, day, suffix, month, year);

        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

	public String getDia( String Fecha ){
		String s_dia = "";
		
		s_dia = Fecha.substring(0,2);
		
		return s_dia;
	}
	
	public String getMesNombre( String Fecha ){
		int nmes = Integer.parseInt(Fecha.substring(3,5));
		String mes = "";
		switch(nmes) {
			case 1:		mes = "january";	break;
			case 2:		mes = "february";	break;
			case 3:		mes = "march";		break;
			case 4:		mes = "april";		break;
			case 5:		mes = "may";		break;
			case 6:		mes = "june";		break;
			case 7:		mes = "july";		break;
			case 8:		mes = "august";		break;
			case 9:		mes = "september";	break;
			case 10:	mes = "october";	break;
			case 11:	mes = "november";	break;
			case 12:	mes = "december";	break;
		}		
		return mes;
	}
	
	public static String getNombreMes( String Fecha ){
		int nmes = Integer.parseInt(Fecha.substring(3,5));
		String mes = "";
		switch(nmes) {
			case 1:		mes = "january";	break;
			case 2:		mes = "february";	break;
			case 3:		mes = "march";		break;
			case 4:		mes = "april";		break;
			case 5:		mes = "may";		break;
			case 6:		mes = "june";		break;
			case 7:		mes = "july";		break;
			case 8:		mes = "august";		break;
			case 9:		mes = "september";	break;
			case 10:	mes = "october";	break;
			case 11:	mes = "november";	break;
			case 12:	mes = "december";	break;
		}		
		return mes;
	}
	
	public static String getMesNombre( int nMes ){		
		String mes = "";
		switch(nMes) {
			case 1:		mes = "January";	break;
			case 2:		mes = "February";	break;
			case 3:		mes = "March";		break;
			case 4:		mes = "April";		break;
			case 5:		mes = "May";		break;
			case 6:		mes = "June";		break;
			case 7:		mes = "July";		break;
			case 8:		mes = "August";		break;
			case 9:		mes = "September";	break;
			case 10:	mes = "October";	break;
			case 11:	mes = "November";	break;
			case 12:	mes = "December";	break;
		}		
		return mes;
	}
	
	public static String getMesNombreCorto( int nMes ){
		String mes = "";
		switch(nMes) {
			case 1:		mes = "Jan";	break;
			case 2:		mes = "Feb";	break;
			case 3:		mes = "May";	break;
			case 4:		mes = "Apr";	break;
			case 5:		mes = "May";	break;
			case 6:		mes = "Jun";	break;
			case 7:		mes = "Jul";	break;
			case 8:		mes = "Aug";	break;
			case 9:		mes = "Sep";	break;
			case 10:	mes = "Oct";	break;
			case 11:	mes = "Nov";	break;
			case 12:	mes = "Dec";	break;
		}		
		return mes;
	}
	
	public static String getFechaConMesCorto( String fecha ){
		String nuevaFecha 	= fecha;		
		String[] partes 	= null;
		if (fecha.contains("/")) partes = fecha.split("/");
		if (fecha.contains("-")) partes = fecha.split("-");
		
		if (partes[1].equals("01")) nuevaFecha = partes[0]+"-Jan-"+partes[2];
		if (partes[1].equals("02")) nuevaFecha = partes[0]+"-Feb-"+partes[2];
		if (partes[1].equals("03")) nuevaFecha = partes[0]+"-May-"+partes[2];
		if (partes[1].equals("04")) nuevaFecha = partes[0]+"-Apr-"+partes[2];
		if (partes[1].equals("05")) nuevaFecha = partes[0]+"-May-"+partes[2];
		if (partes[1].equals("06")) nuevaFecha = partes[0]+"-Jun-"+partes[2];
		if (partes[1].equals("07")) nuevaFecha = partes[0]+"-Jul-"+partes[2];
		if (partes[1].equals("08")) nuevaFecha = partes[0]+"-Aug-"+partes[2];
		if (partes[1].equals("09")) nuevaFecha = partes[0]+"-Sep-"+partes[2];
		if (partes[1].equals("10")) nuevaFecha = partes[0]+"-Oct-"+partes[2];
		if (partes[1].equals("11")) nuevaFecha = partes[0]+"-Nov-"+partes[2];
		if (partes[1].equals("12")) nuevaFecha = partes[0]+"-Dec-"+partes[2];		
			
		return nuevaFecha;
	}

	public String getMes( String Fecha ){
		String s_mes = "";		
		s_mes = Fecha.substring(3,5);		
		return s_mes;
	}	
	public static String getMesActual( String Fecha ){		
		String s_mes = "";		
		s_mes = Fecha.substring(3,5);		
		return s_mes;
	}
	
	public String getYear( String Fecha ){
		String s_year = "";
		
		s_year = Fecha.substring(6,10);
		
		return s_year;
	}
	
	public static String getHoy( ){

		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia = null;
		String mes = null;		
		
		dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		s_fecha = dia+"/"+mes+"/"+Integer.toString(fecha.get(Calendar.YEAR));
		
		return s_fecha;
	}
	
	public static String getEjercicioHoy( ){
		
		String ejercicio = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);		
		ejercicio = "001-"+Integer.toString(fecha.get(Calendar.YEAR));
		
		return ejercicio;
	}
	
	public static Date getFechaHoy(){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaHoy 	= new Date();
		try {
			fechaHoy = formatoFecha.parse(formatoFecha.format(fechaHoy));
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return fechaHoy;
	}	
	
	public static Date getStrToDateddMMyyyy( String fecha){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaHoy 	= new Date();
		try {
			fechaHoy = formatoFecha.parse(fecha);
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return fechaHoy;
	}
	
	public static String getDateToStrddMMyyyy( Date fecha){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fechaStr = ""; 
		try {
			fechaStr = formatoFecha.format(fecha);
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		return fechaStr;
	}
	
	public static Date getStrToDateyyyyMMdd( String fecha){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
		Date fechaHoy 	= new Date();
		try {
		fechaHoy = formatoFecha.parse(fecha);
		} catch (ParseException e) {
            e.printStackTrace();
        }
		
		return fechaHoy;
	}
	
	public static String getHoyReversa( ){
		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia = null;
		String mes = null;		
		
		dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		s_fecha = Integer.toString(fecha.get(Calendar.YEAR))+"/"+mes+"/"+dia;
		
		return s_fecha;
	}
	
	public static String getHoyGuion( ){
		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia = null;
		String mes = null;		
		
		dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		s_fecha = Integer.toString(fecha.get(Calendar.YEAR))+"-"+mes+"-"+dia;
		
		return s_fecha;
	}
	
	public static String getInicioMes( ){
		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia = "01";
		String mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		s_fecha = dia+"/"+mes+"/"+Integer.toString(fecha.get(Calendar.YEAR));
		
		return s_fecha;
	}
	
	public static String getMes( ){
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String mes = null;		
		
		mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		
		return mes;
	}	
	
	public static String getTime( ){
		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia = null;
		String mes = null;		
		
		dia = Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes = Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		
		s_fecha = dia+"/"+mes+"/"+Integer.toString(fecha.get(Calendar.YEAR))+" "+fecha.get(Calendar.HOUR_OF_DAY);
		
		return s_fecha;
	}
	
	public static String getFechayHora( ){
		String s_fecha = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar fecha = new GregorianCalendar(timeZone);
		String dia 		= "";
		String mes 		= "";
		String year		= "";
		String hora		= "";
		String minuto	= "";
		String segundo	= "";
		
		dia 	= Integer.toString(fecha.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes 	= Integer.toString(fecha.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		year 	= Integer.toString(fecha.get(Calendar.YEAR));		
		hora	= Integer.toString(fecha.get(Calendar.HOUR_OF_DAY));
		if (hora.length()==1) hora = "0" + hora;
		minuto	= Integer.toString(fecha.get(Calendar.MINUTE));
		if (minuto.length()==1) minuto = "0" + minuto;
		segundo	= Integer.toString(fecha.get(Calendar.SECOND));
		if (segundo.length()==1) segundo = "0" + segundo;
		
		s_fecha = dia+"/"+mes+"/"+year+" "+hora+":"+minuto+":"+segundo;
		
		return s_fecha;
	}
	
	public static String getHoraDelDia(){	
		return new GregorianCalendar().get(Calendar.HOUR_OF_DAY)+"";
	}
	
	public static String getHora(){	
		return new GregorianCalendar().get(Calendar.HOUR)+"";
	}
	
	public static String getMinutos(){	
		return new GregorianCalendar().get(Calendar.MINUTE)+"";
	}
	
	public static String getSegundos(){	
		return new GregorianCalendar().get(Calendar.SECOND)+"";
	}
	
	public static int getAMPM(){
		return new GregorianCalendar().get(Calendar.AM_PM);
	}
	
	public String getNombreDia(String fecha)throws Exception{
		String dia = "";
		Calendar calendario = new GregorianCalendar();
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fecha));
		switch(calendario.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 		dia = "Sunday";		break;
			case Calendar.MONDAY: 		dia = "Monday";		break;
			case Calendar.TUESDAY:		dia = "Tuesday";	break;
			case Calendar.WEDNESDAY:	dia = "Wednesday";	break;
			case Calendar.THURSDAY:		dia = "Thursday";	break;
			case Calendar.FRIDAY:		dia = "Friday";		break;
			case Calendar.SATURDAY:		dia = "Saturday";	break;
		}
		return dia;
	}
	
	public static int getNumDiaSemanaHoy()throws Exception{
		int dia = 0;
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar calendario = new GregorianCalendar(timeZone);
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(getHoy()));
		switch(calendario.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 		dia = 1;	break;
			case Calendar.MONDAY: 		dia = 2;	break;
			case Calendar.TUESDAY:		dia = 3;	break;
			case Calendar.WEDNESDAY:	dia = 4;	break;
			case Calendar.THURSDAY:		dia = 5;	break;
			case Calendar.FRIDAY:		dia = 6;	break;
			case Calendar.SATURDAY:		dia = 7;	break;
		}
		return dia;
	}
	
	public String getStringFecha(Calendar calendario){
		String fecha = "", dia="",mes="";		
		dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
		if (dia.length() == 1) dia = "0" + dia;
		mes = Integer.toString(calendario.get(Calendar.MONTH)+1);
		if (mes.length() == 1) mes = "0" + mes;
		fecha = dia+"/"+mes+"/"+String.valueOf(calendario.get(Calendar.YEAR));
		return fecha;
		
	}
	
	public ArrayList<String> getSemana(String fechaActual)throws Exception{
		GregorianCalendar calendario = new GregorianCalendar();
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fechaActual));
		ArrayList<String> listor	= new ArrayList<String>();
		listor.add(getStringFecha(calendario));
		for (int i=1;i<7;i++){
			calendario.add(Calendar.DATE,1);
			listor.add(getStringFecha(calendario));
		}
		return listor;
	}
	
	public ArrayList<String> getSemanaActual(){
		GregorianCalendar calendario = new GregorianCalendar();
		//java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		ArrayList<String>listor	= new ArrayList<String>();
		listor.add(getStringFecha(calendario));
		for (int i=1;i<7;i++){
			calendario.add(Calendar.DATE,1);
			listor.add(getStringFecha(calendario));
		}
		return listor;
	}
	
	public String getSemanaAnterior(String fechaInicio)throws Exception{
		GregorianCalendar calendario = new GregorianCalendar();
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fechaInicio));
		calendario.add(Calendar.DATE,-7);
		return getStringFecha(calendario);
	}
	
	public String getSemanaSiguiente(String fechaInicio)throws Exception{
		GregorianCalendar calendario = new GregorianCalendar();
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fechaInicio));
		calendario.add(Calendar.DATE,7);
		
		return getStringFecha(calendario);
	}
	
	public String getDiaSiguiente(String fechaInicio, int dias)throws Exception{
		GregorianCalendar calendario = new GregorianCalendar();
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fechaInicio));
		calendario.add(Calendar.DATE,dias);
		
		return getStringFecha(calendario);
	}
	
	public static int diasEntreFechas(String fechaMenor, String fechaMayor) throws Exception{
		
		String tempMenor[] = fechaMenor.split("/");
		String tempMayor[] = fechaMayor.split("/");
		int dias = 0;	
        
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		// Se agrega para corregir el problema del cambio de horario
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+10"));
		Date dateMenor = dateFormat.parse(tempMenor[2]+"-"+tempMenor[1]+"-"+tempMenor[0]);
		Date dateMayor = dateFormat.parse(tempMayor[2]+"-"+tempMayor[1]+"-"+tempMayor[0]);
		dias=(int) ((dateMayor.getTime() - dateMenor.getTime()) / 86400000); // 60*60*24
		
		return dias; 
	}
	
	public String getFechaTexto(String fecha){
		String texto = "";
		texto = fecha.substring(0,2)+" de "+ getMesNombre(fecha)+" de "+fecha.substring(6);
		return texto;
	}
	
	public static String getPeriodoActual(){
		String hoy = getHoy();
		Fecha fecha = new Fecha();
		String texto="";
		
		if(Integer.parseInt(fecha.getMes(hoy)) < 8){
			texto = (Integer.parseInt(fecha.getYear(hoy))-1) + "-" +fecha.getYear(hoy);
		}
		if(Integer.parseInt(fecha.getMes(hoy)) == 8){
			if(Integer.parseInt(fecha.getDia(hoy)) < 10){
				texto = (Integer.parseInt(fecha.getYear(hoy))-1) + "-" +fecha.getYear(hoy);
			}else{
				texto =  fecha.getYear(hoy) + "-" + (Integer.parseInt(fecha.getYear(hoy))+1);
			}
		}
		if(Integer.parseInt(fecha.getMes(hoy)) > 8){
			texto =  fecha.getYear(hoy) + "-" + (Integer.parseInt(fecha.getYear(hoy))+1);
		}
		
		return texto;
	}
	
	public static String getPeriodoMes(String fecha){
		Fecha date = new Fecha();
		String texto="";
		
		if(Integer.parseInt(date.getMes(fecha)) >= 1 && Integer.parseInt(date.getMes(fecha)) <= 6){
			if(Integer.parseInt(date.getMes(fecha)) == 6){
				if(Integer.parseInt(date.getDia(fecha)) < 15){
					texto = "january to may";
				}
			}else{
				texto = "january to may";
			}
		}
		
		if(Integer.parseInt(date.getMes(fecha)) >= 6 && Integer.parseInt(date.getMes(fecha)) <= 8){
			if(Integer.parseInt(date.getMes(fecha)) == 6){
				if(Integer.parseInt(date.getDia(fecha)) >= 15){
					texto = "june to august";
				}
			}
			if(Integer.parseInt(date.getMes(fecha)) == 8){
				if(Integer.parseInt(date.getDia(fecha)) <= 4){
					texto = "june to august";
				}
			}else{
				texto = "june to august";
			}
		}
		
		if(Integer.parseInt(date.getMes(fecha)) >= 8 && Integer.parseInt(date.getMes(fecha)) <= 12){
			if(Integer.parseInt(date.getMes(fecha)) == 8){
				if(Integer.parseInt(date.getDia(fecha)) >= 5){
					texto = "august to december";
				}
			}else{
				texto = "august to december";
			}
		}		
		return texto;
	}
	
	public static int getDiasEntreFechas(String strFecha1, String strFecha2){
		long diferencia = 0;
		int dias		= 0;		
		try{
			java.util.Date fecha1 = new java.util.Date();
			java.util.Date fecha2 = new java.util.Date();			
		    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		    fecha1 		= sdf.parse(strFecha1);
		    fecha2		= sdf.parse(strFecha2);
		    diferencia 	= fecha2.getTime()-fecha1.getTime();
		    dias 		= (int)Math.floor(diferencia / (1000 * 60 * 60 * 24));		    
		}catch (java.text.ParseException ex){
			System.out.println("Error in date format:"+ex);
		}	    
		return dias; 
	}
	
	public static String getMontName( String Fecha ){
		int nmes = Integer.parseInt(Fecha.substring(3,5));
		String mes = "";
		switch(nmes) {
			case 1:		mes = "january";		break;
			case 2:		mes = "february";	break;
			case 3:		mes = "march";		break;
			case 4:		mes = "april";		break;
			case 5:		mes = "may";		break;
			case 6:		mes = "june";		break;
			case 7:		mes = "july";		break;
			case 8:		mes = "august";		break;
			case 9:		mes = "september";	break;
			case 10:	mes = "october";	break;
			case 11:	mes = "november";	break;
			case 12:	mes = "december";	break;
		}		
		return mes;
	}
	
	public static String getDayName(String fecha)throws Exception{
		String dia = "";
		TimeZone timeZone = TimeZone.getTimeZone("GMT+10");
		Calendar calendario = new GregorianCalendar(timeZone);
		java.text.DateFormat datef = java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT,new Locale("es","SPANISH"));
		calendario.setTime(datef.parse(fecha));
		switch(calendario.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY: 		dia = "Sunday";		break;
			case Calendar.MONDAY: 		dia = "Monday";		break;
			case Calendar.TUESDAY:		dia = "Tuesday";	break;
			case Calendar.WEDNESDAY:	dia = "Wednesday";	break;
			case Calendar.THURSDAY:		dia = "Thursday";	break;
			case Calendar.FRIDAY:		dia = "Friday";		break;
			case Calendar.SATURDAY:		dia = "Saturday";	break;
		}
		return dia;
	}
	
	public static String getNextFecha( String fecha, int meses){
		String dia 			= fecha.substring(0,2);
		String mes			= fecha.substring(3,5);
		int numMes 			= Integer.parseInt(mes);
		int year 			= Integer.parseInt(fecha.substring(6,10));		
		numMes = numMes+meses;
		if (numMes>12){
			year = year+1;
			numMes=numMes-12;
		}		
		mes = String.valueOf(numMes);
		if (mes.length()==1) mes = "0" + mes;		
		String nuevaFecha	= dia+"/"+mes+"/"+String.valueOf(year);		
		return nuevaFecha;
	}
	
	public static boolean getFechaBetween(String fechaInicio, String fechaFinal){
		
		boolean respuesta	= false;
		Date hoy 			= new Date();
		try{			
			java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
			Date fechaIni 	= formatoFecha.parse(fechaInicio);
			Date fechaFin 	= formatoFecha.parse(fechaFinal);
			if (hoy.compareTo(fechaIni)>= 0 && hoy.compareTo(fechaFin) <= 0)
				respuesta = true;		
		}catch (java.text.ParseException ex){
			System.out.println("Error in date format:"+ex);
		}			
		return respuesta;
	}
	
	public static boolean getFechaBetweenFecha(String fechaInicio, String fechaFinal, String fecha) {
		boolean respuesta = false;        
		try {            
			java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
			Date hoy = formatoFecha.parse(fecha);
			Date fechaIni = formatoFecha.parse(fechaInicio);
			Date fechaFin = formatoFecha.parse(fechaFinal);
	
			// Ensure fechaInicio is before or equal to fechaFinal
			if (fechaIni.compareTo(fechaFin) > 0) {
				System.out.println("Invalid date range: fechaInicio is after fechaFinal");
				return false;
			}
	
			if (hoy.compareTo(fechaIni) >= 0 && hoy.compareTo(fechaFin) <= 0) {
				respuesta = true;
			}        
		} catch (java.text.ParseException ex) {
			System.out.println("Error in date format: " + ex);
		}        
		return respuesta;
	}
	
	public static boolean getFechaMayorQueHoy(String fechaData){
		
		boolean respuesta	= false;		
		try{			
			java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
			Date fecha	 	= formatoFecha.parse(fechaData);
			Date hoy 		= formatoFecha.parse(getHoy());
			if (fecha.after(hoy)) respuesta = true;
			
		}catch (java.text.ParseException ex){
			System.out.println("Error in date format:"+ex);
		}		
			
		return respuesta;
	}
	
	public static boolean getFechaMayorIgualQueHoy(String fechaInicio){
		
		boolean respuesta	= false;
		Date hoy 			= new Date();
		try{			
			java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy");
			Date fechaIni 	= formatoFecha.parse(fechaInicio);
			if (fechaIni.compareTo(hoy) >= 0)
				respuesta = true;		
		}catch (java.text.ParseException ex){
			System.out.println("Error in date format:"+ex);
		}		
			
		return respuesta;
	}
	
	public static int getDiaSemana(){
		java.util.Date hoy 	= new java.util.Date();
	    int numeroDia		= 0;
	    Calendar cal		= Calendar.getInstance();	    
	    cal.setTime(hoy);
	    numeroDia = cal.get(Calendar.DAY_OF_WEEK);	    
	    return numeroDia;
	}
	
	public static int getDiaSemana(String fecha){
		
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		int numeroDia		= 0;
		
		try{
			java.util.Date hoy 	= sdf.parse(fecha);
			
			Calendar cal		= Calendar.getInstance();	    
			cal.setTime(hoy);
			numeroDia = cal.get(Calendar.DAY_OF_WEEK);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
	    return numeroDia;
	}
	
	public static int edad(String fechaNac, String fechaHoy) {	 	
 	    String[] dat1 = fechaNac.split("/");
 	    String[] dat2 = fechaHoy.split("/");
 	    int anos = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
 	    int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
 	    if (mes < 0) {
 	      anos = anos - 1;
 	    } else if (mes == 0) {
 	      int dia = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
 	      if (dia > 0) {
 	        anos = anos - 1;
 	      }
 	    }
 	    return anos;
	}
	
	public static String edadCompleta(String fechaNac, String fechaHoy) {
		String edadCompleta = "";
		LocalDate birthdate = LocalDate.of(1970, 1, 20);
		LocalDate now = LocalDate.now();
		Period periodo = Period.between(birthdate, now);
		edadCompleta = "Years:"+periodo.getYears()+" Months:"+periodo.getMonths()+" Days:"+periodo.getDays();
		
 	    return edadCompleta;
	}
	
	public static Date sumarDiasAFecha(Date fecha, int dias){
	      if (dias==0) return fecha;
	      Calendar calendar = Calendar.getInstance();
	      calendar.setTime(fecha); 
	      calendar.add(Calendar.DAY_OF_YEAR, dias);  
	      return calendar.getTime(); 
	}
	
	public static String getDiaNombre(int dia) {		
		String nombre = "";
		switch(dia) {
			case 0: nombre = "Sunday";		break;
			case 1: nombre = "Monday";		break;
			case 2:	nombre = "Tuesday";		break;
			case 3:	nombre = "Wednesday";	break;
			case 4:	nombre = "Thursday";	break;
			case 5: nombre = "Friday";		break;
			case 6: nombre = "Saturday";	break;
			case 7: nombre = "Other";		break;
		}	
 	    return nombre;
	}
	
}