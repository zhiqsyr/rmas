package com.dl.core.jxls.export.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

import com.dl.core.jxls.common.model.ParameterSet;

public class DataContext {
	private ParameterSet parameters = new ParameterSet();

	private Map<String, Object> contextData = new HashMap<String, Object>();

	public void putRootData(Object data) {
		putData("t", data);
	}

	public void putData(String key, Object data) {
		contextData.put(key, data);
	}

	public ParameterSet getParameters() {
		return parameters;
	}

	public void setParameters(ParameterSet parameters) {
		this.parameters = parameters;
	}

	public void setContextData(Map<String, Object> contextData) {
		this.contextData = contextData;
	}

	public Object getData(String key) {
		return contextData.get(key);
	}

	public String renderString(String templateString) {
		try {
			//StringWriter result = new StringWriter();
			// Template t = new Template("default", new StringReader(
			// templateString), new Configuration());
			// t.process(contextData, result);
			return parseString(templateString);
//			return result.toString();
		} catch (Exception e) {
			// e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String evalExpression(String expression) throws Exception {

		JexlContext jexlContext = JexlHelper.createContext();
		jexlContext.setVars(contextData);
		Expression expr = ExpressionFactory.createExpression(expression);
		Object obj = expr.evaluate(jexlContext);
		if (obj != null) {
			return obj.toString();
		}
		return "";

	}

	public String parseString(String content) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;// /TODO 有BUG,在表达式不配对的情况下，不能检查出来
		char c;
		boolean inEL = false;
		List<EL> list = new ArrayList<EL>();
		while (i < content.length()) {
			c = content.charAt(i++);
			if (c == '$' && i < content.length()) {
				c = content.charAt(i++);
				if (c == '{') {
					int endIndex = content.indexOf("}", i);
					if (endIndex != -1) {
						list.add(new EL());
						inEL = true;
					} else {// 后面已经没有表达式结束符了
						sb.append("${");
					}
				} else {
					if (inEL) {
						getCurrentEL(list).appendContennt("$");
					} else {
						sb.append("$");
					}
				}
			} else if (c == '}') {
				// 表达式结束
				EL last = list.get(list.size() - 1);
				list.remove(list.size() - 1);
				if (list.size() <= 0) {
					inEL = false;
				}
				String _content_last = replaceString(last.content());
				String result = evalExpression(_content_last);
				if (inEL) {
					getCurrentEL(list).appendContennt("\"");
					getCurrentEL(list).appendContennt(result);
					getCurrentEL(list).appendContennt("\"");
				} else {
					sb.append(result);
				}
			} else {
				if (inEL) {
					getCurrentEL(list).appendContennt(c);
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}
	
	private String replaceString (String content){
		try {
			return new String(content.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	public EL getCurrentEL(List<EL> list) {
		return list.get(list.size() - 1);
	}

	private static class EL {
		private StringBuilder content = new StringBuilder();

		public void appendContennt(Object o) {
			content.append(o);
		}

		public String content() {
			return content.toString();
		}
	}

	public static void main(String[] args) throws Exception {
		DataContext context = new DataContext();
		context.putData("str", "string");
		context.putData("_index", 1);
//		context.putData("Util", ExpressionUtils.getInstance());
		context.putData("map1", new HashMap<String, Object>()
		{
			{
				this.put("key1", "value1");
				this.put("key2", 123);
			}

		});
		// System.out.println(context
		// .renderString("  aflajkfdljas ${str}  ,${$_index+1}"));

		System.out.println(context.parseString("dfajl ${str}"));
		System.out.println(context.parseString("dfajl ${_index+1}"));
		System.out.println(context.parseString("dfajl ${${\"str\"}}"));
		System.out.println(context.parseString("dfajl ${map1.key1}"));
		System.out.println(context.parseString("dfajl ${map1.key2}"));
		System.out.println(context.parseString("==== ${Util.switchValue(_index<3,'汉字','444')}"));
//		System.out.println(context.parseString("dfajl ${_index<3?\"小于\":\"大于\"}"));
		System.out
				.println(context
						.parseString("dfajl ${_index+${_index+${_index+${_index}}}} fdsa"));
		System.out
				.println(context
						.parseString("dfajl ${_index+${_index+${_index+${_index}}}} ${ fdsa"));
	}

}
