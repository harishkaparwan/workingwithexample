package com;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestScript {

	/**
	 * @param args
	 * @throws ScriptException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws ScriptException, FileNotFoundException {
		// TODO Auto-generated method stub
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("groovy");
       // engine.eval(new FileReader("/Users/harish/Desktop/Desktop/tret/Test.groovy"));
        
        System.out.println("----"+engine.LANGUAGE);
        
	}

}
