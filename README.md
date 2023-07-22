# Proyecto: Cellular Automatas

This is a program similar to the game of life, where each cell acts upon the state of the cells that are adjacent to it.

# Epidemic
The red cells are sick, and infect the ones adjacent to them, after n steps the sick cell becomes immune (green) and losses immunity after m steps.

![Epidemia](https://github.com/AlanLopezC/Celular-Automatas/assets/63161554/8745c79b-0b4a-4f99-b55b-d30874987746)


# Forest Fire
This is a stochastic simulation, where in each cell there's a probability to grow a tree, to start to burn or be immune to adjacent fires.

![Incendio](https://github.com/AlanLopezC/Celular-Automatas/assets/63161554/83263443-c64b-47f2-83bd-65780e9f2d1b)

# Run the code
You need to install JAVAFX and update PATH_TO_FX in the Makefile with the path in your computer.
```
PATH_TO_FX=<path de JavaFX>
```
Example:
```
PATH_TO_FX=/home/blackzafiro/Downloads/openjfx-11.0.1_linux-x64_bin-sdk/javafx-sdk-11.0.1/lib/
```

If you have Ubuntu and don't have ```make```, install it with:
```
sudo apt install build-essential
```
For compiling:
```
make compile
```
For execution:
```
make run
```
For erasing generated files:
```
make clean
```
