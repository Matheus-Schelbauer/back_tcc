package br.com.proxinvest.proxinvest.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.proxinvest.proxinvest.repository.AssetOriginalRepository;

@Service
public class BrApiService {

    private final HttpClient httpClient;

    @Autowired
    private AssetOriginalRepository repo;

    public BrApiService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void getCotacoesAcoes() {
        String url = String.format(Locale.US,
                "https://brapi.dev/api/quote/list?token=qU69XGxpPXpVpfkbkcNGPS&type=stock");

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Erro na requisição: " + response.statusCode());
                System.out.println("Body: " + response.body());
                return;
            }

            JSONObject json = new JSONObject(response.body());

            if (!json.has("stocks")) {
                System.out.println("Nenhuma ação encontrada na resposta.");
                return;
            }

            JSONArray stocks = json.getJSONArray("stocks");

            for (int i = 0; i < stocks.length(); i++) {
                JSONObject item = stocks.getJSONObject(i);

                String codigo = item.optString("stock", "").trim().toUpperCase();
                String nome = item.optString("name", "Desconhecida");
                double preco = item.optDouble("close", 0.0);
                BigDecimal precoDecimal = BigDecimal.valueOf(preco);

                System.out.println("Ação: " + codigo + " | Nome: " + nome + " | Preço: " + preco);

                repo.findByTicketCode(codigo).ifPresentOrElse(cotacao -> {
                    cotacao.setUnitaryValue(precoDecimal);
                    repo.save(cotacao);
                    System.out.println("✅ Atualizado: " + codigo + " | Preço: " + precoDecimal);
                }, () -> {
                    System.out.println("⚠️ Não encontrado no banco: " + codigo);
                });
            }

        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException("Erro ao buscar dados na BrAPI: ", e);
        }
    }
}
