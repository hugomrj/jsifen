# Proyecto JSIFEN

Este proyecto proporciona una interfaz para consultas al sistema JSIFEN (Sistema de Facturación Electrónica de Paraguay) utilizando una arquitectura limpia y el framework Quarkus.

---

**Estructura del Proyecto**  
El proyecto sigue los principios de Clean Architecture:

```
src/main/java/py/com/jsifen/
├── domain/              # Lógica de negocio y entidades
├── infrastructure/      # Implementaciones concretas (config, REST, SOAP)
└── presentation/        # Controladores y endpoints REST
```

---

**Configuración**

**Archivo de configuración** `sifen.properties`  
Edite el archivo `src/main/resources/sifen.properties` con sus credenciales:

```
sifen.id-csc=123                    # ID CSC proporcionado por SIFEN
sifen.csc=ABCD                      # Código CSC proporcionado por SIFEN
sifen.ambiente=prod                 # Ambiente (prod/test)
sifen.keystore.path=/ruta/al/certificado.p12  # Ruta al certificado
sifen.keystore.password=ABCD12345   # Contraseña del certificado
```

**Puerto de la aplicación**  
El puerto predeterminado es 8000. Puede modificarse en `application.properties`:

```
quarkus.http.port=8000
```

---

**Ejecución de la Aplicación**

**Modo desarrollo**
```bash
./gradlew quarkusDev
```

**Modo producción**
```bash
# Empaquetar
./gradlew build

# Ejecutar
java -jar build/quarkus-app/quarkus-run.jar
```

**Ejecutable nativo**
```bash
# Compilar ejecutable nativo
./gradlew build -Dquarkus.native.enabled=true

# Ejecutar
./build/jsifen-1.0.0-SNAPSHOT-runner
```

---

**Documentación de la API**  
Una vez ejecutada la aplicación, acceda a la documentación en:

```
http://localhost:8000/doc/
```

La documentación incluye múltiples formatos:

* Swagger UI
* Redoc
* Stoplight
* RapiDoc

---

**Testing**  
Ejecute las pruebas con:

```bash
./gradlew test
```

---

**Dependencias**

El proyecto utiliza:

* Quarkus 3.26.2 (Framework Java)
* Gradle 9.0.0 (Gestión de dependencias)
* SOAP Client (Comunicación con servicios SIFEN)
* SSL Configuration (Manejo de certificados)

---

**Requisitos del Sistema**

* **Java**: 17 o superior (se recomienda Java 21 LTS)
* **Gradle**: 9.x
* **OS**: Linux, macOS o Windows
* **Certificado PKCS12**: requerido para la conexión con SIFEN
* **Puerto disponible**: por defecto 8000

---
