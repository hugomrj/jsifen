# Proyecto JSIFEN

Este proyecto proporciona una interfaz para consultas al sistema JSIFEN (Sistema de Facturación Electrónica de Paraguay) utilizando una arquitectura limpia y el framework Quarkus.

## Estructura del Proyecto
La aplicación sigue **Clean Architecture**, lo que permite que el flujo sea claro:
```
src/main/java/py/com/jsifen/
│
├── application/   # Casos de uso: coordinan la interacción entre capas
├── domain/        # Modelo de negocio puro y contratos de repositorio
├── infrastructure/# Implementación técnica: clientes SOAP, procesadores, utilidades y configuración
└── presentation/  # Controladores REST que reciben peticiones y devuelven respuestas
```

Flujo típico:
1. **presentation** recibe la solicitud HTTP.
2. Llama al caso de uso en **application**.
3. El caso de uso usa entidades y repositorios de **domain**.
4. **infrastructure** provee las implementaciones concretas (por ejemplo, llamadas a SIFEN).


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



## Modo producción
 Compilar y copiar los archivos de configuración

```bash
./gradlew build
```
El compilado queda en:

```
# build/quarkus-app/
# ├── quarkus-run.jar
# └── config/
#     ├── application.properties
#     └── sifen.properties
```
Podés copiar toda la carpeta quarkus-app a la máquina destino,
editar los archivos dentro de config/ y ejecutar:

```bash
# Ir a la carpeta del compilado (el nombre puede cambiar según el proyecto)
cd <nombre-de-la-carpeta>
```
Ejecutar la aplicación tomando la configuración de config/:
```bash
# Ejecutar usando la carpeta config interna
java -Dquarkus.config.locations=./config -jar quarkus-run.jar
```

**Documentación de la API**

Una vez ejecutada la aplicación, acceda a la documentación en:

```bash
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


**Requisitos del Sistema**

* **Java**: 17 o superior (se recomienda Java 21 LTS)
* **OS**: Linux, macOS o Windows
* **Certificado PKCS12**: requerido para la conexión con SIFEN
* **Puerto disponible**: por defecto 8000

---


## Licencia

Este proyecto está bajo la [Licencia MIT](./LICENSE).
