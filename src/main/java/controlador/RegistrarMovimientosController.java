package controlador;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import modelo.dao.CategoriaDAO;
import modelo.dao.CuentaDAO;
import modelo.dao.EgresoDAO;
import modelo.entidades.Categoria;
import modelo.entidades.Cuenta;
import modelo.entidades.Egreso;
import modelo.entidades.Ingreso;
import modelo.entidades.Movimiento;
import modelo.entidades.Transferencia;
import modelo.entidades.Usuario;
import modelo.dao.IngresoDAO;
import modelo.dao.MovimientoDAO;
import modelo.dao.TransferenciaDAO;

@WebServlet("/RegistrarMovimientosController")
public class RegistrarMovimientosController extends HttpServlet {
	
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
        String ruta = (req.getParameter("ruta") == null) ? "crearIngreso" : req.getParameter("ruta");
        
        switch (ruta) {
            case "crearIngreso":
                this.crearIngreso(req, resp);
                break;
            case "guardarIngreso":
                this.guardarIngreso(req, resp);
                break;
            case "crearEgreso":
            	this.crearEgreso(req, resp);
            	break;
            case "guardarEgreso":
            	this.guardarEgreso(req, resp);
            	break;
            case "crearTransferencia":
            	this.crearTransferencia(req, resp);
            	break;
            case "guardarTransferencia":
            	this.guardarTransferencia(req, resp);
            	break;         	
            //Nuevos casos
            case "editarMovimiento":
                this.editarMovimiento(req, resp);
                break;
            case "actualizarMovimiento":
                this.actualizarMovimiento(req, resp);
                break;
            case "eliminarMovimiento":
                this.eliminarMovimiento(req, resp);
                break;
        }
    }
    
    public void crearIngreso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	CategoriaDAO categoriaDAO = new CategoriaDAO();
    	CuentaDAO cuentaDAO = new CuentaDAO();
    	
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión
    	
        // Obtener las listas de categorías y cuentas
    	List<Categoria> categorias = categoriaDAO.findAll();
    	List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());
        // Establecer las listas en el request
        req.setAttribute("categorias", categorias);
        req.setAttribute("cuentas", cuentas);
        // Redirigir a la vista de crear ingreso
        req.getRequestDispatcher("jsp/crearIngreso.jsp").forward(req, resp);
    }
    
    public void guardarIngreso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión
        
        Ingreso ingreso = new Ingreso();
        ingreso.setConcepto(req.getParameter("concepto"));
        ingreso.setFecha(Date.valueOf(req.getParameter("fecha")));
        ingreso.setMonto(Double.parseDouble(req.getParameter("monto")));
        ingreso.setUsuario(usuario); // Asociar el usuario al ingreso

        // Recuperar y asignar categoría y cuenta
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();
        int categoriaId = Integer.parseInt(req.getParameter("categoria"));
        int cuentaId = Integer.parseInt(req.getParameter("cuenta"));
        Categoria categoria = categoriaDAO.find(categoriaId);
        Cuenta cuenta = cuentaDAO.find(cuentaId);
        ingreso.setCategoria(categoria);
        ingreso.setCuenta(cuenta);
        
        // Guardar el ingreso en la base de datos
        IngresoDAO ingresoDAO = new IngresoDAO();
        ingresoDAO.guardar(ingreso);
        
        // Actualizar el saldo de la cuenta
        cuenta.setUsuario(usuario);
        cuenta.setSaldo(cuenta.getSaldo() + ingreso.getMonto());
        cuentaDAO.actualizar(cuenta);
        
        // Redirigir al dashboard después de guardar el ingreso
        resp.sendRedirect("DashboardController?ruta=verDashboard");
    }

    private void crearEgreso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();
        
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión
        
        // Obtener las listas de categorías y cuentas
        List<Categoria> categorias = categoriaDAO.findAll();
    	List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());
        
        // Establecer las listas en el request
        req.setAttribute("categorias", categorias);
        req.setAttribute("cuentas", cuentas);
        
        // Redirigir a la vista de crear egreso
        req.getRequestDispatcher("jsp/crearEgreso.jsp").forward(req, resp);
    }
    
    private void guardarEgreso(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión

        Egreso egreso = new Egreso();
        egreso.setConcepto(req.getParameter("concepto"));
        egreso.setFecha(java.sql.Date.valueOf(req.getParameter("fecha")));
        egreso.setMonto(Double.parseDouble(req.getParameter("monto")));
        egreso.setUsuario(usuario); // Asociar el usuario al egreso

        // Recuperar y asignar categoría y cuenta
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();
        
        int categoriaId = Integer.parseInt(req.getParameter("categoria"));
        int cuentaId = Integer.parseInt(req.getParameter("cuenta"));
        
        Categoria categoria = categoriaDAO.find(categoriaId);
        Cuenta cuenta = cuentaDAO.find(cuentaId);
        
        egreso.setCategoria(categoria);
        egreso.setCuenta(cuenta);
        
        // Verificar que el saldo de la cuenta no se vuelva negativo
        double nuevoSaldo = cuenta.getSaldo() - egreso.getMonto(); // Restar el monto del egreso
        if(nuevoSaldo < 0) {
            req.setAttribute("error", "No se puede realizar debido a que supera el monto de la cuenta seleccionada.");
            // Reestablecer las listas de categorías y cuentas para la vista
            List<Categoria> categorias = categoriaDAO.findAll();
            List<Cuenta> cuentas = cuentaDAO.findAll();
            req.setAttribute("categorias", categorias);
            req.setAttribute("cuentas", cuentas);
            
            req.getRequestDispatcher("jsp/crearEgreso.jsp").forward(req, resp);
        } else {
            // Guardar el egreso en la base de datos
            EgresoDAO egresoDAO = new EgresoDAO();
            egresoDAO.guardar(egreso);
            
            // Actualizar el saldo de la cuenta
            cuenta.setUsuario(usuario);
            cuenta.setSaldo(nuevoSaldo); // Actualizar el saldo con el nuevo saldo
            cuentaDAO.actualizar(cuenta);
            
            // Redirigir al dashboard después de guardar el egreso
            resp.sendRedirect("DashboardController?ruta=verDashboard");
        }
    }
    
    private void crearTransferencia(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión

        // Obtener la categoría fija con ID 7 y las cuentas
        int idCategoriaTransferencia = 7;
        Categoria categoriaTransferencia = categoriaDAO.find(idCategoriaTransferencia);
        List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());

        // Establecer la categoría y las cuentas en el request
        req.setAttribute("categoriaTransferencia", categoriaTransferencia);
        req.setAttribute("cuentas", cuentas);

        // Redirigir a la vista de crear transferencia
        req.getRequestDispatcher("jsp/crearTransferencia.jsp").forward(req, resp);
    }
    
    private void guardarTransferencia(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado"); // Obtener el usuario de la sesión

        CategoriaDAO categoriaDAO = new CategoriaDAO();
        CuentaDAO cuentaDAO = new CuentaDAO();

        try {
            int cuentaOrigenId = Integer.parseInt(req.getParameter("cuentaOrigen"));
            int cuentaDestinoId = Integer.parseInt(req.getParameter("cuentaDestino"));
            double monto = Double.parseDouble(req.getParameter("monto"));
            String concepto = req.getParameter("concepto");
            String fecha = req.getParameter("fecha");
            int categoriaId = 7; // Usar la categoría fija con ID 7

            if (monto <= 0) {
                req.setAttribute("error", "El monto debe ser mayor a 0.");
                // Reestablecer las listas de cuentas para la vista
                List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());
                req.setAttribute("cuentas", cuentas);
                Categoria categoriaTransferencia = categoriaDAO.find(categoriaId);
                req.setAttribute("categoriaTransferencia", categoriaTransferencia);

                req.getRequestDispatcher("jsp/crearTransferencia.jsp").forward(req, resp);
                return;
            }

            // Obtener las cuentas de origen y destino
            Cuenta cuentaOrigen = cuentaDAO.find(cuentaOrigenId);
            Cuenta cuentaDestino = cuentaDAO.find(cuentaDestinoId);

            // Verificar que el saldo de la cuenta de origen sea suficiente
            if (cuentaOrigen.getSaldo() < monto) {
                req.setAttribute("error", "No se puede realizar la transferencia debido a que el monto excede el saldo de la cuenta de origen.");
                // Reestablecer las listas de cuentas para la vista
                List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());
                req.setAttribute("cuentas", cuentas);
                Categoria categoriaTransferencia = categoriaDAO.find(categoriaId);
                req.setAttribute("categoriaTransferencia", categoriaTransferencia);

                req.getRequestDispatcher("jsp/crearTransferencia.jsp").forward(req, resp);
                return;
            }

            // Crear la transferencia
            Transferencia transferencia = new Transferencia();
            transferencia.setConcepto(concepto);
            transferencia.setFecha(Date.valueOf(fecha));
            transferencia.setMonto(monto);
            transferencia.setCuenta(cuentaOrigen);
            transferencia.setCuentaDestino(cuentaDestino);
            transferencia.setCategoria(categoriaDAO.find(categoriaId));
            transferencia.setUsuario(usuario); // Asociar el usuario a la transferencia

            // Guardar la transferencia en la base de datos
            TransferenciaDAO transferenciaDAO = new TransferenciaDAO();
            transferenciaDAO.guardar(transferencia);

            // Actualizar los saldos de las cuentas
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);

            cuentaDAO.actualizar(cuentaOrigen);
            cuentaDAO.actualizar(cuentaDestino);

            // Redirigir al dashboard después de guardar la transferencia
            resp.sendRedirect("DashboardController?ruta=verDashboard");

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Formato de número inválido.");
            // Reestablecer las listas de cuentas para la vista
            List<Cuenta> cuentas = cuentaDAO.findByUsuario(usuario.getId());
            req.setAttribute("cuentas", cuentas);
            Categoria categoriaTransferencia = categoriaDAO.find(7);
            req.setAttribute("categoriaTransferencia", categoriaTransferencia);

            req.getRequestDispatcher("jsp/crearTransferencia.jsp").forward(req, resp);
        }
    }
    
    //Nuevos métodos
    private void editarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
    	int movimientoId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado");

        // Obtener las listas de categorías y cuentas
        List<Categoria> categorias = categoriaDAO.findAll();
        
        // Establecer las listas en el request
        req.setAttribute("categorias", categorias);
        
        MovimientoDAO movimientoDAO = new MovimientoDAO();
        Movimiento movimiento = movimientoDAO.find(movimientoId);

        if (movimiento != null && movimiento.getUsuario().getId() == usuario.getId()) {
            req.setAttribute("movimiento", movimiento);
            // Lógica para cargar la vista de edición (por ejemplo, jsp/editarMovimiento.jsp)
            req.getRequestDispatcher("jsp/editarMovimiento.jsp").forward(req, resp);
        } else {
            // Manejar el caso donde el movimiento no existe o no pertenece al usuario
            resp.sendRedirect("DashboardController?ruta=verDashboard");
        }
    }

    private void actualizarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        MovimientoDAO movimientoDAO = new MovimientoDAO();
        Movimiento movimiento = movimientoDAO.find(id);

        if (movimiento != null) {
            movimiento.setConcepto(req.getParameter("concepto"));
            movimiento.setFecha(Date.valueOf(req.getParameter("fecha")));
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            int categoriaId = Integer.parseInt(req.getParameter("categoria"));
            Categoria categoria = categoriaDAO.find(categoriaId);

            movimiento.setCategoria(categoria);
            movimientoDAO.actualizar(movimiento);

            resp.sendRedirect("DashboardController?ruta=verDashboard");
        } else {
            resp.sendRedirect("DashboardController?ruta=verDashboard");
        }
    }

    private void eliminarMovimiento(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int movimientoId = Integer.parseInt(req.getParameter("id"));
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("autorizado");

        MovimientoDAO movimientoDAO = new MovimientoDAO();
        Movimiento movimiento = movimientoDAO.find(movimientoId);

        if (movimiento != null && movimiento.getUsuario().getId() == usuario.getId()) {
            movimientoDAO.eliminar(movimiento);
        }

        resp.sendRedirect("DashboardController?ruta=verDashboard");
    }
    
}
