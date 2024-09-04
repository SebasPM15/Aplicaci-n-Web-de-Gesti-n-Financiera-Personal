package recursos;

import java.sql.Date;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import modelo.dao.MovimientoDAO;
import modelo.entidades.Movimiento;

@Path("/movimiento")
public class RecursoMovimiento {
	
    private final MovimientoDAO movimientoDAO = new MovimientoDAO();

	public RecursoMovimiento() {}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response getAllMovimientos() {
		List<Movimiento> movimientos = movimientoDAO.findAll();
		return Response.ok(movimientos).build();
	}
	
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovimiento(@PathParam("id") int id) {
        Movimiento movimiento = movimientoDAO.find(id);
        if (movimiento == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movimiento).build();
    }
    
    @GET
    @Path("/usuario/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovimientosByUsuario(@PathParam("usuarioId") int usuarioId) {
        List<Movimiento> movimientos = movimientoDAO.findByUsuario(usuarioId);
        if (movimientos.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(movimientos).build();
    }
   
}
