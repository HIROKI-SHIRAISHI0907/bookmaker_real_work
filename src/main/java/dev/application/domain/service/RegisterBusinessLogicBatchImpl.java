package dev.application.domain.service;

import org.springframework.stereotype.Service;

import dev.application.common.logic.BookMakerDataRegisterCsvBusinessLogic;

/**
 * 登録業務ロジッククラス
 * @author shiraishitoshio
 *
 */
@Service
public class RegisterBusinessLogicBatchImpl implements BusinessLogicBatch {

	/**
	 * 業務ロジック
	 */
	//@Autowired
	//private BookMakerDataRegisterBusinessLogic businessLogic;

	@Override
	public int execute(String path) {
		//BookMakerDataRegisterBusinessLogic businessLogic = new BookMakerDataRegisterBusinessLogic();
		BookMakerDataRegisterCsvBusinessLogic businessLogic = new BookMakerDataRegisterCsvBusinessLogic();
		//int result = this.businessLogic.execute(path);
		int result = businessLogic.execute(path);
		return result;
	}

}
