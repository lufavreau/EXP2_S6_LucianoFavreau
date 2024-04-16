import java.util.Scanner;

public class EXP2_S6_LucianoFavreau {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        String[][] reservas = new String[4][64]; // 4 matrices de 8x8 asientos para cada evento
        String[] fechas = {"2024-03-23 - Campo de Batalla", "2024-03-24 - Tsunami", "2024-03-25 - La Chilena", "2024-03-26 - Paraíso"};
        double totalGastado = 0; // gasto total
        int entradasCompradas = 0;
        int descuentos = 0; //contador de descuentos
        int dosXuno = 0;// contador de 2x1 aplicados

        // dejar las reservas como disponibles (0)
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 64; j++) {
                reservas[i][j] = "0";
            }
        }

        while (continuar) { //loop continuar es falso o salir
            int opcion;
            //fuerzo la opción valida o sigue el loop, control
            for (opcion = 0; opcion < 1 || opcion > 4; ) {
                System.out.println("Bienvenidos al sistema de reservas de Teatro Moro");
                System.out.println("Menú principal:");
                System.out.println("1. Vender entrada");
                System.out.println("2. Ver asientos disponibles");
                System.out.println("3. Borrar reserva");
                System.out.println("4. Salir");
                System.out.print("Elige una opción: ");
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción no válida. Por favor, elige una opción válida.");
                }
            }
            // opciones validas
            if (opcion >= 1 && opcion <= 3) {
                System.out.println("Las fechas disponibles son:");
                for (int i = 0; i < 4; i++) {
                    System.out.println((i + 1) + ". " + fechas[i]);
                }

                System.out.print("¿Qué fecha deseas elegir? Elige 1, 2, 3, 4: ");
                int fechaSeleccionada = scanner.nextInt() - 1;
               //control
                if (fechaSeleccionada >= 0 && fechaSeleccionada < 4) {
                    if (opcion == 1) { // Comprar entrada
                        System.out.println("Seleccione el asiento:");
                        System.out.println("Ingrese fila (1-8): ");
                        int fila = scanner.nextInt() - 1;
                        System.out.println("Ingrese columna (1-8): ");
                        int columna = scanner.nextInt() - 1;

                        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
                            int indice = fila * 8 + columna;
                            if (reservas[fechaSeleccionada][indice] == "0") {
                                double precioEntrada = 0;
                                String zona = "";
                                
                                if ((columna <= 2 || columna >= 7) && fila <= 7) { //palcos
                                    precioEntrada = 7200;
                                    zona = "Palco";
                                } else if (columna >= 3 && columna <= 6 && fila <= 4) { //platea Alta
                                    precioEntrada = 11000;
                                    zona = "Platea Alta";
                                } else if (columna >= 3 && columna <= 6 && fila >= 5 && fila <= 7) { //platea Baja
                                    precioEntrada = 19000;
                                    zona = "Platea Baja";
                                } else if (fila == 7) { // VIP
                                    precioEntrada = 25000;
                                    zona = "VIP";
                                }

                                if (precioEntrada > 0) {
                                    System.out.println("¿Es estudiante o de la tercera edad? (1: No, 2: Estudiante, 3: Tercera Edad)");
                                    int tipoDescuento = scanner.nextInt();
                                    double descuento = 0;
                                    if (tipoDescuento == 2) {
                                        descuento = precioEntrada * 0.10; //descuento 10% para estudiantes
                                    } else if (tipoDescuento == 3) {
                                        descuento = precioEntrada * 0.15;  //descuento 15% para tercera edad
                                    }
                                    precioEntrada -= descuento;
                                    reservas[fechaSeleccionada][indice] = "1"; //marcar como reservado
                                    entradasCompradas++;
                                    totalGastado += precioEntrada;

                                    if (entradasCompradas % 2 == 0) { //si hay número par de entradas para el 2x1
                                        System.out.println("¿Desea aplicar la oferta 2x1 para estas dos últimas entradas? (si/no)");
                                        String aplicarOferta = scanner.next();
                                        if (aplicarOferta.equalsIgnoreCase("si")) {
                                            totalGastado -= precioEntrada / 2;  //se aplica el 2x1
                                            System.out.println("Oferta 2x1 aplicada a las últimas dos entradas.");
                                            dosXuno=++dosXuno;
                                        }
                                    }
                                    System.out.println("Reserva realizada con éxito en la zona " + zona + ".");
                                    System.out.println("Precio final de la entrada: $" + precioEntrada);
                                    if (descuento > 0){
                                        descuentos=++descuentos;
                                    }
                                }
                            } else {
                                System.out.println("Asiento ya reservado.");
                            }
                        } else {
                            System.out.println("Fila o columna inválida.");
                        }
                    } else if (opcion == 2) { // ver asientos disponibles
                        for (int i = 0; i < 8; i++) {
                            for (int j = 0; j < 8; j++) {
                                System.out.print(reservas[fechaSeleccionada][i * 8 + j] + " ");
                            }
                            System.out.println();
                        }
                    } else if (opcion == 3) { // borrar la reserva
                        System.out.println("Ingrese fila (1-8): ");
                        int fila = scanner.nextInt() - 1;
                        System.out.println("Ingrese columna (1-8): ");
                        int columna = scanner.nextInt() - 1;

                        if (fila >= 0 && fila < 8 && columna >= 0 && columna < 8) {
                            int indice = fila * 8 + columna;
                            if (reservas[fechaSeleccionada][indice] == "1") {
                                reservas[fechaSeleccionada][indice] = "0";
                                System.out.println("Reserva borrada con éxito.");
                                entradasCompradas=--entradasCompradas; // quito el numero de entradas compradas
                            } else {
                                System.out.println("El asiento no está reservado.");
                            }
                        } else {
                            System.out.println("Fila o columna inválida.");
                        }
                    }
                } else {
                    System.out.println("Fecha inválida.");
                }
            } else if (opcion == 4) {
                continuar = false; //salida
            }
            if (continuar) {
                System.out.println("¿Desea realizar otra operación? (si/no)");
                String respuesta = scanner.next();
                if (respuesta.equalsIgnoreCase("no")) {
                    continuar = false; //salida
                }
            }
        }

        System.out.println("BOLETA");
        System.out.println("El total pagado es: $" + totalGastado);
        System.out.println("Número de entradas compradas: " + entradasCompradas);
        if (descuentos == 1) {
            System.out.println("Se aplicó 1 descuento");
        } else if (descuentos > 1) {
            System.out.println("Se aplicaron " + descuentos + " descuentos");
        }
        if (dosXuno >= 1) {
            System.out.println("Se aplicó " + dosXuno + " Dos por Uno");
        }
        System.out.println("Gracias por venir a Teatro Moro. ¡Hasta pronto!");
        scanner.close();
    }
}
