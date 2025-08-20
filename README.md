
# Orders Service - Serverless Application

## 📌 Descripción
Este proyecto implementa un servicio de pedidos utilizando **Node.js**, **Express**, **MongoDB Atlas** y se despliega en **AWS Lambda** mediante **Serverless Framework**.  
La arquitectura es **serverless**, lo que permite escalabilidad automática y bajo costo de operación.

---

## 🏗️ Arquitectura

```text
Cliente (Web/Móvil) → API Gateway → Lambda (Orders Service) → MongoDB Atlas
```

- **API Gateway**: expone los endpoints REST.
- **AWS Lambda**: ejecuta el servicio Express de pedidos.
- **MongoDB Atlas**: almacena la información de los pedidos.
- **IAM**: maneja permisos de acceso seguro.

---

## ⚙️ Decisiones Técnicas

1. **Node.js + Express**: rápido, flexible y con gran comunidad.
2. **MongoDB Atlas**: base de datos NoSQL flexible y escalable.
3. **Serverless Framework**: facilita la gestión de Lambdas, API Gateway y despliegues.
4. **Arquitectura Serverless**: elimina la necesidad de administrar servidores.

---

## 🚀 Despliegue en AWS

### 1. Configuración de credenciales AWS
```bash
aws configure
# AWS Access Key ID: <TU_KEY>
# AWS Secret Access Key: <TU_SECRET>
# Default region: us-east-1 (recomendado si estás en Perú)
```

### 2. Instalación de dependencias
```bash
npm install -g serverless
npm install
```

### 3. Despliegue
```bash
sls deploy
```

### 4. Pruebas
```bash
curl https://<API_GATEWAY_URL>/orders
```

---

## 📊 Endpoints

- `POST /orders` → Crear un pedido
- `GET /orders` → Listar pedidos
- `GET /orders/{id}` → Obtener un pedido por ID

---

## 🛡️ Seguridad

- **IAM Roles** para ejecución de Lambda.
- **API Key** opcional en API Gateway.
- **MongoDB Atlas IP Whitelist** para acceso restringido.

---

## 👨‍💻 Autor
Desarrollado por John Harold Lanza Martínez
