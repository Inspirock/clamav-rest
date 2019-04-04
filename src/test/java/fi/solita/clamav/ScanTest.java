/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.solita.clamav;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.assertEquals;

/**
 * These tests assume clamav-rest Docker container is running and responding locally.
 * @author ashutoshmimani
 */
public class ScanTest
{
  @Test
  public void testEicar() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Api-Key", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("name", "eicar.com.txt");
    body.add("file", new ClassPathResource("eicar.com.txt"));
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate t = new RestTemplate();
    ResponseEntity response = t.postForEntity("http://localhost:8080/scan", requestEntity, String.class);
    String s = response.getBody().toString();
    assertEquals("{\"status\":false}", s);
  }

  @Test
  public void testNoVirus() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("X-Api-Key", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("name", "test.txt");
    body.add("file", new ClassPathResource("test.txt"));
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate t = new RestTemplate();
    ResponseEntity response = t.postForEntity("http://localhost:8080/scan", requestEntity, String.class);
    String s = response.getBody().toString();
    assertEquals("{\"status\":true}", s);
  }
}
