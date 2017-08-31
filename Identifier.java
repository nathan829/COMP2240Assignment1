/*
  Identifier.java

  Author: Nathan Anstess
  Student Number: c3202195
  Date created: 24/08/17
  Last updated: 29/05/17

  Description: 	A singleton class which returns unique increasing numerical identifications.
*/

public class Identifier {

	private static Identifier instance;
	private static int id;

	private Identifier() {
		id = 0;
	}

	public static int getID() {
		if(instance == null) {
			instance = new Identifier();
		}

		return id++;
	}

}