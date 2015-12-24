package org.powertrip.excalibot.common.plugins.sample;

import org.powertrip.excalibot.common.com.Task;
import org.powertrip.excalibot.common.com.TaskResult;
import org.powertrip.excalibot.common.plugins.KnightPlug;

/**
 * Created by Jaime on 23/12/2015.
 * 00:53
 */
public class SampleKnightPlug extends KnightPlug{
	@Override
	public TaskResult submit(Task task) {
		return new TaskResult()
				.setTaskId(task.getTaskId())
				.setSuccessful(true)
				.setKnightId(task.getKnightId())
				.setResponse("msg", "k, good enough");
	}

	@Override
	public TaskResult cancel(long l) {
		return new TaskResult()
				.setTaskId(l)
				.setSuccessful(true)
				.setKnightId(knightInfo.getId())
				.setResponse("msg", "sure, it's canceled");
	}
}
