package com.owera.xaps.tr069;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;

import com.owera.common.log.Context;
import com.owera.xaps.base.BaseCache;
import com.owera.xaps.base.BaseCacheException;
import com.owera.xaps.base.Log;
import com.owera.xaps.tr069.exception.TR069DatabaseException;
import com.owera.xaps.tr069.xml.TR069TransactionID;

public class HTTPReqResData {
	private HTTPReqData request;

	private HTTPResData response;

	private HttpServletRequest req;

	private HttpServletResponse res;

	private Throwable throwable;

	private TR069TransactionID TR069TransactionID;

	private SessionData sessionData;

	private Document doc;

	public HTTPReqResData(HttpServletRequest req, HttpServletResponse res) throws TR069DatabaseException {
		this.req = req;
		this.res = res;
		this.request = new HTTPReqData();
		this.response = new HTTPResData();

		String sessionId = req.getSession().getId();
		try {
			sessionData = (SessionData) BaseCache.getSessionData(sessionId);
		} catch (BaseCacheException tr069Ex) {
			Context.remove(Context.X);
			HttpSession session = req.getSession();
			Log.debug(HTTPReqResData.class, "Sessionid " + sessionId + " did not return a SessionData object from cache, must create a new SessionData object");
			Log.debug(HTTPReqResData.class, "Sessionid " + session.getId() + " created: " + session.getCreationTime() + ", lastAccess:" + session.getLastAccessedTime() + ", mxInactiveInterval:" + session.getMaxInactiveInterval());
			sessionData = new SessionData(sessionId);
			BaseCache.putSessionData(sessionId, sessionData);
		}
		if (sessionData.getStartupTmsForSession() == null)
			sessionData.setStartupTmsForSession(System.currentTimeMillis());
		if (sessionData.getUnitId() != null)
			Context.put(Context.X, sessionData.getUnitId(), BaseCache.SESSIONDATA_CACHE_TIMEOUT);
		Log.debug(HTTPReqResData.class, "Adding a HTTPReqResData object to the list");
		sessionData.getReqResList().add(this);
	}

	public HTTPReqData getRequest() {
		return request;
	}

	public HTTPResData getResponse() {
		return response;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public TR069TransactionID getTR069TransactionID() {
		return TR069TransactionID;
	}

	public void setTR069TransactionID(TR069TransactionID transactionID) {
		TR069TransactionID = transactionID;
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public HttpServletResponse getRes() {
		return res;
	}

	public SessionData getSessionData() {
		return sessionData;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

}
