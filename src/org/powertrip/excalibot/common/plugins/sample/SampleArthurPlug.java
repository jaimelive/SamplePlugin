package org.powertrip.excalibot.common.plugins.sample;

import org.powertrip.excalibot.common.com.*;
import org.powertrip.excalibot.common.plugins.ArthurPlug;
import org.powertrip.excalibot.common.plugins.interfaces.arthur.KnightManagerInterface;
import org.powertrip.excalibot.common.plugins.interfaces.arthur.TaskManagerInterface;
import org.powertrip.excalibot.common.utils.logging.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jaime on 23/12/2015.
 * 00:45
 */
public class SampleArthurPlug extends ArthurPlug {


	public SampleArthurPlug(KnightManagerInterface knightManager, TaskManagerInterface taskManager) {
		super(knightManager, taskManager);
	}

	@Override
	public PluginHelp help() {
		PluginHelp help = new PluginHelp();
		help.setHelp("Sample Plugin: useful for stuff.");
		return help;
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
		List<SubTaskResult> subResults = taskManager.getAllResults(t.getTaskId());
		TaskResult result = new TaskResult()
				.setTaskId(t.getTaskId())
				.setSuccessful(true);

		if(subResults!=null){
			int i = 0;
			for(SubTaskResult sr : subResults){
				result.setResponse(String.valueOf(i++), sr.getResponseAsString());
			}
			if(subResults.size() > 0) result.setComplete(true);
		}
		return result;
	}

	@Override
	public void handleSubTaskResult(Task task, SubTaskResult subTaskResult) {
		Logger.log(subTaskResult.getResponseAsString());
	}


	@Override
	public TaskResult submit(Task task) {
		List<KnightInfo> knights = knightManager.getFreeKnightList(1000000);

		TaskResult result = new TaskResult().setTaskId(task.getTaskId());

		if(knights.size()>0) {
			SubTask botTask = new SubTask(task, knights.get(0));

			List<SubTask> botTasks = new LinkedList<>();
			botTasks.add(botTask);
			botTasks.forEach(knightManager::dispatchToKnight);
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
