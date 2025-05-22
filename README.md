# M06-UF4-A01

##  Pruebas de uso 

### - 1. Registrar compañia 

Vista del menu principale cuando escogemos la opcion 1, que corresponde a registrar una nueva compañia 

![alt text](<Foto Diagrama/image.png>)

### - 2. Listar compañias
Ahora si seleccionamos la opcion 2, que lista todas las compañias, vemos que se ha creado correctamente. 

![alt text](<Foto Diagrama/image-1.png>)

### - 3. Realizar reserva 

Si seleccionamos la opcion 3 del menu, para registrar una nueva reserva,  nos solicirara que ingresemos el ID del cliente, el coste total  y la fecha (en formato YYYY-MM-DD).

![alt text](<Foto Diagrama/image-2.png>)


Una vez introducimos los parametros solicitados de forma correcta, procedera a mostrarnos un listado de las posibles empresas, y deberemos escribir el ID de la indicada. 

![alt text](<Foto Diagrama/image-3.png>)

Una vez confirma la id, nos aparece un mensaje dde confirmación de la reserva, y nos devuelve al menu inicial

![alt text](<Foto Diagrama/image-4.png>)

### - 4. Eliminar compañia 

Si seleccionamos la opción 4, para eliminar una compañia, nos volvera a aparecer un listaddo con las ddiferentes empressas posibles,  y deberemos escribir el ID de la compañia que deseemos borrar.

![alt text](<Foto Diagrama/image-5.png>)

### Vista de las tablas desde la terminal de MariaDB 

![alt text](<Foto Diagrama/image-6.png>)

![alt text](<Foto Diagrama/image-7.png>)

## Preguntas de reflexión 

### - 1. Logica de aplicación del patron DAO 

El patron DAO se encarga de la logica de acceso a datos, capsula las operaciones de consulta CRUD. De esta forma separamos la logica de negocios de la aplicación de la de acceso a la base de daatos, permitiendo cambios en esta sin afectar el resto del sistema. 

### -2. Utilidades del patron DAO 

Una de sus principales utilidades es que permite mantener un codigo mas limpio, modular y facil de mantener.  Facilita eliminar el codigo duplicado ya,que se centralizan operaciones comunes. Esto mejora la reutilización de codigo.  Tambien es beneficioso por su seguridad y encapsulación, ya que no expone toda la información interna de la entidad, ademas del control de formato de la respuesta. 

### -3. Cambios realizados a diferentes clases 

En las clases de los controladores se ha debido hacer modificaciones para que en vez de llamar a metodos estaticos, llamase a instancias de las clases DAO. Tambien se eliminaron las consultas CRUD basicas de CompaniaDAO y ReservaDAO ya que ahora se unifican en GenDAOImpl. 


### - 4. Diagrama completo 
![alt text](<Foto Diagrama/M06-UF4.drawio.png>)

### - 5 . Clase abstracta
La clase abstracta cumple un gran papel al centralizar la lógica común de los DAO, evitando duplicaciones y facilitando la reutilización de código. 

No es estrictamente necesaria, pero es muy recomendable cuando se gestionan múltiples entidades con operaciones similares. Además, ayuda a abstraer la gestión de conexiones, la ejecución de sentencias y el tratamiento de errores.

En el caso de la actividad 2 hubiera sido de gran utilidad  para eliminar el codigo repetido de las operaciones CRUD, tambien ofrece la posibilidad de manejar las conexiones con una nueva clase DAO, que realizase la apertura y cierre de conexiones a paarte de gestionar las excepciones. 

-----

Como dato curioso, actibvidad ya la tenia medio preaparada, ya que la use como estudio para el examen. 

![alt text](<Foto Diagrama/foto.png>)