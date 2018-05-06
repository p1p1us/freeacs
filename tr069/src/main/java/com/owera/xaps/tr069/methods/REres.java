package com.owera.xaps.tr069.methods;

import com.owera.xaps.tr069.HTTPReqResData;
import com.owera.xaps.tr069.exception.TR069Exception;
import com.owera.xaps.tr069.xml.Parser;

public class REres {

	public static void process(HTTPReqResData reqRes) throws TR069Exception {

		reqRes.getRequest().setMethod(TR069Method.REBOOT);
		Parser parser = new Parser(reqRes.getRequest().getXml());
		if (parser.getHeader().getNoMoreRequests() != null && parser.getHeader().getNoMoreRequests().getNoMoreRequestFlag())
			reqRes.getSessionData().setNoMoreRequests(true);
	}
}
