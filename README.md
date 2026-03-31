# 🚀 LevelUp - Aplicación de Retos Gamificados

LevelUp es una aplicación web para la gestión de retos de crecimiento personal, que permite a los usuarios crear, seguir y completar desafíos, con autenticación segura mediante Auth0.

---

## Arquitectura

El proyecto está dividido en dos componentes principales:

### Backend (Spring Boot)

Ubicado en la carpeta `backend/`

* API REST
* Arquitectura en capas (Clean Architecture)
* Persistencia con PostgreSQL
* Autenticación con Auth0
* Manejo de metadata de usuario

### Frontend (React)

Ubicado en la carpeta `frontend/`

* Interfaz de usuario personalizada
* Flujo de autenticación completo
* Consumo de API REST
* Manejo de JWT

---

## Autenticación

Se utiliza Auth0 para:

* Registro de usuarios (email, password, name)
* Login seguro mediante JWT
* Almacenamiento de metadata:

  * Tipo de documento
  * Número de documento

### Flujo implementado:

1. Registro de usuario
2. Login
3. Completar perfil (metadata)
4. Acceso a la aplicación

---

## Ejecución del proyecto

### Backend

```bash
cd backend
./gradlew bootRun
```

En Windows:

```powershell
cd backend
.\gradlew bootRun
```

---

### Frontend

```bash
cd frontend
npm install
npm start
```

---

## URLs

* Frontend: http://localhost:3000
* Backend: http://localhost:8080

---

## Tecnologías utilizadas

### Backend

* Java + Spring Boot
* Spring Security
* Auth0
* PostgreSQL
* Flyway
* Docker

### Frontend

* React
* React Router
* Fetch API

---

## Funcionalidades

✔ Registro de usuarios
✔ Login con Auth0
✔ Flujo adicional de perfil (metadata)
✔ CRUD de retos
✔ Gestión de progreso
✔ Validaciones
✔ Manejo de errores

---

## Buenas prácticas aplicadas

* Separación frontend/backend
* Arquitectura en capas
* Uso de DTOs
* Integración con servicios externos (Auth0)
* Manejo de autenticación con JWT

---

## Autor

Proyecto académico - LevelUp
