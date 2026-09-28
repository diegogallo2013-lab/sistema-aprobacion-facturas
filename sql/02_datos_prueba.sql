USE aprobacion_facturas;
INSERT INTO proveedores(nombre) VALUES ('Transportes SRL'),('Servicios Tecnológicos SA');
INSERT INTO facturas(proveedor_id,importe,estado,fecha) VALUES (1,8500000,'PENDIENTE',NOW()),(2,12500000,'PENDIENTE',NOW());
