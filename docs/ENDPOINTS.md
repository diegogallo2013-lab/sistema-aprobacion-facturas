# Ejemplos de uso

```bash
curl -X POST http://localhost:8080/api/proveedores -H "Content-Type: application/json" -d '{"nombre":"Transportes SRL"}'
curl -X POST "http://localhost:8080/api/facturas?proveedorId=1" -H "Content-Type: application/json" -d '{"importe":8500000}'
curl http://localhost:8080/api/facturas/estado/PENDIENTE
curl -X PUT http://localhost:8080/api/facturas/1/aprobar
```
