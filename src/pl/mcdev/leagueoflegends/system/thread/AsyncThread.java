package pl.mcdev.leagueoflegends.system.thread;

import java.util.ArrayList;
import java.util.List;

import pl.mcdev.leagueoflegends.util.Logger;


public class AsyncThread extends Thread {
	
	private static AsyncThread inst;
	private List<Action> actions = new ArrayList<>();
	private static List<Action> temp = new ArrayList<>();
	private Object locker = new Object();
	
	public AsyncThread(){
		inst = this;
		this.setName("AsyncThread");
		Logger.info("Available Processors: " + Runtime.getRuntime().availableProcessors());
		Logger.info("Active Threads: " + Thread.activeCount());
	}

	@Override
	public void run() {
		while(true) try {
			List<Action> currently = new ArrayList<Action>(actions);
			actions.clear();
			if(currently != null) execute(currently);
			synchronized (locker){
				locker.wait();
			}
		} catch (InterruptedException e){
			if(Logger.exception(e.getCause())) e.printStackTrace();
		}
    }
	
	private void execute(List<Action> actions){
		for(Action action : actions){
			try {
				action.execute();
			} catch (Exception e) {
				if(Logger.exception(e.getCause())) e.printStackTrace();
			}
		}
	}
	
	public static void action(Action... actions){
		AsyncThread it = getInst();
		for(Action action : temp) if(!it.actions.contains(action)) it.actions.add(action);
		for(Action action : actions) if(!it.actions.contains(action)) it.actions.add(action);
		temp.clear();
		synchronized(getInst().locker){
			getInst().locker.notify();
		}
	}
	
	public static void action(ActionType type){
		action(new Action(type));
	}
	
	public static void action(ActionType type, Object... values){
		action(new Action(type, values));
	}
	
	public static void actions(ActionType type){
		Action action = new Action(type);
		if(!temp.contains(action)) temp.add(action);
	}
	
	public static void actions(ActionType type, Object... values){
		Action action = new Action(type, values);
		if(!temp.contains(action)) temp.add(action);
	}
	
	public static AsyncThread getInst(){
		if(inst == null) new AsyncThread().start();
		return inst;
	}
}