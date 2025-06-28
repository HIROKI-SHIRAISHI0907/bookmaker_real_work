package dev.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.application.domain.service.RegisterBusinessLogicBatchImpl;

@SpringBootApplication
public class BookmakersApplication {

	public static void main(String[] args) {
    	RegisterBusinessLogicBatchImpl businessLogicBatch = new RegisterBusinessLogicBatchImpl();
        String testPath = "/Users/shiraishitoshio/bookmaker/";

        // 実行
        businessLogicBatch.execute(testPath);
	}
}
