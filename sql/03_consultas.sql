USE aprobacion_facturas;
SELECT * FROM facturas WHERE estado='PENDIENTE';
SELECT f.id,p.nombre proveedor,f.importe,f.estado,f.fecha FROM facturas f JOIN proveedores p ON p.id=f.proveedor_id ORDER BY f.fecha DESC;
SELECT p.nombre,SUM(f.importe) total_facturado FROM proveedores p JOIN facturas f ON f.proveedor_id=p.id GROUP BY p.id,p.nombre;
SELECT id,importe,CASE WHEN importe>10000000 THEN 'DIRECCION' ELSE 'GERENCIA' END nivel_aprobacion FROM facturas WHERE estado='PENDIENTE';
UPDATE facturas SET estado='APROBADA' WHERE id=1 AND estado='PENDIENTE';
DELETE FROM facturas WHERE id=2;
