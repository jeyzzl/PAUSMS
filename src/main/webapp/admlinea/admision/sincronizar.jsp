<%@ include file= "../../con_enoc.jsp" %>

<%@ page import= "java.security.MessageDigest" %>
<%@ page import= "sun.misc.BASE64Encoder"%>
<%@ page import= "aca.util.Fecha"%>
<%@ page import="aca.tools.Curp"%>

<jsp:useBean id="Academico" scope="page" class="aca.admision.AdmAcademico" />
<jsp:useBean id="Solicitud" scope="page" class="aca.admision.AdmSolicitud" />
<jsp:useBean id="Ubicacion" scope="page" class="aca.admision.AdmUbicacion" />
<jsp:useBean id="UbicacionU" scope="page" class="aca.admision.AdmUbicacionUtil" />
<jsp:useBean id="Salud" scope="page" class="aca.admision.AdmSalud" />
<jsp:useBean id="AdmSaludU" scope="page" class="aca.admision.AdmSaludUtil" />
<jsp:useBean id="Padres" scope="page" class="aca.admision.AdmPadres" />
<jsp:useBean id="PadresU" scope="page" class="aca.admision.AdmPadresUtil" />
<jsp:useBean id="Tutor" scope="page" class="aca.admision.AdmTutor" />
<jsp:useBean id="TutorU" scope="page" class="aca.admision.AdmTutorUtil" />

<jsp:useBean id="AcaPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AcaAcceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>
<jsp:useBean id="AcaUbicacion" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="AcaUbicacionU" scope="page" class="aca.alumno.UbicacionUtil"/>
<jsp:useBean id="AcaAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcaAcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
 <jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="AdmProceso" scope="page" class="aca.admision.AdmProceso" />

<%
	if(request.getParameter("Accion")!=null){
		AcaPersonal.setCodigoPersonal(request.getParameter("Matricula"));
		if(AlumUtil.existeReg(conEnoc, request.getParameter("Matricula"))){ %>
			muestraImagenMatricula('6', '3');
	<%	}
		else{ %>
			muestraImagenMatricula('6', '2');
	<%	}
	}
	else{
		String usuario			= (String)session.getAttribute("codigoPersonal");
		String folio 			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
		String claveHexa		= (String)request.getAttribute("claveHexa"); 
		
		AdmProceso.setFolio(folio);		
		AdmProceso.updateFecha(conEnoc, 4);
		
		Academico.mapeaRegId(conEnoc, folio);		
		Solicitud.mapeaRegId(conEnoc, folio);		
		Ubicacion 	= UbicacionU.mapeaRegId(conEnoc, folio);		
		Padres 		= PadresU.mapeaRegId(conEnoc, folio);		
		Tutor 		= TutorU.mapeaRegId(conEnoc, folio);		
		conEnoc.setAutoCommit(false);
		
		String clave 	= "";
		String codPers 	= "";		
	%>
		muestraImagen('1', '1', '');
	<%
		String apellidoPaterno = Solicitud.getApellidoPaterno()==null ? " " : (Solicitud.getApellidoPaterno().trim().equals("") ? " " : Solicitud.getApellidoPaterno());
		String apellidoMaterno = Solicitud.getApellidoMaterno()==null ? " " : (Solicitud.getApellidoMaterno().trim().equals("") ? " " : Solicitud.getApellidoMaterno());
	
		AcaPersonal.setCodigoPersonal(request.getParameter("Matricula"));
		codPers = AcaPersonal.getCodigoPersonal();
		AcaPersonal.setNombre(Solicitud.getNombre().trim().toUpperCase());
		AcaPersonal.setApellidoPaterno(apellidoPaterno.toUpperCase());
		AcaPersonal.setApellidoMaterno(apellidoMaterno.toUpperCase());
		AcaPersonal.setNombreLegal(Solicitud.getNombre().trim().toUpperCase()+" "+apellidoPaterno.toUpperCase()+" "+apellidoMaterno.toUpperCase());
		AcaPersonal.setFNacimiento(Solicitud.getFechaNac());
		AcaPersonal.setSexo(Solicitud.getGenero());
		AcaPersonal.setEstadoCivil(Solicitud.getEstadoCivil());
		AcaPersonal.setReligionId(Solicitud.getReligionId());
		if(AcaPersonal.getReligionId().equals("1")){
			AcaPersonal.setBautizado(Solicitud.getBautizado());
		}
		AcaPersonal.setPaisId(Solicitud.getPaisId());
		AcaPersonal.setEstadoId(Solicitud.getEstadoId());
		AcaPersonal.setCiudadId(Solicitud.getCiudadId());
		AcaPersonal.setNacionalidad(Solicitud.getNacionalidad());
		AcaPersonal.setEmail(Solicitud.getEmail().trim());
		
		AcaPersonal.setCurp(Curp.getCurp(Solicitud.getNombre().trim(), apellidoPaterno, apellidoMaterno, Solicitud.getGenero(),
				Solicitud.getFechaNac(), Integer.parseInt(Solicitud.getPaisId()), Integer.parseInt(Solicitud.getEstadoId())));
		AcaPersonal.setCotejado("N");
		
		//Establecemos la clave		 
		String claveDigest	= aca.util.Encriptar.sha512ConBase64(request.getParameter("Matricula"));
		
		AcaAcceso.setCodigoPersonal(codPers);
		AcaAcceso.setAdministrador("N");
		AcaAcceso.setAccesos("X");
		AcaAcceso.setCotejador("N");
		AcaAcceso.setSupervisor("N");
		AcaAcceso.setPortalAlumno("N");
		AcaAcceso.setPortalMaestro("N");
		AcaAcceso.setModalidad("0");
		AcaAcceso.setUsuario(codPers);
		AcaAcceso.setClave(claveDigest);
		AcaAcceso.setIdioma("es");
		AcaAcceso.setMenu("1");
		AcaAcceso.setClaveInicial(request.getParameter("Matricula"));		
		
		AcaPersonal.setEstado("A");
		AcaPersonal.setFCreado(aca.util.Fecha.getHoy());
		AcaPersonal.setUsAlta(usuario);
		
		if(!AlumUtil.existeReg(conEnoc, codPers)){
			if(AlumUtil.insertReg(conEnoc, AcaPersonal)){%>
				setTimeout("muestraImagen('1', '2', '');", 1000);
			<%	if(AccesoU.insertReg(conEnoc, AcaAcceso)){%>
					setTimeout("muestraImagen('2', '1', '');", 1000);
				<%	AcaUbicacion.setCodigoPersonal(codPers);
					AcaUbicacion.setPNombre(Padres.getPadreApellido().trim().toUpperCase()+" "+Padres.getPadreNombre().trim().toUpperCase());
					AcaUbicacion.setPReligion(Padres.getPadreReligion());
					AcaUbicacion.setPNacionalidad(Padres.getPadreNacionalidad());
					AcaUbicacion.setMNombre(Padres.getMadreApellido().trim().toUpperCase()+" "+Padres.getMadreNombre().trim().toUpperCase());
					AcaUbicacion.setMReligion(Padres.getMadreReligion());
					AcaUbicacion.setMNacionalidad(Padres.getMadreNacionalidad());%>
					setTimeout("muestraImagen('3', '1', '');", 2000);
				<%	if(Tutor.getTutor().equals("0")){
						AcaUbicacion.setTNombre(Padres.getPadreApellido().trim().toUpperCase()+" "+Padres.getPadreNombre().trim().toUpperCase());
					}
					else if(Tutor.getTutor().equals("1")){
						AcaUbicacion.setTNombre(Padres.getMadreApellido().trim().toUpperCase()+" "+Padres.getMadreNombre().trim().toUpperCase());
					}
					else if(Tutor.getTutor().equals("3")){
						AcaUbicacion.setTNombre(Tutor.getNombre().trim().toUpperCase());
					}
					if(Tutor.getTutor().equals("2")){
						AcaUbicacion.setTNombre(apellidoPaterno.toUpperCase()+" "+apellidoMaterno.toUpperCase()+" "+Solicitud.getNombre().trim().toUpperCase());
					}
					
					AcaUbicacion.setTDireccion(Tutor.getCalle().trim().toUpperCase()+" #"+Tutor.getNumero().trim());
					AcaUbicacion.setTColonia(Tutor.getColonia().trim().toUpperCase());
					AcaUbicacion.setTCodigo(Tutor.getCodigoPostal().trim());
					AcaUbicacion.setTTelefono(Tutor.getTelefono().trim());
					AcaUbicacion.setTPais(Tutor.getPaisId());
					AcaUbicacion.setTEstado(Tutor.getEstadoId());
					AcaUbicacion.setTCiudad(Tutor.getCiudadId());		
					
					AcaUbicacion.setTApartado("");
					AcaUbicacion.settCelular("-");
					AcaUbicacion.settComunica("E");
					
					if(AcaUbicacionU.insertReg(conEnoc, AcaUbicacion)){%>
						setTimeout("muestraImagen('2', '2', '');", 1000);
						setTimeout("muestraImagen('3', '2', '');", 1000);
						setTimeout("muestraImagen('4', '1', '');", 1000);
					<%	
						AcaAcademico.setCodigoPersonal(codPers);
						AcaAcademico.setTipoAlumno("1");
						AcaAcademico.setClasFin("1");
						AcaAcademico.setObrero("N");
						AcaAcademico.setObreroInstitucion("0");
						// Cambiado por covid a externo
						AcaAcademico.setResidenciaId("I");
						AcaAcademico.setRequerido("S");
						AcaAcademico.setDormitorio("0");
						AcaAcademico.setFSolicitud(Solicitud.getFecha());
						AcaAcademico.setFAdmision(Fecha.getHoy());
						AcaAcademico.setFAlta("01/01/1900");
						AcaAcademico.setModalidadId(Academico.getModalidad());
						AcaAcademico.setExtensionId("99");
						AcaAcademico.setPrepaLugar("0");
						
						if(AcademicoU.insertReg(conEnoc, AcaAcademico)){%>
							setTimeout("muestraImagen('4', '2', '<%=folio %>');", 2500);
						<%	Solicitud.setMatricula(codPers);
							Solicitud.setEstado("4");
							Solicitud.updateReg(conEnoc);
						}
						else{%>
							muestraImagen('4', '3', '');
						<%	conEnoc.rollback();
						}
					}
					else{%>
						muestraImagen('2', '3', '');
						muestraImagen('3', '3', '');
					<%	conEnoc.rollback();
					}
				}
				else{%>
					muestraImagen('1', '3', '');
				<%	conEnoc.rollback();
				}
			}
			else{%>
				muestraImagen('1', '3', '');
			<%	conEnoc.rollback();
			}
		}
		else{%>
			muestraImagen('1', '3', '');
		<%	conEnoc.rollback();
		}
		conEnoc.setAutoCommit(true);
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>