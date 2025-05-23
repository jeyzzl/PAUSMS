<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AfeAcuerdos" class="aca.afe.FesCcAfeAcuerdos" scope="page"/>
<jsp:useBean id="AfeAcuerdosU" class="aca.afe.FesCcAfeAcuerdosUtil" scope="page"/>
<jsp:useBean id="BecPuestoAlumno" class="aca.bec.BecPuestoAlumno" scope="page"/>
<jsp:useBean id="BecAcuerdo" class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="BecAcuerdoU" class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="BecTipo" class="aca.bec.BecTipo" scope="page"/>
<jsp:useBean id="BecCategoria" class="aca.bec.BecCategoria" scope="page"/>
<jsp:useBean id="BecCategoriaU" class="aca.bec.BecCategoriaUtil" scope="page"/>
<jsp:useBean id="Inscritos" class="aca.vista.Inscritos" scope="page"/>
<jsp:useBean id="InscritosU" scope="page" class="aca.vista.InscritosUtil" />
<jsp:useBean id="lBeca" class="aca.log.LogBeca" scope="page"/>

<head></head>
<%
	String usuario 			= (String) session.getAttribute("codigoPersonal");
	String idEjercicio 		= (String) session.getAttribute("ejercicioId");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
		
	String matricula		= request.getParameter("matricula")==null?"0":request.getParameter("matricula");
	String puestoId			= request.getParameter("PuestoId")==null?"0":request.getParameter("PuestoId");
	String folio 			= request.getParameter("folio")==null?"0":request.getParameter("folio");
	String ensenanza		= request.getParameter("ensenanza")==null?"0":request.getParameter("ensenanza");
	String internado		= request.getParameter("internado")==null?"0":request.getParameter("internado");
	
	double totalBeca		= Double.valueOf(matricula)+Double.valueOf(ensenanza)+Double.valueOf(internado);
	
	if(folio == null){
		response.sendRedirect("contrato");
	}
	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String msj 				= "";
	
	if(accion.equals("1")){
		//GRABAR
		//MAPEA SU INFORMACION DE LA VISTA DE INSCRITOS (EN ENOC)
		Inscritos = InscritosU.mapeaRegId(conEnoc, codigoAlumno);
		
		//MAPEA SU INFORMACION DE BEC_ACUERDO (EN ENOC)
		BecAcuerdo.setFolio(folio);
		BecAcuerdo.setCodigoPersonal(codigoAlumno);
		BecAcuerdo.setPuestoId(puestoId);
		BecAcuerdo = BecAcuerdoU.mapeaRegPuestoId(conEnoc, folio, codigoAlumno, puestoId);
		//MAPEA LA INFORMACION DE BEC_TIPO DE SU TIPO (EN ENOC)
		BecTipo.setIdEjercicio(idEjercicio);
		BecTipo.setTipo(BecAcuerdo.getTipo());
		BecTipo.mapeaRegId(conEnoc);
		//MAPEA LA INFORMACION DE BEC_PUESTO_ALUMNO DE EL ALUMNO (EN ENOC)
		BecPuestoAlumno.setPuestoId( BecAcuerdo.getPuestoId() );
		BecPuestoAlumno.mapeaRegIdPuesto(conEnoc, puestoId);
		
		//MAPEA LA INFROMACION DE CATEGORIA DEL ALUMNO (EN ENOC)
		BecCategoria = BecCategoriaU.mapeaRegId(conEnoc, BecPuestoAlumno.getCategoriaId());		
		
		//SET LA INFORMACION PARA GRABAR EN MATEO
		AfeAcuerdos.setMatricula( codigoAlumno );
		AfeAcuerdos.setAlpuestoPuestoId( puestoId );
		AfeAcuerdos.setAcuerdoFolio( folio );
		AfeAcuerdos.setCargaId( Inscritos.getCargaId() );
		AfeAcuerdos.setBloque( Inscritos.getBloqueId() );
		AfeAcuerdos.setTipoId( BecTipo.getTipo() );
		AfeAcuerdos.setTipoNombre( BecTipo.getNombre() );
		AfeAcuerdos.setTipoCuenta( BecTipo.getCuenta() );
		AfeAcuerdos.setTipoImporte(BecTipo.getImporte());
		AfeAcuerdos.setTipoAcuerdo( BecTipo.getAcuerdo() );
		AfeAcuerdos.setAcuerdoFecha( BecAcuerdo.getFecha() );
		AfeAcuerdos.setAcuerdoImpMatricula( matricula );
		AfeAcuerdos.setAcuerdoImpEnsenanza( ensenanza );
		AfeAcuerdos.setAcuerdoImpInternado( internado );
		AfeAcuerdos.setAcuerdoVigencia( BecAcuerdo.getVigencia() );		
		AfeAcuerdos.setAcuerdoPromesa( BecAcuerdo.getPromesa() );
		AfeAcuerdos.setAcuerdoHoras( BecAcuerdo.getHoras() );
		AfeAcuerdos.setAcuerdoEjercicioId( BecAcuerdo.getIdEjercicio() );
		AfeAcuerdos.setAcuerdoCcostoId( BecAcuerdo.getIdCcosto() );		
		AfeAcuerdos.setCategoriaId( BecCategoria.getCategoriaId() );
		AfeAcuerdos.setCategoriaNombre( BecCategoria.getCategoriaNombre() );
		AfeAcuerdos.setAlpuestoFechaInical( BecPuestoAlumno.getFechaIni() );
		AfeAcuerdos.setAlpuestoFechaFinal( BecPuestoAlumno.getFechaFin() );
		AfeAcuerdos.setAlpuestoTipo( BecPuestoAlumno.getTipo() );
		AfeAcuerdos.setTotalBecaAdic( String.valueOf(totalBeca) );
		AfeAcuerdos.setValor( BecAcuerdo.getValor() );		
		conEnoc.setAutoCommit(false);
		
		if(AfeAcuerdosU.existePuestoReg(conEnoc, AfeAcuerdos.getMatricula(), AfeAcuerdos.getAcuerdoFolio(), AfeAcuerdos.getAlpuestoPuestoId() )){
			
			if(AfeAcuerdosU.updatePuestoReg(conEnoc, AfeAcuerdos)){
				
				lBeca.setId(lBeca.maximoReg(conEnoc));
				lBeca.setDatos("Alumno:"+codigoAlumno+",Puesto:"+BecPuestoAlumno.getPuestoId()+",Matricula:"+matricula+",Ensenanza:"+ensenanza+",Internado:"+internado+",Neta:"+totalBeca);				
				lBeca.setIp(request.getRemoteAddr());
				lBeca.setOperacion("UPDATE");
				lBeca.setTabla("MATEO.FES_CC_AFE_ACUERDOS");
				lBeca.setUsuario(usuario);
				if (lBeca.insertReg(conEnoc)){
					msj = "<div class='alert alert-success'>Se Modificó Correctamente</div>";
					conEnoc.commit();
				}else{
					conEnoc.rollback();
					msj = "<div class='alert alert-danger'>No se registró la operación</div>";
				}				
			}else{
				conEnoc.rollback();
				msj = "<div class='alert alert-danger'>Ocurrió un Error al Modificar</div>";
			}
		}else{
			
			// Busca el maximo ID
			String id = AfeAcuerdosU.maximoReg( conEnoc );
			AfeAcuerdos.setId(id);
			if(AfeAcuerdosU.insertReg(conEnoc, AfeAcuerdos)){
				msj = "<div class='alert alert-success'>Se Guardó Correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un Error al Guardar</div>";
			}
		}
		conEnoc.setAutoCommit(true);
	}else{
		AfeAcuerdos.setMatricula( codigoAlumno );
		AfeAcuerdos.setAcuerdoFolio( folio );
		AfeAcuerdos.setAlpuestoPuestoId(puestoId);
		if(AfeAcuerdosU.existePuestoReg(conEnoc, AfeAcuerdos.getMatricula(), AfeAcuerdos.getAcuerdoFolio(), AfeAcuerdos.getAlpuestoPuestoId() )){
			AfeAcuerdos = AfeAcuerdosU.mapeaRegPuestoId(conEnoc, AfeAcuerdos.getMatricula(), AfeAcuerdos.getAcuerdoFolio(), AfeAcuerdos.getAlpuestoPuestoId());	
			totalBeca = Double.valueOf(AfeAcuerdos.getTotalBecaAdic());
		}else{
			AfeAcuerdos.setAcuerdoImpMatricula("0");
			AfeAcuerdos.setAcuerdoImpEnsenanza("0");
			AfeAcuerdos.setAcuerdoImpInternado("0");
		}	
	}
	
%>
<style>
 	body{
 		background : white;
 	}
 </style>
<body>
<div class="container-fluid">
		
	<h2>Registrar Acuerdo En Mateo <small class="text-muted fs-4">[<%=folio %>] <%=codigoAlumno%> | <%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoAlumno, "NOMBRE") %></small></h2>
	
	<br />
	<%=msj %>
	
	<div class="alert alert-info">
		<a href="contrato" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	
	<form action="" name="forma">
		<input type="hidden" name="Accion" />
		<input type="hidden" name="folio" value="<%=folio%>" />
		<input type="hidden" name="PuestoId" value="<%=puestoId%>" />
			
		<p>	
			<label for="matricula">$ Matricula</label>
			<input type="text" name="matricula" id="matricula" value="<%=AfeAcuerdos.getAcuerdoImpMatricula() %>" />
		</p>
		<p>
			<label for="ensenanza">$ Enseñanza</label>
			<input type="text" name="ensenanza" id="ensenanza" value="<%=AfeAcuerdos.getAcuerdoImpEnsenanza() %>" />
		</p>
		<p>
			<label for="internado">$ Internado</label>
			<input type="text" name="internado" id="internado" value="<%=AfeAcuerdos.getAcuerdoImpInternado() %>" />
		</p>
		<p>
			<label for="totalBecAd">$ Beca Neta</label>
			<input type="text" name="totalBecAd" id="totalBecAd" value="<%=totalBeca%>" readonly/>
		</p>
		
		<div class="alert alert-info">
			<a class="btn btn-primary btn-large" onclick="grabar();"><i class="icon icon-ok icon-white"></i> Grabar</a>		
		</div>
	</form>
	
</div>

<script>
	function grabar(){
		document.forma.Accion.value = "1";
		document.forma.submit();
	}
</script>

<%@ include file="../../cierra_enoc.jsp"%>