package de.paymill.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.junit.Test;

import de.paymill.Paymill;
import de.paymill.TestCase;
import de.paymill.model.Event;
import de.paymill.model.Webhook;
import de.paymill.model.Webhook.EventType;

public class WebhookServiceTest extends TestCase {
	@Test
	public void testCreateWebhook() {
		WebhookService srv = Paymill.getService(WebhookService.class);
		URL url = getWebhookUrl();
		Webhook webhook = srv.create(url, EventType.SUBSCRIPTION_SUCCEEDED,
				EventType.SUBSCRIPTION_FAILED);
		assertNotNull(webhook);
	}

	@Test
	public void bugParseEvent() {
		String oldKey = Paymill.getApiKey();
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/offerbug.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Paymill.setApiKey(oldKey);
		}
	}
	@Test
	public void testParseEvent() {
		String oldKey = Paymill.getApiKey();
		try {
			Paymill.setApiKey("dummy");
			WebhookService srv = Paymill.getService(WebhookService.class);
			Event event = srv.parse(new FileInputStream(new File("test/failed.js")));
			assertNotNull(event);
			event = srv.parse(new FileInputStream(new File("test/success.js")));
			assertNotNull(event);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Paymill.setApiKey(oldKey);
		}
	}
}