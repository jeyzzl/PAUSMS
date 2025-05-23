<%if(session.getAttribute("codigoPersonal").equals(session.getAttribute("codigoAlumno"))){%>
	<%@ include file= "../../con_enoc.jsp" %>
	<%@ include file="../../idioma.jsp"%>
	
	<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
	<jsp:useBean id="AlumPersonal2" scope="page" class="aca.alumno.AlumPersonal"/>
	<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
	<jsp:useBean id="AlumHermanosU" scope="page" class="aca.alumno.AlumHermanosUtil"/>
	<jsp:useBean id="AlumFamiliaU" scope="page" class="aca.alumno.AlumFamiliaUtil"/>
	<%
		String accion = request.getParameter("Accion");
		String matriculaSesion = session.getAttribute("codigoAlumno").toString();
		
		%>var content = "<head><meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'></head>";<%
		if(accion.equals("1")){
			AlumPersonal.setCodigoPersonal(request.getParameter("Matricula"));
			if(request.getParameter("Matricula").equals(matriculaSesion)){%>
				activarAgregar(false);
				mensaje(content+"<font size='2' color='#AE2113'>No puedes asignarte a ti como hermano</font>");
		<%	}
			else{
				if(AlumUtil.existeReg(conEnoc,request.getParameter("Matricula"))){
					AlumPersonal = AlumUtil.mapeaRegId(conEnoc, request.getParameter("Matricula"));
					AlumPersonal2 = AlumUtil.mapeaRegId(conEnoc, matriculaSesion);
										
					if(AlumPersonal.getApellidoPaterno().trim().equals(AlumPersonal2.getApellidoPaterno().trim())&&
						(AlumPersonal.getApellidoMaterno()==null&&AlumPersonal2.getApellidoMaterno()==null||
							AlumPersonal.getApellidoMaterno().trim().equals(AlumPersonal2.getApellidoMaterno().trim()))){
							String [] arr = request.getParameter("Mats").split("-");
							boolean tieneAsignado = false;
							for(String x : arr){
								if(AlumPersonal.getCodigoPersonal().equals(x)){
									tieneAsignado = true;
									break;
								}
							}
							if(!tieneAsignado){%>
								activarAgregar(true);
								mensaje(content+"<font size='2' color='green'><b><%=AlumPersonal.getNombreLegal()%></b></font>");
						<%	}
							else{%>
								activarAgregar(false);
								mensaje(content+"<font size='2' color='#AE2113'>Ya tienes asignado este hermano</font>");
						<%	}
					}
					else{%>
						mensaje(content+"<font size='2' color='#AE2113'>El alumno encontrado <b>no coincide con tus apellidos</b>,<br>repórtalo si los datos son incorrectos.</font>");
				<%	}%>
			<%	}
				else{ %>
					activarAgregar(false);
					mensaje(content+"<font size='2' color='#AE2113'>El alumno no existe</font>");
			<%	}
			}
		}
		else if(accion.equals("2")){
			AlumPersonal = AlumUtil.mapeaRegId(conEnoc, request.getParameter("Matricula"));
			AlumPersonal2 = AlumUtil.mapeaRegId(conEnoc, matriculaSesion);
			if(AlumPersonal.getApellidoPaterno().equals(AlumPersonal2.getApellidoPaterno())&&
				((AlumPersonal.getApellidoMaterno().trim()==null&&AlumPersonal2.getApellidoMaterno().trim()==null))||
					AlumPersonal.getApellidoMaterno().equals(AlumPersonal2.getApellidoMaterno())){
					conEnoc.setAutoCommit(false);
					aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
					AlumHermanos.mapeaRegIdPorMatricula(conEnoc, request.getParameter("Matricula"));
					if(request.getParameter("Size").equals("0")&&!AlumHermanos.getCodigoPersonal().equals("")){
						AlumHermanos.setFamiliaId(AlumHermanos.getFamiliaId());
						AlumHermanos.setCodigoPersonal(matriculaSesion);
						AlumHermanos.setEstado("A");
						if(!AlumHermanosU.insertReg(conEnoc, AlumHermanos)){
							conEnoc.rollback();
							%>Error<%
						}
					}
					else if(request.getParameter("Size").equals("0")){
						aca.alumno.AlumFamilia AlumFamilia = new aca.alumno.AlumFamilia();
						AlumFamilia.setFamiliaId(AlumFamiliaU.maximoReg(conEnoc));
						AlumFamilia.setEstado("I");
						if(AlumFamiliaU.insertReg(conEnoc, AlumFamilia)){
							AlumHermanos.setFamiliaId(AlumFamilia.getFamiliaId());
							AlumHermanos.setCodigoPersonal(matriculaSesion);
							AlumHermanos.setEstado("A");
							if(AlumHermanosU.insertReg(conEnoc, AlumHermanos)){
								AlumHermanosU.updateReg(conEnoc, AlumHermanos);
								AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
								AlumHermanos.setCodigoPersonal(request.getParameter("Matricula"));
								AlumHermanos.setEstado("E");
								if(!AlumHermanosU.insertReg(conEnoc, AlumHermanos)){
									conEnoc.rollback();
									%>Error<%
								}
							}
							else{
								conEnoc.rollback();
								%>Error<%
							}
						}
						else{
							conEnoc.rollback();
							%>Error<%
						}
					}
					else{
						AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
						AlumHermanos.setCodigoPersonal(request.getParameter("Matricula"));
						AlumHermanos.setEstado("E");
						if(!AlumHermanosU.insertReg(conEnoc, AlumHermanos)){
							conEnoc.rollback();
							%>Error<%
						}
						else{
							aca.alumno.AlumFamilia AlumFamilia = new aca.alumno.AlumFamilia();
							AlumFamilia.mapeaRegId(conEnoc, AlumHermanos.getFamiliaId());
							AlumFamilia.setEstado("I");
							if(!AlumFamiliaU.updateReg(conEnoc, AlumFamilia)){
								conEnoc.rollback();
								%>Error<%
							}
						}
					}
					conEnoc.commit();
					conEnoc.setAutoCommit(true);
			}
		}
		else if(accion.equals("3")){
			aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
			AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
			AlumHermanos.setEstado("E");
			if(!AlumHermanosU.updateReg(conEnoc, AlumHermanos)){
				%>Error<%
			}
		}
		else if(accion.equals("4")){
			conEnoc.setAutoCommit(false);
			aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
			AlumHermanos.mapeaRegIdPorMatricula(conEnoc, matriculaSesion);
			
			aca.alumno.AlumHermanosUtil AlumHermanosUtil = new aca.alumno.AlumHermanosUtil();
			ArrayList<aca.alumno.AlumHermanos> listaHermanos = AlumHermanosUtil.getListAll(conEnoc, "WHERE FAMILIA_ID='"+AlumHermanos.getFamiliaId()+"'");

			AlumHermanos.setEstado("A");
			if(!AlumHermanosU.updateReg(conEnoc, AlumHermanos)){
				conEnoc.rollback();
				%>Error<%
			}
			else{
				int autorizados = 0;
				for(aca.alumno.AlumHermanos alumHermano : listaHermanos){
					if(alumHermano.getEstado().equals("A")) autorizados++;
				}
				if(autorizados+1==listaHermanos.size()){
					aca.alumno.AlumFamilia AlumFamilia = new aca.alumno.AlumFamilia();
					AlumFamilia.mapeaRegId(conEnoc, AlumHermanos.getFamiliaId());
					AlumFamilia.setEstado("A");
					if(!AlumFamiliaU.updateReg(conEnoc, AlumFamilia)){
						conEnoc.rollback();
						%>Error<%
					}
				}
			}
			conEnoc.commit();
			conEnoc.setAutoCommit(true);
		}
		else if(accion.equals("5")){
			conEnoc.setAutoCommit(false);
			aca.alumno.AlumHermanos AlumHermanos = new aca.alumno.AlumHermanos();
			AlumHermanos.mapeaRegIdPorMatricula(conEnoc, request.getParameter("Matricula"));
			if(AlumHermanosU.deleteReg(conEnoc, AlumHermanos.getFamiliaId(), request.getParameter("Matricula"))){
				ArrayList<aca.alumno.AlumHermanos> listaHermanos = new aca.alumno.AlumHermanosUtil().getListAll(conEnoc, "WHERE FAMILIA_ID='"+AlumHermanos.getFamiliaId()+"'");
				if(listaHermanos.size()==1){
					if(AlumHermanosU.deleteReg(conEnoc, listaHermanos.get(0).getFamiliaId(), listaHermanos.get(0).getCodigoPersonal())){
						aca.alumno.AlumFamilia AlumFamilia = new aca.alumno.AlumFamilia();
						AlumFamilia.setFamiliaId(AlumHermanos.getFamiliaId());
						if(!AlumFamiliaU.deleteReg(conEnoc, AlumHermanos.getFamiliaId())){
							conEnoc.rollback();
							%>Error<%						
						}
					}
					else{
						conEnoc.rollback();
						%>Error<%
					}
				}
			}
			else{
				conEnoc.rollback();
				%>Error<%
			}
			conEnoc.commit();
			conEnoc.setAutoCommit(true);
		}
%>
	<%@ include file= "../../cierra_enoc2.jsf" %>
<%
}
%>