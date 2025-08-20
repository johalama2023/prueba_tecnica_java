package com.example.orders;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.*;
public class OrderService {
  private final DynamoDbEnhancedClient enhanced;
  private final DynamoDbTable<Order> table;
  public OrderService(String tableName){
    DynamoDbClient low = DynamoDbClient.create();
    this.enhanced = DynamoDbEnhancedClient.builder().dynamoDbClient(low).build();
    this.table = enhanced.table(tableName, TableSchema.fromBean(Order.class));
  }
  public Order create(String clienteId, String fecha, java.math.BigDecimal monto){
    Order o = new Order();
    o.setOrdenId(java.util.UUID.randomUUID().toString());
    o.setClienteId(clienteId);
    o.setFecha(fecha);
    o.setMontoTotal(monto);
    o.setEstado("PENDIENTE");
    table.putItem(o);
    return o;
  }
  public Order get(String id){
    Key k = Key.builder().partitionValue(id).build();
    return table.getItem(r->r.key(k));
  }
  public Order updateEstado(String id, String estado){
    Order current = get(id);
    if(current==null) return null;
    current.setEstado(estado);
    table.updateItem(current);
    return current;
  }
}
