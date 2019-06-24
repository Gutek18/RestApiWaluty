import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class Waluty {


    public static void main(String[] args) {
        try {
            Client klient = Client.create();

            WebResource webResource = klient.resource("http://api.nbp.pl/api/exchangerates/rates/c/usd/today/");
            ClientResponse clientResponse = webResource.accept("application/json").get(ClientResponse.class);
            if (clientResponse.getStatus() != 200) {
                throw new RuntimeException("Błąd po stronie serwera, KOD BŁĘDU: " + clientResponse.getStatus());
            }
            String odpowiedz = clientResponse.getEntity(String.class);

            System.out.println(odpowiedz);

            /*Jackson*/
            ObjectMapper objectMapper = new ObjectMapper();
            Kursy kursWaluty = objectMapper.readValue(odpowiedz, Kursy.class);
            System.out.println("Waluta: "+kursWaluty.getCurrency() + "\n"+"Kurs skupu: " + kursWaluty.getRates().get(0).getBid() +
                    "\n" + "Kurs sprzedaży: " + kursWaluty.getRates().get(0).getAsk() + "\n" + "Data kursu: " + kursWaluty.getRates().get(0).getEffectiveDate());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
