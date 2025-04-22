package utilidades;
/**
 * Representa una fecha con día, mes y año.
 * @author
 */
public class Fecha {
    private int dia;
    private int mes;
    private int anio;

    /**
     * Construye un objeto Fecha con el día, mes y año especificados.
     * @param dia El día del mes (1-31).
     * @param mes El mes del año (1-12).
     * @param anio El año.
     * @throws IllegalArgumentException Si el mes está fuera del rango válido.
     */
    public Fecha(int dia, int mes, int anio) {
        if (!esValida(dia,mes,anio)) {
            throw new IllegalArgumentException("La fecha tiene un formato incorrecto");
        }
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    /**
     * Obtiene el día de la fecha.
     * @return El día.
     */
    public int getDia() {
        return dia;
    }

    /**
     * Obtiene el mes de la fecha.
     * @return El mes.
     */
    public int getMes() {
        return mes;
    }

    /**
     * Obtiene el año de la fecha.
     * @return El año.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Verifica si una combinación dada de día, mes y año representa una fecha válida.
     * @param dia El día del mes (1-31).
     * @param mes El mes del año (1-12).
     * @param anio El año.
     * @return {@code true} si la fecha es válida, {@code false} en caso contrario.
     */
    public static boolean esValida(int dia, int mes, int anio) {
        if (anio < 0 || mes < 1 || mes > 12 || dia < 1) {
            return false;
        }
        return dia <= getDiasEnMes(mes, anio);
    }

    private static int getDiasEnMes(int mes, int anio) {
        int[] diasPorMes = {31,28,31, 30,31, 30,31,31, 30,31, 30,31};
        int dias= diasPorMes[mes-1];
        if (mes==2 && esBisiesto(anio)) {
            dias++;
        }
        return dias;
    }

    public static boolean esBisiesto(int anio) {
        if ( (anio % 400 == 0) ||
                ( (anio % 4 == 0) && (anio % 100 != 0) ) )
            return true;
        else return false;
    }

    /**
     * Determina si el año de esta fecha es bisiesto.
     * Un año es bisiesto si es divisible por 4, pero no por 100 a menos que también sea divisible por 400.
     * @return {@code true} si el año es bisiesto, {@code false} en caso contrario.
     */
    public boolean esBisiesto() {
        return esBisiesto(this.anio);
    }


    /**
     * Método que calcula el día del año que es una fecha determinadaPor ejemplo el 2/2/2025 es el día 31+2=33 del año
     * @return int número de día del año al que corresponde la fecha
     */
    public int diaDelAnio() {
        int[] diasPorMes = {31,28,31, 30,31, 30,31,31, 30,31, 30,31};
        int diasHastaFecha = 0;
        for (int i = 0; i < this.mes-1; i++) {
            diasHastaFecha += diasPorMes[i];
        }
        diasHastaFecha+=this.dia;
        if(this.mes >= 3 && esBisiesto()) {
            diasHastaFecha++;
        }
        return diasHastaFecha;
    }

    /**
     * Método que calucla si la fecha implicita del método es posterior a la pasada por parámetro o no
     * @param otraFecha
     * @return
     */
    private boolean esPosterior(Fecha otraFecha) {
        if (this.anio > otraFecha.anio) {
            return true;
        } else if (this.anio==otraFecha.anio) {
            return this.diaDelAnio()>otraFecha.diaDelAnio();
        } else {
            return false;
        }


    }





    /**
     * Calcula la diferencia en días entre esta fecha y otra fecha especificada.
     * @param otraFecha La otra fecha con la que se calculará la diferencia.
     * @return La diferencia en días (puede ser negativa si la otra fecha es posterior).
     */
    public long diasEntreFechas(Fecha otraFecha) {
        if (this.equals(otraFecha)) return 0;

        Fecha fechaMenor = this;
        Fecha fechaMayor = otraFecha;
        int invertirSigno = -1;

        // Determinar el orden de las fechas
        if (this.esPosterior(otraFecha)) {
            fechaMenor = otraFecha;
            fechaMayor = this;
            invertirSigno = 1;
        }

        // Caso: mismo año
        if (fechaMenor.anio == fechaMayor.anio) {
            int diferencia = fechaMayor.diaDelAnio() - fechaMenor.diaDelAnio();
            return invertirSigno * diferencia;
        }

        // Caso: años diferentes
        long diasTotales = 0;
        int diasAnio=365;
        if (fechaMenor.esBisiesto()) diasAnio++;
        diasTotales=diasAnio-fechaMenor.diaDelAnio();

        // Días en años intermedios
        for (int anio = fechaMenor.anio+1; anio <= fechaMayor.anio; anio++) {
            diasTotales=diasTotales+365;
            if (esBisiesto(anio)) {
                diasTotales++;
            }
        }

        // Sumamos los días transcurridos en la fecha mayor
        diasTotales += fechaMayor.diaDelAnio();
        return invertirSigno *diasTotales;

    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass()!= obj.getClass()) return false;
        Fecha fecha = (Fecha) obj;
        return dia == fecha.dia && mes == fecha.mes && anio == fecha.anio;
    }


}