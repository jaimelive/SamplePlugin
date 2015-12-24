package org.powertrip.excalibot.common.plugins.sample;

import org.powertrip.excalibot.common.com.KnightInfo;
import org.powertrip.excalibot.common.com.Task;
import org.powertrip.excalibot.common.com.TaskResult;
import org.powertrip.excalibot.common.plugins.ArthurPlug;
import org.powertrip.excalibot.common.plugins.interfaces.KnightManagerInterface;
import org.powertrip.excalibot.common.plugins.interfaces.TaskManagerInterface;
import org.powertrip.excalibot.common.plugins.sample.helper.Help;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jaime on 23/12/2015.
 * 00:45
 */
public class SampleArthurPlug extends ArthurPlug{

	public SampleArthurPlug(KnightManagerInterface knightManager, TaskManagerInterface taskManager) {
		super(knightManager, taskManager);
	}

	@Override
	public String help() {
		Help help = new Help();
		return help.getHelp().concat("\nThis is just a sample!\n No help will come from the elves this day.");
	}

	@Override
	public TaskResult check(Task t) {
		TaskResult result = new TaskResult();
		result
				.setSuccessful(true)
				.setTaskId(t.getTaskId())
				.setResponse("total", String.valueOf(taskManager.getKnightCount(t.getTaskId())))
				.setResponse("done", String.valueOf(taskManager.getResultCount(t.getTaskId())));
		return result;
	}

	@Override
	public TaskResult get(Task t) {
		return new TaskResult()
				.setTaskId(t.getTaskId())
				.setSuccessful(true)
				.setResponse("fake", "this is a fake result");
	}


	@Override
	public TaskResult submit(Task task) {
		List<KnightInfo> knights = knightManager.getFreeKnightList(10000);

		TaskResult result = new TaskResult().setTaskId(task.getTaskId());



		if(knights.size()>0) {
			Task botTask = new Task()
					.setTaskId(task.getTaskId())
					.setKnightId(knights.get(0).getId())
					.setCommand(task.getCommand())
					.setParametersMap(task.getParametersMap());

			List<Task> botTasks = new LinkedList<>();
			botTasks.add(botTask);
			deployTasks(botTasks);
			result
					.setSuccessful(true)
					.setResponse("ok", "Task accepted with id: " +  task.getTaskId());

		}else{
			result
					.setSuccessful(false)
					.setResponse("reason", "not enough bots available");
		}

		return result;
	}


}
