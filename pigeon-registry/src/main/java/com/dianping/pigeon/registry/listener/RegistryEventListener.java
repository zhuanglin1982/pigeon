/**
 * Dianping.com Inc.
 * Copyright (c) 2003-2013 All Rights Reserved.
 */
package com.dianping.pigeon.registry.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dianping.pigeon.log.LoggerLoader;

/**
 * 将lion推送的动态服务信息发送到感兴趣的listener
 * 
 * @author marsqing
 * 
 */
public class RegistryEventListener {

	private static final Logger logger = LoggerLoader.getLogger(RegistryEventListener.class);

	private static List<ServiceProviderChangeListener> serviceProviderChangeListeners = new ArrayList<ServiceProviderChangeListener>();

	private static List<RegistryConnectionListener> registryConnectionListeners = new ArrayList<RegistryConnectionListener>();

	public synchronized static void addListener(ServiceProviderChangeListener listener) {
		serviceProviderChangeListeners.add(listener);
	}

	public synchronized static void removeListener(ServiceProviderChangeListener listener) {
		serviceProviderChangeListeners.remove(listener);
	}

	public synchronized static void addListener(RegistryConnectionListener listener) {
		registryConnectionListeners.add(listener);
	}

	public static void providerRemoved(String serviceName, String host, int port) {
		for (ServiceProviderChangeListener listener : serviceProviderChangeListeners) {
			listener.providerRemoved(new ServiceProviderChangeEvent(serviceName, host, port, -1));
		}
	}

	public static void providerAdded(String serviceName, String host, int port, int weight) {
		for (ServiceProviderChangeListener listener : serviceProviderChangeListeners) {
			ServiceProviderChangeEvent event = new ServiceProviderChangeEvent(serviceName, host, port, weight);
			listener.providerAdded(event);
		}
	}

	public static void hostWeightChanged(String host, int port, int weight) {
		for (ServiceProviderChangeListener listener : serviceProviderChangeListeners) {
			listener.hostWeightChanged(new ServiceProviderChangeEvent(null, host, port, weight));
		}
	}

	public static void connectionReconnected() {
		for (RegistryConnectionListener listener : registryConnectionListeners) {
			listener.reconnected();
		}
	}

}
