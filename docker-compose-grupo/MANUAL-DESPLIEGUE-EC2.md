# Manual de Despliegue — Agrícola Floreza en AWS EC2

## Especificaciones de instancias

| Instancia | Nombre      | Tipo        | RAM  | Almacenamiento | Uso                  |
|-----------|-------------|-------------|------|----------------|----------------------|
| EC2 2     | agro-db     | t3.medium   | 4 GB | 20 GB gp3      | SQL Server           |
| EC2 1     | agro-app    | t3.small    | 2 GB | 15 GB gp3      | Backend + Frontend   |

> SQL Server requiere mínimo 2 GB solo para él, por eso EC2 2 va en t3.medium.
> Spring Boot + Nginx corren bien en t3.small.

---

## Security Groups en AWS

### EC2 1 — agro-app (Backend + Frontend)

| Tipo              | Puerto | Origen    | Descripción                          |
|-------------------|--------|-----------|--------------------------------------|
| SSH               | 22     | Mi IP     | Acceso SSH para administración       |
| HTTP              | 80     | 0.0.0.0/0 | Frontend Angular (acceso público)    |
| TCP personalizado | 8086   | 0.0.0.0/0 | API REST Backend (Postman / Swagger) |

### EC2 2 — agro-db (SQL Server)

| Tipo              | Puerto | Origen                  | Descripción                        |
|-------------------|--------|-------------------------|------------------------------------|
| SSH               | 22     | Mi IP                   | Acceso SSH para administración     |
| TCP personalizado | 1433   | IP privada de EC2 1 /32 | SQL Server — solo desde el backend |

---

## Conexión a las instancias (PowerShell local)

```powershell
# Conectar a EC2 2 (base de datos)
ssh -i "C:\ruta\a\tu-clave.pem" ubuntu@IP_PUBLICA_EC2_2

# Conectar a EC2 1 (backend + frontend)
ssh -i "C:\ruta\a\tu-clave.pem" ubuntu@IP_PUBLICA_EC2_1
```

---

## EC2 2 — Despliegue de SQL Server

### 1. Instalar Docker
```bash
sudo apt update -y
sudo apt install -y ca-certificates curl gnupg
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update -y
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -a -G docker ubuntu
newgrp docker
```

### 2. Crear la red
```bash
docker network create my-network
```

### 3. Levantar SQL Server
```bash
docker run -d \
  --name sqlserver \
  --network my-network \
  -e "ACCEPT_EULA=Y" \
  -e "SA_PASSWORD=Alexis123!" \
  -e "MSSQL_PID=Express" \
  -p 1433:1433 \
  --restart unless-stopped \
  mcr.microsoft.com/mssql/server:2022-latest
```

### 4. Verificar
```bash
docker ps
# Debes ver "sqlserver" en estado "Up"
```

---

## EC2 1 — Despliegue de Backend + Frontend

### 1. Instalar Docker
```bash
sudo apt update -y

sudo apt install -y ca-certificates curl gnupg

sudo install -m 0755 -d /etc/apt/keyrings

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg

sudo chmod a+r /etc/apt/keyrings/docker.gpg

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo $VERSION_CODENAME) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update -y
sudo apt install -y docker-ce docker-ce-cli containerd.io docker-compose-plugin
sudo systemctl start docker
sudo systemctl enable docker
sudo usermod -a -G docker ubuntu
newgrp docker
```

### 2. Crear la red
```bash
docker network create my-network
```

### 3. Crear el archivo docker-compose-app.yml
```bash
nano docker-compose-app.yml
```

Pega este contenido y reemplaza los dos valores indicados:

```yaml
services:

  backend:
    image: concaflores/proyectobackend:latest
    container_name: springboot-backend
    ports:
      - "8086:8086"
    environment:
      - PORT=8086
      - SERVER_URL=http://localhost:8086
      # ← Cambia IP_PRIVADA_EC2_2 por la IP privada de tu instancia agro-db
      - SPRING_DATASOURCE_URL=jdbc:sqlserver://IP_PRIVADA_EC2_2:1433;databaseName=agro_db;encrypt=true;trustServerCertificate=true
      - SPRING_DATASOURCE_USERNAME=sa
      - SPRING_DATASOURCE_PASSWORD=Alexis123!
      - SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.microsoft.sqlserver.jdbc.SQLServerDriver
      # ← Cambia IP_PUBLICA_EC2_1 por la IP pública de esta instancia agro-app
      - CORS_ALLOWED_ORIGINS=http://IP_PUBLICA_EC2_1
    networks:
      - my-network
    restart: unless-stopped

  frontend:
    image: concaflores/proyectofrontend:latest
    container_name: angular-frontend
    ports:
      - "80:80"
    networks:
      - my-network
    depends_on:
      - backend
    restart: unless-stopped

networks:
  my-network:
    external: true
```
Guardar: `Ctrl+O` → `Enter` → `Ctrl+X`

### 4. Descargar las imágenes desde DockerHub
```bash
docker pull concaflores/proyectobackend:latest
docker pull concaflores/proyectofrontend:latest
```

### 5. Levantar backend y frontend
```bash
docker compose -f docker-compose-app.yml up -d
```

### 6. Verificar
```bash
docker ps
# Debes ver "springboot-backend" y "angular-frontend" en estado "Up"

docker logs springboot-backend --tail 50
# Debes ver "Started MybackendApplication" sin errores de conexión
```

---

## Verificación final desde el navegador

```
Frontend:   http://IP_PUBLICA_EC2_1
Swagger UI: http://IP_PUBLICA_EC2_1:8086/swagger-ui.html
API docs:   http://IP_PUBLICA_EC2_1:8086/api-docs
```

## Comandos útiles

```bash
# Ver logs en tiempo real
docker logs springboot-backend -f
docker logs angular-frontend -f

# Ver logs últimas 50 líneas
docker logs springboot-backend --tail 50

# Bajar todos los servicios
docker compose -f docker-compose-app.yml down

# Actualizar imágenes y relanzar
docker pull concaflores/proyectobackend:latest
docker pull concaflores/proyectofrontend:latest
docker compose -f docker-compose-app.yml up -d

# Ver estado de contenedores
docker ps
```
