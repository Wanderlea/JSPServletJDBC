package tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TagProjectJSP extends SimpleTagSupport{
	
	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		
		JspWriter out = getJspContext().getOut();
		out.println("This is my own custom tag");
	}

}
