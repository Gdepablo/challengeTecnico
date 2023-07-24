
## Challenge Tecnico

Este proyecto contiene el código del Backend para la aplicación X. El enunciado del proyecto es de la empresa Ensolvers, por lo que no se incluye aquí por razones de confidencialidad.

### Estado del Backend

El Backend está completo y ha pasado por varias etapas de desarrollo, incluyendo la fase 1, fase 2 y funcionalidades adicionales. Algunas de las funcionalidades que implementé se salen del alcance original, como la creación de un controller de usuarios. Sin embargo, fue necesario para depurar y facilitar el inicio de sesión. Además, durante el desarrollo, se creó una entidad de usuario que también está vinculada a las notas del usuario. Por ahora, solo falta implementar el Frontend para completar el proyecto.

### Script de Ejecución

El script del programa se actualizó para permitir la ejecución en sistemas Mac y Linux, como se solicitó. Esto garantiza que el proyecto sea compatible con diferentes entornos.

### Credenciales de Prueba

Para acceder al sistema y probar las funcionalidades, se pueden utilizar las siguientes credenciales de inicio de sesión:

- Username: admin
- Password: admin

### Importante sobre HTTP y Observers

En este proyecto, los HTTP son Observers que nacen y mueren junto con cada petición. No es necesario gestionar su ciclo de vida manualmente. Sin embargo, es crucial tener en cuenta que los eventEmitter deben desuscribirse usando `ngOnDestroy` para evitar pérdidas de memoria (memory leaks). Un ejemplo de esto se puede encontrar en `notes.component.ts`.

### Deploy en Railway

Este proyecto se puede implementar en Railway sin problemas. Sin embargo, debido a limitaciones de recursos, no se ha llevado a cabo la implementación. Pero en cualquier caso, es fácil realizar el despliegue cuando se tenga el Frontend terminado.

