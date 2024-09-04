package controlador;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.UsuarioDAO;
import modelo.entidades.Usuario;

@WebServlet("/AutenticarController")
public class AutenticarController extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.ruteador(req, resp);
    }

    private void ruteador(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = (req.getParameter("ruta") == null) ? "iniciar" : req.getParameter("ruta");

        switch (ruta) {
            case "ingresar":
                this.ingresar(req, resp);
                break;
            case "iniciar":
                this.iniciar(req, resp);
                break;
            case "cerrarSesion":
                this.cerrarSesion(req, resp);
                break;
        }
    }
    
    private void iniciar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("jsp/login.jsp");
    }

    private void ingresar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. Obtener los parámetros
    	String nombre = req.getParameter("nombre");
        String clave = req.getParameter("clave");
        //2. Hablar con el modelo
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.autenticar(nombre, clave);
        
        if (usuario == null) {
            // No le permito pasar
            resp.sendRedirect("jsp/login.jsp");
        } else {
            // Es correcto
            HttpSession session = req.getSession();
            session.setAttribute("autorizado", usuario);
            session.setAttribute("nombreUsuario", usuario.getNombre()); // Almacenar el nombre del usuario en la sesión
            //le permito pasar
            resp.sendRedirect("DashboardController?ruta=verDashboard");
        }
    }

    private void cerrarSesion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("jsp/login.jsp");
    }
}
