//package com.example.musicat.config;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.servlet.annotation.WebListener;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionAttributeListener;
//import javax.servlet.http.HttpSessionBindingEvent;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//import org.springframework.stereotype.Component;
//
//import lombok.extern.java.Log;
//import lombok.extern.slf4j.Slf4j;
//
//@WebListener
//@Slf4j
//public class SessionConfig implements HttpSessionListener, HttpSessionAttributeListener {
//
//	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
//
//	@Override
//	public void sessionCreated(HttpSessionEvent se) {
//		// HttpSessionListener.super.sessionCreated(se);
//		System.out.println("session created");
//		sessions.put(se.getSession().getId(), se.getSession());
//		log.info("로그인 유저 : " + se.getSession().getAttribute("loginUser"));
//	}
//
//	@Override
//	public void attributeAdded(HttpSessionBindingEvent se) {
//		// TODO Auto-generated method stub
//		//HttpSessionAttributeListener.super.attributeAdded(se);
//		log.info("Session Attribute Added");
//		log.info("로그인 유저 : " + se.getSession().getAttribute("loginUser"));
//	}
//
//	@Override
//	public void attributeReplaced(HttpSessionBindingEvent se) {
//		// TODO Auto-generated method stub
//		//HttpSessionAttributeListener.super.attributeReplaced(se);
//		log.info("Session Attribute Replaced");
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent se) {
//		// HttpSessionListener.super.sessionDestroyed(se);
//		if (sessions.get(se.getSession().getId()) != null) {
//
//			log.info("로그아웃 유저 : " + se.getSession().getAttribute("loginUser"));
//			sessions.get(se.getSession().getId()).invalidate();
//			sessions.remove(se.getSession().getId());
//		}
//	}
//
//	// 중복로그인 지우기
//	public synchronized static String getSessionidCheck(String AttributeName, String compareId) {
//		String result = "";
//		for (String key : sessions.keySet()) {
//			HttpSession value = sessions.get(key);
////			if (value != null && value.getAttribute(AttributeName) != null
////					&& value.getAttribute(AttributeName).toString().equals(compareId)) {
////				System.out.println(compareId);
////				System.out.println(value.getAttribute(AttributeName).toString());
////				result = key.toString();
////			}
//			if(value != null && value.getAttribute(AttributeName) != null) {
//				String[] str = value.getAttribute(AttributeName).toString().split(",");
//				log.info("로그인 멤버 넘버 : " + str[0]);
//				if( str[0].equals(compareId)) {
//					log.info("중복 로그인 삭제 : " + value.getAttribute(AttributeName).toString());
//
//					result = key.toString();
//				}
//			}
//		}
//		removeSessionForDoubleLogin(result);
//		return result;
//	}
//
//	private static void removeSessionForDoubleLogin(String userId) {
//		if (userId != null && userId.length() > 0) {
//			sessions.get(userId).invalidate();
//			sessions.remove(userId);
//		}
//	}
//
//}
