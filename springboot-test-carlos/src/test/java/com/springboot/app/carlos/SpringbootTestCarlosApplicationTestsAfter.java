package com.springboot.app.carlos;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.app.carlos.models.bean.TransactionModel;
import com.springboot.app.carlos.models.bean.TransactionModel.Status;

/**
 * @author CarlosS.
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootTestCarlosApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootTestCarlosApplicationTestsAfter {

	  @Autowired
	  private TestRestTemplate restTemplate;

	  @LocalServerPort
	  private int port;

	  private String getRootUrl() {
	    return "http://localhost:" + port + "/api/v1";
	  }
	  
	  /**
	     * Aqui probamos el metodo que crea la transaccion
	     * @throws ParseException 
	     */
	    @Test
	    @Before
	    public void testZCreate() throws ParseException {
	        //HttpHeaders headers = new HttpHeaders();
	        //HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	        
	        TransactionModel transaction = new TransactionModel();
	        transaction.setReference("12345");
	        transaction.setAccountIban("ES9820385778983000760236");
	        
			LocalDate ldt = LocalDate.now().plusDays(300);
			Date toTest =Date.from(ldt.atStartOfDay(ZoneId.systemDefault()).toInstant());
	        //Date temp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2019-07-16T16:55:42.000Z");
	        //BEFORE TODAY
	        //Date temp = new SimpleDateFormat("yyyy-MM-dd").parse("2019-07-16T16:55:42.000Z");
	        //TODAY
	        //Date temp = new SimpleDateFormat("yyyy-MM-dd").parse("2020-07-23T16:55:42.000Z");
	        
	        //AFTER TODAY
	        //Date temp = new SimpleDateFormat("yyyy-MM-dd").parse(toTest+"T16:55:42.000Z");
	        
	        transaction.setDate(toTest);
	        
	        transaction.setAmount(193.38);
	        transaction.setFee(3.18);
	        transaction.setDescription("Restaurant payment");
	        
	        ResponseEntity<TransactionModel> response = restTemplate.postForEntity(getRootUrl() + "/create", transaction, TransactionModel.class);

	        Assert.assertNotNull(response.getBody());
	        assertEquals("12345", response.getBody().getReference());
	        assertEquals("ES9820385778983000760236", response.getBody().getAccountIban());
	        Assert.assertNotNull(response.getBody().getDate());
	        assertEquals(193.38,  response.getBody().getAmount(), 0.001);
	        assertEquals(3.18,  response.getBody().getFee(), 0.001);
	        assertEquals("Restaurant payment", response.getBody().getDescription());
	    }
	 	    
	    /**
	     * Aqui probamos el metodo que devuelve el estado de la transaccion tipo INTERNAL
	     */
	    @Test
	    public void testGetStatusF() {
	    	System.out.println("entro al statusF");
	    	String ref = "12345";
	    	String client = "CLIENT";
	    	
	        TransactionModel response = restTemplate.getForObject(getRootUrl() + "/status/"+ref+"/"+client, TransactionModel.class);
	        Assert.assertNotNull(response);
	        assertEquals("12345", response.getReference());
	        assertEquals(Status.valueOf("FUTURE"),  response.getStatus());
	        assertEquals(190.20, response.getAmount(), 0.001);

	    }
	    /**
	     * Aqui probamos el metodo que devuelve el estado de la transaccion tipo ATM
	     */
	    @Test
	    public void testGetStatusG() {
	    	System.out.println("entro al statusG");
	    	String ref = "12345";
	    	String client = "ATM";
	    	
	        TransactionModel response = restTemplate.getForObject(getRootUrl() + "/status/"+ref+"/"+client, TransactionModel.class);
	        Assert.assertNotNull(response);
	        assertEquals("12345", response.getReference());
	        assertEquals(Status.valueOf("PENDING"),  response.getStatus());
	        assertEquals(190.20, response.getAmount(), 0.001);

	    }
	    /**
	     * Aqui probamos el metodo que devuelve el estado de la transaccion tipo INTERNAL
	     */
	    @Test
	    public void testGetStatusH() {
	    	System.out.println("entro al statusH");
	    	String ref = "12345";
	    	String client = "INTERNAL";
	    	
	        TransactionModel response = restTemplate.getForObject(getRootUrl() + "/status/"+ref+"/"+client, TransactionModel.class);
	        Assert.assertNotNull(response);
	        assertEquals("12345", response.getReference());
	        assertEquals(Status.valueOf("FUTURE"),  response.getStatus());
	        assertEquals(193.38, response.getAmount(), 0.001);
	        assertEquals(3.18, response.getFee(), 0.001);

	    }
	    
}
