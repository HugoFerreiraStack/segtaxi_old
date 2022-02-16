package br.com.seg.econotaxi.util;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SmsUtil {

    private static final String URL = "http://api.facilitamovel.com.br/api/simpleSend.ft?";
    private static final String PARAM_USER = "user=";
    private static final String PARAM_PASSWORD = "password=";
    private static final String PARAM_DESTINATARIO = "destinatario=";
    private static final String PARAM_EXTERNAL_KEY = "externalkey=";
    private static final String PARAM_MSG = "msg=";

    //private static final String USER = "segrastreadores";
    //private static final String PASS = "SEGbrasil2018";
    private static final String EXTERNAL_KEY = "123";

    private static final String SEPARADOR = "&";

    public synchronized static void sendSms(Long numero, String mensagem, String usuario, String senha) {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(getUrl(numero, mensagem, usuario, senha), HttpMethod.GET, null, String.class);

        System.out.println(response.getBody());
    }

    private static String getUrl(Long numero, String mensagem, String usuario, String senha) {
        StringBuilder url = new StringBuilder();

        url.append(URL);
        url.append(PARAM_USER);
        url.append(usuario);
        url.append(SEPARADOR);
        url.append(PARAM_PASSWORD);
        url.append(senha);
        url.append(SEPARADOR);
        url.append(PARAM_DESTINATARIO);
        url.append(numero);
        url.append(SEPARADOR);
        url.append(PARAM_EXTERNAL_KEY);
        url.append(EXTERNAL_KEY);
        url.append(SEPARADOR);
        url.append(PARAM_MSG);
        url.append(mensagem);

        return url.toString();
    }

}
