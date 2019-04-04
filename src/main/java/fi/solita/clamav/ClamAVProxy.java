package fi.solita.clamav;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ClamAVProxy {

  @Value("${clamd.host}")
  private String hostname;

  @Value("${clamd.port}")
  private int port;

  @Value("${clamd.timeout}")
  private int timeout;

  /**
   * @return Clamd status.
   * @throws java.io.IOException
   */
  @RequestMapping(value="/", produces=MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody StatusResponse ping() throws IOException {
    ClamAVClient client = new ClamAVClient(hostname, port, timeout);
    StatusResponse response = new StatusResponse(client.ping());
    return response;
  }

  /**
   * @param name
   * @param file
   * @return Clamd scan result
   * @throws java.io.IOException
   */
  @RequestMapping(value="/scan", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody StatusResponse handleFileUpload(@RequestParam("name") String name,
                                               @RequestParam("file") MultipartFile file) throws IOException{
    if (!file.isEmpty()) {
      ClamAVClient client = new ClamAVClient(hostname, port, timeout);
      byte[] r = client.scan(file.getInputStream());
      StatusResponse response = new StatusResponse(ClamAVClient.isCleanReply(r));
      return response;
    } else {
        throw new IllegalArgumentException("empty file");
    }
  }

  /**
   * @param name
   * @param file
   * @return Clamd scan reply
   * @throws java.io.IOException
   */
  @RequestMapping(value="/scanReply", method=RequestMethod.POST)
  public @ResponseBody String handleFileUploadReply(@RequestParam("name") String name,
                                                    @RequestParam("file") MultipartFile file) throws IOException{
    if (!file.isEmpty()) {
      ClamAVClient client = new ClamAVClient(hostname, port, timeout);
      return new String(client.scan(file.getInputStream()));
    } else {
        throw new IllegalArgumentException("empty file");
    }
  }
}
