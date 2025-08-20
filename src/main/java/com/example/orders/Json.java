package com.example.orders;
import com.fasterxml.jackson.databind.*;
public class Json {
  private static final ObjectMapper M = new ObjectMapper().findAndRegisterModules();
  public static <T> T read(String s, Class<T> c) { try { return M.readValue(s, c);} catch(Exception e){ throw new RuntimeException(e);} }
  public static String write(Object o){ try { return M.writeValueAsString(o);} catch(Exception e){ throw new RuntimeException(e);} }
}
