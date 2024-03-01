import java.io.RandomAccessFile;

class Lexico {
    nodo cabeza = null, p;
    int estado = 0, columna, valorMT, numRenglon = 1;
    int caracter = 0;
    String lexema = "";
    boolean errorEncontrado = false;
    String archivo = null;
    String resultado = "";

    int [][] Matriz = 
  //                       0        1        2        3      4       5    6    7    8    9     10   11   12   13   14     15        16    
  //                       L        D        <        >      =       +    -    *    /    (     )    ;    ,    :     .     eb        oc    Estados
                        {{ 1      , 2       ,3       ,4  ,   5,     106, 107, 108,  6,  111,  112,  113, 114, 11,  121    ,0       ,503}    //0
                        ,{ 1      , 1       ,100     ,100,   100,   100, 100, 100, 100, 100,  100,  100, 100, 100, 100    ,100     ,100}    //1 
                        ,{ 101    , 2       ,101     ,101,   101,   101, 101, 101, 101, 101,  101,  101, 101, 101,  9     ,101     ,101}    //2
                        ,{ 102    ,102      ,102     ,102,   103,   102, 102, 102, 102, 102,  102,  102, 102, 102, 102    ,102     ,102}    //3
                        ,{ 104    ,104      ,104     ,104,   105,   104, 104, 104, 104, 104,  104,  104, 104, 104, 104    ,104     ,104}    //4
                        ,{ 115    ,115      ,115     ,115,   116,   115, 115, 115, 115, 115,  115,  115, 115, 115, 115    ,115     ,115}    //5
                        ,{ 117    ,117      ,117     ,117,   117,   117, 117,   7, 117, 117,  117,  117, 117, 117, 117    ,117     ,117}    //6
                        ,{ 7      ,7        ,7       ,7  ,     7,     7,   7,   8,   7,   7,    7,    7,   7,  7,   7     ,7       ,501}    //7
                        ,{ 500    ,500      ,500     ,500,   500,   500, 500, 500, 118, 500,  500,  500, 500, 500, 500    ,500     ,500}    //8
                        ,{ 502    ,10       ,502     ,502,   502,   502, 502, 502, 502, 502,  502,  502, 502, 502, 502    ,502     ,502}    //9
                        ,{ 118    ,10       ,118     ,118,   118,   118, 118, 118, 118, 118,  118,  118, 118, 118, 118    ,118     ,118}    //10
                        ,{ 119    ,119      ,119     ,119,   12,    119, 119, 119, 119, 119,  119,  119, 119, 119, 119    ,119     ,119}    //11
                        ,{ 120    ,120      ,120     ,120,   120,   120, 120, 120, 120, 120,  120,  120, 120, 120, 120    ,120     ,120}};  //12

    String[][] palabrasReservadasPascal = {
            { "program",   "301" },
            { "var",       "302" },
            { "while",     "308" },
            { "do",        "314" },
            { "if",        "315" },
            { "then",      "316" },
            { "else",      "317" },
            { "begin",     "318" },
            { "end",       "319" },
            { "procedure", "320" },
            { "integer",   "324" },
            { "real",      "325" },
            { "boolean",   "326" },
            { "char",      "327" },
            { "string",    "328" }
    };

    String errores[][] = {
            // 0 1 <------ NÚMERO DE COLUMNA DEL ARRELGO
            /* 0 */ { "Se espera un dígito", "500" },
            /* 1 */ { "Se espera cerrar el comentario", "501" },
            /* 2 */ { "Se espera un &", "502" },
            /* 3 */ { "Se espera un |", "503" },
            /* 4 */ { "Se espera cerrar cadena", "504" },
            /* 5 */ { "Símbolo no valido", "505" }
    };

    RandomAccessFile file = null;
    
    public void Lexico() {
        int nux = 0;
        try {
            file = new RandomAccessFile(archivo, "r");
            while (caracter != -1) {
                nux = caracter;
                caracter = file.read();
                //System.out.println("caracter: "+caracter);

                if (Character.isLetter(((char) caracter))) {
                    columna = 0;
                    //System.out.println("Es letra");
                } else if (Character.isDigit((char) caracter)) {
                    columna = 1;
                    //System.out.println("Es numero");
                } else {
                    //System.out.println("Es simbolo");
                    switch ((char)caracter) {
                        case '<': columna = 2;
                        //System.out.println(columna);
                        break;
                        case '>': columna = 3;
                        //System.out.println(columna);
                        break;
                        case '=': columna = 4;
                        //System.out.println(columna);
                        break;
                        case '+': columna = 5;
                        //System.out.println(columna);
                        break;
                        case '-': columna = 6;
                        //System.out.println(columna);
                        break;
                        case '*': columna = 7;
                        //System.out.println(columna);
                        break;
                        case '/': columna = 8;
                        //System.out.println(columna);
                        break;
                        case '(': columna = 9;
                        //System.out.println(columna);
                        break;
                        case ')': columna = 10;
                        //System.out.println(columna);
                        break;
                        case ';': columna = 11;
                        //System.out.println(columna);
                        break;
                        case ',': columna = 12;
                        //System.out.println(columna);
                        break;
                        case ':': columna = 13;
                        //System.out.println(columna);
                        break;
                        case '.': columna = 14;
                        //System.out.println(columna);
                        break;
                        case ' ': columna = 15;
                        //System.out.println(columna);
                        break;
                        case '\n':
                            if(nux!='\n'){
                                columna = 16;
                                numRenglon++;
                            }
                            break;
                        default:  columna = 16;
                        //System.out.println(columna);
                        break;
                    }
                } // FIN if caracter
                valorMT = Matriz[estado][columna];
                //System.out.println("valorMT :"+valorMT);
                if (valorMT < 100) {// cambiar de estado
                    estado = valorMT;
                    //System.out.println("Cambio de estado a: "+estado);
                    if (estado == 0) {
                        //System.out.println("estado 0 y limpiamos lexema");
                        lexema = "";
                    } else {
                        lexema = lexema + (char) caracter;
                        //System.out.println("actualizmaos el lexema: "+lexema);
                    }
                } else if (valorMT >= 100 && valorMT < 500) {// Estados finales
                    //System.out.println("Estado: "+estado+", Columna: "+columna+", lexema: "+lexema+", valorMT: "+valorMT);
                    if (valorMT == 100) {
                        //System.out.println("Validar si es una palabra reservada");
                        validarSiEsPalabraReservada();
                    }
                    //System.out.println("Revisar el valorMT para ver si recorrer el file seek o agregar el caracter al lexema");
                    if ( valorMT ==100 || valorMT == 101 || valorMT == 102 || valorMT == 103 || valorMT == 104 || valorMT == 105
                    || valorMT == 115 || valorMT == 116 || valorMT == 117 || valorMT == 119 || valorMT == 120 || valorMT >=200) {
                        file.seek(file.getFilePointer() - 1);
                        //System.out.println("ValorMT: "+valorMT+", recorrer el file seeker");
                    } else {
                        lexema = lexema + (char) caracter;
                        //System.out.println("actualizamos el lexema: "+lexema);
                    }
                    insertarNodo();
                    estado = 0;
                    lexema = "";
                } else {
                    if (caracter!=10 && caracter!=-1 && caracter!=39 && caracter!= 191 && caracter != 63 && caracter != 161 && caracter != 33){
                        imprimirMensajeError();
                        System.out.println("error encontrado: "+caracter);
                        estado = 0;
                        // break;
                    }
                }
            } // FIN While
            if (errorEncontrado == false) {
                System.out.println(ImprimirNodo());
                System.out.println("Analisis lexico Terminado!\n");
            } else {
                System.out.println("Error en el Análisis Léxico");
            }
        } // Fin try
        catch (Exception e) {
            System.out.println("fallo en el try "+e.getMessage());
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                System.out.println("fallo en el finally "+e.getMessage());
            }
        }
    }

    private void validarSiEsPalabraReservada() {
        for (String[] palReservada : palabrasReservadasPascal) {
            if (lexema.equals(palReservada[0])) {
                valorMT = Integer.parseInt(palReservada[1]);
                //System.out.println("valorMT: "+valorMT+", es palabra reservada");
            }
        }
    }

    public boolean respuestaErrores(){
        return errorEncontrado;
    }

    private void imprimirMensajeError() {
        if (caracter != -1 && caracter != 13) {
            for (String[] e : errores) {
                if (valorMT == Integer.valueOf(e[1])) {
                    //System.out.println(caracter);
                    resultado = resultado + "[ERROR " + valorMT + "]:" + e[0]
                            + " caracter " + (char) caracter + " en el renglon " + numRenglon + "\n";
                }
            }
        }
        errorEncontrado = true;
    }

    private void insertarNodo() {
        nodo nodo = new nodo(lexema, valorMT, numRenglon);
        //System.out.println("insertando nodo: (lexema: "+lexema+", valorMT: "+valorMT+", Renglon: "+numRenglon+")");
        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.sig = nodo;
            p = nodo;
        }
    }

    public String ImprimirNodo() {
        p = cabeza;
        String NodoImprimido = "Palabra\tToken\tRenglon\n";
        while (p != null) {
            NodoImprimido = NodoImprimido + (p.lexema + "\t" + p.token + "\t" + p.renglon) + "\n";

            p = p.sig;
        }
        return NodoImprimido;
    }
}
