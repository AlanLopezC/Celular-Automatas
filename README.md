# Proyecto III: Autómatas celulares

Para este proyecto utilizarás las bibliotecas de ```JavaFX```.  Necesitas descargar las que correspondan a la versión que instalaste de la ```jdk```.  Encontrarás las más actuales con soporte en [Gluon JavaFX](https://gluonhq.com/products/javafx/).  La documentación está en [Openjfx](https://openjfx.io/).

Ant no funciona bien con ```JavaFX```, así que usaremos otra tecnología, también muy útil en el mundo de los programadores, aunque más popular en el mundo del lenguaje ```C```.  En lugar del ```build.xml```, esta vez se te provee con un archivo ```Makefile```, este configura las acciones del comando ```make```.  Necesitarás abrir este archivo y, en la línea que dice:
```
PATH_TO_FX=<dirección de JavaFX>
```
escribir la dirección dónde descargaste ```JavaFX```. Por ejemplo:
```
PATH_TO_FX=/home/blackzafiro/Descargas/openjfx-11.0.1_linux-x64_bin-sdk/javafx-sdk-11.0.1/lib/
```

Si tienes Ubuntu y te indica que no tienes ```make```, instálalo con:
```
sudo apt install build-essential
```
Este archivo también define objetivos.  Para compilar el código auxiliar utiliza:
```
make compile
```
Para ejecutar:
```
make run
```
Para borrar los archivos generados:
```
make clean
```
