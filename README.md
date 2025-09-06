# Gestión de Planos Arquitectónicos con Spring Framework

## Escuela Colombiana de Ingeniería - Arquitecturas de Software
### Josué Hernández

El ejercicio introductorio se encuentra en la rama "Ejercicio-introductorio"

![](img/ClassDiagram1.png)

Este proyecto implementa un sistema de gestión de planos arquitectónicos utilizando Spring Framework con inyección de dependencias y patrones de filtrado intercambiables.

---

## 🚀 Implementación Completa

### ✅ Funcionalidades Implementadas

#### 1. **Configuración Spring Framework**
- **Inyección de dependencias**: Configurada mediante anotaciones (`@Service`, `@Component`, `@Autowired`)
- **Configuración**: Clase `BlueprintsConfiguration` con `@Configuration` y `@ComponentScan`
- **Persistencia**: `InMemoryBlueprintPersistence` configurado como `@Component`

#### 2. **Operaciones CRUD Completas**
- ✅ `addNewBlueprint()`: Registra un nuevo plano
- ✅ `getBlueprint()`: Obtiene un plano específico por autor y nombre
- ✅ `getBlueprintsByAuthor()`: Obtiene todos los planos de un autor
- ✅ `getAllBlueprints()`: Obtiene todos los planos del sistema

#### 3. **Sistema de Filtros Intercambiables**

##### 🔍 **Filtro A - Redundancias** (`RedundancyBlueprintFilter`)
- **Función**: Suprime puntos consecutivos duplicados
- **Marcado con**: `@Primary` (filtro predeterminado)
- **Ejemplo**: `[Point(10,10), Point(10,10), Point(20,20)]` → `[Point(10,10), Point(20,20)]`

##### 🔍 **Filtro B - Submuestreo** (`SubsamplingBlueprintFilter`)
- **Función**: Suprime 1 de cada 2 puntos (intercalado)
- **Ejemplo**: `[P1, P2, P3, P4, P5, P6]` → `[P1, P3, P5]`

---

## 🏗️ Arquitectura y Diseño

### **Patrones Implementados**
- **Inyección de Dependencias**: Spring gestiona todas las dependencias
- **Strategy Pattern**: Los filtros implementan una interfaz común (`BlueprintFilter`)
- **Repository Pattern**: `BlueprintsPersistence` abstrae el almacenamiento
- **Service Layer**: `BlueprintsServices` actúa como capa de servicio
- **Configuration**: Configuración basada en anotaciones de Spring

### **Estructura del Proyecto**
```
src/main/java/edu/eci/arsw/blueprints/
├── model/
│   ├── Blueprint.java                    # Modelo de plano
│   └── Point.java                       # Modelo de punto con equals/hashCode
├── persistence/
│   ├── BlueprintsPersistence.java       # Interfaz de persistencia
│   ├── BlueprintNotFoundException.java
│   ├── BlueprintPersistenceException.java
│   └── impl/
│       ├── InMemoryBlueprintPersistence.java  # Implementación en memoria
│       └── Tuple.java
├── filters/
│   ├── BlueprintFilter.java             # Interfaz de filtros
│   └── impl/
│       ├── RedundancyBlueprintFilter.java    # Filtro de redundancias (@Primary)
│       └── SubsamplingBlueprintFilter.java   # Filtro de submuestreo
├── services/
│   └── BlueprintsServices.java          # Servicio principal (@Service)
├── config/
│   └── BlueprintsConfiguration.java     # Configuración Spring (@Configuration)
└── main/
    └── Main.java                        # Programa principal de demostración
```

---

## 🔄 Intercambio de Filtros

Para alternar entre filtros, **simplemente intercambie la anotación `@Primary`**:

### **Configuración Actual (Filtro de Redundancias)**:
```java
@Component
@Primary
public class RedundancyBlueprintFilter implements BlueprintFilter {
```

```java
@Component  
public class SubsamplingBlueprintFilter implements BlueprintFilter {
```

### **Para Cambiar a Submuestreo**:
```java
@Component
public class RedundancyBlueprintFilter implements BlueprintFilter {
```

```java
@Component
@Primary
public class SubsamplingBlueprintFilter implements BlueprintFilter {
```

**¡Sin cambiar ningún otro código!** Solo las anotaciones.

---

## ▶️ Cómo Ejecutar

### **1. Ejecutar Todas las Pruebas**
```bash
mvn test
```

### **2. Ejecutar Programa Principal de Demostración**
```bash
mvn exec:java
```

### **3. Compilar Proyecto**
```bash
mvn compile
```

---

## 🧪 Pruebas Implementadas

### **Resultado de Pruebas**
```
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS ✅
```

### **Cobertura de Pruebas**

#### **Persistencia** (`InMemoryPersistenceTest`) - 5 Tests
- ✅ Guardar y cargar blueprints
- ✅ Manejo de blueprints duplicados  
- ✅ Obtener blueprints por autor
- ✅ Obtener todos los blueprints
- ✅ Manejo de autores inexistentes

#### **Filtro de Redundancias** (`RedundancyBlueprintFilterTest`) - 3 Tests
- ✅ Filtrar puntos consecutivos redundantes
- ✅ Manejo de blueprints vacíos
- ✅ Blueprints sin redundancias

#### **Filtro de Submuestreo** (`SubsamplingBlueprintFilterTest`) - 4 Tests
- ✅ Submuestreo intercalado
- ✅ Blueprints vacíos
- ✅ Un solo punto
- ✅ Número impar de puntos

---

## 🎯 Demostración de Funcionalidad

### **Salida del Programa Principal**
```
=== DEMO DE GESTIÓN DE BLUEPRINTS ===

1. REGISTRANDO BLUEPRINTS:
   - Registrado: Casa Moderna de Juan
   - Registrado: Edificio Comercial de Juan
   - Registrado: Residencia Familiar de Maria

2. CONSULTANDO BLUEPRINT ESPECÍFICO:
   Blueprint: Casa Moderna
   Autor: Juan
   Puntos originales: 8, Puntos después del filtro: 6
   Puntos filtrados: 
      Point{x=10, y=10}
      Point{x=20, y=20}
      Point{x=30, y=30}
      Point{x=40, y=40}
      Point{x=50, y=50}
      Point{x=60, y=60}

3. CONSULTANDO BLUEPRINTS POR AUTOR:
   Blueprints de Juan: 2
      - Casa Moderna (6 puntos filtrados)
      - Edificio Comercial (6 puntos filtrados)

   Blueprints de Maria: 1
      - Residencia Familiar (4 puntos filtrados)

4. CONSULTANDO TODOS LOS BLUEPRINTS:
   Total de blueprints en el sistema: 4
      - Casa Moderna de Juan (6 puntos filtrados)
      - Edificio Comercial de Juan (6 puntos filtrados)
      - Residencia Familiar de Maria (4 puntos filtrados)
      - _bpname_  de _authorname_ (2 puntos filtrados)

=== FILTRO ACTUAL: RedundancyBlueprintFilter (Filtro de redundancias) ===
Para cambiar al filtro de submuestreo, intercambie las anotaciones @Primary
entre RedundancyBlueprintFilter y SubsamplingBlueprintFilter
```


---

## 🔧 Detalles Técnicos

### **Tecnologías Utilizadas**
- **Spring Framework 5.3.23**: Inyección de dependencias (actualizado para compatibilidad)
- **JUnit 4.12**: Pruebas unitarias
- **Maven**: Gestión de dependencias y construcción
- **Java 8**: Lenguaje de programación
- **Maven Exec Plugin 3.1.0**: Para ejecución del programa principal

### **Principios SOLID Aplicados**
- **S**: Cada clase tiene una responsabilidad única
- **O**: Abierto para extensión (nuevos filtros), cerrado para modificación
- **L**: Los filtros son intercambiables sin afectar funcionalidad
- **I**: Interfaces segregadas (`BlueprintFilter`, `BlueprintsPersistence`)
- **D**: Dependencias de abstracciones, no concreciones

### **Configuraciones Spring**
```java
@Configuration
@ComponentScan("edu.eci.arsw.blueprints")
public class BlueprintsConfiguration {
    // Configuración automática por anotaciones
}
```

---

## ✨ Características Destacadas

1. **🔄 Filtros Intercambiables**: Cambio dinámico solo con anotaciones
2. **🧪 Cobertura Completa**: 12 pruebas unitarias con 100% éxito
3. **🏗️ Arquitectura Limpia**: Separación clara de responsabilidades
4. **📦 Spring Nativo**: Uso completo de inyección de dependencias
5. **🎯 Demostración Funcional**: Programa principal que muestra todas las capacidades
6. **📚 Documentación Completa**: Código bien documentado y README detallado

---

