package nl.parkhaven.mysql;

public class MySqlQuery {

	public static void main(String[] args) {
		new MySqlQuery().addWasschemaData();
	}

	// Populate the wasschema table inside the database. This is only for 1 day!
	public void addWasschemaData() {
		String[] wasmachine_id = { "A1", "B1", "C1", "D1", "A2", "B2", "C2", "D2" };
		final int tijd_aantal_max = 10;

		for (int wasmachine_aantal = 0; wasmachine_aantal < wasmachine_id.length; wasmachine_aantal++) {
			for (int tijd_aantal = 1; tijd_aantal <= tijd_aantal_max; tijd_aantal++) {
				System.out.println("INSERT INTO" + " `parkhaven`.`wasschema` (`wasmachine_id`, `tijd_id`)"
						+ " VALUES ('" + wasmachine_id[wasmachine_aantal] + "', '" + tijd_aantal + "');");
			}
		}
	}
}
