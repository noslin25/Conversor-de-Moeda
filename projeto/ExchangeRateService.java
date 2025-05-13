import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public record ExchangeRateService() {
    private static final String API_KEY = "960ca5e29c516f72479250e2";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Gson gson = new Gson();

    public double obterTaxaCambio(String from, String to) {
        try {
            String url = BASE_URL + from;
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ExchangeRateResponse apiResponse = gson.fromJson(response.body(), ExchangeRateResponse.class);

            if (apiResponse.result.equals("success")) {
                return apiResponse.conversion_rates.get(to);
            } else {
                System.out.println("Erro na resposta da API: " + apiResponse.result);
                return -1;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao obter taxa: " + e.getMessage());
            return -1;
        }
    }

    // Classe interna para deserialização com Gson
    private static class ExchangeRateResponse {
        String result;
        String base_code;
        Map<String, Double> conversion_rates;
    }
}
