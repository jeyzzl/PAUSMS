<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.padre.spring.PadrePersonal"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.menu.spring.ModuloAyuda"%>

<%
	Integer accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));	
	String charset 				= "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>";	
	
	ModuloAyuda ayuda 			=(ModuloAyuda) request.getAttribute("ayuda");
	AlumPersonal alumPersonal	=(AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico	=(AlumAcademico) request.getAttribute("alumAcademico");
	PadrePersonal padre			=(PadrePersonal) request.getAttribute("padre");
	Maestros maestro			=(Maestros) request.getAttribute("maestro");
	String carrera				= (String) request.getAttribute("carrera");
	String religion				= (String) request.getAttribute("religion");
	String edad					= (String) request.getAttribute("edad");
	String inscrito				= (String) request.getAttribute("inscrito");
	
	//String charset = "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"></head>";
	//String contAyuda	= request.getParameter("contAyuda");

	switch(accion){
		case 5:{			
%>			
			muestraAyuda('<%=ayuda.getAyuda().equals("")?"<b>Error:</b> No existe ayuda para este objeto. Porfavor reportelo a sistemas Ext. 1059":ayuda.getAyuda() %>');
			//posicionaAyuda(event);
<%			
		}break;
		case 6:{//Consultar al alumno
			String matricula	= request.getParameter("matricula");
			if(matricula.substring(0,1).equals("9")){//Si es empleado				
%>
				muestraAyuda('<%=charset %><table style="width:100%">'+
								'<tr>'+
									'<td align="center">'+
										'<img src="foto?Codigo=<%=matricula%>&Tipo=O" width="98px" /><br>'+
										'<b><%=matricula %></b>'+
									'</td>'+
									'<td>'+
										'<table style="width:100%">'+
											'<tr>'+
												'<td><b><%=maestro.getNombre().replaceAll("'"," ")%> <%=maestro.getApellidoPaterno().replaceAll("'"," ")%> <%=maestro.getApellidoMaterno().replaceAll("'"," ")%></b></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=maestro.getfNacimiento()%></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=maestro.getEstado().equals("A")?"Activo":maestro.getEstado().equals("I")?"Inactivo":maestro.getEstado() %></td>'+
											'</tr>'+											
										'</table>'+
									'</td>'+
								'</tr>'
							 );
<%
			}else if(matricula.substring(0,1).equals("P")){
				
				String tipo = "-";
				if (padre.getTipo().equals("P")) 
					tipo = "Padre";
				else if (padre.getTipo().equals("M"))
					tipo = "Madre";
				else
					tipo = "Tutor";
%>
				muestraAyuda('<%=charset %><table style="width:100%">'+
								'<tr>'+
									'<td align="center">'+
										'<img src="foto?Codigo=<%=matricula%>&Tipo=O" width="98px" /><br>'+
										'<b><%=matricula %></b>'+
									'</td>'+
									'<td>'+
										'<table style="width:100%">'+
											'<tr>'+
												'<td><b><%=padre.getNombre().replaceAll("'","´")%> <%=padre.getPaterno().replaceAll("'","´")%> <%=padre.getMaterno().replaceAll("'","´")%></b></td>'+
											'</tr>'+											
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=padre.getCorreo() %></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=padre.getTelefono() %></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=tipo%></td>'+
											'</tr>'+
										'</table>'+
									'</td>'+
								'</tr>'
							 );
<%				
			}else{//Si es alumno			
%>
				muestraAyuda('<%=charset %><table style="width:100%">'+
								'<tr>'+
									'<td align="center">'+
										'<img src="foto?Codigo=<%=matricula%>&Tipo=O" width="98px" /><br>'+
										'<b><%=matricula %></b>'+
									'</td>'+
									'<td>'+
										'<table style="width:100%">'+
											'<tr>'+
												'<td><b><%=alumPersonal.getNombre().replaceAll("'","´")%> <%=alumPersonal.getApellidoPaterno().replaceAll("'","´")%> <%=alumPersonal.getApellidoMaterno().replaceAll("'","´")%></b></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=carrera%></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=religion%></td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=edad%> años (<%=alumPersonal.getFNacimiento()%>)</td>'+
											'</tr>'+
											'<tr>'+
												'<td style="border-top: dotted 1px gray;"><%=alumAcademico.getResidenciaId().equals("E")?"Externo":"Interno" %>, <%=inscrito%></td>'+
											'</tr>'+
										'</table>'+
									'</td>'+
								'</tr>'
							 );
<%
			}
		}break;
	}	
%>