package aca.wsdl.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import aca.tit.spring.TitAlumno;
import aca.tit.spring.TitAlumnoDao;
import aca.wsdl.sep.AccesoCadena;
import aca.wsdl.sep.AccesoCadenaResponse;
import aca.wsdl.sep.Descarga;
import aca.wsdl.sep.DescargaResponse;

@Component
public class TituloWebService extends  WebServiceGatewaySupport{
	//private static final Logger log = LoggerFactory.getLogger(TituloClient.class);
	
	@Autowired	
	private TitAlumnoDao titAlumnoDao;
	
	public AccesoCadenaResponse getAccesoCadenaResult(String url, int claveDGP, String cuenta, String password, String folio, String recibo){
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
		}
		
		String xmlBase64 		= java.util.Base64.getEncoder().encodeToString(titAlumno.getXml().getBytes());
		String nombre 			= titAlumno.getFolio()+"-"+titAlumno.getCodigoPersonal()+".xml";	
		
		AccesoCadena request = new AccesoCadena();		
		request.setCuenta(cuenta); //"U78M79U8511MOB12" para pruebas
		request.setPassword(password); // "O77N85O7918NMO10" para pruebas
		request.setRecibo(recibo);
		request.setClaveDGP(claveDGP);
		request.setCadena(xmlBase64);
		request.setFileName(nombre);		
		AccesoCadenaResponse response = new AccesoCadenaResponse();
		//Parametro url pruebas= "http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl"		
		response 	= (AccesoCadenaResponse)getWebServiceTemplate().marshalSendAndReceive(url, request, new SoapActionCallback("http://tempuri.org/AccesoCadena"));		
		return response;
		
	}
	
	public DescargaResponse getDescargaResult(String url, int claveDGP, String folio, String recibo){
		
		TitAlumno titAlumno = new TitAlumno();
		if (titAlumnoDao.existeReg(folio)) {
			titAlumno = titAlumnoDao.mapeaRegId(folio);
		}		
		String nombre 		= titAlumno.getFolio()+"-"+titAlumno.getCodigoPersonal()+".xml";
		
		Descarga request = new Descarga();
		request.setClaveDGP(claveDGP);
		request.setRecibo(recibo);
		request.setArchivoNombre(nombre);
		
		DescargaResponse response = new DescargaResponse();		
		//Parametro url pruebas = http://app-msys.uienl.edu.mx:8044/WebPrueba/WsTitulo.asmx?wsdl
		//Ultimo parametro metodo a utilizar = http://tempuri.org/Descarga
		response 	= (DescargaResponse)getWebServiceTemplate().marshalSendAndReceive(url, request, new SoapActionCallback("http://tempuri.org/Descarga"));		
		return response;
		
	}
}
