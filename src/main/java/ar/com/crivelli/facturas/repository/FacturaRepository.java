package ar.com.crivelli.facturas.repository;
import ar.com.crivelli.facturas.model.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface FacturaRepository extends JpaRepository<Factura,Long>{ List<Factura> findByEstado(EstadoFactura estado); List<Factura> findByProveedorId(Long proveedorId); }
