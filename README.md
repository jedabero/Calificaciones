# Calificaciones

Este projecto tiene dos propositos:

  * Crear una aplicación que permita administrar las notas a estudiates de manera flexible
  * Entender a mayor profundidad lo explicado en [Google's Android Architecture TODO-MVP](https://github.com/googlesamples/android-architecture)

### Conceptos

  * Nota:

    Una nota en el contexto de la aplicación consiste en un `valor` (ej.: 3.5, 4.2, 10, 19) y un `peso
    ponderado` (ej.: 30, 40, 100). En un grupo de notas el peso por lo general indica el porcentaje de
    influecia de la nota en el promedio final, por ejemplo, nota corte 1 es 30%, nota corte 2 es 30% y
    nota corte 3 es 40%. Se da a entender entonces que los pesos suman un total por lo general 100.

    En esta aplicación se implementará de tal manera que:

      *&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;W<sub>i</sub><br>
      w<sub>i</sub> = `----`<br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&sum;W<sub>i</sub>*

    Donde *W<sub>i</sub>* es el peso de la nota *i* y *w<sub>i</sub>* es el peso ponderado final.

  * Asignatura:

    Una asignatura consiste principalmente en un `grupo de notas`. A estas se les puede asignar un
    `nombre` cualquiera pero un `código` único. Tienen como propiedad derivada una `definitiva`.

  * Periodo:

    Un periodo consiste en un `grupo de asignaturas`. Por lo general un periodo puede tener `nombre`
    en relación a las fechas en que transcurre y su función. Por ejemplo, 2016-2 ó 20162 sería el
    nombre del segundo semestre del año 2016.

## Características

Esta aplicación ofrecerá

  * Administración de periodos
    * Listado de periodos
    * Adicion y edición de periodos
  * Administración de asignaturas
    * Listado de asignaturas en un periodo (Detalle periodo)
    * Adicion y edición de asignaturas
  * Administración de notas
    * Listado de asignaturas en una asignatura (Detalle asignatura)
    * Adicion y edición de notas
  * Estadisticas



Cada característica tiene:
  * Contratos (interfaces) que definen la Vista y el Presentador
  * Un fragmento que implementa la interfaz de la Vista
  * Un presentador especifíco que implementa la interfaz Presentador
