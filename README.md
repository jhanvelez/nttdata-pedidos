# NTTData Pedidos

API de pedidos empresariales usando Spring Boot (Hexagonal Architecture) con PostgreSQL.

---

## Requisitos

* Docker & Docker Compose
* Java 17 (opcional si se usa Docker)
* Maven (opcional si se usa Docker)
* `curl` o Postman para probar la API

---

## Configuración

1. Clonar el repositorio:

```bash
git clone https://github.com/jhanvelez/nttdata-pedidos.git
cd nttdata-pedidos
```

2. Configurar variables de entorno (opcional)
   Por defecto, la base de datos se expone en el puerto `5433` y la aplicación en el `8080`.
   Si quieres, puedes cambiarlo en el `docker-compose.yml`.

---

## Levantar la aplicación

### 1. Construir las imágenes

```bash
docker compose build --no-cache
```

### 2. Levantar los contenedores

```bash
docker compose up
```

Esto levantará:

* **pedidos_db** → PostgreSQL en `localhost:5433`
* **pedidos_app** → Spring Boot en `localhost:8080`

### 3. Verificar que la aplicación esté corriendo

```bash
docker compose ps
```

Deberías ver ambos contenedores **Running**.

### 4. Probar la API

Por ejemplo, para la ruta raíz:

```bash
curl http://localhost:8080
```

O usar Postman/Insomnia apuntando a:

```
http://localhost:8080/api/
```

> Nota: La autenticación básica está habilitada. Las rutas `/api/users/**` están permitidas sin autenticación.

---

## Parar la aplicación

```bash
docker compose down
```

Esto detiene y elimina los contenedores, pero mantiene las imágenes y volúmenes.

---

## Limpieza total (opcional)

```bash
docker compose down -v --rmi all
```

Esto elimina contenedores, volúmenes e imágenes generadas.

---

## Notas

* Si hay problemas de collation con PostgreSQL, ejecutar:

```sql
ALTER DATABASE pedidosdb REFRESH COLLATION VERSION;
```

* Para desarrollo local sin Docker, se puede correr directamente con Maven:

```bash
./mvnw spring-boot:run
```

---

### Autor

Jhan Robert Vélez
