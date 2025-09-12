package py.com.lab;


import py.com.jsifen.domain.service.ConsultaRucService;
import jakarta.json.JsonObject;

public class MainLab {

    public static void main(String[] args) {
        System.out.println("¡Hola! Tu laboratorio funciona correctamente.");

        // Llamada al método que usa ConsultaRucService
        MainLab lab = new MainLab();
        lab.runConsultaRuc("1234567"); // ejemplo de RUC
    }



    // Nueva función que llama a ConsultaRucService
    public void runConsultaRuc(String ruc) {
        ConsultaRucService service = new ConsultaRucService();
        JsonObject resultado = service.consultar(ruc); // llama al método consultar
        System.out.println("Resultado del servicio: " + resultado.toString());
    }
}
