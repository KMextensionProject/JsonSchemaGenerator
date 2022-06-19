package sk.golddiger.jsonschemagenerator.exceptions;

@SuppressWarnings("serial")
public class InvalidJsonType extends RuntimeException {

	public InvalidJsonType() {
		super();
	}

	public InvalidJsonType(String msg) {
		super(msg);
	}

}
