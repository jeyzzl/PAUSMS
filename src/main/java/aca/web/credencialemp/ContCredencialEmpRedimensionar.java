package aca.web.credencialemp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
//import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.pg.archivo.FotoChica;
import aca.pg.archivo.FotoChicaDao;
import aca.pg.archivo.spring.PosFotoAlum;
import aca.pg.archivo.spring.PosFotoAlumDao;

@Controller
public class ContCredencialEmpRedimensionar {	
	
	@Autowired
	@Qualifier("dsArchivo")
	private DataSource archivo;
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	@Autowired
	private PosFotoAlumDao posFotoAlumDao;
	
	@Autowired
	private FotoChicaDao fotoChicaDao;	
	
	@Autowired
	ServletContext context;	
	
	@RequestMapping("/credencial_emp/redimensionar/fotos")
	public String credencialEmpRedimensionarFotos(HttpServletRequest request, Model modelo) throws SQLException{	
		List<PosFotoAlum> listaFotos 	= posFotoAlumDao.lisEmpleados("O");
        List<String> listaFotosRedi = fotoChicaDao.lisConFoto();
	        
        int numFotos 		= listaFotos.size();
        int numFotosRedi 	= listaFotosRedi.size();
		
		modelo.addAttribute("numFotos", numFotos);
		modelo.addAttribute("numFotosRedi", numFotosRedi);
		
		return "credencial_emp/redimensionar/fotos";
	}
	
	@RequestMapping("/credencial_emp/redimensionar/grabar")
	public String credencialEmpRedimensionarGrabar(HttpServletRequest request, Model modelo) throws SQLException{
		
		List<PosFotoAlum> listaFotos 	= posFotoAlumDao.lisEmpleados("O");
		int row = 0;
		System.out.println(listaFotos.size());
		try { 
	        
	        for(PosFotoAlum foto : listaFotos) {
	        	row++;
	        	System.out.println(row+" redimencionando: "+foto.getCodigoPersonal());
	        	FotoChica fotoChica 	= new FotoChica();
	        	PosFotoAlum fotoNormal 	= new PosFotoAlum();
	    		
	        	fotoNormal = posFotoAlumDao.mapeaRegId(foto.getCodigoPersonal(), "O");
	        	
	        	InputStream fotoInput 	= new ByteArrayInputStream(fotoNormal.getFoto());
	        	BufferedImage imagenOriginal = ImageIO.read(fotoInput);
				//imagenOriginal= Scalr.resize(imagenOriginal, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, 800, 600);
		        //To save with original ratio uncomment next line and comment the above.
				imagenOriginal= Scalr.resize(imagenOriginal, 1200, 900);
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        ImageIO.write(imagenOriginal, "jpg", baos);
		        baos.flush();
		        byte[] imagenReducida = baos.toByteArray();
		        baos.close();        	
	        	
	        	fotoChica.setCodigoPersonal(foto.getCodigoPersonal());
	        	fotoChica.setFecha(fotoNormal.getFecha());
	        	fotoChica.setFoto(imagenReducida);
	        	if (fotoChicaDao.existeReg(foto.getCodigoPersonal())){
	        		fotoChicaDao.updateReg(fotoChica);
	        		System.out.println("Modificando: "+foto.getCodigoPersonal());
	        	}else{
	        		fotoChicaDao.insertReg(fotoChica);
	        		System.out.println("Grabando: "+foto.getCodigoPersonal());
	        	}
	        }
	        
		}catch(Exception ex){
			System.out.println("Error:"+ex);
		}               
          
		return "redirect:/credencial_emp/redimensionar/fotos";
	}
}