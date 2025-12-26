# 🧾 Products Batch Processor

Aplicación Spring Boot que utiliza Spring Batch para procesar productos desde un archivo CSV y persistirlos en una base de datos PostgreSQL.

El job batch se ejecuta mediante una petición HTTP, lo que permite lanzar el procesamiento bajo demanda.

# 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Batch
- Spring Data JPA
- PostgreSQL
- Maven

# 📄 Formato del CSV

Ejemplo del archivo CSV a procesar:

```text
name,description,category,subcategory,price
Garden Tools,Complete set for garden.,Garden,Garden Tools,$9207.16
Coffee,Freshly roasted coffee delivered to your door.,Food,Beverages,$2715.57
Mac & Cheese,Creamy vegan mac and cheese.,Food,Frozen Meals,$6278.10
```

# ⚙️ Instalación y configuración

### Paso 1: Clonar el Repositorio

```bash
git clone https://github.com/miguelgonzalezzdev/products-batch.git
cd products-batch
```

### Paso 2: Configurar postgres

Crea una base de datos en PostgreSQL y ejecuta la siguiente consulta para crear la tabla `products`:

```sql
CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    category TEXT,
    subcategory TEXT,
    price NUMERIC(10,2)
);

```

### Paso 3: Configurar application.yaml

En el repositorio se incluye un archivo de configuración de ejemplo:

```text
src/main/resources/application.yaml.example
```

Cópialo y crea tu propio archivo `application.yaml`:

```bash
cp src/main/resources/application-example.yaml src/main/resources/application.yaml
```

Configura dentro del archivo las credenciales de tu base de datos PostgreSQL:

```text
datasource:
    url: jdbc:postgresql://127.0.0.1:5432/productsbatch
    username: postgres
    password: your_password
```

### Paso 4: Ejecuta el proyecto

Una vez configurado, levanta la aplicación. Para ejecutar el job batch, envía una petición GET al endpoint:

```text
http://127.0.0.1:8080/productsbatch/api/products/import
```

# 📌 Notas finales

Este proyecto ha sido desarrollado como proyecto de aprendizaje para el procesamiento batch con Spring Boot y Spring Batch.

Diseñado con ❤️ por [**Miguel**](https://miguelgonzalezdev.es)
