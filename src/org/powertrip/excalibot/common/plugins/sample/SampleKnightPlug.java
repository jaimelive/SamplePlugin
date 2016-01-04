package org.powertrip.excalibot.common.plugins.sample;

import org.powertrip.excalibot.common.com.SubTask;
import org.powertrip.excalibot.common.plugins.KnightPlug;
import org.powertrip.excalibot.common.plugins.interfaces.knight.ResultManagerInterface;
import org.powertrip.excalibot.common.utils.logging.Logger;

/**
 * Created by Jaime on 23/12/2015.
 * 00:53
 */
public class SampleKnightPlug extends KnightPlug{


	public SampleKnightPlug(ResultManagerInterface resultManager) {
		super(resultManager);
	}

	@Override
	public boolean run(SubTask subTask) {
		int i=0;

		try {
			while(!Thread.currentThread().isInterrupted() && (i++) < 20) {
				Thread.sleep(20000);
				Logger.log("STEP");
			}
			resultManager.returnResult(
					subTask.createResult()
							.setSuccessful(true)
							.setKnightId(subTask.getKnightInfo().getId())
							.setResponse("resp", "task finished")
			);

		} catch (InterruptedException e) {
			Logger.error("[SamplePlugin]" + e.getMessage());
			return false;
		}
		Logger.log("[SamplePlugin]: Good bye.");
		return true;
	}

}
