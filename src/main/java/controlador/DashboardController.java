package controlador;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.CategoriaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.MovimientoDAO;
import modelo.entidades.Categoria;
import modelo.entidades.Cuenta;
import modelo.entidades.Movimiento;
import modelo.entidades.Usuario;

@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {

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
        String ruta = (req.getParameter("ruta") == null) ? "verDashboard" : req.getParameter("ruta");
        
        switch (ruta) {
            case "verDashboard":
                this.verDashboard(req, resp);
                break;
        }
    }

    private void verDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        Usuario userActual = (Usuario) session.getAttribute("autorizado");

        CuentaDAO cuentaDAO = new CuentaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        MovimientoDAO movDAO = new MovimientoDAO();

        List<Cuenta> cuentas = cuentaDAO.findByUsuario(userActual.getId());
        List<Categoria> categorias = categoriaDAO.findAll();

        // Filtrado de movimientos por fecha
        String fechaParam = req.getParameter("fecha");
        Date fecha = null;
        if(fechaParam != null && !fechaParam.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                java.util.Date utilDate = sdf.parse(fechaParam);
                fecha = new Date(utilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String cuentaParam = req.getParameter("cuenta");
        String categoriaParam = req.getParameter("categoria");

        // Recuperación de los movimientos del usuario
        List<Movimiento> movimientos = movDAO.findByUsuario(userActual.getId());

        // Aplicación de filtros sobre los movimientos
        if (fecha != null) {
            movimientos = movDAO.findByUsuarioAndFecha(userActual.getId(), fecha);
        }
        
        if (cuentaParam != null && !cuentaParam.isEmpty()) {
            int cuentaId = Integer.parseInt(cuentaParam);
            movimientos.removeIf(movimiento -> movimiento.getCuenta().getIdCuenta() != cuentaId);
        }
        
        if (categoriaParam != null && !categoriaParam.isEmpty()) {
            int categoriaId = Integer.parseInt(categoriaParam);
            movimientos.removeIf(movimiento -> movimiento.getCategoria().getIdCat() != categoriaId);
        }
        
        // Cálculo de saldos por categoría
        Map<Integer, Double> saldosPorCategoria = new HashMap<>();
        for (Categoria categoria : categorias) {
            double saldo = 0.0;
            for (Movimiento movimiento : movimientos) {
                if (movimiento.getCategoria().getIdCat() == categoria.getIdCat()) {
                    saldo += movimiento.getMonto();
                }
            }
            saldosPorCategoria.put(categoria.getIdCat(), saldo);
        }
        
        // Cálculo de saldos por cuenta
        Map<Integer, Double> saldosPorCuenta = new HashMap<>();
        for (Cuenta cuenta : cuentas) {
            double saldo = 0.0;
            for (Movimiento movimiento : movimientos) {
                if (movimiento.getCuenta().getIdCuenta() == cuenta.getIdCuenta()) {
                    saldo += movimiento.getMonto();
                }
            }
            saldosPorCuenta.put(cuenta.getIdCuenta(), saldo);
        }

        // Atributos para la vista
        req.setAttribute("cuentas", cuentas);
        req.setAttribute("categorias", categorias);
        req.setAttribute("movimientos", movimientos);
        req.setAttribute("saldosPorCategoria", saldosPorCategoria);

        // Redirección a la vista del dashboard
        req.getRequestDispatcher("jsp/verDashboard.jsp").forward(req, resp);
    }
}
