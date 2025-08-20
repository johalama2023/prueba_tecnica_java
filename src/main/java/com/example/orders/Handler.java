package com.example.orders;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.math.BigDecimal;
import java.util.*;
public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
  private final String table = System.getenv("TABLE_NAME");
  private final OrderService svc = new OrderService(table);
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent req, Context ctx){
    try{
      String method = req.getHttpMethod();
      String path = req.getPath();
      if("POST".equals(method) && path.equals("/orden")) return create(req);
      if("GET".equals(method) && path.matches("/orden/[^/]+")) return get(req);
      if("PUT".equals(method) && path.matches("/orden/[^/]+/estado")) return updateEstado(req);
      return resp(404, Map.of("message","Not Found"));
    }catch(Exception e){ return resp(500, Map.of("message", e.getMessage())); }
  }
  private APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent req){
    Map<String,Object> b = Json.read(req.getBody(), Map.class);
    String clienteId = (String)b.get("clienteId");
    String fecha = (String)b.get("fecha");
    Object m = b.get("montoTotal");
    if(clienteId==null || fecha==null || m==null) return resp(400, Map.of("message","Campos requeridos"));
    BigDecimal monto = new BigDecimal(m.toString());
    if(monto.compareTo(BigDecimal.ZERO)<=0) return resp(400, Map.of("message","montoTotal debe ser > 0"));
    Order o = svc.create(clienteId, fecha, monto);
    return resp(201, o);
  }
  private APIGatewayProxyResponseEvent get(APIGatewayProxyRequestEvent req){
    String[] parts = req.getPath().split("/");
    String id = parts[2];
    Order o = svc.get(id);
    if(o==null) return resp(404, Map.of("message","No encontrado"));
    return resp(200, o);
  }
  private APIGatewayProxyResponseEvent updateEstado(APIGatewayProxyRequestEvent req){
    String[] parts = req.getPath().split("/");
    String id = parts[2];
    Map<String,Object> b = Json.read(req.getBody(), Map.class);
    String estado = b==null?null:(String)b.get("estado");
    if(estado==null) return resp(400, Map.of("message","estado requerido"));
    java.util.Set<String> valid = java.util.Set.of("ENVIADO","ENTREGADO","CANCELADO");
    if(!valid.contains(estado)) return resp(400, Map.of("message","estado inválido"));
    Order o = svc.updateEstado(id, estado);
    if(o==null) return resp(404, Map.of("message","No encontrado"));
    return resp(200, o);
  }
  private APIGatewayProxyResponseEvent resp(int code, Object body){
    APIGatewayProxyResponseEvent r = new APIGatewayProxyResponseEvent();
    r.setStatusCode(code);
    java.util.Map<String,String> h = new java.util.HashMap<>();
    h.put("Content-Type","application/json");
    h.put("Access-Control-Allow-Origin","*");
    r.setHeaders(h);
    r.setBody(Json.write(body));
    return r;
  }
}
