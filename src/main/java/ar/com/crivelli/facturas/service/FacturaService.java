package ar.com.crivelli.facturas.service;
import ar.com.crivelli.facturas.model.*; import ar.com.crivelli.facturas.repository.*; import org.springframework.stereotype.Service; import java.util.*;
@Service public class FacturaService {
 private final FacturaRepository facturas; private final ProveedorRepository proveedores;
 public FacturaService(FacturaRepository f,ProveedorRepository p){facturas=f;proveedores=p;}
 public List<Factura> listar(){return facturas.findAll();}
 public List<Factura> porEstado(EstadoFactura e){return facturas.findByEstado(e);}
 public List<Factura> porProveedor(Long id){return facturas.findByProveedorId(id);}
 public Factura crear(Long proveedorId, Factura factura){ factura.setProveedor(proveedores.findById(proveedorId).orElseThrow(()->new NoSuchElementException("Proveedor inexistente"))); factura.setEstado(EstadoFactura.PENDIENTE); return facturas.save(factura); }
 public Factura cambiarEstado(Long id,EstadoFactura estado){Factura f=facturas.findById(id).orElseThrow(()->new NoSuchElementException("Factura inexistente")); if(f.getEstado()!=EstadoFactura.PENDIENTE) throw new IllegalStateException("Solo una factura pendiente puede evaluarse"); f.setEstado(estado); return facturas.save(f);}
}
