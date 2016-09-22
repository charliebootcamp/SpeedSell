package com.bootcamp.portal.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import com.bootcamp.portal.mytests.TestCategoryCtrl;

public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestCategoryCtrl.class);
		for (Failure failure : result.getFailures()){
			System.out.println("Failure: "+failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}
