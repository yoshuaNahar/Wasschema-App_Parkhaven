package nl.parkhaven;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import nl.parkhaven.modal.AllAppointmentDaoTests;
import nl.parkhaven.modal.AllUserDaoTests;

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(AllUserDaoTests.class, AllAppointmentDaoTests.class);

		System.out.println(
				"Tests run: " + result.getRunCount() + ", Failures: " + result.getFailureCount() + ", Skipped: "
						+ result.getIgnoreCount() + ", Time elapsed (in milliseconds): " + result.getRunTime() + "\n");

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getTrace());
			System.out.println(failure.toString() + "\n");
		}

		System.out.println("All tests passed: " + result.wasSuccessful());
	}
}
