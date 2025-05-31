// import java.io.IOException;
// import java.net.URI;
// import java.net.URISyntaxException;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.util.Locale;

// import org.json.JSONObject;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class BrApiService {
//     private final HttpClient httpClient;

//     @Autowired
//     private AssetOriginal repo;

//     public BrApiService() {
//         this.httpClient = HttpClient.newHttpClient();
//     }

//     public HttpResponse<String> getCotacoesAcoes() {
//         String url = String.format(Locale.US,
//                 "https://brapi.dev/api/quote/list?token=qU69XGxpPXpVpfkbkcNGPS&type=stock");

//         try {
//             HttpRequest request = HttpRequest.newBuilder()
//                     .uri(new URI(url))
//                     .GET()
//                     .build();

//             // Executa a chamada para API
//             HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//             // Converte a string pra JSON bruto
//             JSONObject json = new JSONObject(response.body());

//             // A lista tá no campo "stocks"
//             JSONArray stocks = json.getJSONArray("indexes");

//             // Itera por cada item do array
//             for (int i = 0; i < stocks.length(); i++) {
//                 JSONObject item = stocks.getJSONObject(i);

//                 String codigo = item.getString("stock");
//                 String nome = item.optString("name", "Desconhecida");
//                 double preco = item.optDouble("close", 0.0);

//                 System.out.println("Ação: " + codigo + " | Nome: " + nome + " | Preço: " + preco);

//                 repo.findByStock(codigo).ifPresentOrElse(cotacao -> {
//                     cotacao.setUnitaryValue(preco);
//                     repo.save(cotacao);
//                     System.out.println("Atualizou " + codigo + " com valor: " + preco);
//                 }, () -> {
//                     System.out.println("Não encontrou " + codigo + " no banco!");
//                 });
//             }
//         } catch (IOException | InterruptedException | URISyntaxException e) {
//             throw new RuntimeException("Error in Nominatim Service: ", e);
//         }
//     }
// }
