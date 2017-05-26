package pl.mcdev.leagueoflegends.system.thread;

import java.util.Arrays;

import pl.mcdev.leagueoflegends.system.DataManager;

public class Action {
	
	private final ActionType action;
	private Object[] values;

	public Action(ActionType action){
		this.action = action;
	}
	
	public Action(ActionType action, Object... values){
		this.action = action;
		this.values = values;
	}
	
	public ActionType getActionType(){
		return this.action;
	}
	
	public Object[] getValues(){
		return this.values;
	}
	
	public void execute(){
		switch(action){
		case SAVE_DATA:
			DataManager.getInst().save();
			break;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + Arrays.hashCode(values);
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(this.getClass() != o.getClass()) return false;
		Action a = (Action) o;
		if(action != a.getActionType()) return false;
		if(values == null && a.getValues() == null) return true;
		return false;
	}
	
}