package nl.parkhaven.mysql;

class Wasschema {

	private final int _3weken = 273;
	private final String[] wasmachine = { "C1", "C2", "D1", "D2", "C3", "C4", "D3", "D4" };

	public static void main(String[] args) {
		new Wasschema().createQuery();
	}

	private void createQuery() {

		for (int i = 0; i < wasmachine.length; i++) {
			for (int j = 1; j <= _3weken; j++) {
				System.out
						.println("INSERT INTO wasschema (`week_dag_tijd_id`, `wasmachine_id`) VALUES (" + j + ", '" + wasmachine[i] + "');");
			}
		}
	}
}
