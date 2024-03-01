import javax.swing.SwingUtilities;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class P1 {
    int renglon;
    int posicion;
    nodo cabeza, p;
    Lexico lexi = new Lexico();
    String[] linea = null;
    boolean error = false;
    String aux = "";

    public P1(nodo c) {
        cabeza = c;
        renglon = 1;
        posicion = 0;
        p = cabeza;
    }

    static String errores[][] = {
            // 0 1 <------ NÚMERO DE COLUMNA DEL ARRELGO
            /* 0 */ { "Se espera un dígito", "500" },
            /* 2 */ { "Se espera un caracter", "501" },
            /* 2 */ { "Se espera un +", "502" },
            /* 2 */ { "Se espera un -", "503" },
            /* 3 */ { "Se espera un *", "504" },
            /* 2 */ { "Se espera un /", "505" },
            /* 2 */ { "Se espera un ;", "506" },
            /* 2 */ { "Se espera un (", "507" },
            /* 2 */ { "Se espera un )", "508" },
            /* 2 */ { "Se espera un <", "509" },
            /* 2 */ { "Se espera un >", "510" },
            /* 2 */ { "Se espera un =", "511" },
            /* 2 */ { "Se espera un :=", "512" },
            /* 2 */ { "Se espera un ,", "513" },
            /* 2 */ { "Se espera un .", "514" },
            /* 2 */ { "Se espera cerrar una cadena", "515" },
            /* 2 */ { "Se espera un E", "516" },
            /* 2 */ { "Se espera la palabra reservada program", "517" },
            /* 2 */ { "Se espera la palabra reservada var", "518" },
            /* 2 */ { "Se espera la palabra reservada procedure", "519" },
            /* 2 */ { "Se espera la palabra reservada begin", "520" },
            /* 2 */ { "Se espera la palabra reservada end", "521" },
            /* 2 */ { "Se espera la palabra reservada if", "522" },
            /* 2 */ { "Se espera la palabra reservada while", "523" },
            /* 2 */ { "Se esperaba un tipo de dato valido", "524" },
            /* 2 */ { "Se espera la palabra reservada then", "525" },
            /* 2 */ { "Se espera la palabra reservada do", "526" },
            /* 2 */ { "Identificador no declarado", "527" },
            /* 2 */ { "Se espera un comparador logico", "528" },
            { "Se espera un caracter o digito", "529" },
            { "Error sintactico", "530"}
    };
    // se me buggeo
    // el droidcam

    public String Syntax(int numeroError, String[] lineaStrings) {
        String mensajeerror = "error no encontrado";
        int posss = 0;
        for (String[] fila : errores) {
            if (fila.length >= 2 && fila[1].equals(String.valueOf(numeroError))) {
                // Devuelve la descripción si encuentra el número de error
                
                mensajeerror = "Error: " + fila[0] +", renglon: " + p.renglon;
            }
        }
        error = true;
        System.out.println(mensajeerror);
        return mensajeerror;
    }

    public String tokennodo() {
        String NodoImprimido = "text de ejemplo";
        //p = cabeza;
        if (p != null) {
            NodoImprimido = "" + p.token;
            System.out.println("nodo: "+NodoImprimido);
            p = p.sig;
        }else{
            System.out.println("no cargo el nodo");
        }
        return NodoImprimido;
    }

    public String program() {
        if ("301".equals(tokennodo())) {// program
            if ("100".equals(tokennodo())) {// identificador
                if ("111".equals(tokennodo())) {// abrir parentesis (
                    do {
                        if ("100".equals(tokennodo())) {
                            aux = tokennodo();
                        }else{
                            Syntax(527, linea);
                        }
                    } while ("114".equals(aux)|| !"112".equals(aux));
                    if ("112".equals(aux)) {// cerrar parentesis
                        if ("113".equals(tokennodo())) {// punto y coma
                            // abrir boloque
                            bloque();

                            if("319".equals(aux)){
                                aux = tokennodo();
                                if("121".equals(aux)){
                                    if(error == true){
                                        return "Error en el Analisis Sintactico";
                                    }else{
                                        return "Analisis Sintactico Terminado Correctamente";
                                    }
                                }else{
                                    Syntax(514, linea);
                                }
                            }else{
                                return Syntax(521, linea);
                            }
                        } else {
                            return Syntax(506, linea);
                        }
                    } else {
                        return Syntax(508, linea);
                    }
                } else {
                    return Syntax(507, linea);
                }
            } else {
                return Syntax(527, linea);
            }
        } else {
            return Syntax(517, linea);
        }
        return "Syntax Finalizado";
    }

    public String bloque() {
        aux = tokennodo();
        do {
            switch (aux) {
            case "302":
                identificadortipo();
                break;
            case "320":
                procedure();
                break;
            case "318":
                instruccion();
                if("319".equals(aux)){

                }else{
                    Syntax(521, linea);
                }
                break;
            default:
                return Syntax(530,linea);
            }
        } while ("302".equals(aux)||"320".equals(aux)||"318".equals(aux));
        return "";
    }

    public String identificadortipo(){
                aux = tokennodo();
                do {
                    if ("100".equals(aux)) {
                        aux = tokennodo();
                    }else{
                        return Syntax(530, linea);
                    }
                } while ("114".equals(aux));
                if ("119".equals(aux)) {// dos puntos
                    aux = tokennodo();
                    switch(aux){
                        case "324":
                            aux = tokennodo();
                            if ("113".equals(aux)){
                                aux = tokennodo();
                                if("100".equals(aux)){

                                }else{
                                    if(!"320".equals(aux) && !"318".equals(aux)){
                                        return Syntax(530, linea);
                                    }else{
                                        if ("320".equals(aux)) {
                                            
                                        }else{
                                            if ("318".equals(aux)) {
                                                
                                            }else{
                                                return Syntax(530, linea);
                                            }
                                        }
                                    }
                                }
                            }else{
                                return Syntax(506, linea);
                            }
                        break;
                        case "325":
                            aux = tokennodo();
                            if ("113".equals(aux)){
                                aux = tokennodo();
                                if("100".equals(aux)){

                                }else{
                                    if(!"320".equals(aux) && !"318".equals(aux)){
                                        return Syntax(530, linea);
                                    }else{
                                        if ("320".equals(aux)) {
                                            
                                        }else{
                                            if ("318".equals(aux)) {
                                                
                                            }else{
                                                return Syntax(530, linea);
                                            }
                                        }
                                    }
                                }
                            }else{
                                return Syntax(506, linea);
                            }
                        break;
                        case "326":
                            aux = tokennodo();
                            if ("113".equals(aux)){
                                aux = tokennodo();
                                if("100".equals(aux)){

                                }else{
                                    if(!"320".equals(aux) && !"318".equals(aux)){
                                        return Syntax(530, linea);
                                    }else{
                                        if ("320".equals(aux)) {
                                            
                                        }else{
                                            if ("318".equals(aux)) {
                                                
                                            }else{
                                                return Syntax(530, linea);
                                            }
                                        }
                                    }
                                }
                            }else{
                                return Syntax(506, linea);
                            }
                        break;  
                        case "327":
                            aux = tokennodo();
                            if ("113".equals(aux)){
                                aux = tokennodo();
                                if("100".equals(aux)){

                                }else{
                                    if(!"320".equals(aux) && !"318".equals(aux)){
                                        return Syntax(530, linea);
                                    }else{
                                        if ("320".equals(aux)) {
                                            
                                        }else{
                                            if ("318".equals(aux)) {
                                                
                                            }else{
                                                return Syntax(530, linea);
                                            }
                                        }
                                    }
                                }
                            }else{
                                return Syntax(506, linea);
                            }
                        break;
                        case "328":
                            aux = tokennodo();
                            if ("113".equals(aux)){
                                aux = tokennodo();
                                if("100".equals(aux)){

                                }else{
                                    if(!"320".equals(aux) && !"318".equals(aux)){
                                        return Syntax(530, linea);
                                    }else{
                                        if ("320".equals(aux)) {
                                            
                                        }else{
                                            if ("318".equals(aux)) {
                                                
                                            }else{
                                                return Syntax(530, linea);
                                            }
                                        }
                                    }
                                }
                            }else{
                                return Syntax(506, linea);
                            }
                        break;
                        default:
                            
                            return  Syntax(530, linea);
                    }
                        
                } else {
                    return Syntax(512, linea);
                }
                return "";
    }

    public String listaParametros() {
        aux = tokennodo();
        if ("111".equals(aux)){
            do {
                aux = tokennodo();
                if ("100".equals(aux)){
                    aux = tokennodo();
                    if("119".equals(aux)){
                        aux = tokennodo();
                        switch (aux) {
                            case "324":
                                
                                break;
                            case "325":
                                
                                break;
                            case "326":
                                
                                break;
                            case "327":
                                
                                break;
                            case "328":
                                
                                break;
                            default:
                                return Syntax(123, linea);
                        }
                        aux = tokennodo();
                        if(!"113".equals(aux)){
                            Syntax(506, linea);
                        }
                    }else{
                        return Syntax(512, linea);
                    }
                }else{
                    return Syntax(527, linea);
                }
            } while ("113".equals(aux));
            if ("112".equals(aux)){

            }else{
                return Syntax(508, linea);
            }
        } 
        return "";
    }

    public String procedure(){
                aux = tokennodo();
                if("100".equals(aux)){
                    listaParametros();
                    aux = tokennodo();
                    if("113".equals(aux)){
                        bloque();
                        aux = tokennodo();
                        if("113".equals(aux)){
                            if ("320".equals(aux)||"318".equals(aux)){

                            }else{
                                return Syntax(530, linea);
                            }
                        }
                    }
                }else{
                    Syntax(527, linea);
                }
                return "";
    }
    public String instruccion() {
        aux = tokennodo();
        switch (aux) {
            case "100":
                aux = tokennodo();
                if("120".equals(aux)){
                    expresion();
                }else{
                    return Syntax(512, linea);
                }
                break;
            case "318":
                do {
                    if("113".equals(aux)){
                        instruccion();
                    }
                    aux = tokennodo();
                } while ("113".equals(aux));
                if("319".equals(aux)){

                }else{
                    return Syntax(521, linea);
                }
                break;
            case "315":
                expresion();
                if ("316".equals(aux)) {
                    instruccion();
                    aux = tokennodo();
                    if("317".equals(aux)){
                        instruccion();
                    }else{
                        return Syntax(530, linea);
                    }
                }else{
                    return Syntax(525, linea);
                }
                break;
            case "308":
                expresion();
                if("314".equals(aux)){
                    instruccion();
                }else{
                    return Syntax(526, linea);
                }
                break;
            default:
                Syntax(530, linea);
                break;
        }
        return "";
    }

    public String expresion() {
        expresionsimple();
        if("115".equals(aux) || "102".equals(aux) || "104".equals(aux) || "121".equals(aux)){
            expresionsimple();
        }else{
            //return Syntax(528, linea);
        }
        return "";
    }

    public String expresionsimple() {
        termino();
        if("106".equals(aux)||"107".equals(aux)){
            while ("106".equals(aux)||"107".equals(aux)){
            termino();
            }
        }else{
            //return Syntax(528, linea);
        }
        return "";
    }

    public String termino() {
        factor();
        if ("108".equals(aux)||"117".equals(aux)) {
            while ("108".equals(aux)||"117".equals(aux)){
            factor();
            }
        } else {
            //return Syntax(528, linea);   
        }
        return "";
    }

    public String factor() {
        aux = tokennodo();
        if ("100".equals(aux)){
            if("100".equals(aux)){
                aux = tokennodo();
            }
        }else{
                if("111".equals(aux)){
                    expresion();
                    aux = tokennodo();
                    if("112".equals(aux)){

                    }else{
                        return Syntax(508, linea);
                    }
                }else{
                    return Syntax(507, linea);
                }
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            P2 p2 = new P2();
        });
    }

}