# Trabajo práctico — Granja: herencia, polimorfismo e interfaces

Proyecto Maven, **Java 21**, paquete único `ar.edu.utn.frc.backend`.

El sistema representa animales de producción (bovinos, porcinos y ovinos) y las dietas que se les asignan. Parte del modelo ya está escrito; **te toca escribir las clases que faltan**.


## El proyecto no compila al abrirlo, y está bien

Al clonar el proyecto vas a ver errores de compilación por todos lados:

```text
cannot find symbol: class Animal
cannot find symbol: class Bovino
cannot find symbol: class RazaBovino
```

Es el punto de partida esperado: faltan siete clases que tenés que crear vos. Hasta que `Animal` no exista, ni siquiera compila `AsignadorDieta`, porque su método recibe un `Animal`.

**Orden sugerido de trabajo** — cada paso destraba al siguiente:

1. `Animal` (la clase abstracta: es la que destraba casi todo)
2. `RazaBovino`, `RazaPorcino`, `RazaOvino` (los tres enums)
3. `Bovino`, `Porcino`, `Ovino` (las tres especies)
4. `AsignadorDietaService` (completar el TODO)

Recién después de los cuatro pasos el proyecto compila y podés correr los tests.

---

## Qué se te da

Todo esto ya está escrito y funcionando en `src/main/java/ar/edu/utn/frc/backend/`. **No hace falta que lo toques.**

| Archivo | Qué es |
|---|---|
| `Sexo.java` | enum: `MACHO`, `HEMBRA` |
| `Especie.java` | enum: `BOVINO`, `PORCINO`, `OVINO`, cada uno con su nombre |
| `Alimento.java` | enum con el valor energético de cada alimento en Mcal/kg |
| `Racion.java` | un alimento + su porcentaje dentro de una dieta |
| `Dieta.java` | plantilla con nombre, descripción y lista de raciones; valida que los porcentajes sumen 100 y calcula la densidad energética |
| `Dietas.java` | catálogo con las 4 plantillas que usan los tests |
| `Raza.java` | **interfaz** `Raza` |
| `Alimentable.java` | **interfaz** `Alimentable` |
| `AsignadorDieta.java` | **interfaz** `AsignadorDieta` |
| `App.java` | demo ejecutable, para ver el modelo andando cuando termines |

Y en `src/test/java/ar/edu/utn/frc/backend/`: los 8 `Ejercicio*Test.java` y `DatosDePrueba.java`, que arma el establecimiento de prueba.

> **Leé `DatosDePrueba.java`.** Es la mejor pista del trabajo: las llamadas que hace a los constructores te dicen exactamente qué firma tienen que tener las clases que vas a escribir.

---

## Qué tenés que crear

Siete archivos que **no existen** en el proyecto. Los creás vos.

| Archivo | Qué practica |
|---|---|
| `Animal.java` | clase **abstracta** que implementa **dos interfaces** y declara **dos métodos abstractos** |
| `Bovino.java` | **herencia** + **polimorfismo** |
| `Porcino.java` | **herencia** + **polimorfismo** |
| `Ovino.java` | **herencia** + **polimorfismo** |
| `RazaBovino.java` | **enum que implementa una interfaz** |
| `RazaPorcino.java` | **enum que implementa una interfaz** |
| `RazaOvino.java` | **enum que implementa una interfaz** |

Más un archivo a completar:

| Archivo | Qué falta |
|---|---|
| `AsignadorDietaService.java` | existe con la firma correcta, pero el método lanza `UnsupportedOperationException`. Seguí los `TODO` |

---

## Las tres interfaces que tenés que implementar

Están escritas; tu trabajo es cumplir el contrato.

### `Raza` — la implementan los tres enums de raza

```java
public interface Raza {
    String getNombre();
}
```

### `Alimentable` — la implementa `Animal`

```java
public interface Alimentable {
    double calcularConsumoDiario();
    Map<Alimento, Double> generarPlanDietario();
    double calcularEnergiaTotalDiaria();
}
```

### `AsignadorDieta` — la implementa `AsignadorDietaService`

```java
public interface AsignadorDieta {
    void asignarDieta(Animal animal, Dieta dieta);
}
```

---

## Contrato de cada clase

### `Animal` — clase abstracta

Es el corazón del trabajo. Implementa **dos** interfaces: `Alimentable` (el contrato nutricional) y `Comparable<Animal>` (orden natural por peso).

```java
public abstract class Animal implements Alimentable, Comparable<Animal> {

    protected float peso;
    private final LocalDate fechaNacimiento;
    private final Sexo sexo;
    private final Raza raza;          // OJO: el tipo es la INTERFAZ Raza, no un enum concreto
    private Dieta dieta;              // arranca en null: el animal todavía no tiene dieta

    public Animal(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, Raza aRaza)

    public float getPeso()
    public LocalDate getFechaNacimiento()
    public Sexo getSexo()
    public String getRaza()           // devuelve raza.getNombre(), un String
    public Raza getRazaEnum()         // devuelve el objeto Raza
    public Dieta getDieta()
    public void setDieta(Dieta aDieta)
    public boolean tieneDieta()       // true si ya se le asignó una dieta

    // Los dos puntos de variación entre especies:
    protected abstract double getFactorConsumo();
    public abstract Especie getEspecie();

    // Los tres métodos de Alimentable:
    @Override public double calcularConsumoDiario()
    @Override public Map<Alimento, Double> generarPlanDietario()
    @Override public double calcularEnergiaTotalDiaria()

    // El método de Comparable:
    @Override public int compareTo(Animal otro)   // por peso, de menor a mayor

    @Override public String toString()            // que no falle si no hay dieta
}
```

Que el campo `raza` sea de tipo `Raza` (la interfaz) y no `RazaBovino` es lo que permite que una sola clase `Animal` sirva para las tres especies. Es el motivo por el que existe la interfaz.

### `Bovino`, `Porcino`, `Ovino` — heredan de `Animal`

Las tres son cortas: el constructor y los dos métodos abstractos. Cada una recibe **su propio enum de raza** en el constructor y se lo pasa a `super(...)`, donde entra como `Raza`.

```java
public class Bovino extends Animal {

    public Bovino(float aPeso, LocalDate aFechaNacimiento, Sexo aSexo, RazaBovino aRaza) {
        super(aPeso, aFechaNacimiento, aSexo, aRaza);
    }

    @Override protected double getFactorConsumo() { /* ver tabla */ }
    @Override public Especie getEspecie()          { /* ver tabla */ }
}
```

| Clase | Constructor recibe | `getFactorConsumo()` | `getEspecie()` |
|---|---|---:|---|
| `Bovino` | `RazaBovino` | `1.5` | `Especie.BOVINO` |
| `Porcino` | `RazaPorcino` | `2.0` | `Especie.PORCINO` |
| `Ovino` | `RazaOvino` | `1.2` | `Especie.OVINO` |

### `RazaBovino`, `RazaPorcino`, `RazaOvino` — enums que implementan `Raza`

Un enum en Java puede implementar una interfaz. Cada constante lleva su nombre para mostrar:

```java
public enum RazaBovino implements Raza {

    HOLSTEIN("Holstein"),
    HEREFORD("Hereford"),
    ANGUS("Angus");

    private final String nombre;

    RazaBovino(final String aNombre) {
        nombre = aNombre;
    }

    @Override
    public String getNombre() {
        return nombre;
    }
}
```

Las constantes de las otras dos:

| Enum | Constantes |
|---|---|
| `RazaPorcino` | `DUROC("Duroc")`, `KUNEKUNE("Kunekune")`, `HAMPSHIRE("Cerdo Hampshire")` |
| `RazaOvino` | `MERINO("Merino")`, `CORRIEDALE("Corriedale")`, `TEXEL("Texel")` |

Los nombres de las constantes (`HAMPSHIRE`, `MERINO`, …) tienen que ser exactos: `DatosDePrueba` las usa por nombre y si no coinciden no compila.

---

## Reglas de negocio

### Consumo diario

Cuánto alimento come el animal por día, en kg:

```text
consumoDiario = peso × factorDeConsumo
```

El factor sale de `getFactorConsumo()`, que cada especie define distinto. Por eso `calcularConsumoDiario()` se escribe **una sola vez** en `Animal` y funciona para las tres especies: eso es polimorfismo.

### Plan dietario

`generarPlanDietario()` devuelve un `Map<Alimento, Double>` con los kg diarios de cada alimento. Se recorren las raciones de la dieta y para cada una:

```text
kilogramosDelAlimento = consumoDiario × porcentajeDeLaRacion / 100
```

`Racion` ya tiene un método que hace esa cuenta: buscalo antes de escribirla a mano.

> **Si el animal no tiene dieta asignada, devolvé un mapa vacío.** No lances excepción ni devuelvas `null`. Hay tests que lo verifican.

### Energía total diaria

```text
energiaTotalDiaria = consumoDiario × densidadEnergéticaDeLaDieta
```

`Dieta` ya sabe calcular su densidad y también ofrece un método que hace directamente esta multiplicación.

> **Si el animal no tiene dieta asignada, devolvé `0`.** También hay un test para esto.

### Ejemplo resuelto punta a punta

Un **bovino de 300 kg** con la dieta `BALANCEADO_BOVINO` (40 % maíz, 30 % soja, 30 % forraje):

```text
Consumo diario:  300 × 1.5 = 450 kg

Plan dietario:
  Maíz:     450 × 0.40 = 180 kg
  Soja:     450 × 0.30 = 135 kg
  Forraje:  450 × 0.30 = 135 kg

Energía aportada:
  Maíz:     180 × 3.40 = 612 Mcal
  Soja:     135 × 3.00 = 405 Mcal
  Forraje:  135 × 2.00 = 270 Mcal
                       ----------
  Energía total diaria = 1287 Mcal

Densidad energética de la dieta:
  0.40 × 3.40 + 0.30 × 3.00 + 0.30 × 2.00 = 2.86 Mcal/kg

Verificación:  450 × 2.86 = 1287 Mcal
```

Si tu código reproduce estos números, vas bien encaminado.

---

## Datos de referencia

### Energía de los alimentos (ya cargada en `Alimento`)

| Alimento | Energía |
|---|---:|
| Maíz | 3.40 Mcal/kg |
| Sorgo | 3.30 Mcal/kg |
| Trigo | 3.20 Mcal/kg |
| Soja | 3.00 Mcal/kg |
| Forraje | 2.00 Mcal/kg |

### Plantillas de dieta (ya cargadas en `Dietas`)

| Constante | Composición | Densidad |
|---|---|---:|
| `BALANCEADO_BOVINO` | 40 % maíz, 30 % soja, 30 % forraje | 2.86 Mcal/kg |
| `ENGORDE_PORCINO` | 50 % maíz, 30 % soja, 20 % trigo | 3.24 Mcal/kg |
| `MIXTO_SORGO` | 40 % maíz, 20 % sorgo, 20 % soja, 20 % forraje | 3.02 Mcal/kg |
| `PASTORIL_OVINO` | 60 % forraje, 25 % maíz, 15 % trigo | 2.53 Mcal/kg |

### El establecimiento de prueba (`DatosDePrueba.animales()`)

Los 28 tests trabajan sobre estos siete animales. Los dos Angus tienen dietas distintas a propósito: una misma raza puede recibir distintas formulaciones.

| # | Especie | Peso | Raza | Dieta | Consumo diario |
|---|---|---:|---|---|---:|
| 0 | Bovino | 300 kg | Hereford | `BALANCEADO_BOVINO` | 450.0 kg |
| 1 | Bovino | 420 kg | Angus | `MIXTO_SORGO` | 630.0 kg |
| 2 | Bovino | 260 kg | Angus | `BALANCEADO_BOVINO` | 390.0 kg |
| 3 | Porcino | 100 kg | Duroc | `ENGORDE_PORCINO` | 200.0 kg |
| 4 | Porcino | 140 kg | Cerdo Hampshire | `ENGORDE_PORCINO` | 280.0 kg |
| 5 | Porcino | 75 kg | Duroc | `ENGORDE_PORCINO` | 150.0 kg |
| 6 | Ovino | 55 kg | Merino | `PASTORIL_OVINO` | 66.0 kg |

Totales del establecimiento: **1350 kg** de peso, **2166 kg/día** de consumo, **6513.18 Mcal/día** de energía.

---

## Los 8 tests y qué valida cada uno

Cuando uno falle, esta tabla te dice dónde mirar.

| Test | Ejercita | Si falla, revisá |
|---|---|---|
| `Ejercicio1ConsumoDiarioPolimorficoTest` | `calcularConsumoDiario()` sobre las tres especies | los factores 1.5 / 2.0 / 1.2 en `getFactorConsumo()` |
| `Ejercicio2PesoPromedioTest` | `getPeso()` | el constructor de `Animal` y que `peso` se guarde |
| `Ejercicio3AnimalMasPesadoTest` | `getPeso()` | ídem anterior |
| `Ejercicio4EliminarConIteratorTest` | `getPeso()` sobre una `List` mutable | ídem anterior |
| `Ejercicio5AlimentosUtilizadosTest` | `tieneDieta()` y `getDieta()` | que `dieta` arranque en `null` y que `tieneDieta()` lo detecte |
| `Ejercicio6ConsumoPorAlimentoTest` | `generarPlanDietario()` | el reparto por porcentajes y el caso sin dieta (mapa vacío) |
| `Ejercicio7EnergiaTotalTest` | `calcularEnergiaTotalDiaria()` | la multiplicación por la densidad y el caso sin dieta (`0`) |
| `Ejercicio8OrdenarConComparatorTest` | `getPeso()` y `calcularEnergiaTotalDiaria()` | si el peso anda pero la energía no, el problema está en la dieta |

Los tests también dependen de `AsignadorDietaService`: mientras siga lanzando `UnsupportedOperationException`, **fallan los 28**, porque `DatosDePrueba` lo usa para asignarle la dieta a cada animal.

---

## Pistas y errores comunes

**Empezá por `Animal`.** Es la clase que destraba todas las demás. Mientras no exista, los errores de compilación no te dejan ver nada.

**El caso "sin dieta" no es un detalle.** Un `Animal` recién creado tiene `dieta == null`. Tres tests lo verifican: `unAnimalSinDietaNoAportaAlimentos` (ejercicio 5), `unAnimalSinDietaNoAcumulaNada` (ejercicio 6) y `unAnimalSinDietaNoSumaEnergia` (ejercicio 7). Si te aparece un `NullPointerException`, es esto.

`toString()` no tiene test, pero `App` lo usa para imprimir un porcino sin dieta: si no contemplás el caso, la demo revienta aunque los 28 tests estén en verde.

**No uses `instanceof` ni preguntes por el tipo concreto.** Nunca hace falta: `getFactorConsumo()` y `getEspecie()` ya resuelven la diferencia entre especies. Si estás por escribir `if (animal instanceof Bovino)`, hay algo mal en el diseño.

**El peso es `float`, los cálculos son `double`.** Guardá `peso` como `float` (así lo usan los tests) pero devolvé `double` en los cálculos, para no perder precisión.

**`Map<Alimento, Double>`: usá `EnumMap`.** Cuando la clave es un enum, `new EnumMap<>(Alimento.class)` es más eficiente que `HashMap` y además itera en el orden de declaración del enum.

**Si un alimento aparece en dos raciones**, los kg se suman. `Map.merge(clave, valor, Double::sum)` hace exactamente eso en una línea.

---

## Ejecutar la demo

Una vez que compile, `App` imprime el resumen de dos animales:

```bash
mvn compile
java -cp target/classes ar.edu.utn.frc.backend.App
```

Salida esperada para el primer caso:

```text
Animal: Bovino Hereford
Peso: 300.00 kg
Dieta: Balanceado bovino

PLAN DIETARIO DIARIO

MAIZ           180.00 kg     612.00 Mcal
SOJA           135.00 kg     405.00 Mcal
FORRAJE        135.00 kg     270.00 Mcal

TOTAL          450.00 kg    1287.00 Mcal

Densidad energetica: 2.86 Mcal/kg
```

---

## Checklist de entrega

- [ ] Creaste los 7 archivos (`Animal`, las 3 especies, los 3 enums de raza).
- [ ] Completaste los `TODO` de `AsignadorDietaService`.
- [ ] `mvn test` da `Tests run: 28, Failures: 0, Errors: 0`.
- [ ] No modificaste ningún archivo de `src/test`.
- [ ] No hay ningún `instanceof` en tu código.
