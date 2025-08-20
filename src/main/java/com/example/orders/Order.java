package com.example.orders;
import java.math.BigDecimal;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
@DynamoDbBean
public class Order {
  private String ordenId;
  private String clienteId;
  private String fecha;
  private BigDecimal montoTotal;
  private String estado;
  @DynamoDbPartitionKey
  public String getOrdenId(){ return ordenId; }
  public void setOrdenId(String v){ this.ordenId=v; }
  public String getClienteId(){ return clienteId; }
  public void setClienteId(String v){ this.clienteId=v; }
  public String getFecha(){ return fecha; }
  public void setFecha(String v){ this.fecha=v; }
  public BigDecimal getMontoTotal(){ return montoTotal; }
  public void setMontoTotal(BigDecimal v){ this.montoTotal=v; }
  public String getEstado(){ return estado; }
  public void setEstado(String v){ this.estado=v; }
}
