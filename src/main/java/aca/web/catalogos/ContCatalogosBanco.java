package aca.web.catalogos;

import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import aca.catalogo.spring.CatBanco;
import aca.catalogo.spring.CatBancoDao;
import aca.catalogo.spring.CatPais;
import aca.catalogo.spring.CatPaisDao;

@Controller
public class ContCatalogosBanco {
    
    @Autowired
    private CatBancoDao catBancoDao;

    @Autowired
    private CatPaisDao catPaisDao;
    
    @RequestMapping("/catalogos/banco/banco")
    public String catalogosBancoBanco(HttpServletRequest request, Model model){
        
        List<CatBanco> lista 			= catBancoDao.getListAll("ORDER BY 2");
        HashMap<String,String> mapaUsadas 	= catBancoDao.mapaUsados();
        HashMap<String,CatPais> mapaPais = catPaisDao.getMapAll();
        
        model.addAttribute("lista", lista);		
        model.addAttribute("mapaUsadas", mapaUsadas);
        model.addAttribute("mapaPais", mapaPais);
                
        return "catalogos/banco/banco";
    }
    
    @RequestMapping("/catalogos/banco/editar")
    public String catalogosBancoEditar(HttpServletRequest request, Model model){
        
        String bancoId 			= request.getParameter("BancoId")==null?"0":request.getParameter("BancoId");

        List<CatPais> lisPais = catPaisDao.getListAll(" ORDER BY NOMBRE_PAIS");

        CatBanco catBanco 	= new CatBanco();
        if (catBancoDao.existeReg(bancoId)){
            catBanco =  catBancoDao.mapeaRegId(bancoId);
        }else {
            catBanco.setBancoId(catBancoDao.maximoReg());
        }
        
        model.addAttribute("catBanco", catBanco);
        model.addAttribute("lisPais", lisPais);
        
        return "catalogos/banco/editar";
    }
    
    @RequestMapping("/catalogos/banco/grabar")
    public String catalogosBancoGrabar(HttpServletRequest request){
        
        String bancoId			    = request.getParameter("BancoId")==null?"0":request.getParameter("BancoId");
        String nombre		        = request.getParameter("Nombre")==null?"":request.getParameter("Nombre");
        String nombreCorto			= request.getParameter("NombreCorto")==null?"":request.getParameter("NombreCorto");
        String paisId               = request.getParameter("PaisId")==null?"0":request.getParameter("PaisId");
        String swift                = request.getParameter("Swift")==null?"":request.getParameter("Swift");
        String mensaje				= "-";
        
        CatBanco catBanco 	= new CatBanco();
        catBanco.setBancoId(bancoId);
        catBanco.setNombre(nombre);
        catBanco.setNombreCorto(nombreCorto);
        catBanco.setPaisId(paisId);
        catBanco.setSwift(swift);
        
        if (catBancoDao.existeReg(bancoId)){
            if (catBancoDao.updateReg(catBanco)) {
                mensaje = "Updated";
            }else {
                mensaje = "Error updating";
            }
        }else {
            catBanco.setBancoId(catBancoDao.maximoReg());
            if (catBancoDao.insertReg(catBanco)) {
                mensaje = "Saved";
            }else {
                mensaje = "Error saving";
            }
        }
        
        return "redirect:/catalogos/banco/editar?BancoId="+bancoId+"&Mensaje="+mensaje;
    }
    
    @RequestMapping("/catalogos/banco/borrar")
    public String catalogosBancoBorrar(HttpServletRequest request, Model model){
            
        String bancoId 		= request.getParameter("BancoId")==null?"0":request.getParameter("BancoId");
        if (catBancoDao.existeReg(bancoId) == true) {
            if (catBancoDao.deleteReg(bancoId)) {
            }
        }
        
        return "redirect:/catalogos/banco/banco";
    }
}